package de.m0ep.socc.camel;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;
import org.ontoware.rdf2go.util.Builder;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import de.m0ep.socc.core.connector.IConnector;
import de.m0ep.socc.core.connector.IConnector.IPostWriter;

public class SoccPostProducer extends DefaultProducer implements ISoccPostProducer {
	private static final Logger LOG = LoggerFactory.getLogger( SoccPostProducer.class );

	private IPostWriter<? extends IConnector> postWriter;
	private Container container;
	private Post post;

	public SoccPostProducer( Endpoint endpoint ) {
		super( endpoint );
	}

	@Override
	public IPostWriter<? extends IConnector> getPostWriter() {
		return postWriter;
	}

	@Override
	public void setPostWriter( IPostWriter<? extends IConnector> postWriter ) {
		this.postWriter = Preconditions.checkNotNull( postWriter,
		        "Required parameter postWriter must be specified." );
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
	public void process( Exchange exchange ) throws Exception {
		String body = exchange.getIn().getBody( String.class );

		Post replyPost = Post.getInstance(
		        postWriter.getConnector().getContext().getModel(),
		        Builder.createURI( body ) );

		postWriter.writeReply( replyPost, post );
		LOG.debug( "Write reply {} to {} ", replyPost, post );
	}
}
