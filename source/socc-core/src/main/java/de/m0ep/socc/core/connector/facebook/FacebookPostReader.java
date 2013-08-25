/*
 * The MIT License (MIT) Copyright © 2013 Florian Müller
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the “Software”), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package de.m0ep.socc.core.connector.facebook;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ontoware.rdf2go.model.node.URI;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Post;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.restfb.Connection;
import com.restfb.Parameter;
import com.restfb.exception.FacebookException;
import com.restfb.json.JsonObject;

import de.m0ep.socc.core.connector.DefaultConnectorIOComponent;
import de.m0ep.socc.core.connector.IConnector.IPostReader;
import de.m0ep.socc.core.connector.facebook.FacebookSiocUtils.Connections;
import de.m0ep.socc.core.connector.facebook.FacebookSiocUtils.Fields;
import de.m0ep.socc.core.connector.facebook.FacebookSiocUtils.RequestParameters;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.exceptions.NotFoundException;

public class FacebookPostReader extends
        DefaultConnectorIOComponent<FacebookConnector> implements IPostReader<FacebookConnector> {

	private final FacebookClientWrapper defaultClient;

	public FacebookPostReader( FacebookConnector connector ) {
		super( connector );

		this.defaultClient = connector.getServiceClientManager()
		        .getDefaultClient();
	}

	@Override
	public Post readPost( URI uri )
	        throws NotFoundException,
	        AuthenticationException,
	        IOException {

		if ( Post.hasInstance( getModel(), uri ) ) {
			return Post.getInstance( getModel(), uri );
		}

		Pattern pattern = Pattern.compile( FacebookSiocUtils.REGEX_FACEBOOK_URI );
		Matcher matcher = pattern.matcher( uri.toString() );

		if ( matcher.find() ) {
			String id = matcher.group( 1 );
			JsonObject object = null;

			try {
				object = defaultClient.getFacebookClient().fetchObject(
				        "/" + id,
				        JsonObject.class,
				        Parameter.with( RequestParameters.METADATA, 1 ) );
			} catch ( FacebookException e ) {
				FacebookConnector.handleFacebookException( e );
			} catch ( Exception e ) {
				Throwables.propagate( e );
			}

			if ( null != object ) {
				if ( FacebookSiocUtils.hasConnection( object, Connections.COMMENTS ) ) {
					return FacebookSiocUtils.createSiocPost( getConnector(), object, null, null );
				}
			}
		}

		throw new NotFoundException(
		        "The uri "
		                + uri
		                + " is no post at "
		                + getServiceEndpoint() );
	}

	@Override
	public List<Post> pollPosts( URI sourceUri, Date since, int limit )
	        throws AuthenticationException, IOException {
		if ( Forum.hasInstance( getModel(), sourceUri ) ) {
			return pollPostsAtContainer( Forum.getInstance( getModel(), sourceUri ), since, limit );
		} else if ( Post.hasInstance( getModel(), sourceUri ) ) {
			return pollRepliesAtPost( Post.getInstance( getModel(), sourceUri ), since, limit );
		}

		Pattern pattern = Pattern.compile( FacebookSiocUtils.REGEX_FACEBOOK_URI );
		Matcher matcher = pattern.matcher( sourceUri.toString() );

		if ( matcher.find() ) {
			String id = matcher.group( 1 );
			JsonObject object = null;

			try {
				object = defaultClient.getFacebookClient().fetchObject(
				        "/" + id,
				        JsonObject.class,
				        Parameter.with( RequestParameters.METADATA, 1 ) );
			} catch ( FacebookException e ) {
				FacebookConnector.handleFacebookException( e );
			} catch ( Exception e ) {
				Throwables.propagate( e );
			}

			if ( null != object ) {
				if ( FacebookSiocUtils.hasConnection( object, Connections.FEED ) ) {
					Container container = FacebookSiocUtils.createSiocForum(
					        getConnector(),
					        object );
					return pollPostsAtContainer( container, since, limit );
				} else if ( FacebookSiocUtils.hasConnection( object, Connections.COMMENTS ) ) {
					Post post = FacebookSiocUtils.createSiocPost(
					        getConnector(),
					        object,
					        null,
					        null );
					return pollRepliesAtPost( post, since, limit );
				}
			}
		}

		throw new IOException(
		        "Can't read post from uri "
		                + sourceUri
		                + " at "
		                + getServiceEndpoint() );
	}

	public List<Post> pollPostsAtContainer( Container container, Date since, long limit )
	        throws AuthenticationException, IOException {
		if ( 0 == limit ) {
			return Lists.newArrayList();
		}

		Preconditions.checkNotNull( container,
		        "Required parameter container must be specified." );

		Parameter paramSince = Parameter.with(
		        RequestParameters.SINCE,
		        ( null != since ) ? ( since.getTime() / 1000L ) : ( 0 ) );

		Parameter paramLimit = Parameter.with(
		        RequestParameters.LIMIT,
		        ( 0 < limit || limit < 25 ) ? ( limit ) : ( 25 ) );

		Connection<JsonObject> feed = null;
		try {
			feed = defaultClient.getFacebookClient().fetchConnection(
			        container.getId()
			                + "/"
			                + Connections.FEED,
			        JsonObject.class,
			        paramSince,
			        paramLimit );
		} catch ( FacebookException e ) {
			FacebookConnector.handleFacebookException( e );
		}

		List<Post> results = Lists.newArrayList();
		if ( null != feed ) {
			for ( List<JsonObject> objectList : feed ) {
				for ( JsonObject object : objectList ) {
					if ( 0 > limit || limit > results.size() ) {
						Date createdDate = null;
						if ( object.has( Fields.CREATED_TIME ) ) {
							createdDate = com.restfb.util.DateUtils
							        .toDateFromLongFormat(
							        object.getString( Fields.CREATED_TIME ) );
						}

						if ( null == since || null == createdDate || createdDate.after( since ) ) {
							results.add( FacebookSiocUtils.createSiocPost(
							        getConnector(),
							        object,
							        container,
							        null ) );
						} else {
							return results;
						}
					} else {
						return results;
					}
				}
			}
		}

		return results;
	}

	public List<Post> pollRepliesAtPost( Post parentPost, Date since, long limit )
	        throws AuthenticationException, IOException {
		Preconditions.checkNotNull( parentPost,
		        "Required parameter parentPost must be specified." );

		Parameter paramSince = Parameter.with(
		        RequestParameters.SINCE,
		        ( null != since ) ? ( since.getTime() / 1000L ) : ( 0 ) );
		Parameter paramLimit = Parameter.with(
		        RequestParameters.LIMIT,
		        ( 0 < limit || limit < 25 ) ? ( limit ) : ( 25 ) );

		Parameter paramFields = Parameter.with(
		        RequestParameters.FIELDS,
		        FacebookSiocUtils.FIELDS_COMMENT );

		Connection<JsonObject> feed = null;
		try {
			feed = defaultClient.getFacebookClient().fetchConnection(
			        parentPost.getId()
			                + "/"
			                + Connections.COMMENTS,
			        JsonObject.class,
			        paramSince,
			        paramLimit,
			        paramFields );
		} catch ( FacebookException e ) {
			FacebookConnector.handleFacebookException( e );
		}

		List<Post> results = Lists.newArrayList();
		if ( null != feed ) {
			for ( List<JsonObject> objectList : feed ) {
				for ( JsonObject object : objectList ) {
					if ( 0 > limit || limit > results.size() ) {
						Date createdDate = null;
						if ( object.has( Fields.CREATED_TIME ) ) {
							createdDate = com.restfb.util.DateUtils
							        .toDateFromLongFormat(
							        object.getString( Fields.CREATED_TIME ) );
						}

						if ( null == since || null == createdDate || createdDate.after( since ) ) {
							results.add( FacebookSiocUtils.createSiocPost(
							        getConnector(),
							        object,
							        parentPost.hasContainer()
							                ? parentPost.getContainer()
							                : null,
							        null ) );
						} else {
							return results;
						}
					} else {
						return results;
					}
				}
			}
		}

		return results;
	}
}
