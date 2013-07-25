
package de.m0ep.socc.core.connector.canvaslms;

import java.io.IOException;

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

public class CanvasLmsConnector extends AbstractConnector {
    private static final Logger LOG = LoggerFactory.getLogger(CanvasLmsConnector.class);

    private IServiceClientManager clientManager;

    private IServiceStructureReader serviceStructureReader;
    private IPostReader postReader;
    private IPostWriter postWriter;

    public CanvasLmsConnector(ISoccContext context, ConnectorCfg config) {
        super(context, config);
    }

    public CanvasLmsConnector(String id, ISoccContext context, UserAccount defaultUserAccount,
            Service service) {
        super(id, context, defaultUserAccount, service);
    }

    @Override
    public IServiceClientManager getServiceClientManager() {
        return clientManager;
    }

    @Override
    public IServiceStructureReader serviceStructureReader() {
        Preconditions.checkState(isInitialized(), "Connector was not initialized");

        if (null == serviceStructureReader) {
            serviceStructureReader = new CanvasLmsServiceStructureReader(this);
        }

        return serviceStructureReader;
    }

    @Override
    public IPostReader postReader() {
        Preconditions.checkState(isInitialized(), "Connector was not initialized");

        if (null == postReader) {
            postReader = new CanvasLmsPostReader(this);
        }

        return postReader;
    }

    @Override
    public IPostWriter postWriter() {
        Preconditions.checkState(isInitialized(), "Connector was not initialized");

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
            clientManager = new CanvasLmsClientManager(getService(), getDefaultUserAccount());
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
