package de.m0ep.rdf.sioc;

import java.util.HashSet;
import java.util.Set;

import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.RDF;

import de.m0ep.rdf.sioc.vocabulary.SIOC;

/**
 * Implemantation of the {@link Container} interface
 * 
 * @author Florian Müller
 * 
 */
public class ContainerImpl extends SIOCBaseImpl implements Container {

    protected ContainerImpl( final Resource resource, final Model model ) {
        super( resource, model );
    }

    /**
     * Static method to access an existing resource as a Container
     * 
     * @param resource
     * @param model
     * @return
     */
    public static ContainerImpl get( final Resource resource, final Model model ) {
        return new ContainerImpl( resource, model );
    }

    /**
     * Static method to create a new Container
     * 
     * @param uri
     * @param model
     * @return
     */
    public static ContainerImpl create( final String uri, final Model model ) {
        return create( model.createResource( uri ), model );
    }

    /**
     * Static method to create a new Container
     * 
     * @param resource
     * @param model
     * @return
     */
    public static ContainerImpl create( final Resource resource,
            final Model model ) {
        ContainerImpl impl = new ContainerImpl( resource, model );

        if( !impl.isRDFType( SIOC.Container ) )
            impl.resource().addProperty( RDF.type, SIOC.Container );

        return impl;
    }

    /**
     * Get all Item where this resource is the container of
     * 
     * @see de.m0ep.rdf.sioc.Container#getContainerOf()
     */
    public Set<Item> getContainerOf() {
        Set<Item> result = new HashSet<Item>();

        StmtIterator iter = resource().listProperties( SIOC.container_of );

        while ( iter.hasNext() ) {
            Statement stmt = iter.nextStatement();

            if( stmt.getObject().isURIResource() )
                result.add( SIOCFactory.getItem( (Resource) stmt.getObject(),
                        model() ) );
        }

        return result;
    }

    /**
     * Add a new Item to this Container
     * 
     * @see de.m0ep.rdf.sioc.Container#addContainerOf(de.m0ep.rdf.sioc.Item)
     */
    public void addContainerOf( final Item item ) {
        resource().addProperty( SIOC.container_of, item.getUri() );
    }

    /**
     * Remove an Item from this container
     * 
     * @see de.m0ep.rdf.sioc.Container#removeContainerOf(de.m0ep.rdf.sioc.Item)
     */
    public void removeContainerOf( final Item item ) {
        model().removeAll( resource(), SIOC.container_of, item.resource() );
    }

    /**
     * Get the parent Container of this Container
     * 
     * @see de.m0ep.rdf.sioc.Container#getParent()
     */
    public Container getParent() {
        Resource resource = resource().getPropertyResourceValue(
                SIOC.has_parent );

        if( null != resource )
            return SIOCFactory.getContainer( resource, model() );

        return null;
    }

    /**
     * Set the parent Container of this Container
     * 
     * @param parent
     *            The parent Container, or null if it should be unset
     * 
     * @see de.m0ep.rdf.sioc.Container#setParent(de.m0ep.rdf.sioc.Container)
     */
    public void setParent( final Container parent ) {
        if( null == parent )
            resource().removeAll( SIOC.has_parent );
        else
            resource().addProperty( SIOC.has_parent, parent.getUri() );
    }

    /**
     * Get all subscribers of this Container
     * 
     * @return Set of all subscribers
     * 
     * @see de.m0ep.rdf.sioc.Container#getSubscriber()
     */
    public Set<UserAccount> getSubscriber() {
        Set<UserAccount> result = new HashSet<UserAccount>();

        StmtIterator iter = resource().listProperties( SIOC.has_subscriber );

        while ( iter.hasNext() ) {
            Statement stmt = iter.nextStatement();

            if( stmt.getObject().isURIResource() )
                result.add( SIOCFactory.getUserAccount(
                        (Resource) stmt.getObject(), model() ) );
        }

        return result;
    }

    /**
     * Add a new subscriber to this Container
     * 
     * @param account
     * 
     * @see de.m0ep.rdf.sioc.Container#addSubscriber(de.m0ep.rdf.sioc.UserAccount)
     */
    public void addSubscriber( final UserAccount account ) {
        resource().addProperty( SIOC.has_subscriber, account.getUri() );
    }

    /**
     * Remove a subscriber from this Container
     * 
     * @see de.m0ep.rdf.sioc.Container#removeSubscriber(de.m0ep.rdf.sioc.UserAccount)
     */
    public void removeSubscriber( UserAccount account ) {
        model().removeAll( resource(), SIOC.has_subscriber, account.resource() );
    }

    /**
     * Get the date of the last item in this Container
     * 
     * @return Literal
     * 
     * @see de.m0ep.rdf.sioc.Container#getLastItemDate()
     */
    public Literal getLastItemDate() {
        Statement stmt = resource().getProperty( SIOC.last_item_date );

        if( null != stmt )
            return stmt.getObject().asLiteral();

        return null;
    }

    /**
     * Set the date of the last Item in this Container
     * 
     * @param date
     *            Date of the last item, or null if it should be unset
     * 
     * @see de.m0ep.rdf.sioc.Container#setLastItemDate(com.hp.hpl.jena.rdf.model.
     *      Literal)
     */
    public void setLastItemDate( Literal date ) {
        if( null == date )
            resource().removeAll( SIOC.last_item_date );
        else
            resource().addLiteral( SIOC.last_item_date, date );
    }

    /**
     * Get the number of Items in this Container
     * 
     * @return Number of Items
     * 
     * @see de.m0ep.rdf.sioc.Container#getNumItems()
     */
    public int getNumItems() {
        Statement stmt = resource().getProperty( SIOC.last_item_date );

        if( null != stmt && stmt.getObject().isLiteral() )
            return stmt.getObject().asLiteral().getInt();

        return -1;
    }

    /**
     * Set the number of Items in this Container
     * 
     * @param numItems
     *            Number of Items, or null if it should be unset
     * 
     * @see de.m0ep.rdf.sioc.Container#setNumItems(java.lang.Integer)
     */
    public void setNumItems( Integer numItems ) {
        if( null == numItems )
            resource().removeAll( SIOC.num_items );
        else
            resource().addLiteral( SIOC.num_items, numItems );
    }

    /**
     * Get all Containers where this Container is the parent of
     * 
     * @return Set with all child Containers
     * 
     * @see de.m0ep.rdf.sioc.Container#getParentOf()
     */
    public Set<Container> getParentOf() {
        Set<Container> result = new HashSet<Container>();

        StmtIterator iter = resource().listProperties( SIOC.parent_of );
        while ( iter.hasNext() ) {
            Statement stmt = iter.nextStatement();

            if( stmt.getObject().isURIResource() )
                result.add( SIOCFactory.getContainer( stmt.getObject()
                        .asResource(), model() ) );
        }

        return result;
    }

    /**
     * Add a new child Container to this Container
     * 
     * @param child
     * 
     * @see de.m0ep.rdf.sioc.Container#addParentOf(de.m0ep.rdf.sioc.Container)
     */
    public void addParentOf( Container child ) {
        resource().addProperty( SIOC.parent_of, child.getUri() );
    }

    /**
     * Remove a child Container from this Container
     * 
     * @param child
     * 
     * @see de.m0ep.rdf.sioc.Container#removeParentOf(de.m0ep.rdf.sioc.Container)
     */
    public void removeParentOf( Container child ) {
        model().removeAll( resource(), SIOC.parent_of, child.resource() );
    }

}
