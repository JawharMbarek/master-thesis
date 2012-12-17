package de.m0ep.uni.ma.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import de.m0ep.uni.ma.rdf.RDFBase;

public class RDFBaseTest {

    private static String NS = "http://m0ep.de/uni/ma/";

    private Model         model;

    @Before
    public void setUp() throws Exception {
        model = ModelFactory.createDefaultModel();
    }

    @Test
    public void testConstructors() {

        RDFBase rdfBase = new RDFBase( model, model.createResource( NS
                + "testConstructor" ) );

        assertNotNull( rdfBase.resource() );
        assertNotNull( rdfBase.model() );
        assertNotNull( rdfBase.uri() );

        assertEquals( rdfBase.uri(), NS + "testConstructor" );
        assertEquals( rdfBase.uri(), rdfBase.resource().getURI() );
    }

    @Test
    public void testEquals() {
        RDFBase rdfBase = new RDFBase( model, model.createResource( NS
                + "testConstructor" ) );

        RDFBase rdfBase2 = new RDFBase( model, rdfBase.resource() );
        assertTrue( rdfBase2.equals( rdfBase ) );
    }
}
