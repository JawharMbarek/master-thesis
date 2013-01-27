package de.m0ep.uni.ma.socc.connectors;

import java.util.List;
import java.util.Properties;

import de.m0ep.uni.ma.rdf.sioc.Forum;
import de.m0ep.uni.ma.rdf.sioc.Post;
import de.m0ep.uni.ma.rdf.sioc.Thread;
import de.m0ep.uni.ma.rdf.sioc.UserAccount;
import de.m0ep.uni.ma.socc.SIOCModel;

public interface Connector {
    public void init( SIOCModel model, Properties config );

    public void destroy();

    public String getId();

    public String getUserFriendlyName();

    public List<Forum> getForums();

    public List<Thread> getThreads( Forum forum );

    public boolean canPostOn( Forum forum );

    public boolean canPostOn( Thread thread );

    public void publishPost( Forum forum );

    public void publishPost( Thread thread );

    public void commentPost( Post parent );

    public UserAccount getUser();
}
