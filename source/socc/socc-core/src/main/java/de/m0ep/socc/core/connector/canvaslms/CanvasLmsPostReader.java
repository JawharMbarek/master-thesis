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
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ontoware.rdf2go.model.Syntax;
import org.ontoware.rdf2go.model.node.URI;
import org.openrdf.sail.memory.MemoryStore;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import de.m0ep.canvas.CanvasLmsClient;
import de.m0ep.canvas.Pagination;
import de.m0ep.canvas.exceptions.CanvasLmsException;
import de.m0ep.canvas.model.DiscussionTopic;
import de.m0ep.canvas.model.Entry;
import de.m0ep.socc.core.connector.DefaultConnectorIOComponent;
import de.m0ep.socc.core.connector.IConnector.IPostReader;
import de.m0ep.socc.core.exceptions.AccessControlException;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.exceptions.NotFoundException;
import de.m0ep.socc.core.utils.RdfUtils;
import de.m0ep.socc.core.utils.SoccUtils;

/**
 * Implementation of an {@link IPostReader} for the {@link CanvasLmsConnector}.
 * 
 * @author Florian Müller
 */
public class CanvasLmsPostReader extends
        DefaultConnectorIOComponent<CanvasLmsConnector> implements
        IPostReader<CanvasLmsConnector> {
	private static final Logger LOG = LoggerFactory.getLogger( CanvasLmsPostReader.class );

	private final CanvasLmsClient defaultClient;

	MemoryStore store;

	/**
	 * Constructs a new {@link CanvasLmsPostReader}.
	 * 
	 * @param connector
	 *            {@link CanvasLmsConnector} for that PostWriter.
	 */
	public CanvasLmsPostReader( final CanvasLmsConnector connector ) {
		super( connector );
		this.defaultClient = connector.getClientManager()
		        .getDefaultClient();
	}

	@Override
	public boolean isPost( final URI uri ) {
		return CanvasLmsSiocUtils.isInitialEntryUri( uri, getServiceEndpoint() )
		        || CanvasLmsSiocUtils.isEntryUri( uri, getServiceEndpoint() );
	}

	@Override
	public Post getPost( final URI uri )
	        throws NotFoundException,
	        AuthenticationException,
	        IOException {
		LOG.info( "Read post from '{}'", uri );

		if ( Post.hasInstance( getModel(), uri ) ) {
			return Post.getInstance( getModel(), uri );
		}

		try {
			if ( CanvasLmsSiocUtils.isEntryUri( uri, getServiceEndpoint() ) ) {
				return readEntry( uri );
			} else if ( CanvasLmsSiocUtils.isInitialEntryUri( uri,
			        getServiceEndpoint() ) ) {
				return readInitialEntry( uri );
			}
		} catch ( Exception e ) {
			CanvasLmsConnector.handleCanvasExceptions( e );
		}

		throw new NotFoundException( "No Canvas LMS post found at uri " + uri );
	}

	@Override
	public boolean hasPosts( final URI uri ) {
		return CanvasLmsSiocUtils.isDiscussionTopicUri( uri,
		        getServiceEndpoint() )
		        || CanvasLmsSiocUtils.isInitialEntryUri( uri,
		                getServiceEndpoint() )
		        || CanvasLmsSiocUtils.isEntryUri( uri, getServiceEndpoint() );
	}

	@Override
	public List<Post> pollPosts( final URI sourceUri, final Date since, final int limit )
	        throws AuthenticationException,
	        IOException {
		LOG.info( "Poll posts from sourceUri='{}' since='{}' limit='{}'",
		        sourceUri,
		        since,
		        limit );
		try {
			if ( CanvasLmsSiocUtils.isDiscussionTopicUri( sourceUri, getServiceEndpoint() )
			        || CanvasLmsSiocUtils.isInitialEntryUri( sourceUri, getServiceEndpoint() ) ) {
				return pollPostsFromDiscussionTopic( sourceUri, since, limit );

			} else if ( CanvasLmsSiocUtils.isEntryUri( sourceUri, getServiceEndpoint() ) ) {
				return pollPostsFromEntry( sourceUri, since, limit );
			}
		} catch ( Exception e ) {
			CanvasLmsConnector.handleCanvasExceptions( e );
		}

		throw new IOException(
		        "Can't poll posts from uri "
		                + sourceUri
		                + " at service "
		                + getServiceEndpoint() );
	}

	/**
	 * Reads a an Entry from an URI and converts it to SIOC
	 * 
	 * @param uri
	 *            URI of an Entry
	 * @return A {@link Post} converted from the Entry
	 * 
	 * @throws NotFoundException
	 *             Thrown if no resource was found at the URI
	 * @throws AuthenticationException
	 *             Thrown if there is a problem with authentication.
	 * @throws IOException
	 *             Thrown if there ist problem in communication.
	 * @throws AccessControlException
	 *             Thrown if there is a problem with access control.
	 */
	private Post readEntry( final URI uri )
	        throws NotFoundException,
	        AuthenticationException,
	        IOException,
	        AccessControlException {
		if ( Post.hasInstance( getModel(), uri ) ) {
			return Post.getInstance( getModel(), uri );
		}

		Pattern pattern = Pattern.compile( getServiceEndpoint()
		        + CanvasLmsSiocUtils.REGEX_ENTRY_URI );
		Matcher matcher = pattern.matcher( uri.toString() );

		if ( matcher.find() ) {
			String endpoint = matcher.group( 1 );
			long endpointId = Long.parseLong( matcher.group( 2 ) );
			long topicId = Long.parseLong( matcher.group( 3 ) );
			long entryId = Long.parseLong( matcher.group( 4 ) );

			Entry entry = null;
			try {
				if ( CanvasLmsSiocUtils.ENDPOINT_COURSE.equals( endpoint ) ) {
					entry = defaultClient.courses()
					        .discussionTopics( endpointId )
					        .entries( topicId )
					        .get( entryId )
					        .execute();
				} else if ( CanvasLmsSiocUtils.ENDPOINT_GROUPS.equals( endpoint ) ) {
					entry = defaultClient.groups()
					        .discussionTopics( endpointId )
					        .entries( topicId )
					        .get( entryId )
					        .execute();
				} else {
					throw new IOException( "Unknown Canvas endpoint '" + endpoint + "'" );
				}
			} catch ( CanvasLmsException e ) {
				CanvasLmsConnector.handleCanvasExceptions( e );
			}

			if ( null != entry ) {
				Container container = getConnector().getStructureReader().getContainer(
				        CanvasLmsSiocUtils.createDiscussionTopicUri(
				                getServiceEndpoint(),
				                endpoint,
				                endpointId,
				                topicId ) );

				if ( LOG.isDebugEnabled() ) {
					LOG.debug( "Read entry '{}' from {}:\n{}",
					        entry.getId(),
					        uri,
					        entry );
				} else {
					LOG.info( "Read entry '{}' from {}.",
					        entry.getId(),
					        uri );
				}

				Post result = CanvasLmsSiocUtils.createSiocPost(
				        getConnector(),
				        endpoint,
				        entry,
				        container,
				        null );

				if ( LOG.isDebugEnabled() ) {
					LOG.debug( "Converted entry '{}' to SIOC:\n{}",
					        entry.getId(),
					        RdfUtils.resourceToString( result, Syntax.Turtle ) );
				} else {
					LOG.info( "Converted entry '{}' to SIOC {}",
					        entry.getId(),
					        result );
				}

				if ( !SoccUtils.haveReadAccess(
				        getConnector(),
				        result.getCreator(),
				        result.getContainer() ) ) {
					SoccUtils.anonymisePost( result );
					throw new AccessControlException( "Have no permission to read post '"
					        + uri
					        + "'" );
				}

				return result;
			}
		}

		throw new NotFoundException( "Can't read post from uri " + uri );
	}

	/**
	 * Reads the initial entry of a discussion topic and converts it to SIOC
	 * 
	 * @param uri
	 *            URI of the discussion topic
	 * 
	 * @return A {@link Post} converted form the initial entry.
	 * 
	 * @throws NotFoundException
	 *             Thrown if no resource was found at the URI
	 * @throws AuthenticationException
	 *             Thrown if there is a problem with authentication.
	 * @throws IOException
	 *             Thrown if there ist problem in communication.
	 * @throws AccessControlException
	 *             Thrown if there is a problem with access control.
	 */
	private Post readInitialEntry( final URI uri )
	        throws NotFoundException,
	        AuthenticationException,
	        IOException,
	        AccessControlException {
		if ( Post.hasInstance( getModel(), uri ) ) {
			return Post.getInstance( getModel(), uri );
		}

		Pattern pattern = Pattern.compile( getServiceEndpoint()
		        + CanvasLmsSiocUtils.REGEX_INITIAL_ENTRY_URI );
		Matcher matcher = pattern.matcher( uri.toString() );

		if ( matcher.find() ) {
			String endpoint = matcher.group( 1 );
			long endpointId = Long.parseLong( matcher.group( 2 ) );
			long topicId = Long.parseLong( matcher.group( 3 ) );

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
				Container container = getConnector().getStructureReader().getContainer(
				        CanvasLmsSiocUtils.createDiscussionTopicUri(
				                getServiceEndpoint(),
				                endpoint,
				                endpointId,
				                topicId ) );
				if ( LOG.isDebugEnabled() ) {
					LOG.debug( "Read initial entry '{}' from {}:\n{}",
					        discussionTopic.getId(),
					        uri,
					        discussionTopic );
				} else {
					LOG.info( "Read initial entry '{}' from {}.",
					        discussionTopic.getId(),
					        uri );
				}
				Post result = CanvasLmsSiocUtils.createSiocPost(
				        getConnector(),
				        endpoint,
				        container,
				        discussionTopic,
				        endpointId );
				if ( !SoccUtils.haveReadAccess(
				        getConnector(),
				        result.getCreator(),
				        result.getContainer() ) ) {
					SoccUtils.anonymisePost( result );
					throw new AccessControlException( "Have no permission to read post '"
					        + uri
					        + "'" );
				}
				if ( LOG.isDebugEnabled() ) {
					LOG.debug( "Converted initial entry to SIOC:\n{}",
					        RdfUtils.resourceToString(
					                result,
					                Syntax.Turtle ) );
				} else {
					LOG.info( "Converted entry to SIOC:'{}'", result );
				}

				return result;
			}

		}

		throw new NotFoundException( "Can't read post from uri " + uri );
	}

	/**
	 * Polls new entries from a discussion topic and converts them to a
	 * {@link List} of {@link Post}s.
	 * 
	 * @param sourceUri
	 *            URI to the discussion topic.
	 * @param since
	 *            Data since a post is new
	 * @param limit
	 *            Limit result size to this number.
	 * @return A {@link List} of {@link Post}s
	 * 
	 * @throws NotFoundException
	 *             Thrown if no resource was found at the URI
	 * @throws AuthenticationException
	 *             Thrown if there is a problem with authentication.
	 * @throws IOException
	 *             Thrown if there ist problem in communication.
	 * @throws AccessControlException
	 *             Thrown if there is a problem with access control.
	 */
	private List<Post> pollPostsFromDiscussionTopic(
	        final URI sourceUri,
	        final Date since,
	        final int limit )
	        throws NotFoundException,
	        AuthenticationException,
	        IOException,
	        AccessControlException {
		Pattern pattern = Pattern.compile(
		        getServiceEndpoint() + CanvasLmsSiocUtils.REGEX_DISCUSSION_TOPIC_URI );
		Matcher matcher = pattern.matcher( sourceUri.toString() );
		List<Post> result = Lists.newArrayList();

		if ( matcher.find() ) {
			String endpoint = matcher.group( 1 );
			long endpointId = Long.parseLong( matcher.group( 2 ) );
			long topicId = Long.parseLong( matcher.group( 3 ) );

			Container container = getConnector().getStructureReader().getContainer(
			        CanvasLmsSiocUtils.createDiscussionTopicUri(
			                getServiceEndpoint(),
			                endpoint,
			                endpointId,
			                topicId ) );

			Post initPost = readInitialEntry( CanvasLmsSiocUtils.createInitialEntryUri(
			        getServiceEndpoint(),
			        endpoint,
			        endpointId,
			        topicId ) );

			Pagination<Entry> pagination = null;
			try {
				if ( CanvasLmsSiocUtils.ENDPOINT_COURSE.equals( endpoint ) ) {
					pagination = defaultClient.courses()
					        .discussionTopics( endpointId )
					        .entries( topicId )
					        .list()
					        .executePagination();
				} else if ( CanvasLmsSiocUtils.ENDPOINT_GROUPS.equals( endpoint ) ) {
					pagination = defaultClient.groups()
					        .discussionTopics( endpointId )
					        .entries( topicId )
					        .list()
					        .executePagination();
				} else {
					throw new IOException( "Unknown Canvas endpoint '" + endpoint + "'" );
				}
			} catch ( CanvasLmsException e ) {
				CanvasLmsConnector.handleCanvasExceptions( e );
			}

			if ( null != pagination ) {
				for ( List<Entry> entryPage : pagination ) {
					for ( Entry entry : entryPage ) {
						addEntryToList(
						        result,
						        since,
						        limit,
						        entry,
						        container,
						        initPost,
						        endpoint,
						        endpointId,
						        topicId );
					}
				}
			}
		}

		return result;
	}

	/**
	 * Polls all new comments of an entry and converts them to a {@link List} of
	 * {@link Post}s.
	 * 
	 * @param sourceUri
	 *            URI to the entry.
	 * @param since
	 *            Data since a post is new
	 * @param limit
	 *            Limit result size to this number.
	 * @return A {@link List} of {@link Post}s
	 * 
	 * @throws NotFoundException
	 *             Thrown if no resource was found at the URI
	 * @throws AuthenticationException
	 *             Thrown if there is a problem with authentication.
	 * @throws IOException
	 *             Thrown if there ist problem in communication.
	 * @throws AccessControlException
	 *             Thrown if there is a problem with access control.
	 */
	private List<Post> pollPostsFromEntry( final URI sourceUri, final Date since, final int limit )
	        throws NotFoundException,
	        AuthenticationException,
	        IOException,
	        AccessControlException {
		List<Post> result = Lists.newArrayList();

		Pattern pattern = Pattern.compile( getServiceEndpoint()
		        + CanvasLmsSiocUtils.REGEX_ENTRY_URI );
		Matcher matcher = pattern.matcher( sourceUri.toString() );
		if ( matcher.find() ) {
			String endpoint = matcher.group( 1 );
			long endpointId = Long.parseLong( matcher.group( 2 ) );
			long topicId = Long.parseLong( matcher.group( 3 ) );
			long entryId = Long.parseLong( matcher.group( 4 ) );

			Container container = getConnector().getStructureReader().getContainer(
			        CanvasLmsSiocUtils.createDiscussionTopicUri(
			                getServiceEndpoint(),
			                endpoint,
			                endpointId,
			                topicId ) );

			Post parentPost = readEntry( CanvasLmsSiocUtils.createEntryUri(
			        getServiceEndpoint(),
			        endpoint,
			        endpointId,
			        topicId,
			        entryId ) );

			Pagination<Entry> pagination = null;
			try {
				if ( CanvasLmsSiocUtils.ENDPOINT_COURSE.equals( endpoint ) ) {
					pagination = defaultClient.courses()
					        .discussionTopics( endpointId )
					        .entries( topicId )
					        .listReplies( entryId )
					        .executePagination();
				} else if ( CanvasLmsSiocUtils.ENDPOINT_GROUPS.equals( endpoint ) ) {
					pagination = defaultClient.groups()
					        .discussionTopics( endpointId )
					        .entries( topicId )
					        .listReplies( entryId )
					        .executePagination();
				} else {
					throw new IOException( "Unknown Canvas endpoint '" + endpoint + "'" );
				}
			} catch ( Exception e ) {
				CanvasLmsConnector.handleCanvasExceptions( e );
			}

			if ( null != pagination ) {
				for ( List<Entry> replyPage : pagination ) {
					for ( Entry reply : replyPage ) {
						addEntryToList(
						        result,
						        since,
						        Math.max( -1, limit - result.size() ),
						        reply,
						        container,
						        parentPost,
						        endpoint,
						        endpointId,
						        topicId );
					}
				}
			}
		}

		return result;
	}

	/**
	 * Adds a entry to the <code>resultList</code> and all it's comments
	 * matching the <code>since</code> and <code>limit</code> criteria.
	 * 
	 * @param resultList
	 *            {@link List} to add the entry to.
	 * @param since
	 *            Data since a post is new
	 * @param limit
	 *            Limit result size to this number.
	 * @param entry
	 *            The entry to add
	 * @param container
	 *            The parent {@link Container}
	 * @param parentPost
	 *            The parent {@link Post}.
	 * @param courseId
	 *            The id of the course.
	 * @param topicId
	 *            The id of the discussion topic.
	 * 
	 * @throws NotFoundException
	 *             Thrown if no resource was found at the URI
	 * @throws AuthenticationException
	 *             Thrown if there is a problem with authentication.
	 * @throws IOException
	 *             Thrown if there ist problem in communication.
	 */
	private void addEntryToList(
	        final List<Post> resultList,
	        final Date since,
	        final int limit,
	        final Entry entry,
	        final Container container,
	        final Post parentPost,
	        final String endpoint,
	        final long endpointId,
	        final long topicId )
	        throws NotFoundException,
	        AuthenticationException,
	        IOException {

		if ( LOG.isDebugEnabled() ) {
			LOG.debug( "Read entry '{}' from '{}':\n{}",
			        entry.getId(),
			        getServiceEndpoint(),
			        entry );
		} else {
			LOG.info( "Read entry '{}' from '{}'",
			        entry.getId(),
			        getServiceEndpoint() );
		}

		if ( 0 > limit || limit < resultList.size() ) {
			Post post = CanvasLmsSiocUtils.createSiocPost(
			        getConnector(),
			        endpoint,
			        entry,
			        container,
			        parentPost );

			if ( LOG.isDebugEnabled() ) {
				LOG.debug( "Converted entry '{}' to SIOC:\n{}",
				        entry.getId(),
				        RdfUtils.resourceToString( post, Syntax.Turtle ) );
			} else {
				LOG.info( "Converted entry '{}' to SIOC {}",
				        entry.getId(),
				        post );
			}

			if ( SoccUtils.haveReadAccess(
			        getConnector(),
			        post.getCreator(),
			        post.getContainer() ) ) {

				Date createdDate = entry.getCreatedAt();
				if ( null == since || createdDate.after( since ) ) {
					if ( !resultList.contains( post ) ) {
						resultList.add( post );
						LOG.info( "Added {} to polling result. result size: {}",
						        post,
						        resultList.size() );
					} else {
						LOG.info( "Post {} already in list", post );
					}
				} else {
					LOG.info( "Skip Post '{}', it's to old.", post );
				}
			} else {
				LOG.info( "Have no permission to read posts for this UserAccount='{}'",
				        post.getCreator() );
				SoccUtils.anonymisePost( post );
			}

			// read recentReplies
			Entry[] recentReplies = entry.getRecentReplies();
			if ( null != recentReplies ) {
				for ( Entry replyEntry : recentReplies ) {
					addEntryToList(
					        resultList,
					        since,
					        Math.max( -1, limit - resultList.size() ),
					        replyEntry,
					        container,
					        post,
					        endpoint,
					        endpointId,
					        topicId );
				}
			}

			// read more replies if necessary
			if ( entry.hasMoreReplies() ) {
				Pagination<Entry> pagination = null;
				try {
					if ( CanvasLmsSiocUtils.ENDPOINT_COURSE.equals( endpoint ) ) {
						pagination = defaultClient.courses()
						        .discussionTopics( endpointId )
						        .entries( topicId )
						        .listReplies( entry.getId() )
						        .executePagination();
					} else if ( CanvasLmsSiocUtils.ENDPOINT_GROUPS.equals( endpoint ) ) {
						pagination = defaultClient.groups()
						        .discussionTopics( endpointId )
						        .entries( topicId )
						        .listReplies( entry.getId() )
						        .executePagination();
					} else {
						throw new IOException( "Unknown Canvas endpoint '" + endpoint + "'" );
					}
				} catch ( CanvasLmsException e ) {
					CanvasLmsConnector.handleCanvasExceptions( e );
				}

				if ( null != pagination ) {
					for ( List<Entry> replyPage : pagination ) {
						for ( Entry replyEntry : replyPage ) {
							addEntryToList(
							        resultList,
							        since,
							        Math.max( -1, limit - resultList.size() ),
							        replyEntry,
							        container,
							        post,
							        endpoint,
							        endpointId,
							        topicId );
						}
					}
				}
			}
		} else {
			LOG.info( "Limit reached: limit={} size={}", limit, resultList.size() );
		}
	}
}
