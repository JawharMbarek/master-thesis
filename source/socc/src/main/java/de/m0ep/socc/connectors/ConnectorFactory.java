package de.m0ep.socc.connectors;

import java.util.Properties;

import org.ontoware.rdf2go.model.Model;

/**
 * Interface of a {@link ConnectorFactory} to create new {@link Connector}s
 * 
 * @author Florian Müller
 * 
 */
public interface ConnectorFactory {
    /**
     * Return a human readable name for this connectors
     * 
     * @return Name for this connectors
     */
    public String getConnectorName();

    /**
     * Create a new {@link Connector}.
     * 
     * @param id
     *            Id of this connector.
     * @param model
     *            RDF2Go {@link Model} to use as sioc store.
     * @param config
     *            {@link Properties} object with configuration parameters for
     *            the new {@link Connector}.
     * @return
     */
    public Connector createConnector(String id, Model model, Properties config);
}
