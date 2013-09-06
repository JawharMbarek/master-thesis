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

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.ontoware.rdf2go.model.Syntax;
import org.ontoware.rdf2go.model.node.URI;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.Site;
import org.rdfs.sioc.UserAccount;
import org.rdfs.sioc.services.Service;

import de.m0ep.socc.core.ISoccContext;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.exceptions.NotFoundException;

/**
 * An interface that describes a class which helps to connect different online
 * communities with each other.
 * 
 * @author Florian Müller
 */
public interface IConnector {
	public static final String DEFAULT_MESSAGE_TEMPLATE = "{author} wrote at {serviceName}: {message}";
	public static final String MESSAGE_TEMPLATE_VAR_AUTHOR_NAME = "authorName";
	public static final String MESSAGE_TEMPLATE_VAR_MESSAGE = "message";
	public static final String MESSAGE_TEMPLATE_VAR_CONNECTOR_ID = "connectorId";
	public static final String MESSAGE_TEMPLATE_VAR_SERVICE_NAME = "serviceName";
	public static final String MESSAGE_TEMPLATE_VAR_SOURCE_URI = "sourceUri";
	public static final String MESSAGE_TEMPLATE_VAR_CREATION_DATE = "creationDate";

	/**
	 * Returns the Id of this connector.
	 */
	public String getId();

	/**
	 * Returns the context in which the connector is used.
	 */
	public ISoccContext getContext();

	/**
	 * Returns the service informations of this connector.
	 */
	public Service getService();

	/**
	 * Returns the message template for unknown {@link UserAccount}s
	 */
	public String getUnknownMessageTemplate();

	/**
	 * Returns the default {@link UserAccount} of this connector.
	 */
	public UserAccount getDefaultUserAccount();

	/**
	 * Returns a {@link IClientManager} instance to manage client objects of
	 * different {@link UserAccount} for the used service.
	 */
	public <T> IClientManager<T> getClientManager();

	/**
	 * Returns an {@link IStructureReader} to get information about the
	 * structure of the used service.
	 * 
	 * @throws IllegalStateException
	 *             Thrown if the connector was not initialized.
	 */
	public <T extends IConnector> IStructureReader<T> getStructureReader();

	/**
	 * Returns an {@link IPostReader} to read {@link Post}s from the connectors
	 * service endpoint.
	 * 
	 * @throws IllegalStateException
	 *             Thrown if the connector was not initialized.
	 */
	public <T extends IConnector> IPostReader<T> getPostReader();

	/**
	 * Returns an {@link IPostWriter to write {@link Post}s to the connectors
	 * service endpoint.
	 * 
	 * @throws IllegalStateException
	 *             Thrown if the connector was not initialized.
	 */
	public <T extends IConnector> IPostWriter<T> getPostWriter();

	/**
	 * Initializes the connector. Checking the defaultUser and the service for
	 * necessary for properties like required service endpoint or authorization
	 * parameters.
	 * 
	 * @throws IllegalStateException
	 *             Throw if <code>defaultUserAccount</code> or
	 *             <code>service</code> are invalid.
	 * @throws AuthenticationException
	 *             Thrown if authorization of the default {@link UserAccount}
	 *             failed.
	 * @throws IOException
	 *             throw if there is a problem with the connection to the
	 *             service endpoint.
	 */
	public void initialize() throws AuthenticationException, IOException;

	/**
	 * Returns true if the connector was initialized, false otherwise.
	 */
	public boolean isInitialized();

	/**
	 * Shuts down the connector.
	 */
	public void shutdown();

	/**********************************************************************/

	/**
	 * An interface to read some structural information about a service of an
	 * online community.
	 * 
	 * @author Florian Müller
	 */
	public static interface IStructureReader<T extends IConnector> extends IConnectorIOComponent<T> {

		/**
		 * Gets a SIOC representation of the underlying online community site.
		 */
		public Site getSite();

		/**
		 * Returns a {@link Container} that is located at the provided
		 * {@link URI}
		 * 
		 * @param uri
		 *            {@link URI} to load the {@link Container}.
		 * 
		 * @return The {@link Container} at the <code>uri</code>.
		 * 
		 * @throws NotFoundException
		 *             Thrown if there is no {@link Container} at this
		 *             <code>uri</code>.
		 * @throws AuthenticationException
		 *             Thrown if authentication to the service failed.
		 * @throws IOException
		 *             Thrown if there is a problem reading data from the
		 *             service.
		 */
		public Container getContainer( URI uri ) throws
		        NotFoundException,
		        AuthenticationException,
		        IOException;

		/**
		 * List all first level {@link Container}s a the service.
		 * 
		 * @return {@link List} of all found {@link Container}s
		 * @throws AuthenticationException
		 *             Thrown if authentication to the service failed.
		 * @throws IOException
		 *             Thrown if there is a problem reading data from the
		 *             service.
		 */
		public List<Container> listContainer() throws
		        AuthenticationException,
		        IOException;

		public boolean hasChildContainer( URI uri );

		/**
		 * List all child {@link Container}s of the parent at the
		 * <code>parentUri</code>.
		 * 
		 * @param parentURI
		 *            The <code>parentUri</code> to search for child
		 *            {@link Container}s
		 * @return {@link List} of all found {@link Container}s.
		 * @throws AuthenticationException
		 *             Thrown if authentication to the service failed.
		 * @throws IOException
		 *             Thrown if there is a problem reading data from the
		 *             service.
		 */
		public List<Container> listContainer( URI parentURI ) throws
		        AuthenticationException,
		        IOException;

	}

	/**
	 * An interface that describes a class which is used to read data from a
	 * connectors service-endpoint.
	 * 
	 * @author Florian Müller
	 */
	public static interface IPostReader<T extends IConnector> extends IConnectorIOComponent<T> {

		public boolean hasPosts( URI uri );

		public Post readPost( URI uri ) throws
		        NotFoundException,
		        AuthenticationException,
		        IOException;

		public List<Post> pollPosts( URI sourceUri, Date since, int limit ) throws
		        AuthenticationException,
		        IOException;
	}

	/**
	 * An interface that describes a class which is used to write {@link Post}s
	 * to a connectors service-endpoint.
	 * 
	 * @author Florian Müller
	 */
	public static interface IPostWriter<T extends IConnector> extends IConnectorIOComponent<T> {
		public void writePost( URI targetUri, String rdfString, Syntax syntax ) throws
		        NotFoundException,
		        AuthenticationException,
		        IOException;
	}
}
