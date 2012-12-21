package de.m0ep.rdf.sioc;

import java.util.Set;

import com.hp.hpl.jena.rdf.model.Resource;

/**
 * Interface Space sioc:Space - A Space is a place where data resides, e.g. on a
 * website, desktop, fileshare, etc.
 */
public interface Space extends SIOCBase {

    /**
     * sioc:has_usergroup - Points to a Usergroup that has certain access to
     * this Space.
     * 
     * @return
     */
    public Set<Usergroup> getUsergroups();

    /**
     * sioc:has_usergroup - Points to a Usergroup that has certain access to
     * this Space.
     * 
     * @param group
     */
    public void addUsergroup( Usergroup group );

    /**
     * sioc:has_usergroup - Points to a Usergroup that has certain access to
     * this Space.
     * 
     * @param group
     */
    public void removeUsergroup( Usergroup group );

    /**
     * sioc:space_of - A resource which belongs to this data Space.
     * 
     * @return RDFNode
     */
    public Resource getSpaceOf();

    /**
     * sioc:space_of - A resource which belongs to this data Space.
     * 
     * @param node
     */
    public void addSpaceOf( Resource resource );

    /**
     * sioc:space_of - A resource which belongs to this data Space.
     * 
     * @param node
     */
    public void removeSpaceOf( Resource resource );
}
