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

import java.util.Map;

import org.apache.camel.Consumer;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.component.direct.DirectEndpoint;
import org.apache.camel.impl.ScheduledPollConsumer;
import org.rdfs.sioc.Forum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.m0ep.camel.socc.consumer.SOCCPollingConsumerForum;
import de.m0ep.camel.socc.producer.SOCCProducerForum;
import de.m0ep.socc.IConnector;
import de.m0ep.socc.config.DefaultConnectorConfig;
import de.m0ep.socc.exceptions.ConnectorException;

public class SOCCEndpointForum extends DirectEndpoint implements ISOCCEndpoint {
    private static final Logger LOG = LoggerFactory
	    .getLogger(SOCCEndpointForum.class);

    private SOCCComponent soccComponent;
    private String uri;
    private SOCCEndpointProperties properties;
    private IConnector connector;
    private Forum forum;

    public SOCCEndpointForum(SOCCComponent soccComponent, String uri,
	    SOCCEndpointProperties properties, IConnector connector)
	    throws ConnectorException {
	super(uri, soccComponent);
	this.soccComponent = soccComponent;
	this.uri = uri;
	this.properties = properties;
	this.connector = connector;
	this.forum = connector.getForum(properties.getForumId());

	LOG.debug(
		"Create SOCCEndpointForum with uri={} and connector={}",
		uri,
		connector.getId());
    }

    @Override
    public Consumer createConsumer(Processor processor) throws Exception {
	ScheduledPollConsumer consumer = new SOCCPollingConsumerForum(
		this,
		processor);

	Map<String, Object> connectorConfig = connector.getConfiguration();
	if (connectorConfig.containsKey(DefaultConnectorConfig.POLL_COOLDOWN)) {
	    consumer.setDelay((Long) connectorConfig.get(
		    DefaultConnectorConfig.POLL_COOLDOWN));
	} else {
	    consumer.setDelay(properties.getDelay());
	}

	configureConsumer(consumer);
	return consumer;
    }

    @Override
    public Producer createProducer() throws Exception {
	return new SOCCProducerForum(this);
    }

    @Override
    public String getEndpointUri() {
	return uri;
    }

    @Override
    public ExchangePattern getExchangePattern() {
	return ExchangePattern.InOptionalOut;
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

    public synchronized Forum getForum() {
	return this.forum;
    }
}
