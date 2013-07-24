
package de.m0ep.socc.core.connector.moodle;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.rdfs.sioc.Container;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.Thread;
import org.rdfs.sioc.UserAccount;

import com.google.common.base.Preconditions;
import com.xmlns.foaf.Person;

import de.m0ep.moodlews.soap.ForumPostDatum;
import de.m0ep.moodlews.soap.ForumPostRecord;
import de.m0ep.socc.core.connector.IConnector;
import de.m0ep.socc.core.connector.IConnector.IPostWriter;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.utils.PostWriterUtils;
import de.m0ep.socc.core.utils.RdfUtils;

public class Moodle2PostWriter implements IPostWriter {

    private Moodle2Connector connector;
    private String serviceEndpoint;

    private Map<Integer, Integer> firstPostIdMap = new HashMap<Integer, Integer>();

    public Moodle2PostWriter(Moodle2Connector moodle2Connector) {
        this.connector = Preconditions.checkNotNull(moodle2Connector,
                "Required parameter moodle2Connector must be specified.");

        this.serviceEndpoint = connector.getService().getServiceEndpoint().toString();
    }

    @Override
    public IConnector getConnector() {
        return connector;
    }

    @Override
    public boolean canPostTo(Container container) {
        return null != container &&
                RdfUtils.isType(container.getModel(), container, Thread.RDFS_CLASS) &&
                container.toString().startsWith(serviceEndpoint) &&
                container.hasParent() &&
                container.getParent().toString().startsWith(serviceEndpoint);
    }

    @Override
    public void writePost(Post post, Container container) throws AuthenticationException,
            IOException {
        Preconditions.checkNotNull(post,
                "Required parameter post must be specified.");
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
        Person creatorPerson = PostWriterUtils.getPersonOfCreatorOrNull(connector, creatorAccount);

        Moodle2ClientWrapper client = null;
        if (null != creatorPerson) {
            UserAccount serviceAccount = PostWriterUtils.getServiceAccountOfPersonOrNull(
                    connector,
                    creatorPerson,
                    connector.getService().getServiceEndpoint().asURI());
            if (null != serviceAccount) {
                client = (Moodle2ClientWrapper) PostWriterUtils.getClientOfServiceAccountOrNull(
                        connector,
                        serviceAccount);
            }
        }

        String content = post.getContent();
        if (null == client) { // No client found, get default one an adapt
                              // message content
            client = (Moodle2ClientWrapper) connector.getServiceClientManager().getDefaultClient();
            content = PostWriterUtils.createContentOfUnknownAccount(
                    post,
                    creatorAccount,
                    creatorPerson);
        }

        final Moodle2ClientWrapper callingClient = client;

        final int firstPostId;
        if (firstPostIdMap.containsKey(discussionId)) {
            firstPostId = firstPostIdMap.get(discussionId);
        } else {
            // Need the id of the first entry to write the post as reply to it.

            ForumPostRecord[] firstPostRecordArray = callingClient
                    .callMethod(new Callable<ForumPostRecord[]>() {
                        @Override
                        public ForumPostRecord[] call() throws Exception {
                            return callingClient.getBindingStub().get_forum_posts(
                                    callingClient.getAuthClient(),
                                    callingClient.getSessionKey(),
                                    discussionId,
                                    1);
                        }
                    });

            if (null != firstPostRecordArray && 0 < firstPostRecordArray.length) {
                firstPostId = firstPostRecordArray[0].getId();
                firstPostIdMap.put(discussionId, firstPostId);
            } else {
                firstPostId = -1;
            }
        }

        if (-1 != firstPostId) {
            // create Moodle post data
            final ForumPostDatum postDatum = new ForumPostDatum(client.getBindingStub()
                    .getNAMESPACE());
            postDatum.setMessage(content);
            postDatum.setMessage(post.getContent());
            if (post.hasTitle()) {
                postDatum.setSubject(post.getTitle());
            } else if (post.hasSubject()) {
                postDatum.setSubject(post.getSubject());
            } else {
                postDatum.setSubject("");
            }

            // add post to Moodle
            ForumPostRecord[] postRecordArray = callingClient
                    .callMethod(new Callable<ForumPostRecord[]>() {
                        @Override
                        public ForumPostRecord[] call() throws Exception {
                            return callingClient.getBindingStub().forum_add_reply(
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
                        connector,
                        postRecord,
                        container);

                addedPost.addSibling(post);
            }
        }

    }

    @Override
    public boolean canReplyTo(Post parentPost) {
        return null != parentPost && parentPost.toString().startsWith(serviceEndpoint);
    }

    @Override
    public void writeReply(Post replyPost, Post parentPost) throws AuthenticationException,
            IOException {
        Preconditions.checkNotNull(replyPost,
                "Required parameter replyPost must be specified.");
        Preconditions.checkNotNull(parentPost,
                "Required parameter parentPost must be specified.");
        Preconditions.checkArgument(parentPost.hasId(),
                "The parentPost has no id.");
        Preconditions.checkArgument(canReplyTo(parentPost),
                "Can't write replies to the parentPost with this connector.");

        final int postId;
        try {
            postId = Integer.parseInt(parentPost.getId());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "The id of the parentPost is invalid: was " + parentPost.getId());
        }

        UserAccount creatorAccount = replyPost.getCreator();
        Person creatorPerson = PostWriterUtils.getPersonOfCreatorOrNull(connector, creatorAccount);

        Moodle2ClientWrapper client = null;
        if (null != creatorPerson) {
            UserAccount serviceAccount = PostWriterUtils.getServiceAccountOfPersonOrNull(
                    connector,
                    creatorPerson,
                    connector.getService().getServiceEndpoint().asURI());
            if (null != serviceAccount) {
                client = (Moodle2ClientWrapper) PostWriterUtils.getClientOfServiceAccountOrNull(
                        connector,
                        serviceAccount);
            }
        }

        String content = replyPost.getContent();
        if (null == client) { // No client found, get default one an adapt
                              // message content
            client = (Moodle2ClientWrapper) connector.getServiceClientManager().getDefaultClient();
            content = PostWriterUtils.createContentOfUnknownAccount(
                    replyPost,
                    creatorAccount,
                    creatorPerson);
        }

        final Moodle2ClientWrapper finalClient = client;
        final ForumPostDatum replyDatum = new ForumPostDatum(client.getBindingStub().getNAMESPACE());
        replyDatum.setMessage(content);
        if (replyPost.hasSubject()) {
            replyDatum.setSubject(replyPost.getSubject());
        } else if (replyPost.hasTitle()) {
            replyDatum.setSubject(replyPost.getTitle());
        }

        ForumPostRecord[] resultPostRecords = client.callMethod(new Callable<ForumPostRecord[]>() {
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
            ForumPostRecord parentPostRecord = findPost(resultPostRecords, postId);

            if (null != parentPostRecord) {
                int numChildren = parentPostRecord.getChildren().length;
                ForumPostRecord postRecord = parentPostRecord.getChildren()[numChildren - 1];
                Post addedPost = Moodle2SiocConverter.createSiocPost(
                        connector,
                        postRecord,
                        parentPost.getContainer());

                addedPost.addSibling(replyPost);
            }
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
