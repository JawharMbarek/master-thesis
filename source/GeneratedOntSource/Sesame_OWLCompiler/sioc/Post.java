package sioc;

import org.openrdf.annotations.Iri;

import rdfs.subClassOf;
import foaf.Document;

/** An article or message that can be posted to a Forum. */
@subClassOf( { "http://rdfs.org/sioc/ns#Item",
        "http://xmlns.com/foaf/0.1/Document" } )
@Iri( "http://rdfs.org/sioc/ns#Post" )
public interface Post extends Item, Document {

}
