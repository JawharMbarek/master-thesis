package de.m0ep.socc;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.ontoware.rdf2go.model.Model;
import org.rdfs.sioc.UserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import de.m0ep.sioc.service.auth.Service;
import de.m0ep.socc.config.IConfiguration;
import de.m0ep.socc.config.IConfiguration.IEntry;
import de.m0ep.socc.exceptions.ConnectorException;
import de.m0ep.socc.exceptions.NotFoundException;

public class SOCCContext implements ISOCCContext {
    private static final Logger LOG = LoggerFactory
	    .getLogger(SOCCContext.class);

    private Model dataModel;
    private Map<String, IConnector> connectors;

    public SOCCContext() {
	connectors = new HashMap<>();
    }

    public SOCCContext(final Model dataModel) {
	this();
	Preconditions.checkNotNull(dataModel, "DataModel can not be null");
	Preconditions.checkArgument(
		dataModel.isOpen(),
		"DataModel should be open");

	this.dataModel = dataModel;
    }

    public SOCCContext(final Model dataModel, final IConfiguration configuration) {
	this(dataModel);
	Preconditions.checkNotNull(
		configuration,
		"Configuration can not be null");

	loadConfiguration(configuration);
    }

    private void loadConfiguration(final IConfiguration configuration) {
	Preconditions.checkNotNull(
		configuration,
		"Configuration can not be null");
	for (IEntry entry : configuration.getEntries()) {
	    IConnectorFactory factory = null;
	    try {
		factory = ConnectorFactoryManager
			.getInstance()
			.getFactory(entry.getFactoryId());
	    } catch (NotFoundException e) {
		LOG.warn("Missing factory", e);
		continue;
	    }

	    IConnector connector = null;
	    try {
		connector = factory
			.createNewInstance(
				entry.getId(),
				this,
				Service.getInstance(getDataModel(), entry
					.getService()),
				UserAccount.getInstance(
					getDataModel(), entry.getUserAccount()));
	    } catch (ConnectorException e) {
		LOG.warn("Failed to create connector.", e);
		continue;
	    } catch (NullPointerException e) {
		LOG.warn(
			"Some entry values are null or lead to a null object.",
			e);
		continue;
	    } catch (IllegalArgumentException e) {
		LOG.warn("Some arguments are illegal.", e);
		continue;
	    }

	    if (null != connector) {
		addConnector(connector);

		try {
		    connector.connect();
		} catch (ConnectorException e) {
		    LOG.warn("Failed to connect " + connector.getId(), e);
		    continue;
		}
	    }
	}
    }

    @Override
    public Model getDataModel() {
	return dataModel;
    }

    @Override
    public Collection<IConnector> getConnectors() {
	return Collections.unmodifiableCollection(connectors.values());
    }

    @Override
    public IConnector getConnector(final String id) throws NotFoundException {
	if (null == id || !connectors.containsKey(id)) {
	    throw new NotFoundException("No connector found with id=" + id);
	}

	return connectors.get(id);
    }

    @Override
    public IConnector getConnector(final UserAccount userAccount)
	    throws NotFoundException {
	for (IConnector connector : connectors.values()) {
	    if (userAccount.equals(connector.getUserAccount())) {
		return connector;
	    }
	}

	throw new NotFoundException(
		"No connector found for the user " +
			userAccount.getAccountName());
    }

    @Override
    public void addConnector(final IConnector connector) {
	Preconditions.checkNotNull(connector, "Connector can not be null");

	connectors.put(connector.getId(), connector);
    }

    @Override
    public void removeConnector(final IConnector connector) {
	connectors.remove(connector);
    }
}
