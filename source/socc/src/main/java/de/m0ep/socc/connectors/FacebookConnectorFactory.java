package de.m0ep.socc.connectors;

import java.util.Properties;

import org.ontoware.rdf2go.model.Model;

import de.m0ep.socc.Connector;
import de.m0ep.socc.ConnectorFactory;

public class FacebookConnectorFactory implements ConnectorFactory {

    public String getConnectorName() {
	return "Facebook";
    }

    public Connector createConnector(String id, Model model, Properties config) {
	return new FacebookConnector(id, model, config);
    }
}
