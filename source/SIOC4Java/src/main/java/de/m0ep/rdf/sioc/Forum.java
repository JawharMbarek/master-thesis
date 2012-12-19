package de.m0ep.rdf.sioc;

import java.util.Set;

/**
 * Interface Forum sioc:Forum - A discussion area on which Posts or entries are
 * made.
 */
public interface Forum extends Container {

    /**
     * sioc:Forum - A discussion area on which Posts or entries are made.
     * 
     * @return de.m0ep.rdf.sioc.Site
     */
    public Site getHost();

    /**
     * sioc:Forum - A discussion area on which Posts or entries are made.
     * 
     * @param host
     */
    public void setHost( Site host );

    /**
     * sioc:has_moderator - A UserAccount that is a moderator of this Forum.
     */
    public Set<UserAccount> getModerators();

    /**
     * sioc:has_moderator - A UserAccount that is a moderator of this Forum.
     * 
     * @param account
     */
    public void addModerator( UserAccount account );

    /**
     * sioc:has_moderator - A UserAccount that is a moderator of this Forum.
     * 
     * @param account
     */
    public void removeModerator( UserAccount account );

    /**
     * sioc:num_threads - The number of Threads (AKA discussion topics) in a
     * Forum.
     * 
     * @return int
     */
    public int getNumThreads();

    /**
     * sioc:num_threads - The number of Threads (AKA discussion topics) in a
     * Forum.
     * 
     * @param numThreads
     */
    public void setNumthreads( Integer numThreads );

}
