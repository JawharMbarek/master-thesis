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
import java.util.List;

import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.Thread;

import de.m0ep.socc.core.exceptions.AuthenticationException;

/**
 * An interface that describes a class which is used to read data from a
 * connectors service-endpoint.
 * 
 * @author Florian Müller
 */
public interface IReadConnector {
    /**
     * Returns the {@link IConnector} instance to wicht this
     * {@link IReadConnector} belongs.
     */
    public IConnector getConnector();

    /**
     * Returns all {@link Forum}s of an corresponding connector as a
     * {@link List}.
     * 
     * @throws AuthenticationException
     *             Thrown if there is a problem to authenticate the user
     *             account.
     * @throws IOException
     *             Thrown if there is a problem with the network.
     */
    public List<Forum> listForums()
            throws AuthenticationException, IOException;

    /**
     * Returns all {@link Thread}s of a provided {@link Container} as a
     * {@link List}.
     * 
     * @param container
     *            {@link Container} from where the {@link Thread}s should be
     *            listed.
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
    public List<Thread> listThreads(Container container)
            throws AuthenticationException, IOException;

    /**
     * Returns true if the provided <code>container</code> contains possibly
     * posts, false otherwise.
     * 
     * @param container
     * @throws NullPointerException
     *             Thrown if <code>container</code> is <code>null</code>.
     */
    public boolean hasPosts(Container container);

    /**
     * Polls the for new {@link Post}s inside the provided
     * <code>container</code>. The result can be limit to a certain amount of
     * new Posts by the <code>limit</code> parameter. Use
     * <code>limit = -1</code> to return all results.
     * 
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
    public List<Post> pollNewPosts(long limit, Container container)
            throws AuthenticationException, IOException;

    /**
     * Returns true if a {@link Post} contains possibly some replies, false
     * otherwise.
     * 
     * @param parent
     * @throws NullPointerException
     *             Thrown if <code>parent</code> is <code>null</code>.
     */
    public boolean hasReplies(Post parent);

    /**
     * Polls for new replies to a provided parent {@link Post}. The result can
     * be limit to a certain amount of new Posts by the <code>limit</code>
     * parameter. Use <code>limit = -1</code> to return all results.
     * 
     * @param limit
     *            Limit results to this number of entries. Use -1 to disable
     *            limitation.
     * @param parent
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
    public List<Post> pollNewReplies(long limit, Post parent)
            throws AuthenticationException, IOException;
}
