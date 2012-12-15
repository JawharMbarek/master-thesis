package foaf;

import org.openrdf.annotations.Iri;

import rdfs.subClassOf;

/** An online e-commerce account. */
@subClassOf( { "http://xmlns.com/foaf/0.1/OnlineAccount" } )
@Iri( "http://xmlns.com/foaf/0.1/OnlineEcommerceAccount" )
public interface OnlineEcommerceAccount extends OnlineAccount {
}
