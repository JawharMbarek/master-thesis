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

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

import de.m0ep.canvas.exceptions.AccessControlException;
import de.m0ep.canvas.exceptions.AuthorizationException;
import de.m0ep.canvas.exceptions.CanvasLmsException;
import de.m0ep.canvas.exceptions.NetworkException;
import de.m0ep.canvas.exceptions.NotFoundException;

/**
 * Abstract implementation of a REST-Request to the Canvas REST-API
 * 
 * @author Florian Müller
 * 
 * @param <T>
 *            Class of the returned data.
 */
public abstract class CanvasLmsRequest<T> {
	private static final Logger LOG = LoggerFactory.getLogger( CanvasLmsRequest.class );

	private Class<? extends HttpRequestBase> methodType;
	private String uri;
	private CanvasLmsClient client;
	private String oauthToken;
	private HttpEntity content;
	private Class<T> responseType;

	/**
	 * Constructs a new {@link CanvasLmsRequest}.
	 * 
	 * @param client
	 *            The {@link CanvasLmsClient}.
	 * @param methodType
	 *            The HTTP operation type.
	 * @param uri
	 *            The request URI.
	 * @param responseType
	 *            The class of the returned data.
	 */
	public CanvasLmsRequest(
	        final CanvasLmsClient client,
	        final Class<? extends HttpRequestBase> methodType,
	        final String uri,
	        final Class<T> responseType ) {
		super();
		setClient( client );
		setMethodType( methodType );
		setUri( uri );
		setResponseType( responseType );

		if ( !getUri().startsWith( client.getBaseUri() ) ) {
			if ( getUri().startsWith( "/" ) ) {
				setUri( getClient().getBaseUri() + getUri() );
			} else {
				setUri( getClient().getBaseUri() + "/" + getUri() );
			}
		}
	}

	public CanvasLmsClient getClient() {
		return client;
	}

	public CanvasLmsRequest<T> setClient( CanvasLmsClient client ) {
		this.client = Preconditions.checkNotNull(
		        client,
		        "Required parameter client must be specified." );
		return this;
	}

	public Class<? extends HttpRequestBase> getMethodType() {
		return methodType;
	}

	public CanvasLmsRequest<T> setMethodType( Class<? extends HttpRequestBase> methodType ) {
		this.methodType = Preconditions.checkNotNull(
		        methodType,
		        "Required parameter methodType must be specified." );
		return this;
	}

	public String getUri() {
		return uri;
	}

	public CanvasLmsRequest<T> setUri( String uri ) {
		this.uri = Preconditions.checkNotNull( uri, "Required parameter uri must be specified." );
		return this;
	}

	public String getOauthToken() {
		return oauthToken;
	}

	public CanvasLmsRequest<T> setOauthToken( String oauthToken ) {
		this.oauthToken = oauthToken;
		return this;
	}

	public HttpEntity getContent() {
		return content;
	}

	public CanvasLmsRequest<T> setContent( HttpEntity content ) {
		this.content = content;
		return this;
	}

	public Class<T> getResponseType() {
		return responseType;
	}

	public CanvasLmsRequest<T> setResponseType( Class<T> responseType ) {
		this.responseType = Preconditions.checkNotNull(
		        responseType,
		        "Required parameter responseType must be specified." );
		return this;
	}

	private HttpResponse executeUnparsed() throws CanvasLmsException {
		HttpRequestBase request = null;
		try {
			request = getMethodType().newInstance();
		} catch ( InstantiationException | IllegalAccessException e ) {
			throw new CanvasLmsException(
			        "Failed to create requeset method object.",
			        e );
		}

		try {
			request.setURI( new URI( getUri() ) );
		} catch ( URISyntaxException e ) {
			throw new CanvasLmsException( "Invalid uri", e );
		}

		if ( null != getOauthToken() ) {
			request.addHeader( "Authorization", "Bearer " + getOauthToken() );
		}

		if ( null != getContent() ) {
			// Set the content, if the request method can handle it
			if ( request instanceof HttpEntityEnclosingRequest ) {
				( (HttpEntityEnclosingRequest) request ).setEntity( getContent() );
			}
		}

		LOG.info(
		        "Send '{}' request to '{}'.",
		        getMethodType().getSimpleName(),
		        getUri(),
		        getResponseType().getSimpleName() );

		try {
			return getClient().getHttpClient().execute( request );
		} catch ( IOException e ) {
			throw new NetworkException( "Failed to execute "
			        + getMethodType().getSimpleName() + " request.", e );
		}
	}

	/**
	 * Execute this request as a {@link Pagination}.
	 * 
	 * @return
	 * @throws CanvasLmsException
	 *             Thrown if sth. gone wrong.
	 */
	public Pagination<T> executePagination() throws CanvasLmsException {
		HttpResponse response = executeUnparsed();

		String responseContent = null;
		if ( null != response.getEntity() ) {
			try {
				responseContent = EntityUtils.toString( response.getEntity() );
			} catch ( ParseException | IOException e ) {
				throw new CanvasLmsException( "Failed to read data from stream.", e );
			}
		}

		if ( LOG.isDebugEnabled() && null != response.getEntity() ) {
			LOG.debug( "Reseived response from '{}', status='{}' content='{}'.",
			        getUri(),
			        response.getStatusLine(),
			        responseContent );
		} else {
			LOG.info( "Reseived response from '{}', status='{}'.",
			        getUri(),
			        response.getStatusLine() );
		}

		isHttpStatusOkOrThrow( response.getStatusLine(), responseContent );

		Header[] links = response.getHeaders( "Link" );

		String nextUri = null;
		for ( Header link : links ) {
			String[] pageLinks = link.getValue().split( "," );
			for ( String pageLink : pageLinks ) {
				if ( pageLink.contains( "rel=\"next\"" ) ) {
					String[] split = pageLink.split( ";" );

					nextUri = split[0].trim();
					nextUri = nextUri.substring( 1, nextUri.length() - 1 );
					break;
				}
			}
		}

		return new Pagination<T>( getClient(), responseContent, nextUri, getResponseType() );
	}

	/**
	 * Executes this request for a single resource.
	 * 
	 * @return The result as the defined class.
	 * @throws CanvasLmsException
	 *             Thrown if sth. gone wrong.
	 */
	public T execute() throws CanvasLmsException {
		HttpResponse response = executeUnparsed();

		String responseContent = null;
		if ( null != response.getEntity() ) {
			try {
				responseContent = EntityUtils.toString( response.getEntity() );
			} catch ( ParseException | IOException e ) {
				throw new CanvasLmsException( "Failed to read data from stream.", e );
			}
		}

		if ( LOG.isDebugEnabled() && null != response.getEntity() ) {
			LOG.debug( "Reseived response from '{}', status='{}' content='{}'.",
			        getUri(),
			        response.getStatusLine(),
			        responseContent );
		} else {
			LOG.info( "Reseived response from '{}', status='{}'.",
			        getUri(),
			        response.getStatusLine() );
		}

		isHttpStatusOkOrThrow( response.getStatusLine(), responseContent );

		if ( Void.class.equals( getResponseType() ) ) {
			return null;
		}

		return getClient().getGson().fromJson( responseContent, getResponseType() );
	}

	/**
	 * Throw exceptions if the request was not "OK"
	 * 
	 * @param statusLine
	 *            The {@link StatusLine} of the response.
	 * @param content
	 *            The response content.
	 * @throws CanvasLmsException
	 *             Thrown exception goes here.
	 */
	private void isHttpStatusOkOrThrow( StatusLine statusLine, String content )
	        throws CanvasLmsException {
		int statusCode = statusLine.getStatusCode();

		if ( 200 <= statusCode && 300 > statusCode ) {
			return;
		} else if ( 401 == statusCode ) { // Unauthorized
			throw new AuthorizationException(
			        "Authorization error, check your accesstoken." );
		} else if ( 403 == statusCode ) { // Forbidden
			throw new AccessControlException( "Forbidden access" );
		} else if ( 404 == statusCode ) { // Not Found
			throw new NotFoundException( "Resource not found" );
		} else {
			throw new CanvasLmsException( "Http error: " + statusCode );
		}
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(
		        client,
		        methodType,
		        uri,
		        oauthToken,
		        content,
		        responseType );
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

		CanvasLmsRequest<?> other = (CanvasLmsRequest<?>) obj;

		return Objects.equal( this.client, other.client ) &&
		        Objects.equal( this.oauthToken, other.oauthToken ) &&
		        Objects.equal( this.uri, other.uri ) &&
		        Objects.equal( this.methodType, other.methodType ) &&
		        Objects.equal( this.content, other.content ) &&
		        Objects.equal( this.responseType, other.responseType );
	}

	@Override
	public String toString() {
		return Objects.toStringHelper( this )
		        .add( "client", client )
		        .add( "method", methodType.getSimpleName() )
		        .add( "uri", uri )
		        .add( "has_accesstoken", null != oauthToken )
		        .add( "has_content", null != content )
		        .add( "response_type", responseType.getSimpleName() )
		        .toString();
	}

}
