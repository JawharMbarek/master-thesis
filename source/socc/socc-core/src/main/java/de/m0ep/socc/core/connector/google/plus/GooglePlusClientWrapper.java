/*
 * The MIT License (MIT) Copyright © 2013 Florian
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

package de.m0ep.socc.core.connector.google.plus;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.CredentialRefreshListener;
import com.google.api.client.auth.oauth2.TokenErrorResponse;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.model.Person;

import de.m0ep.sioc.services.auth.AccessToken;
import de.m0ep.sioc.services.auth.ClientId;
import de.m0ep.sioc.services.auth.ClientSecret;
import de.m0ep.sioc.services.auth.RefreshToken;

/**
 * Class that wraps an Google+ client to store extra data.
 * 
 * @author Florian Müller
 * 
 */
public class GooglePlusClientWrapper implements CredentialRefreshListener {
	private static final Logger LOG = LoggerFactory
	        .getLogger( GooglePlusClientWrapper.class );

	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	private static final JacksonFactory JSON_FACTORY = new JacksonFactory();

	private final Plus service;
	private AccessToken accessToken;
	private RefreshToken refreshToken;
	private Person person;

	/**
	 * Constructs a new {@link GooglePlusClientWrapper} with a
	 * <code>clientId</code>, <code>clientSecret</code>,
	 * <code>accessToken</code> and <code>refreshToken</code>.
	 * 
	 * @param clientId
	 *            The client id for the Google+ application.
	 * @param clientSecret
	 *            The client secret for the Google+ application
	 * @param accessToken
	 *            The acccessToken for the client.
	 * @param refreshToken
	 *            The refreshToken for the accessToken.
	 * @throws Exception
	 *             Thrown if there is a problem creating the
	 *             {@link GooglePlusClientWrapper}.
	 */
	public GooglePlusClientWrapper(
	        final ClientId clientId,
	        final ClientSecret clientSecret,
	        final AccessToken accessToken,
	        final RefreshToken refreshToken )
	        throws Exception {
		GoogleCredential googleCredential = new GoogleCredential.Builder()
		        .setClientSecrets(
		                clientId.getValue(),
		                clientSecret.getValue() )
		        .setJsonFactory( JSON_FACTORY )
		        .setTransport( HTTP_TRANSPORT )
		        .addRefreshListener( this )
		        .build();

		service = new Plus.Builder(
		        HTTP_TRANSPORT,
		        JSON_FACTORY,
		        googleCredential )
		        .setApplicationName( "SOCC Google Plus Connector" )
		        .build();
		try {
			person = service.people().get( "me" ).execute();
		} catch ( Exception e ) {
			GooglePlusConnector.handleGoogleException( e );
		}
	}

	public Person getPerson() {
		return person;
	}

	public AccessToken getAccessToken() {
		return accessToken;
	}

	public RefreshToken getRefreshToken() {
		return refreshToken;
	}

	public Plus getGooglePlusService() {
		return service;
	}

	@Override
	public void onTokenResponse( final Credential credential, final TokenResponse tokenResponse )
	        throws IOException {
		LOG.info( "Refreshed Google+ accesstoken." );

		String newAccessToken = tokenResponse.getAccessToken();
		if ( null != newAccessToken ) {
			this.accessToken.setValue( newAccessToken );
		}

		String newRefresToken = tokenResponse.getRefreshToken();
		if ( null != newRefresToken ) {
			this.refreshToken.setValue( newRefresToken );
		}
	}

	@Override
	public void onTokenErrorResponse(
	        final Credential credential,
	        final TokenErrorResponse tokenErrorResponse )
	        throws IOException {
		LOG.error( "Google token refresh failed: {} - {}",
		        tokenErrorResponse.getError(),
		        tokenErrorResponse.getErrorDescription() );
	}
}
