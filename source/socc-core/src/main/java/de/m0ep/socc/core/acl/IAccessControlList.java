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

import java.util.EnumSet;
import java.util.List;

import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.node.Resource;
import org.ontoware.rdf2go.model.node.URI;
import org.w3.ns.auth.acl.Authorization;

import com.xmlns.foaf.Agent;

/**
 * Interface that describes an Access Control List to manage authorizations
 * granted by the user to SOCC.
 * 
 * @author Florian Müller
 */
public interface IAccessControlList {

    /**
     * Returns the RDF2Go {@link Model} used by this {@link IAccessControlList}.
     */
    public Model getModel();

    /**
     * Returns the SOCC-Agent {@link URI} that is used as the Agent of an
     * {@link Authorization}.
     */
    public Agent getSoccBotAgent();

    /**
     * Adds an new {@link Authorization} to the Access Control List for a RDF
     * resource.
     * 
     * @param creator The creator of this {@link Authorization}. Stored as
     *            dcterms:creator.
     * @param accessTo To which resource should this {@link Authorization} be
     *            applied.
     * @param accessModeSet The modes that are allowed by this
     *            {@link Authorization}.
     */
    public void addAuthorizationForResource(
            Agent owner,
            URI accessTo,
            EnumSet<Access> accessModeSet);

    /**
     * Adds an new {@link Authorization} to the Access Control List for a RDF
     * class.
     * 
     * @param owner The owner of this {@link Authorization}.
     * @param accessToClass To which class should this {@link Authorization} be
     *            applied.
     * @param accessModeSet The modes that are allowed by this
     *            {@link Authorization}.
     */
    public void addAuthorizationForClass(
            Agent owner,
            URI accessToClass,
            EnumSet<Access> accessModeSet);

    /**
     * Removes an {@link Authorization}.
     * 
     * @param authorization
     */
    public void removeAuthorization(Authorization authorization);

    /**
     * Lists all {@link Authorization} that are created by the provided
     * {@link Agent}.
     * 
     * @param creator
     * @return List of all {@link Authorization} created by this {@link Agent}
     */
    public List<Authorization> listAuthorizations(Agent owner);

    /**
     * Checks if the provided {@link Agent} has granted access to a
     * {@link Resource} with the wanted {@link Access} modes.
     * 
     * @param creator
     * @param accessTo
     * @param accessModeSet
     * @return
     */
    public boolean checkAuthorizationForResource(
            Agent owner,
            URI accessTo,
            EnumSet<Access> accessModeSet);

    /**
     * Checks if the provided {@link Agent} has granted access to a rdf class
     * with the wanted {@link Access} modes.
     * 
     * @param creator
     * @param accessToClass
     * @param accessModeSet
     * @return
     */
    public boolean checkAuthorizationForClass(
            Agent owner,
            URI accessToClass,
            EnumSet<Access> accessModeSet);
}
