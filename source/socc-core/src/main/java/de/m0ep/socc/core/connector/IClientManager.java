
package de.m0ep.socc.core.connector;

import java.util.List;

import org.rdfs.sioc.UserAccount;
import org.rdfs.sioc.services.Service;

import de.m0ep.socc.core.exceptions.NotFoundException;

public interface IClientManager<T> {

    /**
     * Returns the set service.
     */
    public Service getService();

    /**
     * Sets the Service.
     * 
     * @throws NullPointerException
     *             Thrown if <code>service</code> is <code>null</code>.
     */
    public void setService(Service service);

    /**
     * Sets the default client that will be returned, if no matching client was
     * found.
     * 
     * @param client
     * @throws NullPointerException
     *             Thrown if <code>client</code> is <code>null</code>.
     */
    public void setDefaultClient(T client);

    /**
     * Returns the default client.
     */
    public T getDefaultClient();

    /**
     * Creates a new client from a given {@link UserAccount}.
     * 
     * @param userAccount
     * @throws NullPointerException
     *             Thrown if <code>userAccount</code> is <code>null</code>.
     * @throws IllegalArgumentException
     *             Thrown if <code>userAccount</code> has invalid properties to
     *             create a client.
     */
    public T createClientFromAccount(UserAccount userAccount) throws Exception;

    /**
     * Adds a new client with a corresponding {@link UserAccount}.
     * 
     * @param client
     * @param userAccount
     * @throws NullPointerException
     *             Thrown if <code>userAccount</code> or <code>client</code> are
     *             <code>null</code>.
     */
    public void add(UserAccount userAccount, T client);

    /**
     * Removes the client who belongs to this {@link UserAccount}.
     * 
     * @param userAccount
     */
    public void remove(UserAccount userAccount);

    /**
     * Returns a {@link List} of all clients without the default client.
     */
    public List<T> getAll();

    /**
     * Returns the client that belong to the provided {@link UserAccount}.
     * 
     * @param userAccount
     * @throws NullPointerException
     *             Thrown if <code>userAccount</code> was <code>null</code>;
     * @throws NotFoundException
     *             Thrown if no client is found for this {@link UserAccount}.
     */
    public T get(UserAccount userAccount) throws NotFoundException;

    /**
     * Returns true if there is a client for the given {@link UserAccount},
     * false otherwise.
     * 
     * @param userAccount
     */
    public boolean contains(UserAccount userAccount);

    /**
     * Clears the {@link IClientManager} and frees all allocated
     * resources.
     */
    public void clear();
}
