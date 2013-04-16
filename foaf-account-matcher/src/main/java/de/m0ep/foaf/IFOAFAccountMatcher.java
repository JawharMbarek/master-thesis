package de.m0ep.foaf;

import java.io.IOException;
import java.util.List;

import org.ontoware.rdf2go.exception.ModelRuntimeException;

import com.xmlns.foaf.Person;

public interface IFOAFAccountMatcher {

    public abstract void addPerson(String foafXML)
	    throws ModelRuntimeException, IOException;

    public abstract void addPerson(Person person);

    public abstract List<Person> findPersonByOnlineAccount(String accountName,
	    String serviceHomepage);

}