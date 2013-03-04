package de.m0ep.socc.connectors.google.plus;

import java.util.Properties;

import org.ontoware.rdf2go.model.Model;

import de.m0ep.socc.connectors.Connector;
import de.m0ep.socc.connectors.ConnectorFactory;

public class GooglePlusConnectorFactory implements ConnectorFactory {

    public String getConnectorName() {
	return "Google+";
    }

    public String getUniqueFactoryName() {
	return "GooglePlusConnectorFactory_1.0";
    }

    public String[] getConfigKeys() {
	return new String[] { GooglePlusConnector.CONFIG_CLIENT_ID,
		GooglePlusConnector.CONFIG_CLIENT_SECRET,
		GooglePlusConnector.CONFIG_ACCESS_TOKEN,
		GooglePlusConnector.CONFIG_REFRESH_TOKEN };
    }

    public Connector createConnector(String id, Model model, Properties config) {
	return new GooglePlusConnector(id, model, config);
    }

}
