
package de.m0ep.socc.core.connector.canvaslms;

import java.io.IOException;
import java.util.List;

import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.vocabulary.RDF;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.SIOCVocabulary;
import org.rdfs.sioc.Site;
import org.rdfs.sioc.Thread;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import de.m0ep.canvas.CanvasLmsClient;
import de.m0ep.canvas.Pagination;
import de.m0ep.canvas.exceptions.AuthorizationException;
import de.m0ep.canvas.exceptions.CanvasLmsException;
import de.m0ep.canvas.exceptions.NetworkException;
import de.m0ep.canvas.model.Course;
import de.m0ep.canvas.model.DiscussionTopic;
import de.m0ep.socc.core.connector.IConnector;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.exceptions.NotFoundException;

public class CanvasLmsServiceStructureReader implements IConnector.IServiceStructureReader {
    private CanvasLmsConnector connector;
    private CanvasLmsClient client;
    private Model model;

    public CanvasLmsServiceStructureReader(final CanvasLmsConnector connector) {
        this.connector = Preconditions.checkNotNull(
                connector,
                "Required parameter connector must be specified.");

        this.client = this.connector.getServiceClientManager().getDefaultClient();
        this.model = this.connector.getContext().getModel();
    }

    @Override
    public Site getSite() {
        URI serviceEndpointUri = connector.getService().getServiceEndpoint().asURI();

        if (!Site.hasInstance(
                connector.getContext().getModel(),
                serviceEndpointUri)) {
            Site result = new Site(
                    connector.getContext().getModel(),
                    connector.getService().getServiceEndpoint(), true);
            result.setName("Canvas LMS (" + serviceEndpointUri.toString() + ")");
            return result;
        }

        return Site.getInstance(
                connector.getContext().getModel(),
                serviceEndpointUri);
    }

    @Override
    public Forum getForum(String id) throws NotFoundException, AuthenticationException, IOException {
        Preconditions.checkNotNull(id, "Required parameter id must be specified.");
        Preconditions.checkArgument(!id.isEmpty(), "Required parameter id may not be empty.");

        long courseId;
        try {
            courseId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "The parameter id is invalid, was " + id);
        }

        try {
            Course course = client.courses()
                    .get(courseId)
                    .execute();
            return CanvasLmsSiocConverter.createSiocForum(connector, course);
        } catch (CanvasLmsException e) {
            if (e instanceof NetworkException) {
                throw new IOException(e);
            } else if (e instanceof AuthorizationException) {
                throw new AuthenticationException(e);
            } else if (e instanceof de.m0ep.canvas.exceptions.NotFoundException) {
                throw new NotFoundException(e);
            }

            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Forum> listForums() throws AuthenticationException, IOException {
        Pagination<Course> coursePages = null;
        List<Forum> result = Lists.newArrayList();

        try {
            coursePages = client.courses()
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

            throw new RuntimeException(e);
        }

        if (null != coursePages) {
            for (List<Course> courses : coursePages) {
                for (Course course : courses) {
                    result.add(CanvasLmsSiocConverter.createSiocForum(connector, course));
                }
            }
        }

        return result;
    }

    @Override
    public Thread getThread(String id, Container parent) throws NotFoundException,
            AuthenticationException, IOException {
        Preconditions.checkNotNull(id, "Required parameter id must be specified.");
        Preconditions.checkArgument(!id.isEmpty(), "Required parameter id may not be empty.");
        Preconditions.checkNotNull(parent, "Required parameter parent must be specified.");
        Preconditions.checkArgument(parent.hasIds(), "Required parameter parent has no id.");
        Preconditions.checkArgument(
                model.contains(parent, RDF.type, SIOCVocabulary.Forum),
                "Required parameter parent is no SIOC Forum.");
        Forum parentForum = Forum.getInstance(model, parent.getResource());

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
            DiscussionTopic discussionTopic = client.courses()
                    .discussionTopics(courseId)
                    .get(topicId)
                    .execute();

            return CanvasLmsSiocConverter.createSiocThread(connector, discussionTopic, parentForum);
        } catch (CanvasLmsException e) {
            if (e instanceof NetworkException) {
                throw new IOException(e);
            } else if (e instanceof AuthorizationException) {
                throw new AuthenticationException(e);
            } else if (e instanceof de.m0ep.canvas.exceptions.NotFoundException) {
                throw new NotFoundException(e);
            }

            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Thread> listThreads(Container parent) throws AuthenticationException, IOException {
        Preconditions.checkNotNull(parent, "Required parameter parent must be specified.");
        Preconditions.checkArgument(parent.hasIds(), "Required parameter parent has no id.");
        Preconditions.checkArgument(
                model.contains(parent, RDF.type, SIOCVocabulary.Forum),
                "Required parameter parent is no SIOC Forum.");
        Forum parentForum = Forum.getInstance(model, parent.getResource());

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
            discussionTopicPages = client.courses()
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

            throw new RuntimeException(e);
        }

        List<Thread> result = Lists.newArrayList();

        if (null != discussionTopicPages) {

            for (List<DiscussionTopic> topics : discussionTopicPages) {
                for (DiscussionTopic discussionTopic : topics) {
                    result.add(CanvasLmsSiocConverter.createSiocThread(
                            connector,
                            discussionTopic,
                            parentForum));
                }
            }
        }

        return result;
    }

}
