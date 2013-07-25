
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

public class YoutubeV2Connector extends AbstractConnector {
    private static final Logger LOG = LoggerFactory.getLogger(YoutubeV2Connector.class);

    public static final String PLAYLISTS_ID = "playlists";
    public static final String UPLOADS_ID = "uploads";

    private URI serviceEndpoint = Builder.createURI("http://www.youtube.com");

    private IServiceClientManager serviceClientManager;
    private IServiceStructureReader serviceStructureReader;
    private IPostReader postReader;
    private IPostWriter postWriter;

    private YoutubeV2Connector(String id, ISoccContext context, UserAccount defaultUserAccount,
            Service service) {
        super(id, context, defaultUserAccount, service);
    }

    public YoutubeV2Connector(ISoccContext context, ConnectorCfg config) {
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
            serviceStructureReader = new YoutubeV2StructureReader(this);
        }

        return serviceStructureReader;
    }

    @Override
    public IPostReader postReader() {
        Preconditions.checkState(isInitialized(), "Connector was not initialized");
        return null;
    }

    @Override
    public IPostWriter postWriter() {
        Preconditions.checkState(isInitialized(), "Connector was not initialized");
        return null;
    }

    @Override
    public void initialize() throws AuthenticationException, IOException {
        getService().setServiceEndpoint(serviceEndpoint);

        try {
            serviceClientManager = new YoutubeV2ClientManager(getService(), getDefaultUserAccount());
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
