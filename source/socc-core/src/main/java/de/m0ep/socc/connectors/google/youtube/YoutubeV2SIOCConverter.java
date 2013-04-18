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

package de.m0ep.socc.connectors.google.youtube;

import java.text.ParseException;

import org.ontoware.rdf2go.model.node.URI;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.Thread;
import org.rdfs.sioc.UserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.Link;
import com.google.gdata.data.Person;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.TextContent;
import com.google.gdata.data.youtube.CommentEntry;
import com.google.gdata.data.youtube.PlaylistLinkEntry;
import com.google.gdata.data.youtube.UserProfileEntry;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.YouTubeMediaGroup;
import com.google.gdata.data.youtube.YouTubeNamespace;

import de.m0ep.socc.exceptions.ConnectorException;
import de.m0ep.socc.utils.DateUtils;
import de.m0ep.socc.utils.RDF2GoUtils;
import de.m0ep.socc.utils.SIOCUtils;
import de.m0ep.socc.utils.StringUtils;

public class YoutubeV2SIOCConverter {
    private static final Logger LOG = LoggerFactory
	    .getLogger(YoutubeV2SIOCConverter.class);

    public static UserAccount createUserAccount(
	    final YoutubeV2Connector connector,
	    final UserProfileEntry userProfileEntry, final URI uri) {
	UserAccount result = new UserAccount(connector.getModel(), uri, true);

	result.setId(connector.parseYoutubeEntryID(userProfileEntry.getId()));
	result.setIsPartOf(connector.getSite());
	result.setAccountname(userProfileEntry.getUsername());
	result.setAccountservicehomepage(RDF2GoUtils.createURI(connector
		.getURL()));

	if (null != userProfileEntry.getAboutMe()) {
	    result.setDescription(userProfileEntry.getAboutMe());
	}

	if (null != userProfileEntry.getUpdated()) {
	    result.setModified(DateUtils.formatISO8601(userProfileEntry
		    .getUpdated().getValue()));
	}

	if (null != userProfileEntry.getFirstName()
		|| null != userProfileEntry.getLastName()) {
	    result.setName((Strings.nullToEmpty(userProfileEntry.getFirstName())
		    + " " + Strings.nullToEmpty(userProfileEntry.getLastName()))
		    .trim());
	} else {
	    result.setName(Strings.nullToEmpty(userProfileEntry.getUsername()));
	}

	return result;
    }

    public static Thread createThread(final YoutubeV2Connector connector,
	    final PlaylistLinkEntry playlistLinkEntry, final URI uri,
	    final Forum parentForum) throws ConnectorException {
	Thread result = new Thread(connector.getModel(), uri, true);
	result.setId(connector.parseYoutubeEntryID(playlistLinkEntry.getId()));

	if (null != playlistLinkEntry.getTitle()) {
	    result.setName(playlistLinkEntry.getTitle().getPlainText());
	}

	if (null != playlistLinkEntry.getSummary()) {
	    result.setDescription(playlistLinkEntry.getSummary().getPlainText());
	}

	if (null != playlistLinkEntry.getPublished()) {
	    result.setCreated(DateUtils.formatISO8601(playlistLinkEntry
		    .getPublished().getValue()));
	}
	if (null != playlistLinkEntry.getUpdated()) {
	    result.setModified(DateUtils.formatISO8601(playlistLinkEntry
		    .getUpdated().getValue()));
	}

	for (Person person : playlistLinkEntry.getAuthors()) {
	    String personUri = person.getUri();
	    result.addCreator(connector.getUserAccount(personUri
		    .substring(personUri.lastIndexOf('/') + 1)));
	}

	result.setParent(parentForum);
	parentForum.addParentof(result);

	// update statistics
	parentForum.setNumthreads((parentForum.hasNumthreads()) ? (parentForum
		.getNumthreads() + 1) : (1));

	return result;
    }

    public static Post createPost(final YoutubeV2Connector connector,
	    final VideoEntry videoEntry, final URI uri,
	    final Container container) throws ConnectorException {

	Post result = new Post(connector.getModel(), uri, true);
	result.setId(connector.getVideoEntryId(videoEntry));

	for (Person person : videoEntry.getAuthors()) {
	    String personUri = person.getUri();
	    result.addCreator(connector.getUserAccount(personUri
		    .substring(personUri.lastIndexOf('/') + 1)));
	}

	YouTubeMediaGroup mediaGroup = videoEntry.getMediaGroup();

	if (null != mediaGroup.getTitle()) {
	    result.setTitle(mediaGroup.getTitle().getPlainTextContent());
	}

	String content = "";
	if (null != videoEntry.getContent()
		&& videoEntry.getContent() instanceof TextContent) {
	    content = videoEntry.getPlainTextContent();
	} else if (null != mediaGroup.getDescription()) {
	    content = mediaGroup.getDescription().getPlainTextContent();
	}
	result.setContent(StringUtils.stripHTML(content));
	result.setContentEncoded(content);

	Link videoLink = videoEntry.getLink("alternate", "text/html");
	if (null != videoLink) {
	    result.addAttachment(RDF2GoUtils.createURI(videoLink.getHref()));
	}

	if (null != videoEntry.getPublished()) {
	    result.setCreated(DateUtils.formatISO8601(videoEntry.getPublished()
		    .getValue()));
	}

	if (null != videoEntry.getUpdated()) {
	    result.setModified(DateUtils.formatISO8601(videoEntry.getUpdated()
		    .getValue()));
	}

	result.setContainer(container);
	container.addContainerof(result);

	// update statistics
	container.setNumitems((container.hasNumitems()) ? (container
		.getNumitems() + 1) : (1));
	SIOCUtils.updateLastItemDate(container, result);

	return result;
    }

    public static Post createComment(final YoutubeV2Connector connector,
	    final CommentEntry commentEntry, final URI uri,
	    final Container container, final Post videoPost)
	    throws ConnectorException {
	Post result = new Post(connector.getModel(), uri, true);

	result.setId(videoPost.getId()
		+ YoutubeV2Constants.COMMENT_ID_SEPERATOR
		+ connector.parseYoutubeEntryID(commentEntry.getId()));

	for (Person person : commentEntry.getAuthors()) {
	    String personUri = person.getUri();
	    result.addCreator(connector.getUserAccount(personUri
		    .substring(personUri.lastIndexOf('/') + 1)));
	}

	if (null != commentEntry.getTitle()) {
	    result.setTitle(commentEntry.getTitle().getPlainText());
	}

	String content = "";
	if (null != commentEntry.getContent()) {
	    content = commentEntry.getPlainTextContent();
	}

	result.setContent(StringUtils.stripHTML(content));
	result.setContentEncoded(content);

	if (null != commentEntry.getPublished()) {
	    result.setCreated(DateUtils.formatISO8601(commentEntry
		    .getPublished().getValue()));
	}

	if (null != commentEntry.getUpdated()) {
	    result.setModified(DateUtils.formatISO8601(commentEntry
		    .getUpdated().getValue()));
	}

	result.setContainer(container);
	container.addContainerof(result);
	SIOCUtils.updateLastItemDate(container, result);
	result.setDiscussion(videoPost); // comments are a discussion about the
					 // parent video

	Link replyToLink = commentEntry.getLink(YouTubeNamespace.IN_REPLY_TO,
		null);

	if (null != replyToLink) { // this comment is a reply to an other
				   // comment
	    String href = replyToLink.getHref();
	    URI replyToUri = RDF2GoUtils.createURI(href.substring(0,
		    href.lastIndexOf('?')));

	    if (Post.hasInstance(connector.getModel(), replyToUri)) {
		Post replyToPost = Post.getInstance(connector.getModel(),
			replyToUri);
		result.setReplyof(replyToPost);
		replyToPost.addReply(result);

		replyToPost
			.setNumreplies((replyToPost.hasNumreplies()) ? (replyToPost
				.getNumreplies() + 1) : (1));
		SIOCUtils.updateLastReplyDate(replyToPost, result);
	    }
	    // TODO: Load post with this replyToUri

	} else { // make it a reply to the video
	    result.setReplyof(videoPost);
	    videoPost.addReply(result);

	    videoPost.setNumreplies((videoPost.hasNumreplies()) ? (videoPost
		    .getNumreplies() + 1) : (1));
	    SIOCUtils.updateLastReplyDate(videoPost, result);
	}

	return result;
    }

    public static CommentEntry createCommentEntry(final Post reply,
	    final Post parentPost) {
	CommentEntry result = new CommentEntry();

	if (reply.hasCreated()) {
	    try {
		result.setPublished(new DateTime(DateUtils.parseISO8601(reply
			.getCreated())));
	    } catch (ParseException e) {
		LOG.warn("Failed to parse " + reply.getCreated(), e);
	    }
	}

	if (reply.hasModified()) {
	    try {
		result.setPublished(new DateTime(DateUtils.parseISO8601(reply
			.getModified())));
	    } catch (ParseException e) {
		LOG.warn("Failed to parse " + reply.getModified(), e);
	    }
	}

	if (reply.hasTitle()) {
	    result.setTitle(new PlainTextConstruct(reply.getTitle()));
	} else if (reply.hasSubject()) {
	    result.setTitle(new PlainTextConstruct(reply.getSubject()));
	} else if (reply.hasName()) {
	    result.setTitle(new PlainTextConstruct(reply.getName()));
	}

	if (reply.hasContent()) {
	    result.setContent(new PlainTextConstruct(reply.getContent()));
	} else if (reply.hasContentEncoded()) {
	    result.setContent(new PlainTextConstruct(reply.getContentEncoded()));
	}

	if (parentPost.hasReplyof()) {
	    // Add reply link
	    result.addLink(YouTubeNamespace.IN_REPLY_TO,
		    "application/atom+xml", String.format(
			    YoutubeV2Constants.ENTRY_COMMENT, parentPost
				    .getDiscussion().getId(), parentPost
				    .getId()));
	}

	return result;
    }
}
