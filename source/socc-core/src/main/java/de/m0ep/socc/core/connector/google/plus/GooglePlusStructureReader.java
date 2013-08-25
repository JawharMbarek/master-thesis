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

import org.ontoware.rdf2go.model.node.URI;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Site;
import org.rdfs.sioc.Thread;

import com.google.api.client.repackaged.com.google.common.base.Throwables;
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
	private Forum defaultForum;

	public GooglePlusStructureReader( GooglePlusConnector connector ) {
		super( connector );

		this.defaultClient = getConnector().getClientManager()
		        .getDefaultClient();
		try {
			this.defaultForum = getForum(
			        GooglePlusSiocConverter.PUBLIC_FEED_ID_PREFIX
			                + defaultClient.getPerson().getId() );
		} catch ( Exception e ) {
			Throwables.propagate( e );
		}
	}

	@Override
	public Site getSite() {
		Site result = null;
		if ( Site.hasInstance( getModel(), getServiceEndpoint() ) ) {
			result = Site.getInstance( getModel(), getServiceEndpoint() );
		} else {
			result = new Site( getModel(), getServiceEndpoint(), true );
		}

		result.setName( "Google Plus" );

		return result;
	}

	@Override
	public Container getContainer( URI uri ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Container> listContainer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Container> listContainer( URI parent ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Forum getForum( String id ) throws NotFoundException,
	        AuthenticationException, IOException {
		Preconditions.checkNotNull( id,
		        "Required parameter id must be specified." );
		Preconditions.checkArgument( !id.isEmpty(),
		        "Required parameter id may not be empty." );

		if ( id.startsWith( GooglePlusSiocConverter.PUBLIC_FEED_ID_PREFIX ) ) {
			String gId = id.substring( id.lastIndexOf( ':' ) + 1 );

			Person person = null;
			try {
				person = defaultClient.getService().people().get( gId ).execute();
			} catch ( Exception e ) {
				throw new NotFoundException( "No forum found with the id " + id );
			}

			if ( null != person ) {
				return GooglePlusSiocConverter.createSiocForum( getConnector(),
				        person );
			}
		}

		throw new NotFoundException( "No forum found with the id " + id );
	}

	@Override
	public List<Forum> listForums() throws AuthenticationException, IOException {
		return Lists.newArrayList( defaultForum );
	}

	@Override
	public Thread getThread( String id, Container container )
	        throws NotFoundException, AuthenticationException, IOException {
		throw new UnsupportedOperationException(
		        "Google Plus doesn't no something like 'threads'." );
	}

	@Override
	public List<Thread> listThreads( Container container )
	        throws AuthenticationException, IOException {
		throw new UnsupportedOperationException(
		        "Google Plus doesn't no something like 'threads'." );
	}
}
