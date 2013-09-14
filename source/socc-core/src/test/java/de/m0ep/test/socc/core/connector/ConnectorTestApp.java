package de.m0ep.test.socc.core.connector;

import org.ontoware.rdf2go.RDF2Go;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.Syntax;
import org.ontoware.rdf2go.util.Builder;
import org.purl.dc.terms.DCTermsVocabulary;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.SiocVocabulary;
import org.rdfs.sioc.services.Thing;
import org.w3.ns.auth.acl.AclVocabulary;
import org.w3.ns.auth.acl.Authorization;

import com.xmlns.foaf.FoafVocabulary;
import com.xmlns.foaf.Person;

import de.m0ep.sioc.services.auth.APIKey;
import de.m0ep.sioc.services.auth.AccessToken;
import de.m0ep.sioc.services.auth.ClientId;
import de.m0ep.sioc.services.auth.ClientSecret;
import de.m0ep.sioc.services.auth.Direct;
import de.m0ep.sioc.services.auth.OAuth;
import de.m0ep.sioc.services.auth.Password;
import de.m0ep.sioc.services.auth.Service;
import de.m0ep.sioc.services.auth.UserAccount;
import de.m0ep.sioc.services.auth.Username;
import de.m0ep.sioc.services.auth.WebAPI;
import de.m0ep.socc.config.ConnectorConfig;
import de.m0ep.socc.core.SoccContext;
import de.m0ep.socc.core.connector.ConnectorFactory;
import de.m0ep.socc.core.connector.IConnector;
import de.m0ep.socc.core.connector.IConnector.IPostReader;
import de.m0ep.socc.core.connector.IConnector.IPostWriter;
import de.m0ep.socc.core.connector.IConnector.IStructureReader;
import de.m0ep.socc.core.connector.canvaslms.CanvasLmsConnector;
import de.m0ep.socc.core.connector.facebook.FacebookConnector;
import de.m0ep.socc.core.connector.google.youtube.v2.YoutubeConnector;
import de.m0ep.socc.core.connector.google.youtube.v2.YoutubeSiocUtils;
import de.m0ep.socc.core.connector.moodle.Moodle2Connector;
import de.m0ep.socc.core.utils.RdfUtils;

public class ConnectorTestApp {

	private static final String YOUTUBE_CONNECTOR_ID = "youtube-test";
	private static final String YOUTUBE_ROOT_URI = "http://www.youtube.com";
	private static final String CANVAS_LMS_CONNECTOR_ID = "canvas-test";
	private static final String CANVAS_LMS_ROOT_URI = "https://canvas.instructure.com";
	private static final String MOODLE_ROOT_URI = "http://localhost/moodle";
	private static final String MOODLE_CONNECTOR_ID = "moodle-test";
	private static final String FACEBOOK_ROOT_URI = "https://www.facebook.com";
	private static final String FACEBOOK_CONNECTOR_ID = "facebook-test";
	private Person florianPerson;
	private Person defaultPerson;
	private Person kaiPerson;
	private UserAccount florianUserAccountMoodle;
	private UserAccount kaiUserAccountMoodle;
	private UserAccount defaultUserAccountMoodle;

	public static void main( String[] args ) throws
	        Exception {
		ConnectorTestApp app = new ConnectorTestApp();
		app.run();
	}

	public void run() throws Exception {

		Model model = RDF2Go.getModelFactory().createModel();
		model.open();
		model.setNamespace( "sioc", SiocVocabulary.NS_SIOC.toString() );
		model.setNamespace( "foaf", FoafVocabulary.NS_FOAF.toString() );
		model.setNamespace( "dcterms", DCTermsVocabulary.NS_DCTerms.toString() );

		try {
			SoccContext context = new SoccContext( model );

			defaultPerson = new Person( model, "http://www.example.org#bot", true );
			defaultPerson.setName( "Socc Bot" );

			Authorization defaultAuthorization = new Authorization( model, true );
			defaultAuthorization.setOwner( defaultPerson );
			defaultAuthorization.setAgentClass( FoafVocabulary.Agent );
			defaultAuthorization.setAccessToClass( SiocVocabulary.Post );
			defaultAuthorization.addAccessMode( AclVocabulary.Read );
			defaultAuthorization.addAccessMode( AclVocabulary.Write );

			florianPerson = new Person( model, "http://www.example.org#florian", true );
			florianPerson.setName( "Florian" );

			Authorization florianAuthorization = new Authorization( model, true );
			florianAuthorization.setOwner( florianPerson );
			florianAuthorization.setAgentClass( FoafVocabulary.Agent );
			florianAuthorization.setAccessToClass( SiocVocabulary.Post );
			florianAuthorization.addAccessMode( AclVocabulary.Read );
			florianAuthorization.addAccessMode( AclVocabulary.Write );

			kaiPerson = new Person( model, "http://www.example.org#kai", true );
			kaiPerson.setName( "Kai" );

			Authorization kaiAuthorization = new Authorization( model, true );
			kaiAuthorization.setOwner( kaiPerson );
			kaiAuthorization.setAgentClass( FoafVocabulary.Agent );
			kaiAuthorization.setAccessToClass( SiocVocabulary.Post );
			kaiAuthorization.addAccessMode( AclVocabulary.Read );

			addMoodleRdfData( model );
			addCanvasLmsRdfData( model );
			addFacebookRdfData( model );
			addYoutubeRdfData( model );

			IConnector connector = ConnectorFactory.getInstance().createConnector(
			        context,
			        MOODLE_CONNECTOR_ID );

			// IConnector connector = DefaultConnector.createConnector(
			// context,
			// CANVAS_LMS_CONNECTOR_ID );

			// IConnector connector = DefaultConnector.createConnector(
			// context,
			// FACEBOOK_CONNECTOR_ID );

			//			IConnector connector = ConnectorFactory.getInstance()
			//			        .createConnector(
			//			                context,
			//			                YOUTUBE_CONNECTOR_ID );

			connector.initialize();

			IStructureReader<?> structureReader = connector.getStructureReader();
			IPostReader<?> postReader = connector.getPostReader();
			IPostWriter<?> postWriter = connector.getPostWriter();

			/**
			 * List<Container> forums = structureReader.listContainer(); for (
			 * Container forum : forums ) { System.out.println(
			 * RdfUtils.resourceToString( forum, Syntax.Turtle ) );
			 * 
			 * if ( structureReader.hasChildContainer( forum.getResource()
			 * .asURI() ) ) { List<Container> threads = structureReader
			 * .listContainer( forum.asURI() ); for ( Container thread : threads
			 * ) { System.out.println( RdfUtils.resourceToString( thread,
			 * Syntax.Turtle ) );
			 * 
			 * if ( postReader.hasPosts( thread.getResource().asURI() ) ) {
			 * List<Post> posts = postReader.pollPosts( thread .asURI(), null,
			 * -1 ); for ( Post post : posts ) { System.out.println(
			 * RdfUtils.resourceToString( post, Syntax.Turtle ) ); } } } } }
			 */

			Post post = new Post( model, true );
			post.setContent( "Hallo, welt <br>" );
			post.setCreator( kaiUserAccountMoodle );

			postWriter.writePost(
			        Builder.createURI( "http://localhost/moodle/mod/forum/discuss.php?d=2" ),
			        RdfUtils.resourceToString( post, Syntax.RdfXml ),
			        Syntax.RdfXml );

			// Container c = structureReader.getContainer(
			// Builder.createURI(
			// "https://www.facebook.com/19292868552" ) );
			// System.out.println( RdfUtils.resourceToString( c, Syntax.Turtle )
			// );
			// System.out.println(
			// "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<o>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
			// );
			// List<Post> posts = postReader.pollPosts( Builder.createURI(
			// "https://www.facebook.com/m0eper" ), null, 5 );
			// for ( Post post : posts ) {
			// System.out.println( RdfUtils.resourceToString( post,
			// Syntax.Turtle ) );
			// }

		} finally {
			System.err
			        .println(
			        "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<o>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" );
			System.err.println( RdfUtils.modelToString( model, Syntax.Turtle ) );
			model.close();
		}
	}

	public void addMoodleRdfData( final Model model ) {
		Service service = new Service( model, true );
		service.setServiceEndpoint( Builder.createURI( MOODLE_ROOT_URI ) );
		service.setName( "Moodle LMS Service" );

		/********************************************************/

		Username florianUsername = new Username( model, true );
		florianUsername.setValue( "florian" );

		Password florianPassword = new Password( model, true );
		florianPassword.setValue( "Flor1an.moodle" );

		Direct floriandirect = new Direct( model, true );
		floriandirect.addCredentials( florianUsername );
		floriandirect.addCredentials( florianPassword );

		florianUserAccountMoodle = new UserAccount( model, true );
		florianUserAccountMoodle.setAccountName( "6" );
		florianUserAccountMoodle.setAccountServiceHomepage( service
		        .getServiceEndpoint() );
		florianUserAccountMoodle.setAccountAuthentication( floriandirect );
		Thing.setService( model, florianUserAccountMoodle, service );
		florianPerson.addAccount( florianUserAccountMoodle );
		florianUserAccountMoodle.setAccountOf( florianPerson );

		/********************************************************/

		Username kaiUsername = new Username( model, true );
		kaiUsername.setValue( "kai" );

		Password kaiPassword = new Password( model, true );
		kaiPassword.setValue( "Ka1.moodle" );

		Direct kaidirect = new Direct( model, true );
		kaidirect.addCredentials( kaiUsername );
		kaidirect.addCredentials( kaiPassword );

		kaiUserAccountMoodle = new UserAccount( model, true );
		kaiUserAccountMoodle.setAccountName( "4" );
		kaiUserAccountMoodle.setAccountServiceHomepage( service.getServiceEndpoint() );
		kaiUserAccountMoodle.setAccountAuthentication( kaidirect );
		Thing.setService( model, kaiUserAccountMoodle, service );
		kaiPerson.addAccount( kaiUserAccountMoodle );
		kaiUserAccountMoodle.setAccountOf( kaiPerson );

		/********************************************************/

		Username defaultUsername = new Username( model, true );
		defaultUsername.setValue( "bot" );

		Password defaultPassword = new Password( model, true );
		defaultPassword.setValue( "B0t.moodle" );

		Direct defaultdirect = new Direct( model, true );
		defaultdirect.addCredentials( defaultUsername );
		defaultdirect.addCredentials( defaultPassword );

		defaultUserAccountMoodle = new UserAccount( model, true );
		defaultUserAccountMoodle.setAccountName( "5" );
		defaultUserAccountMoodle.setAccountServiceHomepage( service.getServiceEndpoint() );
		defaultUserAccountMoodle.setAccountAuthentication( defaultdirect );
		Thing.setService( model, defaultUserAccountMoodle, service );
		defaultPerson.addAccount( defaultUserAccountMoodle );
		defaultUserAccountMoodle.setAccountOf( defaultPerson );

		/********************************************************/

		ConnectorConfig config = new ConnectorConfig( model, true );
		config.setId( MOODLE_CONNECTOR_ID );
		config.setDefaultUserAccount( defaultUserAccountMoodle );
		config.setService( service );
		config.setConnectorClassName( Moodle2Connector.class.getName() );
	}

	public void addCanvasLmsRdfData( final Model model ) {

		Service service = new Service( model, true );
		service.setServiceEndpoint( Builder.createURI( CANVAS_LMS_ROOT_URI ) );
		service.setServiceDefinition( Builder
		        .createPlainliteral( "Canvas LMS Service" ) );

		/********************************************************/

		AccessToken defaultAccessToken = new AccessToken( model, true );
		defaultAccessToken
		        .setValue(
		        "7~wCpRKiFl91vrGdUHQ8gQFIVlVc9KiUe396TbAsfOXPMp6qWBUbqbjxAsKnDOZcc9" );

		OAuth defaultOAuth = new OAuth( model, true );
		defaultOAuth.addCredentials( defaultAccessToken );

		UserAccount defaultUserAccount = new UserAccount( model, true );
		defaultUserAccount.setAccountName( "3478501" );
		defaultUserAccount.setAccountServiceHomepage( service
		        .getServiceEndpoint() );
		defaultUserAccount.setAccountAuthentication( defaultOAuth );
		Thing.setService( model, defaultUserAccount, service );
		service.addServiceOf( defaultUserAccount );

		defaultPerson.addAccount( defaultUserAccount );
		defaultUserAccount.setAccountOf( defaultPerson );

		/********************************************************/

		AccessToken kaiAccessToken = new AccessToken( model, true );
		kaiAccessToken
		        .setValue(
		        "7~LUpV7B3lJYadvZ2sHlpJiTcyJ6HaduVb3Ho8YjBNXSdIE4AEFzLFfORcOHRHh1fU" );

		OAuth kaiOAuth = new OAuth( model, true );
		kaiOAuth.addCredentials( kaiAccessToken );

		UserAccount kaiUserAccount = new UserAccount( model, true );
		kaiUserAccount.setAccountName( "3451205" );
		kaiUserAccount.setAccountServiceHomepage( service.getServiceEndpoint() );
		kaiUserAccount.setAccountAuthentication( kaiOAuth );

		Thing.setService( model, kaiUserAccount, service );

		kaiPerson.addAccount( kaiUserAccount );
		kaiUserAccount.setAccountOf( kaiPerson );

		/********************************************************/

		AccessToken florianAccessToken = new AccessToken( model, true );
		florianAccessToken
		        .setValue(
		        "7~45nTvodrOtuaDNbsjParF4vMd4xqljGD76LLoeTVbxyg8d2ECHN55KEsL1045G5V" );

		OAuth florianOAuth = new OAuth( model, true );
		florianOAuth.addCredentials( florianAccessToken );

		UserAccount florianUserAccount = new UserAccount( model, true );
		florianUserAccount.setAccountName( "3457836" );
		florianUserAccount.setAccountServiceHomepage( service
		        .getServiceEndpoint() );
		florianUserAccount.setAccountAuthentication( florianOAuth );

		Thing.setService( model, florianUserAccount, service );

		florianPerson.addAccount( florianUserAccount );
		florianUserAccount.setAccountOf( florianPerson );

		/********************************************************/

		ConnectorConfig config = new ConnectorConfig( model, true );
		config.setId( CANVAS_LMS_CONNECTOR_ID );
		config.setDefaultUserAccount( defaultUserAccount );
		config.setService( service );
		config.setConnectorClassName( CanvasLmsConnector.class.getName() );
	}

	public void addFacebookRdfData( final Model model ) {
		Service service = new Service( model, true );
		service.setServiceEndpoint( Builder.createURI( FACEBOOK_ROOT_URI ) );

		OAuth serviceOAuth = new OAuth( model, true );
		service.setServiceAuthentication( serviceOAuth );

		ClientId serviceClientId = new ClientId( model, true );
		serviceClientId.setValue( "218182098322396" );
		serviceOAuth.addCredentials( serviceClientId );

		ClientSecret serviceClientSecret = new ClientSecret( model, true );
		serviceClientSecret.setValue( "f4ed27b621c0f6476c2741f7cf9c4dc5" );
		serviceOAuth.addCredentials( serviceClientSecret );

		/*********************************/

		UserAccount florianUserAccount = new UserAccount( model, true );
		florianUserAccount.setAccountName( "100000490230885" );
		florianUserAccount.setAccountServiceHomepage( service
		        .getServiceEndpoint() );

		florianPerson.addAccount( florianUserAccount );
		florianUserAccount.setAccountOf( florianPerson );

		OAuth florianOAuth = new OAuth( model, true );
		florianUserAccount.setAccountAuthentication( florianOAuth );

		AccessToken florianAccessToken = new AccessToken( model, true );
		florianAccessToken
		        .setValue( "CAADGb3p3g9wBANQpIOfeJjlO6WcgyILAE39y6YMECzuiM6xJB3HG5oAMetr0nQ3FdRfh"
		                + "ysngrNmRysSVAcPQiWPPXd2q9ZBZBNf7kqd4IQnyg1BRZAEvABiUhpkxFlAMhVgn44J2mFN"
		                + "pkgdDgUT" );
		florianOAuth.addCredentials( florianAccessToken );

		/*********************************/

		ConnectorConfig config = new ConnectorConfig( model, true );
		config.setId( FACEBOOK_CONNECTOR_ID );
		config.setDefaultUserAccount( florianUserAccount );
		config.setService( service );
		config.setConnectorClassName( FacebookConnector.class.getName() );
	}

	public void addYoutubeRdfData( final Model model ) {

		Service service = new Service( model, true );
		service.setServiceEndpoint( Builder.createURI( YOUTUBE_ROOT_URI ) );

		WebAPI serviceAuth = new WebAPI( model, true );
		service.setServiceAuthentication( serviceAuth );

		APIKey apiKey = new APIKey( model, true );
		apiKey.setValue( "AI39si48dEjhAE9RrY6w1HnlmyrUUTDt-xssOKkEEcpOIMD1gFcQ-0Xv40YNl-H1MxFzGz"
		        + "bHih4ootWo1cRrPH9gV-5UdazEbQ" );
		serviceAuth.addCredentials( apiKey );

		/*********************************/

		UserAccount defaultUserAccount = new UserAccount( model, true );
		defaultUserAccount.setAccountName( "qX2si9NbFXXGS14BWC1juw" );
		defaultUserAccount.setAccountServiceHomepage( service
		        .getServiceEndpoint() );

		Thing.setService( model, defaultUserAccount, service );
		service.addServiceOf( defaultUserAccount );

		defaultPerson.addAccount( defaultUserAccount );
		defaultUserAccount.setAccountOf( defaultPerson );

		Direct defaultDirect = new Direct( model, true );
		defaultUserAccount.setAccountAuthentication( defaultDirect );

		Username defaultUsername = new Username( model, true );
		defaultUsername.setValue( "tkhiwis@gmail.com" );
		defaultDirect.addCredentials( defaultUsername );

		Password defaultPassword = new Password( model, true );
		defaultPassword.setValue( "turing123" );
		defaultDirect.addCredentials( defaultPassword );

		/*********************************/

		UserAccount florianUserAccount = new UserAccount( model, YoutubeSiocUtils
		        .createUserUri( "m0eper" ), true );
		florianUserAccount.setAccountName( "m0eper" );
		florianUserAccount.setAccountServiceHomepage( service
		        .getServiceEndpoint() );

		Thing.setService( model, florianUserAccount, service );
		service.addServiceOf( florianUserAccount );

		florianPerson.addAccount( florianUserAccount );
		florianUserAccount.setAccountOf( florianPerson );

		Direct florianDirect = new Direct( model, true );
		florianUserAccount.setAccountAuthentication( florianDirect );

		Username florianUsername = new Username( model, true );
		florianUsername.setValue( "email.mufl@gmail.com" );
		florianDirect.addCredentials( florianUsername );

		Password florianPassword = new Password( model, true );
		florianPassword.setValue( "email.mufl.net" );
		florianDirect.addCredentials( florianPassword );

		/*********************************/

		ConnectorConfig config = new ConnectorConfig( model, true );
		config.setId( YOUTUBE_CONNECTOR_ID );
		config.setDefaultUserAccount( defaultUserAccount );
		config.setService( service );
		config.setConnectorClassName( YoutubeConnector.class.getName() );
	}
}
