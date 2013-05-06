package de.m0ep.camel.socc.consumer;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultMessage;
import org.apache.camel.impl.DefaultScheduledPollConsumer;
import org.rdfs.sioc.Post;

import de.m0ep.camel.socc.ForumEndpoint;
import de.m0ep.camel.socc.data.SIOCPostData;
import de.m0ep.socc.utils.RDF2GoUtils;

public class ForumPollingConsumer extends DefaultScheduledPollConsumer {
    private ForumEndpoint forumEndpoint;

    public ForumPollingConsumer(
	    ForumEndpoint forumEndpoint,
	    Processor processor) {
	super(forumEndpoint, processor);

	this.forumEndpoint = forumEndpoint;
    }

    @Override
    protected int poll() throws Exception {
	List<Post> posts = forumEndpoint.getConnector().pollNewPosts(
		forumEndpoint.getForum());

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

	return posts.size();
    }
}
