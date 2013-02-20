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
import de.m0ep.socc.connectors.MoodleConnector;

public class SOCCTest {

    private static Connector connector;

    /**
     * @param args
     */
    public static void main( String[] args ) {
        Model model = RDF2Go.getModelFactory().createModel();
        model.open();

        Properties config = new Properties();
        config.put(
		FacebookConnector.CONFIG_ACCESS_TOKEN,
		"AAACEdEose0cBAEAslVwUVZBVqRZBVRbpFVwyICwErRFGVVkU9sPiTwiEFf4WZCkzK7mj6pNGZC37w63CKBmrrR5aDaWYtLZAR3ZAJVjZArGKzKOQYa1I4z6");
	config.put(FacebookConnector.CONFIG_CLIENT_ID, "218182098322396");
	config.put(FacebookConnector.CONFIG_CLIENT_SECRET,
		"7b80b9d7265c719e1d9efe112e4dbada");
	config.put(MoodleConnector.CONFIG_MOODLE_URL,
		"http://localhost/florian/moodle24/");
	config.put(MoodleConnector.CONFIG_USERNAME, "admin");
	config.put(MoodleConnector.CONFIG_PASSWORD, "admin");

	connector = new FacebookConnector("facebook", model, config);
	// connector = new MoodleConnector("Moodle", model, config);

        UserAccount user = connector.getUser();
	printUser(user);

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
	    System.out.println("canpublish: " + connector.canPublishOn(forum));
            Iterator<Post> posts = connector.getPosts( forum );

            int ctr = 0;
            while ( posts.hasNext() ) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>");
                Post post = (Post) posts.next();
		printPost(post);
                
		if (5 == ctr++)
                    break;
            }

	    System.out.println("====================");
	    break;
        }

	// doPost();

        model.dump();
    }

    private static void doPost() {
	Post p = new Post(connector.getModel(), true);
	p.setContent("Hallo, Welt! :>");
	connector.publishPost(p, connector.getForums().next());
	Post.deleteAllProperties(p.getModel(), p);
    }

    static void printUser(UserAccount user) {
	System.out.println(user);
	System.out.println(SIOCThing.getAllId_as(user.getModel(), user)
		.firstValue());
	System.out.println(user.getAllName_as().firstValue());
	System.out.println(user.getAllAccountname_as().firstValue());
	System.out.println(user.getAllAccountservicehomepage_as().firstValue());
    }

    static void printPost(Item post) {
	System.out.println("Post-----------------");
	System.out.println(post);
	System.out.println(post.getAllId_as().firstValue());
	System.out.println(post.getAllContent_as().firstValue());
	System.out.println(post.getAllCreated_as().firstValue());
	System.out.println("canreply: "
		+ connector.canReplyOn(Post.getInstance(post.getModel(),
			post.getResource())));

	if (post.hasCreator()) {
	    System.out.println(">From----------------");
	    printUser(post.getAllCreator_as().firstValue());
	}

	if (post.hasAttachment()) {
	    System.out.println(">Attachments---------");

	    ClosableIterator<Node> attachments = post.getAllAttachment_asNode();
	    while (attachments.hasNext()) {
		Node node = (Node) attachments.next();
		System.out.println(node);

	    }
	    attachments.close();
	}

	if (post.hasReply()) {
	    System.out.println(">Replies-------------");

	    ClosableIterator<Item> replies = post.getAllReply();

	    while (replies.hasNext()) {
		Item item = (Item) replies.next();
		printPost(item);
	    }
	    replies.close();
	}
    }
}
