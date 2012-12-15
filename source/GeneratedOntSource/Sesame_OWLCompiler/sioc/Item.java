package sioc;

import java.util.Set;

import org.openrdf.annotations.Iri;

import rdfg.Graph;
import rdfs.subPropertyOf;

/** An Item is something which can be in a Container. */
@Iri( "http://rdfs.org/sioc/ns#Item" )
public interface Item extends Resource {
    /**
     * Specifies that this Item is about a particular resource, e.g. a Post
     * describing a book, hotel, etc.
     */
    @Iri( "http://rdfs.org/sioc/ns#about" )
    Set<Object> getSiocAbouts();

    /**
     * Specifies that this Item is about a particular resource, e.g. a Post
     * describing a book, hotel, etc.
     */
    @Iri( "http://rdfs.org/sioc/ns#about" )
    void setSiocAbouts( Set<?> siocAbouts );

    /**
     * Refers to who (e.g. a UserAccount, e-mail address, etc.) a particular
     * Item is addressed to.
     */
    @Iri( "http://rdfs.org/sioc/ns#addressed_to" )
    Set<Object> getSiocAddressed_to();

    /**
     * Refers to who (e.g. a UserAccount, e-mail address, etc.) a particular
     * Item is addressed to.
     */
    @Iri( "http://rdfs.org/sioc/ns#addressed_to" )
    void setSiocAddressed_to( Set<?> siocAddressed_to );

    /** The URI of a file attached to an Item. */
    @Iri( "http://rdfs.org/sioc/ns#attachment" )
    Set<Object> getSiocAttachments();

    /** The URI of a file attached to an Item. */
    @Iri( "http://rdfs.org/sioc/ns#attachment" )
    void setSiocAttachments( Set<?> siocAttachments );

    /** The content of the Item in plain text format. */
    @Iri( "http://rdfs.org/sioc/ns#content" )
    Set<Object> getSiocContents();

    /** The content of the Item in plain text format. */
    @Iri( "http://rdfs.org/sioc/ns#content" )
    void setSiocContents( Set<?> siocContents );

    /** Links to a previous (older) revision of this Item or Post. */
    @Iri( "http://rdfs.org/sioc/ns#earlier_version" )
    Set<Item> getSiocEarlier_version();

    /** Links to a previous (older) revision of this Item or Post. */
    @Iri( "http://rdfs.org/sioc/ns#earlier_version" )
    void setSiocEarlier_version( Set<? extends Item> siocEarlier_version );

    /** This links Items to embedded statements, facts and structured content. */
    @Iri( "http://rdfs.org/sioc/ns#embeds_knowledge" )
    Set<Graph> getSiocEmbeds_knowledge();

    /** This links Items to embedded statements, facts and structured content. */
    @Iri( "http://rdfs.org/sioc/ns#embeds_knowledge" )
    void setSiocEmbeds_knowledge( Set<? extends Graph> siocEmbeds_knowledge );

    /** The Container to which this Item belongs. */
    @Iri( "http://rdfs.org/sioc/ns#has_container" )
    Set<Container> getSiocHas_containers();

    /** The Container to which this Item belongs. */
    @Iri( "http://rdfs.org/sioc/ns#has_container" )
    void setSiocHas_containers( Set<? extends Container> siocHas_containers );

    /** The discussion that is related to this Item. */
    @Iri( "http://rdfs.org/sioc/ns#has_discussion" )
    Set<Object> getSiocHas_discussion();

    /** The discussion that is related to this Item. */
    @Iri( "http://rdfs.org/sioc/ns#has_discussion" )
    void setSiocHas_discussion( Set<?> siocHas_discussion );

    /** A UserAccount that modified this Item. */
    @Iri( "http://rdfs.org/sioc/ns#has_modifier" )
    Set<UserAccount> getSiocHas_modifier();

    /** A UserAccount that modified this Item. */
    @Iri( "http://rdfs.org/sioc/ns#has_modifier" )
    void setSiocHas_modifier( Set<? extends UserAccount> siocHas_modifier );

    /**
     * Points to an Item or Post that is a reply or response to this Item or
     * Post.
     */
    @subPropertyOf( { "http://rdfs.org/sioc/ns#related_to" } )
    @Iri( "http://rdfs.org/sioc/ns#has_reply" )
    Set<Item> getSiocHas_reply();

    /**
     * Points to an Item or Post that is a reply or response to this Item or
     * Post.
     */
    @subPropertyOf( { "http://rdfs.org/sioc/ns#related_to" } )
    @Iri( "http://rdfs.org/sioc/ns#has_reply" )
    void setSiocHas_reply( Set<? extends Item> siocHas_reply );

    /**
     * The IP address used when creating this Item. This can be associated with
     * a creator. Some wiki articles list the IP addresses for the creator or
     * modifiers when the usernames are absent.
     */
    @Iri( "http://rdfs.org/sioc/ns#ip_address" )
    Set<Object> getSiocIp_address();

    /**
     * The IP address used when creating this Item. This can be associated with
     * a creator. Some wiki articles list the IP addresses for the creator or
     * modifiers when the usernames are absent.
     */
    @Iri( "http://rdfs.org/sioc/ns#ip_address" )
    void setSiocIp_address( Set<?> siocIp_address );

    /** Links to a later (newer) revision of this Item or Post. */
    @Iri( "http://rdfs.org/sioc/ns#later_version" )
    Set<Item> getSiocLater_version();

    /** Links to a later (newer) revision of this Item or Post. */
    @Iri( "http://rdfs.org/sioc/ns#later_version" )
    void setSiocLater_version( Set<? extends Item> siocLater_version );

    /** Links to the latest revision of this Item or Post. */
    @Iri( "http://rdfs.org/sioc/ns#latest_version" )
    Set<Item> getSiocLatest_version();

    /** Links to the latest revision of this Item or Post. */
    @Iri( "http://rdfs.org/sioc/ns#latest_version" )
    void setSiocLatest_version( Set<? extends Item> siocLatest_version );

    /** Next Item or Post in a given Container sorted by date. */
    @Iri( "http://rdfs.org/sioc/ns#next_by_date" )
    Set<Item> getSiocNext_by_dates();

    /** Next Item or Post in a given Container sorted by date. */
    @Iri( "http://rdfs.org/sioc/ns#next_by_date" )
    void setSiocNext_by_dates( Set<? extends Item> siocNext_by_dates );

    /** Links to the next revision of this Item or Post. */
    @subPropertyOf( { "http://rdfs.org/sioc/ns#later_version" } )
    @Iri( "http://rdfs.org/sioc/ns#next_version" )
    Set<Item> getSiocNext_version();

    /** Links to the next revision of this Item or Post. */
    @subPropertyOf( { "http://rdfs.org/sioc/ns#later_version" } )
    @Iri( "http://rdfs.org/sioc/ns#next_version" )
    void setSiocNext_version( Set<? extends Item> siocNext_version );

    /** Previous Item or Post in a given Container sorted by date. */
    @Iri( "http://rdfs.org/sioc/ns#previous_by_date" )
    Set<Item> getSiocPrevious_by_dates();

    /** Previous Item or Post in a given Container sorted by date. */
    @Iri( "http://rdfs.org/sioc/ns#previous_by_date" )
    void setSiocPrevious_by_dates( Set<? extends Item> siocPrevious_by_dates );

    /** Links to the previous revision of this Item or Post. */
    @subPropertyOf( { "http://rdfs.org/sioc/ns#earlier_version" } )
    @Iri( "http://rdfs.org/sioc/ns#previous_version" )
    Set<Item> getSiocPrevious_version();

    /** Links to the previous revision of this Item or Post. */
    @subPropertyOf( { "http://rdfs.org/sioc/ns#earlier_version" } )
    @Iri( "http://rdfs.org/sioc/ns#previous_version" )
    void setSiocPrevious_version( Set<? extends Item> siocPrevious_version );

    /** Links to an Item or Post which this Item or Post is a reply to. */
    @subPropertyOf( { "http://rdfs.org/sioc/ns#related_to" } )
    @Iri( "http://rdfs.org/sioc/ns#reply_of" )
    Set<Item> getSiocReply_of();

    /** Links to an Item or Post which this Item or Post is a reply to. */
    @subPropertyOf( { "http://rdfs.org/sioc/ns#related_to" } )
    @Iri( "http://rdfs.org/sioc/ns#reply_of" )
    void setSiocReply_of( Set<? extends Item> siocReply_of );

    /**
     * An Item may have a sibling or a twin that exists in a different
     * Container, but the siblings may differ in some small way (for example,
     * language, category, etc.). The sibling of this Item should be
     * self-describing (that is, it should contain all available information).
     */
    @Iri( "http://rdfs.org/sioc/ns#sibling" )
    Set<Item> getSiocSiblings();

    /**
     * An Item may have a sibling or a twin that exists in a different
     * Container, but the siblings may differ in some small way (for example,
     * language, category, etc.). The sibling of this Item should be
     * self-describing (that is, it should contain all available information).
     */
    @Iri( "http://rdfs.org/sioc/ns#sibling" )
    void setSiocSiblings( Set<? extends Item> siocSiblings );

}
