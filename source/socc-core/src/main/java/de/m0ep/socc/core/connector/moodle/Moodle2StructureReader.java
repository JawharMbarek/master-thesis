/*
 * The MIT License (MIT) Copyright © 2013 Florian Müller Permission is hereby granted,
 * free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the “Software”), to deal in the Software
 * without restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of the
 * Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions: The above copyright notice and this
 * permission notice shall be included in all copies or substantial portions of
 * the Software. THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO
 * EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES
 * OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

package de.m0ep.socc.core.connector.moodle;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ontoware.rdf2go.model.node.URI;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Site;
import org.rdfs.sioc.Thread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.util.Maps;
import com.google.common.collect.Lists;

import de.m0ep.moodlews.soap.CourseRecord;
import de.m0ep.moodlews.soap.ForumDiscussionRecord;
import de.m0ep.moodlews.soap.ForumRecord;
import de.m0ep.socc.core.connector.DefaultConnectorIOComponent;
import de.m0ep.socc.core.connector.IConnector.IStructureReader;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.exceptions.NotFoundException;

public class Moodle2StructureReader extends
        DefaultConnectorIOComponent<Moodle2Connector>
        implements
        IStructureReader<Moodle2Connector> {

	private static final Logger LOG = LoggerFactory.getLogger( Moodle2StructureReader.class );
	static private Map<Integer, CourseRecord> courseMap;
	private final Moodle2ClientWrapper defaultClient;

	static {
		courseMap = Maps.newHashMap();
	}

	public Moodle2StructureReader( Moodle2Connector connector ) {
		super( connector );
		this.defaultClient = connector.getClientManager()
		        .getDefaultClient();
	}

	@Override
	public Site getSite() {
		if ( !Site.hasInstance( getModel(), getServiceEndpoint() ) ) {
			Site result = new Site( getModel(), getServiceEndpoint(), true );
			result.setName( "Moolde LMS (" + getServiceEndpoint() + ")" );
			return result;
		}

		return Site.getInstance( getModel(), getServiceEndpoint() );
	}

	@Override
	public boolean isContainer( URI uri ) {
		return Moodle2SiocUtils.isForumDiscussionUri( uri, getServiceEndpoint() )
		        || Moodle2SiocUtils.isForumUri( uri, getServiceEndpoint() );
	}

	@Override
	public Container getContainer( URI uri ) throws NotFoundException, AuthenticationException,
	        IOException {
		if ( Moodle2SiocUtils.isForumUri( uri, getServiceEndpoint() ) ) {
			return getForum( uri );
		} else if ( Moodle2SiocUtils.isForumDiscussionUri( uri, getServiceEndpoint() ) ) {
			return getThread( uri );
		}

		throw new NotFoundException( "No container found at the uri " + uri );
	}

	@Override
	public List<Container> listContainer() throws AuthenticationException, IOException {
		List<Container> result = Lists.newArrayList();
		ForumRecord[] forumRecordArray = loadForums();

		if ( null != forumRecordArray ) {
			for ( ForumRecord forumRecord : forumRecordArray ) {
				result.add( Moodle2SiocUtils.createSiocForum(
				        getConnector(),
				        forumRecord,
				        getCourse( forumRecord.getCourse() ) ) );
			}
		}

		return result;
	}

	@Override
	public boolean hasChildContainer( URI uri ) {
		return Moodle2SiocUtils.isForumUri( uri, getServiceEndpoint() );
	}

	@Override
	public List<Container> listContainer( URI parentUri ) throws NumberFormatException,
	        AuthenticationException, IOException {
		Pattern pattern = Pattern.compile( "^" + getServiceEndpoint()
		        + Moodle2SiocUtils.REGEX_FORUM_URI );
		Matcher matcher = pattern.matcher( parentUri.toString() );

		List<Container> results = Lists.newArrayList();
		if ( matcher.find() ) {
			Forum parentForum = getForum( parentUri );
			ForumDiscussionRecord[] discussions = loadForumDiscussions(
			        Integer.parseInt(
			                matcher.group( 1 ) ) );

			if ( null != discussions ) {
				for ( ForumDiscussionRecord discussion : discussions ) {
					results.add( Moodle2SiocUtils.createSiocThread(
					        getConnector(),
					        discussion,
					        parentForum ) );
				}
			} else {
				LOG.warn( "No Threads load from " + parentUri );
			}
		} else {
			LOG.debug( parentUri + " is no moodle thread URI." );
		}

		return results;
	}

	private Forum getForum( URI uri ) throws AuthenticationException, IOException {
		if ( Forum.hasInstance( getModel(), uri ) ) {
			return Forum.getInstance( getModel(), uri );
		}

		Pattern pattern = Pattern.compile( "^" + getServiceEndpoint()
		        + Moodle2SiocUtils.REGEX_FORUM_URI );
		final Matcher matcher = pattern.matcher( uri.toString() );

		if ( matcher.find() ) {
			ForumRecord[] forumRecordArray = defaultClient.callMethod(
			        new Callable<ForumRecord[]>() {
				        @Override
				        public ForumRecord[] call() throws Exception {
					        return defaultClient
					                .getBindingStub()
					                .get_all_forums(
					                        defaultClient.getAuthClient(),
					                        defaultClient.getSessionKey(),
					                        "id",
					                        matcher.group( 1 ) );
				        }
			        } );

			if ( null != forumRecordArray && 0 < forumRecordArray.length ) {
				return Moodle2SiocUtils.createSiocForum(
				        getConnector(),
				        forumRecordArray[0],
				        getCourse( forumRecordArray[0].getCourse() ) );
			}
		}

		throw new NotFoundException( "No forum found at the uri " + uri );
	}

	private CourseRecord getCourse( final int courseId ) throws AuthenticationException,
	        IOException {
		if ( !courseMap.containsKey( courseId ) ) {
			CourseRecord[] courses = defaultClient.callMethod( new Callable<CourseRecord[]>() {
				@Override
				public CourseRecord[] call() throws Exception {
					return defaultClient.getBindingStub().get_course_byid(
					        defaultClient.getAuthClient(),
					        defaultClient.getSessionKey(),
					        Integer.toString( courseId ) );
				}
			} );
			if ( null != courses ) {
				courseMap.put( courses[0].getId(), courses[0] );
			}
		}

		return courseMap.get( courseId );
	}

	private Thread getThread( URI uri ) throws AuthenticationException, IOException {
		if ( Thread.hasInstance( getModel(), uri ) ) {
			return Thread.getInstance( getModel(), uri );
		}

		Pattern pattern = Pattern.compile( "^" + getServiceEndpoint()
		        + Moodle2SiocUtils.REGEX_DISCUSSION_URI );
		Matcher matcher = pattern.matcher( uri.toString() );

		if ( matcher.find() ) {
			int id = Integer.parseInt( matcher.group( 1 ) );
			ForumRecord[] forums = loadForums();

			if ( null != forums ) {
				for ( final ForumRecord forumRecord : forums ) {
					Forum parentForum = Moodle2SiocUtils.createSiocForum(
					        getConnector(),
					        forumRecord,
					        getCourse( forumRecord.getCourse() ) );

					ForumDiscussionRecord[] discussionsArray = loadForumDiscussions(
					        forumRecord.getId() );
					if ( null != discussionsArray ) {
						for ( ForumDiscussionRecord discussion : discussionsArray ) {
							if ( id == discussion.getPost().getDiscussion() ) {
								return Moodle2SiocUtils.createSiocThread(
								        getConnector(),
								        discussion, parentForum );
							}
						}
					}
				}
			}
		}

		throw new NotFoundException( "No thread found at the uri " + uri );
	}

	/**
	 * Load all Forums for Moodle.
	 * 
	 * @return An array of all found Forums.
	 * @throws IOException
	 * @throws AuthenticationException
	 */
	private ForumRecord[] loadForums() throws IOException, AuthenticationException {
		ForumRecord[] forumRecordArray = defaultClient
		        .callMethod( new Callable<ForumRecord[]>() {
			        @Override
			        public ForumRecord[] call() throws Exception {
				        return defaultClient
				                .getBindingStub()
				                .get_all_forums(
				                        defaultClient.getAuthClient(),
				                        defaultClient.getSessionKey(),
				                        "",
				                        "" );
			        }
		        } );
		return forumRecordArray;
	}

	/**
	 * Load all ForumDiscussions from a Forum.
	 * 
	 * @param id
	 *            ID of the Forum
	 * @return An array if all found ForumDiscussions
	 * @throws IOException
	 * @throws AuthenticationException
	 */
	private ForumDiscussionRecord[] loadForumDiscussions( final int id )
	        throws IOException, AuthenticationException {
		ForumDiscussionRecord[] discussionRecordArray = defaultClient.callMethod(
		        new Callable<ForumDiscussionRecord[]>() {
			        @Override
			        public ForumDiscussionRecord[] call() throws Exception {
				        return defaultClient
				                .getBindingStub()
				                .get_forum_discussions(
				                        defaultClient.getAuthClient(),
				                        defaultClient.getSessionKey(),
				                        id,
				                        999999 );
			        }
		        } );
		return discussionRecordArray;
	}
}
