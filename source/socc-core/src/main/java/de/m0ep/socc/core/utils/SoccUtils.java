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

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ontoware.rdf2go.model.node.Node;
import org.ontoware.rdf2go.model.node.URI;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.SiocVocabulary;
import org.rdfs.sioc.UserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3.ns.auth.acl.AclVocabulary;

import com.google.common.collect.Sets;
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
	public static String addContentWatermark( final Node siteNode, final String content ) {
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
	public static boolean hasContentWatermark( final Node siteNode, final String content ) {
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
	public static String addContentWatermark( Node siteNode, String content, String lineBreak ) {
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
				IAccessControl accessControl = connector.getContext().getAccessControl();

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
		return haveAccess( connector, userAccount, container, AclVocabulary.Write );
	}
}
