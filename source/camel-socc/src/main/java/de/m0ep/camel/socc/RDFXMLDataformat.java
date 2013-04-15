package de.m0ep.camel.socc;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.spi.DataFormat;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.Statement;
import org.ontoware.rdf2go.model.Syntax;
import org.ontoware.rdf2go.util.RDFTool;

import com.google.common.collect.Lists;

import de.m0ep.socc.utils.SIOCUtils;

public class RDFXMLDataformat implements DataFormat {
    @Override
    public void marshal(Exchange exchange, Object graph, OutputStream stream)
	    throws Exception {
	@SuppressWarnings("unchecked")
	List<Statement> stmts = exchange.getContext().getTypeConverter()
		.mandatoryConvertTo(List.class, graph);

	Model model = SIOCUtils.createDefaultMemoryModel();
	model.addAll(stmts.iterator());
	String xml = RDFTool.modelToString(model, Syntax.RdfXml);
	stream.write(xml.getBytes());

	model.close();
    }

    @Override
    public Object unmarshal(Exchange exchange, InputStream stream)
	    throws Exception {
	String xml = exchange.getContext().getTypeConverter()
		.mandatoryConvertTo(String.class, stream);
	Model model = RDFTool.stringToModel(xml);

	try {
	    return Lists.newArrayList(model.iterator());
	} finally {
	    model.close();
	}
    }
}
