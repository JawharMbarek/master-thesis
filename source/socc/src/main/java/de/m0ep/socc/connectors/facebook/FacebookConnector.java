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
import java.util.Map;
import java.util.NoSuchElementException;
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
import com.restfb.Parameter;
import com.restfb.exception.FacebookException;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.json.JsonObject;
import com.restfb.types.FacebookType;
import com.restfb.types.Group;
import com.restfb.types.User;

import de.m0ep.socc.connectors.AbstractConnector;
import de.m0ep.socc.utils.ConfigUtils;
import de.m0ep.socc.utils.RDF2GoUtils;

public class FacebookConnector extends AbstractConnector {
    private static final Logger LOG = LoggerFactory
	    .getLogger(FacebookConnector.class);

    private static final String CONNECTION_COMMENTS = "comments";
    private static final String CONNECTION_FEED = "feed";
    private static final String SPECIAL_ID_ME = "me";

    private FacebookClient client;
    private String myId;
    private FacebookConnectorConfig fbConfig;

    @Override
    public void initialize(String id, Model model,
	    Map<String, Object> parameters) {
	super.initialize(id, model, parameters);

	this.fbConfig = ConfigUtils.fromMap(parameters,
		FacebookConnectorConfig.class);
	this.client = new DefaultFacebookClient(fbConfig.getAccessToken());
	this.myId = client.fetchObject(SPECIAL_ID_ME, FacebookType.class,
		Parameter.with("fields", "id")).getId();
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

    public UserAccount getLoginUser() {
	return getUserAccount(myId);
    }

    public UserAccount getUserAccount(final String id) {
	URI uri = (URI) new URIImpl(getURL() + id);

	if (!UserAccount.hasInstance(getModel(), uri)) {
	    User user = client.fetchObject(id, User.class);
	    UserAccount result = new UserAccount(getModel(), uri, true);

	    // SIOC statements
	    result.setId(user.getId());
	    result.setIsPartOf(getSite());
	    result.setAccountservicehomepage(RDF2GoUtils.createURI(getURL()));

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

	    return result;
	} else {
	    return UserAccount.getInstance(getModel(), uri);
	}
    }

    public Iterator<Forum> getForums() {
	List<Forum> result = new ArrayList<Forum>();

	URI uri = RDF2GoUtils
		.createURI(getURL() + myId + "/" + CONNECTION_FEED);
	if (!Forum.hasInstance(getModel(), uri)) {
	    Forum wall = new Forum(getModel(), uri, true);
	    wall.setId(myId);
	    wall.setName(getLoginUser().getAccountname() + "'s Wall");
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
	    feed = client.fetchConnection(container.getId() + "/"
		    + CONNECTION_FEED, JsonObject.class);
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
		    && hasConnection(container.getId(), CONNECTION_FEED);
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
	    Container container = parent.getContainer();
	    String id = parent.getId();

	    return canPublishOn(container)
		    && Pattern.matches("^\\d+_\\d+$", id)
		    && hasConnection(parent.getId(), CONNECTION_COMMENTS);
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
	    params.add(Parameter.with("message", post.getContent()));

	if (post.hasAttachment())
	    params.add(Parameter.with("link", post.getAttachment()));

	if (post.hasTitle())
	    params.add(Parameter.with("caption", post.getTitle()));

	if (post.hasDescription())
	    params.add(Parameter.with("description", post.getDescription()));

	if (post.hasName())
	    params.add(Parameter.with("name", post.getName()));

	FacebookType result;
	try {
	    result = client.publish(container.getId() + "/" + CONNECTION_FEED,
		    FacebookType.class, params.toArray(new Parameter[0]));
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
	    result = client.publish(parent.getId() + "/" + CONNECTION_COMMENTS,
		    FacebookType.class,
		    Parameter.with("message", post.getContent()));
	} catch (Throwable e) {
	    Throwables.propagateIfInstanceOf(e, FacebookOAuthException.class);
	    return false;
	}

	// TODO Add Post to model

	return null != result && null != result.getId()
		&& !result.getId().isEmpty();
    };

    public java.util.Iterator<Post> pollNewPosts(Container container) {
	List<Post> result = new ArrayList<Post>();

	Iterator<Forum> forums = getForums();

	while (forums.hasNext()) {
	    Forum forum = forums.next();
	    long unixtime = 0;

	    if (forum.hasLastitemdate()) {
		try {
		    String lastItemDate = forum.getLastitemdate();
		    Date date = RDFTool.string2DateTime(lastItemDate);
		    unixtime = date.getTime() / 1000L;
		} catch (ParseException e) {
		    // use 0 as since parameter
		}
	    }

	    Connection<JsonObject> feed = null;
	    try {
		feed = client.fetchConnection(forum.getId() + "/"
			+ CONNECTION_FEED, JsonObject.class,
			Parameter.with("since", unixtime));
	    } catch (FacebookException e) {
		LOG.debug("skip container " + forum.getId(), e);
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
				    forum));
			}

		    } catch (Exception e) {
			LOG.debug("skip message " + post.getString("id"), e);
		    }
		}
	    }
	}

	return Collections.unmodifiableList(result).iterator();
    }

    /* package */FacebookClient getFacebookClient() {
	return client;
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
