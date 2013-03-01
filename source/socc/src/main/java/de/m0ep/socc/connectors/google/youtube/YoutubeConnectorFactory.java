package de.m0ep.socc.connectors.google.youtube;

import java.util.Properties;

import org.ontoware.rdf2go.model.Model;

import de.m0ep.socc.connectors.Connector;
import de.m0ep.socc.connectors.ConnectorFactory;

public class YoutubeConnectorFactory implements ConnectorFactory {

    @Override
    public String getConnectorName() {
	return "Youtube";
    }

    @Override
    public String getUniqueFactoryName() {
	return "YoutubeConnectorFactory_v2";
    }

    @Override
    public String[] getConfigKeys() {
	return new String[] { YoutubeConnector.CONFIG_USERNAME,
		YoutubeConnector.CONFIG_PASSWORD };
    }

    @Override
    public Connector createConnector(String id, Model model, Properties config) {
	return new YoutubeConnector(id, model, config);
    }

}
