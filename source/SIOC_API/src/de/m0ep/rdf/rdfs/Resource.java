package de.m0ep.rdf.rdfs;

import java.util.Set;

import org.openrdf.annotations.Iri;

@Iri( "http://www.w3.org/2000/01/rdf-schema#Resource" )
public interface Resource {

    @Iri( "http://www.w3.org/2000/01/rdf-schema#label" )
    Set<Object> getRdfsLabel();

    @Iri( "http://www.w3.org/2000/01/rdf-schema#label" )
    void setRdfsLabel( Set<?> rdfsLabel );

    @Iri( "http://www.w3.org/2003/06/sw-vocab-status/ns#term_status" )
    Set<Object> getVsTerm_status();

    @Iri( "http://www.w3.org/2003/06/sw-vocab-status/ns#term_status" )
    void setVsTerm_status( Set<?> vsTerm_status );

    @Iri( "http://xmlns.com/wot/0.1/assurance" )
    Set<Object> getWotAssurance();

    @Iri( "http://xmlns.com/wot/0.1/assurance" )
    void setWotAssurance( Set<?> wotAssurance );

    @Iri( "http://xmlns.com/wot/0.1/src_assurance" )
    Set<Object> getWotSrc_assurance();

    @Iri( "http://xmlns.com/wot/0.1/src_assurance" )
    void setWotSrc_assurance( Set<?> wotSrc_assurance );
}
