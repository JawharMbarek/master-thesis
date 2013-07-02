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

package de.m0ep.socc.connectors.google.plus;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.ontoware.rdf2go.model.QueryResultTable;
import org.ontoware.rdf2go.model.QueryRow;
import org.ontoware.rdf2go.model.Statement;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.model.node.Variable;
import org.ontoware.rdf2go.util.Builder;
import org.ontoware.rdf2go.util.RDFTool;
import org.ontoware.rdf2go.util.SparqlUtil;
import org.ontoware.rdf2go.vocabulary.RDF;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.SIOCVocabulary;
import org.rdfs.sioc.Site;
import org.rdfs.sioc.UserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.CredentialRefreshListener;
import com.google.api.client.auth.oauth2.TokenErrorResponse;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.Plus.Activities;
import com.google.api.services.plus.Plus.Comments;
import com.google.api.services.plus.model.Activity;
import com.google.api.services.plus.model.Activity.PlusObject.Attachments;
import com.google.api.services.plus.model.ActivityFeed;
import com.google.api.services.plus.model.Comment;
import com.google.api.services.plus.model.CommentFeed;
import com.google.api.services.plus.model.Person;
import com.google.api.services.plus.model.Person.Emails;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

import de.m0ep.sioc.service.auth.AccessToken;
import de.m0ep.sioc.service.auth.Authentication;
import de.m0ep.sioc.service.auth.ClientId;
import de.m0ep.sioc.service.auth.ClientSecret;
import de.m0ep.sioc.service.auth.RefreshToken;
import de.m0ep.sioc.service.auth.Service;
import de.m0ep.socc.AbstractConnector;
import de.m0ep.socc.ISOCCContext;
import de.m0ep.socc.exceptions.ConnectorException;
import de.m0ep.socc.exceptions.NotFoundException;
import de.m0ep.socc.utils.DateUtils;
import de.m0ep.socc.utils.RDF2GoUtils;
import de.m0ep.socc.utils.SIOCUtils;
import de.m0ep.socc.utils.StringUtils;

public class GooglePlusConnector extends AbstractConnector {
    private static final Logger LOG = LoggerFactory
	    .getLogger(GooglePlusConnector.class);

    /*
     * URI Path segments
     */
    private static final String PATH_ACTIVITY = "activity/";
    // private static final String PATH_CIRCLE = "circle/";
    private static final String PATH_COMMENT = "comment/";
    private static final String PATH_USER = "user/";

    /*
     * Connector constants
     */
    private static final String ID_FORUM_PUBLIC = "public";
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final JacksonFactory JSON_FACTORY = new JacksonFactory();

    /*
     * SPARQL query templates
     */
    private static final String SPARQL_SELECT_FORUMS_OF_SITE = "SELECT ?forum WHERE { ?forum "
	    + RDF.type.toSPARQL()
	    + " "
	    + SIOCVocabulary.Forum.toSPARQL()
	    + " ; \n"
	    + SIOCVocabulary.has_host.toSPARQL() + " %s . }";
    private static final String SPARQL_ASK_IS_FORUM_OF_SITE = "ASK { %s "
	    + RDF.type.toSPARQL() + " " + SIOCVocabulary.Forum.toSPARQL()
	    + " ; \n"
	    + SIOCVocabulary.has_host.toSPARQL() + " %s . }";

    /*
     * Member variables
     */

    private Plus gpClient;
    private URI endpoint = Builder.createURI("http://plus.google.com");
    private String myId;

    private GoogleCredential credential;
    private ClientId clientId;
    private ClientSecret clientSecret;
    private AccessToken accessToken;
    private RefreshToken refreshToken;

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
	ClosableIterator<de.m0ep.sioc.service.auth.Credential> serviceCredentialsIter =
		serviceAuth.getAllCredential();

	this.clientId = null;
	this.clientSecret = null;
	while (serviceCredentialsIter.hasNext()) {
	    de.m0ep.sioc.service.auth.Credential credential =
		    (de.m0ep.sioc.service.auth.Credential)
		    serviceCredentialsIter.next();
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
	ClosableIterator<de.m0ep.sioc.service.auth.Credential> credentials = userAuth
		.getAllCredential();

	this.accessToken = null;
	this.refreshToken = null;
	while (credentials.hasNext()) {
	    de.m0ep.sioc.service.auth.Credential credential =
		    (de.m0ep.sioc.service.auth.Credential) credentials.next();

	    URI type = RDF2GoUtils.getType(
		    credential.getModel(),
		    credential);

	    if (type.equals(AccessToken.RDFS_CLASS)) {
		this.accessToken = AccessToken.getInstance(
			credential.getModel(),
			credential.getResource());
	    } else if (type.equals(RefreshToken.RDFS_CLASS)) {
		this.refreshToken = RefreshToken.getInstance(
			credential.getModel(),
			credential.getResource());
	    }
	}

	Preconditions.checkArgument(
		null != this.accessToken && accessToken.hasValue(),
		"No AccessToken credential found in userAccount");

	credential = new GoogleCredential.Builder()
		.setClientSecrets(
			clientId.getValue(),
			clientSecret.getValue())
		.setJsonFactory(JSON_FACTORY).setTransport(HTTP_TRANSPORT)
		// Save new token on token refresh
		.addRefreshListener(new CredentialRefreshListener() {
		    @Override
		    public void onTokenResponse(Credential credential,
			    TokenResponse tokenResponse) throws IOException {
			if (null != tokenResponse.getAccessToken()) {
			    accessToken
				    .setValue(tokenResponse.getAccessToken());
			}

			if (null != tokenResponse.getRefreshToken()) {
			    refreshToken.setValue(tokenResponse
				    .getRefreshToken());
			}
		    }

		    @Override
		    public void onTokenErrorResponse(Credential credential,
			    TokenErrorResponse tokenErrorResponse)
			    throws IOException {
		    }
		}).build();

	credential.setAccessToken(accessToken.getValue());

	// Set refreshtoken if there is one.
	if (null != refreshToken && refreshToken.hasValue()) {
	    credential.setRefreshToken(refreshToken.getValue());
	}
    }

    @Override
    public void connect() throws ConnectorException {
	setConnected(false);

	gpClient = new Plus.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
		.setApplicationName(getId()).build();

	try {
	    Person me = gpClient.people().get("me").setFields("id").execute();
	    myId = me.getId();
	} catch (GoogleJsonResponseException e) {
	    LOG.error(e.getLocalizedMessage(), e);
	    throw mapToConnectorException(e);
	} catch (Throwable t) {
	    LOG.error(t.getLocalizedMessage(), t);
	    throw new ConnectorException("Unknown error", t);
	}

	// set all static forums
	URI uri = Builder.createURI(endpoint + "/" + myId + "/"
		+ ID_FORUM_PUBLIC);

	if (!Forum.hasInstance(context.getDataModel(), uri)) {
	    Forum pub = new Forum(context.getDataModel(), uri, true);
	    pub.setId(myId + "/" + ID_FORUM_PUBLIC);
	    pub.setName("Public");
	    pub.setHost(getSite());
	    getSite().addHostOf(pub);
	}

	setConnected(true);
    }

    @Override
    public void disconnect() {
	gpClient = null;
	myId = null;

	setConnected(false);
    }

    @Override
    public Site getSite() {
	if (!Site.hasInstance(context.getDataModel(), endpoint)) {
	    Site result = new Site(context.getDataModel(), endpoint, true);
	    result.setName("Google+");
	    return result;
	}

	return Site.getInstance(context.getDataModel(), endpoint);
    }

    @Override
    public UserAccount getUserAccount(final String id)
	    throws ConnectorException {
	Preconditions.checkNotNull(id, "Id can not be null.");
	Preconditions.checkArgument(!id.isEmpty(), "Id can not be empty.");
	Preconditions.checkState(isConnected(), "Connector is not online");
	URI uri = Builder.createURI(endpoint + "/" + PATH_USER + id);

	if (!UserAccount.hasInstance(context.getDataModel(), uri)) {
	    Person user = null;
	    try {
		user = gpClient
			.people()
			.get(id)
			.setFields(
				"aboutMe,displayName,emails/value,id,image,"
					+ "nickname,url").execute();

	    } catch (GoogleJsonResponseException e) {
		LOG.error(e.getLocalizedMessage(), e);
		throw mapToConnectorException(e);
	    } catch (Throwable t) {
		LOG.error(t.getLocalizedMessage(), t);
		throw new ConnectorException("Unknown error", t);
	    }

	    UserAccount result = new UserAccount(
		    context.getDataModel(),
		    uri,
		    true);

	    result.setId(user.getId());
	    result.setIsPartOf(getSite());
	    result.setAccountServiceHomepage(endpoint);

	    if (null != user.getAboutMe()) {
		result.setDescription(user.getAboutMe());
	    }

	    if (null != user.getDisplayName()) {
		result.setName(user.getDisplayName());
	    }

	    if (null != user.getNickname()) {
		result.setAccountName(user.getNickname());
	    }

	    if (null != user.getEmails()) {
		for (Emails email : user.getEmails()) {
		    result.addEmail(RDF2GoUtils.createMailtoURI(email
			    .getValue()));
		    result.addEmailSha1(RDFTool.sha1sum(email.getValue()));
		}
	    }

	    if (null != user.getImage()) {
		result.setAvatar(Builder.createURI(user.getImage().getUrl()));
	    }

	    return result;
	} else {
	    return UserAccount.getInstance(context.getDataModel(), uri);
	}
    }

    @Override
    public Forum getForum(final String id) throws ConnectorException {
	Preconditions.checkNotNull(id, "Id can not be null.");
	Preconditions.checkArgument(!id.isEmpty(), "Id can not be empty.");
	Preconditions.checkState(isConnected(), "Connector is not online");

	if ((myId + "/" + ID_FORUM_PUBLIC).equalsIgnoreCase(id)) {
	    URI uri = Builder.createURI(endpoint + "/" + myId + "/"
		    + ID_FORUM_PUBLIC);
	    return Forum.getInstance(context.getDataModel(), uri);
	}

	throw new NotFoundException("Forum with id " + id + " not found");
    }

    @Override
    public List<Forum> getForums() throws ConnectorException {
	Preconditions.checkState(isConnected(), "Connector is not online");

	List<Forum> result = new ArrayList<Forum>();

	QueryResultTable table = context.getDataModel().sparqlSelect(
		SparqlUtil.formatQuery(SPARQL_SELECT_FORUMS_OF_SITE,
			getSite()));

	for (QueryRow row : table) {
	    result.add(Forum.getInstance(
		    context.getDataModel(),
		    row.getValue("forum")
			    .asURI()));
	}

	return result;
    }

    @Override
    public List<Post> getPosts(final Container container)
	    throws ConnectorException {
	Preconditions.checkNotNull(container, "container can not be null");
	Preconditions.checkArgument(hasPosts(container), "container "
		+ container + " has no post on this site");
	Preconditions.checkState(isConnected(), "Connector is not online");

	List<Post> result = new ArrayList<Post>();
	ClosableIterator<Statement> stmtsIter = context.getDataModel().
		findStatements(
			Variable.ANY,
			SIOCVocabulary.has_container,
			container);

	while (stmtsIter.hasNext()) {
	    Statement statement = stmtsIter.next();

	    if (Post.hasInstance(
		    context.getDataModel(),
		    statement.getSubject())) {
		result.add(Post.getInstance(
			context.getDataModel(),
			statement.getSubject()));
	    }
	}
	stmtsIter.close();

	return result;
    }

    @Override
    public List<Post> pollPosts(final Container container, long limit)
	    throws ConnectorException {
	Preconditions.checkNotNull(container, "container can not be null");
	Preconditions.checkArgument(hasPosts(container),
		"this container has no post on " + getSite().getName());
	Preconditions.checkState(isConnected(), "Connector is not online");

	List<Post> result = new ArrayList<Post>();

	if (container.getId().contains(ID_FORUM_PUBLIC)) {
	    result.addAll(pollPublicActivities(container));
	}

	return result;
    }

    private List<Post> pollPublicActivities(final Container container)
	    throws ConnectorException {
	String pageToken = null;
	List<Post> result = new ArrayList<Post>();
	Date lastItemDate = new Date(0);

	if (container.hasLastItemDate()) {
	    try {
		lastItemDate = RDFTool.string2DateTime(container
			.getLastItemDate());
	    } catch (ParseException e1) {
		lastItemDate = new Date(0);
	    }
	}

	Activities.List activityListRequest = null;
	do {
	    ActivityFeed feed = null;
	    try {
		if (null == activityListRequest) {
		    activityListRequest = gpClient.activities()
			    .list("me", "public");
		    activityListRequest.setFields("id,items(actor/id,id,"
			    + "object(attachments/url,content,"
			    + "replies/totalItems),published,title,updated),"
			    + "nextPageToken,updated");
		}

		if (null != pageToken) {
		    activityListRequest.setPageToken(pageToken);
		}

		feed = activityListRequest.execute();
	    } catch (GoogleJsonResponseException e) {
		LOG.error(e.getLocalizedMessage(), e);
		throw mapToConnectorException(e);
	    } catch (Throwable t) {
		LOG.error(t.getLocalizedMessage(), t);
		throw new ConnectorException("Unknown error", t);
	    }

	    if (null != feed) {
		pageToken = feed.getNextPageToken();
		for (Activity activity : feed.getItems()) {
		    URI uri = Builder.createURI(
			    endpoint + "/" +
				    PATH_ACTIVITY
				    + activity.getId());
		    Date created = new Date(activity.getPublished().getValue());
		    // TODO: check for updated posts!?

		    if (created.after(lastItemDate)) {
			if (!Post.hasInstance(context.getDataModel(), uri)) {
			    Post post = new Post(
				    context.getDataModel(),
				    uri,
				    true);
			    post.setId(activity.getId());

			    if (null != activity.getTitle())
				post.setTitle(activity.getTitle());

			    if (null != activity.getActor())
				post.setCreator(getUserAccount(activity
					.getActor().getId()));

			    if (null != activity.getPublished())
				post.setCreated(DateUtils
					.formatISO8601(activity.getPublished()
						.getValue()));

			    if (null != activity.getUpdated())
				post.setModified(DateUtils
					.formatISO8601(activity.getUpdated()
						.getValue()));

			    String content = activity.getObject().getContent();
			    post.setContent(StringUtils.stripHTML(content));
			    post.setContentEncoded(content);

			    if (null != activity.getObject().getAttachments()) {
				for (Attachments attachments : activity
					.getObject().getAttachments()) {
				    post.addAttachment(Builder.createURI(
					    attachments.getUrl()));
				}
			    }

			    // set sioc connections
			    post.setContainer(container);
			    container.addContainerOf(post);
			    SIOCUtils.updateLastItemDate(container, post);

			    result.add(post);
			    LOG.debug("add new post " + post.toString());
			}
		    }

		    Long numReplies = activity.getObject().getReplies()
			    .getTotalItems();
		    if (Post.hasInstance(
			    context.getDataModel(),
			    uri) && 0 < numReplies) {
			List<Post> comments = pollNewReplies(
				Post.getInstance(
					context.getDataModel(),
					uri),
				activity);

			result.addAll(comments);
		    }
		}
	    }
	} while (null != pageToken);

	return result;
    }

    private List<Post> pollNewReplies(final Post parentPost,
	    final Activity activity) throws ConnectorException {
	List<Post> result = new ArrayList<Post>();
	String pageToken = null;
	Date lastReplyDate = new Date(0);

	if (parentPost.hasLastReplyDate()) {
	    try {
		lastReplyDate = RDFTool.string2DateTime(parentPost
			.getLastReplyDate());
	    } catch (ParseException e1) {
		lastReplyDate = new Date(0);
	    }
	}

	Comments.List commentListRequest = null;
	do {
	    CommentFeed feed = null;
	    try {
		if (null == commentListRequest) {
		    commentListRequest = gpClient.comments().list(
			    activity.getId());
		    commentListRequest.setFields("id,items(actor/id,id,"
			    + "object/content,published,updated),"
			    + "nextPageToken");
		    commentListRequest.setSortOrder("descending");
		}

		if (null != pageToken)
		    commentListRequest.setPageToken(pageToken);

		feed = commentListRequest.execute();
	    } catch (GoogleJsonResponseException e) {
		LOG.error(e.getLocalizedMessage(), e);
		throw mapToConnectorException(e);
	    } catch (Throwable t) {
		LOG.error(t.getLocalizedMessage(), t);
		throw new ConnectorException("Unknown error", t);
	    }

	    if (feed != null) {
		pageToken = (null != feed.getNextPageToken()) ? feed
			.getNextPageToken() : null;

		for (Comment comment : feed.getItems()) {
		    Date created = new Date(comment.getPublished().getValue());

		    if (created.after(lastReplyDate)) {
			URI uri = Builder.createURI(
				endpoint + "/"
					+ PATH_COMMENT
					+ comment.getId());

			// TODO: check for updated comments!?
			if (!Post.hasInstance(context.getDataModel(), uri)) {
			    Post reply = new Post(
				    context.getDataModel(),
				    uri,
				    true);

			    reply.setId(comment.getId());

			    if (null != comment.getActor())
				reply.setCreator(getUserAccount(comment
					.getActor().getId()));

			    if (null != comment.getPublished())
				reply.setCreated(DateUtils
					.formatISO8601(comment.getPublished()
						.getValue()));
			    if (null != comment.getUpdated())
				reply.setModified(DateUtils
					.formatISO8601(comment.getUpdated()
						.getValue()));

			    if (null != comment.getObject().getContent()) {
				String content = comment.getObject()
					.getContent();
				reply.setContent(StringUtils.stripHTML(content));
				reply.setContentEncoded(content);
			    }

			    // set sioc connections
			    reply.setReplyOf(parentPost);
			    parentPost.addReply(reply);
			    SIOCUtils.updateLastReplyDate(parentPost, reply);

			    Container container = parentPost.getContainer();
			    reply.setContainer(container);
			    container.addContainerOf(reply);
			    SIOCUtils.updateLastItemDate(container, reply);

			    result.add(reply);
			    LOG.debug("add comment " + reply.toString());
			}
		    }
		}
	    }
	} while (null != pageToken);

	return result;
    }

    @Override
    public boolean hasPosts(final Container container) {
	return context.getDataModel().sparqlAsk(
		SparqlUtil.formatQuery(
			SPARQL_ASK_IS_FORUM_OF_SITE,
			container,
			getSite()));
    }

    protected ConnectorException mapToConnectorException(
	    GoogleJsonResponseException e) {
	GoogleJsonError error = e.getDetails();

	switch (error.getCode()) {
	case 404:
	    return new NotFoundException(error.getMessage());
	case 401:
	    setConnected(false);
	    return new de.m0ep.socc.exceptions.AuthenticationException(
		    error.getMessage());
	default:
	    return new ConnectorException(error.getMessage());
	}
    }

    /*********************************************************************************/

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result
		+ Objects.hashCode(this.myId);
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
	if (!(obj instanceof GooglePlusConnector)) {
	    return false;
	}
	GooglePlusConnector other = (GooglePlusConnector) obj;

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
