package de.m0ep.socc.shop.utils;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.Constants;
import org.ontoware.rdf2go.RDF2Go;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.Statement;
import org.ontoware.rdf2go.model.Syntax;
import org.ontoware.rdf2go.util.RDFTool;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.SIOC;

import com.google.common.collect.Lists;

import de.m0ep.socc.SOCC;
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
	Model tmpModel = SOCC.createDefaultMemoryModel();
	tmpModel.open();
	tmpModel.addAll(statements.iterator());

	// write sorted model to xml string
	result = RDFTool.modelToString(tmpModel, Syntax.RdfXml);
	
	// close sorted model
	tmpModel.close();

	return result;
    }

    public static String getRoutesXML(final DefaultCamelContext camelContext)
	    throws Exception {
	JAXBContext jaxbContext = JAXBContext.newInstance(
		Constants.JAXB_CONTEXT_PACKAGES, ExportUtils.class
			.getClassLoader());

	Marshaller marshaller = jaxbContext.createMarshaller();
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	marshaller.marshal(camelContext, baos);

	return new String(baos.toByteArray());
    }
}
