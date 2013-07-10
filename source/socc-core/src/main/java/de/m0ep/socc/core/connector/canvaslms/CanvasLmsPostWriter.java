
package de.m0ep.socc.core.connector.canvaslms;

import java.io.IOException;

import org.rdfs.sioc.Container;
import org.rdfs.sioc.Post;

import com.google.common.base.Preconditions;

import de.m0ep.socc.core.connector.IConnector;
import de.m0ep.socc.core.connector.IConnector.IPostWriter;
import de.m0ep.socc.core.exceptions.AuthenticationException;

public class CanvasLmsPostWriter implements IPostWriter {

    private CanvasLmsConnector connector;

    public CanvasLmsPostWriter(final CanvasLmsConnector connector) {
        this.connector = Preconditions.checkNotNull(
                connector,
                "Required parameter connector must be specified.");
    }

    @Override
    public IConnector getConnector() {
        return connector;
    }

    @Override
    public boolean canPostTo(Container container) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void writePost(Post post, Container container) throws AuthenticationException,
            IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean canReplyTo(Post parent) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void writeReply(Post reply, Post parent) throws AuthenticationException, IOException {
        // TODO Auto-generated method stub

    }

}
