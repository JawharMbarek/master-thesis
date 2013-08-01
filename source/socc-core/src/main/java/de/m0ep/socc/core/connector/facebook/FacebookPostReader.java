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
import java.util.Date;
import java.util.List;

import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Post;

import com.google.common.base.Preconditions;

import de.m0ep.socc.core.connector.IConnector;
import de.m0ep.socc.core.connector.IConnector.IPostReader;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.utils.RdfUtils;

public class FacebookPostReader implements IPostReader {

    private FacebookConnector connector;
    private String endpointUri;

    public FacebookPostReader(FacebookConnector connector) {
        this.connector = Preconditions.checkNotNull(connector,
                "Required parameter connector must be specified.");

        this.endpointUri = connector.getService().getServiceEndpoint().toString();
    }

    @Override
    public IConnector getConnector() {
        return connector;
    }

    @Override
    public boolean containsPosts(Container container) {
        return null != container &&
                container.toString().startsWith(endpointUri) &&
                RdfUtils.isType(container.getModel(), container.getResource(), Forum.RDFS_CLASS) &&
                container.hasId() &&
                (container.getId().startsWith("wall:") ||
                container.getId().startsWith("group:"));
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
