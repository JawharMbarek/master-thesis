package de.m0ep.rdf.geo;

import java.util.Set;

import org.openrdf.annotations.Iri;

import de.m0ep.rdf.vs.term_status;

@Iri( "http://www.w3.org/2003/01/geo/wgs84_pos#SpatialThing" )
public interface SpatialThing {
    /**
     * A location that something is based near, for some broadly human notion of
     * near.
     */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/based_near" )
    Set<SpatialThing> getFoafBased_near();

    /**
     * A location that something is based near, for some broadly human notion of
     * near.
     */
    @term_status( { "testing" } )
    @Iri( "http://xmlns.com/foaf/0.1/based_near" )
    void setFoafBased_near( Set<? extends SpatialThing> foafBased_near );

}
