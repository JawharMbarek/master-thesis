/*
 * The MIT License (MIT) Copyright © 2013 Florian Müller
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the “Software”), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
import de.m0ep.socc.core.utils.SiocUtils;

/**
 * @author Florian Müller
 */
public class CanvasLmsPostReader extends
        AbstractConnectorIOComponent<CanvasLmsConnector> implements
        IPostReader {

    private CanvasLmsClient defaultClient;

    /**
     * @param connector
     */
    public CanvasLmsPostReader(final CanvasLmsConnector connector) {
        super(connector);
        this.defaultClient = connector.getServiceClientManager()
                .getDefaultClient();
    }

    @Override
    public boolean containsPosts(final Container container) {
        return null != container
                && RdfUtils.isType(
                        container,
                        Thread.RDFS_CLASS)
                && SiocUtils.isContainerOfSite(
                        container,
                        getServiceEndpoint());
    }

    @Override
    public List<Post> readNewPosts(final Date since, final long limit,
            final Container container)
            throws AuthenticationException, IOException {
        if (0 == limit) {
            return Lists.newArrayList();
        }

        Preconditions.checkNotNull(container,
                "Required parameter container must be specified.");
        Preconditions.checkArgument(containsPosts(container),
                "The container contains no posts on this service.");
        Preconditions.checkArgument(container.hasId(),
                "The parameter container has no id.");
        Preconditions.checkArgument(container.hasParent(),
                "The parameter container has no required parent container.");

        Container parentContainer = container.getParent();
        Preconditions.checkArgument(parentContainer.hasId(),
                "The parent container has no id.");

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
            entryPages = defaultClient.courses()
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
                    if (0 > limit || limit < result.size()) {
                        Date createdDate = entry.getCreatedAt();
                        if (null == since || createdDate.after(since)) {
                            result.add(CanvasLmsSiocConverter.createSiocPost(
                                    getConnector(),
                                    entry,
                                    container));
                        } else {
                            // abort, because entries are sorted by
                            // 'newest first'
                            return result;
                        }
                    } else {
                        // limit reached
                        return result;
                    }
                }
            }
        }

        return result;
    }

    @Override
    public boolean containsReplies(final Post post) {
        return null != post
                && post.hasContainer()
                && containsPosts(post.getContainer());
    }

    @Override
    public List<Post> readNewReplies(final Date since, final long limit,
            final Post post)
            throws AuthenticationException, IOException {
        if (0 == limit) {
            return Lists.newArrayList();
        }

        Preconditions.checkNotNull(post,
                "Required parameter post must be specified.");
        Preconditions.checkArgument(containsReplies(post),
                "The post contains no replies on this service.");
        Preconditions.checkArgument(post.hasId(),
                "Required parameter post has no id.");
        Preconditions.checkArgument(post.hasContainer(),
                "The paramater post has no container.");

        Container container = post.getContainer();
        Preconditions.checkArgument(container.hasId(),
                "The container of the post has no id.");
        Preconditions.checkArgument(container.hasParent(),
                "The container of post has no parent");

        Container parentContainer = container.getParent();
        Preconditions.checkArgument(parentContainer.hasId(),
                "The parent of post container has no id.");

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
            entryId = Long.parseLong(post.getId());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "The id of the parentPost is invalid: was "
                            + container.getId());
        }

        Pagination<Entry> replyPages = null;
        try {
            replyPages = defaultClient.courses()
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
                    if (0 > limit || limit < result.size()) {
                        Date createdDate = entry.getCreatedAt();
                        if (null == since || createdDate.after(since)) {
                            result.add(CanvasLmsSiocConverter.createSiocPost(
                                    getConnector(),
                                    entry,
                                    container));
                        } else {
                            return result;
                        }
                    } else {
                        return result;
                    }
                }
            }
        }

        return result;
    }
}
