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

import org.ontoware.rdf2go.model.Syntax;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.util.Builder;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Site;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import de.m0ep.socc.core.utils.RdfUtils;

/**
 * Implementation of an {@link IStructureReader} for a {@link FacebookConnector}
 * .
 * 
 * @author Florian Müller
 * 
 */
public class FacebookStructureReader extends DefaultConnectorIOComponent<FacebookConnector>
        implements IStructureReader<FacebookConnector> {
	private static final Logger LOG = LoggerFactory.getLogger( FacebookStructureReader.class );

	private final FacebookClientWrapper defaultClient;

	public FacebookStructureReader( FacebookConnector connector ) {
		super( connector );
		this.defaultClient = connector.getClientManager()
		        .getDefaultClient();
	}

	@Override
	public Site getSite() {
		URI uri = Builder.createURI( "https://www.facebook.com" );

		if ( !Site.hasInstance( getModel(), uri ) ) {
			Site result = new Site( getModel(), uri, true );
			result.setName( "Facebook" );
			return result;
		}

		return Site.getInstance( getModel(), uri );
	}

	@Override
	public boolean isContainer( URI uri ) {
		if ( Forum.hasInstance( getModel(), uri ) ) {
			return true;
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
			} catch ( Exception e ) {
				Throwables.propagate( e );
			}

			if ( FacebookSiocUtils.hasConnection( object, Connections.FEED ) ) {
				FacebookSiocUtils.createSiocForum( getConnector(), object );
				return true;
			}
		}

		return false;
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
					if ( LOG.isDebugEnabled() ) {
						LOG.debug( "Read post feed {} from {}:\n{}",
						        object.get( FacebookSiocUtils.Fields.ID ),
						        uri,
						        object.toString( 2 ) );
					} else {
						LOG.info( "Read post feed {} from {}.",
						        object.get( FacebookSiocUtils.Fields.ID ),
						        uri );
					}

					Forum resultForum = FacebookSiocUtils.createSiocForum( getConnector(), object );

					if ( LOG.isDebugEnabled() ) {
						LOG.debug( "Convert post feed {} to SIOC:\n{}",
						        resultForum.getId(),
						        RdfUtils.resourceToString( resultForum, Syntax.Turtle ) );
					} else {
						LOG.info( "Read post feed {} to SIOC {}.",
						        resultForum.getId(),
						        resultForum );
					}

					return resultForum;
				}
			}
		}

		throw new NotFoundException( "No container of "
		        + getServiceEndpoint()
		        + " found at "
		        + uri );
	}

	@Override
	public List<Container> listContainer() throws AuthenticationException, NotFoundException,
	        IOException {
		List<Container> result = Lists.newArrayList();

		Connection<JsonObject> groupConnection = null;
		try {
			Parameter fields = Parameter.with(
			        RequestParameters.FIELDS,
			        FacebookSiocUtils.FIELDS_GROUP );

			groupConnection = defaultClient.getFacebookClient().fetchConnection(
			        "/me/" + Connections.GROUPS,
			        JsonObject.class,
			        fields );
		} catch ( FacebookException e ) {
			FacebookConnector.handleFacebookException( e );
		} catch ( Exception e ) {
			Throwables.propagate( e );
		}

		if ( null != groupConnection ) {
			for ( List<JsonObject> list : groupConnection ) {
				for ( JsonObject object : list ) {
					if ( LOG.isDebugEnabled() ) {
						LOG.debug( "Read group feed {} from {}:\n{}",
						        object.get( FacebookSiocUtils.Fields.ID ),
						        getServiceEndpoint(),
						        object.toString( 2 ) );
					} else {
						LOG.info( "Read group feed {} from {}.",
						        object.get( FacebookSiocUtils.Fields.ID ),
						        getServiceEndpoint() );
					}

					Forum resultForum = FacebookSiocUtils.createSiocForum(
					        getConnector(),
					        object );

					result.add( resultForum );

					if ( LOG.isDebugEnabled() ) {
						LOG.debug( "Convert group feed {} to SIOC:\n{}",
						        resultForum.getId(),
						        RdfUtils.resourceToString( resultForum, Syntax.Turtle ) );
					} else {
						LOG.info( "Read group feed {} to SIOC {}.",
						        resultForum.getId(),
						        resultForum );
					}
				}
			}
		} else {
			LOG.warn( "GroupConnection was null" );
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
