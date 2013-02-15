package de.m0ep.socc;

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

public interface Connector {

    public void destroy();

    public String getId();

    public String getURL();

    public Properties getConfig();

    public Model getModel();

    public Site getSite();

    public UserAccount getUser();

    public Iterator<Forum> getForums();

    public Iterator<Thread> getThreads(Forum forum);

    public Iterator<Post> getPosts(Container container);

    public Iterator<Usergroup> getUsergroups();

    public Iterator<UserAccount> getUserAccounts();

    public boolean canPublishOn( Container container );

    public boolean canReplyOn(Post parent);

    public void publishPost( Post post, Container container );

    public void replyPost(Post post, Post parent);
}
