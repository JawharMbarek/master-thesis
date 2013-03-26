package de.m0ep.socc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.ontoware.rdf2go.RDF2Go;
import org.ontoware.rdf2go.exception.ModelRuntimeException;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.Statement;
import org.ontoware.rdf2go.model.Syntax;
import org.ontoware.rdf2go.model.node.Node;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Item;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.SIOCThing;
import org.rdfs.sioc.Thread;
import org.rdfs.sioc.UserAccount;

import com.google.common.collect.Lists;

import de.m0ep.socc.connectors.DefaultConnectorConfig;
import de.m0ep.socc.connectors.IConnector;
import de.m0ep.socc.connectors.exceptions.ConnectorException;
import de.m0ep.socc.connectors.facebook.FacebookConnectorConfig;
import de.m0ep.socc.connectors.facebook.FacebookConnectorFactory;
import de.m0ep.socc.connectors.google.plus.GooglePlusConnectorConfig;
import de.m0ep.socc.connectors.google.plus.GooglePlusConnectorFactory;
import de.m0ep.socc.connectors.google.youtube.YoutubeConnectorV2Config;
import de.m0ep.socc.connectors.google.youtube.YoutubeConnectorV2Factory;
import de.m0ep.socc.connectors.moodle.MoodleConnectorConfig;
import de.m0ep.socc.connectors.moodle.MoodleConnectorFactory;

public class SOCCTest {

    // private static Connector connector;
    private static final boolean WRITE_DUMP = true;
    private static final boolean WRITE_OUTPUT = true;

    /**
     * @param args
     * @throws ConnectorException
     */
    public static void main(String[] args) throws ConnectorException {
	Model model = RDF2Go.getModelFactory().createModel();
	model.open();

	File dump = new File("dump.xml");

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

	Properties config = new Properties();
	try {
	    config.load(SOCCTest.class.getResourceAsStream("/user.properties"));
	} catch (IOException e1) {
	    e1.printStackTrace();
	    System.exit(-1);
	}

	int maxPostPerPoll = Integer.parseInt(config.getProperty(
		"global.postPerPoll", "30"));

	int pollCoolDown = Integer.parseInt(config.getProperty(
		"global.pollCooldown", "300"));

	Map<String, Object> fbParams = new HashMap<String, Object>();
	fbParams.put(DefaultConnectorConfig.MAX_NEW_POSTS_ON_POLL,
		maxPostPerPoll);
	fbParams.put(DefaultConnectorConfig.POLL_COOLDOWN, pollCoolDown);
	fbParams.put(FacebookConnectorConfig.ACCESS_TOKEN,
		config.get("fb.accessToken"));
	fbParams.put(FacebookConnectorConfig.CLIENT_ID,
		config.get("fb.clientID"));
	fbParams.put(FacebookConnectorConfig.CLIENT_SECRET,
		config.get("fb.clientSecret"));

	Map<String, Object> mdlParams = new HashMap<String, Object>();
	mdlParams.put(DefaultConnectorConfig.MAX_NEW_POSTS_ON_POLL,
		maxPostPerPoll);
	mdlParams.put(DefaultConnectorConfig.POLL_COOLDOWN, pollCoolDown);
	mdlParams.put(MoodleConnectorConfig.URL, config.get("mdl.url"));
	mdlParams.put(MoodleConnectorConfig.USERNAME,
		config.get("mdl.username"));
	mdlParams.put(MoodleConnectorConfig.PASSWORD,
		config.get("mdl.password"));

	Map<String, Object> gpParams = new HashMap<String, Object>();
	gpParams.put(DefaultConnectorConfig.MAX_NEW_POSTS_ON_POLL,
		maxPostPerPoll);
	gpParams.put(DefaultConnectorConfig.POLL_COOLDOWN, pollCoolDown);
	gpParams.put(GooglePlusConnectorConfig.CLIENT_ID,
		config.get("gp.clientId"));
	gpParams.put(GooglePlusConnectorConfig.CLIENT_SECRET,
		config.get("gp.clientSecret"));
	gpParams.put(GooglePlusConnectorConfig.ACCESS_TOKEN,
		config.get("gp.accessToken"));
	gpParams.put(GooglePlusConnectorConfig.REFRESH_TOKEN,
		config.get("gp.refreshToken"));

	Map<String, Object> ytParams = new HashMap<String, Object>();
	ytParams.put(DefaultConnectorConfig.MAX_NEW_POSTS_ON_POLL,
		maxPostPerPoll);
	ytParams.put(DefaultConnectorConfig.POLL_COOLDOWN, pollCoolDown);
	ytParams.put(YoutubeConnectorV2Config.EMAIL, config.get("yt.email"));
	ytParams.put(YoutubeConnectorV2Config.PASSWORD,
		config.get("yt.password"));
	ytParams.put(YoutubeConnectorV2Config.USERNAME,
		config.get("yt.username"));
	ytParams.put(YoutubeConnectorV2Config.DEVELOPER_KEY,
		config.get("yt.developerKey"));

	FacebookConnectorFactory fbFactory = new FacebookConnectorFactory();
	GooglePlusConnectorFactory gpFactory = new GooglePlusConnectorFactory();
	MoodleConnectorFactory mdlFactory = new MoodleConnectorFactory();
	YoutubeConnectorV2Factory ytFactory = new YoutubeConnectorV2Factory();

	IConnector[] connectors = { fbFactory.createConnector("facebook",
		model, fbParams)
	// mdlFactory.createConnector("moodle", model, mdlParams),
	// gpFactory.createConnector("google+", model, gpParams)
	// ytFactory.createConnector("youtube", model, ytParams)
	};

	for (IConnector connector : connectors) {
	    printConnector(connector);
	}

	if (WRITE_DUMP) {

	    List<Statement> statements = Lists.newArrayList(model.iterator());

	    Collections.sort(statements, new Comparator<Statement>() {
		public int compare(Statement o1, Statement o2) {
		    return o1.compareTo(o2);
		};
	    });

	    Model writeModel = RDF2Go.getModelFactory().createModel();
	    writeModel.open();
	    writeModel.addAll(statements.iterator());

	    try {
		writeModel.writeTo(new FileOutputStream(dump), Syntax.RdfXml);
	    } catch (ModelRuntimeException e) {
		e.printStackTrace();
	    } catch (FileNotFoundException e) {
		e.printStackTrace();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	    writeModel.close();

	}

	model.close();
    }

    private static void printConnector(IConnector connector)
	    throws ConnectorException {
	System.out.println();
	System.out.println(connector.getURL());
	System.out.println("=====================================");
	System.out.println("=====================================");

	try {
	    System.err.println("test connector");
	    printPost(connector,
		    connector.getPost("100000490230885_629013553791647"), 1);
	} catch (Exception e) {
	    e.printStackTrace();
	}

	UserAccount user = connector.getLoginUser();
	printUser(user, 0);

	Iterator<Forum> forums = connector.getForums().iterator();

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
	    printWithIndent("created:    " + forum.getCreated(), 0);
	    printWithIndent("modified:   " + forum.getModified(), 0);
	    printWithIndent(
		    "host:       " + forum.getAllHost_as().firstValue(), 0);
	    printWithIndent("canPublishOn: " + connector.canPublishOn(forum), 0);
	    printWithIndent("hasPost: " + connector.hasPosts(forum), 0);

	    if (connector.hasPosts(forum)) {
		connector.pollNewPosts(forum);
		listPosts(connector, forum, 1);
	    }

	    Iterator<Thread> threads = connector.getThreads(forum).iterator();

	    while (threads.hasNext()) {
		Thread thread = (Thread) threads.next();

		printWithIndent("Thread..............", 1);
		printWithIndent("uri:        " + thread, 1);
		printWithIndent("id:         "
			+ thread.getAllId_as().firstValue(), 1);
		printWithIndent("name:       "
			+ thread.getAllName_as().firstValue(), 1);
		printWithIndent("created:    " + thread.getCreated(), 1);
		printWithIndent("modified:   "
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
	    int indent) throws ConnectorException {
	Iterator<Post> posts = connector.getPosts(container).iterator();

	int ctr = 0;
	while (posts.hasNext()) {
	    Post post = (Post) posts.next();
	    if (!post.hasReplyof()) // print only top posts, replies are printed
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

    static void printPost(IConnector connector, Item post, int indent)
	    throws ConnectorException {
	printWithIndent("Post-----------------", indent);
	printWithIndent("uri:       " + post, indent);
	printWithIndent("id:        " + post.getAllId_as().firstValue(), indent);
	if (post.hasTitle()) {
	    printWithIndent("Title:     " + post.getTitle(), indent);
	}

	if (post.hasName()) {
	    printWithIndent("Name:      " + post.getName(), indent);
	}

	if (post.hasSubject()) {
	    printWithIndent("Subject:   " + post.getSubject(), indent);
	}
	printWithIndent("content:   " + post.getAllContent_as().firstValue(),
		indent);
	printWithIndent("created:   " + post.getAllCreated_as().firstValue(),
		indent);
	printWithIndent("mod:       " + post.getAllModified_as().firstValue(),
		indent);
	printWithIndent("container: " + post.getAllContainer_as().firstValue(),
		indent);
	if (post.hasReplyof())
	    printWithIndent("replyTo:   " + post.getReplyof(), indent);
	if (post.hasDiscussion())
	    printWithIndent("dsicussion:" + post.getDiscussion(), indent);

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

	// Post reply;
	// try {
	// reply = new Post(post.getModel(), true);
	// reply.setContent("test post " + new Date());
	// reply.setTitle("Test post");
	// connector.replyPost(reply,
	// Post.getInstance(post.getModel(), post.getResource()));
	// Post.deleteAllProperties(post.getModel(), reply);
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// }

    }

    static void printWithIndent(String text, int val) {
	String indent = "";
	while (0 < val) {
	    indent += "    ";
	    val--;
	}
	indent += "|";

	if (WRITE_OUTPUT)
	    System.out.println(indent + text);
    }
}
