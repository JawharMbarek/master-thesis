package foaf;

import java.util.Set;

import org.openrdf.annotations.Iri;

import rdfs.subClassOf;

/** A class of Agents. */
@subClassOf( { "http://xmlns.com/foaf/0.1/Agent" } )
@Iri( "http://xmlns.com/foaf/0.1/Group" )
public interface Group extends Agent {
    /** Indicates a member of a Group */
    @Iri( "http://xmlns.com/foaf/0.1/member" )
    Set<Agent> getFoafMembers();

    /** Indicates a member of a Group */
    @Iri( "http://xmlns.com/foaf/0.1/member" )
    void setFoafMembers( Set<? extends Agent> foafMembers );

}
