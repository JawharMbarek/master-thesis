package de.m0ep.uni.ma.socc;

import java.util.List;
import java.util.Properties;

import de.m0ep.uni.ma.rdf.sioc.Forum;

public interface Connector {
    public void init( Properties config );

    public void destroy();

    public String getId();

    public String getURL();

    public String getUserFriendlyName();

    public List<Forum> getForums();
}
