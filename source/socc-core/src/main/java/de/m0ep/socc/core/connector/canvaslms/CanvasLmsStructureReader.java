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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ontoware.rdf2go.model.node.URI;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Site;
import org.rdfs.sioc.Thread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

/**
 * Structure reader for the CanvasLMS.
 * 
 * @author Florian Müller
 */
public class CanvasLmsStructureReader
        extends DefaultConnectorIOComponent<CanvasLmsConnector>
        implements IStructureReader<CanvasLmsConnector> {
	private static final Logger LOG =
	        LoggerFactory.getLogger( CanvasLmsStructureReader.class );

	private final CanvasLmsClient defaultClient;

	/**
	 * Constructs a new {@link CanvasLmsStructureReader} for the given
	 * {@link CanvasLmsConnector}.
	 * 
	 * @param connector
	 */
	public CanvasLmsStructureReader( final CanvasLmsConnector connector ) {
		super( connector );

		this.defaultClient = connector.getClientManager()
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
	public boolean isContainer( URI uri ) {
		return CanvasLmsSiocUtils.isDiscussionTopicUri( uri, getServiceEndpoint() )
		        || CanvasLmsSiocUtils.isCourseUri( uri, getServiceEndpoint() );
	}

	@Override
	public Container getContainer( URI uri ) throws
	        NotFoundException,
	        AuthenticationException,
	        IOException {
		if ( CanvasLmsSiocUtils.isCourseUri( uri, getServiceEndpoint() ) ) {
			return getCourse( uri );
		} else if ( CanvasLmsSiocUtils.isDiscussionTopicUri( uri, getServiceEndpoint() ) ) {
			return getDiscussionTopic( uri );
		}

		throw new NotFoundException( "No Container found at uri " + uri );
	}

	@Override
	public List<Container> listContainer() throws
	        NotFoundException,
	        AuthenticationException,
	        IOException {
		Pagination<Course> coursePages = null;
		List<Container> result = Lists.newArrayList();

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
					        CanvasLmsSiocUtils.createSiocForum(
					                getConnector(),
					                course ) );
				}
			}
		}

		return result;
	}

	@Override
	public boolean hasChildContainer( URI uri ) {
		return CanvasLmsSiocUtils.isCourseUri( uri, getServiceEndpoint() );
	}

	@Override
	public List<Container> listContainer( URI parentUri ) throws
	        NotFoundException,
	        AuthenticationException,
	        IOException {
		List<Container> result = Lists.newArrayList();
		Pattern pattern = Pattern.compile(
		        getServiceEndpoint()
		                + CanvasLmsSiocUtils.REGEX_COURSE_URI );
		Matcher matcher = pattern.matcher( parentUri.toString() );

		if ( matcher.find() ) {
			long courseId = Long.parseLong( matcher.group( 1 ) );
			Forum parentCourse = getCourse(
			        CanvasLmsSiocUtils.createCourseUri(
			                getServiceEndpoint(),
			                courseId ) );

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

			if ( null != discussionTopicPages ) {

				for ( List<DiscussionTopic> topics : discussionTopicPages ) {
					for ( DiscussionTopic discussionTopic : topics ) {
						result.add( CanvasLmsSiocUtils.createSiocThread(
						        getConnector(),
						        discussionTopic,
						        parentCourse ) );
					}
				}
			}
		} else {
			LOG.debug( "The uri {} has no child container at {}", parentUri, getServiceEndpoint() );
		}

		return result;
	}

	private Forum getCourse( URI uri ) throws
	        NotFoundException,
	        AuthenticationException,
	        IOException {
		if ( Forum.hasInstance( getModel(), uri ) ) {
			return Forum.getInstance( getModel(), uri );
		}

		Pattern pattern = Pattern.compile( CanvasLmsSiocUtils.REGEX_COURSE_URI );
		Matcher matcher = pattern.matcher( uri.toString() );

		if ( matcher.find() ) {
			long courseId = Long.parseLong( matcher.group( 1 ) );

			try {
				Course course = defaultClient.courses()
				        .get( courseId )
				        .execute();
				return CanvasLmsSiocUtils.createSiocForum(
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

		throw new NotFoundException( "No course found at uri " + uri );
	}

	private Thread getDiscussionTopic( URI uri ) throws
	        NotFoundException,
	        AuthenticationException,
	        IOException {
		if ( Thread.hasInstance( getModel(), uri ) ) {
			return Thread.getInstance( getModel(), uri );
		}

		Pattern pattern = Pattern.compile( CanvasLmsSiocUtils.REGEX_DISCUSSION_TOPIC_URI );
		Matcher matcher = pattern.matcher( uri.toString() );

		if ( matcher.find() ) {
			long courseId = Long.parseLong( matcher.group( 1 ) );
			long topicId = Long.parseLong( matcher.group( 2 ) );
			Forum parentCourse = getCourse(
			        CanvasLmsSiocUtils.createCourseUri(
			                getServiceEndpoint(),
			                courseId ) );

			try {
				DiscussionTopic discussionTopic = defaultClient.courses()
				        .discussionTopics( courseId )
				        .get( topicId )
				        .execute();

				return CanvasLmsSiocUtils.createSiocThread(
				        getConnector(),
				        discussionTopic,
				        parentCourse );
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
		} else {
		}

		throw new NotFoundException( "No discussion topic found at uri " + uri );
	}
}
