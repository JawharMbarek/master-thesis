package de.m0ep.camel.socc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.Statement;
import org.ontoware.rdf2go.model.node.Resource;

import de.m0ep.socc.utils.RDF2GoUtils;

public class RDFSClass implements Collection<Statement>, Serializable {
    private static final long serialVersionUID = 3885667839526513297L;
    private List<Statement> delegate;

    public RDFSClass() {
	this.delegate = new ArrayList<Statement>();
    }

    public RDFSClass(final Model model, final Resource resource) {
	this.delegate = RDF2GoUtils.getAllStatements(model, resource);
    }

    @Override
    public int size() {
	return delegate.size();
    }

    @Override
    public boolean isEmpty() {
	return delegate.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
	return delegate.contains(o);
    }

    @Override
    public Iterator<Statement> iterator() {
	return delegate.iterator();
    }

    @Override
    public Object[] toArray() {
	return delegate.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
	return delegate.toArray(a);
    }

    @Override
    public boolean add(Statement e) {
	return delegate.add(e);
    }

    @Override
    public boolean remove(Object o) {
	return delegate.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
	return delegate.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends Statement> c) {
	return delegate.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
	return delegate.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
	return delegate.retainAll(c);
    }

    @Override
    public void clear() {
	delegate.clear();
    }

}
