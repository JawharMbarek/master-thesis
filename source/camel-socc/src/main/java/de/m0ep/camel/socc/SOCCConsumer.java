package de.m0ep.camel.socc;

import java.util.List;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultMessage;
import org.apache.camel.impl.ScheduledPollConsumer;
import org.ontoware.rdf2go.model.Model;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.Thread;

import de.m0ep.socc.IConnector;

public class SOCCConsumer extends ScheduledPollConsumer {

    Model model;
    Thread thread;
    IConnector connector;

    public SOCCConsumer(Endpoint endpoint, Processor processor,
	    IConnector connector, Thread thread) {
	super(endpoint, processor);
	this.connector = connector;
	this.thread = thread;
	System.out.println("consumer");
    }

    @Override
    protected int poll() throws Exception {

	List<Post> posts = connector.pollNewPosts(thread);

	for (Post post : posts) {
	    Exchange exchange = getEndpoint().createExchange();
	    Message msg = new DefaultMessage();
	    msg.setHeader(Exchange.CONTENT_TYPE, "RDFStatements");
	    msg.setHeader("SIOCType", "Post");
	    exchange.getIn().setBody(new RDFSClass(post.getModel(), post));
	    getProcessor().process(exchange);
	}

	return posts.size();
    }
}
