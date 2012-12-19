package de.m0ep.rdf.sioc;

import java.util.Set;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;

import de.m0ep.rdf.sioc.vocabulary.SIOC;

public class UsergroupImpl extends SIOCBaseImpl implements Usergroup {

    protected UsergroupImpl( final Resource resource, final Model model ) {
        super( resource, model );
    }

    public static UsergroupImpl get( final Resource resource, final Model model ) {
        return new UsergroupImpl( resource, model );
    }

    public static UsergroupImpl create( final String uri, final Model model ) {
        return create( model.createResource( uri ), model );
    }

    public static UsergroupImpl create( final Resource resource,
            final Model model ) {
        UsergroupImpl impl = new UsergroupImpl( resource, model );

        if( !impl.isRDFType( SIOC.Usergroup ) )
            impl.resource().addProperty( RDF.type, SIOC.Usergroup );

        return impl;
    }

    public Set<UserAccount> getMembers() {
        // TODO Auto-generated method stub
        return null;
    }

    public void addMember( UserAccount account ) {
        // TODO Auto-generated method stub

    }

    public void removeMember( UserAccount account ) {
        // TODO Auto-generated method stub

    }

    public Set<Space> getUsergroupOf() {
        // TODO Auto-generated method stub
        return null;
    }

    public void addUsergroupOf( Space space ) {
        // TODO Auto-generated method stub

    }

    public void removeUsergroupOf( Space space ) {
        // TODO Auto-generated method stub

    }

}
