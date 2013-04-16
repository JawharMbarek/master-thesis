package de.m0ep.foaf;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.List;

import com.xmlns.foaf.Person;

public interface IFOAFAccountMatcher {

    public abstract void readFOAFXML(Reader reader) throws IOException;

    public abstract void readFOAFXML(InputStream stream) throws IOException;

    public abstract void addPerson(Person person);

    public abstract List<Person> findPersonByOnlineAccount(String accountName,
	    String serviceHomepage);

}