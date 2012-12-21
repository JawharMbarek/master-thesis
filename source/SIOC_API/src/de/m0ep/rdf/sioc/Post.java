package de.m0ep.rdf.sioc;

import org.openrdf.annotations.Iri;

import de.m0ep.rdf.foaf.Document;
import de.m0ep.rdf.rdfs.subClassOf;

/** An article or message that can be posted to a Forum. */
@subClassOf( { "http://rdfs.org/sioc/ns#Item",
        "http://xmlns.com/foaf/0.1/Document" } )
@Iri( "http://rdfs.org/sioc/ns#Post" )
public interface Post extends Item, Document {
}
