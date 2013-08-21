
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
import de.m0ep.socc.core.connector.AbstractConnectorIOComponent;
import de.m0ep.socc.core.connector.IConnector.IPostReader;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.utils.RdfUtils;
import de.m0ep.socc.core.utils.SiocUtils;

public class Moodle2PostReader extends
        AbstractConnectorIOComponent<Moodle2Connector> implements IPostReader {
    private static final Logger LOG = LoggerFactory
            .getLogger(Moodle2PostReader.class);

    private final Moodle2ClientWrapper defaultClient;

    public Moodle2PostReader(Moodle2Connector connector) {
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
                "The container contains no post at this connector.");
        Preconditions.checkArgument(container.hasId(),
                "The container has no id.");

        final int discussionId;
        try {
            discussionId = Integer.parseInt(container.getId());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "The id of the container is invalid: was "
                            + container.getId());
        }

        ForumPostRecord[] postRecordArray = defaultClient
                .callMethod(new Callable<ForumPostRecord[]>() {
                    @Override
                    public ForumPostRecord[] call() throws Exception {
                        return defaultClient
                                .getBindingStub()
                                .get_forum_posts(
                                        defaultClient.getAuthClient(),
                                        defaultClient.getSessionKey(),
                                        discussionId,
                                        (int) limit);
                    }
                });

        List<Post> result = Lists.newArrayList();
        if (null != postRecordArray && 0 < postRecordArray.length) {
            result.addAll(
                    extractPosts(
                            since,
                            limit,
                            SiocUtils.asThread(container),
                            null,
                            postRecordArray));
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
        Preconditions.checkNotNull(post,
                "Required parameter post must be specified.");
        Preconditions.checkArgument(containsReplies(post),
                "The parameter post has no replies at this Moodle instance.");
        Preconditions.checkArgument(post.hasId(),
                "The paramater post has no id.");
        Preconditions.checkArgument(post.hasContainer(),
                "The paramater post has no container.");

        Container container = post.getContainer();
        Preconditions.checkArgument(container.hasId(),
                "The container of the post has no id");

        final int discussionId;
        try {
            discussionId = Integer.parseInt(container.getId());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "The id of the container is invalid: was "
                            + container.getId());
        }

        final int postId;
        try {
            postId = Integer.parseInt(post.getId());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "The id of the parentPost is invalid: was "
                            + post.getId());
        }

        ForumPostRecord[] postRecordArray = defaultClient.callMethod(
                new Callable<ForumPostRecord[]>() {
                    @Override
                    public ForumPostRecord[] call() throws Exception {
                        return defaultClient.getBindingStub()
                                .get_forum_posts(
                                        defaultClient.getAuthClient(),
                                        defaultClient.getSessionKey(),
                                        discussionId,
                                        (int) limit);
                    }
                });

        List<Post> result = Lists.newArrayList();
        if (null != postRecordArray && 0 < postRecordArray.length) {
            ForumPostRecord postRecord = findPostRecordWithId(postRecordArray,
                    postId);

            if (null != postRecord) {
                ForumPostRecord[] children = postRecord.getChildren();
                if (null != children && 0 < children.length) {
                    result.addAll(
                            extractPosts(
                                    since,
                                    limit,
                                    SiocUtils.asThread(container),
                                    post,
                                    children));
                }
            } else {
                LOG.warn(
                        "No post found in thread {} with id {} to read replies from.",
                        discussionId, postId);
            }
        }

        return result;
    }

    private List<Post> extractPosts(final Date since, final long limit,
            final Thread container,
            final Post parentPost, final ForumPostRecord[] postRecordArray)
            throws AuthenticationException, IOException {

        List<Post> results = Lists.newArrayList();

        for (ForumPostRecord postRecord : postRecordArray) {
            if (0 > limit || limit < results.size()) {
                Post post = Moodle2SiocConverter.createSiocPost(
                        getConnector(),
                        postRecord,
                        container,
                        parentPost);
                results.add(post);

                ForumPostRecord[] children = postRecord.getChildren();
                if (null != children && 0 < children.length) {
                    results.addAll(extractPosts(
                            since,
                            (0 > limit)
                                    ? -1
                                    : Math.max(limit - results.size(),
                                            0),
                            container,
                            post,
                            postRecord.getChildren()));
                }
            }
        }

        return results;
    }

    private ForumPostRecord findPostRecordWithId(
            ForumPostRecord[] postRecordArray, int postId) {
        for (ForumPostRecord postRecord : postRecordArray) {
            if (postId == postRecord.getId()) {
                return postRecord;
            }

            ForumPostRecord[] children = postRecord.getChildren();
            if (null != children && 0 < children.length) {
                ForumPostRecord result = findPostRecordWithId(children, postId);

                if (null != result) {
                    return result;
                }
            }
        }

        return null;
    }
}
