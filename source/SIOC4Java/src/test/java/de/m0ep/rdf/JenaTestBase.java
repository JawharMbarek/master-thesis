package de.m0ep.rdf;

import org.junit.Before;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class JenaTestBase {

    public static final String NS        = "http://m0ep.de/rdf/";
    public static final String URI_TEST  = NS + "test";
    public static final String URI_TEST1 = NS + "test1";
    public static final String URI_TEST2 = NS + "test2";
    public static final String URI_TEST3 = NS + "test3";

    private Model              model;

    @Before
    public void setUp() {
        model = ModelFactory.createDefaultModel();

    }

    public Model getModel() {
        return model;
    }

    public void reset() {
        model.removeAll();
    }
}
