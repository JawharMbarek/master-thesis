package de.m0ep.camel.socc.consumer;

import java.util.concurrent.ScheduledExecutorService;

import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultScheduledPollConsumer;

import de.m0ep.camel.socc.ISOCCEndpoint;
import de.m0ep.camel.socc.PostEndpoint;

public class PostPollingConsumer extends DefaultScheduledPollConsumer {

    ISOCCEndpoint postEndpoint;

    public PostPollingConsumer(PostEndpoint postEndpoint, Processor processor) {
	super(postEndpoint, processor);
	this.postEndpoint = postEndpoint;

    }

    public PostPollingConsumer(PostEndpoint postEndpoint, Processor processor,
	    ScheduledExecutorService executor) {
	super(postEndpoint, processor, executor);
	this.postEndpoint = postEndpoint;
    }

    @Override
    protected int poll() throws Exception {
	// TODO Auto-generated method stub

	return super.poll();
    }
}
