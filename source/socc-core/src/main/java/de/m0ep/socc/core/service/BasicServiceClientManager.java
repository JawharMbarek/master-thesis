
package de.m0ep.socc.core.service;

import java.util.List;
import java.util.Map;

import org.rdfs.sioc.UserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class BasicServiceClientManager<T> implements IServiceClientManager<T> {
    private static final Logger LOG = LoggerFactory.getLogger(BasicServiceClientManager.class);

    private T defaultClient;
    private Map<UserAccount, T> clientMap;

    public BasicServiceClientManager() {
        clientMap = Maps.newHashMap();
    }

    public BasicServiceClientManager(T defaultClient) {
        this();
        setDefaultClient(defaultClient);
    }

    @Override
    public void setDefaultClient(T client) {
        this.defaultClient = Preconditions.checkNotNull(
                client,
                "Required parameter client must be specified.");
    }

    @Override
    public T getDefaultClient() {
        return defaultClient;
    }

    @Override
    public void add(UserAccount userAccount, T client) {
        Preconditions.checkNotNull(
                userAccount,
                "Required parameter userAccount must be specified.");
        Preconditions.checkNotNull(
                client,
                "Required parameter client must be specified.");

        clientMap.put(
                userAccount,
                client);
    }

    @Override
    public void remove(UserAccount userAccount) {
        clientMap.remove(userAccount);
    }

    @Override
    public List<T> getAll() {
        return Lists.newArrayList(clientMap.values());
    }

    @Override
    public T get(UserAccount userAccount) {
        Preconditions.checkState(
                null != defaultClient,
                "No default client set.");

        T result = clientMap.get(userAccount);

        if (null == userAccount) {
            LOG.info("No client for {} found. Return default client.", userAccount);
        }

        return (null != result) ? (result) : (getDefaultClient());
    }
}
