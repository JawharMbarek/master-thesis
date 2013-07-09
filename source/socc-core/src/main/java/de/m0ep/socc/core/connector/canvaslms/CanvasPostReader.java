
package de.m0ep.socc.core.connector.canvaslms;

import java.io.IOException;
import java.util.List;

import org.rdfs.sioc.Container;
import org.rdfs.sioc.Post;

import com.google.common.base.Preconditions;

import de.m0ep.socc.core.connector.IConnector;
import de.m0ep.socc.core.connector.IPostReader;
import de.m0ep.socc.core.exceptions.AuthenticationException;

public class CanvasPostReader implements IPostReader {
    private CanvasLmsConnector connector;

    public CanvasPostReader(final CanvasLmsConnector connector) {
        this.connector = Preconditions.checkNotNull(
                connector,
                "Required parameter connector must be specified.");
    }

    @Override
    public IConnector getConnector() {
        return connector;
    }

    @Override
    public boolean containsPosts(Container container) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<Post> readNewPosts(long limit, Container container) throws AuthenticationException,
            IOException {

        return null;
    }

    @Override
    public boolean containsReplies(Post parent) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<Post> readNewReplies(long limit, Post parent) throws AuthenticationException,
            IOException {

        return null;
    }

}
