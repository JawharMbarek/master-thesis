package de.m0ep.socc.core.connector.canvaslms;

import java.io.IOException;
import java.util.List;
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

import de.m0ep.canvas.model.Attachment;
import de.m0ep.canvas.model.Course;
import de.m0ep.canvas.model.DiscussionTopic;
import de.m0ep.canvas.model.Entry;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.exceptions.NotFoundException;
import de.m0ep.socc.core.utils.DateUtils;
import de.m0ep.socc.core.utils.HtmlLinkExtractor;
import de.m0ep.socc.core.utils.HtmlLinkExtractor.Link;
import de.m0ep.socc.core.utils.SiocUtils;
import de.m0ep.socc.core.utils.StringUtils;
import de.m0ep.socc.core.utils.UserAccountUtils;

public final class CanvasLmsSiocUtils {

	public static final String CANVAS_LMS_API_PATH = "/api/v1";

	public static final String REGEX_API_PATH = "(?:/api/v1)?";

	public static final String REGEX_INT_ID_GROUP = "([\\d]+)";

	public static final String REGEX_QUERY_PARAMETER = "(?:\\?.*)?";

	public static final String REGEX_USER_URI =
	        REGEX_API_PATH
	                + "/users/"
	                + REGEX_INT_ID_GROUP
	                + "/profile"
	                + REGEX_QUERY_PARAMETER;

	public static final String REGEX_COURSE_URI =
	        REGEX_API_PATH
	                + "/courses/"
	                + REGEX_INT_ID_GROUP;

	public static final String REGEX_DISCUSSION_TOPIC_URI =
	        REGEX_COURSE_URI
	                + "/discussion_topics/"
	                + REGEX_INT_ID_GROUP;

	public static final String REGEX_INITIAL_ENTRY_URI =
	        REGEX_DISCUSSION_TOPIC_URI
	                + "#discussion_topic";

	public static final String REGEX_ENTRY_URI =
	        REGEX_DISCUSSION_TOPIC_URI
	                + "/entries/"
	                + REGEX_INT_ID_GROUP;

	public static final String TEMPLATE_VAR_USER_ID = "userId";

	public static final String TEMPLATE_VAR_COURSE_ID = "courseId";

	public static final String TEMPLATE_VAR_DISCUSSION_TOPIC_ID = "discussionId";

	public static final String TEMPLATE_VAR_ENTRY_ID = "entryId";

	public static final String TEMPLATE_USER_URI =
	        CANVAS_LMS_API_PATH
	                + "/users/{"
	                + TEMPLATE_VAR_USER_ID
	                + "}/profile";

	public static final String TEMPLATE_COURSE_URI =
	        CANVAS_LMS_API_PATH
	                + "/courses/{"
	                + TEMPLATE_VAR_COURSE_ID
	                + "}";

	public static final String TEMPLATE_DISCUSSION_TOPIC_URI =
	        TEMPLATE_COURSE_URI
	                + "/discussion_topics/{"
	                + TEMPLATE_VAR_DISCUSSION_TOPIC_ID
	                + "}";

	public static final String TEMPLATE_INITIAL_ENTRY_URI =
	        TEMPLATE_DISCUSSION_TOPIC_URI
	                + "#discussion_topic";

	public static final String TEMPLATE_ENTRY_URI =
	        TEMPLATE_DISCUSSION_TOPIC_URI
	                + "/entries/{"
	                + TEMPLATE_VAR_ENTRY_ID
	                + "}";

	private CanvasLmsSiocUtils() {
	}

	/*
	 * SIOC object creation methods
	 */

	public static UserAccount createSiocUserAccount(
	        final CanvasLmsConnector connector,
	        final DiscussionTopic.Author author ) {

		return createSiocUserAccount( connector, author.getId(), author.getDisplayName() );
	}

	public static UserAccount createSiocUserAccount(
	        final CanvasLmsConnector connector,
	        final Entry entry ) {

		return createSiocUserAccount( connector, entry.getUserId(), entry.getUserName() );
	}

	public static UserAccount createSiocUserAccount(
	        final CanvasLmsConnector connector,
	        final long userId,
	        final String username ) {

		Model model = connector.getContext().getModel();
		Service service = connector.getService();
		URI userUri = createUserUri(
		        service.getServiceEndpoint().asURI(),
		        userId );

		UserAccount result = new UserAccount( model, userUri, true );
		result.setId( Long.toString( userId ) );
		result.setName( username );
		result.setAccountName( Long.toString( userId ) );
		result.setAccountServiceHomepage( service.getServiceEndpoint() );

		Thing.setService( result.getModel(), result, service );
		service.addServiceOf( result );

		return result;
	}

	public static Forum createSiocForum(
	        final CanvasLmsConnector connector,
	        final Course course ) {
		URI serviceEndpoint = connector.getService().getServiceEndpoint()
		        .asURI();
		URI uri = createCourseUri( serviceEndpoint, course.getId() );

		if ( !Forum.hasInstance( connector.getContext().getModel(), uri ) ) {
			Forum result = new Forum( connector.getContext().getModel(), uri,
			        true );

			result.setId( Long.toString( course.getId() ) );
			result.setName( course.getName() );
			result.setNumThreads( 0 );

			Site site = connector.getStructureReader().getSite();
			result.setHost( site );
			site.addHostOf( result );
		}

		return Forum.getInstance( connector.getContext().getModel(), uri );
	}

	public static Thread createSiocThread(
	        final CanvasLmsConnector connector,
	        final DiscussionTopic discussionTopic,
	        final Forum parent ) {
		Model model = connector.getContext().getModel();
		URI serviceEndpoint = connector.getService()
		        .getServiceEndpoint()
		        .asURI();

		long courseId;
		try {
			courseId = Long.parseLong( parent.getId() );
		} catch ( NumberFormatException e1 ) {
			throw new IllegalArgumentException(
			        "The parent has an invalid id: was "
			                + parent.getId() );
		}

		URI uri = createDiscussionTopicUri(
		        serviceEndpoint,
		        courseId,
		        discussionTopic.getId() );

		if ( !Thread.hasInstance( model, uri ) ) {
			Thread result = new Thread( model, uri,
			        true );
			result.setId( Long.toString( discussionTopic.getId() ) );
			result.setName( discussionTopic.getTitle() );
			result.setNumItems( 0 );

			result.setParent( parent );
			parent.addParentOf( result );
			SiocUtils.incNumThreads( parent );

			createSiocPost(
			        connector,
			        result,
			        discussionTopic,
			        courseId );

			return result;
		}

		return Thread.getInstance( model, uri );
	}

	public static Post createSiocPost(
	        final CanvasLmsConnector connector,
	        final Container container,
	        final DiscussionTopic discussionTopic,
	        final long courseId ) {
		Model model = connector.getContext().getModel();
		URI serviceEndpoint = connector.getService()
		        .getServiceEndpoint()
		        .asURI();

		UserAccount creator = null;
		try {
			String accountName = Long.toString( discussionTopic.getAuthor()
			        .getId() );
			creator = UserAccountUtils.findUserAccount(
			        model,
			        accountName,
			        serviceEndpoint );
		} catch ( NotFoundException e ) {
			creator = createSiocUserAccount( connector,
			        discussionTopic.getAuthor() );
		}

		String createdDate = DateUtils.formatISO8601(
		        discussionTopic.getPostedAt() );

		URI initPostUri = createInitialEntryUri(
		        serviceEndpoint,
		        courseId,
		        discussionTopic.getId() );

		Post result = new Post( model, initPostUri, true );
		result.setId( Long.toString( discussionTopic.getId() ) );
		result.setIsPartOf( connector.getStructureReader().getSite() );
		result.setTitle( discussionTopic.getTitle() );
		result.setContent( StringUtils.stripHTML( discussionTopic.getMessage() ) );
		result.setCreator( creator );
		result.setCreated( createdDate );
		result.setNumReplies( 0 );

		result.removeAllAttachments();

		List<Link> links = HtmlLinkExtractor.extractLinks( discussionTopic.getMessage() );
		for ( Link link : links ) {
			result.addAttachment( link.getUri() );
		}

		for ( Attachment attachment : discussionTopic.getAttachments() ) {
			result.addAttachment( Builder.createURI( attachment.getUrl() ) );
		}

		// update container relation.
		if ( null != container ) {
			result.setContainer( container );
			container.addContainerOf( result );
			SiocUtils.incNumItems( container );
			SiocUtils.updateLastItemDate( container, discussionTopic.getPostedAt() );
		}

		return result;
	}

	public static Post createSiocPost(
	        final CanvasLmsConnector connector,
	        final Entry entry,
	        final Container container,
	        final Post parentPost )
	        throws NotFoundException,
	        AuthenticationException,
	        IOException {
		Model model = connector.getContext().getModel();
		URI serviceEndpoint = connector.getService().getServiceEndpoint().asURI();
		Container containerParent = connector.getStructureReader().getContainer(
		        container.getParent().asURI() );

		long courseId;
		try {
			courseId = Long.parseLong( containerParent.getId() );
		} catch ( NumberFormatException e1 ) {
			throw new IllegalArgumentException(
			        "The parent of the container has an invalid id: was "
			                + containerParent.getId() );
		}

		long discussionId;
		try {
			discussionId = Long.parseLong( container.getId() );
		} catch ( NumberFormatException e1 ) {
			throw new IllegalArgumentException(
			        "The  container has an invalid id: was "
			                + containerParent.getId() );
		}

		URI uri = createEntryUri(
		        serviceEndpoint,
		        courseId,
		        discussionId,
		        entry.getId() );

		// use already exiting Post to update it or create a new one
		Post result = ( Post.hasInstance( model, uri ) )
		        ? Post.getInstance( model, uri )
		        : new Post( model, uri, true );

		UserAccount creator = null;
		try {
			String accountName = Long.toString( entry.getUserId() );
			creator = UserAccountUtils.findUserAccount(
			        model,
			        accountName,
			        serviceEndpoint );
		} catch ( NotFoundException e ) {
			creator = createSiocUserAccount( connector, entry );
		}

		result.setId( Long.toString( entry.getId() ) );
		result.setIsPartOf( connector.getStructureReader().getSite() );
		result.setCreator( creator );
		result.setCreated( DateUtils.formatISO8601( entry.getCreatedAt() ) );
		result.setContent( StringUtils.stripHTML( entry.getMessage() ) );
		result.setNumReplies( 0 );

		if ( null != entry.getUpdatedAt() ) {
			result.setModified( DateUtils.formatISO8601( entry.getUpdatedAt() ) );
		}

		result.removeAllAttachments();

		List<Link> links = HtmlLinkExtractor.extractLinks( entry.getMessage() );
		for ( Link link : links ) {
			result.addAttachment( link.getUri() );
		}

		if ( null != entry.getAttachment() ) {
			result.addAttachment(
			        Builder.createURI(
			                entry.getAttachment().getUrl() ) );
		}

		// update container relation.
		if ( null != container ) {
			result.setContainer( container );
			container.addContainerOf( result );
			SiocUtils.incNumItems( container );
			SiocUtils.updateLastItemDate( container, entry.getCreatedAt() );
		}

		if ( null != parentPost ) {
			result.setReplyOf( parentPost );
			parentPost.addReply( result );
			SiocUtils.incNumReplies( parentPost );
			SiocUtils.updateLastReplyDate( parentPost, entry.getCreatedAt() );
		}

		return result;
	}

	public static URI createUserUri( final URI rootUri, final long userId ) {
		return Builder.createURI(
		        UriTemplate.fromTemplate( rootUri + TEMPLATE_USER_URI )
		                .set( TEMPLATE_VAR_USER_ID, userId )
		                .expand() );
	}

	public static URI createCourseUri( final URI rootUri, final long courseId ) {
		return Builder.createURI(
		        UriTemplate.fromTemplate( rootUri + TEMPLATE_COURSE_URI )
		                .set( TEMPLATE_VAR_COURSE_ID, courseId )
		                .expand() );
	}

	public static URI createDiscussionTopicUri(
	        final URI rootUri,
	        final long courseId,
	        final long discussionId ) {
		return Builder.createURI(
		        UriTemplate.fromTemplate(
		                rootUri + TEMPLATE_DISCUSSION_TOPIC_URI )
		                .set( TEMPLATE_VAR_COURSE_ID, courseId )
		                .set( TEMPLATE_VAR_DISCUSSION_TOPIC_ID, discussionId )
		                .expand() );
	}

	public static URI createInitialEntryUri(
	        final URI rootUri,
	        final long courseId,
	        final long discussionId ) {
		return Builder.createURI(
		        UriTemplate.fromTemplate( rootUri + TEMPLATE_INITIAL_ENTRY_URI )
		                .set( TEMPLATE_VAR_COURSE_ID, courseId )
		                .set( TEMPLATE_VAR_DISCUSSION_TOPIC_ID, discussionId )
		                .expand() );
	}

	public static URI createEntryUri(
	        final URI rootUri,
	        final long courseId,
	        final long discussionId,
	        final long entryId ) {
		return Builder.createURI(
		        UriTemplate.fromTemplate( rootUri + TEMPLATE_ENTRY_URI )
		                .set( TEMPLATE_VAR_COURSE_ID, courseId )
		                .set( TEMPLATE_VAR_DISCUSSION_TOPIC_ID, discussionId )
		                .set( TEMPLATE_VAR_ENTRY_ID, entryId )
		                .expand() );
	}

	public static boolean isUserUri( final URI uri, final URI rootUri ) {
		Pattern pattern = Pattern.compile( "^" + rootUri + REGEX_USER_URI );
		Matcher matcher = pattern.matcher( uri.toString() );

		return matcher.matches();
	}

	public static boolean isCourseUri( final URI uri, final URI rootUri ) {
		Pattern pattern = Pattern.compile( "^" + rootUri + REGEX_COURSE_URI );
		Matcher matcher = pattern.matcher( uri.toString() );

		return matcher.matches();
	}

	public static boolean isDiscussionTopicUri( final URI uri, final URI rootUri ) {
		Pattern pattern = Pattern.compile( "^" + rootUri + REGEX_DISCUSSION_TOPIC_URI );
		Matcher matcher = pattern.matcher( uri.toString() );

		return matcher.matches();
	}

	public static boolean isInitialEntryUri( final URI uri, final URI rootUri ) {
		Pattern pattern = Pattern.compile( "^" + rootUri + REGEX_INITIAL_ENTRY_URI );
		Matcher matcher = pattern.matcher( uri.toString() );

		return matcher.matches();
	}

	public static boolean isEntryUri( final URI uri, final URI rootUri ) {
		Pattern pattern = Pattern.compile( "^" + rootUri + REGEX_ENTRY_URI );
		Matcher matcher = pattern.matcher( uri.toString() );

		return matcher.matches();
	}
}
