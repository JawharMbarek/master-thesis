package de.m0ep.socc.camel;

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

import de.m0ep.socc.core.connector.IConnector.IPostReader;

public class SoccContainerPollConsumer extends ScheduledPollConsumer {
    private static final Logger LOG = LoggerFactory.getLogger( SoccContainerPollConsumer.class );

    private IPostReader postReader;
    private Container container;

    public SoccContainerPollConsumer( Endpoint endpoint, Processor processor,
            IPostReader postReader, Container container ) {
        super( endpoint, processor );

        this.postReader = Preconditions.checkNotNull( postReader,
                "Required parameter postReader must be specified." );

        this.container = Preconditions.checkNotNull( container,
                "Required parameter container must be specified." );
    }

    @Override
    protected int poll() throws Exception {

        List<Post> posts = postReader.readNewPosts( null, -1, container );

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
