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

package de.m0ep.socc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.QueryResultTable;
import org.ontoware.rdf2go.model.QueryRow;
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

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

import de.m0ep.socc.exceptions.ConnectorException;
import de.m0ep.socc.exceptions.NotFoundException;

/**
 * Abstract connector that implements the {@link IConnector} interface. It's a
 * good startingpoint for new connectors.
 * 
 * @author Florian Müller
 * 
 */
public abstract class AbstractConnector implements IConnector {
    private static final String SPARQL_VAR_USER = "user";
    private static final String SPARQL_VAR_FORUM = "forum";
    private static final String SPARQL_VAR_THREAD = "thread";

    private static final String SPARQL_SELECT_USERACCOUNTS_OF_SITE = "SELECT ?"
	    + SPARQL_VAR_USER + " WHERE {" + "?" + SPARQL_VAR_USER + " "
	    + RDF.type.toSPARQL() + " " + SIOC.UserAccount.toSPARQL() + " ; "
	    + SIOCThing.ISPARTOF.toSPARQL() + " %s . }";

    private static final String SPARQL_SELECT_FORUMS_OF_SITE = "SELECT ?"
	    + SPARQL_VAR_FORUM + " WHERE { ?" + SPARQL_VAR_FORUM + " "
	    + RDF.type.toSPARQL() + " " + SIOC.Forum.toSPARQL() + " ; "
	    + SIOC.has_host.toSPARQL() + " %s . }";

    private static final String SPARQL_SELECT_THREADS_OF_FORUM = "SELECT ?"
	    + SPARQL_VAR_THREAD + " WHERE { ?" + SPARQL_VAR_THREAD + " "
	    + RDF.type.toSPARQL() + " " + SIOC.Thread.toSPARQL() + " ; "
	    + SIOC.has_parent.toSPARQL() + " %s . }";

    private String id;
    private Model model;

    @Override
    public void initialize(String id, Model model,
	    Map<String, Object> parameters) throws ConnectorException {
	this.id = Preconditions.checkNotNull(id, "id can't be null");
	this.model = Preconditions.checkNotNull(model, "model can't be null");
	Preconditions.checkNotNull(parameters, "parameters can't be null");
	Preconditions.checkArgument(!id.isEmpty(), "id can't be empty");
	Preconditions.checkArgument(model.isOpen(), "model is not open");
    }

    /**
     * (non-Javadoc)
     * 
     * @see de.m0ep.socc.IConnector#destroy()
     */
    @Override
    public void destroy() throws ConnectorException {
    }

    /**
     * (non-Javadoc)
     * 
     * @see de.m0ep.socc.IConnector#getId()
     */
    @Override
    public String getId() {
	return id;
    }

    /**
     * (non-Javadoc)
     * 
     * @see de.m0ep.socc.IConnector#getURL()
     */
    @Override
    public abstract String getURL();

    /**
     * (non-Javadoc)
     * 
     * @see de.m0ep.socc.IConnector#getModel()
     */
    @Override
    public Model getModel() {
	return model;
    }

    /**
     * (non-Javadoc)
     * 
     * @see de.m0ep.socc.IConnector#getSite()
     */
    @Override
    public abstract Site getSite() throws ConnectorException;

    /**
     * (non-Javadoc)
     * 
     * @see de.m0ep.socc.IConnector#getLoginUser()
     */
    @Override
    public abstract UserAccount getLoginUser() throws ConnectorException;

    /**
     * @throws NotFoundException
     *             Thrown if no {@link UserAccount} with this id was found.
     * 
     * @see de.m0ep.socc.IConnector#getUserAccount(java.lang.String)
     */
    @Override
    public UserAccount getUserAccount(String id) throws ConnectorException {
	throw new NotFoundException("There is no user with the id=" + id);
    }

    /**
     * (non-Javadoc)
     * 
     * @see de.m0ep.socc.IConnector#getUserAccounts()
     */
    @Override
    public List<UserAccount> getUserAccounts() throws ConnectorException {
	List<UserAccount> result = new ArrayList<UserAccount>();

	QueryResultTable resultTable = getModel().sparqlSelect(
		SparqlUtil.formatQuery(SPARQL_SELECT_USERACCOUNTS_OF_SITE,
			getSite()));
	for (QueryRow row : resultTable) {
	    result.add(UserAccount.getInstance(getModel(),
		    row.getValue(SPARQL_VAR_USER).asResource()));
	}

	return result;
    }

    /**
     * @throws NotFoundException
     *             Thrown if no {@link Forum} with this id was found.
     * 
     * @see de.m0ep.socc.IConnector#getForum(java.lang.String)
     */
    @Override
    public Forum getForum(String id) throws ConnectorException {
	throw new NotFoundException("There is no forum with this id=" + id);
    }

    /**
     * (non-Javadoc)
     * 
     * @see de.m0ep.socc.IConnector#getForums()
     */
    @Override
    public List<Forum> getForums() throws ConnectorException {
	List<Forum> result = new ArrayList<Forum>();

	QueryResultTable table = getModel()
		.sparqlSelect(
			SparqlUtil.formatQuery(SPARQL_SELECT_FORUMS_OF_SITE,
				getSite()));

	for (QueryRow row : table) {
	    result.add(Forum.getInstance(getModel(),
		    row.getValue(SPARQL_VAR_FORUM).asURI()));
	}

	return result;
    }

    /**
     * @throws NotFoundException
     *             Thrown if no {@link Thread} with this id was found.
     * 
     * @see de.m0ep.socc.IConnector#getThread(java.lang.String)
     */
    @Override
    public Thread getThread(String id) throws ConnectorException {
	throw new NotFoundException("There is no thread with the id=" + id);
    }

    /**
     * (non-Javadoc)
     * 
     * @see de.m0ep.socc.IConnector#getThreads(org.rdfs.sioc.Forum)
     */
    @Override
    public List<Thread> getThreads(Forum forum) throws ConnectorException {
	Preconditions.checkArgument(forum.hasHost(getSite()),
		"forum don't belong to this site");
	List<Thread> result = new ArrayList<Thread>();

	QueryResultTable resultTable = getModel().sparqlSelect(
		SparqlUtil.formatQuery(SPARQL_SELECT_THREADS_OF_FORUM, forum));
	for (QueryRow row : resultTable) {
	    result.add(Thread.getInstance(getModel(),
		    row.getValue(SPARQL_VAR_FORUM).asResource()));
	}

	return result;
    }

    /**
     * @throws NotFoundException
     *             Thrown if no {@link Post} with this id was found.
     * 
     * @see de.m0ep.socc.IConnector#getPost(java.lang.String)
     */
    @Override
    public Post getPost(String id) throws ConnectorException {
	throw new NotFoundException("There is no post with the id=" + id);
    }

    /**
     * (non-Javadoc)
     * 
     * @see de.m0ep.socc.IConnector#getPosts(org.rdfs.sioc.Container)
     */
    @Override
    public List<Post> getPosts(Container container) throws ConnectorException {
	return new ArrayList<Post>();
    }

    /**
     * @throws NotFoundException
     *             Thrown if no {@link Usergroup} with this id was found.
     * 
     * @see de.m0ep.socc.IConnector#getUsergroup(java.lang.String)
     */
    @Override
    public Usergroup getUsergroup(String id) throws ConnectorException {
	throw new NotFoundException("There is no usergroup with the id=" + id);
    }

    /**
     * (non-Javadoc)
     * 
     * @see de.m0ep.socc.IConnector#getUsergroups()
     */
    @Override
    public List<Usergroup> getUsergroups() throws ConnectorException {
	return new ArrayList<Usergroup>();
    }

    /**
     * (non-Javadoc)
     * 
     * @see de.m0ep.socc.IConnector#canPublishOn(org.rdfs.sioc.Container)
     */
    @Override
    public boolean canPublishOn(Container container) {
	return false;
    }

    /**
     * (non-Javadoc)
     * 
     * @see de.m0ep.socc.IConnector#canReplyOn(org.rdfs.sioc.Post)
     */
    @Override
    public boolean canReplyOn(Post parent) {
	return false;
    }

    /**
     * (non-Javadoc)
     * 
     * @see de.m0ep.socc.IConnector#hasPosts(org.rdfs.sioc.Container)
     */
    @Override
    public boolean hasPosts(Container container) {
	return false;
    }

    /**
     * @throws UnsupportedOperationException
     *             Thrown if the connector don't support post publishing.
     * 
     * @see de.m0ep.socc.IConnector#publishPost(org.rdfs.sioc.Post,
     *      org.rdfs.sioc.Container)
     */
    @Override
    public Post publishPost(Post post, Container container)
	    throws ConnectorException {
	throw new UnsupportedOperationException();
    }

    /**
     * @throws UnsupportedOperationException
     *             Thrown if the connector don't support post replying.
     * 
     * @see de.m0ep.socc.IConnector#replyPost(org.rdfs.sioc.Post,
     *      org.rdfs.sioc.Post)
     */
    @Override
    public Post replyPost(Post post, Post parent) throws ConnectorException {
	throw new UnsupportedOperationException();
    }

    /**
     * (non-Javadoc)
     * 
     * @see de.m0ep.socc.IConnector#pollNewPosts(org.rdfs.sioc.Container)
     */
    @Override
    public List<Post> pollNewPosts(Container container)
	    throws ConnectorException {
	return new ArrayList<Post>();
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + Objects.hashCode(this.id, this.model);
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
	if (!(obj instanceof AbstractConnector)) {
	    return false;
	}
	AbstractConnector other = (AbstractConnector) obj;

	if (!Objects.equal(this.id, other.id)) {
	    return false;
	}

	if (!Objects.equal(this.model, other.model)) {
	    return false;
	}

	return super.equals(obj);
    }

    @Override
    public String toString() {
	return Objects.toStringHelper(this).add("id", this.id).toString();
    }
}
