package de.m0ep.rdf.skos;

import java.util.Set;

import org.openrdf.annotations.Iri;

import de.m0ep.rdf.owl.Thing;
import de.m0ep.rdf.vs.term_status;

@Iri( "http://www.w3.org/2004/02/skos/core#Concept" )
public interface Concept {
    /**
     * The underlying or 'focal' entity associated with some SKOS-described
     * concept.
     */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/focus" )
    Set<Thing> getFoafFocus();

    /**
     * The underlying or 'focal' entity associated with some SKOS-described
     * concept.
     */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/focus" )
    void setFoafFocus( Set<? extends Thing> foafFocus );

}
