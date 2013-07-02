
package de.m0ep.socc.core.acl;

import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdfreactor.schema.rdfs.Class;
import org.w3.ns.auth.acl.Append;
import org.w3.ns.auth.acl.Authorization;
import org.w3.ns.auth.acl.Control;
import org.w3.ns.auth.acl.Read;
import org.w3.ns.auth.acl.Write;

import com.google.common.base.Preconditions;

/**
 * Enum for better handling with {@link Authorization} access modes.
 * 
 * @author Florian Müller
 */
public enum Access {
    READ(Read.RDFS_CLASS),
    WRITE(Write.RDFS_CLASS),
    APPEND(Append.RDFS_CLASS),
    CONTROL(Control.RDFS_CLASS);

    private URI classUri;

    /**
     * Constructs a new Access with a provided class URI.
     * 
     * @param classUri
     */
    private Access(URI classUri) {
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

    public static Access fromClass(Class clazz) {
        Preconditions.checkNotNull(clazz, "Required parameter clazz must be specified.");

        if (Read.RDFS_CLASS.equals(clazz.asURI())) {
            return Access.READ;
        } else if (Write.RDFS_CLASS.equals(clazz.asURI())) {
            return Access.WRITE;
        } else if (Append.RDFS_CLASS.equals(clazz.asURI())) {
            return Access.APPEND;
        } else if (Control.RDFS_CLASS.equals(clazz.asURI())) {
            return Access.CONTROL;
        }

        throw new IllegalArgumentException(
                "Failed to match '" + clazz.asURI() + "' to an Access type.");
    }
}
