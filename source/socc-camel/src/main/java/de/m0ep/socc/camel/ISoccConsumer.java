package de.m0ep.socc.camel;

import org.apache.camel.Consumer;
import org.rdfs.sioc.Container;

import de.m0ep.socc.core.connector.IConnector;
import de.m0ep.socc.core.connector.IConnector.IPostReader;

public interface ISoccConsumer extends Consumer {
	public abstract IPostReader<? extends IConnector> getPostReader();

	public abstract void setPostReader( IPostReader<? extends IConnector> postReader );

	public abstract Container getContainer();

	public abstract void setContainer( Container container );

	public abstract int getLimit();

	public abstract void setLimit( int limit );

}