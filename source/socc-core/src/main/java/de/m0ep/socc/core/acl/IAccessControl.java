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

import org.ontoware.rdf2go.model.node.Resource;
import org.ontoware.rdf2go.model.node.URI;
import org.rdfs.sioc.Post;

import com.xmlns.foaf.Agent;

/**
 * An interface to check if a user granted access the SOCC Framework to
 * resources owned by him. The SOCC-Bot is a default user that is used to read
 * SIOC data form various serviced and writes {@link Post} if the creator is
 * unknown by the system or has not granted access wo write on its behalf.
 * 
 * @author Florian Müller
 */
public interface IAccessControl {
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
