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

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.Statement;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.model.node.Variable;
import org.ontoware.rdf2go.util.RDFTool;
import org.ontoware.rdf2go.vocabulary.RDF;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.SIOC;
import org.rdfs.sioc.Site;
import org.rdfs.sioc.Thread;
import org.rdfs.sioc.UserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.Link;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.youtube.CommentEntry;
import com.google.gdata.data.youtube.CommentFeed;
import com.google.gdata.data.youtube.PlaylistLinkEntry;
import com.google.gdata.data.youtube.PlaylistLinkFeed;
import com.google.gdata.data.youtube.UserProfileEntry;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.VideoFeed;
import com.google.gdata.data.youtube.YouTubeNamespace;
import com.google.gdata.util.ResourceNotFoundException;
import com.google.gdata.util.ServiceException;
import com.google.gdata.util.ServiceForbiddenException;

import de.m0ep.socc.AbstractConnector;
import de.m0ep.socc.exceptions.AuthenticationException;
import de.m0ep.socc.exceptions.ConnectorException;
import de.m0ep.socc.exceptions.NetworkException;
import de.m0ep.socc.exceptions.NotFoundException;
import de.m0ep.socc.utils.ConfigUtils;
import de.m0ep.socc.utils.DateUtils;
import de.m0ep.socc.utils.RDF2GoUtils;
import de.m0ep.socc.utils.StringUtils;

public class YoutubeV2Connector extends AbstractConnector {
    private static final Logger LOG = LoggerFactory
	    .getLogger(YoutubeV2Connector.class);

    private YouTubeService service;
    private YoutubeV2ConnectorConfig ytConfig;
    private String myId;

    private UserProfileEntry userProfile;
    private Forum uploads;
    private Forum playlists;

    @Override
    public void initialize(String id, Model model,
	    Map<String, Object> parameters) throws ConnectorException {
	Preconditions.checkNotNull(id, "Id can not be null");
	Preconditions.checkNotNull(model, "Model can not be null");
	Preconditions.checkArgument(!id.isEmpty(), "Id can not be empty");
	Preconditions.checkArgument(model.isOpen(), "Model must be open");
	Preconditions.checkArgument(
		parameters.containsKey(YoutubeV2ConnectorConfig.USERNAME),
		"No username given");
	Preconditions.checkArgument(
		parameters.containsKey(YoutubeV2ConnectorConfig.PASSWORD),
		"No password given");
	super.initialize(id, model, parameters);

	this.ytConfig = new YoutubeV2ConnectorConfig();
	this.ytConfig = ConfigUtils.fromMap(parameters, this.ytConfig);

	this.service = new YouTubeService("YoutubeConnectorV2",
		this.ytConfig.getDeveloperKey());

	try {
	    this.service.setUserCredentials(this.ytConfig.getUsername(),
		    this.ytConfig.getPassword());
	} catch (com.google.gdata.util.AuthenticationException e) {
	    throw new AuthenticationException(e.getMessage(), e);
	}

	try {
	    this.userProfile = service.getEntry(
		    new URL(String.format(YoutubeV2Constants.ENTRY_USER,
			    "default")), UserProfileEntry.class);
	    this.myId = userProfile.getUsername();
	} catch (MalformedURLException e) {
	    LOG.error("Malformed URL", e);
	    throw new ConnectorException("Malformed URL", e);
	} catch (IOException e) {
	    LOG.error("Network error", e);
	    throw new NetworkException(e.getLocalizedMessage(), e);
	} catch (ServiceException e) {
	    LOG.error("Service error", e);
	    throw mapToConenctorException(e);
	} catch (Throwable t) {
	    LOG.error("unknown error", t);
	    throw new ConnectorException("Unknown error", t);
	}

	URI uri = RDF2GoUtils.createURI(String.format(
		YoutubeV2Constants.FEED_UPLOADS, myId));
	this.uploads = new Forum(model, uri, true);
	this.uploads.setId(YoutubeV2Constants.ID_FOURM_UPLOADS);
	this.uploads.setName("Uploads");
	this.uploads.setHost(getSite());
	getSite().addHostof(this.uploads);

	uri = RDF2GoUtils.createURI(String.format(
		YoutubeV2Constants.FEED_PLAYLISTS, myId));
	this.playlists = new Forum(model, uri, true);
	this.playlists.setId(YoutubeV2Constants.ID_FORUM_PLAYLISTS);
	this.playlists.setName("Playlists");
	this.playlists.setNumthreads(0);
	this.playlists.setHost(getSite());
	getSite().addHostof(this.playlists);
    }

    @Override
    public String getURL() {
	return "http://www.youtube.com/";
    }

    @Override
    public Map<String, Object> getConfiguration() {
	return ConfigUtils.toMap(ytConfig);
    }

    @Override
    public Site getSite() {
	URI uri = RDF2GoUtils.createURI(getURL());

	if (!Site.hasInstance(getModel(), uri)) {
	    Site result = new Site(getModel(), uri, true);
	    result.setName("Youtube");

	    return result;
	}

	return Site.getInstance(getModel(), uri);
    }

    @Override
    public UserAccount getLoginUser() throws ConnectorException {
	return getUserAccount(myId);
    }

    @Override
    public UserAccount getUserAccount(String id) throws ConnectorException {
	URI uri = RDF2GoUtils.createURI(String.format(
		YoutubeV2Constants.ENTRY_USER, id));

	if (!UserAccount.hasAccountname(getModel(), uri)) {
	    UserProfileEntry profileEntry = null;

	    // do cooldown if necessary
	    doCoolDown();

	    try {
		profileEntry = service.getEntry(
			new URL(String
				.format(YoutubeV2Constants.ENTRY_USER, id)),
			UserProfileEntry.class);
	    } catch (MalformedURLException e) {
		LOG.error("Malformed URL", e);
		throw new ConnectorException("Malformed URL", e);
	    } catch (IOException e) {
		LOG.error("Network error", e);
		throw new NetworkException(e.getLocalizedMessage(), e);
	    } catch (ServiceException e) {
		LOG.error("Service error", e);
		throw mapToConenctorException(e);
	    } catch (Throwable t) {
		LOG.error("unknown error", t);
		throw new ConnectorException("Unknown error", t);
	    }

	    if (null != profileEntry) {
		UserAccount result = new UserAccount(getModel(), uri, true);
		result.setId(parseYoutubeEntryID(profileEntry.getId()));
		result.setIsPartOf(getSite());
		result.setAccountname(profileEntry.getUsername());
		result.setAccountservicehomepage(RDF2GoUtils
			.createURI(getURL()));

		if (null != profileEntry.getAboutMe()) {
		    result.setDescription(profileEntry.getAboutMe());
		}

		result.setName(StringUtils.trimToEmpty(profileEntry
			.getFirstName())
			+ " "
			+ StringUtils.trimToEmpty(profileEntry.getLastName()));

		return result;
	    }
	    LOG.error("Failed to create UserAccount");
	    throw new ConnectorException("Failed to create UserAccount");
	}

	return UserAccount.getInstance(getModel(), uri);
    }

    @Override
    public Forum getForum(String id) throws ConnectorException {
	if (YoutubeV2Constants.ID_FOURM_UPLOADS.equalsIgnoreCase(id)) {
	    return Forum.getInstance(getModel(), RDF2GoUtils.createURI(String
		    .format(YoutubeV2Constants.FEED_UPLOADS, myId)));
	} else if (YoutubeV2Constants.ID_FORUM_PLAYLISTS.equalsIgnoreCase(id)) {
	    return Forum.getInstance(getModel(), RDF2GoUtils.createURI(String
		    .format(YoutubeV2Constants.FEED_PLAYLISTS, myId)));
	}

	throw new NotFoundException("No forum found wiht id " + id);
    }

    @Override
    public Thread getThread(String id) throws ConnectorException {
	URI uri = RDF2GoUtils.createURI(String.format(
		YoutubeV2Constants.FEED_PLAYLIST, id));
	if (Thread.hasInstance(getModel(), uri)) {
	    return Thread.getInstance(getModel(), uri);
	}

	// do cooldown if necessary
	doCoolDown();

	PlaylistLinkEntry playlistLinkEntry = null;
	try {
	    playlistLinkEntry = service.getEntry(
		    new URL(String.format(YoutubeV2Constants.ENTRY_PLAYLIST,
			    myId, id)), PlaylistLinkEntry.class);
	} catch (MalformedURLException e) {
	    LOG.error("Malformed URL", e);
	    throw new ConnectorException("Malformed URL", e);
	} catch (IOException e) {
	    LOG.error("Network error", e);
	    throw new NetworkException(e.getLocalizedMessage(), e);
	} catch (ServiceException e) {
	    LOG.error("Service error", e);
	    throw mapToConenctorException(e);
	} catch (Throwable t) {
	    LOG.error("unknown error", t);
	    throw new ConnectorException("Unknown error", t);
	}

	if (null != playlistLinkEntry) {
	    Thread thread = YoutubeV2toSIOCConverter.createThread(this,
		    playlistLinkEntry, uri, playlists);
	    return thread;
	}

	throw new NotFoundException("Thread with id " + id + " not found");
    }

    @Override
    public List<Thread> getThreads(Forum forum) throws ConnectorException {
	List<Thread> result = new ArrayList<Thread>();
	String feedURL = String.format(YoutubeV2Constants.FEED_PLAYLISTS, myId);

	if (forum.equals(playlists)) {
	    PlaylistLinkFeed linkFeed = null;

	    try {
		linkFeed = service.getFeed(new URL(feedURL),
			PlaylistLinkFeed.class);
		feedURL = null;
	    } catch (MalformedURLException e) {
		LOG.error("Malformed URL", e);
		throw new ConnectorException("Malformed URL", e);
	    } catch (IOException e) {
		LOG.error("Network error", e);
		throw new NetworkException(e.getLocalizedMessage(), e);
	    } catch (ServiceException e) {
		LOG.error("Service error", e);
		throw mapToConenctorException(e);
	    } catch (Throwable t) {
		LOG.error("unknown error", t);
		throw new ConnectorException("Unknown error", t);
	    }

	    if (null != linkFeed) {
		for (PlaylistLinkEntry linkEntry : linkFeed.getEntries()) {
		    URI uri = RDF2GoUtils.createURI(String.format(
			    YoutubeV2Constants.FEED_PLAYLIST,
			    parseYoutubeEntryID(linkEntry.getId())));

		    if (!Thread.hasInstance(getModel(), uri)) {
			Thread thread = YoutubeV2toSIOCConverter.createThread(
				this, linkEntry, uri, playlists);
			result.add(thread);

		    } else {
			result.add(Thread.getInstance(getModel(), uri));
		    }
		}

		if (null != linkFeed.getNextLink()) {
		    feedURL = linkFeed.getNextLink().getHref();
		}
	    }
	}

	return result;
    }

    @Override
    public Post getPost(String id) throws ConnectorException {
	URI uri = RDF2GoUtils.createURI(String.format(
		YoutubeV2Constants.ENTRY_VIDEO, id));

	if (Post.hasInstance(getModel(), uri)) {
	    return Post.getInstance(getModel(), uri);
	}

	// TODO try to load post with this id

	throw new NotFoundException("Post with the id " + id + " not found");
    }

    @Override
    public List<Post> getPosts(Container container) throws ConnectorException {
	Preconditions.checkNotNull(container, "container can not be null");
	Preconditions.checkArgument(hasPosts(container), "container "
		+ container + " has no post on this site");

	List<Post> result = new ArrayList<Post>();
	ClosableIterator<Statement> stmtsIter = getModel().findStatements(
		Variable.ANY, SIOC.has_container, container);

	while (stmtsIter.hasNext()) {
	    Statement statement = stmtsIter.next();

	    if (Post.hasInstance(getModel(), statement.getSubject())) {
		result.add(Post.getInstance(getModel(), statement.getSubject()));
	    }
	}
	stmtsIter.close();

	return result;
    }

    @Override
    public List<Post> pollNewPosts(Container container)
	    throws ConnectorException {
	Preconditions.checkArgument(hasPosts(container),
		"Container has no post in this connector");
	List<Post> result = new ArrayList<Post>();
	String nextFeedUrl = null;

	if (isUploadsForum(container)) {
	    nextFeedUrl = String.format(YoutubeV2Constants.FEED_UPLOADS, myId);
	} else if (isPlaylistThread(container)) {
	    nextFeedUrl = String.format(YoutubeV2Constants.FEED_PLAYLIST,
		    container.getId());
	} else {
	    // Programm shouldn't reach this code
	    throw new ConnectorException("Container " + container
		    + " has no post in this connector");
	}

	int numPostsLeft = ytConfig.getMaxNewPostsOnPoll();

	do {
	    // do cooldown if necessary
	    doCoolDown();

	    VideoFeed videoFeed = null;
	    try {
		videoFeed = service.getFeed(new URL(nextFeedUrl),
			VideoFeed.class);
		nextFeedUrl = null;
	    } catch (MalformedURLException e) {
		LOG.error("Malformed URL", e);
		throw new ConnectorException("Malformed URL", e);
	    } catch (IOException e) {
		LOG.error("Network error", e);
		throw new NetworkException(e.getLocalizedMessage(), e);
	    } catch (ServiceException e) {
		LOG.error("Service error", e);
		throw mapToConenctorException(e);
	    } catch (Throwable t) {
		LOG.error("unknown error", t);
		throw new ConnectorException("Unknown error", t);
	    }

	    if (null != videoFeed) {
		Date lastItemDate = new Date(0);
		if (container.hasLastitemdate()) {
		    try {
			lastItemDate = RDFTool.string2DateTime(container
				.getLastitemdate());
		    } catch (ParseException e1) {
			lastItemDate = new Date(0);
		    }
		}

		for (VideoEntry videoEntry : videoFeed.getEntries()) {
		    String videoId = getVideoEntryId(videoEntry);
		    URI uri = RDF2GoUtils.createURI(String.format(
			    YoutubeV2Constants.ENTRY_VIDEO, videoId));

		    Date created = new Date(videoEntry.getPublished()
			    .getValue());

		    if (created.after(lastItemDate)) {
			if (!Post.hasInstance(getModel(), uri)) {
			    Post post = YoutubeV2toSIOCConverter.createPost(
				    this, videoEntry, uri, container);
			    result.add(post);
			    LOG.debug("add post " + post.toString());
			    numPostsLeft--;
			}
		    }

		    if (Post.hasInstance(getModel(), uri)
			    && null != videoEntry.getComments()) {
			List<Post> comments = pollNewReplies(
				Post.getInstance(getModel(), uri), videoEntry);
			result.addAll(comments);
		    }

		    if (0 == numPostsLeft) {
			return result;
		    }
		}
	    }

	    if (null != videoFeed.getNextLink()) {
		nextFeedUrl = videoFeed.getNextLink().getHref();
	    }

	} while (null != nextFeedUrl);

	return result;
    }

    @Override
    public boolean hasPosts(Container container) {
	return isUploadsForum(container) || isPlaylistThread(container);
    }

    @Override
    public boolean canPublishOn(Container container) {
	// Publishing on a container is not supported
	return false;
    }

    @Override
    public boolean canReplyOn(Post parentPost) {
	Preconditions.checkNotNull(parentPost, "parentPost can not be null");

	return parentPost.hasContainer()
		&& (parentPost.hasContainer(uploads) || parentPost
			.getContainer().hasParent(playlists));
    }

    @Override
    public Post replyPost(Post post, Post parentPost) throws ConnectorException {
	Preconditions.checkNotNull(post, "post can not be null");
	Preconditions.checkNotNull(parentPost, "parent can not be null");
	Preconditions.checkArgument(canReplyOn(parentPost),
		"Can not reply on this post " + parentPost.toString());
	CommentEntry commentEntry = new CommentEntry();

	if (post.hasCreated()) {
	    try {
		commentEntry.setPublished(new DateTime(DateUtils
			.parseISO8601(post.getCreated())));
	    } catch (ParseException e) {
		LOG.warn("Failed to parse " + post.getCreated(), e);
	    }
	}

	if (post.hasModified()) {
	    try {
		commentEntry.setPublished(new DateTime(DateUtils
			.parseISO8601(post.getModified())));
	    } catch (ParseException e) {
		LOG.warn("Failed to parse " + post.getModified(), e);
	    }
	}

	if (post.hasTitle()) {
	    commentEntry.setTitle(new PlainTextConstruct(post.getTitle()));

	} else if (post.hasSubject()) {
	    commentEntry.setTitle(new PlainTextConstruct(post.getSubject()));
	} else if (post.hasName()) {
	    commentEntry.setTitle(new PlainTextConstruct(post.getName()));
	}

	if (post.hasContent()) {
	    commentEntry.setContent(new PlainTextConstruct(post.getContent()));
	} else if (post.hasContentEncoded()) {
	    commentEntry.setContent(new PlainTextConstruct(post
		    .getContentEncoded()));
	}

	String parentFeedURL = null;

	if (parentPost.hasReplyof()) {
	    parentFeedURL = String.format(YoutubeV2Constants.FEED_COMMENTS,
		    parentPost.getDiscussion().getId());

	    // Add reply link
	    commentEntry.addLink(YouTubeNamespace.IN_REPLY_TO,
		    "application/atom+xml", String.format(
			    YoutubeV2Constants.ENTRY_COMMENT, parentPost
				    .getDiscussion().getId(), parentPost
				    .getId()));
	} else {
	    parentFeedURL = String.format(YoutubeV2Constants.FEED_COMMENTS,
		    parentPost.getId());
	}

	CommentEntry resultCommentEntry = null;

	try {
	    resultCommentEntry = service.insert(new URL(parentFeedURL),
		    commentEntry);
	} catch (MalformedURLException e) {
	    LOG.error("Malformed URL", e);
	    throw new ConnectorException("Malformed URL", e);
	} catch (IOException e) {
	    LOG.error("Network error", e);
	    throw new NetworkException(e.getLocalizedMessage(), e);
	} catch (ServiceException e) {
	    LOG.error("Service error", e);
	    throw mapToConenctorException(e);
	} catch (Throwable t) {
	    LOG.error("unknown error", t);
	    throw new ConnectorException("Unknown error", t);
	}

	if (null != resultCommentEntry) {
	    Post videoPost = null;
	    if (parentPost.hasReplyof()) {
		videoPost = Post.getInstance(getModel(),
			parentPost.getDiscussion());
	    } else {
		videoPost = parentPost;
	    }

	    String commentId = parseYoutubeEntryID(resultCommentEntry.getId());
	    URI uri = RDF2GoUtils.createURI(String.format(
		    YoutubeV2Constants.ENTRY_COMMENT, videoPost.getId(),
		    commentId));
	    Post addedPost = YoutubeV2toSIOCConverter.createComment(this,
		    resultCommentEntry, uri, videoPost.getContainer(),
		    videoPost);
	    addedPost.setSibling(post);
	    return addedPost;
	}

	throw new ConnectorException("Failed to reply post with unknown reason");
    }

    private List<Post> pollNewReplies(final Post videoPost,
	    final VideoEntry videoEntry) throws ConnectorException {
	if (null == videoEntry.getComments())
	    return new ArrayList<Post>();

	List<Post> result = new ArrayList<Post>();
	String nextFeedUrl = videoEntry.getComments().getFeedLink().getHref();
	CommentFeed commentFeed = null;
	int numCommentsLeft = ytConfig.getMaxNewPostsOnPoll();

	do {
	    try {
		commentFeed = service.getFeed(new URL(nextFeedUrl),
			CommentFeed.class);
		nextFeedUrl = null;
	    } catch (MalformedURLException e) {
		LOG.error("Malformed URL", e);
		throw new ConnectorException("Malformed URL", e);
	    } catch (IOException e) {
		LOG.error("Network error", e);
		throw new NetworkException(e.getLocalizedMessage(), e);
	    } catch (ServiceException e) {
		LOG.error("Service error", e);
		throw mapToConenctorException(e);
	    } catch (Throwable t) {
		LOG.error("unknown error", t);
		throw new ConnectorException("Unknown error", t);
	    }

	    if (null != commentFeed) {
		Date lastReplyDate = new Date(0);
		if (videoPost.hasLastreplydate()) {
		    try {
			lastReplyDate = RDFTool.string2DateTime(videoPost
				.getLastreplydate());
		    } catch (ParseException e1) {
			LOG.warn("failed to parse date "
				+ videoPost.getLastreplydate());
			lastReplyDate = new Date(0);
		    }
		}

		// Sort from old to newest comment
		Collections.sort(commentFeed.getEntries(),
			new Comparator<CommentEntry>() {
			    @Override
			    public int compare(CommentEntry o1, CommentEntry o2) {
				return o1.getPublished().compareTo(
					o2.getPublished());
			    }
			});

		for (CommentEntry commentEntry : commentFeed.getEntries()) {
		    Date created = new Date(commentEntry.getPublished()
			    .getValue());

		    if (created.after(lastReplyDate)) {
			String commentId = parseYoutubeEntryID(commentEntry
				.getId());
			URI uri = RDF2GoUtils.createURI(String.format(
				YoutubeV2Constants.ENTRY_COMMENT,
				videoPost.getId(), commentId));

			if (!Post.hasInstance(getModel(), uri)) {
			    Post reply = YoutubeV2toSIOCConverter
				    .createComment(this, commentEntry, uri,
					    videoPost.getContainer(), videoPost);
			    result.add(reply);
			    numCommentsLeft--;
			}

			if (0 == numCommentsLeft) {
			    return result;
			}
		    }
		}
	    }

	    if (null != commentFeed.getNextLink()) {
		nextFeedUrl = commentFeed.getNextLink().getHref();

		// Cooldown
		try {
		    java.lang.Thread.sleep(ytConfig.getPollCooldownMillis());
		} catch (InterruptedException e) {
		    LOG.error(e.getMessage(), e);
		}
	    }

	} while (null != nextFeedUrl);

	return result;
    }

    ConnectorException mapToConenctorException(ServiceException e) {
	if (e instanceof ResourceNotFoundException) {
	    return new NotFoundException("Not found", e);
	} else if (e instanceof com.google.gdata.util.AuthenticationException) {
	    return new AuthenticationException(e.getLocalizedMessage(), e);
	} else if (e instanceof ServiceForbiddenException) {
	    return new AuthenticationException("Service forbidden", e);
	}

	return new ConnectorException(e.getLocalizedMessage(), e);
    }

    boolean isUploadsForum(Container container) {
	if (getModel().contains(container, RDF.type, SIOC.Forum)) {
	    Forum forum = Forum
		    .getInstance(getModel(), container.getResource());

	    return YoutubeV2Constants.ID_FOURM_UPLOADS.equalsIgnoreCase(forum
		    .getId()) && forum.hasHost(getSite());
	}

	return false;
    }

    boolean isPlaylistThread(Container container) {
	if (getModel().contains(container, RDF.type, SIOC.Thread)) {
	    Thread thread = Thread.getInstance(getModel(),
		    container.getResource());
	    return thread.hasParent(playlists);
	}

	return false;
    }

    String parseYoutubeEntryID(String id) {
	return id.substring(id.lastIndexOf(':') + 1);
    }

    String getVideoEntryId(VideoEntry videoEntry) {
	Link relatedLink = videoEntry.getLink("related", null);
	String videoHref = null;

	if (null != relatedLink) {
	    videoHref = relatedLink.getHref();
	    videoHref = videoHref.substring(0, videoHref.lastIndexOf('?'));

	} else {
	    videoHref = videoEntry.getRelatedVideosLink().getHref();
	    videoHref = videoHref.substring(0, videoHref.lastIndexOf('/'));
	}

	String videoId = videoHref.substring(videoHref.lastIndexOf('/') + 1);
	return videoId;
    }

    /*********************************************************************************/

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + Objects.hashCode(this.ytConfig, this.myId);
	return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (!(obj instanceof YoutubeV2Connector)) {
	    return false;
	}
	YoutubeV2Connector other = (YoutubeV2Connector) obj;

	if (!Objects.equal(this.ytConfig, other.ytConfig)) {
	    return false;
	}

	if (!Objects.equal(this.myId, other.myId)) {
	    return false;
	}

	return super.equals(obj);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return Objects.toStringHelper(this).add("id", getId())
		.add("userId", myId).toString();
    }

}
