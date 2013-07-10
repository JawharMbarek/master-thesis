
package de.m0ep.socc.core.connector.canvaslms;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.rdfs.sioc.Container;
import org.rdfs.sioc.Post;

import com.google.common.base.Preconditions;

import de.m0ep.socc.core.connector.IConnector;
import de.m0ep.socc.core.exceptions.AuthenticationException;

public class CanvasLmsPostReader implements IConnector.IPostReader {
    private CanvasLmsConnector connector;

    public CanvasLmsPostReader(final CanvasLmsConnector connector) {
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
    public List<Post> readNewPosts(Date lastPostDate, long limit, Container container)
            throws AuthenticationException, IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean containsReplies(Post parent) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<Post> readNewReplies(Date lastReplyDate, long limit, Post parent)
            throws AuthenticationException, IOException {
        // TODO Auto-generated method stub
        return null;
    }
}
