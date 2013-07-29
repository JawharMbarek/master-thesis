
package de.m0ep.socc.core.connector.google.plus;

import java.io.IOException;

import org.rdfs.sioc.UserAccount;
import org.rdfs.sioc.services.Service;

import de.m0ep.socc.config.ConnectorCfg;
import de.m0ep.socc.core.ISoccContext;
import de.m0ep.socc.core.connector.AbstractConnector;
import de.m0ep.socc.core.connector.IServiceClientManager;
import de.m0ep.socc.core.exceptions.AuthenticationException;

public class GooglePlusConnector extends AbstractConnector {

    public GooglePlusConnector(ISoccContext context, ConnectorCfg config) {
        super(context, config);
    }

    public GooglePlusConnector(String id, ISoccContext context, UserAccount defaultUserAccount,
            Service service) {
        super(id, context, defaultUserAccount, service);
    }

    @Override
    public IServiceClientManager getServiceClientManager() {
        // TODO Auto-generated method stub
        return null;
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
