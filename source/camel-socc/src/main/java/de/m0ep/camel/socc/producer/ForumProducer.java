package de.m0ep.camel.socc.producer;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.impl.DefaultProducer;
import org.rdfs.sioc.Forum;

import de.m0ep.camel.socc.ForumEndpoint;
import de.m0ep.socc.IConnector;

public class ForumProducer extends DefaultProducer {

    IConnector connector;
    Forum forum;

    public ForumProducer(ForumEndpoint soccForumEndpoint,
	    IConnector connector, Forum forum) {
	super(soccForumEndpoint);

	this.connector = connector;
	this.forum = forum;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
	Message msg = exchange.getIn();
	System.err.println(msg.getBody());
    }
}
