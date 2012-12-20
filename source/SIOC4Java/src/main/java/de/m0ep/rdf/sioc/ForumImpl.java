package de.m0ep.rdf.sioc;

import java.util.Set;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.vocabulary.RDF;

import de.m0ep.rdf.sioc.vocabulary.SIOC;

public class ForumImpl extends ContainerImpl implements Forum {

    protected ForumImpl( final Resource resource, final Model model ) {
        super( resource, model );
    }

    public static ForumImpl get( final Resource resource, final Model model ) {
        return new ForumImpl( resource, model );
    }

    public static ForumImpl create( final String uri, final Model model ) {
        return create( model.createResource( uri ), model );
    }

    public static ForumImpl create( final Resource resource, final Model model ) {
        ForumImpl impl = new ForumImpl( resource, model );

        if( !impl.isRDFType( SIOC.Forum ) )
            impl.model().add( impl.resource(), RDF.type, SIOC.Forum );

        if( !impl.isRDFType( SIOC.Container ) )
            impl.model().add( impl.resource(), RDF.type, SIOC.Container );

        return impl;
    }

    public Site getHost() {
        Statement stmt = model().getProperty( resource(), SIOC.has_host );

        if( null != stmt && stmt.getObject().isURIResource() ) {
            return SIOCFactory.getSite( stmt.getObject().asResource(), model() );
        }

        return null;
    }

    public void setHost( Site host ) {
        if( null == host ) {
            model().removeAll( resource(), SIOC.has_host, null );
        } else {
            model().add( resource(), SIOC.has_host, host.resource() );
        }

    }

    public Set<UserAccount> getModerators() {
        // TODO Auto-generated method stub
        return null;
    }

    public void addModerator( UserAccount account ) {
        // TODO Auto-generated method stub

    }

    public void removeModerator( UserAccount account ) {
        // TODO Auto-generated method stub

    }

    public void removeAllModerators() {
        // TODO Auto-generated method stub

    }

    public int getNumThreads() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void setNumthreads( Integer numThreads ) {
        // TODO Auto-generated method stub

    }

}
