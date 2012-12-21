package de.m0ep.uni.ma.rdf;

import java.io.IOException;

import org.ontoware.rdf2go.RDF2Go;
import org.ontoware.rdf2go.exception.ModelRuntimeException;
import org.ontoware.rdf2go.model.Model;

import de.m0ep.uni.ma.rdf.sioc.Post;
import de.m0ep.uni.ma.rdf.sioc.UserAccount;

public class ApiTest {

    public static final String NS = "http://m0ep.de/uni/ma/";

    /**
     * @param args
     */
    public static void main( String[] args ) {
        Model model = RDF2Go.getModelFactory().createModel();
        model.open();
        model.setNamespace( "sioc", "http://rdfs.org/sioc/ns#" );
        model.setNamespace( "foaf", "http://xmlns.com/foaf/0.1/" );
        model.setNamespace( "dcterm", "http://purl.org/dc/terms/" );

        UserAccount user = new UserAccount( model, NS + "user1", true );
        user.setSIOCName( "Florian Müller" );
        user.setSIOCId( "1" );
        user.setFOAFNickname( model.createPlainLiteral( "m0ep" ) );

        Post post = new Post( model, NS + "test", true );
        post.setSIOCContent( "Hallo, Welt!" );
        post.setSIOCCreator( user );
        post.setSIOCTitle( model.createPlainLiteral( "Testpost" ) );


        try {
            model.writeTo( System.out );
        } catch ( ModelRuntimeException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch ( IOException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
