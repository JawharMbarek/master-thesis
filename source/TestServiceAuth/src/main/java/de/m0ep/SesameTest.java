package de.m0ep;

import org.ontoware.rdf2go.model.Syntax;
import org.ontoware.rdf2go.util.Builder;
import org.ontoware.rdf2go.util.RDFTool;
import org.openrdf.rdf2go.RepositoryModel;
import org.openrdf.repository.http.HTTPRepository;
import org.rdfs.sioc.SIOCVocabulary;
import org.rdfs.sioc.services.ServicesVocabulary;
import org.rdfs.sioc.services.Thing;

import com.xmlns.foaf.FOAFVocabulary;

import de.m0ep.sioc.service.auth.AccessToken;
import de.m0ep.sioc.service.auth.ClientId;
import de.m0ep.sioc.service.auth.ClientSecret;
import de.m0ep.sioc.service.auth.OAuth;
import de.m0ep.sioc.service.auth.Service;
import de.m0ep.sioc.service.auth.ServicesAuthVocabulary;
import de.m0ep.sioc.service.auth.UserAccount;

public class SesameTest {

    /**
     * @param args
     */
    public static void main(String[] args) {

	HTTPRepository repository = new HTTPRepository(
		"http://localhost:8080/openrdf-sesame/",
		"master-thesis");

	RepositoryModel model = new RepositoryModel(repository);
	model.open();

	model.setNamespace(
		"foaf",
		FOAFVocabulary.NS_FOAF.toString());
	model.setNamespace(
		"sioc",
		SIOCVocabulary.NS_SIOC.toString());
	model.setNamespace(
		"service",
		ServicesVocabulary.NS_ServicesVocabulary.toString());
	model.setNamespace(
		"service-auth",
		ServicesAuthVocabulary.NS_ServicesAuthVocabulary.toString());

	ClientId clientId = new ClientId(
		model,
		"https://graph.facebook.com/oauth/clientid",
		true);

	ClientSecret clientSecret = new ClientSecret(
		model,
		"https://graph.facebook.com/oauth/clientsecret",
		true);

	OAuth oAuth = new OAuth(
		model,
		"https://graph.facebook.com/oauth",
		true);

	Service graphAPI = new Service(
		model,
		"https://graph.facebook.com",
		true);

	graphAPI.setAuthentication(oAuth);
	graphAPI.setName("Facebook Graph API");
	graphAPI.setDescription("REST like API to Access Facebook data");

	oAuth.addCredential(clientId);
	oAuth.addCredential(clientSecret);

	clientId.setValue(Builder.createPlainliteral("clientId"));
	clientSecret.setValue(Builder.createPlainliteral("clientSecret"));

	UserAccount userAccount = new UserAccount(
		model,
		"https://facebook.com/1",
		true);
	OAuth userOAuth = new OAuth(
		model,
		"https://facebook.com/1/oauth",
		true);
	AccessToken accessToken = new AccessToken(
		model,
		"https://facebook.com/1/oauth/accesstoken",
		true);

	userAccount.setAccountName("m0eper");
	userAccount.setName("Florian Müller");
	userAccount.setAuthentication(userOAuth);
	Thing.setService(model, userAccount, graphAPI);

	userOAuth.addCredential(accessToken);

	accessToken.setValue(Builder.createPlainliteral("accesstoken"));

	// ServiceTestApp app = new ServiceTestApp(model);
	// app.getFrame().setVisible(true);

	System.out.println(RDFTool.modelToString(model, Syntax.RdfXml));
	model.close();
    }

}
