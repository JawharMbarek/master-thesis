package de.m0ep.socc.connectors.moodle;

import java.util.Properties;

import org.ontoware.rdf2go.model.Model;

import de.m0ep.socc.connectors.Connector;
import de.m0ep.socc.connectors.ConnectorFactory;

public class MoodleConnectorFactory implements ConnectorFactory {

    public String getConnectorName() {
	return "Moodle";
    }

    public String getUniqueFactoryName() {
	return "MoodleConnectorFactory_2.4";
    }

    public String[] getConfigKeys() {
	return new String[] { MoodleConnector.CONFIG_MOODLE_URL,
		MoodleConnector.CONFIG_USERNAME,
		MoodleConnector.CONFIG_PASSWORD };
    }

    public Connector createConnector(String id, Model model, Properties config) {
	return new MoodleConnector(id, model, config);
    }

}
