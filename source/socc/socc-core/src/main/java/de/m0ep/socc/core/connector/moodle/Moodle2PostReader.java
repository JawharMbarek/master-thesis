package de.m0ep.socc.core.connector.moodle;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ontoware.rdf2go.model.Syntax;
import org.ontoware.rdf2go.model.node.URI;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import de.m0ep.moodlews.soap.ForumPostRecord;
import de.m0ep.socc.core.connector.DefaultConnectorIOComponent;
import de.m0ep.socc.core.connector.IConnector.IPostReader;
import de.m0ep.socc.core.exceptions.AccessControlException;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.exceptions.NotFoundException;
import de.m0ep.socc.core.utils.RdfUtils;
import de.m0ep.socc.core.utils.SiocUtils;
import de.m0ep.socc.core.utils.SoccUtils;

public class Moodle2PostReader extends
        DefaultConnectorIOComponent<Moodle2Connector> implements IPostReader<Moodle2Connector> {
	private static final Logger LOG = LoggerFactory
	        .getLogger( Moodle2PostReader.class );

	private final Moodle2ClientWrapper defaultClient;

	public Moodle2PostReader( Moodle2Connector connector ) {
		super( connector );
		this.defaultClient = connector.getClientManager().getDefaultClient();
	}

	@Override
	public boolean isPost( URI uri ) {
		return Moodle2SiocUtils.isForumPostUri( uri, getServiceEndpoint() );
	}

	@Override
	public Post getPost( URI uri )
	        throws AuthenticationException,
	        IOException,
	        AccessControlException {
		Preconditions.checkNotNull( uri,
		        "Required parameter uri must be specified." );

		if ( Post.hasInstance( getModel(), uri ) ) {
			return Post.getInstance( getModel(), uri );
		}

		Pattern pattern = Pattern.compile( Moodle2SiocUtils.REGEX_FORUM_POST_URI );
		Matcher matcher = pattern.matcher( uri.toString() );

		if ( matcher.find() ) {
			final int discussionId = Integer.parseInt( matcher.group( 1 ) );
			final int postId = Integer.parseInt( matcher.group( 2 ) );

			ForumPostRecord[] postRecordArray = defaultClient.callMethod(
			        new Callable<ForumPostRecord[]>() {
				        @Override
				        public ForumPostRecord[] call() throws Exception {
					        return defaultClient
					                .getBindingStub()
					                .get_forum_posts(
					                        defaultClient.getAuthClient(),
					                        defaultClient.getSessionKey(),
					                        discussionId,
					                        Integer.MAX_VALUE );
				        }
			        } );

			ForumPostRecord postRecord = findPostRecordWithId( postRecordArray, postId );

			if ( null != postRecord ) {
				Container container = getConnector().getStructureReader().getContainer(
				        Moodle2SiocUtils.createForumDiscussionUri(
				                getServiceEndpoint(),
				                discussionId ) );

				if ( LOG.isDebugEnabled() ) {
					LOG.debug( "Read entry '{}' from '{}':\n{}",
					        postRecord.getId(),
					        getServiceEndpoint(),
					        postRecord );
				} else {
					LOG.info( "Read entry '{}' from '{}'",
					        postRecord.getId(),
					        getServiceEndpoint() );
				}

				Post result = Moodle2SiocUtils.createSiocPost(
				        getConnector(),
				        postRecord,
				        container,
				        null );

				if ( LOG.isDebugEnabled() ) {
					LOG.debug( "Converted entry '{}' to SIOC:\n{}",
					        postRecord.getId(),
					        RdfUtils.resourceToString( result, Syntax.Turtle ) );
				} else {
					LOG.info( "Converted entry '{}' to SIOC {}",
					        postRecord.getId(),
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

		throw new NotFoundException( "No post found at uri " + uri );
	}

	@Override
	public boolean hasPosts( URI uri ) {
		return Moodle2SiocUtils.isForumDiscussionUri( uri, getServiceEndpoint() )
		        || Moodle2SiocUtils.isForumPostUri( uri, getServiceEndpoint() );
	}

	@Override
	public List<Post> pollPosts(
	        final URI sourceUri,
	        final Date since,
	        final int limit )
	        throws AuthenticationException,
	        IOException,
	        AccessControlException {
		Preconditions.checkNotNull( sourceUri,
		        "Required parameter uri must be specified." );

		if ( Moodle2SiocUtils.isForumPostUri( sourceUri, getServiceEndpoint() ) ) {
			Post post = getPost( sourceUri );
			return pollRepliesAtPost( post, since, Math.max( -1, limit ) );
		} else if ( Moodle2SiocUtils.isForumDiscussionUri( sourceUri, getServiceEndpoint() ) ) {
			Container container = getConnector().getStructureReader().getContainer( sourceUri );
			return pollPostsAtContainer( container, since, Math.max( -1, limit ) );
		}

		throw new IOException( "Can't poll posts from uri "
		        + sourceUri
		        + " at service "
		        + getServiceEndpoint() );
	}

	/**
	 * Polls post from a {@link Container}.
	 * 
	 * @param container
	 * @param since
	 * @param limit
	 * @return
	 * @throws AuthenticationException
	 * @throws IOException
	 */
	private List<Post> pollPostsAtContainer(
	        final Container container,
	        final Date since,
	        final long limit )
	        throws AuthenticationException,
	        IOException {

		final int discussionId;
		try {
			discussionId = Integer.parseInt( container.getId() );
		} catch ( NumberFormatException e ) {
			throw new IllegalArgumentException(
			        "The id of the container is invalid: was "
			                + container.getId() );
		}

		ForumPostRecord[] postRecordArray = defaultClient
		        .callMethod( new Callable<ForumPostRecord[]>() {
			        @Override
			        public ForumPostRecord[] call() throws Exception {
				        return defaultClient
				                .getBindingStub()
				                .get_forum_posts(
				                        defaultClient.getAuthClient(),
				                        defaultClient.getSessionKey(),
				                        discussionId,
				                        Integer.MAX_VALUE );
			        }
		        } );

		List<Post> result = Lists.newArrayList();
		if ( null != postRecordArray ) {
			for ( ForumPostRecord postRecord : postRecordArray ) {
				addEntryToList(
				        result,
				        since,
				        limit,
				        container,
				        null,
				        postRecord );
			}
		}

		return result;
	}

	/**
	 * Polls replies from a post.0
	 * 
	 * @param post
	 * @param since
	 * @param limit
	 * @return
	 * @throws AuthenticationException
	 * @throws IOException
	 */
	private List<Post> pollRepliesAtPost( final Post post, final Date since, final long limit )
	        throws AuthenticationException,
	        IOException {
		Container container = getConnector().getStructureReader()
		        .getContainer( post.getContainer().asURI() );

		final int discussionId;
		try {
			discussionId = Integer.parseInt( container.getId() );
		} catch ( NumberFormatException e ) {
			throw new IllegalArgumentException(
			        "The id of the container is invalid: was "
			                + container.getId() );
		}

		final int postId;
		try {
			postId = Integer.parseInt( post.getId() );
		} catch ( NumberFormatException e ) {
			throw new IllegalArgumentException(
			        "The id of the parentPost is invalid: was "
			                + post.getId() );
		}

		ForumPostRecord[] postRecordArray = defaultClient.callMethod(
		        new Callable<ForumPostRecord[]>() {
			        @Override
			        public ForumPostRecord[] call() throws Exception {
				        return defaultClient.getBindingStub()
				                .get_forum_posts(
				                        defaultClient.getAuthClient(),
				                        defaultClient.getSessionKey(),
				                        discussionId,
				                        Integer.MAX_VALUE );
			        }
		        } );

		List<Post> result = Lists.newArrayList();
		if ( null != postRecordArray ) {
			ForumPostRecord postRecord = findPostRecordWithId( postRecordArray, postId );

			if ( null != postRecord ) {
				ForumPostRecord[] children = postRecord.getChildren();
				if ( null != children ) {
					for ( ForumPostRecord forumPost : children ) {
						addEntryToList(
						        result,
						        since,
						        limit,
						        container,
						        post,
						        forumPost );
					}
				}
			} else {
				LOG.warn( "No post found in thread {} with id {} to read replies from.",
				        discussionId, postId );
			}
		}

		return result;
	}

	private ForumPostRecord findPostRecordWithId( ForumPostRecord[] postRecordArray, int postId ) {
		for ( ForumPostRecord postRecord : postRecordArray ) {
			if ( postId == postRecord.getId() ) {
				return postRecord;
			}
	
			ForumPostRecord[] children = postRecord.getChildren();
			if ( null != children && 0 < children.length ) {
				ForumPostRecord result = findPostRecordWithId( children, postId );
	
				if ( null != result ) {
					return result;
				}
			}
		}
	
		return null;
	}

	private void addEntryToList(
	        final List<Post> resultList,
	        final Date since,
	        final long limit,
	        final Container container,
	        final Post parentPost,
	        final ForumPostRecord postRecord )
	        throws AuthenticationException,
	        IOException {
		if ( LOG.isDebugEnabled() ) {
			LOG.debug( "Read entry '{}' from '{}':\n{}",
			        postRecord.getId(),
			        getServiceEndpoint(),
			        postRecord );
		} else {
			LOG.info( "Read entry '{}' from '{}'",
			        postRecord.getId(),
			        getServiceEndpoint() );
		}

		if ( 0 > limit || limit < resultList.size() ) {
			Post post = Moodle2SiocUtils.createSiocPost(
			        getConnector(),
			        postRecord,
			        SiocUtils.asThread( container ),
			        parentPost );

			if ( LOG.isDebugEnabled() ) {
				LOG.debug( "Converted entry '{}' to SIOC:\n{}",
				        postRecord.getId(),
				        RdfUtils.resourceToString( post, Syntax.Turtle ) );
			} else {
				LOG.info( "Converted entry '{}' to SIOC {}",
				        postRecord.getId(),
				        post );
			}

			if ( SoccUtils.haveReadAccess(
			        getConnector(),
			        post.getCreator(),
			        post.getContainer() ) ) {
				Date createdDate = new Date( postRecord.getCreated() * 1000L );
				if ( null == since || createdDate.after( since ) ) {
					resultList.add( post );
					LOG.info( "Added {} to polling result. result size: {}",
					        post,
					        resultList.size() );
				} else {
					LOG.info( "Skip Post '{}', it's to old.", post );
				}
			} else {
				LOG.info( "Have no permission to read posts for this UserAccount='{}'",
				        post.getCreator() );
				SoccUtils.anonymisePost( post );
			}

			// add children
			if ( null != postRecord.getChildren() ) {
				for ( ForumPostRecord children : postRecord.getChildren() ) {
					addEntryToList(
					        resultList,
					        since,
					        Math.max( -1, limit - resultList.size() ),
					        container,
					        post,
					        children );
				}
			}
		} else {
			LOG.info( "Limit reached: limit={} size={}", limit, resultList.size() );
		}
	}
}
