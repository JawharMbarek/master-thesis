package de.m0ep.socc.connectors.google.plus;

import java.util.Properties;

import org.ontoware.rdf2go.model.Model;

import de.m0ep.socc.connectors.Connector;
import de.m0ep.socc.connectors.ConnectorFactory;

public class GooglePlusConnectorFactory implements ConnectorFactory {

    @Override
    public String getConnectorName() {
	return "Google+";
    }

    @Override
    public Connector createConnector(String id, Model model, Properties config) {
	return new GooglePlusConnector(id, model, config);
    }

}
