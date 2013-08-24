package de.m0ep.socc.core.connector.canvaslms;

import java.io.IOException;

import org.rdfs.sioc.Container;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.Thread;
import org.rdfs.sioc.UserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.xmlns.foaf.Person;

import de.m0ep.canvas.CanvasLmsClient;
import de.m0ep.canvas.exceptions.AuthorizationException;
import de.m0ep.canvas.exceptions.CanvasLmsException;
import de.m0ep.canvas.exceptions.NetworkException;
import de.m0ep.canvas.model.Entry;
import de.m0ep.socc.core.connector.DefaultConnectorIOComponent;
import de.m0ep.socc.core.connector.IConnector.IPostWriter;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.utils.PostWriterUtils;
import de.m0ep.socc.core.utils.RdfUtils;
import de.m0ep.socc.core.utils.SiocUtils;

public class CanvasLmsPostWriter extends
        DefaultConnectorIOComponent<CanvasLmsConnector> implements
        IPostWriter<CanvasLmsConnector> {
	private static final Logger LOG = LoggerFactory
	        .getLogger( CanvasLmsPostWriter.class );

	public CanvasLmsPostWriter( final CanvasLmsConnector connector ) {
		super( connector );
	}

	@Override
	public boolean canPostTo( Container container ) {
		return null != container
		        && RdfUtils.isType(
		                container.getModel(),
		                container,
		                Thread.RDFS_CLASS )
		        && SiocUtils.isContainerOfSite(
		                container,
		                getServiceEndpoint() );
	}

	@Override
	public void writePost( Post post, Container container )
	        throws AuthenticationException,
	        IOException {
		Preconditions.checkNotNull( container,
		        "Required parameter container must be specified." );
		Preconditions.checkArgument( canPostTo( container ),
		        "Can't write Post to this container at this service." );
		Preconditions.checkArgument( container.hasId(),
		        "The container has no id." );
		Preconditions.checkArgument( container.hasParent(),
		        "The parameter container has no parent." );

		Container parentContainer = container.getParent();
		Preconditions.checkArgument( parentContainer.hasId(),
		        "The container parent has no id." );

		long courseId;
		try {
			courseId = Long.parseLong( parentContainer.getId() );
		} catch ( NumberFormatException e ) {
			throw new IllegalArgumentException(
			        "The id of the containers parent is invalid: was "
			                + parentContainer.getId() );
		}

		long discussionId;
		try {
			discussionId = Long.parseLong( container.getId() );
		} catch ( NumberFormatException e ) {
			throw new IllegalArgumentException(
			        "The id of the container is invalid: was "
			                + container.getId() );
		}

		UserAccount creatorAccount = post.getCreator();
		Person creatorPerson = PostWriterUtils.getPersonOfCreatorOrNull(
		        getConnector(),
		        creatorAccount );

		CanvasLmsClient client = null;
		if ( null != creatorPerson ) {
			UserAccount serviceAccount = PostWriterUtils
			        .getServiceAccountOfPersonOrNull(
			                getConnector(),
			                creatorPerson,
			                getServiceEndpoint() );
			if ( null != serviceAccount ) {
				client = (CanvasLmsClient) PostWriterUtils
				        .getClientOfServiceAccountOrNull(
				                getConnector(),
				                serviceAccount );
			}
		}

		String content = post.getContent();
		if ( null == client ) { // No client found, get default one an adapt
			                    // message content
			client = getConnector().getServiceClientManager()
			        .getDefaultClient();
			content = PostWriterUtils.createContentOfUnknownAccount(
			        post,
			        creatorAccount,
			        creatorPerson );
		}

		Entry resultEntry = null;
		try {
			resultEntry = client.courses()
			        .discussionTopics( courseId )
			        .entries( discussionId )
			        .post( content )
			        .execute();
		} catch ( CanvasLmsException e ) {
			if ( e instanceof NetworkException ) {
				throw new IOException( e );
			} else if ( e instanceof AuthorizationException ) {
				throw new AuthenticationException( e );
			}

			throw Throwables.propagate( e );
		}

		if ( null != resultEntry ) {
			Post initPost = Post.getInstance(
			        getModel(),
			        CanvasLmsSiocUtils.createInitialEntryUri(
			                getServiceEndpoint(),
			                courseId,
			                discussionId ) );

			Post resultPost = CanvasLmsSiocUtils.createSiocPost(
			        getConnector(),
			        resultEntry,
			        container,
			        initPost );

			resultPost.addSibling( post );
			post.addSibling( resultPost );
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
		Preconditions.checkArgument( replyPost.hasContent(),
		        "The replyPost has no content." );
		Preconditions.checkArgument( replyPost.hasCreator(),
		        "The replyPost has no creator." );

		Preconditions.checkNotNull( parentPost,
		        "Required parameter parentPost must be specified." );
		Preconditions.checkArgument( canReplyTo( parentPost ),
		        "Can't write a reply to the parentPost at this service." );
		Preconditions.checkArgument( parentPost.hasId(),
		        "The parentPost has no id." );
		Preconditions.checkArgument( parentPost.hasContainer(),
		        "The parentPost has no container" );

		Container container = parentPost.getContainer();
		Preconditions.checkArgument( container.hasId(),
		        "The container of the parentPost has no id" );
		Preconditions.checkArgument( container.hasParent(),
		        "The container of the parentPost has no required parent" );

		Container parentContainer = container.getParent();
		Preconditions.checkArgument( parentContainer.hasId(),
		        "The parent of the parentPost container has no id." );

		long courseId;
		try {
			courseId = Long.parseLong( parentContainer.getId() );
		} catch ( NumberFormatException e ) {
			throw new IllegalArgumentException(
			        "The id of the containers parent is invalid: was "
			                + parentContainer.getId() );
		}

		long discussionId;
		try {
			discussionId = Long.parseLong( container.getId() );
		} catch ( NumberFormatException e ) {
			throw new IllegalArgumentException(
			        "The id of the container is invalid: was "
			                + container.getId() );
		}

		long entryId;
		try {
			entryId = Long.parseLong( parentPost.getId() );
		} catch ( NumberFormatException e ) {
			throw new IllegalArgumentException(
			        "The id of the parentPost is invalid: was "
			                + parentPost.getId() );
		}

		UserAccount creatorAccount = replyPost.getCreator();
		Person creatorPerson = PostWriterUtils.getPersonOfCreatorOrNull(
		        getConnector(),
		        creatorAccount );

		CanvasLmsClient client = null;
		if ( null != creatorPerson ) {
			UserAccount serviceAccount = PostWriterUtils
			        .getServiceAccountOfPersonOrNull(
			                getConnector(),
			                creatorPerson,
			                getServiceEndpoint() );
			if ( null != serviceAccount ) {
				client = (CanvasLmsClient) PostWriterUtils
				        .getClientOfServiceAccountOrNull(
				                getConnector(),
				                serviceAccount );
			}
		}

		String content = replyPost.getContent();
		if ( null == client ) { // No client found, get default one an modify
			                    // message content
			LOG.debug( "no client found  use dafault." );

			client = getConnector().getServiceClientManager()
			        .getDefaultClient();
			content = PostWriterUtils.createContentOfUnknownAccount(
			        replyPost,
			        creatorAccount,
			        creatorPerson );
		}

		Entry resultEntry = null;
		try {
			resultEntry = client.courses()
			        .discussionTopics( courseId )
			        .entries( discussionId )
			        .postReply( content, entryId )
			        .execute();
		} catch ( NetworkException e ) {
			throw new IOException( e );
		} catch ( AuthorizationException e ) {
			throw new AuthenticationException( e );
		} catch ( CanvasLmsException e ) {
			throw Throwables.propagate( e );
		}

		if ( null != resultEntry ) {
			Post initPost = Post.getInstance(
			        getModel(),
			        CanvasLmsSiocUtils.createInitialEntryUri(
			                getServiceEndpoint(),
			                courseId,
			                discussionId ) );

			Post resultPost = CanvasLmsSiocUtils.createSiocPost(
			        getConnector(),
			        resultEntry,
			        container,
			        initPost );

			resultPost.addSibling( replyPost );
			replyPost.addSibling( resultPost );
		}
	}
}
