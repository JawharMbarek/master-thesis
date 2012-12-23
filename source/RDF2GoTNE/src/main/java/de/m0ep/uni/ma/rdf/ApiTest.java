package de.m0ep.uni.ma.rdf;

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.ontoware.rdf2go.RDF2Go;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.Statement;
import org.ontoware.rdf2go.model.node.Variable;

import de.m0ep.uni.ma.rdf.sioc.Post;
import de.m0ep.uni.ma.rdf.sioc.UserAccount;
import de.m0ep.uni.ma.rdf.vocabularies.DCTerms;

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

        Post post2 = new Post( model, NS + "test2", true );
        post2.setSIOCContent( "Hallo, World!" );
        post2.setSIOCCreator( user );
        post2.setSIOCTitle( model.createPlainLiteral( "Testpost2" ) );

        post2.setSIOCReplyof( post );
        post.addSIOCReply( post2 );

        model.addStatement( post.asResource(), DCTerms.title,
                model.createPlainLiteral( "Testpost" ) );
        model.addStatement( post.asResource(), DCTerms.title,
                model.createPlainLiteral( "Testpost" ) );
        model.addStatement( post.asResource(), DCTerms.title,
                model.createPlainLiteral( "Testpost" ) );


        ClosableIterator<Statement> result = model.findStatements(
                Variable.ANY, Variable.ANY, Variable.ANY );

        while ( result.hasNext() ) {
            Statement next = result.next();

            System.out.println( next );
        }

        // model.dump();
    }

}
