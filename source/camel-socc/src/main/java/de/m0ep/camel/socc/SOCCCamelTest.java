package de.m0ep.camel.socc;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
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
		from("socc:test").to("socc:test2").process(new Processor() {

		    @Override
		    public void process(Exchange exchange) throws Exception {
			System.out.println(exchange.getIn().getBody());
		    }
		});
	    }
	});

	ProducerTemplate producer = context.createProducerTemplate();
	context.start();

	producer.sendBody("socc:test", "");

	Thread.sleep(4000);
	context.stop();
    }
}
