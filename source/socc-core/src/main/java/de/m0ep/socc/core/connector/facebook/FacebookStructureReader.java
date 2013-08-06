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

import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.util.Builder;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Site;
import org.rdfs.sioc.Thread;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.restfb.Connection;
import com.restfb.Parameter;
import com.restfb.exception.FacebookException;
import com.restfb.types.FacebookType;
import com.restfb.types.Group;

import de.m0ep.socc.core.connector.AbstractConnectorIOComponent;
import de.m0ep.socc.core.connector.IConnector.IStructureReader;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.exceptions.NotFoundException;

public class FacebookStructureReader extends
        AbstractConnectorIOComponent<FacebookConnector>
        implements
        IStructureReader {

    private FacebookClientWrapper defaultClient;

    public FacebookStructureReader(FacebookConnector connector) {
        super(connector);
        this.defaultClient = connector.getServiceClientManager()
                .getDefaultClient();
    }

    @Override
    public Site getSite() {
        if (!Site.hasInstance(getModel(), getServiceEndpoint())) {
            Site result = new Site(getModel(), getServiceEndpoint(), true);
            result.setName("Facebook");
            return result;
        }

        return Site.getInstance(getModel(), getServiceEndpoint());
    }

    @Override
    public Forum getForum(String id) throws NotFoundException,
            AuthenticationException, IOException {
        Preconditions.checkNotNull(id,
                "Required parameter id must be specified.");
        Preconditions.checkArgument(!id.isEmpty()
                , "Required parameter id may not be empty.");

        int seperatorIndex = id.lastIndexOf(FacebookSiocConverter.ID_SEPERATOR);
        if (-1 != seperatorIndex) {
            String fbId = id.substring(seperatorIndex + 1);

            if (id.startsWith(FacebookSiocConverter.WALL_ID_PREFIX)) {
                URI uri = Builder.createURI(
                        getServiceEndpoint()
                                + FacebookSiocConverter.WALL_URI_PATH
                                + fbId);

                if (Forum.hasInstance(getModel(), uri)) {
                    return Forum.getInstance(getModel(), uri);
                } else {
                    try {
                        // check if this is a valid user id.
                        // there should be an exception if not.
                        defaultClient.getClient().fetchObject(
                                "/" + fbId, FacebookType.class);
                    } catch (FacebookException e) {
                        FacebookConnector.handleFacebookException(e);
                    }

                    return FacebookSiocConverter.createSiocForum(
                            getConnector(),
                            defaultClient.getUser());
                }
            } else if (id.startsWith(FacebookSiocConverter.GROUP_ID_PREFIX)) {
                URI uri = Builder.createURI(
                        getServiceEndpoint()
                                + FacebookSiocConverter.GROUP_URI_PATH
                                + fbId);

                if (Forum.hasInstance(getModel(), uri)) {
                    return Forum.getInstance(getModel(), uri);
                } else {
                    Group group = null;

                    try {
                        group = defaultClient
                                .getClient()
                                .fetchObject(
                                        fbId,
                                        Group.class,
                                        Parameter
                                                .with(FacebookApiConstants.PARAM_FIELDS,
                                                        FacebookApiConstants.FIELD_ID
                                                                + ","
                                                                + FacebookApiConstants.FIELD_NAME
                                                                + ","
                                                                + FacebookApiConstants.FIELD_DESCRIPTION));

                    } catch (FacebookException e) {
                        FacebookConnector.handleFacebookException(e);
                    }

                    return FacebookSiocConverter.createSiocForum(
                            getConnector(),
                            group);
                }
            }
        }

        throw new NotFoundException("No forum found with id " + id);
    }

    @Override
    public List<Forum> listForums() throws AuthenticationException, IOException {
        List<Forum> results = Lists.newArrayList();

        // add the default users wall
        results.add(getForum(FacebookSiocConverter.WALL_ID_PREFIX
                + defaultClient.getUser().getId()));

        Connection<Group> groupsConnections = null;
        try {
            groupsConnections = defaultClient
                    .getClient()
                    .fetchConnection(
                            "me/groups",
                            Group.class,
                            Parameter
                                    .with(FacebookApiConstants.PARAM_FIELDS,
                                            FacebookApiConstants.FIELD_ID
                                                    + ","
                                                    + FacebookApiConstants.FIELD_NAME
                                                    + ","
                                                    + FacebookApiConstants.FIELD_DESCRIPTION));
        } catch (FacebookException e) {
            FacebookConnector.handleFacebookException(e);
        }

        for (List<Group> list : groupsConnections) {
            for (Group group : list) {
                results.add(FacebookSiocConverter.createSiocForum(
                        getConnector(),
                        group));
            }
        }

        return results;
    }

    @Override
    public Thread getThread(String id, Container parent)
            throws NotFoundException,
            AuthenticationException, IOException {
        throw new UnsupportedOperationException(
                "Facbook has nothing like 'threads'.");
    }

    @Override
    public List<Thread> listThreads(Container parent)
            throws AuthenticationException, IOException {
        throw new UnsupportedOperationException(
                "Facbook has nothing like 'threads'.");
    }

}
