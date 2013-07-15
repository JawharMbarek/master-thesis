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
import java.util.regex.Pattern;

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.ontoware.rdf2go.model.Statement;
import org.ontoware.rdf2go.model.node.Resource;
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
import org.rdfs.sioc.UserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Objects;
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

import de.m0ep.sioc.service.auth.AccessToken;
import de.m0ep.sioc.service.auth.Authentication;
import de.m0ep.sioc.service.auth.ClientId;
import de.m0ep.sioc.service.auth.ClientSecret;
import de.m0ep.sioc.service.auth.Credential;
import de.m0ep.sioc.service.auth.Service;
import de.m0ep.socc.AbstractConnector;
import de.m0ep.socc.ISOCCContext;
import de.m0ep.socc.exceptions.AuthenticationException;
import de.m0ep.socc.exceptions.ConnectorException;
import de.m0ep.socc.exceptions.NetworkException;
import de.m0ep.socc.exceptions.NotFoundException;
import de.m0ep.socc.utils.RDF2GoUtils;

/**
 * A SOCC Connector for Facebook using RestFb to access Facebooks GraphAPI.
 * 
 * @author Florian Müller
 * 
 */
public class FacebookConnector extends AbstractConnector {
    private static final Logger LOG = LoggerFactory
	    .getLogger(FacebookConnector.class);

    private static final String POST_FIELDS = "id,type,story,message,"
	    + "comments.fields(id,from,message,created_time),"
	    + "description,caption,source,updated_time,created_time,"
	    + "link,name,from";

    private FacebookClient fbClient;
    private String myId;

    private ClientId clientId;
    private ClientSecret clientSecret;
    private AccessToken accessToken;

    private URI endpoint = Builder.createURI("http://facebook.com");

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

	this.clientId = null;
	this.clientSecret = null;
	while (serviceCredentialsIter.hasNext()) {
	    Credential credential = (Credential) serviceCredentialsIter.next();
	    URI type = RDF2GoUtils.getType(
		    credential.getModel(),
		    credential.asResource());

	    if (type.equals(ClientId.RDFS_CLASS)) {
		this.clientId = ClientId.getInstance(
			credential.getModel(),
			credential.asResource());
	    } else if (type.equals(ClientSecret.RDFS_CLASS)) {
		this.clientSecret = ClientSecret.getInstance(
			credential.getModel(),
			credential.asResource());
	    }
	}
	serviceCredentialsIter.close();

	Preconditions.checkArgument(
		null != this.clientId && clientId.hasValue(),
		"No ClientId credential found in service");

	Preconditions.checkArgument(
		null != this.clientSecret && clientSecret.hasValue(),
		"No ClientSecret credential found in service");

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
	ClosableIterator<Credential> credentials = userAuth.getAllCredential();

	this.accessToken = null;
	while (credentials.hasNext()) {
	    Credential credential = (Credential) credentials.next();

	    URI type = RDF2GoUtils.getType(
		    credential.getModel(),
		    credential);

	    if (type.equals(de.m0ep.sioc.service.auth.AccessToken.RDFS_CLASS)) {
		this.accessToken = de.m0ep.sioc.service.auth.AccessToken
			.getInstance(
				credential.getModel(),
				credential.getResource());
	    }
	}

	Preconditions.checkArgument(
		null != this.accessToken && accessToken.hasValue(),
		"No AccessToken credential found in userAccount");
    }

    @Override
    public void connect() throws ConnectorException {
	setConnected(false);

	this.fbClient = new DefaultFacebookClient(accessToken.getValue());

	try {
	    com.restfb.FacebookClient.AccessToken extendedToken = this.fbClient
		    .obtainExtendedAccessToken(
			    clientId.getValue(),
			    clientSecret.getValue(),
			    accessToken.getValue());

	    accessToken.setValue(extendedToken.getAccessToken());
	} catch (Exception e) {
	    LOG.error("failed to obtain an extended accesstoken", e);
	}

	try {
	    this.myId = fbClient.fetchObject(
		    FacebookConstants.ID_ME,
		    FacebookType.class,
		    Parameter.with(FacebookConstants.PARAM_FIELDS,
			    FacebookConstants.FIELD_ID)).getId();
	} catch (FacebookException e) {
	    LOG.error(e.getLocalizedMessage(), e);
	    throw mapToConnectorException(e);
	} catch (Throwable t) {
	    LOG.error(t.getLocalizedMessage(), t);
	    throw new ConnectorException("unknown error", t);
	}

	// add users wall as a forum
	URI uri = Builder.createURI(endpoint + "/" + myId + "/"
		+ FacebookConstants.CONNECTION_FEED);
	if (!Forum.hasInstance(context.getDataModel(), uri)) {
	    Forum wall = new Forum(context.getDataModel(), uri, true);
	    wall.setId(myId);
	    wall.setName(getUserAccount().getAccountName() + "'s Wall");
	    wall.setHost(getSite());
	    wall.setOwner(getUserAccount());
	    getSite().addHostOf(wall);
	}

	setConnected(true);
    }

    @Override
    public void disconnect() {
	this.fbClient = null;
	this.myId = null;

	setConnected(false);
    }

    @Override
    public Site getSite() throws ConnectorException {
	if (!Site.hasInstance(context.getDataModel(), endpoint)) {
	    Site result = new Site(context.getDataModel(), endpoint, true);
	    result.setName("Facebook");
	    return result;
	} else {
	    return Site.getInstance(context.getDataModel(), endpoint);
	}
    }

    /**
     * @throws ConnectorException
     *             Thrown if the connector reported a problem.
     * @throws NullPointerException
     *             Thrown if id is null.
     * @throws IllegalArgumentException
     *             Thrown if id is empty.
     */
    @Override
    public UserAccount getUserAccount(final String id)
	    throws ConnectorException {
	Preconditions.checkNotNull(id, "id can not be null");
	Preconditions.checkArgument(!id.isEmpty(), "id can not be empty");
	Preconditions.checkState(isConnected(), "Connector is not online");

	URI uri = Builder.createURI(endpoint + "/" + id);

	if (!UserAccount.hasInstance(context.getDataModel(), uri)) {
	    User user = null;
	    try {
		user = fbClient.fetchObject(id, User.class);
	    } catch (FacebookException e) {
		LOG.error(e.getLocalizedMessage(), e);
		throw mapToConnectorException(e);
	    } catch (Throwable t) {
		LOG.error(t.getLocalizedMessage(), t);
		throw new ConnectorException("unknown error", t);
	    }

	    return FacebookSIOCConverter.createUserAccount(this, user, uri);

	} else {
	    return UserAccount.getInstance(context.getDataModel(), uri);
	}
    }

    @Override
    public Forum getForum(final String id) throws ConnectorException {
	Preconditions.checkNotNull(id, "Id can not be null.");
	Preconditions.checkArgument(!id.isEmpty(), "Id can not be empty.");
	Preconditions.checkState(isConnected(), "Connector is not online");

	URI uri = Builder.createURI(endpoint + "/"
		+ (FacebookConstants.ID_ME.equalsIgnoreCase(id) ? (myId) : (id
			.toLowerCase())) + "/"
		+ FacebookConstants.CONNECTION_FEED);

	if (!Forum.hasInstance(context.getDataModel(), uri)) {
	    Group group = null;
	    try {
		// need 'metadata=1' parameter to get 'type' value
		group = fbClient.fetchObject(id, Group.class,
			Parameter.with(FacebookConstants.PARAM_METADATA, "1"));
	    } catch (FacebookException e) {
		LOG.error(e.getLocalizedMessage(), e);
		throw mapToConnectorException(e);
	    } catch (Throwable t) {
		LOG.error(t.getLocalizedMessage(), t);
		throw new ConnectorException("unknown error", t);
	    }

	    if (null != group.getType()
		    && FacebookConstants.TYPE_GROUP.equalsIgnoreCase(group
			    .getType())) {

		return FacebookSIOCConverter.createForum(this, group, uri);
	    }

	    throw new NotFoundException("No forum found with id " + id);
	}

	return Forum.getInstance(context.getDataModel(), uri);
    }

    @Override
    public List<Forum> getForums() throws ConnectorException {
	Preconditions.checkState(isConnected(), "Connector is not online");
	List<Forum> result = new ArrayList<Forum>();

	// add all known forums
	ClosableIterator<Statement> stmtIter = context.getDataModel()
		.findStatements(
			Variable.ANY, SIOCVocabulary.has_host, getSite());
	while (stmtIter.hasNext()) {
	    Statement statement = stmtIter.next();
	    Resource subject = statement.getSubject();

	    if (context.getDataModel().contains(
		    subject,
		    RDF.type,
		    SIOCVocabulary.Forum)) {
		result.add(Forum.getInstance(context.getDataModel(), subject));
	    }
	}
	stmtIter.close();

	Connection<Group> groupsConnections = null;
	try {
	    groupsConnections = fbClient.fetchConnection("me/groups",
		    Group.class, Parameter.with(FacebookConstants.PARAM_FIELDS,
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
		URI uri = Builder.createURI(endpoint + "/" + group.getId()
			+ "/"
			+ FacebookConstants.CONNECTION_FEED);

		if (!Forum.hasInstance(context.getDataModel(), uri)) {
		    Forum forum = FacebookSIOCConverter.createForum(this,
			    group, uri);
		    LOG.debug("Add new forum " + uri.toString());
		    result.add(forum);
		}
	    }
	}

	return result;
    }

    @Override
    public Post getPost(String id) throws ConnectorException {
	Preconditions.checkNotNull(id, "Id can not be null.");
	Preconditions.checkArgument(!id.isEmpty(), "Id can not be empty.");
	Preconditions.checkState(isConnected(), "Connector is not online");

	URI uri = Builder.createURI(endpoint + "/" + id);

	if (!Post.hasInstance(context.getDataModel(), uri)) {
	    JsonObject obj;

	    try {
		obj = fbClient.fetchObject(id, JsonObject.class,
			Parameter.with(FacebookConstants.PARAM_METADATA, "1"));
	    } catch (FacebookException e) {
		LOG.error(e.getLocalizedMessage(), e);
		throw mapToConnectorException(e);
	    } catch (Throwable t) {
		LOG.error(t.getLocalizedMessage(), t);
		throw new ConnectorException("unknown error", t);
	    }

	    if (Pattern.matches("^\\d+_\\d+$", id)) { // it is maybe a post
		if (obj.has(FacebookConstants.FIELD_TYPE)) {
		    String type = obj.getString(FacebookConstants.FIELD_TYPE);

		    if (FacebookConstants.TYPE_LINK.equalsIgnoreCase(type)
			    || FacebookConstants.TYPE_STATUS
				    .equalsIgnoreCase(type)
			    || FacebookConstants.TYPE_VIDEO
				    .equalsIgnoreCase(type)
			    || FacebookConstants.TYPE_LINK
				    .equalsIgnoreCase(type)) {

			String containerId = id.substring(0,
				id.lastIndexOf('_'));
			Container container = null;

			try {
			    container = getForum(containerId);
			} catch (Exception e) {
			    throw new NotFoundException(
				    "ID is maybe a post, but no corresponding container was found");
			}

			if (null != container) {
			    Post result = FacebookSIOCConverter.createPost(
				    this, container, obj, uri);

			    // read available comments
			    if (obj.has(FacebookConstants.FIELD_COMMENTS)) {
				JsonObject comments = obj
					.getJsonObject(FacebookConstants.FIELD_COMMENTS);

				if (comments.has(FacebookConstants.FIELD_DATA)) {
				    JsonArray data = comments
					    .getJsonArray(FacebookConstants.FIELD_DATA);

				    getNewReplies(result, data);
				}
			    }

			    return result;
			}
		    }
		}
	    } else if (Pattern.matches("^\\d+_\\d+_\\d+$", id)) { // it is maybe
								  // a comment
		String parentId = id.substring(0, id.lastIndexOf('_'));

		Post parentPost;
		try {
		    parentPost = getPost(parentId);
		} catch (Exception e) {
		    throw new NotFoundException(
			    "Id is maybe a comment, but no corresponding parent was found");
		}

		if (null != parentPost) {
		    return FacebookSIOCConverter.createComment(this,
			    parentPost, obj, uri);
		}

	    }

	    throw new NotFoundException("No post found with id " + id);
	}

	return Post.getInstance(context.getDataModel(), uri);
    }

    @Override
    public boolean canPublishOn(Container container) {
	// We can not publish on null by default
	if (null != container) {
	    // We can publish on this container if:
	    // 1) The container is a SIOC Forum
	    // 2) Facebook is the host of this container
	    // 3) The container has a "feed" connection in Facebooks GraphAPI

	    if (context.getDataModel().contains(
		    container,
		    RDF.type,
		    SIOCVocabulary.Forum)) {
		Forum forum = Forum.getInstance(context.getDataModel(),
			container.getResource());
		try {
		    return forum.hasHost(getSite())
			    && hasConnection(container.getId(),
				    FacebookConstants.CONNECTION_FEED);
		} catch (ConnectorException e) {
		    LOG.error(e.getMessage(), e);
		    return false;
		}
	    }
	}

	return false;
    }

    @Override
    public boolean canReplyOn(Post parent) {
	// We can not reply on null by default.
	if (null != parent) {
	    // We can reply on this post if:
	    // 1) The parent is not a reply to another post
	    // 2) The parent was posted on a publishable container
	    // 3) The parent id is like "[0-9]+_[0-9]+"
	    // 4) The parent has a "comments" connection in Facebooks GraphAPI

	    if (!parent.hasReplyOf()) {
		if (parent.hasContainer()) {
		    Container container = parent.getContainer();
		    String id = parent.getId();

		    try {
			return canPublishOn(container)
				&& Pattern.matches("^\\d+_\\d+$", id)
				&& hasConnection(parent.getId(),
					FacebookConstants.CONNECTION_COMMENTS);
		    } catch (ConnectorException e) {
			LOG.error(e.getMessage(), e);
			return false;
		    }
		}
	    }
	}

	return false;
    }

    @Override
    public boolean hasPosts(Container container) {
	// A container has posts if we can publish on him
	return canPublishOn(container);
    }

    /**
     * @throws ConnectorException
     *             Thrown if the connector reported a problem.
     * @throws NullPointerException
     *             Thrown if one or more parameter are null.
     * @throws IllegalArgumentException
     *             Thrown if the post has no content or publishing on the
     *             container is not possible.
     * 
     * @see AbstractConnector#canPublishOn(Container)
     */
    @Override
    public Post publishPost(final Post post, final Container container)
	    throws ConnectorException {
	Preconditions.checkNotNull(post, "post can't be null");
	Preconditions.checkNotNull(container, "container can't be null");
	Preconditions.checkArgument(post.hasContent());
	Preconditions.checkArgument(canPublishOn(container));
	Preconditions.checkState(isConnected(), "Connector is not online");

	List<Parameter> params = new ArrayList<Parameter>();

	if (post.hasContent()) {
	    params.add(Parameter.with(FacebookConstants.FIELD_MESSAGE,
		    post.getContent()));
	}

	if (post.hasAttachments()) {
	    params.add(Parameter.with(FacebookConstants.FIELD_LINK,
		    post.getAttachment()));
	}

	if (post.hasTitle()) {
	    params.add(Parameter.with(FacebookConstants.FIELD_CAPTION,
		    post.getTitle()));
	} else if (post.hasSubject()) {
	    params.add(Parameter.with(FacebookConstants.FIELD_CAPTION,
		    post.getSubject()));
	}

	if (post.hasDescriptions()) {
	    params.add(Parameter.with(FacebookConstants.FIELD_DESCRIPTION,
		    post.getDescription()));
	}

	if (post.hasName()) {
	    params.add(Parameter.with(FacebookConstants.FIELD_NAME,
		    post.getName()));
	}

	FacebookType result = null;
	try {
	    result = fbClient.publish(container.getId() + "/"
		    + FacebookConstants.CONNECTION_FEED, FacebookType.class,
		    params.toArray(new Parameter[0]));
	} catch (FacebookException e) {
	    LOG.error(e.getLocalizedMessage(), e);
	    throw mapToConnectorException(e);
	} catch (Throwable t) {
	    LOG.error(t.getLocalizedMessage(), t);
	    throw new ConnectorException("unknown error", t);
	}

	if (null != result && null != result.getId()) {
	    Post addedPost = getPost(result.getId());
	    addedPost.setSibling(post);
	    return addedPost;
	}

	throw new ConnectorException(
		"Failed to publish post with unknown reason");
    };

    @Override
    public Post replyPost(final Post post, final Post parentPost)
	    throws ConnectorException {
	Preconditions.checkNotNull(post, "post can't be null");
	Preconditions.checkNotNull(parentPost, "parent can't be null");
	Preconditions.checkArgument(post.hasContent(), "post has no content");
	Preconditions.checkArgument(canReplyOn(parentPost),
		"can't reply on the parent post");
	Preconditions.checkState(isConnected(), "Connector is not online");

	FacebookType result = null;
	try {
	    result = fbClient.publish(parentPost.getId() + "/"
		    + FacebookConstants.CONNECTION_COMMENTS,
		    FacebookType.class,
		    Parameter.with("message", post.getContent()));
	} catch (FacebookException e) {
	    LOG.error(e.getLocalizedMessage(), e);
	    throw mapToConnectorException(e);
	} catch (Throwable t) {
	    LOG.error(t.getLocalizedMessage(), t);
	    throw new ConnectorException("unknown error", t);
	}

	if (null != result && null != result.getId()) {
	    Post addedPost = getPost(result.getId());
	    addedPost.setSibling(post);
	    return addedPost;
	}

	throw new ConnectorException("Failed to reply post with unknown reason");
    };

    @Override
    public List<Post> pollPosts(Container container, long limit)
	    throws ConnectorException {
	Preconditions.checkNotNull(container, "Container can not be null.");
	Preconditions.checkArgument(
		hasPosts(container),
		"This container has no posts in this connector");
	Preconditions.checkState(isConnected(), "Connector is not online");

	LOG.debug("pollNewPosts");
	List<Post> result = new ArrayList<Post>();
	Date lastItemDate = new Date(0);
	long numPostsLeftToLimit = limit;

	if (container.hasLastItemDate()) {
	    try {
		lastItemDate = RDFTool.string2DateTime(container
			.getLastItemDate());
	    } catch (ParseException e) {
		LOG.warn(e.getLocalizedMessage());
		lastItemDate = new Date(0);
	    }
	}

	Connection<JsonObject> feed = null;
	try {
	    feed = fbClient
		    .fetchConnection(container.getId() + "/"
			    + FacebookConstants.CONNECTION_FEED,
			    JsonObject.class, Parameter.with(
				    FacebookConstants.PARAM_LIMIT,
				    lastItemDate.getTime() / 1000L), Parameter
				    .with(FacebookConstants.PARAM_FIELDS,
					    POST_FIELDS), Parameter.with(
				    FacebookConstants.PARAM_SINCE,
				    Math.min(20, limit)));
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
		    URI uri = Builder.createURI(endpoint + "/"
			    + obj.getString(FacebookConstants.FIELD_ID));
		    Date created = com.restfb.util.DateUtils
			    .toDateFromLongFormat(obj
				    .getString(FacebookConstants.FIELD_CREATED_TIME));

		    if (created.after(lastItemDate)) {
			if (!Post.hasInstance(context.getDataModel(), uri)) {
			    LOG.debug("Add new post " + uri.toString());
			    result.add(FacebookSIOCConverter.createPost(this,
				    container, obj, uri));
			}
		    }

		    if (Post.hasInstance(context.getDataModel(), uri)
			    && obj.has(FacebookConstants.FIELD_COMMENTS)) {
			JsonObject comments = obj
				.getJsonObject(FacebookConstants.FIELD_COMMENTS);

			if (comments.has(FacebookConstants.FIELD_DATA)) {
			    JsonArray data = comments
				    .getJsonArray(FacebookConstants.FIELD_DATA);

			    result.addAll(getNewReplies(
				    Post.getInstance(
					    context.getDataModel(),
					    uri),
				    data));
			}
		    }

		    if (0 == --numPostsLeftToLimit) {
			return result;
		    }
		}
	    }
	}

	return result;
    }

    private List<Post> getNewReplies(final Post parentPost,
	    final JsonArray commentsArray) throws ConnectorException {
	List<Post> result = new ArrayList<Post>();
	Date lastReplyDate = new Date(0);

	if (parentPost.hasLastReplyDate()) {
	    try {
		lastReplyDate = RDFTool.string2DateTime(parentPost
			.getLastReplyDate());
	    } catch (ParseException e1) {
		lastReplyDate = new Date(0);
	    }
	}

	for (int i = 0; i < commentsArray.length(); i++) {
	    JsonObject obj = commentsArray.getJsonObject(i);
	    Date created = com.restfb.util.DateUtils.toDateFromLongFormat(obj
		    .getString(FacebookConstants.FIELD_CREATED_TIME));

	    if (created.after(lastReplyDate)) {
		String id = obj.getString(FacebookConstants.FIELD_ID);
		URI uri = Builder.createURI(endpoint + "/" + id);

		if (!Post.hasInstance(context.getDataModel(), uri)) {
		    result.add(FacebookSIOCConverter.createComment(this,
			    parentPost, obj, uri));
		}
	    }
	}

	return result;
    }

    private boolean hasConnection(final String id, final String connection)
	    throws ConnectorException {

	try {
	    JsonObject obj = fbClient.fetchObject(id, JsonObject.class,
		    Parameter
			    .with(FacebookConstants.PARAM_FIELDS,
				    FacebookConstants.FIELD_ID), Parameter
			    .with(
				    FacebookConstants.PARAM_METADATA, "1"));

	    if (null != obj && obj.has(FacebookConstants.FIELD_METADATA)) {
		JsonObject metadata = obj
			.getJsonObject(FacebookConstants.FIELD_METADATA);

		if (metadata.has(FacebookConstants.FIELD_CONNECTIONS)) {
		    JsonObject connections = metadata
			    .getJsonObject(FacebookConstants.FIELD_CONNECTIONS);

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
		setConnected(false);
		return new AuthenticationException(fae.getErrorMessage(), fae);
	    case 803: // Specified object cannot be found
		return new NotFoundException("Not found", fae);
	    }
	} else if (e instanceof FacebookNetworkException) {
	    FacebookNetworkException fne = (FacebookNetworkException) e;
	    setConnected(false);
	    return new NetworkException("Network error: "
		    + fne.getHttpStatusCode() + " " + fne.getMessage(), fne);
	}

	return new ConnectorException(e.getMessage(), e);
    }

    /*********************************************************************************/

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + Objects.hashCode(
		this.clientId,
		this.clientSecret,
		this.accessToken,
		this.myId);
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (!(obj instanceof FacebookConnector)) {
	    return false;
	}
	FacebookConnector other = (FacebookConnector) obj;

	if (!Objects.equal(this.clientId, other.clientId)) {
	    return false;
	}

	if (!Objects.equal(this.clientSecret, other.clientSecret)) {
	    return false;
	}

	if (!Objects.equal(this.accessToken, other.accessToken)) {
	    return false;
	}

	if (!Objects.equal(this.myId, other.myId)) {
	    return false;
	}

	return super.equals(obj);
    }

    @Override
    public String toString() {
	return Objects.toStringHelper(this).add("id", getId())
		.add("userId", myId).toString();
    }
}
