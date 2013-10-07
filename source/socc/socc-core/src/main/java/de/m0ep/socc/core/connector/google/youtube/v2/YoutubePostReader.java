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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;

import org.ontoware.rdf2go.model.Syntax;
import org.ontoware.rdf2go.model.node.URI;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.Link;
import com.google.gdata.data.youtube.CommentEntry;
import com.google.gdata.data.youtube.CommentFeed;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.VideoFeed;
import com.google.gdata.util.NotModifiedException;
import com.google.gdata.util.ServiceException;

import de.m0ep.socc.core.connector.DefaultConnectorIOComponent;
import de.m0ep.socc.core.connector.IConnector.IPostReader;
import de.m0ep.socc.core.exceptions.AccessControlException;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.exceptions.NotFoundException;
import de.m0ep.socc.core.utils.RdfUtils;
import de.m0ep.socc.core.utils.SiocUtils;
import de.m0ep.socc.core.utils.SoccUtils;

/**
 * Implementation of an {@link IPostReader} for Youtube
 * 
 * @author Florian Müller
 */
public class YoutubePostReader extends
        DefaultConnectorIOComponent<YoutubeConnector> implements
        IPostReader<YoutubeConnector> {
	private static final Logger LOG = LoggerFactory.getLogger( YoutubePostReader.class );

	private final YoutubeClientWrapper defaultClient;

	/**
	 * Construct a new {@link YoutubePostReader} for a {@link YoutubeConnector}.
	 * 
	 * @param connector
	 *            Connector of this PostReaeder
	 */
	public YoutubePostReader( final YoutubeConnector connector ) {
		super( connector );
		this.defaultClient = connector.getClientManager().getDefaultClient();
	}

	@Override
	public boolean isPost( final URI uri ) {
		return YoutubeSiocUtils.isCommentUri( uri )
		        || YoutubeSiocUtils.isVideoUri( uri );
	}

	@Override
	public Post getPost( final URI uri )
	        throws NotFoundException,
	        AuthenticationException,
	        IOException,
	        AccessControlException {
		if ( Post.hasInstance( getModel(), uri ) ) {
			return Post.getInstance( getModel(), uri );
		}

		Post result;
		if ( YoutubeSiocUtils.isVideoUri( uri ) ) {
			result = readVideo( uri );
		} else if ( YoutubeSiocUtils.isCommentUri( uri ) ) {
			result = readComment( uri );
		} else {
			throw new NotFoundException( "There is no Youtube post at " + uri );
		}

		if ( !SoccUtils.haveReadAccess(
		        getConnector(),
		        result.getCreator(),
		        result.getContainer() ) ) {
			SoccUtils.anonymisePost( result );
			throw new AccessControlException( "Have no permission to read post '" + uri + "'" );
		}

		return result;
	}

	@Override
	public boolean hasPosts( final URI uri ) {
		return null != uri &&
		        ( YoutubeSiocUtils.isUserUploadsUri( uri )
		                || YoutubeSiocUtils.isPlaylistUri( uri )
		                || YoutubeSiocUtils.isVideoUri( uri ) );
	}

	@Override
	public List<Post> pollPosts( final URI sourceUri, final Date since, final int limit )
	        throws NotFoundException,
	        AuthenticationException,
	        IOException,
	        AccessControlException {
		if ( YoutubeSiocUtils.isUserUploadsUri( sourceUri )
		        || YoutubeSiocUtils.isPlaylistUri( sourceUri ) ) {
			return pollFromVideoFeed( sourceUri, since, Math.max( -1, limit ) );
		} else if ( YoutubeSiocUtils.isVideoUri( sourceUri ) ) {
			return pollFromVideoCommentFeed( sourceUri, since, Math.max( -1, limit ) );
		}

		throw new IOException( "Can't poll posts from uri "
		        + sourceUri
		        + " at service "
		        + getServiceEndpoint() );
	}

	/**
	 * Reads a {@link VideoEntry} from an URI and converts it to SIOC.
	 * 
	 * @param uri
	 *            URI to read the {@link VideoEntry} from.
	 * @return A {@link Post} of the {@link VideoEntry}.
	 * 
	 * @throws NotFoundException
	 *             Thrown if no resource was found at the URI
	 * @throws AuthenticationException
	 *             Thrown if there is a problem with authentication.
	 * @throws IOException
	 *             Thrown if there ist problem in communication.
	 */
	private Post readVideo( final URI uri )
	        throws NotFoundException,
	        AuthenticationException,
	        IOException {
		VideoEntry videoEntry = null;
		try {
			getConnector().waitForCooldown();
			videoEntry = defaultClient.getYoutubeService().getEntry(
			        new URL( uri.toString() ),
			        VideoEntry.class );
		} catch ( MalformedURLException e ) {
			// shouldn't happened
			Throwables.propagate( e );
		} catch ( ServiceException e ) {
			YoutubeConnector.handleYoutubeExceptions( e );
		}

		if ( LOG.isDebugEnabled() ) {
			LOG.debug( "Read entry '{}' from '{}':\n{}",
			        videoEntry.getId(),
			        getServiceEndpoint(),
			        videoEntry );
		} else {
			LOG.info( "Read entry '{}' from '{}'",
			        videoEntry.getId(),
			        getServiceEndpoint() );
		}

		Post result = YoutubeSiocUtils.createSiocPost( getConnector(), videoEntry, null );

		if ( LOG.isDebugEnabled() ) {
			LOG.debug( "Converted entry '{}' to SIOC:\n{}",
			        videoEntry.getId(),
			        RdfUtils.resourceToString( result, Syntax.Turtle ) );
		} else {
			LOG.info( "Converted entry '{}' to SIOC {}",
			        videoEntry.getId(),
			        result );
		}

		return result;
	}

	/**
	 * Reads a {@link CommentEntry} from an URI and converts it to SIOC.
	 * 
	 * @param uri
	 *            URI to read the {@link CommentEntry} from.
	 * @return A {@link Post} of the {@link CommentEntry}.
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
		CommentEntry commentEntry = null;
		try {
			getConnector().waitForCooldown();
			commentEntry = defaultClient.getYoutubeService().getEntry(
			        new URL( uri.toString() ),
			        CommentEntry.class );
		} catch ( MalformedURLException e ) {
			// shouldn't happened
			Throwables.propagate( e );
		} catch ( ServiceException e ) {
			YoutubeConnector.handleYoutubeExceptions( e );
		}

		if ( LOG.isDebugEnabled() ) {
			LOG.debug( "Read entry '{}' from '{}':\n{}",
			        commentEntry.getId(),
			        getServiceEndpoint(),
			        commentEntry );
		} else {
			LOG.info( "Read entry '{}' from '{}'",
			        commentEntry.getId(),
			        getServiceEndpoint() );
		}

		Post result = YoutubeSiocUtils.createSiocPost( getConnector(), commentEntry, null );

		if ( LOG.isDebugEnabled() ) {
			LOG.debug( "Converted entry '{}' to SIOC:\n{}",
			        commentEntry.getId(),
			        RdfUtils.resourceToString( result, Syntax.Turtle ) );
		} else {
			LOG.info( "Converted entry '{}' to SIOC {}",
			        commentEntry.getId(),
			        result );
		}

		return result;
	}

	/**
	 * Poll videos from a video-feed
	 * 
	 * @param sourceUri
	 *            URI of the video-feed
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
	 *             Thrown if there is an access controll error.
	 */
	private List<Post> pollFromVideoFeed( URI sourceUri, Date since, int limit )
	        throws NotFoundException,
	        AuthenticationException,
	        IOException, AccessControlException, ClassCastException {
		Forum parent = SiocUtils.asForum( getConnector().getStructureReader()
		        .getContainer( sourceUri ) );
		String pageUrl = sourceUri.toString();

		List<Post> resultList = Lists.newArrayList();
		do {
			getConnector().waitForCooldown();
			VideoFeed videoFeed = null;
			try {
				if ( null != since ) {
					getConnector().waitForCooldown();
					videoFeed = defaultClient.getYoutubeService().getFeed(
					        new URL( pageUrl ),
					        VideoFeed.class,
					        new DateTime( since ) );
				} else {
					getConnector().waitForCooldown();
					videoFeed = defaultClient.getYoutubeService().getFeed(
					        new URL( pageUrl ),
					        VideoFeed.class );
				}
			} catch ( MalformedURLException e ) {
				// shouldn't happened
				Throwables.propagate( e );
			} catch ( NotModifiedException e ) {
				return resultList;
			} catch ( ServiceException e ) {
				YoutubeConnector.handleYoutubeExceptions( e );
			}

			for ( VideoEntry videoEntry : videoFeed.getEntries() ) {
				if ( LOG.isDebugEnabled() ) {
					LOG.debug( "Read entry '{}' from '{}':\n{}",
					        videoEntry.getId(),
					        getServiceEndpoint(),
					        videoEntry );
				} else {
					LOG.info( "Read entry '{}' from '{}'",
					        videoEntry.getId(),
					        getServiceEndpoint() );
				}

				if ( 0 > limit || limit < resultList.size() ) {
					Post post = YoutubeSiocUtils.createSiocPost(
					        getConnector(),
					        videoEntry,
					        parent );

					if ( LOG.isDebugEnabled() ) {
						LOG.debug( "Converted entry '{}' to SIOC:\n{}",
						        videoEntry.getId(),
						        RdfUtils.resourceToString( post, Syntax.Turtle ) );
					} else {
						LOG.info( "Converted entry '{}' to SIOC {}",
						        videoEntry.getId(),
						        post );
					}

					if ( SoccUtils.haveReadAccess(
					        getConnector(),
					        post.getCreator(),
					        post.getContainer() ) ) {
						Date createdDate = new Date( videoEntry.getPublished().getValue() );
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

					// poll for comments
					resultList.addAll( pollFromVideoCommentFeed(
					        post.getResource().asURI(),
					        since,
					        Math.max( -1, limit - resultList.size() ) ) );
				} else {
					LOG.info( "Limit reached: limit={} size={}", limit, resultList.size() );
					return resultList;
				}
			}

			// get next page Link, if there is one
			Link nextLink = videoFeed.getNextLink();
			pageUrl = ( null != nextLink ) ? nextLink.getHref() : null;
		} while ( null != pageUrl );

		return resultList;
	}

	/**
	 * Poll comments from a comment-feed
	 * 
	 * @param sourceUri
	 *            URI of the comment-feed
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
	private List<Post> pollFromVideoCommentFeed(
	        final URI sourceUri,
	        final Date since,
	        final int limit )
	        throws NotFoundException,
	        AuthenticationException,
	        IOException,
	        AccessControlException {
		Post parentPost = getPost( sourceUri );
		String pageUrl = sourceUri.toString() + "/comments";

		List<Post> resultList = Lists.newArrayList();
		do {
			getConnector().waitForCooldown();
			CommentFeed commentFeed = null;
			try {

				if ( null != since ) {
					getConnector().waitForCooldown();
					commentFeed = defaultClient.getYoutubeService().getFeed(
					        new URL( pageUrl ),
					        CommentFeed.class,
					        new DateTime( since ) );
				} else {
					getConnector().waitForCooldown();
					commentFeed = defaultClient.getYoutubeService().getFeed(
					        new URL( pageUrl ),
					        CommentFeed.class );
				}
			} catch ( MalformedURLException e ) {
				// shouldn't happened
				Throwables.propagate( e );
			} catch ( NotModifiedException e ) {
				return resultList;
			} catch ( ServiceException e ) {
				YoutubeConnector.handleYoutubeExceptions( e );
			}

			for ( CommentEntry commentEntry : commentFeed.getEntries() ) {
				if ( LOG.isDebugEnabled() ) {
					LOG.debug( "Read entry '{}' from '{}':\n{}",
					        commentEntry.getId(),
					        getServiceEndpoint(),
					        commentEntry );
				} else {
					LOG.info( "Read entry '{}' from '{}'",
					        commentEntry.getId(),
					        getServiceEndpoint() );
				}

				if ( 0 > limit || limit < resultList.size() ) {
					Post post = YoutubeSiocUtils.createSiocPost(
					        getConnector(),
					        commentEntry,
					        parentPost );

					if ( LOG.isDebugEnabled() ) {
						LOG.debug( "Converted entry '{}' to SIOC:\n{}",
						        commentEntry.getId(),
						        RdfUtils.resourceToString( post, Syntax.Turtle ) );
					} else {
						LOG.info( "Converted entry '{}' to SIOC {}",
						        commentEntry.getId(),
						        post );
					}

					if ( SoccUtils.haveReadAccess(
					        getConnector(),
					        post.getCreator(),
					        post.getContainer() ) ) {
						Date createdDate = new Date( commentEntry.getPublished().getValue() );
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
				} else {
					LOG.info( "Limit reached: limit={} size={}", limit, resultList.size() );
					return resultList;
				}
			}

			// get next page Link, if there is one
			Link nextLink = commentFeed.getNextLink();
			pageUrl = ( null != nextLink ) ? nextLink.getHref() : null;
		} while ( null != pageUrl );

		return resultList;
	}
}
