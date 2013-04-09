package de.m0ep.camel.socc;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.component.direct.DirectEndpoint;
import org.rdfs.sioc.Forum;

import de.m0ep.socc.IConnector;
import de.m0ep.socc.exceptions.ConnectorException;

public class SOCCForumEndpoint extends DirectEndpoint {

    String uri;
    IConnector connector;
    Forum forum;

    public SOCCForumEndpoint(String uri, IConnector connector, String forumId)
	    throws ConnectorException {
	this.uri = uri;
	this.connector = connector;
	this.forum = connector.getForum(forumId);
    }

    @Override
    public Consumer createConsumer(Processor processor) throws Exception {
	// TODO Auto-generated method stub
	return new SOCCForumPollingConsumer(this, processor, connector, forum);
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
