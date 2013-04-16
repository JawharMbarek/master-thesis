package de.m0ep.foaf.test;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ontoware.rdf2go.RDF2Go;
import org.ontoware.rdf2go.model.Model;
import org.purl.dc.terms.DCTerms;

import com.xmlns.foaf.FOAF;

public class FOAFAccountMatcher extends TestCase {

    private Model model;

    @Before
    protected void setUp() throws Exception {
	super.setUp();

	model = RDF2Go.getModelFactory().createModel();
	model.open();
	model.setNamespace("foaf", FOAF.NS_FOAF.toString());
	model.setNamespace("dcterms", DCTerms.NS_DCTerms.toString());
    }

    @After
    protected void tearDown() throws Exception {
	super.tearDown();
	model.close();
    }

    @Test
    public void test() {
	fail("Not yet implemented");
    }

}
