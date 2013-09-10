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

package de.m0ep.socc.core.utils;

import java.util.List;

import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.QueryResultTable;
import org.ontoware.rdf2go.model.QueryRow;
import org.ontoware.rdf2go.model.node.Node;
import org.ontoware.rdf2go.model.node.URI;
import org.rdfs.sioc.SiocVocabulary;
import org.rdfs.sioc.UserAccount;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.xmlns.foaf.FoafVocabulary;
import com.xmlns.foaf.OnlineAccount;
import com.xmlns.foaf.Person;

import de.m0ep.socc.core.exceptions.NotFoundException;

/**
 * A utility class to to find {@link UserAccount} of a {@link Person} or vice
 * versa.
 * 
 * @author Florian Müller
 */
public final class UserAccountUtils {
    /**
     * Private constructor, because this class has only static methods.
     */
    private UserAccountUtils() {
    }

    /**
     * List all {@link UserAccount} of a {@link Person} in a specific
     * {@link Model}.
     * 
     * @param model
     * @param person
     * @return Returns a {@link List} of all found {@link UserAccount}s.
     * @throws NullPointerException
     *             Thrown if <code>model</code> or <code>person</code> are
     *             <code>null</code>.
     * @throws IllegalArgumentException
     *             Thrown if <code>model</code> is not open.
     */
    public static List<UserAccount> listUserAccounts(Model model, Person person) {
        Preconditions.checkNotNull(model,
                "Required parameter model must be specified.");
        Preconditions.checkArgument(model.isOpen(),
                "Required parameter model may be open.");
        Preconditions.checkNotNull(person,
                "Required parameter person must be specified.");

        List<UserAccount> result = Lists.newArrayList();

        QueryResultTable resultTable = model.sparqlSelect(
                "SELECT DISTINCT ?acc WHERE {{{?acc a "
                        + SiocVocabulary.UserAccount.toSPARQL()
                        + "}UNION{?acc a "
                        + FoafVocabulary.OnlineAccount.toSPARQL()
                        + "}}{{?acc "
                        + SiocVocabulary.account_of.toSPARQL() + " " + person.toSPARQL()
                        + "}UNION{"
                        + person.toSPARQL() + " " + FoafVocabulary.account.toSPARQL()
                        + " ?acc}}}");

        for (QueryRow row : resultTable) {
            Node node = row.getValue("acc");
            result.add(UserAccount.getInstance(model, node.asResource()));
        }

        return result;
    }

    /**
     * Find a {@link UserAccount} that matches <code>accountName</code> and
     * <code>accountServiceHompage</code>.
     * 
     * @param model
     * @param accountName
     * @param accountServiceHomepage
     * @return Returns an instance of the found {@link UserAccount}.
     * @throws NotFoundException
     *             Thrown if no {@link UserAccount} was found that matches
     *             <code>accountName</code> and
     *             <code>accountServiceHompage</code>.
     * @throws NullPointerException
     *             Thrown if <code>model</code>, <code>accountName</code> or
     *             <code>accountServiceHompage</code> are <code>null</code>.
     * @throws IllegalArgumentException
     *             Thrown if <code>model</code> is not open or
     *             <code>accountName</code> or
     *             <code>accountServiceHompage</code> are empty.
     */
    public static UserAccount findUserAccount(Model model, String accountName,
            URI accountServiceHomepage)
            throws NotFoundException {
        Preconditions.checkNotNull(model,
                "Required parameter model must be specified.");
        Preconditions.checkArgument(model.isOpen(),
                "Required parameter model may be open.");
        Preconditions.checkNotNull(
                accountName,
                "Required parameter accountName must be specified.");
        Preconditions.checkArgument(
                !accountName.isEmpty(),
                "Required parameter accountName may not be empty.");
        Preconditions.checkNotNull(
                accountServiceHomepage,
                "Required parameter accountServiceHomepage must be specified.");
        Preconditions.checkArgument(
                !accountServiceHomepage.toString().isEmpty(),
                "Required parameter accountServiceHomepage may not be empty.");

        QueryResultTable resultTable = model.sparqlSelect(
                "SELECT DISTINCT ?acc WHERE{{{?acc a "
                        + SiocVocabulary.UserAccount.toSPARQL() + "}UNION{?acc a " +
                        FoafVocabulary.OnlineAccount.toSPARQL()
                        + "}} ?acc "
                        + FoafVocabulary.accountName.toSPARQL()
                        + " \""
                        + accountName
                        + "\" . ?acc "
                        + FoafVocabulary.accountServiceHomepage.toSPARQL()
                        + " "
                        + accountServiceHomepage.toSPARQL() + " .}");

        for (QueryRow row : resultTable) {
            Node node = row.getValue("acc");
            return UserAccount.getInstance(model, node.asResource());
        }

        throw new NotFoundException("No UserAccount found matching criteria.");
    }

    /**
     * Find a {@link UserAccount} of a {@link Person} that belongs to the
     * <code>accountServiceHomepage</code> service.
     * 
     * @param model
     * @param person
     * @param accountServiceHomepage
     * @return Returns an instance of the found {@link UserAccount}.
     * @throws NotFoundException
     *             Thrown if the {@link Person} has no {@link UserAccount} for
     *             that service.
     * @throws NullPointerException
     *             Thrown if <code>model</code>, <code>person</code> or
     *             <code>accountServiceHomepage</code> are <code>null</code>.
     * @throws IllegalArgumentException
     *             Thrown if <code>model</code> is not open or
     *             <code>accountServiceHomepage</code> is empty.
     */
    public static UserAccount findUserAccountOfService(Model model, Person person,
            URI accountServiceHomepage)
            throws NotFoundException {
        Preconditions.checkNotNull(model,
                "Required parameter model must be specified.");
        Preconditions.checkArgument(model.isOpen(),
                "Required parameter model may be open.");
        Preconditions.checkNotNull(person,
                "Required parameter person must be specified.");
        Preconditions.checkNotNull(accountServiceHomepage,
                "Required parameter accountServiceHomepage must be specified.");
        Preconditions.checkArgument(
                !accountServiceHomepage.toString().isEmpty(),
                "Required parameter accountServiceHomepage may not be empty.");

        QueryResultTable resultTable = model.sparqlSelect(
                "SELECT ?acc WHERE {{{"
                        + person.toSPARQL()
                        + " "
                        + FoafVocabulary.account.toSPARQL()
                        + " ?acc} UNION {?acc "
                        + SiocVocabulary.account_of.toSPARQL()
                        + " "
                        + person.toSPARQL()
                        + "}} ?acc "
                        + FoafVocabulary.accountServiceHomepage.toSPARQL()
                        + " "
                        + accountServiceHomepage.toSPARQL()
                        + ".}");

        for (QueryRow row : resultTable) {
            Node node = row.getValue("acc");
            return UserAccount.getInstance(model, node.asResource());
        }

        throw new NotFoundException("No UserAccount found matching criteria.");
    }

    /**
     * Searches for the {@link Person} to which this {@link UserAccount} belongs
     * to.
     * 
     * @param model
     * @param userAccount
     * @return Returns an instance of the found {@link Person}.
     * @throws NotFoundException
     *             Thrown if no {@link Person} found for this
     *             {@link UserAccount}.
     * @throws NullPointerException
     *             thrown if <code>model</code> or <code>userAccount</code> are
     *             null.
     * @throws IllegalArgumentException
     *             Thrown if <code>model</code> is not open.
     */
    public static Person findPerson(Model model, UserAccount userAccount) throws NotFoundException {
        Preconditions.checkNotNull(model,
                "Required parameter model must be specified.");
        Preconditions.checkArgument(model.isOpen(),
                "Required parameter model may be open.");
        Preconditions.checkNotNull(userAccount,
                "Required parameter userAccount must be specified.");

        if (userAccount.hasAccountOf()) {
            return Person.getInstance(model, userAccount.getAccountOf());
        }

        QueryResultTable resultTable = model.sparqlSelect(
                "SELECT DISTINCT ?person WHERE {{{?person a "
                        + FoafVocabulary.Person.toSPARQL()
                        + "}UNION{?person a "
                        + FoafVocabulary.Agent.toSPARQL()
                        + "}}{{" + userAccount.toSPARQL() + " "
                        + SiocVocabulary.account_of.toSPARQL()
                        + " ?person}UNION{?person "
                        + FoafVocabulary.account.toSPARQL()
                        + " "
                        + userAccount.toSPARQL()
                        + "}}}");

        for (QueryRow row : resultTable) {
            Node node = row.getValue("person");
            return Person.getInstance(model, node.asResource());
        }

        throw new NotFoundException("No Person found matching criteria.");
    }

    /**
     * Searches for the {@link Person} to which this {@link OnlineAccount}
     * belongs to.
     * 
     * @param model
     * @param onlineAccount
     * @return Returns an instance of the found {@link Person}.
     * @throws NotFoundException
     *             Thrown if no {@link Person} found for this
     *             {@link OnlineAccount}.
     * @throws NullPointerException
     *             thrown if <code>model</code> or <code>onlineAccount</code>
     *             are null.
     * @throws IllegalArgumentException
     *             Thrown if <code>model</code> is not open.
     */
    public static Person findPerson(Model model, OnlineAccount onlineAccount)
            throws NotFoundException {
        UserAccount userAccount = UserAccount.getInstance(
                onlineAccount.getModel(),
                onlineAccount.getResource());

        return findPerson(model, userAccount);
    }

}
