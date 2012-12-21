package de.m0ep.rdf.sioc;

import java.util.Set;

import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;

import de.m0ep.rdf.foaf.Agent;
import de.m0ep.rdf.foaf.Image;
import de.m0ep.rdf.foaf.vocabulary.FOAF;
import de.m0ep.rdf.sioc.vocabulary.SIOC;

public class UserAccountImpl extends SIOCBaseImpl implements UserAccount {

    protected UserAccountImpl( final Resource resource, final Model model ) {
        super( resource, model );
    }

    public static UserAccountImpl get( final Resource resource,
            final Model model ) {
        return new UserAccountImpl( resource, model );
    }

    public static UserAccountImpl create( final String uri, final Model model ) {
        return create( model.createResource( uri ), model );
    }

    public static UserAccountImpl create( final Resource resource,
            final Model model ) {
        UserAccountImpl impl = new UserAccountImpl( resource, model );

        if( !impl.isRDFType( SIOC.UserAccount ) )
            impl.model().add( impl.resource(), RDF.type, SIOC.UserAccount );

        if( !impl.isRDFType( FOAF.OnlineAccount ) )
            impl.model().add( impl.resource(), RDF.type, FOAF.OnlineAccount );

        return impl;
    }

    public Agent getAccountOf() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setAccountOf( Agent agent ) {
        // TODO Auto-generated method stub

    }

    public Set<Site> getAdministratorOf() {
        // TODO Auto-generated method stub
        return null;
    }

    public void addAdministratorOf( Site site ) {
        // TODO Auto-generated method stub

    }

    public void removeAdministratorOf( Site site ) {
        // TODO Auto-generated method stub

    }

    public Image getAvatar() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setAvatar( Image image ) {
        // TODO Auto-generated method stub

    }

    public Set<Resource> getCreatorOf() {
        // TODO Auto-generated method stub
        return null;
    }

    public void addCreatorOf( Resource resource ) {
        // TODO Auto-generated method stub

    }

    public void removeCreatorOf( Resource resource ) {
        // TODO Auto-generated method stub

    }

    public Set<Literal> getEmail() {
        // TODO Auto-generated method stub
        return null;
    }

    public void addEmail( Literal email ) {
        // TODO Auto-generated method stub

    }

    public void removeEmail( Literal email ) {
        // TODO Auto-generated method stub

    }

    public Set<Literal> getEmailSHA1() {
        // TODO Auto-generated method stub
        return null;
    }

    public void addEmailSHA1( Literal sha1 ) {
        // TODO Auto-generated method stub

    }

    public void removeEmailSHA1( Literal sha1 ) {
        // TODO Auto-generated method stub

    }

    public Set<UserAccount> getFollows() {
        // TODO Auto-generated method stub
        return null;
    }

    public void addFollows( UserAccount account ) {
        // TODO Auto-generated method stub

    }

    public void removeFollows( UserAccount account ) {
        // TODO Auto-generated method stub

    }

    public Set<Usergroup> getMemberOf() {
        // TODO Auto-generated method stub
        return null;
    }

    public void addMemberOf( Usergroup group ) {
        // TODO Auto-generated method stub

    }

    public void removeMemberOf( Usergroup group ) {
        // TODO Auto-generated method stub

    }

    public Set<Item> getModifierOf() {
        // TODO Auto-generated method stub
        return null;
    }

    public void addModifierOf( Item item ) {
        // TODO Auto-generated method stub

    }

    public void removeModifierOf( Item item ) {
        // TODO Auto-generated method stub

    }

    public Set<Resource> getOwnerOf() {
        // TODO Auto-generated method stub
        return null;
    }

    public void addOwnerOf( Resource resource ) {
        // TODO Auto-generated method stub

    }

    public void removeOwnerOf( Resource resource ) {
        // TODO Auto-generated method stub

    }

    public Set<Container> getSubscriberOf() {
        // TODO Auto-generated method stub
        return null;
    }

    public void addSubscriberOf( Container container ) {
        // TODO Auto-generated method stub

    }

    public void removeSubscriberOf( Container container ) {
        // TODO Auto-generated method stub

    }

    public Set<Role> getFunctions() {
        // TODO Auto-generated method stub
        return null;
    }

    public void addFunction( Role role ) {
        // TODO Auto-generated method stub

    }

    public void removeFunction( Role role ) {
        // TODO Auto-generated method stub

    }

}
