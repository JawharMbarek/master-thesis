package de.m0ep.canvas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.http.client.methods.HttpGet;

import com.google.common.base.Strings;
import com.google.gson.JsonArray;

import de.m0ep.canvas.exceptions.CanvasException;

public class Pagination<T> implements Iterable<List<T>> {
    protected class PaginationIterator implements Iterator<List<T>> {
	private Pagination<T> pagination;
	private boolean initialPage = false;

	public PaginationIterator(Pagination<T> pagination) {
	    this.pagination = pagination;
	    this.initialPage = true;
	}

	@Override
	public boolean hasNext() {
	    if (initialPage) {
		return true;
	    }

	    return pagination.hasNext();
	}

	@Override
	public List<T> next() {
	    if (initialPage) {
		initialPage = false;
		return pagination.getData();
	    }

	    if (!pagination.hasNext()) {
		throw new NoSuchElementException("No further pages to fetch.");
	    }

	    pagination = pagination.fetchNextPage();
	    return pagination.getData();
	}

	@Override
	public void remove() {
	    throw new UnsupportedOperationException(
		    "Remove is not supported by "
			    + PaginationIterator.class.getSimpleName());
	}
    }

    private Canvas client;
    private Class<T> paginationType;
    private String nextURL;
    private List<T> data;

    public Pagination(Canvas client, String json, String next,
	    Class<T> paginationType) {
	this.paginationType = paginationType;
	this.client = client;
	this.nextURL = next;

	JsonArray array = client.getGson()
		.fromJson(json, JsonArray.class);
	List<T> pageItems = new ArrayList<T>();
	for (int i = 0; i < array.size(); i++) {
	    pageItems.add(client.getGson().fromJson(
		    array.get(i),
		    paginationType));
	}

	this.data = Collections.unmodifiableList(pageItems);
    }

    @Override
    public Iterator<List<T>> iterator() {
	return new PaginationIterator(this);
    }

    public Pagination<T> fetchNextPage() {
	try {
	    CanvasRequest<T> request = new CanvasRequest<T>(
		    client,
		    HttpGet.class,
		    getNextURL(),
		    null,
		    paginationType) {
	    };

	    client.initialize(request);
	    return request.executePagination();
	} catch (CanvasException e) {
	    throw new RuntimeException(e);
	}
    }

    public String getNextURL() {
	return nextURL;
    }

    public List<T> getData() {
	return data;
    }

    public boolean hasNext() {
	return !Strings.isNullOrEmpty(nextURL);
    }
}
