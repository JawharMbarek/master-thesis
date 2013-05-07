package de.m0ep.camel.socc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.Constants;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.model.RoutesDefinition;
import org.ontoware.rdf2go.model.Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.m0ep.socc.SOCC;
import de.m0ep.socc.config.DefaultConnectorConfig;
import de.m0ep.socc.config.SOCCConfigConnectorEntry;
import de.m0ep.socc.config.SOCCConfiguration;
import de.m0ep.socc.connectors.google.plus.GooglePlusConnectorConfig;
import de.m0ep.socc.connectors.google.youtube.YoutubeV2ConnectorConfig;
import de.m0ep.socc.connectors.moodle.MoodleConnectorConfig;

public class SOCCCamelTest {
    public static void main(String[] args) throws Exception {
	DefaultCamelContext context = new DefaultCamelContext();
	Model model = SOCC.createDefaultMemoryModel();

	SOCC socc = new SOCC(model);

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

	Map<String, Object> ytParams = new HashMap<String, Object>();
	ytParams.put(DefaultConnectorConfig.MAX_NEW_POSTS_ON_POLL,
		configFile.get("global.postPerPoll"));
	ytParams.put(DefaultConnectorConfig.POLL_COOLDOWN,
		configFile.get("global.pollCooldown"));
	ytParams.put(YoutubeV2ConnectorConfig.PASSWORD,
		configFile.get("yt.password"));
	ytParams.put(YoutubeV2ConnectorConfig.USERNAME,
		configFile.get("yt.username"));
	ytParams.put(YoutubeV2ConnectorConfig.DEVELOPER_KEY,
		configFile.get("yt.developerKey"));

	socc.createConnector("YoutubeV2ConnectorFactory", "youtube-test",
		ytParams);
	socc.createConnector("GooglePlusConnectorFactory",
		"googleplus-test", gpParams);
	socc.createConnector("Moodle2.4ConnectorFactory", "moodle-test",
		mdlParams);
	socc.createConnector("FacebookConnectorFactory_1.0", "facebook-test",
		fbParams);

	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	String json = gson.toJson(socc.getConfiguration());
	SOCCConfiguration config = gson.fromJson(json, SOCCConfiguration.class);

	for (SOCCConfigConnectorEntry entry : config.getConnectors()) {
	    System.err.println(entry.getId());
	    System.err.println(entry.getFactoryId());

	    for (Entry<String, Object> param : entry.getParameters().entrySet()) {
		System.err.println("\t" + param.getKey() + " = "
			+ param.getValue());
	    }

	    System.err.println("-------------------------");
	}

	SOCCComponent soccComponent = new SOCCComponent();
	soccComponent.setSOCC(socc);
	context.addComponent("socc", soccComponent);

	context.addRoutes(new RouteBuilder() {
	    @Override
	    public void configure() throws Exception {
		from("socc:facebook-test?forumId=100000490230885&delay=5000")
			/* .marshal(new RDFXMLDataformat()) */.to(
				"socc:moodle-test?forumId=1&threadId=2");
	    }
	});

	context.start();

	String line = "";
	BufferedReader bis = new BufferedReader(
		new InputStreamReader(System.in));

	do {
	    line = bis.readLine();
	} while (!"q".equals(line));

	context.stop();

	JAXBContext jaxbContext = JAXBContext.newInstance(
		Constants.JAXB_CONTEXT_PACKAGES, SOCCCamelTest.class
			.getClassLoader());
	
	Marshaller marshaller = jaxbContext.createMarshaller();
	List<RouteDefinition> routeList = context.getRouteDefinitions();
	RoutesDefinition routesDefinition = new RoutesDefinition();
	for (RouteDefinition routeDefinition : routeList) {
	    routesDefinition.getRoutes().add(routeDefinition);
	}
	
	System.out.println("-----------------------");
	marshaller.marshal(routesDefinition, System.out);
    }
}
