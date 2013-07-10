
package de.m0ep.socc.core.connector.canvaslms;

import java.io.IOException;

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.ontoware.rdf2go.model.node.URI;
import org.rdfs.sioc.UserAccount;
import org.rdfs.sioc.services.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import de.m0ep.canvas.CanvasLmsClient;
import de.m0ep.sioc.service.auth.AccessToken;
import de.m0ep.sioc.service.auth.Authentication;
import de.m0ep.sioc.service.auth.Credential;
import de.m0ep.socc.config.ConnectorCfg;
import de.m0ep.socc.core.ISoccContext;
import de.m0ep.socc.core.connector.AbstractConnector;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.service.BasicServiceClientManager;
import de.m0ep.socc.core.service.IServiceClientManager;
import de.m0ep.socc.core.utils.RDF2GoUtils;

public class CanvasLmsConnector extends AbstractConnector {
    private static final Logger LOG = LoggerFactory.getLogger(CanvasLmsConnector.class);

    private URI serviceEndpointUri;
    private BasicServiceClientManager<CanvasLmsClient> clientManager;

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
    public IServiceClientManager<CanvasLmsClient> getServiceClientManager() {
        return clientManager;
    }

    @Override
    public IServiceStructureReader getServiceStructureReader() {
        return serviceStructureReader;
    }

    @Override
    public IPostReader getPostReader() {
        return postReader;
    }

    @Override
    public IPostWriter getPostWriter() {
        return postWriter;
    }

    @Override
    public void initialize() throws AuthenticationException, IOException {
        Preconditions.checkArgument(
                service.hasServiceEndpoint(),
                "Provided parameter service contains no ServiceEndpoint.");
        serviceEndpointUri = getService().getServiceEndpoint().asURI();

        LOG.info("Create CanvasLMS connector with endpoint at {}.", serviceEndpointUri);

        de.m0ep.sioc.service.auth.UserAccount authUserAccount =
                de.m0ep.sioc.service.auth.UserAccount.getInstance(
                        getDefaultUserAccount().getModel(),
                        getDefaultUserAccount().getResource());

        Preconditions.checkArgument(
                authUserAccount.hasAuthentication(),
                "The userAccount has no authentication data.");
        Authentication authentication = authUserAccount.getAuthentication();

        Preconditions.checkArgument(
                authentication.hasCredential(),
                "The authentication has no credentials");
        ClosableIterator<Credential> credentialIter = authentication.getAllCredential();

        CanvasLmsClient defaultClient = null;
        while (credentialIter.hasNext()) {
            Credential credential = (Credential) credentialIter.next();
            URI type = RDF2GoUtils.getType(credential.getModel(), credential.getResource());

            if (AccessToken.RDFS_CLASS.equals(type) && credential.hasValue()) {
                defaultClient = new CanvasLmsClient(
                        serviceEndpointUri.toString(),
                        credential.getValue());
            }
        }

        if (null == defaultClient) {
            throw new IllegalArgumentException("No access token found in userAccount");
        }

        clientManager = new BasicServiceClientManager<CanvasLmsClient>(defaultClient);

        this.serviceStructureReader = new CanvasLmsServiceStructureReader(this);
        this.postReader = new CanvasLmsPostReader(this);
        this.postWriter = new CanvasLmsPostWriter(this);
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }
}
