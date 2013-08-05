
package de.m0ep.socc.core.connector.canvaslms;

import java.io.IOException;
import java.util.List;

import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.SIOCVocabulary;
import org.rdfs.sioc.Site;
import org.rdfs.sioc.Thread;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;

import de.m0ep.canvas.CanvasLmsClient;
import de.m0ep.canvas.Pagination;
import de.m0ep.canvas.exceptions.AuthorizationException;
import de.m0ep.canvas.exceptions.CanvasLmsException;
import de.m0ep.canvas.exceptions.NetworkException;
import de.m0ep.canvas.model.Course;
import de.m0ep.canvas.model.DiscussionTopic;
import de.m0ep.socc.core.connector.AbstractConnectorIOComponent;
import de.m0ep.socc.core.connector.IConnector.IStructureReader;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.exceptions.NotFoundException;
import de.m0ep.socc.core.utils.RdfUtils;

public class CanvasLmsServiceStructureReader extends AbstractConnectorIOComponent implements
        IStructureReader {
    public CanvasLmsServiceStructureReader(final CanvasLmsConnector connector) {
        super(connector);
    }

    @Override
    public Site getSite() {
        if (!Site.hasInstance(
                getModel(),
                getServiceEndpoint())) {
            Site result = new Site(
                    getModel(),
                    getServiceEndpoint(), true);
            result.setName("Canvas LMS (" + getServiceEndpoint() + ")");
            return result;
        }

        return Site.getInstance(
                getModel(),
                getServiceEndpoint());
    }

    @Override
    public Forum getForum(String id) throws NotFoundException, AuthenticationException, IOException {
        Preconditions.checkNotNull(id,
                "Required parameter id must be specified.");
        Preconditions.checkArgument(!id.isEmpty(),
                "Required parameter id may not be empty.");

        long courseId;
        try {
            courseId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "The parameter id is invalid, was " + id);
        }

        try {
            Course course = ((CanvasLmsClient) getDefaultClient()).courses()
                    .get(courseId)
                    .execute();
            return CanvasLmsSiocConverter.createSiocForum(
                    (CanvasLmsConnector) connector,
                    course);
        } catch (CanvasLmsException e) {
            if (e instanceof NetworkException) {
                throw new IOException(e);
            } else if (e instanceof AuthorizationException) {
                throw new AuthenticationException(e);
            } else if (e instanceof de.m0ep.canvas.exceptions.NotFoundException) {
                throw new NotFoundException(e);
            }

            throw Throwables.propagate(e);
        }
    }

    @Override
    public List<Forum> listForums() throws AuthenticationException, IOException {
        Pagination<Course> coursePages = null;
        List<Forum> result = Lists.newArrayList();

        try {
            coursePages = ((CanvasLmsClient) getDefaultClient()).courses()
                    .list()
                    .executePagination();
        } catch (CanvasLmsException e) {
            if (e instanceof NetworkException) {
                throw new IOException(e);
            } else if (e instanceof AuthorizationException) {
                throw new AuthenticationException(e);
            } else if (e instanceof de.m0ep.canvas.exceptions.NotFoundException) {
                throw new NotFoundException(e);
            }

            throw Throwables.propagate(e);
        }

        if (null != coursePages) {
            for (List<Course> courses : coursePages) {
                for (Course course : courses) {
                    result.add(CanvasLmsSiocConverter.createSiocForum(
                            (CanvasLmsConnector) connector,
                            course));
                }
            }
        }

        return result;
    }

    @Override
    public Thread getThread(String id, Container contaienr) throws NotFoundException,
            AuthenticationException, IOException {
        Preconditions.checkNotNull(id,
                "Required parameter id must be specified.");
        Preconditions.checkArgument(!id.isEmpty(),
                "Required parameter id may not be empty.");
        Preconditions.checkNotNull(contaienr,
                "Required parameter parent must be specified.");
        Preconditions.checkArgument(contaienr.hasId(),
                "Required parameter parent has no id.");
        Preconditions.checkArgument(RdfUtils.isType(getModel(), contaienr, SIOCVocabulary.Forum),
                "Required parameter parent is no SIOC Forum.");

        Forum parentForum = Forum.getInstance(getModel(), contaienr.getResource());

        long topicId;
        try {
            topicId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("The parameter id is invalid, was " + id);
        }

        long courseId;
        try {
            courseId = Long.parseLong(parentForum.getId());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "The parameter parent contains no valid id, was "
                            + parentForum.getId());
        }

        try {
            DiscussionTopic discussionTopic = ((CanvasLmsClient) getDefaultClient()).courses()
                    .discussionTopics(courseId)
                    .get(topicId)
                    .execute();

            return CanvasLmsSiocConverter.createSiocThread(
                    (CanvasLmsConnector) connector,
                    discussionTopic,
                    parentForum);
        } catch (CanvasLmsException e) {
            if (e instanceof NetworkException) {
                throw new IOException(e);
            } else if (e instanceof AuthorizationException) {
                throw new AuthenticationException(e);
            } else if (e instanceof de.m0ep.canvas.exceptions.NotFoundException) {
                throw new NotFoundException(e);
            }

            throw Throwables.propagate(e);
        }
    }

    @Override
    public List<Thread> listThreads(Container container) throws AuthenticationException,
            IOException {
        Preconditions.checkNotNull(container, "Required parameter parent must be specified.");
        Preconditions.checkArgument(container.hasId(), "Required parameter parent has no id.");
        Preconditions.checkArgument(RdfUtils.isType(getModel(), container, SIOCVocabulary.Forum),
                "Required parameter parent is no SIOC Forum.");
        Forum parentForum = Forum.getInstance(getModel(), container.getResource());

        long courseId;
        try {
            courseId = Long.parseLong(parentForum.getId());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "The parameter parent contains an invalid id: "
                            + parentForum.getId());
        }

        Pagination<DiscussionTopic> discussionTopicPages = null;
        try {
            discussionTopicPages = ((CanvasLmsClient) getDefaultClient()).courses()
                    .discussionTopics(courseId)
                    .list()
                    .executePagination();
        } catch (CanvasLmsException e) {
            if (e instanceof NetworkException) {
                throw new IOException(e);
            } else if (e instanceof AuthorizationException) {
                throw new AuthenticationException(e);
            } else if (e instanceof de.m0ep.canvas.exceptions.NotFoundException) {
                throw new NotFoundException(e);
            }

            throw Throwables.propagate(e);
        }

        List<Thread> result = Lists.newArrayList();

        if (null != discussionTopicPages) {

            for (List<DiscussionTopic> topics : discussionTopicPages) {
                for (DiscussionTopic discussionTopic : topics) {
                    result.add(CanvasLmsSiocConverter.createSiocThread(
                            (CanvasLmsConnector) connector,
                            discussionTopic,
                            parentForum));
                }
            }
        }

        return result;
    }
}
