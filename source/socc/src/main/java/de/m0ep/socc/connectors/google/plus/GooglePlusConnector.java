package de.m0ep.socc.connectors.google.plus;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Properties;

import org.apache.commons.codec.digest.DigestUtils;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.node.URI;
import org.rdfs.sioc.Site;
import org.rdfs.sioc.UserAccount;

import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.java6.auth.oauth2.FileCredentialStore;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.PlusScopes;
import com.google.api.services.plus.model.Person;
import com.google.api.services.plus.model.Person.Emails;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;

import de.m0ep.socc.connectors.AbstractConnector;
import de.m0ep.socc.utils.RDF2GoUtils;

public class GooglePlusConnector extends AbstractConnector {

    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();

    public static final String CONFIG_ACCESS_TOKEN = "access_token";
    public static final String CONFIG_REFRESH_TOKEN = "refresh_token";
    public static final String CONFIG_CLIENT_ID = "client_id";
    public static final String CONFIG_CLIENT_SECRET = "client_secret";

    public static final String CONFIG_CLIENT_SECRETS_FILE = "client_secrets_file";
    public static final String CONFIG_CREDENTIALS_FILE = "credent_file";
    public static final String CONFIG_USER_ID = "user_id";

    GoogleClientSecrets clientSecrets;
    GoogleCredential googleCredentials;
    FileCredentialStore fileStore;

    Plus plus;
    String myId;

    public GooglePlusConnector(String id, Model model, Properties config) {
	super(id, model, config);
	Preconditions.checkArgument(config
		.containsKey(CONFIG_CLIENT_SECRETS_FILE));

	try {
	    clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, ClassLoader
		    .getSystemResourceAsStream(getConfig().getProperty(
			    CONFIG_CLIENT_SECRETS_FILE)));
	} catch (IOException e) {
	    Throwables.propagate(e);
	}

	if (null != clientSecrets) {
	    String credentials_file = getConfig().getProperty(
		    CONFIG_CREDENTIALS_FILE, "credentials.json");

	    try {
		fileStore = new FileCredentialStore(new File(ClassLoader
			.getSystemResource(credentials_file).toURI()),
			JSON_FACTORY);
	    } catch (Exception e) {
		Throwables.propagate(e);
	    }

	    FileCredentialStore credentialStore2 = null;
	    try {
		credentialStore2 = new FileCredentialStore(
		    new File(ClassLoader.getSystemResource("credentials.json")
			    .toURI()), JSON_FACTORY);
	    } catch (Exception e) {
		Throwables.propagate(e);
	    }

	    // set up authorization code flow
	    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
		    HTTP_TRANSPORT, JSON_FACTORY, clientSecrets,
		    Collections.singleton(PlusScopes.PLUS_ME))
		    .setCredentialStore(credentialStore2).build();
	    // authorize
	    try {
		googleCredentials = (GoogleCredential) new AuthorizationCodeInstalledApp(
		    flow, new LocalServerReceiver()).authorize("user");
	    } catch (Exception e) {
		Throwables.propagate(e);
	    }

	    plus = new Plus.Builder(HTTP_TRANSPORT, JSON_FACTORY,
		    googleCredentials).setApplicationName("SOCC").build();

	    try {
		Person me = plus.people().get("me").setFields("id").execute();
		myId = me.getId();
	    } catch (IOException e) {
		Throwables.propagate(e);
	    }

	} else {
	    throw new RuntimeException("no clientsecrets loaded");
	}

    }

    @Override
    public String getURL() {
	// TODO Auto-generated method stub
	return "https://plus.google.com/";
    }

    @Override
    public Site getSite() {
	URI uri = RDF2GoUtils.createURI(getURL());

	if (!Site.hasInstance(getModel(), uri)) {
	    Site result = new Site(getModel(), uri, true);
	    result.setName("Google+");
	    return result;
	} else {
	    return Site.getInstance(getModel(), uri);
	}
    }

    @Override
    public UserAccount getUser() {
	return getUser(myId);
    }

    private UserAccount getUser(String id) {
	Person user = null;
	try {
	    user = plus
		    .people()
		    .get(id)
		    .setFields(
			    "aboutMe,displayName,emails/value,id,image,nickname,url")
		    .execute();

	} catch (IOException e) {
	    Throwables.propagate(e);
	}

	URI uri = RDF2GoUtils.createURI(getURL() + user.getId());

	if (!UserAccount.hasInstance(getModel(), uri)) {
	    UserAccount result = new UserAccount(getModel(), uri, true);

	    result.setId(user.getId());
	    result.setIsPartOf(getSite());

	    if (null != user.getAboutMe()) {
		result.setDescription(user.getAboutMe());
	    }

	    if (null != user.getDisplayName()) {
		result.setName(user.getDisplayName());
	    }

	    if (null != user.getNickname()) {
		result.setAccountname(user.getNickname());
	    }

	    if (null != user.getUrl()) {
		result.setAccountservicehomepage(RDF2GoUtils.createURI(user
			.getUrl()));
	    }

	    if (null != user.getEmails()) {
		for (Emails email : user.getEmails()) {
		    result.addEmail(RDF2GoUtils.createMailtoURI(email
			    .getValue()));
		    result.addEmailsha1(DigestUtils.sha1Hex(email.getValue()));
		}
	    }

	    if (null != user.getImage()) {
		result.setAvatar(RDF2GoUtils
			.createURI(user.getImage().getUrl()));
	    }

	    return result;
	} else {
	    return UserAccount.getInstance(getModel(), uri);
	}
    }

}
