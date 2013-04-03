package de.m0ep.camel.socc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;
import org.ontoware.rdf2go.model.Model;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import de.m0ep.socc.IConnector;
import de.m0ep.socc.connectors.moodle.MoodleConnectorConfig;
import de.m0ep.socc.connectors.moodle.MoodleConnectorFactory;
import de.m0ep.socc.utils.SIOCUtils;

public class SOCCComponent extends DefaultComponent {

    private Model model;
    private Map<String, IConnector> connectors;

    @Override
    protected Endpoint createEndpoint(String uri, String remaining,
	    Map<String, Object> parameters) throws Exception {

	model = SIOCUtils.createDefaultMemoryModel();
	connectors = new HashMap<String, IConnector>();

	Map<String, Object> mdlParams = new HashMap<>();
	mdlParams.put(MoodleConnectorConfig.USERNAME, "admin");
	mdlParams.put(MoodleConnectorConfig.PASSWORD, "admin");
	mdlParams.put(MoodleConnectorConfig.URL,
		"http://localhost/florian/moodle24/");

	MoodleConnectorFactory mdlFactory = new MoodleConnectorFactory();
	IConnector mdlConnector = mdlFactory.createConnector("moodle-test",
		getModel(), mdlParams);
	connectors.put(mdlConnector.getId(), mdlConnector);

	List<String> soccPath = Lists.newArrayList(Splitter.on('/').split(
		remaining));

	if (3 != soccPath.size())
	    throw new IllegalArgumentException(
		    "invalid url '{connectorId}/[thread|forum|post]/{id}");

	System.err.println(soccPath.get(0));
	System.err.println(soccPath.get(1));
	System.err.println(soccPath.get(2));

	SOCCEndpoint endpoint = new SOCCEndpoint(uri, connectors.get(soccPath
		.get(0)), soccPath.get(1), soccPath.get(2));
	return endpoint;
    }

    @Override
    public void start() throws Exception {
	super.start();
	System.out.println("start");
    }

    @Override
    public void stop() throws Exception {
	super.stop();

	System.out.println("stop");
    }

    public Model getModel() {
	return model;
    }

    public void setModel(Model model) {
	this.model = model;
    }

    public Map<String, IConnector> getConnectors() {
	return this.connectors;
    }

    public void setConnectors(Map<String, IConnector> connectors) {
	this.connectors = connectors;
    }
}
