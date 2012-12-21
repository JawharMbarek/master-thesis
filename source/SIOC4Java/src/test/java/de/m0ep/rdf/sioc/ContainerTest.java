package de.m0ep.rdf.sioc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

import com.hp.hpl.jena.util.FileUtils;
import com.hp.hpl.jena.vocabulary.RDF;

import de.m0ep.rdf.JenaTestBase;
import de.m0ep.rdf.sioc.vocabulary.SIOC;

public class ContainerTest extends JenaTestBase {

    @Test
    public void testCreateStringModel() {
        reset();

        ContainerImpl con = ContainerImpl.create( URI_TEST, getModel() );
        assertNotNull( con );
        assertEquals( getModel().getResource( URI_TEST ), con.resource() );
        assertTrue( getModel().contains( con.resource(), RDF.type,
                SIOC.Container ) );
    }

    @Test
    public void testCreateResourceModel() {
        reset();
        ContainerImpl con = ContainerImpl.create(
                getModel().createResource( URI_TEST ), getModel() );
        assertNotNull( con );
        assertEquals( con.resource(), getModel().getResource( URI_TEST ) );
        assertTrue( getModel().contains( con.resource(), RDF.type,
                SIOC.Container ) );
    }

    @Test
    public void testGetContainerOf() {
        reset();
        ContainerImpl con = ContainerImpl.create(
                getModel().createResource( URI_TEST ), getModel() );

        Item item1 = SIOCFactory.createItem( URI_TEST1, getModel() );
        Item item2 = SIOCFactory.createItem( URI_TEST2, getModel() );
        con.addContainerOf( item1 );
        con.addContainerOf( item2 );

        assertEquals( 2, con.getContainerOf().size() );
        assertTrue( con.getContainerOf().contains( item1 ) );
        assertTrue( con.getContainerOf().contains( item2 ) );
    }

    @Test
    public void testAddRemoveContainerOf() {
        reset();
        ContainerImpl con = ContainerImpl.create(
                getModel().createResource( URI_TEST ), getModel() );

        Item item = SIOCFactory.createItem( URI_TEST1, getModel() );
        con.addContainerOf( item );

        assertTrue( getModel().contains( con.resource(), SIOC.container_of,
                item.resource() ) );

        con.removeContainerOf( item );

        assertFalse( getModel().contains( con.resource(), SIOC.container_of,
                item.resource() ) );
    }

    @Test
    public void testGetParent() {
        reset();
        ContainerImpl con1 = ContainerImpl.create(
                getModel().createResource( URI_TEST1 ), getModel() );
        ContainerImpl con2 = ContainerImpl.create(
                getModel().createResource( URI_TEST2 ), getModel() );

        con1.setParent( con2 );

        assertEquals( con2.resource(), con1.getParent().resource() );
    }

    @Test
    public void testSetParent() {
        reset();
        Container con1 = SIOCFactory.createContainer( URI_TEST1, getModel() );
        Container con2 = SIOCFactory.createContainer( URI_TEST2, getModel() );

        con1.setParent( con2 );

        assertTrue( getModel().contains( con1.resource(), SIOC.has_parent,
                con2.resource() ) );
        con1.setParent( null );
        assertFalse( getModel().contains( con1.resource(), SIOC.has_parent,
                con2.resource() ) );
    }

    @Test
    public void testGetSubscriber() {
        reset();
        Container con = SIOCFactory.createContainer( URI_TEST, getModel() );

        UserAccount user1 = SIOCFactory.createUserAccount( URI_TEST1,
                getModel() );
        UserAccount user2 = SIOCFactory.createUserAccount( URI_TEST2,
                getModel() );

        con.addSubscriber( user1 );
        con.addSubscriber( user2 );

        assertEquals( 2, con.getSubscriber().size() );
        assertTrue( con.getSubscriber().contains( user1 ) );
        assertTrue( con.getSubscriber().contains( user2 ) );
    }

    @Test
    public void testAddRemoveSubscriber() {
        reset();
        Container con = SIOCFactory.createContainer( URI_TEST, getModel() );

        UserAccount user1 = SIOCFactory.createUserAccount( URI_TEST1,
                getModel() );

        con.addSubscriber( user1 );

        assertTrue( getModel().contains( con.resource(), SIOC.has_subscriber,
                user1.resource() ) );

        con.removeSubscriber( user1 );

        assertFalse( getModel().contains( con.resource(), SIOC.has_subscriber,
                user1.resource() ) );
    }

    @Test
    public void testSetGetLastItemDate() {
        reset();
        Container con = SIOCFactory.createContainer( URI_TEST, getModel() );
        String date = new Date().toString();

        con.setLastItemDate( date );

        assertTrue( getModel().contains( con.resource(), SIOC.last_item_date,
                date ) );

        assertEquals( date, con.getLastItemDate() );

        con.setLastItemDate( null );
        assertFalse( getModel().contains( con.resource(), SIOC.last_item_date,
                date ) );
    }

    @Test
    public void testSetGetNumItems() {
        reset();
        Container con = SIOCFactory.createContainer( URI_TEST, getModel() );

        con.setNumItems( 1337 );
        getModel().write( System.out, FileUtils.langTurtle );

        assertTrue( getModel().containsLiteral( con.resource(), SIOC.num_items,
                1337 ) );

        assertEquals( 1337, con.getNumItems() );

        con.setNumItems( null );
        assertFalse( getModel().containsLiteral( con.resource(),
                SIOC.num_items, 1337 ) );
    }

    @Test
    public void testGetRemoveAllParentOf() {
        reset();
        Container con1 = SIOCFactory.createContainer( URI_TEST1, getModel() );
        Container con2 = SIOCFactory.createContainer( URI_TEST2, getModel() );
        Container con3 = SIOCFactory.createContainer( URI_TEST3, getModel() );

        con1.addParentOf( con2 );
        con1.addParentOf( con3 );

        assertEquals( 2, con1.getParentOf().size() );
        assertTrue( con1.getParentOf().contains( con2 ) );
        assertTrue( con1.getParentOf().contains( con3 ) );

        con1.removeAllParentOf();
        assertEquals( 0, con1.getParentOf().size() );
    }

    @Test
    public void testAddRemoveParentOf() {
        reset();
        Container con1 = SIOCFactory.createContainer( URI_TEST1, getModel() );
        Container con2 = SIOCFactory.createContainer( URI_TEST2, getModel() );

        con1.addParentOf( con2 );

        assertTrue( getModel().contains( con1.resource(), SIOC.parent_of,
                con2.resource() ) );

        con1.removeParentOf( con2 );

        assertFalse( getModel().contains( con1.resource(), SIOC.parent_of,
                con2.resource() ) );
    }

}
