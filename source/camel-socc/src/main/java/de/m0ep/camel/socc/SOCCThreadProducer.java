package de.m0ep.camel.socc;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.impl.DefaultProducer;
import org.ontoware.rdf2go.model.node.Resource;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.Thread;

import de.m0ep.socc.IConnector;

public class SOCCThreadProducer extends DefaultProducer {

    IConnector connector;
    Thread thread;

    public SOCCThreadProducer(SOCCThreadEndpoint soccThreadEndpoint,
	    IConnector connector, Thread thread) {
	super(soccThreadEndpoint);

	this.connector = connector;
	this.thread = thread;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
	Message msg = exchange.getIn();

	RDFSClass clazz = msg.getBody(RDFSClass.class);
	Resource res = clazz.get(0).getSubject();

	connector.getModel().dump();

	if (Post.hasInstance(connector.getModel(), res)) {
	    System.err.println("write post");
	    Post post = Post.getInstance(connector.getModel(), res);
	    connector.publishPost(post, thread);
	}
    }
}
