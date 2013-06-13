package de.m0ep.socc;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ontoware.rdf2go.RDF2Go;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.util.Builder;

import de.m0ep.sioc.service.auth.APIKey;
import de.m0ep.sioc.service.auth.Authentication;
import de.m0ep.sioc.service.auth.Password;
import de.m0ep.sioc.service.auth.Service;
import de.m0ep.sioc.service.auth.UserAccount;
import de.m0ep.sioc.service.auth.Username;
import de.m0ep.sioc.service.auth.WebAPI;
import de.m0ep.socc.connectors.google.youtube.YoutubeV2Connector;
import de.m0ep.socc.exceptions.ConnectorException;

public class YoutubeConnectorTest {

    private Model model;
    private ISOCCContext context;

    private Service service;
    private UserAccount userAccount;

    @Before
    public void setUp() throws Exception {
	model = RDF2Go.getModelFactory().createModel();
	model.open();
	context = new SOCCContext(model);

	service = new Service(model, true);
	service.setServiceEndpoint(Builder.createURI("http://youtube.com"));

	userAccount = new UserAccount(model, true);
	userAccount.setAccountName("m0eper");
	userAccount.setAccountServiceHomepage(
		Builder.createURI("http://youtube.com"));

	Authentication serviceAuth = new WebAPI(model, true);
	service.setAuthentication(serviceAuth);

	Authentication userAuth = new WebAPI(model, true);
	userAccount.setAuthentication(userAuth);

	APIKey apiKey = new APIKey(model, true);
	apiKey.setValue("");
	serviceAuth.addCredential(apiKey);

	Username username = new Username(model, true);
	username.setValue("tkhiwis@gmail.com");
	userAuth.addCredential(username);

	Password password = new Password(model, true);
	password.setValue("turing123");
	userAuth.addCredential(password);
    }

    @After
    public void tearDown() throws Exception {
	model.close();
    }

    @Test
    public void testInitialize() throws ConnectorException {
	IConnector connector = new YoutubeV2Connector();
	connector.initialize("youtube-test", context, service, userAccount);
    }

    @Test
    public void testConnectDisconnect() throws ConnectorException {
	IConnector connector = new YoutubeV2Connector();
	connector.initialize("youtube-test", context, service, userAccount);

	connector.connect();
	Assert.assertTrue(connector.isConnected());
	connector.disconnect();
	Assert.assertFalse(connector.isConnected());
    }
}
