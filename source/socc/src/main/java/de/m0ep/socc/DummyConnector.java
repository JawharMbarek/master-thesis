package de.m0ep.socc;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.QueryResultTable;
import org.ontoware.rdf2go.model.QueryRow;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.util.SparqlUtil;
import org.ontoware.rdf2go.vocabulary.RDF;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.SIOC;
import org.rdfs.sioc.SIOCThing;
import org.rdfs.sioc.Site;
import org.rdfs.sioc.Thread;
import org.rdfs.sioc.UserAccount;
import org.rdfs.sioc.Usergroup;

import com.google.common.base.Preconditions;

public final class DummyConnector implements Connector {

    public static final String URL = "http://www.example.org/";
    public static final String URI_PATH_USER = "user/";
    public static final String URI_PATH_FORUM = "forum/";
    public static final String URI_PATH_THREAD = "thread/";
    public static final String URI_PATH_POST = "post/";
    public static final String URI_PATH_USERGROUP = "usergroup/";

    private String id;
    private Model model;


    public DummyConnector(String id, Model model, Properties config) {
	Preconditions.checkNotNull(id, "id cannot be null");
	Preconditions.checkNotNull(model, "model cannot be null");
	Preconditions.checkArgument(!id.isEmpty(), "id cannot be empty");
	Preconditions.checkArgument(model.isOpen(), "model is not open");

	this.id = id;
	this.model = model;
    }

    public void destroy() {
	// do nothing
    }

    public String getId() {
	return id;
    }

    public String getURL() {
	return URL;
    }

    public Site getSite() {
	URI uri = model.createURI(URL + "dummysite");

	if (!Site.hasInstance(model, uri)) {
	    Site result = new Site(model, uri, true);
	    result.setId("dummysite");
	    result.setName("Dummy Site");

	    return result;
	} else {
	    return Site.getInstance(model, uri);
	}
    }

    public List<Forum> getForums() {
	return new ArrayList<Forum>();
    }

    public List<Thread> getThreads(Forum forum) {
	return new ArrayList<Thread>();
    }

    public List<Post> getPosts(Container container) {
	return new ArrayList<Post>();
    }

    public List<Usergroup> getUsergroups() {
	return new ArrayList<Usergroup>();
    }

    public boolean canPostOn(Container container) {
	return false;
    }

    public boolean canReplyOn(Post parent) {
	return false;
    }

    public void publishPost(Post post, Container container) {
    }

    public void replyPost(Post post, Post parent) {
    }

    public UserAccount getUser() {
	URI uri = model.createURI(URL + URI_PATH_USER + "1");

	if (!UserAccount.hasInstance(model, uri)) {
	    UserAccount result = new UserAccount(model, uri, true);
	    SIOCThing.setId(model, result, "1");
	    result.setAccountname("dummy");
	    result.setEmail(model.createPlainLiteral("dummy@example.org"));
	    result.setName("Dummy Example");

	    return result;
	} else {
	    return UserAccount.getInstance(model, uri);
	}
    }

    public List<UserAccount> getAllUserAccounts() {
	List<UserAccount> result = new ArrayList<UserAccount>();

	String query = "SELECT ?user\n" + "WHERE {\n" + "?user %s %s.\n"
		+ "FILTER regex(str(?user), \"^%s\") . }";

	QueryResultTable table = model.sparqlSelect(SparqlUtil.formatQuery(
		query, RDF.type, SIOC.UserAccount, URL + URI_PATH_USER));

	while (table.iterator().hasNext()) {
	    QueryRow row = table.iterator().next();
	    result.add(UserAccount.getInstance(model, row.getValue("user")
		    .asURI()));
	}

	return result;
    }
}
