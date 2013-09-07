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
        DefaultConnectorIOComponent<FacebookConnector> implements
        IPostWriter<FacebookConnector> {
	public FacebookPostWriter( FacebookConnector connector ) {
		super( connector );
	}

	@Override
	public void writePost( URI targetUri, String rdfString, Syntax syntax )
	        throws NotFoundException,
	        AuthenticationException,
	        IOException {

		if ( Forum.hasInstance( getModel(), targetUri ) ) {
			writePostToContainer( rdfString, syntax, Forum.getInstance(
			        getModel(), targetUri ) );
		} else if ( Post.hasInstance( getModel(), targetUri ) ) {
			writeReplyToPost( rdfString, syntax, Post.getInstance( getModel(),
			        targetUri ) );
		} else {
			Pattern pattern = Pattern
			        .compile( FacebookSiocUtils.REGEX_FACEBOOK_URI );
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
					if ( FacebookSiocUtils.hasConnection( object,
					        Connections.FEED ) ) {
						Container container = FacebookSiocUtils
						        .createSiocForum(
						                getConnector(),
						                object );
						writePostToContainer( rdfString, syntax, container );

					} else if ( FacebookSiocUtils.hasConnection( object,
					        Connections.COMMENTS ) ) {
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

	private void writePostToContainer( String rdfString, Syntax syntax,
	        Container container )
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
				if ( PostWriterUtils.hasContentWatermark( getConnector(), post.getContent() ) ) {
					continue;
				}

				UserAccount creatorAccount = post.getCreator();
				Person creatorPerson = PostWriterUtils
				        .getPersonOfCreatorOrNull(
				                getConnector(),
				                creatorAccount );

				FacebookClientWrapper client = null;
				String content = Strings.nullToEmpty( post.getContent() );
				if ( null != creatorPerson ) {
					UserAccount serviceAccount = PostWriterUtils
					        .getServiceAccountOfPersonOrNull(
					                getConnector(),
					                creatorPerson,
					                getServiceEndpoint() );
					if ( null != serviceAccount ) {
						try {
							client = getConnector().getClientManager().get( serviceAccount );
						} catch ( Exception e ) {
							client = getConnector().getClientManager().getDefaultClient();
							content = PostWriterUtils.formatUnknownMessage(
							        getConnector(),
							        post );
						}
					}
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

				// add watermark for 'already forwarded' check
				content = PostWriterUtils.addContentWatermark( getConnector(), content );

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
					Post resultPost = getConnector().getPostReader().readPost(
					        FacebookSiocUtils.createSiocUri(
					                result.getId() ) );
					resultPost.setSibling( post );
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

				// skip all posts that are already forwarded from this site
				if ( PostWriterUtils.hasContentWatermark( getConnector(), post.getContent() ) ) {
					continue;
				}

				UserAccount creatorAccount = reply.getCreator();
				Person creatorPerson = PostWriterUtils
				        .getPersonOfCreatorOrNull(
				                getConnector(),
				                creatorAccount );

				FacebookClientWrapper client = null;
				String content = Strings.nullToEmpty( reply.getContent() );
				if ( null != creatorPerson ) {
					UserAccount serviceAccount = PostWriterUtils
					        .getServiceAccountOfPersonOrNull(
					                getConnector(),
					                creatorPerson,
					                getServiceEndpoint() );
					if ( null != serviceAccount ) {
						try {
							client = getConnector().getClientManager().get( serviceAccount );
						} catch ( Exception e ) {
							client = getConnector().getClientManager().getDefaultClient();
							content = PostWriterUtils.formatUnknownMessage(
							        getConnector(),
							        post );
						}
					}
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

				// add watermark for 'already forwarded' check
				content = PostWriterUtils.addContentWatermark( getConnector(), content );

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
					Post resultPost = getConnector().getPostReader().readPost(
					        FacebookSiocUtils.createSiocUri(
					                result.getId() ) );

					resultPost.setSibling( post );
				}
			}
		} finally {
			postIter.close();
			tmpModel.close();
		}
	}
}
