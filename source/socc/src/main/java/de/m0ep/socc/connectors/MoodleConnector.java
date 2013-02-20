package de.m0ep.socc.connectors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.codec.digest.DigestUtils;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.util.RDFTool;
import org.ontoware.rdf2go.vocabulary.RDF;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.SIOC;
import org.rdfs.sioc.SIOCThing;
import org.rdfs.sioc.Site;
import org.rdfs.sioc.Thread;
import org.rdfs.sioc.UserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import de.m0ep.moodlews.soap.CourseRecord;
import de.m0ep.moodlews.soap.ForumDiscussionRecord;
import de.m0ep.moodlews.soap.ForumPostDatum;
import de.m0ep.moodlews.soap.ForumPostRecord;
import de.m0ep.moodlews.soap.ForumRecord;
import de.m0ep.moodlews.soap.LoginReturn;
import de.m0ep.moodlews.soap.Mdl_soapserverBindingStub;
import de.m0ep.moodlews.soap.UserRecord;
import de.m0ep.socc.AbstractConnector;
import de.m0ep.socc.utils.RDFUtils;
import de.m0ep.socc.utils.StringUtils;

public class MoodleConnector extends AbstractConnector {
    private Logger LOG = LoggerFactory.getLogger(MoodleConnector.class);

    public static final String CONFIG_MOODLE_URL = "moodle_url";
    public static final String CONFIG_USERNAME = "username";
    public static final String CONFIG_PASSWORD = "password";
    public static final String MOODLEWS_PATH = "wspp/service_pp2.php";

    private static final String URI_USER_PATH = "user/";
    private static final String URI_FORUM_PATH = "forum/";
    private static final String URI_THREAD_PATH = "discussion/";
    private static final String URI_POST_PATH = "post/";

    private String moodle_url;
    private String username;
    private String password;
    private Mdl_soapserverBindingStub moodle;

    private int client;
    private String sesskey;
    private int myId;
    Map<Integer, CourseRecord> courses = new HashMap<Integer, CourseRecord>();

    public MoodleConnector(String id, Model model, Properties config) {
	super(id, model, config);

	Preconditions.checkArgument(config.containsKey(CONFIG_MOODLE_URL),
		"no moodle url in config");
	Preconditions.checkArgument(config.containsKey(CONFIG_USERNAME),
		"no username in config");
	Preconditions.checkArgument(config.containsKey(CONFIG_PASSWORD),
		"no password in config");

	this.moodle_url = RDFUtils.endsWithSlash(config.getProperty(
		CONFIG_MOODLE_URL, ""));
	this.username = config.getProperty(CONFIG_USERNAME, "");
	this.password = config.getProperty(CONFIG_PASSWORD, "");

	this.moodle = new Mdl_soapserverBindingStub(getURL() + MOODLEWS_PATH,
		getURL() + "/wspp/wsdl2", false);

	LoginReturn login = moodle.login(username, password);
	this.client = login.getClient();
	this.sesskey = login.getSessionkey();
	this.myId = moodle.get_my_id(client, sesskey);
    }

    @Override
    public String getURL() {
	return moodle_url;
    }

    @Override
    public Site getSite() {
	URI uri = RDFUtils.createURI(getURL());

	if (!Site.hasInstance(getModel(), uri)) {
	    Site result = new Site(getModel(), uri, true);
	    result.setName("Moodle");
	    return result;
	}

	return Site.getInstance(getModel(), uri);
    }

    @Override
    public UserAccount getUser() {
	return getUser(myId);
    }

    @Override
    public Iterator<Forum> getForums() {
	List<Forum> result = new ArrayList<Forum>();
	ForumRecord[] forums = moodle.get_all_forums(client, sesskey, "", "");

	for (ForumRecord forum : forums) {
	    URI uri = RDFUtils.createURI(getURL() + URI_FORUM_PATH
		    + forum.getId());

	    if (!Forum.hasInstance(getModel(), uri)) {
		Forum entry = new Forum(getModel(), uri, true);
		CourseRecord course = getCourse(forum.getCourse());

		entry.setId(Integer.toString(forum.getId()));
		entry.setModified(RDFTool.dateTime2String(new Date((long) forum
			.getTimemodified() * 1000)));
		entry.setDescription(forum.getIntro());
		entry.setName(course.getFullname() + "/" + forum.getName());
		entry.setHost(getSite());
		getSite().addHostof(entry);

		result.add(entry);
	    } else {
		result.add(Forum.getInstance(getModel(), uri));
	    }
	}

	return Collections.unmodifiableList(result).iterator();
    }

    public Iterator<Thread> getThreads(Forum forum) {
	Preconditions.checkArgument(forum.hasHost(getSite()));

	List<Thread> result = new ArrayList<Thread>();
	int forumid = Integer.parseInt(forum.getAllId_as().firstValue());
	ForumDiscussionRecord[] forumDiscussions = moodle
		.get_forum_discussions(client, sesskey, forumid, 999);

	for (ForumDiscussionRecord discussion : forumDiscussions) {
	    URI uri = RDFUtils.createURI(getURL() + URI_THREAD_PATH
		    + discussion.getId());

	    if (!Thread.hasInstance(getModel(), uri)) {
		Thread entry = new Thread(getModel(), uri, true);

		entry.setId(Integer.toString(discussion.getId()));
		entry.setParent(forum);
		forum.addParentof(entry);
		entry.setModified(RDFTool.dateTime2String(new Date(
			(long) discussion.getTimemodified() * 1000)));

		entry.setCreator(getUser(discussion.getUserid()));
		entry.setName(discussion.getName());

		result.add(entry);
	    } else {
		result.add(Thread.getInstance(getModel(), uri));
	    }
	}

	return Collections.unmodifiableList(result).iterator();
    }

    public Iterator<Post> getPosts(Container container) {
	Preconditions.checkArgument(canPublishOn(container));
	
	List<Post> result = new ArrayList<Post>();
	int discussionId = Integer.parseInt(container.getAllId_as().firstValue());
	ForumPostRecord[] posts = moodle.get_forum_posts(client, sesskey,
		discussionId, 999);

	for (ForumPostRecord post : posts)
	    addPost(result, post, null, container);

	return Collections.unmodifiableList(result).iterator();
    }

    /* package */void addPost(List<Post> result, final ForumPostRecord post, final Post parent, final Container discussion){
	URI uri = RDFUtils.createURI(getURL() + URI_POST_PATH
 + post.getId());
	
	if (!Post.hasInstance(getModel(), uri)) {
		Post entry = new Post(getModel(), uri, true);

		entry.setId(Integer.toString(post.getId()));
		entry.setContainer(discussion);
		entry.setDiscussion(discussion);
		
		if(null != parent)
		    entry.setReplyof(parent);
		
		entry.setSubject(post.getSubject());

		entry.setContent(StringUtils.stripHTML(post.getMessage()));
		entry.setContentEncoded(post.getMessage());

		entry.setCreated(RDFTool.dateTime2String(new Date((long) post
			.getCreated() * 1000)));

		entry.setModified(RDFTool.dateTime2String(new Date((long) post
			.getModified() * 1000)));
		
		for(ForumPostRecord comment : post.getChildren()){
		addPost(result, comment, entry, discussion);
		}

		result.add(entry);
	}
	
	result.add(Post.getInstance(getModel(), uri));
    }

    public boolean canPublishOn(Container container) {

	/*
	 * @formatter:off Can publish on this conainer if: - It's a Thread - Has
	 * a parent that is a Forum - The parent forum belongs to this moodle
	 * instance
	 * 
	 * @formatter:on
	 */

	if (getModel().contains(container, RDF.type, SIOC.Thread)) {
	    Thread thread = Thread.getInstance(getModel(),
		    container.getResource());
	    if (thread.hasParent()) {
		Container parent = thread.getAllParent_as().firstValue();
		if (getModel().contains(parent, RDF.type, SIOC.Forum)) {
		    Forum forum = Forum.getInstance(getModel(),
			    parent.getResource());

		    return forum.hasHost(getSite());
		}
	    }
	}

	return false;
    }

    @Override
    public boolean canReplyOn(Post parent) {
	/**
	 * @formatter:off We can reply on this post if: - it has a container
	 *                that is a thread - this thread has a parent Forum -
	 *                the parent forum belongs to this moodle instance
	 * @formatter:on
	 */
	if (parent.hasContainer()) {
	    Container container = parent.getAllContainer_as().firstValue();
	    if (getModel().contains(container, RDF.type, SIOC.Thread)) {
		Thread thread = Thread.getInstance(getModel(),
			container.getResource());
		if (thread.hasParent()) {
		    Container parentContainer = thread.getAllParent_as()
			    .firstValue();
		    if (getModel().contains(parentContainer, RDF.type,
			    SIOC.Forum)) {
			Forum forum = Forum.getInstance(getModel(),
				parentContainer.getResource());
			return forum.hasHost(getSite());
		    }
		}
	    }
	}

	return false;
    }

    public boolean publishPost(Post post, Container container) {
	Preconditions.checkArgument(post.hasContent());
	Preconditions.checkArgument(canPublishOn(container));

	ForumPostDatum datum = new ForumPostDatum(moodle.getNAMESPACE());
	datum.setMessage(post.getAllContent_as().firstValue());
	if (post.hasTitle()) {
	    datum.setSubject(post.getAllTitle_as().firstValue());
	} else if (post.hasSubject()) {
	    datum.setSubject(post.getAllSubject_as().firstValue());
	} else {
	    datum.setSubject("");
	}

	// get first post of the discussion to post a reply
	ForumPostRecord[] posts = moodle.get_forum_posts(client, sesskey,
		Integer.parseInt(container.getAllId_as().firstValue()), 1);

	if (0 != posts.length) {
	    ForumPostRecord[] result = moodle.forum_add_reply(client, sesskey,
		    posts[0].getId(), datum);

	    // TODO add new post to model

	    return null != result && 0 < result.length;
	}

	return false;
    }

    /* package */UserAccount getUser(int id) {
	URI uri = RDFUtils.createURI(getURL() + URI_USER_PATH + id);

	if (!UserAccount.hasInstance(getModel(), uri)) {
	    UserRecord[] users = moodle.get_user_byid(client, sesskey, myId);

	    if (0 < users.length) {
		UserRecord me = users[0];
		UserAccount result = new UserAccount(getModel(), uri, true);
		SIOCThing.setId(getModel(), result,
			Integer.toString(me.getId())); // TODO ad sioc:id to
						       // UserAccount
		SIOCThing.setIsPartOf(getModel(), result, getSite());

		result.setName(me.getFirstname() + " " + me.getLastname());
		result.setAccountname(me.getUsername());
		result.setEmail(RDFUtils.createMailtoURI(me.getEmail()));
		result.setEmailsha1(DigestUtils.sha1Hex(me.getEmail()));
		result.setAccountservicehomepage(RDFUtils.createURI(getURL()
			+ "/user/profile.php?id=" + me.getId()));
		result.setDescription(RDFUtils.createLiteral(me
			.getDescription()));

		return result;
	    }
	}

	return UserAccount.getInstance(getModel(), uri);
    }

    /* package */CourseRecord getCourse(final int id) {
	if (courses.containsKey(id))
	    return courses.get(id);

	CourseRecord[] courseRecords = moodle.get_course_byid(client, sesskey,
		Integer.toString(id));

	CourseRecord course = courseRecords[0];
	courses.put(course.getId(), course);
	return course;
    }
}
