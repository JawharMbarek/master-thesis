package foaf;

import org.openrdf.annotations.Iri;

import sioc.Resource;

/**
 * A foaf:LabelProperty is any RDF property with texual values that serve as
 * labels.
 */
@Iri( "http://xmlns.com/foaf/0.1/LabelProperty" )
public interface LabelProperty extends Resource {
}
