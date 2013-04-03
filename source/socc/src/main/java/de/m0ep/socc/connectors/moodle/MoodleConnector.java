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
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.node.URI;
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

import com.google.common.base.Objects;
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
import de.m0ep.socc.exceptions.AuthenticationException;
import de.m0ep.socc.exceptions.ConnectorException;
import de.m0ep.socc.exceptions.NetworkException;
import de.m0ep.socc.exceptions.NotFoundException;
import de.m0ep.socc.utils.ConfigUtils;
import de.m0ep.socc.utils.RDF2GoUtils;
import de.m0ep.socc.utils.StringUtils;

public class MoodleConnector extends AbstractConnector {
    private static final Logger LOG = LoggerFactory
	    .getLogger(MoodleConnector.class);

    private static final String MOODLEWS_PATH = "wspp/service_pp2.php";
    private static final String MOODLE_WSDL_POSTFIX = "/wspp/wsdl2";

    private static final String URI_USER_PATH = "user/";
    private static final String URI_FORUM_PATH = "forum/";
    private static final String URI_THREAD_PATH = "thread/";
    private static final String URI_POST_PATH = "post/";

    private Mdl_soapserverBindingStub moodle;
    private MoodleConnectorConfig mdlConfig;

    private int client;
    private String sesskey;
    private int myId;

    // Map to save retrieved courses, so no need to call the server twice.
    private Map<Integer, CourseRecord> courses = new HashMap<Integer, CourseRecord>();

    /**
     * @throws ConnectorException
     *             Thrown if there is a problem while login procedure.
     * @throws NullPointerException
     *             Thrown if one or more parameters are null.
     * @throws IllegalArgumentException
     *             Thrown if the id is empty or the model is not open.
     */
    @Override
    public void initialize(String id, Model model,
	    Map<String, Object> parameters) throws ConnectorException {
	Preconditions.checkNotNull(id, "Id can not be null");
	Preconditions.checkNotNull(model, "Model can not be null");
	Preconditions.checkArgument(!id.isEmpty(), "Id can not be empty");
	Preconditions.checkArgument(model.isOpen(), "Model must be open");
	Preconditions.checkArgument(
		parameters.containsKey(MoodleConnectorConfig.USERNAME),
		"No username given");
	Preconditions.checkArgument(
		parameters.containsKey(MoodleConnectorConfig.PASSWORD),
		"No password given");
	super.initialize(id, model, parameters);

	this.mdlConfig = new MoodleConnectorConfig();
	this.mdlConfig = ConfigUtils.fromMap(parameters, this.mdlConfig);

	this.moodle = new Mdl_soapserverBindingStub(getURL() + MOODLEWS_PATH,
		getURL() + MOODLE_WSDL_POSTFIX, false);

	tryLogin();
	this.myId = moodle.get_my_id(client, sesskey);
    }

    @Override
    public String getURL() {
	return StringUtils.endsWithSlash(mdlConfig.getUrl());
    }

    @Override
    public Map<String, Object> getConfiguration() {
	return ConfigUtils.toMap(mdlConfig);
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
    public UserAccount getLoginUser() throws ConnectorException {
	return getUserAccount(Integer.toString(myId));
    }

    /**
     * @throws NotFoundException
     *             Thrown if no user with this id was found.
     */
    @Override
    public UserAccount getUserAccount(String id) throws ConnectorException {
	Preconditions.checkNotNull(id, "id can not be null");
	Preconditions.checkArgument(!id.isEmpty(), "id can not be empty");

	URI uri = RDF2GoUtils.createURI(getURL() + URI_USER_PATH + id);

	if (!UserAccount.hasInstance(getModel(), uri)) {
	    UserRecord[] users = moodle.get_user_byid(client, sesskey, myId);

	    if (null != users && 0 < users.length) {
		return MoodleToSIOCConverter.createUserAccount(this, users[0],
			uri);
	    }

	    throw new NotFoundException("Can't retriev user with id=" + id);
	}

	return UserAccount.getInstance(getModel(), uri);
    }

    @Override
    public List<Forum> getForums() throws ConnectorException {
	List<Forum> result = new ArrayList<Forum>();

	ForumRecord[] forumRecordArray = callMethod(new Callable<ForumRecord[]>() {
	    @Override
	    public ForumRecord[] call() throws Exception {
		return moodle.get_all_forums(client, sesskey, "", "");
	    }
	});

	if (null == forumRecordArray)
	    return result;

	for (ForumRecord forumRecord : forumRecordArray) {
	    URI uri = RDF2GoUtils.createURI(getURL() + URI_FORUM_PATH
		    + forumRecord.getId());

	    if (!Forum.hasInstance(getModel(), uri)) {
		result.add(MoodleToSIOCConverter.createForum(this, forumRecord,
			uri));
	    } else {
		result.add(Forum.getInstance(getModel(), uri));
	    }
	}

	return result;
    }

    @Override
    public List<Thread> getThreads(Forum forum) throws ConnectorException {
	Preconditions.checkNotNull(forum, "forum can not be null");
	Preconditions.checkArgument(forum.hasHost(getSite()));

	List<Thread> result = new ArrayList<Thread>();
	final int forumid = Integer.parseInt(forum.getId());

	ForumDiscussionRecord[] forumDiscussionArray = callMethod(new Callable<ForumDiscussionRecord[]>() {
	    @Override
	    public ForumDiscussionRecord[] call() throws Exception {
		return moodle.get_forum_discussions(client, sesskey, forumid,
			Integer.MAX_VALUE);
	    }
	});

	if (null == forumDiscussionArray)
	    forumDiscussionArray = new ForumDiscussionRecord[0];

	for (ForumDiscussionRecord discussionRecord : forumDiscussionArray) {
	    URI uri = RDF2GoUtils.createURI(getURL() + URI_THREAD_PATH
		    + discussionRecord.getId());

	    if (!Thread.hasInstance(getModel(), uri)) {
		result.add(MoodleToSIOCConverter.createThread(this,
			discussionRecord, uri, forum));
	    } else {
		result.add(Thread.getInstance(getModel(), uri));
	    }
	}

	return result;
    }

    @Override
    public List<Post> pollNewPosts(Container container)
	    throws ConnectorException {
	Preconditions.checkNotNull(container, "container can not be null");
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
		URI uri = RDF2GoUtils.createURI(getURL() + URI_POST_PATH
			+ postRecord.getId());
		Date created = new Date(postRecord.getCreated() * 1000L);

		if (created.after(lastItemDate)) {

		    if (!Post.hasInstance(getModel(), uri)) {
			Post post = MoodleToSIOCConverter.createPost(this,
				postRecord, uri, container, null);
			result.add(post);

		    }
		}

		if (Post.hasInstance(getModel(), uri)
			&& null != postRecord.getChildren())
		    result.addAll(pollNewReplies(
			    Post.getInstance(getModel(), uri), postRecord));
	    }
	}

	LOG.debug(result.size() + " new posts polled");

	return result;
    }

    private List<Post> pollNewReplies(Post parentPost,
	    ForumPostRecord parentRecord) throws ConnectorException {
	Preconditions.checkNotNull(parentPost, "parentPost can not be null");
	Preconditions
		.checkNotNull(parentRecord, "parentRecord can not be null");

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
	    for (ForumPostRecord replyRecord : parentRecord.getChildren()) {
		URI uri = RDF2GoUtils.createURI(getURL() + URI_POST_PATH
			+ replyRecord.getId());
		Date created = new Date(replyRecord.getCreated() * 1000L);

		if (created.after(lastReplyDate)) {

		    if (!Post.hasInstance(getModel(), uri)) {
			Post replyPost = MoodleToSIOCConverter.createPost(this,
				replyRecord, uri, parentPost.getContainer(),
				parentPost);
			result.add(replyPost);
		    }
		}

		if (Post.hasInstance(getModel(), uri)
			&& null != replyRecord.getChildren())
		    result.addAll(pollNewReplies(
			    Post.getInstance(getModel(), uri), replyRecord));
	    }
	}

	return result;
    }

    @Override
    public boolean canPublishOn(Container container) {
	Preconditions.checkNotNull(container, "container can not be null");

	// Can publish on this conainer if:
	// 1) It's a Thread
	// 2) Has a parent that is a Forum
	// 3) The parent forum belongs to this moodle instance

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
    public boolean canReplyOn(Post parentPost) {
	Preconditions.checkNotNull(parentPost, "parentPost can not be null");

	// We can reply on this post if:
	// 1) It has a container that is a thread
	// 2) This thread has a parent Forum
	// 3) The parent forum belongs to this moodle instance
	if (parentPost.hasContainer()) {
	    Container container = parentPost.getContainer();
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

    @Override
    public boolean hasPosts(Container container) {
	Preconditions.checkNotNull(container, "container can not be null");

	return canPublishOn(container);
    }

    @Override
    public Post publishPost(final Post post, final Container container)
	    throws ConnectorException {
	Preconditions.checkNotNull(post, "post can not be null");
	Preconditions.checkNotNull(container, "container can not be null");
	Preconditions.checkArgument(post.hasContent());
	Preconditions.checkArgument(canPublishOn(container));

	final ForumPostDatum datum = new ForumPostDatum(moodle.getNAMESPACE());
	datum.setMessage(post.getAllContent_as().firstValue());
	if (post.hasTitle()) {
	    datum.setSubject(post.getAllTitle_as().firstValue());
	} else if (post.hasSubject()) {
	    datum.setSubject(post.getAllSubject_as().firstValue());
	} else {
	    datum.setSubject("");
	}

	// get first post of the discussion to post a reply
	ForumPostRecord[] firstPostRecordArray = callMethod(new Callable<ForumPostRecord[]>() {
	    @Override
	    public ForumPostRecord[] call() throws Exception {
		return moodle.get_forum_posts(client, sesskey,
			Integer.parseInt(container.getId()), 1);
	    }
	});

	if (null != firstPostRecordArray && 0 < firstPostRecordArray.length) {
	    final ForumPostRecord firstPostRecord = firstPostRecordArray[0];

	    // add post to moodle
	    ForumPostRecord[] postRecordArray = callMethod(new Callable<ForumPostRecord[]>() {
		@Override
		public ForumPostRecord[] call() throws Exception {
		    return moodle.forum_add_reply(client, sesskey,
			    firstPostRecord.getId(), datum);
		}
	    });

	    if (null != postRecordArray && 0 < postRecordArray.length) {
		ForumPostRecord postRecord = postRecordArray[0];
		URI uri = RDF2GoUtils.createURI(getURL() + URI_POST_PATH
			+ postRecord.getId());

		if (!Post.hasInstance(getModel(), uri)) {
		    Post addedPost = MoodleToSIOCConverter.createPost(this,
			    postRecord, uri, container, null);
		    // add original post as sibling
		    addedPost.setSibling(post);
		    return addedPost;
		}

		throw new ConnectorException(
			"Problem while adding the published post: Post already exists");
	    }

	    throw new ConnectorException(
		    "Failed to publish post with unknown reason");
	}

	throw new ConnectorException("Failed to publish post to container");
    }

    @Override
    public Post replyPost(final Post post, final Post parentPost)
	    throws ConnectorException {
	Preconditions.checkNotNull(post, "post can not be null");
	Preconditions.checkNotNull(parentPost, "parentPost can not be null");
	Preconditions.checkArgument(post.hasContent(), "post has no content");
	Preconditions.checkArgument(canReplyOn(parentPost),
		"can not reply on parentPost");

	final ForumPostDatum datum = new ForumPostDatum(moodle.getNAMESPACE());
	datum.setMessage(post.getAllContent_as().firstValue());
	if (post.hasTitle()) {
	    datum.setSubject(post.getAllTitle_as().firstValue());
	} else if (post.hasSubject()) {
	    datum.setSubject(post.getAllSubject_as().firstValue());
	} else {
	    datum.setSubject("");
	}

	ForumPostRecord[] postRecordArray = callMethod(new Callable<ForumPostRecord[]>() {
	    @Override
	    public ForumPostRecord[] call() throws Exception {
		return moodle.forum_add_reply(client, sesskey,
			Integer.parseInt(parentPost.getId()), datum);
	    }
	});

	if (null != postRecordArray && 0 < postRecordArray.length) {
	    ForumPostRecord postRecord = postRecordArray[0];
	    URI uri = RDF2GoUtils.createURI(getURL() + URI_POST_PATH
		    + postRecord.getId());

	    if (!Post.hasInstance(getModel(), uri)) {
		Post addedPost = MoodleToSIOCConverter.createPost(this,
			postRecord, uri, parentPost.getContainer(), parentPost);
		// add original post as sibling
		addedPost.setSibling(post);
		return addedPost;
	    }

	    throw new ConnectorException(
		    "Problem while adding the published post: Post already exists");
	}

	throw new ConnectorException(
		"Failed to publish post with unknown reason");
    }

    /* package */CourseRecord getCourse(final int id) throws ConnectorException {
	if (courses.containsKey(id))
	    return courses.get(id);

	CourseRecord[] courseRecordArray = callMethod(new Callable<CourseRecord[]>() {
	    @Override
	    public CourseRecord[] call() throws Exception {
		return moodle.get_course_byid(client, sesskey,
			Integer.toString(id));
	    }
	});

	if (null != courseRecordArray && 0 < courseRecordArray.length) {
	    CourseRecord courseRecord = courseRecordArray[0];
	    courses.put(courseRecord.getId(), courseRecord);
	    return courseRecord;
	}

	throw new ConnectorException("Failed to load course info from "
		+ getURL());
    }

    /**
     * Check if the moodle webservice is running.
     * 
     * @return Returns true if MoodleWS is running, false otherwise.
     * 
     * @throws ConnectorException
     *             Thrown if the url to moodle is invalid.
     */
    private boolean isMoodleWSRunning() throws ConnectorException {
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

    /**
     * 
     * @return Returns true if the webservice session in Moodle is maybe expired
     */
    private boolean isSessionMaybeExpired() {
	return 0 == moodle.get_my_id(client, sesskey);
    }

    /**
     * Tries to login to Moodle.
     * 
     * @throws AuthenticationException
     *             Thrown if username and/or password are invalid.
     * @throws NetworkException
     *             Thrown if the login failed, because of network issues.
     */
    private void tryLogin() throws ConnectorException {
	if (null != sesskey)
	    moodle.logout(client, sesskey);

	LoginReturn login = moodle.login(mdlConfig.getUsername(),
		mdlConfig.getPassword());

	if (null == login) {
	    if (isMoodleWSRunning()) {
		throw new AuthenticationException("invalid user details");
	    }

	    throw new NetworkException("No connection to " + moodle.getURL());
	}

	this.client = login.getClient();
	this.sesskey = login.getSessionkey();
    }

    /**
     * Calls a {@link Callable} with a MoodleWS function and tries to relogin if
     * the usersession is expired.
     * 
     * @param callable
     *            {@link Callable} with MoodleWS function call.
     * @return Result of the {@link Callable}.
     * 
     * @throws ConnectorException
     *             Thrown if the called failed.
     * @throws NetworkException
     *             Thrown if the MoodleWS service is not running.
     */
    private <T> T callMethod(final Callable<T> callable)
	    throws ConnectorException {
	Preconditions.checkNotNull(callable, "callable can not be null");
	T result = null;
	try {
	    result = callable.call();

	    if (null == result) {
		if (!isMoodleWSRunning()) {
		    throw new NetworkException("No connection to "
			    + moodle.getURL());
		}

		if (isSessionMaybeExpired()) {
		    LOG.debug("try relogin and call method again");
		    tryLogin();
		    result = callable.call();
		}
	    }
	} catch (Exception e) {
	    throw new ConnectorException(
		    "Failed to call method on MoodleWS Server", e);
	}

	return result;
    }

    /*********************************************************************************/

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime
		* result
		+ Objects.hashCode(this.client, this.sesskey, this.mdlConfig,
			this.myId, getURL());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (!(obj instanceof MoodleConnector)) {
	    return false;
	}
	MoodleConnector other = (MoodleConnector) obj;

	if (!Objects.equal(this.client, other.client)) {
	    return false;
	}

	if (!Objects.equal(this.sesskey, other.sesskey)) {
	    return false;
	}

	if (!Objects.equal(this.mdlConfig, other.mdlConfig)) {
	    return false;
	}

	if (!Objects.equal(this.myId, other.myId)) {
	    return false;
	}

	if (!Objects.equal(this.getURL(), other.getURL())) {
	    return false;
	}

	return super.equals(obj);
    }

    @Override
    public String toString() {
	return Objects.toStringHelper(this).add("id", getId())
		.add("userId", myId).toString();
    };
}
