package de.m0ep.socc.camel;

import java.util.Date;
import java.util.List;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultMessage;
import org.apache.camel.impl.ScheduledPollConsumer;
import org.ontoware.rdf2go.model.Syntax;
import org.ontoware.rdf2go.model.node.URI;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Item;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.Thing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;

import de.m0ep.socc.core.connector.IConnector;
import de.m0ep.socc.core.connector.IConnector.IPostReader;
import de.m0ep.socc.core.exceptions.NotFoundException;
import de.m0ep.socc.core.utils.RdfUtils;
import de.m0ep.socc.core.utils.SiocUtils;

public class SoccPostPollConsumer extends ScheduledPollConsumer implements ISoccConsumer {
	private static final Logger LOG = LoggerFactory.getLogger( SoccPostPollConsumer.class );

	private IPostReader<? extends IConnector> postReader;
	private URI uri;
	private Thing thing;
	private int limit;

	public SoccPostPollConsumer( Endpoint endpoint, Processor processor ) {
		super( endpoint, processor );
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
		if ( null == thing ) {
			thing = createThing( postReader.getConnector(), uri );
		}

		Date since = getSince( thing );
		LOG.debug( "Polling posts uri='{}' since='{}' limit='{}'", uri, since, limit );
		List<Post> posts = postReader.pollPosts( uri, since, limit );

		for ( Post post : posts ) {
			Message msg = new DefaultMessage();
			msg.setBody( RdfUtils.resourceToString( post, Syntax.RdfXml ) );
			msg.setHeader( Exchange.CONTENT_TYPE, Syntax.RdfXml.getMimeType() );

			Exchange ex = getEndpoint().createExchange();
			ex.setIn( msg );
			getProcessor().process( ex );
		}

		LOG.info( "polled {} posts.", posts.size() );

		return posts.size();
	}

	private Thing createThing( IConnector connector, URI uri ) throws NotFoundException {
		try {
			if ( connector.getStructureReader().isContainer( uri ) ) {
				LOG.debug( "'{}' is a container uri", uri );
				return connector.getStructureReader().getContainer( uri );
			} else if ( connector.getPostReader().isPost( uri ) ) {
				LOG.debug( "'{}' is a post uri", uri );
				return connector.getPostReader().getPost( uri );
			}
		} catch ( Exception e ) {
			Throwables.propagate( e );
		}

		throw new NotFoundException( "The uri '" + uri
		        + "' is neither a container or a post" );
	}

	private Date getSince( Thing thing ) {
		if ( thing instanceof Container ) {
			return SiocUtils.getLastItemDate( (Container) thing );
		} else if ( thing instanceof Item ) {
			return SiocUtils.getLastReplyDate( (Item) thing );
		}

		return null;
	}
}
