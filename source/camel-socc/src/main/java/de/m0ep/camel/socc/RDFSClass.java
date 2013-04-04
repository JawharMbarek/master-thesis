package de.m0ep.camel.socc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.Statement;
import org.ontoware.rdf2go.model.node.Resource;

import de.m0ep.socc.utils.RDF2GoUtils;

public class RDFSClass implements List<Statement>, Serializable {
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

    @Override
    public boolean addAll(int index, Collection<? extends Statement> c) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public Statement get(int index) {
	return delegate.get(index);
    }

    @Override
    public Statement set(int index, Statement element) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void add(int index, Statement element) {
	// TODO Auto-generated method stub

    }

    @Override
    public Statement remove(int index) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public int indexOf(Object o) {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public ListIterator<Statement> listIterator() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public ListIterator<Statement> listIterator(int index) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<Statement> subList(int fromIndex, int toIndex) {
	// TODO Auto-generated method stub
	return null;
    }

}
