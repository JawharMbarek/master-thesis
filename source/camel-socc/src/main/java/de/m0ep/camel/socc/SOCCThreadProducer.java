package de.m0ep.camel.socc;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.impl.DefaultProducer;
import org.ontoware.rdf2go.model.Statement;
import org.ontoware.rdf2go.model.node.Resource;
import org.ontoware.rdf2go.vocabulary.RDF;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.SIOC;
import org.rdfs.sioc.Thread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import de.m0ep.socc.IConnector;

public class SOCCThreadProducer extends DefaultProducer {
    private static final Logger LOG = LoggerFactory
	    .getLogger(SOCCThreadProducer.class);

    IConnector connector;
    Thread thread;

    public SOCCThreadProducer(SOCCThreadEndpoint soccThreadEndpoint,
	    IConnector connector, Thread thread) {
	super(soccThreadEndpoint);
	this.connector = Preconditions.checkNotNull(connector,
		"Connector can not be null");
	this.thread = Preconditions.checkNotNull(thread,
		"Thread can not be null");
    }

    @Override
    public void process(Exchange exchange) throws Exception {
	Message msg = exchange.getIn();

	@SuppressWarnings("unchecked")
	List<Statement> stmts = msg.getBody(List.class);

	Resource res = null;
	for (Statement stmt : stmts) {
	    if (stmt.getPredicate().equals(RDF.type)) {
		if (stmt.getObject().equals(SIOC.Post)) {
		    res = stmt.getSubject();
		    break;
		}
	    }
	}

	if (null != res) {
	    if (!Post.hasInstance(connector.getModel(), res)) {
		// unknown post, add it to model
		getConnector().getModel().addAll(stmts.iterator());
	    }

	    Post post = Post.getInstance(getConnector().getModel(), res);
	    getConnector().publishPost(post, getThread());
	} else {
	    LOG.warn("There is no SIOC:Post in this message");
	}
    }

    public IConnector getConnector() {
	return this.connector;
    }

    public void setConnector(IConnector connector) {
	this.connector = connector;
    }

    public Thread getThread() {
	return this.thread;
    }

    public void setThread(Thread thread) {
	this.thread = thread;
    }
}
