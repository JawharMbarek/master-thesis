
package de.m0ep.socc.core.connector.moodle;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import org.rdfs.sioc.Container;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.Thread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import de.m0ep.moodlews.soap.ForumPostRecord;
import de.m0ep.socc.core.connector.IConnector;
import de.m0ep.socc.core.connector.IConnector.IPostReader;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.utils.RdfUtils;

public class Moodle2PostReader implements IPostReader {
    private static final Logger LOG = LoggerFactory.getLogger(Moodle2PostReader.class);

    private Moodle2Connector connector;
    private Moodle2ClientWrapper client;
    private String serviceEndpoint;

    public Moodle2PostReader(Moodle2Connector moodle2Connector) {
        this.connector = Preconditions.checkNotNull(moodle2Connector,
                "Required parameter moodle2Connector must be specified.");

        this.client = (Moodle2ClientWrapper) connector.getServiceClientManager().getDefaultClient();
        this.serviceEndpoint = this.connector.getService().getServiceEndpoint().toString();
    }

    @Override
    public IConnector getConnector() {
        return connector;
    }

    @Override
    public boolean containsPosts(final Container container) {
        return null != container &&
                RdfUtils.isType(container.getModel(), container, Thread.RDFS_CLASS) &&
                container.toString().startsWith(serviceEndpoint);
    }

    @Override
    public List<Post> readNewPosts(final Date lastPostDate, final long limit,
            final Container container)
            throws AuthenticationException, IOException {
        if (0 == limit) {
            return Lists.newArrayList();
        }

        Preconditions.checkNotNull(container,
                "Required parameter container must be specified.");
        Preconditions.checkArgument(containsPosts(container),
                "The container contains no post at this connector.");
        Preconditions.checkArgument(container.hasId(), "The container has no id.");

        final int discussionId;
        try {
            discussionId = Integer.parseInt(container.getId());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "The id of the container is invalid: was " + container.getId());
        }

        ForumPostRecord[] postRecordArray = client.callMethod(new Callable<ForumPostRecord[]>() {
            @Override
            public ForumPostRecord[] call() throws Exception {
                return client.getBindingStub().get_forum_posts(
                        client.getAuthClient(),
                        client.getSessionKey(),
                        discussionId,
                        (int) limit);
            }
        });

        List<Post> result = Lists.newArrayList();
        if (null != postRecordArray && 0 < postRecordArray.length) {
            addPosts(result, postRecordArray, lastPostDate, limit, container);
        }

        return result;
    }

    @Override
    public boolean containsReplies(final Post post) {
        return null != post &&
                post.toString().startsWith(serviceEndpoint) &&
                post.hasContainer() &&
                containsPosts(post.getContainer());
    }

    @Override
    public List<Post> readNewReplies(final Date lastReplyDate, final long limit,
            final Post parentPost)
            throws AuthenticationException, IOException {
        Preconditions.checkNotNull(parentPost,
                "Required parameter parentPost must be specified.");
        Preconditions.checkArgument(containsReplies(parentPost),
                "The parameter parentPost contains no replies at this connector.");
        Preconditions.checkArgument(parentPost.hasId(),
                "The parentPost has no id.");

        Container container = parentPost.getContainer();
        Preconditions.checkArgument(container.hasId(),
                "The container of the parentPost has no id");

        final int discussionId;
        try {
            discussionId = Integer.parseInt(container.getId());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "The id of the container is invalid: was " + container.getId());
        }

        final int postId;
        try {
            postId = Integer.parseInt(parentPost.getId());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "The id of the parentPost is invalid: was " + parentPost.getId());
        }

        ForumPostRecord[] postRecordArray = client.callMethod(new Callable<ForumPostRecord[]>() {
            @Override
            public ForumPostRecord[] call() throws Exception {
                return client.getBindingStub().get_forum_posts(
                        client.getAuthClient(),
                        client.getSessionKey(),
                        discussionId,
                        (int) limit);
            }
        });

        List<Post> result = Lists.newArrayList();
        if (null != postRecordArray && 0 < postRecordArray.length) {
            ForumPostRecord postRecord = findPost(postRecordArray, postId);

            if (null != postRecord) {
                ForumPostRecord[] children = postRecord.getChildren();
                if (null != children && 0 < children.length) {
                    addPosts(result, children, lastReplyDate, limit, container);
                }
            } else {
                LOG.warn("No post found in thread {} with id {} to read replies from.",
                        discussionId, postId);
            }
        }
        return result;
    }

    private void addPosts(List<Post> result, final ForumPostRecord[] postRecordArray,
            final Date lastPostDate, final long limit, final Container container) {

        for (ForumPostRecord postRecord : postRecordArray) {
            addPost(result, postRecord, lastPostDate, limit, container);

            ForumPostRecord[] children = postRecord.getChildren();
            if (null != children && 0 < children.length) {
                addPosts(result, postRecord.getChildren(), lastPostDate, limit, container);
            }
        }
    }

    private void addPost(List<Post> result, final ForumPostRecord postRecord,
            final Date lastPostDate, final long limit, final Container container) {
        if (0 < limit && limit <= result.size()) {
            return;
        }

        Date createdDate = new Date(postRecord.getCreated() * 1000L);

        if (null == lastPostDate || createdDate.after(lastPostDate)) {
            result.add(Moodle2SiocConverter.createSiocPost(connector, postRecord, container));
        }
    }

    private ForumPostRecord findPost(ForumPostRecord[] postRecordArray, int postId) {
        for (ForumPostRecord postRecord : postRecordArray) {
            if (postId == postRecord.getId()) {
                return postRecord;
            }

            ForumPostRecord[] children = postRecord.getChildren();
            if (null != children && 0 < children.length) {
                ForumPostRecord result = findPost(children, postId);

                if (null != result) {
                    return result;
                }
            }
        }

        return null;
    }
}
