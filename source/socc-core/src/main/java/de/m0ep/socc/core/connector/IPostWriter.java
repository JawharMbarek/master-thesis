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

import org.rdfs.sioc.Container;
import org.rdfs.sioc.Post;

import de.m0ep.socc.core.exceptions.AuthenticationException;

/**
 * An interface that describes a class which is used to write {@link Post}s to a
 * connectors service-endpoint.
 * 
 * @author Florian Müller
 */
public interface IPostWriter {
    /**
     * Returns the {@link IConnector} instance to which this
     * {@link IPostWriter} belongs.
     */
    public IConnector getConnector();

    /**
     * Returns true if it's possible to write to the provided container, false
     * otherwise.
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
     *            The {@link Container} where the {@link Post} should be written
     *            to.
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
     * @param parent
     * @throws NullPointerException
     *             Thrown if <code>parent</code> is <code>null</code>.
     */
    public boolean canReplyTo(Post parent);

    /**
     * Writes a <code>reply</code> to a <code>parent</code> post.
     * 
     * @param post
     *            The reply that should be written to the parent post.
     * @param container
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
    public void writeReply(Post reply, Post parent)
            throws AuthenticationException, IOException;
}
