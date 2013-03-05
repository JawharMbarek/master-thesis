package de.m0ep.camel.socc;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;
import org.ontoware.rdf2go.model.Model;

public class SOCCEndpoint extends DefaultEndpoint {

    Model model;

    @Override
    public Producer createProducer() throws Exception {
	// TODO Auto-generated method stub
	return new SOCCProducer(this);
    }

    @Override
    public Consumer createConsumer(Processor processor) throws Exception {
	return new SOCCConsumer(this, processor);
    }

    @Override
    public boolean isSingleton() {
	return true;
    }

    public Model getModel() {
	return model;
    }

    public void setModel(Model model) {
	this.model = model;
    }

    @Override
    protected String createEndpointUri() {
	return "test";
    }
}
