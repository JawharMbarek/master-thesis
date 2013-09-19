/*
 * The MIT License (MIT) Copyright © 2013 Florian Müller Permission is hereby
 * granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the “Software”), to deal in the Software
 * without restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of the
 * Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions: The above copyright notice and this
 * permission notice shall be included in all copies or substantial portions of
 * the Software. THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO
 * EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES
 * OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

package de.m0ep.canvas;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

public abstract class AbstractEndpoint implements IEndpoint {
	protected CanvasLmsClient client;
	protected String endpoint;
	protected String parentEndpointPath;

	public AbstractEndpoint() {

	}

	@Override
	public CanvasLmsClient getClient() {
		return client;
	}

	@Override
	public void setClient( CanvasLmsClient client ) {
		this.client = Preconditions.checkNotNull(
		        client,
		        "Required parameter client must be specified." );
	}

	@Override
	public String getEndpointPath() {
		return endpoint;
	}

	@Override
	public void setEndpointPath( String endpoint ) {
		this.endpoint = Preconditions.checkNotNull(
		        endpoint,
		        "Required parameter endpoint must be specified." );
	}

	@Override
	public String getParentEndpointPath() {
		return Strings.nullToEmpty( parentEndpointPath );
	}

	@Override
	public void setParentEndpointPath( String parentEndpointPath ) {
		this.parentEndpointPath = Strings.nullToEmpty( parentEndpointPath );
	}

	public void initializeRequest( final CanvasLmsRequest<?> request ) {
		request.setOauthToken( getClient().getOAuthToken() );
	}

	@Override
	public int hashCode() {
		return Objects.hashCode( client, endpoint, parentEndpointPath );
	}

	@Override
	public String toString() {
		return Objects.toStringHelper( this )
		        .add( "client", client )
		        .add( "endpoint", endpoint )
		        .add( "parentEndmpointPath", parentEndpointPath )
		        .toString();
	}
}
