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
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.model.node.Variable;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Site;
import org.rdfs.sioc.Thread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

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
	private final Moodle2ClientWrapper defaultClient;

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
	public Container getContainer( URI uri ) throws NotFoundException, AuthenticationException,
	        IOException {
		if ( getModel().contains( uri, Variable.ANY, Variable.ANY ) ) {
			return Container.getInstance( getModel(), uri );
		}

		if ( Moodle2SiocUtils.isForumUri( uri, getServiceEndpoint() ) ) {
			return getForum( uri );
		} else if ( Moodle2SiocUtils.isThreadUri( uri, getServiceEndpoint() ) ) {
			return getThread( uri );
		}

		throw new NotFoundException( "No container found at the uri " + uri );
	}

	@Override
	public List<Container> listContainer() throws AuthenticationException, IOException {
		List<Container> result = Lists.newArrayList();
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

		if ( null != forumRecordArray && 0 < forumRecordArray.length ) {
			for ( ForumRecord forumRecord : forumRecordArray ) {
				result.add( Moodle2SiocUtils.createSiocForum(
				        getConnector(),
				        forumRecord ) );
			}
		}

		return result;
	}

	@Override
	public List<Container> listContainer( URI parent ) throws NumberFormatException,
	        AuthenticationException, IOException {
		Matcher matcher = Pattern
		        .compile( getServiceEndpoint() + Moodle2SiocUtils.REGEX_FORUM_URI )
		        .matcher( parent.toString() );

		List<Container> results = Lists.newArrayList();
		if ( matcher.matches() ) {
			Forum parentForum = getForum( parent );
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
			}
		} else {
			LOG.debug( parent + " is no moodle thread URI." );
		}

		return results;
	}

	private Forum getForum( URI uri ) throws AuthenticationException, IOException {
		if ( getModel().contains( uri, Variable.ANY, Variable.ANY ) ) {
			return Forum.getInstance( getModel(), uri );
		}

		final Matcher matcher = Pattern
		        .compile( getServiceEndpoint() + Moodle2SiocUtils.REGEX_FORUM_URI )
		        .matcher( uri.toString() );

		if ( matcher.matches() ) {
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
				        forumRecordArray[0] );
			}
		}

		throw new NotFoundException( "No forum found at the uri " + uri );
	}

	private Thread getThread( URI uri ) throws AuthenticationException, IOException {
		if ( getModel().contains( uri, Variable.ANY, Variable.ANY ) ) {
			return Thread.getInstance( getModel(), uri );
		}

		Matcher matcher = Pattern
		        .compile( getServiceEndpoint() + Moodle2SiocUtils.REGEX_DISCUSSION_URI )
		        .matcher( uri.toString() );

		if ( matcher.matches() ) {
			int id = Integer.parseInt( matcher.group( 1 ) );
			ForumRecord[] forumArray = loadForums();

			if ( null != forumArray ) {
				for ( final ForumRecord forum : forumArray ) {
					Forum parentForum = Moodle2SiocUtils.createSiocForum(
					        getConnector(),
					        forum );

					ForumDiscussionRecord[] discussionsArray = loadForumDiscussions( forum.getId() );
					if ( null != discussionsArray ) {
						for ( ForumDiscussionRecord discussion : discussionsArray ) {
							if ( id == discussion.getId() ) {
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
				                        9999 );
			        }
		        } );
		return discussionRecordArray;
	}
}
