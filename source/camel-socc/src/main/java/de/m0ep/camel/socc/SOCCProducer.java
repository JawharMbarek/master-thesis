package de.m0ep.camel.socc;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;

public class SOCCProducer extends DefaultProducer {

    public SOCCProducer(Endpoint endpoint) {
	super(endpoint);
    }

    @Override
    public void process(Exchange exchange) throws Exception {
	System.out.println(exchange.getIn().getBody());
    }

}
