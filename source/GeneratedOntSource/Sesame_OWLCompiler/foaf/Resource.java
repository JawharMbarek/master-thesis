package foaf;

import java.util.Set;

import org.openrdf.annotations.Iri;

@Iri( "http://www.w3.org/2000/01/rdf-schema#Resource" )
public interface Resource {
    /** A checksum for the DNA of some thing. Joke. */
    @Iri( "http://xmlns.com/foaf/0.1/dnaChecksum" )
    Set<Object> getFoafDnaChecksum();

    /** A checksum for the DNA of some thing. Joke. */
    @Iri( "http://xmlns.com/foaf/0.1/dnaChecksum" )
    void setFoafDnaChecksum( Set<?> foafDnaChecksum );

    /** The given name of some person. */
    @Iri( "http://xmlns.com/foaf/0.1/givenName" )
    Set<Object> getFoafGivenNames();

    /** The given name of some person. */
    @Iri( "http://xmlns.com/foaf/0.1/givenName" )
    void setFoafGivenNames( Set<?> foafGivenNames );

    /** The given name of some person. */
    @Iri( "http://xmlns.com/foaf/0.1/givenname" )
    Set<Object> getFoafGivennames();

    /** The given name of some person. */
    @Iri( "http://xmlns.com/foaf/0.1/givenname" )
    void setFoafGivennames( Set<?> foafGivennames );

    /** Indicates the class of individuals that are a member of a Group */
    @Iri( "http://xmlns.com/foaf/0.1/membershipClass" )
    Set<Object> getFoafMembershipClasses();

    /** Indicates the class of individuals that are a member of a Group */
    @Iri( "http://xmlns.com/foaf/0.1/membershipClass" )
    void setFoafMembershipClasses( Set<?> foafMembershipClasses );

    /**
     * A short informal nickname characterising an agent (includes login
     * identifiers, IRC and other chat nicknames).
     */
    @Iri( "http://xmlns.com/foaf/0.1/nick" )
    Set<Object> getFoafNicks();

    /**
     * A short informal nickname characterising an agent (includes login
     * identifiers, IRC and other chat nicknames).
     */
    @Iri( "http://xmlns.com/foaf/0.1/nick" )
    void setFoafNicks( Set<?> foafNicks );

    /**
     * A phone, specified using fully qualified tel: URI scheme (refs:
     * http://www.w3.org/Addressing/schemes.html#tel).
     */
    @Iri( "http://xmlns.com/foaf/0.1/phone" )
    Set<Object> getFoafPhones();

    /**
     * A phone, specified using fully qualified tel: URI scheme (refs:
     * http://www.w3.org/Addressing/schemes.html#tel).
     */
    @Iri( "http://xmlns.com/foaf/0.1/phone" )
    void setFoafPhones( Set<?> foafPhones );

    /** Title (Mr, Mrs, Ms, Dr. etc) */
    @Iri( "http://xmlns.com/foaf/0.1/title" )
    Set<Object> getFoafTitles();

    /** Title (Mr, Mrs, Ms, Dr. etc) */
    @Iri( "http://xmlns.com/foaf/0.1/title" )
    void setFoafTitles( Set<?> foafTitles );
}
