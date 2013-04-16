package de.m0ep.foaf;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.ontoware.rdf2go.exception.ModelRuntimeException;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.QueryResultTable;
import org.ontoware.rdf2go.model.QueryRow;
import org.ontoware.rdf2go.model.Statement;
import org.ontoware.rdf2go.model.node.Node;
import org.ontoware.rdf2go.model.node.Variable;
import org.ontoware.rdf2go.util.SparqlUtil;

import com.google.common.base.Preconditions;
import com.xmlns.foaf.FOAF;
import com.xmlns.foaf.Person;

public class FOAFAccountMatcher implements IFOAFAccountMatcher {
    private Model model;

    public FOAFAccountMatcher(final Model model) {
	this.model = Preconditions.checkNotNull(model, "Model can not be null");
	Preconditions.checkArgument(model.isOpen(), "Model must be open");
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.m0ep.foaf.IFOAFAccountMatcher#addPerson(java.lang.String)
     */
    public void addPerson(final String foafXML) throws ModelRuntimeException,
	    IOException {
	Preconditions.checkNotNull(foafXML, "FoafXML can not be null");
	model.readFrom(new StringReader(foafXML));
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.m0ep.foaf.IFOAFAccountMatcher#addPerson(com.xmlns.foaf.Person)
     */
    public void addPerson(final Person person) {
	Preconditions.checkNotNull(person, "Person can not be null");
	ClosableIterator<Statement> stmtIter = person.getModel()
		.findStatements(person, Variable.ANY, Variable.ANY);
	model.addAll(stmtIter);
	stmtIter.close();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.m0ep.foaf.IFOAFAccountMatcher#findPersonByOnlineAccount(java.lang.
     * String, java.lang.String)
     */
    public List<Person> findPersonByOnlineAccount(final String accountName,
	    final String serviceHomepage) {
	Preconditions.checkNotNull(accountName, "AccountName can not be null");
	Preconditions.checkArgument(!accountName.isEmpty(),
		"AccountName can not be empty");
	String query = "SELECT ?person WHERE {" + "?person " + FOAF.account
		+ " ?acc . " + "?acc " + FOAF.accountName + " %s ."
		+ " OPTIONAL {" + "?acc " + FOAF.accountServiceHomepage
		+ " %s ." + "}}";

	QueryResultTable qrt = model.sparqlSelect(SparqlUtil.formatQuery(query,
		accountName, serviceHomepage));
	List<Person> result = new ArrayList<Person>();

	for (QueryRow row : qrt) {
	    Node node = row.getValue("person");

	    Person person = Person.getInstance(model, node.asResource());
	    if (null != person) {
		result.add(person);
	    }
	}

	return result;
    }
}
