
package de.m0ep.socc.core.connector;

import java.util.List;
import java.util.Map;

import org.rdfs.sioc.UserAccount;
import org.rdfs.sioc.services.Service;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import de.m0ep.socc.core.exceptions.NotFoundException;

public abstract class AbstractServiceClientManager implements IServiceClientManager {
    private Service service;
    private Object defaultClient;
    private Map<Integer, Object> clientMap;

    public AbstractServiceClientManager(Service service) {
        this.service = Preconditions.checkNotNull(service,
                "Required parameter service must be specified.");
        this.clientMap = Maps.newHashMap();
    }

    public AbstractServiceClientManager(Service service, UserAccount defaultUserAccount)
            throws Exception {
        this(service);
        Preconditions.checkNotNull(defaultUserAccount,
                "Required parameter defaultUserAccount must be specified.");
        this.defaultClient = createClientFromAccount(defaultUserAccount);
    }

    @Override
    public Service getService() {
        return service;
    }

    @Override
    public void setService(Service service) {
        this.service = service;
    }

    @Override
    public void setDefaultClient(Object client) {
        this.defaultClient = Preconditions.checkNotNull(client,
                "Required parameter client must be specified.");
    }

    @Override
    public Object getDefaultClient() {
        Preconditions.checkState(null != defaultClient,
                "No default client set.");

        return defaultClient;
    }

    @Override
    public void add(UserAccount userAccount, Object client) {
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
    public List<Object> getAll() {
        return Lists.newArrayList(clientMap.values());
    }

    @Override
    public Object get(UserAccount userAccount) throws NotFoundException {
        Preconditions.checkNotNull(userAccount,
                "Required parameter userAccount must be specified.");
        Preconditions.checkArgument(userAccount.hasAccountName(),
                "the paremeter userAccount has no accountName.");
        Preconditions.checkArgument(userAccount.hasAccountServiceHomepage(),
                "The parameter userAccount has no accountServiceHomepage.");

        Object result = clientMap.get(generateKey(userAccount));

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
