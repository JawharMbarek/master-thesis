package foaf;

import java.util.Set;

import org.openrdf.annotations.Iri;

import rdfs.subPropertyOf;
import sioc.Resource;

@Iri( "http://www.w3.org/2002/07/owl#Thing" )
public interface Thing extends Resource {
    /** A depiction of some thing. */
    @Iri( "http://xmlns.com/foaf/0.1/depiction" )
    Set<Image> getFoafDepictions();

    /** A depiction of some thing. */
    @Iri( "http://xmlns.com/foaf/0.1/depiction" )
    void setFoafDepictions( Set<? extends Image> foafDepictions );

    /** An organization funding a project or person. */
    @Iri( "http://xmlns.com/foaf/0.1/fundedBy" )
    Set<Thing> getFoafFundedBy();

    /** An organization funding a project or person. */
    @Iri( "http://xmlns.com/foaf/0.1/fundedBy" )
    void setFoafFundedBy( Set<? extends Thing> foafFundedBy );

    /** A homepage for some thing. */
    @subPropertyOf( { "http://xmlns.com/foaf/0.1/isPrimaryTopicOf",
            "http://xmlns.com/foaf/0.1/page" } )
    @Iri( "http://xmlns.com/foaf/0.1/homepage" )
    Set<Document> getFoafHomepages();

    /** A homepage for some thing. */
    @subPropertyOf( { "http://xmlns.com/foaf/0.1/isPrimaryTopicOf",
            "http://xmlns.com/foaf/0.1/page" } )
    @Iri( "http://xmlns.com/foaf/0.1/homepage" )
    void setFoafHomepages( Set<? extends Document> foafHomepages );

    /** A document that this thing is the primary topic of. */
    @subPropertyOf( { "http://xmlns.com/foaf/0.1/page" } )
    @Iri( "http://xmlns.com/foaf/0.1/isPrimaryTopicOf" )
    Set<Document> getFoafIsPrimaryTopicOf();

    /** A document that this thing is the primary topic of. */
    @subPropertyOf( { "http://xmlns.com/foaf/0.1/page" } )
    @Iri( "http://xmlns.com/foaf/0.1/isPrimaryTopicOf" )
    void setFoafIsPrimaryTopicOf( Set<? extends Document> foafIsPrimaryTopicOf );

    /** A logo representing some thing. */
    @Iri( "http://xmlns.com/foaf/0.1/logo" )
    Set<Thing> getFoafLogos();

    /** A logo representing some thing. */
    @Iri( "http://xmlns.com/foaf/0.1/logo" )
    void setFoafLogos( Set<? extends Thing> foafLogos );

    /** An agent that made this thing. */
    @Iri( "http://xmlns.com/foaf/0.1/maker" )
    Set<Agent> getFoafMakers();

    /** An agent that made this thing. */
    @Iri( "http://xmlns.com/foaf/0.1/maker" )
    void setFoafMakers( Set<? extends Agent> foafMakers );

    /** A name for some thing. */
    @subPropertyOf( { "http://www.w3.org/2000/01/rdf-schema#label" } )
    @Iri( "http://xmlns.com/foaf/0.1/name" )
    Set<Object> getFoafNames();

    /** A name for some thing. */
    @subPropertyOf( { "http://www.w3.org/2000/01/rdf-schema#label" } )
    @Iri( "http://xmlns.com/foaf/0.1/name" )
    void setFoafNames( Set<?> foafNames );

    /** A page or document about this thing. */
    @Iri( "http://xmlns.com/foaf/0.1/page" )
    Set<Document> getFoafPages();

    /** A page or document about this thing. */
    @Iri( "http://xmlns.com/foaf/0.1/page" )
    void setFoafPages( Set<? extends Document> foafPages );

    /** A theme. */
    @Iri( "http://xmlns.com/foaf/0.1/theme" )
    Set<Thing> getFoafThemes();

    /** A theme. */
    @Iri( "http://xmlns.com/foaf/0.1/theme" )
    void setFoafThemes( Set<? extends Thing> foafThemes );

}
