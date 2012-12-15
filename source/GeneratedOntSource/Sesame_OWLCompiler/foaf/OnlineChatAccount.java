package foaf;

import org.openrdf.annotations.Iri;

import rdfs.subClassOf;

/** An online chat account. */
@subClassOf( { "http://xmlns.com/foaf/0.1/OnlineAccount" } )
@Iri( "http://xmlns.com/foaf/0.1/OnlineChatAccount" )
public interface OnlineChatAccount extends OnlineAccount {
}
