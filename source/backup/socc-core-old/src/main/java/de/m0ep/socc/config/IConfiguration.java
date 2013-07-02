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

package de.m0ep.socc.config;

import java.io.Serializable;
import java.util.Collection;

import org.ontoware.rdf2go.model.node.URI;

import de.m0ep.sioc.service.auth.Service;
import de.m0ep.sioc.service.auth.UserAccount;
import de.m0ep.socc.IConnector;

/**
 * Interface for classes that store configuration data for {@link IConnector}s.
 * 
 * @author Florian Müller
 * 
 */
public interface IConfiguration extends Serializable {

    /**
     * An entry that represents a {@link IConnector}.
     * 
     * @author Florian Müller
     * 
     */
    public static interface IEntry extends Serializable {
	/**
	 * Returns the id of the connector.
	 * 
	 * @return Id as {@link String}.
	 */
	public String getId();

	/**
	 * Returns the id of the factory that created the connector to recreate
	 * it.
	 * 
	 * @return Factory id as {@link String}.
	 */
	public String getFactoryId();

	/**
	 * Returns the RDF resource that is associated with this connector.
	 * 
	 * @return An {@link Service} {@link URI}.
	 */
	public URI getService();

	/**
	 * Returns the SIOC {@link UserAccount} that is associated with this
	 * connector.
	 * 
	 * @return An {@link UserAccount} {@link URI}.
	 */
	public URI getUserAccount();
    }

    /**
     * Returns all entries as an unmodifiable {@link Collection}
     * 
     * @return
     */
    public Collection<IEntry> getEntries();

    /**
     * Add an new {@link IEntry} to the configuration.
     * 
     * @param entry
     *            The {@link IEntry} to add.
     * 
     * @throws NullPointerException
     *             Thrown if <code>entry</code> is null.
     */
    public void addEntry(IEntry entry);

    /**
     * Removes an {@link IEntry} from the configuration.
     * 
     * @param entry
     *            The {@link IEntry} to remove
     * 
     * @throws NullPointerException
     *             Thrown if <code>entry</code> is null.
     */
    public void removeEntry(IEntry entry);
}
