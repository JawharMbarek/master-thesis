/*
 * The MIT License (MIT) Copyright © 2013 Florian Müller
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the “Software”), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package de.m0ep.camel.socc.data;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Route;
import org.apache.camel.TypeConverter;
import org.apache.camel.spi.DataFormat;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.Syntax;
import org.ontoware.rdf2go.util.RDFTool;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.SIOC;

import de.m0ep.socc.SOCC;
import de.m0ep.socc.utils.RDF2GoUtils;

/**
 * The {@link RDFXMLDataformat} class is used by Apache camel to convert
 * {@link SIOCPostData} objects to a RDF/XML String and vice versa in a
 * {@link Route}.
 * 
 * @author Florian Müller
 * 
 */
public class RDFXMLDataformat implements DataFormat {
    @Override
    public void marshal(Exchange exchange, Object graph, OutputStream stream)
	    throws Exception {
	TypeConverter typeConverter = exchange.getContext().getTypeConverter();
	SIOCPostData postData = typeConverter.mandatoryConvertTo(
		SIOCPostData.class,
		graph);

	Model model = SOCC.createDefaultMemoryModel();

	try {
	    model.addAll(postData.getRDFStatements().iterator());
	    String xml = RDFTool.modelToString(model, Syntax.RdfXml);
	    stream.write(xml.getBytes());
	} finally {
	    model.close();
	}
    }

    @Override
    public Object unmarshal(Exchange exchange, InputStream stream)
	    throws Exception {
	TypeConverter typeConverter = exchange.getContext().getTypeConverter();
	String xml = typeConverter.mandatoryConvertTo(String.class, stream);
	Model model = RDFTool.stringToModel(xml);

	try {
	    List<Post> postList = SIOC.listAllPosts(model);

	    if (!postList.isEmpty()) {
		Post post = postList.get(0);
		return new SIOCPostData(
			post.asURI().toString(),
			RDF2GoUtils.getAllStatements(model, post));
	    }
	} finally {
	    model.close();
	}

	return null;
    }
}
