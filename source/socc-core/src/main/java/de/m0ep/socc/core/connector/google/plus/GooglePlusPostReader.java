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

import org.rdfs.sioc.Container;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.SIOCVocabulary;
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
import de.m0ep.socc.core.utils.RdfUtils;

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
	public boolean containsPosts( Container container ) {
		return null != container
		        && container.toString().startsWith(
		                getServiceEndpoint().toString() )
		        && RdfUtils.isType(
		                container.getModel(),
		                container.getResource(),
		                SIOCVocabulary.Forum )
		        && container.hasId()
		        && container.getId().startsWith(
		                GooglePlusSiocConverter.PUBLIC_FEED_ID_PREFIX );
	}

	@Override
	public List<Post> readNewPosts( Date since, long limit, Container container )
	        throws AuthenticationException, IOException {
		if ( 0 == limit ) {
			return Lists.newArrayList();
		}

		Preconditions.checkNotNull( container,
		        "Required parameter container must be specified." );
		Preconditions.checkArgument( containsPosts( container ),
		        "This container has no post at this service." );

		String siocId = container.getId();
		String googleId = siocId.substring(
		        siocId.lastIndexOf(
		                GooglePlusSiocConverter.ID_SEPERATOR ) );

		List<Post> results = Lists.newArrayList();
		String pageToken = null;
		Activities.List activityListRequest = null;
		do {
			ActivityFeed activityFeed = null;
			try {
				if ( null == activityListRequest ) {
					activityListRequest = defaultClient.getService()
					        .activities()
					        .list( googleId, "public" );
					activityListRequest
					        .setFields( "id,"
					                + "items(actor,id,"
					                + "object(attachments/url,content,replies/totalItems)"
					                + ",published,title,updated)" +
					                ",nextPageToken,updated" );
				}

				if ( null != pageToken ) {
					activityListRequest.setPageToken( pageToken );
				}

				activityFeed = activityListRequest.execute();
			} catch ( Exception e ) {
				GooglePlusConnector.handleGoogleException( e );
			}

			if ( null != activityFeed ) {
				LOG.debug( "{} activities fetcht from Google Plus.",
				        activityFeed.getItems().size() );

				pageToken = activityFeed.getNextPageToken();
				for ( Activity activity : activityFeed.getItems() ) {
					Date created = new Date( activity.getPublished().getValue() );

					if ( 0 > limit || limit < results.size() ) {
						if ( created.after( since ) ) {
							Post post = GooglePlusSiocConverter.createSiocPost(
							        getConnector(),
							        activity,
							        container );

							// Long numReplies =
							// activity.getObject().getReplies()
							// .getTotalItems();
							results.add( post );
						}
					}
				}
			}
		} while ( null != pageToken );

		return results;
	}

	@Override
	public boolean containsReplies( Post post ) {
		return null != post
		        && post.toString().startsWith( getServiceEndpoint().toString() )
		        && post.hasId()
		        && post.getId().startsWith(
		                GooglePlusSiocConverter.ACTIVITY_ID_PREFIX );
	}

	@Override
	public List<Post> pollRepliesAtPost( Date since, long limit, Post parentPost )
	        throws AuthenticationException, IOException {
		if ( 0 == limit ) {
			return Lists.newArrayList();
		}

		Preconditions.checkNotNull( parentPost,
		        "Required parameter parentPost must be specified." );
		Preconditions.checkArgument( containsReplies( parentPost ),
		        "This container has no post at this service." );

		String siocId = parentPost.getId();
		String googleId = siocId.substring(
		        siocId.lastIndexOf(
		                GooglePlusSiocConverter.ID_SEPERATOR ) );

		List<Post> results = Lists.newArrayList();
		String pageToken = null;
		Comments.List commentsListRequest = null;
		do {
			CommentFeed commentFeed = null;
			try {
				if ( null == commentsListRequest ) {
					commentsListRequest = defaultClient.getService()
					        .comments()
					        .list( googleId );
					commentsListRequest
					        .setFields( "id,"
					                + "items(actor,id,inReplyTo,object/content,published,updated)"
					                + ",nextPageToken,updated" );
				}

				if ( null != pageToken ) {
					commentsListRequest.setPageToken( pageToken );
				}

				commentFeed = commentsListRequest.execute();
			} catch ( Exception e ) {
				GooglePlusConnector.handleGoogleException( e );
			}

			if ( null != commentFeed ) {
				LOG.debug( "{} comments fetcht from Activity {}.",
				        commentFeed.getItems().size(),
				        googleId );

				pageToken = commentFeed.getNextPageToken();
				for ( Comment comment : commentFeed.getItems() ) {
					Date created = new Date( comment.getPublished().getValue() );

					if ( 0 > limit || limit < results.size() ) {
						if ( created.after( since ) ) {
							Post post = GooglePlusSiocConverter
							        .createSiocComment(
							                getConnector(),
							                comment,
							                parentPost );

							// Long numReplies =
							// activity.getObject().getReplies()
							// .getTotalItems();
							results.add( post );
						}
					}
				}
			}
		} while ( null != pageToken );

		return results;
	}

}
