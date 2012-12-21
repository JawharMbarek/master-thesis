package de.m0ep.rdf.sioc;

import java.util.Set;

/**
 * Interface Usergroup sioc:Usergroup - A set of UserAccounts whose owners have
 * a common purpose or interest. Can be used for access control purposes.
 */
public interface Usergroup extends SIOCBase {

    /**
     * sioc:has_member - A UserAccount that is a member of this Usergroup.
     * 
     * @return UserAccount
     */
    public Set<UserAccount> getMembers();

    /**
     * sioc:has_member - A UserAccount that is a member of this Usergroup.
     * 
     * @param account
     */
    public void addMember( UserAccount account );

    /**
     * sioc:has_member - A UserAccount that is a member of this Usergroup.
     * 
     * @param account
     */
    public void removeMember( UserAccount account );

    /**
     * sioc:usergroup_of - A Space that the Usergroup has access to.
     * 
     * @return Space
     */
    public Set<Space> getUsergroupOf();

    /**
     * sioc:usergroup_of - A Space that the Usergroup has access to.
     * 
     * @param space
     */
    public void addUsergroupOf( Space space );

    /**
     * sioc:usergroup_of - A Space that the Usergroup has access to.
     * 
     * @param space
     */
    public void removeUsergroupOf( Space space );

}
