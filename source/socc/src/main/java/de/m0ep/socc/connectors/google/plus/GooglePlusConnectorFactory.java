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

package de.m0ep.socc.connectors.google.plus;

import java.util.Map;

import org.ontoware.rdf2go.model.Model;

import de.m0ep.socc.IConnector;
import de.m0ep.socc.IConnectorFactory;
import de.m0ep.socc.exceptions.ConnectorException;
import de.m0ep.socc.utils.ConfigUtils;

public class GooglePlusConnectorFactory implements IConnectorFactory {

    @Override
    public String getConnectorName() {
	return "Google+";
    }

    @Override
    public String getUniqueFactoryId() {
	return "GooglePlusConnectorFactory_1.0";
    }

    @Override
    public String[] getConfigKeys() {
	return ConfigUtils.getPropertyNames(GooglePlusConnectorConfig.class);
    }

    @Override
    public IConnector createConnector(String id, Model model,
	    Map<String, Object> parameters) throws ConnectorException {

	IConnector connector = new GooglePlusConnector();
	connector.initialize(id, model, parameters);
	return connector;
    }

}
