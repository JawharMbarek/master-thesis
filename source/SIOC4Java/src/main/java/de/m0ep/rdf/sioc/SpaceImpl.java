package de.m0ep.rdf.sioc;

import java.util.Set;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;

import de.m0ep.rdf.sioc.vocabulary.SIOC;

public class SpaceImpl extends SIOCBaseImpl implements Space {

    protected SpaceImpl( final Resource resource, final Model model ) {
        super( resource, model );
    }

    public static SpaceImpl get( final Resource resource, final Model model ) {
        return new SpaceImpl( resource, model );
    }

    public static SpaceImpl create( final String uri, final Model model ) {
        return create( model.createResource( uri ), model );
    }

    public static SpaceImpl create( final Resource resource, final Model model ) {
        SpaceImpl impl = new SpaceImpl( resource, model );

        if( !impl.isRDFType( SIOC.Space ) )
            impl.resource().addProperty( RDF.type, SIOC.Space );

        return impl;
    }

    public Set<Usergroup> getUsergroups() {
        // TODO Auto-generated method stub
        return null;
    }

    public void addUsergroup( Usergroup group ) {
        // TODO Auto-generated method stub

    }

    public void removeUsergroup( Usergroup group ) {
        // TODO Auto-generated method stub

    }

    public Resource getSpaceOf() {
        // TODO Auto-generated method stub
        return null;
    }

    public void addSpaceOf( Resource resource ) {
        // TODO Auto-generated method stub

    }

    public void removeSpaceOf( Resource resource ) {
        // TODO Auto-generated method stub

    }

}
