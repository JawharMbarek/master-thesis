package de.m0ep.uni.ma.socc.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.ontoware.rdf2go.util.RDFTool;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.Group;
import com.restfb.types.User;
import com.restfb.util.StringUtils;

import de.m0ep.uni.ma.rdf.sioc.Forum;
import de.m0ep.uni.ma.rdf.sioc.UserAccount;
import de.m0ep.uni.ma.socc.Connector;
import de.m0ep.uni.ma.socc.SIOCFactory;

public class FacebookProvider implements Connector {

    public static final String URL              = "http://facebook.com/";
    public static final String ID               = "com.facebook";
    public static final String NAME             = "Facebook";
    public static final String KEY_ACCESS_TOKEN = ID + ".access_token";

    private Properties config;

    private SIOCFactory        siocFactory;
    private FacebookClient     client;

    private Map<Forum, String> connectionMap;

    public FacebookProvider( SIOCFactory factory ) {
        this.siocFactory = factory;
        this.connectionMap = new HashMap<Forum, String>();
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

    public void destroy() {
        client = null;
    }

    public List<Forum> getForums() {
        List<Forum> result = new ArrayList<Forum>();

        Forum wall = siocFactory.createForum( "me/feed" );
        wall.setSIOCName( "Wall" );
        
        connectionMap.put( wall, "me/feed" );
        result.add( wall );

        Connection<Group> groupsConnections = client.fetchConnection(
                "me/groups",
                Group.class );

        for ( List<Group> myGroups : groupsConnections ) {
            for ( Group group : myGroups ) {
                Forum forum = siocFactory.createForum( group.getId() );
                forum.setSIOCName( "Group: " + group.getName() );
                forum.setDCTermsDescription( StringUtils.trimToEmpty( group
                        .getDescription() ) );
                

                connectionMap.put( forum, group.getId() + "/feed" );
                result.add( forum );
            }
        }

        return result;
    }

    public UserAccount getUser() {
        UserAccount result = null;
        User user = client.fetchObject( "me", User.class );

        if( null != user ) {
            result = siocFactory.createUserAccount( user.getId() );
            result.setFOAFName( user.getName() );
            result.setFOAFAccountname( user.getUsername() );
            result.setDCTermsModified( RDFTool.dateTime2String( user
                    .getUpdatedTime() ) );
        }

        return result;
    }

}
