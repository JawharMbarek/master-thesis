package de.m0ep.camel.socc;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.component.direct.DirectEndpoint;
import org.rdfs.sioc.Thread;

import de.m0ep.socc.IConnector;
import de.m0ep.socc.exceptions.ConnectorException;

public class SOCCThreadEndpoint extends DirectEndpoint {

    String uri;
    IConnector connector;
    Thread thread;
    SOCCComponentConfiguration configuration;

    public SOCCThreadEndpoint(String uri, IConnector connector,
	    SOCCComponentConfiguration configuration) throws ConnectorException {
	this.uri = uri;
	this.connector = connector;
	this.thread = connector.getThread(configuration.getThreadId());
	this.configuration = configuration;
    }

    @Override
    public Consumer createConsumer(Processor processor) throws Exception {
	return new SOCCThreadPollingConsumer(this, processor, connector, thread);
    }

    @Override
    public Producer createProducer() throws Exception {
	return new SOCCThreadProducer(this, connector, thread);
    }

    @Override
    public String getEndpointUri() {
	return uri;
    }
}
