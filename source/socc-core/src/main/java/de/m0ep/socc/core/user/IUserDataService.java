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

import org.ontoware.rdf2go.model.node.URI;
import org.rdfs.sioc.UserAccount;

import com.xmlns.foaf.OnlineAccount;
import com.xmlns.foaf.Person;

import de.m0ep.socc.core.exceptions.NotFoundException;

/**
 * An interface to list/search for {@link UserAccount}s of {@link Person}s and
 * vice versa.
 * 
 * @author Florian Müller
 */
public interface IUserDataService {
    /**
     * Returns all {@link UserAccount}s that are linked to the provided
     * {@link Person}.
     * 
     * @param person
     * @throws IllegalArgumentException Thrown if <code>person</code> leads to
     *             no valid {@link Person}.
     */
    public List<UserAccount> listUserAccounts(Person person);

    /**
     * Searches for an {@link UserAccount} that matches the provided parameters.
     * 
     * @param accountName The unique Id of the {@link UserAccount}.
     * @param accountServiceHomepage The homepage of the service that is used
     *            for this account.
     * @throws NotFoundException Thrown if there is no {@link UserAccount}
     *             matching the provided parameters.
     */
    public UserAccount findUserAccount(String accountName, URI accountServiceHomepage)
            throws NotFoundException;

    /**
     * Returns the {@link Person} that is possibly linked to the provided
     * {@link UserAccount}.
     * 
     * @param userAccount
     * @throws NotFoundException Thrown if there is no {@link Person} linked to
     *             this {@link UserAccount}.
     */
    public Person findPerson(UserAccount userAccount) throws NotFoundException;

    /**
     * Returns the {@link Person} that is possibly linked to the provided
     * {@link OnlineAccount}
     * 
     * @param onlineAccount
     * @throws NotFoundException Throw if there is no {@link Person} linked to
     *             this {@link OnlineAccount}.
     */
    public Person findPerson(OnlineAccount onlineAccount) throws NotFoundException;
}
