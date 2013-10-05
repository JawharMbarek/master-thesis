package de.m0ep.socc.camel;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.impl.DefaultProducer;
import org.ontoware.rdf2go.model.Syntax;
import org.ontoware.rdf2go.model.node.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import de.m0ep.socc.core.connector.IConnector;
import de.m0ep.socc.core.connector.IConnector.IPostWriter;

public class SoccPostProducer extends DefaultProducer implements ISoccProducer {
	private static final Logger LOG = LoggerFactory.getLogger( SoccPostProducer.class );

	private IPostWriter<? extends IConnector> postWriter;
	private URI uri;

	public SoccPostProducer( final Endpoint endpoint ) {
		super( endpoint );
	}

	@Override
	public IPostWriter<? extends IConnector> getPostWriter() {
		return postWriter;
	}

	@Override
	public void setPostWriter( final IPostWriter<? extends IConnector> postWriter ) {
		this.postWriter = Preconditions.checkNotNull( postWriter,
		        "Required parameter postWriter must be specified." );
	}

	@Override
	public URI getUri() {
		return uri;
	}

	@Override
	public void setUri( final URI uri ) {
		this.uri = Preconditions.checkNotNull( uri,
		        "Required parameter uri must be specified." );
	}

	@Override
	public void process( final Exchange exchange ) throws Exception {
		Message msg = exchange.getIn();
		String contentType = msg.getHeader( Exchange.CONTENT_TYPE ).toString();
		String rdfString = msg.getBody().toString();
		Syntax syntax = getSyntax( contentType );

		if ( LOG.isDebugEnabled() ) {
			LOG.debug( "Write post(s) to uri='{}' syntax='{}' with connector '{}':\n{}",
			        uri,
			        syntax,
			        postWriter.getConnector().getId(),
			        rdfString );
		} else {
			LOG.info( "Write post(s) to uri='{}' syntax='{}' with connector '{}'",
			        uri,
			        syntax,
			        postWriter.getConnector().getId() );
		}

		postWriter.writePosts( uri, rdfString, syntax );
	}

	private Syntax getSyntax( String contentType ) {
		if ( Syntax.RdfXml.getMimeType().equals( contentType ) ) {
			return Syntax.RdfXml;
		} else if ( Syntax.Turtle.getMimeType().equals( contentType ) ) {
			return Syntax.Turtle;
		} else if ( Syntax.JsonLd.getMimeType().equals( contentType ) ) {
			return Syntax.JsonLd;
		} else if ( Syntax.Nquads.getMimeType().equals( contentType ) ) {
			return Syntax.Nquads;
		} else if ( Syntax.Ntriples.getMimeType().equals( contentType ) ) {
			return Syntax.Ntriples;
		} else if ( Syntax.RdfJson.getMimeType().equals( contentType ) ) {
			return Syntax.RdfJson;
		} else if ( Syntax.Trig.getMimeType().equals( contentType ) ) {
			return Syntax.Trig;
		} else if ( Syntax.Trix.getMimeType().equals( contentType ) ) {
			return Syntax.Trix;
		}

		LOG.warn( "Received message with unknown content-type: '{}'", contentType );

		return null;
	}
}
