/*
 * The MIT License (MIT) Copyright © 2013 Florian Müller
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the “Software”), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package de.m0ep.socc.core.acl;

import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.node.URI;
import org.w3.ns.auth.acl.Access;
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
public enum AccessMode {
    READ(Read.RDFS_CLASS),
    WRITE(Write.RDFS_CLASS),
    APPEND(Append.RDFS_CLASS),
    CONTROL(Control.RDFS_CLASS);

    private URI uri;

    /**
     * Constructs a new access mode with the provided URI.
     * 
     * @param uri
     */
    private AccessMode(URI uri) {
        this.uri = uri;
    }

    /**
     * Returns the {@link URI} of this access mode.
     */
    public URI toUri() {
        return uri;
    }

    /**
     * Returns the access mode as an {@link Access} class object.
     * 
     * @param model
     * @throws NullPointerException Thrown if <code>model</code> is
     *             <code>null</code>.
     */
    public Access toAccess(Model model) {
        Preconditions.checkNotNull(model, "Required parameter model must be specified.");
        return new Access(model, uri, false);
    }
}
