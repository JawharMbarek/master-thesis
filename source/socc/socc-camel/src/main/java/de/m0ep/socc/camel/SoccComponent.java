package de.m0ep.socc.camel;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.CamelException;
import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.util.Lists;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.base.Throwables;

import de.m0ep.socc.core.ISoccContext;
import de.m0ep.socc.core.connector.ConnectorFactory;
import de.m0ep.socc.core.connector.IConnector;

public class SoccComponent extends DefaultComponent {
    private static final Logger LOG = LoggerFactory.getLogger( SoccComponent.class );

    private ISoccContext soccContext;

    public SoccComponent( final CamelContext camelContext, final ISoccContext soccContext ) {
        super( camelContext );
        setSoccContext( soccContext );
    }

    public ISoccContext getSoccContext() {
        return soccContext;
    }

    public void setSoccContext( final ISoccContext soccContext ) {
        Preconditions.checkNotNull( soccContext,
                "Required parameter soccContext must be specified." );
        this.soccContext = soccContext;
    }

    @Override
    protected Endpoint createEndpoint( final String uri, final String remaining,
            Map<String, Object> parameters )
            throws Exception {
        LOG.debug( "create endpoint: uri={} remaining={}", uri, remaining );

        URI remainingUri = null;
        try {
            remainingUri = new URI( remaining );
        } catch ( URISyntaxException e ) {
            LOG.debug( "Invalid uri: was {}", uri );
            Throwables.propagateIfPossible( e, URISyntaxException.class );
        }

        String path = remainingUri.getPath();
        if ( null == path ) {
            throw new CamelException( "invalid endpointUri" );
        }

        List<String> pathElements = splitUriPath( path );
        String connectorId = pathElements.remove( 0 );
        IConnector connector = ConnectorFactory.getInstance().createConnector(
                getSoccContext(),
                connectorId );

        if ( !connector.isInitialized() ) {
            connector.initialize();
        }

        SoccEndpoint endpoint = new SoccEndpoint( uri, this, connector );
        endpoint.configureProperties( parameters );

        return endpoint;
    }

    private List<String> splitUriPath( final String path ) {
        Iterable<String> iter = Splitter.on( "/" )
                .omitEmptyStrings()
                .trimResults()
                .split( path );

        return Lists.newArrayList( iter );
    }

}
