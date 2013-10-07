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

package de.m0ep.canvas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.google.gson.JsonArray;

import de.m0ep.canvas.exceptions.CanvasLmsException;

/**
 * Class to navigate through canvas pagination
 * 
 * @author Florian Müller
 * 
 * @param <T>
 *            Ressource type
 */
public class Pagination<T> implements Iterable<List<T>> {
	private static final Logger LOG = LoggerFactory.getLogger( Pagination.class );

	/**
	 * Pagination iterator to iterate through pages.
	 * 
	 * @author Florian Müller
	 */
	protected class PaginationIterator implements Iterator<List<T>> {
		private Pagination<T> pagination;
		private boolean initialPage = false;

		public PaginationIterator( Pagination<T> pagination ) {
			this.pagination = pagination;
			this.initialPage = true;
		}

		@Override
		public boolean hasNext() {
			if ( initialPage ) {
				return true;
			}

			return pagination.hasNext();
		}

		@Override
		public List<T> next() {
			if ( initialPage ) {
				initialPage = false;
				return pagination.getData();
			}

			if ( !pagination.hasNext() ) {
				throw new NoSuchElementException( "No further pages to fetch." );
			}

			pagination = pagination.fetchNextPage();
			return pagination.getData();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException(
			        "Remove is not supported by "
			                + PaginationIterator.class.getSimpleName() );
		}
	}

	private final CanvasLmsClient client;
	private final Class<T> paginationType;
	private final String nextURL;
	private final List<T> data;

	public Pagination(
	        final CanvasLmsClient client,
	        final String json,
	        final String next,
	        final Class<T> paginationType ) {
		this.paginationType = paginationType;
		this.client = client;
		this.nextURL = next;

		JsonArray array = client.getGson()
		        .fromJson( json, JsonArray.class );
		List<T> pageItems = new ArrayList<T>();
		for ( int i = 0; i < array.size(); i++ ) {
			pageItems.add( client.getGson().fromJson(
			        array.get( i ),
			        paginationType ) );
		}

		this.data = Collections.unmodifiableList( pageItems );
	}

	@Override
	public Iterator<List<T>> iterator() {
		return new PaginationIterator( this );
	}

	public Pagination<T> fetchNextPage() {
		LOG.info( "Fetch next page {}.", getNextURL() );
		try {
			CanvasLmsRequest<T> request = new CanvasLmsRequest<T>(
			        client,
			        HttpGet.class,
			        getNextURL() + "",
			        paginationType ) {
			};
			request.setOauthToken( client.getOAuthToken() );

			return request.executePagination();
		} catch ( CanvasLmsException e ) {
			throw new RuntimeException( e );
		}
	}

	public String getNextURL() {
		return nextURL;
	}

	public List<T> getData() {
		return data;
	}

	public boolean hasNext() {
		return !Strings.isNullOrEmpty( nextURL );
	}
}
