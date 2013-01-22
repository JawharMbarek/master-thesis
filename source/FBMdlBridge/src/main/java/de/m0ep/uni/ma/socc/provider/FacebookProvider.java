package de.m0ep.uni.ma.socc.provider;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.Group;

import de.m0ep.uni.ma.rdf.sioc.Forum;
import de.m0ep.uni.ma.socc.Connector;
import de.m0ep.uni.ma.socc.SIOCFactory;

public class FacebookProvider implements Connector {

    public static final String URL              = "http://facebook.com/";
    public static final String ID               = "com.facebook";
    public static final String NAME             = "Facebook";
    public static final String KEY_ACCESS_TOKEN = ID + ".access_token";

    private Properties config;

    SIOCFactory                siocFactory;
    private FacebookClient     client;

    public FacebookProvider( SIOCFactory factory, Properties config ) {
        this.siocFactory = factory;
        this.config = config;
    }

    public String getId() {
        return ID;
    }

    public String getURL() {
        return URL;
    }

    public String getUserFriendlyName() {
        return NAME;
    }

    public void init( Properties config ) {
        this.config = config;
        
        String token = this.config.getProperty( KEY_ACCESS_TOKEN );

        if( null != token ) {
            client = new DefaultFacebookClient( token );
        } else {
            throw new RuntimeException( "no access token in config" );
        }
    }

    public List<Forum> getForums() {
        List<Forum> result = new ArrayList<Forum>();

        Forum feed = siocFactory.createForum( "me/feed" );
        feed.setSIOCName( "feed" );
        result.add( feed );

        Connection<Group> groups = client.fetchConnection( "me/groups",
                Group.class );

        if( groups.hasNext() ) {
            // TODO:
        }

        return result;
    }

    public void destroy() {
        client = null;
    }

}
