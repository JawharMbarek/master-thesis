package foaf;

import org.openrdf.annotations.Iri;

import rdfs.subClassOf;

/** A personal profile RDF document. */
@subClassOf( { "http://xmlns.com/foaf/0.1/Document" } )
@Iri( "http://xmlns.com/foaf/0.1/PersonalProfileDocument" )
public interface PersonalProfileDocument extends Document {
}
