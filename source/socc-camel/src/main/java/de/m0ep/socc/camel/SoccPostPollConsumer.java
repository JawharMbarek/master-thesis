package de.m0ep.socc.camel;

import java.util.Date;
import java.util.List;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultMessage;
import org.apache.camel.impl.ScheduledPollConsumer;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import de.m0ep.socc.core.connector.IConnector;
import de.m0ep.socc.core.connector.IConnector.IPostReader;
import de.m0ep.socc.core.utils.SiocUtils;

public class SoccPostPollConsumer extends ScheduledPollConsumer implements ISoccPostConsumer {
	private static final Logger LOG = LoggerFactory.getLogger( SoccPostPollConsumer.class );

	private IPostReader<? extends IConnector> postReader;
	private Container container;
	private Post post;
	private int limit;

	public SoccPostPollConsumer( Endpoint endpoint, Processor processor ) {
		super( endpoint, processor );
	}

	@Override
	public IPostReader<? extends IConnector> getPostReader() {
		return postReader;
	}

	@Override
	public void setPostReader( IPostReader<? extends IConnector> postReader ) {
		this.postReader = Preconditions.checkNotNull( postReader,
		        "Required parameter postReader must be specified." );
	}

	@Override
	public Container getContainer() {
		return container;
	}

	@Override
	public void setContainer( Container container ) {
		this.container = Preconditions.checkNotNull( container,
		        "Required parameter container must be specified." );
	}

	@Override
	public Post getPost() {
		return post;
	}

	@Override
	public void setPost( Post post ) {
		this.post = Preconditions.checkNotNull( post,
		        "Required parameter post must be specified." );
	}

	@Override
	public int getLimit() {
		return limit;
	}

	@Override
	public void setLimit( int limit ) {
		this.limit = Math.max( -1, limit );
	}

	@Override
	protected int poll() throws Exception {
		Date since = SiocUtils.getLastReplyDate( post );
		List<Post> posts = postReader.pollRepliesAtPost( since, limit, post );

		for ( Post post : posts ) {
			LOG.debug( "receive message {}", post );
			Message msg = new DefaultMessage();
			msg.setBody( post.toString() );
			Exchange ex = getEndpoint().createExchange();
			ex.setIn( msg );
			getProcessor().process( ex );
		}

		return posts.size();
	}
}
