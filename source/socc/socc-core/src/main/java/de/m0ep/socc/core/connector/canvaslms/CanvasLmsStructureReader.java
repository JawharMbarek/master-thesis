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

import com.google.common.collect.Lists;

import de.m0ep.canvas.CanvasLmsClient;
import de.m0ep.canvas.Pagination;
import de.m0ep.canvas.exceptions.CanvasLmsException;
import de.m0ep.canvas.model.Course;
import de.m0ep.canvas.model.DiscussionTopic;
import de.m0ep.canvas.model.Group;
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
	private static final Logger LOG = LoggerFactory.getLogger( CanvasLmsStructureReader.class );

	private final CanvasLmsClient defaultClient;

	/**
	 * Constructs a new {@link CanvasLmsStructureReader} for a
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

		return Site.getInstance( getModel(), getServiceEndpoint() );
	}

	@Override
	public boolean isContainer( URI uri ) {
		return CanvasLmsSiocUtils.isDiscussionTopicUri( uri, getServiceEndpoint() )
		        || CanvasLmsSiocUtils.isCourseUri( uri, getServiceEndpoint() )
		        || CanvasLmsSiocUtils.isGroupUri( uri, getServiceEndpoint() );
	}

	@Override
	public Container getContainer( URI uri )
	        throws NotFoundException,
	        AuthenticationException,
	        IOException {
		if ( CanvasLmsSiocUtils.isCourseUri( uri, getServiceEndpoint() ) ) {
			return getCourse( uri );
		} else if ( CanvasLmsSiocUtils.isGroupUri( uri, getServiceEndpoint() ) ) {
			return getGroup( uri );
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
		List<Container> result = Lists.newArrayList();

		// list all courses
		Pagination<Course> coursePages = null;
		try {
			coursePages = defaultClient.courses()
			        .list()
			        .executePagination();
		} catch ( de.m0ep.canvas.exceptions.NotFoundException e ) {
			LOG.warn( "DefaultUserAccount no courses found", e );
		} catch ( CanvasLmsException e ) {
			CanvasLmsConnector.handleCanvasExceptions( e );
		}

		if ( null != coursePages ) {
			for ( List<Course> courses : coursePages ) {
				for ( Course course : courses ) {
					Forum courseForum = CanvasLmsSiocUtils.createSiocForum(
					        getConnector(),
					        course );

					result.add( courseForum );

					// list all course groups
					Pagination<Group> groupPages = null;
					try {
						groupPages = defaultClient.groups()
						        .listFromCourse( course.getId() )
						        .executePagination();
					} catch ( de.m0ep.canvas.exceptions.NotFoundException e ) {
						LOG.warn( "Course '{}' no groups found", course.getId() );
						continue;
					} catch ( CanvasLmsException e ) {
						CanvasLmsConnector.handleCanvasExceptions( e );
					}

					if ( null != groupPages ) {
						for ( List<Group> list : groupPages ) {
							for ( Group group : list ) {
								Forum groupForum = CanvasLmsSiocUtils.createSiocForum(
								        getConnector(),
								        group );

								result.add( groupForum );
							}
						}
					}
				}
			}
		}

		// list all groups of default user
		Pagination<Group> groupPages = null;
		try {
			groupPages = defaultClient.groups()
			        .listFromSelf()
			        .executePagination();
		} catch ( de.m0ep.canvas.exceptions.NotFoundException e ) {
			LOG.warn( "DefaultUserAccount has no groups" );
		} catch ( CanvasLmsException e ) {
			CanvasLmsConnector.handleCanvasExceptions( e );
		}

		if ( null != groupPages ) {
			for ( List<Group> list : groupPages ) {
				for ( Group group : list ) {
					Forum groupForum = CanvasLmsSiocUtils.createSiocForum(
					        getConnector(),
					        group );

					if ( !result.contains( groupForum ) ) {
						result.add( groupForum );
					}
				}
			}
		}

		return result;
	}

	@Override
	public boolean hasChildContainer( URI uri ) {
		return CanvasLmsSiocUtils.isCourseUri( uri, getServiceEndpoint() )
		        || CanvasLmsSiocUtils.isGroupUri( uri, getServiceEndpoint() );
	}

	@Override
	public List<Container> listContainer( URI parentUri ) throws
	        NotFoundException,
	        AuthenticationException,
	        IOException {
		List<Container> result = Lists.newArrayList();
		Pattern pattern = Pattern.compile( getServiceEndpoint()
		        + CanvasLmsSiocUtils.REGEX_ENDPOINT_URI );
		Matcher matcher = pattern.matcher( parentUri.toString() );

		if ( matcher.find() ) {
			String endpoint = matcher.group( 1 );
			long endpointId = Long.parseLong( matcher.group( 2 ) );

			Forum parentContainer = null;
			if ( CanvasLmsSiocUtils.ENDPOINT_COURSE.equals( endpoint ) ) {
				parentContainer = getCourse( CanvasLmsSiocUtils.createCourseUri(
				        getServiceEndpoint(),
				        endpointId ) );
			} else if ( CanvasLmsSiocUtils.ENDPOINT_GROUPS.equals( endpoint ) ) {
				parentContainer = getGroup( CanvasLmsSiocUtils.createGroupUri(
				        getServiceEndpoint(),
				        endpointId ) );
			} else {
				throw new IOException( "Unknown Canvas endpoint '" + endpoint + "'" );
			}

			Pagination<DiscussionTopic> discussionTopicPages = null;
			try {
				if ( CanvasLmsSiocUtils.ENDPOINT_COURSE.equals( endpoint ) ) {
					discussionTopicPages = defaultClient.courses()
					        .discussionTopics( endpointId )
					        .list()
					        .executePagination();
				} else if ( CanvasLmsSiocUtils.ENDPOINT_GROUPS.equals( endpoint ) ) {
					discussionTopicPages = defaultClient.groups()
					        .discussionTopics( endpointId )
					        .list()
					        .executePagination();
				} else {
					throw new IOException( "Unknown Canvas endpoint '" + endpoint + "'" );
				}
			} catch ( CanvasLmsException e ) {
				CanvasLmsConnector.handleCanvasExceptions( e );
			}

			if ( null != discussionTopicPages ) {
				for ( List<DiscussionTopic> topics : discussionTopicPages ) {
					for ( DiscussionTopic discussionTopic : topics ) {
						Thread topicThread = CanvasLmsSiocUtils
						        .createSiocThread(
						                getConnector(),
						                endpoint,
						                discussionTopic,
						                parentContainer );

						result.add( topicThread );
					}
				}
			}
		} else {
			LOG.debug( "The uri {} has no child container at {}",
			        parentUri,
			        getServiceEndpoint() );
		}

		return result;
	}

	/**
	 * Reads a course and converts it to SIOC
	 * 
	 * @param uri
	 *            URI to the course
	 * @return A {@link Forum} converted from the course
	 * 
	 * @throws NotFoundException
	 *             Thrown if no resource was found at the URI
	 * @throws AuthenticationException
	 *             Thrown if there is a problem with authentication.
	 * @throws IOException
	 *             Thrown if there ist problem in communication.
	 */
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

			Course course = null;
			try {
				course = defaultClient.courses()
				        .get( courseId )
				        .execute();

			} catch ( CanvasLmsException e ) {
				CanvasLmsConnector.handleCanvasExceptions( e );
			}

			if ( null != course ) {
				Forum courseForum = CanvasLmsSiocUtils.createSiocForum(
				        getConnector(),
				        course );
				return courseForum;
			}
		}

		throw new NotFoundException( "No course found at uri " + uri );
	}

	/**
	 * Reads a course and converts it to SIOC
	 * 
	 * @param uri
	 *            URI to the course
	 * @return A {@link Forum} converted from the course
	 * 
	 * @throws NotFoundException
	 *             Thrown if no resource was found at the URI
	 * @throws AuthenticationException
	 *             Thrown if there is a problem with authentication.
	 * @throws IOException
	 *             Thrown if there ist problem in communication.
	 */
	private Forum getGroup( URI uri ) throws
	        NotFoundException,
	        AuthenticationException,
	        IOException {
		if ( Forum.hasInstance( getModel(), uri ) ) {
			return Forum.getInstance( getModel(), uri );
		}

		Pattern pattern = Pattern.compile( CanvasLmsSiocUtils.REGEX_GROUP_URI );
		Matcher matcher = pattern.matcher( uri.toString() );
		if ( matcher.find() ) {
			long groupId = Long.parseLong( matcher.group( 1 ) );

			Group group = null;
			try {
				group = defaultClient.groups()
				        .get( groupId )
				        .execute();
			} catch ( CanvasLmsException e ) {
				CanvasLmsConnector.handleCanvasExceptions( e );
			}

			if ( null != group ) {
				Forum groupForum = CanvasLmsSiocUtils.createSiocForum(
				        getConnector(),
				        group );

				return groupForum;
			}
		}

		throw new NotFoundException( "No group found at uri " + uri );
	}

	/**
	 * Reads a discussion topic and converts it to SIOC.
	 * 
	 * @param uri
	 *            URI to the discussion topic.
	 * @return A {@link Forum} converted from the discussion topic.
	 * 
	 * @throws NotFoundException
	 *             Thrown if no resource was found at the URI
	 * @throws AuthenticationException
	 *             Thrown if there is a problem with authentication.
	 * @throws IOException
	 *             Thrown if there ist problem in communication.
	 */
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
			String endpoint = matcher.group( 1 );
			long endpointId = Long.parseLong( matcher.group( 2 ) );
			long topicId = Long.parseLong( matcher.group( 3 ) );

			Forum parentContainer = null;
			if ( CanvasLmsSiocUtils.ENDPOINT_COURSE.equals( endpoint ) ) {
				parentContainer = getCourse( CanvasLmsSiocUtils.createCourseUri(
				        getServiceEndpoint(),
				        endpointId ) );
			} else if ( CanvasLmsSiocUtils.ENDPOINT_GROUPS.equals( endpoint ) ) {
				parentContainer = getGroup( CanvasLmsSiocUtils.createGroupUri(
				        getServiceEndpoint(),
				        endpointId ) );
			} else {
				throw new IOException( "Unknown Canvas endpoint '" + endpoint + "'" );
			}

			DiscussionTopic discussionTopic = null;
			try {
				if ( CanvasLmsSiocUtils.ENDPOINT_COURSE.equals( endpoint ) ) {
					discussionTopic = defaultClient.courses()
					        .discussionTopics( endpointId )
					        .get( topicId )
					        .execute();
				} else if ( CanvasLmsSiocUtils.ENDPOINT_GROUPS.equals( endpoint ) ) {
					discussionTopic = defaultClient.groups()
					        .discussionTopics( endpointId )
					        .get( topicId )
					        .execute();
				} else {
					throw new IOException( "Unknown Canvas endpoint '" + endpoint + "'" );
				}
			} catch ( CanvasLmsException e ) {
				CanvasLmsConnector.handleCanvasExceptions( e );
			}

			if ( null != discussionTopic ) {
				Thread topicThread = CanvasLmsSiocUtils.createSiocThread(
				        getConnector(),
				        endpoint,
				        discussionTopic,
				        parentContainer );

				return topicThread;
			}

		}

		throw new NotFoundException( "No discussion topic found at uri " + uri );
	}
}
