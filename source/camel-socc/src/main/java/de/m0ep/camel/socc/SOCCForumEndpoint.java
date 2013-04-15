package de.m0ep.camel.socc;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.component.direct.DirectEndpoint;
import org.apache.camel.impl.ScheduledPollConsumer;
import org.rdfs.sioc.Forum;

import de.m0ep.socc.IConnector;
import de.m0ep.socc.exceptions.ConnectorException;

public class SOCCForumEndpoint extends DirectEndpoint {

    String uri;
    IConnector connector;
    Forum forum;
    SOCCComponentConfiguration configuration;

    public SOCCForumEndpoint(String uri, IConnector connector,
	    SOCCComponentConfiguration configuration) throws ConnectorException {
	this.uri = uri;
	this.connector = connector;
	this.forum = connector.getForum(configuration.getForumId());
	this.configuration = configuration;
    }

    @Override
    public Consumer createConsumer(Processor processor) throws Exception {
	ScheduledPollConsumer consumer = new SOCCForumPollingConsumer(this,
		processor, connector, forum);
	consumer.setDelay(configuration.getDelay());
	return consumer;
    }

    @Override
    public Producer createProducer() throws Exception {
	return new SOCCForumProducer(this, connector, forum);
    }

    @Override
    public String getEndpointUri() {
	return uri;
    }
}
