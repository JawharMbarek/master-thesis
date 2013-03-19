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
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.Statement;
import org.ontoware.rdf2go.model.node.Resource;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.model.node.Variable;
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
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.exception.FacebookException;
import com.restfb.exception.FacebookNetworkException;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.json.JsonArray;
import com.restfb.json.JsonObject;
import com.restfb.types.FacebookType;
import com.restfb.types.Group;
import com.restfb.types.User;

import de.m0ep.socc.connectors.AbstractConnector;
import de.m0ep.socc.connectors.exceptions.AuthenticationException;
import de.m0ep.socc.connectors.exceptions.ConnectorException;
import de.m0ep.socc.connectors.exceptions.NetworkException;
import de.m0ep.socc.connectors.exceptions.NotFoundException;
import de.m0ep.socc.utils.ConfigUtils;
import de.m0ep.socc.utils.DateUtils;
import de.m0ep.socc.utils.RDF2GoUtils;
import de.m0ep.socc.utils.SIOCUtils;
import de.m0ep.socc.utils.StringUtils;

public class FacebookConnector extends AbstractConnector {
    private static final Logger LOG = LoggerFactory
	    .getLogger(FacebookConnector.class);

    private static final String POST_FIELDS = "id,type,story,message,"
	    + "comments.fields(id,from,message,created_time),"
	    + "description,caption,source,updated_time,created_time,"
	    + "link,name,from";

    private static final String FIELD_ID = "id";
    private static final String FIELD_STORY = "story";
    private static final String FIELD_MESSAGE = "message";
    private static final String FIELD_COMMENTS = "comments";
    private static final String FIELD_DATA = "data";
    private static final String FIELD_DESCRIPTION = "description";
    private static final String FIELD_CAPTION = "caption";
    private static final String FIELD_SOURCE = "source";
    private static final String FIELD_UPDATED_TIME = "updated_time";
    private static final String FIELD_CREATED_TIME = "created_time";
    private static final String FIELD_LINK = "link";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_FROM = "from";

    private static final String CONNECTION_COMMENTS = "comments";
    private static final String CONNECTION_FEED = "feed";
    private static final String SPECIAL_ID_ME = "me";

    private FacebookClient client;
    private String myId;
    private FacebookConnectorConfig fbConfig;

    @Override
    public void initialize(String id, Model model,
	    Map<String, Object> parameters) throws ConnectorException {
	super.initialize(id, model, parameters);

	this.fbConfig = ConfigUtils.fromMap(parameters,
		FacebookConnectorConfig.class);
	this.client = new DefaultFacebookClient(fbConfig.getAccessToken());

	try {
	    this.myId = client.fetchObject(SPECIAL_ID_ME, FacebookType.class,
		    Parameter.with("fields", "id")).getId();
	} catch (FacebookException e) {
	    LOG.error(e.getLocalizedMessage(), e);
	    throw mapToConnectorException(e);
	} catch (Throwable t) {
	    LOG.error(t.getLocalizedMessage(), t);
	    throw new ConnectorException("unknown error", t);
	}

	// add static forums
	URI uri = RDF2GoUtils
		.createURI(getURL() + myId + "/" + CONNECTION_FEED);
	if (!Forum.hasInstance(getModel(), uri)) {
	    Forum wall = new Forum(getModel(), uri, true);
	    wall.setId(myId);
	    wall.setName(getLoginUser().getAccountname() + "'s Wall");
	    wall.setHost(getSite());
	    getSite().addHostof(wall);
	}
    }

    public String getURL() {
	return "http://www.facebook.com/";
    }

    @Override
    public Map<String, Object> getConfiguration() {
	return ConfigUtils.toMap(fbConfig);
    }

    public Site getSite() throws ConnectorException {
	URI uri = RDF2GoUtils.createURI(getURL());

	if (!Site.hasInstance(getModel(), uri)) {
	    Site result = new Site(getModel(), uri, true);
	    result.setName("Facebook");
	    return result;
	} else {
	    return Site.getInstance(getModel(), uri);
	}
    }

    public UserAccount getLoginUser() throws ConnectorException {
	return getUserAccount(myId);
    }

    public UserAccount getUserAccount(final String id)
	    throws ConnectorException {
	URI uri = (URI) new URIImpl(getURL() + id);

	if (!UserAccount.hasInstance(getModel(), uri)) {
	    User user = null;
	    try {
		user = client.fetchObject(id, User.class);
	    } catch (FacebookException e) {
		LOG.error(e.getLocalizedMessage(), e);
		throw mapToConnectorException(e);
	    } catch (Throwable t) {
		LOG.error(t.getLocalizedMessage(), t);
		throw new ConnectorException("unknown error", t);
	    }

	    UserAccount result = new UserAccount(getModel(), uri, true);

	    // SIOC statements
	    result.setId(user.getId());
	    result.setIsPartOf(getSite());
	    result.setAccountservicehomepage(RDF2GoUtils.createURI(getURL()));

	    if (null != user.getEmail() && !user.getEmail().isEmpty()) {
		result.setEmail(RDF2GoUtils.createMailtoURI(user.getEmail()));
		result.setEmailsha1(RDFTool.sha1sum(user.getEmail()));
	    }

	    if (null != user.getUsername() && !user.getUsername().isEmpty()) {
		result.setAccountname(user.getUsername());
	    }

	    if (null != user.getName() && !user.getName().isEmpty()) {
		result.setName(user.getName());
	    }

	    if (null != user.getUpdatedTime()) {
		result.setModified(DateUtils.formatISO8601(user
			.getUpdatedTime()));
	    }

	    return result;
	} else {
	    return UserAccount.getInstance(getModel(), uri);
	}
    }

    public List<Forum> getForums() throws ConnectorException {
	List<Forum> result = new ArrayList<Forum>();

	// add all known forums
	ClosableIterator<Statement> stmtIter = getModel().findStatements(
		Variable.ANY, SIOC.has_host, getSite());
	while (stmtIter.hasNext()) {
	    Statement statement = (Statement) stmtIter.next();
	    Resource subject = statement.getSubject();

	    if (getModel().contains(subject, RDF.type, SIOC.Forum)) {
		result.add(Forum.getInstance(getModel(), subject));
	    }
	}
	stmtIter.close();

	Connection<Group> groupsConnections = null;
	try {
	    groupsConnections = client.fetchConnection("me/groups",
		    Group.class, Parameter.with("fields",
			    "name,id,description,updated_time"));
	} catch (FacebookException e) {
	    LOG.error(e.getLocalizedMessage(), e);
	    throw mapToConnectorException(e);
	} catch (Throwable t) {
	    LOG.error(t.getLocalizedMessage(), t);
	    throw new ConnectorException("unknown error", t);
	}

	for (List<Group> myGroups : groupsConnections) {
	    for (Group group : myGroups) {
		URI uri = RDF2GoUtils.createURI(getURL() + group.getId() + "/"
			+ CONNECTION_FEED);

		if (!Forum.hasInstance(getModel(), uri)) {
		    Forum forum = new Forum(getModel(), uri, true);
		    forum.setId(group.getId());
		    forum.setName(group.getName() + "'s Wall");
		    forum.setHost(getSite());
		    getSite().addHostof(forum);

		    if (null != group.getDescription()) {
			forum.setDescription(group.getDescription());
		    }

		    if (null != group.getUpdatedTime()) {
			forum.setModified(DateUtils.formatISO8601(group
				.getUpdatedTime()));
		    }

		    LOG.debug("Add new forum " + uri.toString());
		    result.add(forum);
		}
	    }
	}

	return result;
    }

    @Override
    public List<Post> getPosts(Container container)
	    throws ConnectorException {
	Preconditions.checkNotNull(container, "container can not be null");
	Preconditions.checkArgument(hasPosts(container), "container "
		+ container + " has no post on this site");

	List<Post> result = new ArrayList<Post>();
	ClosableIterator<Statement> stmtsIter = getModel().findStatements(
		Variable.ANY, SIOC.has_container, container);

	while (stmtsIter.hasNext()) {
	    Statement statement = (Statement) stmtsIter.next();

	    if (Post.hasInstance(getModel(), statement.getSubject())) {
		result.add(Post.getInstance(getModel(), statement.getSubject()));
	    }
	}
	stmtsIter.close();

	return result;
    }

    @Override
    public boolean canPublishOn(Container container) {
	if (getModel().contains(container, RDF.type, SIOC.Forum)) {
	    Forum forum = Forum
		    .getInstance(getModel(), container.getResource());
	    try {
		return forum.hasHost(getSite())
		    && hasConnection(container.getId(), CONNECTION_FEED);
	    } catch (ConnectorException e) {
		LOG.error(e.getMessage(), e);
		return false;
	    }
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

	    try {
		return canPublishOn(container)
		    && Pattern.matches("^\\d+_\\d+$", id)
		    && hasConnection(parent.getId(), CONNECTION_COMMENTS);
	    } catch (ConnectorException e) {
		LOG.error(e.getMessage(), e);
		return false;
	    }
	}

	return false;
    }

    @Override
    public boolean hasPosts(Container container) {
	return canPublishOn(container);
    }

    public boolean publishPost(final Post post, final Container container)
	    throws ConnectorException {
	Preconditions.checkNotNull(post, "post can't be null");
	Preconditions.checkNotNull(container, "container can't be null");
	Preconditions.checkArgument(post.hasContent());
	Preconditions.checkArgument(canPublishOn(container));

	List<Parameter> params = new ArrayList<Parameter>();

	if (post.hasContent()) {
	    params.add(Parameter.with("message", post.getContent()));
	}

	if (post.hasAttachment()) {
	    params.add(Parameter.with("link", post.getAttachment()));
	}

	if (post.hasTitle()) {
	    params.add(Parameter.with("caption", post.getTitle()));
	} else if (post.hasSubject()) {
	    params.add(Parameter.with("caption", post.getSubject()));
	}

	if (post.hasDescription()) {
	    params.add(Parameter.with("description", post.getDescription()));
	}

	if (post.hasName()) {
	    params.add(Parameter.with("name", post.getName()));
	}

	FacebookType result = null;
	try {
	    result = client.publish(container.getId() + "/" + CONNECTION_FEED,
		    FacebookType.class, params.toArray(new Parameter[0]));
	} catch (FacebookException e) {
	    LOG.error(e.getLocalizedMessage(), e);
	    throw mapToConnectorException(e);
	} catch (Throwable t) {
	    LOG.error(t.getLocalizedMessage(), t);
	    throw new ConnectorException("unknown error", t);
	}

	// TODO Add Post to model

	return null != result && null != result.getId()
		&& !result.getId().isEmpty();
    };

    public boolean replyPost(final Post post, final Post parent)
	    throws ConnectorException {
	Preconditions.checkNotNull(post, "post can't be null");
	Preconditions.checkNotNull(parent, "parent can't be null");
	Preconditions.checkArgument(post.hasContent(), "post has no content");
	Preconditions.checkArgument(canReplyOn(parent),
		"can't reply on the parent post");

	FacebookType result = null;
	try {
	    result = client.publish(parent.getId() + "/" + CONNECTION_COMMENTS,
		    FacebookType.class,
		    Parameter.with("message", post.getContent()));
	} catch (FacebookException e) {
	    LOG.error(e.getLocalizedMessage(), e);
	    throw mapToConnectorException(e);
	} catch (Throwable t) {
	    LOG.error(t.getLocalizedMessage(), t);
	    throw new ConnectorException("unknown error", t);
	}

	// TODO Add Post to model

	return null != result && null != result.getId()
		&& !result.getId().isEmpty();
    };

    public List<Post> pollNewPosts(Container container)
	    throws ConnectorException {
	LOG.debug("pollNewPosts");
	List<Post> result = new ArrayList<Post>();
	Date lastItemDate = new Date(0);
	int numLoadedPosts = fbConfig.getMaxNewPostsOnPoll();

	if (container.hasLastitemdate()) {
	    try {
		lastItemDate = RDFTool.string2DateTime(container
			.getLastitemdate());
	    } catch (ParseException e) {
		LOG.warn(e.getLocalizedMessage());
		lastItemDate = new Date(0);
	    }
	}

	Connection<JsonObject> feed = null;
	try {
	    feed = client.fetchConnection(
		    container.getId() + "/" + CONNECTION_FEED,
		    JsonObject.class,
		    Parameter.with("since", lastItemDate.getTime() / 1000L),
		    Parameter.with("fields", POST_FIELDS),
		    Parameter.with("limit",
			    Math.min(20, fbConfig.getMaxNewPostsOnPoll())));
	} catch (FacebookException e) {
	    LOG.error(e.getLocalizedMessage(), e);
	    throw mapToConnectorException(e);
	} catch (Throwable t) {
	    LOG.error(t.getLocalizedMessage(), t);
	    throw new ConnectorException("unknown error", t);
	}

	if (null != feed) {
	    for (List<JsonObject> posts : feed) {
		for (JsonObject obj : posts) {
		    URI uri = RDF2GoUtils.createURI(getURL()
			    + obj.getString(FIELD_ID));
		    Date created = com.restfb.util.DateUtils
			    .toDateFromLongFormat(obj
				    .getString(FIELD_CREATED_TIME));

		    if (created.after(lastItemDate)) {
			if (!Post.hasInstance(getModel(), uri)) {
			    LOG.debug("Add new post " + uri.toString());
			    result.add(parsePost(container, obj, uri));
			}
		    }

		    if (Post.hasInstance(getModel(), uri)
			    && obj.has(FIELD_COMMENTS)) {
			JsonObject comments = obj.getJsonObject(FIELD_COMMENTS);

			if (comments.has(FIELD_DATA)) {
			    JsonArray data = comments.getJsonArray(FIELD_DATA);

			    result.addAll(pollNewReplies(
				    Post.getInstance(getModel(), uri), data));
			}
		    }

		    if (0 == --numLoadedPosts) {
			return result;
		    }
		}
	    }
	}

	return result;
    }

    private Post parsePost(final Container container, final JsonObject obj,
	    final URI uri) throws ConnectorException {
	Post result = new Post(getModel(), uri, true);
	result.setId(obj.getString(FIELD_ID));

	if (obj.has(FIELD_FROM)) {
	    result.setCreator(getUserAccount(obj.getJsonObject(FIELD_FROM)
		    .getString(FIELD_ID)));
	}

	String content = "";
	if (obj.has(FIELD_STORY)) {
	    content = obj.getString(FIELD_STORY);
	} else if (obj.has(FIELD_MESSAGE)) {
	    content = obj.getString(FIELD_MESSAGE);
	}

	result.setContent(StringUtils.stripHTML(content));
	result.setContentEncoded(RDF2GoUtils.createCDATASection(content));

	if (obj.has(FIELD_NAME)) {
	    result.setName(obj.getString(FIELD_NAME));
	}

	if (obj.has(FIELD_CAPTION)) {
	    result.setTitle(FIELD_CAPTION);
	}

	if (obj.has(FIELD_DESCRIPTION)) {
	    result.setDescription(obj.getString(FIELD_DESCRIPTION));
	}

	if (obj.has(FIELD_LINK)) {
	    result.setAttachment(RDF2GoUtils.createURI(obj
		    .getString(FIELD_LINK)));
	} else if (obj.has(FIELD_SOURCE)) {
	    result.setAttachment(RDF2GoUtils.createURI(obj
		    .getString(FIELD_SOURCE)));
	}

	if (obj.has(FIELD_CREATED_TIME)) {
	    Date date = com.restfb.util.DateUtils.toDateFromLongFormat(obj
		    .getString(FIELD_CREATED_TIME));
	    result.setCreated(DateUtils.formatISO8601(date));
	}

	if (obj.has(FIELD_UPDATED_TIME)) {
	    Date date = com.restfb.util.DateUtils.toDateFromLongFormat(obj
		    .getString(FIELD_UPDATED_TIME));
	    result.setModified(DateUtils.formatISO8601(date));
	}

	if (null != container) {
	    result.setContainer(container);
	    container.addContainerof(result);
	    SIOCUtils.updateLastItemDate(container, result);
	}

	return result;
    }

    private List<Post> pollNewReplies(final Post parentPost,
	    final JsonArray commentsArray) throws ConnectorException {
	List<Post> result = new ArrayList<Post>();
	Date lastReplyDate = new Date(0);

	if (parentPost.hasLastreplydate()) {
	    try {
		lastReplyDate = RDFTool.string2DateTime(parentPost
			.getLastreplydate());
	    } catch (ParseException e1) {
		lastReplyDate = new Date(0);
	    }
	}

	for (int i = 0; i < commentsArray.length(); i++) {
	    JsonObject obj = commentsArray.getJsonObject(i);
	    Date created = com.restfb.util.DateUtils.toDateFromLongFormat(obj
		    .getString(FIELD_CREATED_TIME));

	    if (created.after(lastReplyDate)) {
		String id = obj.getString(FIELD_ID);
		URI uri = RDF2GoUtils.createURI(getURL() + id);

		if (!Post.hasInstance(getModel(), uri)) {
		    result.add(parseComment(parentPost, obj, uri));
		}
	    }
	}

	return result;
    }

    private Post parseComment(final Post parentPost, final JsonObject obj,
	    final URI uri) throws ConnectorException {
	Post result = new Post(getModel(), uri, true);
	result.setId(obj.getString(FIELD_ID));

	if (obj.has(FIELD_FROM)) {
	    result.setCreator(getUserAccount(obj.getJsonObject(FIELD_FROM)
		    .getString(FIELD_ID)));
	}

	String content = "";
	if (obj.has(FIELD_MESSAGE)) {
	    content = obj.getString(FIELD_MESSAGE);
	}

	result.setContent(StringUtils.stripHTML(content));
	result.setContentEncoded(RDF2GoUtils.createCDATASection(content));

	if (obj.has(FIELD_CREATED_TIME)) {
	    Date date = com.restfb.util.DateUtils.toDateFromLongFormat(obj
		    .getString(FIELD_CREATED_TIME));
	    result.setCreated(DateUtils.formatISO8601(date));
	}

	if (parentPost.hasContainer()) {
	    Container container = parentPost.getContainer();
	    result.setContainer(container);
	    container.addContainerof(result);
	    SIOCUtils.updateLastItemDate(container, result);
	}

	if (null != parentPost) {
	    result.setReplyof(parentPost);
	    parentPost.addReply(result);
	    SIOCUtils.updateLastReplyDate(parentPost, result);
	}

	return result;
    }

    /* package */boolean hasConnection(final String id, final String connection)
	    throws ConnectorException {

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

	} catch (FacebookException e) {
	    throw mapToConnectorException(e);
	} catch (Throwable t) {
	    throw new ConnectorException("unknown error", t);
	}

	return false;
    }

    private ConnectorException mapToConnectorException(FacebookException e) {
	if (e instanceof FacebookOAuthException) {
	    FacebookOAuthException fae = (FacebookOAuthException) e;

	    // error codes:
	    // http://www.fb-developers.info/tech/fb_dev/faq/general/gen_10.html
	    switch (fae.getErrorCode()) {
	    case 101: // Invalid API key
	    case 190: // Invalid Access Token
	    case 400: // Invalid email address
	    case 401: // Invalid username or password
	    case 402: // Invalid application auth sig
	    case 403: // Invalid timestamp for authentication
	    case 450: // Session key specified has passed its expiration time
	    case 451: // Session key specified cannot be used to call this
		      // method
	    case 452: // Session key invalid. This could be because the session
		      // key has an incorrect format, or because the user has
	    case 453: // revoked this session
	    case 454: // A session key is required for calling this method
	    case 455: // A session key must be specified when request is signed
		      // with a session secret
		return new AuthenticationException(fae.getErrorMessage(), fae);
	    case 803: // Specified object cannot be found
		return new NotFoundException("Not found", fae);
	    }
	} else if (e instanceof FacebookNetworkException) {
	    FacebookNetworkException fne = (FacebookNetworkException) e;
	    return new NetworkException("Network error: "
		    + fne.getHttpStatusCode() + " " + fne.getMessage(), fne);
	}

	return new ConnectorException(e.getMessage(), e);
    }
}
