/*
 * The MIT License (MIT) Copyright © 2013 Florian Mueller
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the “Software”), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package de.m0ep.socc.core.connector.canvaslms;

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
import org.rdfs.sioc.Post;
import org.rdfs.sioc.Thing;
import org.rdfs.sioc.UserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.m0ep.canvas.CanvasLmsClient;
import de.m0ep.canvas.model.Entry;
import de.m0ep.socc.core.connector.DefaultConnectorIOComponent;
import de.m0ep.socc.core.connector.IConnector.IPostWriter;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.exceptions.NotFoundException;
import de.m0ep.socc.core.utils.PostWriterUtils;
import de.m0ep.socc.core.utils.RdfUtils;
import de.m0ep.socc.core.utils.SiocUtils;
import de.m0ep.socc.core.utils.SoccUtils;

/**
 * Class to write post to a Canvas LMS instance through a
 * {@link CanvasLmsConnector}.
 * 
 * @author Florian Mueller
 */
public class CanvasLmsPostWriter extends
        DefaultConnectorIOComponent<CanvasLmsConnector> implements
        IPostWriter<CanvasLmsConnector> {
	private static final Logger LOG = LoggerFactory
	        .getLogger( CanvasLmsPostWriter.class );

	/**
	 * Constructor to create a new instance for a {@link CanvasLmsConnector}.
	 * 
	 * @param connector
	 *            Instance of a {@link CanvasLmsConnector}.
	 */
	public CanvasLmsPostWriter( final CanvasLmsConnector connector ) {
		super( connector );
	}

	@Override
	public void writePosts( final URI targetUri, final String rdfString, final Syntax syntax )
	        throws NotFoundException,
	        AuthenticationException,
	        IOException {

		boolean isDiscussionTopicUri = CanvasLmsSiocUtils.isDiscussionTopicUri(
		        targetUri,
		        getServiceEndpoint() );

		boolean isInitialEntryUri = CanvasLmsSiocUtils.isInitialEntryUri(
		        targetUri,
		        getServiceEndpoint() );

		boolean isEntryUri = CanvasLmsSiocUtils.isEntryUri(
		        targetUri,
		        getServiceEndpoint() );

		Thing targetResource = null;
		if ( isDiscussionTopicUri || isInitialEntryUri ) {
			targetResource = getConnector().getStructureReader().getContainer(
			        targetUri );
		} else if ( isEntryUri ) {
			targetResource = getConnector().getPostReader().getPost( targetUri );
		} else {
			throw new IOException( "Invalid URI to write post to "
			        + getServiceEndpoint()
			        + ": "
			        + targetUri );
		}

		Model tmpModel = RDFTool.stringToModel( rdfString, syntax );
		ClosableIterator<Resource> postIter = Post.getAllInstances( tmpModel );
		try {
			while ( postIter.hasNext() ) {
				Resource resource = postIter.next();
				Post post = Post.getInstance( tmpModel, resource );

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
					LOG.info( "Skip this post, posted was from this site" );
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

				if ( isDiscussionTopicUri || isInitialEntryUri ) {
					writePostToContainer( (Container) targetResource, post );
				} else if ( isEntryUri ) {
					writeReplyToPost( (Post) targetResource, post );
				}
			}
		} finally {
			postIter.close();
			tmpModel.close();
		}
	}

	/**
	 * Write a {@link Post} to a Discussion Topic of a Canvas LMS Course.
	 * 
	 * @param targetUri
	 *            Target URI of the discussion topic
	 * @param post
	 *            The post to write
	 * @throws AuthenticationException
	 *             Thrown if there are some issues with authentication to Canvas
	 *             LMS.
	 * @throws IOException
	 *             Thrown if there are errors writing the post.
	 */
	private void writePostToContainer( final Container targetContainer, final Post post )
	        throws AuthenticationException,
	        IOException {
		Pattern pattern = Pattern.compile( getServiceEndpoint()
		        + CanvasLmsSiocUtils.REGEX_DISCUSSION_TOPIC_URI );
		Matcher matcher = pattern.matcher( targetContainer.toString() );

		if ( matcher.find() ) {
			long courseId = Long.parseLong( matcher.group( 1 ) );
			long topicId = Long.parseLong( matcher.group( 2 ) );

			UserAccount creatorAccount = PostWriterUtils.getCreatorUserAccount(
			        getConnector(),
			        post );

			// check if the creator has an UserAccount and a corresponding Person 
			// in the connectors triplestore, then check if we can write with the client
			if ( null != creatorAccount
			        && UserAccount.hasInstance( getModel(), creatorAccount )
			        && creatorAccount.hasAccountOf() ) {
				// skip post, if we have no write permission  
				if ( !SoccUtils.haveWriteAccess(
				        getConnector(),
				        creatorAccount,
				        targetContainer ) ) {
					LOG.info( "Skip writing post {}, have no writing permission.", post );
					return;
				}
			}

			CanvasLmsClient client = PostWriterUtils.getClientOfCreator(
			        getConnector(),
			        creatorAccount );

			String content = post.getContent();

			if ( null == client ) {
				LOG.info( "Using default client to access {}", getServiceEndpoint() );
				client = getConnector().getClientManager().getDefaultClient();
				content = SoccUtils.formatUnknownMessage(
				        getConnector(),
				        post );
			}

			// Add Attachments to message content
			content = SoccUtils.addAttachmentsToContent( post, content, "<br>" );

			if ( !SoccUtils.hasAnyContentWatermark( content ) ) {
				// add watermark for 'already forwarded' check
				content = SoccUtils.addContentWatermark( post.getIsPartOf(),
				        content );
			}

			try {
				Entry resultEntry = client.courses()
				        .discussionTopics( courseId )
				        .entries( topicId )
				        .post( content )
				        .execute();

				if ( null != resultEntry ) {
					convertWrittenEntryToSioc( post, resultEntry, targetContainer, null );
					return;
				}
			} catch ( Exception e ) {
				CanvasLmsConnector.handleCanvasExceptions( e );
			}
		} else {
			LOG.warn( "Invalid URI to write post to {}: {}", getServiceEndpoint(), targetContainer );
		}
	}

	/**
	 * Write a {@link Post} as a reply to a other entry of a Canvas LMS course
	 * discussion topic.
	 * 
	 * @param targetUri
	 *            Target URI of the entry
	 * @param post
	 *            The post to write
	 * @throws AuthenticationException
	 *             Thrown if there are some issues with authentication to Canvas
	 *             LMS.
	 * @throws IOException
	 *             Thrown if there are errors writing the post.
	 */
	private void writeReplyToPost( final Post targetPost, final Post post )
	        throws AuthenticationException,
	        IOException {
		Pattern pattern = Pattern.compile( getServiceEndpoint()
		        + CanvasLmsSiocUtils.REGEX_ENTRY_URI );
		Matcher matcher = pattern.matcher( targetPost.toString() );

		if ( matcher.find() ) {
			long courseId = Long.parseLong( matcher.group( 1 ) );
			long topicId = Long.parseLong( matcher.group( 2 ) );
			long entryId = Long.parseLong( matcher.group( 3 ) );

			UserAccount creatorAccount = PostWriterUtils.getCreatorUserAccount(
			        getConnector(),
			        post );

			// check if the creator has an UserAccount and a corresponding Person 
			// in the connectors triplestore, then check if we can write with the client
			if ( null != creatorAccount
			        && UserAccount.hasInstance( getModel(), creatorAccount )
			        && creatorAccount.hasAccountOf() ) {
				// skip post, if we have no write permission
				if ( !SoccUtils.haveWriteAccess(
				        getConnector(),
				        creatorAccount,
				        targetPost.getContainer() ) ) {
					LOG.info( "Skip writing post {}, have no writing permission.", post );
					return;
				}
			}

			CanvasLmsClient client = PostWriterUtils.getClientOfCreator(
			        getConnector(),
			        creatorAccount );

			String content = post.getContent();

			// Get the default client, if no client of the creator was found
			if ( null == client ) {
				LOG.info( "Using default client" );
				client = getConnector().getClientManager().getDefaultClient();
				content = SoccUtils.formatUnknownMessage(
				        getConnector(),
				        post );
			}

			// Add Attachments to message content
			content = SoccUtils.addAttachmentsToContent( post, content, "<br>" );

			// Check if the post has no watermark
			if ( !SoccUtils.hasAnyContentWatermark( content ) ) {
				// add watermark for 'already forwarded' check
				content = SoccUtils.addContentWatermark( post.getIsPartOf(), content );
			}

			try {
				Entry resultEntry = client.courses()
				        .discussionTopics( courseId )
				        .entries( topicId )
				        .postReply( content, entryId )
				        .execute();

				if ( null != resultEntry ) {
					convertWrittenEntryToSioc(
					        post,
					        resultEntry,
					        targetPost.getContainer(),
					        targetPost );
					return;
				}
			} catch ( Exception e ) {
				CanvasLmsConnector.handleCanvasExceptions( e );
			}
		} else {
			LOG.warn( "Invalid URI to write post to {}: {}", getServiceEndpoint(), targetPost );
		}
	}

	/**
	 * Converts a written entry to SIOC and stores it in the triplestore
	 * 
	 * @param post
	 *            The original {@link Post}.
	 * @param entry
	 *            The written entry.
	 * @param container
	 *            The parent {@link Container}.
	 * @param parentPost
	 *            The parent {@link Post}
	 * 
	 * @throws NotFoundException
	 *             Thrown if no resource was found at the URI
	 * @throws AuthenticationException
	 *             Thrown if there is a problem with authentication.
	 * @throws IOException
	 *             Thrown if there ist problem in communication.
	 */
	private void convertWrittenEntryToSioc(
	        final Post post,
	        final Entry entry,
	        final Container container,
	        final Post parentPost )
	        throws NotFoundException,
	        AuthenticationException,
	        IOException {
		if ( LOG.isDebugEnabled() ) {
			LOG.debug( "Received written entry:\n{}",
			        getServiceEndpoint(),
			        entry );
		} else {
			LOG.info( "Received written entry '{}'",
			        getServiceEndpoint(),
			        entry.getId() );
		}

		Post resultPost = CanvasLmsSiocUtils.createSiocPost(
		        getConnector(),
		        entry,
		        container,
		        parentPost );

		resultPost.addSibling( post );

		if ( LOG.isDebugEnabled() ) {
			LOG.debug( "Writing a post to {} was successful:\n{}",
			        getServiceEndpoint(),
			        RdfUtils.resourceToString( resultPost, Syntax.Turtle ) );
		} else {
			LOG.info( "Writing a post to {} was successful: '{}'",
			        getServiceEndpoint(),
			        resultPost );
		}
	}
}
