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

package de.m0ep.canvas;

import java.util.Date;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.m0ep.canvas.gson.ISO8601TypeAdapter;

/**
 * The client to use CanvasLMS4J
 * 
 * @author Florian Müller
 */
public class CanvasLmsClient {
	private static final String SERVICE_PATH = "/api/v1";

	private final String oauthToken;
	private final CloseableHttpClient httpClient;

	private final Gson gson;

	private final String baseUri;
	private String rootUri;

	/**
	 * Constructs a new {@link CanvasLmsClient} using the provided rootUri and
	 * oauthtoken.
	 * 
	 * @param rootUri
	 *            The uri to a CanvasLMS instance.
	 * @param oauthtoken
	 *            An CanvasLMS oauthtoken
	 * @throws NullPointerException
	 *             Thrown if rootUri or oauthtoken are null.
	 * @throws IllegalArgumentException
	 *             Thrown if rootUri or oauthtoken are empty.
	 */
	public CanvasLmsClient( final String rootUri, final String oauthtoken ) {
		this.rootUri = Preconditions.checkNotNull( rootUri,
		        "Required parameter uri must be specified." );

		Preconditions.checkArgument(
		        !rootUri.isEmpty(),
		        "Required parameter rootUri may not be empty" );

		if ( this.rootUri.endsWith( "/" ) ) { // remove tailing backslash
			this.rootUri = this.rootUri.substring( 0, this.rootUri.length() - 1 );
		}

		this.baseUri = this.rootUri + SERVICE_PATH;

		this.oauthToken = Preconditions.checkNotNull( oauthtoken,
		        "Required parameter oauthToken must be specified." );
		Preconditions.checkArgument(
		        !oauthtoken.isEmpty(),
		        "Required parameter oauthToken may not be empty" );

		this.httpClient = HttpClients.createDefault();
		this.gson = new GsonBuilder()
		        .registerTypeAdapter( Date.class,
		                new ISO8601TypeAdapter().nullSafe() )
		        .create();
	}

	public String getRootUri() {
		return baseUri;
	}

	public String getBaseUri() {
		return baseUri;
	}

	public String getOAuthToken() {
		return oauthToken;
	}

	public Gson getGson() {
		return gson;
	}

	public HttpClient getHttpClient() {
		return httpClient;
	}

	/**
	 * Get the API endpoint for courses.
	 * 
	 * @return A {@link Courses} endpoint.
	 */
	public Courses courses() {
		return new Courses( this );
	}

	/**
	 * Get the API endpoint for groups-
	 * 
	 * @return A {@link Groups} endpoint.
	 */
	public Groups groups() {
		return new Groups( this );
	}

	@Override
	public int hashCode() {
		return Objects.hashCode( oauthToken, rootUri, baseUri );
	}

	@Override
	public boolean equals( Object obj ) {
		if ( null == obj ) {
			return false;
		}

		if ( this == obj ) {
			return true;
		}

		if ( this.getClass() != obj.getClass() ) {
			return false;
		}

		CanvasLmsClient other = (CanvasLmsClient) obj;

		return Objects.equal( this.oauthToken, other.oauthToken ) &&
		        Objects.equal( this.rootUri, other.rootUri ) &&
		        Objects.equal( this.baseUri, other.baseUri );
	}

	@Override
	public String toString() {
		return Objects.toStringHelper( this )
		        .add( "rootUri", rootUri )
		        .add( "baseUri", baseUri )
		        .toString();
	}
}
