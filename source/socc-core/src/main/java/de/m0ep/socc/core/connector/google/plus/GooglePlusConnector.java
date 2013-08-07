
package de.m0ep.socc.core.connector.google.plus;

import java.io.IOException;

import org.rdfs.sioc.UserAccount;
import org.rdfs.sioc.services.Service;

import de.m0ep.socc.config.ConnectorConfig;
import de.m0ep.socc.core.ISoccContext;
import de.m0ep.socc.core.connector.AbstractConnector;
import de.m0ep.socc.core.connector.IServiceClientManager;
import de.m0ep.socc.core.exceptions.AuthenticationException;

public class GooglePlusConnector extends AbstractConnector {

    public GooglePlusConnector(ISoccContext context, ConnectorConfig config) {
        super(context, config);
    }

    public GooglePlusConnector(String id, ISoccContext context,
            UserAccount defaultUserAccount,
            Service service) {
        super(id, context, defaultUserAccount, service);
    }

    @Override
    public IServiceClientManager<Object> getServiceClientManager() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IStructureReader getStructureReader() {
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
    public void shutdown() {
        // TODO Auto-generated method stub

    }

}
