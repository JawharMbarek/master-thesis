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

package de.m0ep.socc.core.connector.moodle;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
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
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;

import de.m0ep.moodlews.soap.CourseRecord;
import de.m0ep.moodlews.soap.ForumDiscussionRecord;
import de.m0ep.moodlews.soap.ForumPostRecord;
import de.m0ep.moodlews.soap.ForumRecord;
import de.m0ep.moodlews.soap.UserRecord;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.exceptions.NotFoundException;
import de.m0ep.socc.core.utils.DateUtils;
import de.m0ep.socc.core.utils.HtmlLinkExtractor;
import de.m0ep.socc.core.utils.HtmlLinkExtractor.Link;
import de.m0ep.socc.core.utils.SiocUtils;
import de.m0ep.socc.core.utils.StringUtils;
import de.m0ep.socc.core.utils.UserAccountUtils;

/**
 * Utility methods to convert Moodle resouces to SIOC and handle URIs.
 * 
 * @author Florian Müller
 */
public final class Moodle2SiocUtils {
	public static final String REGEX_INT_ID_GROUP = "([0-9]+)";

	public static final String REGEX_USER_URI =
	        "/user/profile\\.php\\?id="
	                + REGEX_INT_ID_GROUP;

	public static final String REGEX_FORUM_URI =
	        "/mod/forum/view\\.php\\?id="
	                + REGEX_INT_ID_GROUP;

	public static final String REGEX_DISCUSSION_URI =
	        "/mod/forum/discuss\\.php\\?d="
	                + REGEX_INT_ID_GROUP;

	public static final String REGEX_FORUM_POST_URI =
	        "/mod/forum/discuss\\.php\\?d="
	                + REGEX_INT_ID_GROUP
	                + "#p"
	                + REGEX_INT_ID_GROUP;

	public static final String TEMPLATE_VAR_USER_ID = "userId";

	public static final String TEMPLATE_VAR_FORUM_ID = "forumId";

	public static final String TEMPLATE_VAR_DISCUSSION_ID = "discussionId";

	public static final String TEMPLATE_VAR_ENTRY_ID = "postId";

	public static final String TEMPLATE_USER_URI =
	        "/user/profile.php?id={"
	                + TEMPLATE_VAR_USER_ID
	                + "}";

	public static final String TEMPLATE_FORUM_URI =
	        "/mod/forum/view.php?id={"
	                + TEMPLATE_VAR_FORUM_ID
	                + "}";

	public static final String TEMPLATE_DISCUSSION_URI =
	        "/mod/forum/discuss.php?d={"
	                + TEMPLATE_VAR_DISCUSSION_ID
	                + "}";

	public static final String TEMPLATE_FORUM_POST_URI =
	        "/mod/forum/discuss.php?d={"
	                + TEMPLATE_VAR_DISCUSSION_ID
	                + "}#p{"
	                + TEMPLATE_VAR_ENTRY_ID
	                + "}";

	private Moodle2SiocUtils() {
	}

	/**
	 * Creates an {@link UserAccount} for an Moodle user.
	 * 
	 * @param connector
	 *            Used Connector.
	 * @param userid
	 *            Id of the user
	 * @return A {@link UserAccount} for the User with hat id.
	 * 
	 * @throws AuthenticationException
	 *             Thrown if there is a problem with authentication.
	 * @throws IOException
	 *             Thrown if there ist problem in communication.
	 */
	public static UserAccount createSiocUserAccount(
	        final Moodle2Connector connector,
	        final int userid )
	        throws AuthenticationException, IOException {
		Preconditions.checkNotNull( connector,
		        "Required parameter connector must be specified." );

		Model model = connector.getContext().getModel();
		URI serviceEndpoint = connector.getService().getServiceEndpoint().asURI();
		URI uri = createUserUri( serviceEndpoint, userid );

		if ( UserAccount.hasInstance( model, uri ) ) {
			return UserAccount.getInstance( model, uri );
		} else {
			final Moodle2ClientWrapper client = connector.getClientManager().getDefaultClient();
			UserRecord[] userRecords = client
			        .callMethod( new Callable<UserRecord[]>() {
				        @Override
				        public UserRecord[] call() throws Exception {
					        return client.getBindingStub().get_user_byid(
					                client.getAuthClient(),
					                client.getSessionKey(),
					                userid );
				        }
			        } );

			if ( null != userRecords && 0 < userRecords.length ) {
				UserAccount result = new UserAccount( model, uri, true );
				result.setId( Integer.toString( userRecords[0].getId() ) );
				result.setName( userRecords[0].getName() );
				result.setAccountName( Integer.toString( userRecords[0].getId() ) );
				result.setAccountServiceHomepage( serviceEndpoint );

				Service service = connector.getService();
				Thing.setService( result.getModel(), result.getResource(), service );
				service.addServiceOf( result );

				return result;
			}
		}

		throw new IOException( "Failed to read user data" );
	}

	/**
	 * Creates a {@link Forum} for an {@link ForumRecord} of a
	 * {@link CourseRecord}.
	 * 
	 * @param connector
	 *            Used Connector.
	 * @param forumRecord
	 *            The {@link ForumRecord} to convert.
	 * @param courseRecord
	 *            The {@link CourseRecord} of the {@link ForumRecord}.
	 * @return A {@link Forum} of the {@link ForumRecord}.
	 */
	public static Forum createSiocForum(
	        final Moodle2Connector connector,
	        final ForumRecord forumRecord,
	        final CourseRecord courseRecord ) {
		Preconditions.checkNotNull( connector,
		        "Required parameter connector must be specified." );
		Preconditions.checkNotNull( forumRecord,
		        "Required parameter forumRecord must be specified." );

		Model model = connector.getContext().getModel();
		URI uri = createForumUri(
		        connector.getService().getServiceEndpoint().asURI(),
		        forumRecord.getId() );

		Forum result = Forum.hasInstance( model, uri )
		        ? Forum.getInstance( model, uri )
		        : new Forum( model, uri, true );

		String courseName = ( null != courseRecord )
		        ? courseRecord.getFullname()
		        : "Course " + forumRecord.getId();

		result.setId( Integer.toString( forumRecord.getId() ) );
		result.setName( courseName
		        + "/"
		        + forumRecord.getName() );
		result.setDescription( forumRecord.getIntro() );

		// update relationships
		Site site = connector.getStructureReader().getSite();
		result.setHost( site );
		site.addHostOf( result );

		return result;
	}

	/**
	 * Creates a {@link Thread} from a {@link ForumDiscussionRecord}.
	 * 
	 * @param connector
	 *            Used Connector.
	 * @param discussionRecord
	 *            The {@link ForumDiscussionRecord} to convert.
	 * @param parentForum
	 *            The parent {@link Forum}.
	 * @return A Thread from the {@link ForumDiscussionRecord}.
	 */
	public static Thread createSiocThread(
	        final Moodle2Connector connector,
	        final ForumDiscussionRecord discussionRecord,
	        final Forum parentForum ) {
		Preconditions.checkNotNull( connector,
		        "Required parameter connector must be specified." );
		Preconditions.checkNotNull( discussionRecord,
		        "Required parameter discussionRecord must be specified." );
		Preconditions.checkNotNull( parentForum,
		        "Required parameter parentForum must be specified." );
		Preconditions.checkArgument( parentForum.hasId(),
		        "The parameter parentForum has no id." );

		int id = discussionRecord.getPost().getDiscussion();
		Model model = connector.getContext().getModel();
		URI uri = createForumDiscussionUri(
		        connector.getService().getServiceEndpoint().asURI(),
		        id );

		Thread result = ( Thread.hasInstance( model, uri ) )
		        ? Thread.getInstance( model, uri )
		        : new Thread( model, uri, true );

		result.setId( Integer.toString( id ) );
		result.setName( discussionRecord.getName() );

		// update relationships
		if ( null != parentForum ) {
			result.setParent( parentForum );
			parentForum.addParentOf( result );
			SiocUtils.incNumThreads( parentForum );
		}

		return result;
	}

	/**
	 * Creates a {@link Post} from a {@link ForumPostRecord}.
	 * 
	 * @param connector
	 *            Used Connector.
	 * @param postRecord
	 *            {@link ForumPostRecord} to convert.
	 * @param container
	 *            Parent {@link Container}.
	 * @param parentPost
	 *            Parent {@link Post}.
	 * @return A Post from the {@link ForumPostRecord}.
	 * 
	 * @throws AuthenticationException
	 *             Thrown if there is a problem with authentication.
	 * @throws IOException
	 *             Thrown if there ist problem in communication.
	 */
	public static Post createSiocPost(
	        final Moodle2Connector connector,
	        final ForumPostRecord postRecord,
	        final Container container,
	        final Post parentPost )
	        throws IOException,
	        AuthenticationException {
		Preconditions.checkNotNull( connector,
		        "Required parameter connector must be specified." );
		Preconditions.checkNotNull( postRecord,
		        "Required parameter postRecord must be specified." );
		Preconditions.checkNotNull( container,
		        "Required parameter discussion must be specified." );

		Model model = connector.getContext().getModel();
		URI serviceEndpoint = connector.getService().getServiceEndpoint().asURI();

		int discussionId;
		try {
			discussionId = Integer.parseInt( container.getId() );
		} catch ( NumberFormatException e2 ) {
			throw new IllegalArgumentException(
			        "The parameter discussion has an ivalid id." );
		}

		URI uri = createForumPostUri( serviceEndpoint, discussionId, postRecord.getId() );
		Post result = ( Post.hasInstance( model, uri ) )
		        ? Post.getInstance( model, uri )
		        : new Post( model, uri, true );

		UserAccount creator = null;
		try {
			String accountName = Integer.toString( postRecord.getUserid() );
			creator = UserAccountUtils.findUserAccount(
			        model,
			        accountName,
			        serviceEndpoint );
		} catch ( NotFoundException e ) {
			try {
				creator = createSiocUserAccount( connector, postRecord
				        .getUserid() );
			} catch ( Exception e1 ) {
				Throwables.propagateIfInstanceOf( e1, AuthenticationException.class );
				Throwables.propagateIfInstanceOf( e1, IOException.class );
				Throwables.propagate( e1 );
			}
		}

		result.setId( Integer.toString( postRecord.getId() ) );
		result.setIsPartOf( connector.getStructureReader().getSite() );
		result.setTitle( postRecord.getSubject() );
		result.setCreator( creator );
		result.setContent(
		        StringUtils.stripHTML(
		                Strings.nullToEmpty(
		                        postRecord.getMessage() ) ) );

		Date createdDate = new Date( postRecord.getCreated() * 1000L );
		result.setCreated( DateUtils.formatISO8601( createdDate ) );
		result.setModified( DateUtils.formatISO8601( postRecord.getModified() * 1000L ) );

		result.removeAllAttachments();
		List<Link> links = HtmlLinkExtractor.extractLinks( postRecord.getMessage() );
		for ( Link link : links ) {
			result.addAttachment( link.getUri() );
		}

		// update relationships
		if ( null != container ) {
			result.setContainer( container );
			container.addContainerOf( result );
			SiocUtils.updateLastItemDate( container, createdDate );
			SiocUtils.incNumItems( container );
		}

		if ( null != parentPost ) {
			result.setReplyOf( parentPost );
			parentPost.addReply( result );
			SiocUtils.updateLastReplyDate( parentPost, createdDate );
			SiocUtils.incNumReplies( parentPost );
		}

		return result;
	}

	/**
	 * Creates a URI for an {@link UserAccount}.
	 * 
	 * @param rootUri
	 *            Root URI of the Moodle instance.
	 * @param id
	 *            Id of the user.
	 * @return A URI for an {@link UserAccount}.
	 */
	public static URI createUserUri( final URI rootUri, final int id ) {
		return Builder.createURI(
		        UriTemplate.fromTemplate( rootUri + TEMPLATE_USER_URI )
		                .set( TEMPLATE_VAR_USER_ID, id )
		                .expand() );
	}

	/**
	 * Crates a URI for a {@link Forum}.
	 * 
	 * @param rootUri
	 *            Root URI of the Moodle instance.
	 * @param id
	 *            Id of the {@link Forum}.
	 * @return A URI for a {@link Forum}.
	 */
	public static URI createForumUri( final URI rootUri, final int id ) {
		return Builder.createURI(
		        UriTemplate.fromTemplate( rootUri + TEMPLATE_FORUM_URI )
		                .set( TEMPLATE_VAR_FORUM_ID, id )
		                .expand() );
	}

	/**
	 * Crates an URI for a {@link Thread}.
	 * 
	 * @param rootUri
	 *            Root URI of the Moodle instance.
	 * @param id
	 *            Id of the {@link Thread}.
	 * 
	 * @return An URI for a {@link Thread}.
	 */
	public static URI createForumDiscussionUri( final URI rootUri, final int id ) {
		return Builder.createURI(
		        UriTemplate.fromTemplate( rootUri + TEMPLATE_DISCUSSION_URI )
		                .set( TEMPLATE_VAR_DISCUSSION_ID, id )
		                .expand() );
	}

	/**
	 * Creates a URI for a {@link Post}
	 * 
	 * 
	 * @param rootUri
	 *            Root URI of the Moodle instance.
	 * @param discussionId
	 *            Id of the Thread of the {@link Post}.
	 * @param entryId
	 *            Id of the {@link Post}.
	 * @return A URI for a Post.
	 */
	public static URI createForumPostUri(
	        final URI rootUri,
	        final int discussionId,
	        final int entryId ) {
		return Builder.createURI(
		        UriTemplate.fromTemplate( rootUri + TEMPLATE_FORUM_POST_URI )
		                .set( TEMPLATE_VAR_DISCUSSION_ID, discussionId )
		                .set( TEMPLATE_VAR_ENTRY_ID, entryId )
		                .expand() );
	}

	/**
	 * Tests if an URI is from a {@link Forum}.
	 * 
	 * @param uri
	 *            URI to test.
	 * @param rootUri
	 *            Root URI of the Moodle instance.
	 * @return <code>true</code> if the URI is form a {@link Forum},
	 *         <code>false</code> otherwise.
	 */
	public static boolean isForumUri( final URI uri, final URI rootUri ) {
		Pattern pattern = Pattern.compile(
		        "^" + rootUri + Moodle2SiocUtils.REGEX_FORUM_URI );
		Matcher matcher = pattern.matcher( uri.toString() );

		return matcher.matches();
	}

	/**
	 * Tests if an URI is from a Thread.
	 * 
	 * @param uri
	 *            URI to test.
	 * @param rootUri
	 *            Root URI of the Moodle instance.
	 * @return <code>true</code> if the URI is form a {@link Thread},
	 *         <code>false</code> otherwise.
	 */
	public static boolean isForumDiscussionUri( final URI uri, final URI rootUri ) {
		Pattern pattern = Pattern.compile(
		        "^" + rootUri + Moodle2SiocUtils.REGEX_DISCUSSION_URI );
		Matcher matcher = pattern.matcher( uri.toString() );

		return matcher.matches();
	}

	/**
	 * Tests if the URI is from a {@link Post}.
	 * 
	 * @param uri
	 *            URI to test.
	 * @param rootUri
	 *            Root URI of the Moodle instance.
	 * @return <code>true</code> if the URI is form a {@link Post},
	 *         <code>false</code> otherwise.
	 */
	public static boolean isForumPostUri( final URI uri, final URI rootUri ) {
		Pattern pattern = Pattern.compile(
		        "^" + rootUri + Moodle2SiocUtils.REGEX_FORUM_POST_URI );
		Matcher matcher = pattern.matcher( uri.toString() );

		return matcher.matches();
	}
}
