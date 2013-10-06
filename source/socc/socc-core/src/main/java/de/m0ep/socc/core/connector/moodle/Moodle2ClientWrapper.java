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

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.Callable;

import org.ontoware.rdf2go.model.node.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import de.m0ep.moodlews.soap.LoginReturn;
import de.m0ep.moodlews.soap.Mdl_soapserverBindingStub;
import de.m0ep.socc.core.exceptions.AuthenticationException;

/**
 * Class that wraps the moodle client to store extra values.
 * 
 * @author Florian Müller
 */
public class Moodle2ClientWrapper {
	private static final Logger LOG = LoggerFactory.getLogger( Moodle2ClientWrapper.class );

	private static final String MOODLEWS_PATH = "/wspp/service_pp2.php";
	private static final String MOODLE_WSDL_POSTFIX = "/wspp/wsdl2";

	private final Mdl_soapserverBindingStub bindingStub;
	private final String username;
	private final String password;
	private int authClient;
	private String sessionKey;

	/**
	 * Constructs a new {@link Moodle2ClientWrapper}.
	 * 
	 * @param serviceEndpoint
	 *            Endpoint URI to an Moodle instance.
	 * @param username
	 *            Username of an Moodle user
	 * @param password
	 *            Password of that Moodle user
	 * 
	 * @throws AuthenticationException
	 *             Thrown if there is a problem with authentication.
	 * @throws IOException
	 *             Thrown if there ist problem in communication.
	 */
	public Moodle2ClientWrapper(
	        final URI serviceEndpoint,
	        final String username,
	        final String password )
	        throws AuthenticationException,
	        IOException {
		this.bindingStub = new Mdl_soapserverBindingStub(
		        serviceEndpoint.toString() + MOODLEWS_PATH,
		        serviceEndpoint.toString() + MOODLE_WSDL_POSTFIX,
		        false );
		this.username = username;
		this.password = password;

		tryLogin();
	}

	public Mdl_soapserverBindingStub getBindingStub() {
		return bindingStub;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public int getAuthClient() {
		return authClient;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	/**
	 * Calls a {@link Callable} with a MoodleWS function and tries to relogin if
	 * the usersession is expired.
	 * 
	 * @param callable
	 *            {@link Callable} with MoodleWS function call.
	 * @return Result of the {@link Callable}.
	 */
	public <T> T callMethod( final Callable<T> callable )
	        throws IOException, AuthenticationException {
		Preconditions.checkNotNull( callable, "callable can not be null" );
		T result = null;

		try {
			result = callable.call();

			if ( null == result ) {
				if ( !isMoodleWSRunning() ) {
					throw new IOException( "No connection to " + bindingStub.getURL() );
				}

				if ( isSessionMaybeExpired() ) {
					LOG.debug( "try relogin and call method again" );
					tryLogin();
					result = callable.call();
				}
			}
		} catch ( Exception e ) {
			throw new IOException( e );
		}

		return result;
	}

	/**
	 * Check if the moodle webservice is running.
	 * 
	 * @return Returns true if MoodleWS is running, false otherwise.
	 */
	private boolean isMoodleWSRunning() {
		if ( null == bindingStub )
			return false;

		try {
			new URL( bindingStub.getURL() ).openConnection().connect();
		} catch ( IOException e ) {
			LOG.warn( e.getMessage(), e );
			return false;
		}

		return true;
	}

	/**
	 * @return Returns true if the webservice session in Moodle is maybe expired
	 */
	private boolean isSessionMaybeExpired() {
		return 0 == bindingStub.get_my_id( authClient, sessionKey );
	}

	/**
	 * Tries to login to Moodle.
	 */
	private void tryLogin() throws IOException, AuthenticationException {
		if ( null != sessionKey ) {
			bindingStub.logout( authClient, sessionKey );
		}

		LoginReturn login = bindingStub.login( username, password );
		if ( null == login ) {
			if ( isMoodleWSRunning() ) {
				throw new AuthenticationException(
				        "Maybe the login parameter are invalid or there" +
				                " is no MoodleWS webservice." );
			}

			throw new IOException( "Connection to " + bindingStub.getURL() + " failed." );
		}

		this.authClient = login.getClient();
		this.sessionKey = login.getSessionkey();
	}
}
