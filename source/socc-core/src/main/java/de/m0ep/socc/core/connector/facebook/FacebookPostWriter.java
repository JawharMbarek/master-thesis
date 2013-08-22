package de.m0ep.socc.core.connector.facebook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.Thing;
import org.rdfs.sioc.UserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.restfb.Parameter;
import com.restfb.exception.FacebookException;
import com.restfb.json.JsonObject;
import com.restfb.types.FacebookType;
import com.xmlns.foaf.Person;

import de.m0ep.socc.core.connector.DefaultConnectorIOComponent;
import de.m0ep.socc.core.connector.IConnector.IPostWriter;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.utils.PostWriterUtils;
import de.m0ep.socc.core.utils.RdfUtils;
import de.m0ep.socc.core.utils.SiocUtils;

public class FacebookPostWriter extends
        DefaultConnectorIOComponent<FacebookConnector> implements IPostWriter<FacebookConnector> {
	private static final Logger LOG = LoggerFactory.getLogger(
	        FacebookPostWriter.class );

	public FacebookPostWriter( FacebookConnector connector ) {
		super( connector );
	}

	@Override
	public boolean canPostTo( Container container ) {
		return null != container
		        && RdfUtils.isType( container, Forum.RDFS_CLASS )
		        && SiocUtils.isContainerOfSite( container, getServiceEndpoint() );
	}

	@Override
	public void writePost( Post post, Container container )
	        throws AuthenticationException,
	        IOException {
		Preconditions.checkNotNull( post,
		        "Required parameter post must be specified." );
		Preconditions.checkArgument( post.hasCreator(),
		        "The parameter post has no creator." );
		Preconditions.checkArgument( post.hasContent(),
		        "The parameter post has no content." );

		Preconditions.checkNotNull( container,
		        "Required parameter container must be specified." );
		Preconditions.checkArgument( canPostTo( container ),
		        "Can't write a post to this container at this service." );
		Preconditions.checkArgument( container.hasId(),
		        "The parameter container has no id." );

		UserAccount creatorAccount = post.getCreator();
		Person creatorPerson = PostWriterUtils.getPersonOfCreatorOrNull(
		        getConnector(),
		        creatorAccount );

		FacebookClientWrapper client = null;
		if ( null != creatorPerson ) {
			UserAccount serviceAccount = PostWriterUtils
			        .getServiceAccountOfPersonOrNull(
			                getConnector(),
			                creatorPerson,
			                getServiceEndpoint() );
			if ( null != serviceAccount ) {
				client = (FacebookClientWrapper) PostWriterUtils
				        .getClientOfServiceAccountOrNull(
				                getConnector(),
				                serviceAccount );
			}
		}

		String content = Strings.nullToEmpty( post.getContent() );
		if ( null == client ) { // No client found, get default one an adapt
			                    // message content
			client = getConnector().getServiceClientManager()
			        .getDefaultClient();
			content = PostWriterUtils.createContentOfUnknownAccount(
			        post,
			        creatorAccount,
			        creatorPerson );
		}

		ClosableIterator<Thing> attachIter = post.getAllAttachments();
		try {
			while ( attachIter.hasNext() ) {
				Thing thing = attachIter.next();
				content += "\n" + thing.toString();
			}

		} finally {
			attachIter.close();
		}

		// create Facebook Graph API publish parameter
		List<Parameter> params = new ArrayList<Parameter>();
		params.add( Parameter.with( FacebookApiConstants.FIELD_MESSAGE, content ) );

		if ( post.hasAttachments() ) {
			params.add(
			        Parameter.with(
			                FacebookApiConstants.FIELD_LINK,
			                post.getAttachment().toString() ) );
		}

		FacebookType result = null;
		try {
			result = client.getClient().publish(
			        container.getId()
			                + "/"
			                + FacebookApiConstants.CONNECTION_FEED,
			        FacebookType.class,
			        params.toArray( new Parameter[params.size()] ) );
		} catch ( FacebookException e ) {
			FacebookConnector.handleFacebookException( e );
		}

		if ( null != result && null != result.getId() ) {
			LOG.debug( "Successfully writen post {} to container {}.", post,
			        container );
			JsonObject object = null;
			try {
				object = client.getClient().fetchObject(
				        result.getId(),
				        JsonObject.class );
			} catch ( FacebookException e ) {
				FacebookConnector.handleFacebookException( e );
			}

			if ( null != object ) {
				Post addedPost = FacebookSiocConverter.createSiocPost(
				        getConnector(),
				        object,
				        container,
				        null );

				addedPost.setSibling( post );
				post.addSibling( addedPost );
			}
		}
	}

	@Override
	public boolean canReplyTo( Post post ) {
		return null != post
		        && post.hasContainer()
		        && canPostTo( post.getContainer() );
	}

	@Override
	public void writeReply( Post replyPost, Post parentPost )
	        throws AuthenticationException,
	        IOException {
		Preconditions.checkNotNull( replyPost,
		        "Required parameter replyPost must be specified." );
		Preconditions.checkArgument( replyPost.hasCreator(),
		        "The parameter replyPost has no creator." );
		Preconditions.checkArgument( replyPost.hasContent(),
		        "The parameter replyPost has no content." );

		Preconditions.checkNotNull( parentPost,
		        "Required parameter parentPost must be specified." );
		Preconditions.checkArgument( canReplyTo( parentPost ),
		        "Can't write a reply to this parenPost at this service." );
		Preconditions.checkArgument( parentPost.hasId(),
		        "The parameter parentPost has no id." );

		UserAccount creatorAccount = replyPost.getCreator();
		Person creatorPerson = PostWriterUtils.getPersonOfCreatorOrNull(
		        getConnector(),
		        creatorAccount );

		FacebookClientWrapper client = null;
		if ( null != creatorPerson ) {
			UserAccount serviceAccount = PostWriterUtils
			        .getServiceAccountOfPersonOrNull(
			                getConnector(),
			                creatorPerson,
			                getServiceEndpoint() );
			if ( null != serviceAccount ) {
				client = (FacebookClientWrapper) PostWriterUtils
				        .getClientOfServiceAccountOrNull(
				                getConnector(),
				                serviceAccount );
			}
		}

		String content = Strings.nullToEmpty( replyPost.getContent() );
		if ( null == client ) { // No client found, get default one an adapt
			                    // message content
			client = getConnector().getServiceClientManager()
			        .getDefaultClient();
			content = PostWriterUtils.createContentOfUnknownAccount(
			        replyPost,
			        creatorAccount,
			        creatorPerson );
		}

		// can only send a message -> add attachments to the content.
		ClosableIterator<Thing> attachIter = replyPost.getAllAttachments();
		try {
			while ( attachIter.hasNext() ) {
				Thing thing = attachIter.next();
				content += "\n" + thing.toString();
			}

		} finally {
			attachIter.close();
		}

		FacebookType fbResult = null;
		try {
			fbResult = client.getClient()
			        .publish( parentPost.getId()
			                + "/"
			                + FacebookApiConstants.CONNECTION_COMMENTS,
			                FacebookType.class,
			                Parameter.with(
			                        FacebookApiConstants.FIELD_MESSAGE,
			                        content ) );
		} catch ( FacebookException e ) {
			FacebookConnector.handleFacebookException( e );
		}

		if ( null != fbResult && null != fbResult.getId() ) {
			LOG.debug( "Successfully writen reply {} to post {}.",
			        replyPost,
			        parentPost );

			JsonObject object = null;
			try {
				object = client.getClient().fetchObject(
				        fbResult.getId(),
				        JsonObject.class );
			} catch ( FacebookException e ) {
				FacebookConnector.handleFacebookException( e );
			}
			if ( null != object ) {
				Post addedPost = FacebookSiocConverter.createSiocPost(
				        getConnector(),
				        object,
				        parentPost.getContainer(),
				        parentPost );

				addedPost.setSibling( replyPost );
				replyPost.addSibling( addedPost );
			}
		}
	}
}
