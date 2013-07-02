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

package de.m0ep.socc.utils;

import java.text.ParseException;
import java.util.Date;

import org.rdfs.sioc.Container;
import org.rdfs.sioc.Post;

import com.google.common.base.Preconditions;

/**
 * Some utility methods to work easier with SIOC
 * 
 * @author Florian Müller
 * 
 */
public class SIOCUtils {
    /*
     * Private constructor to avoid creating objects from this class.
     */
    private SIOCUtils() {
    }

    /**
     * Update the LastItemDate property of a {@link Container}, if the creation
     * date of the {@link Post} is newer then the old value.
     * 
     * @param container
     *            {@link Container} to check the LastItemDate property.
     * @param post
     *            Possibly newer {@link Post}.
     * 
     * @throws NullPointerException
     *             Thrown if container or post is null.
     */
    public static void updateLastItemDate(Container container, Post post) {
	Preconditions.checkNotNull(container, "Container can not be null.");
	Preconditions.checkNotNull(post, "Post can not be null.");

	if (post.hasCreated()) {
	    if (container.hasLastItemDate()) {
		try {
		    Date postDate = DateUtils.parseISO8601(post.getCreated());
		    Date lastDate = DateUtils.parseISO8601(container
			    .getLastItemDate());

		    // return if this post is older then the last
		    if (!postDate.after(lastDate)) {
			return;
		    }

		} catch (ParseException e) {
		    // ignore this post
		    return;
		}
	    }

	    container.setLastItemDate(post.getCreated());
	}
    }

    /**
     * Update the LastReplyDate property of a {@link Post}, if the creation
     * date of the reply is newer then the old value.
     * 
     * @param container
     *            Parent {@link Post} to check the LastReplyDate property.
     * @param reply
     *            Possibly newer {@link Post} reply.
     * 
     * @throws NullPointerException
     *             Thrown if parent or reply is null.
     */
    public static void updateLastReplyDate(Post parent, Post reply) {
	Preconditions.checkNotNull(parent, "Parent can not be null.");
	Preconditions.checkNotNull(reply, "Reply can not be null.");
	
	if (reply.hasCreated()) {
	    if (parent.hasLastReplyDate()) {
		try {
		    Date replyDate = DateUtils.parseISO8601(reply.getCreated());
		    Date parentDate = DateUtils.parseISO8601(parent
			    .getLastReplyDate());

		    // return if this reply is older then the last
		    if (!replyDate.after(parentDate))
			return;

		} catch (ParseException e) {
		    // ignore this post
		    return;
		}
	    }

	    parent.setLastReplyDate(reply.getCreated());
	}
    }
}
