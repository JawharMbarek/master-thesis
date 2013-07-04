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
import org.rdfs.sioc.UserAccount;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.xmlns.foaf.OnlineAccount;
import com.xmlns.foaf.Person;

import de.m0ep.socc.core.exceptions.NotFoundException;

/**
 * A class that implements the {@link IUserTool} interface.
 * 
 * @author Florian Müller
 */
public class UserTool implements IUserTool {
    private Model model;

    public UserTool(final Model model) {
        this.model = Preconditions.checkNotNull(model,
                "Required parameter model must be specified.");
        Preconditions.checkArgument(model.isOpen(), "The provided model is not open.");
    }

    @Override
    public Model getModel() {
        return model;
    }

    @Override
    public List<UserAccount> listUserAccounts(Person person) throws NotFoundException {
        Preconditions.checkNotNull(person, "Required parameter person must be specified.");

        List<UserAccount> result = Lists.newArrayList();
        // TODO Auto-generated method stub
        return result;
    }

    @Override
    public UserAccount findUserAccount(String accountName, String accountServiceHomepage)
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
                !accountServiceHomepage.isEmpty(),
                "Required parameter accountServiceHomepage may not be empty.");

        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Person findPerson(UserAccount userAccount) throws NotFoundException {
        Preconditions.checkNotNull(
                userAccount,
                "Required parameter userAccount must be specified.");

        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Person findPerson(OnlineAccount onlineAccount) throws NotFoundException {
        UserAccount userAccount = UserAccount.getInstance(
                onlineAccount.getModel(),
                onlineAccount.getResource());

        return findPerson(userAccount);
    }

}
