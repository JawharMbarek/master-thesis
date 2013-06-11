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
import java.util.Map;

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.ontoware.rdf2go.model.Model;
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
import com.google.api.services.plus.Plus.Moments;
import com.google.api.services.plus.model.Activity;
import com.google.api.services.plus.model.Activity.PlusObject.Attachments;
import com.google.api.services.plus.model.ActivityFeed;
import com.google.api.services.plus.model.Comment;
import com.google.api.services.plus.model.CommentFeed;
import com.google.api.services.plus.model.ItemScope;
import com.google.api.services.plus.model.Moment;
import com.google.api.services.plus.model.MomentsFeed;
import com.google.api.services.plus.model.Person;
import com.google.api.services.plus.model.Person.Emails;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

import de.m0ep.socc.AbstractConnector;
import de.m0ep.socc.exceptions.ConnectorException;
import de.m0ep.socc.exceptions.NotFoundException;
import de.m0ep.socc.utils.ConfigUtils;
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
    private static final String PATH_MOMENT = "moment/";
    // private static final String PATH_CIRCLE = "circle/";
    private static final String PATH_COMMENT = "comment/";
    private static final String PATH_USER = "user/";

    /*
     * Connector constants
     */
    private static final String ID_FORUM_PUBLIC = "public";
    private static final String ID_FORUM_MOMENTS = "moments";
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
    private GooglePlusConnectorConfig gpConfig;
    private GoogleCredential credential;

    private Plus plus;
    private String myId;

    @Override
    public void initialize(final String id, final Model model,
	    final Map<String, Object> parameters) throws ConnectorException {
	Preconditions.checkNotNull(id, "Id can not be null");
	Preconditions.checkNotNull(model, "Model can not be null");
	Preconditions.checkArgument(!id.isEmpty(), "Id can not be empty");
	Preconditions.checkArgument(model.isOpen(), "Model must be open");
	Preconditions.checkArgument(
		parameters.containsKey(GooglePlusConnectorConfig.CLIENT_ID),
		"No client-id given");
	Preconditions
		.checkArgument(parameters
			.containsKey(GooglePlusConnectorConfig.CLIENT_SECRET),
			"No client-secret given");
	Preconditions.checkArgument(
		parameters.containsKey(GooglePlusConnectorConfig.ACCESS_TOKEN),
		"No accesstoken given");
	Preconditions
		.checkArgument(parameters
			.containsKey(GooglePlusConnectorConfig.REFRESH_TOKEN),
			"No refreshtoken given");
	super.initialize(id, model, parameters);

	this.gpConfig = new GooglePlusConnectorConfig();
	this.gpConfig = ConfigUtils.fromMap(parameters, this.gpConfig);

	credential = new GoogleCredential.Builder()
		.setClientSecrets(gpConfig.getClientId(),
			gpConfig.getClientSecret())
		.setJsonFactory(JSON_FACTORY).setTransport(HTTP_TRANSPORT)
		.addRefreshListener(new CredentialRefreshListener() {
		    @Override
		    public void onTokenResponse(Credential credential,
			    TokenResponse tokenResponse) throws IOException {
			// update accesstoken
			gpConfig.setAccessToken(tokenResponse.getAccessToken());
		    }

		    @Override
		    public void onTokenErrorResponse(Credential credential,
			    TokenErrorResponse tokenErrorResponse)
			    throws IOException {
		    }
		}).build();
	credential.setAccessToken(gpConfig.getAccessToken());
	credential.setRefreshToken(gpConfig.getRefreshToken());

    }

    @Override
    public void connect() throws ConnectorException {
	setConnected(false);

	plus = new Plus.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
		.setApplicationName(getId()).build();

	try {
	    Person me = plus.people().get("me").setFields("id").execute();
	    myId = me.getId();
	} catch (GoogleJsonResponseException e) {
	    LOG.error(e.getLocalizedMessage(), e);
	    throw mapToConnectorException(e);
	} catch (Throwable t) {
	    LOG.error(t.getLocalizedMessage(), t);
	    throw new ConnectorException("Unknown error", t);
	}

	// set all static forums
	URI uri = Builder.createURI(getURL() + myId + "/" + ID_FORUM_PUBLIC);

	if (!Forum.hasInstance(getModel(), uri)) {
	    Forum pub = new Forum(getModel(), uri, true);
	    pub.setId(myId + "/" + ID_FORUM_PUBLIC);
	    pub.setName("Public");
	    pub.setHost(getSite());
	    getSite().addHostOf(pub);
	}

	uri = Builder.createURI(getURL() + myId + "/" + ID_FORUM_MOMENTS);

	if (!Forum.hasInstance(getModel(), uri)) {
	    Forum moments = new Forum(getModel(), uri, true);
	    moments.setId(myId + "/" + ID_FORUM_MOMENTS);
	    moments.setName("Moments");
	    moments.setHost(getSite());
	    getSite().addHostOf(moments);
	}

	setConnected(true);
    }

    @Override
    public String getURL() {
	return "https://plus.google.com/";
    }

    @Override
    public Site getSite() {
	URI uri = Builder.createURI(getURL());

	if (!Site.hasInstance(getModel(), uri)) {
	    Site result = new Site(getModel(), uri, true);
	    result.setName("Google+");
	    return result;
	}

	return Site.getInstance(getModel(), uri);
    }

    @Override
    public UserAccount getLoginUser() throws ConnectorException {
	return getUserAccount(myId);
    }

    @Override
    public Map<String, Object> getConfiguration() {
	return ConfigUtils.toMap(gpConfig);
    }

    @Override
    public UserAccount getUserAccount(final String id)
	    throws ConnectorException {
	Preconditions.checkNotNull(id, "Id can not be null.");
	Preconditions.checkArgument(!id.isEmpty(), "Id can not be empty.");
	Preconditions.checkState(isOnline(), "Connector is not online");
	URI uri = Builder.createURI(getURL() + PATH_USER + id);

	if (!UserAccount.hasInstance(getModel(), uri)) {
	    Person user = null;
	    try {
		user = plus
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

	    UserAccount result = new UserAccount(getModel(), uri, true);

	    result.setId(user.getId());
	    result.setIsPartOf(getSite());
	    result.setAccountServiceHomepage(Builder.createURI(getURL()));

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
	    return UserAccount.getInstance(getModel(), uri);
	}
    }

    @Override
    public Forum getForum(final String id) throws ConnectorException {
	Preconditions.checkNotNull(id, "Id can not be null.");
	Preconditions.checkArgument(!id.isEmpty(), "Id can not be empty.");
	Preconditions.checkState(isOnline(), "Connector is not online");
	// atm. only public posts and moments are available
	// TODO: later check for circles

	if ((myId + "/" + ID_FORUM_PUBLIC).equalsIgnoreCase(id)) {
	    URI uri = Builder.createURI(getURL() + myId + "/"
		    + ID_FORUM_PUBLIC);
	    return Forum.getInstance(getModel(), uri);
	} else if ((myId + "/" + ID_FORUM_MOMENTS).equalsIgnoreCase(id)) {
	    URI uri = Builder.createURI(getURL() + myId + "/"
		    + ID_FORUM_MOMENTS);
	    return Forum.getInstance(getModel(), uri);
	}

	throw new NotFoundException("Forum with id " + id + " not found");
    }

    @Override
    public List<Forum> getForums() throws ConnectorException {
	Preconditions.checkState(isOnline(), "Connector is not online");

	List<Forum> result = new ArrayList<Forum>();

	QueryResultTable table = getModel()
		.sparqlSelect(
			SparqlUtil.formatQuery(SPARQL_SELECT_FORUMS_OF_SITE,
				getSite()));

	for (QueryRow row : table) {
	    result.add(Forum.getInstance(getModel(), row.getValue("forum")
		    .asURI()));
	}

	// TODO: load dynamic circles if necessary

	return result;
    }

    @Override
    public List<Post> getPosts(final Container container)
	    throws ConnectorException {
	Preconditions.checkNotNull(container, "container can not be null");
	Preconditions.checkArgument(hasPosts(container), "container "
		+ container + " has no post on this site");
	Preconditions.checkState(isOnline(), "Connector is not online");

	List<Post> result = new ArrayList<Post>();
	ClosableIterator<Statement> stmtsIter = getModel().findStatements(
		Variable.ANY, SIOCVocabulary.has_container, container);

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
    public List<Post> pollPosts(final Container container)
	    throws ConnectorException {
	Preconditions.checkNotNull(container, "container can not be null");
	Preconditions.checkArgument(hasPosts(container),
		"this container has no post on " + getSite().getName());
	Preconditions.checkState(isOnline(), "Connector is not online");

	List<Post> result = new ArrayList<Post>();

	if (container.getId().contains(ID_FORUM_MOMENTS)) {
	    result.addAll(pollMoments(container));
	} else if (container.getId().contains(ID_FORUM_PUBLIC)) {
	    result.addAll(pollPublicActivities(container));
	}

	return result;
    }

    private List<Post> pollMoments(final Container container)
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

	Moments.List momentListRequest = null;
	do {
	    MomentsFeed feed = null;
	    try {
		if (null == momentListRequest) {
		    momentListRequest = plus.moments().list("me", "vault");
		    // activityListRequest.setFields("id,items(actor/id,id,"
		    // + "object(attachments/url,content,"
		    // + "replies/totalItems),published,title,updated),"
		    // + "nextPageToken,updated");

		}

		if (null != pageToken) {
		    momentListRequest.setPageToken(pageToken);
		}

		feed = momentListRequest.execute();
	    } catch (GoogleJsonResponseException e) {
		LOG.error(e.getLocalizedMessage(), e);
		throw mapToConnectorException(e);
	    } catch (Throwable t) {
		LOG.error(t.getLocalizedMessage(), t);
		throw new ConnectorException("Unknown error", t);
	    }

	    if (null != feed) {
		pageToken = feed.getNextPageToken();
		for (Moment moment : feed.getItems()) {
		    URI uri = Builder.createURI(getURL() + PATH_MOMENT
			    + moment.getId());

		    if (null != moment.getTarget()) {
			ItemScope target = moment.getTarget();
			if (null != target.getDateCreated()) {
			    Date created;
			    try {
				created = DateUtils.parseISO8601(target
					.getDateCreated());
			    } catch (ParseException e) {
				created = new Date(1);
			    }
			    if (created.after(lastItemDate)) {
				if (!Post.hasInstance(getModel(), uri)) {
				    Post post = new Post(getModel(), uri, true);
				    post.setId(moment.getId());

				    if (null != target.getName()) {
					post.setTitle(target.getName());
				    }

				    if (null != target.getDescription()) {
					post.setContent(target.getDescription());
				    }

				    if (null != target.getAuthor()) {
					try {
					    post.setCreator(getUserAccount(target
						    .getAuthor().get(0).getId()));
					} catch (NotFoundException e) {
					    LOG.error(e.toString(), e);
					}
				    }

				    if (null != target.getDateCreated()) {
					try {
					    post.setCreated(DateUtils
						    .formatISO8601(DateUtils
							    .parseISO8601(target
								    .getDateCreated())));
					} catch (ParseException e) {
					    LOG.error(e.getMessage(), e);
					}
				    }

				    if (null != target.getDateModified()) {
					try {
					    post.setModified(DateUtils
						    .formatISO8601(DateUtils
							    .parseISO8601(target
								    .getDateModified())));
					} catch (ParseException e) {
					    LOG.error(e.getMessage(), e);
					}
				    }

				    result.add(post);
				}
			    }
			}
		    }
		}
	    }
	} while (null != pageToken);

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
		    activityListRequest = plus.activities()
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
		    URI uri = Builder.createURI(getURL() + PATH_ACTIVITY
			    + activity.getId());
		    Date created = new Date(activity.getPublished().getValue());
		    // TODO: check for updated posts!?

		    if (created.after(lastItemDate)) {
			if (!Post.hasInstance(getModel(), uri)) {
			    Post post = new Post(getModel(), uri, true);
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
		    if (Post.hasInstance(getModel(), uri) && 0 < numReplies) {
			List<Post> comments = pollNewReplies(
				Post.getInstance(getModel(), uri), activity);

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
		    commentListRequest = plus.comments().list(activity.getId());
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
			URI uri = Builder.createURI(getURL() + PATH_COMMENT
				+ comment.getId());

			// TODO: check for updated comments!?
			if (!Post.hasInstance(getModel(), uri)) {
			    Post reply = new Post(getModel(), uri, true);

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
	return getModel().sparqlAsk(
		SparqlUtil.formatQuery(SPARQL_ASK_IS_FORUM_OF_SITE, container,
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
		+ Objects.hashCode(this.credential, this.gpConfig, this.myId);
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

	if (!Objects.equal(this.credential, other.credential)) {
	    return false;
	}

	if (!Objects.equal(this.gpConfig, other.gpConfig)) {
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
