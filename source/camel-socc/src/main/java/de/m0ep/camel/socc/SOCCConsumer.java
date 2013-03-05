package de.m0ep.camel.socc;

import java.util.Map.Entry;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.ScheduledPollConsumer;
import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.ontoware.rdf2go.RDF2Go;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.Statement;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.model.node.Variable;
import org.ontoware.rdf2go.util.RDFTool;
import org.rdfs.sioc.Post;

import de.m0ep.socc.utils.RDF2GoUtils;

public class SOCCConsumer extends ScheduledPollConsumer {

    Model model;

    public SOCCConsumer(Endpoint endpoint, Processor processor) {
	super(endpoint, processor);

	this.model = ((SOCCEndpoint) getEndpoint()).getModel();
	System.out.println("consumer");
    }

    @Override
    protected int poll() throws Exception {
	System.out.println("poll");
	URI uri = RDF2GoUtils.createURI("http://example.com/"
		+ System.currentTimeMillis());
	Post post = new Post(model, uri, true);
	post.setName("Testpost");

	Exchange exchange = getEndpoint().createExchange();
	exchange.getIn().setBody(serializePost(post));

	getProcessor().process(exchange);

	return 1;
    }

    private String serializePost(Post p) {
	ClosableIterator<Statement> stmtsCloseableIterator = model
		.findStatements(p, Variable.ANY, Variable.ANY);

	Model sm = RDF2Go.getModelFactory().createModel();
	sm.open();

	for (Entry<String, String> ns : model.getNamespaces().entrySet())
	    sm.setNamespace(ns.getKey(), ns.getValue());

	while (stmtsCloseableIterator.hasNext()) {
	    Statement statement = (Statement) stmtsCloseableIterator.next();

	    sm.addStatement(statement);
	}
	String xml = RDFTool.modelToString(sm);
	stmtsCloseableIterator.close();
	sm.close();
	System.out.println("test " + xml.replace('\n', ' '));
	return xml;
    }

}
