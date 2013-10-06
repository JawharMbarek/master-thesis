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

package de.m0ep.socc.core.connector.moodle;

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.rdfs.sioc.UserAccount;
import org.rdfs.sioc.services.Service;

import com.google.common.base.Preconditions;

import de.m0ep.sioc.services.auth.AuthenticationMechanism;
import de.m0ep.sioc.services.auth.Credentials;
import de.m0ep.sioc.services.auth.Password;
import de.m0ep.sioc.services.auth.ServicesAuthVocabulary;
import de.m0ep.sioc.services.auth.Username;
import de.m0ep.socc.core.connector.DefaultClientManager;
import de.m0ep.socc.core.connector.IClientManager;
import de.m0ep.socc.core.utils.RdfUtils;

/**
 * Implementation of an {@link IClientManager} for {@link Moodle2Connector}.
 * 
 * @author Florian Müller
 * 
 */
public class Moodle2ClientManager extends
        DefaultClientManager<Moodle2ClientWrapper> {

	/**
	 * Constructs a new {@link Moodle2ClientManager}.
	 * 
	 * @param service
	 *            The {@link Service} object for Moodle.
	 * @param defaultUserAccount
	 *            The default user.
	 * @throws Exception
	 *             Throw if there is a problem initializing the clientmanager.
	 */
	public Moodle2ClientManager( final Service service, final UserAccount defaultUserAccount )
	        throws Exception {
		super( service, defaultUserAccount );
	}

	@Override
	protected void init() {
	}

	@Override
	public Moodle2ClientWrapper createClient( final UserAccount userAccount )
	        throws Exception {
		de.m0ep.sioc.services.auth.UserAccount authUserAccount =
		        de.m0ep.sioc.services.auth.UserAccount.getInstance(
		                userAccount.getModel(),
		                userAccount.getResource() );

		Preconditions.checkArgument( authUserAccount.hasAccountAuthentication(),
		        "The defaultUserAccount has no required authentication data." );
		AuthenticationMechanism authentication = authUserAccount.getAccountAuthentication();

		Preconditions.checkArgument( authentication.hasCredentials(),
		        "The defaultUserAccount authentication has no required credentials" );
		ClosableIterator<Credentials> credentialIter = authentication.getAllCredentials();

		Username username = null;
		Password password = null;
		while ( credentialIter.hasNext() ) {
			Credentials credential = credentialIter.next();

			if ( RdfUtils.isType(
			        credential.getModel(),
			        credential.getResource(),
			        ServicesAuthVocabulary.Username )
			        && credential.hasValue() ) {
				username = Username.getInstance(
				        credential.getModel(),
				        credential.asResource() );
			} else if ( RdfUtils.isType(
			        credential.getModel(),
			        credential.getResource(),
			        ServicesAuthVocabulary.Password )
			        && credential.hasValue() ) {
				password = Password.getInstance(
				        credential.getModel(),
				        credential.asResource() );
			}
		}

		Preconditions.checkArgument( null != username,
		        "The defaultUserAccount authentication contains no required username" );

		Preconditions.checkArgument( null != password,
		        "The defaultUserAccount authentication contains no required password" );

		return new Moodle2ClientWrapper(
		        getService().getServiceEndpoint().asURI(),
		        username.getValue(),
		        password.getValue() );
	}
}
