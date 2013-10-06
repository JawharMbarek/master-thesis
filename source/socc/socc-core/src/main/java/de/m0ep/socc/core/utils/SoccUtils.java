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

package de.m0ep.socc.core.utils;

import java.text.ParseException;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.Statement;
import org.ontoware.rdf2go.model.Syntax;
import org.ontoware.rdf2go.model.node.Node;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.model.node.Variable;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.SiocVocabulary;
import org.rdfs.sioc.Site;
import org.rdfs.sioc.Thing;
import org.rdfs.sioc.UserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3.ns.auth.acl.AclVocabulary;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.java2s.code.java.i18n.MapFormat;
import com.xmlns.foaf.Person;

import de.m0ep.socc.core.acl.IAccessControl;
import de.m0ep.socc.core.connector.IConnector;
import de.m0ep.socc.core.connector.IConnector.IPostWriter;

/**
 * Utility methods for the SOCC framework
 * 
 * @author "Florian Mueller"
 */
public final class SoccUtils {
	private static final Logger LOG = LoggerFactory.getLogger( SoccUtils.class );

	private SoccUtils() {
	}

	/**
	 * Adds a SOCC watermark to the content to prevent writing duplicate posts.
	 * 
	 * @param siteNode
	 *            Site where the post was made.
	 * @param content
	 *            Content of the post where the watermark will be added.
	 * @return Post content with watermark.
	 */
	public static String addContentWatermark( final Node siteNode,
	        final String content ) {
		return addContentWatermark( siteNode, content, "\n" );
	}

	/**
	 * Checks if the content has a watermark from a particular site.
	 * 
	 * @param siteNode
	 *            Site for watermark check
	 * @param content
	 *            Content of the post to check
	 * @return True if a watermark for the <code>siteNode</code> exists, false
	 *         otherwise.
	 */
	public static boolean hasContentWatermark( final Node siteNode,
	        final String content ) {
		return content.contains(
		        IPostWriter.MESSAGE_WATERMARK_PREFIX
		                + siteNode
		                + IPostWriter.MESSAGE_WATERMARK_POSTFIX );
	}

	/**
	 * Checks if the content of a post has any SOCC watermark.
	 * 
	 * @param content
	 *            Content to check for a watermark.
	 * @return True if there is any socc watermark, false otherwise.
	 */
	public static boolean hasAnyContentWatermark( final String content ) {
		Pattern pattern = Pattern.compile( IPostWriter.REGEX_MESSAGE_WATERMARK );
		Matcher matcher = pattern.matcher( content );
		return matcher.find();
	}

	/**
	 * Adds a SOCC watermark to the content to prevent writing duplicate posts.
	 * 
	 * @param siteNode
	 *            Site where the post was made.
	 * @param content
	 *            Content of the post where the watermark will be added.
	 * @param lineBreak
	 *            Linebreak symbol between content and watermark
	 * @return Post content with watermark.
	 */
	public static String addContentWatermark( Node siteNode, String content,
	        String lineBreak ) {
		return content
		        + lineBreak
		        + IPostWriter.MESSAGE_WATERMARK_PREFIX
		        + siteNode
		        + IPostWriter.MESSAGE_WATERMARK_POSTFIX;
	}

	public static boolean haveAccess(
	        final IConnector connector,
	        final UserAccount userAccount,
	        final Container container,
	        final URI accessModeClass ) {
		if ( null != userAccount ) {
			final Set<URI> readSet = Sets.newHashSet( accessModeClass );

			try {
				Person person = UserAccountUtils.findPerson(
				        connector.getContext().getModel(),
				        userAccount );
				IAccessControl accessControl = connector.getContext()
				        .getAccessControl();

				// check public access to for all posts
				if ( accessControl.checkAccessToClass(
				        person,
				        SiocVocabulary.Post,
				        readSet ) ) {
					return true;
				}

				// check public access for site
				if ( accessControl.checkAccessTo(
				        person,
				        connector.getStructureReader().getSite().asURI(),
				        readSet ) ) {
					return true;
				}

				// check access for containers
				Container tmpContainer = container;
				while ( null != tmpContainer ) {
					if ( accessControl.checkAccessTo(
					        person,
					        tmpContainer.asURI(),
					        readSet ) ) {
						return true;
					}

					tmpContainer = ( tmpContainer.hasParent() )
					        ? connector.getStructureReader().getContainer(
					                tmpContainer.getParent().asURI() )
					        : null;
				}

			} catch ( Exception e ) {
				LOG.info( "No Person found for UserAccount '{}'", userAccount );
			}
		}

		return false;
	}

	public static boolean haveReadAccess(
	        final IConnector connector,
	        final UserAccount userAccount,
	        final Container container ) {
		return haveAccess( connector, userAccount, container, AclVocabulary.Read );
	}

	public static boolean haveWriteAccess(
	        final IConnector connector,
	        final UserAccount userAccount,
	        final Container container ) {
		return haveAccess( connector, userAccount, container,
		        AclVocabulary.Write );
	}

	/**
	 * Formats the content of a post with the
	 * <code>unknownMessageTemplate</code> of the <code>connector</code>. If the
	 * creator of the post has no sioc:name property set,
	 * {@link IConnector#MESSAGE_TEMPLATE_UNKNOWN_AUTHOR_NAME} will be used as
	 * <code>{authorName}</code>.
	 * 
	 * @param connector
	 *            Used Connector
	 * @param post
	 *            The Post from where the content should be formated.
	 * @return The formated content as {@link String}.
	 * @throws NullPointerException
	 *             Thrown if <code>connector</code> or <code>post</code> are
	 *             <code>null</code>.
	 */
	public static String formatUnknownMessage(
	        final IConnector connector,
	        final Post post ) {
		Preconditions.checkNotNull( connector,
		        "Required parameter connector must be specified." );
		Preconditions.checkNotNull( post,
		        "Required parameter post must be specified." );
		Preconditions.checkArgument( post.hasContent(),
		        "The parameter post has no content." );

		Map<String, Object> args = Maps.newHashMap();
		UserAccount creatorAccount = post.hasCreator() ? post.getCreator()
		        : null;
		String authorName = ( null != creatorAccount && creatorAccount.hasName() )
		        ? creatorAccount.getName()
		        : "an unknown user";

		args.put( IPostWriter.MESSAGE_TEMPLATE_VAR_MESSAGE, post.getContent() );
		args.put( IPostWriter.MESSAGE_TEMPLATE_VAR_CONNECTOR_ID, connector
		        .getId() );
		args.put( IPostWriter.MESSAGE_TEMPLATE_VAR_AUTHOR_NAME, authorName );
		args.put( IPostWriter.MESSAGE_TEMPLATE_VAR_SOURCE_URI, post
		        .getResource() );

		if ( connector.getService().hasName() ) {
			args.put(
			        IPostWriter.MESSAGE_TEMPLATE_VAR_SERVICE_NAME,
			        connector.getService().getName() );
		}

		if ( post.hasCreated() ) {
			try {
				args.put(
				        IPostWriter.MESSAGE_TEMPLATE_VAR_CREATION_DATE,
				        DateUtils.parseISO8601( post.getCreated() ) );
			} catch ( ParseException e ) {
				LOG.warn(
				        "Failed to parse date {} from post {}",
				        post.getCreated(),
				        post.getResource() );
			}
		}

		return MapFormat.format( connector.getUnknownMessageTemplate(), args );
	}

	/**
	 * Find a sibling {@link Post} to <code>post</code> that isPartOf
	 * <code>site</code>.
	 * 
	 * @param model
	 *            Model where to search for the siblin.
	 * @param post
	 *            Post to which a sibling should be found.
	 * @param site
	 *            {@link Site} where the siblins isPartOf.
	 * @return Returns {@link Post} if a sibling is found, <code>null</code>
	 *         otherwise.
	 */
	public static Post findSibling( final Model model, final Post post, final Site site ) {
		ClosableIterator<Statement> stmtIter = model.findStatements(
		        Variable.ANY,
		        SiocVocabulary.sibling,
		        post );
		try {
			while ( stmtIter.hasNext() ) {
				Post item = Post.getInstance( model, stmtIter.next()
				        .getSubject() );

				if ( item.hasIsPartOf( site ) ) {
					return item;
				}
			}
		} finally {
			stmtIter.close();
		}

		return null;
	}

	public static void logPost( final Logger log, final Post post, final String prefix ) {
		if ( log.isDebugEnabled() ) {
			log.debug( "{}:\n{}",
			        prefix,
			        RdfUtils.resourceToString( post, Syntax.Turtle ) );
		} else {
			LOG.info( "{} - {}",
			        prefix,
			        post );
		}
	}

	public static String addAttachmentsToContent(
	        final Post post,
	        final String content,
	        final String lineBreak ) {
		StringBuffer strBuffer = new StringBuffer( content );

		if ( post.hasAttachments() ) {
			strBuffer.append( lineBreak ).append( "Attachments:" );

			ClosableIterator<Thing> attachIter = post.getAllAttachments();
			try {
				while ( attachIter.hasNext() ) {
					strBuffer.append( lineBreak ).append( attachIter.next() );
				}

			} finally {
				attachIter.close();
			}
		}

		return strBuffer.toString();
	}

	public static void anonymisePost( Post post ) {
		post.removeAllCreators();
		post.removeAllAttachments();
		post.removeAllCreated();
		post.removeAllModified();
		post.setContent( "--- anonymised ---" );
	}
}
