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

package de.m0ep.socc.core.connector.google.plus;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ontoware.rdf2go.model.node.URI;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Site;

import com.google.api.services.plus.model.ActivityFeed;
import com.google.api.services.plus.model.Person;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import de.m0ep.socc.core.connector.DefaultConnectorIOComponent;
import de.m0ep.socc.core.connector.IConnector.IStructureReader;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.exceptions.NotFoundException;

public class GooglePlusStructureReader extends
        DefaultConnectorIOComponent<GooglePlusConnector> implements
        IStructureReader<GooglePlusConnector> {

	private final GooglePlusClientWrapper defaultClient;
	private Container defaultsPublicFeed;

	public GooglePlusStructureReader( GooglePlusConnector connector ) {
		super( connector );

		this.defaultClient = getConnector().getClientManager().getDefaultClient();
	}

	@Override
	public Site getSite() {
		Site result = null;
		if ( Site.hasInstance( getModel(), getServiceEndpoint() ) ) {
			result = Site.getInstance( getModel(), getServiceEndpoint() );
		} else {
			result = new Site( getModel(), getServiceEndpoint(), true );
			result.setName( "Google Plus" );
		}

		return result;
	}

	@Override
	public Container getContainer( URI uri )
	        throws NotFoundException,
	        AuthenticationException,
	        IOException {
		Preconditions.checkNotNull( uri,
		        "Required parameter uri must be specified." );

		if ( Forum.hasInstance( getModel(), uri ) ) {
			return Forum.getInstance( getModel(), uri );
		}

		Pattern pattern = Pattern.compile( "^"
		        + GooglePlusSiocUtils.GOOGLE_PLUS_ROOT_URI
		        + GooglePlusSiocUtils.REGEX_ACTIVITY_FEED_URI );
		Matcher matcher = pattern.matcher( uri.toString() );

		if ( matcher.find() && 3 <= matcher.groupCount() ) {
			String userId = matcher.group( 1 );
			String collection = matcher.group( 2 );

			try {
				// check if this activity feed really exists
				ActivityFeed activityFeed = defaultClient.getGooglePlusService()
				        .activities()
				        .list( userId, collection )
				        .setMaxResults( 1L )
				        .setFields( "title" )
				        .execute();
				if ( null != activityFeed ) {
					Person person = defaultClient.getGooglePlusService()
					        .people()
					        .get( userId )
					        .execute();

					return GooglePlusSiocUtils.createSiocForum(
					        getConnector(),
					        person,
					        collection );
				}
			} catch ( Exception e ) {
				GooglePlusConnector.handleGoogleException( e );
			}
		}

		throw new NotFoundException( "No Google+ container found at uri " + uri );
	}

	@Override
	public List<Container> listContainer()
	        throws NotFoundException,
	        AuthenticationException,
	        IOException {
		if ( null == defaultsPublicFeed ) {
			URI publicFeedUri = GooglePlusSiocUtils.createActivityFeedUri(
			        defaultClient.getPerson().getId(),
			        GooglePlusSiocUtils.COLLECTION_PUBLIC );
			defaultsPublicFeed = getContainer( publicFeedUri );
		}

		return Lists.newArrayList( defaultsPublicFeed );
	}

	@Override
	public boolean hasChildContainer( URI uri ) {
		return false;
	}

	@Override
	public List<Container> listContainer( URI parentUri ) {
		throw new UnsupportedOperationException(
		        "listContainers is not supported by Google+ Connector" );
	}
}
