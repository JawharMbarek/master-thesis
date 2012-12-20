package de.m0ep.rdf.owl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDF;

import de.m0ep.rdf.JenaTestBase;

public class ThingTest extends JenaTestBase {

    @Test
    public void test() {
        ThingImpl thing1 = ThingImpl.create( URI_TEST1, getModel() );
        ThingImpl thing2 = ThingImpl.create( URI_TEST2, getModel() );

        assertNotNull( thing1 );
        assertEquals( thing1.getUri(), URI_TEST1 );
        assertEquals( thing1.model(), getModel() );
        assertEquals( thing1.resource(), getModel().getResource( URI_TEST1 ) );

        thing1.resource().addProperty( RDF.type, OWL.Thing );
        assertTrue( thing1.isRDFType( OWL.Thing ) );
        assertTrue( thing1.equals( thing1 ) );
        assertFalse( thing1.equals( thing2 ) );
    }
}
