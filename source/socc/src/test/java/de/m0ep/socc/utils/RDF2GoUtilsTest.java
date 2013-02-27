package de.m0ep.socc.utils;

import static org.junit.Assert.*;

import org.junit.Test;
import org.ontoware.rdf2go.model.node.Literal;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.model.node.impl.PlainLiteralImpl;
import org.ontoware.rdf2go.model.node.impl.URIImpl;

public class RDF2GoUtilsTest {

    public static final String TEST_URI_STRING = "http://www.example.com";
    public static final URI TEST_URI = new URIImpl(TEST_URI_STRING);

    @Test
    public void testCreateURI() {
	URI actual = RDF2GoUtils.createURI(TEST_URI_STRING);
	assertEquals(TEST_URI, actual);
	
    }

    @Test
    public void testCreateMailtoURI() {
	URI actual = RDF2GoUtils.createMailtoURI("example@example.com");
	URI expected = new URIImpl("mailto:example@example.com");
	assertEquals(expected, actual);
    }

    @Test
    public void testCreateLiteral() {
	Literal actual = RDF2GoUtils.createLiteral("test");
	Literal expected = new PlainLiteralImpl("test");
	assertEquals(expected, actual);
    }
}
