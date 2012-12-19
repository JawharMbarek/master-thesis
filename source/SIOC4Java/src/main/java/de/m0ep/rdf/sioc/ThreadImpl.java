package de.m0ep.rdf.sioc;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;

import de.m0ep.rdf.sioc.vocabulary.SIOC;

public class ThreadImpl extends ContainerImpl implements Thread {
    protected ThreadImpl( final Resource resource, final Model model ) {
        super( resource, model );
    }

    public static ThreadImpl get( final Resource resource, final Model model ) {
        return new ThreadImpl( resource, model );
    }

    public static ThreadImpl create( final String uri, final Model model ) {
        return create( model.createResource( uri ), model );
    }

    public static ThreadImpl create( final Resource resource, final Model model ) {
        ThreadImpl impl = new ThreadImpl( resource, model );

        if( !impl.isRDFType( SIOC.Thread ) )
            impl.resource().addProperty( RDF.type, SIOC.Thread );

        // Set superclass type
        if( !impl.isRDFType( SIOC.Container ) )
            impl.resource().addProperty( RDF.type, SIOC.Container );

        return impl;
    }
}
