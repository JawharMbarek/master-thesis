package de.m0ep.socc;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ontoware.rdf2go.RDF2Go;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.util.Builder;

import de.m0ep.sioc.service.auth.AccessToken;
import de.m0ep.sioc.service.auth.Authentication;
import de.m0ep.sioc.service.auth.ClientId;
import de.m0ep.sioc.service.auth.ClientSecret;
import de.m0ep.sioc.service.auth.Service;
import de.m0ep.sioc.service.auth.UserAccount;
import de.m0ep.socc.connectors.facebook.FacebookConnector;
import de.m0ep.socc.exceptions.ConnectorException;

public class FacebookConnectorTest {

    private Model model;
    private ISOCCContext context;

    private Service service;
    private UserAccount userAccount;

    private URI endpoint = Builder.createURI("http://facebook.com");

    @Before
    public void setUp() throws Exception {
	this.model = RDF2Go.getModelFactory().createModel();
	this.model.open();
	this.context = new SOCCContext(model);

	service = new Service(model, true);
	userAccount = new UserAccount(model, true);

	Authentication userAuth = new Authentication(model, true);
	Authentication serviceAuth = new Authentication(model, true);

	ClientId clientId = new ClientId(model, true);
	ClientSecret clientSecret = new ClientSecret(model, true);
	AccessToken accessToken = new AccessToken(model, true);

	clientId.setValue("218182098322396");
	clientSecret.setValue("f4ed27b621c0f6476c2741f7cf9c4dc5");
	accessToken
		.setValue(
		"CAACEdEose0cBAPJyBxlErQUcETSyuMlmKkrru5kux4o2sZBbReEXunIc4"
			+ "QSvaiFlJ6esRJnfbnUNdqgzdSWagYAGmQH2YkQTqmjVOHqmZAC3DSTkxfP"
			+ "wQfAhCzkl68KnspLAMGXFn5t46Cai0sHsrZCqO0z784QsXgH5pgTBgZDZD");

	serviceAuth.addCredential(clientId);
	serviceAuth.addCredential(clientSecret);
	userAuth.addCredential(accessToken);

	service.setAuthentication(serviceAuth);
	service.setServiceEndpoint(endpoint);

	userAccount.setAuthentication(userAuth);
	userAccount.setAccountName("m0eper");
	userAccount.setAccountServiceHomepage(endpoint);
    }

    @After
    public void tearDown() throws Exception {
	model.close();
    }

    @Test
    public void testInitialize() throws ConnectorException {
	IConnector connector = new FacebookConnector();
	connector.initialize("facebook-test", context, service, userAccount);
    }

    @Test
    public void testConnectDisconnect() throws ConnectorException {
	IConnector connector = new FacebookConnector();
	connector.initialize("facebook-test", context, service, userAccount);

	connector.connect();
	Assert.assertTrue(connector.isConnected());
	connector.disconnect();
	Assert.assertFalse(connector.isConnected());
    }
}
