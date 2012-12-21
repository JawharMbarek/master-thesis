package de.m0ep.rdf.foaf;

import org.openrdf.annotations.Iri;

import de.m0ep.rdf.rdfs.subClassOf;
import de.m0ep.rdf.vs.term_status;

/** An organization. */
@term_status( { "stable" } )
@subClassOf( { "http://xmlns.com/foaf/0.1/Agent" } )
@Iri( "http://xmlns.com/foaf/0.1/Organization" )
public interface Organization extends Agent {
}
