package de.m0ep.test.socc.core;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.ontoware.rdf2go.RDF2Go;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.Statement;
import org.ontoware.rdf2go.model.Syntax;
import org.ontoware.rdf2go.util.Builder;
import org.ontoware.rdf2go.util.RDFTool;
import org.rdfs.sioc.Container;

import com.google.common.collect.Lists;
import com.xmlns.foaf.Person;

import de.m0ep.sioc.services.auth.Direct;
import de.m0ep.sioc.services.auth.Password;
import de.m0ep.sioc.services.auth.Service;
import de.m0ep.sioc.services.auth.UserAccount;
import de.m0ep.sioc.services.auth.Username;
import de.m0ep.socc.config.ConnectorConfig;
import de.m0ep.socc.core.SoccContext;
import de.m0ep.socc.core.connector.IConnector.IStructureReader;
import de.m0ep.socc.core.connector.moodle.Moodle2Connector;
import de.m0ep.socc.core.exceptions.AuthenticationException;

public class Moodle2ConnectorTestApp {

	public static void main( String[] args ) throws AuthenticationException,
	        IOException {
		String rootUri = "http://localhost/moodle";

		Model model = RDF2Go.getModelFactory().createModel();
		model.open();
		SoccContext context = new SoccContext( model );

		Username username = new Username( model, true );
		username.setValue( "admin" );

		Password password = new Password( model, true );
		password.setValue( "Admin1.mufl.net" );

		Direct direct = new Direct( model, true );
		direct.addCredentials( username );
		direct.addCredentials( password );

		UserAccount defaultUserAccount = new UserAccount( model, true );
		defaultUserAccount.setAccountName( "2" );
		defaultUserAccount
		        .setAccountServiceHomepage( Builder.createURI( rootUri ) );
		defaultUserAccount.setAccountAuthentication( direct );

		Person defaultPerson = new Person( model, true );
		defaultPerson.setName( "Admin User" );
		defaultPerson.addAccount( defaultUserAccount );
		defaultUserAccount.setAccountOf( defaultPerson );

		Service service = new Service( model, true );
		service.setServiceEndpoint( Builder.createURI( rootUri ) );
		service.setName( "Moodle LMS Service" );

		ConnectorConfig config = new ConnectorConfig( model, true );
		config.setId( "moodle-test" );
		config.setDefaultUserAccount( defaultUserAccount );
		config.setService( service );

		Moodle2Connector connector = new Moodle2Connector( context, config );

		try {
			connector.initialize();
		} catch ( AuthenticationException | IOException e ) {
			e.printStackTrace();
			return;
		}

		IStructureReader<Moodle2Connector> structureReader = connector.getStructureReader();

		List<Container> forums = structureReader.listContainer();
		for ( Container forum : forums ) {
			System.out.println( "URI : " + forum );
			System.out.println( "ID  : " + forum.getId() );
			System.out.println( "Name: " + forum.getName() );
			System.out.println( "----------------------------" );

			List<Container> threads = structureReader.listContainer( forum.asURI() );
			System.err.println( threads.size() );
			for ( Container thread : threads ) {
				System.out.println( "\tURI : " + thread );
				System.out.println( "\tID  : " + thread.getId() );
				System.out.println( "\tName: " + thread.getName() );
				System.out.println( "\t----------------------------" );
			}
		}

		printModel( model );
		model.close();
	}

	private static void printModel( Model model ) {
		List<Statement> sortedStmts = Lists.newArrayList( model );
		Collections.sort( sortedStmts );

		Model writeModel = RDF2Go.getModelFactory().createModel();
		writeModel.open();
		writeModel.addAll( sortedStmts.iterator() );

		System.out.println( RDFTool.modelToString( writeModel, Syntax.RdfXml ) );
		writeModel.close();
	}

}
