package de.m0ep.socc.camel;

import java.io.IOException;
import java.util.Arrays;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.ScheduledPollConsumer;
import org.apache.camel.impl.ScheduledPollEndpoint;
import org.rdfs.sioc.Container;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import de.m0ep.socc.core.connector.IConnector;
import de.m0ep.socc.core.connector.IConnector.IStructureReader;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.exceptions.NotFoundException;

public class SoccEndpoint extends ScheduledPollEndpoint {
    private static final Logger LOG = LoggerFactory.getLogger( SoccEndpoint.class );

    private IConnector connector;
    private ImmutableList<String> containerIds;
    private String postId;
    private Container container;

    public SoccEndpoint( String endpointUri, SoccComponent component, IConnector connector,
            ImmutableList<String> containerIds, String postId ) throws NotFoundException,
            AuthenticationException, IOException {
        super( endpointUri, component );
        this.connector = Preconditions.checkNotNull( connector,
                "Required parameter connector must be specified." );
        this.containerIds = Preconditions.checkNotNull( containerIds,
                "Required parameter containerIds must be specified." );
        Preconditions.checkArgument( !containerIds.isEmpty(),
                "The parameter shouldn't be empty." );

        this.postId = postId;

        IStructureReader structureReader = connector.getStructureReader();
        String rootContainerId = containerIds.get( 0 );
        Container parent = null;
        parent = structureReader.getForum( rootContainerId );

        for ( int i = 1; i < containerIds.size(); i++ ) {
            parent = structureReader.getThread( containerIds.get( i ), parent );
        }
        this.container = parent;

        LOG.debug( "create Endpoint uri={}  connector={} containerIds={} postId={}",
                endpointUri,
                connector.getId(),
                Arrays.toString( containerIds.toArray() ),
                postId );
    }

    @Override
    public Producer createProducer() throws Exception {
        Producer producer = null;
        if ( null == postId ) {
            producer = new SoccContainerProducer( this, connector.getPostWriter(), container );
        } else {

        }

        return producer;
    }

    @Override
    public Consumer createConsumer( Processor processor ) throws Exception {

        ScheduledPollConsumer pollConsumer = null;
        if ( null == postId ) {
            pollConsumer = new SoccContainerPollConsumer(
                    this,
                    processor,
                    connector.getPostReader(),
                    container );
        } else {
            pollConsumer = new SoccContainerPollConsumer(
                    this,
                    processor,
                    connector.getPostReader(),
                    container );
        }

        configureConsumer( pollConsumer );
        return pollConsumer;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
