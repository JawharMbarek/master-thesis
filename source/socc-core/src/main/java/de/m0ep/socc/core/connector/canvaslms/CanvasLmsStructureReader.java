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

package de.m0ep.socc.core.connector.canvaslms;

import java.io.IOException;
import java.util.List;

import org.ontoware.rdf2go.model.node.URI;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.SIOCVocabulary;
import org.rdfs.sioc.Site;
import org.rdfs.sioc.Thread;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;

import de.m0ep.canvas.CanvasLmsClient;
import de.m0ep.canvas.Pagination;
import de.m0ep.canvas.exceptions.AuthorizationException;
import de.m0ep.canvas.exceptions.CanvasLmsException;
import de.m0ep.canvas.exceptions.NetworkException;
import de.m0ep.canvas.model.Course;
import de.m0ep.canvas.model.DiscussionTopic;
import de.m0ep.socc.core.connector.DefaultConnectorIOComponent;
import de.m0ep.socc.core.connector.IConnector.IStructureReader;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.exceptions.NotFoundException;
import de.m0ep.socc.core.utils.RdfUtils;
import de.m0ep.socc.core.utils.SiocUtils;

/**
 * Structure reader for the CanvasLMS.
 * 
 * @author Florian Müller
 */
public class CanvasLmsStructureReader extends
        DefaultConnectorIOComponent<CanvasLmsConnector>
        implements IStructureReader<CanvasLmsConnector> {

	private final CanvasLmsClient defaultClient;

	/**
	 * Constructs a new {@link CanvasLmsStructureReader} for the given
	 * {@link CanvasLmsConnector}.
	 * 
	 * @param connector
	 */
	public CanvasLmsStructureReader( final CanvasLmsConnector connector ) {
		super( connector );

		this.defaultClient = connector.getServiceClientManager()
		        .getDefaultClient();
	}

	@Override
	public Site getSite() {
		if ( !Site.hasInstance( getModel(), getServiceEndpoint() ) ) {
			Site result = new Site( getModel(), getServiceEndpoint(), true );
			result.setName( "Canvas LMS (" + getServiceEndpoint() + ")" );
			return result;
		}

		return Site.getInstance(
		        getModel(),
		        getServiceEndpoint() );
	}

	@Override
	public Container getContainer( URI uri ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Container> listContainer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Container> listContainer( URI parent ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Forum getForum( String id ) throws NotFoundException,
	        AuthenticationException, IOException {
		Preconditions.checkNotNull( id,
		        "Required parameter id must be specified." );
		Preconditions.checkArgument( !id.isEmpty(),
		        "Required parameter id may not be empty." );

		long courseId;
		try {
			courseId = Long.parseLong( id );
		} catch ( NumberFormatException e ) {
			throw new IllegalArgumentException(
			        "The parameter id is invalid, was " + id );
		}

		try {
			Course course = defaultClient.courses()
			        .get( courseId )
			        .execute();
			return CanvasLmsSiocConverter.createSiocForum(
			        getConnector(),
			        course );
		} catch ( CanvasLmsException e ) {
			if ( e instanceof NetworkException ) {
				throw new IOException( e );
			} else if ( e instanceof AuthorizationException ) {
				throw new AuthenticationException( e );
			} else if ( e instanceof de.m0ep.canvas.exceptions.NotFoundException ) {
				throw new NotFoundException( e );
			}

			throw Throwables.propagate( e );
		}
	}

	@Override
	public List<Forum> listForums() throws AuthenticationException, IOException {
		Pagination<Course> coursePages = null;
		List<Forum> result = Lists.newArrayList();

		try {
			coursePages = defaultClient.courses()
			        .list()
			        .executePagination();
		} catch ( CanvasLmsException e ) {
			if ( e instanceof NetworkException ) {
				throw new IOException( e );
			} else if ( e instanceof AuthorizationException ) {
				throw new AuthenticationException( e );
			} else if ( e instanceof de.m0ep.canvas.exceptions.NotFoundException ) {
				throw new NotFoundException( e );
			}

			throw Throwables.propagate( e );
		}

		if ( null != coursePages ) {
			for ( List<Course> courses : coursePages ) {
				for ( Course course : courses ) {
					result.add(
					        CanvasLmsSiocConverter.createSiocForum(
					                getConnector(),
					                course ) );
				}
			}
		}

		return result;
	}

	@Override
	public Thread getThread( String id, Container container )
	        throws NotFoundException,
	        AuthenticationException, IOException {
		Preconditions.checkNotNull( id,
		        "Required parameter id must be specified." );
		Preconditions.checkArgument( !id.isEmpty(),
		        "Required parameter id may not be empty." );

		Preconditions.checkNotNull( container,
		        "Required parameter parent must be specified." );
		Preconditions.checkArgument(
		        RdfUtils.isType(
		                container,
		                SIOCVocabulary.Forum ),
		        "Required parameter container is no SIOC Forum." );
		Preconditions.checkArgument(
		        SiocUtils.isContainerOfSite(
		                container,
		                getServiceEndpoint() ),
		        "The parameter container has no moodle forums threads." );
		Preconditions.checkArgument( container.hasId(),
		        "Required parameter container has no id." );

		Forum parentForum = SiocUtils.asForum( container );

		long topicId;
		try {
			topicId = Long.parseLong( id );
		} catch ( NumberFormatException e ) {
			throw new IllegalArgumentException(
			        "The parameter id is invalid, was " + id );
		}

		long courseId;
		try {
			courseId = Long.parseLong( parentForum.getId() );
		} catch ( NumberFormatException e ) {
			throw new IllegalArgumentException(
			        "The parameter parent contains no valid id, was "
			                + parentForum.getId() );
		}

		try {
			DiscussionTopic discussionTopic = defaultClient.courses()
			        .discussionTopics( courseId )
			        .get( topicId )
			        .execute();

			return CanvasLmsSiocConverter.createSiocThread(
			        getConnector(),
			        discussionTopic,
			        parentForum );
		} catch ( CanvasLmsException e ) {
			if ( e instanceof NetworkException ) {
				throw new IOException( e );
			} else if ( e instanceof AuthorizationException ) {
				throw new AuthenticationException( e );
			} else if ( e instanceof de.m0ep.canvas.exceptions.NotFoundException ) {
				throw new NotFoundException( e );
			}

			throw Throwables.propagate( e );
		}
	}

	@Override
	public List<Thread> listThreads( Container container )
	        throws AuthenticationException,
	        IOException {
		Preconditions.checkNotNull( container,
		        "Required parameter container must be specified." );
		Preconditions.checkArgument(
		        RdfUtils.isType(
		                container,
		                SIOCVocabulary.Forum ),
		        "Required parameter container is no SIOC Forum." );
		Preconditions.checkArgument(
		        SiocUtils.isContainerOfSite(
		                container,
		                getServiceEndpoint() ),
		        "The container is no CanvasLMS forum" );
		Preconditions.checkArgument( container.hasId(),
		        "Required parameter container has no id." );

		Forum parentForum = SiocUtils.asForum( container );

		long courseId;
		try {
			courseId = Long.parseLong( parentForum.getId() );
		} catch ( NumberFormatException e ) {
			throw new IllegalArgumentException(
			        "The parameter parent contains an invalid id: "
			                + parentForum.getId() );
		}

		Pagination<DiscussionTopic> discussionTopicPages = null;
		try {
			discussionTopicPages = defaultClient.courses()
			        .discussionTopics( courseId )
			        .list()
			        .executePagination();
		} catch ( CanvasLmsException e ) {
			if ( e instanceof NetworkException ) {
				throw new IOException( e );
			} else if ( e instanceof AuthorizationException ) {
				throw new AuthenticationException( e );
			} else if ( e instanceof de.m0ep.canvas.exceptions.NotFoundException ) {
				throw new NotFoundException( e );
			}

			throw Throwables.propagate( e );
		}

		List<Thread> result = Lists.newArrayList();

		if ( null != discussionTopicPages ) {

			for ( List<DiscussionTopic> topics : discussionTopicPages ) {
				for ( DiscussionTopic discussionTopic : topics ) {
					result.add( CanvasLmsSiocConverter.createSiocThread(
					        getConnector(),
					        discussionTopic,
					        parentForum ) );
				}
			}
		}

		return result;
	}
}
