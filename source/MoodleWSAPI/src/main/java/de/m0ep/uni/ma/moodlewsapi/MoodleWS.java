package de.m0ep.uni.ma.moodlewsapi;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import de.m0ep.uni.ma.moodlewsapi.exception.RESTException;
import de.m0ep.uni.ma.moodlewsapi.model.CourseRecord;
import de.m0ep.uni.ma.moodlewsapi.model.LoginResult;
import de.m0ep.uni.ma.moodlewsapi.model.UserRecord;

public class MoodleWS extends RESTClient {
    public static final Logger LOG       = Logger.getLogger( MoodleWS.class
                                                 .getName() );

    private String             moodleURL  = null;

    private int                client     = Integer.MIN_VALUE;
    private String             sessionKey = null;

    private Gson               gson       = null;

    public MoodleWS( final String url ) {
        Preconditions.checkNotNull( url, "Url can not be null" );

        this.moodleURL = url;
        this.gson = new Gson();

        this.open();
    }

    private String callFunction( String function, Map<String, String> params )
            throws RESTException {
        
        if( null == params )
            params = new HashMap<String, String>();
        
        params.put( "wsfunction", function );
        params.put( "wsformatout", "json" );
        
        if(isLoggedIn()){
            params.put( "client", Integer.toString( client ) );
            params.put( "sesskey", sessionKey );
        }
        
        return doGETRequest( moodleURL, params );
    }

    public void login( final String username, final String password )
            throws RESTException {
        Preconditions.checkNotNull( username, "Username can not be null" );
        Preconditions.checkNotNull( password, "Password can not be null" );

        Map<String, String> params = new HashMap<String, String>();
        params.put( "username", username );
        params.put( "password", password );

        String response = callFunction( "login", params );
        LoginResult result = gson.fromJson( response, LoginResult.class );
        client = result.getClient();
        sessionKey = result.getSessionKey();
    }

    public boolean isLoggedIn() {
        return Integer.MIN_VALUE != client && null != sessionKey;
    }

    public boolean logout() throws RESTException {
        if( !isLoggedIn() )
            return true; // we already are loggedout

        String response = callFunction( "logout", null );

        return Boolean.parseBoolean( response );
    }

    public int getMyId() throws RESTException {
        String response = callFunction( "get_my_id", null );
        return Integer.parseInt( response );
    }

    public UserRecord getUserById( final int id ) throws RESTException {
        Map<String, String> params = new HashMap<String, String>();
        params.put( "userinfo", Integer.toString( id ) );
        String response = callFunction( "get_user_byid", params );

        Collection<UserRecord> records = gson.fromJson( response,
                new TypeToken<Collection<UserRecord>>() {
                }.getType() );

        return records.iterator().next();
    }
    
    public Collection<CourseRecord> getMyCourses( int id ) throws RESTException {
        Map<String, String> params = new HashMap<String, String>();
        params.put( "uid", Integer.toString( id ) );
        params.put( "sort", "asc" );
        String response = callFunction( "get_my_courses", params );
        System.out.println( response );
        
        Type token = new TypeToken<Collection<CourseRecord>>() {
        }.getType();
        Collection<CourseRecord> cources = gson.fromJson( response, token );
        
        return cources;
    }
}
