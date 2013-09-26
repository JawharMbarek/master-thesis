package de.m0ep.socc.core.connector.facebook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
			throw new IOException( "Failed to write post to target uri "
			        + targetUri
			        + ", it's neither a conainer nor a post od comment" );
		}

		Model tmpModel = RDFTool.stringToModel( rdfString, syntax );
		ClosableIterator<Resource> postIter = Post.getAllInstances( tmpModel );
		try {
			while ( postIter.hasNext() ) {
				Resource resource = postIter.next();
				Post post = Post.getInstance( tmpModel, resource );

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
			content = SoccUtils.addContentWatermark( post.getIsPartOf(),
			        content );
		}

		// create Facebook Graph API publish parameter
		List<Parameter> params = new ArrayList<Parameter>();
		params.add( Parameter.with( Fields.MESSAGE, content ) );

		if ( post.hasAttachments() ) {
			params.add( Parameter.with(
			        Fields.LINK,
			        post.getAttachment().toString() ) );
		}

		FacebookType result = null;
		try {
			result = client.getFacebookClient().publish(
			        targetContainer.getId()
			                + "/"
			                + Connections.FEED,
			        FacebookType.class,
			        params.toArray( new Parameter[params.size()] ) );
		} catch ( FacebookException e ) {
			FacebookConnector.handleFacebookException( e );
		}

		if ( null != result && null != result.getId() ) {
			Post resultPost = getConnector().getPostReader().getPost(
			        FacebookSiocUtils.createSiocUri( result.getId() ) );
			resultPost.setSibling( post );
			return;
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
			        targetPost );
		}

		// Add Attachments to message content
		content = SoccUtils.addAttachmentsToContent( post, content, "\n" );

		if ( !SoccUtils.hasAnyContentWatermark( content ) ) {
			// add watermark for 'already forwarded' check
			content = SoccUtils.addContentWatermark( targetPost
			        .getIsPartOf(), content );
		}

		FacebookType result = null;
		try {
			result = client.getFacebookClient()
			        .publish( targetPost.getId()
			                + "/"
			                + Connections.COMMENTS,
			                FacebookType.class,
			                Parameter.with(
			                        Fields.MESSAGE,
			                        content ) );
		} catch ( FacebookException e ) {
			FacebookConnector.handleFacebookException( e );
		}

		if ( null != result && null != result.getId() ) {
			Post resultPost = getConnector().getPostReader().getPost(
			        FacebookSiocUtils.createSiocUri(
			                result.getId() ) );

			resultPost.setSibling( targetPost );
		}
	}
}
