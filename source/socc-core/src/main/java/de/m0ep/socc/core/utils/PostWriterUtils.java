/*
 * The MIT License (MIT) Copyright © 2013 Florian Müller
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

import org.ontoware.rdf2go.model.node.URI;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.UserAccount;
import org.rdfs.sioc.services.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.util.Maps;
import com.google.common.base.Preconditions;
import com.xmlns.foaf.Person;

import de.m0ep.socc.core.connector.IConnector;
import de.m0ep.socc.core.connector.IConnector.IPostWriter;
import de.m0ep.socc.core.exceptions.NotFoundException;

/**
 * Utility methods so the PostWriters are not a huge mess ;)
 * 
 * @author "Florian Mueller"
 */
public final class PostWriterUtils {
	private static final Logger LOG = LoggerFactory
	        .getLogger( PostWriterUtils.class );

	private PostWriterUtils() {
	}

	/**
	 * Formats the content of a post with the
	 * <code>unknownMessageTemplate</code> of the <code>connector</code>. If the
	 * creator of the post has no sioc:name property set,
	 * {@link IConnector#MESSAGE_TEMPLATE_UNKNOWN_AUTHOR_NAME} will be used as
	 * <code>{authorName}</code>.
	 * 
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

		Map<String, Object> args = Maps.newHashMap();
		UserAccount creatorAccount = post.getCreator();
		String authorName = creatorAccount.hasName() ? creatorAccount.getName() : "an unknown user";

		args.put( IPostWriter.MESSAGE_TEMPLATE_VAR_MESSAGE, post.getContent() );
		args.put( IPostWriter.MESSAGE_TEMPLATE_VAR_CONNECTOR_ID, connector.getId() );
		args.put( IPostWriter.MESSAGE_TEMPLATE_VAR_AUTHOR_NAME, authorName );
		args.put( IPostWriter.MESSAGE_TEMPLATE_VAR_SOURCE_URI, post.getResource() );

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
	 * Finds a {@link UserAccount} that ist mapped to
	 * <code>creatorUserAccount</code> and belongs to the {@link Service} of the
	 * <code>connector</code>.
	 * 
	 * @param connector
	 *            Used Connector
	 * @param creatorUserAccount
	 *            {@link UserAccount} of which a mapped {@link UserAccount}
	 *            should be found.
	 * 
	 * @return The mapped {@link UserAccount}
	 * @throws NotFoundException
	 *             Thrown if no such mapped {@link UserAccount} could be found.
	 * @throws NullPointerException
	 *             Thrown if <code>connector</code> or
	 *             <code>creatorUserAccount</code> are <code>null</code>.
	 */
	public static UserAccount findUserAccountOfService(
	        final IConnector connector,
	        final UserAccount creatorUserAccount ) {
		Preconditions.checkNotNull( connector,
		        "Required parameter connector must be specified." );
		Preconditions.checkNotNull( creatorUserAccount,
		        "Required parameter creatorUserAccount must be specified." );

		//Service service = connector.getService();

		// TODO: mhm... ganz schön leer ;)

		return null;
	}

	public static String createContentOfUnknownAccount(
	        final Post post,
	        final UserAccount creatorAccount,
	        final Person creatorPerson ) {
		String creator = "unknown";

		if ( null != creatorPerson ) {
			if ( creatorPerson.hasNickname() ) {
				creator = creatorPerson.getNickname();
			} else if ( creatorPerson.hasName() ) {
				creator = creatorPerson.getName();
			}
		} else if ( null != creatorAccount ) {
			creator = creatorAccount.getName();
		}

		return creator
		        + " wrote: \n"
		        + post.getContent();
	}

	public static Object getClientOfServiceAccountOrNull(
	        final IConnector connector,
	        final UserAccount serviceAccount ) {
		try {
			if ( connector.getClientManager().contains( serviceAccount ) ) {
				return connector.getClientManager().get( serviceAccount );
			} else {
				try {
					Object client = connector.getClientManager()
					        .createClient( serviceAccount );

					connector.getClientManager().add( serviceAccount,
					        client );
					return client;
				} catch ( Exception e ) {
					LOG.debug( "Failed to create client form userAccount "
					        + connector.getService().getServiceEndpoint() );
				}
			}
		} catch ( Exception e ) {
			LOG.debug( "Failed to load client form userAccount "
			        + connector.getService().getServiceEndpoint(),
			        e );
		}

		return null;
	}

	public static UserAccount getServiceAccountOfPersonOrNull(
	        final IConnector connector,
	        final Person creatorPerson,
	        final URI serviceEndpoint ) {
		try {
			return UserAccountUtils.findUserAccountOfService(
			        connector.getContext().getModel(),
			        creatorPerson,
			        serviceEndpoint );
		} catch ( NotFoundException e ) {
			LOG.debug( "Person {} has no userAccount at the {} service",
			        creatorPerson,
			        connector.getService().getServiceEndpoint() );
			return null;
		}
	}

	public static Person getPersonOfCreatorOrNull(
	        final IConnector connector,
	        final UserAccount creatorAccount ) {
		if ( null != creatorAccount ) {
			if ( creatorAccount.hasAccountOf() ) {
				return Person.getInstance(
				        connector.getContext().getModel(),
				        creatorAccount.getAccountOf().getResource() );
			} else {
				try {
					return UserAccountUtils.findPerson(
					        connector.getContext().getModel(),
					        creatorAccount );
				} catch ( NotFoundException e ) {
					LOG.debug( "No corresponding person found for " + creatorAccount );
				}
			}
		}

		return null;
	}
}
