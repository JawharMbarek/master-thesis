
package de.m0ep.socc.core.service;

import java.util.EventListener;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.rdfs.sioc.UserAccount;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import de.m0ep.socc.core.exceptions.NotFoundException;

public class BasicServiceClientManager<T> implements IServiceClientManager<T> {
    public interface IClientShutdownListener<T> extends EventListener {
        public void onShutdown(T client);
    }

    private T defaultClient;
    private Map<Integer, T> clientMap;
    private Set<IClientShutdownListener<T>> listenerSet;

    public BasicServiceClientManager() {
        clientMap = Maps.newHashMap();
        this.listenerSet = Sets.newHashSet();
    }

    public BasicServiceClientManager(T defaultClient) {
        this();
        setDefaultClient(defaultClient);
    }

    public void addClientShutdownListener(IClientShutdownListener<T> listener) {
        synchronized (listenerSet) {
            listenerSet.add(listener);
        }
    }

    public void removeClientShutdownListener(IClientShutdownListener<T> listener) {
        synchronized (listenerSet) {
            listenerSet.remove(listener);
        }
    }

    private void fireClientShutdown(T client) {
        synchronized (listenerSet) {
            for (IClientShutdownListener<T> listener : listenerSet) {
                listener.onShutdown(client);
            }
        }
    }

    @Override
    public void setDefaultClient(T client) {
        this.defaultClient = Preconditions.checkNotNull(client,
                "Required parameter client must be specified.");
    }

    @Override
    public T getDefaultClient() {
        Preconditions.checkState(null != defaultClient,
                "No default client set.");

        return defaultClient;
    }

    @Override
    public void add(UserAccount userAccount, T client) {
        Preconditions.checkNotNull(userAccount,
                "Required parameter userAccount must be specified.");
        Preconditions.checkArgument(userAccount.hasAccountName(),
                "the paremeter userAccount has no accountName.");
        Preconditions.checkArgument(userAccount.hasAccountServiceHomepage(),
                "The parameter userAccount has no accountServiceHomepage.");
        Preconditions.checkNotNull(
                client,
                "Required parameter client must be specified.");

        clientMap.put(
                generateKey(userAccount),
                client);
    }

    @Override
    public void remove(UserAccount userAccount) {
        int key = generateKey(userAccount);
        if (clientMap.containsKey(key)) {
            fireClientShutdown(clientMap.get(key));
            clientMap.remove(key);
        }
    }

    @Override
    public List<T> getAll() {
        return Lists.newArrayList(clientMap.values());
    }

    @Override
    public T get(UserAccount userAccount) throws NotFoundException {
        Preconditions.checkNotNull(userAccount,
                "Required parameter userAccount must be specified.");
        Preconditions.checkArgument(userAccount.hasAccountName(),
                "the paremeter userAccount has no accountName.");
        Preconditions.checkArgument(userAccount.hasAccountServiceHomepage(),
                "The parameter userAccount has no accountServiceHomepage.");

        T result = clientMap.get(generateKey(userAccount));

        if (null == result) {
            throw new NotFoundException("No client found for " + userAccount);
        }

        return result;
    }

    @Override
    public void clear() {
        for (T client : clientMap.values()) {
            fireClientShutdown(client);
        }

        clientMap.clear();
    }

    private int generateKey(UserAccount userAccount) {
        return Objects.hashCode(
                userAccount.getAccountName(),
                userAccount.getAccountServiceHomepage());
    }

    @Override
    protected void finalize() throws Throwable {
        clear();
        fireClientShutdown(defaultClient);
        super.finalize();
    }
}
