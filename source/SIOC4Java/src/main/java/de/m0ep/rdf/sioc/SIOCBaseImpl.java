package de.m0ep.rdf.sioc;

import java.util.HashSet;
import java.util.Set;

import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

import de.m0ep.rdf.owl.ThingImpl;
import de.m0ep.rdf.sioc.vocabulary.SIOC;

public class SIOCBaseImpl extends ThingImpl implements SIOCBase {

    protected SIOCBaseImpl( final Resource resource, final Model model ) {
        super( resource, model );
    }

    public static SIOCBaseImpl get( final Resource resource, final Model model ) {
        return new SIOCBaseImpl( resource, model );
    }

    public static SIOCBaseImpl create( final String uri, final Model model ) {
        return create( model.createResource( uri ), model );
    }

    public static SIOCBaseImpl create( final Resource resource,
            final Model model ) {
        SIOCBaseImpl impl = new SIOCBaseImpl( resource, model );
        return impl;
    }

    public Resource getFeed() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setFeed( Resource resource ) {
        // TODO Auto-generated method stub

    }

    public UserAccount getCreator() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setCreator( UserAccount account ) {
        // TODO Auto-generated method stub

    }

    public Space getSpace() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setSpace( Space space ) {
        // TODO Auto-generated method stub

    }

    public Literal getId() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setId( Literal id ) {
        // TODO Auto-generated method stub

    }

    public Literal getLastActivityDate() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setLastActivityDate( Literal date ) {
        // TODO Auto-generated method stub

    }

    public Literal getLastReplyDate() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setLastReplyDate( Literal date ) {
        // TODO Auto-generated method stub

    }

    public Resource getLink() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setLink( Resource resource ) {
        // TODO Auto-generated method stub

    }

    public Set<Resource> getLinksTo() {
        Set<Resource> result = new HashSet<Resource>();

        StmtIterator iter = resource().listProperties( SIOC.links_to );

        while ( iter.hasNext() ) {
            Statement stmt = iter.next();

            if( stmt.getObject().isResource() ) {
                result.add( (Resource) stmt.getObject() );
            }
        }

        return result;
    }

    public void addLinksTo( Resource resource ) {
        // TODO Auto-generated method stub

    }

    public void removeLinksTo( Resource resource ) {
        // TODO Auto-generated method stub

    }

    public Literal getName() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setName( Literal literal ) {
        // TODO Auto-generated method stub

    }

    public Literal getNote() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setNote( Literal literal ) {
        // TODO Auto-generated method stub

    }

    public int getNumAuthors() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void setNumAuthors( Integer numAuthors ) {
        // TODO Auto-generated method stub

    }

    public int getNumReplies() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void setNumReplies( Integer numReplies ) {
        // TODO Auto-generated method stub

    }

    public int getNumViews() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void setNumViews( Integer numViews ) {
        // TODO Auto-generated method stub

    }

    public Set<Role> getScopeOf() {
        return null;
    }

    public void addScopeOf( Role role ) {
        // TODO Auto-generated method stub

    }

    public void removeScopeOf( Role role ) {
        // TODO Auto-generated method stub

    }

    public Resource getTopic() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setTopic( Resource resource ) {
        // TODO Auto-generated method stub

    }

    public Set<UserAccount> getOwner() {
        // TODO Auto-generated method stub
        return null;
    }

    public void addOwner( UserAccount account ) {
        // TODO Auto-generated method stub

    }

    public void removeOwner( UserAccount account ) {
        // TODO Auto-generated method stub

    }

    public Literal setSubject() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setSubject( Literal literal ) {
        // TODO Auto-generated method stub

    }

    public Literal getTitle() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setTitle( Literal literal ) {
        // TODO Auto-generated method stub

    }

    public Literal getCreated() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setCreated( Literal literal ) {
        // TODO Auto-generated method stub

    }

    public Set<Resource> getParts() {
        // TODO Auto-generated method stub
        return null;
    }

    public void addPart( Resource resource ) {
        // TODO Auto-generated method stub

    }

    public void removePart( Resource resource ) {
        // TODO Auto-generated method stub

    }

    public Set<Resource> getPartOf() {
        // TODO Auto-generated method stub
        return null;
    }

    public void addPartOf( Resource resource ) {
        // TODO Auto-generated method stub

    }

    public void removePartOf( Resource resource ) {
        // TODO Auto-generated method stub

    }

    public Literal getModified() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setMofified( Literal literal ) {
        // TODO Auto-generated method stub

    }

}
