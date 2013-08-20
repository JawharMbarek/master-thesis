
package de.m0ep.socc.core.connector;

import java.util.List;
import java.util.Map;

import org.rdfs.sioc.UserAccount;
import org.rdfs.sioc.services.Service;

import com.google.api.client.util.Maps;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import de.m0ep.socc.core.exceptions.NotFoundException;

public abstract class AbstractServiceClientManager<T> implements
        IServiceClientManager<T> {
    private Service service;
    private T defaultClient;
    private Map<Integer, T> clientMap = Maps.newHashMap();

    public AbstractServiceClientManager(Service service,
            UserAccount defaultUserAccount)
            throws Exception {
        this.service = Preconditions.checkNotNull(service,
                "Required parameter service must be specified.");
        Preconditions.checkNotNull(defaultUserAccount,
                "Required parameter defaultUserAccount must be specified.");

        init();
        this.defaultClient = createClientFromAccount(defaultUserAccount);
    }

    protected abstract void init();

    @Override
    public Service getService() {
        return service;
    }

    @Override
    public void setService(Service service) {
        this.service = service;
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
        Preconditions.checkNotNull(client,
                "Required parameter client must be specified.");

        clientMap.put(
                generateKey(userAccount),
                client);
    }

    @Override
    public void remove(UserAccount userAccount) {
        if (null == userAccount) {
            return;
        }

        Preconditions.checkArgument(userAccount.hasAccountName(),
                "the paremeter userAccount has no accountName.");
        Preconditions.checkArgument(userAccount.hasAccountServiceHomepage(),
                "The parameter userAccount has no accountServiceHomepage.");

        int key = generateKey(userAccount);
        if (clientMap.containsKey(key)) {
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
    public boolean contains(UserAccount userAccount) {
        if (null == userAccount) {
            return false;
        }

        Preconditions.checkArgument(userAccount.hasAccountName(),
                "the paremeter userAccount has no accountName.");
        Preconditions.checkArgument(userAccount.hasAccountServiceHomepage(),
                "The parameter userAccount has no accountServiceHomepage.");

        return clientMap.containsKey(generateKey(userAccount));
    }

    @Override
    public void clear() {
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
        super.finalize();
    }
}
