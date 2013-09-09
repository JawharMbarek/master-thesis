
package de.m0ep.socc.core.connector;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.QueryResultTable;
import org.ontoware.rdf2go.model.QueryRow;
import org.ontoware.rdf2go.model.node.Node;
import org.ontoware.rdf2go.util.SparqlUtil;
import org.rdfs.sioc.SiocVocabulary;

import com.google.api.client.util.Lists;
import com.google.common.base.Preconditions;

import de.m0ep.socc.config.ConnectorConfig;
import de.m0ep.socc.config.SoccConfigVocabulary;
import de.m0ep.socc.core.ISoccContext;
import de.m0ep.socc.core.exceptions.ConnectorException;
import de.m0ep.socc.core.exceptions.NotFoundException;

public final class ConnectorFactory {
    private static ConnectorFactory instance;

    public ConnectorFactory() {
    };

    public static ConnectorFactory getInstance() {
        if (null != instance) {
            instance = new ConnectorFactory();
        }

        return instance;
    }

    /**
     * Creates an new connector. The {@link ConnectorConfig} will be loaded from
     * the {@link Model} of the <code>context</code> with the
     * <code>connectorId</code>.
     * 
     * @param context
     *            {@link ISoccContext} to create the connector and load the
     *            configuration information.
     * @param connectorId
     *            ID of the new connector.
     * @return The new connector.
     * @throws ConnectorException
     *             Thrown if there is a Problem creating the {@link IConnector}
     *             from the stored {@link ConnectorConfig}.
     * @throws NotFoundException
     *             Thrown if there is a problem reading {@link ConnectorConfig}
     *             data from the <code>context</code>.
     * @throws IOException
     *             Thrown if multiple instances found for the
     *             <code>connectorId</code>.
     * @throws NullPointerException
     *             Thrown if <code>context</code> or <code>connectorId</code>
     *             are <code>null</code>.
     */
    public IConnector createConnector(
            final ISoccContext context,
            final String connectorId)
            throws ConnectorException,
            NotFoundException,
            IOException {
        Preconditions.checkNotNull(context,
                "Required parameter context must be specified.");
        Preconditions.checkNotNull(connectorId,
                "Required parameter connectorId must be specified.");

        ConnectorConfig cfg = readConnectorConfig(
                context.getModel(),
                connectorId);

        return createConnector(context, cfg);
    }

    /**
     * Creates a new {@link IConnector} with a {@link ISoccContext} from a
     * {@link ConnectorConfig}.
     * 
     * @param context
     * @param config
     * @return
     * @throws ConnectorException
     *             Throw if there is a problem creating the connector from the
     *             {@link ConnectorConfig}.
     * @throws NullPointerException
     *             Thrown if the parameter <code>context</code> or
     *             <code>config</code> are <code>null</code>.
     * @throws IllegalArgumentException
     *             Thrown if the parameter <code>config</code> has no
     *             <b>connectorClassName<b> property.
     */
    public IConnector createConnector(
            final ISoccContext context,
            final ConnectorConfig config)
            throws ConnectorException {
        Preconditions.checkNotNull(context,
                "Required parameter context must be specified.");
        Preconditions.checkNotNull(config,
                "Required parameter config must be specified.");
        Preconditions.checkArgument(config.hasConnectorClassName(),
                "The parameter config has no connectorClassName.");

        String connectorClassName = config.getConnectorClassName();
        Class<?> connectorClass = null;
        try {
            connectorClass = Class.forName(connectorClassName);
        } catch (ClassNotFoundException e) {
            throw new ConnectorException(
                    "Failed to create connector from className: '"
                            + connectorClassName
                            + "'");
        }

        Constructor<?> constructor = null;
        try {
            constructor = connectorClass.getConstructor(ISoccContext.class,
                    ConnectorConfig.class);
        } catch (NoSuchMethodException e) {
            throw new ConnectorException(
                    "The connector "
                            + connectorClass.getSimpleName()
                            + " has no required cunstructor 'public "
                            + connectorClass.getSimpleName()
                            + "("
                            + ISoccContext.class.getSimpleName()
                            + ", "
                            + ConnectorConfig.class.getSimpleName()
                            + ")'.", e);
        } catch (SecurityException e) {
            throw new ConnectorException(
                    "Can't access the constructor of "
                            + connectorClass.getSimpleName()
                            + " because of some security issues.", e);
        }

        try {
            return (IConnector) constructor.newInstance(context, config);
        } catch (InstantiationException
                | IllegalAccessException
                | IllegalArgumentException
                | InvocationTargetException e) {
            throw new ConnectorException(
                    "Failed to invoke cunstructor 'public "
                            + connectorClass.getSimpleName()
                            + "("
                            + ISoccContext.class.getSimpleName()
                            + ", "
                            + ConnectorConfig.class.getSimpleName()
                            + ")'.", e);
        }
    }

    /**
     * Reads a {@link ConnectorConfig} with the <code>connectorId</code> from a
     * <code>model</code>.
     * 
     * @param model
     *            The model where to search for the {@link ConnectorConfig}.
     * @param connectorId
     *            The id of the wanted {@link ConnectorConfig}.
     * @return A {@link ConnectorConfig} with the wanted
     *         <code>connectorId</code>.
     * @throws NotFoundException
     *             Thrown if no {@link ConnectorConfig} was found with this
     *             <code>connectorId</code>.
     * @throws IOException
     *             Thrown if multiple instances found for the
     *             <code>connectorId</code>.
     * @throws NullPointerException
     *             Thrown if <code>model</code> or <code>connectorId</code> are
     *             <code>null</code>.
     * @throws IllegalStateException
     *             Thrown if the <code>model</code> was not opened.
     */
    private ConnectorConfig readConnectorConfig(
            final Model model,
            final String connectorId)
            throws NotFoundException,
            IOException {
        Preconditions.checkNotNull(model,
                "Required parameter model must be specified.");
        Preconditions.checkArgument(model.isOpen(),
                "The parameter model is not open.");
        Preconditions.checkNotNull(connectorId,
                "Required parameter connectorId must be specified.");

        String query = "PREFIX sioc:"
                + SiocVocabulary.NS_SIOC.toSPARQL()
                + "PREFIX ccfg:"
                + SoccConfigVocabulary.NS_SoccConfigVocabulary.toSPARQL()
                + "SELECT ?cfg"
                + "WHERE {"
                + "?cfg a ccfg:ConnectorConfig ."
                + "?cfg ccfg:id %s ."
                + "}";

        QueryResultTable resultTable = model.sparqlSelect(
                SparqlUtil.formatQuery(
                        query,
                        connectorId));

        List<ConnectorConfig> result = Lists.newArrayList();
        for (QueryRow resultRow : resultTable) {
            Node node = resultRow.getValue("cfg");
            result.add(ConnectorConfig.getInstance(model, node.asResource()));
        }

        if (0 == result.size()) {
            throw new NotFoundException(
                    "No ConnectorConfig found for connector with id "
                            + connectorId
                            + ".");
        } else if (1 == result.size()) {
            return result.get(0);
        } else {
            throw new IOException(
                    "Found multiple ConnectorConfig instances with id='"
                            + connectorId
                            + "'");
        }
    }
}
