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

import org.rdfs.sioc.UserAccount;

import de.m0ep.sioc.service.auth.Service;
import de.m0ep.socc.exceptions.ConnectorException;

/**
 * Fabric class to create new instance of {@link IConnector}s.
 * 
 * @author Florian Müller
 * 
 */
public interface IConnectorFactory {
    /**
     * Returns the id of this factory.
     * 
     * @return Id as {@link String}
     */
    public String getId();

    /**
     * Returns a human readable label for this factory.
     * 
     * @return Label as {@link String}
     */
    public String getLabel();

    /**
     * Create a new instance of an {@link IConnector}.
     * 
     * @param id
     *            Id of the connector.
     * @param context
     *            Instance of an {@link ISOCCContext} implementation.
     * @param service
     *            {@link Service} resource to use with this instance.
     * @param userAccount
     *            The {@link UserAccount} that is used with this instance.
     * 
     * @return A new instance of an {@link IConnector}
     * 
     * @throws ConnectorException
     *             Thrown if there is a problem creating the connector.
     * @throws NullPointerException
     *             Thrown if one or more arguments are null.
     * @throws IllegalArgumentException
     *             Thrown if there are problems with the passed arguments.
     */
    public IConnector createNewInstance(
	    String id,
	    ISOCCContext context,
	    Service service,
	    UserAccount userAccount) throws ConnectorException;
}
