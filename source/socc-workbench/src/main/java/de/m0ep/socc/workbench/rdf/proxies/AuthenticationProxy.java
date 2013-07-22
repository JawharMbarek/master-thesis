
package de.m0ep.socc.workbench.rdf.proxies;

import java.util.List;

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.node.Resource;
import org.ontoware.rdf2go.model.node.URI;

import com.google.common.collect.Lists;

import de.m0ep.sioc.service.auth.Authentication;
import de.m0ep.sioc.service.auth.Credential;

public class AuthenticationProxy extends AbstractRdfResourceProxy {
    public static final String PROPERTY_CREDENTIALS = "credentials";

    private static final long serialVersionUID = 1L;

    protected Authentication authentication;

    public static AuthenticationProxy createNewInstance(Model model, URI uri) {
        return new AuthenticationProxy(model, new Authentication(model, uri, true));
    }

    public AuthenticationProxy(Model Model, Resource resource) {
        super(Model, resource);

        this.authentication = Authentication.getInstance(getModel(), getResource());
    }

    public Authentication getAuthentication() {
        return authentication;
    }

    public List<CredentialsProxy> getCredentials() {
        ClosableIterator<Credential> iterator = authentication.getAllCredential();
        List<CredentialsProxy> result = Lists.newArrayList();

        while (iterator.hasNext()) {
            result.add(new CredentialsProxy(
                    authentication.getModel(),
                    iterator.next()));
        }
        iterator.close();

        return result;
    }

    public void addCredential(CredentialsProxy credentialsProxy) {
        firePropertyChange(PROPERTY_CREDENTIALS, null, null);
        authentication.addCredential(credentialsProxy.getCredential());
    }

    public void removeCredential(CredentialsProxy credentialsProxy) {
        firePropertyChange(PROPERTY_CREDENTIALS, null, null);
        authentication.removeCredential(credentialsProxy.getCredential());
    }
}
