package de.m0ep.camel.socc;

import de.m0ep.socc.IConnector;

public interface ISOCCEndpoint {

    public abstract SOCCComponent getSOCCComponent();

    public abstract EndpointProperties getProperties();

    public abstract IConnector getConnector();

}