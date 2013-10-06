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
import java.util.List;

import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.util.Builder;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Site;
import org.rdfs.sioc.Thread;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.google.gdata.data.Link;
import com.google.gdata.data.youtube.PlaylistLinkEntry;
import com.google.gdata.data.youtube.PlaylistLinkFeed;
import com.google.gdata.data.youtube.VideoFeed;
import com.google.gdata.util.ServiceException;

import de.m0ep.socc.core.connector.DefaultConnectorIOComponent;
import de.m0ep.socc.core.connector.IConnector.IStructureReader;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.exceptions.NotFoundException;
import de.m0ep.socc.core.utils.SiocUtils;

/**
 * Implementation of an {@link IStructureReader} for Youtube.
 * 
 * @author Florian Müller
 */
public class YoutubeStructureReader extends
        DefaultConnectorIOComponent<YoutubeConnector> implements
        IStructureReader<YoutubeConnector> {

	private final YoutubeClientWrapper defaultClient;
	private Forum playlists;
	private Forum uploads;

	/**
	 * Constructs a new {@link YoutubeStructureReader} for a connector.
	 * 
	 * @param connector
	 *            Connector for this {@link YoutubeStructureReader}.
	 */
	public YoutubeStructureReader( YoutubeConnector connector ) {
		super( connector );
		this.defaultClient = connector.getClientManager().getDefaultClient();

		try {
			URI playlistsUri = YoutubeSiocUtils.createUserPlaylistsUri(
			        defaultClient.getUserProfile().getUsername() );
			this.playlists = SiocUtils.asForum( getContainer( playlistsUri ) );

			URI uploadsUri = YoutubeSiocUtils.createUserUploadsUri(
			        defaultClient.getUserProfile().getUsername() );
			this.uploads = SiocUtils.asForum( getContainer( uploadsUri ) );
		} catch ( AuthenticationException | IOException e ) {
			Throwables.propagate( e );
		}
	}

	@Override
	public Site getSite() {
		URI uri = Builder.createURI( "http://www.youtube.com" );
		if ( !Site.hasInstance( getModel(), uri ) ) {
			Site result = new Site( getModel(), uri, true );
			result.setName( "Youtube" );
			return result;
		}

		return Site.getInstance( getModel(), uri );
	}

	@Override
	public boolean isContainer( URI uri ) {
		return YoutubeSiocUtils.isPlaylistUri( uri )
		        || YoutubeSiocUtils.isUserPlaylistsUri( uri )
		        || YoutubeSiocUtils.isUserUploadsUri( uri );
	}

	@Override
	public Container getContainer( URI uri )
	        throws NotFoundException,
	        AuthenticationException,
	        IOException {

		if ( YoutubeSiocUtils.isPlaylistUri( uri ) ) {
			return getPlaylist( uri );
		} else if ( YoutubeSiocUtils.isUserUploadsUri( uri ) ) {
			return getUserUploads( uri );
		} else if ( YoutubeSiocUtils.isUserPlaylistsUri( uri ) ) {
			return getUserPlaylists( uri );
		}

		throw new NotFoundException( "No Youtube container found at uri " + uri );
	}

	@Override
	public List<Container> listContainer() {
		return Lists.newArrayList( (Container) uploads, (Container) playlists );
	}

	@Override
	public boolean hasChildContainer( URI uri ) {
		return YoutubeSiocUtils.isUserPlaylistsUri( uri );
	}

	@Override
	public List<Container> listContainer( URI parentUri )
	        throws NotFoundException,
	        AuthenticationException,
	        IOException {
		if ( YoutubeSiocUtils.isUserPlaylistsUri( parentUri ) ) {
			List<Container> result = Lists.newArrayList();
			Forum parent = SiocUtils.asForum( getContainer( parentUri ) );
			String pageUrl = parentUri.toString();

			do {
				PlaylistLinkFeed playlistFeed = null;
				try {
					getConnector().waitForCooldown();
					playlistFeed = defaultClient.getYoutubeService().getFeed(
					        new URL( pageUrl ),
					        PlaylistLinkFeed.class );
				} catch ( MalformedURLException e ) {
					// shouldn't happened
					Throwables.propagate( e );
				} catch ( ServiceException e ) {
					YoutubeConnector.handleYoutubeExceptions( e );
				}

				for ( PlaylistLinkEntry playlistEntry : playlistFeed.getEntries() ) {
					result.add( YoutubeSiocUtils.createSiocThread(
					        getConnector(),
					        playlistEntry,
					        parent ) );
				}

				Link nextLink = playlistFeed.getNextLink();
				pageUrl = ( null != nextLink ) ? nextLink.getHref() : null;
			} while ( null != pageUrl );

			return result;
		}

		throw new NotFoundException(
		        "There are no Youtube sub containers for the uri "
		                + parentUri );
	}

	/**
	 * Gets a playlist from that URI as an {@link Container}.
	 * 
	 * @param uri
	 *            URI to get the playlist from.
	 * @return The playlist {@link Container} of the URI.
	 * 
	 * @throws NotFoundException
	 *             Thrown if no resource was found at the URI
	 * @throws AuthenticationException
	 *             Thrown if there is a problem with authentication.
	 * @throws IOException
	 *             Thrown if there ist problem in communication.
	 */
	private Container getPlaylist( URI uri )
	        throws NotFoundException,
	        AuthenticationException,
	        IOException {
		if ( Thread.hasInstance( getModel(), uri ) ) {
			return Thread.getInstance( getModel(), uri );
		}

		PlaylistLinkEntry playlistEntry = null;
		try {
			getConnector().waitForCooldown();
			playlistEntry = defaultClient.getYoutubeService().getEntry(
			        new URL( uri.toString() ),
			        PlaylistLinkEntry.class );
		} catch ( MalformedURLException e ) {
			// shouldn't happened
			Throwables.propagate( e );
		} catch ( ServiceException e ) {
			YoutubeConnector.handleYoutubeExceptions( e );
		}

		Container parent = getContainer( Builder.createURI(
		        playlistEntry.getAuthors().get( 0 ).getUri()
		                + "/playlists" ) );

		return YoutubeSiocUtils.createSiocThread(
		        getConnector(),
		        playlistEntry,
		        SiocUtils.asForum( parent ) );
	}

	/**
	 * Get an uploads-feed from an URI as {@link Container}.
	 * 
	 * @param uri
	 *            URI to get the uploads-feed from.
	 * @return A {@link Container} of that uploads-feed.
	 * 
	 * @throws NotFoundException
	 *             Thrown if no resource was found at the URI
	 * @throws AuthenticationException
	 *             Thrown if there is a problem with authentication.
	 * @throws IOException
	 *             Thrown if there ist problem in communication.
	 */
	private Container getUserUploads( URI uri )
	        throws NotFoundException,
	        AuthenticationException,
	        IOException {
		if ( Forum.hasInstance( getModel(), uri ) ) {
			return Forum.getInstance( getModel(), uri );
		}

		VideoFeed videoFeed = null;
		try {
			getConnector().waitForCooldown();
			videoFeed = defaultClient.getYoutubeService().getFeed(
			        new URL( uri.toString() ),
			        VideoFeed.class );
		} catch ( MalformedURLException e ) {
			// shouldn't happened
			Throwables.propagate( e );
		} catch ( ServiceException e ) {
			YoutubeConnector.handleYoutubeExceptions( e );
		}

		return YoutubeSiocUtils.createSiocForum( getConnector(), uri, videoFeed );
	}

	/**
	 * Gets a playlists-feed from an URI as a {@link Container}.
	 * 
	 * @param uri
	 *            URI to get the playlists-feed from.
	 * @return A Container of that playlists-feed.
	 * 
	 * @throws NotFoundException
	 *             Thrown if no resource was found at the URI
	 * @throws AuthenticationException
	 *             Thrown if there is a problem with authentication.
	 * @throws IOException
	 *             Thrown if there ist problem in communication.
	 */
	private Container getUserPlaylists( URI uri )
	        throws NotFoundException,
	        AuthenticationException,
	        IOException {
		if ( Forum.hasInstance( getModel(), uri ) ) {
			return Forum.getInstance( getModel(), uri );
		}

		PlaylistLinkFeed playlistFeed = null;
		try {
			getConnector().waitForCooldown();
			playlistFeed = defaultClient.getYoutubeService().getFeed(
			        new URL( uri.toString() ),
			        PlaylistLinkFeed.class );
		} catch ( MalformedURLException e ) {
			// shouldn't happened
			Throwables.propagate( e );
		} catch ( ServiceException e ) {
			YoutubeConnector.handleYoutubeExceptions( e );
		}

		return YoutubeSiocUtils.createSiocForum( getConnector(), uri, playlistFeed );
	}
}
