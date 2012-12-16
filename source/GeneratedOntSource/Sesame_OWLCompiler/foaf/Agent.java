package foaf;

import java.util.Set;

import org.openrdf.annotations.Iri;

import owl.equivalentClass;
import rdfs.subPropertyOf;
import sioc.Resource;

/** An agent (eg. person, group, software or physical artifact). */
@equivalentClass( { "http://purl.org/dc/terms/Agent" } )
@Iri( "http://xmlns.com/foaf/0.1/Agent" )
public interface Agent extends Resource {
    /** Indicates an account held by this agent. */
    @Iri( "http://xmlns.com/foaf/0.1/account" )
    Set<OnlineAccount> getFoafAccounts();

    /** Indicates an account held by this agent. */
    @Iri( "http://xmlns.com/foaf/0.1/account" )
    void setFoafAccounts( Set<? extends OnlineAccount> foafAccounts );

    /** The age in years of some agent. */
    @Iri( "http://xmlns.com/foaf/0.1/age" )
    Object getFoafAge();

    /** The age in years of some agent. */
    @Iri( "http://xmlns.com/foaf/0.1/age" )
    void setFoafAge( Object foafAge );

    /** An AIM chat ID */
    @subPropertyOf( { "http://xmlns.com/foaf/0.1/nick" } )
    @Iri( "http://xmlns.com/foaf/0.1/aimChatID" )
    Set<Object> getFoafAimChatID();

    /** An AIM chat ID */
    @subPropertyOf( { "http://xmlns.com/foaf/0.1/nick" } )
    @Iri( "http://xmlns.com/foaf/0.1/aimChatID" )
    void setFoafAimChatID( Set<?> foafAimChatID );

    /**
     * The birthday of this Agent, represented in mm-dd string form, eg.
     * '12-31'.
     */
    @Iri( "http://xmlns.com/foaf/0.1/birthday" )
    Object getFoafBirthday();

    /**
     * The birthday of this Agent, represented in mm-dd string form, eg.
     * '12-31'.
     */
    @Iri( "http://xmlns.com/foaf/0.1/birthday" )
    void setFoafBirthday( Object foafBirthday );

    /**
     * The gender of this Agent (typically but not necessarily 'male' or
     * 'female').
     */
    @Iri( "http://xmlns.com/foaf/0.1/gender" )
    Object getFoafGender();

    /**
     * The gender of this Agent (typically but not necessarily 'male' or
     * 'female').
     */
    @Iri( "http://xmlns.com/foaf/0.1/gender" )
    void setFoafGender( Object foafGender );

    /** Indicates an account held by this agent. */
    @Iri( "http://xmlns.com/foaf/0.1/holdsAccount" )
    Set<OnlineAccount> getFoafHoldsAccounts();

    /** Indicates an account held by this agent. */
    @Iri( "http://xmlns.com/foaf/0.1/holdsAccount" )
    void setFoafHoldsAccounts( Set<? extends OnlineAccount> foafHoldsAccounts );

    /** An ICQ chat ID */
    @subPropertyOf( { "http://xmlns.com/foaf/0.1/nick" } )
    @Iri( "http://xmlns.com/foaf/0.1/icqChatID" )
    Set<Object> getFoafIcqChatID();

    /** An ICQ chat ID */
    @subPropertyOf( { "http://xmlns.com/foaf/0.1/nick" } )
    @Iri( "http://xmlns.com/foaf/0.1/icqChatID" )
    void setFoafIcqChatID( Set<?> foafIcqChatID );

    /** A page about a topic of interest to this person. */
    @Iri( "http://xmlns.com/foaf/0.1/interest" )
    Set<Document> getFoafInterests();

    /** A page about a topic of interest to this person. */
    @Iri( "http://xmlns.com/foaf/0.1/interest" )
    void setFoafInterests( Set<? extends Document> foafInterests );

    /** A jabber ID for something. */
    @Iri( "http://xmlns.com/foaf/0.1/jabberID" )
    Set<Object> getFoafJabberID();

    /** A jabber ID for something. */
    @Iri( "http://xmlns.com/foaf/0.1/jabberID" )
    void setFoafJabberID( Set<?> foafJabberID );

    /** Something that was made by this agent. */
    @Iri( "http://xmlns.com/foaf/0.1/made" )
    Set<Thing> getFoafMades();

    /** Something that was made by this agent. */
    @Iri( "http://xmlns.com/foaf/0.1/made" )
    void setFoafMades( Set<? extends Thing> foafMades );

    /**
     * A personal mailbox, ie. an Internet mailbox associated with exactly one
     * owner, the first owner of this mailbox. This is a 'static inverse
     * functional property', in that there is (across time and change) at most
     * one individual that ever has any particular value for foaf:mbox.
     */
    @Iri( "http://xmlns.com/foaf/0.1/mbox" )
    Set<Thing> getFoafMboxes();

    /**
     * A personal mailbox, ie. an Internet mailbox associated with exactly one
     * owner, the first owner of this mailbox. This is a 'static inverse
     * functional property', in that there is (across time and change) at most
     * one individual that ever has any particular value for foaf:mbox.
     */
    @Iri( "http://xmlns.com/foaf/0.1/mbox" )
    void setFoafMboxes( Set<? extends Thing> foafMboxes );

    /**
     * The sha1sum of the URI of an Internet mailbox associated with exactly one
     * owner, the first owner of the mailbox.
     */
    @Iri( "http://xmlns.com/foaf/0.1/mbox_sha1sum" )
    Set<Object> getFoafMbox_sha1sum();

    /**
     * The sha1sum of the URI of an Internet mailbox associated with exactly one
     * owner, the first owner of the mailbox.
     */
    @Iri( "http://xmlns.com/foaf/0.1/mbox_sha1sum" )
    void setFoafMbox_sha1sum( Set<?> foafMbox_sha1sum );

    /** An MSN chat ID */
    @subPropertyOf( { "http://xmlns.com/foaf/0.1/nick" } )
    @Iri( "http://xmlns.com/foaf/0.1/msnChatID" )
    Set<Object> getFoafMsnChatID();

    /** An MSN chat ID */
    @subPropertyOf( { "http://xmlns.com/foaf/0.1/nick" } )
    @Iri( "http://xmlns.com/foaf/0.1/msnChatID" )
    void setFoafMsnChatID( Set<?> foafMsnChatID );

    /** An OpenID for an Agent. */
    @subPropertyOf( { "http://xmlns.com/foaf/0.1/isPrimaryTopicOf" } )
    @Iri( "http://xmlns.com/foaf/0.1/openid" )
    Set<Document> getFoafOpenids();

    /** An OpenID for an Agent. */
    @subPropertyOf( { "http://xmlns.com/foaf/0.1/isPrimaryTopicOf" } )
    @Iri( "http://xmlns.com/foaf/0.1/openid" )
    void setFoafOpenids( Set<? extends Document> foafOpenids );

    /** A Skype ID */
    @subPropertyOf( { "http://xmlns.com/foaf/0.1/nick" } )
    @Iri( "http://xmlns.com/foaf/0.1/skypeID" )
    Set<Object> getFoafSkypeID();

    /** A Skype ID */
    @subPropertyOf( { "http://xmlns.com/foaf/0.1/nick" } )
    @Iri( "http://xmlns.com/foaf/0.1/skypeID" )
    void setFoafSkypeID( Set<?> foafSkypeID );

    /**
     * A string expressing what the user is happy for the general public
     * (normally) to know about their current activity.
     */
    @Iri( "http://xmlns.com/foaf/0.1/status" )
    Set<Object> getFoafStatus();

    /**
     * A string expressing what the user is happy for the general public
     * (normally) to know about their current activity.
     */
    @Iri( "http://xmlns.com/foaf/0.1/status" )
    void setFoafStatus( Set<?> foafStatus );

    /**
     * A tipjar document for this agent, describing means for payment and
     * reward.
     */
    @subPropertyOf( { "http://xmlns.com/foaf/0.1/page" } )
    @Iri( "http://xmlns.com/foaf/0.1/tipjar" )
    Set<Document> getFoafTipjars();

    /**
     * A tipjar document for this agent, describing means for payment and
     * reward.
     */
    @subPropertyOf( { "http://xmlns.com/foaf/0.1/page" } )
    @Iri( "http://xmlns.com/foaf/0.1/tipjar" )
    void setFoafTipjars( Set<? extends Document> foafTipjars );

    /** A thing of interest to this person. */
    @Iri( "http://xmlns.com/foaf/0.1/topic_interest" )
    Set<Thing> getFoafTopic_interests();

    /** A thing of interest to this person. */
    @Iri( "http://xmlns.com/foaf/0.1/topic_interest" )
    void setFoafTopic_interests( Set<? extends Thing> foafTopic_interests );

    /** A weblog of some thing (whether person, group, company etc.). */
    @subPropertyOf( { "http://xmlns.com/foaf/0.1/page" } )
    @Iri( "http://xmlns.com/foaf/0.1/weblog" )
    Set<Document> getFoafWeblogs();

    /** A weblog of some thing (whether person, group, company etc.). */
    @subPropertyOf( { "http://xmlns.com/foaf/0.1/page" } )
    @Iri( "http://xmlns.com/foaf/0.1/weblog" )
    void setFoafWeblogs( Set<? extends Document> foafWeblogs );

    /** A Yahoo chat ID */
    @subPropertyOf( { "http://xmlns.com/foaf/0.1/nick" } )
    @Iri( "http://xmlns.com/foaf/0.1/yahooChatID" )
    Set<Object> getFoafYahooChatID();

    /** A Yahoo chat ID */
    @subPropertyOf( { "http://xmlns.com/foaf/0.1/nick" } )
    @Iri( "http://xmlns.com/foaf/0.1/yahooChatID" )
    void setFoafYahooChatID( Set<?> foafYahooChatID );
}
