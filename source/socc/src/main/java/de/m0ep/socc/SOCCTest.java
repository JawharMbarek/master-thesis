package de.m0ep.socc;

import java.util.Iterator;
import java.util.Properties;

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.ontoware.rdf2go.RDF2Go;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.node.Node;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Item;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.SIOCThing;
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
		"AAACEdEose0cBANEXYyMEZCkwyyY9breh3m6BdfjBtabld8oDcdLYQRtLPseDKF3InRZBqoHj79CivtAZCo9ZA4kFl3dp3Gp9ZCybajCYZC9oh80frOfwlV");

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

		printPost(post);
		System.out.println("~~~~~~~~~~~~~~");
                
		if (15 == ctr++)
                    break;
            }

            System.out.println( "===========" );
        }

        model.dump();
    }

    static void printUser(UserAccount user) {
	System.out.println(user);
	System.out.println("\t" + SIOCThing.getAllId_as(user.getModel(), user));
	System.out.println("\t" + user.getAllName_as().firstValue());
	System.out.println("\t" + user.getAllAccountname_as().firstValue());
	System.out.println("\t"
		+ user.getAllAccountservicehomepage_as().firstValue());
    }

    static void printPost(Item post) {
	System.out.println(post);
	System.out.println("\t" + post.getAllId_as().firstValue());
	System.out.println("\t" + post.getAllContent_as().firstValue());
	System.out.println("\t" + post.getAllCreated_as().firstValue());

	if (post.hasCreator()) {
	    System.out.println("From-----------------");
	    printUser(post.getAllCreator_as().firstValue());
	}

	if (post.hasAttachment()) {
	    System.out.println("Attachments----------");

	    ClosableIterator<Node> attachments = post.getAllAttachment_asNode();
	    while (attachments.hasNext()) {
		Node node = (Node) attachments.next();
		System.out.println("\t" + node);

	    }
	    attachments.close();
	}

	if (post.hasReply()) {
	    System.out.println("Replies--------------");

	    ClosableIterator<Item> replies = post.getAllReply();

	    while (replies.hasNext()) {
		Item item = (Item) replies.next();
		printPost(item);
	    }
	    replies.close();
	}
    }
}
