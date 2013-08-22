package de.m0ep.socc.camel;

import org.apache.camel.Producer;
import org.rdfs.sioc.Container;

import de.m0ep.socc.core.connector.IConnector;
import de.m0ep.socc.core.connector.IConnector.IPostWriter;

public interface ISoccProducer extends Producer {
	public abstract IPostWriter<? extends IConnector> getPostWriter();

	public abstract void setPostWriter( IPostWriter<? extends IConnector> postWriter );

	public abstract Container getContainer();

	public abstract void setContainer( Container container );

}
