package de.m0ep.socc.shop;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

import org.apache.camel.impl.DefaultCamelContext;
import org.ontoware.rdf2go.RDF2Go;
import org.ontoware.rdf2go.exception.ModelRuntimeException;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.Statement;
import org.ontoware.rdf2go.model.Syntax;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.xmlns.foaf.FOAF;

import de.m0ep.socc.SOCC;
import de.m0ep.socc.config.SOCCConfiguration;
import de.m0ep.socc.shop.ui.ApplicationWindow;

public class SOCCShopApplication {
    private static final String CONNECTOR_CFG_FILENAME = "connectors.json";

    private static final Logger LOG = LoggerFactory
	    .getLogger(SOCCShopApplication.class);

    private ApplicationWindow window;

    private SOCC socc;
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
		stop(1);
	    }
	} else {
	    int result = JOptionPane
		    .showConfirmDialog(
			    null,
			    "Can't found any connector configuration file.\nContinue anyway?",
			    "No connector configuration!",
			    JOptionPane.YES_NO_OPTION,
			    JOptionPane.QUESTION_MESSAGE);

	    if (JOptionPane.NO_OPTION == result) {
		stop(2);
	    }

	    configuration = new SOCCConfiguration();
	}

	this.siocModel = SOCC.createDefaultMemoryModel();
	this.foafModel = FOAF.createDefaultMemoryModel();
	this.socc = new SOCC(siocModel, configuration);

	this.camelContext = new DefaultCamelContext();
	try {
	    this.camelContext.start();
	} catch (Exception e) {
	    LOG.error("Failed to start Apache Camel", e);
	    stop(1);
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
		    LOG.warn("failed to load settings file from classpath",
			    t);
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
		    stop(1);
		}
	    }
	});
    }

    /* Runtime */

    public void save() {
	LOG.info("Saving application settings and data...");

	if (null != socc) {
	    URL settingsUrl = SOCCShopApplication.class
		    .getResource(CONNECTOR_CFG_FILENAME);

	    if (null != settingsUrl) {
		try {
		    File settingsFile = new File(settingsUrl.toURI());
		    socc.getConfiguration().save(settingsFile);
		} catch (Throwable t) {
		    LOG.error("Failed to save settings.", t);
		}
	    }
	}

	if (null != siocModel) {
	    writeModelToFile(new File("sioc.xml"), siocModel);
	}

	if (null != foafModel) {
	    writeModelToFile(new File("foaf.xml"), foafModel);
	}
    }

    private void writeModelToFile(final File file, Model model) {
	// get all statements and order them by the subject
	List<Statement> statements = Lists.newArrayList(model.iterator());
	Collections.sort(statements, new Comparator<Statement>() {
	    @Override
	    public int compare(Statement o1, Statement o2) {
		return o1.compareTo(o2);
	    };
	});

	// create a new model to write it to the file
	Model writeModel = RDF2Go.getModelFactory().createModel();
	writeModel.open();

	// copy namespaces
	for (Entry<String, String> ns : model.getNamespaces().entrySet()) {
	    writeModel.setNamespace(ns.getKey(), ns.getValue());
	}

	writeModel.addAll(statements.iterator());

	try {
	    writeModel.writeTo(new FileOutputStream(file), Syntax.RdfXml);
	} catch (ModelRuntimeException e) {
	    LOG.error("Failed to write model", e);
	} catch (FileNotFoundException e) {
	    LOG.error("No file '" + file.getAbsolutePath() + "' found", e);
	} catch (IOException e) {
	    LOG.error("Failed to write model to file", e);
	}

	writeModel.close();
    }

    public void stop(int code) {
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
	System.exit(code);
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
