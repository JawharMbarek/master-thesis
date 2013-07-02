package de.m0ep.socc.shop;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.RoutesDefinition;
import org.apache.commons.io.FileUtils;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.util.RDFTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xmlns.foaf.FOAF;

import de.m0ep.camel.socc.SOCCComponent;
import de.m0ep.socc.SOCCContext;
import de.m0ep.socc.config.SOCCConfiguration;
import de.m0ep.socc.shop.ui.ApplicationWindow;
import de.m0ep.socc.shop.utils.ExportUtils;

public class SOCCShopApplication {
    private static final String DATA_DIR = "./data/";

    private static final String FOAF_DATA_FILENAME = "foaf.xml";

    private static final String SIOC_DATA_FILENAME = "sioc.xml";

    private static final String CAMEL_ROUTES_FILENAME = "routes.xml";

    private static final String CONNECTOR_CFG_FILENAME = "connectors.json";

    private static final Logger LOG = LoggerFactory
	    .getLogger(SOCCShopApplication.class);

    private ApplicationWindow window;

    private SOCCContext socc;

    private Model siocModel;
    private Model foafModel;
    private DefaultCamelContext camelContext;

    public static void main(String[] args) {
	SOCCShopApplication app = new SOCCShopApplication();
	app.start();
    }

    public SOCCShopApplication() {
	SOCCConfiguration configuration = null;

	File dataDir = new File(DATA_DIR);
	if (!dataDir.exists()) {
	    dataDir.mkdirs();
	}

	File settingsFile = new File(dataDir, CONNECTOR_CFG_FILENAME);
	if (null != settingsFile && settingsFile.exists()) {
	    try {
		configuration = SOCCConfiguration.load(settingsFile);
	    } catch (Throwable t) {
		LOG.error("Failed to load settings from file.", t);
		shutdown();
	    }
	}

	// try to load saved sioc model
	File siocFile = new File(dataDir, SIOC_DATA_FILENAME);
	if (siocFile.exists()) {
	    try {
		String siocXML = FileUtils.readFileToString(siocFile, "UTF-8");
		this.siocModel = RDFTool.stringToModel(siocXML);
	    } catch (IOException e) {
		LOG.error("Failed to load " + siocFile.getAbsolutePath(), e);
	    }
	}

	if (null == this.siocModel) {
	    this.siocModel = SOCCContext.createDefaultMemoryModel();
	}

	this.socc = new SOCCContext(siocModel, configuration);

	// try to load saved foaf model
	File foafFile = new File(dataDir, FOAF_DATA_FILENAME);
	if (foafFile.exists()) {

	    try {
		String foafXML = FileUtils.readFileToString(foafFile, "UTF-8");
		this.foafModel = RDFTool.stringToModel(foafXML);
	    } catch (IOException e) {
		LOG.error("Failed to load " + foafFile.getAbsolutePath(), e);
	    }
	}

	if (null == this.foafModel) {
	    this.foafModel = FOAF.createDefaultMemoryModel();
	}

	this.camelContext = new DefaultCamelContext();
	this.camelContext.addComponent("socc", new SOCCComponent(this.socc));

	File camelFile = new File(dataDir, CAMEL_ROUTES_FILENAME);
	if (camelFile.exists()) {
	    try {
		RoutesDefinition routes = this.camelContext
			.loadRoutesDefinition(new FileInputStream(camelFile));
		this.camelContext.addRouteDefinitions(routes.getRoutes());
	    } catch (FileNotFoundException e) {
		LOG.warn("No " + camelFile.getAbsolutePath() + " file found");
	    } catch (Exception e) {
		LOG.error("unknown error", e);
	    }
	}

	try {
	    this.camelContext.start();
	} catch (Exception e) {
	    LOG.error("Failed to start Apache Camel", e);
	    shutdown();
	}
    }

    public void start() {
	LOG.info("Starting SOCC-Shop...");
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {
		    window = new ApplicationWindow(
			    SOCCShopApplication.this);
		    window.showWindow();

		} catch (Exception e) {
		    LOG.error("Failed to start application window", e);
		    shutdown();
		}
	    }
	});
    }

    /* Runtime */

    public void save() {
	LOG.info("Saving application settings and data...");

	File dataDir = new File(DATA_DIR);
	if (!dataDir.exists()) {
	    dataDir.mkdirs();
	}

	if (null != socc) {
	    File settingsFile = new File(dataDir, CONNECTOR_CFG_FILENAME);

	    try {
		socc.getConfiguration().save(settingsFile);

		LOG.debug(
			"{} successfuly written.",
			settingsFile.getAbsolutePath());
	    } catch (IOException e) {
		LOG.error("Failed to write " + settingsFile.getAbsolutePath(),
			e);
	    }
	}

	if (null != camelContext) {
	    File routesFile = new File(dataDir, CAMEL_ROUTES_FILENAME);
	    String routesXML = null;

	    try {
		routesXML = ExportUtils.getRoutesXML(camelContext);
	    } catch (Exception e) {
		LOG.error(
			"Failed to convert camel-routes into xml",
			e);
	    }

	    if (null != routesXML) {
		try {
		    FileUtils.writeStringToFile(
			    routesFile,
			    routesXML,
			    "UTF-8");

		    LOG.debug(
			    "{} successfuly written.",
			    routesFile.getAbsolutePath());
		} catch (IOException e) {
		    LOG.error(
			    "Failed to write " +
				    routesFile.getAbsolutePath(),
			    e);
		}
	    }
	}

	if (null != siocModel) {
	    File siocFile = new File(dataDir, SIOC_DATA_FILENAME);
	    String siocXML = ExportUtils.getModelXML(siocModel);

	    if (null != siocXML) {
		try {
		    FileUtils.writeStringToFile(
			    siocFile,
			    siocXML,
			    "UTF-8");

		    LOG.debug(
			    "{} successfuly written.",
			    siocFile.getAbsolutePath());
		} catch (IOException e) {
		    LOG.error("Failed to write " + siocFile.getAbsolutePath(),
			    e);
		}
	    }
	}

	if (null != foafModel) {
	    File foafFile = new File(dataDir, FOAF_DATA_FILENAME);
	    String foafXML = ExportUtils.getModelXML(foafModel);

	    if (null != foafXML) {
		try {
		    FileUtils.writeStringToFile(
			    foafFile,
			    foafXML,
			    "UTF-8");
		    LOG.debug(
			    "{} successfuly written.",
			    foafFile.getAbsolutePath());
		} catch (IOException e) {
		    LOG.error("Failed to write " + foafFile.getAbsolutePath(),
			    e);
		}
	    }
	}
    }

    public void shutdown() {
	LOG.info("Stopping SOCC-Shop...");
	save();

	if (null != camelContext) {
	    try {
		camelContext.stop();
	    } catch (Exception e) {
		LOG.error("Failed to stop Apache Camel", e);
	    }
	}

	if (null != siocModel) {
	    siocModel.close();
	}

	if (null != foafModel) {
	    foafModel.close();
	}

	System.exit(0);
    }

    /* Getter & Setter */

    public SOCCContext getSocc() {
	return this.socc;
    }

    public Model getSiocModel() {
	return this.siocModel;
    }

    public Model getFoafModel() {
	return foafModel;
    }

    public DefaultCamelContext getCamelContext() {
	return camelContext;
    }
}
