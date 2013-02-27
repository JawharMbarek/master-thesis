package de.m0ep.socc.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.Statement;
import org.ontoware.rdf2go.model.node.Literal;
import org.ontoware.rdf2go.model.node.PlainLiteral;
import org.ontoware.rdf2go.model.node.Resource;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.model.node.Variable;
import org.ontoware.rdf2go.model.node.impl.PlainLiteralImpl;
import org.ontoware.rdf2go.model.node.impl.URIImpl;

import com.google.common.base.Preconditions;

/**
 * Some utility methods to work with RDF2Go
 * 
 * @author Florian Müller
 * 
 */
public final class RDF2GoUtils {

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
	    Statement statement = (Statement) iter.next();
	    if (null != statement)
		result.add(statement);
	}
	iter.close();

	return Collections.unmodifiableList(result);
    }

    /**
     * Create a RDF2Go URI with the given value.
     * 
     * @param value
     *            Value fro the URI
     * @return A RDF2Go URI from value
     * @throws NullPointerException
     *             Thrown if value was null.
     * @throws IllegalArgumentException
     *             Thrown if value is no valid URI
     */
    public static URI createURI(final String value) {
	return new URIImpl(Preconditions.checkNotNull(value), true);
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
     * Create a RDF {@link Literal} with the given value.
     * 
     * @param value
     *            Value for the literal
     * @return A RDF2Go {@link PlainLiteral}
     */
    public static Literal createLiteral(final String value) {
	return new PlainLiteralImpl(StringUtils.trimToEmpty(value));
    }

    /**
     * Create a XML CDATA Section from a String
     * 
     * @param value
     *            String with encoded data
     * @return "<![CDATA[" + value + "]]>"
     */
    public static String createCDATASection(final String value) {
	return "<![CDATA["
		+ StringUtils.trimToEmpty(value).replace("]]>",
			"]]]]><![CDATA[>") + "]]>";
    }
}
