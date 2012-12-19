package de.m0ep.rdf.sioc;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;

import de.m0ep.rdf.sioc.vocabulary.SIOC;

public class CommunityImpl extends SIOCBaseImpl implements Community {

    protected CommunityImpl( final Resource resource, final Model model ) {
        super( resource, model );
    }

    public static CommunityImpl get( final Resource resource, final Model model ) {
        return new CommunityImpl( resource, model );
    }

    public static CommunityImpl create( final String uri, final Model model ) {
        return create( model.createResource( uri ), model );
    }

    public static CommunityImpl create( final Resource resource,
            final Model model ) {
        CommunityImpl impl = new CommunityImpl( resource, model );

        if( !impl.isRDFType( SIOC.Community ) )
            impl.resource().addProperty( RDF.type, SIOC.Community );

        return impl;
    }
}
