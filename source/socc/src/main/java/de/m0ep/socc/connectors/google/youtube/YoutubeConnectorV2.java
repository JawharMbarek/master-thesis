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
import com.google.gdata.data.Person;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.TextContent;
import com.google.gdata.data.youtube.CommentEntry;
import com.google.gdata.data.youtube.CommentFeed;
import com.google.gdata.data.youtube.PlaylistLinkEntry;
import com.google.gdata.data.youtube.PlaylistLinkFeed;
import com.google.gdata.data.youtube.UserProfileEntry;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.VideoFeed;
import com.google.gdata.data.youtube.YouTubeMediaGroup;
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
import de.m0ep.socc.utils.SIOCUtils;
import de.m0ep.socc.utils.StringUtils;

public class YoutubeConnectorV2 extends AbstractConnector {
    private static final Logger LOG = LoggerFactory
	    .getLogger(YoutubeConnectorV2.class);

    protected static final String GDATA_FEED_BASE = "http://gdata.youtube.com/feeds/api/";

    protected static final String FEED_UPLOADS = GDATA_FEED_BASE
	    + "users/%s/uploads";
    protected static final String FEED_PLAYLISTS = GDATA_FEED_BASE
	    + "users/%s/playlists";
    protected static final String FEED_PLAYLIST = GDATA_FEED_BASE
	    + "playlists/%s";
    protected static final String FEED_COMMENTS = GDATA_FEED_BASE
	    + "videos/%s/comments";
    protected static final String ENTRY_PLAYLIST = GDATA_FEED_BASE
	    + "users/%s/playlists/%s";
    protected static final String ENTRY_VIDEO = GDATA_FEED_BASE + "videos/%s";
    // protected static final String ENTRY_COMMENT = GDATA_FEED_BASE
    // + "videos/%s/comments/%s";
    protected static final String ENTRY_USER = GDATA_FEED_BASE + "users/%s";

    private static final String ID_FOURM_UPLOADS = "uploads";
    private static final String ID_FORUM_PLAYLISTS = "playlists";

    private YouTubeService service;
    private YoutubeConnectorV2Config ytConfig;
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
		parameters.containsKey(YoutubeConnectorV2Config.USERNAME),
		"No username given");
	Preconditions.checkArgument(
		parameters.containsKey(YoutubeConnectorV2Config.PASSWORD),
		"No password given");
	super.initialize(id, model, parameters);

	this.ytConfig = new YoutubeConnectorV2Config();
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
		    new URL(String.format(ENTRY_USER, "default")),
		    UserProfileEntry.class);
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

	URI uri = RDF2GoUtils.createURI(String.format(FEED_UPLOADS, myId));
	this.uploads = new Forum(model, uri, true);
	this.uploads.setId(ID_FOURM_UPLOADS);
	this.uploads.setName("Uploads");
	this.uploads.setHost(getSite());
	getSite().addHostof(this.uploads);

	uri = RDF2GoUtils.createURI(String.format(FEED_PLAYLISTS, myId));
	this.playlists = new Forum(model, uri, true);
	this.playlists.setId(ID_FORUM_PLAYLISTS);
	this.playlists.setName("Playlist");
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
	URI uri = RDF2GoUtils.createURI(String.format(ENTRY_USER, id));

	if (!UserAccount.hasAccountname(getModel(), uri)) {
	    UserProfileEntry profileEntry = null;

	    try {
		profileEntry = service.getEntry(
			new URL(String.format(ENTRY_USER, id)),
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
		result.setId(getYoutubeID(profileEntry.getId()));
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
	if (ID_FOURM_UPLOADS.equalsIgnoreCase(id)) {
	    return Forum.getInstance(getModel(),
		    RDF2GoUtils.createURI(String.format(FEED_UPLOADS, myId)));
	} else if (ID_FORUM_PLAYLISTS.equalsIgnoreCase(id)) {
	    return Forum.getInstance(getModel(),
		    RDF2GoUtils.createURI(String.format(FEED_PLAYLISTS, myId)));
	}

	throw new NotFoundException("No forum found wiht id " + id);
    }

    @Override
    public Thread getThread(String id) throws ConnectorException {
	URI uri = RDF2GoUtils.createURI(String.format(FEED_PLAYLIST, id));
	if (Thread.hasInstance(getModel(), uri)) {
	    return Thread.getInstance(getModel(), uri);
	}

	PlaylistLinkEntry playlistLinkEntry = null;
	try {
	    playlistLinkEntry = service.getEntry(
		    new URL(String.format(ENTRY_PLAYLIST, myId, id)),
		    PlaylistLinkEntry.class);
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
	    Thread thread = createThread(playlists, uri, playlistLinkEntry);
	    return thread;
	}

	throw new NotFoundException("Thread with id " + id + " not found");
    }

    @Override
    public List<Thread> getThreads(Forum forum) throws ConnectorException {
	List<Thread> result = new ArrayList<Thread>();
	String feedURL = String.format(FEED_PLAYLISTS, myId);

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
			    FEED_PLAYLIST, getYoutubeID(linkEntry.getId())));

		    if (!Thread.hasInstance(getModel(), uri)) {
			Thread thread = createThread(playlists, uri, linkEntry);
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
	URI uri = RDF2GoUtils.createURI(String.format(ENTRY_VIDEO, id));

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
	    nextFeedUrl = String.format(FEED_UPLOADS, myId);
	} else if (isPlaylistThread(container)) {
	    nextFeedUrl = String.format(FEED_PLAYLIST, container.getId());
	} else {
	    // Programm shouldn't reach this code
	    throw new ConnectorException("Container " + container
		    + " has no post in this connector");
	}

	int numPostsLeft = ytConfig.getMaxNewPostsOnPoll();

	do {
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
		    URI uri = RDF2GoUtils.createURI(String.format(ENTRY_VIDEO,
			    getYoutubeID(videoEntry.getId())));
		    Date created = new Date(videoEntry.getPublished()
			    .getValue());

		    if (created.after(lastItemDate)) {
			if (!Post.hasInstance(getModel(), uri)) {
			    Post post = createPost(container, videoEntry, uri);
			    result.add(post);
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
    public Post replyPost(Post post, Post parent) throws ConnectorException {
	Preconditions.checkNotNull(post, "post can not be null");
	Preconditions.checkNotNull(parent, "parent can not be null");
	Preconditions.checkArgument(canReplyOn(parent),
		"Can not reply on this post " + parent.toString());
	CommentEntry comment = new CommentEntry();

	if (post.hasCreated()) {
	    try {
		comment.setPublished(new DateTime(DateUtils.parseISO8601(post
			.getCreated())));
	    } catch (ParseException e) {
		LOG.warn("Failed to parse " + post.getCreated(), e);
	    }
	}

	if (post.hasModified()) {
	    try {
		comment.setPublished(new DateTime(DateUtils.parseISO8601(post
			.getModified())));
	    } catch (ParseException e) {
		LOG.warn("Failed to parse " + post.getModified(), e);
	    }
	}

	if (post.hasTitle()) {
	    comment.setTitle(new PlainTextConstruct(post.getTitle()));

	} else if (post.hasSubject()) {
	    comment.setTitle(new PlainTextConstruct(post.getSubject()));
	} else if (post.hasName()) {
	    comment.setTitle(new PlainTextConstruct(post.getName()));
	}

	if (post.hasContent()) {
	    comment.setContent(new PlainTextConstruct(post.getContent()));
	} else if (post.hasContentEncoded()) {
	    comment.setContent(new PlainTextConstruct(post.getContentEncoded()));
	}

	String parentFeedURL = null;

	if (parent.getId().contains("/comments/")) {
	    parentFeedURL = parent.getDiscussion().toString() + "/comments";

	    // Add reply link
	    comment.addLink(YouTubeNamespace.IN_REPLY_TO,
		    "application/atom+xml", parent.toString());
	} else {
	    parentFeedURL = parent.toString() + "/comments";
	}

	CommentEntry result = null;

	try {
	    result = service.insert(new URL(parentFeedURL), comment);
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

	if (null != result) {
	    Post videoPost = null;
	    if (parent.getId().contains("/comments/")) {
		videoPost = Post
			.getInstance(getModel(), parent.getDiscussion());
	    } else {
		videoPost = parent;
	    }

	    String commentId = videoPost.getId() + "/comments/"
		    + getYoutubeID(comment.getId());
	    URI uri = RDF2GoUtils.createURI(String.format(ENTRY_VIDEO,
		    commentId));
	    Post addedPost = createComment(videoPost.getContainer(), parent,
		    comment, uri);
	    addedPost.setSibling(post);
	    return addedPost;
	}

	throw new ConnectorException("Failed to reply post with unknown reason");
    }

    private Thread createThread(final Forum forum, final URI uri,
	    final PlaylistLinkEntry entry) throws ConnectorException {
	Thread result = new Thread(getModel(), uri, true);
	result.setId(getYoutubeID(entry.getId()));

	if (null != entry.getTitle()) {
	    result.setName(entry.getTitle().getPlainText());
	}

	if (null != entry.getSummary()) {
	    result.setDescription(entry.getSummary().getPlainText());
	}

	if (null != entry.getPublished()) {
	    result.setCreated(DateUtils.formatISO8601(entry.getPublished()
		    .getValue()));
	}
	if (null != entry.getUpdated()) {
	    result.setModified(DateUtils.formatISO8601(entry.getUpdated()
		    .getValue()));
	}

	for (Person person : entry.getAuthors()) {
	    String personUri = person.getUri();
	    result.addCreator(getUserAccount(personUri.substring(personUri
		    .lastIndexOf('/') + 1)));
	}

	result.setParent(forum);
	forum.addParentof(result);
	if (forum.hasNumthreads()) {
	    forum.setNumthreads(forum.getNumthreads() + 1);
	} else {
	    forum.setNumthreads(1);
	}

	return result;
    }

    private Post createPost(final Container container,
	    final VideoEntry videoEntry, final URI uri)
	    throws ConnectorException {

	Post result = new Post(getModel(), uri, true);
	result.setId(getYoutubeID(videoEntry.getId()));

	for (Person person : videoEntry.getAuthors()) {
	    String personUri = person.getUri();
	    result.addCreator(getUserAccount(personUri.substring(personUri
		    .lastIndexOf('/') + 1)));
	}

	YouTubeMediaGroup mediaGroup = videoEntry.getMediaGroup();

	if (null != mediaGroup.getTitle()) {
	    result.setTitle(mediaGroup.getTitle().getPlainTextContent());
	}

	String content = "";
	if (null != videoEntry.getContent()
		&& videoEntry.getContent() instanceof TextContent) {
	    content = ((TextContent) videoEntry.getContent()).getContent()
		    .getPlainText();
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
	SIOCUtils.updateLastItemDate(container, result);

	return result;
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
		    String commentId = videoPost.getId() + "/comments/"
			    + getYoutubeID(commentEntry.getId());

		    if (created.after(lastReplyDate)) {
			URI uri = RDF2GoUtils.createURI(String.format(
				ENTRY_VIDEO, commentId));

			if (!Post.hasInstance(getModel(), uri)) {
			    Post reply = createComment(
				    videoPost.getContainer(), videoPost,
				    commentEntry, uri);
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

    private Post createComment(final Container container, final Post videoPost,
	    final CommentEntry commentEntry, final URI uri)
	    throws ConnectorException {
	Post result = new Post(getModel(), uri, true);
	result.setId(videoPost.getId() + "/comments/"
		+ getYoutubeID(commentEntry.getId()));

	for (Person person : commentEntry.getAuthors()) {
	    String personUri = person.getUri();
	    result.addCreator(getUserAccount(personUri.substring(personUri
		    .lastIndexOf('/') + 1)));
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

	    if (Post.hasInstance(getModel(), replyToUri)) {
		Post replyToPost = Post.getInstance(getModel(), replyToUri);
		result.setReplyof(replyToPost);
		replyToPost.addReply(result);
		SIOCUtils.updateLastReplyDate(replyToPost, result);
	    }
	    // TODO: Load post with this replyToUri

	} else { // make it a reply to the video
	    result.setReplyof(videoPost);
	    videoPost.addReply(result);
	    SIOCUtils.updateLastReplyDate(videoPost, result);
	}

	return result;
    }

    private ConnectorException mapToConenctorException(ServiceException e) {
	if (e instanceof ResourceNotFoundException) {
	    return new NotFoundException("Not found", e);
	} else if (e instanceof com.google.gdata.util.AuthenticationException) {
	    return new AuthenticationException(e.getLocalizedMessage(), e);
	} else if (e instanceof ServiceForbiddenException) {
	    return new AuthenticationException("Service forbidden", e);
	}

	return new ConnectorException(e.getLocalizedMessage(), e);
    }

    private String getYoutubeID(String id) {
	return id.substring(id.lastIndexOf(':') + 1);
    }

    private boolean isUploadsForum(Container container) {
	if (getModel().contains(container, RDF.type, SIOC.Forum)) {
	    Forum forum = Forum
		    .getInstance(getModel(), container.getResource());

	    return ID_FOURM_UPLOADS.equalsIgnoreCase(forum.getId())
		    && forum.hasHost(getSite());
	}

	return false;
    }

    private boolean isPlaylistThread(Container container) {
	if (getModel().contains(container, RDF.type, SIOC.Thread)) {
	    Thread thread = Thread.getInstance(getModel(),
		    container.getResource());
	    return thread.hasParent(playlists);
	}

	return false;
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
	if (!(obj instanceof YoutubeConnectorV2)) {
	    return false;
	}
	YoutubeConnectorV2 other = (YoutubeConnectorV2) obj;

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
