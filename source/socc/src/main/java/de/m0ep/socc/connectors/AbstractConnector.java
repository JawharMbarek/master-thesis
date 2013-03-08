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

package de.m0ep.socc.connectors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import org.ontoware.rdf2go.model.Model;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.Site;
import org.rdfs.sioc.Thread;
import org.rdfs.sioc.UserAccount;
import org.rdfs.sioc.Usergroup;

import com.google.common.base.Preconditions;

import de.m0ep.socc.connectors.exceptions.ConnectorException;

/**
 * Abstract connector that implements the {@link IConnector} interface. It's a
 * good startingpoint for new connectors.
 * 
 * @author Florian Müller
 * 
 */
public abstract class AbstractConnector implements IConnector {
    private String id;
    private Model model;
    private Map<String, Object> parameter;

    @Override
    public void initialize(String id, Model model,
	    Map<String, Object> parameters) {
	this.id = Preconditions.checkNotNull(id, "id can't be null");
	this.model = Preconditions.checkNotNull(model, "model can't be null");
	this.parameter = Preconditions.checkNotNull(parameters,
		"parameters can't be null");
	Preconditions.checkArgument(!id.isEmpty(), "id can't be empty");
	Preconditions.checkArgument(model.isOpen(), "model is not open");
    }

    /**
     * (non-Javadoc)
     * 
     * @see de.m0ep.socc.connectors.IConnector#destroy()
     */
    public void destroy() {
    }

    /**
     * (non-Javadoc)
     * 
     * @see de.m0ep.socc.connectors.IConnector#getId()
     */
    public String getId() {
	return id;
    }

    /**
     * (non-Javadoc)
     * 
     * @see de.m0ep.socc.connectors.IConnector#getURL()
     */
    public abstract String getURL();

    /**
     * (non-Javadoc)
     * 
     * @see de.m0ep.socc.connectors.IConnector#getModel()
     */
    public Model getModel() {
	return model;
    }

    /**
     * (non-Javadoc)
     * 
     * @see de.m0ep.socc.connectors.IConnector#getConfiguration()
     */
    public Map<String, Object> getConfiguration() {
	return Collections.unmodifiableMap(parameter);
    }

    /**
     * (non-Javadoc)
     * 
     * @see de.m0ep.socc.connectors.IConnector#getSite()
     */
    public abstract Site getSite();

    /**
     * (non-Javadoc)
     * 
     * @see de.m0ep.socc.connectors.IConnector#getLoginUser()
     */
    public abstract UserAccount getLoginUser();

    /**
     * (non-Javadoc)
     * 
     * @see de.m0ep.socc.connectors.IConnector#getUserAccount(java.lang.String)
     */
    public UserAccount getUserAccount(String id) throws ConnectorException {
	throw new ConnectorException("There is no user with the id=" + id);
    }

    /**
     * (non-Javadoc)
     * 
     * @see de.m0ep.socc.connectors.IConnector#getUserAccounts()
     */
    public Iterator<UserAccount> getUserAccounts() {
	return new ArrayList<UserAccount>().iterator();
    }

    /**
     * (non-Javadoc)
     * 
     * @see de.m0ep.socc.connectors.IConnector#getForum(java.lang.String)
     */
    public Forum getForum(String id) throws ConnectorException {
	throw new ConnectorException("There is no forum with the id=" + id);
    }

    /**
     * (non-Javadoc)
     * 
     * @see de.m0ep.socc.connectors.IConnector#getForums()
     */
    public Iterator<Forum> getForums() {
	return new ArrayList<Forum>().iterator();
    }

    /**
     * (non-Javadoc)
     * 
     * @see de.m0ep.socc.connectors.IConnector#getThread(java.lang.String)
     */
    public Thread getThread(String id) throws ConnectorException {
	throw new ConnectorException("There is no thread with the id=" + id);
    }

    /**
     * (non-Javadoc)
     * 
     * @see de.m0ep.socc.connectors.IConnector#getThreads(org.rdfs.sioc.Forum)
     */
    public Iterator<Thread> getThreads(Forum forum) {
	return new ArrayList<Thread>().iterator();
    }

    /**
     * (non-Javadoc)
     * 
     * @see de.m0ep.socc.connectors.IConnector#getPost(java.lang.String)
     */
    public Post getPost(String id) throws ConnectorException {
	throw new ConnectorException("There is no post with the id=" + id);
    }

    /**
     * (non-Javadoc)
     * 
     * @see de.m0ep.socc.connectors.IConnector#getPosts(org.rdfs.sioc.Container)
     */
    public Iterator<Post> getPosts(Container container) {
	return new ArrayList<Post>().iterator();
    }

    /**
     * (non-Javadoc)
     * 
     * @see de.m0ep.socc.connectors.IConnector#getUsergroup(java.lang.String)
     */
    public Usergroup getUsergroup(String id) throws ConnectorException {
	throw new ConnectorException("There is no usergroup with the id=" + id);
    }

    /**
     * (non-Javadoc)
     * 
     * @see de.m0ep.socc.connectors.IConnector#getUsergroups()
     */
    public Iterator<Usergroup> getUsergroups() {
	return new ArrayList<Usergroup>().iterator();
    }

    /**
     * (non-Javadoc)
     * 
     * @see de.m0ep.socc.connectors.IConnector#canPublishOn(org.rdfs.sioc.Container)
     */
    public boolean canPublishOn(Container container) {
	return false;
    }

    /**
     * (non-Javadoc)
     * 
     * @see de.m0ep.socc.connectors.IConnector#canReplyOn(org.rdfs.sioc.Post)
     */
    public boolean canReplyOn(Post parent) {
	return false;
    }

    /**
     * (non-Javadoc)
     * 
     * @see de.m0ep.socc.connectors.IConnector#hasPosts(org.rdfs.sioc.Container)
     */
    public boolean hasPosts(Container container) {
	return false;
    }

    /**
     * (non-Javadoc)
     * 
     * @see de.m0ep.socc.connectors.IConnector#publishPost(org.rdfs.sioc.Post,
     *      org.rdfs.sioc.Container)
     */
    public boolean publishPost(Post post, Container container) {
	return false;
    }

    /**
     * (non-Javadoc)
     * 
     * @see de.m0ep.socc.connectors.IConnector#replyPost(org.rdfs.sioc.Post,
     *      org.rdfs.sioc.Post)
     */
    public boolean replyPost(Post post, Post parent) {
	return false;
    }

    /**
     * (non-Javadoc)
     * 
     * @see de.m0ep.socc.connectors.IConnector#pollNewPosts(org.rdfs.sioc.Container)
     */
    public Iterator<Post> pollNewPosts(Container container) {
	return new ArrayList<Post>().iterator();
    }
}
