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
import de.m0ep.canvas.model.Group;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.exceptions.NotFoundException;
import de.m0ep.socc.core.utils.DateUtils;
import de.m0ep.socc.core.utils.HtmlLinkExtractor;
import de.m0ep.socc.core.utils.HtmlLinkExtractor.Link;
import de.m0ep.socc.core.utils.SiocUtils;
import de.m0ep.socc.core.utils.StringUtils;
import de.m0ep.socc.core.utils.UserAccountUtils;

/**
 * Utility methods to convert Canvas resources to SIOC and handle URIs.
 * 
 * @author Florian Mueller
 * 
 */
public final class CanvasLmsSiocUtils {

	public static final String ENDPOINT_COURSE = "courses";
	public static final String ENDPOINT_GROUPS = "groups";

	public static final String CANVAS_LMS_API_PATH = "/api/v1";

	public static final String REGEX_API_PATH = "(?:/api/v1)?";

	public static final String REGEX_INT_ID_GROUP = "([\\d]+)";

	public static final String REGEX_QUERY_PARAMETER = "(?:\\?.*)?";

	public static final String REGEX_ENDPOINT_GROUP = "([\\w]+)";

	public static final String REGEX_DISCUSSION_TOPIC_PATH = "(?:/discussion_topics)?";

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

	public static final String REGEX_GROUP_URI =
	        REGEX_API_PATH
	                + "/groups/"
	                + REGEX_INT_ID_GROUP;

	public static final String REGEX_ENDPOINT_URI =
	        REGEX_API_PATH +
	                "/" + REGEX_ENDPOINT_GROUP + "/"
	                + REGEX_INT_ID_GROUP;

	public static final String REGEX_DISCUSSION_TOPIC_URI =
	        REGEX_ENDPOINT_URI
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

	public static final String TEMPLATE_VAR_GROUP_ID = "groupId";

	public static final String TEMPLATE_VAR_ENDPOINT = "endpoint";

	public static final String TEMPLATE_VAR_ENDPOINT_ID = "endpointId";

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

	public static final String TEMPLATE_GROUP_URI =
	        CANVAS_LMS_API_PATH
	                + "/groups/{"
	                + TEMPLATE_VAR_GROUP_ID
	                + "}";

	public static final String TEMPLATE_DISCUSSION_TOPIC_URI =
	        CANVAS_LMS_API_PATH
	                + "/{"
	                + TEMPLATE_VAR_ENDPOINT
	                + "}/{"
	                + TEMPLATE_VAR_ENDPOINT_ID
	                + "}"
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

	/**
	 * Creates a {@link UserAccount} for eht author of a discussion topic.
	 * 
	 * @param connector
	 *            Used Connector.
	 * @param author
	 *            The author data
	 * @return A {@link UserAccount} converted from the author data.
	 */
	public static UserAccount createSiocUserAccount(
	        final CanvasLmsConnector connector,
	        final DiscussionTopic.Author author ) {

		return createSiocUserAccount( connector, author.getId(), author.getDisplayName() );
	}

	/**
	 * Creates a {@link UserAccount} for the author of a discussion topic entry.
	 * 
	 * @param connector
	 *            Used Connector.
	 * @param entry
	 *            The entry data,
	 * @return A {@link UserAccount} converted from the entry data.
	 */
	public static UserAccount createSiocUserAccount(
	        final CanvasLmsConnector connector,
	        final Entry entry ) {

		return createSiocUserAccount( connector, entry.getUserId(), entry.getUserName() );
	}

	/**
	 * Creates a {@link UserAccount} for a Canvas user from it's id and name.
	 * 
	 * @param connector
	 *            Used connector.
	 * @param userId
	 *            The id of the user.
	 * @param username
	 *            The name of the user.
	 * @return A {@link UserAccount} from that data.
	 */
	public static UserAccount createSiocUserAccount(
	        final CanvasLmsConnector connector,
	        final long userId,
	        final String username ) {

		Model model = connector.getContext().getModel();
		Service service = connector.getService();
		URI userUri = createUserUri( service.getServiceEndpoint().asURI(), userId );

		UserAccount result = new UserAccount( model, userUri, true );
		result.setId( Long.toString( userId ) );
		result.setName( username );
		result.setAccountName( Long.toString( userId ) );
		result.setAccountServiceHomepage( service.getServiceEndpoint() );

		Thing.setService( result.getModel(), result, service );
		service.addServiceOf( result );

		return result;
	}

	/**
	 * Creates a {@link Forum} from a course.
	 * 
	 * @param connector
	 *            Used connector.
	 * @param course
	 *            Course data.
	 * @return A {@link Forum} from that course data.
	 */
	public static Forum createSiocForum(
	        final CanvasLmsConnector connector,
	        final Course course ) {
		URI serviceEndpoint = connector.getService().getServiceEndpoint().asURI();
		URI uri = createCourseUri( serviceEndpoint, course.getId() );

		if ( !Forum.hasInstance( connector.getContext().getModel(), uri ) ) {
			Forum result = new Forum( connector.getContext().getModel(), uri,
			        true );

			result.setId( Long.toString( course.getId() ) );
			result.setName( course.getName() );

			Site site = connector.getStructureReader().getSite();
			result.setHost( site );
			site.addHostOf( result );
		}

		return Forum.getInstance( connector.getContext().getModel(), uri );
	}

	public static Forum createSiocForum( final CanvasLmsConnector connector, final Group group ) {
		URI serviceEndpoint = connector.getService().getServiceEndpoint().asURI();
		URI uri = createGroupUri( serviceEndpoint, group.getId() );

		if ( !Forum.hasInstance( connector.getContext().getModel(), uri ) ) {
			Forum result = new Forum( connector.getContext().getModel(), uri,
			        true );

			result.setId( Long.toString( group.getId() ) );
			result.setName( group.getName() );

			Site site = connector.getStructureReader().getSite();
			result.setHost( site );
			site.addHostOf( result );
		}

		return Forum.getInstance( connector.getContext().getModel(), uri );
	}

	/**
	 * Creates a {@link Thread} from a discussion topic. Creates also the inital
	 * post.
	 * 
	 * @param connector
	 *            Used connector.
	 * @param endpoint
	 *            Name of the discussion topics endpoint.
	 * @param discussionTopic
	 *            The discussion topic data.
	 * @param parent
	 *            The parent course {@link Forum}.
	 * @return A Thread converted from that data.
	 */
	public static Thread createSiocThread(
	        final CanvasLmsConnector connector,
	        final String endpoint,
	        final DiscussionTopic discussionTopic,
	        final Forum parent ) {
		Model model = connector.getContext().getModel();
		URI serviceEndpoint = connector.getService().getServiceEndpoint().asURI();

		long endpointId;
		try {
			endpointId = Long.parseLong( parent.getId() );
		} catch ( NumberFormatException e1 ) {
			throw new IllegalArgumentException(
			        "The parent has an invalid id: was "
			                + parent.getId() );
		}

		URI uri = createDiscussionTopicUri(
		        serviceEndpoint,
		        endpoint,
		        endpointId,
		        discussionTopic.getId() );

		if ( !Thread.hasInstance( model, uri ) ) {
			Thread result = new Thread( model, uri,
			        true );
			result.setId( Long.toString( discussionTopic.getId() ) );
			result.setName( discussionTopic.getTitle() );

			result.setParent( parent );
			parent.addParentOf( result );
			SiocUtils.incNumThreads( parent );

			createSiocPost(
			        connector,
			        endpoint,
			        result,
			        discussionTopic,
			        endpointId );

			return result;
		}

		return Thread.getInstance( model, uri );
	}

	/**
	 * Creates a {@link Post} form the initial post of a discussion topic.
	 * 
	 * @param connector
	 *            Used connector.
	 * @param endpoint
	 *            Name of the discussion topics endpoint.
	 * @param container
	 *            The parent thread {@link Container}.
	 * @param discussionTopic
	 *            The discussion topic data.
	 * @param endpointId
	 *            The course id.
	 * @return A {@link Post} converted from that data.
	 */
	public static Post createSiocPost(
	        final CanvasLmsConnector connector,
	        final String endpoint,
	        final Container container,
	        final DiscussionTopic discussionTopic,
	        final long endpointId ) {
		Model model = connector.getContext().getModel();
		URI serviceEndpoint = connector.getService().getServiceEndpoint().asURI();

		UserAccount creator = null;
		try {
			String accountName = Long.toString( discussionTopic.getAuthor().getId() );
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
		        endpoint,
		        endpointId,
		        discussionTopic.getId() );

		Post result = new Post( model, initPostUri, true );
		result.setId( Long.toString( discussionTopic.getId() ) );
		result.setIsPartOf( connector.getStructureReader().getSite() );
		result.setTitle( discussionTopic.getTitle() );
		result.setContent( StringUtils.stripHTML( discussionTopic.getMessage() ) );
		result.setCreator( creator );
		result.setCreated( createdDate );

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

	/**
	 * Creates a {@link Post} from a discussion topic entry.
	 * 
	 * @param connector
	 *            Used connector.
	 * @param endpoint
	 *            Name of the discussion topics endpoint.
	 * @param entry
	 *            Entry to convert
	 * @param container
	 *            The parent {@link Container}.
	 * @param parentPost
	 *            The parent {@link Post} (can be null).
	 * @return A {@link Post} converted from these data.
	 * 
	 * @throws NotFoundException
	 *             Thrown if no resource was found at the URI
	 * @throws AuthenticationException
	 *             Thrown if there is a problem with authentication.
	 * @throws IOException
	 *             Thrown if there ist problem in communication.
	 */
	public static Post createSiocPost(
	        final CanvasLmsConnector connector,
	        final String endpoint,
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

		long endpointId;
		try {
			endpointId = Long.parseLong( containerParent.getId() );
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
		        endpoint,
		        endpointId,
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

	/**
	 * Creates an URI for an {@link UserAccount}.
	 * 
	 * @param rootUri
	 *            URI of the Canvas instance
	 * @param userId
	 *            The user id.
	 * @return The created URI
	 */
	public static URI createUserUri( final URI rootUri, final long userId ) {
		return Builder.createURI(
		        UriTemplate.fromTemplate( rootUri + TEMPLATE_USER_URI )
		                .set( TEMPLATE_VAR_USER_ID, userId )
		                .expand() );
	}

	/**
	 * Creates an URI for a {@link Forum} of a course.
	 * 
	 * @param rootUri
	 *            URI of the Canvas instance
	 * @param courseId
	 *            The course id.
	 * @return The created URI.
	 */
	public static URI createCourseUri( final URI rootUri, final long courseId ) {
		return Builder.createURI(
		        UriTemplate.fromTemplate( rootUri + TEMPLATE_COURSE_URI )
		                .set( TEMPLATE_VAR_COURSE_ID, courseId )
		                .expand() );
	}

	/**
	 * Creates an URI for a {@link Forum} of a group.
	 * 
	 * @param rootUri
	 *            URI of the Canvas instance
	 * @param groupid
	 *            The group id.
	 * @return The created URI.
	 */
	public static URI createGroupUri( URI rootUri, long groupid ) {
		return Builder.createURI(
		        UriTemplate.fromTemplate( rootUri + TEMPLATE_GROUP_URI )
		                .set( TEMPLATE_VAR_GROUP_ID, groupid )
		                .expand() );
	}

	/**
	 * Creates the URI for a Thread of a discussion topic.
	 * 
	 * @param rootUri
	 *            URI of the Canvas instance
	 * @param endpoint
	 *            Name of the endpoint.
	 * @param endpointId
	 *            The endpoint id.
	 * @param discussionId
	 *            The discussion topic id-
	 * @return The created URI.
	 */
	public static URI createDiscussionTopicUri(
	        final URI rootUri,
	        final String endpoint,
	        final long endpointId,
	        final long discussionId ) {
		return Builder.createURI(
		        UriTemplate.fromTemplate(
		                rootUri + TEMPLATE_DISCUSSION_TOPIC_URI )
		                .set( TEMPLATE_VAR_ENDPOINT, endpoint )
		                .set( TEMPLATE_VAR_ENDPOINT_ID, endpointId )
		                .set( TEMPLATE_VAR_DISCUSSION_TOPIC_ID, discussionId )
		                .expand() );
	}

	/**
	 * Creates the URI for a {@link Post} of a discussion topic inital entry.
	 * 
	 * @param rootUri
	 *            URI of the Canvas instance.
	 * @param endpoint
	 *            Name of the endpoint.
	 * @param endpointId
	 *            The endpoint ID.
	 * @param discussionId
	 *            The discussionTopic.
	 * @return The created URI.
	 */
	public static URI createInitialEntryUri(
	        final URI rootUri,
	        final String endpoint,
	        final long endpointId,
	        final long discussionId ) {
		return Builder.createURI(
		        UriTemplate.fromTemplate( rootUri + TEMPLATE_INITIAL_ENTRY_URI )
		                .set( TEMPLATE_VAR_ENDPOINT, endpoint )
		                .set( TEMPLATE_VAR_ENDPOINT_ID, endpointId )
		                .set( TEMPLATE_VAR_DISCUSSION_TOPIC_ID, discussionId )
		                .expand() );
	}

	/**
	 * Creates the URI of a {@link Post} from a discussion topic entry.
	 * 
	 * @param rootUri
	 *            URI of the Canvas instance,
	 * @param endpoint
	 *            Name of the endpoint.
	 * @param endpointId
	 *            The endpoint id.
	 * @param discussionId
	 *            The discussion topic id.
	 * @param entryId
	 *            The entry id.
	 * @return The created URI.
	 */
	public static URI createEntryUri(
	        final URI rootUri,
	        final String endpoint,
	        final long endpointId,
	        final long discussionId,
	        final long entryId ) {
		return Builder.createURI(
		        UriTemplate.fromTemplate( rootUri + TEMPLATE_ENTRY_URI )
		                .set( TEMPLATE_VAR_ENDPOINT, endpoint )
		                .set( TEMPLATE_VAR_ENDPOINT_ID, endpointId )
		                .set( TEMPLATE_VAR_DISCUSSION_TOPIC_ID, discussionId )
		                .set( TEMPLATE_VAR_ENTRY_ID, entryId )
		                .expand() );
	}

	/**
	 * Tests if the URI is one of an user.
	 * 
	 * @param uri
	 *            URI to test.
	 * @param rootUri
	 *            URI of the Canvas instance
	 * @return <code>true</code> if the URI is a user, <code>false</code>
	 *         otherwise.
	 */
	public static boolean isUserUri( final URI uri, final URI rootUri ) {
		Pattern pattern = Pattern.compile( "^" + rootUri + REGEX_USER_URI );
		Matcher matcher = pattern.matcher( uri.toString() );

		return matcher.matches();
	}

	/**
	 * Test if the URI is one of a course.
	 * 
	 * @param uri
	 *            URI to test.
	 * @param rootUri
	 *            URI of the Canvas instance
	 * @return <code>true</code> if the URI is a course, <code>false</code>
	 *         otherwise.
	 */
	public static boolean isCourseUri( final URI uri, final URI rootUri ) {
		Pattern pattern = Pattern.compile( "^" + rootUri + REGEX_COURSE_URI
		        + REGEX_DISCUSSION_TOPIC_PATH );
		Matcher matcher = pattern.matcher( uri.toString() );

		return matcher.matches();
	}

	/**
	 * Test if the URI is one of a course.
	 * 
	 * @param uri
	 *            URI to test.
	 * @param rootUri
	 *            URI of the Canvas instance
	 * @return <code>true</code> if the URI is a course, <code>false</code>
	 *         otherwise.
	 */
	public static boolean isGroupUri( final URI uri, final URI rootUri ) {
		Pattern pattern = Pattern.compile( "^" + rootUri + REGEX_GROUP_URI
		        + REGEX_DISCUSSION_TOPIC_PATH );
		Matcher matcher = pattern.matcher( uri.toString() );

		return matcher.matches();
	}

	/**
	 * Test if the URI is one of a discussion topic.
	 * 
	 * @param uri
	 *            Uri to test.
	 * @param rootUri
	 *            URI of the Canvas instance
	 * @return <code>true</code> if the URI is a discussion topic,
	 *         <code>false</code> otherwise.
	 */
	public static boolean isDiscussionTopicUri( final URI uri, final URI rootUri ) {
		Pattern pattern = Pattern.compile( "^" + rootUri + REGEX_DISCUSSION_TOPIC_URI );
		Matcher matcher = pattern.matcher( uri.toString() );

		return matcher.matches();
	}

	/**
	 * Test if the URI is one of a discussion topic initial entry.
	 * 
	 * @param uri
	 *            URI to test.
	 * @param rootUri
	 *            URI of the Canvas instance
	 * @return <code>true</code> if the URI is a discussion topic initial entry,
	 *         <code>false</code> otherwise.
	 */
	public static boolean isInitialEntryUri( final URI uri, final URI rootUri ) {
		Pattern pattern = Pattern.compile( "^" + rootUri + REGEX_INITIAL_ENTRY_URI );
		Matcher matcher = pattern.matcher( uri.toString() );

		return matcher.matches();
	}

	/**
	 * Test if the URI is one of a discussion topic entry.
	 * 
	 * @param uri
	 *            URI to test.
	 * @param rootUri
	 *            URI of the Canvas instance
	 * @return <code>true</code> if the URI is a discussion topic entry,
	 *         <code>false</code> otherwise.
	 */
	public static boolean isEntryUri( final URI uri, final URI rootUri ) {
		Pattern pattern = Pattern.compile( "^" + rootUri + REGEX_ENTRY_URI );
		Matcher matcher = pattern.matcher( uri.toString() );

		return matcher.matches();
	}
}
