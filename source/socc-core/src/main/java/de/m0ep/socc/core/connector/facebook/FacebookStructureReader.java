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
import java.util.List;

import org.ontoware.rdf2go.model.node.URI;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Site;
import org.rdfs.sioc.Thread;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.restfb.Connection;
import com.restfb.DefaultJsonMapper;
import com.restfb.JsonMapper;
import com.restfb.Parameter;
import com.restfb.exception.FacebookException;
import com.restfb.json.JsonObject;
import com.restfb.types.Group;
import com.restfb.types.User;

import de.m0ep.socc.core.connector.DefaultConnectorIOComponent;
import de.m0ep.socc.core.connector.IConnector.IStructureReader;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.exceptions.NotFoundException;

public class FacebookStructureReader extends
        DefaultConnectorIOComponent<FacebookConnector>
        implements IStructureReader<FacebookConnector> {

	private final FacebookClientWrapper defaultClient;
	private Forum defaultClientWall;

	public FacebookStructureReader( FacebookConnector connector ) {
		super( connector );
		this.defaultClient = connector.getServiceClientManager()
		        .getDefaultClient();
	}

	@Override
	public Site getSite() {
		if ( !Site.hasInstance( getModel(), getServiceEndpoint() ) ) {
			Site result = new Site( getModel(), getServiceEndpoint(), true );
			result.setName( "Facebook" );
			return result;
		}

		return Site.getInstance( getModel(), getServiceEndpoint() );
	}

	@Override
	public Forum getForum( String id ) throws NotFoundException,
	        AuthenticationException, IOException {
		Preconditions.checkNotNull( id,
		        "Required parameter id must be specified." );
		Preconditions.checkArgument( !id.isEmpty()
		        , "Required parameter id may not be empty." );

		URI uri = FacebookSiocConverter.createSiocUri( id );

		if ( Forum.hasInstance( getModel(), uri ) ) {
			return Forum.getInstance( getModel(), uri );
		} else {
			JsonObject object = null;

			try {
				object = defaultClient.getClient().fetchObject( "/" + id,
				        JsonObject.class );
			} catch ( FacebookException e ) {
				FacebookConnector.handleFacebookException( e );
			}

			JsonMapper mapper = new DefaultJsonMapper();

			if ( object.has( FacebookApiConstants.FIELD_OWNER ) ) {
				// it's (maybe) a group
				Group group = mapper.toJavaObject(
				        object.toString(),
				        Group.class );

				return FacebookSiocConverter.createSiocForum(
				        getConnector(),
				        group );

			} else if ( object.has( FacebookApiConstants.FIELD_GENDER ) ) {
				// it's a user
				User user = mapper.toJavaObject(
				        object.toString(),
				        User.class );

				return FacebookSiocConverter.createSiocForum(
				        getConnector(),
				        user );
			}
		}

		throw new NotFoundException( "No forum found with id " + id );
	}

	@Override
	public List<Forum> listForums() throws AuthenticationException, IOException {
		List<Forum> results = Lists.newArrayList();

		if ( null == defaultClientWall ) {
			// create the wall forum of the default client.
			defaultClientWall = getForum( defaultClient.getUser().getId() );
		}

		// add the default users wall
		results.add( defaultClientWall );

		Connection<Group> groupsConnections = null;
		try {
			Parameter fields = Parameter.with(
			        FacebookApiConstants.PARAM_FIELDS,
			        FacebookApiConstants.FIELDS_GROUP );

			groupsConnections = defaultClient.getClient().fetchConnection(
			        "me/" + FacebookApiConstants.CONNECTION_GROUPS,
			        Group.class,
			        fields );
		} catch ( FacebookException e ) {
			FacebookConnector.handleFacebookException( e );
		}

		for ( List<Group> list : groupsConnections ) {
			for ( Group group : list ) {
				results.add( FacebookSiocConverter.createSiocForum(
				        getConnector(),
				        group ) );
			}
		}

		return results;
	}

	@Override
	public Thread getThread( String id, Container parent )
	        throws NotFoundException,
	        AuthenticationException, IOException {
		throw new UnsupportedOperationException(
		        "Facbook has nothing like 'threads'." );
	}

	@Override
	public List<Thread> listThreads( Container parent )
	        throws AuthenticationException, IOException {
		throw new UnsupportedOperationException(
		        "Facbook has nothing like 'threads'." );
	}

}
