
package de.m0ep.socc.workbench.rdf.proxies;

import org.ontoware.rdf2go.model.Model;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

public abstract class AbstractRdfStoreProxy extends com.jgoodies.binding.beans.Model {
    public static final String PROPTERY_RDF_MODEL = "rdfModel";

    private static final long serialVersionUID = 1L;

    protected Model model;

    public AbstractRdfStoreProxy(Model model) {
        this.model = Preconditions.checkNotNull(model,
                "Required parameter rdfModel must be specified.");
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(model);
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

        AbstractRdfStoreProxy other = (AbstractRdfStoreProxy) obj;

        return Objects.equal(this.model, other.model);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("rdfModel", model)
                .toString();
    }
}
