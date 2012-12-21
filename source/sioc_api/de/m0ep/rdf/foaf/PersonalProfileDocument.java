package de.m0ep.rdf.foaf;

import org.openrdf.annotations.Iri;

import de.m0ep.rdf.rdfs.subClassOf;
import de.m0ep.rdf.vs.term_status;

/** A personal profile RDF document. */
@term_status( { "testing" } )
@subClassOf( { "http://xmlns.com/foaf/0.1/Document" } )
@Iri( "http://xmlns.com/foaf/0.1/PersonalProfileDocument" )
public interface PersonalProfileDocument extends Document {
}
