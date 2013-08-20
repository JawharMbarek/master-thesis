package de.m0ep.socc.camel;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;
import org.ontoware.rdf2go.util.Builder;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Post;

import com.google.common.base.Preconditions;

import de.m0ep.socc.core.connector.IConnector.IPostWriter;

public class SoccContainerProducer extends DefaultProducer {

    private IPostWriter postWriter;
    private Container container;

    public SoccContainerProducer( Endpoint endpoint, IPostWriter postWriter,
            Container container ) {
        super( endpoint );

        this.postWriter = Preconditions.checkNotNull( postWriter,
                "Required parameter postWriter must be specified." );
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
