
package de.m0ep.socc.core.connector.canvaslms;

import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.util.Builder;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Thread;
import org.rdfs.sioc.UserAccount;

import de.m0ep.canvas.model.Course;
import de.m0ep.canvas.model.DiscussionTopic;
import de.m0ep.socc.core.exceptions.NotFoundException;
import de.m0ep.socc.core.utils.DateUtils;

public final class CanvasLmsSiocConverter {

    private CanvasLmsSiocConverter() {
    }

    public static Forum createSiocForum(CanvasLmsConnector connector, Course course) {
        URI uri = Builder.createURI(
                connector.getServiceEndpointUri().toString() + "/forum/" + course.getId());

        Forum result = null;

        if (!Forum.hasInstance(connector.getContext().getModel(), uri)) {
            result = new Forum(connector.getContext().getModel(), uri, true);

            result.setId(Long.toString(course.getId()));
            result.setNumThreads(0);
            result.setName(course.getName());
            result.setHost(connector.getSite());
            connector.getSite().addHostOf(result);
        }

        return Forum.getInstance(connector.getContext().getModel(), uri);
    }

    public static Thread createSiocThread(CanvasLmsConnector connector,
            DiscussionTopic discussionTopic, Container parent) {
        URI uri = Builder.createURI(
                connector.getServiceEndpointUri().toString()
                        + "/thread/"
                        + discussionTopic.getId());

        if (!Thread.hasInstance(connector.getContext().getModel(), uri)) {
            Thread result = new Thread(connector.getContext().getModel(), uri, true);
            result.setId(Long.toString(discussionTopic.getId()));
            result.setParent(parent);
            parent.addParentOf(result);

            result.setName(discussionTopic.getTitle());
            result.setCreated(DateUtils.formatISO8601(discussionTopic.getPostedAt()));
            result.setSeeAlso(Builder.createURI(discussionTopic.getHtmlURL()));

            UserAccount creator = null;
            try {
                creator = connector.getContext().getPersonAccountService().findUserAccount(
                        Long.toString(discussionTopic.getAuthor().getId()),
                        connector.getServiceEndpointUri());
            } catch (NotFoundException e) {
                URI userUri = Builder.createURI(connector.getServiceEndpointUri().toString()
                        + "/user/"
                        + discussionTopic.getAuthor().getId());

                creator = new UserAccount(connector.getContext().getModel(), userUri, true);
            }
        }

        return Thread.getInstance(connector.getContext().getModel(), uri);
    }
}
