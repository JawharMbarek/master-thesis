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

package de.m0ep.socc;

import java.util.Collection;
import java.util.Set;

import org.ontoware.rdf2go.model.Model;
import org.rdfs.sioc.UserAccount;

import de.m0ep.socc.exceptions.NotFoundException;

/**
 * 
 * 
 * @author Florian Müller
 * 
 */
public interface ISOCCContext {
    /**
     * Return the {@link Model} that is used to store all data.
     * 
     * @return A {@link Model}
     */
    public Model getDataModel();

    /**
     * /** Returns a {@link Set} with all connectors.
     * 
     * @return A {@link Set} of {@link IConnector}s.
     */
    public Collection<IConnector> getConnectors();

    /**
     * Returns the connector with this id.
     * 
     * @param id
     *            Id of the connector
     * 
     * @return A {@link IConnector}
     * 
     * @throws NotFoundException
     *             Thrown if there is no connector with this id.
     */
    public IConnector getConnector(String id) throws NotFoundException;

    /**
     * Returns the connector for this {@link UserAccount}.
     * 
     * @param userAccount
     *            {@link UserAccount} for this connector
     * 
     * @return A {@link IConnector}
     * 
     * @throws NotFoundException
     *             Thrown if there is no connector with this {@link UserAccount}
     *             .
     */
    public IConnector getConnector(UserAccount userAccount)
	    throws NotFoundException;

    /**
     * Add a new connector.
     * 
     * @param connector
     *            New {@link IConnector}, should not be null.
     * 
     * @throws NullPointerException
     *             Thrown if <code>connector</code> is <code>null</code>.
     */
    public void addConnector(IConnector connector);

    /**
     * Removes a connector.
     * 
     * @param connector
     *            The connector that should be removed.
     */
    public void removeConnector(IConnector connector);
}
