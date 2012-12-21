package de.m0ep.rdf.foaf;

import java.util.Set;

import org.openrdf.annotations.Iri;

import de.m0ep.rdf.geo.SpatialThing;
import de.m0ep.rdf.owl.Thing;
import de.m0ep.rdf.rdfs.subClassOf;
import de.m0ep.rdf.rdfs.subPropertyOf;
import de.m0ep.rdf.vs.term_status;

/** A person. */
@term_status( { "stable" } )
@subClassOf( { "http://www.w3.org/2000/10/swap/pim/contact#Person",
        "http://www.w3.org/2003/01/geo/wgs84_pos#SpatialThing",
        "http://xmlns.com/foaf/0.1/Agent" } )
@Iri( "http://xmlns.com/foaf/0.1/Person" )
public interface Person extends de.m0ep.rdf._con.Person, SpatialThing, Agent {
    /** A current project this person works on. */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/currentProject" )
    Set<Thing> getFoafCurrentProject();

    /** A current project this person works on. */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/currentProject" )
    void setFoafCurrentProject( Set<? extends Thing> foafCurrentProject );

    /** The family name of some person. */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/familyName" )
    Set<Object> getFoafFamilyName();

    /** The family name of some person. */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/familyName" )
    void setFoafFamilyName( Set<?> foafFamilyName );

    /** The family name of some person. */
    @term_status( { "archaic" } )
    @Iri( "http://xmlns.com/foaf/0.1/family_name" )
    Set<Object> getFoafFamily_name();

    /** The family name of some person. */
    @term_status( { "archaic" } )
    @Iri( "http://xmlns.com/foaf/0.1/family_name" )
    void setFoafFamily_name( Set<?> foafFamily_name );

    /** The first name of a person. */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/firstName" )
    Set<Object> getFoafFirstName();

    /** The first name of a person. */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/firstName" )
    void setFoafFirstName( Set<?> foafFirstName );

    /**
     * A textual geekcode for this person, see http://www.geekcode.com/geek.html
     */
    @term_status( { "archaic" } )
    @Iri( "http://xmlns.com/foaf/0.1/geekcode" )
    Set<Object> getFoafGeekcode();

    /**
     * A textual geekcode for this person, see http://www.geekcode.com/geek.html
     */
    @term_status( { "archaic" } )
    @Iri( "http://xmlns.com/foaf/0.1/geekcode" )
    void setFoafGeekcode( Set<?> foafGeekcode );

    /**
     * An image that can be used to represent some thing (ie. those depictions
     * which are particularly representative of something, eg. one's photo on a
     * homepage).
     */
    @term_status( { "testing" } )
    @subPropertyOf( { "http://xmlns.com/foaf/0.1/depiction" } )
    @Iri( "http://xmlns.com/foaf/0.1/img" )
    Set<Image> getFoafImg();

    /**
     * An image that can be used to represent some thing (ie. those depictions
     * which are particularly representative of something, eg. one's photo on a
     * homepage).
     */
    @term_status( { "testing" } )
    @subPropertyOf( { "http://xmlns.com/foaf/0.1/depiction" } )
    @Iri( "http://xmlns.com/foaf/0.1/img" )
    void setFoafImg( Set<? extends Image> foafImg );

    /**
     * A person known by this person (indicating some level of reciprocated
     * interaction between the parties).
     */
    @term_status( { "stable" } )
    @Iri( "http://xmlns.com/foaf/0.1/knows" )
    Set<Person> getFoafKnows();

    /**
     * A person known by this person (indicating some level of reciprocated
     * interaction between the parties).
     */
    @term_status( { "stable" } )
    @Iri( "http://xmlns.com/foaf/0.1/knows" )
    void setFoafKnows( Set<? extends Person> foafKnows );

    /** The last name of a person. */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/lastName" )
    Set<Object> getFoafLastName();

    /** The last name of a person. */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/lastName" )
    void setFoafLastName( Set<?> foafLastName );

    /** A Myers Briggs (MBTI) personality classification. */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/myersBriggs" )
    Set<Object> getFoafMyersBriggs();

    /** A Myers Briggs (MBTI) personality classification. */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/myersBriggs" )
    void setFoafMyersBriggs( Set<?> foafMyersBriggs );

    /** A project this person has previously worked on. */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/pastProject" )
    Set<Thing> getFoafPastProject();

    /** A project this person has previously worked on. */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/pastProject" )
    void setFoafPastProject( Set<? extends Thing> foafPastProject );

    /** A .plan comment, in the tradition of finger and '.plan' files. */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/plan" )
    Set<Object> getFoafPlan();

    /** A .plan comment, in the tradition of finger and '.plan' files. */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/plan" )
    void setFoafPlan( Set<?> foafPlan );

    /** A link to the publications of this person. */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/publications" )
    Set<Document> getFoafPublications();

    /** A link to the publications of this person. */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/publications" )
    void setFoafPublications( Set<? extends Document> foafPublications );

    /** A homepage of a school attended by the person. */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/schoolHomepage" )
    Set<Document> getFoafSchoolHomepage();

    /** A homepage of a school attended by the person. */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/schoolHomepage" )
    void setFoafSchoolHomepage( Set<? extends Document> foafSchoolHomepage );

    /** The surname of some person. */
    @term_status( { "archaic" } )
    @Iri( "http://xmlns.com/foaf/0.1/surname" )
    Set<Object> getFoafSurname();

    /** The surname of some person. */
    @term_status( { "archaic" } )
    @Iri( "http://xmlns.com/foaf/0.1/surname" )
    void setFoafSurname( Set<?> foafSurname );

    /**
     * A work info homepage of some person; a page about their work for some
     * organization.
     */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/workInfoHomepage" )
    Set<Document> getFoafWorkInfoHomepage();

    /**
     * A work info homepage of some person; a page about their work for some
     * organization.
     */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/workInfoHomepage" )
    void setFoafWorkInfoHomepage( Set<? extends Document> foafWorkInfoHomepage );

    /**
     * A workplace homepage of some person; the homepage of an organization they
     * work for.
     */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/workplaceHomepage" )
    Set<Document> getFoafWorkplaceHomepage();

    /**
     * A workplace homepage of some person; the homepage of an organization they
     * work for.
     */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/workplaceHomepage" )
    void setFoafWorkplaceHomepage( Set<? extends Document> foafWorkplaceHomepage );

}
