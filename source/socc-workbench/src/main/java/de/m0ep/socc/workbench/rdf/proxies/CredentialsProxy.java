
package de.m0ep.socc.workbench.rdf.proxies;

import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.node.Resource;
import org.ontoware.rdf2go.model.node.URI;

import com.google.common.base.Objects;

import de.m0ep.sioc.service.auth.Credential;

public class CredentialsProxy extends AbstractRdfResourceProxy {
    private static final String PROPERTY_VALUE = "value";

    private static final long serialVersionUID = 1L;

    private Credential credential;

    public static CredentialsProxy createNewInstance(Model model, URI uri) {
        return new CredentialsProxy(model, new Credential(model, uri, true));
    }

    public CredentialsProxy(Model model, Resource resource) {
        super(model, resource);

        this.credential = Credential.getInstance(getModel(), getResource());
    }

    public Credential getCredential() {
        return credential;
    }

    public String getValue() {
        return credential.getValue();
    }

    public void setValue(String value) {
        firePropertyChange(PROPERTY_VALUE, getValue(), value);
        credential.setValue(value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getResource(), getValue());
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (this.getClass() != obj.getClass()) {
            return false;
        }

        CredentialsProxy other = (CredentialsProxy) obj;

        return Objects.equal(this.getResource(), other.getResource()) &&
                Objects.equal(this.getValue(), other.getValue());
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("resource", getResource())
                .add("value", getValue())
                .toString();
    }
}
