package de.m0ep.camel.socc;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultMessage;
import org.apache.camel.impl.DefaultScheduledPollConsumer;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Post;

import de.m0ep.socc.IConnector;

public class SOCCForumPollingConsumer extends DefaultScheduledPollConsumer {
    IConnector connector;
    Forum forum;

    public SOCCForumPollingConsumer(SOCCForumEndpoint soccForumEndpoint,
	    Processor processor, IConnector connector, Forum forum) {
	super(soccForumEndpoint, processor);

	this.connector = connector;
	this.forum = forum;
    }

    @Override
    protected int poll() throws Exception {
	List<Post> posts = connector.pollNewPosts(forum);

	for (Post post : posts) {
	    Exchange exchange = getEndpoint().createExchange();
	    Message msg = new DefaultMessage();
	    msg.setHeader(Exchange.CONTENT_TYPE, "RDFStatements");
	    msg.setHeader("SIOCType", "Post");
	    exchange.getIn().setBody(new RDFSClass(post.getModel(), post));
	    getProcessor().process(exchange);
	}

	return posts.size();
    }

}
