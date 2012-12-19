package de.m0ep.rdf.sioc;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;

import de.m0ep.rdf.foaf.vocabulary.FOAF;
import de.m0ep.rdf.sioc.vocabulary.SIOC;

public class PostImpl extends ItemImpl implements Post {

    protected PostImpl( final Resource resource, final Model model ) {
        super( resource, model );
    }

    public static PostImpl get( final Resource resource, final Model model ) {
        return new PostImpl( resource, model );
    }

    public static PostImpl create( final String uri, final Model model ) {
        return create( model.createResource( uri ), model );
    }

    public static PostImpl create( final Resource resource, final Model model ) {
        PostImpl impl = new PostImpl( resource, model );

        if( !impl.isRDFType( SIOC.Post ) )
            impl.resource().addProperty( RDF.type, SIOC.Post );

        // set superclass type
        if( !impl.isRDFType( SIOC.Item ) )
            impl.resource().addProperty( RDF.type, SIOC.Item );

        if( !impl.isRDFType( FOAF.Document ) )
            impl.resource().addProperty( RDF.type, FOAF.Document );

        return impl;
    }

    public Post getRelatedTo() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setRelatedTo( Post post ) {
        // TODO Auto-generated method stub

    }

}
