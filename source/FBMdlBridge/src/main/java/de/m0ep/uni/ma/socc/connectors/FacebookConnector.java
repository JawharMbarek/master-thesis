package de.m0ep.uni.ma.socc.connectors;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.ontoware.rdf2go.util.RDFTool;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.FacebookType;
import com.restfb.types.Group;
import com.restfb.types.User;

import de.m0ep.uni.ma.rdf.sioc.Forum;
import de.m0ep.uni.ma.rdf.sioc.UserAccount;
import de.m0ep.uni.ma.socc.SIOCModel;

public class FacebookConnector implements Connector {

    public static final String URL              = "http://facebook.com/";
    public static final String ID               = "com.facebook";
    public static final String NAME             = "Facebook";
    public static final String KEY_ACCESS_TOKEN = ID + ".access_token";

    private Properties config;

    private SIOCModel          model;
    private FacebookClient     client;


    public FacebookConnector() {
    }

    public String getId() {
        return ID;
    }

    public String getUserFriendlyName() {
        return NAME;
    }

    public void init( SIOCModel model, Properties config ) {
        this.model = model;
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

        Forum wall = model.createForum( URL + "me/feed" );
        wall.setSIOCId( "me" );
        wall.setSIOCName( "Wall" );
        
        result.add( wall );

        Connection<FacebookType> groupsConnections = client.fetchConnection(
                "me/groups", FacebookType.class );

        for ( List<FacebookType> myGroups : groupsConnections ) {
            for ( FacebookType type : myGroups ) {
                Group group = client.fetchObject( type.getId(), Group.class );

                Forum forum = model.createForum( URL + group.getId() );
                forum.setSIOCId( group.getId() );
                forum.setSIOCName( group.getName() );
                
                if( null != group.getDescription() )
                    forum.setDCTermsDescription( group.getDescription() );

                if( null != group.getUpdatedTime() )
                    forum.setDCTermsModified( RDFTool.dateTime2String( group
                            .getUpdatedTime() ) );

                result.add( forum );
            }
        }

        return result;
    }

    public UserAccount getUser() {
        UserAccount result = null;
        User user = client.fetchObject( "me", User.class );

        if( null != user ) {
            result = model.createUserAccount( URL + user.getId() );
            result.setSIOCId( user.getId() );
            result.setFOAFName( user.getName() );
            result.setFOAFAccountname( user.getUsername() );
            result.setDCTermsModified( RDFTool.dateTime2String( user
                    .getUpdatedTime() ) );
        }

        return result;
    }

}
