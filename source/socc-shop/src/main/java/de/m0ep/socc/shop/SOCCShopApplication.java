package de.m0ep.socc.shop;

import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.camel.impl.DefaultCamelContext;
import org.apache.commons.io.FileUtils;
import org.ontoware.rdf2go.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xmlns.foaf.FOAF;

import de.m0ep.socc.SOCC;
import de.m0ep.socc.config.SOCCConfiguration;
import de.m0ep.socc.shop.ui.ApplicationWindow;
import de.m0ep.socc.shop.utils.ExportUtils;

public class SOCCShopApplication {
    private static final String FOAF_DATA_FILENAME = "foaf.xml";

    private static final String SIOC_DATA_FILENAME = "sioc.xml";

    private static final String CAMEL_ROUTES_FILENAME = "routes.xml";

    private static final String CONNECTOR_CFG_FILENAME = "connectors.json";

    private static final Logger LOG = LoggerFactory
	    .getLogger(SOCCShopApplication.class);

    private ApplicationWindow window;

    private SOCC socc;
    private SOCCConfiguration soccConfiguration;

    private Model siocModel;
    private Model foafModel;
    private DefaultCamelContext camelContext;

    public static void main(String[] args) {
	SOCCShopApplication app = new SOCCShopApplication();
	app.start();
    }

    public SOCCShopApplication() {
	SOCCConfiguration configuration = null;
	File settingsFile = getSettingsFile();

	if (null != settingsFile && settingsFile.exists()) {
	    try {
		configuration = SOCCConfiguration.load(settingsFile);
	    } catch (Throwable t) {
		LOG.error("Failed to load settings from file.", t);
		shutdown();
	    }
	}

	this.siocModel = SOCC.createDefaultMemoryModel();
	this.foafModel = FOAF.createDefaultMemoryModel();

	this.socc = new SOCC(siocModel, configuration);

	this.camelContext = new DefaultCamelContext();
	try {
	    this.camelContext.start();
	} catch (Exception e) {
	    LOG.error("Failed to start Apache Camel", e);
	    shutdown();
	}
    }

    private File getSettingsFile() {
	File result = new File("./" + CONNECTOR_CFG_FILENAME);

	if (!result.exists()) {
	    URL url = SOCCShopApplication.class.getResource(
		    "/" + CONNECTOR_CFG_FILENAME);

	    if (null != url) {
		try {
		    result = new File(url.toURI());
		} catch (Throwable t) {
		    LOG.warn("Failed to load settings file from classpath", t);
		    return null;
		}
	    } else {
		return null;
	    }
	}

	return result;
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

	if (null != socc) {
	    File settingsFile = getSettingsFile();

	    if (null == settingsFile) {
		settingsFile = new File("./" + CONNECTOR_CFG_FILENAME);
	    }

	    try {
		socc.getConfiguration().save(settingsFile);

		LOG.debug(
			"{} successfuly written.",
			CONNECTOR_CFG_FILENAME);
	    } catch (IOException e) {
		LOG.error("Failed to write " + CONNECTOR_CFG_FILENAME, e);
	    }
	}

	if (null != camelContext) {
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
			    new File("./" + CAMEL_ROUTES_FILENAME),
			    routesXML,
			    "UTF-8");

		    LOG.debug(
			    "{} successfuly written.",
			    CAMEL_ROUTES_FILENAME);
		} catch (IOException e) {
		    LOG.error("Failed to write " + CAMEL_ROUTES_FILENAME, e);
		}
	    }
	}

	if (null != siocModel) {
	    String siocXML = ExportUtils.getModelXML(siocModel);

	    if (null != siocXML) {
		try {
		    FileUtils.writeStringToFile(
			    new File("./" + SIOC_DATA_FILENAME),
			    siocXML,
			    "UTF-8");

		    LOG.debug(
			    "{} successfuly written.",
			    SIOC_DATA_FILENAME);
		} catch (IOException e) {
		    LOG.error("Failed to write " + SIOC_DATA_FILENAME, e);
		}
	    }
	}

	if (null != foafModel) {
	    String foafXML = ExportUtils.getModelXML(foafModel);

	    if (null != foafXML) {
		try {
		    FileUtils.writeStringToFile(
			    new File("./" + FOAF_DATA_FILENAME),
			    foafXML,
			    "UTF-8");
		    LOG.debug(
			    "{} successfuly written.",
			    FOAF_DATA_FILENAME);
		} catch (IOException e) {
		    LOG.error("Failed to write " + FOAF_DATA_FILENAME, e);
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

    public SOCC getSocc() {
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
