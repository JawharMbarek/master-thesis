package de.m0ep.socc;

import java.util.Properties;

import org.ontoware.rdf2go.model.Model;

public interface ConnectorFactory {
    public String getConnectorName();
    public Connector createConnector(String id, Model model, Properties config);
}
