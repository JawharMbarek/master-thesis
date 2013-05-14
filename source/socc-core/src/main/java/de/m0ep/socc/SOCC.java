package de.m0ep.socc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.ServiceLoader;
import java.util.Set;

import org.ontoware.rdf2go.RDF2Go;
import org.ontoware.rdf2go.model.Model;
import org.purl.dc.terms.DCTermsVocabulary;
import org.rdfs.sioc.SIOCVocabulary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.xmlns.foaf.FOAFVocabulary;

import de.m0ep.socc.config.SOCCConfigConnectorEntry;
import de.m0ep.socc.config.SOCCConfiguration;
import de.m0ep.socc.exceptions.ConnectorException;

public class SOCC {
    private static final Logger LOG = LoggerFactory.getLogger(SOCC.class);

    private Map<String, IConnectorFactory> factories = new HashMap<String, IConnectorFactory>();
    private Map<String, IConnector> connectorMap = new HashMap<String, IConnector>();
    private Map<String, String> connectorFactoryMapping = new HashMap<String, String>();

    private Model model;

    public SOCC(final Model model) {
	this.model = Preconditions.checkNotNull(model, "Model can not be null");
	Preconditions.checkArgument(model.isOpen(), "Model must be open");

	loadFactories();
    }

    public SOCC(final Model model, final SOCCConfiguration configuration) {
	this(model);
	Preconditions.checkNotNull(configuration,
		"Confiuration can not be null");

	for (SOCCConfigConnectorEntry connectorEntry : configuration
		.getConnectors()) {

	    String factoryId = connectorEntry.getFactoryId();
	    if (factories.containsKey(factoryId)) {
		try {
		    IConnectorFactory factory = factories.get(factoryId);
		    IConnector connector = factory.createConnector(
			    connectorEntry.getId(), getModel(),
			    connectorEntry.getParameters());

		    try {
			connector.connect();
		    } catch (ConnectorException e) {
			LOG.error("Failed to connect " + connector.getId(), e);
		    }

		    connectorMap.put(connector.getId(), connector);
		    connectorFactoryMapping.put(connector.getId(),
			    factory.getId());
		} catch (ConnectorException e) {
		    LOG.error("Failed to create connector from configuration",
			    e);
		}
	    } else {
		LOG.warn("No ConnectorFactory found with id {}",
			connectorEntry.getFactoryId());
	    }
	}
    }

    public void loadFactories() {
	factories = new HashMap<String, IConnectorFactory>();
	ServiceLoader<IConnectorFactory> loader = ServiceLoader
		.load(IConnectorFactory.class);

	for (IConnectorFactory factory : loader) {
	    factories.put(factory.getId(), factory);
	}
    }

    /**
     * Create a new {@link IConnector} with <code>id</code> and
     * <code>parameters</code> from the factory with <code>factoryId</code>.
     * 
     * @param factoryId
     *            FactoryId from where the connector should be created.
     * @param id
     *            Id of the connector.
     * @param parameters
     *            Parameters for the connector.
     * @return A new {@link IConnector}
     * 
     * @throws NullPointerException
     *             Thrown if one or more parameter are null.
     * @throws IllegalArgumentException
     *             Thrown if <code>factoryId</code> or <code>id</code> are empty
     *             or if there is already a connector with this id.
     * @throws ConnectorException
     *             Thrown if there is an error while creating the connector.
     * 
     */
    public IConnector createConnector(final String factoryId, final String id,
	    final Map<String, Object> parameters) throws ConnectorException {
	Preconditions.checkNotNull(factoryId, "FactoryId can not be null.");
	Preconditions.checkArgument(!factoryId.isEmpty(),
		"FactoryId can not be empty.");
	Preconditions.checkArgument(factories.containsKey(factoryId),
		"No factory with this id exists.");
	Preconditions.checkNotNull(id, "Id can not be null.");
	Preconditions.checkArgument(!id.isEmpty(), "Id can not be empty.");
	Preconditions.checkArgument(!connectorMap.containsKey(id),
		"There is already a connector with this id.");

	IConnectorFactory factory = factories.get(factoryId);
	IConnector connector = factory.createConnector(id, getModel(),
		parameters);

	connectorMap.put(connector.getId(), connector);
	connectorFactoryMapping.put(connector.getId(), factory.getId());

	return connector;
    }

    /**
     * Return a {@link Collection} of all created Connectors.
     * 
     * @return Unmodifiable {@link Collection} of {@link IConnector}s
     */
    public Collection<IConnector> getConnectors() {
	return Collections.unmodifiableCollection(this.connectorMap.values());
    }

    /**
     * Get the ids of all created connectors.
     * 
     * @return A {@link Set} of all connector ids.
     */
    public Set<String> getConnectorIds() {
	return this.connectorMap.keySet();
    }

    /**
     * Returns the {@link IConnector} with this <code>id</code>
     * 
     * @param id
     *            Id of the wanted connector.
     * 
     * @return {@link IConnector} of the connector with this id.
     * 
     * @throws NoSuchElementException
     *             Thrown if there is no connector with this <code>id</code>.
     */
    public IConnector getConnector(final String id) {
	if (connectorMap.containsKey(id)) {
	    return connectorMap.get(id);
	}

	throw new NoSuchElementException("There is no connector with id = "
		+ id);
    }

    /**
     * Remove the connector with the <code>id</code>.
     * 
     * @param id
     *            Id of the connector that should be removed.
     */
    public void removeConnector(final String id) {
	if (connectorMap.containsKey(id)) {
	    connectorMap.remove(id);
	    connectorFactoryMapping.remove(id);
	}
    }

    /**
     * Get a {@link Collection} of all loaded factories.
     * 
     * @return Unmodifiable {@link Collection} of {@link IConnectorFactory}s
     */
    public Collection<IConnectorFactory> getFactories() {
	return Collections.unmodifiableCollection(factories.values());
    }

    /**
     * Returns a {@link Set} of all factory ids.
     * 
     * @return {@link Set} of factory ids.
     */
    public Set<String> getFactoryIds() {
	return this.factories.keySet();
    }

    /**
     * Returns the {@link IConnectorFactory} from the factory with this
     * <code>id</code>.
     * 
     * @param id
     *            Id of the wanted factory.
     * 
     * @return {@link IConnectorFactory} of the factory with this id.
     * 
     * @throws NoSuchElementException
     *             Thrown if there is no factory with this id.
     */
    public IConnectorFactory getFactory(final String id) {
	if (factories.containsKey(id)) {
	    return factories.get(id);
	}

	throw new NoSuchElementException("No factory found with id = " + id);
    }

    /**
     * Returns the {@link IConnectorFactory} of the factory with which the
     * connector with <code>connectorId</code> was created.
     * 
     * @param connectorId
     * 
     * @return {@link IConnectorFactory} which created <code>connectorId</code>.
     * 
     * @throws NullPointerException
     *             Thrown if connectorId is null.
     * @throws IllegalArgumentException
     *             Thrown if there is no connector with this id.
     * @throws NoSuchElementException
     *             Thrown if no factory found for this connector.
     */
    public IConnectorFactory getFactoryOfConnector(String connectorId) {
	Preconditions.checkNotNull(connectorId, "ConnectorId can not be null");
	Preconditions.checkArgument(
		connectorFactoryMapping.containsKey(connectorId),
		"No connector with this id found");

	return getFactory(connectorFactoryMapping.get(connectorId));
    }

    public SOCCConfiguration getConfiguration() {
	SOCCConfiguration result = new SOCCConfiguration();
	List<SOCCConfigConnectorEntry> connectorEntries = new ArrayList<SOCCConfigConnectorEntry>();

	for (IConnector connector : connectorMap.values()) {
	    SOCCConfigConnectorEntry entry = new SOCCConfigConnectorEntry();
	    entry.setId(connector.getId());
	    entry.setFactoryId(connectorFactoryMapping.get(connector.getId()));

	    Map<String, Object> parameters = connector.getConfiguration();
	    parameters.remove("class");
	    entry.setParameters(parameters);

	    connectorEntries.add(entry);
	}

	result.setConnectors(connectorEntries);

	return result;
    }

    /**
     * REturns the {@link Model} used with this {@link SOCC} instance.
     * 
     * @return The used RDF2Go {@link Model}.
     */
    public Model getModel() {
	return this.model;
    }

    /**
     * Create a default RDF2Go {@link Model} to use with the Social Online
     * Community Connectors.
     * 
     * These model contains the RDF namespaces for:
     * <ul>
     * <li>SIOC</li>
     * <li>FOAF</li>
     * <li>DCTerms</li>
     * </ul>
     * 
     * @return A default {@link Model}
     */
    public static Model createDefaultMemoryModel() {
	Model model = RDF2Go.getModelFactory().createModel();
	model = RDF2Go.getModelFactory().createModel();
	model.open();
	model.setNamespace("sioc", SIOCVocabulary.NS_SIOC.toString());
	model.setNamespace("foaf", FOAFVocabulary.NS_FOAF.toString());
	model.setNamespace("dcterms", DCTermsVocabulary.NS_DCTerms.toString());

	return model;
    }
}
