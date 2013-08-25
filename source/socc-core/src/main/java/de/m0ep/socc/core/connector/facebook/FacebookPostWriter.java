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
import com.xmlns.foaf.Person;

import de.m0ep.socc.core.connector.DefaultConnectorIOComponent;
import de.m0ep.socc.core.connector.IConnector.IPostWriter;
import de.m0ep.socc.core.connector.facebook.FacebookSiocUtils.Connections;
import de.m0ep.socc.core.connector.facebook.FacebookSiocUtils.Fields;
import de.m0ep.socc.core.connector.facebook.FacebookSiocUtils.RequestParameters;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.exceptions.NotFoundException;
import de.m0ep.socc.core.utils.PostWriterUtils;

public class FacebookPostWriter extends
        DefaultConnectorIOComponent<FacebookConnector> implements IPostWriter<FacebookConnector> {
	private static final Logger LOG = LoggerFactory.getLogger(
	        FacebookPostWriter.class );

	public FacebookPostWriter( FacebookConnector connector ) {
		super( connector );
	}

	@Override
	public void writePost( URI targetUri, String rdfString, Syntax syntax )
	        throws NotFoundException,
	        AuthenticationException,
	        IOException {

		if ( Forum.hasInstance( getModel(), targetUri ) ) {
			writePostToContainer( rdfString, syntax, Forum.getInstance( getModel(), targetUri ) );
		} else if ( Post.hasInstance( getModel(), targetUri ) ) {
			writeReplyToPost( rdfString, syntax, Post.getInstance( getModel(), targetUri ) );
		} else {
			Pattern pattern = Pattern.compile( FacebookSiocUtils.REGEX_FACEBOOK_URI );
			Matcher matcher = pattern.matcher( targetUri.toString() );

			if ( matcher.find() ) {
				String id = matcher.group( 1 );
				JsonObject object = null;

				try {
					FacebookClientWrapper defaultClient = getConnector().getClientManager()
					        .getDefaultClient();
					object = defaultClient
					        .getFacebookClient()
					        .fetchObject(
					                "/" + id,
					                JsonObject.class,
					                Parameter.with( RequestParameters.METADATA, 1 ) );
				} catch ( FacebookException e ) {
					FacebookConnector.handleFacebookException( e );
				} catch ( Exception e ) {
					Throwables.propagate( e );
				}

				if ( null != object ) {
					if ( FacebookSiocUtils.hasConnection( object, Connections.FEED ) ) {
						Container container = FacebookSiocUtils.createSiocForum(
						        getConnector(),
						        object );
						writePostToContainer( rdfString, syntax, container );

					} else if ( FacebookSiocUtils.hasConnection( object, Connections.COMMENTS ) ) {
						Post post = FacebookSiocUtils.createSiocPost(
						        getConnector(),
						        object,
						        null,
						        null );
						writeReplyToPost( rdfString, syntax, post );
					}
				}
			} else {
				throw new IOException(
				        "Can't write post to target uri "
				                + targetUri
				                + " at "
				                + getServiceEndpoint() );
			}
		}
	}

	private void writePostToContainer( String rdfString, Syntax syntax, Container container )
	        throws AuthenticationException,
	        NotFoundException,
	        IOException {
		Model tmpModel = RDFTool.stringToModel( rdfString, syntax );
		ClosableIterator<Resource> postIter = Post.getAllInstances( tmpModel );
		try {
			while ( postIter.hasNext() ) {
				Resource resource = postIter.next();
				Post post = Post.getInstance( tmpModel, resource );

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
					client = getConnector().getClientManager()
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
					        container.getId()
					                + "/"
					                + Connections.FEED,
					        FacebookType.class,
					        params.toArray( new Parameter[params.size()] ) );
				} catch ( FacebookException e ) {
					FacebookConnector.handleFacebookException( e );
				}

				if ( null != result && null != result.getId() ) {
					LOG.debug( "Successfully writen post {} to container {} with id {}.",
					        post,
					        container,
					        result.getId() );
				} else {
					LOG.debug( "Failed to writen post {} to container {}.",
					        post,
					        container );
				}
			}
		} finally {
			postIter.close();
			tmpModel.close();
		}
	}

	private void writeReplyToPost( String rdfString, Syntax syntax, Post post )
	        throws AuthenticationException,
	        NotFoundException,
	        IOException {
		Model tmpModel = RDFTool.stringToModel( rdfString, syntax );
		ClosableIterator<Resource> postIter = Post.getAllInstances( tmpModel );
		try {
			while ( postIter.hasNext() ) {
				Resource resource = postIter.next();
				Post reply = Post.getInstance( tmpModel, resource );

				UserAccount creatorAccount = reply.getCreator();
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

				String content = Strings.nullToEmpty( reply.getContent() );
				if ( null == client ) { // No client found, get default one an adapt
					                    // message content
					client = getConnector().getClientManager()
					        .getDefaultClient();
					content = PostWriterUtils.createContentOfUnknownAccount(
					        reply,
					        creatorAccount,
					        creatorPerson );
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

				FacebookType result = null;
				try {
					result = client.getFacebookClient()
					        .publish( post.getId()
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
					LOG.debug( "Successfully writen reply {} to post {} with id {}.",
					        reply,
					        post,
					        result.getId() );
				} else {
					LOG.debug( "Failed to write reply {} to post {}.",
					        reply,
					        post );
				}
			}
		} finally {
			postIter.close();
			tmpModel.close();
		}
	}
}
