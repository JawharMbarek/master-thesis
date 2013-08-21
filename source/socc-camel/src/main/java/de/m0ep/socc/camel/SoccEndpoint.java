package de.m0ep.socc.camel;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.ScheduledPollEndpoint;
import org.ontoware.rdf2go.model.Model;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;

import de.m0ep.socc.core.connector.IConnector;
import de.m0ep.socc.core.connector.IConnector.IStructureReader;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.exceptions.NotFoundException;
import de.m0ep.socc.core.utils.SiocUtils;

public class SoccEndpoint extends ScheduledPollEndpoint {
    private static final Logger LOG = LoggerFactory.getLogger( SoccEndpoint.class );

    private IConnector connector;
    private List<String> containerIds;
    private String postId;
    private int limit = -1;
    private Container container;
    private Post post;

    public SoccEndpoint( String endpointUri, SoccComponent component, IConnector connector,
            List<String> containerIds ) throws NotFoundException,
            AuthenticationException, IOException {
        super( endpointUri, component );
        this.connector = Preconditions.checkNotNull( connector,
                "Required parameter connector must be specified." );
        this.containerIds = Preconditions.checkNotNull( containerIds,
                "Required parameter containerIds must be specified." );
        Preconditions.checkArgument( !containerIds.isEmpty(),
                "The parameter shouldn't be empty." );

        getPollContainer( connector, containerIds );

        LOG.debug( "create Endpoint uri={}  connector={} containerIds={} postId={}",
                endpointUri,
                connector.getId(),
                Arrays.toString( containerIds.toArray() ),
                postId );
    }

    private void getPollContainer( IConnector connector, List<String> containerIds2 )
            throws NotFoundException, AuthenticationException, IOException {
        IStructureReader structureReader = connector.getStructureReader();
        String rootContainerId = containerIds2.get( 0 );
        Container parent = null;
        parent = structureReader.getForum( rootContainerId );

        for ( int i = 1; i < containerIds2.size(); i++ ) {
            parent = structureReader.getThread( containerIds2.get( i ), parent );
        }
        this.container = parent;
    }

    public IConnector getConnector() {
        return connector;
    }

    public void setConnector( IConnector connector ) {
        this.connector = Preconditions.checkNotNull( connector,
                "Required parameter connector must be specified." );
    }

    public List<String> getContainerIds() {
        return containerIds;
    }

    public void setContainerIds( ImmutableList<String> containerIds ) {
        this.containerIds = Preconditions.checkNotNull( containerIds,
                "Required parameter containerIds must be specified." );
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId( String postId ) {
        this.postId = postId;
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer( Container container ) {
        this.container = Preconditions.checkNotNull( container,
                "Required parameter container must be specified." );
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit( int limit ) {
        this.limit = limit;
    }

    public Post getPost() throws NotFoundException {
        if ( null == post ) { // TODO in Methode auslagern
            Model model = getConnector().getContext().getModel();
            post = SiocUtils.getPost( model, getPostId(), getContainer() );
        }

        return post;
    }

    @Override
    public void setConsumerProperties( Map<String, Object> consumerProperties ) {
        String limit = Strings.emptyToNull( (String) consumerProperties.remove( "limit" ) );
        if ( null != limit ) {
            try {
                setLimit( Integer.parseInt( limit ) );
            } catch ( Exception e ) {
                LOG.warn( "Failed to parse 'limit' parameter: was {}", limit );
            }
        }

        super.setConsumerProperties( consumerProperties );
    }

    @Override
    protected void configureConsumer( Consumer consumer ) throws Exception {
        if ( consumer instanceof ISoccConsumer ) {
            ISoccConsumer soccConsumer = (ISoccConsumer) consumer;
            soccConsumer.setContainer( container );
            soccConsumer.setLimit( limit );

            if ( consumer instanceof ISoccPostConsumer ) {
                ( (ISoccPostConsumer) consumer ).setPost( getPost() );
            }
        }

        super.configureConsumer( consumer );
    }

    @Override
    public void configureProperties( Map<String, Object> options ) {
        String postId = Strings.emptyToNull( (String) options.remove( "post" ) );
        if ( null != postId ) {
            setPostId( postId );
        }

        super.configureProperties( options );
    }

    @Override
    public Producer createProducer() throws Exception {
        ISoccProducer producer = null;
        if ( null == postId ) {
            LOG.debug( "Create SoccProducer" );
            producer = new SoccContainerProducer( this );

        } else {
            LOG.debug( "Create SoccPostProducer" );
            producer = new SoccPostProducer( this );
            ( (ISoccPostProducer) producer ).setPost( getPost() );
        }

        producer.setPostWriter( getConnector().getPostWriter() );
        producer.setContainer( getContainer() );

        return producer;
    }

    @Override
    public Consumer createConsumer( Processor processor ) throws Exception {
        ISoccConsumer pollConsumer = null;
        if ( null == postId ) {
            pollConsumer = new SoccContainerPollConsumer( this, processor );
        } else {
            pollConsumer = new SoccPostPollConsumer( this, processor );
        }

        pollConsumer.setPostReader( getConnector().getPostReader() );
        pollConsumer.setContainer( getContainer() );
        pollConsumer.setLimit( getLimit() );

        configureConsumer( pollConsumer );
        return pollConsumer;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
