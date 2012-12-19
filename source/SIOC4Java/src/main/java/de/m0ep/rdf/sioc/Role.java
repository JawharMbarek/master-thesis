package de.m0ep.rdf.sioc;

import java.util.Set;

import com.hp.hpl.jena.rdf.model.Resource;

/**
 * Interface Role sioc:Role - A Role is a function of a UserAccount within a
 * scope of a particular Forum, Site, etc.
 */
public interface Role extends SIOCBase {

    /**
     * sioc:function_of - A UserAccount that has this Role.
     */
    public Set<UserAccount> getFunctionOf();

    /**
     * sioc:function_of - A UserAccount that has this Role.
     * 
     * @param account
     */
    public void addFunctionOf( UserAccount account );

    /**
     * sioc:function_of - A UserAccount that has this Role.
     * 
     * @param account
     */
    public void removeFunctionOf( UserAccount account );

    /**
     * sioc:has_scope - A resource that this Role applies to.
     */
    public Set<Resource> getScopes();

    /**
     * sioc:has_scope - A resource that this Role applies to.
     * 
     * @param node
     */
    public void addScope( Resource resource );

    /**
     * sioc:has_scope - A resource that this Role applies to.
     * 
     * @param node
     */
    public void removeScope( Resource resource );

}
