package de.m0ep.rdf.foaf;

import java.util.Set;

import org.openrdf.annotations.Iri;

import de.m0ep.rdf.owl.Thing;
import de.m0ep.rdf.rdfs.subClassOf;
import de.m0ep.rdf.vs.term_status;

/** An image. */
@term_status( { "testing" } )
@subClassOf( { "http://xmlns.com/foaf/0.1/Document" } )
@Iri( "http://xmlns.com/foaf/0.1/Image" )
public interface Image extends Document {
    /** A thing depicted in this representation. */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/depicts" )
    Set<Thing> getFoafDepicts();

    /** A thing depicted in this representation. */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/depicts" )
    void setFoafDepicts( Set<? extends Thing> foafDepicts );

    /** A derived thumbnail image. */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/thumbnail" )
    Set<Image> getFoafThumbnail();

    /** A derived thumbnail image. */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/thumbnail" )
    void setFoafThumbnail( Set<? extends Image> foafThumbnail );

}
