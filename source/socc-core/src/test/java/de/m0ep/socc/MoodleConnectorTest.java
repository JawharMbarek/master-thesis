package de.m0ep.socc;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ontoware.rdf2go.RDF2Go;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.util.Builder;

import de.m0ep.sioc.service.auth.Authentication;
import de.m0ep.sioc.service.auth.Password;
import de.m0ep.sioc.service.auth.Service;
import de.m0ep.sioc.service.auth.UserAccount;
import de.m0ep.sioc.service.auth.Username;
import de.m0ep.socc.connectors.moodle.MoodleConnector;
import de.m0ep.socc.exceptions.ConnectorException;

public class MoodleConnectorTest {

    private Model model;
    private ISOCCContext context = null;

    private UserAccount moodleUser;
    private Service moodleService;

    @Before
    public void setUp() throws Exception {
	this.model = RDF2Go.getModelFactory().createModel();
	this.model.open();
	this.context = new SOCCContext(this.model);

	moodleService = new Service(model, true);
	moodleService.setServiceEndpoint(Builder
		.createURI("http://localhost/moodle"));

	moodleUser = new UserAccount(model, true);
	Authentication authentication = new Authentication(model, true);
	Username username = new Username(model, true);
	Password password = new Password(model, true);

	username.setValue("admin");
	password.setValue("admin");
	authentication.addCredential(username);
	authentication.addCredential(password);
	moodleUser.setAuthentication(authentication);
	moodleUser.setAccountName("admin");
	moodleUser.setAccountServiceHomepage(Builder
		.createURI("http://localhost/moodle"));
    }

    @After
    public void tearDown() throws Exception {
	model.close();
    }

    @Test
    public void testInitialize() throws ConnectorException {
	IConnector connector = new MoodleConnector();
	connector.initialize("moodle-test", context, moodleService,
		moodleUser);
    }

    @Test(expected = NullPointerException.class)
    public void testInitializeNull() throws ConnectorException {
	IConnector connector = new MoodleConnector();
	connector.initialize(null, context, moodleService, moodleUser);
	connector.initialize("moodle-test", null, moodleService, moodleUser);
	connector.initialize("moodle-test", context, null, moodleUser);
	connector.initialize("moodle-test", context, null, moodleUser);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInitializerEmptyId() throws ConnectorException {
	IConnector connector = new MoodleConnector();
	connector.initialize("", context, moodleService, moodleUser);
    }

    @Test
    public void testConnectDisconnect() throws ConnectorException {
	IConnector connector = new MoodleConnector();
	connector.initialize("moodle-test", context, moodleService,
		moodleUser);

	connector.connect();
	Assert.assertTrue(connector.isConnected());
	connector.disconnect();
	Assert.assertFalse(connector.isConnected());
    }
}
