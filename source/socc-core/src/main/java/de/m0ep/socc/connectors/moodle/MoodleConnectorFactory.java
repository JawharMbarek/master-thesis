package de.m0ep.socc.connectors.moodle;

import de.m0ep.socc.IConnector;
import de.m0ep.socc.IConnectorFactory;

/**
 * ConnectorFactory to create new instances of an Moodle 2.4 connector.
 * 
 * @author Florian Müller
 * 
 */
public class MoodleConnectorFactory implements IConnectorFactory {

    @Override
    public String getId() {
	return "MoodleConnectorFactory2.4";
    }

    @Override
    public String getLabel() {
	return "Moodle 2.4";
    }

    @Override
    public IConnector createNewInstance() {
	return new MoodleConnector();
    }
}
