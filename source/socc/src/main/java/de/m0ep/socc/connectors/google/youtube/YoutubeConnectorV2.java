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
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.node.URI;
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

import com.google.common.base.Preconditions;
import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.data.Person;
import com.google.gdata.data.youtube.PlaylistLinkEntry;
import com.google.gdata.data.youtube.PlaylistLinkFeed;
import com.google.gdata.data.youtube.UserProfileEntry;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.VideoFeed;
import com.google.gdata.util.ResourceNotFoundException;
import com.google.gdata.util.ServiceException;
import com.google.gdata.util.ServiceForbiddenException;

import de.m0ep.socc.connectors.AbstractConnector;
import de.m0ep.socc.connectors.exceptions.AuthenticationException;
import de.m0ep.socc.connectors.exceptions.ConnectorException;
import de.m0ep.socc.connectors.exceptions.NetworkException;
import de.m0ep.socc.connectors.exceptions.NotFoundException;
import de.m0ep.socc.utils.ConfigUtils;
import de.m0ep.socc.utils.DateUtils;
import de.m0ep.socc.utils.RDF2GoUtils;
import de.m0ep.socc.utils.StringUtils;

public class YoutubeConnectorV2 extends AbstractConnector {
    private static final Logger LOG = LoggerFactory
	    .getLogger(YoutubeConnectorV2.class);

    protected static final String FEED_UPLOADS = "http://gdata.youtube.com/feeds/api/users/%s/uploads";
    protected static final String FEED_PLAYLISTS = "http://gdata.youtube.com/feeds/api/users/%s/playlists";
    protected static final String FEED_PLAYLIST = "http://gdata.youtube.com/feeds/api/playlists/%s";
    protected static final String ENTRY_USER = "http://gdata.youtube.com/feeds/api/users/%s";
    protected static final String ENTRY_VIDEO = "http://gdata.youtube.com/feeds/api/videos/%s";

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
	    Map<String, Object> parameters) {
	super.initialize(id, model, parameters);

	this.ytConfig = ConfigUtils.fromMap(parameters,
		YoutubeConnectorV2Config.class);

	service = new YouTubeService("YoutubeConnectorV2",
		this.ytConfig.getDeveloperKey());

	try {
	    service.setUserCredentials(this.ytConfig.getEmail(),
		    this.ytConfig.getPassword());
	} catch (com.google.gdata.util.AuthenticationException e) {
	    throw new AuthenticationException(e.getMessage(), e);
	}

	try {
	    userProfile = service.getEntry(
		    new URL(String.format(ENTRY_USER, "default")),
		    UserProfileEntry.class);
	    myId = userProfile.getUsername();
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
	getSite().setHostof(this.uploads);

	uri = RDF2GoUtils.createURI(String.format(FEED_PLAYLISTS, myId));
	this.playlists = new Forum(model, uri, true);
	this.playlists.setId(ID_FORUM_PLAYLISTS);
	this.playlists.setName("Playlist");
	this.playlists.setNumthreads(0);
	this.playlists.setHost(getSite());
	getSite().setHostof(this.playlists);
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
    public UserAccount getLoginUser() {
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
	// TODO Auto-generated method stub
	return super.getThread(id);
    }

    @Override
    public Iterator<Thread> getThreads(Forum forum) {
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
		    URI uri = RDF2GoUtils.createURI(linkEntry.getFeedUrl());

		    if (!Thread.hasInstance(getModel(), uri)) {
			Thread thread = new Thread(getModel(), uri, true);
			thread.setId(getYoutubeID(linkEntry.getId()));

			if (null != linkEntry.getTitle()) {
			    thread.setName(linkEntry.getTitle().getPlainText());
			}

			if (null != linkEntry.getSummary()) {
			    thread.setDescription(linkEntry.getSummary()
				    .getPlainText());
			}

			if (null != linkEntry.getPublished()) {
			    thread.setCreated(DateUtils.formatISO8601(linkEntry
				    .getPublished().getValue()));
			}
			if (null != linkEntry.getUpdated()) {
			    thread.setModified(DateUtils
				    .formatISO8601(linkEntry.getUpdated()
					    .getValue()));
			}

			for (Person person : linkEntry.getAuthors()) {
			    String personUri = person.getUri();
			    thread.addCreator(getUserAccount(personUri
				    .substring(personUri.lastIndexOf('/') + 1)));
			}

			thread.setParent(playlists);
			playlists.setParentof(thread);
			if (playlists.hasNumthreads()) {
			    playlists
				    .setNumthreads(playlists.getNumthreads() + 1);
			} else {
			    playlists.setNumthreads(1);
			}

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

	return result.iterator();
    }

    @Override
    public Post getPost(String id) throws ConnectorException {
	// TODO Auto-generated method stub
	return super.getPost(id);
    }

    @Override
    public Iterator<Post> getPosts(Container container) {
	Preconditions.checkArgument(hasPosts(container));
	return super.getPosts(container);
    }

    @Override
    public Iterator<Post> pollNewPosts(Container container) {
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

		System.out.println(videoFeed.getSelfLink().getHref());
		System.out
			.println("=============================================");
		for (VideoEntry videoEntry : videoFeed.getEntries()) {
		    URI uri = RDF2GoUtils.createURI(String.format(ENTRY_VIDEO,
			    getYoutubeID(videoEntry.getId())));
		    Date created = new Date(videoEntry.getPublished()
			    .getValue());

		    if (created.after(lastItemDate)) {
			if (!Post.hasInstance(getModel(), uri)) {
			    Post post = createPost(container, videoEntry, uri);
			    result.add(post);
			}
		    }

		    System.out.println(videoEntry.getId());
		    System.out.println(videoEntry.getSelfLink().getHref());
		    System.out.println(videoEntry.getTitle().getPlainText());
		    // System.out.println(videoEntry.getSummary().getPlainText());
		    System.out.println(videoEntry.getPublished()
			    .toStringRfc822());
		    System.out
			    .println(videoEntry.getUpdated().toStringRfc822());
		    System.out
			    .println("---------------------------------------------");
		}

		System.out
			.println("=============================================");
		System.out
			.println("=============================================");
	    }

	    if (null != videoFeed.getNextLink()) {
		nextFeedUrl = videoFeed.getNextLink().getHref();
	    }
	} while (null != nextFeedUrl);

	return result.iterator();
    }

    private Post createPost(final Container container,
	    final VideoEntry videoEntry, final URI uri) {
	Post result = new Post(getModel(), uri, true);

	// TODO: fill post data

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
    public boolean canReplyOn(Post parent) {

	if (parent.hasContainer(uploads)) {
	    return !parent.hasReplyof(); // it's a video post -> is no reply to
					 // an other post
	} else {
	    Container container = parent.getContainer();
	    if (container.hasParent(playlists)) {
		return !parent.hasReplyof(); // it's a video post -> is no reply
					     // to an other post
	    }
	}

	return false;
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
}
