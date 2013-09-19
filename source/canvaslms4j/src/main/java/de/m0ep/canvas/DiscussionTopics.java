package de.m0ep.canvas;

import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.damnhandy.uri.template.UriTemplate;

import de.m0ep.canvas.exceptions.CanvasLmsException;
import de.m0ep.canvas.model.DiscussionTopic;

public class DiscussionTopics extends AbstractEndpoint {
	private static final Logger LOG = LoggerFactory.getLogger( DiscussionTopics.class );

	private static final String PARAM_TOPIC_ID = "topicId";

	private static final String PATH = "/discussion_topics";
	private static final String PATH_DISCUSSION_TOPIC = "/discussion_topics/{topicId}";

	public class List extends CanvasLmsRequest<DiscussionTopic> {
		public List() {
			super( DiscussionTopics.this.getClient(),
			        HttpGet.class,
			        getEndpointPath(),
			        DiscussionTopic.class );
		}

		@Override
		public DiscussionTopic execute() throws CanvasLmsException {
			throw new UnsupportedOperationException(
			        "execute() is not supported by List" );
		}
	}

	public class Get extends CanvasLmsRequest<DiscussionTopic> {
		public Get( long topicId ) {
			super( DiscussionTopics.this.getClient(),
			        HttpGet.class,
			        UriTemplate.fromTemplate( getParentEndpointPath() + PATH_DISCUSSION_TOPIC )
			                .set( PARAM_TOPIC_ID, topicId )
			                .expand(),
			        DiscussionTopic.class );
		}

		@Override
		public Pagination<DiscussionTopic> executePagination()
		        throws CanvasLmsException {
			throw new UnsupportedOperationException(
			        "executePagination() is not supported by Get" );
		}
	}

	public DiscussionTopics( final CanvasLmsClient client, final String parentEndpointPath ) {
		setClient( client );
		setParentEndpointPath( parentEndpointPath );
		setEndpointPath( getParentEndpointPath() + PATH );

		LOG.info( "Create DiscussionTopics for '{}' endpoint.", getParentEndpointPath() );
	}

	public List list() {
		List request = new List();
		initializeRequest( request );
		return request;
	}

	public Get get( final long topicId ) {
		Get request = new Get( topicId );
		initializeRequest( request );
		return request;
	}

	public Entries entries( final long topicId ) {
		return new Entries(
		        getClient(),
		        UriTemplate.fromTemplate(
		                getParentEndpointPath()
		                        + PATH_DISCUSSION_TOPIC )
		                .set( PARAM_TOPIC_ID, topicId )
		                .expand() );
	}
}
