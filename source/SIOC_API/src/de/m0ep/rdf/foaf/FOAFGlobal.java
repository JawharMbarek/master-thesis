package de.m0ep.rdf.foaf;

import java.util.Set;

import org.openrdf.annotations.Iri;

import de.m0ep.rdf.vs.term_status;

@Iri( "http://www.w3.org/2000/01/rdf-schema#Resource" )
public interface FOAFGlobal {
    /** A checksum for the DNA of some thing. Joke. */
    @term_status( { "archaic" } )
    @Iri( "http://xmlns.com/foaf/0.1/dnaChecksum" )
    Set<Object> getFoafDnaChecksum();

    /** A checksum for the DNA of some thing. Joke. */
    @term_status( { "archaic" } )
    @Iri( "http://xmlns.com/foaf/0.1/dnaChecksum" )
    void setFoafDnaChecksum( Set<?> foafDnaChecksum );

    /** The given name of some person. */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/givenName" )
    Set<Object> getFoafGivenName();

    /** The given name of some person. */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/givenName" )
    void setFoafGivenName( Set<?> foafGivenName );

    /** The given name of some person. */
    @term_status( { "archaic" } )
    @Iri( "http://xmlns.com/foaf/0.1/givenname" )
    Set<Object> getFoafGivenname();

    /** The given name of some person. */
    @term_status( { "archaic" } )
    @Iri( "http://xmlns.com/foaf/0.1/givenname" )
    void setFoafGivenname( Set<?> foafGivenname );

    /** Indicates the class of individuals that are a member of a Group */
    @term_status( { "unstable" } )
    @Iri( "http://xmlns.com/foaf/0.1/membershipClass" )
    Set<Object> getFoafMembershipClass();

    /** Indicates the class of individuals that are a member of a Group */
    @term_status( { "unstable" } )
    @Iri( "http://xmlns.com/foaf/0.1/membershipClass" )
    void setFoafMembershipClass( Set<?> foafMembershipClass );

    /**
     * A short informal nickname characterising an agent (includes login
     * identifiers, IRC and other chat nicknames).
     */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/nick" )
    Set<Object> getFoafNick();

    /**
     * A short informal nickname characterising an agent (includes login
     * identifiers, IRC and other chat nicknames).
     */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/nick" )
    void setFoafNick( Set<?> foafNick );

    /**
     * A phone, specified using fully qualified tel: URI scheme (refs:
     * http://www.w3.org/Addressing/schemes.html#tel).
     */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/phone" )
    Set<Object> getFoafPhone();

    /**
     * A phone, specified using fully qualified tel: URI scheme (refs:
     * http://www.w3.org/Addressing/schemes.html#tel).
     */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/phone" )
    void setFoafPhone( Set<?> foafPhone );

    /** Title (Mr, Mrs, Ms, Dr. etc) */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/title" )
    Set<Object> getFoafTitle();

    /** Title (Mr, Mrs, Ms, Dr. etc) */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/title" )
    void setFoafTitle( Set<?> foafTitle );
}
