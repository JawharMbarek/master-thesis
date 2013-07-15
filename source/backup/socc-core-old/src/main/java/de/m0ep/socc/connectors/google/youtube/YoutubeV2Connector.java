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

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.ontoware.rdf2go.model.Statement;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.model.node.Variable;
import org.ontoware.rdf2go.util.Builder;
import org.ontoware.rdf2go.util.RDFTool;
import org.ontoware.rdf2go.vocabulary.RDF;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.SIOCVocabulary;
import org.rdfs.sioc.Site;
import org.rdfs.sioc.Thread;
import org.rdfs.sioc.UserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.data.Link;
import com.google.gdata.data.youtube.CommentEntry;
import com.google.gdata.data.youtube.CommentFeed;
import com.google.gdata.data.youtube.PlaylistLinkEntry;
import com.google.gdata.data.youtube.PlaylistLinkFeed;
import com.google.gdata.data.youtube.UserProfileEntry;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.VideoFeed;
import com.google.gdata.util.ResourceNotFoundException;
import com.google.gdata.util.ServiceException;
import com.google.gdata.util.ServiceForbiddenException;

import de.m0ep.sioc.service.auth.APIKey;
import de.m0ep.sioc.service.auth.Authentication;
import de.m0ep.sioc.service.auth.Credential;
import de.m0ep.sioc.service.auth.Password;
import de.m0ep.sioc.service.auth.Service;
import de.m0ep.sioc.service.auth.Username;
import de.m0ep.socc.AbstractConnector;
import de.m0ep.socc.ISOCCContext;
import de.m0ep.socc.exceptions.AuthenticationException;
import de.m0ep.socc.exceptions.ConnectorException;
import de.m0ep.socc.exceptions.NetworkException;
import de.m0ep.socc.exceptions.NotFoundException;
import de.m0ep.socc.utils.RDF2GoUtils;

public class YoutubeV2Connector extends AbstractConnector {
    /*
     * Youtube can make problems if it is polled more the once every 500ms.
     */
    private static final int YT_POLL_COOLDOWN = 500;

    private static final Logger LOG = LoggerFactory
	    .getLogger(YoutubeV2Connector.class);

    private YouTubeService ytService = null;
    private String myId = null;

    private UserProfileEntry userProfile = null;
    private Forum uploads = null;
    private Forum playlists = null;

    private URI endpoint = Builder.createURI("http://www.youtube.com");

    private APIKey developerKey = null;
    private Username username = null;
    private Password password = null;

    private long nextPollTime = 0;

    @Override
    public void initialize(String id, ISOCCContext context, Service service,
	    UserAccount userAccount) throws ConnectorException {
	super.initialize(id, context, service, userAccount);

	if (service.hasServiceEndpoint()) {
	    this.endpoint = Builder.createURI(
		    service.getServiceEndpoint().toString());
	}
	service.setServiceEndpoint(endpoint);

	Preconditions.checkArgument(
		service.hasAuthentication(),
		"Service has no Authentication.");
	Authentication serviceAuth = service.getAuthentication();

	Preconditions.checkArgument(
		serviceAuth.hasCredential(),
		"Service has no credentials.");
	ClosableIterator<Credential> serviceCredentialsIter = serviceAuth
		.getAllCredential();

	this.developerKey = null;
	while (serviceCredentialsIter.hasNext()) {
	    Credential credential = (Credential) serviceCredentialsIter.next();
	    URI type = RDF2GoUtils.getType(
		    credential.getModel(),
		    credential.asResource());

	    if (type.equals(APIKey.RDFS_CLASS)) {
		this.developerKey = APIKey.getInstance(
			credential.getModel(),
			credential.asResource());
	    }
	}
	serviceCredentialsIter.close();

	Preconditions.checkArgument(
		null != developerKey && developerKey.hasValue(),
		"No APIKey (DeveloperKey) credential found in service");

	de.m0ep.sioc.service.auth.UserAccount authUserAccount =
		de.m0ep.sioc.service.auth.UserAccount.getInstance(
			userAccount.getModel(),
			userAccount.getResource());

	Preconditions.checkArgument(
		authUserAccount.hasAuthentication(),
		"UserAccount has no authentication");
	Authentication userAuth = authUserAccount.getAuthentication();

	Preconditions.checkArgument(
		userAuth.hasCredential(),
		"UserAccount has no credentials.");
	ClosableIterator<Credential> credentialsIter = userAuth
		.getAllCredential();

	this.username = null;
	this.password = null;
	while (credentialsIter.hasNext()) {
	    Credential credential = (Credential) credentialsIter.next();

	    URI type = RDF2GoUtils.getType(
		    credential.getModel(),
		    credential);

	    if (type.equals(Username.RDFS_CLASS)) {
		this.username = Username.getInstance(
			credential.getModel(),
			credential.getResource());
	    } else if (type.equals(Password.RDFS_CLASS)) {
		this.password = Password.getInstance(
			credential.getModel(),
			credential.asResource());
	    }
	}
	credentialsIter.close();

	Preconditions.checkArgument(
		null != this.username && username.hasValue(),
		"No Username credential found in userAccount");

	Preconditions.checkArgument(
		null != this.password && password.hasValue(),
		"No Password credential found in userAccount");
    }

    @Override
    public void connect() throws ConnectorException {
	setConnected(false);

	this.ytService = new YouTubeService(
		getId(),
		developerKey.getValue());

	try {
	    this.ytService.setUserCredentials(
		    username.getValue(),
		    password.getValue());
	} catch (com.google.gdata.util.AuthenticationException e) {
	    throw new AuthenticationException(e.getMessage(), e);
	}

	startPolling();

	try {
	    this.userProfile = ytService.getEntry(
		    new URL(String.format(YoutubeV2Constants.FMT_ENTRY_USER,
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
	} finally {
	    finishPolling();
	}

	initModel();
	setConnected(true);
    }

    private void initModel() {
	URI uri = Builder.createURI(String.format(
		YoutubeV2Constants.FMT_FEED_UPLOADS, myId));

	if (Forum.hasInstance(context.getDataModel(), uri)) {
	    uploads = Forum.getInstance(context.getDataModel(), uri);
	} else {
	    uploads = new Forum(context.getDataModel(), uri, true);
	    uploads
		    .setId(
		    String.format(
			    YoutubeV2Constants.FMT_ID_FORUM_UPLOADS,
			    myId));
	    uploads.setName("Uploads");
	    uploads.setHost(getSite());
	    getSite().addHostOf(uploads);
	}

	uri = Builder.createURI(String.format(
		YoutubeV2Constants.FMT_FEED_PLAYLISTS, myId));

	if (Forum.hasInstance(context.getDataModel(), uri)) {
	    playlists = Forum.getInstance(context.getDataModel(), uri);
	} else {
	    playlists = new Forum(context.getDataModel(), uri, true);
	    playlists.setId(
		    String.format(YoutubeV2Constants.FMT_ID_FORUM_PLAYLISTS,
			    myId));
	    playlists.setName("Playlists");
	    playlists.setNumThreads(0);
	    playlists.setHost(getSite());
	    getSite().addHostOf(playlists);
	}
    }

    @Override
    public void disconnect() {
	ytService = null;
	userProfile = null;
	myId = null;

	setConnected(false);
    }

    @Override
    public Site getSite() {
	if (!Site.hasInstance(context.getDataModel(), endpoint)) {
	    Site result = new Site(context.getDataModel(), endpoint, true);
	    result.setName("Youtube");

	    return result;
	}

	return Site.getInstance(context.getDataModel(), endpoint);
    }

    @Override
    public UserAccount getUserAccount(String id) throws ConnectorException {
	URI uri = Builder.createURI(String.format(
		YoutubeV2Constants.FMT_ENTRY_USER, id));

	if (!UserAccount.hasAccountName(context.getDataModel(), uri)) {
	    UserProfileEntry profileEntry = null;

	    startPolling();

	    try {
		profileEntry = ytService
			.getEntry(
				new URL(
					String
						.format(YoutubeV2Constants.FMT_ENTRY_USER,
							id)),
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
	    } finally {
		finishPolling();
	    }

	    if (null != profileEntry) {
		UserAccount result = new UserAccount(context.getDataModel(),
			uri, true);
		result.setId(parseYoutubeEntryID(profileEntry.getId()));
		result.setIsPartOf(getSite());
		result.setAccountName(profileEntry.getUsername());
		result.setAccountServiceHomepage(endpoint);

		if (null != profileEntry.getAboutMe()) {
		    result.setDescription(profileEntry.getAboutMe());
		}

		result.setName(Strings.nullToEmpty(profileEntry.getFirstName())
			+ " "
			+ Strings.nullToEmpty(profileEntry.getLastName()));

		return result;
	    }
	    LOG.error("Failed to create UserAccount");
	    throw new ConnectorException("Failed to create UserAccount");
	}

	return UserAccount.getInstance(context.getDataModel(), uri);
    }

    @Override
    public Forum getForum(String id) throws ConnectorException {
	if (YoutubeV2Constants.FMT_ID_FORUM_UPLOADS.equalsIgnoreCase(id)) {
	    return Forum
		    .getInstance(
			    context.getDataModel(),
			    Builder.createURI(String
				    .format(YoutubeV2Constants.FMT_FEED_UPLOADS,
					    myId)));
	} else if (YoutubeV2Constants.FMT_ID_FORUM_PLAYLISTS
		.equalsIgnoreCase(id)) {
	    return Forum
		    .getInstance(
			    context.getDataModel(),
			    Builder.createURI(String
				    .format(YoutubeV2Constants.FMT_FEED_PLAYLISTS,
					    myId)));
	}

	throw new NotFoundException("No forum found wiht id " + id);
    }

    @Override
    public Thread getThread(String id) throws ConnectorException {
	URI uri = Builder.createURI(String.format(
		YoutubeV2Constants.FMT_FEED_PLAYLIST, id));
	if (Thread.hasInstance(context.getDataModel(), uri)) {
	    return Thread.getInstance(context.getDataModel(), uri);
	}

	startPolling();

	PlaylistLinkEntry playlistLinkEntry = null;
	try {
	    playlistLinkEntry = ytService.getEntry(
		    new URL(String.format(
			    YoutubeV2Constants.FMT_ENTRY_PLAYLIST,
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
	} finally {
	    finishPolling();
	}

	if (null != playlistLinkEntry) {
	    Thread thread = YoutubeV2SIOCConverter.createThread(this,
		    playlistLinkEntry, uri, playlists);
	    return thread;
	}

	throw new NotFoundException("Thread with id " + id + " not found");
    }

    @Override
    public List<Thread> getThreads(Forum forum) throws ConnectorException {
	List<Thread> result = new ArrayList<Thread>();
	String feedURL = String.format(YoutubeV2Constants.FMT_FEED_PLAYLISTS,
		myId);

	if (forum.hasHost(getSite())) {
	    if (playlists.equals(forum)) {
		LOG.debug("load playlists from youtube");
		PlaylistLinkFeed linkFeed = null;

		startPolling();

		try {
		    linkFeed = ytService.getFeed(new URL(feedURL),
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
		} finally {
		    finishPolling();
		}

		if (null != linkFeed) {
		    for (PlaylistLinkEntry linkEntry : linkFeed.getEntries()) {
			URI uri = Builder.createURI(String.format(
				YoutubeV2Constants.FMT_FEED_PLAYLIST,
				parseYoutubeEntryID(linkEntry.getId())));

			if (!Thread.hasInstance(context.getDataModel(), uri)) {
			    Thread thread = YoutubeV2SIOCConverter
				    .createThread(
					    this, linkEntry, uri, playlists);
			    result.add(thread);

			} else {
			    result.add(Thread.getInstance(context
				    .getDataModel(), uri));
			}
		    }

		    if (null != linkFeed.getNextLink()) {
			feedURL = linkFeed.getNextLink().getHref();
		    }
		}
	    }
	}

	return result;
    }

    @Override
    public Post getPost(String id) throws ConnectorException {
	if (id.contains(YoutubeV2Constants.COMMENT_ID_SEPERATOR)) {
	    // it is maybe a comment
	    String[] ids = id.split(YoutubeV2Constants.COMMENT_ID_SEPERATOR);

	    if (2 == ids.length && !ids[0].isEmpty() && !ids[1].isEmpty()) {
		ids[0].trim();
		ids[1].trim();

		URI uri = Builder.createURI(String.format(
			YoutubeV2Constants.FMT_ENTRY_COMMENT, ids[0], ids[2]));

		if (Post.hasInstance(context.getDataModel(), uri)) {
		    return Post.getInstance(context.getDataModel(), uri);
		}

		CommentEntry commentEntry = null;

		// try to load the comment from youtube
		try {
		    commentEntry = ytService.getEntry(uri.asJavaURI().toURL(),
			    CommentEntry.class);
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

		if (null != commentEntry) {
		    Post parentPost;
		    try {
			parentPost = getPost(ids[0]);
		    } catch (NotFoundException e) {
			throw new NotFoundException(
				"Could not find parent post of this comment", e);
		    }

		    return YoutubeV2SIOCConverter.createComment(this,
			    commentEntry, uri, parentPost.getContainer(),
			    parentPost);
		}
	    }
	} else {
	    // it is maybe a video post
	    URI uri = Builder.createURI(String.format(
		    YoutubeV2Constants.FMT_ENTRY_VIDEO, id));

	    if (Post.hasInstance(context.getDataModel(), uri)) {
		return Post.getInstance(context.getDataModel(), uri);
	    }

	    // TODO try to load post with this id
	    // in wich container?
	}

	throw new NotFoundException("Post with the id " + id + " not found");
    }

    @Override
    public List<Post> getPosts(Container container) throws ConnectorException {
	Preconditions.checkNotNull(container, "container can not be null");
	Preconditions.checkArgument(hasPosts(container), "container "
		+ container + " has no post on this site");

	List<Post> result = new ArrayList<Post>();
	ClosableIterator<Statement> stmtsIter = context.getDataModel()
		.findStatements(
			Variable.ANY, SIOCVocabulary.has_container, container);

	while (stmtsIter.hasNext()) {
	    Statement statement = stmtsIter.next();

	    if (Post.hasInstance(context.getDataModel(), statement.getSubject())) {
		result.add(Post.getInstance(context.getDataModel(), statement
			.getSubject()));
	    }
	}
	stmtsIter.close();

	return result;
    }

    @Override
    public List<Post> pollPosts(Container container, long limit)
	    throws ConnectorException {
	Preconditions.checkArgument(hasPosts(container),
		"Container has no post in this connector");
	List<Post> result = new ArrayList<Post>();
	String nextFeedUrl = null;

	if (isUploadsForum(container)) {
	    nextFeedUrl = String.format(YoutubeV2Constants.FMT_FEED_UPLOADS,
		    myId);
	} else if (isPlaylistThread(container)) {
	    nextFeedUrl = String.format(YoutubeV2Constants.FMT_FEED_PLAYLIST,
		    container.getId());
	} else {
	    // Programm shouldn't reach this code
	    throw new ConnectorException("Container " + container
		    + " has no post in this connector");
	}

	long numPostsLeft = limit;

	do {
	    startPolling();

	    VideoFeed videoFeed = null;
	    try {
		videoFeed = ytService.getFeed(new URL(nextFeedUrl),
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
	    } finally {
		finishPolling();
	    }

	    if (null != videoFeed) {
		Date lastItemDate = new Date(0);
		if (container.hasLastItemDate()) {
		    try {
			lastItemDate = RDFTool.string2DateTime(container
				.getLastItemDate());
		    } catch (ParseException e1) {
			lastItemDate = new Date(0);
		    }
		}

		for (VideoEntry videoEntry : videoFeed.getEntries()) {
		    String videoId = getVideoEntryId(videoEntry);
		    URI uri = Builder.createURI(String.format(
			    YoutubeV2Constants.FMT_ENTRY_VIDEO, videoId));

		    Date created = new Date(videoEntry.getPublished()
			    .getValue());

		    if (created.after(lastItemDate)) {
			if (!Post.hasInstance(context.getDataModel(), uri)) {
			    Post post = YoutubeV2SIOCConverter.createPost(this,
				    videoEntry, uri, container);
			    result.add(post);
			    LOG.debug("add post " + post.toString());
			    numPostsLeft--;
			}
		    }

		    if (Post.hasInstance(context.getDataModel(), uri)
			    && null != videoEntry.getComments()) {
			List<Post> comments = pollNewReplies(
				Post.getInstance(
					context.getDataModel(),
					uri),
				videoEntry,
				limit);
			result.addAll(comments);
		    }

		    // TODO: Update post?

		    if (0 == numPostsLeft) {
			return result;
		    }
		}
	    }

	    // get next feed 'page', if there is one.
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
    public Post publishPost(Post post, Container container)
	    throws ConnectorException {
	throw new UnsupportedOperationException(
		"YoutubeV2Connector doesn't support publishing new video post");
    }

    @Override
    public Post replyPost(Post reply, Post parentPost)
	    throws ConnectorException {
	Preconditions.checkNotNull(reply, "post can not be null");
	Preconditions.checkNotNull(parentPost, "parent can not be null");
	Preconditions.checkArgument(canReplyOn(parentPost),
		"Can not reply on this post " + parentPost.toString());

	CommentEntry commentEntry = YoutubeV2SIOCConverter.createCommentEntry(
		reply, parentPost);
	CommentEntry resultCommentEntry = null;
	String commentsFeedURL = null;

	// get the correct url for the comments feed
	if (parentPost.hasReplyOf()) {
	    commentsFeedURL = String.format(
		    YoutubeV2Constants.FMT_FEED_COMMENTS,
		    parentPost.getDiscussion().getId());
	} else {
	    commentsFeedURL = String.format(
		    YoutubeV2Constants.FMT_FEED_COMMENTS,
		    parentPost.getId());
	}

	try {
	    resultCommentEntry = ytService.insert(new URL(commentsFeedURL),
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
	    if (parentPost.hasReplies()) {
		videoPost = Post.getInstance(
			context.getDataModel(),
			parentPost.getDiscussion());
	    } else {
		videoPost = parentPost;
	    }

	    String commentId = parseYoutubeEntryID(resultCommentEntry.getId());
	    URI uri = Builder.createURI(String.format(
		    YoutubeV2Constants.FMT_ENTRY_COMMENT, videoPost.getId(),
		    commentId));
	    Post addedPost = YoutubeV2SIOCConverter.createComment(this,
		    resultCommentEntry, uri, videoPost.getContainer(),
		    videoPost);
	    addedPost.setSibling(reply);
	    return addedPost;
	}

	throw new ConnectorException("Failed to reply post with unknown reason");
    }

    private List<Post> pollNewReplies(final Post videoPost,
	    final VideoEntry videoEntry, long limit) throws ConnectorException {
	if (null == videoEntry.getComments())
	    return new ArrayList<Post>();

	List<Post> result = new ArrayList<Post>();
	String nextFeedUrl = videoEntry.getComments().getFeedLink().getHref();
	CommentFeed commentFeed = null;
	long numCommentsLeft = limit;

	do {
	    startPolling();

	    try {
		commentFeed = ytService.getFeed(new URL(nextFeedUrl),
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
	    } finally {
		finishPolling();
	    }

	    if (null != commentFeed) {
		Date lastReplyDate = new Date(0);
		if (videoPost.hasLastReplyDate()) {
		    try {
			lastReplyDate = RDFTool.string2DateTime(videoPost
				.getLastReplyDate());
		    } catch (ParseException e1) {
			LOG.warn("failed to parse date "
				+ videoPost.getLastReplyDate());
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
			URI uri = Builder.createURI(String.format(
				YoutubeV2Constants.FMT_ENTRY_COMMENT,
				videoPost.getId(), commentId));

			if (!Post.hasInstance(context.getDataModel(), uri)) {
			    Post reply = YoutubeV2SIOCConverter.createComment(
				    this, commentEntry, uri,
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
	    }

	} while (null != nextFeedUrl);

	return result;
    }

    private void finishPolling() {
	if (nextPollTime > System.currentTimeMillis()) {
	    try {
		java.lang.Thread.sleep(nextPollTime
			- System.currentTimeMillis());
	    } catch (InterruptedException e) {
		LOG.warn("Interrupted", e);
	    }
	}
    }

    private void startPolling() {
	nextPollTime = System.currentTimeMillis() + YT_POLL_COOLDOWN;
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
	if (context.getDataModel().contains(
		container,
		RDF.type,
		SIOCVocabulary.Forum)) {
	    Forum forum = Forum
		    .getInstance(context.getDataModel(), container
			    .getResource());

	    return YoutubeV2Constants.FMT_ID_FORUM_UPLOADS
		    .equalsIgnoreCase(forum
			    .getId())
		    && forum.hasHost(getSite());
	}

	return false;
    }

    boolean isPlaylistThread(Container container) {
	if (context.getDataModel().contains(
		container,
		RDF.type,
		SIOCVocabulary.Thread)) {
	    Thread thread = Thread.getInstance(context.getDataModel(),
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
	result = prime * result + Objects.hashCode(this.myId);
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
