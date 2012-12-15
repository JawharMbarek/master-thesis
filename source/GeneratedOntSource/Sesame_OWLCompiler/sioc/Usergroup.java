package sioc;

import java.util.Set;

import org.openrdf.annotations.Iri;

/**
 * A set of UserAccounts whose owners have a common purpose or interest. Can be
 * used for access control purposes.
 */
@Iri( "http://rdfs.org/sioc/ns#Usergroup" )
public interface Usergroup extends Resource {
    /** A UserAccount that is a member of this Usergroup. */
    @Iri( "http://rdfs.org/sioc/ns#has_member" )
    Set<UserAccount> getSiocHas_members();

    /** A UserAccount that is a member of this Usergroup. */
    @Iri( "http://rdfs.org/sioc/ns#has_member" )
    void setSiocHas_members( Set<? extends UserAccount> siocHas_members );

    /** A Space that the Usergroup has access to. */
    @Iri( "http://rdfs.org/sioc/ns#usergroup_of" )
    Set<Space> getSiocUsergroup_of();

    /** A Space that the Usergroup has access to. */
    @Iri( "http://rdfs.org/sioc/ns#usergroup_of" )
    void setSiocUsergroup_of( Set<? extends Space> siocUsergroup_of );

}
