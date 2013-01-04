package de.m0ep.uni.ma.rdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map.Entry;

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.ontoware.rdf2go.RDF2Go;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.Statement;
import org.ontoware.rdf2go.model.Syntax;
import org.ontoware.rdf2go.model.node.Resource;
import org.ontoware.rdf2go.model.node.Variable;

public class ResourceIO {

    public static void write( final Model model, final File outFile,
            final Syntax syntax, final Resource... resources )
            throws IOException {
        write( model, new FileOutputStream( outFile ), syntax, resources );
    }

    public static void write( final Model model, final String outFile,
            final Syntax syntax, final Resource... resources )
            throws IOException {
        write( model, new FileOutputStream( outFile ), syntax, resources );
    }

    public static void write( final Model model, final OutputStream out,
            final Syntax syntax, final Resource... resources )
            throws IOException {

        Model outModel = RDF2Go.getModelFactory().createModel();
        outModel.open();
        
        model.lock();

        // Copy namespaces
        for (  Entry<String, String> entry : model.getNamespaces().entrySet() ) {
            outModel.setNamespace( entry.getKey(), entry.getValue() );
        }
        
        for ( Resource resource : resources ) {
            ClosableIterator<Statement> iter = model.findStatements( resource,
                    Variable.ANY, Variable.ANY );
            
            while ( iter.hasNext() ) {
                Statement statement = (Statement) iter.next();
                outModel.addStatement( statement );
            }
            iter.close();
        }
        model.unlock();

        outModel.writeTo( out, syntax );
        outModel.close();
    }

    public static void read( Model model, final String inFile )
            throws IOException {
        read( model, new File( inFile ) );

    }

    public static void read( Model model, final File file ) throws IOException {
        read( model, new FileInputStream( file ) );
    }

    public static void read( Model model, final FileInputStream in )
            throws IOException {
        Model inModel = RDF2Go.getModelFactory().createModel();
        inModel.open();

        model.lock();

        inModel.readFrom( in );
        for ( Statement statement : inModel ) {
            model.addStatement( statement );
        }

        model.unlock();
        inModel.close();
    }
}
