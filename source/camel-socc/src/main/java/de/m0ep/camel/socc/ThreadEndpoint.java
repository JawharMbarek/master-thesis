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

import de.m0ep.camel.socc.consumer.ThreadPollingConsumer;
import de.m0ep.camel.socc.producer.ThreadProducer;
import de.m0ep.socc.IConnector;
import de.m0ep.socc.exceptions.ConnectorException;

public class ThreadEndpoint extends DirectEndpoint implements ISOCCEndpoint {

	private SOCCComponent soccComponent;
	private String uri;
	private EndpointProperties properties;
	private IConnector connector;
	private Thread thread;

	public ThreadEndpoint(SOCCComponent soccComponent, String uri,
			EndpointProperties properties, IConnector connector)
			throws ConnectorException {
		super(uri, soccComponent);

		this.soccComponent = soccComponent;
		this.uri = uri;
		this.connector = connector;
		this.properties = properties;
		this.thread = connector.getThread(properties.getThreadId());
	}

	@Override
	public Consumer createConsumer(Processor processor) throws Exception {
		ScheduledPollConsumer pollConsumer = new ThreadPollingConsumer(
				this,
				processor);
		pollConsumer.setDelay(properties.getDelay());

		return pollConsumer;
	}

	@Override
	public Producer createProducer() throws Exception {
		return new ThreadProducer(this);
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
	public EndpointProperties getProperties() {
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
