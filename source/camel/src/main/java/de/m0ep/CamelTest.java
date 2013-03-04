package de.m0ep;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.activemq.ActiveMQComponent;
import org.apache.camel.impl.DefaultCamelContext;


public class CamelTest {
    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();
	context.addComponent("activemq", ActiveMQComponent
.activeMQComponent("tcp://localhost:61616"));
        context.addRoutes(new RouteBuilder() {
            public void configure() {
		from("direct:myqueue").process(new Processor() {

		    @Override
		    public void process(Exchange exchange) throws Exception {
			System.out.println(exchange.getIn().getBody());
		    }
		});
            }
        });
        ProducerTemplate template = context.createProducerTemplate();
        context.start();
        for (int i = 0; i < 10; i++) {
	    template.sendBody("direct:myqueue", "Test Message: " + i);
        }
        Thread.sleep(1000);
        context.stop();
    }
}
