/*
 * The MIT License (MIT) Copyright © 2013 Florian Müller
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the “Software”), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package de.m0ep.socc.connectors.moodle;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.Statement;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.model.node.Variable;
import org.ontoware.rdf2go.util.RDFTool;
import org.ontoware.rdf2go.vocabulary.RDF;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.SIOC;
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
import de.m0ep.socc.connectors.AbstractConnector;
import de.m0ep.socc.connectors.exceptions.AuthenticationException;
import de.m0ep.socc.connectors.exceptions.ConnectorException;
import de.m0ep.socc.connectors.exceptions.NetworkException;
import de.m0ep.socc.utils.ConfigUtils;
import de.m0ep.socc.utils.DateUtils;
import de.m0ep.socc.utils.RDF2GoUtils;
import de.m0ep.socc.utils.SIOCUtils;
import de.m0ep.socc.utils.StringUtils;

public class MoodleConnector extends AbstractConnector {
    private static final Logger LOG = LoggerFactory
	    .getLogger(MoodleConnector.class);

    public static final String MOODLEWS_PATH = "wspp/service_pp2.php";

    private static final String URI_USER_PATH = "user/";
    private static final String URI_FORUM_PATH = "forum/";
    private static final String URI_THREAD_PATH = "discussion/";
    private static final String URI_POST_PATH = "post/";

    private Mdl_soapserverBindingStub moodle;
    private MoodleConnectorConfig mdlConfig;

    private int client;
    private String sesskey;
    private int myId;

    private Map<Integer, CourseRecord> courses = new HashMap<Integer, CourseRecord>();

    private boolean isMoodleWSRunning() {
	if (null == moodle)
	    return false;

	try {
	    new URL(moodle.getURL()).openConnection().connect();
	} catch (MalformedURLException e) {
	    throw new ConnectorException("Invalid URL to moodle", e);
	} catch (IOException e) {
	    LOG.warn(e.getMessage(), e);
	    return false;
	}

	return true;
    }

    private void tryLogin() throws ConnectorException {
	if (null != sesskey)
	    moodle.logout(client, sesskey);

	LoginReturn login = moodle.login(mdlConfig.getUsername(),
		mdlConfig.getPassword());

	if (null == login) {
	    if (isMoodleWSRunning()) {
		LOG.error("Login failed");
		throw new AuthenticationException("invalid user details");
	    }

	    LOG.warn("No connection to " + moodle.getURL());
	    throw new NetworkException("No connection to " + moodle.getURL());
	}

	this.client = login.getClient();
	this.sesskey = login.getSessionkey();
    }

    private boolean isSessionExpired() {
	return 0 == moodle.get_my_id(client, sesskey);
    }

    private <T> T callMethod(final Callable<T> callable)
	    throws ConnectorException {
	T result;
	try {
	    result = callable.call();

	    if (null == result) {
		if (!isMoodleWSRunning()) {
		    LOG.error("No connection to " + moodle.getURL());
		    throw new NetworkException("No connection to "
			    + moodle.getURL());
		}

		if (isSessionExpired()) {
		    LOG.debug("try relogin and call method again");
		    tryLogin();
		    result = callable.call();
		}
	    }
	} catch (Exception e) {
	    throw new ConnectorException(e);
	}

	return result;
    }

    @Override
    public void initialize(String id, Model model,
	    Map<String, Object> parameters) {
	super.initialize(id, model, parameters);

	this.mdlConfig = ConfigUtils.fromMap(parameters,
		MoodleConnectorConfig.class);

	this.moodle = new Mdl_soapserverBindingStub(getURL() + MOODLEWS_PATH,
		getURL() + "/wspp/wsdl2", false);

	tryLogin();
	this.myId = moodle.get_my_id(client, sesskey);
    }

    @Override
    public String getURL() {
	return StringUtils.endsWithSlash(mdlConfig.getUrl());
    }

    @Override
    public Site getSite() {
	URI uri = RDF2GoUtils.createURI(getURL());

	if (!Site.hasInstance(getModel(), uri)) {
	    Site result = new Site(getModel(), uri, true);
	    result.setName("Moodle");
	    return result;
	}

	return Site.getInstance(getModel(), uri);
    }

    @Override
    public UserAccount getLoginUser() {
	return getUserAccount(Integer.toString(myId));
    }

    @Override
    public Iterator<Forum> getForums() {
	List<Forum> result = new ArrayList<Forum>();

	ForumRecord[] forums = callMethod(new Callable<ForumRecord[]>() {
	    @Override
	    public ForumRecord[] call() throws Exception {
		return moodle.get_all_forums(client, sesskey, "", "");
	    }
	});

	if (null == forums)
	    forums = new ForumRecord[0];

	for (ForumRecord forum : forums) {
	    URI uri = RDF2GoUtils.createURI(getURL() + URI_FORUM_PATH
		    + forum.getId());

	    if (!Forum.hasInstance(getModel(), uri)) {
		Forum entry = new Forum(getModel(), uri, true);
		CourseRecord course = getCourse(forum.getCourse());

		entry.setId(Integer.toString(forum.getId()));
		entry.setModified(DateUtils.formatISO8601(createDate(forum
			.getTimemodified())));
		entry.setDescription(forum.getIntro());
		entry.setName(course.getFullname() + "/" + forum.getName());
		entry.setHost(getSite());
		getSite().addHostof(entry);

		result.add(entry);
	    } else {
		result.add(Forum.getInstance(getModel(), uri));
	    }
	}

	return result.iterator();
    }

    public Iterator<Thread> getThreads(Forum forum) {
	Preconditions.checkArgument(forum.hasHost(getSite()));

	List<Thread> result = new ArrayList<Thread>();
	final int forumid = Integer.parseInt(forum.getId());

	ForumDiscussionRecord[] forumDiscussions = callMethod(new Callable<ForumDiscussionRecord[]>() {
	    @Override
	    public ForumDiscussionRecord[] call() throws Exception {
		return moodle.get_forum_discussions(client, sesskey, forumid,
			Integer.MAX_VALUE);
	    }
	});

	if (null == forumDiscussions)
	    forumDiscussions = new ForumDiscussionRecord[0];

	for (ForumDiscussionRecord discussion : forumDiscussions) {
	    URI uri = RDF2GoUtils.createURI(getURL() + URI_THREAD_PATH
		    + discussion.getId());

	    if (!Thread.hasInstance(getModel(), uri)) {
		Thread entry = new Thread(getModel(), uri, true);

		entry.setId(Integer.toString(discussion.getId()));

		entry.setParent(forum);
		forum.addParentof(entry);

		entry.setModified(DateUtils.formatISO8601(createDate(discussion
			.getTimemodified())));

		entry.setCreator(getUserAccount(Integer.toString(discussion
			.getUserid())));
		entry.setName(discussion.getName());

		result.add(entry);
	    } else {
		result.add(Thread.getInstance(getModel(), uri));
	    }
	}

	return result.iterator();
    }

    public Iterator<Post> getPosts(Container container) {
	Preconditions.checkArgument(hasPosts(container));

	List<Post> result = new ArrayList<Post>();

	ClosableIterator<Statement> stmtsIter = getModel().findStatements(
		Variable.ANY, SIOC.has_container, container);

	while (stmtsIter.hasNext()) {
	    Statement statement = (Statement) stmtsIter.next();

	    if (Post.hasInstance(getModel(), statement.getSubject())) {
		result.add(Post.getInstance(getModel(), statement.getSubject()));
	    }
	}
	stmtsIter.close();

	return result.iterator();
    }

    @Override
    public Iterator<Post> pollNewPosts(Container container) {
	Preconditions.checkArgument(hasPosts(container));

	List<Post> result = new ArrayList<Post>();

	final int discussionId = Integer.parseInt(container.getId());
	ForumPostRecord[] posts = callMethod(new Callable<ForumPostRecord[]>() {
	    @Override
	    public ForumPostRecord[] call() throws Exception {
		return moodle.get_forum_posts(client, sesskey, discussionId,
			mdlConfig.getMaxNewPostsOnPoll());
	    }
	});

	if (null != posts) {
	    Date lastItemDate = new Date(0);
	    if (container.hasLastitemdate()) {
		try {
		    lastItemDate = RDFTool.string2DateTime(container
			    .getLastitemdate());
		} catch (ParseException e1) {
		    lastItemDate = new Date(0);
		}
	    }

	    for (ForumPostRecord postRecord : posts) {
		Date created = createDate(postRecord.getCreated());

		if (created.after(lastItemDate)) {
		    URI uri = RDF2GoUtils.createURI(getURL() + URI_POST_PATH
			    + postRecord.getId());

		    if (!Post.hasInstance(getModel(), uri)) {
			Post post = createPost(container, null, postRecord, uri);
			result.add(post);
			result.addAll(getComments(container, post, postRecord));
		    }
		}
	    }
	}

	LOG.debug(result.size() + " new posts polled");

	return result.iterator();
    }

    private List<Post> getComments(Container container, Post parentPost,
	    ForumPostRecord parentRecord) {
	List<Post> result = new ArrayList<Post>();
	Date lastReplyDate = new Date(0);

	if (parentPost.hasLastreplydate()) {
	    try {
		lastReplyDate = RDFTool.string2DateTime(parentPost
			.getLastreplydate());
	    } catch (ParseException e1) {
		lastReplyDate = new Date(0);
	    }
	}

	if (null != parentRecord.getChildren()) {
	    for (ForumPostRecord commentRecord : parentRecord.getChildren()) {
		Date created = createDate(commentRecord.getCreated());

		if (created.after(lastReplyDate)) {
		    URI uri = RDF2GoUtils.createURI(getURL() + URI_POST_PATH
			    + commentRecord.getId());

		    if (!Post.hasInstance(getModel(), uri)) {
			Post commentPost = createPost(container, parentPost,
				commentRecord, uri);
			result.add(commentPost);
			result.addAll(getComments(container, commentPost,
				commentRecord));
		    }
		}
	    }
	}

	return result;
    }

    private Post createPost(Container container, Post parentPost,
	    ForumPostRecord postRecord, URI uri) {
	Post post = new Post(getModel(), uri, true);

	post.setId(Integer.toString(postRecord.getId()));
	post.setCreator(getUserAccount(Integer.toString(postRecord.getUserid())));

	post.setSubject(postRecord.getSubject());

	String content = StringUtils.trimToEmpty(postRecord.getMessage());
	post.setContent(StringUtils.stripHTML(content));
	post.setContentEncoded(RDF2GoUtils.createCDATASection(content));

	post.setCreated(DateUtils.formatISO8601(createDate(postRecord
		.getCreated())));
	post.setModified(DateUtils.formatISO8601(createDate(postRecord
		.getModified())));

	if (null != container) {
	    post.setContainer(container);
	    container.addContainerof(post);
	    post.setDiscussion(container);
	    SIOCUtils.updateLastItemDate(container, post);
	}

	if (null != parentPost) {
	    post.setReplyof(parentPost);
	    parentPost.setReply(post);
	    SIOCUtils.updateLastReplyDate(parentPost, post);
	}

	return post;
    }

    public boolean canPublishOn(Container container) {

	/*
	 * Can publish on this conainer if: - It's a Thread - Has a parent that
	 * is a Forum - The parent forum belongs to this moodle instance
	 */

	if (getModel().contains(container, RDF.type, SIOC.Thread)) {
	    Thread thread = Thread.getInstance(getModel(),
		    container.getResource());
	    if (thread.hasParent()) {
		Container parent = thread.getParent();
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
	/*
	 * We can reply on this post if: - it has a container that is a thread -
	 * this thread has a parent Forum - the parent forum belongs to this
	 * moodle instance
	 */
	if (parent.hasContainer()) {
	    Container container = parent.getContainer();
	    if (getModel().contains(container, RDF.type, SIOC.Thread)) {
		Thread thread = Thread.getInstance(getModel(),
			container.getResource());
		if (thread.hasParent()) {
		    Container parentContainer = thread.getParent();
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

    public boolean hasPosts(Container container) {
	return canPublishOn(container);
    }

    public boolean publishPost(Post post, Container container) {
	Preconditions.checkNotNull(post, "post can't be null");
	Preconditions.checkNotNull(container, "container can't be null");
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

    @Override
    public boolean replyPost(Post post, Post parent) {
	Preconditions.checkNotNull(post, "post can't be null");
	Preconditions.checkNotNull(parent, "parent can't be null");
	Preconditions.checkArgument(post.hasContent(), "post has no content");
	Preconditions.checkArgument(canReplyOn(parent),
		"can't reply on the parent post");

	ForumPostDatum datum = new ForumPostDatum(moodle.getNAMESPACE());
	datum.setMessage(post.getAllContent_as().firstValue());
	if (post.hasTitle()) {
	    datum.setSubject(post.getAllTitle_as().firstValue());
	} else if (post.hasSubject()) {
	    datum.setSubject(post.getAllSubject_as().firstValue());
	} else {
	    datum.setSubject("");
	}

	ForumPostRecord[] result = moodle.forum_add_reply(client, sesskey,
		Integer.parseInt(parent.getId()), datum);

	// TODO add new post to model

	return null != result && 0 < result.length;
    }

    public UserAccount getUserAccount(String id) {
	URI uri = RDF2GoUtils.createURI(getURL() + URI_USER_PATH + id);

	if (!UserAccount.hasInstance(getModel(), uri)) {
	    UserRecord[] users = moodle.get_user_byid(client, sesskey, myId);

	    if (null != users && 0 < users.length) {
		UserRecord me = users[0];
		UserAccount result = new UserAccount(getModel(), uri, true);
		result.setId(Integer.toString(me.getId()));
		result.setIsPartOf(getSite());
		result.setAccountservicehomepage(RDF2GoUtils
			.createURI(getURL()));
		result.setName(me.getFirstname() + " " + me.getLastname());
		result.setAccountname(me.getUsername());
		result.setEmail(RDF2GoUtils.createMailtoURI(me.getEmail()));
		result.setEmailsha1(RDFTool.sha1sum(me.getEmail()));
		result.setDescription(RDF2GoUtils.createLiteral(me
			.getDescription()));

		return result;
	    }

	    throw new ConnectorException("Can't retriev user with id=" + id);
	}

	return UserAccount.getInstance(getModel(), uri);
    }

    protected CourseRecord getCourse(final int id) {
	if (courses.containsKey(id))
	    return courses.get(id);

	CourseRecord[] courseRecords = moodle.get_course_byid(client, sesskey,
		Integer.toString(id));

	CourseRecord course = courseRecords[0];
	courses.put(course.getId(), course);
	return course;
    }

    protected Date createDate(final int time) {
	return new Date((long) time * 1000L);
    }
}
