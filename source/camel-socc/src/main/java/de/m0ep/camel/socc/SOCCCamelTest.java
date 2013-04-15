package de.m0ep.camel.socc;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.ontoware.rdf2go.model.Model;

import de.m0ep.socc.utils.SIOCUtils;

public class SOCCCamelTest {
    public static void main(String[] args) throws Exception {
	CamelContext context = new DefaultCamelContext();
	Model model = SIOCUtils.createDefaultMemoryModel();

	SOCCComponent soccComponent = new SOCCComponent();
	soccComponent.setModel(model);
	context.addComponent("socc", soccComponent);

	context.addRoutes(new RouteBuilder() {
	    @Override
	    public void configure() throws Exception {
		from("socc:facebook-test?forumId=100000490230885&delay=5000")
		/* .marshal(new RDFXMLDataformat()) */.to(
			"socc:moodle-test?forumId=1&threadId=2");
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
