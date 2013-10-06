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

package de.m0ep.socc.core.connector.google.plus;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ontoware.rdf2go.model.Syntax;
import org.ontoware.rdf2go.model.node.URI;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.services.plus.Plus.Activities;
import com.google.api.services.plus.Plus.Comments;
import com.google.api.services.plus.model.Activity;
import com.google.api.services.plus.model.ActivityFeed;
import com.google.api.services.plus.model.Comment;
import com.google.api.services.plus.model.CommentFeed;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import de.m0ep.socc.core.connector.DefaultConnectorIOComponent;
import de.m0ep.socc.core.connector.IConnector.IPostReader;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.exceptions.NotFoundException;
import de.m0ep.socc.core.utils.RdfUtils;
import de.m0ep.socc.core.utils.SoccUtils;

/**
 * Implementation of an {@link IPostReader} for Google+.
 * 
 * @author Florian Müller
 * 
 */
public class GooglePlusPostReader extends
        DefaultConnectorIOComponent<GooglePlusConnector> implements
        IPostReader<GooglePlusConnector> {
	private static final Logger LOG = LoggerFactory.getLogger( GooglePlusPostReader.class );

	private final GooglePlusClientWrapper defaultClient;

	/**
	 * Creates a new {@link GooglePlusPostReader} for an
	 * {@link GooglePlusConnector}.
	 * 
	 * @param connector
	 *            Connector of that PostReader.
	 */
	public GooglePlusPostReader( final GooglePlusConnector connector ) {
		super( connector );
		this.defaultClient = getConnector().getClientManager().getDefaultClient();
	}

	@Override
	public boolean isPost( final URI uri ) {
		return GooglePlusSiocUtils.isActivityUri( uri )
		        || GooglePlusSiocUtils.isCommentUri( uri );
	}

	@Override
	public Post getPost( final URI uri )
	        throws NotFoundException,
	        AuthenticationException,
	        IOException {
		Preconditions.checkNotNull( uri,
		        "Required parameter uri must be specified." );

		LOG.debug( "Read Post uri='{}'", uri );

		if ( GooglePlusSiocUtils.isActivityUri( uri ) ) {
			return readActivity( uri );
		} else if ( GooglePlusSiocUtils.isCommentUri( uri ) ) {
			return readComment( uri );
		}

		throw new NotFoundException( "No Google+ post found at uri " + uri );
	}

	@Override
	public boolean hasPosts( final URI uri ) {
		return null != uri
		        && ( GooglePlusSiocUtils.isActivityFeedUri( uri )
		        || GooglePlusSiocUtils.isActivityUri( uri ) );
	}

	@Override
	public List<Post> pollPosts( final URI sourceUri, final Date since, final int limit )
	        throws AuthenticationException, IOException {
		Preconditions.checkNotNull( since,
		        "Required parameter since must be specified." );

		LOG.debug( "Poll posts uri='{}' since='{}' limit='{}'", sourceUri, since, limit );

		if ( GooglePlusSiocUtils.isActivityFeedUri( sourceUri ) ) {
			return pollActivityFeed( sourceUri, since, limit );
		} else if ( GooglePlusSiocUtils.isActivityUri( sourceUri ) ) {
			return pollActivityCommentFeed( sourceUri, since, limit );
		}

		throw new IOException( "Can't poll for Google+ posts at this uri " + sourceUri );
	}

	/**
	 * Reads an {@link Activity} from an URI and convert it to SIOC.
	 * 
	 * @param uri
	 *            URI to read the {@link Activity}
	 * @return A {@link Post} of the {@link Activity} from that URI.
	 * 
	 * @throws NotFoundException
	 *             Thrown if no resource was found at the URI
	 * @throws AuthenticationException
	 *             Thrown if there is a problem with authentication.
	 * @throws IOException
	 *             Thrown if there ist problem in communication.
	 */
	private Post readActivity( final URI uri )
	        throws NotFoundException,
	        AuthenticationException,
	        IOException {
		Pattern pattern = Pattern.compile( GooglePlusSiocUtils.REGEX_ACTIVITY_URI );
		Matcher matcher = pattern.matcher( uri.toString() );

		if ( matcher.find() && 2 <= matcher.groupCount() ) {
			String activityId = matcher.group( 1 );

			try {
				Activity activity = defaultClient.getGooglePlusService()
				        .activities()
				        .get( activityId )
				        .execute();

				return GooglePlusSiocUtils.createSiocPost( getConnector(), activity, null );
			} catch ( IOException e ) {
				GooglePlusConnector.handleGoogleException( e );
			}
		}

		throw new NotFoundException( "No Google+ post found at uri " + uri );
	}

	/**
	 * Reads an {@link Comment} from an URI and convert it to SIOC.
	 * 
	 * @param uri
	 *            URI to read the {@link Comment}
	 * @return A {@link Post} of the {@link Comment} from that URI.
	 * 
	 * @throws NotFoundException
	 *             Thrown if no resource was found at the URI
	 * @throws AuthenticationException
	 *             Thrown if there is a problem with authentication.
	 * @throws IOException
	 *             Thrown if there ist problem in communication.
	 */
	private Post readComment( final URI uri )
	        throws NotFoundException,
	        AuthenticationException,
	        IOException {
		Pattern pattern = Pattern.compile( GooglePlusSiocUtils.REGEX_COMMENT_URI );
		Matcher matcher = pattern.matcher( uri.toString() );

		if ( matcher.find() && 2 <= matcher.groupCount() ) {
			String commentId = matcher.group( 1 );

			try {
				Comment comment = defaultClient.getGooglePlusService()
				        .comments()
				        .get( commentId )
				        .execute();

				return GooglePlusSiocUtils.createSiocPost( getConnector(), comment, null );
			} catch ( IOException e ) {
				GooglePlusConnector.handleGoogleException( e );
			}
		}

		throw new NotFoundException( "No Google+ post found at uri " + uri );
	}

	/**
	 * Poll {@link Activity}s from an {@link ActivityFeed} from an URI an
	 * convert them to SIOC.
	 * 
	 * @param sourceUri
	 *            URI of that {@link ActivityFeed}.
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
	 */
	private List<Post> pollActivityFeed(
	        final URI sourceUri,
	        final Date since,
	        final int limit )
	        throws AuthenticationException,
	        IOException {
		Pattern pattern = Pattern.compile( GooglePlusSiocUtils.REGEX_ACTIVITY_FEED_URI );
		Matcher matcher = pattern.matcher( sourceUri.toString() );
		List<Post> resultList = Lists.newArrayList();

		if ( matcher.find() && 3 <= matcher.groupCount() ) {
			String userId = matcher.group( 1 );
			String collection = matcher.group( 2 );
			String pageToken = null;

			Container container = getConnector().getStructureReader().getContainer( sourceUri );

			try {
				do {
					Activities.List activityFeedList = defaultClient.getGooglePlusService()
					        .activities()
					        .list( userId, collection );

					if ( null != pageToken ) {
						activityFeedList.setPageToken( pageToken );
					}

					ActivityFeed activityFeed = activityFeedList.execute();

					for ( Activity activity : activityFeed.getItems() ) {
						if ( LOG.isDebugEnabled() ) {
							LOG.debug( "Read entry '{}' from '{}':\n{}",
							        activity.getId(),
							        getServiceEndpoint(),
							        activity );
						} else {
							LOG.info( "Read entry '{}' from '{}'",
							        activity.getId(),
							        getServiceEndpoint() );
						}

						if ( 0 > limit || limit < resultList.size() ) {
							Post post = GooglePlusSiocUtils.createSiocPost(
							        getConnector(),
							        activity,
							        container );

							if ( LOG.isDebugEnabled() ) {
								LOG.debug( "Converted entry '{}' to SIOC:\n{}",
								        activity.getId(),
								        RdfUtils.resourceToString( post, Syntax.Turtle ) );
							} else {
								LOG.info( "Converted entry '{}' to SIOC {}",
								        activity.getId(),
								        post );
							}

							if ( SoccUtils.haveReadAccess(
							        getConnector(),
							        post.getCreator(),
							        post.getContainer() ) ) {
								Date createdDate = new Date( activity.getPublished().getValue() );
								if ( null == since || createdDate.after( since ) ) {
									resultList.add( post );
									LOG.info( "Added {} to polling result. result size: {}",
									        post,
									        resultList.size() );
								} else {
									LOG.info( "Skip Post '{}', it's to old.", post );
								}
							} else {
								LOG.info(
								        "Have no permission to read posts for this UserAccount='{}'",
								        post.getCreator() );
								SoccUtils.anonymisePost( post );
							}

							// check for new comments
							resultList.addAll( pollActivityCommentFeed(
							        post.getResource().asURI(),
							        since,
							        Math.max( -1, limit - resultList.size() ) ) );
						} else {
							LOG.info( "Limit reached: limit={} size={}", limit, resultList.size() );
							return resultList;
						}
					}

					pageToken = activityFeed.getNextPageToken();
				} while ( null != pageToken );
			} catch ( Exception e ) {
				GooglePlusConnector.handleGoogleException( e );
			}
		}

		return resultList;
	}

	/**
	 * Poll {@link Comment}s from an {@link CommentFeed} from an URI an convert
	 * them to SIOC.
	 * 
	 * @param sourceUri
	 *            URI of that {@link CommentFeed}.
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
	 */
	private List<Post> pollActivityCommentFeed(
	        final URI sourceUri,
	        final Date since,
	        final int limit )
	        throws AuthenticationException,
	        IOException {
		List<Post> resultList = Lists.newArrayList();

		Pattern pattern = Pattern.compile( GooglePlusSiocUtils.REGEX_ACTIVITY_FEED_URI );
		Matcher matcher = pattern.matcher( sourceUri.toString() );
		if ( matcher.find() && 2 <= matcher.groupCount() ) {
			String activityId = matcher.group( 1 );
			String pageToken = null;
			Post parentPost = readActivity( sourceUri );

			try {
				do {
					Comments.List commentFeedList = defaultClient.getGooglePlusService()
					        .comments()
					        .list( activityId );

					if ( null != pageToken ) {
						commentFeedList.setPageToken( pageToken );
					}

					CommentFeed commentFeed = commentFeedList.execute();

					for ( Comment comment : commentFeed.getItems() ) {
						if ( LOG.isDebugEnabled() ) {
							LOG.debug( "Read entry '{}' from '{}':\n{}",
							        comment.getId(),
							        getServiceEndpoint(),
							        comment );
						} else {
							LOG.info( "Read entry '{}' from '{}'",
							        comment.getId(),
							        getServiceEndpoint() );
						}

						if ( 0 > limit || limit < resultList.size() ) {
							Post post = GooglePlusSiocUtils.createSiocPost(
							        getConnector(),
							        comment,
							        parentPost );

							if ( LOG.isDebugEnabled() ) {
								LOG.debug( "Converted entry '{}' to SIOC:\n{}",
								        comment.getId(),
								        RdfUtils.resourceToString( post, Syntax.Turtle ) );
							} else {
								LOG.info( "Converted entry '{}' to SIOC {}",
								        comment.getId(),
								        post );
							}

							if ( SoccUtils.haveReadAccess(
							        getConnector(),
							        post.getCreator(),
							        post.getContainer() ) ) {
								Date createdDate = new Date( comment.getPublished().getValue() );
								if ( null == since || createdDate.after( since ) ) {
									resultList.add( post );
									LOG.info( "Added {} to polling result. result size: {}",
									        post,
									        resultList.size() );
								} else {
									LOG.info( "Skip Post '{}', it's to old.", post );
								}
							} else {
								LOG.info(
								        "Have no permission to read posts for this UserAccount='{}'",
								        post.getCreator() );
								SoccUtils.anonymisePost( post );
							}
						} else {
							LOG.info( "Limit reached: limit={} size={}", limit, resultList.size() );
							return resultList;
						}
					}

					pageToken = commentFeed.getNextPageToken();
				} while ( null != pageToken );
			} catch ( Exception e ) {
				GooglePlusConnector.handleGoogleException( e );
			}
		}

		return resultList;
	}

}
