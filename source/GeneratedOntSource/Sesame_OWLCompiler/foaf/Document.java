package foaf;

import java.util.Set;

import org.openrdf.annotations.Iri;

import sioc.Resource;

/** A document. */
@Iri( "http://xmlns.com/foaf/0.1/Document" )
public interface Document extends Resource {
    /** The primary topic of some page or document. */
    @Iri( "http://xmlns.com/foaf/0.1/primaryTopic" )
    Thing getFoafPrimaryTopic();

    /** The primary topic of some page or document. */
    @Iri( "http://xmlns.com/foaf/0.1/primaryTopic" )
    void setFoafPrimaryTopic( Thing foafPrimaryTopic );

    /** A sha1sum hash, in hex. */
    @Iri( "http://xmlns.com/foaf/0.1/sha1" )
    Set<Object> getFoafSha1s();

    /** A sha1sum hash, in hex. */
    @Iri( "http://xmlns.com/foaf/0.1/sha1" )
    void setFoafSha1s( Set<?> foafSha1s );

    /** A topic of some page or document. */
    @Iri( "http://xmlns.com/foaf/0.1/topic" )
    Set<Thing> getFoafTopics();

    /** A topic of some page or document. */
    @Iri( "http://xmlns.com/foaf/0.1/topic" )
    void setFoafTopics( Set<? extends Thing> foafTopics );

}
