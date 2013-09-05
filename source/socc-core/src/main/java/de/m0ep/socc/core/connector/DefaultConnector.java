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

package de.m0ep.socc.core.connector;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.Statement;
import org.ontoware.rdf2go.model.node.Variable;
import org.ontoware.rdf2go.util.Builder;
import org.rdfs.sioc.UserAccount;
import org.rdfs.sioc.services.Service;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import de.m0ep.socc.config.ConnectorConfig;
import de.m0ep.socc.core.ISoccContext;
import de.m0ep.socc.core.exceptions.ConnectorException;
import de.m0ep.socc.core.exceptions.NotFoundException;

/**
 * Abstract connector that implements some basic methods which may be used in
 * all subclasses.
 * 
 * @author Florian Müller
 */
public abstract class DefaultConnector implements IConnector {
	public static final String DEFAULT_MESSAGE_TEMPLATE = "{author} wrote: {message}";
	public static final String MESSAGE_TEMPLATE_VAR_AUTHOR = "author";
	public static final String MESSAGE_TEMPLATE_VAR_MESSAGE = "message";
	public static final String MESSAGE_TEMPLATE_VAR_CONNECTOR_ID = "connectorId";
	public static final String MESSAGE_TEMPLATE_VAR_SERVICE = "service";

	protected String id;
	protected ISoccContext context;
	protected UserAccount defaultUserAccount;
	protected Service service;
	protected boolean isInitialized;
	protected String messageTemplateString;

	private static Map<String, IConnector> connectorMap;

	static {
		connectorMap = Maps.newHashMap();
	}

	/**
	 * Creates an {@link IConnector} with the provided <code>id</code>.
	 * 
	 * @param context
	 * @param connectorId
	 * @return
	 * @throws ConnectorException
	 *             Thrown if there is a Problem creating the {@link IConnector}
	 *             from the stored {@link ConnectorConfig}.
	 * @throws NotFoundException
	 *             Thrown if there is a problem reading {@link ConnectorConfig}
	 *             data from the <code>context</code>.
	 * @throws NullPointerException
	 *             Thrown if <code>context</code> or <code>id</code> are
	 *             <code>null</code>.
	 */
	public static IConnector createConnector(
	        final ISoccContext context,
	        final String connectorId )
	        throws ConnectorException,
	        NotFoundException {
		Preconditions.checkNotNull( context,
		        "Required parameter context must be specified." );
		Preconditions.checkNotNull( connectorId,
		        "Required parameter connectorId must be specified." );

		IConnector result = connectorMap.get( connectorId );

		if ( null == result ) {
			ConnectorConfig cfg = readConnectorConfig( context.getModel(), connectorId );
			result = createConnector( context, cfg );
		}

		return result;
	}

	/**
	 * Creates a new {@link IConnector} with a {@link ISoccContext} from a
	 * {@link ConnectorConfig}.
	 * 
	 * @param context
	 * @param config
	 * @return
	 * @throws ConnectorException
	 *             Throw if there is a problem creating the connector from the
	 *             {@link ConnectorConfig}.
	 * @throws NullPointerException
	 *             Thrown if the parameter <code>context</code> or
	 *             <code>config</code> are <code>null</code>.
	 * @throws IllegalArgumentException
	 *             Thrown if the parameter <code>config</code> has no
	 *             <b>connectorClassName<b> property.
	 */
	public static IConnector createConnector(
	        final ISoccContext context,
	        final ConnectorConfig config )
	        throws ConnectorException {
		Preconditions.checkNotNull( context,
		        "Required parameter context must be specified." );
		Preconditions.checkNotNull( config,
		        "Required parameter config must be specified." );
		Preconditions.checkArgument( config.hasConnectorClassName(),
		        "The parameter config has no connectorClassName." );

		// check if we already have this connector
		if ( config.hasId() ) {
			String id = config.getId();
			if ( connectorMap.containsKey( id ) ) {
				return connectorMap.get( id );
			}
		}

		String connectorClassName = config.getConnectorClassName();
		Class<?> connectorClass = null;
		try {
			connectorClass = Class.forName( connectorClassName );
		} catch ( ClassNotFoundException e ) {
			throw new ConnectorException(
			        "Failed to create connector from className: '"
			                + connectorClassName
			                + "'" );
		}

		Constructor<?> constructor = null;
		try {
			constructor = connectorClass.getConstructor( ISoccContext.class, ConnectorConfig.class );
		} catch ( NoSuchMethodException e ) {
			throw new ConnectorException(
			        "The connector "
			                + connectorClass.getSimpleName()
			                + " has no required cunstructor 'public "
			                + connectorClass.getSimpleName()
			                + "("
			                + ISoccContext.class.getSimpleName()
			                + ", "
			                + ConnectorConfig.class.getSimpleName()
			                + ")'.", e );
		} catch ( SecurityException e ) {
			throw new ConnectorException(
			        "Can't access the constructor of "
			                + connectorClass.getSimpleName()
			                + " because of some security issues.", e );
		}

		try {
			return (IConnector) constructor.newInstance( context, config );
		} catch ( InstantiationException
		        | IllegalAccessException
		        | IllegalArgumentException
		        | InvocationTargetException e ) {
			throw new ConnectorException(
			        "Failed to invoke cunstructor 'public "
			                + connectorClass.getSimpleName()
			                + "("
			                + ISoccContext.class.getSimpleName()
			                + ", "
			                + ConnectorConfig.class.getSimpleName()
			                + ")'.", e );
		}
	}

	/**
	 * Reads a {@link ConnectorConfig} with the <code>connectorId</code> from a
	 * <code>model</code>.
	 * 
	 * @param model
	 *            The model where to search for the {@link ConnectorConfig}.
	 * @param connectorId
	 *            The id of the wanted {@link ConnectorConfig}.
	 * @return A {@link ConnectorConfig} with the wanted
	 *         <code>connectorId</code>.
	 * @throws NotFoundException
	 *             Thrown if no {@link ConnectorConfig} was found with this
	 *             <code>connectorId</code>.
	 * @throws NullPointerException
	 *             Thrown if <code>model</code> or <code>connectorId</code> are
	 *             <code>null</code>.
	 * @throws IllegalStateException
	 *             Thrown if the <code>model</code> was not opened.
	 */
	public static ConnectorConfig readConnectorConfig(
	        final Model model,
	        final String connectorId )
	        throws NotFoundException {
		Preconditions.checkNotNull( model,
		        "Required parameter model must be specified." );
		Preconditions.checkState( model.isOpen(),
		        "The parameter model is not open." );
		Preconditions.checkNotNull( connectorId,
		        "Required parameter connectorId must be specified." );

		ClosableIterator<Statement> cfgIter = model.findStatements(
		        Variable.ANY,
		        ConnectorConfig.ID,
		        Builder.createPlainliteral( connectorId ) );

		if ( cfgIter.hasNext() ) {
			Statement statement = cfgIter.next();
			return ConnectorConfig.getInstance( model, statement.getSubject() );
		} else {
			throw new NotFoundException(
			        "No ConnectorConfig found for connector with id "
			                + connectorId
			                + "." );
		}
	}

	/**
	 * Default constructor
	 */
	private DefaultConnector() {
		this.id = null;
		this.context = null;
		this.defaultUserAccount = null;
		this.service = null;
		this.isInitialized = false;
		this.messageTemplateString = DEFAULT_MESSAGE_TEMPLATE;
	}

	/**
	 * Constructs a new instance from a {@link ConnectorConfig}.
	 * 
	 * @param context
	 * @param config
	 * @throws NullPointerException
	 *             Thrown if one or more parameter are <code>null</code>.
	 * @throws IllegalArgumentException
	 *             Thrown if <code>config</code> contains no <code>id</code>,
	 *             <code>defaultUser</code> or <code>service</code>.
	 */
	public DefaultConnector(
	        final ISoccContext context,
	        final ConnectorConfig config ) {
		this();
		this.context = Preconditions.checkNotNull(
		        context,
		        "Required parameter context must be specified." );

		Preconditions.checkNotNull( config,
		        "Required parameter config must be specified." );

		Preconditions.checkArgument( config.hasId(),
		        "Provides parameter config contains no id." );
		this.id = config.getId();

		Preconditions.checkArgument(
		        config.hasDefaultUserAccount(),
		        "Provided parameter config contains no default UserAccount." );
		this.defaultUserAccount = config.getDefaultUserAccount();

		Preconditions.checkArgument( config.hasService(),
		        "Provided parameter config contains no service" );
		this.service = config.getService();

		if ( config.hasUnknownMessageTemplate() ) {
			this.messageTemplateString = config.getUnknownMessageTemplate();
		}
	}

	/**
	 * Constructs a new instance with an <code>id</code>, <code>context</code>,
	 * <code>defaultUserAccount</code> and a <code>service</code>.
	 * 
	 * @param id
	 * @param context
	 * @param defaultUserAccount
	 * @param service
	 * @throws NullPointerException
	 *             Thrown if one or more parameter are <code>null</code>.
	 * @throws IllegalArgumentException
	 *             Thrown if <code>id</code> is empty.
	 */
	public DefaultConnector(
	        final String id,
	        final ISoccContext context,
	        final UserAccount defaultUserAccount,
	        final Service service ) {
		this();
		this.id = Preconditions.checkNotNull(
		        id,
		        "Required parameter id must be specified." );
		Preconditions.checkArgument(
		        !id.isEmpty(),
		        "Required parameter id may not be empty." );

		this.context = Preconditions.checkNotNull(
		        context,
		        "Required parameter context must be specified." );

		this.defaultUserAccount = Preconditions.checkNotNull(
		        defaultUserAccount,
		        "Required parameter defaultUserAccount must be specified." );

		this.service = Preconditions.checkNotNull(
		        service,
		        "Required parameter service must be specified." );
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public ISoccContext getContext() {
		return context;
	}

	@Override
	public UserAccount getDefaultUserAccount() {
		return defaultUserAccount;
	}

	@Override
	public Service getService() {
		return service;
	}

	@Override
	public String getUnknownMessageTemplate() {
		return messageTemplateString;
	}

	@Override
	public boolean isInitialized() {
		return isInitialized;
	}

	/**
	 * Sets initialized to the provided value.
	 * 
	 * @param isInitialized
	 */
	protected void setInitialized( final boolean isInitialized ) {
		this.isInitialized = isInitialized;
	}
}
