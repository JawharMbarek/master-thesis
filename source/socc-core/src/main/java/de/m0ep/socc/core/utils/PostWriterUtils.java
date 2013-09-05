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

import org.ontoware.rdf2go.model.node.URI;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.UserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xmlns.foaf.Person;

import de.m0ep.socc.core.connector.IConnector;
import de.m0ep.socc.core.exceptions.NotFoundException;

public final class PostWriterUtils {
	private static final Logger LOG = LoggerFactory
	        .getLogger( PostWriterUtils.class );

	/**
	 * Private constructor, because this class has only static methods.
	 */
	private PostWriterUtils() {
	}

	public static String createContentOfUnknownAccount( Post post,
	        UserAccount creatorAccount,
	        Person creatorPerson ) {
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

	public static Object getClientOfServiceAccountOrNull( IConnector connector,
	        UserAccount serviceAccount ) {
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
	        IConnector connector,
	        Person creatorPerson,
	        URI serviceEndpoint ) {
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

	public static Person getPersonOfCreatorOrNull( IConnector connector,
	        UserAccount creatorAccount ) {
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
