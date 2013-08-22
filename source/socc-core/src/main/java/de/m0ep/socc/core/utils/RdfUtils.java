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
import org.ontoware.rdf2go.RDF2Go;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.Statement;
import org.ontoware.rdf2go.model.Syntax;
import org.ontoware.rdf2go.model.node.Resource;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.model.node.Variable;
import org.ontoware.rdf2go.model.node.impl.URIImpl;
import org.ontoware.rdf2go.util.RDFTool;
import org.ontoware.rdf2go.vocabulary.RDF;
import org.ontoware.rdfreactor.runtime.ReactorRuntimeEntity;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

/**
 * Some utility methods to work with RDF2Go
 * 
 * @author Florian Müller
 */
public final class RdfUtils {

	/**
	 * Private constructor, because this class has only static methods.
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
	public static List<Statement> getAllStatements( final Model model,
	        final Resource resource ) {
		Preconditions.checkNotNull( model, "model can't be null" );
		Preconditions.checkNotNull( resource, "resource can't be null" );
		Preconditions.checkArgument( model.isOpen(), "model should be open" );

		List<Statement> result = new ArrayList<Statement>();
		ClosableIterator<Statement> iter = model.findStatements( resource,
		        Variable.ANY, Variable.ANY );

		while ( iter.hasNext() ) {
			Statement statement = iter.next();
			if ( null != statement )
				result.add( statement );
		}
		iter.close();

		return Collections.unmodifiableList( result );
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
	public static URI createMailtoURI( final String email ) {
		return new URIImpl( "mailto:" + Preconditions.checkNotNull( email ) );
	}

	/**
	 * Returns a {@link List} of all RDF types of a resource in a model.
	 * 
	 * @param model
	 * @param resource
	 * @return
	 */
	public static List<URI> getTypes( final Model model, final Resource resource ) {
		Preconditions.checkNotNull( model,
		        "Required parameter model must be specified." );
		Preconditions.checkNotNull( resource,
		        "Required parameter resource must be specified." );

		List<URI> result = Lists.newArrayList();
		ClosableIterator<Statement> stmtIter = model.findStatements(
		        resource,
		        RDF.type,
		        Variable.ANY );

		try {
			if ( stmtIter.hasNext() ) {
				Statement next = stmtIter.next();
				result.add( (URI) next.getObject() );
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
	public static boolean isType( final Model model, final Resource resource,
	        final URI type ) {
		Preconditions.checkNotNull( model,
		        "Required parameter model must be specified." );
		Preconditions.checkNotNull( resource,
		        "Required parameter resource must be specified." );
		Preconditions.checkNotNull( type,
		        "Required parameter type must be specified." );

		ClosableIterator<Statement> stmtIter = model.findStatements(
		        resource,
		        RDF.type,
		        Variable.ANY );
		try {
			if ( stmtIter.hasNext() ) {
				Statement next = stmtIter.next();
				URI result = next.getObject().asURI();

				if ( result.equals( type ) ) {
					return true;
				}
			}
		} finally {
			stmtIter.close();
		}

		return false;
	}

	/**
	 * Checks if a subclass of {@link ReactorRuntimeEntity} is a specific
	 * <code>type</code>.
	 * 
	 * @param model
	 * @param resource
	 * @param type
	 */
	public static <T extends ReactorRuntimeEntity> boolean isType(
	        final T entity, final URI type ) {
		return isType( entity.getModel(), entity.getResource(), type );
	}

	/**
	 * Converts a {@link Resource} of a {@link Model} into a {@link String} with
	 * the provided {@link Syntax}.
	 * 
	 * @param model
	 *            Model of the resource.
	 * @param resource
	 *            The resource to convert.
	 * @param syntax
	 *            The RDF syntax to use.
	 * @return String representation of the {@link Resource}.
	 */
	public static String resourceToString( final Model model, final Resource resource,
	        final Syntax syntax ) {
		Model tmpModel = RDF2Go.getModelFactory().createModel();
		tmpModel.open();

		try {
			List<Statement> statements = getAllStatements( model, resource );
			tmpModel.addAll( statements.iterator() );
			return RDFTool.modelToString( tmpModel, syntax );
		} finally {
			tmpModel.close();
		}
	}

	/**
	 * Converts a subclass of {@link ReactorRuntimeEntity} into a {@link String}
	 * with the provided {@link Syntax}.
	 * 
	 * @param resource
	 *            The resource to convert.
	 * @param syntax
	 *            The RDF Syntax to use.
	 * @return String representation of the subclass of
	 *         {@link ReactorRuntimeEntity}.
	 */
	public static <T extends ReactorRuntimeEntity> String resourceToString( final T resource,
	        final Syntax syntax ) {
		return resourceToString( resource.getModel(), resource.getResource(), syntax );
	}

}
