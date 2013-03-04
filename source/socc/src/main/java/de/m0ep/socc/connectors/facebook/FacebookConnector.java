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

package de.m0ep.socc.connectors.facebook;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.model.node.impl.URIImpl;
import org.ontoware.rdf2go.util.RDFTool;
import org.ontoware.rdf2go.vocabulary.RDF;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.SIOC;
import org.rdfs.sioc.Site;
import org.rdfs.sioc.UserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.Parameter;
import com.restfb.exception.FacebookException;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.json.JsonObject;
import com.restfb.types.FacebookType;
import com.restfb.types.Group;
import com.restfb.types.User;

import de.m0ep.socc.connectors.AbstractConnector;
import de.m0ep.socc.utils.RDF2GoUtils;

public class FacebookConnector extends AbstractConnector {
    private static final Logger LOG = LoggerFactory
	    .getLogger(FacebookConnector.class);

    public static final String CONFIG_ACCESS_TOKEN = "access_token";
    public static final String CONFIG_CLIENT_ID = "client_id";
    public static final String CONFIG_CLIENT_SECRET = "client_secret";

    private static final String CONNECTION_COMMENTS = "comments";
    private static final String CONNECTION_FEED = "feed";

    private FacebookClient client;
    private String myId;
    private Properties config;

    List<Container> postContainer;

    public FacebookConnector(String id, Model model, Properties config) {
	super(id, model, config);

	this.config = config;
	this.client = new DefaultFacebookClient(
		config.getProperty(CONFIG_ACCESS_TOKEN));

	FacebookType me = client.fetchObject("me", FacebookType.class,
		Parameter.with("fields", "id"));
	this.myId = me.getId();
	this.postContainer = new ArrayList<Container>();
    }

    public String getURL() {
	return "http://www.facebook.com/";
    }

    public Site getSite() {
	URI uri = RDF2GoUtils.createURI(getURL());

	if (!Site.hasInstance(getModel(), uri)) {
	    Site result = new Site(getModel(), uri, true);
	    result.setName("Facebook");
	    return result;
	} else {
	    return Site.getInstance(getModel(), uri);
	}
    }

    public UserAccount getUser() {
	return getUser(myId);
    }

    public Iterator<Forum> getForums() {
	List<Forum> result = new ArrayList<Forum>();

	URI uri = RDF2GoUtils
		.createURI(getURL() + myId + "/" + CONNECTION_FEED);
	if (!Forum.hasInstance(getModel(), uri)) {
	    Forum wall = new Forum(getModel(), uri, true);
	    wall.setId(myId);
	    wall.setName(getUser().getAllAccountname_as().firstValue()
		    + "'s Wall");
	    wall.setHost(getSite());
	    getSite().addHostof(wall);

	    result.add(wall);
	} else {
	    result.add(Forum.getInstance(getModel(), uri));
	}

	Connection<Group> groupsConnections = client.fetchConnection(
		"me/groups", Group.class,
		Parameter.with("fields", "name,id,description,updated_time"));

	for (List<Group> myGroups : groupsConnections) {
	    for (Group group : myGroups) {
		uri = RDF2GoUtils.createURI(getURL() + group.getId() + "/"
			+ CONNECTION_FEED);

		if (!Forum.hasInstance(getModel(), uri)) {
		    Forum forum = new Forum(getModel(), uri, true);
		    forum.setId(group.getId());
		    forum.setName(group.getName() + "'s Wall");
		    forum.setHost(getSite());
		    getSite().addHostof(forum);

		    if (null != group.getDescription()
			    && !group.getDescription().isEmpty())
			forum.setDescription(group.getDescription());

		    if (null != group.getUpdatedTime())
			forum.setModified(RDFTool.dateTime2String(group
				.getUpdatedTime()));

		    result.add(forum);
		} else {
		    result.add(Forum.getInstance(getModel(), uri));
		}
	    }
	}

	return Collections.unmodifiableList(result).iterator();
    }

    @Override
    public Iterator<Post> getPosts(Container container) {
	if (!canPublishOn(container))
	    return super.getPosts(container);

	Connection<JsonObject> feed;
	try {
	    feed = client.fetchConnection(container.getAllId_as().firstValue()
		    + "/" + CONNECTION_FEED, JsonObject.class);
	} catch (Exception e) {
	    LOG.warn(e.getMessage(), e);
	    return super.getPosts(container);
	}

	return new PostIterator(feed.iterator(), container);
    }

    @Override
    public boolean canPublishOn(Container container) {
	if (getModel().contains(container, RDF.type, SIOC.Forum)) {
	    Forum forum = Forum
		    .getInstance(getModel(), container.getResource());
	    return forum.hasHost(getSite())
		    && hasConnection(container.getAllId_as().firstValue(),
			    CONNECTION_FEED);
	}

	return false;
    }

    @Override
    public boolean canReplyOn(Post parent) {
	/**
	 * We can reply on this post if: - this post is not already a reply - we
	 * can publish on the container of this post - the post id is like
	 * "[0-9]+_[0-9]+" Post has a "comments" connection
	 */
	if (!parent.hasReplyof() && parent.hasContainer()) {
	    Container container = parent.getAllContainer_as().firstValue();
	    String id = parent.getAllId_as().firstValue();

	    return canPublishOn(container)
		    && Pattern.matches("^\\d+_\\d+$", id)
		    && hasConnection(parent.getAllId_as().firstValue(),
			    CONNECTION_COMMENTS);
	}

	return false;
    }

    @Override
    public boolean hasPosts(Container container) {
	return canPublishOn(container);
    }

    public boolean publishPost(final Post post, final Container container) {
	Preconditions.checkNotNull(post, "post can't be null");
	Preconditions.checkNotNull(container, "container can't be null");
	Preconditions.checkArgument(post.hasContent());
	Preconditions.checkArgument(canPublishOn(container));

	List<Parameter> params = new ArrayList<Parameter>();

	if (post.hasContent())
	    params.add(Parameter.with("message", post.getAllContent_as()
		    .firstValue()));

	if (post.hasAttachment())
	    params.add(Parameter.with("link", post.getAllAttachment_as()
		    .firstValue()));

	if (post.hasTitle())
	    params.add(Parameter.with("caption", post.getAllTitle_as()
		    .firstValue()));

	if (post.hasDescription())
	    params.add(Parameter.with("description", post
		    .getAllDescription_as().firstValue()));

	if (post.hasName())
	    params.add(Parameter
		    .with("name", post.getAllName_as().firstValue()));

	FacebookType result;
	try {
	    result = client.publish(container.getAllId_as().firstValue() + "/"
		    + CONNECTION_FEED, FacebookType.class,
		    params.toArray(new Parameter[0]));
	} catch (Throwable e) {
	    Throwables.propagateIfInstanceOf(e, FacebookOAuthException.class);
	    return false;
	}

	// TODO Add Post to model

	return null != result && null != result.getId()
		&& !result.getId().isEmpty();
    };

    public boolean replyPost(final Post post, final Post parent) {
	Preconditions.checkNotNull(post, "post can't be null");
	Preconditions.checkNotNull(parent, "parent can't be null");
	Preconditions.checkArgument(post.hasContent(), "post has no content");
	Preconditions.checkArgument(canReplyOn(parent),
		"can't reply on the parent post");

	FacebookType result;
	try {
	    result = client.publish(parent.getAllId_as().firstValue() + "/"
		    + CONNECTION_COMMENTS, FacebookType.class, Parameter.with(
		    "message", post.getAllContent_as().firstValue()));
	} catch (Throwable e) {
	    Throwables.propagateIfInstanceOf(e, FacebookOAuthException.class);
	    return false;
	}

	// TODO Add Post to model

	return null != result && null != result.getId()
		&& !result.getId().isEmpty();
    };

    public java.util.Iterator<Post> pollPosts() {
	List<Post> result = new ArrayList<Post>();

	Iterator<Forum> forums = getForums();

	while (forums.hasNext()) {
	    Forum container = forums.next();
	    long unixtime = 0;

	    if (container.hasLastitemdate()) {
		try {
		    String lastItemDate = container.getLastitemdate();
		    Date date = RDFTool.string2DateTime(lastItemDate);
		    unixtime = date.getTime() / 1000L;
		} catch (ParseException e) {
		    // use 0 as since parameter
		}
	    }

	    Connection<JsonObject> feed = null;
	    try {
		feed = client.fetchConnection(container.getId() + "/"
			+ CONNECTION_FEED, JsonObject.class,
			Parameter.with("since", unixtime));
	    } catch (FacebookException e) {
		// skip container
		continue;
	    }

	    for (List<JsonObject> posts : feed) {
		for (JsonObject post : posts) {
		    try {
			if (post.has("created_time")) {
			    Date date = RDFTool.string2DateTime(post
				    .getString("created_time"));

			    if (unixtime >= date.getTime() / 1000L)
				return Collections.unmodifiableList(result)
					.iterator();

			    result.add(FacebookPostParser.parse(this, post,
				    container));
			}

		    } catch (Exception e) {
			// skip post
		    }
		}
	    }
	}

	return Collections.unmodifiableList(result).iterator();
    }

    /* package */FacebookClient getFacebookClient() {
	return client;
    }

    /* package */UserAccount getUser(final String id) {
	URI uri = (URI) new URIImpl(getURL() + id);

	if (!UserAccount.hasInstance(getModel(), uri)) {
	    User user = client.fetchObject(id, User.class);
	    UserAccount result = new UserAccount(getModel(), uri, true);

	    // SIOC statements
	    result.setId(user.getId());
	    result.setIsPartOf(getSite());

	    if (null != user.getEmail() && !user.getEmail().isEmpty()) {
		result.setEmail(RDF2GoUtils.createMailtoURI(user.getEmail()));
		result.setEmailsha1(DigestUtils.sha1Hex(user.getEmail()));
	    }

	    if (null != user.getUsername() && !user.getUsername().isEmpty())
		result.setAccountname(user.getUsername());

	    if (null != user.getName() && !user.getName().isEmpty())
		result.setName(user.getName());

	    if (null != user.getUpdatedTime())
		result.setModified(RDFTool.dateTime2String(user
			.getUpdatedTime()));

	    if (null != user.getLink())
		result.setAccountservicehomepage(RDF2GoUtils.createURI(user
			.getLink()));

	    return result;
	} else {
	    return UserAccount.getInstance(getModel(), uri);
	}
    }

    /* package */void tryToExtendAccessToken() {
	AccessToken token = client.obtainExtendedAccessToken(
		config.getProperty(CONFIG_CLIENT_ID),
		config.getProperty(CONFIG_CLIENT_SECRET),
		config.getProperty(CONFIG_ACCESS_TOKEN));

	config.setProperty(CONFIG_ACCESS_TOKEN, token.getAccessToken());
	client = new DefaultFacebookClient(token.getAccessToken());
	// TODO does this really work???
    }

    /* package */boolean hasConnection(final String id, final String connection) {

	try {
	    JsonObject obj = client.fetchObject(id, JsonObject.class,
		    Parameter.with("fields", "id"),
		    Parameter.with("metadata", "1"));

	    if (null != obj && obj.has("metadata")) {
		JsonObject metadata = obj.getJsonObject("metadata");

		if (metadata.has("connections")) {
		    JsonObject connections = metadata
			    .getJsonObject("connections");

		    return connections.has(connection);
		}
	    }

	} catch (Throwable t) {
	    LOG.warn("something not right...", t);
	}

	return false;
    }

    private class PostIterator implements Iterator<Post> {
	private final Iterator<List<JsonObject>> feed;
	private final Container container;
	private Iterator<JsonObject> page;

	public PostIterator(final Iterator<List<JsonObject>> feed,
		final Container container) {
	    this.feed = feed;
	    this.page = feed.next().iterator();
	    this.container = container;
	}

	public boolean hasNext() {
	    return null != feed && null != page
		    && (feed.hasNext() || page.hasNext());
	}

	public Post next() {
	    if (!hasNext())
		throw new NoSuchElementException("nothing here");
	    // page is empty, fetch next
	    if (feed.hasNext() && !page.hasNext()) {
		page = feed.next().iterator();
	    }

	    if (page.hasNext()) {
		JsonObject next = page.next();
		Post result = FacebookPostParser.parse(FacebookConnector.this,
			next, container);
		return result;
	    }

	    return null;
	}

	public void remove() {
	    throw new UnsupportedOperationException("remove is not supported");
	}
    }
}
