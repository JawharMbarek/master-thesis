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

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.ontoware.rdf2go.model.node.URI;

public class Configuration implements IConfiguration {
    private static final long serialVersionUID = 3138524513306771841L;

    public static class Entry implements IEntry {
	private static final long serialVersionUID = 2242221038052184427L;

	private String id;
	private String factoryId;
	private URI service;
	private URI userAccount;

	public Entry() {
	    this.id = null;
	    this.factoryId = null;
	    this.service = null;
	    this.userAccount = null;
	}

	public Entry(String id, String factoryId, URI service, URI userAccount) {
	    this();
	    this.id = id;
	    this.factoryId = factoryId;
	    this.service = service;
	    this.userAccount = userAccount;
	}

	@Override
	public String getId() {
	    return id;
	}

	public void setId(final String id) {
	    this.id = id;
	}

	@Override
	public String getFactoryId() {
	    return factoryId;
	}

	public void setFactoryId(final String factoryId) {
	    this.factoryId = factoryId;
	}

	@Override
	public URI getService() {
	    return service;
	}

	public void setService(final URI service) {
	    this.service = service;
	}

	@Override
	public URI getUserAccount() {
	    return userAccount;
	}

	public void setUserAccount(final URI userAccount) {
	    this.userAccount = userAccount;
	}
    }

    private Map<String, IEntry> entries;

    public Configuration() {
	entries = new HashMap<>();
    }

    @Override
    public Collection<IEntry> getEntries() {
	return Collections.unmodifiableCollection(entries.values());
    }

    @Override
    public void addEntry(IEntry entry) {
	entries.put(entry.getId(), entry);
    }

    @Override
    public void removeEntry(IEntry entry) {
	if (entries.containsKey(entry.getId())) {
	    entries.remove(entry.getId());
	}
    }

}
