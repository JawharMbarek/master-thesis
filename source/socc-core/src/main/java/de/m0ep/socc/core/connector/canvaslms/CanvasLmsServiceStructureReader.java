
package de.m0ep.socc.core.connector.canvaslms;

import java.io.IOException;
import java.util.List;

import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Thread;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import de.m0ep.canvas.Pagination;
import de.m0ep.canvas.exceptions.AuthorizationException;
import de.m0ep.canvas.exceptions.CanvasException;
import de.m0ep.canvas.exceptions.NetworkException;
import de.m0ep.canvas.model.Course;
import de.m0ep.canvas.model.DiscussionTopic;
import de.m0ep.socc.core.connector.IServiceStructureReader;
import de.m0ep.socc.core.exceptions.AuthenticationException;

public class CanvasLmsServiceStructureReader implements IServiceStructureReader {
    private CanvasLmsConnector connector;
    private CanvasLmsConnectorClient client;

    public CanvasLmsServiceStructureReader(final CanvasLmsConnector connector) {
        this.connector = Preconditions.checkNotNull(
                connector,
                "Required parameter connector must be specified.");

        client = this.connector.getServiceClientManager().getDefaultClient();
    }

    @Override
    public Forum getForum(String id) throws IOException, AuthenticationException {
        Preconditions.checkNotNull(id, "Required parameter id must be specified.");
        Preconditions.checkArgument(!id.isEmpty(), "Required parameter id may not be empty.");

        try {
            Course course = client.getClient().courses().get(Long.parseLong(id)).execute();
            return CanvasLmsSiocConverter.createSiocForum(connector, course);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid id: Was " + id + ", should be a Long.");
        } catch (CanvasException e) {
            if (e instanceof NetworkException) {
                throw new IOException(e);
            } else if (e instanceof AuthorizationException) {
                throw new AuthenticationException(e);
            }

            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Forum> listForums() throws IOException, AuthenticationException {
        Pagination<Course> coursePages = null;
        List<Forum> result = Lists.newArrayList();

        try {
            coursePages = client.getClient()
                    .courses()
                    .list()
                    .executePagination();
        } catch (CanvasException e) {
            if (e instanceof NetworkException) {
                throw new IOException(e);
            } else if (e instanceof AuthorizationException) {
                throw new AuthenticationException(e);
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

        return listForums();
    }

    @Override
    public Thread getThread(String id, Container parent) throws IOException,
            AuthenticationException {
        Preconditions.checkNotNull(id, "Required parameter id must be specified.");
        Preconditions.checkArgument(!id.isEmpty(), "Required parameter id may not be empty.");
        Preconditions.checkNotNull(parent, "Required parameter parent must be specified.");
        Preconditions.checkArgument(parent.hasIds(), "Required parameter parent has no id.");

        // TODO Check NumberFormatException
        long topicId = Long.parseLong(id);
        long courseId = Long.parseLong(parent.getId());

        try {
            DiscussionTopic discussionTopic = client.getClient().courses()
                    .discussionTopics(courseId)
                    .get(topicId)
                    .execute();

            return CanvasLmsSiocConverter.createSiocThread(connector, discussionTopic, parent);
        } catch (CanvasException e) {
            if (e instanceof NetworkException) {
                throw new IOException(e);
            } else if (e instanceof AuthorizationException) {
                throw new AuthenticationException(e);
            }

            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Thread> listThreads(Container parent) throws IOException, AuthenticationException {
        // TODO Auto-generated method stub
        return null;
    }

}
