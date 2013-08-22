package de.m0ep.socc.core.connector.moodle;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ontoware.rdf2go.model.node.URI;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.Thread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import de.m0ep.moodlews.soap.ForumPostRecord;
import de.m0ep.socc.core.connector.DefaultConnectorIOComponent;
import de.m0ep.socc.core.connector.IConnector.IPostReader;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.exceptions.NotFoundException;
import de.m0ep.socc.core.utils.SiocUtils;

public class Moodle2PostReader extends
        DefaultConnectorIOComponent<Moodle2Connector> implements IPostReader<Moodle2Connector> {
	private static final Logger LOG = LoggerFactory
	        .getLogger( Moodle2PostReader.class );

	private final Moodle2ClientWrapper defaultClient;

	public Moodle2PostReader( Moodle2Connector connector ) {
		super( connector );
		this.defaultClient = connector.getServiceClientManager()
		        .getDefaultClient();
	}

	@Override
	public Post readPost( URI uri ) throws AuthenticationException, IOException {
		Preconditions.checkNotNull( uri,
		        "Required parameter uri must be specified." );

		if ( Post.hasInstance( getModel(), uri ) ) {
			return Post.getInstance( getModel(), uri );
		}

		Pattern pattern = Pattern.compile( Moodle2SiocUtils.REGEX_ENTRY_URI );
		Matcher matcher = pattern.matcher( uri.toString() );

		if ( matcher.matches() ) {
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
					                        9999999 );
				        }
			        } );

			ForumPostRecord postRecord = findPostRecordWithId( postRecordArray, postId );

			if ( null != postRecord ) {
				Container discussion = getConnector().getStructureReader().getContainer(
				        Moodle2SiocUtils.createSiocThreadUri(
				                getServiceEndpoint(),
				                discussionId ) );

				return Moodle2SiocUtils.createSiocPost(
				        getConnector(),
				        postRecord,
				        SiocUtils.asThread( discussion ),
				        null );
			}
		}

		throw new NotFoundException( "No post found at uri " + uri );
	}

	@Override
	public List<Post> pollPosts( URI sourceUri, Date since, int limit ) throws
	        AuthenticationException,
	        IOException {
		Preconditions.checkNotNull( sourceUri,
		        "Required parameter uri must be specified." );
		limit = Math.max( -1, limit );

		if ( Moodle2SiocUtils.isPostUri( sourceUri, getServiceEndpoint() ) ) {
			Post post = readPost( sourceUri );
			return pollRepliesAtPost( post, since, limit );
		} else if ( Moodle2SiocUtils.isThreadUri( sourceUri, getServiceEndpoint() ) ) {
			Container container = getConnector().getStructureReader().getContainer( sourceUri );
			return pollPostsAtContainer( container, since, limit );
		}

		throw new IOException( "Can't poll posts from " + sourceUri + " at this service " );
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
	private List<Post> pollPostsAtContainer( final Container container, final Date since,
	        final long limit ) throws
	        AuthenticationException,
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
				                        (int) limit );
			        }
		        } );

		List<Post> result = Lists.newArrayList();
		if ( null != postRecordArray && 0 < postRecordArray.length ) {
			result.addAll(
			        extractPosts(
			                since,
			                limit,
			                SiocUtils.asThread( container ),
			                null,
			                postRecordArray ) );
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
	        throws
	        AuthenticationException,
	        IOException {
		Container container = getConnector().getStructureReader().getContainer(
		        post.getContainer().asURI() );

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
				                        (int) limit );
			        }
		        } );

		List<Post> result = Lists.newArrayList();
		if ( null != postRecordArray && 0 < postRecordArray.length ) {
			ForumPostRecord postRecord = findPostRecordWithId( postRecordArray,
			        postId );

			if ( null != postRecord ) {
				ForumPostRecord[] children = postRecord.getChildren();
				if ( null != children && 0 < children.length ) {
					result.addAll(
					        extractPosts(
					                since,
					                limit,
					                SiocUtils.asThread( container ),
					                post,
					                children ) );
				}
			} else {
				LOG.warn(
				        "No post found in thread {} with id {} to read replies from.",
				        discussionId, postId );
			}
		}

		return result;
	}

	private List<Post> extractPosts( final Date since, final long limit, final Thread container,
	        final Post parentPost, final ForumPostRecord[] postRecordArray ) throws
	        AuthenticationException,
	        IOException {
		List<Post> results = Lists.newArrayList();

		for ( ForumPostRecord postRecord : postRecordArray ) {
			if ( 0 > limit || limit < results.size() ) {
				Date createdDate = new Date( postRecord.getCreated() * 1000L );
				Post post = Moodle2SiocUtils.createSiocPost(
				        getConnector(),
				        postRecord,
				        container,
				        parentPost );

				if ( null == since || createdDate.after( since ) ) {
					results.add( post );
				}

				ForumPostRecord[] children = postRecord.getChildren();
				if ( null != children && 0 < children.length ) {
					results.addAll( extractPosts(
					        since,
					        0 > limit
					                ? -1
					                : Math.max( 0, limit - results.size() ),
					        container,
					        post,
					        postRecord.getChildren() ) );
				}
			}
		}

		return results;
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
}
