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

import org.ontoware.rdf2go.model.node.URI;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Post;

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
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.exceptions.NotFoundException;
import de.m0ep.socc.core.utils.SiocUtils;

public class YoutubePostReader extends
        DefaultConnectorIOComponent<YoutubeConnector> implements
        IPostReader<YoutubeConnector> {
	private final YoutubeClientWrapper defaultClient;

	public YoutubePostReader( YoutubeConnector connector ) {
		super( connector );
		this.defaultClient = connector.getClientManager().getDefaultClient();
	}

	@Override
	public Post readPost( URI uri ) throws NotFoundException, AuthenticationException, IOException {
		if ( Post.hasInstance( getModel(), uri ) ) {
			return Post.getInstance( getModel(), uri );
		}

		if ( YoutubeSiocUtils.isVideoUri( uri ) ) {
			return readVideo( uri );
		} else if ( YoutubeSiocUtils.isCommentUri( uri ) ) {
			return readComment( uri );
		}

		throw new NotFoundException( "There is no Youtube post at uri " + uri );
	}

	@Override
	public boolean hasPosts( URI uri ) {
		return null != uri &&
		        ( YoutubeSiocUtils.isUserUploadsUri( uri )
		                || YoutubeSiocUtils.isPlaylistUri( uri )
		                || YoutubeSiocUtils.isVideoUri( uri ) );
	}

	@Override
	public List<Post> pollPosts( URI sourceUri, Date since, int limit )
	        throws NotFoundException,
	        AuthenticationException,
	        IOException {

		if ( YoutubeSiocUtils.isUserUploadsUri( sourceUri )
		        || YoutubeSiocUtils.isPlaylistUri( sourceUri ) ) {
			return pollFromVideoFeed( sourceUri, since, Math.max( -1, limit ) );
		} else if ( YoutubeSiocUtils.isVideoUri( sourceUri ) ) {
			return pollFromVideoCommentsFeed( sourceUri, since, Math.max( -1, limit ) );
		}

		throw new NotFoundException( "Can't poll Youtube posts from uri " + sourceUri );
	}

	private Post readVideo( URI uri )
	        throws NotFoundException,
	        AuthenticationException,
	        IOException {
		VideoEntry videoEntry = null;
		try {
			videoEntry = defaultClient.getService().getEntry(
			        new URL( uri.toString() ),
			        VideoEntry.class );
		} catch ( MalformedURLException e ) {
			// shouldn't happened
			Throwables.propagate( e );
		} catch ( ServiceException e ) {
			YoutubeConnector.handleYoutubeExceptions( e );
		}

		return YoutubeSiocUtils.createSiocPost( getConnector(), videoEntry, null );
	}

	private Post readComment( URI uri )
	        throws NotFoundException,
	        AuthenticationException,
	        IOException {
		CommentEntry commentEntry = null;
		try {
			commentEntry = defaultClient.getService().getEntry(
			        new URL( uri.toString() ),
			        CommentEntry.class );
		} catch ( MalformedURLException e ) {
			// shouldn't happened
			Throwables.propagate( e );
		} catch ( ServiceException e ) {
			YoutubeConnector.handleYoutubeExceptions( e );
		}

		return YoutubeSiocUtils.createSiocPost( getConnector(), commentEntry, null );
	}

	private List<Post> pollFromVideoFeed( URI sourceUri, Date since, int limit )
	        throws NotFoundException,
	        AuthenticationException,
	        IOException {
		Forum parent = SiocUtils.asForum( getConnector().getStructureReader()
		        .getContainer( sourceUri ) );
		String pageUrl = sourceUri.toString();

		List<Post> result = Lists.newArrayList();
		do {
			VideoFeed videoFeed = null;
			try {
				if ( null != since ) {
					videoFeed = defaultClient.getService().getFeed(
					        new URL( pageUrl ),
					        VideoFeed.class,
					        new DateTime( since ) );
				} else {
					videoFeed = defaultClient.getService().getFeed(
					        new URL( pageUrl ),
					        VideoFeed.class );
				}
			} catch ( MalformedURLException e ) {
				// shouldn't happened
				Throwables.propagate( e );
			} catch ( NotModifiedException e ) {
				return result;
			} catch ( ServiceException e ) {
				YoutubeConnector.handleYoutubeExceptions( e );
			}

			for ( VideoEntry videoEntry : videoFeed.getEntries() ) {
				if ( 0 > limit || limit < result.size() ) {
					result.add( YoutubeSiocUtils.createSiocPost(
					        getConnector(),
					        videoEntry,
					        parent ) );
				} else {
					return result;
				}
			}

			Link nextLink = videoFeed.getNextLink();
			pageUrl = ( null != nextLink ) ? nextLink.getHref() : null;
		} while ( null != pageUrl );

		return result;
	}

	private List<Post> pollFromVideoCommentsFeed( URI sourceUri, Date since, int limit )
	        throws NotFoundException,
	        AuthenticationException,
	        IOException {
		Post parentPost = readPost( sourceUri );
		String pageUrl = sourceUri.toString();

		List<Post> result = Lists.newArrayList();
		do {
			CommentFeed commentFeed = null;
			try {

				if ( null != since ) {
					commentFeed = defaultClient.getService().getFeed(
					        new URL( pageUrl ),
					        CommentFeed.class,
					        new DateTime( since ) );
				} else {
					commentFeed = defaultClient.getService().getFeed(
					        new URL( pageUrl ),
					        CommentFeed.class );
				}
			} catch ( MalformedURLException e ) {
				// shouldn't happened
				Throwables.propagate( e );
			} catch ( NotModifiedException e ) {
				return result;
			} catch ( ServiceException e ) {
				YoutubeConnector.handleYoutubeExceptions( e );
			}

			for ( CommentEntry commentEntry : commentFeed.getEntries() ) {
				if ( 0 > limit || limit < result.size() ) {
					result.add( YoutubeSiocUtils.createSiocPost(
					        getConnector(),
					        commentEntry,
					        parentPost ) );
				} else {
					return result;
				}
			}

			Link nextLink = commentFeed.getNextLink();
			pageUrl = ( null != nextLink ) ? nextLink.getHref() : null;
		} while ( null != pageUrl );

		return result;
	}

}
