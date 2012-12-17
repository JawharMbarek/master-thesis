package de.m0ep.uni.ma.rdf.sioc;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.RDF;

import de.m0ep.uni.ma.rdf.RDFBase;
import de.m0ep.uni.ma.rdf.vocabulary.SIOC;

/**
 * An area in which content Items are contained.
 */
public class Container extends RDFBase {
    Container( Model model, Resource resource ) {
        super( model, resource );
    }

    public static Container get( final Model model, final Resource resource ) {
        return new Container( model, resource );
    }

    public static Container create( final Model model, final String uri ) {
        return create( model, model.createResource( uri ) );
    }

    public static Container create( final Model model, final Resource resource ) {
        Container container = new Container( model, resource );

        if( !container.isRDFType( SIOC.Container ) )
            container.resource().addProperty( RDF.type, SIOC.Container );

        return container;
    }

    /**
     * @return All Items in this container
     */
    public Set<Item> getItems() {
        Set<Item> result = new HashSet<Item>();
        StmtIterator iter = resource().listProperties( SIOC.container_of );

        while ( iter.hasNext() ) {
            Statement stmt = iter.next();

            if( stmt.getObject().isURIResource() ) {
                result.add( Item.getItem( model(), (Resource) stmt.getObject() ) );
            }
        }

        return result;
    }

    /**
     * Add an new Item to this container
     * 
     * @param item
     *            the new Item
     */
    public void addItem( final Item item ) {
        resource().addProperty( SIOC.container_of, item.resource() );
    }

    /**
     * Remove an Item from this container
     * 
     * @param item
     *            The Item to remove
     */
    public void removeItem( final Item item ) {
        model().remove( resource(), SIOC.container_of, item.resource() );
    }

    /**
     * @return Return The Container that this container is a child of
     */
    public Container getParent() {
        Resource res = resource().getPropertyResourceValue( SIOC.has_parent );

        if( null != res )
            return Container.get( model(), res );

        return null;
    }

    /**
     * Set parent Container
     * 
     * @param parent
     */
    public void setParent( final Container parent ) {
        removeParent();
        resource().addProperty( SIOC.has_parent, parent.resource() );
    }

    public void removeParent() {
        if( resource().hasProperty( SIOC.has_parent ) )
            resource().removeAll( SIOC.has_parent );
    }

    /**
     * @return Return all UserAccounts that are subscribed to this Container
     */
    public Set<UserAccount> getSubscriber() {
        Set<UserAccount> result = new HashSet<UserAccount>();
        StmtIterator iter = resource().listProperties( SIOC.has_subscriber );

        while ( iter.hasNext() ) {
            Statement stmt = iter.next();

            if( stmt.getObject().isURIResource() ) {
                result.add( UserAccount.get( model(),
                        (Resource) stmt.getObject() ) );
            }
        }

        return result;
    }

    /**
     * Add new Subscriber to this Container
     * 
     * @param account
     *            UserAccount who subscribed
     */
    public void addSubscriber( final UserAccount account ) {

    }

    /**
     * Remove Subscriber of this container
     * 
     * @param account
     */
    public void removeSubscriber( final UserAccount account ) {

    }

    /**
     * @return Return the date and time of the last Item in this container
     */
    public Date getLastItemDate() {
        return null;
    }

    /**
     * Set the date and time of the last Item in this container
     * 
     * @param date
     *            Date and time of the last Item
     */
    public void setLastItemDate( final Date date ) {

    }

    /**
     * Remove the date and time of the last item in this container
     * 
     * @param date
     */
    public void removeLastItemDate() {

    }

    /** The number of Posts (or Items) in a Forum (or a Container). */

    /**
     * @return Return the number of items in this container
     */
    public Integer getNumItems() {
        return null;
    }

    /**
     * Set the number of Items in this container
     * 
     * @param numItems
     */
    public void setNumItems( final Integer numItems ) {

    }

    /**
     * Remove the number of Items in this container
     */
    public void removeNumItems() {

    }

    /** A child Container or Forum that this Container or Forum is a parent of. */

    /**
     * @return Return all Containers where this Container is the parent of
     */
    public Set<Container> getParentOf() {
        return null;
    }

    /**
     * Add Container where this Container is the parent of
     * 
     * @param container
     */
    public void addParentOf( final Container container ) {

    }

    /**
     * Remove Container where this Container is the parent of
     * 
     * @param container
     */
    public void removeParentOf( final Container container ) {

    }
}
