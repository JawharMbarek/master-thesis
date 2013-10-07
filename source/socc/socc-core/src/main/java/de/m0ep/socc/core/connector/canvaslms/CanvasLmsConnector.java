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

package de.m0ep.socc.core.connector.canvaslms;

import java.io.IOException;

import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.util.Builder;
import org.rdfs.sioc.UserAccount;
import org.rdfs.sioc.services.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;

import de.m0ep.canvas.exceptions.AuthorizationException;
import de.m0ep.canvas.exceptions.NetworkException;
import de.m0ep.socc.config.ConnectorConfig;
import de.m0ep.socc.core.ISoccContext;
import de.m0ep.socc.core.connector.DefaultConnector;
import de.m0ep.socc.core.connector.IConnector;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.exceptions.NotFoundException;

/**
 * Implementation of an {@link IConnector} for Canvas.
 * 
 * @author Florian Müller
 */
public class CanvasLmsConnector extends DefaultConnector {
	private static final Logger LOG = LoggerFactory
	        .getLogger( CanvasLmsConnector.class );

	private CanvasLmsClientManager clientManager;
	private CanvasLmsStructureReader serviceStructureReader;
	private CanvasLmsPostReader postReader;
	private CanvasLmsPostWriter postWriter;

	/**
	 * Construct a new {@link CanvasLmsConnector} wich an <code>id</code>,
	 * <code>context</code>, <code>defaultUserAccount</code> and
	 * <code>service</code> objects.
	 * 
	 * @param id
	 *            The Id of the connector.
	 * @param context
	 *            The context of the connector.
	 * @param defaultUserAccount
	 *            The default user account of the connector.
	 * @param service
	 *            The service object of the Moodle service.
	 */
	public CanvasLmsConnector( final String id, final ISoccContext context,
	        final UserAccount defaultUserAccount,
	        Service service ) {
		super( id, context, defaultUserAccount, service );
	}

	/**
	 * Construct a new {@link CanvasLmsConnector} with a <code>context</code>
	 * and a connector <code>config</code>.
	 * 
	 * @param context
	 *            The context of the connector.
	 * @param config
	 *            {@link ConnectorConfig} with all other data.
	 */
	public CanvasLmsConnector( final ISoccContext context,
	        final ConnectorConfig config ) {
		super( context, config );
	}

	@Override
	@SuppressWarnings( "unchecked" )
	public CanvasLmsClientManager getClientManager() {
		return clientManager;
	}

	@Override
	@SuppressWarnings( "unchecked" )
	public CanvasLmsStructureReader getStructureReader() {
		Preconditions.checkState( isInitialized(),
		        "Connector was not initialized" );

		if ( null == serviceStructureReader ) {
			serviceStructureReader = new CanvasLmsStructureReader( this );
		}

		return serviceStructureReader;
	}

	@Override
	@SuppressWarnings( "unchecked" )
	public CanvasLmsPostReader getPostReader() {
		Preconditions.checkState( isInitialized(),
		        "Connector was not initialized" );

		if ( null == postReader ) {
			postReader = new CanvasLmsPostReader( this );
		}

		return postReader;
	}

	@Override
	@SuppressWarnings( "unchecked" )
	public CanvasLmsPostWriter getPostWriter() {
		Preconditions.checkState( isInitialized(),
		        "Connector was not initialized" );

		if ( null == postWriter ) {
			postWriter = new CanvasLmsPostWriter( this );
		}

		return postWriter;
	}

	@Override
	public void initialize() throws AuthenticationException, IOException {
		Preconditions.checkArgument(
		        service.hasServiceEndpoint(),
		        "Provided parameter service contains no ServiceEndpoint." );

		// remove '/' at the end
		URI serviceEndpointUri = getService().getServiceEndpoint().asURI();
		String uriString = serviceEndpointUri.toString();
		if ( uriString.endsWith( "/" ) ) {
			serviceEndpointUri = Builder.createURI(
			        uriString.substring(
			                0,
			                uriString.length() - 1 ) );
			getService().setServiceEndpoint( serviceEndpointUri );
		}

		try {
			clientManager = new CanvasLmsClientManager( getService(),
			        getDefaultUserAccount() );
		} catch ( Exception e ) {
			Throwables.propagateIfInstanceOf( e, AuthenticationException.class );
			Throwables.propagateIfInstanceOf( e, IOException.class );
			throw Throwables.propagate( e );
		}
		setInitialized( true );

		LOG.info( "Create CanvasLMS connector with endpoint at {}.",
		        getService().getServiceEndpoint() );
	}

	@Override
	public void shutdown() {
		serviceStructureReader = null;
		postReader = null;
		postWriter = null;
		clientManager.clear();
		clientManager = null;

		setInitialized( false );
	}

	/**
	 * Converts CanvasLms4J Exceptions, convert them to SOCC exceptions and
	 * propagates them.
	 * 
	 * @param exception
	 *            Exception to convert and propagate.
	 * @throws NotFoundException
	 *             Thrown if no resource was found at the URI
	 * @throws AuthenticationException
	 *             Thrown if there is a problem with authentication.
	 * @throws IOException
	 *             Thrown if there ist problem in communication.
	 */
	static void handleCanvasExceptions( Exception exception )
	        throws NotFoundException,
	        AuthenticationException,
	        IOException {
		if ( exception instanceof NetworkException ) {
			throw new IOException( exception );
		} else if ( exception instanceof AuthorizationException ) {
			throw new AuthenticationException( exception );
		} else if ( exception instanceof de.m0ep.canvas.exceptions.NotFoundException ) {
			throw new NotFoundException( exception );
		}

		throw Throwables.propagate( exception );
	}
}
