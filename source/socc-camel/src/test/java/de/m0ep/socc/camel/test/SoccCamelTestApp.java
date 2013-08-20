package de.m0ep.socc.camel.test;

import java.io.IOException;

import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.RouteDefinition;
import org.ontoware.rdf2go.RDF2Go;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.util.Builder;
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
        Model model = RDF2Go.getModelFactory().createModel();
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
            }
        } ) );

        try {
            camelContext.start();
        } catch ( Exception e ) {
            LOG.debug( e.getMessage(), e );
            return;
        }

        RouteDefinition rd = new RouteDefinition()
                .from( "socc://canvas-test/798152/1424406?delay=5000" )
                .to( "socc://canvas-test/798152/1440785" );
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

        ConnectorConfig config = new ConnectorConfig( model, true );
        config.setId( "canvas-test" );
        config.setDefaultUserAccount( defaultUserAccount );
        config.setService( service );
        config.setConnectorClass( CanvasLmsConnector.class.getName() );
    }
}
