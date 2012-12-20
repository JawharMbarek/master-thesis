package de.m0ep.rdf.sioc;

import java.math.BigInteger;
import java.util.Set;

import org.openrdf.annotations.Iri;

import de.m0ep.rdf.rdfs.subClassOf;

/** A discussion area on which Posts or entries are made. */
@subClassOf( { "http://rdfs.org/sioc/ns#Container" } )
@Iri( "http://rdfs.org/sioc/ns#Forum" )
public interface Forum extends Container {
    /** The Site that hosts this Forum. */
    @Iri( "http://rdfs.org/sioc/ns#has_host" )
    Set<Site> getSiocHas_host();

    /** The Site that hosts this Forum. */
    @Iri( "http://rdfs.org/sioc/ns#has_host" )
    void setSiocHas_host( Set<? extends Site> siocHas_host );

    /** A UserAccount that is a moderator of this Forum. */
    @Iri( "http://rdfs.org/sioc/ns#has_moderator" )
    Set<UserAccount> getSiocHas_moderator();

    /** A UserAccount that is a moderator of this Forum. */
    @Iri( "http://rdfs.org/sioc/ns#has_moderator" )
    void setSiocHas_moderator( Set<? extends UserAccount> siocHas_moderator );

    /** The number of Threads (AKA discussion topics) in a Forum. */
    @Iri( "http://rdfs.org/sioc/ns#num_threads" )
    Set<BigInteger> getSiocNum_threads();

    /** The number of Threads (AKA discussion topics) in a Forum. */
    @Iri( "http://rdfs.org/sioc/ns#num_threads" )
    void setSiocNum_threads( Set<? extends BigInteger> siocNum_threads );

}
