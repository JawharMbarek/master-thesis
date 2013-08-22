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

package de.m0ep.socc.core.connector.canvaslms;

import java.io.IOException;

import org.rdfs.sioc.UserAccount;
import org.rdfs.sioc.services.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;

import de.m0ep.canvas.CanvasLmsClient;
import de.m0ep.socc.config.ConnectorConfig;
import de.m0ep.socc.core.ISoccContext;
import de.m0ep.socc.core.connector.DefaultConnector;
import de.m0ep.socc.core.connector.IClientManager;
import de.m0ep.socc.core.exceptions.AuthenticationException;

/**
 * @author Florian Müller
 */
public class CanvasLmsConnector extends DefaultConnector {
    private static final Logger LOG = LoggerFactory
            .getLogger(CanvasLmsConnector.class);

    private IClientManager<CanvasLmsClient> clientManager;
    private IStructureReader serviceStructureReader;
    private IPostReader postReader;
    private IPostWriter postWriter;

    /**
     * Constructs a new {@link CanvasLmsConnector} from a {@link ConnectorConfig}
     * and a given {@link ISoccContext}.
     * 
     * @param context
     * @param config
     */
    public CanvasLmsConnector(final ISoccContext context,
            final ConnectorConfig config) {
        super(context, config);
    }

    public CanvasLmsConnector(final String id, final ISoccContext context,
            final UserAccount defaultUserAccount,
            Service service) {
        super(id, context, defaultUserAccount, service);
    }

    @SuppressWarnings("unchecked")
    @Override
    public IClientManager<CanvasLmsClient> getServiceClientManager() {
        return clientManager;
    }

    @Override
    public IStructureReader getStructureReader() {
        Preconditions.checkState(isInitialized(),
                "Connector was not initialized");

        if (null == serviceStructureReader) {
            serviceStructureReader = new CanvasLmsStructureReader(this);
        }

        return serviceStructureReader;
    }

    @Override
    public IPostReader getPostReader() {
        Preconditions.checkState(isInitialized(),
                "Connector was not initialized");

        if (null == postReader) {
            postReader = new CanvasLmsPostReader(this);
        }

        return postReader;
    }

    @Override
    public IPostWriter getPostWriter() {
        Preconditions.checkState(isInitialized(),
                "Connector was not initialized");

        if (null == postWriter) {
            postWriter = new CanvasLmsPostWriter(this);
        }

        return postWriter;
    }

    @Override
    public void initialize() throws AuthenticationException, IOException {
        Preconditions.checkArgument(
                service.hasServiceEndpoint(),
                "Provided parameter service contains no ServiceEndpoint.");

        try {
            clientManager = new CanvasLmsClientManager(getService(),
                    getDefaultUserAccount());
        } catch (Exception e) {
            Throwables.propagateIfInstanceOf(e, AuthenticationException.class);
            Throwables.propagateIfInstanceOf(e, IOException.class);
            throw Throwables.propagate(e);
        }
        setInitialized(true);

        LOG.info("Create CanvasLMS connector with endpoint at {}.",
                getService().getServiceEndpoint());
    }

    @Override
    public void shutdown() {
        serviceStructureReader = null;
        postReader = null;
        postWriter = null;
        clientManager.clear();
        clientManager = null;

        setInitialized(false);
    }
}
