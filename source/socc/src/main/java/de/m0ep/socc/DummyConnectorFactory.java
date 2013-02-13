package de.m0ep.socc;

import java.util.Properties;

import org.ontoware.rdf2go.model.Model;

public final class DummyConnectorFactory implements ConnectorFactory {

    public String getConnectorName() {
	return "Dummy";
    }

    public Connector createConnector(String id, Model model, Properties config) {
	return new DummyConnector(id, model, config);
    }
}
