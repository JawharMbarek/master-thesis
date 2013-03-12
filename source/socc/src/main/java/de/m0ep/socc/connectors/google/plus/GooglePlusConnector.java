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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.QueryResultTable;
import org.ontoware.rdf2go.model.QueryRow;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.util.RDFTool;
import org.ontoware.rdf2go.util.SparqlUtil;
import org.ontoware.rdf2go.vocabulary.RDF;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.SIOC;
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
import com.google.common.base.Preconditions;

import de.m0ep.socc.connectors.AbstractConnector;
import de.m0ep.socc.connectors.exceptions.AuthException;
import de.m0ep.socc.connectors.exceptions.ConnectorException;
import de.m0ep.socc.connectors.exceptions.NotFoundException;
import de.m0ep.socc.utils.ConfigUtils;
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
    private static final String PATH_CIRCLE = "circle/";
    private static final String PATH_COMMENT = "comment/";
    private static final String PATH_USER = "user/";

    /*
     * Connector constants
     */
    private static final String CIRCLE_PUBLIC = "public";
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final JacksonFactory JSON_FACTORY = new JacksonFactory();

    /*
     * SPARQL query templates
     */
    private static final String SPARQL_SELECT_FORUMS_OF_SITE = "SELECT ?forum WHERE { ?forum "
	    + RDF.type.toSPARQL()
	    + " "
	    + SIOC.Forum.toSPARQL()
	    + " ; \n"
	    + SIOC.has_host.toSPARQL() + " %s . }";
    private static final String SPARQL_ASK_IS_FORUM_OF_SITE = "ASK { %s "
	    + RDF.type.toSPARQL() + " " + SIOC.Forum.toSPARQL() + " ; \n"
	    + SIOC.has_host.toSPARQL() + " %s . }";
    private static final String SPARQL_SELECT_POST_NO_COMMENTS = "SELECT ?post WHERE { ?post "
	    + RDF.type.toSPARQL()
	    + " "
	    + SIOC.Post.toSPARQL()
	    + " ; "
	    + SIOC.has_container.toSPARQL()
	    + " %s . OPTIONAL { ?y "
	    + SIOC.reply_of.toSPARQL()
	    + " ?z . FILTER (?post = ?y) . } "
	    + " FILTER ( !BOUND(?y) ) }";

    /*
     * Member variables
     */
    private GooglePlusConnectorConfig gpConfig;
    private GoogleCredential credential;

    private Plus plus;
    private String myId;

    @Override
    public void initialize(String id, Model model,
	    Map<String, Object> parameters) {
	super.initialize(id, model, parameters);

	this.gpConfig = ConfigUtils.fromMap(parameters,
		GooglePlusConnectorConfig.class);

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
			    TokenErrorResponse tokenErrorResponse) throws IOException {
		    }
		}).build();
	credential.setAccessToken(gpConfig.getAccessToken());
	credential.setRefreshToken(gpConfig.getRefreshToken());

	plus = new Plus.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
		.build();

	try {
	    Person me = plus.people().get("me").setFields("id").execute();
	    myId = me.getId();
	} catch (GoogleJsonResponseException e) {
	    handleGoogleError(e);
	} catch (Exception e) {
	    throw new ConnectorException("Unknown error", e);
	}

	// set all static forums
	URI uri = RDF2GoUtils.createURI(getURL() + PATH_CIRCLE + CIRCLE_PUBLIC);

	if (!Forum.hasInstance(getModel(), uri)) {
	    Forum pub = new Forum(getModel(), uri, true);
	    pub.setId(CIRCLE_PUBLIC);
	    pub.setName(CIRCLE_PUBLIC);
	    pub.setHost(getSite());
	    getSite().setHostof(pub);
	}
    }

    @Override
    public String getURL() {
	return "https://plus.google.com/";
    }

    @Override
    public Site getSite() {
	URI uri = RDF2GoUtils.createURI(getURL());

	if (!Site.hasInstance(getModel(), uri)) {
	    Site result = new Site(getModel(), uri, true);
	    result.setName("Google+");
	    return result;
	}

	return Site.getInstance(getModel(), uri);
    }

    @Override
    public UserAccount getLoginUser() {
	return getUserAccount(myId);
    }

    @Override
    public Map<String, Object> getConfiguration() {
	return ConfigUtils.toMap(gpConfig);
    }

    public UserAccount getUserAccount(String id) {
	URI uri = RDF2GoUtils.createURI(getURL() + PATH_USER + id);

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
		handleGoogleError(e);
	    } catch (Exception e) {
		throw new ConnectorException("Unknown error", e);
	    }

	    UserAccount result = new UserAccount(getModel(), uri, true);

	    result.setId(user.getId());
	    result.setIsPartOf(getSite());
	    result.setAccountservicehomepage(RDF2GoUtils.createURI(getURL()));

	    if (null != user.getAboutMe()) {
		result.setDescription(user.getAboutMe());
	    }

	    if (null != user.getDisplayName()) {
		result.setName(user.getDisplayName());
	    }

	    if (null != user.getNickname()) {
		result.setAccountname(user.getNickname());
	    }

	    if (null != user.getEmails()) {
		for (Emails email : user.getEmails()) {
		    result.addEmail(RDF2GoUtils.createMailtoURI(email
			    .getValue()));
		    result.addEmailsha1(DigestUtils.sha1Hex(email.getValue()));
		}
	    }

	    if (null != user.getImage()) {
		result.setAvatar(RDF2GoUtils
			.createURI(user.getImage().getUrl()));
	    }

	    return result;
	} else {
	    return UserAccount.getInstance(getModel(), uri);
	}
    }

    @Override
    public Forum getForum(String id) throws ConnectorException {
	// atm. only public circle is available
	// TODO: later check for other circles

	if (CIRCLE_PUBLIC.equalsIgnoreCase(id)) {
	    URI uri = RDF2GoUtils.createURI(getURL() + PATH_CIRCLE + id);
	    return Forum.getInstance(getModel(), uri);
	}

	throw new NotFoundException("Not found");
    }

    @Override
    public Iterator<Forum> getForums() {
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

	return result.iterator();
    }

    @Override
    public Iterator<Post> getPosts(Container container) {
	Preconditions.checkNotNull(container, "container can not be null");
	Preconditions.checkArgument(hasPosts(container),
		"this container has no post on " + getSite().getName());

	List<Post> result = new ArrayList<Post>();
	QueryResultTable table = getModel().sparqlSelect(
		SparqlUtil.formatQuery(SPARQL_SELECT_POST_NO_COMMENTS,
			container));

	for (QueryRow row : table) {
	    result.add(Post.getInstance(getModel(), row.getValue("post")
		    .asURI()));
	}

	return result.iterator();
    }

    @Override
    public Iterator<Post> pollNewPosts(Container container) {
	Preconditions.checkNotNull(container, "container can not be null");
	Preconditions.checkArgument(hasPosts(container),
		"this container has no post on " + getSite().getName());

	String pageToken = null;
	List<Post> result = new ArrayList<Post>();
	Date lastItemDate = new Date(0);

	if (container.hasLastitemdate()) {
	    try {
		lastItemDate = RDFTool.string2DateTime(container
			.getLastitemdate());
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
		handleGoogleError(e);
	    } catch (Exception e) {
		throw new ConnectorException("Unknown error", e);
	    }

	    for (Activity activity : feed.getItems()) {
		Date created = new Date(trimToSeconds(activity
			.getPublished().getValue()));
		// TODO: check for updated posts!?

		if (created.after(lastItemDate)) {
		    URI uri = RDF2GoUtils.createURI(getURL() + PATH_ACTIVITY
			    + activity.getId());

		    if (Post.hasInstance(getModel(), uri)) {
			Post post = new Post(getModel(), uri, true);
			post.setId(activity.getId());

			if (null != activity.getTitle())
			    post.setTitle(activity.getTitle());

			if (null != activity.getActor())
			    post.setCreator(getUserAccount(activity.getActor()
				    .getId()));

			if (null != activity.getPublished())
			    post.setCreated(RDFTool.dateTime2String(new Date(
				    activity.getPublished().getValue())));

			if (null != activity.getUpdated())
			    post.setModified(RDFTool.dateTime2String(new Date(
				    activity.getUpdated().getValue())));

			String content = activity.getObject().getContent();
			post.setContent(StringUtils.stripHTML(content));
			post.setContentEncoded(content);

			if (null != activity.getObject().getAttachments()) {
			    for (Attachments attachments : activity.getObject()
				    .getAttachments()) {
				post.addAttachment(RDF2GoUtils
					.createURI(attachments.getUrl()));
			    }
			}

			if (0 < activity.getObject().getReplies()
				.getTotalItems()) {
			    List<Post> comments = updateComments(container,
				    post, activity);

			    result.addAll(comments);
			}

			// set sioc connections
			post.setContainer(container);
			container.setContainerof(post);
			SIOCUtils.updateLastItemDate(container, post);

			result.add(post);
			LOG.debug("add new post " + post.toString());
		    }
		} else {
		    // first old post -> return;
		    return result.iterator();
		}
	    }
	} while (null != pageToken);

	return result.iterator();
    }

    private List<Post> updateComments(Container container, Post parent,
	    Activity activity) throws ConnectorException {
	List<Post> result = new ArrayList<Post>();
	String pageToken = null;
	Date lastReplyDate = new Date(0);

	if (parent.hasLastreplydate()) {
	    try {
		lastReplyDate = RDFTool.string2DateTime(parent
			.getLastreplydate());
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
		handleGoogleError(e);
	    } catch (Exception e) {
		throw new ConnectorException("Unknown error", e);
	    }

	    if (feed != null) {
		pageToken = (null != feed.getNextPageToken()) ? feed
			.getNextPageToken() : null;

		for (Comment comment : feed.getItems()) {
		    Date created = new Date(trimToSeconds(comment
			    .getPublished().getValue()));

		    if (created.after(lastReplyDate)) {
			URI uri = RDF2GoUtils.createURI(getURL() + PATH_COMMENT
				+ comment.getId());

			// TODO: check for updated comments!?
			if (!Post.hasInstance(getModel(), uri)) {
			    Post reply = new Post(getModel(), uri, true);

			    reply.setId(comment.getId());

			    if (null != comment.getActor())
				reply.setCreator(getUserAccount(comment
					.getActor().getId()));

			    if (null != comment.getPublished())
				reply.setCreated(RDFTool
					.dateTime2String(new Date(comment
						.getPublished().getValue())));
			    if (null != comment.getUpdated())
				reply.setModified(RDFTool
					.dateTime2String(new Date(comment
						.getUpdated().getValue())));

			    if (null != comment.getObject().getContent()) {
				String content = comment.getObject()
					.getContent();
				reply.setContent(StringUtils.stripHTML(content));
				reply.setContentEncoded(content);
			    }

			    // set sioc connections
			    reply.setReplyof(parent);
			    parent.setReply(reply);
			    SIOCUtils.updateLastReplyDate(parent, reply);

			    reply.setContainer(container);
			    container.setContainerof(reply);
			    SIOCUtils.updateLastItemDate(container, reply);

			    result.add(reply);
			    LOG.debug("add comment " + comment.toString());
			}
		    }
		}
	    }
	} while (null != pageToken);

	return result;
    }

    @Override
    public boolean hasPosts(Container container) {
	return getModel().sparqlAsk(
		SparqlUtil.formatQuery(SPARQL_ASK_IS_FORUM_OF_SITE, container,
			getSite()));
    }

    /* package */void handleGoogleError(GoogleJsonResponseException e)
	    throws ConnectorException {
	GoogleJsonError error = e.getDetails();
	LOG.error(error.getMessage(), e);

	switch (error.getCode()) {
	case 404:
	    throw new NotFoundException(error.getMessage());
	case 401:
	    throw new AuthException(error.getMessage());
	default:
	    throw new ConnectorException(error.getMessage());
	}
    }

    private Long trimToSeconds(Long millis) {
	return (millis / 1000L) * 1000L;
    }
}
