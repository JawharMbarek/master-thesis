package de.m0ep.uni.ma.rdf;

import java.util.Date;
import java.util.Iterator;

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.ontoware.rdf2go.RDF2Go;
import org.ontoware.rdf2go.model.DiffReader;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.ModelChangedListener;
import org.ontoware.rdf2go.model.Statement;
import org.ontoware.rdf2go.model.impl.NotifyingModelLayer;
import org.ontoware.rdf2go.model.node.Variable;
import org.ontoware.rdf2go.util.RDFTool;

import de.m0ep.uni.ma.rdf.sioc.Post;
import de.m0ep.uni.ma.rdf.sioc.UserAccount;
import de.m0ep.uni.ma.rdf.vocabularies.DCTerms;

public class ApiTest {

    public static final String NS = "http://m0ep.de/uni/ma/";

    /**
     * @param args
     */
    public static void main( String[] args ) {
        Model _model = RDF2Go.getModelFactory().createModel();
        NotifyingModelLayer model = new NotifyingModelLayer( _model );
        model.addModelChangedListener( new ModelChangedListener() {

            @Override
            public void removedStatements( Iterator<? extends Statement> arg0 ) {
                System.out.println( "removed:" );
                while ( arg0.hasNext() ) {
                    Statement s = arg0.next();
                    System.out.println( "\t" + s );
                }

            }

            @Override
            public void removedStatement( Statement arg0 ) {
                System.out.println( "removed: " + arg0 );
            }

            @Override
            public void performedUpdate( DiffReader arg0 ) {
                System.out.println( "uptaded:" );
                System.out.println( "r\temoved:" );
                while ( arg0.getRemoved().iterator().hasNext() ) {
                    Statement s = arg0.getRemoved().iterator().next();
                    System.out.println( "\t\t" + s );
                }

                System.out.println( "\tremoved:" );
                while ( arg0.getAdded().iterator().hasNext() ) {
                    Statement s = arg0.getAdded().iterator().next();
                    System.out.println( "\t\t" + s );
                }
            }

            @Override
            public void addedStatements( Iterator<? extends Statement> arg0 ) {
                System.out.println( "added:" );
                while ( arg0.hasNext() ) {
                    Statement s = arg0.next();
                    System.out.println( "\t" + s );
                }
            }

            @Override
            public void addedStatement( Statement arg0 ) {
                System.out.println( "added: " + arg0 );
            }
        } );

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
        post.setDCTermsTitle( model.createPlainLiteral( "Testpost" ) );
        post.setDCTermsCreated( RDFTool.dateTime2String( new Date() ) );

        Post post2 = new Post( model, NS + "test2", true );
        post2.setSIOCContent( "Hallo, World!" );
        post2.setSIOCCreator( user );
        post2.setDCTermsTitle( model.createPlainLiteral( "Testpost2" ) );
        post2.setDCTermsCreated( RDFTool.dateTime2String( new Date() ) );

        post2.setSIOCReplyof( post );
        post.addSIOCReply( post2 );

        model.addStatement( post.asResource(), DCTerms.title,
                model.createPlainLiteral( "Testpost" ) );
        model.addStatement( post.asResource(), DCTerms.title,
                model.createPlainLiteral( "Testpost" ) );
        model.addStatement( post.asResource(), DCTerms.title,
                model.createPlainLiteral( "Testpost" ) );
        post.setDCTermsTitle( model.createPlainLiteral( "Updated Title" ) );



        ClosableIterator<Statement> result = model.findStatements(
                Variable.ANY, Variable.ANY, Variable.ANY );

        while ( result.hasNext() ) {
            Statement next = result.next();

            System.out.println( next );
        }

        // model.dump();
    }

    public ApiTest( final Model model ) {

    }

    public void creatGUI() {

    }

}
