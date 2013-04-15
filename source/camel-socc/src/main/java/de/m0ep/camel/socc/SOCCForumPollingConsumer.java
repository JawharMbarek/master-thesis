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
import de.m0ep.socc.utils.RDF2GoUtils;

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
	List<Post> posts = getConnector().pollNewPosts(getForum());

	for (Post post : posts) {
	    Exchange exchange = getEndpoint().createExchange();
	    Message msg = new DefaultMessage();
	    msg.setHeader(Exchange.CONTENT_TYPE, "RDFStatements");
	    msg.setHeader("SIOCType", "Post");
	    exchange.getIn().setBody(
		    RDF2GoUtils.getAllStatements(post.getModel(), post));
	    getProcessor().process(exchange);
	}

	return posts.size();
    }

    public IConnector getConnector() {
	return this.connector;
    }

    public void setConnector(IConnector connector) {
	this.connector = connector;
    }

    public Forum getForum() {
	return this.forum;
    }

    public void setForum(Forum forum) {
	this.forum = forum;
    }
}
