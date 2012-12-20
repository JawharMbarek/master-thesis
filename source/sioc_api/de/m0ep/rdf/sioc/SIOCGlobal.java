package de.m0ep.rdf.sioc;

import java.math.BigInteger;
import java.util.Set;

import org.openrdf.annotations.Iri;

import de.m0ep.rdf.rdfs.subPropertyOf;

@Iri( "http://www.w3.org/2000/01/rdf-schema#Resource" )
public interface SIOCGlobal {
    @Iri( "http://purl.org/dc/elements/1.1/date" )
    Set<Object> getDcDate();

    @Iri( "http://purl.org/dc/elements/1.1/date" )
    void setDcDate( Set<?> dcDate );

    @Iri( "http://purl.org/dc/elements/1.1/description" )
    Set<Object> getDcDescription();

    @Iri( "http://purl.org/dc/elements/1.1/description" )
    void setDcDescription( Set<?> dcDescription );

    @Iri( "http://purl.org/dc/elements/1.1/title" )
    Set<Object> getDcTitle();

    @Iri( "http://purl.org/dc/elements/1.1/title" )
    void setDcTitle( Set<?> dcTitle );

    @Iri( "http://purl.org/dc/terms/date" )
    Set<Object> getDctermsDate();

    @Iri( "http://purl.org/dc/terms/date" )
    void setDctermsDate( Set<?> dctermsDate );

    @Iri( "http://purl.org/dc/terms/description" )
    Set<Object> getDctermsDescription();

    @Iri( "http://purl.org/dc/terms/description" )
    void setDctermsDescription( Set<?> dctermsDescription );

    @Iri( "http://purl.org/dc/terms/references" )
    Set<Object> getDctermsReferences();

    @Iri( "http://purl.org/dc/terms/references" )
    void setDctermsReferences( Set<?> dctermsReferences );

    @Iri( "http://purl.org/dc/terms/subject" )
    Set<Object> getDctermsSubject();

    @Iri( "http://purl.org/dc/terms/subject" )
    void setDctermsSubject( Set<?> dctermsSubject );

    @Iri( "http://purl.org/dc/terms/title" )
    Set<Object> getDctermsTitle();

    @Iri( "http://purl.org/dc/terms/title" )
    void setDctermsTitle( Set<?> dctermsTitle );

    /**
     * A feed (e.g. RSS, Atom, etc.) pertaining to this resource (e.g. for a
     * Forum, Site, UserAccount, etc.).
     */
    @Iri( "http://rdfs.org/sioc/ns#feed" )
    Set<Object> getSiocFeed();

    /**
     * A feed (e.g. RSS, Atom, etc.) pertaining to this resource (e.g. for a
     * Forum, Site, UserAccount, etc.).
     */
    @Iri( "http://rdfs.org/sioc/ns#feed" )
    void setSiocFeed( Set<?> siocFeed );

    /** This is the UserAccount that made this resource. */
    @Iri( "http://rdfs.org/sioc/ns#has_creator" )
    Set<UserAccount> getSiocHas_creator();

    /** This is the UserAccount that made this resource. */
    @Iri( "http://rdfs.org/sioc/ns#has_creator" )
    void setSiocHas_creator( Set<? extends UserAccount> siocHas_creator );

    /** A Role that this UserAccount has. */
    @Iri( "http://rdfs.org/sioc/ns#has_function" )
    Set<Role> getSiocHas_function();

    /** A Role that this UserAccount has. */
    @Iri( "http://rdfs.org/sioc/ns#has_function" )
    void setSiocHas_function( Set<? extends Role> siocHas_function );

    /** A UserAccount that this resource is owned by. */
    @Iri( "http://rdfs.org/sioc/ns#has_owner" )
    Set<UserAccount> getSiocHas_owner();

    /** A UserAccount that this resource is owned by. */
    @Iri( "http://rdfs.org/sioc/ns#has_owner" )
    void setSiocHas_owner( Set<? extends UserAccount> siocHas_owner );

    /** A data Space which this resource is a part of. */
    @Iri( "http://rdfs.org/sioc/ns#has_space" )
    Set<Space> getSiocHas_space();

    /** A data Space which this resource is a part of. */
    @Iri( "http://rdfs.org/sioc/ns#has_space" )
    void setSiocHas_space( Set<? extends Space> siocHas_space );

    /**
     * An identifier of a SIOC concept instance. For example, a user ID. Must be
     * unique for instances of each type of SIOC concept within the same site.
     */
    @Iri( "http://rdfs.org/sioc/ns#id" )
    Set<Object> getSiocId();

    /**
     * An identifier of a SIOC concept instance. For example, a user ID. Must be
     * unique for instances of each type of SIOC concept within the same site.
     */
    @Iri( "http://rdfs.org/sioc/ns#id" )
    void setSiocId( Set<?> siocId );

    /**
     * The date and time of the last activity associated with a SIOC concept
     * instance, and expressed in ISO 8601 format. This could be due to a reply
     * Post or Comment, a modification to an Item, etc.
     */
    @subPropertyOf( { "http://purl.org/dc/terms/date" } )
    @Iri( "http://rdfs.org/sioc/ns#last_activity_date" )
    Set<Object> getSiocLast_activity_date();

    /**
     * The date and time of the last activity associated with a SIOC concept
     * instance, and expressed in ISO 8601 format. This could be due to a reply
     * Post or Comment, a modification to an Item, etc.
     */
    @subPropertyOf( { "http://purl.org/dc/terms/date" } )
    @Iri( "http://rdfs.org/sioc/ns#last_activity_date" )
    void setSiocLast_activity_date( Set<?> siocLast_activity_date );

    /**
     * The date and time of the last reply Post or Comment, which could be
     * associated with a starter Item or Post or with a Thread, and expressed in
     * ISO 8601 format.
     */
    @subPropertyOf( { "http://purl.org/dc/terms/date" } )
    @Iri( "http://rdfs.org/sioc/ns#last_reply_date" )
    Set<Object> getSiocLast_reply_date();

    /**
     * The date and time of the last reply Post or Comment, which could be
     * associated with a starter Item or Post or with a Thread, and expressed in
     * ISO 8601 format.
     */
    @subPropertyOf( { "http://purl.org/dc/terms/date" } )
    @Iri( "http://rdfs.org/sioc/ns#last_reply_date" )
    void setSiocLast_reply_date( Set<?> siocLast_reply_date );

    /** A URI of a document which contains this SIOC object. */
    @Iri( "http://rdfs.org/sioc/ns#link" )
    Set<Object> getSiocLink();

    /** A URI of a document which contains this SIOC object. */
    @Iri( "http://rdfs.org/sioc/ns#link" )
    void setSiocLink( Set<?> siocLink );

    /**
     * Links extracted from hyperlinks within a SIOC concept, e.g. Post or Site.
     */
    @subPropertyOf( { "http://purl.org/dc/terms/references" } )
    @Iri( "http://rdfs.org/sioc/ns#links_to" )
    Set<Object> getSiocLinks_to();

    /**
     * Links extracted from hyperlinks within a SIOC concept, e.g. Post or Site.
     */
    @subPropertyOf( { "http://purl.org/dc/terms/references" } )
    @Iri( "http://rdfs.org/sioc/ns#links_to" )
    void setSiocLinks_to( Set<?> siocLinks_to );

    /**
     * The name of a SIOC concept instance, e.g. a username for a UserAccount,
     * group name for a Usergroup, etc.
     */
    @Iri( "http://rdfs.org/sioc/ns#name" )
    Set<Object> getSiocName();

    /**
     * The name of a SIOC concept instance, e.g. a username for a UserAccount,
     * group name for a Usergroup, etc.
     */
    @Iri( "http://rdfs.org/sioc/ns#name" )
    void setSiocName( Set<?> siocName );

    /**
     * A note associated with this resource, for example, if it has been edited
     * by a UserAccount.
     */
    @Iri( "http://rdfs.org/sioc/ns#note" )
    Set<Object> getSiocNote();

    /**
     * A note associated with this resource, for example, if it has been edited
     * by a UserAccount.
     */
    @Iri( "http://rdfs.org/sioc/ns#note" )
    void setSiocNote( Set<?> siocNote );

    /**
     * The number of unique authors (UserAccounts and unregistered posters) who
     * have contributed to this Item, Thread, Post, etc.
     */
    @Iri( "http://rdfs.org/sioc/ns#num_authors" )
    Set<BigInteger> getSiocNum_authors();

    /**
     * The number of unique authors (UserAccounts and unregistered posters) who
     * have contributed to this Item, Thread, Post, etc.
     */
    @Iri( "http://rdfs.org/sioc/ns#num_authors" )
    void setSiocNum_authors( Set<? extends BigInteger> siocNum_authors );

    /**
     * The number of replies that this Item, Thread, Post, etc. has. Useful for
     * when the reply structure is absent.
     */
    @Iri( "http://rdfs.org/sioc/ns#num_replies" )
    Set<BigInteger> getSiocNum_replies();

    /**
     * The number of replies that this Item, Thread, Post, etc. has. Useful for
     * when the reply structure is absent.
     */
    @Iri( "http://rdfs.org/sioc/ns#num_replies" )
    void setSiocNum_replies( Set<? extends BigInteger> siocNum_replies );

    /**
     * The number of times this Item, Thread, UserAccount profile, etc. has been
     * viewed.
     */
    @Iri( "http://rdfs.org/sioc/ns#num_views" )
    Set<BigInteger> getSiocNum_views();

    /**
     * The number of times this Item, Thread, UserAccount profile, etc. has been
     * viewed.
     */
    @Iri( "http://rdfs.org/sioc/ns#num_views" )
    void setSiocNum_views( Set<? extends BigInteger> siocNum_views );

    /**
     * Related Posts for this Post, perhaps determined implicitly from topics or
     * references.
     */
    @Iri( "http://rdfs.org/sioc/ns#related_to" )
    Set<Object> getSiocRelated_to();

    /**
     * Related Posts for this Post, perhaps determined implicitly from topics or
     * references.
     */
    @Iri( "http://rdfs.org/sioc/ns#related_to" )
    void setSiocRelated_to( Set<?> siocRelated_to );

    /** A Role that has a scope of this resource. */
    @Iri( "http://rdfs.org/sioc/ns#scope_of" )
    Set<Role> getSiocScope_of();

    /** A Role that has a scope of this resource. */
    @Iri( "http://rdfs.org/sioc/ns#scope_of" )
    void setSiocScope_of( Set<? extends Role> siocScope_of );

    /**
     * A topic of interest, linking to the appropriate URI, e.g. in the Open
     * Directory Project or of a SKOS category.
     */
    @subPropertyOf( { "http://purl.org/dc/terms/subject" } )
    @Iri( "http://rdfs.org/sioc/ns#topic" )
    Set<Object> getSiocTopic();

    /**
     * A topic of interest, linking to the appropriate URI, e.g. in the Open
     * Directory Project or of a SKOS category.
     */
    @subPropertyOf( { "http://purl.org/dc/terms/subject" } )
    @Iri( "http://rdfs.org/sioc/ns#topic" )
    void setSiocTopic( Set<?> siocTopic );

}
