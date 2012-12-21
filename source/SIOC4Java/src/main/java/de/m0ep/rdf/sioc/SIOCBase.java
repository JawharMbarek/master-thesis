package de.m0ep.rdf.sioc;

import java.util.Set;

import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Resource;

import de.m0ep.rdf.owl.Thing;

/**
 * Interface SIOCBase
 */
public interface SIOCBase extends Thing {

    /**
     * sioc:feed - A feed (e.g. RSS, Atom, etc.) pertaining to this resource
     * (e.g. for a Forum, Site, UserAccount, etc.).
     * 
     * @return Resource
     */
    public Resource getFeed();

    /**
     * sioc:feed - A feed (e.g. RSS, Atom, etc.) pertaining to this resource
     * (e.g. for a Forum, Site, UserAccount, etc.).
     * 
     * @param resource
     */
    public void setFeed( Resource resource );

    /**
     * sioc:has_creator - This is the UserAccount that made this resource.
     * 
     * @return de.m0ep.rdf.sioc.UserAccount
     */
    public UserAccount getCreator();

    /**
     * sioc:has_creator - This is the UserAccount that made this resource.
     * 
     * @param account
     */
    public void setCreator( UserAccount account );

    /**
     * sioc:has_space - A data Space which this resource is a part of.
     * 
     * @return de.m0ep.rdf.sioc.Space
     */
    public Space getSpace();

    /**
     * sioc:has_space - A data Space which this resource is a part of.
     * 
     * @param space
     */
    public void setSpace( Space space );

    /**
     * sioc:id - An identifier of a SIOC concept instance. For example, a user
     * ID. Must be unique for instances of each type of SIOC concept within the
     * same site.
     * 
     * @return Literal
     */
    public Literal getId();

    /**
     * sioc:id - An identifier of a SIOC concept instance. For example, a user
     * ID. Must be unique for instances of each type of SIOC concept within the
     * same site.
     * 
     * @param id
     */
    public void setId( Literal id );

    /**
     * sioc:last_activity_date - The date and time of the last activity
     * associated with a SIOC concept instance, and expressed in ISO 8601
     * format. This could be due to a reply Post or Comment, a modification to
     * an Item, etc.
     * 
     * @return Literal
     */
    public Literal getLastActivityDate();

    /**
     * sioc:last_activity_date - The date and time of the last activity
     * associated with a SIOC concept instance, and expressed in ISO 8601
     * format. This could be due to a reply Post or Comment, a modification to
     * an Item, etc.
     * 
     * @param date
     */
    public void setLastActivityDate( Literal date );

    /**
     * sioc:last_reply_date - The date and time of the last reply Post or
     * Comment, which could be associated with a starter Item or Post or with a
     * Thread, and expressed in ISO 8601 format.
     * 
     * @return Literal
     */
    public Literal getLastReplyDate();

    /**
     * sioc:last_reply_date - The date and time of the last reply Post or
     * Comment, which could be associated with a starter Item or Post or with a
     * Thread, and expressed in ISO 8601 format.
     * 
     * @param date
     */
    public void setLastReplyDate( Literal date );

    /**
     * sioc:link - A URI of a document which contains this SIOC object.
     * 
     * @return Resource
     */
    public Resource getLink();

    /**
     * sioc:link - A URI of a document which contains this SIOC object.
     * 
     * @param resource
     */
    public void setLink( Resource resource );

    /**
     * sioc:links_to - Links extracted from hyperlinks within a SIOC concept,
     * e.g. Post or Site.
     * 
     * @return Set<Resource>
     */
    public Set<Resource> getLinksTo();

    /**
     * sioc:links_to - Links extracted from hyperlinks within a SIOC concept,
     * e.g. Post or Site.
     * 
     * @param resource
     */
    public void addLinksTo( Resource resource );

    /**
     * sioc:links_to - Links extracted from hyperlinks within a SIOC concept,
     * e.g. Post or Site.
     * 
     * @param resource
     */
    public void removeLinksTo( Resource resource );

    /**
     * sioc:name - The name of a SIOC concept instance, e.g. a username for a
     * UserAccount, group name for a Usergroup, etc.
     * 
     * @return Literal
     */
    public Literal getName();

    /**
     * sioc:name - The name of a SIOC concept instance, e.g. a username for a
     * UserAccount, group name for a Usergroup, etc.
     * 
     * @param literal
     */
    public void setName( Literal literal );

    /**
     * sioc:note - A note associated with this resource, for example, if it has
     * been edited by a UserAccount.
     * 
     * @return Literal
     */
    public Literal getNote();

    /**
     * sioc:note - A note associated with this resource, for example, if it has
     * been edited by a UserAccount.
     * 
     * @param literal
     */
    public void setNote( Literal literal );

    /**
     * sioc:num_authors - The number of unique authors (UserAccounts and
     * unregistered posters) who have contributed to this Item, Thread, Post,
     * etc.
     * 
     * @return unsigned int
     */
    public int getNumAuthors();

    /**
     * sioc:num_authors - The number of unique authors (UserAccounts and
     * unregistered posters) who have contributed to this Item, Thread, Post,
     * etc.
     * 
     * @param numAuthors
     */
    public void setNumAuthors( Integer numAuthors );

    /**
     * sioc:num_replies - The number of replies that this Item, Thread, Post,
     * etc. has. Useful for when the reply structure is absent.
     * 
     * @return int
     */
    public int getNumReplies();

    /**
     * sioc:num_replies - The number of replies that this Item, Thread, Post,
     * etc. has. Useful for when the reply structure is absent.
     * 
     * @param numReplies
     */
    public void setNumReplies( Integer numReplies );

    /**
     * sioc:num_views - The number of times this Item, Thread, UserAccount
     * profile, etc. has been viewed.
     * 
     * @return int
     */
    public int getNumViews();

    /**
     * sioc:num_views - The number of times this Item, Thread, UserAccount
     * profile, etc. has been viewed.
     * 
     * @param numViews
     */
    public void setNumViews( Integer numViews );

    /**
     * sioc:scope_of - A Role that has a scope of this resource.
     * 
     * @return Set<de.m0ep.rdf.sioc.Role>
     */
    public Set<Role> getScopeOf();

    /**
     * sioc:scope_of - A Role that has a scope of this resource.
     * 
     * @param role
     */
    public void addScopeOf( Role role );

    /**
     * sioc:scope_of - A Role that has a scope of this resource.
     * 
     * @param role
     */
    public void removeScopeOf( Role role );

    /**
     * sioc:topic - A topic of interest, linking to the appropriate URI, e.g. in
     * the Open Directory Project or of a SKOS category.
     * 
     * @return RDFNode
     */
    public Resource getTopic();

    /**
     * sioc:topic - A topic of interest, linking to the appropriate URI, e.g. in
     * the Open Directory Project or of a SKOS category.
     * 
     * @param node
     */
    public void setTopic( Resource resource );

    /**
     * sioc:has_owner - A UserAccount that this resource is owned by.
     * 
     * @return Set<de.m0ep.rdf.sioc.UserAccount>
     */
    public Set<UserAccount> getOwner();

    /**
     * sioc:has_owner - A UserAccount that this resource is owned by.
     * 
     * @param account
     */
    public void addOwner( UserAccount account );

    /**
     * sioc:has_owner - A UserAccount that this resource is owned by.
     * 
     * @param account
     */
    public void removeOwner( UserAccount account );

    /**
     * dcterms:subject - Can be used for keywords describing the subject of an
     * Item or Post. See also: sioc:topic.
     * 
     * @return
     */
    public Literal setSubject();

    /**
     * dcterms:subject - Can be used for keywords describing the subject of an
     * Item or Post. See also: sioc:topic.
     * 
     * @param literal
     */
    public void setSubject( Literal literal );

    /**
     * dcterms:title - Specifies the title of a resource. Usually used to
     * describe the title of an Item or Post.
     * 
     * @return
     */
    public Literal getTitle();

    /**
     * dcterms:title - Specifies the title of a resource. Usually used to
     * describe the title of an Item or Post.
     * 
     * @param literal
     */
    public void setTitle( Literal literal );

    /**
     * dcterms:created - Details the date and time when a resource was created.
     * Usually used as a property of an Item or Post.
     * 
     * @return Literal
     */
    public Literal getCreated();

    /**
     * dcterms:created - Details the date and time when a resource was created.
     * Usually used as a property of an Item or Post.
     * 
     * @param literal
     */
    public void setCreated( Literal literal );

    /**
     * dcterms:hasPart - A resource that is a part of this subject. Usually used
     * from the domain of a Post or Community.
     * 
     * @return
     */
    public Set<Resource> getParts();

    /**
     * dcterms:hasPart - A resource that is a part of this subject. Usually used
     * from the domain of a Post or Community.
     * 
     * @param resource
     */
    public void addPart( Resource resource );

    /**
     * dcterms:hasPart - A resource that is a part of this subject. Usually used
     * from the domain of a Post or Community.
     * 
     * @param resource
     */
    public void removePart( Resource resource );

    /**
     * dcterms:isPartOf - A resource that the subject is a part of. Usually used
     * with the range of a Post or Community.
     * 
     * @return
     */
    public Set<Resource> getPartOf();

    /**
     * dcterms:isPartOf - A resource that the subject is a part of. Usually used
     * with the range of a Post or Community.
     * 
     * @param resource
     */
    public void addPartOf( Resource resource );

    /**
     * dcterms:isPartOf - A resource that the subject is a part of. Usually used
     * with the range of a Post or Community.
     * 
     * @param resource
     */
    public void removePartOf( Resource resource );

    /**
     * dcterms:modified - Details the date and time when a resource was
     * modified. Usually used as a property of an Item or Post.
     * 
     * @return
     */
    public Literal getModified();

    /**
     * dcterms:modified - Details the date and time when a resource was
     * modified. Usually used as a property of an Item or Post.
     * 
     * @param literal
     */
    public void setMofified( Literal literal );
}
