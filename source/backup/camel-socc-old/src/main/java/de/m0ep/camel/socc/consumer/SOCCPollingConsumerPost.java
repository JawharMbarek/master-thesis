package de.m0ep.camel.socc.consumer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultMessage;
import org.apache.camel.impl.DefaultScheduledPollConsumer;
import org.rdfs.sioc.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.m0ep.camel.socc.SOCCEndpointPost;
import de.m0ep.camel.socc.data.SIOCPostData;
import de.m0ep.socc.utils.RDF2GoUtils;

public class SOCCPollingConsumerPost extends DefaultScheduledPollConsumer {
	private static final Logger LOG = LoggerFactory
			.getLogger(SOCCPollingConsumerPost.class);

	SOCCEndpointPost endpoint;

	public SOCCPollingConsumerPost(SOCCEndpointPost postEndpoint,
			Processor processor) {
		super(postEndpoint, processor);

		this.endpoint = postEndpoint;

		LOG.debug(
				"Created {} for {}",
				getClass().getSimpleName(),
				endpoint.getEndpointUri());

	}

	public SOCCPollingConsumerPost(SOCCEndpointPost postEndpoint,
			Processor processor,
			ScheduledExecutorService executor) {
		super(postEndpoint, processor, executor);
		this.endpoint = postEndpoint;
	}

	@Override
	protected int poll() throws Exception {
		List<Post> posts = new ArrayList<Post>(); // TODO: poll comments

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
