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

	public static URI createUserUri( final URI rootUri, final int id ) {
		return Builder.createURI(
		        UriTemplate.fromTemplate( rootUri + TEMPLATE_USER_URI )
		                .set( TEMPLATE_VAR_USER_ID, id )
		                .expand() );
	}

	public static URI createForumUri( final URI rootUri, final int id ) {
		return Builder.createURI(
		        UriTemplate.fromTemplate( rootUri + TEMPLATE_FORUM_URI )
		                .set( TEMPLATE_VAR_FORUM_ID, id )
		                .expand() );
	}

	public static URI createForumDiscussionUri( final URI rootUri, final int id ) {
		return Builder.createURI(
		        UriTemplate.fromTemplate( rootUri + TEMPLATE_DISCUSSION_URI )
		                .set( TEMPLATE_VAR_DISCUSSION_ID, id )
		                .expand() );
	}

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

	public static boolean isForumUri( final URI uri, final URI rootUri ) {
		Pattern pattern = Pattern.compile(
		        "^" + rootUri + Moodle2SiocUtils.REGEX_FORUM_URI );
		Matcher matcher = pattern.matcher( uri.toString() );

		return matcher.matches();
	}

	public static boolean isForumDiscussionUri( final URI uri, final URI rootUri ) {
		Pattern pattern = Pattern.compile(
		        "^" + rootUri + Moodle2SiocUtils.REGEX_DISCUSSION_URI );
		Matcher matcher = pattern.matcher( uri.toString() );

		return matcher.matches();
	}

	public static boolean isForumPostUri( final URI uri, final URI rootUri ) {
		Pattern pattern = Pattern.compile(
		        "^" + rootUri + Moodle2SiocUtils.REGEX_FORUM_POST_URI );
		Matcher matcher = pattern.matcher( uri.toString() );

		return matcher.matches();
	}
}
