
package de.m0ep.socc.core.connector.moodle;

import java.io.IOException;

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.ontoware.rdf2go.model.node.URI;
import org.rdfs.sioc.UserAccount;
import org.rdfs.sioc.services.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import de.m0ep.sioc.service.auth.Authentication;
import de.m0ep.sioc.service.auth.Credential;
import de.m0ep.sioc.service.auth.Password;
import de.m0ep.sioc.service.auth.Username;
import de.m0ep.socc.config.ConnectorCfg;
import de.m0ep.socc.core.ISoccContext;
import de.m0ep.socc.core.connector.AbstractConnector;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.service.BasicServiceClientManager;
import de.m0ep.socc.core.service.IServiceClientManager;
import de.m0ep.socc.core.utils.RdfUtils;

public class Moodle2Connector extends AbstractConnector {
    private static final Logger LOG = LoggerFactory.getLogger(Moodle2Connector.class);

    private URI serviceEndpointUri;
    private BasicServiceClientManager<MoodleClientWrapper> serviceClientManager;

    private IServiceStructureReader serviceStructureReader;
    private IPostReader postReader;
    private IPostWriter postWriter;

    public Moodle2Connector(ISoccContext context, ConnectorCfg config) {
        super(context, config);
    }

    public Moodle2Connector(String id, ISoccContext context, UserAccount defaultUserAccount,
            Service service) {
        super(id, context, defaultUserAccount, service);
    }

    @Override
    public IServiceClientManager<?> getServiceClientManager() {
        return serviceClientManager;
    }

    @Override
    public IServiceStructureReader serviceStructureReader() {
        return serviceStructureReader;
    }

    @Override
    public IPostReader postReader() {
        return postReader;
    }

    @Override
    public IPostWriter postWriter() {
        return postWriter;
    }

    @Override
    public void initialize() throws AuthenticationException, IOException {
        Preconditions.checkArgument(
                service.hasServiceEndpoint(),
                "The service contains no required serviceEndpoint.");
        serviceEndpointUri = getService().getServiceEndpoint().asURI();

        LOG.info("Create Moodle connector with endpoint at {}.", serviceEndpointUri);

        de.m0ep.sioc.service.auth.UserAccount authUserAccount =
                de.m0ep.sioc.service.auth.UserAccount.getInstance(
                        getDefaultUserAccount().getModel(),
                        getDefaultUserAccount().getResource());

        Preconditions.checkArgument(
                authUserAccount.hasAuthentication(),
                "The defaultUserAccount has no required authentication data.");
        Authentication authentication = authUserAccount.getAuthentication();

        Preconditions.checkArgument(
                authentication.hasCredential(),
                "The defaultUserAccount authentication has no required credentials");
        ClosableIterator<Credential> credentialIter = authentication.getAllCredential();

        Username username = null;
        Password password = null;
        while (credentialIter.hasNext()) {
            Credential credential = (Credential) credentialIter.next();

            if (RdfUtils.isType(credential.getModel(), credential.getResource(),
                    Username.RDFS_CLASS) && credential.hasValue()) {
                username = Username.getInstance(credential.getModel(), credential.asResource());
            } else if (RdfUtils.isType(credential.getModel(), credential.getResource(),
                    Password.RDFS_CLASS) && credential.hasValue()) {
                password = Password.getInstance(credential.getModel(), credential.asResource());
            }
        }

        Preconditions.checkArgument(null != username,
                "The defaultUserAccount authentication contains no required username");
        Preconditions.checkArgument(null != password,
                "The defaultUserAccount authentication contains no required password");

        MoodleClientWrapper defaultClient = new MoodleClientWrapper(
                serviceEndpointUri,
                username.getValue(),
                password.getValue());

        serviceClientManager = new BasicServiceClientManager<MoodleClientWrapper>(defaultClient);
    }

    @Override
    public void shutdown() {

    }
}
