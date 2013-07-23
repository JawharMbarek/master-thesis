
package de.m0ep.socc.core.connector.moodle;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.rdfs.sioc.Container;
import org.rdfs.sioc.Post;

import com.google.common.base.Preconditions;

import de.m0ep.socc.core.connector.IConnector;
import de.m0ep.socc.core.connector.IConnector.IPostReader;
import de.m0ep.socc.core.exceptions.AuthenticationException;

public class Moodle2PostReader implements IPostReader {

    private Moodle2Connector connector;

    public Moodle2PostReader(Moodle2Connector moodle2Connector) {
        this.connector = Preconditions.checkNotNull(moodle2Connector,
                "Required parameter moodle2Connector must be specified.");
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
    public boolean containsReplies(Post post) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<Post> readNewReplies(Date lastReplyDate, long limit, Post parentPost)
            throws AuthenticationException, IOException {
        // TODO Auto-generated method stub
        return null;
    }

}
