
package de.m0ep.socc.core.user;

import java.util.List;

import org.ontoware.rdf2go.model.Model;
import org.rdfs.sioc.UserAccount;

import com.xmlns.foaf.OnlineAccount;
import com.xmlns.foaf.Person;

public interface IUserManager {
    public Model getModel();

    public List<OnlineAccount> listOnlineAccounts(Person person);

    public Person findPersonOfUserAccount(UserAccount userAccount);
}
