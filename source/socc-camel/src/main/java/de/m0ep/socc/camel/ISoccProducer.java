package de.m0ep.socc.camel;

import org.apache.camel.Producer;
import org.ontoware.rdf2go.model.node.URI;

import de.m0ep.socc.core.connector.IConnector;
import de.m0ep.socc.core.connector.IConnector.IPostWriter;

public interface ISoccProducer extends Producer {
	public abstract IPostWriter<? extends IConnector> getPostWriter();

	public abstract void setPostWriter( IPostWriter<? extends IConnector> postWriter );

	public abstract URI getUri();

	public abstract void setUri( URI uri );
}
