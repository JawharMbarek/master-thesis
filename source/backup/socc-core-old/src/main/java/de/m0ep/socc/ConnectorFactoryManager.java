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

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.m0ep.socc.exceptions.NotFoundException;

/**
 * Singleton class that loads all {@link IConnectorFactory} implementing classes
 * in the classpath at the first invocation.
 * 
 * @author Florian Müller
 * 
 */
public final class ConnectorFactoryManager {
    private static final Logger LOG = LoggerFactory
	    .getLogger(ConnectorFactoryManager.class);

    private static ConnectorFactoryManager instance;

    /**
     * Returns an unique instance of {@link ConnectorFactoryManager}.
     * 
     * @return Instance of {@link ConnectorFactoryManager}.
     */
    public static ConnectorFactoryManager getInstance() {
	if (null == instance) {
	    instance = new ConnectorFactoryManager();
	}

	return instance;
    }

    private Map<String, IConnectorFactory> factories;

    /*
     * Private Singleton constructor.
     */
    private ConnectorFactoryManager() {
	factories = new HashMap<>();

	ServiceLoader<IConnectorFactory> serviceLoader = ServiceLoader.load(
		IConnectorFactory.class);

	for (IConnectorFactory factory : serviceLoader) {
	    LOG.debug("Load factory {}", factory.getId());
	    factories.put(factory.getId(), factory);
	}
    }

    /**
     * Returns a unmodifiable {@link Collection} of all loaded factories.
     * 
     * @return {@link Collection} of {@link IConnectorFactory}s.
     */
    public Collection<IConnectorFactory> getFactories() {
	return Collections.unmodifiableCollection(factories.values());
    }

    /**
     * Returns the factory with the passed id.
     * 
     * @param id
     *            Id of the wanted factory.
     * 
     * @return The factory with the passed id.
     * 
     * @throws NotFoundException
     *             Thrown if there is no factory wiht this id.
     */
    public IConnectorFactory getFactory(final String id)
	    throws NotFoundException {
	if (null == id || !factories.containsKey(id)) {
	    LOG.warn("There is no factory with the id={}", id);
	    throw new NotFoundException("There is no factory with the id=" + id);
	}

	return factories.get(id);
    }
}
