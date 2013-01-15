package de.m0ep.uni.ma.moodlewsapi;

import java.util.logging.Logger;

import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;

import de.m0ep.uni.ma.moodlewsapi.exception.RESTException;
import de.m0ep.uni.ma.moodlewsapi.model.LoginResult;

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

    private String callFunction( String function, HttpParams params )
            throws RESTException {
        
        if(null != params)
            params = new BasicHttpParams();
        
        params.setParameter( "wsfunction", function );
        params.setParameter( "wsformatout", "json" );
        
        // json&wsfunction=get_all_forums&client=<clientid>&sesskey=<sessionkey>&fieldname=&fieldvalue=
        if(isLoggedIn()){
            params.setParameter( "client", client );
            params.setParameter( "sesskey", sessionKey );
        }
        
        return doGETRequest( moodleURL, params );
    }

    public void login( final String username, final String password )
            throws RESTException {
        Preconditions.checkNotNull( username, "Username can not be null" );
        Preconditions.checkNotNull( password, "Password can not be null" );

        HttpParams params = new BasicHttpParams();
        params.setParameter( "username", username );
        params.setParameter( "password", password );

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
}
