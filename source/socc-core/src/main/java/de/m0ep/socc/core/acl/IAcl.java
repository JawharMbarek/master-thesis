/*
 * The MIT License (MIT) Copyright © 2013 Florian Müller Permission is hereby
 * granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the “Software”), to deal in the Software
 * without restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of the
 * Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions: The above copyright notice and this
 * permission notice shall be included in all copies or substantial portions of
 * the Software. THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO
 * EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES
 * OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

package de.m0ep.socc.core.acl;

import java.util.EnumSet;
import java.util.List;

import org.ontoware.rdf2go.model.node.Resource;
import org.ontoware.rdf2go.model.node.URI;
import org.rdfs.sioc.UserAccount;
import org.w3.ns.auth.acl.Authorization;

/**
 * Interface that describes a Access Control List to manage authorizations
 * granted by the user to SOCC.
 * 
 * @author Florian Müller
 */
public interface IAcl {
    /**
     * Returns the SOCC-Agent {@link URI} that is used as the Agent of an
     * {@link Authorization}.
     */
    public URI getSoccAgent();

    /**
     * Adds an new {@link Authorization} to the Access Control List for a RDF
     * resource.
     * 
     * @param creator The creator of this {@link Authorization}. Stored as
     *            dcterms:creator.
     * @param accessTo To which resource should this {@link Authorization} be
     *            applied.
     * @param accessModes The modes that are allowed by this
     *            {@link Authorization}.
     */
    public void addAuthorization(
            UserAccount creator,
            Resource accessTo,
            EnumSet<AccessMode> accessModes);

    /**
     * Adds an new {@link Authorization} to the Access Control List for a RDF
     * class.
     * 
     * @param creator The creator of this {@link Authorization}. Stored as
     *            dcterms:creator.
     * @param accessToClass To which class should this {@link Authorization} be
     *            applied.
     * @param accessModes The modes that are allowed by this
     *            {@link Authorization}.
     */
    public void addAuthorization(
            UserAccount creator,
            URI accessToClass,
            EnumSet<AccessMode> accessModes);

    /**
     * Lists all {@link Authorization} that are created by the provided
     * {@link UserAccount}.
     * 
     * @param creator
     * @return List of all {@link Authorization} created by this
     *         {@link UserAccount}
     */
    public List<Authorization> listAuthorizations(UserAccount creator);

    /**
     * Checks if the provided {@link UserAccount} has granted access to a
     * {@link Resource} with the wanted {@link AccessMode}s.
     * 
     * @param creator
     * @param accessTo
     * @param accessModes
     * @return
     */
    public boolean checkAuthorization(
            UserAccount creator,
            Resource accessTo,
            EnumSet<AccessMode> accessModes);
}
