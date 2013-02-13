package sioc;

import java.math.BigInteger;
import java.util.Set;

import org.openrdf.annotations.Iri;

import rdfs.subPropertyOf;

/** An area in which content Items are contained. */
@Iri( "http://rdfs.org/sioc/ns#Container" )
public interface Container extends Resource {
    /** An Item that this Container contains. */
    @Iri( "http://rdfs.org/sioc/ns#container_of" )
    Set<Item> getSiocContainer_of();

    /** An Item that this Container contains. */
    @Iri( "http://rdfs.org/sioc/ns#container_of" )
    void setSiocContainer_of( Set<? extends Item> siocContainer_of );

    /** A Container or Forum that this Container or Forum is a child of. */
    @Iri( "http://rdfs.org/sioc/ns#has_parent" )
    Set<Container> getSiocHas_parent();

    /** A Container or Forum that this Container or Forum is a child of. */
    @Iri( "http://rdfs.org/sioc/ns#has_parent" )
    void setSiocHas_parent( Set<? extends Container> siocHas_parent );

    /**
     * A UserAccount that is subscribed to this Container.
     * 
     * @see rdfs.Resource#getSiocFeed
     */
    @Iri( "http://rdfs.org/sioc/ns#has_subscriber" )
    Set<UserAccount> getSiocHas_subscriber();

    /**
     * A UserAccount that is subscribed to this Container.
     * 
     * @see rdfs.Resource#getSiocFeed_1
     */
    @Iri( "http://rdfs.org/sioc/ns#has_subscriber" )
    void setSiocHas_subscriber( Set<? extends UserAccount> siocHas_subscriber );

    /**
     * The date and time of the last Post (or Item) in a Forum (or a Container),
     * in ISO 8601 format.
     */
    @subPropertyOf( { "http://purl.org/dc/terms/date" } )
    @Iri( "http://rdfs.org/sioc/ns#last_item_date" )
    Set<Object> getSiocLast_item_dates();

    /**
     * The date and time of the last Post (or Item) in a Forum (or a Container),
     * in ISO 8601 format.
     */
    @subPropertyOf( { "http://purl.org/dc/terms/date" } )
    @Iri( "http://rdfs.org/sioc/ns#last_item_date" )
    void setSiocLast_item_dates( Set<?> siocLast_item_dates );

    /** The number of Posts (or Items) in a Forum (or a Container). */
    @Iri( "http://rdfs.org/sioc/ns#num_items" )
    Set<BigInteger> getSiocNum_items();

    /** The number of Posts (or Items) in a Forum (or a Container). */
    @Iri( "http://rdfs.org/sioc/ns#num_items" )
    void setSiocNum_items( Set<? extends BigInteger> siocNum_items );

    /** A child Container or Forum that this Container or Forum is a parent of. */
    @Iri( "http://rdfs.org/sioc/ns#parent_of" )
    Set<Container> getSiocParent_of();

    /** A child Container or Forum that this Container or Forum is a parent of. */
    @Iri( "http://rdfs.org/sioc/ns#parent_of" )
    void setSiocParent_of( Set<? extends Container> siocParent_of );

}
