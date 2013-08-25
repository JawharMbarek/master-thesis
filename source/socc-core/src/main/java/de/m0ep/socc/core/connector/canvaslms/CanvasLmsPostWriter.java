/*
 * The MIT License (MIT) Copyright © 2013 "Florian Mueller"
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
import org.rdfs.sioc.Post;
import org.rdfs.sioc.UserAccount;

import com.google.common.base.Throwables;
import com.xmlns.foaf.Person;

import de.m0ep.canvas.CanvasLmsClient;
import de.m0ep.canvas.exceptions.AuthorizationException;
import de.m0ep.canvas.exceptions.CanvasLmsException;
import de.m0ep.canvas.exceptions.NetworkException;
import de.m0ep.socc.core.connector.DefaultConnectorIOComponent;
import de.m0ep.socc.core.connector.IConnector.IPostWriter;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.exceptions.NotFoundException;
import de.m0ep.socc.core.utils.PostWriterUtils;

/**
 * Class to write post to a Canvas LMS instance through a
 * {@link CanvasLmsConnector}.
 * 
 * @author "Florian Mueller"
 * 
 */
public class CanvasLmsPostWriter extends
        DefaultConnectorIOComponent<CanvasLmsConnector> implements
        IPostWriter<CanvasLmsConnector> {

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
	public void writePost( URI targetUri, String rdfString, Syntax syntax )
	        throws NotFoundException,
	        AuthenticationException,
	        IOException {
		Model tmpModel = RDFTool.stringToModel( rdfString, syntax );

		try {
			ClosableIterator<Resource> resIter = Post.getAllInstances( tmpModel );
			while ( resIter.hasNext() ) {
				Resource resource = resIter.next();
				Post post = Post.getInstance( tmpModel, resource );

				if ( CanvasLmsSiocUtils.isDiscussionTopicUri( targetUri, getServiceEndpoint() )
				        || CanvasLmsSiocUtils.isInitialEntryUri( targetUri, getServiceEndpoint() ) ) {
					writePostToTopic( targetUri, post );
				} else if ( CanvasLmsSiocUtils.isEntryUri( targetUri, getServiceEndpoint() ) ) {
					writeReplyToPost( targetUri, post );
				}
			}
		} finally {
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
	 * 
	 * @throws AuthenticationException
	 *             Thrown if there are some issues with authentication to Canvas
	 *             LMS.
	 * @throws IOException
	 *             Thrown if there are errors writing the post.
	 */
	private void writePostToTopic( URI targetUri, Post post )
	        throws AuthenticationException,
	        IOException {
		Pattern pattern = Pattern.compile(
		        getServiceEndpoint()
		                + CanvasLmsSiocUtils.REGEX_DISCUSSION_TOPIC_URI );
		Matcher matcher = pattern.matcher( targetUri.toString() );

		if ( matcher.find() ) {
			long courseId = Long.parseLong( matcher.group( 1 ) );
			long topicId = Long.parseLong( matcher.group( 2 ) );

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
				client = getConnector().getClientManager()
				        .getDefaultClient();
				content = PostWriterUtils.createContentOfUnknownAccount(
				        post,
				        creatorAccount,
				        creatorPerson );
			}

			try {
				client.courses()
				        .discussionTopics( courseId )
				        .entries( topicId )
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
	 * 
	 * @throws AuthenticationException
	 *             Thrown if there are some issues with authentication to Canvas
	 *             LMS.
	 * @throws IOException
	 *             Thrown if there are errors writing the post.
	 */
	private void writeReplyToPost( URI targetUri, Post post )
	        throws AuthenticationException,
	        IOException {
		Pattern pattern = Pattern.compile(
		        getServiceEndpoint()
		                + CanvasLmsSiocUtils.REGEX_ENTRY_URI );
		Matcher matcher = pattern.matcher( targetUri.toString() );

		if ( matcher.find() ) {
			long courseId = Long.parseLong( matcher.group( 1 ) );
			long topicId = Long.parseLong( matcher.group( 2 ) );
			long entryId = Long.parseLong( matcher.group( 3 ) );

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
				client = getConnector().getClientManager()
				        .getDefaultClient();
				content = PostWriterUtils.createContentOfUnknownAccount(
				        post,
				        creatorAccount,
				        creatorPerson );
			}

			try {
				client.courses()
				        .discussionTopics( courseId )
				        .entries( topicId )
				        .postReply( content, entryId )
				        .execute();
			} catch ( CanvasLmsException e ) {
				if ( e instanceof NetworkException ) {
					throw new IOException( e );
				} else if ( e instanceof AuthorizationException ) {
					throw new AuthenticationException( e );
				}

				throw Throwables.propagate( e );
			}
		}
	}
}
