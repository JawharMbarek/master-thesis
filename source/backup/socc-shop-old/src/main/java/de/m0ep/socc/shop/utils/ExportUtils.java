package de.m0ep.socc.shop.utils;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.Constants;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.model.RoutesDefinition;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.Statement;
import org.ontoware.rdf2go.model.Syntax;
import org.ontoware.rdf2go.util.RDFTool;

import com.google.common.collect.Lists;

import de.m0ep.socc.SOCCContext;
import de.m0ep.socc.utils.RDF2GoUtils;

public final class ExportUtils {
    private ExportUtils() {
    }

    public static String getModelXML(final Model model) {
	String result = "";

	RDF2GoUtils.lockModelOrWait(model);

	// get all statements from the origin model
	List<Statement> statements = Lists.newArrayList(model.iterator());

	model.unlock();

	// sort them by subject
	Collections.sort(statements, new Comparator<Statement>() {
	    @Override
	    public int compare(Statement o1, Statement o2) {
		return o1.compareTo(o2);
	    };
	});

	// create a new model with sorted statements
	Model tmpModel = SOCCContext.createDefaultMemoryModel();
	tmpModel.open();
	tmpModel.addAll(statements.iterator());

	// write sorted model to xml string
	result = RDFTool.modelToString(tmpModel, Syntax.RdfXml);

	// close sorted model
	tmpModel.close();

	return result;
    }

    public static String getRoutesXML(final DefaultCamelContext context)
	    throws Exception {
	JAXBContext jaxbContext = JAXBContext.newInstance(
		Constants.JAXB_CONTEXT_PACKAGES, ExportUtils.class
			.getClassLoader());

	Marshaller marshaller = jaxbContext.createMarshaller();

	// get all routes inside this context
	RoutesDefinition routesDefinition = new RoutesDefinition();
	for (RouteDefinition routeDefinition : context.getRouteDefinitions()) {
	    routesDefinition.getRoutes().add(routeDefinition);
	}

	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	marshaller.marshal(routesDefinition, baos);

	return new String(baos.toByteArray());
    }
}
