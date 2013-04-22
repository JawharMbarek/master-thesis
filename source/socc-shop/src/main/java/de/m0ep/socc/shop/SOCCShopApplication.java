package de.m0ep.socc.shop;

import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.ontoware.rdf2go.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.m0ep.socc.SOCC;
import de.m0ep.socc.config.SOCCConfiguration;
import de.m0ep.socc.shop.ui.ApplicationWindow;

public class SOCCShopApplication {
    private static final Logger LOG = LoggerFactory
	    .getLogger(SOCCShopApplication.class);

    SOCCConfiguration configuration;
    SOCC socc;
    Model model;

    public static void main(String[] args) {
	SOCCShopApplication app = new SOCCShopApplication();
	app.runGUI();
    }

    public SOCCShopApplication() {
	URL settingsUrl = getClass().getResource("/setings.json");

	if (null != settingsUrl) {
	    File settingsFile = new File(settingsUrl.toString());

	    try {
		this.configuration = SOCCConfiguration.load(settingsFile);
	    } catch (IOException e) {
		LOG.error("Failed to load settings from file.", e);
		shutdown(-1);
	    }
	} else {
	    this.configuration = new SOCCConfiguration();
	}

	this.model = SOCC.createDefaultMemoryModel();
	this.socc = new SOCC(model, configuration);
    }

    public void runGUI() {
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {
		    ApplicationWindow window = new ApplicationWindow(
			    SOCCShopApplication.this);
		    window.run();
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	});
    }

    /* Runtime */

    public void save() {

	if (null != configuration) {
	    URL settingsUrl = getClass().getResource("/setings.json");
	    File settingsFile = new File(settingsUrl.toString());
	    try {
		this.configuration.save(settingsFile);
	    } catch (IOException e) {
		LOG.error("Failed to save settings.", e);
	    }
	}
    }

    public void shutdown(int code) {
	save();
	System.exit(code);
    }

    /* Getter & Setter */

    public SOCC getSocc() {
	return this.socc;
    }

    public SOCCConfiguration getConfiguration() {
	return this.configuration;
    }

    public Model getModel() {
	return this.model;
    }
}
