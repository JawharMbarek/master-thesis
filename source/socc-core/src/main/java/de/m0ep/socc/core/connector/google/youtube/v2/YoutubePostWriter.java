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

package de.m0ep.socc.core.connector.google.youtube.v2;

import java.io.IOException;
import java.net.URL;

import org.rdfs.sioc.Container;
import org.rdfs.sioc.Item;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.UserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.damnhandy.uri.template.UriTemplate;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.gdata.data.Link;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.youtube.CommentEntry;
import com.google.gdata.data.youtube.YouTubeNamespace;
import com.google.gdata.util.ServiceException;
import com.xmlns.foaf.Person;

import de.m0ep.socc.core.connector.IConnector;
import de.m0ep.socc.core.connector.IConnector.IPostWriter;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.utils.PostWriterUtils;

public class YoutubePostWriter implements IPostWriter {
    private static final Logger LOG = LoggerFactory.getLogger(YoutubePostWriter.class);

    private YoutubeConnector connector;
    private String serviceEndpoint;

    public YoutubePostWriter(YoutubeConnector connector) {
        Preconditions.checkNotNull(connector,
                "Required parameter connector must be specified.");

        this.connector = connector;
        this.serviceEndpoint = connector.getService().getServiceEndpoint().toString();
    }

    @Override
    public IConnector getConnector() {
        return connector;
    }

    @Override
    public boolean canPostTo(Container container) {
        return false;
    }

    @Override
    public void writePost(Post post, Container container) throws AuthenticationException,
            IOException {
        throw new UnsupportedOperationException(
                "Can't write posts to containers with this service.");
    }

    @Override
    public boolean canReplyTo(Post post) {
        return post.toString().startsWith(serviceEndpoint) &&
                post.hasId() &&
                (post.getId().startsWith(YoutubeSiocConverter.VIDEO_ID_PREFIX) ||
                post.getId().startsWith(YoutubeSiocConverter.COMMENT_ID_PREFIX));
    }

    @Override
    public void writeReply(Post replyPost, Post parentPost) throws AuthenticationException,
            IOException {
        Preconditions.checkNotNull(replyPost,
                "Required parameter replyPost must be specified.");
        Preconditions.checkNotNull(parentPost,
                "Required parameter parentPost must be specified.");
        Preconditions.checkArgument(parentPost.hasId(),
                "The parameter parentPost has no id.");

        LOG.debug("Fetch creator UserAccount of replyPost");
        UserAccount creatorAccount = replyPost.getCreator();
        Person creatorPerson = PostWriterUtils.getPersonOfCreatorOrNull(connector, creatorAccount);

        LOG.debug("Fetch client of creator UserAccount or defaultClient");
        YoutubeClientWrapper client = null;
        if (null != creatorPerson) {
            UserAccount serviceAccount = PostWriterUtils.getServiceAccountOfPersonOrNull(
                    connector,
                    creatorPerson,
                    connector.getService().getServiceEndpoint().asURI());
            if (null != serviceAccount) {
                client = (YoutubeClientWrapper) PostWriterUtils.getClientOfServiceAccountOrNull(
                        connector,
                        serviceAccount);
            }
        }

        LOG.debug("Create content String");
        String content = replyPost.getContent();
        if (null == client) { // No client found, get default one an adapt
                              // message content
            client = (YoutubeClientWrapper) connector.getServiceClientManager().getDefaultClient();
            content = PostWriterUtils.createContentOfUnknownAccount(
                    replyPost,
                    creatorAccount,
                    creatorPerson);
        }

        String videoId = getParentVideoId(parentPost);
        String commentsUri = UriTemplate.fromTemplate(
                "http://gdata.youtube.com/feeds/api/videos/{videoId}/comments")
                .set("videoId", videoId)
                .expand(); // FIXME: magic strings

        LOG.debug("Create CommentEntry");
        CommentEntry insertEntry = new CommentEntry();
        insertEntry.setContent(new PlainTextConstruct(content));

        if (parentPost.getId().startsWith(YoutubeSiocConverter.COMMENT_ID_PREFIX)) {
            String commentId = parentPost.getId().substring(
                    parentPost.getId().lastIndexOf(YoutubeSiocConverter.ID_SEPERATOR) + 1);

            String replyToUri = UriTemplate.fromTemplate(
                    "http://gdata.youtube.com/feeds/api/videos/{videoId}/comments/{commentId}")
                    .set("videoId", videoId)
                    .set("commentId", commentId)
                    .expand(); // FIXME: magic strings

            insertEntry.getLinks().add(new Link(YouTubeNamespace.IN_REPLY_TO,
                    "application/atom+xml",
                    replyToUri));
        }

        CommentEntry resultEntry = null;
        try {
            resultEntry = client.getService()
                    .insert(
                            new URL(commentsUri),
                            insertEntry);
            LOG.debug("Write post {} to {}", replyPost, commentsUri);
        } catch (com.google.gdata.util.AuthenticationException e) {
            throw new AuthenticationException(e);
        } catch (ServiceException e) {
            Throwables.propagate(e);
        }

        if (null != resultEntry) {
            LOG.debug("Convert result CommentEntry to SIOC Post");
            Post resultPost = YoutubeSiocConverter.createSiocPost(
                    connector,
                    resultEntry,
                    parentPost);

            resultPost.addSibling(parentPost);
            parentPost.addSibling(resultPost);
        }

        try {
            Thread.currentThread().sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String getParentVideoId(Post post) {
        Item replyOf = post;
        do {
            if (replyOf.getId().startsWith(YoutubeSiocConverter.VIDEO_ID_PREFIX)) {
                return replyOf.getId().substring(
                        replyOf.getId().lastIndexOf(YoutubeSiocConverter.ID_SEPERATOR) + 1);
            }

            replyOf = (post.hasReplyOf()) ? (post.getReplyOf()) : (null);
        } while (null != replyOf);

        throw new IllegalArgumentException("Couldn't found the id of the parent video.");
    }
}
