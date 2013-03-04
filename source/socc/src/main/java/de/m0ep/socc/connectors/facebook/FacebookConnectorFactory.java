package de.m0ep.socc.connectors.facebook;

import java.util.Properties;

import org.ontoware.rdf2go.model.Model;

import de.m0ep.socc.connectors.Connector;
import de.m0ep.socc.connectors.ConnectorFactory;

public class FacebookConnectorFactory implements ConnectorFactory {

    public String getConnectorName() {
	return "Facebook";
    }

    public String getUniqueFactoryName() {
	return "FacebookConnectorFactory_1.0";
    }

    public String[] getConfigKeys() {
	return new String[] { FacebookConnector.CONFIG_ACCESS_TOKEN,
		FacebookConnector.CONFIG_CLIENT_ID,
		FacebookConnector.CONFIG_CLIENT_SECRET };
    }

    public Connector createConnector(String id, Model model, Properties config) {
	return new FacebookConnector(id, model, config);
    }
}
