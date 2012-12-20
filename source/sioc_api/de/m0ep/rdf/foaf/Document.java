package de.m0ep.rdf.foaf;

import java.util.Set;

import org.openrdf.annotations.Iri;

import de.m0ep.rdf.owl.Thing;
import de.m0ep.rdf.vs.term_status;

/** A document. */
@term_status( { "testing" } )
@Iri( "http://xmlns.com/foaf/0.1/Document" )
public interface Document extends FOAFGlobal {
    /** The primary topic of some page or document. */
    @term_status( { "stable" } )
    @Iri( "http://xmlns.com/foaf/0.1/primaryTopic" )
    Thing getFoafPrimaryTopic();

    /** The primary topic of some page or document. */
    @term_status( { "stable" } )
    @Iri( "http://xmlns.com/foaf/0.1/primaryTopic" )
    void setFoafPrimaryTopic( Thing foafPrimaryTopic );

    /** A sha1sum hash, in hex. */
    @term_status( { "unstable" } )
    @Iri( "http://xmlns.com/foaf/0.1/sha1" )
    Set<Object> getFoafSha1();

    /** A sha1sum hash, in hex. */
    @term_status( { "unstable" } )
    @Iri( "http://xmlns.com/foaf/0.1/sha1" )
    void setFoafSha1( Set<?> foafSha1 );

    /** A topic of some page or document. */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/topic" )
    Set<Thing> getFoafTopic();

    /** A topic of some page or document. */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/topic" )
    void setFoafTopic( Set<? extends Thing> foafTopic );

}
