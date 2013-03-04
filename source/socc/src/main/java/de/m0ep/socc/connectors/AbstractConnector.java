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
}
