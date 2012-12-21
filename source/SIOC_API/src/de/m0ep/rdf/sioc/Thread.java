package de.m0ep.rdf.sioc;

import org.openrdf.annotations.Iri;

import de.m0ep.rdf.rdfs.subClassOf;

/** A container for a series of threaded discussion Posts or Items. */
@subClassOf( { "http://rdfs.org/sioc/ns#Container" } )
@Iri( "http://rdfs.org/sioc/ns#Thread" )
public interface Thread extends Container {
}
