
package de.m0ep.socc.core.connector.moodle;

import java.io.IOException;

import org.ontoware.rdf2go.model.node.URI;
import org.rdfs.sioc.UserAccount;
import org.rdfs.sioc.services.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import de.m0ep.socc.config.ConnectorCfg;
import de.m0ep.socc.core.ISoccContext;
import de.m0ep.socc.core.connector.AbstractConnector;
import de.m0ep.socc.core.connector.IServiceClientManager;
import de.m0ep.socc.core.exceptions.AuthenticationException;

public class Moodle2Connector extends AbstractConnector {
    private static final Logger LOG = LoggerFactory
            .getLogger(Moodle2Connector.class);

    private URI serviceEndpointUri;
    private IServiceClientManager<Moodle2ClientWrapper> serviceClientManager;

    private IStructureReader serviceStructureReader;
    private IPostReader postReader;
    private IPostWriter postWriter;

    public Moodle2Connector(ISoccContext context, ConnectorCfg config) {
        super(context, config);
    }

    public Moodle2Connector(String id, ISoccContext context,
            UserAccount defaultUserAccount,
            Service service) {
        super(id, context, defaultUserAccount, service);
    }

    @SuppressWarnings("unchecked")
    @Override
    public IServiceClientManager<Moodle2ClientWrapper> getServiceClientManager() {
        return serviceClientManager;
    }

    @Override
    public IStructureReader getStructureReader() {
        Preconditions.checkState(isInitialized(),
                "Connector was not initialized");

        if (null == serviceStructureReader) {
            serviceStructureReader = new Moodle2ServiceStructureReader(this);
        }

        return serviceStructureReader;
    }

    @Override
    public IPostReader getPostReader() {
        Preconditions.checkState(isInitialized(),
                "Connector was not initialized");

        if (null == postReader) {
            postReader = new Moodle2PostReader(this);
        }

        return postReader;
    }

    @Override
    public IPostWriter getPostWriter() {
        Preconditions.checkState(isInitialized(),
                "Connector was not initialized");

        if (null == postWriter) {
            postWriter = new Moodle2PostWriter(this);
        }

        return postWriter;
    }

    @Override
    public void initialize() throws AuthenticationException, IOException {
        Preconditions.checkArgument(
                service.hasServiceEndpoint(),
                "The service contains no required serviceEndpoint.");
        serviceEndpointUri = getService().getServiceEndpoint().asURI();

        LOG.info("Create Moodle connector with endpoint at {}.",
                serviceEndpointUri);

        try {
            serviceClientManager = new Moodle2ClientManager(getService(),
                    getDefaultUserAccount());
        } catch (Exception e) {
            throw new AuthenticationException(
                    "Failed to create and login default client.", e);
        }

        serviceStructureReader = new Moodle2ServiceStructureReader(this);
        setInitialized(true);
    }

    @Override
    public void shutdown() {
        for (Object obj : serviceClientManager.getAll()) {
            Moodle2ClientWrapper client = (Moodle2ClientWrapper) obj;
            client.getBindingStub().logout(
                    client.getAuthClient(),
                    client.getSessionKey());
        }
        serviceClientManager.clear();

        Moodle2ClientWrapper client = (Moodle2ClientWrapper) serviceClientManager
                .getDefaultClient();
        client.getBindingStub().logout(
                client.getAuthClient(),
                client.getSessionKey());

        setInitialized(false);
    }
}
