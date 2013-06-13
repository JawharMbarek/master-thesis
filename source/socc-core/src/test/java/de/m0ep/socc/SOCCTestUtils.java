package de.m0ep.socc;

import java.util.Iterator;

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.ontoware.rdf2go.model.node.Node;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Item;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.Thread;
import org.rdfs.sioc.UserAccount;

import de.m0ep.socc.exceptions.ConnectorException;

public class SOCCTestUtils {

    private static final boolean WRITE_OUTPUT = true;

    public static void printConnector(IConnector connector)
	    throws ConnectorException {
	System.out.println();
	System.out.println(connector.getSite().toString());
	System.out.println("=====================================");
	System.out.println("=====================================");

	UserAccount user = connector.getUserAccount();
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
		connector.pollPosts(forum, 25);
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
		    connector.pollPosts(thread, 25);
		    listPosts(connector, thread, 2);
		}
	    }
	    printWithIndent("====================", 0);
	    printWithIndent("", 0);

	    // break;
	}

    }

    public static void listPosts(IConnector connector, Container container,
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

    public static void printUser(UserAccount user, int indent) {
	printWithIndent("uri:      " + user, indent);
	printWithIndent("id:       " + user.getId(), indent);
	printWithIndent("name:     " + user.getName(), indent);
	printWithIndent("username: " + user.getAccountName(), indent);
	printWithIndent("profile:  " + user.getAccountServiceHomepage(), indent);
    }

    public static void printPost(IConnector connector, Item post, int indent)
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

    public static void printWithIndent(String text, int val) {
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
