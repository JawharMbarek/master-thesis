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

package de.m0ep.camel.socc;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.component.direct.DirectEndpoint;
import org.apache.camel.impl.ScheduledPollConsumer;
import org.rdfs.sioc.Thread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.m0ep.camel.socc.consumer.SOCCPollingConsumerThread;
import de.m0ep.camel.socc.producer.SOCCProducerThread;
import de.m0ep.socc.IConnector;
import de.m0ep.socc.exceptions.ConnectorException;

public class SOCCEndpointThread extends DirectEndpoint implements ISOCCEndpoint {
	private static final Logger LOG = LoggerFactory
			.getLogger(SOCCEndpointThread.class);

	private SOCCComponent soccComponent;
	private String uri;
	private SOCCEndpointProperties properties;
	private IConnector connector;
	private Thread thread;

	public SOCCEndpointThread(SOCCComponent soccComponent, String uri,
			SOCCEndpointProperties properties, IConnector connector)
			throws ConnectorException {
		super(uri, soccComponent);

		this.soccComponent = soccComponent;
		this.uri = uri;
		this.properties = properties;
		this.connector = connector;
		this.thread = connector.getThread(properties.getThreadId());

		LOG.debug(
				"Create SOCCEndpointThread with uri={} and connector={}",
				uri,
				connector.getId());
	}

	@Override
	public Consumer createConsumer(Processor processor) throws Exception {
		ScheduledPollConsumer consumer = new SOCCPollingConsumerThread(
				this,
				processor);
		consumer.setDelay(properties.getDelay());
		configureConsumer(consumer);

		return consumer;
	}

	@Override
	public Producer createProducer() throws Exception {
		return new SOCCProducerThread(this);
	}

	@Override
	public String getEndpointUri() {
		return uri;
	}

	@Override
	public SOCCComponent getSOCCComponent() {
		return soccComponent;
	}

	@Override
	public SOCCEndpointProperties getProperties() {
		return properties;
	}

	@Override
	public synchronized IConnector getConnector() {
		return connector;
	}

	public synchronized Thread getThread() {
		return this.thread;
	}
}
