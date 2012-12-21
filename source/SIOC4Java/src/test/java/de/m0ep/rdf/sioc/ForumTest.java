package de.m0ep.rdf.sioc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.hp.hpl.jena.vocabulary.RDF;

import de.m0ep.rdf.JenaTestBase;
import de.m0ep.rdf.sioc.vocabulary.SIOC;

public class ForumTest extends JenaTestBase {

    @Test
    public void testCreateStringModel() {
        reset();
        ForumImpl forum = ForumImpl.create( URI_TEST, getModel() );

        assertNotNull( forum );
        assertEquals( getModel().getResource( URI_TEST ), forum.resource() );
        assertTrue( getModel()
                .contains( forum.resource(), RDF.type, SIOC.Forum ) );
    }

    @Test
    public void testCreateResourceModel() {
        reset();
        ForumImpl forum = ForumImpl.create( getModel()
                .createResource( URI_TEST ), getModel() );

        assertNotNull( forum );
        assertEquals( getModel().getResource( URI_TEST ), forum.resource() );
        assertTrue( getModel()
                .contains( forum.resource(), RDF.type, SIOC.Forum ) );
    }

    @Test
    public void testSetGetHost() {
        reset();
        Forum forum = SIOCFactory.createForum( URI_TEST, getModel() );
        Site site = SIOCFactory.createSite( URI_TEST1, getModel() );

        // test set
        forum.setHost( site );
        assertTrue( getModel().contains( forum.resource(), SIOC.has_host,
                site.resource() ) );

        // test get
        assertEquals( site.resource(), forum.getHost().resource() );

        // test set(null) -> remove
        forum.setHost( null );
        assertFalse( getModel().contains( forum.resource(), SIOC.has_host,
                site.resource() ) );

    }

    @Test
    public void testGetRemoveAllModerators() {
        reset();
        Forum forum = SIOCFactory.createForum( URI_TEST, getModel() );
        UserAccount user1 = SIOCFactory.createUserAccount( URI_TEST1,
                getModel() );
        UserAccount user2 = SIOCFactory.createUserAccount( URI_TEST2,
                getModel() );
        forum.addModerator( user1 );
        forum.addModerator( user2 );

        assertEquals( 2, forum.getModerators().size() );
        assertTrue( forum.getModerators().contains( user1 ) );
        assertTrue( forum.getModerators().contains( user2 ) );

        forum.removeAllModerators();
        assertEquals( 0, forum.getModerators().size() );
    }

    @Test
    public void testAddRemoveModerator() {
        Forum forum = SIOCFactory.createForum( URI_TEST, getModel() );
        UserAccount user1 = SIOCFactory.createUserAccount( URI_TEST1,
                getModel() );

        forum.addModerator( user1 );
        assertTrue( getModel().contains( forum.resource(), SIOC.has_moderator,
                user1.resource() ) );
        forum.removeModerator( user1 );
        assertFalse( getModel().contains( forum.resource(), SIOC.has_moderator,
                user1.resource() ) );
    }

    @Test
    public void testSetGetNumThreads() {
        reset();
        Forum forum = SIOCFactory.createForum( URI_TEST, getModel() );
        Site site = SIOCFactory.createSite( URI_TEST1, getModel() );

        // test set
        forum.setNumItems( 1337 );
        assertTrue( getModel().containsLiteral( forum.resource(),
                SIOC.num_items, 1337 ) );

        // test get
        assertEquals( 1337, forum.getNumItems() );

        // test set(null) -> remove
        forum.setNumItems( null );
        assertFalse( getModel().containsLiteral( forum.resource(),
                SIOC.num_items, 1337 ) );
    }
}
