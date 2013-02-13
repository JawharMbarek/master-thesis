package foaf;

import java.util.Set;

import org.openrdf.annotations.Iri;

import rdfs.subClassOf;

/** An online account. */
@subClassOf( { "http://www.w3.org/2002/07/owl#Thing" } )
@Iri( "http://xmlns.com/foaf/0.1/OnlineAccount" )
public interface OnlineAccount extends Thing {
    /** Indicates the name (identifier) associated with this online account. */
    @Iri( "http://xmlns.com/foaf/0.1/accountName" )
    Set<Object> getFoafAccountNames();

    /** Indicates the name (identifier) associated with this online account. */
    @Iri( "http://xmlns.com/foaf/0.1/accountName" )
    void setFoafAccountNames( Set<?> foafAccountNames );

    /** Indicates a homepage of the service provide for this online account. */
    @Iri( "http://xmlns.com/foaf/0.1/accountServiceHomepage" )
    Set<Document> getFoafAccountServiceHomepages();

    /** Indicates a homepage of the service provide for this online account. */
    @Iri( "http://xmlns.com/foaf/0.1/accountServiceHomepage" )
    void setFoafAccountServiceHomepages(
            Set<? extends Document> foafAccountServiceHomepages );

}
