package de.m0ep.socc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import org.ontoware.rdf2go.model.Model;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.Thread;
import org.rdfs.sioc.UserAccount;
import org.rdfs.sioc.Usergroup;

import com.google.common.base.Preconditions;

public abstract class AbstractConnector implements Connector {
    private String id;
    private Model model;
    private Properties config;

    public AbstractConnector(final String id, final Model model,
	    final Properties config) {
	Preconditions.checkNotNull(id, "id cannot be null");
	Preconditions.checkNotNull(model, "model cannot be null");
	Preconditions.checkArgument(!id.isEmpty(), "id cannot be empty");
	Preconditions.checkArgument(model.isOpen(), "model is not open");

	this.id = id;
	this.model = model;
	this.config = config;
    }

    public void destroy() {
    }

    public String getId() {
	return id;
    }

    public Properties getConfig() {
	return config;
    }

    public Model getModel() {
	return model;
    }

    public Iterator<Forum> getForums() {
	return new ArrayList<Forum>().iterator();
    }

    public Iterator<Thread> getThreads(Forum forum) {
	return new ArrayList<Thread>().iterator();
    }

    public Iterator<Post> getPosts(Container container) {
	return new ArrayList<Post>().iterator();
    }

    public Iterator<Usergroup> getUsergroups() {
	return new ArrayList<Usergroup>().iterator();
    }

    public Iterator<UserAccount> getUserAccounts() {
	return new ArrayList<UserAccount>().iterator();
    }

    public boolean canPublishOn(Container container) {
	return false;
    }

    public boolean canReplyOn(Post parent) {
	return false;
    }

    public boolean publishPost(Post post, Container container) {
	return false;
    }

    public boolean replyPost(Post post, Post parent) {
	return false;
    }
}
