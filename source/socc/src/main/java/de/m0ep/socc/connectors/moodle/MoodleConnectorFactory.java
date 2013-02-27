package de.m0ep.socc.connectors.moodle;

import java.util.Properties;

import org.ontoware.rdf2go.model.Model;

import de.m0ep.socc.connectors.Connector;
import de.m0ep.socc.connectors.ConnectorFactory;

public class MoodleConnectorFactory implements ConnectorFactory {

    @Override
    public String getConnectorName() {
	return "Moodle";
    }

    @Override
    public Connector createConnector(String id, Model model, Properties config) {
	return new MoodleConnector(id, model, config);
    }

}
