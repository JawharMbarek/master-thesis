package de.m0ep.socc.core.connector.google.plus;

import java.io.IOException;

import org.ontoware.rdf2go.model.Syntax;
import org.ontoware.rdf2go.model.node.URI;

import de.m0ep.socc.core.connector.DefaultConnectorIOComponent;
import de.m0ep.socc.core.connector.IConnector.IPostWriter;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.exceptions.NotFoundException;

public class GooglePlusPostWriter extends DefaultConnectorIOComponent<GooglePlusConnector>
        implements IPostWriter<GooglePlusConnector> {

	public GooglePlusPostWriter( GooglePlusConnector connector ) {
		super( connector );
	}

	@Override
	public void writePost( URI targetUri, String rdfString, Syntax syntax )
	        throws NotFoundException, AuthenticationException, IOException {
		throw new UnsupportedOperationException(
		        "Google Plus supports currently no writing of posts" );
	}
}
