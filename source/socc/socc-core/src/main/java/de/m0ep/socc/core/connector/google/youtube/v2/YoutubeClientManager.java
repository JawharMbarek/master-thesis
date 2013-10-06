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

package de.m0ep.socc.core.connector.google.youtube.v2;

import java.io.IOException;

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.rdfs.sioc.UserAccount;
import org.rdfs.sioc.services.Service;

import com.google.common.base.Preconditions;

import de.m0ep.sioc.services.auth.APIKey;
import de.m0ep.sioc.services.auth.AuthenticationMechanism;
import de.m0ep.sioc.services.auth.Credentials;
import de.m0ep.sioc.services.auth.Password;
import de.m0ep.sioc.services.auth.ServicesAuthVocabulary;
import de.m0ep.sioc.services.auth.Username;
import de.m0ep.socc.core.connector.DefaultClientManager;
import de.m0ep.socc.core.connector.IClientManager;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.utils.RdfUtils;

/**
 * Implementation of a {@link IClientManager} for the {@link YoutubeConnector}.
 * 
 * @author Florian Müller
 * 
 */
public class YoutubeClientManager extends
        DefaultClientManager<YoutubeClientWrapper> {
	private APIKey apiKey;

	/**
	 * Constructs a new {@link YoutubeClientManager} for a <code>service</code>
	 * with a <code>defaultUserAccount</code>.
	 * 
	 * @param service
	 * @param defaultUserAccount
	 * @throws NullPointerException
	 *             Thrown if one or more parameters are <code>null</code>.
	 * @throws IllegalArgumentException
	 *             Thrown if <code>service</code> or
	 *             <code>defaultUserAccount</code> has missing authentication
	 *             parameter.
	 * @throws IOException
	 *             Thrown if a network error occurred.
	 * @throws AuthenticationException
	 *             Thrown if creating the defaultClient failed because of
	 *             authentication problems.
	 */
	public YoutubeClientManager( final Service service, final UserAccount defaultUserAccount )
	        throws Exception {
		super( service, defaultUserAccount );
	}

	public APIKey getApiKey() {
		return apiKey;
	}

	@Override
	protected void init() {
		Preconditions.checkArgument( getService().hasServiceEndpoint(),
		        "The parameter service has no serviceEndpoint." );

		de.m0ep.sioc.services.auth.Service authService =
		        de.m0ep.sioc.services.auth.Service.getInstance(
		                getService().getModel(),
		                getService().getResource() );

		Preconditions.checkArgument( authService.hasServiceAuthentication(),
		        "The parameter service has no authentication." );

		AuthenticationMechanism authentication = authService
		        .getServiceAuthentication();
		Preconditions.checkArgument( authentication.hasCredentials(),
		        "The service authentication has no credentials." );

		ClosableIterator<Credentials> credIter = authentication
		        .getAllCredentials();
		try {
			while ( credIter.hasNext() ) {
				Credentials credential = credIter.next();

				if ( RdfUtils.isType(
				        credential.getModel(),
				        credential.getResource(),
				        ServicesAuthVocabulary.APIKey )
				        && credential.hasValue() ) {
					apiKey = APIKey.getInstance( credential.getModel(),
					        credential.getResource() );
					return;
				}
			}
		} finally {
			credIter.close();
		}

		throw new IllegalArgumentException(
		        "The service authentication has no apikey credential." );
	}

	@Override
	public YoutubeClientWrapper createClient( final UserAccount userAccount )
	        throws Exception {
		Preconditions.checkState( null != apiKey,
		        "API key missing." );

		de.m0ep.sioc.services.auth.UserAccount authUserAccount =
		        de.m0ep.sioc.services.auth.UserAccount.getInstance(
		                userAccount.getModel(),
		                userAccount.getResource() );

		Preconditions.checkArgument( authUserAccount.hasAccountAuthentication(),
		        "The defaultUserAccount has no required authentication data." );
		AuthenticationMechanism authentication = authUserAccount
		        .getAccountAuthentication();

		Preconditions.checkArgument( authentication.hasCredentials(),
		        "The defaultUserAccount authentication has no required credentials" );
		ClosableIterator<Credentials> credentialIter = authentication
		        .getAllCredentials();

		Username username = null;
		Password password = null;
		while ( credentialIter.hasNext() ) {
			Credentials credential = credentialIter.next();

			if ( RdfUtils.isType(
			        credential.getModel(),
			        credential.getResource(),
			        ServicesAuthVocabulary.Username )
			        && credential.hasValue() ) {
				username = Username.getInstance( credential.getModel(),
				        credential.asResource() );
			} else if ( RdfUtils.isType(
			        credential.getModel(),
			        credential.getResource(),
			        ServicesAuthVocabulary.Password )
			        && credential.hasValue() ) {
				password = Password.getInstance( credential.getModel(),
				        credential.asResource() );
			}
		}

		Preconditions.checkArgument( null != username,
		        "The defaultUserAccount authentication contains no required username" );
		Preconditions.checkArgument( null != password,
		        "The defaultUserAccount authentication contains no required password" );

		return new YoutubeClientWrapper( apiKey, username, password );
	}
}
