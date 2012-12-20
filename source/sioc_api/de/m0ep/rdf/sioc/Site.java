package de.m0ep.rdf.sioc;

import java.util.Set;

import org.openrdf.annotations.Iri;

import de.m0ep.rdf.rdfs.subClassOf;

/**
 * A Site can be the location of an online community or set of communities, with
 * UserAccounts and Usergroups creating Items in a set of Containers. It can be
 * thought of as a web-accessible data Space.
 */
@subClassOf( { "http://rdfs.org/sioc/ns#Space" } )
@Iri( "http://rdfs.org/sioc/ns#Site" )
public interface Site extends Space {
    /** A UserAccount that is an administrator of this Site. */
    @Iri( "http://rdfs.org/sioc/ns#has_administrator" )
    Set<UserAccount> getSiocHas_administrator();

    /** A UserAccount that is an administrator of this Site. */
    @Iri( "http://rdfs.org/sioc/ns#has_administrator" )
    void setSiocHas_administrator(
            Set<? extends UserAccount> siocHas_administrator );

    /** A Forum that is hosted on this Site. */
    @Iri( "http://rdfs.org/sioc/ns#host_of" )
    Set<Forum> getSiocHost_of();

    /** A Forum that is hosted on this Site. */
    @Iri( "http://rdfs.org/sioc/ns#host_of" )
    void setSiocHost_of( Set<? extends Forum> siocHost_of );

}
