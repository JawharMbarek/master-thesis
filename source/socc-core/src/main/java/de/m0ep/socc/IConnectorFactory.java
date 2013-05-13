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

package de.m0ep.socc;

import java.util.Map;

import org.ontoware.rdf2go.model.Model;

import de.m0ep.socc.config.form.DataForm;
import de.m0ep.socc.exceptions.ConnectorException;

/**
 * Interface of a {@link IConnectorFactory} to create new {@link IConnector}s
 * 
 * @author Florian Müller
 * 
 */
public interface IConnectorFactory {
    /**
     * Returns a human readable name for this connectors
     * 
     * @return Name for this connectors
     */
    public String getConnectorName();

    /**
     * Returns a unique id for this factory
     * 
     * @return
     */
    public String getId();

    public DataForm getParameterForm();

    /**
     * Create a new {@link IConnector}.
     * 
     * @param id
     *            Id of this connector.
     * @param model
     *            RDF2Go {@link Model} to use as SIOC store.
     * @param parameters
     *            {@link Map} with configuration parameters for a new
     *            {@link IConnector}.
     * @return
     */
    public IConnector createConnector(String id, Model model,
	    Map<String, Object> parameters) throws ConnectorException;

}
