package de.m0ep.rdf.foaf;

import java.util.Set;

import org.openrdf.annotations.Iri;

import de.m0ep.rdf.owl.Thing;
import de.m0ep.rdf.rdfs.subClassOf;
import de.m0ep.rdf.vs.term_status;

/** An online account. */
@term_status( { "testing" } )
@subClassOf( { "http://www.w3.org/2002/07/owl#Thing" } )
@Iri( "http://xmlns.com/foaf/0.1/OnlineAccount" )
public interface OnlineAccount extends FOAFGlobal, Thing {
    /** Indicates the name (identifier) associated with this online account. */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/accountName" )
    Set<Object> getFoafAccountName();

    /** Indicates the name (identifier) associated with this online account. */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/accountName" )
    void setFoafAccountName( Set<?> foafAccountName );

    /** Indicates a homepage of the service provide for this online account. */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/accountServiceHomepage" )
    Set<Document> getFoafAccountServiceHomepage();

    /** Indicates a homepage of the service provide for this online account. */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/accountServiceHomepage" )
    void setFoafAccountServiceHomepage(
            Set<? extends Document> foafAccountServiceHomepage );

}
