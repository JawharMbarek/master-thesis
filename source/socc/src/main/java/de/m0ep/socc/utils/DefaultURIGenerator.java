package de.m0ep.socc.utils;

import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.model.node.impl.URIImpl;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.Site;
import org.rdfs.sioc.Thread;
import org.rdfs.sioc.UserAccount;
import org.rdfs.sioc.Usergroup;

import com.google.common.base.Preconditions;

import de.m0ep.socc.Connector;

/**
 * Default implementation of an {@link URIGenerator}
 * 
 * @author Florian Müller
 * 
 */
public class DefaultURIGenerator implements URIGenerator {

    private Connector connector;

    /**
     * Constructor to create a DefaultUriGenerator for a specific
     * {@link Connector}
     * 
     * @param connector
     *            Connector of this {@link URIGenerator}
     */
    public DefaultURIGenerator(Connector connector) {
	Preconditions.checkNotNull(connector, "connector cannot be null");
	this.connector = connector;
    }

    /**
     * Create a default {@link Usergroup} {@link URI} of the schema:
     * 
     * <pre>
     * {@link Connector#getURL()}  + "usergroup/" + id
     * </pre>
     */
    @Override
    public URI createUsergroupURI(final String id) {
	Preconditions.checkNotNull(id, "id cannot be null");
	return new URIImpl(connector.getURL() + "usergroup/" + id, true);
    }

    /**
     * Create a default {@link UserAccount} {@link URI} of the schema:
     * 
     * <pre>
     * {@link Connector#getURL()}  + "user/" + id
     * </pre>
     */
    @Override
    public URI createUserAccountURI(final String id) {
	Preconditions.checkNotNull(id, "id cannot be null");
	return new URIImpl(connector.getURL() + "user/" + id, true);
    }

    /**
     * Create a default {@link Site} {@link URI} of the schema:
     * 
     * <pre>
     * {@link Connector#getURL()}  + "site/" + id
     * </pre>
     */
    @Override
    public URI createSiteURI(final String id) {
	Preconditions.checkNotNull(id, "id cannot be null");
	return new URIImpl(connector.getURL() + "site/" + id, true);
    }

    /**
     * Create a default {@link Forum} {@link URI} of the schema:
     * 
     * <pre>
     * {@link Connector#getURL()}  + "forum/" + id
     * </pre>
     */
    @Override
    public URI createForumURI(final String id) {
	Preconditions.checkNotNull(id, "id cannot be null");
	return new URIImpl(connector.getURL() + "forum/" + id, true);
    }

    /**
     * Create a default {@link Thread} {@link URI} of the schema:
     * 
     * <pre>
     * {@link Connector#getURL()} + "thread/" + id
     * </pre>
     */
    @Override
    public URI createThreadURI(final String id) {
	Preconditions.checkNotNull(id, "id cannot be null");
	return new URIImpl(connector.getURL() + "thread/" + id, true);
    }

    /**
     * Create a default {@link Post} {@link URI} of the schema:
     * 
     * <pre>
     * {@link Connector#getURL()}  + "post/" + id
     * </pre>
     */
    @Override
    public URI createPostURI(final String id) {
	Preconditions.checkNotNull(id, "id cannot be null");
	return new URIImpl(connector.getURL() + "post/" + id, true);
    }
}
