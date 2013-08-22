package de.m0ep.socc.camel;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;
import org.ontoware.rdf2go.util.Builder;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Post;

import com.google.common.base.Preconditions;

import de.m0ep.socc.core.connector.IConnector;
import de.m0ep.socc.core.connector.IConnector.IPostWriter;

public class SoccContainerProducer extends DefaultProducer implements ISoccProducer {
	private IPostWriter<? extends IConnector> postWriter;
	private Container container;

	public SoccContainerProducer( Endpoint endpoint ) {
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
	public void process( Exchange exchange ) throws Exception {
		String body = exchange.getIn().getBody( String.class );

		Post post = Post.getInstance(
		        postWriter.getConnector().getContext().getModel(),
		        Builder.createURI( body ) );

		postWriter.writePost( post, container );
	}
}
