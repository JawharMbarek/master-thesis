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
import java.util.Iterator;
import java.util.Properties;

import org.ontoware.rdf2go.model.Model;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.Site;
import org.rdfs.sioc.Thread;
import org.rdfs.sioc.UserAccount;
import org.rdfs.sioc.Usergroup;

import com.google.common.base.Preconditions;

/**
 * Abstract connector that implements the {@link Connector} interface. It's a
 * good startingpoint for new connectors.
 * 
 * @author Florian Müller
 * 
 */
public abstract class AbstractConnector implements Connector {
    private String id;
    private Model model;
    private Properties config;

    /**
     * {@link AbstractConnector} constructor to create a new connector.
     * 
     * @param id
     *            Id of this connector.
     * @param model
     *            RDF2Go {@link Model} to use.
     * @param config
     *            Configuration parameters for this connector.
     * 
     * @throws NullPointerException
     *             Thrown if id, model or config are null.
     * @throws IllegalArgumentException
     *             Thrown if id is empty or mode is not open.
     */
    public AbstractConnector(final String id, final Model model,
	    final Properties config) {
	this.id = Preconditions.checkNotNull(id, "id can't be null");
	this.model = Preconditions.checkNotNull(model, "model can't be null");
	this.config = Preconditions
		.checkNotNull(config, "config can't be null");
	Preconditions.checkArgument(!id.isEmpty(), "id can't be empty");
	Preconditions.checkArgument(model.isOpen(), "model is not open");
    }

    /**
     * @see Connector#destroy()
     */
    public void destroy() {
    }

    /**
     * @see Connector#getId()
     */
    public String getId() {
	return id;
    }

    /**
     * @see Connector#getURL()
     */
    public abstract String getURL();

    /**
     * @see Connector#getConfig()
     */
    public Properties getConfig() {
	return config;
    }

    /**
     * @see Connector#getConfig()
     */
    public Model getModel() {
	return model;
    }

    /**
     * @see Connector#getSite()
     */
    public abstract Site getSite();

    /**
     * @see Connector#getUser()
     */
    public abstract UserAccount getUser();

    /**
     * @see Connector#getForums()
     */
    public Iterator<Forum> getForums() {
	return new ArrayList<Forum>().iterator();
    }

    /**
     * @see Connector#getThreads(Forum)
     */
    public Iterator<Thread> getThreads(Forum forum) {
	return new ArrayList<Thread>().iterator();
    }

    /**
     * @see Connector#getPosts(Container)
     */
    public Iterator<Post> getPosts(Container container) {
	return new ArrayList<Post>().iterator();
    }

    /**
     * @see Connector#getUsergroups()
     */
    public Iterator<Usergroup> getUsergroups() {
	return new ArrayList<Usergroup>().iterator();
    }

    /**
     * @see Connector#getUserAccounts()
     */
    public Iterator<UserAccount> getUserAccounts() {
	return new ArrayList<UserAccount>().iterator();
    }

    /**
     * @see Connector#canPublishOn(Container)
     */
    public boolean canPublishOn(Container container) {
	return false;
    }

    /**
     * @see Connector#canReplyOn(Post)
     */
    public boolean canReplyOn(Post parent) {
	return false;
    }

    /**
     * @see Connector#hasPosts(Container)
     */
    public boolean hasPosts(Container container) {
	return false;
    }

    /**
     * @see Connector#publishPost(Post, Container)
     */
    public boolean publishPost(Post post, Container container) {
	return false;
    }

    /**
     * @see Connector#replyPost(Post, Post)
     */
    public boolean replyPost(Post post, Post parent) {
	return false;
    }

    /**
     * @see Connector#pollPosts()
     */
    public Iterator<Post> pollPosts() {
	return new ArrayList<Post>().iterator();
    }
}
