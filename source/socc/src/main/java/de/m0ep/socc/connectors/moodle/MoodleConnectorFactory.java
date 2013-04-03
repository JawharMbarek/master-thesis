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

package de.m0ep.socc.connectors.moodle;

import java.util.Map;

import org.ontoware.rdf2go.model.Model;

import de.m0ep.socc.IConnector;
import de.m0ep.socc.IConnectorFactory;
import de.m0ep.socc.config.ConfigParameterUse;
import de.m0ep.socc.exceptions.ConnectorException;
import de.m0ep.socc.utils.ConfigUtils;

public class MoodleConnectorFactory implements IConnectorFactory {

    @Override
    public String getConnectorName() {
	return "Moodle";
    }

    @Override
    public String getFactoryId() {
	return "MoodleConnectorFactory_2.4";
    }

    @Override
    public String[] getParameterKeys() {
	return ConfigUtils.getParameterNames(MoodleConnectorConfig.class);
    }

    @Override
    public ConfigParameterUse getConfigParameterUse(String key) {
	if (MoodleConnectorConfig.USERNAME.equals(key)) {
	    return ConfigParameterUse.REQUIRED;
	} else if (MoodleConnectorConfig.PASSWORD.equals(key)) {
	    return ConfigParameterUse.REQUIRED;
	} else if (MoodleConnectorConfig.MAX_NEW_POSTS_ON_POLL.equals(key)) {
	    return ConfigParameterUse.OPTIONAL;
	} else if (MoodleConnectorConfig.POLL_COOLDOWN.equals(key)) {
	    return ConfigParameterUse.OPTIONAL;
	}

	return ConfigParameterUse.NOT_USED;
    }

    @Override
    public IConnector createConnector(String id, Model model,
	    Map<String, Object> parameters) throws ConnectorException {
	IConnector connector = new MoodleConnector();
	connector.initialize(id, model, parameters);
	return connector;
    }

}
