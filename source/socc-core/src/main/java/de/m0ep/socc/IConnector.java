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

package de.m0ep.socc;

import java.util.List;

import org.ontoware.rdf2go.model.Model;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.Site;
import org.rdfs.sioc.Thread;
import org.rdfs.sioc.UserAccount;

import de.m0ep.sioc.service.auth.Service;
import de.m0ep.socc.exceptions.ConnectorException;

/**
 * Interface for a connector to a social online community
 * 
 * @author Florian Müller
 * 
 */
public interface IConnector {

    /**
     * Initialize this connector
     * 
     * @param id
     *            Id of this connector.
     * @param model
     *            RDF2Go {@link Model} to use.
     * @param parameters
     *            Configuration parameters for this connector.
     */
    public void initialize(
	    String id,
	    ISOCCContext context,
	    Service service,
	    UserAccount userAccount)
	    throws ConnectorException;

    /**
     * Called if the Connector will be destroyed.
     */
    public void destroy() throws ConnectorException;

    /**
     * Connect this connector.
     */
    public void connect() throws ConnectorException;

    /**
     * Returns if a connector is connected or not.
     * 
     * @return Returns true if this connector is connected, false otherwise.
     */
    public boolean isConnected();

    /**
     * Disconnect this connector.
     */
    public void disconnect();

    /**
     * Return the Id of this Connector.
     * 
     * @return Id of Connector
     */
    public String getId();

    /**
     * Return the URL of the connected social online community.
     * 
     * @return URL to the community
     */
    public Service getService();

    /**
     * Returns the {@link UserAccount} object of the used user to connect to the
     * community.
     * 
     * @return {@link UserAccount} object of the used user.
     */
    public UserAccount getUserAccount();

    /**
     * Get the used {@link ISOCCContext} implementation
     * 
     * @return An {@link ISOCCContext} implementation
     */
    public ISOCCContext getContext();

    /**
     * Returns {@link Site} object of the connected community.
     * 
     * @return
     */
    public Site getSite() throws ConnectorException;

    /**
     * Returns the {@link Forum} with the committed id
     * 
     * @param id
     *            Id of the {@link Forum}
     * 
     * @return The {@link Forum} with this id
     * 
     * @throws ConnectorException
     *             Thrown if there is no forum with this id
     */
    public Forum getForum(String id) throws ConnectorException;

    /**
     * Returns all accessible {@link Forum}s of this community.
     * 
     * @return Iterator of all accessible {@link Forum}s.
     */
    public List<Forum> getForums() throws ConnectorException;

    /**
     * Returns the {@link Thread} with the committed id
     * 
     * @param id
     *            Id of the {@link Thread}
     * 
     * @return The {@link Thread} with this id
     * 
     * @throws ConnectorException
     *             Thrown if there is no thread with this id
     */
    public Thread getThread(String id) throws ConnectorException;

    /**
     * Returns all {@link Thread}s of the given {@link Forum}.
     * 
     * @param forum
     *            {@link Forum} from where the {@link Thread}s should be
     *            retrieved.
     * @return Iterator of all found {@link Thread}s.
     */
    public List<Thread> getThreads(Forum forum) throws ConnectorException;

    /**
     * Returns the {@link Post} with the committed id
     * 
     * @param id
     *            Id of the {@link Post}
     * 
     * @return The {@link Post} with this id
     * 
     * @throws ConnectorException
     *             Thrown if there is no post with this id
     */
    public Post getPost(String id) throws ConnectorException;

    /**
     * Returns all {@link Post}s an their Replies (also Posts) inside the given
     * {@link Container}. The container can be a {@link Forum} or {@link Thread}
     * where {@link IConnector#hasPosts(Container)} returns true.
     * 
     * @param container
     *            {@link Container} from where the {@link Post}s should be
     *            retrieved.
     * @return Iterator of all found {@link Post}s.
     */
    public List<Post> getPosts(Container container) throws ConnectorException;

    /**
     * Poll this connector to retrieve all new {@link Post}s since the last call
     * of {@link IConnector#pollPosts(Container)}
     * 
     * @param container
     *            The container where should be polled to get new posts
     * @param limit
     *            Limit the number of returned posts.
     * 
     * @return List of all new Posts
     */
    public List<Post> pollPosts(Container container, long limit)
	    throws ConnectorException;

    /**
     * Poll this connector to retrieve all new replies as {@link Post}s since
     * the last call of {@link IConnector#pollReplies(Post)}.
     * 
     * @param parent
     *            The {@link Post} from where the replies should be polled.
     * @param limit
     *            Limit the number of returned posts.
     * 
     * @return List of all replies
     * 
     * @throws ConnectorException
     *             Thrown if sth. went wrong while polling. That could be an
     *             unknown parent, network issues, problems with the underlying
     *             API...
     */
    public List<Post> pollReplies(Post parent, long limit)
	    throws ConnectorException;

    /**
     * Test if their are possible {@link Post}s inside this {@link Container}.
     * E.g. a usual Forum has no Post direct inside it, but the threads inside
     * this forum have some posts.
     * 
     * @param container
     *            {@link Container} to test for containing {@link Post}s.
     * 
     * @return Returns true if there are possibly {@link Post}s, false
     *         otherwise.
     */
    public boolean hasPosts(Container container);

    /**
     * Tests if {@link Post}s can be published on this {@link Container}
     * 
     * @param container
     *            {@link Container} to test for publishing.
     * 
     * @return Returns true if it's ok to publish {@link Post}s on this
     *         {@link Container}, false otherwise.
     */
    public boolean canPublishOn(Container container);

    /**
     * Publish a {@link Post} to the given {@link Container}.
     * 
     * @param post
     *            {@link Post} object to publish.
     * @param container
     *            {@link Container} where to publish the {@link Post}.
     * @return Returns the published post (Not the same as the given post).
     */
    public Post publishPost(Post post, Container container)
	    throws ConnectorException;

    /**
     * Tests if this post has possible replies inside this connector.
     * 
     * @param parent
     *            {@link Post} to test for containing replies
     * @return Returns true, if this {@link Post} contains maybe replies, false
     *         otherwise.
     */
    public boolean hasReplies(Post parent);

    /**
     * Tests if it's possible to post a reply on this {@link Post}
     * 
     * @param parent
     *            {@link Post} to test for replying.
     * @return Returns true if it's ok to reply on this {@link Post}.
     */
    public boolean canReplyOn(Post parent);

    /**
     * Reply a {@link Post} to the given parent {@link Post}.
     * 
     * @param post
     *            {@link Post} object of the reply.
     * @param parent
     *            {@link Post} where to reply on.
     * @return Returns the replied post (Not the same as the given post).
     */
    public Post replyPost(Post post, Post parent) throws ConnectorException;
}
