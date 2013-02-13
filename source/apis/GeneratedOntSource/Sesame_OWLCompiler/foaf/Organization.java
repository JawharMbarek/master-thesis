package foaf;

import org.openrdf.annotations.Iri;

import rdfs.subClassOf;

/** An organization. */
@subClassOf( { "http://xmlns.com/foaf/0.1/Agent" } )
@Iri( "http://xmlns.com/foaf/0.1/Organization" )
public interface Organization extends Agent {
}
