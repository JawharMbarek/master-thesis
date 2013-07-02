package de.m0ep.camel.socc.producer;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.impl.DefaultProducer;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.util.Builder;
import org.rdfs.sioc.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

import de.m0ep.camel.socc.SOCCEndpointThread;
import de.m0ep.camel.socc.data.SIOCPostData;

public class SOCCProducerThread extends DefaultProducer {
    private static final Logger LOG = LoggerFactory
	    .getLogger(SOCCProducerThread.class);

    private SOCCEndpointThread endpoint;

    public SOCCProducerThread(SOCCEndpointThread endpoint) {
	super(endpoint);

	this.endpoint = endpoint;

	LOG.debug("{} created", getClass().getSimpleName());
    }

    @Override
    public void process(Exchange exchange) throws Exception {
	Model model = endpoint.getConnector().getContext().getDataModel();
	SIOCPostData postData = null;
	Message msg = exchange.getIn();
	String type = Strings.nullToEmpty(
		msg.getHeader(
			Exchange.CONTENT_TYPE,
			String.class));

	if (type.equals(SIOCPostData.class.getName())) {
	    postData = msg.getBody(SIOCPostData.class);
	} else if (type.equals("rdf/xml") || msg.getBody() instanceof String) {
	    postData = new SIOCPostData(msg.getBody(String.class));
	}

	if (null != postData) {
	    URI postUri = Builder.createURI(postData.getId());
	    if (!Post.hasInstance(model, postUri)) {
		// unknown post, add it to model
		model.addAll(postData.getRDFStatements().iterator());
	    }

	    Post post = Post.getInstance(model, postUri);
	    endpoint.getConnector().publishPost(
		    post,
		    endpoint.getThread());
	} else {
	    LOG.warn("There is no SIOC:Post in this message");
	}
    }
}
