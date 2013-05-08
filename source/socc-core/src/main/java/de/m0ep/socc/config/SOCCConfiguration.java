package de.m0ep.socc.config;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SOCCConfiguration implements Serializable {
    private static final long serialVersionUID = -4200515066656834230L;
    private List<SOCCConfigConnectorEntry> connectors;

    public List<SOCCConfigConnectorEntry> getConnectors() {
	if (null == connectors) {
	    this.connectors = new ArrayList<SOCCConfigConnectorEntry>();
	}

	return Collections.unmodifiableList(this.connectors);
    }

    public void setConnectors(List<SOCCConfigConnectorEntry> connectors) {
	this.connectors = new ArrayList<SOCCConfigConnectorEntry>(connectors);
    }

    public void addConnector(SOCCConfigConnectorEntry connector) {
	if (!connectors.contains(connector)) {
	    connectors.add(connector);
	}
    }

    public void removeConnector(SOCCConfigConnectorEntry connector) {
	connectors.remove(connector);
    }

    public static SOCCConfiguration load(File file) throws IOException {
	Preconditions.checkNotNull(file, "File can not be null");
	Preconditions.checkArgument(file.exists(), "File must exist");

	String json = FileUtils.readFileToString(file, "UTF-8");
	Gson gson = new Gson();
	return gson.fromJson(json, SOCCConfiguration.class);
    }

    public void save(final File file) throws IOException {
	Preconditions.checkNotNull(file, "File can not be null");

	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	String json = gson.toJson(this);

	FileUtils.writeStringToFile(file, json, "UTF-8");

    }
}
