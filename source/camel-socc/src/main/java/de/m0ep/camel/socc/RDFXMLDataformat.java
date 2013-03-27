package de.m0ep.camel.socc;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.camel.Exchange;
import org.apache.camel.spi.DataFormat;
import org.ontoware.rdf2go.RDF2Go;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.Syntax;
import org.ontoware.rdf2go.util.RDFTool;
import org.rdfs.sioc.SIOC;

public class RDFXMLDataformat implements DataFormat {
    @Override
    public void marshal(Exchange exchange, Object graph, OutputStream stream)
	    throws Exception {
	RDFSClass stmts = exchange.getContext().getTypeConverter()
		.mandatoryConvertTo(RDFSClass.class, graph);

	Model model = RDF2Go.getModelFactory().createModel();
	model.open();
	model.setNamespace("sioc", SIOC.NS_SIOC.toString());
	model.setNamespace("dcterms", "http://purl.org/dc/terms/");

	model.addAll(stmts.iterator());
	String xml = RDFTool.modelToString(model, Syntax.RdfXml);
	stream.write(xml.getBytes());

	model.close();
    }

    @Override
    public Object unmarshal(Exchange exchange, InputStream stream)
	    throws Exception {
	// String xml = exchange.getContext().getTypeConverter()
	// .mandatoryConvertTo(String.class, stream);

	// return new RDFSClass(RDFTool.stringToModel(xml, Syntax.RdfXml));
	return null;
    }
}
