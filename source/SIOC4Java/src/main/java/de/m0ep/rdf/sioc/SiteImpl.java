package de.m0ep.rdf.sioc;

import java.util.Set;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;

import de.m0ep.rdf.sioc.vocabulary.SIOC;

public class SiteImpl extends SpaceImpl implements Site {

    protected SiteImpl( final Resource resource, final Model model ) {
        super( resource, model );
    }

    public static SiteImpl get( final Resource resource, final Model model ) {
        return new SiteImpl( resource, model );
    }

    public static SiteImpl create( final String uri, final Model model ) {
        return create( model.createResource( uri ), model );
    }

    public static SiteImpl create( final Resource resource, final Model model ) {
        SiteImpl impl = new SiteImpl( resource, model );

        if( !impl.isRDFType( SIOC.Site ) )
            impl.resource().addProperty( RDF.type, SIOC.Site );

        // Set superclass type
        if( !impl.isRDFType( SIOC.Space ) )
            impl.resource().addProperty( RDF.type, SIOC.Space );

        return impl;
    }

    public Set<UserAccount> getAdministrators() {
        // TODO Auto-generated method stub
        return null;
    }

    public void addAdministrator( UserAccount account ) {
        // TODO Auto-generated method stub

    }

    public void removeAdministrator( UserAccount account ) {
        // TODO Auto-generated method stub

    }

    public Set<Forum> getHostOf() {
        // TODO Auto-generated method stub
        return null;
    }

    public void addHostOf( Forum forum ) {
        // TODO Auto-generated method stub

    }

    public void removeHostOf( Forum forum ) {
        // TODO Auto-generated method stub

    }

}
