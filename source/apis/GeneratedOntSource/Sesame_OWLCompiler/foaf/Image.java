package foaf;

import java.util.Set;

import org.openrdf.annotations.Iri;

import rdfs.subClassOf;

/** An image. */
@subClassOf( { "http://xmlns.com/foaf/0.1/Document" } )
@Iri( "http://xmlns.com/foaf/0.1/Image" )
public interface Image extends Document {
    /** A thing depicted in this representation. */
    @Iri( "http://xmlns.com/foaf/0.1/depicts" )
    Set<Thing> getFoafDepicts();

    /** A thing depicted in this representation. */
    @Iri( "http://xmlns.com/foaf/0.1/depicts" )
    void setFoafDepicts( Set<? extends Thing> foafDepicts );

    /** A derived thumbnail image. */
    @Iri( "http://xmlns.com/foaf/0.1/thumbnail" )
    Set<Image> getFoafThumbnails();

    /** A derived thumbnail image. */
    @Iri( "http://xmlns.com/foaf/0.1/thumbnail" )
    void setFoafThumbnails( Set<? extends Image> foafThumbnails );

}
