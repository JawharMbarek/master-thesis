package de.m0ep.camel.socc;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class SOCCCamelTest {
    public static void main(String[] args) throws Exception {
	CamelContext context = new DefaultCamelContext();

	context.addComponent("socc", new SOCCComponent());

	context.addRoutes(new RouteBuilder() {
	    @Override
	    public void configure() throws Exception {
		from("socc:test").marshal(new RDFXMLDataformat()).to(
			"mock:test2");
	    }
	});

	ProducerTemplate producer = context.createProducerTemplate();
	context.start();

	producer.sendBody("socc:test", "");

	Thread.sleep(4000);
	context.stop();
    }
}
