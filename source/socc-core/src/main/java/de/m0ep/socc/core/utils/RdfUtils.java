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

package de.m0ep.socc.core.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.Statement;
import org.ontoware.rdf2go.model.node.Resource;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.model.node.Variable;
import org.ontoware.rdf2go.model.node.impl.URIImpl;
import org.ontoware.rdf2go.vocabulary.RDF;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

/**
 * Some utility methods to work with RDF2Go
 * 
 * @author Florian Müller
 */
public final class RdfUtils {

    /*
     * Private constructor to avoid creating objects from this class.
     */
    private RdfUtils() {
    }

    /**
     * Returns all {@link Statement}s of a {@link Resource} in a given
     * {@link Model}.
     * 
     * @param model
     *            The model where the statements should be retrieved.
     * @param resource
     *            Resource that should be the subject of the statements.
     * @return An (unmodifiable) list of all statements with the resource as
     *         subject.
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
     * Tries to lock a RDF2Go {@link Model} or waits if its already locked-
     * 
     * @param model
     *            {@link Model} to lock.
     */
    public static void lockModelOrWait(final Model model) {
        Preconditions.checkNotNull(model, "Required parameter model must be specified.");

        while (model.isLocked()) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                // ignore this
            }
        }

        model.lock();
    }

    /**
     * Returns a {@link List} of all RDF types of a resource in a model.
     * 
     * @param model
     * @param resource
     * @return
     */
    public static List<URI> getTypes(final Model model, final Resource resource) {
        Preconditions.checkNotNull(model, "Required parameter model must be specified.");
        Preconditions.checkNotNull(resource, "Required parameter resource must be specified.");

        List<URI> result = Lists.newArrayList();
        ClosableIterator<Statement> stmtIter = model.findStatements(
                resource,
                RDF.type,
                Variable.ANY);

        try {
            if (stmtIter.hasNext()) {
                Statement next = stmtIter.next();
                result.add((URI) next.getObject());
            }
        } finally {
            stmtIter.close();
        }

        return result;
    }

    /**
     * Checks if a <code>resource</code> is a specific <code>type</code> inside
     * a <code>model</code>.
     * 
     * @param model
     * @param resource
     * @param type
     */
    public static boolean isType(final Model model, final Resource resource, final URI type) {
        Preconditions.checkNotNull(model, "Required parameter model must be specified.");
        Preconditions.checkNotNull(resource, "Required parameter resource must be specified.");
        Preconditions.checkNotNull(type, "Required parameter type must be specified.");

        ClosableIterator<Statement> stmtIter = model.findStatements(
                resource,
                RDF.type,
                Variable.ANY);
        try {
            if (stmtIter.hasNext()) {
                Statement next = stmtIter.next();
                URI result = next.getObject().asURI();

                if (result.equals(type)) {
                    return true;
                }
            }
        } finally {
            stmtIter.close();
        }

        return false;
    }
}