package de.m0ep.test.socc.core;

import java.io.IOException;
import java.util.List;

import org.ontoware.rdf2go.RDF2Go;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.Syntax;
import org.ontoware.rdf2go.util.Builder;
import org.purl.dc.terms.DCTermsVocabulary;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.SIOCVocabulary;

import com.xmlns.foaf.FOAFVocabulary;
import com.xmlns.foaf.Person;

import de.m0ep.sioc.services.auth.Direct;
import de.m0ep.sioc.services.auth.Password;
import de.m0ep.sioc.services.auth.Service;
import de.m0ep.sioc.services.auth.UserAccount;
import de.m0ep.sioc.services.auth.Username;
import de.m0ep.socc.config.ConnectorConfig;
import de.m0ep.socc.core.SoccContext;
import de.m0ep.socc.core.connector.DefaultConnector;
import de.m0ep.socc.core.connector.IConnector;
import de.m0ep.socc.core.connector.IConnector.IPostReader;
import de.m0ep.socc.core.connector.IConnector.IStructureReader;
import de.m0ep.socc.core.connector.moodle.Moodle2Connector;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.utils.RdfUtils;

public class ConnectorTestApp {

	private static final String MOODLE_ROOT_URI = "http://localhost/moodle";
	private static final String MOODLE_CONNECTOR_ID = "moodle-test";
	private Person florianPerson;
	private Person defaultPerson;
	private Person kaiPerson;

	public static void main( String[] args ) throws
	        AuthenticationException,
	        IOException {
		ConnectorTestApp app = new ConnectorTestApp();
		app.run();
	}

	public void run() throws AuthenticationException, IOException {

		Model model = RDF2Go.getModelFactory().createModel();
		model.open();
		model.setNamespace( "sioc", SIOCVocabulary.NS_SIOC.toString() );
		model.setNamespace( "foaf", FOAFVocabulary.NS_FOAF.toString() );
		model.setNamespace( "dcterms", DCTermsVocabulary.NS_DCTerms.toString() );

		try {
			SoccContext context = new SoccContext( model );

			defaultPerson = new Person( model, true );
			defaultPerson.setName( "Socc Bot" );
			florianPerson = new Person( model, true );
			florianPerson.setName( "Florian" );
			kaiPerson = new Person( model, true );
			kaiPerson.setName( "Kai" );

			addMoodleRdfData( model );

			IConnector connector = DefaultConnector.createConnector( context, MOODLE_CONNECTOR_ID );
			connector.initialize();

			IStructureReader<Moodle2Connector> structureReader = connector.getStructureReader();
			IPostReader<Moodle2Connector> postReader = connector.getPostReader();
			// IPostWriter<Moodle2Connector> postWriter = connector.getPostWriter();

			List<Container> forums = structureReader.listContainer();
			for ( Container forum : forums ) {
				System.out.println( RdfUtils.resourceToString( forum, Syntax.Turtle ) );

				List<Container> threads = structureReader.listContainer( forum.asURI() );
				for ( Container thread : threads ) {
					System.out.println( RdfUtils.resourceToString( thread, Syntax.Turtle ) );

					List<Post> posts = postReader.pollPosts( thread.asURI(), null, -1 );
					for ( Post post : posts ) {
						System.out.println( RdfUtils.resourceToString( post, Syntax.Turtle ) );
					}
				}
			}
		} finally {
			//System.out.println( RdfUtils.modelToString( model, Syntax.Turtle ) );
			model.close();
		}
	}

	private void addMoodleRdfData( final Model model ) {
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

		UserAccount florianUserAccount = new UserAccount( model, true );
		florianUserAccount.setAccountName( "6" );
		florianUserAccount.setAccountServiceHomepage( service.getServiceEndpoint() );
		florianUserAccount.setAccountAuthentication( floriandirect );
		florianPerson.addAccount( florianUserAccount );
		florianUserAccount.setAccountOf( florianPerson );

		/********************************************************/

		Username kaiUsername = new Username( model, true );
		kaiUsername.setValue( "kai" );

		Password kaiPassword = new Password( model, true );
		kaiPassword.setValue( "Ka1.moodle" );

		Direct kaidirect = new Direct( model, true );
		kaidirect.addCredentials( kaiUsername );
		kaidirect.addCredentials( kaiPassword );

		UserAccount kaiUserAccount = new UserAccount( model, true );
		kaiUserAccount.setAccountName( "4" );
		kaiUserAccount.setAccountServiceHomepage( service.getServiceEndpoint() );
		kaiUserAccount.setAccountAuthentication( kaidirect );
		kaiPerson.addAccount( kaiUserAccount );
		kaiUserAccount.setAccountOf( kaiPerson );

		/********************************************************/

		Username defaultUsername = new Username( model, true );
		defaultUsername.setValue( "bot" );

		Password defaultPassword = new Password( model, true );
		defaultPassword.setValue( "B0t.moodle" );

		Direct defaultdirect = new Direct( model, true );
		defaultdirect.addCredentials( defaultUsername );
		defaultdirect.addCredentials( defaultPassword );

		UserAccount defaultUserAccount = new UserAccount( model, true );
		defaultUserAccount.setAccountName( "5" );
		defaultUserAccount.setAccountServiceHomepage( service.getServiceEndpoint() );
		defaultUserAccount.setAccountAuthentication( defaultdirect );
		defaultPerson.addAccount( defaultUserAccount );
		defaultUserAccount.setAccountOf( defaultPerson );

		/********************************************************/

		ConnectorConfig config = new ConnectorConfig( model, true );
		config.setId( MOODLE_CONNECTOR_ID );
		config.setDefaultUserAccount( defaultUserAccount );
		config.setService( service );
		config.setConnectorClassName( Moodle2Connector.class.getName() );
	}
}
