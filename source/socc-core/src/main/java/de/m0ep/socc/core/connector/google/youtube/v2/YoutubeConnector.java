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

package de.m0ep.socc.core.connector.google.youtube.v2;

import java.io.IOException;

import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.util.Builder;
import org.rdfs.sioc.UserAccount;
import org.rdfs.sioc.services.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;

import de.m0ep.socc.config.ConnectorCfg;
import de.m0ep.socc.core.ISoccContext;
import de.m0ep.socc.core.connector.AbstractConnector;
import de.m0ep.socc.core.connector.IServiceClientManager;
import de.m0ep.socc.core.exceptions.AuthenticationException;

public class YoutubeConnector extends AbstractConnector {
    private static final Logger LOG = LoggerFactory.getLogger(YoutubeConnector.class);

    public static final String PLAYLISTS_ID = "playlists";
    public static final String UPLOADS_ID = "uploads";

    private URI serviceEndpoint = Builder.createURI("http://www.youtube.com");

    private IServiceClientManager serviceClientManager;
    private IServiceStructureReader serviceStructureReader;
    private IPostReader postReader;
    private IPostWriter postWriter;

    private YoutubeConnector(String id, ISoccContext context, UserAccount defaultUserAccount,
            Service service) {
        super(id, context, defaultUserAccount, service);
    }

    public YoutubeConnector(ISoccContext context, ConnectorCfg config) {
        super(context, config);
    }

    @Override
    public IServiceClientManager getServiceClientManager() {
        return serviceClientManager;
    }

    @Override
    public IServiceStructureReader serviceStructureReader() {
        Preconditions.checkState(isInitialized(), "Connector was not initialized");

        if (null == serviceStructureReader) {
            serviceStructureReader = new YoutubeStructureReader(this);
        }

        return serviceStructureReader;
    }

    @Override
    public IPostReader postReader() {
        Preconditions.checkState(isInitialized(), "Connector was not initialized");
        if (null == postReader) {
            postReader = new YoutubePostReader(this);
        }

        return postReader;
    }

    @Override
    public IPostWriter postWriter() {
        Preconditions.checkState(isInitialized(), "Connector was not initialized");

        if (null == postWriter) {
            postWriter = new YoutubePostWriter(this);
        }

        return postWriter;
    }

    @Override
    public void initialize() throws AuthenticationException, IOException {
        getService().setServiceEndpoint(serviceEndpoint);

        try {
            serviceClientManager = new YoutubeClientManager(getService(), getDefaultUserAccount());
        } catch (Exception e) {
            Throwables.propagateIfInstanceOf(e, AuthenticationException.class);
            Throwables.propagateIfInstanceOf(e, IOException.class);
            throw Throwables.propagate(e);
        }

        setInitialized(true);

        LOG.info("Create Youtube connector.");
    }

    @Override
    public void shutdown() {
        serviceClientManager.clear();
    }

}
