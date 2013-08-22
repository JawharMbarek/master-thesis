package de.m0ep.socc.core.connector.google.plus;

import java.io.IOException;

import org.rdfs.sioc.Container;
import org.rdfs.sioc.Post;

import de.m0ep.socc.core.connector.DefaultConnectorIOComponent;
import de.m0ep.socc.core.connector.IConnector.IPostWriter;
import de.m0ep.socc.core.exceptions.AuthenticationException;

public class GooglePlusPostWriter extends DefaultConnectorIOComponent<GooglePlusConnector>
        implements IPostWriter<GooglePlusConnector> {

	public GooglePlusPostWriter( GooglePlusConnector connector ) {
		super( connector );
	}

	@Override
	public boolean canPostTo( Container container ) {
		return false;
	}

	@Override
	public void writePost( Post post, Container container ) throws AuthenticationException,
	        IOException {
		throw new UnsupportedOperationException(
		        "Google Plus supports currently no writing of posts" );
	}

	@Override
	public boolean canReplyTo( Post post ) {
		return false;
	}

	@Override
	public void writeReply( Post replyPost, Post parentPost ) throws AuthenticationException,
	        IOException {
		throw new UnsupportedOperationException(
		        "Google Plus supports currently no writing of posts" );
	}

}
