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

import com.google.common.base.Strings;
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
import de.m0ep.socc.core.utils.SoccUtils;
import de.m0ep.socc.core.utils.UserAccountUtils;

public class FacebookPostWriter extends
        DefaultConnectorIOComponent<FacebookConnector> implements
        IPostWriter<FacebookConnector> {
	private static final Logger LOG = LoggerFactory.getLogger( FacebookPostWriter.class );

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

		if ( Forum.hasInstance( getModel(), targetUri ) ) {
			writePostToContainer( Forum.getInstance(
			        getModel(), targetUri ), rdfString, syntax );
		} else if ( Post.hasInstance( getModel(), targetUri ) ) {
			writeReplyToPost( Post.getInstance( getModel(),
			        targetUri ), rdfString, syntax );
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

					object = defaultClient
					        .getFacebookClient()
					        .fetchObject(
					                "/" + id,
					                JsonObject.class,
					                Parameter.with( RequestParameters.METADATA,
					                        1 ) );
				} catch ( FacebookException e ) {
					FacebookConnector.handleFacebookException( e );
				} catch ( Exception e ) {
					Throwables.propagate( e );
				}

				if ( null != object ) {
					if ( FacebookSiocUtils.hasConnection( object, Connections.FEED ) ) {
						Container container = FacebookSiocUtils
						        .createSiocForum(
						                getConnector(),
						                object );
						writePostToContainer( container, rdfString, syntax );

					} else if ( FacebookSiocUtils.hasConnection( object, Connections.COMMENTS ) ) {
						Post post = FacebookSiocUtils.createSiocPost(
						        getConnector(),
						        object,
						        null,
						        null );
						writeReplyToPost( post, rdfString, syntax );
					}
				}
			} else {
				throw new IOException(
				        "Failed to write post to target uri "
				                + targetUri
				                + ", it's neither a conainer nor a post od comment" );
			}
		}
	}

	private void writePostToContainer(
	        final Container targetContainer,
	        final String rdfString,
	        final Syntax syntax )
	        throws AuthenticationException,
	        NotFoundException,
	        IOException {
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

				UserAccount creatorAccount = UserAccount.getInstance(
				        getModel(),
				        post.getCreator().getResource() );
				FacebookClientWrapper client = null;
				String content = Strings.nullToEmpty( post.getContent() );
				if ( null != creatorAccount ) {
					try {
						UserAccount serviceAccount = UserAccountUtils.findUserAccountOfService(
						        getModel(),
						        creatorAccount,
						        getConnector().getService() );

						client = getConnector().getClientManager().get( serviceAccount );
					} catch ( Exception e ) {
						LOG.debug( "No client found for UserAccount {}: exception -> {}\n{}",
						        creatorAccount,
						        e.getMessage(),
						        Throwables.getStackTraceAsString( e ) );
						client = null;
					}
				}

				if ( null == client ) {
					LOG.debug( "Using default client" );
					client = getConnector().getClientManager().getDefaultClient();
					content = PostWriterUtils.formatUnknownMessage(
					        getConnector(),
					        post );
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

				if ( !SoccUtils.hasAnyContentWatermark( content ) ) {
					// add watermark for 'already forwarded' check
					content = SoccUtils.addContentWatermark( post.getIsPartOf(), content );
				}

				// create Facebook Graph API publish parameter
				List<Parameter> params = new ArrayList<Parameter>();
				params.add( Parameter.with( Fields.MESSAGE, content ) );

				if ( post.hasAttachments() ) {
					params.add(
					        Parameter.with(
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
					        FacebookSiocUtils.createSiocUri(
					                result.getId() ) );
					resultPost.setSibling( post );
					return;
				}
			}
		} finally {
			postIter.close();
			tmpModel.close();
		}

		LOG.warn( "Failed to write post(s) to uri " + targetContainer );
	}

	private void writeReplyToPost(
	        final Post targetPost,
	        final String rdfString,
	        final Syntax syntax )
	        throws AuthenticationException,
	        NotFoundException,
	        IOException {
		Model tmpModel = RDFTool.stringToModel( rdfString, syntax );
		ClosableIterator<Resource> postIter = Post.getAllInstances( tmpModel );
		try {
			while ( postIter.hasNext() ) {
				Resource resource = postIter.next();
				Post reply = Post.getInstance( tmpModel, resource );

				// skip all posts that are already forwarded from this site
				if ( SoccUtils.hasContentWatermark(
				        getConnector().getStructureReader().getSite(),
				        targetPost.getContent() ) ) {
					continue;
				}

				UserAccount creatorAccount = UserAccount.getInstance(
				        getModel(),
				        targetPost.getCreator().getResource() );
				FacebookClientWrapper client = null;
				String content = Strings.nullToEmpty( reply.getContent() );
				if ( null != creatorAccount ) {
					try {
						UserAccount serviceAccount = UserAccountUtils.findUserAccountOfService(
						        getModel(),
						        creatorAccount,
						        getConnector().getService() );

						client = getConnector().getClientManager().get( serviceAccount );
					} catch ( Exception e ) {
						LOG.debug( "No client found for UserAccount {}: exception -> {}\n{}",
						        creatorAccount,
						        e.getMessage(),
						        Throwables.getStackTraceAsString( e ) );
						client = null;
					}
				}

				if ( null == client ) {
					LOG.debug( "Using default client" );
					client = getConnector().getClientManager().getDefaultClient();
					content = PostWriterUtils.formatUnknownMessage(
					        getConnector(),
					        targetPost );
				}

				// can only send a message -> add attachments to the content.
				ClosableIterator<Thing> attachIter = reply.getAllAttachments();
				try {
					while ( attachIter.hasNext() ) {
						Thing thing = attachIter.next();
						content += "\n" + thing.toString();
					}

				} finally {
					attachIter.close();
				}

				if ( !SoccUtils.hasAnyContentWatermark( content ) ) {
					// add watermark for 'already forwarded' check
					content = SoccUtils.addContentWatermark( targetPost.getIsPartOf(), content );
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
					return;
				}
			}
		} finally {
			postIter.close();
			tmpModel.close();
		}

		LOG.warn( "Failed to write post(s) to uri " + targetPost );
	}
}
