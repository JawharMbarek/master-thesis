package de.m0ep.camel.socc;

import java.util.Map;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;
import org.ontoware.rdf2go.RDF2Go;
import org.ontoware.rdf2go.model.Model;
import org.rdfs.sioc.SIOC;

import com.xmlns.foaf.FOAF;

public class SOCCComponent extends DefaultComponent {

    private Model model;

    @Override
    protected Endpoint createEndpoint(String uri, String remaining,
	    Map<String, Object> parameters) throws Exception {

	this.model = RDF2Go.getModelFactory().createModel();
	this.model.open();
	this.model.setNamespace("sioc", SIOC.NS_SIOC.toString());
	this.model.setNamespace("foaf", FOAF.NS_FOAF.toString());

	SOCCEndpoint endpoint = new SOCCEndpoint();
	endpoint.setModel(getModel());
	return endpoint;
    }

    @Override
    public void start() throws Exception {
	super.start();
	System.out.println("start");
    }

    @Override
    public void stop() throws Exception {
	super.stop();

	System.out.println("stop");
    }

    public Model getModel() {
	return model;
    }

    public void setModel(Model model) {
	this.model = model;
    }
}
