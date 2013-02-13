package de.m0ep.socc;

import java.util.List;

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

    public Site getSite();

    public List<Forum> getForums();

    public List<Thread> getThreads( Forum forum );

    public List<Post> getPosts(Container container);

    public List<Usergroup> getUsergroups();

    public boolean canPostOn( Container container );

    public boolean canReplyOn(Post parent);

    public void publishPost( Post post, Container container );

    public void replyPost(Post post, Post parent);

    public UserAccount getUser();

    public List<UserAccount> getAllUserAccounts();
}
