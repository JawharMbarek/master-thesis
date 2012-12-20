package de.m0ep.rdf.sioc;

import java.util.Set;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;

import de.m0ep.rdf.sioc.vocabulary.SIOC;

public class RoleImpl extends SIOCBaseImpl implements Role {

    protected RoleImpl( final Resource resource, final Model model ) {
        super( resource, model );
    }

    public static RoleImpl get( final Resource resource, final Model model ) {
        return new RoleImpl( resource, model );
    }

    public static RoleImpl create( final String uri, final Model model ) {
        return create( model.createResource( uri ), model );
    }

    public static RoleImpl create( final Resource resource, final Model model ) {
        RoleImpl impl = new RoleImpl( resource, model );

        if( !impl.isRDFType( SIOC.Role ) )
            impl.model().add( impl.resource(), RDF.type, SIOC.Role );

        return impl;
    }

    public Set<UserAccount> getFunctionOf() {
        // TODO Auto-generated method stub
        return null;
    }

    public void addFunctionOf( UserAccount account ) {
        // TODO Auto-generated method stub

    }

    public void removeFunctionOf( UserAccount account ) {
        // TODO Auto-generated method stub

    }

    public Set<Resource> getScopes() {
        // TODO Auto-generated method stub
        return null;
    }

    public void addScope( Resource resource ) {
        // TODO Auto-generated method stub

    }

    public void removeScope( Resource resource ) {
        // TODO Auto-generated method stub

    }

}
