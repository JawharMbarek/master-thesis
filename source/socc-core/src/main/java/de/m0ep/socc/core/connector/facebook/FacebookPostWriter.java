package de.m0ep.socc.core.connector.facebook;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.Syntax;
import org.ontoware.rdf2go.model.node.Resource;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.util.RDFTool;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.Thing;
import org.rdfs.sioc.UserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Throwables;
import com.restfb.Parameter;
import com.restfb.exception.FacebookException;
import com.restfb.json.JsonObject;
import com.restfb.types.FacebookType;

import de.m0ep.socc.core.connector.DefaultConnectorIOComponent;
import de.m0ep.socc.core.connector.IConnector.IPostWriter;
import de.m0ep.socc.core.connector.facebook.FacebookSiocUtils.Connections;
import de.m0ep.socc.core.connector.facebook.FacebookSiocUtils.Fields;
import de.m0ep.socc.core.connector.facebook.FacebookSiocUtils.RequestParameters;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.exceptions.NotFoundException;
import de.m0ep.socc.core.utils.PostWriterUtils;
import de.m0ep.socc.core.utils.RdfUtils;
import de.m0ep.socc.core.utils.SiocUtils;
import de.m0ep.socc.core.utils.SoccUtils;

public class FacebookPostWriter extends
        DefaultConnectorIOComponent<FacebookConnector> implements
        IPostWriter<FacebookConnector> {
	private static final Logger LOG = LoggerFactory
	        .getLogger( FacebookPostWriter.class );

	public FacebookPostWriter( final FacebookConnector connector ) {
		super( connector );
	}

	@Override
	public void writePost(
	        final URI targetUri,
	        final String rdfString,
	        final Syntax syntax )
	        throws NotFoundException,
	        AuthenticationException,
	        IOException {

		boolean targetIsPost = false;
		boolean targetIsContainer = false;
		Thing targetResource = null;
		if ( Forum.hasInstance( getModel(), targetUri ) ) {
			targetResource = getConnector().getStructureReader().getContainer( targetUri );
			targetIsContainer = true;
		} else if ( Post.hasInstance( getModel(), targetUri ) ) {
			targetResource = getConnector().getPostReader().getPost( targetUri );
			targetIsPost = true;
		} else {
			Pattern pattern = Pattern.compile( FacebookSiocUtils.REGEX_FACEBOOK_URI );
			Matcher matcher = pattern.matcher( targetUri.toString() );

			if ( matcher.find() ) {
				String id = matcher.group( 1 );
				JsonObject object = null;

				try {
					FacebookClientWrapper defaultClient = getConnector()
					        .getClientManager()
					        .getDefaultClient();

					object = defaultClient.getFacebookClient().fetchObject(
					        "/" + id,
					        JsonObject.class,
					        Parameter.with( RequestParameters.METADATA, 1 ) );
				} catch ( FacebookException e ) {
					FacebookConnector.handleFacebookException( e );
				} catch ( Exception e ) {
					Throwables.propagate( e );
				}

				if ( null != object ) {
					if ( FacebookSiocUtils.hasConnection( object,
					        Connections.FEED ) ) {
						targetResource = FacebookSiocUtils.createSiocForum(
						        getConnector(),
						        object );
						targetIsContainer = true;
					} else if ( FacebookSiocUtils.hasConnection( object, Connections.COMMENTS ) ) {
						targetResource = FacebookSiocUtils.createSiocPost(
						        getConnector(),
						        object,
						        null,
						        null );
						targetIsPost = true;
					}
				}
			}
		}

		if ( null == targetResource ) {
			throw new IOException( "Invalid URI to write post to "
			        + getServiceEndpoint()
			        + ": "
			        + targetUri );
		}

		Model tmpModel = RDFTool.stringToModel( rdfString, syntax );
		ClosableIterator<Resource> postIter = Post.getAllInstances( tmpModel );
		try {
			while ( postIter.hasNext() ) {
				Post post = Post.getInstance( tmpModel, postIter.next() );

				if ( LOG.isDebugEnabled() ) {
					LOG.debug( "Try to write Post to {}:\n{}",
					        getServiceEndpoint(),
					        RdfUtils.resourceToString(
					                post,
					                Syntax.Turtle ) );
				}

				// skip all posts that are already forwarded from this site
				if ( SoccUtils.hasContentWatermark(
				        getConnector().getStructureReader().getSite(),
				        post.getContent() ) ) {
					continue;
				}

				if ( post.hasReplyOf() ) {
					Post sibling = SoccUtils.findSibling(
					        getModel(),
					        SiocUtils.asPost( post.getReplyOf() ),
					        getConnector().getStructureReader().getSite() );

					if ( null != sibling ) {
						LOG.debug( "Write {} as reply to {}.", post, sibling );
						writeReplyToPost( sibling, post );
						return;
					} else {
						LOG.debug( "No sibling found for {}", post.getReplyOf() );
					}
				}

				if ( targetIsContainer ) {
					writePostToContainer( (Container) targetResource, post );
				} else if ( targetIsPost ) {
					writeReplyToPost( (Post) targetResource, post );
				}
			}

		} finally {
			postIter.close();
			tmpModel.close();
		}
	}

	private void writePostToContainer(
	        final Container targetContainer,
	        final Post post )
	        throws AuthenticationException,
	        NotFoundException,
	        IOException {
		UserAccount creatorAccount = PostWriterUtils.getCreatorUserAccount(
		        getConnector(),
		        post );

		FacebookClientWrapper client = PostWriterUtils.getClientOfCreator(
		        getConnector(),
		        creatorAccount );

		String content = post.getContent();

		if ( null == client ) {
			LOG.info( "Using default client" );
			client = getConnector().getClientManager()
			        .getDefaultClient();
			content = SoccUtils.formatUnknownMessage(
			        getConnector(),
			        post );
		}

		// Add Attachments to message content
		content = SoccUtils.addAttachmentsToContent( post, content, "\n" );

		if ( !SoccUtils.hasAnyContentWatermark( content ) ) {
			// add watermark for 'already forwarded' check
			content = SoccUtils.addContentWatermark( post.getIsPartOf(),
			        content );
		}

		// Add Attachments to message content
		content = SoccUtils.addAttachmentsToContent( post, content, "<br>" );

		FacebookType result = null;
		try {
			result = client.getFacebookClient().publish(
			        targetContainer.getId() + "/" + Connections.FEED,
			        FacebookType.class,
			        Parameter.with( Fields.MESSAGE, content ) );
		} catch ( FacebookException e ) {
			FacebookConnector.handleFacebookException( e );
		}

		if ( null != result && null != result.getId() ) {
			Post resultPost = getConnector().getPostReader().getPost(
			        FacebookSiocUtils.createSiocUri( result.getId() ) );

			resultPost.setSibling( post );

			if ( LOG.isDebugEnabled() ) {
				LOG.debug( "Writing a post to {} was successful:\n{}",
				        getServiceEndpoint(),
				        RdfUtils.resourceToString( resultPost, Syntax.Turtle ) );
			} else {
				LOG.info( "Writing a post to {} was successful '{}'",
				        getServiceEndpoint(),
				        resultPost );
			}
		} else {
			LOG.warn( "Failed write post to {}, result was null or has no id",
			        getServiceEndpoint() );
		}
	}

	private void writeReplyToPost(
	        final Post targetPost,
	        final Post post )
	        throws AuthenticationException,
	        NotFoundException,
	        IOException {
		UserAccount creatorAccount = PostWriterUtils.getCreatorUserAccount(
		        getConnector(),
		        post );

		FacebookClientWrapper client = PostWriterUtils.getClientOfCreator(
		        getConnector(),
		        creatorAccount );

		String content = post.getContent();
		if ( null == client ) {
			LOG.debug( "Using default client" );
			client = getConnector().getClientManager()
			        .getDefaultClient();
			content = SoccUtils.formatUnknownMessage(
			        getConnector(),
			        post );
		}

		// Add Attachments to message content
		content = SoccUtils.addAttachmentsToContent( post, content, "\n" );

		if ( !SoccUtils.hasAnyContentWatermark( content ) ) {
			// add watermark for 'already forwarded' check
			content = SoccUtils.addContentWatermark( post
			        .getIsPartOf(), content );
		}
		String result = null;
		try {
			result = client.getFacebookClient().publish(
			        targetPost.getId() + "/" + Connections.COMMENTS,
			        String.class,
			        Parameter.with( Fields.MESSAGE, content ) );
		} catch ( FacebookException e ) {
			FacebookConnector.handleFacebookException( e );
		} catch ( Exception e ) {
			Throwables.propagate( e );
		}

		if ( null != result ) {
			result = result.trim();
			String resultId = null;
			if ( result.startsWith( "{" ) ) {
				FacebookType type = null;
				try {
					type = client.getFacebookClient().getJsonMapper().toJavaObject(
					        result,
					        FacebookType.class );
				} catch ( Exception e ) {
					throw new IOException( "Failed to parse result of the writing query: " + result );
				}

				resultId = type.getId();
			} else {
				resultId = result;
			}

			Post resultPost = getConnector().getPostReader().getPost(
			        FacebookSiocUtils.createSiocUri( resultId ) );

			resultPost.setSibling( post );

			if ( LOG.isDebugEnabled() ) {
				LOG.debug( "Writing a post to {} was successful:\n{}",
				        getServiceEndpoint(),
				        RdfUtils.resourceToString( resultPost, Syntax.Turtle ) );
			} else {
				LOG.info( "Writing a post to {} was successful '{}'",
				        getServiceEndpoint(),
				        resultPost );
			}
		} else {
			LOG.warn( "Failed write post to {}, result was null",
			        getServiceEndpoint() );
		}
	}
}
