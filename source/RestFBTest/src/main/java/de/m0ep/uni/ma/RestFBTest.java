package de.m0ep.uni.ma;

import java.util.Properties;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.Post;

public class RestFBTest {

    private static final Properties config = new Properties();

    /**
     * @param args
     * @throws Throwable
     */
    public static void main( String[] args ) throws Throwable {
        config.load( ClassLoader.getSystemResourceAsStream( "local.properties" ) );
        
        FacebookClient client = new DefaultFacebookClient(
                config.getProperty( "facebook.access_token" ) );

        Connection<Post> posts = client
                .fetchConnection( "me/posts",
                Post.class );

        for ( Post post : posts.getData() ) {
            System.out.println( post );
        }

        // client.publish( "me/feed", FacebookType.class, Parameter.with(
        // "message", "This post is powered by RestFB API" ) );
    }

}
