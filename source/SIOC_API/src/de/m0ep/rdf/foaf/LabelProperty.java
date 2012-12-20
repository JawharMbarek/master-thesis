package de.m0ep.rdf.foaf;

import org.openrdf.annotations.Iri;

import de.m0ep.rdf.vs.term_status;

/**
 * A foaf:LabelProperty is any RDF property with texual values that serve as
 * labels.
 */
@term_status( { "unstable" } )
@Iri( "http://xmlns.com/foaf/0.1/LabelProperty" )
public interface LabelProperty extends FOAFGlobal {
}
