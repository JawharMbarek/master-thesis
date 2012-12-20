package de.m0ep.rdf.foaf;

import java.util.Set;

import org.openrdf.annotations.Iri;

import de.m0ep.rdf.owl.Thing;
import de.m0ep.rdf.owl.equivalentClass;
import de.m0ep.rdf.rdfs.subPropertyOf;
import de.m0ep.rdf.vs.term_status;

/** An agent (eg. person, group, software or physical artifact). */
@equivalentClass( { "http://purl.org/dc/terms/Agent" } )
@term_status( { "stable" } )
@Iri( "http://xmlns.com/foaf/0.1/Agent" )
public interface Agent extends FOAFGlobal {
    /** Indicates an account held by this agent. */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/account" )
    Set<OnlineAccount> getFoafAccount();

    /** Indicates an account held by this agent. */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/account" )
    void setFoafAccount( Set<? extends OnlineAccount> foafAccount );

    /** The age in years of some agent. */
    @term_status( { "unstable" } )
    @Iri( "http://xmlns.com/foaf/0.1/age" )
    Object getFoafAge();

    /** The age in years of some agent. */
    @term_status( { "unstable" } )
    @Iri( "http://xmlns.com/foaf/0.1/age" )
    void setFoafAge( Object foafAge );

    /** An AIM chat ID */
    @term_status( { "testing" } )
    @subPropertyOf( { "http://xmlns.com/foaf/0.1/nick" } )
    @Iri( "http://xmlns.com/foaf/0.1/aimChatID" )
    Set<Object> getFoafAimChatID();

    /** An AIM chat ID */
    @term_status( { "testing" } )
    @subPropertyOf( { "http://xmlns.com/foaf/0.1/nick" } )
    @Iri( "http://xmlns.com/foaf/0.1/aimChatID" )
    void setFoafAimChatID( Set<?> foafAimChatID );

    /**
     * The birthday of this Agent, represented in mm-dd string form, eg.
     * '12-31'.
     */
    @term_status( { "unstable" } )
    @Iri( "http://xmlns.com/foaf/0.1/birthday" )
    Object getFoafBirthday();

    /**
     * The birthday of this Agent, represented in mm-dd string form, eg.
     * '12-31'.
     */
    @term_status( { "unstable" } )
    @Iri( "http://xmlns.com/foaf/0.1/birthday" )
    void setFoafBirthday( Object foafBirthday );

    /**
     * The gender of this Agent (typically but not necessarily 'male' or
     * 'female').
     */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/gender" )
    Object getFoafGender();

    /**
     * The gender of this Agent (typically but not necessarily 'male' or
     * 'female').
     */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/gender" )
    void setFoafGender( Object foafGender );

    /** Indicates an account held by this agent. */
    @term_status( { "archaic" } )
    @Iri( "http://xmlns.com/foaf/0.1/holdsAccount" )
    Set<OnlineAccount> getFoafHoldsAccount();

    /** Indicates an account held by this agent. */
    @term_status( { "archaic" } )
    @Iri( "http://xmlns.com/foaf/0.1/holdsAccount" )
    void setFoafHoldsAccount( Set<? extends OnlineAccount> foafHoldsAccount );

    /** An ICQ chat ID */
    @term_status( { "testing" } )
    @subPropertyOf( { "http://xmlns.com/foaf/0.1/nick" } )
    @Iri( "http://xmlns.com/foaf/0.1/icqChatID" )
    Set<Object> getFoafIcqChatID();

    /** An ICQ chat ID */
    @term_status( { "testing" } )
    @subPropertyOf( { "http://xmlns.com/foaf/0.1/nick" } )
    @Iri( "http://xmlns.com/foaf/0.1/icqChatID" )
    void setFoafIcqChatID( Set<?> foafIcqChatID );

    /** A page about a topic of interest to this person. */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/interest" )
    Set<Document> getFoafInterest();

    /** A page about a topic of interest to this person. */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/interest" )
    void setFoafInterest( Set<? extends Document> foafInterest );

    /** A jabber ID for something. */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/jabberID" )
    Set<Object> getFoafJabberID();

    /** A jabber ID for something. */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/jabberID" )
    void setFoafJabberID( Set<?> foafJabberID );

    /** Something that was made by this agent. */
    @term_status( { "stable" } )
    @Iri( "http://xmlns.com/foaf/0.1/made" )
    Set<Thing> getFoafMade();

    /** Something that was made by this agent. */
    @term_status( { "stable" } )
    @Iri( "http://xmlns.com/foaf/0.1/made" )
    void setFoafMade( Set<? extends Thing> foafMade );

    /**
     * A personal mailbox, ie. an Internet mailbox associated with exactly one
     * owner, the first owner of this mailbox. This is a 'static inverse
     * functional property', in that there is (across time and change) at most
     * one individual that ever has any particular value for foaf:mbox.
     */
    @term_status( { "stable" } )
    @Iri( "http://xmlns.com/foaf/0.1/mbox" )
    Set<Thing> getFoafMbox();

    /**
     * A personal mailbox, ie. an Internet mailbox associated with exactly one
     * owner, the first owner of this mailbox. This is a 'static inverse
     * functional property', in that there is (across time and change) at most
     * one individual that ever has any particular value for foaf:mbox.
     */
    @term_status( { "stable" } )
    @Iri( "http://xmlns.com/foaf/0.1/mbox" )
    void setFoafMbox( Set<? extends Thing> foafMbox );

    /**
     * The sha1sum of the URI of an Internet mailbox associated with exactly one
     * owner, the first owner of the mailbox.
     */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/mbox_sha1sum" )
    Set<Object> getFoafMbox_sha1sum();

    /**
     * The sha1sum of the URI of an Internet mailbox associated with exactly one
     * owner, the first owner of the mailbox.
     */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/mbox_sha1sum" )
    void setFoafMbox_sha1sum( Set<?> foafMbox_sha1sum );

    /** An MSN chat ID */
    @term_status( { "testing" } )
    @subPropertyOf( { "http://xmlns.com/foaf/0.1/nick" } )
    @Iri( "http://xmlns.com/foaf/0.1/msnChatID" )
    Set<Object> getFoafMsnChatID();

    /** An MSN chat ID */
    @term_status( { "testing" } )
    @subPropertyOf( { "http://xmlns.com/foaf/0.1/nick" } )
    @Iri( "http://xmlns.com/foaf/0.1/msnChatID" )
    void setFoafMsnChatID( Set<?> foafMsnChatID );

    /** An OpenID for an Agent. */
    @term_status( { "testing" } )
    @subPropertyOf( { "http://xmlns.com/foaf/0.1/isPrimaryTopicOf" } )
    @Iri( "http://xmlns.com/foaf/0.1/openid" )
    Set<Document> getFoafOpenid();

    /** An OpenID for an Agent. */
    @term_status( { "testing" } )
    @subPropertyOf( { "http://xmlns.com/foaf/0.1/isPrimaryTopicOf" } )
    @Iri( "http://xmlns.com/foaf/0.1/openid" )
    void setFoafOpenid( Set<? extends Document> foafOpenid );

    /** A Skype ID */
    @term_status( { "testing" } )
    @subPropertyOf( { "http://xmlns.com/foaf/0.1/nick" } )
    @Iri( "http://xmlns.com/foaf/0.1/skypeID" )
    Set<Object> getFoafSkypeID();

    /** A Skype ID */
    @term_status( { "testing" } )
    @subPropertyOf( { "http://xmlns.com/foaf/0.1/nick" } )
    @Iri( "http://xmlns.com/foaf/0.1/skypeID" )
    void setFoafSkypeID( Set<?> foafSkypeID );

    /**
     * A string expressing what the user is happy for the general public
     * (normally) to know about their current activity.
     */
    @term_status( { "unstable" } )
    @Iri( "http://xmlns.com/foaf/0.1/status" )
    Set<Object> getFoafStatus();

    /**
     * A string expressing what the user is happy for the general public
     * (normally) to know about their current activity.
     */
    @term_status( { "unstable" } )
    @Iri( "http://xmlns.com/foaf/0.1/status" )
    void setFoafStatus( Set<?> foafStatus );

    /**
     * A tipjar document for this agent, describing means for payment and
     * reward.
     */
    @term_status( { "testing" } )
    @subPropertyOf( { "http://xmlns.com/foaf/0.1/page" } )
    @Iri( "http://xmlns.com/foaf/0.1/tipjar" )
    Set<Document> getFoafTipjar();

    /**
     * A tipjar document for this agent, describing means for payment and
     * reward.
     */
    @term_status( { "testing" } )
    @subPropertyOf( { "http://xmlns.com/foaf/0.1/page" } )
    @Iri( "http://xmlns.com/foaf/0.1/tipjar" )
    void setFoafTipjar( Set<? extends Document> foafTipjar );

    /** A thing of interest to this person. */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/topic_interest" )
    Set<Thing> getFoafTopic_interest();

    /** A thing of interest to this person. */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/topic_interest" )
    void setFoafTopic_interest( Set<? extends Thing> foafTopic_interest );

    /** A weblog of some thing (whether person, group, company etc.). */
    @term_status( { "testing" } )
    @subPropertyOf( { "http://xmlns.com/foaf/0.1/page" } )
    @Iri( "http://xmlns.com/foaf/0.1/weblog" )
    Set<Document> getFoafWeblog();

    /** A weblog of some thing (whether person, group, company etc.). */
    @term_status( { "testing" } )
    @subPropertyOf( { "http://xmlns.com/foaf/0.1/page" } )
    @Iri( "http://xmlns.com/foaf/0.1/weblog" )
    void setFoafWeblog( Set<? extends Document> foafWeblog );

    /** A Yahoo chat ID */
    @term_status( { "testing" } )
    @subPropertyOf( { "http://xmlns.com/foaf/0.1/nick" } )
    @Iri( "http://xmlns.com/foaf/0.1/yahooChatID" )
    Set<Object> getFoafYahooChatID();

    /** A Yahoo chat ID */
    @term_status( { "testing" } )
    @subPropertyOf( { "http://xmlns.com/foaf/0.1/nick" } )
    @Iri( "http://xmlns.com/foaf/0.1/yahooChatID" )
    void setFoafYahooChatID( Set<?> foafYahooChatID );

}
