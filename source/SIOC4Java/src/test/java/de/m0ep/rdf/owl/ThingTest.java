package de.m0ep.rdf.owl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDF;

import de.m0ep.rdf.JenaTestBase;

public class ThingTest extends JenaTestBase {

    @Test
    public void test() {
        ThingImpl thing = ThingImpl.create( NS + "test", getModel() );

        assertNotNull( thing );
        assertEquals( thing.getUri(), NS + "test" );
        assertEquals( thing.model(), getModel() );
        assertEquals( thing.resource(), getModel().getResource( NS + "test" ) );

        thing.resource().addProperty( RDF.type, OWL.Thing );
        assertTrue( thing.isRDFType( OWL.Thing ) );
    }

}
