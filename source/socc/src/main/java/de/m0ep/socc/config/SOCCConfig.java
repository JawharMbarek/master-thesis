package de.m0ep.socc.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.m0ep.socc.connectors.Connector;
import de.m0ep.socc.connectors.ConnectorFactory;

public class SOCCConfig {
    private static final Logger LOG = LoggerFactory.getLogger(SOCCConfig.class);

    List<ConnectorConfig> connectors;

    public SOCCConfig() {
	connectors = new ArrayList<ConnectorConfig>();
    }

    public void addConnector(Connector connector, ConnectorFactory factory) {
	ConnectorConfig cfg = new ConnectorConfig();
	cfg.setId(connector.getId());
	cfg.setFactoryName(factory.getUniqueFactoryName());

	for (String key : factory.getConfigKeys())
	    cfg.putParameter(key, connector.getConfig().getProperty(key));

	connectors.add(cfg);
    }

    public static SOCCConfig load(InputStream in) {
	Gson gson = new Gson();
	return gson.fromJson(new InputStreamReader(in), SOCCConfig.class);
    }
    
    public static void save(OutputStream out, SOCCConfig cfg){
	Gson gson = new GsonBuilder().setPrettyPrinting().setVersion(1)
		.create();
	OutputStreamWriter osw = new OutputStreamWriter(out);

	try {
	    osw.write(gson.toJson(cfg));
	} catch (IOException e) {
	    LOG.warn("Failed to write config", e);
	}
    }
}
