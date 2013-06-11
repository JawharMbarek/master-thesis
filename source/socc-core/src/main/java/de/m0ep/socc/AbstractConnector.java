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

import org.ontoware.rdf2go.model.QueryResultTable;
import org.ontoware.rdf2go.model.QueryRow;
import org.ontoware.rdf2go.util.SparqlUtil;
import org.ontoware.rdf2go.vocabulary.RDF;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.SIOCVocabulary;
import org.rdfs.sioc.Site;
import org.rdfs.sioc.Thread;
import org.rdfs.sioc.UserAccount;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

import de.m0ep.sioc.service.auth.Service;
import de.m0ep.socc.exceptions.ConnectorException;
import de.m0ep.socc.exceptions.NotFoundException;

/**
 * Abstract connector that implements the {@link IConnector} interface. It's a
 * good starting point for new connectors.
 * 
 * @author Florian Müller
 * 
 */
public abstract class AbstractConnector implements IConnector {
    private static final String SPARQL_VAR_FORUM = "forum";
    private static final String SPARQL_VAR_THREAD = "thread";

    private static final String SPARQL_SELECT_FORUMS_OF_SITE = "SELECT ?"
	    + SPARQL_VAR_FORUM + " WHERE { ?" + SPARQL_VAR_FORUM + " "
	    + RDF.type.toSPARQL() + " " + SIOCVocabulary.Forum.toSPARQL()
	    + " ; "
	    + SIOCVocabulary.has_host.toSPARQL() + " %s . }";

    private static final String SPARQL_SELECT_THREADS_OF_FORUM = "SELECT ?"
	    + SPARQL_VAR_THREAD + " WHERE { ?" + SPARQL_VAR_THREAD + " "
	    + RDF.type.toSPARQL() + " " + SIOCVocabulary.Thread.toSPARQL()
	    + " ; "
	    + SIOCVocabulary.has_parent.toSPARQL() + " %s . }";

    protected String id;
    protected ISOCCContext context;
    protected Service service;
    protected UserAccount userAccount;

    protected boolean isConnected;

    @Override
    public void initialize(String id, ISOCCContext context, Service service,
	    UserAccount userAccount) throws ConnectorException {
	this.id = Preconditions.checkNotNull(
		id,
		"Id can not be null.");
	this.context = Preconditions.checkNotNull(
		context,
		"Context can not be null.");
	this.userAccount = Preconditions.checkNotNull(
		userAccount,
		"UserAccount can not be null.");
	this.service = Preconditions.checkNotNull(
		service,
		"Service can not be null.");
	Preconditions.checkArgument(
		!id.isEmpty(),
		"Id can not be empty.");

	this.isConnected = false;
    }

    @Override
    public abstract void connect() throws ConnectorException;

    @Override
    public boolean isConnected() {
	return isConnected;
    }

    /**
     * Sets this connector to be online/offline.
     * 
     * @param online
     *            Online status
     */
    protected void setConnected(final boolean online) {
	this.isConnected = online;
    }

    @Override
    public abstract void disconnect();

    @Override
    public void destroy() throws ConnectorException {
    }

    @Override
    public String getId() {
	return id;
    }

    @Override
    public ISOCCContext getContext() {
	return context;
    }

    @Override
    public Service getService() {
	return service;
    }

    @Override
    public UserAccount getUserAccount() {
	return userAccount;
    }

    @Override
    public abstract Site getSite() throws ConnectorException;

    /**
     * @throws NotFoundException
     *             Thrown if no {@link Forum} with this id was found.
     */
    @Override
    public Forum getForum(String id) throws ConnectorException {
	throw new NotFoundException("There is no forum with the id=" + id);
    }

    @Override
    public List<Forum> getForums() throws ConnectorException {
	List<Forum> result = new ArrayList<Forum>();

	QueryResultTable table = context.getDataModel()
		.sparqlSelect(
			SparqlUtil.formatQuery(SPARQL_SELECT_FORUMS_OF_SITE,
				getSite()));

	for (QueryRow row : table) {
	    result.add(Forum.getInstance(context.getDataModel(),
		    row.getValue(SPARQL_VAR_FORUM).asURI()));
	}

	return result;
    }

    /**
     * @throws NotFoundException
     *             Thrown if no {@link Thread} with this id was found.
     */
    @Override
    public Thread getThread(String id) throws ConnectorException {
	throw new NotFoundException("There is no thread with the id=" + id);
    }

    @Override
    public List<Thread> getThreads(Forum forum) throws ConnectorException {
	Preconditions.checkArgument(forum.hasHost(getSite()),
		"forum don't belong to this site");
	List<Thread> result = new ArrayList<Thread>();

	QueryResultTable resultTable = context.getDataModel().sparqlSelect(
		SparqlUtil.formatQuery(SPARQL_SELECT_THREADS_OF_FORUM, forum));
	for (QueryRow row : resultTable) {
	    result.add(Thread.getInstance(context.getDataModel(),
		    row.getValue(SPARQL_VAR_FORUM).asResource()));
	}

	return result;
    }

    /**
     * @throws NotFoundException
     *             Thrown if no {@link Post} with this id was found.
     */
    @Override
    public Post getPost(String id) throws ConnectorException {
	throw new NotFoundException("There is no post with the id=" + id);
    }

    @Override
    public List<Post> getPosts(Container container) throws ConnectorException {
	return new ArrayList<Post>();
    }

    @Override
    public List<Post> pollPosts(Container container, long limit)
	    throws ConnectorException {
	return new ArrayList<Post>();
    }

    @Override
    public List<Post> pollReplies(Post parent, long limit)
	    throws ConnectorException {
	return new ArrayList<Post>();
    }

    @Override
    public boolean hasPosts(Container container) {
	return false;
    }

    @Override
    public boolean canPublishOn(Container container) {
	return false;
    }

    /**
     * @throws UnsupportedOperationException
     *             Thrown if the connector don't support post publishing.
     */
    @Override
    public Post publishPost(Post post, Container container)
	    throws ConnectorException {
	throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasReplies(Post parent) {
	return false;
    }

    @Override
    public boolean canReplyOn(Post parent) {
	return false;
    }

    /**
     * @throws UnsupportedOperationException
     *             Thrown if the connector don't support post replying.
     */
    @Override
    public Post replyPost(Post post, Post parent) throws ConnectorException {
	throw new UnsupportedOperationException();
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + Objects.hashCode(this.id, this.context);
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

	if (!Objects.equal(this.context, other.context)) {
	    return false;
	}

	return true;
    }

    @Override
    public String toString() {
	return Objects.toStringHelper(this).add("id", this.id).toString();
    }
}
