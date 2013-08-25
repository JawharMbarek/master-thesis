package de.m0ep.socc.camel.test;

import java.io.IOException;

import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.RouteDefinition;
import org.ontoware.rdf2go.RDF2Go;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.Syntax;
import org.ontoware.rdf2go.util.Builder;
import org.ontoware.rdf2go.util.RDFTool;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.Site;
import org.rdfs.sioc.services.Thing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xmlns.foaf.Person;

import de.m0ep.sioc.services.auth.AccessToken;
import de.m0ep.sioc.services.auth.OAuth;
import de.m0ep.sioc.services.auth.Service;
import de.m0ep.sioc.services.auth.UserAccount;
import de.m0ep.socc.camel.SoccComponent;
import de.m0ep.socc.config.ConnectorConfig;
import de.m0ep.socc.core.ISoccContext;
import de.m0ep.socc.core.SoccContext;
import de.m0ep.socc.core.connector.canvaslms.CanvasLmsConnector;
import de.m0ep.socc.core.exceptions.AuthenticationException;

public class SoccCamelTestApp {
	private static final Logger LOG = LoggerFactory.getLogger( SoccCamelTestApp.class );

	/**
	 * @param args
	 * @throws IOException
	 * @throws AuthenticationException
	 */
	public static void main( String[] args ) throws AuthenticationException, IOException {
		final Model model = RDF2Go.getModelFactory().createModel();
		model.open();
		initModel( model );

		final ISoccContext soccContext = new SoccContext( model );
		final DefaultCamelContext camelContext = new DefaultCamelContext();
		camelContext.addComponent( "socc", new SoccComponent( camelContext, soccContext ) );

		Runtime.getRuntime().addShutdownHook( new Thread( new Runnable() {
			@Override
			public void run() {
				if ( null != camelContext && camelContext.isStarted() ) {
					try {
						camelContext.stop();
					} catch ( Exception e ) {
						LOG.debug( e.getMessage(), e );
					}
				}

				System.out.println( RDFTool.modelToString( model, Syntax.Turtle ) );
				model.close();
			}
		} ) );

		try {
			camelContext.start();
		} catch ( Exception e ) {
			LOG.debug( e.getMessage(), e );
			return;
		}

		RouteDefinition rd = new RouteDefinition()
		        .from( "socc://canvas-test/798152/1440784?delay=10000" )
		        .to( "socc://canvas-test/798152/1440783" );
		try {
			camelContext.addRouteDefinition( rd );
		} catch ( Exception e ) {
			LOG.debug( e.getMessage(), e );
		}

		try {
			System.in.read();
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}

	private static void initModel( Model model ) {
		String rootUri = "https://canvas.instructure.com";

		// florian.mueller@stud.tu-darmstadt.de
		// String oAuthToken =
		// ;
		Service service = new Service( model, true );
		service.setServiceEndpoint( Builder.createURI( rootUri ) );
		service.setServiceDefinition( Builder
		        .createPlainliteral( "Canvas LMS Service" ) );

		AccessToken defaultAccessToken = new AccessToken( model, true );
		defaultAccessToken.setValue(
		        "7~wCpRKiFl91vrGdUHQ8gQFIVlVc9KiUe396TbAsfOXPMp6qWBUbqbjxAsKnDOZcc9" );

		OAuth defaultOAuth = new OAuth( model, true );
		defaultOAuth.addCredentials( defaultAccessToken );

		UserAccount defaultUserAccount = new UserAccount( model, true );
		defaultUserAccount.setAccountName( "3478501" );
		defaultUserAccount.setAccountServiceHomepage( service.getServiceEndpoint() );
		defaultUserAccount.setAccountAuthentication( defaultOAuth );
		Thing.setService( model, defaultUserAccount, service );

		Person defaultPerson = new Person( model, true );
		defaultPerson.setName( "SOCC-Bot" );
		defaultPerson.addAccount( defaultUserAccount );
		defaultUserAccount.setAccountOf( defaultPerson );

		AccessToken accessToken = new AccessToken( model, true );
		accessToken.setValue(
		        "7~LUpV7B3lJYadvZ2sHlpJiTcyJ6HaduVb3Ho8YjBNXSdIE4AEFzLFfORcOHRHh1fU" );

		OAuth oAuth = new OAuth( model, true );
		oAuth.addCredentials( accessToken );

		UserAccount userAccount = new UserAccount( model, true );
		userAccount.setAccountName( "3451205" );
		userAccount.setAccountServiceHomepage( service.getServiceEndpoint() );
		userAccount.setAccountAuthentication( oAuth );
		Thing.setService( model, userAccount, service );

		Person person = new Person( model, true );
		person.setName( "Kai" );
		person.addAccount( userAccount );
		userAccount.setAccountOf( person );

		AccessToken accessToken2 = new AccessToken( model, true );
		accessToken2.setValue(
		        "7~45nTvodrOtuaDNbsjParF4vMd4xqljGD76LLoeTVbxyg8d2ECHN55KEsL1045G5V" );

		OAuth oAuth2 = new OAuth( model, true );
		oAuth2.addCredentials( accessToken2 );

		UserAccount userAccount2 = new UserAccount( model, true );
		userAccount2.setAccountName( "3457836" );
		userAccount2.setAccountServiceHomepage( service.getServiceEndpoint() );
		userAccount2.setAccountAuthentication( oAuth2 );
		Thing.setService( model, userAccount2, service );

		Person person2 = new Person( model, true );
		person2.setName( "Florian" );
		person2.addAccount( userAccount2 );
		userAccount2.setAccountOf( person2 );

		ConnectorConfig config = new ConnectorConfig( model, true );
		config.setId( "canvas-test" );
		config.setDefaultUserAccount( defaultUserAccount );
		config.setService( service );
		config.setConnectorClassName( CanvasLmsConnector.class.getName() );

		Site site = new Site( model, service.getServiceEndpoint(), true );

		Forum forum = new Forum(
		        model,
		        "https://canvas.instructure.com/courses/798152",
		        true );
		forum.setId( "798152" );
		site.setHostOf( forum );
		forum.setHost( site );

		org.rdfs.sioc.Thread thread = new org.rdfs.sioc.Thread(
		        model,
		        "https://canvas.instructure.com/courses/798152/discussion_topics/1440785",
		        true );
		thread.setId( "1440785" );
		forum.setParentOf( thread );
		thread.setParent( forum );

		Post post = new Post(
		        model,
		        "https://canvas.instructure.com/courses/798152/discussion_topics/1440785/entries/3167309",
		        true );
		post.setId( "3167309" );
		thread.setContainerOf( post );
		post.setContainer( thread );
	}
}
