package de.m0ep.camel.socc;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class SOCCCamelTest {
    public static void main(String[] args) throws Exception {
	CamelContext context = new DefaultCamelContext();

	context.addComponent("socc", new SOCCComponent());

	context.addRoutes(new RouteBuilder() {
	    @Override
	    public void configure() throws Exception {
		from("socc:moodle-test/thread/2").marshal(
			new RDFXMLDataformat()).to("mock:test2");
	    }
	});

	context.start();

	String line = "";
	BufferedReader bis = new BufferedReader(
		new InputStreamReader(System.in));

	do {
	    line = bis.readLine();
	} while (!"q".equals(line));

	context.stop();
    }
}
