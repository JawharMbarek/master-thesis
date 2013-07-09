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

package de.m0ep.socc.core.connector;

import java.io.IOException;

import org.rdfs.sioc.Post;
import org.rdfs.sioc.Site;
import org.rdfs.sioc.UserAccount;

import de.m0ep.sioc.service.auth.Service;
import de.m0ep.socc.core.ISoccContext;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.service.IServiceClientManager;

/**
 * An interface that describes a class which helps to connect different online
 * communities with each other.
 * 
 * @author Florian Müller
 */
public interface IConnector {

    /**
     * Returns the Id of this connector.
     */
    public String getId();

    /**
     * Returns the context in which the connector is used.
     */
    public ISoccContext getContext();

    /**
     * Sets the used context.
     * 
     * @param context
     * @throws NullPointerException
     *             Thrown if <code>context</code> is <code>null</code>.
     */
    public void setContext(ISoccContext context);

    /**
     * Returns the service informations of this connector.
     */
    public Service getService();

    /**
     * Sets the service informations of this connector.
     * 
     * @param service
     */
    public void setService(Service service);

    /**
     * Returns the default {@link UserAccount} of this connector.
     */
    public UserAccount getDefaultUserAccount();

    /**
     * Sets the default {@link UserAccount} of this connector.
     * 
     * @param defaultUserAccount
     */
    public void setDefaultUserAccount(UserAccount defaultUserAccount);

    /**
     * Gets a SIOC representation of the underlying online community site.
     */
    public Site getSite();

    /**
     * Returns a {@link IServiceClientManager} instance to manage client objects
     * of different {@link UserAccount} for the used service.
     */
    public <T> IServiceClientManager<T> getServiceClientManager();

    /**
     * Returns an {@link IServiceStructureReader} to get information about the
     * structure of the used service.
     */
    public IServiceStructureReader getServiceStructureReader();

    /**
     * Returns an {@link IPostReader} to read {@link Post}s from the connectors
     * service endpoint.
     */
    public IPostReader getPostReader();

    /**
     * Returns an {@link IPostWriter to write {@link Post}s to the connectors
     * service endpoint.
     */
    public IPostWriter getPostWriter();

    /**
     * Initializes the connector.
     * 
     * @throws IllegalStateException
     *             Throw if was no {@link Site} or default {@link UserAccount}
     *             set or are invalid.
     * @throws AuthenticationException
     *             Thrown if authorization of the default {@link UserAccount}
     *             failed.
     * @throws IOException
     *             throw if there is a problem with the connection to the
     *             service endpoint.
     */
    public void initialize() throws AuthenticationException, IOException;

    /**
     * Destroys the connector.
     */
    public void destroy();
}
