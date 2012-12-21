package de.m0ep.rdf.foaf;

import java.util.Set;

import org.openrdf.annotations.Iri;

import de.m0ep.rdf.rdfs.subClassOf;
import de.m0ep.rdf.vs.term_status;

/** A class of Agents. */
@term_status( { "stable" } )
@subClassOf( { "http://xmlns.com/foaf/0.1/Agent" } )
@Iri( "http://xmlns.com/foaf/0.1/Group" )
public interface Group extends Agent {
    /** Indicates a member of a Group */
    @term_status( { "stable" } )
    @Iri( "http://xmlns.com/foaf/0.1/member" )
    Set<Agent> getFoafMember();

    /** Indicates a member of a Group */
    @term_status( { "stable" } )
    @Iri( "http://xmlns.com/foaf/0.1/member" )
    void setFoafMember( Set<? extends Agent> foafMember );

}
