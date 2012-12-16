package foaf;

import org.openrdf.annotations.Iri;

import rdfs.subClassOf;

/** An online gaming account. */
@subClassOf( { "http://xmlns.com/foaf/0.1/OnlineAccount" } )
@Iri( "http://xmlns.com/foaf/0.1/OnlineGamingAccount" )
public interface OnlineGamingAccount extends OnlineAccount {
}
