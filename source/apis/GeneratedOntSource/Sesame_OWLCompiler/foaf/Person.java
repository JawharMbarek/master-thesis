package foaf;

import geo.SpatialThing;

import java.util.Set;

import org.openrdf.annotations.Iri;

import rdfs.subClassOf;
import rdfs.subPropertyOf;

/** A person. */
@subClassOf( { "http://www.w3.org/2000/10/swap/pim/contact#Person",
        "http://www.w3.org/2003/01/geo/wgs84_pos#SpatialThing",
        "http://xmlns.com/foaf/0.1/Agent" } )
@Iri( "http://xmlns.com/foaf/0.1/Person" )
public interface Person extends SpatialThing, Agent {
    /** A current project this person works on. */
    @Iri( "http://xmlns.com/foaf/0.1/currentProject" )
    Set<Thing> getFoafCurrentProjects();

    /** A current project this person works on. */
    @Iri( "http://xmlns.com/foaf/0.1/currentProject" )
    void setFoafCurrentProjects( Set<? extends Thing> foafCurrentProjects );

    /** The family name of some person. */
    @Iri( "http://xmlns.com/foaf/0.1/familyName" )
    Set<Object> getFoafFamilyNames();

    /** The family name of some person. */
    @Iri( "http://xmlns.com/foaf/0.1/familyName" )
    void setFoafFamilyNames( Set<?> foafFamilyNames );

    /** The family name of some person. */
    @Iri( "http://xmlns.com/foaf/0.1/family_name" )
    Set<Object> getFoafFamily_names();

    /** The family name of some person. */
    @Iri( "http://xmlns.com/foaf/0.1/family_name" )
    void setFoafFamily_names( Set<?> foafFamily_names );

    /** The first name of a person. */
    @Iri( "http://xmlns.com/foaf/0.1/firstName" )
    Set<Object> getFoafFirstNames();

    /** The first name of a person. */
    @Iri( "http://xmlns.com/foaf/0.1/firstName" )
    void setFoafFirstNames( Set<?> foafFirstNames );

    /**
     * A textual geekcode for this person, see http://www.geekcode.com/geek.html
     */
    @Iri( "http://xmlns.com/foaf/0.1/geekcode" )
    Set<Object> getFoafGeekcodes();

    /**
     * A textual geekcode for this person, see http://www.geekcode.com/geek.html
     */
    @Iri( "http://xmlns.com/foaf/0.1/geekcode" )
    void setFoafGeekcodes( Set<?> foafGeekcodes );

    /**
     * An image that can be used to represent some thing (ie. those depictions
     * which are particularly representative of something, eg. one's photo on a
     * homepage).
     */
    @subPropertyOf( { "http://xmlns.com/foaf/0.1/depiction" } )
    @Iri( "http://xmlns.com/foaf/0.1/img" )
    Set<Image> getFoafImgs();

    /**
     * An image that can be used to represent some thing (ie. those depictions
     * which are particularly representative of something, eg. one's photo on a
     * homepage).
     */
    @subPropertyOf( { "http://xmlns.com/foaf/0.1/depiction" } )
    @Iri( "http://xmlns.com/foaf/0.1/img" )
    void setFoafImgs( Set<? extends Image> foafImgs );

    /**
     * A person known by this person (indicating some level of reciprocated
     * interaction between the parties).
     */
    @Iri( "http://xmlns.com/foaf/0.1/knows" )
    Set<Person> getFoafKnows();

    /**
     * A person known by this person (indicating some level of reciprocated
     * interaction between the parties).
     */
    @Iri( "http://xmlns.com/foaf/0.1/knows" )
    void setFoafKnows( Set<? extends Person> foafKnows );

    /** The last name of a person. */
    @Iri( "http://xmlns.com/foaf/0.1/lastName" )
    Set<Object> getFoafLastNames();

    /** The last name of a person. */
    @Iri( "http://xmlns.com/foaf/0.1/lastName" )
    void setFoafLastNames( Set<?> foafLastNames );

    /** A Myers Briggs (MBTI) personality classification. */
    @Iri( "http://xmlns.com/foaf/0.1/myersBriggs" )
    Set<Object> getFoafMyersBriggs();

    /** A Myers Briggs (MBTI) personality classification. */
    @Iri( "http://xmlns.com/foaf/0.1/myersBriggs" )
    void setFoafMyersBriggs( Set<?> foafMyersBriggs );

    /** A project this person has previously worked on. */
    @Iri( "http://xmlns.com/foaf/0.1/pastProject" )
    Set<Thing> getFoafPastProjects();

    /** A project this person has previously worked on. */
    @Iri( "http://xmlns.com/foaf/0.1/pastProject" )
    void setFoafPastProjects( Set<? extends Thing> foafPastProjects );

    /** A .plan comment, in the tradition of finger and '.plan' files. */
    @Iri( "http://xmlns.com/foaf/0.1/plan" )
    Set<Object> getFoafPlans();

    /** A .plan comment, in the tradition of finger and '.plan' files. */
    @Iri( "http://xmlns.com/foaf/0.1/plan" )
    void setFoafPlans( Set<?> foafPlans );

    /** A link to the publications of this person. */
    @Iri( "http://xmlns.com/foaf/0.1/publications" )
    Set<Document> getFoafPublications();

    /** A link to the publications of this person. */
    @Iri( "http://xmlns.com/foaf/0.1/publications" )
    void setFoafPublications( Set<? extends Document> foafPublications );

    /** A homepage of a school attended by the person. */
    @Iri( "http://xmlns.com/foaf/0.1/schoolHomepage" )
    Set<Document> getFoafSchoolHomepages();

    /** A homepage of a school attended by the person. */
    @Iri( "http://xmlns.com/foaf/0.1/schoolHomepage" )
    void setFoafSchoolHomepages( Set<? extends Document> foafSchoolHomepages );

    /** The surname of some person. */
    @Iri( "http://xmlns.com/foaf/0.1/surname" )
    Set<Object> getFoafSurnames();

    /** The surname of some person. */
    @Iri( "http://xmlns.com/foaf/0.1/surname" )
    void setFoafSurnames( Set<?> foafSurnames );

    /**
     * A work info homepage of some person; a page about their work for some
     * organization.
     */
    @Iri( "http://xmlns.com/foaf/0.1/workInfoHomepage" )
    Set<Document> getFoafWorkInfoHomepages();

    /**
     * A work info homepage of some person; a page about their work for some
     * organization.
     */
    @Iri( "http://xmlns.com/foaf/0.1/workInfoHomepage" )
    void setFoafWorkInfoHomepages( Set<? extends Document> foafWorkInfoHomepages );

    /**
     * A workplace homepage of some person; the homepage of an organization they
     * work for.
     */
    @Iri( "http://xmlns.com/foaf/0.1/workplaceHomepage" )
    Set<Document> getFoafWorkplaceHomepages();

    /**
     * A workplace homepage of some person; the homepage of an organization they
     * work for.
     */
    @Iri( "http://xmlns.com/foaf/0.1/workplaceHomepage" )
    void setFoafWorkplaceHomepages(
            Set<? extends Document> foafWorkplaceHomepages );
}
