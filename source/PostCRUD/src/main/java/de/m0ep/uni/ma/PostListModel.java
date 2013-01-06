package de.m0ep.uni.ma;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.AbstractListModel;

import org.ontoware.rdf2go.model.DiffReader;
import org.ontoware.rdf2go.model.ModelChangedListener;
import org.ontoware.rdf2go.model.Statement;
import org.ontoware.rdf2go.model.impl.NotifyingModelLayer;
import org.ontoware.rdf2go.vocabulary.RDF;

import com.google.common.base.Preconditions;

import de.m0ep.uni.ma.rdf.vocabularies.SIOC;

public class PostListModel extends AbstractListModel implements
        ModelChangedListener {

    /**
     * 
     */
    private static final long serialVersionUID = -5584603361542370798L;
    ArrayList<String> array;
    NotifyingModelLayer model;

    public PostListModel( NotifyingModelLayer model ) {
        Preconditions.checkNotNull( model );

        this.model = model;
        this.model.addModelChangedListener( this );
    }

    private ArrayList<String> getArray() {
        if( null == array )
            array = new ArrayList<String>();

        return array;
    }

    public void add( String uri ) {
        if( !getArray().contains( uri ) ) {
            getArray().add( uri );
            int index = getArray().indexOf( uri );
            System.out.println( "add " + uri + " at " + index + " to list" );
            fireIntervalAdded( uri, index, index );
        }
    }

    public void remove( String uri ) {
        if( getArray().contains( uri ) ) {
            int index = getArray().indexOf( uri );
            System.out.println( "remove " + uri + " at " + index + " to list" );
            getArray().remove( index );
            fireIntervalRemoved( uri, index, index );
        }
    }

    @Override
    public int getSize() {
        return getArray().size();
    }

    @Override
    public String getElementAt( int index ) {
        return getArray().get( index );
    }

    @Override
    public void addedStatement( Statement arg ) {
        if( arg.getPredicate().equals( RDF.type )
                && arg.getObject().equals( SIOC.Post ) ) {
            System.out.println( "added post " + arg.getSubject() + " to model" );
            add( arg.getSubject().asURI().toString() );
        }
    }

    @Override
    public void addedStatements( Iterator<? extends Statement> arg ) {
        while ( arg.hasNext() ) {
            Statement statement = (Statement) arg.next();
            addedStatement( statement );
        }
    }

    @Override
    public void performedUpdate( DiffReader arg ) {
    }

    @Override
    public void removedStatement( Statement arg ) {
        if( arg.getPredicate().equals( RDF.type )
                && arg.getObject().equals( SIOC.Post ) ) {
            System.out.println( "removed post " + arg.getSubject()
                    + " from model" );
            remove( arg.getSubject().asURI().toString() );
        }
    }

    @Override
    public void removedStatements( Iterator<? extends Statement> arg ) {
        while ( arg.hasNext() ) {
            Statement statement = (Statement) arg.next();
            removedStatement( statement );
        }
    }

}
