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

import java.nio.charset.Charset;
import java.util.Arrays;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.damnhandy.uri.template.UriTemplate;

import de.m0ep.canvas.exceptions.CanvasLmsException;
import de.m0ep.canvas.exceptions.NotFoundException;
import de.m0ep.canvas.model.Entry;

/**
 * Implementation of an Entries endpoint
 * 
 * @author Florian Müller
 * 
 */
public class Entries extends AbstractEndpoint {
	private static final Logger LOG = LoggerFactory.getLogger( Entries.class );

	private static final String PARAM_ENTRY_ID = "entryId";

	private static final String PATH = "/entries";
	private static final String PATH_REPLIES = "/entries/{entryId}/replies";
	private static final String PATH_ENTRY_LIST = "/entry_list?ids[]={entryId}";

	public class List extends CanvasLmsRequest<Entry> {
		public List() {
			super( Entries.this.getClient(),
			        HttpGet.class,
			        getEndpointPath(),
			        Entry.class );
		}

		@Override
		public Entry execute()
		        throws CanvasLmsException {
			throw new UnsupportedOperationException(
			        "execute() is not supported by List" );
		}
	}

	public class Get extends CanvasLmsRequest<Entry> {
		long entryId;

		public Get( long entryId ) {
			super( Entries.this.getClient(),
			        HttpGet.class,
			        UriTemplate.fromTemplate(
			                getParentEndpointPath() + PATH_ENTRY_LIST )
			                .set( PARAM_ENTRY_ID, entryId )
			                .expand(),
			        Entry.class );
			this.entryId = entryId;
		}

		@Override
		public Entry execute() throws CanvasLmsException {
			Pagination<Entry> pagination = super.executePagination();
			for ( java.util.List<Entry> list : pagination ) {
				for ( Entry entry : list ) {
					return entry;
				}
			}

			throw new NotFoundException( "No entry found with id= " + entryId );
		}

		@Override
		public Pagination<Entry> executePagination()
		        throws CanvasLmsException {
			throw new UnsupportedOperationException(
			        "executePagination() is not supported by Post" );
		}
	}

	public class Post extends CanvasLmsRequest<Entry> {
		public Post( final String message ) {
			super( Entries.this.getClient(),
			        HttpPost.class,
			        getEndpointPath(),
			        Entry.class );

			setContent( new UrlEncodedFormEntity(
			        Arrays.asList(
			                new BasicNameValuePair(
			                        "message",
			                        message ) ),
			        Charset.defaultCharset() ) );
		}

		@Override
		public Pagination<Entry> executePagination()
		        throws CanvasLmsException {
			throw new UnsupportedOperationException(
			        "executePagination() is not supported by Post" );
		}
	}

	public class ListReplies extends
	        CanvasLmsRequest<Entry> {
		public ListReplies( final long entryId ) {
			super( Entries.this.getClient(),
			        HttpGet.class,
			        UriTemplate.fromTemplate( getParentEndpointPath() + PATH_REPLIES )
			                .set( PARAM_ENTRY_ID, entryId ).expand(),
			        Entry.class );
		}

		@Override
		public Entry execute()
		        throws CanvasLmsException {
			throw new UnsupportedOperationException(
			        "execute() is not supported by List" );
		}
	}

	public class PostReply extends
	        CanvasLmsRequest<Entry> {
		public PostReply( final String message, final long entryId ) {
			super( Entries.this.getClient(),
			        HttpPost.class,
			        UriTemplate.fromTemplate( getParentEndpointPath() + PATH_REPLIES )
			                .set( PARAM_ENTRY_ID, entryId ).expand(),
			        Entry.class );

			setContent( new UrlEncodedFormEntity(
			        Arrays.asList(
			                new BasicNameValuePair(
			                        "message",
			                        message ) ),
			        Charset.defaultCharset() ) );
		}

		@Override
		public Pagination<Entry> executePagination()
		        throws CanvasLmsException {
			throw new UnsupportedOperationException(
			        "executePagination() is not supported by Post" );
		}
	}

	public Entries( final CanvasLmsClient client, final String parentEndpointPath ) {
		setClient( client );
		setParentEndpointPath( parentEndpointPath );
		setEndpointPath( getParentEndpointPath() + PATH );

		LOG.info( "Create Entries for '{}' endpoint.", getParentEndpointPath() );
	}

	/**
	 * Create a request to list all entries.
	 * 
	 * @return A {@link List} request.
	 */
	public List list() {
		List request = new List();
		initializeRequest( request );
		return request;
	}

	/**
	 * Create a request to get a single entry.
	 * 
	 * @param entryId
	 *            Entry id.
	 * @return A {@link Get} request.
	 */
	public Get get( final long entryId ) {
		Get request = new Get( entryId );
		initializeRequest( request );
		return request;
	}

	/**
	 * Request to post a message.
	 * 
	 * @param message
	 *            The message content.
	 * @return A {@link Post} request.
	 */
	public Post post( final String message ) {
		Post request = new Post( message );
		initializeRequest( request );
		return request;
	}

	/**
	 * Creates a request to list all replies of an entry.
	 * 
	 * @param entryId
	 *            The entry id.
	 * @return A {@link ListReplies} request.
	 */
	public ListReplies listReplies( final long entryId ) {
		ListReplies request = new ListReplies( entryId );
		initializeRequest( request );
		return request;
	}

	/**
	 * Creates a request to post a reply to an entry.
	 * 
	 * @param message
	 *            Message content
	 * @param entryId
	 *            The id of the entry.
	 * @return A {@link PostReply} request.
	 */
	public PostReply postReply( final String message, final long entryId ) {
		PostReply request = new PostReply( message, entryId );
		initializeRequest( request );
		return request;
	}
}
