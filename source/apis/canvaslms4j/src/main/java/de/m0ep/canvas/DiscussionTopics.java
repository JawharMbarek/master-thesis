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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.damnhandy.uri.template.UriTemplate;

import de.m0ep.canvas.exceptions.CanvasLmsException;
import de.m0ep.canvas.model.DiscussionTopic;

/**
 * Implementation of a discussion topic endpoint.
 * 
 * @author Florian Müller
 * 
 */
public class DiscussionTopics extends AbstractEndpoint {
	private static final Logger LOG = LoggerFactory.getLogger( DiscussionTopics.class );

	private static final String PARAM_TOPIC_ID = "topicId";
	private static final String PATH = "/discussion_topics";
	private static final String PATH_DISCUSSION_TOPIC = "/discussion_topics/{topicId}";

	public class List extends CanvasLmsRequest<DiscussionTopic> {
		public List() {
			super( DiscussionTopics.this.getClient(),
			        HttpGet.class,
			        getEndpointPath(),
			        DiscussionTopic.class );
		}

		@Override
		public DiscussionTopic execute() throws CanvasLmsException {
			throw new UnsupportedOperationException(
			        "execute() is not supported by List" );
		}
	}

	public class Get extends CanvasLmsRequest<DiscussionTopic> {
		public Get( long topicId ) {
			super( DiscussionTopics.this.getClient(),
			        HttpGet.class,
			        UriTemplate.fromTemplate( getParentEndpointPath() + PATH_DISCUSSION_TOPIC )
			                .set( PARAM_TOPIC_ID, topicId )
			                .expand(),
			        DiscussionTopic.class );
		}

		@Override
		public Pagination<DiscussionTopic> executePagination()
		        throws CanvasLmsException {
			throw new UnsupportedOperationException(
			        "executePagination() is not supported by Get" );
		}
	}

	public DiscussionTopics( final CanvasLmsClient client, final String parentEndpointPath ) {
		setClient( client );
		setParentEndpointPath( parentEndpointPath );
		setEndpointPath( getParentEndpointPath() + PATH );

		LOG.info( "Create DiscussionTopics for '{}' endpoint.", getParentEndpointPath() );
	}

	/**
	 * Creates a request to list all discussion topics
	 * 
	 * @return A {@link List} request.
	 */
	public List list() {
		List request = new List();
		initializeRequest( request );
		return request;
	}

	/**
	 * Create an request to get a single discussion topic.
	 * 
	 * @param topicId
	 *            The id of the discussion topic.
	 * @return A {@link Get} request.
	 */
	public Get get( final long topicId ) {
		Get request = new Get( topicId );
		initializeRequest( request );
		return request;
	}

	/**
	 * Get the Entries entpoint for a discussion topic.
	 * 
	 * @param topicId
	 *            The id of the discussion topic
	 * 
	 * @return A {@link Entries} endpoint.
	 */
	public Entries entries( final long topicId ) {
		return new Entries(
		        getClient(),
		        UriTemplate.fromTemplate(
		                getParentEndpointPath()
		                        + PATH_DISCUSSION_TOPIC )
		                .set( PARAM_TOPIC_ID, topicId )
		                .expand() );
	}
}
