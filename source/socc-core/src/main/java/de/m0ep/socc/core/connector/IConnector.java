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

package de.m0ep.socc.core.connector;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.Site;
import org.rdfs.sioc.Thread;
import org.rdfs.sioc.UserAccount;
import org.rdfs.sioc.services.Service;

import de.m0ep.socc.core.ISoccContext;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.exceptions.NotFoundException;

/**
 * An interface that describes a class which helps to connect different online
 * communities with each other.
 * 
 * @author Florian Müller
 */
public interface IConnector {

    /**
     * Returns the Id of this connector.
     */
    public String getId();

    /**
     * Returns the context in which the connector is used.
     */
    public ISoccContext getContext();

    /**
     * Returns the service informations of this connector.
     */
    public Service getService();

    /**
     * Returns the default {@link UserAccount} of this connector.
     */
    public UserAccount getDefaultUserAccount();

    /**
     * Returns a {@link IServiceClientManager} instance to manage client objects
     * of different {@link UserAccount} for the used service.
     */
    public <T> IServiceClientManager<T> getServiceClientManager();

    /**
     * Returns an {@link IStructureReader} to get information about the
     * structure of the used service.
     * 
     * @throws IllegalStateException
     *             Thrown if the connector was not initialized.
     */
    public IStructureReader getStructureReader();

    /**
     * Returns an {@link IPostReader} to read {@link Post}s from the connectors
     * service endpoint.
     * 
     * @throws IllegalStateException
     *             Thrown if the connector was not initialized.
     */
    public IPostReader getPostReader();

    /**
     * Returns an {@link IPostWriter to write {@link Post}s to the connectors
     * service endpoint.
     * 
     * @throws IllegalStateException
     *             Thrown if the connector was not initialized.
     */
    public IPostWriter getPostWriter();

    /**
     * Initializes the connector. Checking the defaultUser and the service for
     * necessary for properties like required service endpoint or authorization
     * parameters.
     * 
     * @throws IllegalStateException
     *             Throw if <code>defaultUserAccount</code> or
     *             <code>service</code> are invalid.
     * @throws AuthenticationException
     *             Thrown if authorization of the default {@link UserAccount}
     *             failed.
     * @throws IOException
     *             throw if there is a problem with the connection to the
     *             service endpoint.
     */
    public void initialize() throws AuthenticationException, IOException;

    /**
     * Returns true if the connector was initialized, false otherwise.
     */
    public boolean isInitialized();

    /**
     * Shuts down the connector.
     */
    public void shutdown();

    /**********************************************************************/

    /**
     * An interface to read some structural information about a service of an
     * online community.
     * 
     * @author Florian Müller
     */
    public static interface IStructureReader {

        /**
         * Returns the {@link IConnector} instance to wich this
         * {@link IStructureReader} belongs.
         */
        public IConnector getConnector();

        /**
         * Gets a SIOC representation of the underlying online community site.
         */
        public Site getSite();

        /**
         * Returns a {@link Forum} by its <code>id</code>.
         * 
         * @param id
         * @throws NullPointerException
         *             Thrown if <code>id</code> is <code>null</code>.
         * @throws IllegalArgumentException
         *             Thrown if <code>id</code> is empty.
         * @throws IOException
         *             Thrown if there is a problem with the connection to the
         *             service endpoint.
         * @throws AuthenticationException
         * @throws NotFoundException
         *             Thrown if there is no {@link Forum} with this
         *             <code>id</code> .
         */
        public Forum getForum(String id) throws NotFoundException,
                AuthenticationException,
                IOException;

        /**
         * Returns a {@link List} with all {@link Forum}s of this service.
         * 
         * @throws IOException
         * @throws AuthenticationException
         */
        public List<Forum> listForums() throws AuthenticationException,
                IOException;

        /**
         * Returns a {@link Thread} by its <code>id</code>.
         * 
         * @param id
         * @throws AuthenticationException
         * @throws NullPointerException
         *             Thrown if <code>id</code> is <code>null</code>.
         * @throws IllegalArgumentException
         *             Thrown if <code>id</code> is empty.
         * @throws IOException
         *             Thrown if there is a problem with the connection to the
         *             service endpoint.
         * @throws NotFoundException
         *             Thrown if there is no {@link Thread} with this
         *             <code>id</code> .
         */
        public Thread getThread(String id, Container container)
                throws NotFoundException,
                AuthenticationException, IOException;

        /**
         * Returns a {@link List} with all {@link Thread}s inside the
         * <code>parent</code> {@link Container}.
         * 
         * @param container
         * @throws AuthenticationException
         * @throws IOException
         */
        public List<Thread> listThreads(Container container)
                throws AuthenticationException,
                IOException;
    }

    /**
     * An interface that describes a class which is used to read data from a
     * connectors service-endpoint.
     * 
     * @author Florian Müller
     */
    public static interface IPostReader {
        /**
         * Returns the {@link IConnector} instance to wich this
         * {@link IPostReader} belongs.
         */
        public IConnector getConnector();

        /**
         * Returns true if the provided <code>container</code> contains possibly
         * posts, false otherwise.
         * 
         * @param container
         * @throws NullPointerException
         *             Thrown if <code>container</code> is <code>null</code>.
         */
        public boolean containsPosts(Container container);

        /**
         * Polls the for new {@link Post}s inside the provided
         * <code>container</code>. The result can be limit to a certain amount
         * of new Posts by the <code>limit</code> parameter. Use
         * <code>limit = -1</code> to return all results.
         * 
         * @param since
         * @param limit
         *            Limit results to this number of entries. Use -1 to disable
         *            limitation.
         * @param container
         *            The container to poll.
         * @throws AuthenticationException
         *             Thrown if there is a problem to authenticate the user
         *             account.
         * @throws IOException
         *             Thrown if there is a problem with the network.
         * @throws NullPointerException
         *             Thrown if <code>container</code> is <code>null</code>.
         * @throws IllegalArgumentException
         *             Thrown if <code>container</code> doesn't belong to the
         *             connector.
         */
        public List<Post> readNewPosts(Date since, long limit,
                Container container)
                throws AuthenticationException, IOException;

        /**
         * Returns true if a {@link Post} contains possibly some replies, false
         * otherwise.
         * 
         * @param post
         * @throws NullPointerException
         *             Thrown if <code>parent</code> is <code>null</code>.
         */
        public boolean containsReplies(Post post);

        /**
         * Polls for new replies to a provided parent {@link Post}. The result
         * can be limit to a certain amount of new Posts by the
         * <code>limit</code> parameter. Use <code>limit = -1</code> to return
         * all results.
         * 
         * @param limit
         *            Limit results to this number of entries. Use -1 to disable
         *            limitation.
         * @param parentPost
         *            The parent post to poll.
         * @throws AuthenticationException
         *             Thrown if there is a problem to authenticate the user
         *             account.
         * @throws IOException
         *             Thrown if there is a problem with the network.
         * @throws NullPointerException
         *             Thrown if <code>parent</code> is <code>null</code>.
         * @throws IllegalArgumentException
         *             Thrown if <code>parent</code> doesn't belong to the
         *             connector.
         */
        public List<Post> readNewReplies(Date since, long limit, Post parentPost)
                throws AuthenticationException, IOException;
    }

    /**
     * An interface that describes a class which is used to write {@link Post}s
     * to a connectors service-endpoint.
     * 
     * @author Florian Müller
     */
    public static interface IPostWriter {
        /**
         * Returns the {@link IConnector} instance to which this
         * {@link IPostWriter} belongs.
         */
        public IConnector getConnector();

        /**
         * Returns true if it's possible to write to the provided container,
         * false otherwise.
         * 
         * @param container
         * @throws NullPointerException
         *             Thrown if <code>container</code> is <code>null</code>.
         */
        public boolean canPostTo(Container container);

        /**
         * Writes a <code>post</code> to the <code>container</code>
         * 
         * @param post
         *            The {@link Post} that should be written to the
         *            {@link Container}.
         * @param container
         *            The {@link Container} where the {@link Post} should be
         *            written to.
         * @throws AuthenticationException
         *             Thrown if there is a problem to authenticate the user
         *             account.
         * @throws IOException
         *             Thrown if there is a problem with the network.
         * @throws NullPointerException
         *             Thrown if <code>post</code> or <code>container</code> are
         *             <code>null</code>.
         * @throws IllegalArgumentException
         *             Thrown if <code>post</code> is invalid or
         *             <code>container</code> doesn't belong to the connector.
         */
        public void writePost(Post post, Container container)
                throws AuthenticationException, IOException;

        /**
         * Returns true if it's possible to reply to the provided
         * <code>parent</code> {@link Post}, false otherwise.
         * 
         * @param post
         * @throws NullPointerException
         *             Thrown if <code>parent</code> is <code>null</code>.
         */
        public boolean canReplyTo(Post post);

        /**
         * Writes a <code>reply</code> to a <code>parent</code> post.
         * 
         * @param replyPost
         *            The reply that should be written to the parent post.
         * @param parentPost
         *            The parent post where the reply should be written to.
         * @throws AuthenticationException
         *             Thrown if there is a problem to authenticate the user
         *             account.
         * @throws IOException
         *             Thrown if there is a problem with the network.
         * @throws NullPointerException
         *             Thrown if <code>reply</code> or <code>parent</code> are
         *             <code>null</code>.
         * @throws IllegalArgumentException
         *             Thrown if <code>reply</code> is invalid or
         *             <code>parent</code> doesn't belong to the connector.
         */
        public void writeReply(Post replyPost, Post parentPost)
                throws AuthenticationException, IOException;
    }
}
