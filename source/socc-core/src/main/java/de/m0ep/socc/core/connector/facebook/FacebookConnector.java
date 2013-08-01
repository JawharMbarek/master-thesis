
package de.m0ep.socc.core.connector.facebook;

import java.io.IOException;

import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.util.Builder;
import org.rdfs.sioc.UserAccount;
import org.rdfs.sioc.services.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.m0ep.socc.config.ConnectorCfg;
import de.m0ep.socc.core.ISoccContext;
import de.m0ep.socc.core.connector.AbstractConnector;
import de.m0ep.socc.core.connector.IServiceClientManager;
import de.m0ep.socc.core.exceptions.AuthenticationException;

public class FacebookConnector extends AbstractConnector {
    private static final Logger LOG = LoggerFactory.getLogger(FacebookConnector.class);

    public static final URI SERVICE_ENDPOINT = Builder.createURI("https://www.facebook.com");

    private IServiceClientManager clientManager;

    private FacebookConnector(String id, ISoccContext context, UserAccount defaultUserAccount,
            Service service) {
        super(id, context, defaultUserAccount, service);
    }

    private FacebookConnector(ISoccContext context, ConnectorCfg config) {
        super(context, config);
    }

    @Override
    public IServiceClientManager getServiceClientManager() {
        return clientManager;
    }

    @Override
    public IServiceStructureReader serviceStructureReader() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IPostReader postReader() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IPostWriter postWriter() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void initialize() throws AuthenticationException, IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void shutdown() {
        // TODO Auto-generated method stub

    }

}
