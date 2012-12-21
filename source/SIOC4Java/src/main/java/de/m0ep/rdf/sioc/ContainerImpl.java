package de.m0ep.rdf.sioc;

import java.util.HashSet;
import java.util.Set;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
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
            impl.model().add( impl.resource(), RDF.type, SIOC.Container );

        return impl;
    }

    /**
     * Get all Item where this resource is the container of
     * 
     * @see de.m0ep.rdf.sioc.Container#getContainerOf()
     */
    public Set<Item> getContainerOf() {
        Set<Item> result = new HashSet<Item>();

        StmtIterator iter = model().listStatements( resource(),
                SIOC.container_of, (RDFNode) null );

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
        model().add( resource(), SIOC.container_of, item.resource() );
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
     * Remove all Items from this container
     */
    public void removeAllContainerOf() {
        model().removeAll( resource(), SIOC.container_of, null );
    }

    /**
     * Get the parent Container of this Container
     * 
     * @see de.m0ep.rdf.sioc.Container#getParent()
     */
    public Container getParent() {
        Statement stmt = model().getProperty( resource(), SIOC.has_parent );

        if( null != stmt && stmt.getObject().isURIResource() )
            return SIOCFactory.getContainer( stmt.getObject().asResource(),
                    model() );

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
            model().removeAll( resource(), SIOC.has_parent, null );
        else
            model().add( resource(), SIOC.has_parent, parent.resource() );
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

        StmtIterator iter = model().listStatements( resource(),
                SIOC.has_subscriber, (RDFNode) null );

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
        model().add( resource(), SIOC.has_subscriber, account.resource() );
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
     * Remove all subscribers from this Container
     */
    @Override
    public void removeAllSubscriber() {
        model().removeAll( resource(), SIOC.has_subscriber, null );
    }

    /**
     * Get the date of the last item in this Container
     * 
     * @return Literal
     * 
     * @see de.m0ep.rdf.sioc.Container#getLastItemDate()
     */
    public String getLastItemDate() {
        Statement stmt = model().getProperty( resource(), SIOC.last_item_date );

        if( null != stmt && stmt.getObject().isLiteral() )
            return stmt.getObject().asLiteral().getString();

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
    public void setLastItemDate( String date ) {
        if( null == date )
            model().removeAll( resource(), SIOC.last_item_date, null );
        else
            model().addLiteral( resource(), SIOC.last_item_date,
                    model().createLiteral( date ) );
    }

    /**
     * Get the number of Items in this Container
     * 
     * @return Number of Items
     * 
     * @see de.m0ep.rdf.sioc.Container#getNumItems()
     */
    public int getNumItems() {
        Statement stmt = model().getProperty( resource(), SIOC.num_items );

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
            model().removeAll( resource(), SIOC.num_items, null );
        else
            model().addLiteral( resource(), SIOC.num_items, numItems.intValue() );
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

        StmtIterator iter = model().listStatements( resource(), SIOC.parent_of,
                (RDFNode) null );
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
        model().add( resource(), SIOC.parent_of, child.resource() );
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

    /**
     * Remove all child Container from this Container
     */
    @Override
    public void removeAllParentOf() {
        resource().removeAll( SIOC.parent_of );
        model().removeAll( resource(), SIOC.parent_of, null );
    }
}
