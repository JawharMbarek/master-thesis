
package de.m0ep.socc.core.connector;

import java.io.IOException;

import org.rdfs.sioc.UserAccount;

import de.m0ep.socc.core.exceptions.AuthenticationException;

public interface IConnectorClient<T> {
    public T getClient();

    public UserAccount getUserAccount();

    public void recoverClient() throws AuthenticationException, IOException;
}
