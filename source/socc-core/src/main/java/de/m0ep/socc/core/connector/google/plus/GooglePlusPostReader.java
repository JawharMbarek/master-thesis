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

public class GooglePlusPostReader extends
        DefaultConnectorIOComponent<GooglePlusConnector> implements
        IPostReader<GooglePlusConnector> {
	private static final Logger LOG = LoggerFactory
	        .getLogger( GooglePlusPostReader.class );

	private final GooglePlusClientWrapper defaultClient;

	public GooglePlusPostReader( GooglePlusConnector connector ) {
		super( connector );

		this.defaultClient = getConnector().getClientManager()
		        .getDefaultClient();
	}

	@Override
	public Post readPost( URI uri )
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
	public boolean hasPosts( URI uri ) {
		return null != uri
		        && ( GooglePlusSiocUtils.isActivityFeedUri( uri )
		        || GooglePlusSiocUtils.isActivityUri( uri ) );
	}

	@Override
	public List<Post> pollPosts( URI sourceUri, Date since, int limit )
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

	private Post readActivity( URI uri )
	        throws NotFoundException,
	        AuthenticationException,
	        IOException {
		Pattern pattern = Pattern.compile( "^"
		        + GooglePlusSiocUtils.GOOGLE_PLUS_API_ROOT_URI
		        + GooglePlusSiocUtils.REGEX_ACTIVITY_URI );
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

	private Post readComment( URI uri )
	        throws NotFoundException,
	        AuthenticationException,
	        IOException {
		Pattern pattern = Pattern.compile( "^"
		        + GooglePlusSiocUtils.GOOGLE_PLUS_API_ROOT_URI
		        + GooglePlusSiocUtils.REGEX_COMMENT_URI );
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

	private List<Post> pollActivityFeed( URI sourceUri, Date since, int limit )
	        throws AuthenticationException,
	        IOException {
		Pattern pattern = Pattern.compile( "^"
		        + GooglePlusSiocUtils.GOOGLE_PLUS_API_ROOT_URI
		        + GooglePlusSiocUtils.REGEX_ACTIVITY_FEED_URI );
		Matcher matcher = pattern.matcher( sourceUri.toString() );
		List<Post> result = Lists.newArrayList();

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
						Date createdDate = new Date( activity.getPublished().getValue() );
						if ( ( 0 > limit || limit < result.size() )
						        && ( null == since || createdDate.after( since ) ) ) {
							result.add( GooglePlusSiocUtils.createSiocPost(
							        getConnector(),
							        activity,
							        container ) );
						} else {
							return result;
						}
					}

					pageToken = activityFeed.getNextPageToken();
				} while ( null != pageToken );
			} catch ( Exception e ) {
				GooglePlusConnector.handleGoogleException( e );
			}
		}

		LOG.debug( "polled {} activities from Google+ uri '{}'", result.size(), sourceUri );
		return result;
	}

	private List<Post> pollActivityCommentFeed( URI sourceUri, Date since, int limit )
	        throws AuthenticationException,
	        IOException {
		Pattern pattern = Pattern.compile( "^"
		        + GooglePlusSiocUtils.GOOGLE_PLUS_API_ROOT_URI
		        + GooglePlusSiocUtils.REGEX_ACTIVITY_FEED_URI );
		Matcher matcher = pattern.matcher( sourceUri.toString() );
		List<Post> result = Lists.newArrayList();

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
						Date createdDate = new Date( comment.getPublished().getValue() );
						if ( ( 0 > limit || limit < result.size() )
						        && ( null == since || createdDate.after( since ) ) ) {
							result.add( GooglePlusSiocUtils.createSiocPost(
							        getConnector(),
							        comment,
							        parentPost ) );
						} else {
							return result;
						}
					}

					pageToken = commentFeed.getNextPageToken();
				} while ( null != pageToken );
			} catch ( Exception e ) {
				GooglePlusConnector.handleGoogleException( e );
			}
		}

		LOG.debug( "polled {} comments from Google+ uri '{}'", result.size(), sourceUri );
		return result;
	}

}
