/*
 * The MIT License (MIT) Copyright © 2013 Florian Müller
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the “Software”), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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

import de.m0ep.camel.socc.SOCCEndpointThread;
import de.m0ep.camel.socc.data.SIOCPostData;
import de.m0ep.socc.utils.RDF2GoUtils;

public class SOCCPollingConsumerThread extends DefaultScheduledPollConsumer {
	private static final Logger LOG = LoggerFactory
			.getLogger(SOCCPollingConsumerThread.class);

	private SOCCEndpointThread endpoint;

	public SOCCPollingConsumerThread(SOCCEndpointThread threadEndpoint,
			Processor processor) {
		super(threadEndpoint, processor);

		this.endpoint = threadEndpoint;

		LOG.debug(
				"Created {} for {}",
				getClass().getSimpleName(),
				threadEndpoint.getEndpointUri());
	}

	@Override
	protected int poll() throws Exception {
		List<Post> posts = endpoint.getConnector().pollPosts(
				endpoint.getThread());

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
