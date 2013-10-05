package de.m0ep.socc.camel;

import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultMessage;
import org.apache.camel.impl.ScheduledPollConsumer;
import org.ontoware.rdf2go.RDF2Go;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.Syntax;
import org.ontoware.rdf2go.model.node.URI;
import org.rdfs.sioc.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import de.m0ep.socc.core.connector.IConnector;
import de.m0ep.socc.core.connector.IConnector.IPostReader;
import de.m0ep.socc.core.utils.DateUtils;
import de.m0ep.socc.core.utils.RdfUtils;

public class SoccPostPollConsumer extends ScheduledPollConsumer implements ISoccConsumer {
	private static final Syntax DEFAULT_SYNTAX = Syntax.RdfXml;

	private static final Logger LOG = LoggerFactory.getLogger( SoccPostPollConsumer.class );

	private IPostReader<? extends IConnector> postReader;
	private URI uri;
	private int limit;
	private Date lastPollTime = null;

	public SoccPostPollConsumer( Endpoint endpoint, Processor processor ) {
		super( endpoint, processor );
		lastPollTime = new Date( 0 );
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
	public URI getUri() {
		return uri;
	}

	@Override
	public void setUri( URI uri ) {
		this.uri = Preconditions.checkNotNull( uri,
		        "Required parameter uri must be specified." );
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
		List<Post> posts = postReader.pollPosts( uri, lastPollTime, limit );

		LOG.info( "Polling posts from connector '{}' uri='{}' since='{}' limit='{}'",
		        postReader.getConnector().getId(),
		        uri,
		        ( null != lastPollTime ) ? DateUtils.formatISO8601( lastPollTime ) : "",
		        limit );

		if ( !posts.isEmpty() ) {
			Model tmpModel = RDF2Go.getModelFactory().createModel();
			tmpModel.open();

			try {
				// copy namespaces
				Model model = postReader.getConnector().getContext().getModel();
				for ( Entry<String, String> entry : model.getNamespaces().entrySet() ) {
					tmpModel.setNamespace( entry.getKey(), entry.getValue() );
				}
				// add posts to temporary model
				for ( Post post : posts ) {
					tmpModel.addAll( RdfUtils.getAllStatements( model, post ).iterator() );
				}
				Message msg = new DefaultMessage();
				msg.setBody( RdfUtils.modelToString( tmpModel, DEFAULT_SYNTAX ) );
				msg.setHeader( Exchange.CONTENT_TYPE, DEFAULT_SYNTAX.getMimeType() );

				Exchange ex = getEndpoint().createExchange();
				ex.setIn( msg );

				getProcessor().process( ex );
			} finally {
				tmpModel.close();
			}
		}

		lastPollTime.setTime( System.currentTimeMillis() );

		LOG.info( "Polled {} posts from connector '{}'.",
		        posts.size(),
		        postReader.getConnector().getId() );

		return posts.size();
	}
}
