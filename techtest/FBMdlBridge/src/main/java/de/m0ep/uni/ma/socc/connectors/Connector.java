package de.m0ep.uni.ma.socc.connectors;

import java.util.List;
import java.util.Properties;

import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.Site;
import org.rdfs.sioc.Thread;
import org.rdfs.sioc.UserAccount;

import de.m0ep.uni.ma.socc.SIOCModel;

public interface Connector {
    public void init( SIOCModel model, Properties config );

    public void destroy();

    public String getURL();

    public String getUserFriendlyName();

    public SIOCModel getModel();

    public Site getSite();

    public List<Forum> getForums();

    public List<Thread> getThreads( Forum forum );

    public List<Post> getPost( Container container );

    public boolean canPostOn( Container container );

    public void publishPost( Post post, Container container );

    public void commentPost( Post post, Post parent );

    public UserAccount getUserAccount();
}
