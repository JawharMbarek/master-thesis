package de.m0ep.camel.socc;

import java.util.Date;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultMessage;
import org.apache.camel.impl.ScheduledPollConsumer;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.node.URI;
import org.rdfs.sioc.Post;

import de.m0ep.socc.utils.DateUtils;

public class SOCCConsumer extends ScheduledPollConsumer {

    Model model;

    public SOCCConsumer(Endpoint endpoint, Processor processor) {
	super(endpoint, processor);

	this.model = ((SOCCEndpoint) getEndpoint()).getModel();
	System.out.println("consumer");
    }

    @Override
    protected int poll() throws Exception {
	URI uri = model.newRandomUniqueURI();
	Post post = new Post(model, uri, true);
	post.setTitle("Testpost");
	post.setContent("Hallo, ich bin ein test post");
	post.setCreated(DateUtils.formatISO8601(new Date()));

	Exchange exchange = getEndpoint().createExchange();

	Message msg = new DefaultMessage();
	msg.setHeader(Exchange.CONTENT_TYPE, "RDFStatements");
	msg.setHeader("SIOCType", "Post");

	exchange.getIn().setBody(new RDFSClass(model, uri));

	getProcessor().process(exchange);

	return 1;
    }
}
