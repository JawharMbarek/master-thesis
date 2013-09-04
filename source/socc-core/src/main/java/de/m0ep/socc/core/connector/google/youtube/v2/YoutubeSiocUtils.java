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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.util.Builder;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.Site;
import org.rdfs.sioc.Thread;
import org.rdfs.sioc.UserAccount;
import org.rdfs.sioc.services.Service;
import org.rdfs.sioc.services.Thing;

import com.damnhandy.uri.template.UriTemplate;
import com.google.common.base.Throwables;
import com.google.gdata.data.Link;
import com.google.gdata.data.Person;
import com.google.gdata.data.TextContent;
import com.google.gdata.data.youtube.CommentEntry;
import com.google.gdata.data.youtube.PlaylistLinkEntry;
import com.google.gdata.data.youtube.PlaylistLinkFeed;
import com.google.gdata.data.youtube.UserProfileEntry;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.VideoFeed;
import com.google.gdata.data.youtube.YouTubeMediaGroup;
import com.google.gdata.data.youtube.YouTubeNamespace;
import com.google.gdata.util.ServiceException;

import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.exceptions.NotFoundException;
import de.m0ep.socc.core.utils.DateUtils;
import de.m0ep.socc.core.utils.SiocUtils;
import de.m0ep.socc.core.utils.StringUtils;

public class YoutubeSiocUtils {

	public static final String YOUTUBE_GDATA_API_ROOT_URI = "http://gdata.youtube.com/feeds/api";

	public static final String REGEX_STRING_ID_GROUP = "([^/\\s?]+)";

	public static final String REGEX_USER_URI =
	        "^"
	                + YOUTUBE_GDATA_API_ROOT_URI
	                + "/users/"
	                + REGEX_STRING_ID_GROUP;

	public static final String REGEX_PLAYLIST_URI =
	        "^"
	                + YOUTUBE_GDATA_API_ROOT_URI
	                + "/playlists/"
	                + REGEX_STRING_ID_GROUP;

	public static final String REGEX_VIDEO_URI =
	        "^"
	                + YOUTUBE_GDATA_API_ROOT_URI
	                + "/videos/"
	                + REGEX_STRING_ID_GROUP;

	public static final String REGEX_COMMENT_URI =
	        "^"
	                + YOUTUBE_GDATA_API_ROOT_URI
	                + "/videos/"
	                + REGEX_STRING_ID_GROUP
	                + "/comments/"
	                + REGEX_STRING_ID_GROUP;

	public static final String REGEX_USER_UPLOADS_URI =
	        "^"
	                + YOUTUBE_GDATA_API_ROOT_URI
	                + "/users/"
	                + REGEX_STRING_ID_GROUP
	                + "/uploads";

	public static final String REGEX_USER_PLAYLISTS_URI =
	        "^"
	                + YOUTUBE_GDATA_API_ROOT_URI
	                + "/users/"
	                + REGEX_STRING_ID_GROUP
	                + "/playlists";

	public static final String TEMPLATE_VAR_USER_ID = "userId";

	public static final String TEMPLATE_VAR_PLAYLIST_ID = "playlistId";

	public static final String TEMPLATE_VAR_VIDEO_ID = "videoId";

	public static final String TEMPLATE_VAR_COMMENT_ID = "commentId";

	/*
	 * http://gdata.youtube.com/feeds/api/users/{userId}?v=2
	 */
	public static final String TEMPLATE_USER_URI =
	        YOUTUBE_GDATA_API_ROOT_URI
	                + "/users/{"
	                + TEMPLATE_VAR_USER_ID
	                + "}";
	/*
	 * http://gdata.youtube.com/feeds/api/playlists/{playlistId}?v=2
	 */
	public static final String TEMPLATE_PLAYLIST_URI =
	        YOUTUBE_GDATA_API_ROOT_URI
	                + "/playlists/{"
	                + TEMPLATE_VAR_PLAYLIST_ID
	                + "}";

	/*
	 * http://gdata.youtube.com/feeds/api/videos/{videoId}?v=2
	 */
	public static final String TEMPLATE_VIDEO_URI =
	        YOUTUBE_GDATA_API_ROOT_URI
	                + "/videos/{"
	                + TEMPLATE_VAR_VIDEO_ID
	                + "}";
	/*
	 * http://gdata.youtube.com/feeds/api/videos/{videoId}/comments/{commentId}?v
	 * =2
	 */
	public static final String TEMPLATE_COMMENT_URI =
	        YOUTUBE_GDATA_API_ROOT_URI
	                + "/videos/{"
	                + TEMPLATE_VAR_VIDEO_ID
	                + "}/comments/{"
	                + TEMPLATE_VAR_COMMENT_ID
	                + "}";
	/*
	 * http://gdata.youtube.com/feeds/api/users/{userId}/uploads?v=2
	 */
	public static final String TEMPLATE_USER_UPLOADS_URI =
	        YOUTUBE_GDATA_API_ROOT_URI
	                + "/users/{"
	                + TEMPLATE_VAR_USER_ID
	                + "}/uploads";

	/*
	 * http://gdata.youtube.com/feeds/api/users/{userId}/playlists?v=2
	 */
	public static final String TEMPLATE_USER_PLAYLISTS_URI =
	        YOUTUBE_GDATA_API_ROOT_URI
	                + "/users/{"
	                + TEMPLATE_VAR_USER_ID
	                + "}/playlists";

	private YoutubeSiocUtils() {
	}

	public static UserAccount createSiocUserAccount(
	        final YoutubeConnector connector,
	        final Person author )
	        throws NotFoundException,
	        AuthenticationException,
	        IOException {
		Model model = connector.getContext().getModel();
	
		URI uri = Builder.createURI( author.getUri() );
		if ( !UserAccount.hasInstance( model, uri ) ) {
			YoutubeClientWrapper client = connector.getClientManager()
			        .getDefaultClient();
			UserProfileEntry profileEntry = null;
			try {
				// load full profile
				connector.waitForCooldown();
				profileEntry = client.getService().getEntry(
				        new URL( author.getUri() ),
				        UserProfileEntry.class );
			} catch ( MalformedURLException e ) {
				// shouldn't happened
				Throwables.propagate( e );
			} catch ( ServiceException e ) {
				YoutubeConnector.handleYoutubeExceptions( e );
			}
			Service service = connector.getService();
	
			UserAccount result = new UserAccount( model, uri, true );
			result.setId( profileEntry.getUsername() );
			result.setName( profileEntry.getTitle().toString() );
			result.setAccountName( profileEntry.getUsername() );
			result.setAccountServiceHomepage( service.getServiceEndpoint() );
	
			Thing.setService( result.getModel(), result.asResource(), service );
			service.addServiceOf( result );
	
			return result;
		}
	
		return UserAccount.getInstance( model, uri );
	}

	public static Forum createSiocForum(
	        final YoutubeConnector connector,
	        final URI uri,
	        final PlaylistLinkFeed playlistFeed ) {
		Model model = connector.getContext().getModel();
		Pattern pattern = Pattern
		        .compile( YoutubeSiocUtils.REGEX_USER_PLAYLISTS_URI );
		Matcher matcher = pattern.matcher( uri.toString() );
		matcher.find();

		Forum result = new Forum( model, uri, true );
		result.setId( matcher.group( 1 ) + "/playlists" );
		result.setName( playlistFeed.getTitle().toString() );
		result.setNumItems( 0 );
		result.setNumThreads( 0 );

		Site site = connector.getStructureReader().getSite();
		result.setHost( site );
		site.addHostOf( result );
		return result;
	}

	public static Forum createSiocForum(
	        final YoutubeConnector connector,
	        final URI uri,
	        final VideoFeed videoFeed ) {
		Model model = connector.getContext().getModel();

		Pattern pattern = Pattern
		        .compile( YoutubeSiocUtils.REGEX_USER_UPLOADS_URI );
		Matcher matcher = pattern.matcher( uri.toString() );
		matcher.find();

		Forum result = new Forum( model, uri, true );
		result.setId( matcher.group( 1 ) + "/uploads" );
		result.setName( videoFeed.getTitle().toString() );
		result.setNumItems( 0 );
		result.setNumThreads( 0 );

		Site site = connector.getStructureReader().getSite();
		result.setHost( site );
		site.addHostOf( result );
		return result;
	}

	public static Thread createSiocThread(
	        final YoutubeConnector connector,
	        final PlaylistLinkEntry playlistEntry,
	        final Forum parent ) {
		Model model = connector.getContext().getModel();
		URI uri = createPlaylistUri( playlistEntry.getPlaylistId() );

		if ( !Thread.hasInstance( model, uri ) ) {
			Thread result = new Thread( model, uri, true );
			result.setId( playlistEntry.getPlaylistId() );

			if ( null != playlistEntry.getTitle() ) {
				result.setName( playlistEntry.getTitle().getPlainText() );
			}

			if ( null != playlistEntry.getSummary() ) {
				result.setDescription( playlistEntry.getSummary().getPlainText() );
			}

			if ( null != playlistEntry.getPublished() ) {
				result.setCreated( DateUtils.formatISO8601( playlistEntry
				        .getPublished().getValue() ) );
			}
			if ( null != playlistEntry.getUpdated() ) {
				result.setModified( DateUtils.formatISO8601( playlistEntry
				        .getUpdated().getValue() ) );
			}

			if ( null != parent ) {
				result.setParent( parent );
				parent.addParentOf( result );
				SiocUtils.incNumThreads( parent );
			}

			return result;
		}

		return Thread.getInstance( model, uri );
	}

	public static Post createSiocPost(
	        final YoutubeConnector connector,
	        final VideoEntry videoEntry,
	        final Container container )
	        throws NotFoundException,
	        AuthenticationException,
	        IOException {
		Model model = connector.getContext().getModel();
		YouTubeMediaGroup mediaGroup = videoEntry.getMediaGroup();

		URI uri = createVideoUri( mediaGroup.getVideoId() );

		Post result = new Post( model, uri, true );
		result.setId( mediaGroup.getVideoId() );

		for ( Person author : videoEntry.getAuthors() ) {
			UserAccount creator = createSiocUserAccount( connector, author );
			result.addCreator( creator );
		}

		if ( null != mediaGroup.getTitle() ) {
			result.setTitle( mediaGroup.getTitle().getPlainTextContent() );
		}

		if ( null != videoEntry.getContent()
		        && videoEntry.getContent() instanceof TextContent ) {
			result.setContent(
			        StringUtils.stripHTML(
			                videoEntry.getPlainTextContent() ) );
		} else if ( null != mediaGroup.getDescription() ) {
			result.setContent(
			        StringUtils.stripHTML(
			                mediaGroup.getDescription().getPlainTextContent() ) );
		}

		// get the link to the video on Youtube
		Link videoLink = videoEntry.getLink( "alternate", "text/html" );
		if ( null != videoLink ) {
			result.addAttachment( Builder.createURI( videoLink.getHref() ) );
		}

		Date createdDate = new Date( videoEntry.getPublished().getValue() );
		result.setCreated( DateUtils.formatISO8601( createdDate ) );

		if ( null != videoEntry.getUpdated() ) {
			result.setModified(
			        DateUtils.formatISO8601(
			                videoEntry.getUpdated().getValue() ) );
		}

		if ( null != container ) {
			result.setContainer( container );
			container.addContainerOf( result );
			SiocUtils.incNumItems( container );
			SiocUtils.updateLastItemDate( container, createdDate );
		}

		return result;
	}

	public static Post createSiocPost(
	        final YoutubeConnector connector,
	        final CommentEntry commentEntry,
	        final Post parentPost )
	        throws NotFoundException,
	        AuthenticationException,
	        IOException {
		Model model = connector.getContext().getModel();

		Link selfLink = commentEntry.getLink( "self", null );

		Pattern pattern = Pattern.compile( YoutubeSiocUtils.REGEX_COMMENT_URI );
		Matcher matcher = pattern.matcher( selfLink.getHref() );
		matcher.find();

		String commentId = matcher.group( 2 );
		String videoId = matcher.group( 1 );

		URI uri = createCommentUri( videoId, commentId );

		Post result = new Post( model, uri, true );
		result.setId( commentId );

		for ( Person author : commentEntry.getAuthors() ) {
			UserAccount creator = createSiocUserAccount( connector, author );
			result.addCreator( creator );
		}

		if ( null != commentEntry.getTitle() ) {
			result.setTitle( commentEntry.getTitle().getPlainText() );
		}

		if ( null != commentEntry.getPlainTextContent() ) {
			result.setContent( commentEntry.getPlainTextContent() );
		}

		Date createdDate = new Date( commentEntry.getPublished().getValue() );
		result.setCreated( DateUtils.formatISO8601( createdDate ) );

		if ( null != commentEntry.getUpdated() ) {
			result.setModified(
			        DateUtils.formatISO8601(
			                commentEntry.getUpdated().getValue() ) );
		}

		if ( null != parentPost && parentPost.hasContainer() ) {
			Container container = parentPost.getContainer();
			result.setContainer( container );
			container.addContainerOf( result );
			SiocUtils.incNumItems( container );
			SiocUtils.updateLastItemDate( container, createdDate );
		}

		Link replyToLink = commentEntry.getLink(
		        YouTubeNamespace.IN_REPLY_TO,
		        "application/atom+xml" );
		if ( null != replyToLink ) { // it's a reply to another comment
			String replyToLinkHref = replyToLink.getHref();

			int qmIndex = replyToLinkHref.indexOf( '?' );

			String replyToId = replyToLinkHref.substring(
			        replyToLinkHref.lastIndexOf( '/' ) + 1,
			        ( -1 != qmIndex ) ? qmIndex : replyToLinkHref.length() );
			URI replyToUri = createCommentUri( videoId, replyToId );

			Post replyToPost = new Post( model, replyToUri, true );
			replyToPost.setId( replyToId );

			result.setReplyOf( replyToPost );
			replyToPost.addReply( result );
			SiocUtils.incNumReplies( result );
			SiocUtils.updateLastReplyDate( result, createdDate );

			if ( result.hasContainer() ) {
				Container container = result.getContainer();
				replyToPost.setContainer( container );
				container.addContainerOf( replyToPost );
				SiocUtils.incNumItems( container );
				SiocUtils.updateLastItemDate( container, createdDate );
			}
		}
		else if ( null != parentPost ) { // it's a reply to a video
			result.setReplyOf( parentPost );
			parentPost.addReply( result );
			SiocUtils.incNumReplies( parentPost );
			SiocUtils.updateLastReplyDate( parentPost, createdDate );
		}

		return result;
	}

	public static URI createUserUri( final String userId ) {
		return Builder.createURI(
		        UriTemplate.fromTemplate( TEMPLATE_USER_URI )
		                .set( TEMPLATE_VAR_USER_ID, userId )
		                .expand() );
	}

	public static URI createUserUploadsUri( final String userId ) {
		return Builder.createURI(
		        UriTemplate.fromTemplate( TEMPLATE_USER_UPLOADS_URI )
		                .set( TEMPLATE_VAR_USER_ID, userId )
		                .expand() );
	}

	public static URI createUserPlaylistsUri( final String userId ) {
		return Builder.createURI(
		        UriTemplate.fromTemplate( TEMPLATE_USER_PLAYLISTS_URI )
		                .set( TEMPLATE_VAR_USER_ID, userId )
		                .expand() );
	}

	public static URI createPlaylistUri( final String playlistId ) {
		return Builder.createURI(
		        UriTemplate.fromTemplate( TEMPLATE_PLAYLIST_URI )
		                .set( TEMPLATE_VAR_PLAYLIST_ID, playlistId )
		                .expand() );
	}

	public static URI createVideoUri( final String videoId ) {
		return Builder.createURI(
		        UriTemplate.fromTemplate( TEMPLATE_VIDEO_URI )
		                .set( TEMPLATE_VAR_VIDEO_ID, videoId )
		                .expand() );
	}

	public static URI createCommentUri( final String videoId, final String commentId ) {
		return Builder.createURI(
		        UriTemplate.fromTemplate( TEMPLATE_COMMENT_URI )
		                .set( TEMPLATE_VAR_VIDEO_ID, videoId )
		                .set( TEMPLATE_VAR_COMMENT_ID, commentId )
		                .expand() );
	}

	public static boolean isUserUri( final URI uri ) {
		Pattern pattern = Pattern.compile( REGEX_USER_URI );
		Matcher matcher = pattern.matcher( uri.toString() );
		return matcher.find();
	}

	public static boolean isUserUploadsUri( final URI uri ) {
		Pattern pattern = Pattern.compile( REGEX_USER_UPLOADS_URI );
		Matcher matcher = pattern.matcher( uri.toString() );
		return matcher.find();
	}

	public static boolean isUserPlaylistsUri( final URI uri ) {
		Pattern pattern = Pattern.compile( REGEX_USER_PLAYLISTS_URI );
		Matcher matcher = pattern.matcher( uri.toString() );
		return matcher.find();
	}

	public static boolean isPlaylistUri( final URI uri ) {
		Pattern pattern = Pattern.compile( REGEX_PLAYLIST_URI );
		Matcher matcher = pattern.matcher( uri.toString() );
		return matcher.find();
	}

	public static boolean isVideoUri( final URI uri ) {
		Pattern pattern = Pattern.compile( REGEX_VIDEO_URI );
		Matcher matcher = pattern.matcher( uri.toString() );
		return matcher.find() && !isCommentUri( uri );
	}

	public static boolean isCommentUri( final URI uri ) {
		Pattern pattern = Pattern.compile( REGEX_COMMENT_URI );
		Matcher matcher = pattern.matcher( uri.toString() );
		return matcher.find();
	}
}
