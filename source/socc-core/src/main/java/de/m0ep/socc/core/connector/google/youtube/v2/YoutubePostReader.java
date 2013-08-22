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

package de.m0ep.socc.core.connector.google.youtube.v2;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;

import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.Thread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.damnhandy.uri.template.UriTemplate;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.google.gdata.data.youtube.CommentEntry;
import com.google.gdata.data.youtube.CommentFeed;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.VideoFeed;
import com.google.gdata.util.ServiceException;

import de.m0ep.socc.core.connector.DefaultConnectorIOComponent;
import de.m0ep.socc.core.connector.IConnector.IPostReader;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.utils.RdfUtils;

public class YoutubePostReader extends
        DefaultConnectorIOComponent<YoutubeConnector> implements
        IPostReader<YoutubeConnector> {
	private static final Logger LOG = LoggerFactory
	        .getLogger( YoutubePostReader.class );

	private final YoutubeClientWrapper defaultClient;

	public YoutubePostReader( YoutubeConnector connector ) {
		super( connector );

		this.defaultClient = connector.getServiceClientManager()
		        .getDefaultClient();
	}

	@Override
	public boolean containsPosts( Container container ) {
		return isUploadsContainer( container ) || isPlaylistContainer( container );
	}

	@Override
	public List<Post> readNewPosts( Date lastPostDate, long limit,
	        Container container )
	        throws AuthenticationException, IOException {
		if ( 0 == limit ) {
			return Lists.newArrayList();
		}

		Preconditions.checkNotNull( container,
		        "Required parameter container must be specified." );
		Preconditions.checkArgument( containsPosts( container ),
		        "The container contains no posts on this service." );
		List<Post> results = Lists.newArrayList();

		String nextFeedUri = null;
		if ( isUploadsContainer( container ) ) {
			nextFeedUri = UriTemplate
			        .fromTemplate(
			                "http://gdata.youtube.com/feeds/api/users/{userId}/uploads?v=2" )
			        .set( "userId",
			                container.getId().split(
			                        YoutubeSiocConverter.ID_SEPERATOR )[1] )
			        .expand(); // FIXME: magic strings
		} else if ( isPlaylistContainer( container ) ) {
			nextFeedUri = UriTemplate
			        .fromTemplate(
			                "http://gdata.youtube.com/feeds/api/playlists/{playlistId}?v=2" )
			        .set( "playlistId",
			                container.getId().split(
			                        YoutubeSiocConverter.ID_SEPERATOR )[1] )
			        .expand(); // FIXME: magic strings
		}

		while ( null != nextFeedUri ) {
			System.out.println( nextFeedUri );
			VideoFeed videoFeed = null;
			try {
				getConnector().waitForCooldown();
				videoFeed = defaultClient
				        .getService().getFeed(
				                new URL( nextFeedUri ),
				                VideoFeed.class );
				LOG.debug(
				        "Fetched {} videos from {}.",
				        videoFeed.getEntries().size(),
				        nextFeedUri );
			} catch ( com.google.gdata.util.AuthenticationException e ) {
				throw new AuthenticationException( e );
			} catch ( ServiceException e ) {
				throw Throwables.propagate( e );
			} finally {
				nextFeedUri = null;
			}

			if ( null != videoFeed ) {
				for ( VideoEntry videoEntry : videoFeed.getEntries() ) {
					Date created;
					if ( null != videoEntry.getPublished() ) {
						created = new Date( videoEntry.getPublished().getValue() );
					} else {
						created = new Date( 0 );
					}

					if ( null != lastPostDate && lastPostDate.before( created ) ) {
						return results;
					}

					results.add(
					        YoutubeSiocConverter.createSiocPost(
					                getConnector(),
					                videoEntry,
					                container ) );

					if ( 0 < limit && limit == results.size() ) {
						return results;
					}
				}
			}

			if ( null != videoFeed.getNextLink() ) {
				nextFeedUri = videoFeed.getNextLink().getHref();
			}
		}

		return results;
	}

	@Override
	public boolean containsReplies( Post post ) {
		return post.toString().startsWith( getServiceEndpoint().toString() )
		        &&
		        post.hasId()
		        &&
		        post.getId().startsWith( YoutubeSiocConverter.VIDEO_ID_PREFIX )
		        &&
		        post.hasContainer()
		        &&
		        post.getContainer().toString().startsWith(
		                getServiceEndpoint().toString() );
	}

	@Override
	public List<Post> readNewReplies( Date lastReplyDate, long limit,
	        Post parentPost )
	        throws AuthenticationException, IOException {
		if ( 0 == limit ) {
			return Lists.newArrayList();
		}

		Preconditions.checkNotNull( parentPost,
		        "Required parameter parentPost must be specified." );
		Preconditions
		        .checkArgument( containsReplies( parentPost ),
		                "The parameter parentPost contians no replies at this service." );

		String videoId = parentPost.getId().split(
		        YoutubeSiocConverter.ID_SEPERATOR )[1];
		String nextFeedUri = UriTemplate.fromTemplate(
		        "http://gdata.youtube.com/feeds/api/videos/{videoId}/comments" )
		        .set( "videoId", videoId )
		        .expand(); // FIXME: magic strings

		List<Post> results = Lists.newArrayList();
		while ( null != nextFeedUri ) {
			CommentFeed commentFeed = null;
			try {
				getConnector().waitForCooldown();
				commentFeed = defaultClient
				        .getService().getFeed(
				                new URL( nextFeedUri ),
				                CommentFeed.class );
				LOG.debug( "Fetched {} comments from {}.",
				        commentFeed.getEntries().size(),
				        nextFeedUri );
			} catch ( com.google.gdata.util.AuthenticationException e ) {
				throw new AuthenticationException( e );
			} catch ( ServiceException e ) {
				throw Throwables.propagate( e );
			} finally {
				nextFeedUri = null;
			}

			if ( null != commentFeed ) {
				for ( CommentEntry commentEntry : commentFeed.getEntries() ) {
					Date created;
					if ( null != commentEntry.getPublished() ) {
						created = new Date( commentEntry.getPublished()
						        .getValue() );
					} else {
						created = new Date( 0 );
					}

					if ( null != lastReplyDate && lastReplyDate.before( created ) ) {
						return results;
					}

					results.add(
					        YoutubeSiocConverter.createSiocPost(
					                getConnector(),
					                commentEntry,
					                parentPost ) );

					if ( 0 < limit && limit == results.size() ) {
						return results;
					}
				}
			}

			if ( null != commentFeed.getNextLink() ) {
				nextFeedUri = commentFeed.getNextLink().getHref();
			}
		}

		return results;
	}

	private boolean isPlaylistContainer( Container container ) {
		return null != container
		        && container.toString()
		                .startsWith( getServiceEndpoint().toString() )
		        && RdfUtils.isType(
		                container.getModel(),
		                container.getResource(),
		                Thread.RDFS_CLASS )
		        && container.hasParent()
		        && container.getParent().toString().startsWith(
		                getServiceEndpoint().toString() )
		        && container.getParent().hasId()
		        && container.getParent().getId().startsWith(
		                YoutubeSiocConverter.PLAYLISTS_ID_PREFIX );
	}

	private boolean isUploadsContainer( Container container ) {
		return container.toString().startsWith( getServiceEndpoint().toString() )
		        && RdfUtils.isType(
		                container.getModel(),
		                container.getResource(),
		                Forum.RDFS_CLASS )
		        && container.hasId()
		        && container.getId().startsWith(
		                YoutubeSiocConverter.UPLOADS_ID_PREFIX );
	}

}
