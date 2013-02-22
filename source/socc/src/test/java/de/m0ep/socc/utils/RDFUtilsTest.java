package de.m0ep.socc.utils;

import static org.junit.Assert.*;

import org.junit.Test;
import org.ontoware.rdf2go.model.node.Literal;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.model.node.impl.PlainLiteralImpl;
import org.ontoware.rdf2go.model.node.impl.URIImpl;

public class RDFUtilsTest {

    public static final String TEST_URI_STRING = "http://www.example.com";
    public static final URI TEST_URI = new URIImpl(TEST_URI_STRING);

    @Test
    public void testCreateURI() {
	URI actual = RDFUtils.createURI(TEST_URI_STRING);
	assertEquals(TEST_URI, actual);
	
    }

    @Test
    public void testCreateMailtoURI() {
	URI actual = RDFUtils.createMailtoURI("example@example.com");
	URI expected = new URIImpl("mailto:example@example.com");
	assertEquals(expected, actual);
    }

    @Test
    public void testCreateLiteral() {
	Literal actual = RDFUtils.createLiteral("test");
	Literal expected = new PlainLiteralImpl("test");
	assertEquals(expected, actual);
    }

    @Test
    public void testEndsWithSlash() {
	String actual = RDFUtils.endsWithSlash(TEST_URI_STRING);
	String expected = TEST_URI_STRING + "/";
	assertEquals(expected, actual);

	actual = RDFUtils.endsWithSlash(TEST_URI_STRING + "/");
	assertEquals(expected, actual);
    }

}
