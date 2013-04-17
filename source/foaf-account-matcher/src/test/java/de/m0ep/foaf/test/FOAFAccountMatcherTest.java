package de.m0ep.foaf.test;

import java.util.List;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ontoware.rdf2go.RDF2Go;
import org.ontoware.rdf2go.model.Model;

import com.xmlns.foaf.Person;

import de.m0ep.foaf.FOAFAccountMatcher;

public class FOAFAccountMatcherTest extends TestCase {

    private Model model;
    private FOAFAccountMatcher matcher;

    @Before
    protected void setUp() throws Exception {
	super.setUp();
	System.out.println("setup");

	model = RDF2Go.getModelFactory().createModel();
	model.open();

	matcher = new FOAFAccountMatcher(model);
	matcher.readFOAFXML(FOAFAccountMatcherTest.class
		.getResourceAsStream("/foaf.rdf"));

	model.dump();
    }

    @After
    protected void tearDown() throws Exception {
	super.tearDown();
	model.close();
    }

    @Test
    public void testMatch() {
	List<Person> result = matcher.findPersonByOnlineAccount("m0eper",
		"http://www.facebook.com");
	assertEquals(1, result.size());
	assertEquals("#me", result.get(0).toString());

	result = matcher.findPersonByOnlineAccount("not-m0eper",
		"http://www.facebook.com");
	assertEquals(1, result.size());
	assertEquals("#other", result.get(0).toString());

	result = matcher.findPersonByOnlineAccount("alice1988",
		"http://www.wer-kennt-wen.de");
	assertEquals(0, result.size());
    }
}
