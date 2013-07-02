
package de.m0ep.socc.core.acl;

import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdfreactor.schema.rdfs.Class;
import org.w3.ns.auth.acl.Append;
import org.w3.ns.auth.acl.Authorization;
import org.w3.ns.auth.acl.Control;
import org.w3.ns.auth.acl.Read;
import org.w3.ns.auth.acl.Write;

/**
 * Enum for better handling with {@link Authorization} access modes.
 * 
 * @author Florian Müller
 */
public enum AccessMode {
    READ(Read.RDFS_CLASS),
    WRITE(Write.RDFS_CLASS),
    APPEND(Append.RDFS_CLASS),
    CONTROL(Control.RDFS_CLASS);

    private URI classUri;

    /**
     * Constructs a new AccessMode with a provided class URI.
     * 
     * @param classUri
     */
    private AccessMode(URI classUri) {
        this.classUri = classUri;
    }

    /**
     * Returns the access mode class URI.
     */
    public URI getClassUri() {
        return classUri;
    }

    /**
     * Returns the class URI as a {@link Class} object. No Triple will be writen
     * to the model.
     * 
     * @param model RDF2Go model needed to create the object.
     */
    public Class asClass(final Model model) {
        return new Class(model, classUri, false);
    }
}
