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

import org.mortbay.log.Log;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.util.Builder;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.Thread;
import org.rdfs.sioc.UserAccount;

import com.google.gdata.data.Link;
import com.google.gdata.data.Person;
import com.google.gdata.data.TextContent;
import com.google.gdata.data.youtube.CommentEntry;
import com.google.gdata.data.youtube.PlaylistLinkEntry;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.YouTubeMediaGroup;
import com.google.gdata.data.youtube.YouTubeNamespace;

import de.m0ep.socc.core.exceptions.NotFoundException;
import de.m0ep.socc.core.utils.DateUtils;
import de.m0ep.socc.core.utils.StringUtils;
import de.m0ep.socc.core.utils.UserAccountUtils;

public class YoutubeV2SiocConverter {
    public static final String PLAYLIST_URI_PATH = "/playlist/";
    public static final String VIDEO_URI_PATH = "/video/";
    public static final String VIDEO_ID_PREFIX = "video:";
    public static final String COMMENT_URI_PATH = "/comment/";
    public static final String COMMENT_ID_PREFIX = "comment:";

    public static Thread createSiocThread(YoutubeV2Connector connector,
            PlaylistLinkEntry playlistEntry, Forum parent) {
        Model model = connector.getContext().getModel();
        URI uri = Builder.createURI(
                connector.getService().getServiceEndpoint()
                        + PLAYLIST_URI_PATH
                        + playlistEntry.getPlaylistId());

        Thread result = new Thread(model, uri, true);
        result.setId(playlistEntry.getPlaylistId());
        result.setSeeAlso(Builder.createURI(playlistEntry.getSelfLink().getHref()));

        if (null != playlistEntry.getTitle()) {
            result.setName(playlistEntry.getTitle().getPlainText());
        }

        if (null != playlistEntry.getSummary()) {
            result.setDescription(playlistEntry.getSummary().getPlainText());
        }

        if (null != playlistEntry.getPublished()) {
            result.setCreated(DateUtils.formatISO8601(playlistEntry
                    .getPublished().getValue()));
        }
        if (null != playlistEntry.getUpdated()) {
            result.setModified(DateUtils.formatISO8601(playlistEntry
                    .getUpdated().getValue()));
        }

        result.setParent(parent);
        parent.addParentOf(result);
        parent.setNumThreads((parent.hasNumThreads()) ? (parent.getNumThreads() + 1) : (1));
        return result;
    }

    public static Post createSiocPost(YoutubeV2Connector connector, VideoEntry videoEntry,
            Container container) {
        Model model = connector.getContext().getModel();
        URI serviceEndpoint = connector.getService().getServiceEndpoint().asURI();
        YouTubeMediaGroup mediaGroup = videoEntry.getMediaGroup();

        URI uri = Builder.createURI(serviceEndpoint + VIDEO_URI_PATH + mediaGroup.getVideoId());

        Post result = new Post(model, uri, true);
        result.setId(VIDEO_ID_PREFIX + mediaGroup.getVideoId());

        for (Person author : videoEntry.getAuthors()) {
            String accountName = extractAuthorUsername(author);

            UserAccount creator = null;
            try {
                creator = UserAccountUtils.findUserAccount(
                        model,
                        accountName,
                        serviceEndpoint);
            } catch (NotFoundException e) {
                Log.debug(
                        "No user found with accountName='{}' and accountServiceHomepage='{}'," +
                                " create a new one.",
                        accountName,
                        serviceEndpoint);
                creator = createSiocUserAccount(connector, author);
            }

            result.addCreator(creator);
        }

        if (null != mediaGroup.getTitle()) {
            result.setTitle(mediaGroup.getTitle().getPlainTextContent());
        }

        if (null != videoEntry.getContent()
                && videoEntry.getContent() instanceof TextContent) {
            result.setContent(StringUtils.stripHTML(videoEntry.getPlainTextContent()));
        } else if (null != mediaGroup.getDescription()) {
            result.setContent(
                    StringUtils.stripHTML(
                            mediaGroup.getDescription().getPlainTextContent()));
        }

        // get the link to the video on Youtube
        Link videoLink = videoEntry.getLink("alternate", "text/html");
        if (null != videoLink) {
            result.addAttachment(Builder.createURI(videoLink.getHref()));
        }

        if (null != videoEntry.getPublished()) {
            result.setCreated(
                    DateUtils.formatISO8601(
                            videoEntry.getPublished().getValue()));
        }

        if (null != videoEntry.getUpdated()) {
            result.setModified(
                    DateUtils.formatISO8601(
                            videoEntry.getUpdated().getValue()));
        }

        result.setContainer(container);
        container.addContainerOf(result);
        container.setNumItems((container.hasNumItems()) ? (container
                .getNumItems() + 1) : (1));

        return result;
    }

    public static Post createSiocPost(YoutubeV2Connector connector, CommentEntry commentEntry,
            Post parentPost) {
        Model model = connector.getContext().getModel();
        URI serviceEndpoint = connector.getService().getServiceEndpoint().asURI();

        String commentIdRaw = commentEntry.getId();
        String commentId = commentIdRaw.substring(
                commentIdRaw.lastIndexOf(':') + 1);

        URI uri = Builder.createURI(serviceEndpoint + COMMENT_URI_PATH + commentId);

        Post result = new Post(model, uri, true);
        result.setId(COMMENT_ID_PREFIX + commentId);

        for (Person author : commentEntry.getAuthors()) {
            String accountName = extractAuthorUsername(author);

            UserAccount creator = null;
            try {
                creator = UserAccountUtils.findUserAccount(
                        model,
                        accountName,
                        serviceEndpoint);
            } catch (NotFoundException e) {
                Log.debug(
                        "No user found with accountName='{}' and accountServiceHomepage='{}'," +
                                " create a new one.",
                        accountName,
                        serviceEndpoint);
                creator = createSiocUserAccount(connector, author);
            }

            result.addCreator(creator);
        }

        if (null != commentEntry.getTitle()) {
            result.setTitle(commentEntry.getTitle().getPlainText());
        }

        if (null != commentEntry.getPlainTextContent()) {
            result.setContent(commentEntry.getPlainTextContent());
        }

        if (null != commentEntry.getPublished()) {
            result.setCreated(
                    DateUtils.formatISO8601(
                            commentEntry.getPublished().getValue()));
        }

        if (null != commentEntry.getUpdated()) {
            result.setModified(
                    DateUtils.formatISO8601(
                            commentEntry.getUpdated().getValue()));
        }

        Link replyToLink = commentEntry.getLink(YouTubeNamespace.IN_REPLY_TO, null);
        if (null != replyToLink) {
            String replyToLinkHref = replyToLink.getHref();
            String replyToId = replyToLinkHref
                    .substring(
                            replyToLinkHref.lastIndexOf('/') + 1,
                            replyToLinkHref.lastIndexOf('?'));

            URI replyToUri = Builder.createURI(serviceEndpoint + COMMENT_URI_PATH + replyToId);

            Post replyToPost = new Post(model, replyToUri, true);
            replyToPost.setId(COMMENT_ID_PREFIX + replyToId);

            result.setReplyOf(replyToPost);
            replyToPost.addReply(result);
            replyToPost.setNumReplies(
                    (replyToPost.hasNumReplies()) ?
                            (replyToPost.getNumReplies() + 1) :
                            (1));

            // replyToPost.setContainer(parentPost.getContainer());
            // parentPost.getContainer().addContainerOf(replyToPost);
        }
        else {
            result.setReplyOf(parentPost);
            parentPost.addReply(result);
            parentPost.setNumReplies(
                    (parentPost.hasNumReplies()) ? (parentPost.getNumReplies() + 1) : (1));
        }

        // result.setContainer(parentPost.getContainer());
        // parentPost.getContainer().addContainerOf(result);

        return result;
    }

    public static UserAccount createSiocUserAccount(YoutubeV2Connector connector, Person author) {
        Model model = connector.getContext().getModel();
        URI serviceEndpoint = connector.getService().getServiceEndpoint().asURI();
        String accountName = extractAuthorUsername(author);

        URI uri = Builder.createURI(serviceEndpoint + "/user/" + accountName);

        UserAccount result = new UserAccount(model, uri, true);
        result.setAccountName(accountName);
        result.setAccountServiceHomepage(serviceEndpoint);
        result.setSeeAlso(Builder.createURI(author.getUri()));

        com.xmlns.foaf.Person person = new com.xmlns.foaf.Person(model, true);
        person.setName(author.getName());

        person.setAccount(result);
        result.setAccountOf(person);

        return result;
    }

    public static String extractAuthorUsername(Person author) {
        String authorUri = author.getUri();
        String accountName = authorUri.substring(authorUri.lastIndexOf('/') + 1);
        return accountName;
    }
}
