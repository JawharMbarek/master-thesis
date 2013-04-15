package de.m0ep.socc.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SOCCConfiguration implements Serializable {
    private static final long serialVersionUID = -4200515066656834230L;
    private List<SOCCConfigConnectorEntry> connectors;

    public SOCCConfiguration() {
	// TODO Auto-generated constructor stub
    }

    public SOCCConfiguration(final InputStream stream) {

    }

    public SOCCConfiguration(final String configFileName) throws FileNotFoundException {
	this(new FileInputStream(configFileName));
    }

    public List<SOCCConfigConnectorEntry> getConnectors() {
	return Collections.unmodifiableList(this.connectors);
    }

    public void setConnectors(List<SOCCConfigConnectorEntry> connectors) {
	this.connectors = new ArrayList<SOCCConfigConnectorEntry>(connectors);
    }
}
