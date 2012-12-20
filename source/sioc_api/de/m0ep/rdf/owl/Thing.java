package de.m0ep.rdf.owl;

import java.util.Set;

import org.openrdf.annotations.Iri;

import de.m0ep.rdf.foaf.Agent;
import de.m0ep.rdf.foaf.Document;
import de.m0ep.rdf.foaf.Image;
import de.m0ep.rdf.rdfs.subPropertyOf;
import de.m0ep.rdf.vs.term_status;

@Iri( "http://www.w3.org/2002/07/owl#Thing" )
public interface Thing {
    /** A depiction of some thing. */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/depiction" )
    Set<Image> getFoafDepiction();

    /** A depiction of some thing. */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/depiction" )
    void setFoafDepiction( Set<? extends Image> foafDepiction );

    /** An organization funding a project or person. */
    @term_status( { "archaic" } )
    @Iri( "http://xmlns.com/foaf/0.1/fundedBy" )
    Set<Thing> getFoafFundedBy();

    /** An organization funding a project or person. */
    @term_status( { "archaic" } )
    @Iri( "http://xmlns.com/foaf/0.1/fundedBy" )
    void setFoafFundedBy( Set<? extends Thing> foafFundedBy );

    /** A homepage for some thing. */
    @term_status( { "stable" } )
    @subPropertyOf( { "http://xmlns.com/foaf/0.1/isPrimaryTopicOf",
            "http://xmlns.com/foaf/0.1/page" } )
    @Iri( "http://xmlns.com/foaf/0.1/homepage" )
    Set<Document> getFoafHomepage();

    /** A homepage for some thing. */
    @term_status( { "stable" } )
    @subPropertyOf( { "http://xmlns.com/foaf/0.1/isPrimaryTopicOf",
            "http://xmlns.com/foaf/0.1/page" } )
    @Iri( "http://xmlns.com/foaf/0.1/homepage" )
    void setFoafHomepage( Set<? extends Document> foafHomepage );

    /** A document that this thing is the primary topic of. */
    @term_status( { "stable" } )
    @subPropertyOf( { "http://xmlns.com/foaf/0.1/page" } )
    @Iri( "http://xmlns.com/foaf/0.1/isPrimaryTopicOf" )
    Set<Document> getFoafIsPrimaryTopicOf();

    /** A document that this thing is the primary topic of. */
    @term_status( { "stable" } )
    @subPropertyOf( { "http://xmlns.com/foaf/0.1/page" } )
    @Iri( "http://xmlns.com/foaf/0.1/isPrimaryTopicOf" )
    void setFoafIsPrimaryTopicOf( Set<? extends Document> foafIsPrimaryTopicOf );

    /** A logo representing some thing. */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/logo" )
    Set<Thing> getFoafLogo();

    /** A logo representing some thing. */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/logo" )
    void setFoafLogo( Set<? extends Thing> foafLogo );

    /** An agent that made this thing. */
    @term_status( { "stable" } )
    @Iri( "http://xmlns.com/foaf/0.1/maker" )
    Set<Agent> getFoafMaker();

    /** An agent that made this thing. */
    @term_status( { "stable" } )
    @Iri( "http://xmlns.com/foaf/0.1/maker" )
    void setFoafMaker( Set<? extends Agent> foafMaker );

    /** A name for some thing. */
    @term_status( { "testing" } )
    @subPropertyOf( { "http://www.w3.org/2000/01/rdf-schema#label" } )
    @Iri( "http://xmlns.com/foaf/0.1/name" )
    Set<Object> getFoafName();

    /** A name for some thing. */
    @term_status( { "testing" } )
    @subPropertyOf( { "http://www.w3.org/2000/01/rdf-schema#label" } )
    @Iri( "http://xmlns.com/foaf/0.1/name" )
    void setFoafName( Set<?> foafName );

    /** A page or document about this thing. */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/page" )
    Set<Document> getFoafPage();

    /** A page or document about this thing. */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/page" )
    void setFoafPage( Set<? extends Document> foafPage );

    /** A theme. */
    @term_status( { "archaic" } )
    @Iri( "http://xmlns.com/foaf/0.1/theme" )
    Set<Thing> getFoafTheme();

    /** A theme. */
    @term_status( { "archaic" } )
    @Iri( "http://xmlns.com/foaf/0.1/theme" )
    void setFoafTheme( Set<? extends Thing> foafTheme );

}
