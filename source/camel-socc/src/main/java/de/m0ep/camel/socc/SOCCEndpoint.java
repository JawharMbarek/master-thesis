package de.m0ep.camel.socc;

import java.util.List;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.component.direct.DirectEndpoint;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Thread;

import com.google.common.base.Preconditions;

import de.m0ep.socc.IConnector;
import de.m0ep.socc.exceptions.ConnectorException;

public class SOCCEndpoint extends DirectEndpoint {

    IConnector connector;
    String uri;
    Thread thread;

    public SOCCEndpoint(String uri, IConnector connector,
	    String destinationType, String destinationId) {
	this.uri = Preconditions.checkNotNull(uri);
	this.connector = Preconditions.checkNotNull(connector);

	try {
	    if ("thread".equals(destinationType)) {
		List<Forum> forums = connector.getForums();

		for (Forum forum : forums) {
		    List<Thread> threads = connector.getThreads(forum);

		    for (Thread thread : threads) {
			if (destinationId.equals(thread.getId())) {
			    System.err.println(thread.toSPARQL());
			    this.thread = thread;
			    return;
			}
		    }
		}

	    }
	} catch (ConnectorException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public Producer createProducer() throws Exception {
	// TODO Auto-generated method stub
	return new SOCCProducer(this);
    }

    @Override
    public Consumer createConsumer(Processor processor) throws Exception {
	return new SOCCConsumer(this, processor, connector, thread);
    }

    @Override
    public String getEndpointUri() {
	System.err.println("getEndpoint");
	return uri;
    }
}
