package de.m0ep.camel.socc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;
import org.ontoware.rdf2go.model.Model;

import de.m0ep.socc.IConnector;
import de.m0ep.socc.config.DefaultConnectorConfig;
import de.m0ep.socc.connectors.google.plus.GooglePlusConnectorConfig;
import de.m0ep.socc.connectors.google.plus.GooglePlusConnectorFactory;
import de.m0ep.socc.connectors.moodle.MoodleConnectorConfig;
import de.m0ep.socc.connectors.moodle.MoodleConnectorFactory;
import de.m0ep.socc.utils.SIOCUtils;

public class SOCCComponent extends DefaultComponent {

    private static Model model;
    private Map<String, IConnector> connectors;

    static {
	model = SIOCUtils.createDefaultMemoryModel();
	// TODO: model von aussen angeben!!!!
    }

    @Override
    protected Endpoint createEndpoint(String uri, String remaining,
	    Map<String, Object> parameters) throws Exception {

	connectors = new HashMap<String, IConnector>();

	Properties config = new Properties();
	try {
	    config.load(SOCCComponent.class
		    .getResourceAsStream("/user.properties"));
	} catch (IOException e1) {
	    e1.printStackTrace();
	    System.exit(-1);
	}

	Map<String, Object> mdlParams = new HashMap<String, Object>();
	mdlParams.put(DefaultConnectorConfig.MAX_NEW_POSTS_ON_POLL,
		config.get("global.postPerPoll"));
	mdlParams.put(DefaultConnectorConfig.POLL_COOLDOWN,
		config.get("global.pollCooldown"));
	mdlParams.put(MoodleConnectorConfig.URL, config.get("mdl.url"));
	mdlParams.put(MoodleConnectorConfig.USERNAME,
		config.get("mdl.username"));
	mdlParams.put(MoodleConnectorConfig.PASSWORD,
		config.get("mdl.password"));

	Map<String, Object> gpParams = new HashMap<String, Object>();
	gpParams.put(DefaultConnectorConfig.MAX_NEW_POSTS_ON_POLL,
		config.get("global.postPerPoll"));
	gpParams.put(DefaultConnectorConfig.POLL_COOLDOWN,
		config.get("global.pollCooldown"));
	gpParams.put(GooglePlusConnectorConfig.CLIENT_ID,
		config.get("gp.clientId"));
	gpParams.put(GooglePlusConnectorConfig.CLIENT_SECRET,
		config.get("gp.clientSecret"));
	gpParams.put(GooglePlusConnectorConfig.ACCESS_TOKEN,
		config.get("gp.accessToken"));
	gpParams.put(GooglePlusConnectorConfig.REFRESH_TOKEN,
		config.get("gp.refreshToken"));

	String forumId = getAndRemoveParameter(parameters, "forumId",
		String.class, null);
	String threadId = getAndRemoveParameter(parameters, "threadId",
		String.class, null);
	String postId = getAndRemoveParameter(parameters, "postId",
		String.class, null);

	System.err.println(uri);
	System.err.println(remaining);
	System.err.println(forumId);
	System.err.println(threadId);
	System.err.println(postId);

	MoodleConnectorFactory mdlFactory = new MoodleConnectorFactory();
	GooglePlusConnectorFactory gpFactory = new GooglePlusConnectorFactory();

	IConnector mdlConnector = mdlFactory.createConnector("moodle-test",
		getModel(), mdlParams);
	IConnector gpConnector = gpFactory.createConnector("googleplus-test",
		model, gpParams);

	connectors.put(mdlConnector.getId(), mdlConnector);
	connectors.put(gpConnector.getId(), gpConnector);

	if (connectors.containsKey(remaining)) {

	    if (null != postId) {
		return new SOCCPostEndpoint(uri, connectors.get(remaining),
			postId);
	    } else if (null != forumId && null == threadId) {
		return new SOCCForumEndpoint(uri, connectors.get(remaining),
			forumId);
	    } else if (null != threadId) {
		return new SOCCThreadEndpoint(uri, connectors.get(remaining),
			forumId, threadId);
	    }
	}

	throw new Exception("");
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
