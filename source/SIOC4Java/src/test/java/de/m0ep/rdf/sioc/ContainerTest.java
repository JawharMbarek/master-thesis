package de.m0ep.rdf.sioc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import de.m0ep.rdf.JenaTestBase;

public class ContainerTest extends JenaTestBase {

    @Test
    public void testCreateStringModel() {
        reset();

        ContainerImpl con = ContainerImpl.create( URI_TEST, getModel() );
        assertEquals( con.resource(), getModel().getResource( URI_TEST ) );
    }

    @Test
    public void testCreateResourceModel() {
        ContainerImpl con = ContainerImpl.create(
                getModel().createResource( URI_TEST ), getModel() );
        assertEquals( con.resource(), getModel().getResource( URI_TEST ) );
    }

    @Test
    public void testGetContainerOf() {
        reset();
    }

    @Test
    public void testAddContainerOf() {
        fail( "Not yet implemented" );
    }

    @Test
    public void testRemoveContainerOf() {
        fail( "Not yet implemented" );
    }

    @Test
    public void testGetParent() {
        fail( "Not yet implemented" );
    }

    @Test
    public void testSetParent() {
        fail( "Not yet implemented" );
    }

    @Test
    public void testGetSubscriber() {
        fail( "Not yet implemented" );
    }

    @Test
    public void testAddSubscriber() {
        fail( "Not yet implemented" );
    }

    @Test
    public void testRemoveSubscriber() {
        fail( "Not yet implemented" );
    }

    @Test
    public void testGetLastItemDate() {
        fail( "Not yet implemented" );
    }

    @Test
    public void testSetLastItemDate() {
        fail( "Not yet implemented" );
    }

    @Test
    public void testGetNumItems() {
        fail( "Not yet implemented" );
    }

    @Test
    public void testSetNumItems() {
        fail( "Not yet implemented" );
    }

    @Test
    public void testGetParentOf() {
        fail( "Not yet implemented" );
    }

    @Test
    public void testAddParentOf() {
        fail( "Not yet implemented" );
    }

    @Test
    public void testRemoveParentOf() {
        fail( "Not yet implemented" );
    }

}
