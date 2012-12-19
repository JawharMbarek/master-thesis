package de.m0ep.rdf.sioc;

import java.util.Set;

import com.hp.hpl.jena.rdf.model.Literal;

/**
 * Interface Container sioc:Container - An area in which content Items are
 * contained.
 */
public interface Container extends SIOCBase {

    /**
     * sioc:container_of - An Item that this Container contains.
     */
    public Set<Item> getContainerOf();

    /**
     * sioc:container_of - An Item that this Container contains.
     * 
     * @param item
     */
    public void addContainerOf( Item item );

    /**
     * sioc:container_of - An Item that this Container contains.
     * 
     * @param item
     */
    public void removeContainerOf( Item item );

    /**
     * sioc:has_parent - A Container or Forum that this Container or Forum is a
     * child of.
     * 
     * @return de.m0ep.rdf.sioc.Container
     */
    public Container getParent();

    /**
     * sioc:has_parent - A Container or Forum that this Container or Forum is a
     * child of.
     * 
     * @param parent
     */
    public void setParent( Container parent );

    /**
     * sioc:has_subscriber - A UserAccount that is subscribed to this Container.
     */
    public Set<UserAccount> getSubscriber();

    /**
     * sioc:has_subscriber - A UserAccount that is subscribed to this Container.
     * 
     * @param account
     */
    public void addSubscriber( UserAccount account );

    /**
     * sioc:has_subscriber - A UserAccount that is subscribed to this Container.
     * 
     * @param account
     */
    public void removeSubscriber( UserAccount account );

    /**
     * sioc:last_item_date - The date and time of the last Post (or Item) in a
     * Forum (or a Container), in ISO 8601 format.
     * 
     * @return Literal
     */
    public Literal getLastItemDate();

    /**
     * sioc:last_item_date - The date and time of the last Post (or Item) in a
     * Forum (or a Container), in ISO 8601 format.
     * 
     * @param date
     */
    public void setLastItemDate( Literal date );

    /**
     * sioc:num_items - The number of Posts (or Items) in a Forum (or a
     * Container).
     * 
     * @return unsigned int
     */
    public int getNumItems();

    /**
     * sioc:num_items - The number of Posts (or Items) in a Forum (or a
     * Container).
     * 
     * @param numItems
     */
    public void setNumItems( Integer numItems );

    /**
     * sioc:parent_of - A child Container or Forum that this Container or Forum
     * is a parent of.
     */
    public Set<Container> getParentOf();

    /**
     * sioc:parent_of - A child Container or Forum that this Container or Forum
     * is a parent of.
     * 
     * @param child
     */
    public void addParentOf( Container container );

    /**
     * sioc:parent_of - A child Container or Forum that this Container or Forum
     * is a parent of.
     * 
     * @param child
     */
    public void removeParentOf( Container container );

}
