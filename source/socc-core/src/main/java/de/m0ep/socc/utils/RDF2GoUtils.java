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

package de.m0ep.socc.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.Statement;
import org.ontoware.rdf2go.model.node.Literal;
import org.ontoware.rdf2go.model.node.Resource;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.model.node.Variable;
import org.ontoware.rdf2go.model.node.impl.PlainLiteralImpl;
import org.ontoware.rdf2go.model.node.impl.URIImpl;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

/**
 * Some utility methods to work with RDF2Go
 * 
 * @author Florian Müller
 * 
 */
public final class RDF2GoUtils {

    /*
     * Private constructor to avoid creating objects from this class.
     */
    private RDF2GoUtils(){
    }
    
    /**
     * Resturn all {@link Statement}s of a {@link Resource} in a given
     * {@link Model}.
     * 
     * @param model
     *            The model where the statements should be retrieved.
     * @param resource
     *            Resource that should be the subject of the statements.
     * @return An (unmodifiable) list of all statements with the resource as
     *         subject.
     * 
     * @throws NullPointerException
     *             Thrown if model or resource are <i>null</i>
     * @throws IllegalArgumentException
     *             Thrown if model is not open
     */
    public static List<Statement> getAllStatements(final Model model,
	    final Resource resource) {
	Preconditions.checkNotNull(model, "model can't be null");
	Preconditions.checkNotNull(resource, "resource can't be null");
	Preconditions.checkArgument(model.isOpen(), "model should be open");

	List<Statement> result = new ArrayList<Statement>();
	ClosableIterator<Statement> iter = model.findStatements(resource,
		Variable.ANY, Variable.ANY);

	while (iter.hasNext()) {
	    Statement statement = iter.next();
	    if (null != statement)
		result.add(statement);
	}
	iter.close();

	return Collections.unmodifiableList(result);
    }

    /**
     * Create a valid "mailto" URI for email addresses.
     * 
     * @param email
     *            Email string
     * @return "mailto" URI
     * @throws NullPointerException
     *             Thrown if value was null.
     */
    public static URI createMailtoURI(final String email) {
	return new URIImpl("mailto:" + Preconditions.checkNotNull(email));
    }

    /**
     * Create a XML CDATA Section from a String
     * 
     * @param value
     *            String with encoded data
     * @return "<![CDATA[" + value + "]]>"
     */
    public static Literal createCDATASection(final String value) {
	return new PlainLiteralImpl("<![CDATA["
		+ Strings.nullToEmpty(value).replace("]]>",
			"]]]]><![CDATA[>") + "]]>");
    }
}
