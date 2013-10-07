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

import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.util.Builder;
import org.rdfs.sioc.UserAccount;
import org.rdfs.sioc.services.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import de.m0ep.socc.config.ConnectorConfig;
import de.m0ep.socc.core.ISoccContext;
import de.m0ep.socc.core.connector.DefaultConnector;
import de.m0ep.socc.core.connector.IConnector;
import de.m0ep.socc.core.exceptions.AuthenticationException;

/**
 * Implementation of an {@link IConnector} for Moodle 2.x
 * 
 * @author Florian Müller
 * 
 */
public class Moodle2Connector extends DefaultConnector {
	private static final Logger LOG = LoggerFactory.getLogger( Moodle2Connector.class );

	private Moodle2ClientManager serviceClientManager;
	private Moodle2StructureReader serviceStructureReader;
	private Moodle2PostReader postReader;
	private Moodle2PostWriter postWriter;

	/**
	 * Construct a new {@link Moodle2Connector} wich an <code>id</code>,
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
	public Moodle2Connector( final ISoccContext context, final ConnectorConfig config ) {
		super( context, config );
	}

	/**
	 * Construct a new {@link Moodle2Connector} with a <code>context</code> and
	 * a connector <code>config</code>.
	 * 
	 * @param context
	 *            The context of the connector.
	 * @param config
	 *            {@link ConnectorConfig} with all other data.
	 */
	public Moodle2Connector(
	        final String id,
	        final ISoccContext context,
	        final UserAccount defaultUserAccount,
	        final Service service ) {
		super( id, context, defaultUserAccount, service );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public Moodle2ClientManager getClientManager() {
		return serviceClientManager;
	}

	@Override
	@SuppressWarnings( "unchecked" )
	public Moodle2StructureReader getStructureReader() {
		Preconditions.checkState( isInitialized(),
		        "Connector was not initialized" );

		if ( null == serviceStructureReader ) {
			serviceStructureReader = new Moodle2StructureReader( this );
		}

		return serviceStructureReader;
	}

	@Override
	@SuppressWarnings( "unchecked" )
	public Moodle2PostReader getPostReader() {
		Preconditions.checkState( isInitialized(),
		        "Connector was not initialized" );

		if ( null == postReader ) {
			postReader = new Moodle2PostReader( this );
		}

		return postReader;
	}

	@Override
	@SuppressWarnings( "unchecked" )
	public Moodle2PostWriter getPostWriter() {
		Preconditions.checkState( isInitialized(),
		        "Connector was not initialized" );

		if ( null == postWriter ) {
			postWriter = new Moodle2PostWriter( this );
		}

		return postWriter;
	}

	@Override
	public void initialize() throws AuthenticationException, IOException {
		Preconditions.checkArgument(
		        service.hasServiceEndpoint(),
		        "The service contains no required serviceEndpoint." );

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

		LOG.info( "Create Moodle connector with endpoint at {}.",
		        serviceEndpointUri );

		try {
			serviceClientManager = new Moodle2ClientManager( getService(),
			        getDefaultUserAccount() );
		} catch ( Exception e ) {
			throw new AuthenticationException(
			        "Failed to create and login default client.", e );
		}

		serviceStructureReader = new Moodle2StructureReader( this );
		setInitialized( true );
	}

	@Override
	public void shutdown() {
		for ( Object obj : serviceClientManager.getAll() ) {
			Moodle2ClientWrapper client = (Moodle2ClientWrapper) obj;
			client.getBindingStub().logout(
			        client.getAuthClient(),
			        client.getSessionKey() );
		}
		serviceClientManager.clear();

		Moodle2ClientWrapper client = serviceClientManager
		        .getDefaultClient();
		client.getBindingStub().logout(
		        client.getAuthClient(),
		        client.getSessionKey() );

		setInitialized( false );
	}
}
