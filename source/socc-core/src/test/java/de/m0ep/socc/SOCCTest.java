package de.m0ep.socc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

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
import org.rdfs.sioc.Thread;
import org.rdfs.sioc.UserAccount;

import com.google.common.collect.Lists;

import de.m0ep.socc.config.SOCCConfiguration;
import de.m0ep.socc.exceptions.ConnectorException;

public class SOCCTest {

    // private static Connector connector;
    private static final boolean WRITE_DUMP = true;
    private static final boolean WRITE_OUTPUT = true;

    /**
     * @param args
     * @throws ConnectorException
     * @throws IOException
     * @throws URISyntaxException
     */
    public static void main(String[] args) throws ConnectorException,
	    IOException, URISyntaxException {
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

	// Properties config = new Properties();
	// try {
	// config.load(SOCCTest.class.getResourceAsStream("/user.properties"));
	// } catch (IOException e1) {
	// e1.printStackTrace();
	// System.exit(-1);
	// }
	//
	// int maxPostPerPoll = Integer.parseInt(config.getProperty(
	// "global.postPerPoll", "30"));
	//
	// int pollCoolDown = Integer.parseInt(config.getProperty(
	// "global.pollCooldown", "300"));
	//
	// Map<String, Object> fbParams = new HashMap<String, Object>();
	// fbParams.put(DefaultConnectorConfig.MAX_NEW_POSTS_ON_POLL,
	// maxPostPerPoll);
	// fbParams.put(DefaultConnectorConfig.POLL_COOLDOWN, pollCoolDown);
	// fbParams.put(FacebookConnectorConfig.ACCESS_TOKEN,
	// config.get("fb.accessToken"));
	// fbParams.put(FacebookConnectorConfig.CLIENT_ID,
	// config.get("fb.clientID"));
	// fbParams.put(FacebookConnectorConfig.CLIENT_SECRET,
	// config.get("fb.clientSecret"));
	//
	// Map<String, Object> mdlParams = new HashMap<String, Object>();
	// mdlParams.put(DefaultConnectorConfig.MAX_NEW_POSTS_ON_POLL,
	// maxPostPerPoll);
	// mdlParams.put(DefaultConnectorConfig.POLL_COOLDOWN, pollCoolDown);
	// mdlParams.put(MoodleConnectorConfig.URL, config.get("mdl.url"));
	// mdlParams.put(MoodleConnectorConfig.USERNAME,
	// config.get("mdl.username"));
	// mdlParams.put(MoodleConnectorConfig.PASSWORD,
	// config.get("mdl.password"));
	//
	// Map<String, Object> gpParams = new HashMap<String, Object>();
	// gpParams.put(DefaultConnectorConfig.MAX_NEW_POSTS_ON_POLL,
	// maxPostPerPoll);
	// gpParams.put(DefaultConnectorConfig.POLL_COOLDOWN, pollCoolDown);
	// gpParams.put(GooglePlusConnectorConfig.CLIENT_ID,
	// config.get("gp.clientId"));
	// gpParams.put(GooglePlusConnectorConfig.CLIENT_SECRET,
	// config.get("gp.clientSecret"));
	// gpParams.put(GooglePlusConnectorConfig.ACCESS_TOKEN,
	// config.get("gp.accessToken"));
	// gpParams.put(GooglePlusConnectorConfig.REFRESH_TOKEN,
	// config.get("gp.refreshToken"));
	//
	// Map<String, Object> ytParams = new HashMap<String, Object>();
	// ytParams.put(DefaultConnectorConfig.MAX_NEW_POSTS_ON_POLL,
	// maxPostPerPoll);
	// ytParams.put(DefaultConnectorConfig.POLL_COOLDOWN, pollCoolDown);
	// ytParams.put(YoutubeV2ConnectorConfig.PASSWORD,
	// config.get("yt.password"));
	// ytParams.put(YoutubeV2ConnectorConfig.USERNAME,
	// config.get("yt.username"));
	// ytParams.put(YoutubeV2ConnectorConfig.DEVELOPER_KEY,
	// config.get("yt.developerKey"));
	//
	//
	//
	// IConnector[] connectors = {
	// socc.createConnector("YoutubeConnectorFactory_v2",
	// "youtube-test", ytParams),
	// socc.createConnector("GooglePlusConnectorFactory_1.0",
	// "googleplus-test", gpParams),
	// socc.createConnector("MoodleConnectorFactory_2.4",
	// "moodle-test", mdlParams),
	// socc.createConnector("FacebookConnectorFactory_1.0",
	// "facebook-test", fbParams) };

	SOCC socc = null;
	File soccConfigFile = new File(SOCCTest.class.getResource(
		"/soccConfiguration.json").toURI());

	if (soccConfigFile.exists()) {
	    SOCCConfiguration soccConfiguration = SOCCConfiguration
		    .load(soccConfigFile);
	    socc = new SOCC(model, soccConfiguration);
	} else {
	    socc = new SOCC(model);
	}

	List<IConnector> connectors = new ArrayList<IConnector>();
	for (String id : socc.getConnectorIds()) {
	    IConnector connector = socc.getConnector(id);
	    connectors.add(connector);
	    System.err.println(connector.getId());
	}

	socc.getConfiguration().save(soccConfigFile);

	for (IConnector connector : connectors) {
	    printConnector(connector);
	}

	if (WRITE_DUMP) {
	    List<Statement> statements = Lists.newArrayList(model.iterator());

	    Collections.sort(statements, new Comparator<Statement>() {
		@Override
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

	UserAccount user = connector.getLoginUser();
	printUser(user, 0);

	Iterator<Forum> forums = connector.getForums().iterator();

	while (forums.hasNext()) {
	    Forum forum = forums.next();

	    printWithIndent("Forum===============", 0);
	    printWithIndent("uri:        " + forum, 0);
	    printWithIndent("id:         " + forum.getId(), 0);
	    printWithIndent("name:       " + forum.getName(), 0);
	    printWithIndent("desc:       " + forum.getDescription(), 0);
	    printWithIndent("created:    " + forum.getCreated(), 0);
	    printWithIndent("modified:   " + forum.getModified(), 0);
	    printWithIndent("host:       " + forum.getHost(), 0);
	    printWithIndent("canPublishOn: " + connector.canPublishOn(forum), 0);
	    printWithIndent("hasPost: " + connector.hasPosts(forum), 0);

	    if (connector.hasPosts(forum)) {
		connector.pollNewPosts(forum);
		listPosts(connector, forum, 1);
	    }

	    Iterator<Thread> threads = connector.getThreads(forum).iterator();

	    while (threads.hasNext()) {
		Thread thread = threads.next();

		printWithIndent("Thread..............", 1);
		printWithIndent("uri:        " + thread, 1);
		printWithIndent("id:         " + thread.getId(), 1);
		printWithIndent("name:       " + thread.getName(), 1);
		printWithIndent("created:    " + thread.getCreated(), 1);
		printWithIndent("modified:   " + thread.getModified(), 1);
		printWithIndent("parent:     " + thread.getParent(), 1);
		printWithIndent("creator:    " + thread.getCreator(), 1);
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
	    Post post = posts.next();
	    if (!post.hasReplyOf()) // print only top posts, replies are printed
				    // later
		printPost(connector, post, indent);

	    if (5 == ctr++)
		break;
	}
    }

    static void printUser(UserAccount user, int indent) {
	printWithIndent("uri:      " + user, indent);
	printWithIndent("id:       " + user.getId(), indent);
	printWithIndent("name:     " + user.getName(), indent);
	printWithIndent("username: " + user.getAccountName(), indent);
	printWithIndent("profile:  " + user.getAccountServiceHomepage(), indent);
    }

    static void printPost(IConnector connector, Item post, int indent)
	    throws ConnectorException {
	printWithIndent("Post-----------------", indent);
	printWithIndent("uri:       " + post, indent);
	printWithIndent("id:        " + post.getId(), indent);
	if (post.hasTitle()) {
	    printWithIndent("Title:     " + post.getTitle(), indent);
	}

	if (post.hasName()) {
	    printWithIndent("Name:      " + post.getName(), indent);
	}

	if (post.hasSubject()) {
	    printWithIndent("Subject:   " + post.getSubject(), indent);
	}
	printWithIndent("content:   " + post.getContent(), indent);
	printWithIndent("created:   " + post.getCreated(), indent);
	printWithIndent("mod:       " + post.getModified(), indent);
	printWithIndent("container: " + post.getContainer(), indent);
	if (post.hasReplyOf())
	    printWithIndent("replyTo:   " + post.getReplyOf(), indent);
	if (post.hasDiscussions())
	    printWithIndent("dsicussion:" + post.getDiscussion(), indent);

	printWithIndent(
		"canreply:  "
			+ connector.canReplyOn(Post.getInstance(
				post.getModel(), post.getResource())), indent);

	if (post.hasCreators()) {
	    printWithIndent("Post.From----------------", indent);
	    printUser(post.getCreator(), indent + 1);
	}

	if (post.hasAttachments()) {
	    printWithIndent("Post.Attachments---------", indent);

	    ClosableIterator<Node> attachments = post
		    .getAllAttachments_asNode();
	    while (attachments.hasNext()) {
		Node node = attachments.next();
		printWithIndent(node.toString(), indent + 1);

	    }
	    attachments.close();
	}

	if (post.hasReplies()) {
	    printWithIndent("Post.Replies-------------", indent);

	    ClosableIterator<Item> replies = post.getAllReplies();

	    while (replies.hasNext()) {
		Item item = replies.next();
		printPost(connector, item, indent + 1);
	    }
	    replies.close();
	}
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
