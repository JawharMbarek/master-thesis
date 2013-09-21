/*
 * The MIT License (MIT) Copyright © 2013 "Florian Mueller"
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

package de.m0ep.socc.core.connector.moodle;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.Syntax;
import org.ontoware.rdf2go.model.node.Resource;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.util.RDFTool;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.Thing;
import org.rdfs.sioc.UserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.repackaged.com.google.common.base.Strings;
import com.google.api.client.repackaged.com.google.common.base.Throwables;

import de.m0ep.moodlews.soap.ForumPostDatum;
import de.m0ep.moodlews.soap.ForumPostRecord;
import de.m0ep.socc.core.connector.DefaultConnectorIOComponent;
import de.m0ep.socc.core.connector.IConnector.IPostWriter;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.exceptions.NotFoundException;
import de.m0ep.socc.core.utils.SiocUtils;
import de.m0ep.socc.core.utils.SoccUtils;
import de.m0ep.socc.core.utils.UserAccountUtils;

public class Moodle2PostWriter extends
        DefaultConnectorIOComponent<Moodle2Connector> implements
        IPostWriter<Moodle2Connector> {

    private static final Logger LOG = LoggerFactory
            .getLogger(Moodle2PostWriter.class);
    private final Map<Integer, Post> firstPostIdMap = new HashMap<Integer, Post>();

    public Moodle2PostWriter(Moodle2Connector connector) {
        super(connector);
    }

    @Override
    public void writePost(URI targetUri, String rdfString, Syntax syntax)
            throws NotFoundException,
            AuthenticationException,
            IOException {
        Model tmpModel = RDFTool.stringToModel(rdfString, syntax);

        boolean isForumDiscussionUri = Moodle2SiocUtils.isForumDiscussionUri(
                targetUri,
                getServiceEndpoint());

        boolean isForumPostUri = Moodle2SiocUtils.isForumPostUri(
                targetUri,
                getServiceEndpoint());

        Thing targetResource = null;
        if (isForumDiscussionUri) {
            targetResource = getConnector()
                    .getStructureReader()
                    .getContainer(targetUri);
        } else if (isForumPostUri) {
            targetResource = getConnector()
                    .getPostReader()
                    .getPost(targetUri);
        } else {
            throw new NotFoundException(
                    "No suitable Moodle target found at uri "
                            + targetUri
                            + " to write posts to it.");
        }

        ClosableIterator<Resource> postIter = Post.getAllInstances(tmpModel);
        try {
            while (postIter.hasNext()) {
                Resource resource = postIter.next();
                Post post = Post.getInstance(tmpModel, resource);

                // skip all posts that are already forwarded from this site
                if (SoccUtils.hasContentWatermark(
                        getConnector().getStructureReader().getSite(),
                        post.getContent())) {
                    continue;
                }

                if (isForumDiscussionUri) {
                    writePostToContainer((Container) targetResource, post);
                } else if (isForumPostUri) {
                    writeReplyToPost((Post) targetResource, post);
                }
            }
        } finally {
            postIter.close();
            tmpModel.close();
        }
    }

    private void writePostToContainer(Container targetContainer, Post post)
            throws AuthenticationException,
            IOException {
        final int discussionId;
        try {
            discussionId = Integer.parseInt(targetContainer.getId());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "The id of the container is invalid: was "
                            + targetContainer.getId());
        }

        UserAccount creatorAccount = UserAccount.getInstance(
                getModel(),
                post.getCreator().getResource());
        String content = post.getContent();
        Moodle2ClientWrapper client = null;
        if (null != creatorAccount) {
            try {
                UserAccount serviceAccount = UserAccountUtils
                        .findUserAccountOfService(
                                getModel(),
                                creatorAccount,
                                getConnector().getService());

                client = getConnector().getClientManager().get(serviceAccount);
            } catch (Exception e) {
                LOG.debug(
                        "No client found for UserAccount {}: exception -> {}\n{}",
                        creatorAccount,
                        e.getMessage(),
                        Throwables.getStackTraceAsString(e));
                client = null;
            }
        }

        if (null == client) {
            LOG.debug("Using default client");
            client = getConnector().getClientManager().getDefaultClient();
            content = SoccUtils.formatUnknownMessage(
                    getConnector(),
                    post);
        }

        if (!SoccUtils.hasAnyContentWatermark(content)) {
            // add watermark for 'already forwarded' check
            content = SoccUtils.addContentWatermark(post.getIsPartOf(),
                    content, "<br>");
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
                firstPost = Moodle2SiocUtils.createSiocPost(
                        getConnector(),
                        firstPostRecordArray[0],
                        SiocUtils.asThread(targetContainer),
                        null);

                LOG.debug("Loaded first post {} of discussion {}", firstPost
                        .getId(), discussionId);
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
            ForumPostRecord[] resultPostRecords = callingClient
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

            if (null != resultPostRecords && 0 < resultPostRecords.length) {
                int numChildren = resultPostRecords[0].getChildren().length;
                ForumPostRecord postRecord = resultPostRecords[0].getChildren()[numChildren - 1];
                Post addedPost = Moodle2SiocUtils.createSiocPost(
                        getConnector(),
                        postRecord,
                        SiocUtils.asThread(targetContainer),
                        firstPost);

                addedPost.addSibling(post);
            } else {
                LOG.warn("Failed to write post(s) to uri " + targetContainer);
            }
        }
    }

    private void writeReplyToPost(Post targetPost, Post post)
            throws AuthenticationException,
            IOException {
        final int postId;
        try {
            postId = Integer.parseInt(targetPost.getId());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "The id of the parentPost is invalid: was "
                            + targetPost.getId());
        }

        UserAccount creatorAccount = UserAccount.getInstance(
                getModel(),
                post.getCreator().getResource());
        String content = post.getContent();
        Moodle2ClientWrapper client = null;
        if (null != creatorAccount) {
            try {
                UserAccount serviceAccount = UserAccountUtils
                        .findUserAccountOfService(
                                getModel(),
                                creatorAccount,
                                getConnector().getService());

                client = getConnector().getClientManager().get(serviceAccount);
            } catch (Exception e) {
                LOG.debug(
                        "No client found for UserAccount {}: exception -> {}\n{}",
                        creatorAccount,
                        e.getMessage(),
                        Throwables.getStackTraceAsString(e));
                client = null;
            }
        }

        if (null == client) {
            LOG.debug("Using default client");
            client = getConnector().getClientManager().getDefaultClient();
            content = SoccUtils.formatUnknownMessage(
                    getConnector(),
                    post);
        }

        if (!SoccUtils.hasAnyContentWatermark(content)) {
            // add watermark for 'already forwarded' check
            content = SoccUtils
                    .addContentWatermark(post.getIsPartOf(), content);
        }

        final Moodle2ClientWrapper finalClient = client;
        final ForumPostDatum replyDatum = new ForumPostDatum(client
                .getBindingStub().getNAMESPACE());
        replyDatum.setMessage(content);
        replyDatum.setSubject(Strings.nullToEmpty(post.getTitle()));

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

                Container container = targetPost.getContainer();
                Post addedPost = Moodle2SiocUtils.createSiocPost(
                        getConnector(),
                        postRecord,
                        SiocUtils.asThread(container),
                        targetPost);

                addedPost.addSibling(post);
            }
        } else {
            LOG.warn("Failed to write post(s) to uri " + targetPost);
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
