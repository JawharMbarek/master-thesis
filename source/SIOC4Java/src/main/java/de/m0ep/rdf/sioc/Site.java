package de.m0ep.rdf.sioc;

import java.util.Set;

/**
 * Interface Site
 */
public interface Site extends Space {

    /**
     * sioc:has_administrator - A UserAccount that is an administrator of this
     * Site.
     * 
     * @return Set
     */
    public Set<UserAccount> getAdministrators();

    /**
     * sioc:has_administrator - A UserAccount that is an administrator of this
     * Site.
     * 
     * @param account
     */
    public void addAdministrator( UserAccount account );

    /**
     * sioc:has_administrator - A UserAccount that is an administrator of this
     * Site.
     * 
     * @param account
     */
    public void removeAdministrator( UserAccount account );

    /**
     * sioc:host_of - A Forum that is hosted on this Site.
     * 
     * @return de.m0ep.rdf.sioc.Forum
     */
    public Set<Forum> getHostOf();

    /**
     * sioc:host_of - A Forum that is hosted on this Site.
     * 
     * @param forum
     */
    public void addHostOf( Forum forum );

    /**
     * sioc:host_of - A Forum that is hosted on this Site.
     * 
     * @param forum
     */
    public void removeHostOf( Forum forum );
}
