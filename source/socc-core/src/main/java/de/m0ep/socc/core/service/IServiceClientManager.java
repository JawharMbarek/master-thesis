
package de.m0ep.socc.core.service;

import java.util.List;

import org.rdfs.sioc.UserAccount;

import de.m0ep.socc.core.exceptions.NotFoundException;

public interface IServiceClientManager<T> {
    /**
     * Sets the default client that will be returned, if no matching client was
     * found.
     * 
     * @param client
     * @throws NullPointerException Thrown if <code>client</code> is
     *             <code>null</code>.
     */
    public void setDefaultClient(T client);

    /**
     * Returns the default client.
     */
    public T getDefaultClient();

    /**
     * Adds a new client with a corresponding {@link UserAccount}.
     * 
     * @param client
     * @param userAccount
     * @throws NullPointerException Thrown if <code>userAccount</code> or
     *             <code>client</code> are <code>null</code>.
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
     * @throws NullPointerException Thrown if <code>userAccount</code> was
     *             <code>null</code>;
     * @throws NotFoundException Thrown if no client is found for this
     *             {@link UserAccount}.
     */
    public T get(UserAccount userAccount) throws NotFoundException;

    /**
     * Clears the {@link IServiceClientManager} and frees all allocated
     * resources.
     */
    public void clear();
}
