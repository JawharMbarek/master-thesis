package de.m0ep.camel.socc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;
import org.ontoware.rdf2go.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.m0ep.socc.IConnector;
import de.m0ep.socc.config.DefaultConnectorConfig;
import de.m0ep.socc.connectors.facebook.FacebookConnectorFactory;
import de.m0ep.socc.connectors.google.plus.GooglePlusConnectorConfig;
import de.m0ep.socc.connectors.google.plus.GooglePlusConnectorFactory;
import de.m0ep.socc.connectors.moodle.MoodleConnectorConfig;
import de.m0ep.socc.connectors.moodle.MoodleConnectorFactory;

public class SOCCComponent extends DefaultComponent {
    private static final Logger LOG = LoggerFactory
	    .getLogger(SOCCComponent.class);

    private Model model;
    private Map<String, IConnector> connectors;
    private long delay;

    public SOCCComponent() {
    }

    public SOCCComponent(final Model model) {
	this.model = model;
    }

    @Override
    protected Endpoint createEndpoint(String uri, String remaining,
	    Map<String, Object> parameters) throws Exception {

	connectors = new HashMap<String, IConnector>();

	Properties configFile = new Properties();
	try {
	    configFile.load(SOCCComponent.class
		    .getResourceAsStream("/user.properties"));
	} catch (IOException e1) {
	    e1.printStackTrace();
	    System.exit(-1);
	}

	Map<String, Object> mdlParams = new HashMap<String, Object>();
	mdlParams.put(DefaultConnectorConfig.MAX_NEW_POSTS_ON_POLL,
		configFile.get("global.postPerPoll"));
	mdlParams.put(DefaultConnectorConfig.POLL_COOLDOWN,
		configFile.get("global.pollCooldown"));
	mdlParams.put(MoodleConnectorConfig.URL, configFile.get("mdl.url"));
	mdlParams.put(MoodleConnectorConfig.USERNAME,
		configFile.get("mdl.username"));
	mdlParams.put(MoodleConnectorConfig.PASSWORD,
		configFile.get("mdl.password"));

	Map<String, Object> gpParams = new HashMap<String, Object>();
	gpParams.put(DefaultConnectorConfig.MAX_NEW_POSTS_ON_POLL,
		configFile.get("global.postPerPoll"));
	gpParams.put(DefaultConnectorConfig.POLL_COOLDOWN,
		configFile.get("global.pollCooldown"));
	gpParams.put(GooglePlusConnectorConfig.CLIENT_ID,
		configFile.get("gp.clientId"));
	gpParams.put(GooglePlusConnectorConfig.CLIENT_SECRET,
		configFile.get("gp.clientSecret"));
	gpParams.put(GooglePlusConnectorConfig.ACCESS_TOKEN,
		configFile.get("gp.accessToken"));
	gpParams.put(GooglePlusConnectorConfig.REFRESH_TOKEN,
		configFile.get("gp.refreshToken"));

	Map<String, Object> fbParams = new HashMap<String, Object>();
	fbParams.put(DefaultConnectorConfig.MAX_NEW_POSTS_ON_POLL,
		configFile.get("global.postPerPoll"));
	fbParams.put(DefaultConnectorConfig.POLL_COOLDOWN,
		configFile.get("global.pollCooldown"));
	fbParams.put(GooglePlusConnectorConfig.CLIENT_ID,
		configFile.get("fb.clientId"));
	fbParams.put(GooglePlusConnectorConfig.CLIENT_SECRET,
		configFile.get("fb.clientSecret"));
	fbParams.put(GooglePlusConnectorConfig.ACCESS_TOKEN,
		configFile.get("fb.accessToken"));

	SOCCComponentConfiguration configuration = new SOCCComponentConfiguration();
	setProperties(configuration, parameters);

	MoodleConnectorFactory mdlFactory = new MoodleConnectorFactory();
	GooglePlusConnectorFactory gpFactory = new GooglePlusConnectorFactory();
	FacebookConnectorFactory fbFactory = new FacebookConnectorFactory();

	IConnector mdlConnector = mdlFactory.createConnector("moodle-test",
		getModel(), mdlParams);
	IConnector gpConnector = gpFactory.createConnector("googleplus-test",
		model, gpParams);
	IConnector fbConnector = fbFactory.createConnector("facebook-test",
		getModel(), fbParams);

	connectors.put(mdlConnector.getId(), mdlConnector);
	connectors.put(gpConnector.getId(), gpConnector);
	connectors.put(fbConnector.getId(), fbConnector);

	if (connectors.containsKey(remaining)) {

	    if (null != configuration.getPostId()) {
		return new SOCCPostEndpoint(uri, connectors.get(remaining),
			configuration);
	    } else if (null != configuration.getForumId()
		    && null == configuration.getThreadId()) {
		return new SOCCForumEndpoint(uri, connectors.get(remaining),
			configuration);
	    } else if (null != configuration.getThreadId()) {
		return new SOCCThreadEndpoint(uri, connectors.get(remaining),
			configuration);
	    }
	}

	throw new Exception("");
    }

    @Override
    public void start() throws Exception {
	super.start();
	LOG.debug("start component");
    }

    @Override
    public void stop() throws Exception {
	super.stop();

	LOG.debug("stop component");
    }

    public Model getModel() {
	return this.model;
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

    public long getDelay() {
	return this.delay;
    }

    public void setDelay(long delay) {
	this.delay = delay;
    }
}
