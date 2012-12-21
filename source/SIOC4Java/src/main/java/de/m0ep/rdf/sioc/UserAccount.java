package de.m0ep.rdf.sioc;

import java.util.Set;

import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Resource;

import de.m0ep.rdf.foaf.Agent;
import de.m0ep.rdf.foaf.Image;

/**
 * Interface UserAccount
 */
public interface UserAccount extends SIOCBase {

    /**
     * sioc:account_of - Refers to the foaf:Agent or foaf:Person who owns this
     * sioc:UserAccount.
     * 
     * @return Agent
     */
    public Agent getAccountOf();

    /**
     * sioc:account_of - Refers to the foaf:Agent or foaf:Person who owns this
     * sioc:UserAccount.
     * 
     * @param agent
     */
    public void setAccountOf( Agent agent );

    /**
     * sioc:administrator_of - A Site that the UserAccount is an administrator
     * of.
     */
    public Set<Site> getAdministratorOf();

    /**
     * sioc:administrator_of - A Site that the UserAccount is an administrator
     * of.
     * 
     * @param site
     */
    public void addAdministratorOf( Site site );

    /**
     * sioc:administrator_of - A Site that the UserAccount is an administrator
     * of.
     * 
     * @param site
     */
    public void removeAdministratorOf( Site site );

    /**
     * @return Image
     */
    public Image getAvatar();

    /**
     * @param image
     */
    public void setAvatar( Image image );

    /**
     * sioc:creator_of - A resource that the UserAccount is a creator of.
     */
    public Set<Resource> getCreatorOf();

    /**
     * sioc:creator_of - A resource that the UserAccount is a creator of.
     * 
     * @param node
     */
    public void addCreatorOf( Resource resource );

    /**
     * sioc:creator_of - A resource that the UserAccount is a creator of.
     * 
     * @param node
     */
    public void removeCreatorOf( Resource resource );

    /**
     */
    public Set<Literal> getEmail();

    /**
     * sioc:email - An electronic mail address of the UserAccount.
     * 
     * @param email
     */
    public void addEmail( Literal email );

    /**
     * sioc:email - An electronic mail address of the UserAccount.
     * 
     * @param email
     */
    public void removeEmail( Literal email );

    /**
     * sioc:email_sha1 - An electronic mail address of the UserAccount, encoded
     * using SHA1.
     */
    public Set<Literal> getEmailSHA1();

    /**
     * sioc:email_sha1 - An electronic mail address of the UserAccount, encoded
     * using SHA1.
     * 
     * @param sha1
     */
    public void addEmailSHA1( Literal sha1 );

    /**
     * @param sha1
     */
    public void removeEmailSHA1( Literal sha1 );

    /**
     * sioc:follows - Indicates that one UserAccount follows another UserAccount
     * (e.g. for microblog posts or other content item update.
     */
    public Set<UserAccount> getFollows();

    /**
     * sioc:follows - Indicates that one UserAccount follows another UserAccount
     * (e.g. for microblog posts or other content item update.
     * 
     * @param account
     */
    public void addFollows( UserAccount account );

    /**
     * sioc:follows - Indicates that one UserAccount follows another UserAccount
     * (e.g. for microblog posts or other content item update.
     * 
     * @param account
     */
    public void removeFollows( UserAccount account );

    /**
     * sioc:member_of - A Usergroup that this UserAccount is a member of.
     */
    public Set<Usergroup> getMemberOf();

    /**
     * sioc:member_of - A Usergroup that this UserAccount is a member of.
     * 
     * @param group
     */
    public void addMemberOf( Usergroup group );

    /**
     * sioc:member_of - A Usergroup that this UserAccount is a member of.
     * 
     * @param group
     */
    public void removeMemberOf( Usergroup group );

    /**
     * sioc:modifier_of - An Item that this UserAccount has modified.
     */
    public Set<Item> getModifierOf();

    /**
     * sioc:modifier_of - An Item that this UserAccount has modified.
     * 
     * @param item
     */
    public void addModifierOf( Item item );

    /**
     * sioc:modifier_of - An Item that this UserAccount has modified.
     * 
     * @param item
     */
    public void removeModifierOf( Item item );

    /**
     * sioc:owner_of - A resource owned by a particular UserAccount, for
     * example, a weblog or image gallery.
     */
    public Set<Resource> getOwnerOf();

    /**
     * sioc:owner_of - A resource owned by a particular UserAccount, for
     * example, a weblog or image gallery.
     * 
     * @param resource
     */
    public void addOwnerOf( Resource resource );

    /**
     * sioc:owner_of - A resource owned by a particular UserAccount, for
     * example, a weblog or image gallery.
     * 
     * @param resource
     */
    public void removeOwnerOf( Resource resource );

    /**
     * sioc:subscriber_of - A Container that a UserAccount is subscribed to.
     */
    public Set<Container> getSubscriberOf();

    /**
     * sioc:subscriber_of - A Container that a UserAccount is subscribed to.
     * 
     * @param container
     */
    public void addSubscriberOf( Container container );

    /**
     * sioc:subscriber_of - A Container that a UserAccount is subscribed to.
     * 
     * @param container
     */
    public void removeSubscriberOf( Container container );

    /**
     * sioc:has_function - A Role that this UserAccount has.
     * 
     * @return Set<de.m0ep.rdf.sioc.Role>
     */
    public Set<Role> getFunctions();

    /**
     * sioc:has_function - A Role that this UserAccount has.
     * 
     * @param role
     */
    public void addFunction( Role role );

    /**
     * sioc:has_function - A Role that this UserAccount has.
     * 
     * @param role
     */
    public void removeFunction( Role role );

}
