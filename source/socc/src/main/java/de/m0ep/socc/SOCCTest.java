package de.m0ep.socc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.ontoware.rdf2go.RDF2Go;
import org.ontoware.rdf2go.exception.ModelRuntimeException;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.Syntax;
import org.ontoware.rdf2go.model.node.Node;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Item;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.SIOCThing;
import org.rdfs.sioc.Thread;
import org.rdfs.sioc.UserAccount;

import de.m0ep.socc.connectors.AbstractConnectorConfig;
import de.m0ep.socc.connectors.IConnector;
import de.m0ep.socc.connectors.facebook.FacebookConnectorConfig;
import de.m0ep.socc.connectors.facebook.FacebookConnectorFactory;
import de.m0ep.socc.connectors.google.plus.GooglePlusConnectorConfig;
import de.m0ep.socc.connectors.google.plus.GooglePlusConnectorFactory;
import de.m0ep.socc.connectors.moodle.MoodleConnectorConfig;
import de.m0ep.socc.connectors.moodle.MoodleConnectorFactory;

public class SOCCTest {

    // private static Connector connector;
    private static final boolean WRITE_DUMP = true;
    private static final boolean WRITE_OUTPUT = true;
    private static final int MAX_NEW_POSTS_ON_POLL = 30;

    /**
     * @param args
     */
    public static void main(String[] args) {
	Model model = RDF2Go.getModelFactory().createModel();
	model.open();

	File dump = new File("dump.rdf");

	if (dump.exists()) {
	    try {
		model.readFrom(new FileReader(dump));
	    } catch (ModelRuntimeException e) {
		e.printStackTrace();
	    } catch (FileNotFoundException e) {
		e.printStackTrace();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	    // model.dump();
	}

	Map<String, Object> fbParams = new HashMap<String, Object>();
	fbParams.put(AbstractConnectorConfig.MAX_NEW_POSTS_ON_POLL,
		MAX_NEW_POSTS_ON_POLL);
	fbParams.put(
		FacebookConnectorConfig.ACCESS_TOKEN,
		"AAACEdEose0cBAOM7W9bMuGUOwD3u4yml1t8THNKEZBB20FwwmoWegdg6ew4CAdrIanZCdQCFSDCFltiL9oSZBEwl1F0ZBQXhjxFsZCCFThfNliA46vWNA");
	fbParams.put(FacebookConnectorConfig.CLIENT_ID, "218182098322396");
	fbParams.put(FacebookConnectorConfig.CLIENT_SECRET,
		"7b80b9d7265c719e1d9efe112e4dbada");

	Map<String, Object> mdlParams = new HashMap<String, Object>();
	fbParams.put(AbstractConnectorConfig.MAX_NEW_POSTS_ON_POLL,
		MAX_NEW_POSTS_ON_POLL);
	mdlParams.put(MoodleConnectorConfig.URL,
		"http://localhost/florian/moodle24/");
	mdlParams.put(MoodleConnectorConfig.USERNAME, "admin");
	mdlParams.put(MoodleConnectorConfig.PASSWORD, "admin");

	Map<String, Object> gpParams = new HashMap<String, Object>();
	gpParams.put(AbstractConnectorConfig.MAX_NEW_POSTS_ON_POLL,
		MAX_NEW_POSTS_ON_POLL);
	gpParams.put(GooglePlusConnectorConfig.CLIENT_ID,
		"733024832603-patciplam4cqq0dnv7a5qdhuq262n6ia.apps.googleusercontent.com");
	gpParams.put(GooglePlusConnectorConfig.CLIENT_SECRET,
		"LckucP4MA1jJsZQKjk9okhAu");
	gpParams.put(GooglePlusConnectorConfig.ACCESS_TOKEN,
		"ya29.AHES6ZSCX94Qwhg7_Zzf3Nuyk2DTd76HBGxXB4OcG695Qcg");
	gpParams.put(GooglePlusConnectorConfig.REFRESH_TOKEN,
		"1/9dxT-o_8JnA4gUxm1q0XwCgjA6oz4kcFfZxN1pyJCVc");

	FacebookConnectorFactory fbFactory = new FacebookConnectorFactory();
	GooglePlusConnectorFactory gpFactory = new GooglePlusConnectorFactory();
	MoodleConnectorFactory mdlFactory = new MoodleConnectorFactory();

	IConnector[] connectors = {
		fbFactory.createConnector("facebook", model, fbParams),
		mdlFactory.createConnector("moodle", model, mdlParams),
		gpFactory.createConnector("google+", model, gpParams) };

	for (IConnector connector : connectors) {
	    printConnector(connector);
	}

	if (WRITE_DUMP) {
	    try {
		model.writeTo(new FileOutputStream(dump), Syntax.RdfXml);
	    } catch (ModelRuntimeException e) {
		e.printStackTrace();
	    } catch (FileNotFoundException e) {
		e.printStackTrace();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
    }

    private static void printConnector(IConnector connector) {
	System.out.println();
	System.out.println(connector.getURL());
	System.out.println("=====================================");
	System.out.println("=====================================");

	UserAccount user = connector.getLoginUser();
	printUser(user, 0);

	Iterator<Forum> forums = connector.getForums();

	while (forums.hasNext()) {
	    Forum forum = (Forum) forums.next();

	    printWithIndent("Forum===============", 0);
	    printWithIndent("uri:        " + forum, 0);
	    printWithIndent("id:         " + forum.getAllId_as().firstValue(),
		    0);
	    printWithIndent(
		    "name:       " + forum.getAllName_as().firstValue(), 0);
	    printWithIndent("desc:       "
		    + forum.getAllDescription_as().firstValue(), 0);
	    printWithIndent("mod:        "
		    + forum.getAllModified_as().firstValue(), 0);
	    printWithIndent(
		    "host:       " + forum.getAllHost_as().firstValue(), 0);
	    printWithIndent("canPublishOn: " + connector.canPublishOn(forum), 0);
	    printWithIndent("hasPost: " + connector.hasPosts(forum), 0);

	    if (connector.hasPosts(forum)) {
		connector.pollNewPosts(forum);
		listPosts(connector, forum, 1);
	    }

	    Iterator<Thread> threads = connector.getThreads(forum);

	    while (threads.hasNext()) {
		Thread thread = (Thread) threads.next();

		printWithIndent("Thread..............", 1);
		printWithIndent("uri:        " + thread, 1);
		printWithIndent("id:         "
			+ thread.getAllId_as().firstValue(), 1);
		printWithIndent("name:       "
			+ thread.getAllName_as().firstValue(), 1);
		printWithIndent("mod:        "
			+ thread.getAllModified_as().firstValue(), 1);
		printWithIndent("parent:     "
			+ thread.getAllParent_as().firstValue(), 1);
		printWithIndent("creator:    "
			+ thread.getAllCreator_as().firstValue(), 1);
		printWithIndent(
			"canpublish: " + connector.canPublishOn(thread), 1);
		printWithIndent("hasPost: " + connector.hasPosts(thread), 1);

		if (connector.hasPosts(thread)) {
		    connector.pollNewPosts(thread);
		    listPosts(connector, thread, 2);
		}
	    }
	    printWithIndent("====================", 0);
	    printWithIndent("", 0);

	    // break;
	}

    }

    private static void listPosts(IConnector connector, Container container,
	    int indent) {
	Iterator<Post> posts = connector.getPosts(container);

	int ctr = 0;
	while (posts.hasNext()) {
	    Post post = (Post) posts.next();
	    if (!post.hasReply()) // print only top posts, replies are printed
				  // later
		printPost(connector, post, indent);

	    if (5 == ctr++)
		break;
	}
    }

    static void printUser(UserAccount user, int indent) {
	printWithIndent("uri:      " + user, indent);
	printWithIndent(
		"id:       "
			+ SIOCThing.getAllId_as(user.getModel(), user)
				.firstValue(), indent);
	printWithIndent("name:     " + user.getAllName_as().firstValue(),
		indent);
	printWithIndent(
		"username: " + user.getAllAccountname_as().firstValue(), indent);
	printWithIndent("profile:  "
		+ user.getAllAccountservicehomepage_as().firstValue(), indent);
    }

    static void printPost(IConnector connector, Item post, int indent) {
	printWithIndent("Post-----------------", indent);
	printWithIndent("uri:       " + post, indent);
	printWithIndent("id:        " + post.getAllId_as().firstValue(), indent);
	printWithIndent("content:   " + post.getAllContent_as().firstValue(),
		indent);
	printWithIndent("created:   " + post.getAllCreated_as().firstValue(),
		indent);
	printWithIndent("mod:       " + post.getAllModified_as().firstValue(),
		indent);
	printWithIndent("container: " + post.getAllContainer_as().firstValue(),
		indent);
	if (post.hasReplyof())
	    printWithIndent("parent:    "
		    + post.getAllReplyof_as().firstValue(), indent);
	printWithIndent(
		"canreply:  "
			+ connector.canReplyOn(Post.getInstance(
				post.getModel(), post.getResource())), indent);

	if (post.hasCreator()) {
	    printWithIndent("Post.From----------------", indent);
	    printUser(post.getAllCreator_as().firstValue(), indent + 1);
	}

	if (post.hasAttachment()) {
	    printWithIndent("Post.Attachments---------", indent);

	    ClosableIterator<Node> attachments = post.getAllAttachment_asNode();
	    while (attachments.hasNext()) {
		Node node = (Node) attachments.next();
		printWithIndent(node.toString(), indent);

	    }
	    attachments.close();
	}

	if (post.hasReply()) {
	    printWithIndent("Post.Replies-------------", indent);

	    ClosableIterator<Item> replies = post.getAllReply();

	    while (replies.hasNext()) {
		Item item = (Item) replies.next();
		printPost(connector, item, indent + 1);
	    }
	    replies.close();
	}
    }

    static void printWithIndent(String text, int val) {
	String indent = "";
	while (0 < val) {
	    indent += "  ";
	    val--;
	}

	if (WRITE_OUTPUT)
	    System.out.println(indent + text);
    }
}
