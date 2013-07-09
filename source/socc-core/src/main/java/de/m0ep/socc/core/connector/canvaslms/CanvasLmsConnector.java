
package de.m0ep.socc.core.connector.canvaslms;

import java.io.IOException;

import org.ontoware.rdf2go.model.node.URI;
import org.rdfs.sioc.Site;
import org.rdfs.sioc.UserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import de.m0ep.sioc.service.auth.Service;
import de.m0ep.socc.core.ISoccContext;
import de.m0ep.socc.core.connector.AbstractConnector;
import de.m0ep.socc.core.connector.IPostReader;
import de.m0ep.socc.core.connector.IPostWriter;
import de.m0ep.socc.core.connector.IServiceStructureReader;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.service.BasicServiceClientManager;
import de.m0ep.socc.core.service.IServiceClientManager;

public class CanvasLmsConnector extends AbstractConnector {
    private static final Logger LOG = LoggerFactory.getLogger(CanvasLmsConnector.class);

    private URI serviceEndpointUri;
    private BasicServiceClientManager<CanvasLmsConnectorClient> clientManager;

    public CanvasLmsConnector(String id, ISoccContext context, UserAccount defaultUserAccount,
            Service service) {
        super(id, context, defaultUserAccount, service);

        Preconditions.checkArgument(
                service.hasServiceEndpoint(),
                "Proviced service contains no required service endpoint.");
        serviceEndpointUri = service.getServiceEndpoint().asURI();
        LOG.info("Create CanvasLMS connector with endpoint at {}.", serviceEndpointUri);

        CanvasLmsConnectorClient client = new CanvasLmsConnectorClient(
                serviceEndpointUri,
                defaultUserAccount);
        clientManager = new BasicServiceClientManager<CanvasLmsConnectorClient>(client);
    }

    @Override
    public Site getSite() {
        if (!Site.hasInstance(context.getModel(), serviceEndpointUri)) {
            Site result = new Site(context.getModel(), serviceEndpointUri, true);
            result.setName("Canvas LMS (" + serviceEndpointUri.toString() + ")");
        }

        return Site.getInstance(context.getModel(), serviceEndpointUri);
    }

    @Override
    public IServiceClientManager<CanvasLmsConnectorClient> getServiceClientManager() {
        return clientManager;
    }

    @Override
    public IServiceStructureReader getServiceStructureReader() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IPostReader getPostReader() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IPostWriter getPostWriter() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void initialize() throws AuthenticationException, IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

    /* package */URI getServiceEndpointUri() {
        return serviceEndpointUri;
    }
}
