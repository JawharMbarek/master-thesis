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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ontoware.rdf2go.model.node.URI;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Site;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.restfb.Connection;
import com.restfb.Parameter;
import com.restfb.exception.FacebookException;
import com.restfb.json.JsonObject;

import de.m0ep.socc.core.connector.DefaultConnectorIOComponent;
import de.m0ep.socc.core.connector.IConnector.IStructureReader;
import de.m0ep.socc.core.connector.facebook.FacebookSiocUtils.Connections;
import de.m0ep.socc.core.connector.facebook.FacebookSiocUtils.RequestParameters;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.exceptions.NotFoundException;

public class FacebookStructureReader extends
        DefaultConnectorIOComponent<FacebookConnector>
        implements IStructureReader<FacebookConnector> {

	private final FacebookClientWrapper defaultClient;

	public FacebookStructureReader( FacebookConnector connector ) {
		super( connector );
		this.defaultClient = connector.getClientManager()
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
	public Container getContainer( URI uri )
	        throws AuthenticationException,
	        NotFoundException,
	        IOException {
		if ( Forum.hasInstance( getModel(), uri ) ) {
			return Forum.getInstance( getModel(), uri );
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
				if ( FacebookSiocUtils.hasConnection( object, Connections.FEED ) ) {
					return FacebookSiocUtils.createSiocForum( getConnector(), object );
				}
			}
		}

		throw new NotFoundException(
		        "The uri "
		                + uri
		                + " is no container at "
		                + getServiceEndpoint() );
	}

	@Override
	public List<Container> listContainer() throws AuthenticationException, NotFoundException,
	        IOException {
		List<Container> result = Lists.newArrayList();

		result.add( getContainer( FacebookSiocUtils.createSiocUri(
		        defaultClient.getUser().getId() ) ) );

		Connection<JsonObject> groupsConnections = null;
		try {
			Parameter fields = Parameter.with(
			        RequestParameters.FIELDS,
			        FacebookSiocUtils.FIELDS_GROUP );

			groupsConnections = defaultClient.getFacebookClient().fetchConnection(
			        "me/" + Connections.GROUPS,
			        JsonObject.class,
			        fields );
		} catch ( FacebookException e ) {
			FacebookConnector.handleFacebookException( e );
		} catch ( Exception e ) {
			Throwables.propagate( e );
		}

		if ( null != groupsConnections ) {
			for ( List<JsonObject> list : groupsConnections ) {
				for ( JsonObject object : list ) {
					result.add( FacebookSiocUtils.createSiocForum(
					        getConnector(),
					        object ) );
				}
			}
		}

		return result;
	}

	@Override
	public boolean hasChildContainer( URI uri ) {
		return false;
	}

	@Override
	public List<Container> listContainer( URI parent ) {
		throw new UnsupportedOperationException( "Facbook don't support threaded containers" );
	}
}
