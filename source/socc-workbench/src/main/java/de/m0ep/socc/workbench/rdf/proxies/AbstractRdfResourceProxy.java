
package de.m0ep.socc.workbench.rdf.proxies;

import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.node.Resource;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

public abstract class AbstractRdfResourceProxy extends AbstractRdfStoreProxy {
    public static final String PROPERTY_RESOURCE = "resource";

    private static final long serialVersionUID = 1L;

    protected Resource resource;

    public AbstractRdfResourceProxy(Model model, Resource resource) {
        super(model);

        this.resource = Preconditions.checkNotNull(resource,
                "Required parameter resource must be specified.");
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(resource);
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

        AbstractRdfResourceProxy other = (AbstractRdfResourceProxy) obj;

        return Objects.equal(this.resource, other.resource);
    }
}
