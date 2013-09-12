package de.m0ep.socc.camel;

import java.io.IOException;
import java.util.Map;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.ScheduledPollEndpoint;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.util.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import de.m0ep.socc.core.connector.IConnector;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.exceptions.NotFoundException;

public class SoccEndpoint extends ScheduledPollEndpoint {
	public static final String PROPERTY_LIMIT = "limit";
	public static final String PROPERTY_URI = "uri";

	private static final Logger LOG = LoggerFactory.getLogger( SoccEndpoint.class );

	private IConnector connector;
	private int limit = -1;
	private URI uri;

	public SoccEndpoint(
	        final String endpointUri,
	        final SoccComponent component,
	        final IConnector connector )
	        throws NotFoundException,
	        AuthenticationException,
	        IOException {
		super( endpointUri, component );
		this.connector = Preconditions.checkNotNull( connector,
		        "Required parameter connector must be specified." );

		LOG.debug( "create Endpoint uri={}  connector={} containerIds={} postId={}",
		        endpointUri,
		        connector.getId() );
	}

	public IConnector getConnector() {
		return connector;
	}

	public void setConnector( final IConnector connector ) {
		this.connector = Preconditions.checkNotNull( connector,
		        "Required parameter connector must be specified." );
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit( int limit ) {
		this.limit = Math.max( -1, limit );
	}

	public URI getUri() {
		return uri;
	}

	public void setUri( URI uri ) {
		this.uri = Preconditions.checkNotNull( uri,
		        "Required parameter uri must be specified." );
	}

	@Override
	public void configureProperties( final Map<String, Object> options ) {
		if ( options.containsKey( PROPERTY_URI ) ) {
			setUri( Builder.createURI( (String) options.remove( PROPERTY_URI ) ) );
			LOG.debug( "Found property uri='{}'", getUri() );
		}

		if ( options.containsKey( PROPERTY_LIMIT ) ) {
			try {
				setLimit( Integer.parseInt( (String) options.remove( PROPERTY_LIMIT ) ) );
				LOG.debug( "Found property limit='{}'", getLimit() );
			} catch ( Exception e ) {
				LOG.warn( "Failed to parse 'limit' parameter: was {}", limit );
				setLimit( -1 );
			}
		}

		super.configureProperties( options );
	}

	@Override
	public Consumer createConsumer( final Processor processor ) throws Exception {
		ISoccConsumer pollConsumer = new SoccPostPollConsumer( this, processor );
		configureConsumer( pollConsumer );
		return pollConsumer;
	}

	@Override
	protected void configureConsumer( final Consumer consumer ) throws Exception {
		if ( consumer instanceof ISoccConsumer ) {
			ISoccConsumer soccConsumer = (ISoccConsumer) consumer;
			soccConsumer.setPostReader( connector.getPostReader() );
			soccConsumer.setUri( uri );
			soccConsumer.setLimit( limit );
		}

		super.configureConsumer( consumer );
	}

	@Override
	public Producer createProducer() throws Exception {
		ISoccProducer producer = new SoccPostProducer( this );
		configureProducer( producer );

		return producer;
	}

	public void configureProducer( final Producer producer ) {
		if ( producer instanceof ISoccProducer ) {
			ISoccProducer soccProducer = (ISoccProducer) producer;
			soccProducer.setPostWriter( getConnector().getPostWriter() );
			soccProducer.setUri( uri );
		}
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
}
