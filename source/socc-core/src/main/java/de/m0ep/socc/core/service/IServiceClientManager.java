
package de.m0ep.socc.core.service;

import java.util.List;

import org.rdfs.sioc.UserAccount;

public interface IServiceClientManager<T> {
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
     * Returns the client that belong to the provided {@link UserAccount}. If
     * there is no such client, it will return the default client.
     * 
     * @param userAccount
     * @throws IllegalStateException
     *             Thrown if no default client was set.
     */
    public T get(UserAccount userAccount);

    /**
     * Clears the {@link IServiceClientManager} and frees all allocated
     * resources.
     */
    public void clear();
}
