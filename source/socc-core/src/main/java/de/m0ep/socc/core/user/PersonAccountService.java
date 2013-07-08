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

package de.m0ep.socc.core.user;

import java.util.List;

import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.QueryResultTable;
import org.ontoware.rdf2go.model.QueryRow;
import org.ontoware.rdf2go.model.node.Node;
import org.ontoware.rdf2go.model.node.URI;
import org.rdfs.sioc.SIOCVocabulary;
import org.rdfs.sioc.UserAccount;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.xmlns.foaf.FOAFVocabulary;
import com.xmlns.foaf.OnlineAccount;
import com.xmlns.foaf.Person;

import de.m0ep.socc.core.exceptions.NotFoundException;

/**
 * A class that implements the {@link IPersonAccountService} interface.
 * 
 * @author Florian Müller
 */
public class PersonAccountService implements IPersonAccountService {
    private Model model;

    /**
     * Construct a new {@link PersonAccountService} object that operates on
     * the provided {@link Model}.
     * 
     * @param model
     */
    public PersonAccountService(final Model model) {
        this.model = Preconditions.checkNotNull(model,
                "Required parameter model must be specified.");
        Preconditions.checkArgument(model.isOpen(), "The provided model is not open.");
    }

    @Override
    public List<UserAccount> listUserAccounts(Person person) {
        Preconditions.checkNotNull(person, "Required parameter person must be specified.");

        List<UserAccount> result = Lists.newArrayList();

        QueryResultTable resultTable = model.sparqlSelect(
                "SELECT DISTINCT ?acc WHERE {{{?acc a "
                        + SIOCVocabulary.UserAccount.toSPARQL()
                        + "}UNION{?acc a "
                        + FOAFVocabulary.OnlineAccount.toSPARQL()
                        + "}}{{?acc "
                        + SIOCVocabulary.account_of.toSPARQL() + " " + person.toSPARQL()
                        + "}UNION{"
                        + person.toSPARQL() + " " + FOAFVocabulary.account.toSPARQL()
                        + " ?acc}}}");

        for (QueryRow row : resultTable) {
            Node node = row.getValue("acc");
            result.add(UserAccount.getInstance(model, node.asResource()));
        }

        return result;
    }

    @Override
    public UserAccount findUserAccount(String accountName, URI accountServiceHomepage)
            throws NotFoundException {
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
                        + SIOCVocabulary.UserAccount.toSPARQL() + "}UNION{?acc a " +
                        FOAFVocabulary.OnlineAccount.toSPARQL()
                        + "}} ?acc "
                        + FOAFVocabulary.accountName.toSPARQL()
                        + " \""
                        + accountName
                        + "\" . ?acc "
                        + FOAFVocabulary.accountServiceHomepage.toSPARQL()
                        + " "
                        + accountServiceHomepage.toSPARQL() + " .}");

        for (QueryRow row : resultTable) {
            Node node = row.getValue("acc");
            return UserAccount.getInstance(model, node.asResource());
        }

        throw new NotFoundException("No UserAccount found matching criteria.");
    }

    @Override
    public Person findPerson(UserAccount userAccount) throws NotFoundException {
        Preconditions.checkNotNull(
                userAccount,
                "Required parameter userAccount must be specified.");

        QueryResultTable resultTable = model.sparqlSelect(
                "SELECT DISTINCT ?person WHERE {{{?person a "
                        + FOAFVocabulary.Person.toSPARQL()
                        + "}UNION{?person a "
                        + FOAFVocabulary.Agent.toSPARQL()
                        + "}}{{" + userAccount.toSPARQL() + " "
                        + SIOCVocabulary.account_of.toSPARQL()
                        + " ?person}UNION{?person "
                        + FOAFVocabulary.account.toSPARQL()
                        + " "
                        + userAccount.toSPARQL()
                        + "}}}");

        for (QueryRow row : resultTable) {
            Node node = row.getValue("person");
            return Person.getInstance(model, node.asResource());
        }

        throw new NotFoundException("No Person found matching criteria.");
    }

    @Override
    public Person findPerson(OnlineAccount onlineAccount) throws NotFoundException {
        UserAccount userAccount = UserAccount.getInstance(
                onlineAccount.getModel(),
                onlineAccount.getResource());

        return findPerson(userAccount);
    }

}
