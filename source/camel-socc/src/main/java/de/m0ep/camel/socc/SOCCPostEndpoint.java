package de.m0ep.camel.socc;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.component.direct.DirectEndpoint;
import org.rdfs.sioc.Post;

import de.m0ep.socc.IConnector;
import de.m0ep.socc.exceptions.ConnectorException;

public class SOCCPostEndpoint extends DirectEndpoint {

    String uri;
    IConnector connector;
    Post post;
    SOCCComponentConfiguration configuration;

    public SOCCPostEndpoint(String uri, IConnector connector,
	    SOCCComponentConfiguration configuration) throws ConnectorException {
	this.uri = uri;
	this.connector = connector;
	this.post = connector.getPost(configuration.getPostId());
	this.configuration = configuration;
    }

    @Override
    public Consumer createConsumer(Processor processor) throws Exception {
	return super.createConsumer(processor);
    }

    @Override
    public Producer createProducer() throws Exception {
	return super.createProducer();
    }

    @Override
    public String getEndpointUri() {
	return uri;
    }
}
