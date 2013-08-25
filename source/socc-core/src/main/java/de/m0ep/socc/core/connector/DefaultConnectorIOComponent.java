
package de.m0ep.socc.core.connector;

/*
 * The MIT License (MIT) Copyright © 2013 Florian Müller
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the “Software”), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.node.URI;

import com.google.common.base.Preconditions;

public class DefaultConnectorIOComponent<T extends IConnector> implements
        IConnectorIOComponent<T> {
    private T connector;
    private URI serviceEndpoint;

    public DefaultConnectorIOComponent(T connector) {
        this.connector = Preconditions.checkNotNull(connector,
                "Required parameter connector must be specified.");
        Preconditions.checkNotNull(this.connector.getService(),
                "The parameter connector has no service");
        Preconditions.checkArgument(this.connector.getService()
                .hasServiceEndpoint(),
                "The connectors service has no serviceEndpoint.");

        this.serviceEndpoint = this.connector.getService().getServiceEndpoint()
                .asURI();
    }

    @Override
    public T getConnector() {
        return connector;
    }

    protected Model getModel() {
        return connector.getContext().getModel();
    }

    protected URI getServiceEndpoint() {
        return serviceEndpoint;
    }
}
