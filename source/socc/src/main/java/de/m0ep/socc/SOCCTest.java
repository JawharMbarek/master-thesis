package de.m0ep.socc;

import java.util.Iterator;
import java.util.Properties;

import org.ontoware.rdf2go.RDF2Go;
import org.ontoware.rdf2go.model.Model;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.UserAccount;

import de.m0ep.socc.connectors.FacebookConnector;

public class SOCCTest {

    /**
     * @param args
     */
    public static void main( String[] args ) {
        Model model = RDF2Go.getModelFactory().createModel();
        model.open();

        Properties config = new Properties();
        config.put(
                "access_token",
                "AAACEdEose0cBAFq5l3LqyvQTZBjmrhXMe5cHkM4MRMH4xuXRauIzOptD6VebaQVK0a8lrlIddXnycxXYBYeD2LubX5JLOKBsNOaFhKqmhpafWIbJy" );

        Connector connector = new FacebookConnector( "facebook", model, config );

        UserAccount user = connector.getUser();

        System.out.println( user.getAllEmail_as().firstValue() );
        System.out.println( user.getAllEmailsha1_as().firstValue() );
        System.out.println( user.getAllAccountname_as().firstValue() );
        System.out.println( user.getAllName_as().firstValue() );

        Iterator<Forum> forums = connector.getForums();

        while ( forums.hasNext() ) {
            Forum forum = (Forum) forums.next();

            System.out.println( "\n" );
            System.out.println( forum );
            System.out.println( forum.getAllId_as().firstValue() );
            System.out.println( forum.getAllName_as().firstValue() );
            System.out.println( forum.getAllDescription_as().firstValue() );
            System.out.println( forum.getAllModified_as().firstValue() );
            System.out.println( forum.getAllHost_as().firstValue() );
            System.out.println( "-----------" );
            Iterator<Post> posts = connector.getPosts( forum );

            int ctr = 0;
            while ( posts.hasNext() ) {
                Post post = (Post) posts.next();

                if( 10 == ctr++ )
                    break;
            }

            System.out.println( "===========" );
        }

        model.dump();
    }

}
