package de.m0ep.camel.socc.producer;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;

import de.m0ep.camel.socc.ISOCCEndpoint;
import de.m0ep.camel.socc.PostEndpoint;

public class PostProducer extends DefaultProducer {

    ISOCCEndpoint postEndpoint;

    public PostProducer(PostEndpoint postEndpoint) {
	super(postEndpoint);
	this.postEndpoint = postEndpoint;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
	// TODO Auto-generated method stub

    }
}
