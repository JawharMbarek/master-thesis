package de.m0ep.socc.utils;

import org.ontoware.rdf2go.model.node.URI;

/**
 * Interface for an URIGaneratror that creates URIs for SIOC classes
 * 
 * @author Florian Müller
 * 
 */
public interface URIGenerator {
    /**
     * Create URI for a Site
     * 
     * @param id
     *            Site id
     * @return URI for the Site with <i>id</i>.
     */
    public URI createSiteURI(String id);

    /**
     * Create URI for an UserAccount
     * 
     * @param id
     *            UserAccount id
     * @return URI for the UserAccount with <i>id</i>.
     */
    public URI createUserAccountURI(String id);

    /**
     * Create URI for a Usergroup
     * 
     * @param id
     *            Usergroup id
     * @return URI for the Usergroup with <i>id</i>.
     */
    public URI createUsergroupURI(String id);

    /**
     * Create URI for a Forum
     * 
     * @param id
     *            Forum id
     * @return URI for the Forum with <i>id</i>.
     */
    public URI createForumURI(String id);

    /**
     * Create URI for a Thread
     * 
     * @param id
     *            Thread id
     * @return URI for the Thread with <i>id</i>.
     */
    public URI createThreadURI(String id);

    /**
     * Create URI for a Post
     * 
     * @param id
     *            Post id
     * @return URI for the Post with <i>id</i>.
     */
    public URI createPostURI(String id);
}
