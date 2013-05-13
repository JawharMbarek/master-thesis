package de.m0ep.camel.socc.consumer;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultMessage;
import org.apache.camel.impl.DefaultScheduledPollConsumer;
import org.rdfs.sioc.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.m0ep.camel.socc.SOCCEndpointForum;
import de.m0ep.camel.socc.data.SIOCPostData;
import de.m0ep.socc.utils.RDF2GoUtils;

public class SOCCPollingConsumerForum extends DefaultScheduledPollConsumer {
	private static final Logger LOG = LoggerFactory
			.getLogger(SOCCPollingConsumerForum.class);

	private SOCCEndpointForum endpoint;

	public SOCCPollingConsumerForum(
			SOCCEndpointForum endpoint,
			Processor processor) {
		super(endpoint, processor);

		this.endpoint = endpoint;

		LOG.debug(
				"Created {} for {}",
				getClass().getSimpleName(),
				endpoint.getEndpointUri());
	}

	@Override
	protected int poll() throws Exception {
		List<Post> posts = endpoint.getConnector().pollPosts(
				endpoint.getForum());

		for (Post post : posts) {
			Message msg = new DefaultMessage();
			msg.setHeader(Exchange.CONTENT_TYPE, SIOCPostData.class.getName());
			SIOCPostData postData = new SIOCPostData(
					post.asURI().toString(),
					RDF2GoUtils.getAllStatements(post.getModel(), post));
			msg.setBody(postData);

			Exchange exchange = getEndpoint().createExchange();
			exchange.setIn(msg);
			getProcessor().process(exchange);
		}

		LOG.debug(
				"Polled {} new posts @ {}",
				posts.size(),
				endpoint.getEndpointUri());

		return posts.size();
	}
}
