package de.m0ep.camel.socc.producer;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.impl.DefaultProducer;
import org.ontoware.rdf2go.model.node.URI;
import org.rdfs.sioc.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

import de.m0ep.camel.socc.SOCCEndpointPost;
import de.m0ep.camel.socc.data.SIOCPostData;
import de.m0ep.socc.utils.RDF2GoUtils;

public class SOCCProducerPost extends DefaultProducer {
	private static final Logger LOG = LoggerFactory
			.getLogger(SOCCProducerPost.class);

	SOCCEndpointPost endpoint;

	public SOCCProducerPost(SOCCEndpointPost postEndpoint) {
		super(postEndpoint);

		this.endpoint = postEndpoint;

		LOG.debug("{} created", getClass().getSimpleName());
	}

	@Override
	public void process(Exchange exchange) throws Exception {
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
			URI postUri = RDF2GoUtils.createURI(postData.getId());
			if (!Post.hasInstance(
					endpoint.getConnector().getModel(),
					postUri)) {
				// unknown post, add it to model
				endpoint.getConnector().getModel()
						.addAll(postData.getRDFStatements().iterator());
			}

			Post post = Post.getInstance(
					endpoint.getConnector()
							.getModel(),
					postUri);
			endpoint.getConnector().replyPost(
					post,
					endpoint.getPost());
		} else {
			LOG.warn("There is no SIOC:Post in this message");
		}
	}
}
