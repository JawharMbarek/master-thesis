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

package de.m0ep.canvas;

import org.apache.http.client.methods.HttpGet;

import com.damnhandy.uri.template.UriTemplate;

import de.m0ep.canvas.exceptions.CanvasLmsException;
import de.m0ep.canvas.model.Group;

/**
 * Endpoint for Canvas Groups.
 * 
 * @author Florian Müller
 */
public class Groups extends AbstractEndpoint {
	private static final String PARAM_GROUP_ID = "groupId";
	private static final String PATH = "/groups";
	private static final String PATH_GROUP = "/groups/{" + PARAM_GROUP_ID + "}";

	public class ListFromCrourse extends CanvasLmsRequest<Group> {
		public ListFromCrourse( long courseId ) {
			super( Groups.this.getClient(),
			        HttpGet.class,
			        "/course/" + courseId + getEndpointPath(),
			        Group.class );
		}

		@Override
		public Group execute() throws CanvasLmsException {
			throw new UnsupportedOperationException(
			        "execute() is not supported by List" );
		}
	}

	public class ListFromAccount extends CanvasLmsRequest<Group> {
		public ListFromAccount( long accountId ) {
			super( Groups.this.getClient(),
			        HttpGet.class,
			        "/accounts/" + accountId + getEndpointPath(),
			        Group.class );
		}

		@Override
		public Group execute() throws CanvasLmsException {
			throw new UnsupportedOperationException(
			        "execute() is not supported by List" );
		}
	}

	public class ListFromSelf extends CanvasLmsRequest<Group> {
		public ListFromSelf() {
			super( Groups.this.getClient(),
			        HttpGet.class,
			        "/users/self/" + getEndpointPath(),
			        Group.class );
		}

		@Override
		public Group execute() throws CanvasLmsException {
			throw new UnsupportedOperationException(
			        "execute() is not supported by List" );
		}
	}

	public class Get extends CanvasLmsRequest<Group> {
		public Get( final long courseId ) {
			super( Groups.this.getClient(),
			        HttpGet.class,
			        UriTemplate.fromTemplate( PATH_GROUP )
			                .set( PARAM_GROUP_ID, courseId )
			                .expand(),
			        Group.class );
		}

		@Override
		public Pagination<Group> executePagination()
		        throws CanvasLmsException {
			throw new UnsupportedOperationException(
			        "executePagination() is not supported by Get" );
		}
	}

	public Groups( final CanvasLmsClient client ) {
		setClient( client );
		setEndpointPath( PATH );
	}

	/**
	 * Creates a request to retrieve all groups from a course.
	 * 
	 * @param courseId
	 *            The id of the course.
	 * @return {@link ListFromCrourse} Request.
	 */
	public ListFromCrourse listFromCourse( long courseId ) {
		ListFromCrourse request = new ListFromCrourse( courseId );
		initializeRequest( request );
		return request;
	}

	/**
	 * Creates a request to retrieve all groups from a account.
	 * 
	 * @param accountId
	 *            The id of the account.
	 * @return {@link ListFromAccount} Request.
	 */
	public ListFromAccount listFromAccount( long accountId ) {
		ListFromAccount request = new ListFromAccount( accountId );
		initializeRequest( request );
		return request;
	}

	/**
	 * Creates a request to retrieve all groups from the logedin user.
	 * 
	 * @return {@link ListFromSelf} Request.
	 */
	public ListFromSelf listFromSelf() {
		ListFromSelf request = new ListFromSelf();
		initializeRequest( request );
		return request;
	}

	public Get get( final long courseId ) {
		Get request = new Get( courseId );
		initializeRequest( request );
		return request;
	}

	/**
	 * Get the {@link DiscussionTopics} endpoint for a group.
	 * 
	 * @param courseId
	 *            The group id.
	 * @return A {@link DiscussionTopics} endpoint.
	 */
	public DiscussionTopics discussionTopics( final long courseId ) {
		return new DiscussionTopics(
		        getClient(),
		        UriTemplate.fromTemplate( PATH_GROUP )
		                .set( PARAM_GROUP_ID, courseId )
		                .expand() );
	}
}
