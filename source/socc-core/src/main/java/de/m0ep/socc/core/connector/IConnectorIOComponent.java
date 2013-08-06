
package de.m0ep.socc.core.connector;

public interface IConnectorIOComponent<T extends IConnector> {

    public abstract T getConnector();
}
