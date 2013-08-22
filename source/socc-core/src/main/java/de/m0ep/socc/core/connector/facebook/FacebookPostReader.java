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

import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Post;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.restfb.Connection;
import com.restfb.Parameter;
import com.restfb.exception.FacebookException;
import com.restfb.json.JsonObject;

import de.m0ep.socc.core.connector.DefaultConnectorIOComponent;
import de.m0ep.socc.core.connector.IConnector.IPostReader;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.utils.RdfUtils;
import de.m0ep.socc.core.utils.SiocUtils;

public class FacebookPostReader extends
        DefaultConnectorIOComponent<FacebookConnector> implements IPostReader<FacebookConnector> {

	private final FacebookClientWrapper defaultClient;

	public FacebookPostReader( FacebookConnector connector ) {
		super( connector );

		this.defaultClient = connector.getServiceClientManager()
		        .getDefaultClient();
	}

	@Override
	public boolean containsPosts( Container container ) {
		return null != container
		        && SiocUtils.isContainerOfSite( container, getServiceEndpoint() )
		        && RdfUtils.isType( container, Forum.RDFS_CLASS );
	}

	@Override
	public List<Post> readNewPosts( Date since, long limit, Container container )
	        throws AuthenticationException, IOException {
		if ( 0 == limit ) {
			return Lists.newArrayList();
		}

		Preconditions.checkNotNull( container,
		        "Required parameter container must be specified." );
		Preconditions.checkArgument( containsPosts( container ),
		        "This container has no posts at this service" );
		Preconditions.checkArgument( container.hasId(),
		        "The container has no id." );

		Parameter paramSince = Parameter.with(
		        FacebookApiConstants.PARAM_SINCE,
		        ( null != since ) ? ( since.getTime() / 1000L ) : ( 0 ) );

		Parameter paramLimit = Parameter.with(
		        FacebookApiConstants.PARAM_LIMIT,
		        ( 0 < limit || limit < 25 ) ? ( limit ) : ( 25 ) );

		Parameter paramFields = Parameter.with(
		        FacebookApiConstants.PARAM_FIELDS,
		        FacebookApiConstants.FIELDS_COMMENT );

		Connection<JsonObject> feed = null;
		try {
			feed = defaultClient.getClient().fetchConnection(
			        container.getId()
			                + "/"
			                + FacebookApiConstants.CONNECTION_FEED,
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
						results.add( FacebookSiocConverter.createSiocPost(
						        getConnector(),
						        object,
						        container,
						        null ) );
					}
				}
			}
		}

		return results;
	}

	@Override
	public boolean containsReplies( Post post ) {
		return null != post
		        && post.hasContainer()
		        && containsPosts( post.getContainer() );
	}

	@Override
	public List<Post> pollRepliesAtPost( Date since, long limit, Post parentPost )
	        throws AuthenticationException, IOException {
		Preconditions.checkNotNull( parentPost,
		        "Required parameter parentPost must be specified." );
		Preconditions.checkArgument( containsReplies( parentPost ),
		        "This parentPost has no replies at this service." );
		Preconditions.checkArgument( parentPost.hasId(),
		        "The parameter parentPost has no id." );

		Parameter paramSince = Parameter.with(
		        FacebookApiConstants.PARAM_SINCE,
		        ( null != since ) ? ( since.getTime() / 1000L ) : ( 0 ) );
		Parameter paramLimit = Parameter.with(
		        FacebookApiConstants.PARAM_LIMIT,
		        ( 0 < limit || limit < 25 ) ? ( limit ) : ( 25 ) );

		Parameter paramFields = Parameter.with(
		        FacebookApiConstants.PARAM_FIELDS,
		        FacebookApiConstants.FIELDS_COMMENT );

		Connection<JsonObject> feed = null;
		try {
			feed = defaultClient.getClient().fetchConnection(
			        parentPost.getId()
			                + "/"
			                + FacebookApiConstants.CONNECTION_COMMENTS,
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
						results.add( FacebookSiocConverter.createSiocPost(
						        getConnector(),
						        object,
						        parentPost.getContainer(),
						        parentPost ) );
					}
				}
			}
		}

		return results;
	}
}
