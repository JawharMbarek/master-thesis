
package de.m0ep.socc.core;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.node.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.util.Maps;
import com.google.common.base.Preconditions;

import de.m0ep.socc.config.ConnectorConfig;
import de.m0ep.socc.core.acl.IAccessControl;
import de.m0ep.socc.core.connector.IConnector;
import de.m0ep.socc.core.exceptions.AuthenticationException;

public class SoccContext implements ISoccContext {
    private static final Logger LOG = LoggerFactory
            .getLogger(SoccContext.class);

    private Model model;
    private IAccessControl accessControl;
    private Map<String, IConnector> connectorMap;

    public SoccContext(final Model model) throws AuthenticationException,
            IOException {
        this.connectorMap = Maps.newHashMap();
        this.model = Preconditions.checkNotNull(
                model,
                "Required parameter model must be specified.");
        Preconditions.checkArgument(
                model.isOpen(),
                "Required paramater model is not open");

        // TODO: finish initialization

        ClosableIterator<Resource> conCfgIter =
                ConnectorConfig.getAllInstances(getModel());
        while (conCfgIter.hasNext()) {
            Resource resource = (Resource) conCfgIter.next();
            ConnectorConfig connectorConfig = ConnectorConfig.getInstance(
                    getModel(),
                    resource);

            try {
                addConnector(connectorConfig);
            } catch (ClassNotFoundException | NoSuchMethodException
                    | InstantiationException e) {
                LOG.warn("Failed to create Connector {}",
                        connectorConfig.getId());
            }
        }
    }

    /**
     * Create an instance of an {@link IConnector} from a
     * {@link ConnectorConfig}.
     * 
     * @param connectorConfig
     * @throws ClassNotFoundException
     *             Thrown if the connectorClass of the
     *             </code>connectorConfig</code> can not been found.
     * @throws NoSuchMethodException
     *             Thrown if the connectorClass has no valid constructor
     *             (ISoccContext, ConnectorConfig).
     * @throws InstantiationException
     *             Thrown if creation of a new instance has failed.
     */
    public IConnector createConnector(ConnectorConfig connectorConfig)
            throws ClassNotFoundException, NoSuchMethodException,
            InstantiationException {
        if (!connectorConfig.hasConnectorClass()) {
            throw new IllegalArgumentException(
                    "The parameter connectorConfig has no connectorClass");
        }

        String connectorClassName = connectorConfig.getConnectorClass();
        Class<?> connectorClass = Class.forName(connectorClassName);

        Constructor<?> constructor = null;
        try {
            constructor = connectorClass.getConstructor(
                    ISoccContext.class,
                    ConnectorConfig.class);
        } catch (NoSuchMethodException | SecurityException e) {
            throw new NoSuchMethodException(
                    "The class "
                            + connectorClass.getName()
                            + " has no required constructor "
                            + connectorClass.getSimpleName()
                            + "(ISoccContext, ConnectorConfig)");
        }

        try {
            return (IConnector) constructor.newInstance(this, connectorConfig);
        } catch (InstantiationException
                | IllegalAccessException
                | IllegalArgumentException
                | InvocationTargetException e) {
            throw new InstantiationException("Failed to create an instance of "
                    + connectorClass.getName());
        }
    }

    public void addConnector(ConnectorConfig connectorConfig)
            throws ClassNotFoundException, NoSuchMethodException,
            InstantiationException, AuthenticationException, IOException {
        IConnector connector = createConnector(connectorConfig);
        connector.initialize();
        addConnector(connector);
    }

    public void addConnector(IConnector connector) {
        LOG.debug("Add new connector {} to context.", connector.getId());
        connectorMap.put(connector.getId(), connector);
    }

    @Override
    public Model getModel() {
        return model;
    }

    @Override
    public IAccessControl getAccessControl() {
        return accessControl;
    }

    @Override
    public IConnector getConnector(String id) {
        return connectorMap.get(id);
    }
}
