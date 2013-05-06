package de.m0ep.camel.socc.producer;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.impl.DefaultProducer;
import org.ontoware.rdf2go.model.Statement;
import org.ontoware.rdf2go.model.node.Resource;
import org.ontoware.rdf2go.vocabulary.RDF;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.SIOCVocabulary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.m0ep.camel.socc.ThreadEndpoint;
import de.m0ep.camel.socc.data.SIOCPostData;

public class ThreadProducer extends DefaultProducer {
    private static final Logger LOG = LoggerFactory
	    .getLogger(ThreadProducer.class);

    private ThreadEndpoint threadEndpoint;

    public ThreadProducer(ThreadEndpoint soccThreadEndpoint) {
	super(soccThreadEndpoint);
    }

    @Override
    public void process(Exchange exchange) throws Exception {
	SIOCPostData postData = null;
	Message msg = exchange.getIn();
	String type = msg.getHeader(Exchange.CONTENT_TYPE, String.class);

	if (type.equals(SIOCPostData.class.getName())) {
	    postData = msg.getBody(SIOCPostData.class);
	} else {

	}

	@SuppressWarnings("unchecked")
	List<Statement> stmts = msg.getBody(List.class);

	Resource res = null;
	for (Statement stmt : stmts) {
	    if (stmt.getPredicate().equals(RDF.type)) {
		if (stmt.getObject().equals(SIOCVocabulary.Post)) {
		    res = stmt.getSubject();
		    break;
		}
	    }
	}

	if (null != res) {
	    if (!Post
		    .hasInstance(threadEndpoint.getConnector().getModel(), res)) {
		// unknown post, add it to model
		threadEndpoint.getConnector().getModel()
			.addAll(stmts.iterator());
	    }

	    Post post = Post.getInstance(
		    threadEndpoint.getConnector()
			    .getModel(),
		    res);
	    threadEndpoint.getConnector().publishPost(
		    post,
		    threadEndpoint.getThread());
	} else {
	    LOG.warn("There is no SIOC:Post in this message");
	}
    }
}
