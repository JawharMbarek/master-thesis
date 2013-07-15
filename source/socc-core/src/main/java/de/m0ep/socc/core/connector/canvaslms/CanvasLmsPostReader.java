
package de.m0ep.socc.core.connector.canvaslms;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.rdfs.sioc.Container;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.Thread;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import de.m0ep.canvas.CanvasLmsClient;
import de.m0ep.canvas.Pagination;
import de.m0ep.canvas.exceptions.AuthorizationException;
import de.m0ep.canvas.exceptions.CanvasLmsException;
import de.m0ep.canvas.exceptions.NetworkException;
import de.m0ep.canvas.model.Entry;
import de.m0ep.socc.core.connector.IConnector;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.exceptions.NotFoundException;
import de.m0ep.socc.core.utils.RdfUtils;

public class CanvasLmsPostReader implements IConnector.IPostReader {
    private CanvasLmsConnector connector;
    private CanvasLmsClient client;
    private String serviceEndpoint;

    public CanvasLmsPostReader(final CanvasLmsConnector connector) {
        this.connector = Preconditions.checkNotNull(
                connector,
                "Required parameter connector must be specified.");

        this.client = connector.serviceClientManager().getDefaultClient();
        this.serviceEndpoint = connector.getService().getServiceEndpoint().toString();
    }

    @Override
    public IConnector getConnector() {
        return connector;
    }

    @Override
    public boolean containsPosts(Container container) {
        Preconditions.checkNotNull(container, "Required parameter container must be specified.");

        if (!RdfUtils.isType(container.getModel(), container, Thread.RDFS_CLASS)) {
            return false;
        }

        String uri = container.toString();
        return uri.startsWith(serviceEndpoint);
    }

    @Override
    public List<Post> readNewPosts(Date lastPostDate, long limit, Container container)
            throws AuthenticationException, IOException {
        Preconditions.checkNotNull(
                container,
                "Required parameter container must be specified.");
        Preconditions.checkArgument(
                containsPosts(container),
                "The container contains no posts on this service.");
        Preconditions.checkArgument(
                container.hasId(),
                "The container has no id.");
        Preconditions.checkArgument(
                container.hasParent(),
                "Required paramter container has no parent.");
        Preconditions.checkArgument(
                container.getParent().hasId(),
                "The parent of the container has no id.");

        if (0 == limit) {
            return Lists.newArrayList();
        }

        long courseId;
        try {
            courseId = Long.parseLong(container.getParent().getId());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "The id of the containers parent is invalid: was "
                            + container.getParent().getId());
        }

        long topicId;
        try {
            topicId = Long.parseLong(container.getId());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "The id of the container is invalid: was "
                            + container.getId());
        }

        Pagination<Entry> entryPages = null;
        try {
            entryPages = client.courses()
                    .discussionTopics(courseId)
                    .entries(topicId)
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

        List<Post> result = Lists.newArrayList();
        if (null != entryPages) {
            for (List<Entry> entries : entryPages) {
                for (Entry entry : entries) {
                    if (null != lastPostDate && lastPostDate.before(entry.getCreatedAt())) {
                        return result;
                    }

                    result.add(CanvasLmsSiocConverter.createSiocPost(connector, entry,
                            container));

                    if (0 < limit && limit == result.size()) {
                        return result;
                    }
                }
            }
        }

        return result;
    }

    @Override
    public boolean containsReplies(Post parent) {
        Preconditions.checkNotNull(parent, "Required parameter parent must be specified.");

        Container parentContainer = parent.getContainer();
        if (!containsPosts(parentContainer)) {
            return false;
        }

        String uri = parent.toString();
        return uri.startsWith(serviceEndpoint);
    }

    @Override
    public List<Post> readNewReplies(Date lastReplyDate, long limit, Post parent)
            throws AuthenticationException, IOException {
        Preconditions.checkNotNull(
                parent,
                "Required parameter parent must be specified.");
        Preconditions.checkArgument(
                containsReplies(parent),
                "The parent post contains no replies on this service.");
        Preconditions.checkArgument(
                parent.hasId(),
                "Required parameter parent has no id.");
        Preconditions.checkArgument(
                parent.hasContainer(),
                "Required parameter parent has no container.");

        Container container = parent.getContainer();
        Preconditions.checkArgument(
                container.hasId(),
                "The container of the parent has no id.");
        Preconditions.checkArgument(
                container.hasParent(),
                "The container of the parent post has no parent container.");

        Container parentContainer = container.getParent();
        Preconditions.checkArgument(
                parentContainer.hasId(),
                "The parent container of the container of the parent post has no id.");

        if (0 == limit) {
            return Lists.newArrayList();
        }

        long courseId;
        try {
            courseId = Long.parseLong(container.getParent().getId());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "The id of the containers parent is invalid: was "
                            + container.getParent().getId());
        }

        long topicId;
        try {
            topicId = Long.parseLong(container.getId());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "The id of the container is invalid: was "
                            + container.getId());
        }

        long entryId;
        try {
            entryId = Long.parseLong(parent.getId());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "The id of the parent post is invalid: was "
                            + container.getId());
        }

        Pagination<Entry> replyPages = null;
        try {
            replyPages = client.courses()
                    .discussionTopics(courseId)
                    .entries(topicId)
                    .listReplies(entryId)
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

        List<Post> result = Lists.newArrayList();
        if (null != replyPages) {
            for (List<Entry> entries : replyPages) {
                for (Entry entry : entries) {
                    if (null != lastReplyDate && lastReplyDate.before(entry.getCreatedAt())) {
                        return result;
                    }

                    result.add(CanvasLmsSiocConverter.createSiocPost(connector, entry,
                            container));

                    if (0 < limit && limit == result.size()) {
                        return result;
                    }
                }
            }
        }

        return result;
    }
}
