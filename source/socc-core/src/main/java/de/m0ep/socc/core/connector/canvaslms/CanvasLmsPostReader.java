
package de.m0ep.socc.core.connector.canvaslms;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.rdfs.sioc.Container;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.Thread;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;

import de.m0ep.canvas.CanvasLmsClient;
import de.m0ep.canvas.Pagination;
import de.m0ep.canvas.exceptions.AuthorizationException;
import de.m0ep.canvas.exceptions.CanvasLmsException;
import de.m0ep.canvas.exceptions.NetworkException;
import de.m0ep.canvas.model.Entry;
import de.m0ep.socc.core.connector.AbstractConnectorIOComponent;
import de.m0ep.socc.core.connector.IConnector.IPostReader;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.exceptions.NotFoundException;
import de.m0ep.socc.core.utils.RdfUtils;

public class CanvasLmsPostReader extends AbstractConnectorIOComponent implements IPostReader {

    public CanvasLmsPostReader(final CanvasLmsConnector connector) {
        super(connector);
    }

    @Override
    public boolean containsPosts(Container container) {
        Preconditions.checkNotNull(container, "Required parameter container must be specified.");

        return RdfUtils.isType(container.getModel(), container, Thread.RDFS_CLASS) &&
                container.toString().startsWith(getServiceEndpoint().toString()) &&
                container.hasParent() &&
                container.getParent().toString().startsWith(getServiceEndpoint().toString());
    }

    @Override
    public List<Post> readNewPosts(Date lastPostDate, long limit, Container container)
            throws AuthenticationException, IOException {
        Preconditions.checkArgument(
                containsPosts(container),
                "The container contains no posts on this service.");
        Preconditions.checkArgument(
                container.hasId(),
                "The container has no id.");
        Preconditions.checkArgument(
                container.getParent().hasId(),
                "The parent of the container has no id.");

        Container parentContainer = container.getParent();

        if (0 == limit) {
            return Lists.newArrayList();
        }

        long courseId;
        try {
            courseId = Long.parseLong(parentContainer.getId());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "The id of the containers parent is invalid: was "
                            + parentContainer.getId());
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
            entryPages = ((CanvasLmsClient) getDefaultClient()).courses()
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

            throw Throwables.propagate(e);
        }

        List<Post> result = Lists.newArrayList();
        if (null != entryPages) {
            for (List<Entry> entries : entryPages) {
                for (Entry entry : entries) {
                    if (null != lastPostDate && lastPostDate.before(entry.getCreatedAt())) {
                        return result;
                    }

                    result.add(CanvasLmsSiocConverter.createSiocPost(
                            (CanvasLmsConnector) connector,
                            entry,
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
    public boolean containsReplies(Post post) {
        Preconditions.checkNotNull(post, "Required parameter parent must be specified.");

        return post.toString().startsWith(getServiceEndpoint().toString()) &&
                post.hasContainer() &&
                containsPosts(post.getContainer());
    }

    @Override
    public List<Post> readNewReplies(Date lastReplyDate, long limit, Post parentPost)
            throws AuthenticationException, IOException {
        Preconditions.checkNotNull(
                parentPost,
                "Required parameter parentPost must be specified.");
        Preconditions.checkArgument(
                containsReplies(parentPost),
                "The parentPost contains no replies on this service.");
        Preconditions.checkArgument(
                parentPost.hasId(),
                "Required parameter parentPost has no id.");
        Preconditions.checkArgument(
                parentPost.getContainer().hasId(),
                "The container of the parentPost has no id.");
        Preconditions.checkArgument(
                parentPost.getContainer().getParent().hasId(),
                "The parent container of the container of the parentPost has no id.");

        Container container = parentPost.getContainer();
        Container parentContainer = container.getParent();

        if (0 == limit) {
            return Lists.newArrayList();
        }

        long courseId;
        try {
            courseId = Long.parseLong(parentContainer.getId());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "The id of the containers parent is invalid: was "
                            + parentContainer.getId());
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
            entryId = Long.parseLong(parentPost.getId());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "The id of the parentPost is invalid: was "
                            + container.getId());
        }

        Pagination<Entry> replyPages = null;
        try {
            replyPages = ((CanvasLmsClient) getDefaultClient()).courses()
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

            throw Throwables.propagate(e);
        }

        List<Post> result = Lists.newArrayList();
        if (null != replyPages) {
            for (List<Entry> entries : replyPages) {
                for (Entry entry : entries) {
                    if (null != lastReplyDate && lastReplyDate.before(entry.getCreatedAt())) {
                        return result;
                    }

                    result.add(CanvasLmsSiocConverter.createSiocPost(
                            (CanvasLmsConnector) connector,
                            entry,
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
