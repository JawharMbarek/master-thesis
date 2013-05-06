package de.m0ep.camel.socc;

import org.apache.camel.Endpoint;

import de.m0ep.socc.IConnector;

/**
 * Interface for all Apache Camel {@link Endpoint} using SOCC library.
 * 
 * @author Florian Müller
 * 
 */
public interface ISOCCEndpoint {

	/**
	 * The {@link SOCCComponent} used to create this {@link Endpoint}
	 * 
	 * @return The {@link SOCCComponent}
	 */
	public abstract SOCCComponent getSOCCComponent();

	/**
	 * Returns the {@link SOCCEndpointProperties} that where given to this
	 * {@link Endpoint}.
	 * 
	 * @return THe {@link SOCCEndpointProperties}
	 */
	public abstract SOCCEndpointProperties getProperties();

	/**
	 * Returns an {@link IConnector} to access the SOCC Connector for this
	 * Endpoint.
	 * 
	 * @return
	 */
	public abstract IConnector getConnector();

}