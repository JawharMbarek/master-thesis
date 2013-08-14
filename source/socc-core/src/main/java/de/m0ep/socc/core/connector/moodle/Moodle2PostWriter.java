
package de.m0ep.socc.core.connector.moodle;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.rdfs.sioc.Container;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.Thread;
import org.rdfs.sioc.UserAccount;

import com.google.api.client.repackaged.com.google.common.base.Strings;
import com.google.api.client.repackaged.com.google.common.base.Throwables;
import com.google.common.base.Preconditions;
import com.xmlns.foaf.Person;

import de.m0ep.moodlews.soap.ForumPostDatum;
import de.m0ep.moodlews.soap.ForumPostRecord;
import de.m0ep.socc.core.connector.AbstractConnectorIOComponent;
import de.m0ep.socc.core.connector.IConnector.IPostWriter;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.utils.PostWriterUtils;
import de.m0ep.socc.core.utils.RdfUtils;
import de.m0ep.socc.core.utils.SiocUtils;

public class Moodle2PostWriter extends
        AbstractConnectorIOComponent<Moodle2Connector> implements IPostWriter {

    private final Map<Integer, Post> firstPostIdMap = new HashMap<Integer, Post>();

    public Moodle2PostWriter(Moodle2Connector connector) {
        super(connector);
    }

    @Override
    public boolean canPostTo(Container container) {
        return null != container
                && RdfUtils.isType(
                        container,
                        Thread.RDFS_CLASS)
                && SiocUtils.isContainerOfSite(
                        container,
                        getServiceEndpoint());
    }

    @Override
    public void writePost(Post post, Container container)
            throws AuthenticationException,
            IOException {
        Preconditions.checkNotNull(post,
                "Required parameter post must be specified.");
        Preconditions.checkArgument(post.hasContent(),
                "The parameter post has no content");
        Preconditions.checkArgument(post.hasCreator(),
                "The paramter post has no creator.");

        Preconditions.checkNotNull(container,
                "Required parameter container must be specified.");
        Preconditions.checkArgument(canPostTo(container),
                "Can't write the post to this container");
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

        UserAccount creatorAccount = post.getCreator();
        Person creatorPerson = PostWriterUtils.getPersonOfCreatorOrNull(
                getConnector(), creatorAccount);

        Moodle2ClientWrapper client = null;
        if (null != creatorPerson) {
            UserAccount serviceAccount = PostWriterUtils
                    .getServiceAccountOfPersonOrNull(
                            getConnector(),
                            creatorPerson,
                            getServiceEndpoint());
            if (null != serviceAccount) {
                client = (Moodle2ClientWrapper) PostWriterUtils
                        .getClientOfServiceAccountOrNull(
                                getConnector(),
                                serviceAccount);
            }
        }

        String content = post.getContent();
        if (null == client) { // No client found, get default one an adapt
                              // message content
            client = getConnector().getServiceClientManager()
                    .getDefaultClient();
            content = PostWriterUtils.createContentOfUnknownAccount(
                    post,
                    creatorAccount,
                    creatorPerson);
        }

        final Moodle2ClientWrapper callingClient = client;

        Post firstPost = null;
        if (firstPostIdMap.containsKey(discussionId)) {
            firstPost = firstPostIdMap.get(discussionId);
        } else {
            // Need the id of the first entry to write the post as reply to it.

            ForumPostRecord[] firstPostRecordArray = callingClient
                    .callMethod(new Callable<ForumPostRecord[]>() {
                        @Override
                        public ForumPostRecord[] call() throws Exception {
                            return callingClient.getBindingStub()
                                    .get_forum_posts(
                                            callingClient.getAuthClient(),
                                            callingClient.getSessionKey(),
                                            discussionId,
                                            1);
                        }
                    });

            if (null != firstPostRecordArray && 0 < firstPostRecordArray.length) {
                firstPost = Moodle2SiocConverter.createSiocPost(
                        getConnector(),
                        firstPostRecordArray[0],
                        SiocUtils.asThread(container),
                        null);

                firstPostIdMap.put(discussionId, firstPost);
            }
        }

        if (null != firstPost) {
            final int firstPostId;
            try {
                firstPostId = Integer.parseInt(firstPost.getId());
            } catch (NumberFormatException e) {
                throw Throwables.propagate(e); // shouldn't happened
            }

            // create Moodle post data
            final ForumPostDatum postDatum = new ForumPostDatum(client
                    .getBindingStub()
                    .getNAMESPACE());

            postDatum.setMessage(content);
            postDatum.setSubject(Strings.nullToEmpty(post.getTitle()));

            // add post to Moodle
            ForumPostRecord[] postRecordArray = callingClient
                    .callMethod(new Callable<ForumPostRecord[]>() {
                        @Override
                        public ForumPostRecord[] call() throws Exception {
                            return callingClient.getBindingStub()
                                    .forum_add_reply(
                                            callingClient.getAuthClient(),
                                            callingClient.getSessionKey(),
                                            firstPostId,
                                            postDatum);
                        }
                    });

            if (null != postRecordArray && 0 < postRecordArray.length) {
                int numChildren = postRecordArray[0].getChildren().length;
                ForumPostRecord postRecord = postRecordArray[0].getChildren()[numChildren - 1];
                Post addedPost = Moodle2SiocConverter.createSiocPost(
                        getConnector(),
                        postRecord,
                        SiocUtils.asThread(container),
                        firstPost);

                addedPost.addSibling(post);
                post.addSibling(addedPost);
            }
        }
    }

    @Override
    public boolean canReplyTo(Post post) {
        return null != post
                && post.hasContainer()
                && SiocUtils.isContainerOfSite(
                        post.getContainer(),
                        getServiceEndpoint());
    }

    @Override
    public void writeReply(Post replyPost, Post parentPost)
            throws AuthenticationException,
            IOException {
        Preconditions.checkNotNull(replyPost,
                "Required parameter replyPost must be specified.");
        Preconditions.checkNotNull(parentPost,
                "Required parameter parentPost must be specified.");
        Preconditions.checkArgument(canReplyTo(parentPost),
                "Can't write replies to the parentPost with this connector.");
        Preconditions.checkArgument(parentPost.hasId(),
                "The parameter parentPost has no id.");

        final int postId;
        try {
            postId = Integer.parseInt(parentPost.getId());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "The id of the parentPost is invalid: was "
                            + parentPost.getId());
        }

        UserAccount creatorAccount = replyPost.getCreator();
        Person creatorPerson = PostWriterUtils.getPersonOfCreatorOrNull(
                getConnector(), creatorAccount);

        Moodle2ClientWrapper client = null;
        if (null != creatorPerson) {
            UserAccount serviceAccount = PostWriterUtils
                    .getServiceAccountOfPersonOrNull(
                            getConnector(),
                            creatorPerson,
                            getServiceEndpoint());
            if (null != serviceAccount) {
                client = (Moodle2ClientWrapper) PostWriterUtils
                        .getClientOfServiceAccountOrNull(
                                getConnector(),
                                serviceAccount);
            }
        }

        String content = replyPost.getContent();
        if (null == client) { // No client found, get default one an adapt
                              // message content
            client = getConnector().getServiceClientManager()
                    .getDefaultClient();
            content = PostWriterUtils.createContentOfUnknownAccount(
                    replyPost,
                    creatorAccount,
                    creatorPerson);
        }

        final Moodle2ClientWrapper finalClient = client;
        final ForumPostDatum replyDatum = new ForumPostDatum(client
                .getBindingStub().getNAMESPACE());
        replyDatum.setMessage(content);
        replyDatum.setSubject(Strings.nullToEmpty(replyPost.getTitle()));

        ForumPostRecord[] resultPostRecords = client
                .callMethod(new Callable<ForumPostRecord[]>() {
                    @Override
                    public ForumPostRecord[] call() throws Exception {
                        return finalClient.getBindingStub().forum_add_reply(
                                finalClient.getAuthClient(),
                                finalClient.getSessionKey(),
                                postId,
                                replyDatum);
                    }
                });

        if (null != resultPostRecords && 0 < resultPostRecords.length) {
            ForumPostRecord parentPostRecord = findPostRecordWithId(
                    resultPostRecords,
                    postId);

            if (null != parentPostRecord) {
                int numChildren = parentPostRecord.getChildren().length;
                ForumPostRecord postRecord = parentPostRecord.getChildren()[numChildren - 1];

                Container container = parentPost.getContainer();
                Post addedPost = Moodle2SiocConverter.createSiocPost(
                        getConnector(),
                        postRecord,
                        SiocUtils.asThread(container),
                        parentPost);

                addedPost.addSibling(replyPost);
                replyPost.addSibling(addedPost);
            }
        }
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
