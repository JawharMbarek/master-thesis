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

import java.util.Set;

import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.QueryResultTable;
import org.ontoware.rdf2go.model.QueryRow;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.vocabulary.RDF;
import org.w3.ns.auth.acl.AclVocabulary;
import org.w3.ns.auth.acl.Authorization;

import com.google.common.base.Preconditions;
import com.xmlns.foaf.Agent;
import com.xmlns.foaf.FoafVocabulary;

/**
 * Implements the {@link IAccessControl} to create an {@link AccessControl} for
 * the SOCC-Framework to access user data or to write data in behalf of an
 * individual user.
 * 
 * @author Florian Müller
 */
public class AccessControl implements IAccessControl {
    private Model model;

    /**
     * Constructs a new {@link AccessControl} object with a specified
     * {@link Model} and <code>soccBotAgent</code>.
     * 
     * @param model
     * @param soccBotAgentUri
     */
    public AccessControl(final Model model) {
        this.model = Preconditions.checkNotNull(
                model,
                "Required parameter model must be specified.");
        Preconditions.checkArgument(model.isOpen(), "The model isn't open. ");
    }

    @Override
    public boolean checkAccessTo(
            Agent owner,
            URI accessTo,
            Set<URI> accessModeSet) {
        Preconditions.checkNotNull(owner,
                "Required parameter owner must be specified.");
        Preconditions.checkNotNull(accessTo,
                "Required parameter accessTo must be specified.");
        Preconditions.checkNotNull(accessModeSet,
                "Required parameter accessModeSet must be specified.");
        Preconditions.checkArgument(!accessModeSet.isEmpty(),
                "Required parameter accessModeSet may not be empty.");

        QueryResultTable resultTable = model.sparqlSelect(
                "SELECT ?auth\n" +
                        "WHERE {\n" +
                        "?auth " + RDF.type.toSPARQL() + " "
                        + AclVocabulary.Authorization.toSPARQL() + ".\n" +
                        "?auth " + AclVocabulary.owner.toSPARQL() + " "
                        + owner.toSPARQL()
                        + ".\n" +
                        "?auth " + AclVocabulary.agentClass.toSPARQL() + " "
                        + FoafVocabulary.Agent.toSPARQL() + ".\n" +
                        "?auth " + AclVocabulary.accessTo.toSPARQL() + " "
                        + accessTo.toSPARQL() +
                        ".}");

        for (QueryRow row : resultTable) {
            Authorization authorization = Authorization.getInstance(
                    model,
                    row.getValue("auth").asResource());

            int hits = 0;
            for (URI accessMode : accessModeSet) {
                if (authorization.hasAccessMode(accessMode)) {
                    hits++;
                }
            }

            if (accessModeSet.size() == hits) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean checkAccessToClass(Agent owner, URI accessToClass,
            Set<URI> accessModeSet) {
        Preconditions.checkNotNull(owner,
                "Required parameter owner must be specified.");
        Preconditions.checkNotNull(accessToClass,
                "Required parameter accessModeSet must be specified.");
        Preconditions.checkNotNull(accessModeSet,
                "Required parameter accessModeSet must be specified.");
        Preconditions.checkArgument(!accessModeSet.isEmpty(),
                "Required parameter accessModeSet may not be empty.");

        QueryResultTable resultTable = model.sparqlSelect(
                "SELECT ?auth\n" +
                        "WHERE {\n" +
                        "?auth " + RDF.type.toSPARQL() + " "
                        + AclVocabulary.Authorization.toSPARQL() + ".\n" +
                        "?auth " + AclVocabulary.owner.toSPARQL() + " "
                        + owner.toSPARQL()
                        + ".\n" +
                        "?auth " + AclVocabulary.agentClass.toSPARQL() + " "
                        + FoafVocabulary.Agent.toSPARQL() + ".\n" +
                        "?auth " + AclVocabulary.accessToClass.toSPARQL() + " "
                        + accessToClass.toSPARQL() + ".}");

        for (QueryRow row : resultTable) {
            Authorization authorization = Authorization.getInstance(
                    model,
                    row.getValue("auth").asResource());

            int hits = 0;
            for (URI accessMode : accessModeSet) {
                if (authorization.hasAccessMode(accessMode)) {
                    hits++;
                }
            }

            if (accessModeSet.size() == hits) {
                return true;
            }
        }

        return false;
    }
}
