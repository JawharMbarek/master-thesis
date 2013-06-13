package de.m0ep.socc;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ontoware.rdf2go.RDF2Go;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.util.Builder;

import de.m0ep.sioc.service.auth.AccessToken;
import de.m0ep.sioc.service.auth.Authentication;
import de.m0ep.sioc.service.auth.ClientId;
import de.m0ep.sioc.service.auth.ClientSecret;
import de.m0ep.sioc.service.auth.OAuth;
import de.m0ep.sioc.service.auth.RefreshToken;
import de.m0ep.sioc.service.auth.Service;
import de.m0ep.sioc.service.auth.UserAccount;
import de.m0ep.socc.connectors.google.plus.GooglePlusConnector;
import de.m0ep.socc.exceptions.ConnectorException;

public class GooglePlusConnectorTest {

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
	service.setServiceEndpoint(Builder.createURI("http://plus.google.com"));

	userAccount = new UserAccount(model, true);
	userAccount.setAccountName("max.hiwi");
	userAccount.setAccountServiceHomepage(
		Builder.createURI("http://plus.google.com"));

	Authentication serviceAuth = new OAuth(model, true);
	service.setAuthentication(serviceAuth);

	Authentication userAuth = new OAuth(model, true);
	userAccount.setAuthentication(userAuth);

	ClientId clientId = new ClientId(model, true);
	clientId.setValue("733024832603-patciplam4cqq0dnv7a5qdhuq262n6ia.apps.googleusercontent.com");
	serviceAuth.addCredential(clientId);

	ClientSecret clientSecret = new ClientSecret(model, true);
	clientSecret.setValue("LckucP4MA1jJsZQKjk9okhAu");
	serviceAuth.addCredential(clientSecret);

	AccessToken accessToken = new AccessToken(model, true);
	accessToken.setValue(
		"ya29.AHES6ZQn8OmkXk3Gu8y2LGHWszfM99U1LqFa2zAEZcN7BhjYSRyc");
	userAuth.addCredential(accessToken);

	RefreshToken refreshToken = new RefreshToken(model, true);
	refreshToken.setValue("1/Qsc2kHvKM5g-t_GyrFIF54RvXmCMU5AM3ezxrHdRjBI");
	userAuth.addCredential(refreshToken);
    }

    @After
    public void tearDown() throws Exception {
	model.close();
    }

    @Test
    public void testInitialize() throws ConnectorException {
	IConnector connector = new GooglePlusConnector();
	connector.initialize("facebook-test", context, service, userAccount);
    }

    @Test
    public void testConnectDisconnect() throws ConnectorException {
	IConnector connector = new GooglePlusConnector();
	connector.initialize("facebook-test", context, service, userAccount);

	connector.connect();
	Assert.assertTrue(connector.isConnected());
	connector.disconnect();
	Assert.assertFalse(connector.isConnected());
    }

}
