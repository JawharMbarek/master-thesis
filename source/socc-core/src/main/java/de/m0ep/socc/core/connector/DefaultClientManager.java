/*
 * The MIT License (MIT) Copyright © 2013 "Florian Mueller"
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

package de.m0ep.socc.core.connector;

import java.util.List;
import java.util.Map;

import org.rdfs.sioc.UserAccount;
import org.rdfs.sioc.services.Service;

import com.google.api.client.util.Maps;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import de.m0ep.socc.core.exceptions.NotFoundException;

/**
 * Default implementation of a ClientManager. Every subclass should implement
 * the <code>init</code> and <code>createClient</code> method.
 * 
 * @author "Florian Mueller"
 * 
 * @param <T>
 *            Class of the clients
 */
public abstract class DefaultClientManager<T> implements
        IClientManager<T> {
	private Service service;
	private T defaultClient;
	private final Map<Integer, T> clientMap = Maps.newHashMap();

	/**
	 * Constructs a new ClientManager for a {@link Service} and a defaultClient
	 * form the <code>defaultUserAccount</code>. The method
	 * {@link DefaultClientManager#init()} will be called before the creation of
	 * the default client.
	 * 
	 * @param service
	 *            Service object with service credentials.
	 * @param defaultUserAccount
	 *            The default {@link UserAccount} with it's credentials.
	 * @throws Exception
	 *             Thrown if there is a problem creating the default client.
	 */
	public DefaultClientManager( Service service,
	        UserAccount defaultUserAccount )
	        throws Exception {
		this.service = Preconditions.checkNotNull( service,
		        "Required parameter service must be specified." );
		Preconditions.checkNotNull( defaultUserAccount,
		        "Required parameter defaultUserAccount must be specified." );

		init();
		this.defaultClient = createClient( defaultUserAccount );
	}

	/**
	 * Method to initialize the ClientManager
	 */
	protected abstract void init();

	@Override
	public abstract T createClient( UserAccount userAccount ) throws Exception;

	@Override
	public Service getService() {
		return service;
	}

	@Override
	public void setService( Service service ) {
		this.service = Preconditions.checkNotNull( service,
		        "Required parameter service must be specified." );
	}

	@Override
	public T getDefaultClient() {
		Preconditions.checkState( null != defaultClient,
		        "No default client set." );
	
		return defaultClient;
	}

	@Override
	public void setDefaultClient( T client ) {
		this.defaultClient = Preconditions.checkNotNull( client,
		        "Required parameter client must be specified." );
	}

	@Override
	public void add( UserAccount userAccount, T client ) {
		Preconditions.checkNotNull( userAccount,
		        "Required parameter userAccount must be specified." );
		Preconditions.checkArgument( userAccount.hasAccountName(),
		        "the paremeter userAccount has no accountName." );
		Preconditions.checkArgument( userAccount.hasAccountServiceHomepage(),
		        "The parameter userAccount has no accountServiceHomepage." );
		Preconditions.checkNotNull( client,
		        "Required parameter client must be specified." );

		clientMap.put(
		        generateKey( userAccount ),
		        client );
	}

	@Override
	public void remove( UserAccount userAccount ) {
		if ( null == userAccount ) {
			return;
		}

		Preconditions.checkArgument( userAccount.hasAccountName(),
		        "the paremeter userAccount has no accountName." );
		Preconditions.checkArgument( userAccount.hasAccountServiceHomepage(),
		        "The parameter userAccount has no accountServiceHomepage." );

		int key = generateKey( userAccount );
		if ( clientMap.containsKey( key ) ) {
			clientMap.remove( key );
		}
	}

	@Override
	public List<T> getAll() {
		return Lists.newArrayList( clientMap.values() );
	}

	@Override
	public T get( UserAccount userAccount ) throws NotFoundException {
		Preconditions.checkNotNull( userAccount,
		        "Required parameter userAccount must be specified." );
		Preconditions.checkArgument( userAccount.hasAccountName(),
		        "the paremeter userAccount has no accountName." );
		Preconditions.checkArgument( userAccount.hasAccountServiceHomepage(),
		        "The parameter userAccount has no accountServiceHomepage." );

		T result = clientMap.get( generateKey( userAccount ) );

		if ( null == result ) {
			throw new NotFoundException( "No client found for " + userAccount );
		}

		return result;
	}

	@Override
	public boolean contains( UserAccount userAccount ) {
		if ( null == userAccount ) {
			return false;
		}

		Preconditions.checkArgument( userAccount.hasAccountName(),
		        "the paremeter userAccount has no accountName." );
		Preconditions.checkArgument( userAccount.hasAccountServiceHomepage(),
		        "The parameter userAccount has no accountServiceHomepage." );

		return clientMap.containsKey( generateKey( userAccount ) );
	}

	@Override
	public void clear() {
		clientMap.clear();
	}

	private int generateKey( UserAccount userAccount ) {
		return Objects.hashCode(
		        userAccount.getAccountName(),
		        userAccount.getAccountServiceHomepage() );
	}

	@Override
	protected void finalize() throws Throwable {
		clear();
		super.finalize();
	}
}
