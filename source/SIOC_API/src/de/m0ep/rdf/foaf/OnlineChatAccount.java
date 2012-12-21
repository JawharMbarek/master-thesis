package de.m0ep.rdf.foaf;

import org.openrdf.annotations.Iri;

import de.m0ep.rdf.rdfs.subClassOf;
import de.m0ep.rdf.vs.term_status;

/** An online chat account. */
@term_status( { "unstable" } )
@subClassOf( { "http://xmlns.com/foaf/0.1/OnlineAccount" } )
@Iri( "http://xmlns.com/foaf/0.1/OnlineChatAccount" )
public interface OnlineChatAccount extends OnlineAccount {
}
