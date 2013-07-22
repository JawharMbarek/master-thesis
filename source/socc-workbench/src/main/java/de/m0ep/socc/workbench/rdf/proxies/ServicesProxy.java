
package de.m0ep.socc.workbench.rdf.proxies;

import java.util.List;

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.node.Resource;

import com.google.common.collect.Lists;

import de.m0ep.sioc.service.auth.Service;

public class ServicesProxy extends AbstractRdfStoreProxy {
    public static final String PROPERTY_SERVICES = "services";

    private static final long serialVersionUID = 1L;

    public ServicesProxy(Model rdfModel) {
        super(rdfModel);
    }

    public List<ServiceProxy> getServices() {
        ClosableIterator<Resource> serviceIter = Service.getAllInstances(getModel());
        List<ServiceProxy> result = Lists.newArrayList();

        while (serviceIter.hasNext()) {
            result.add(new ServiceProxy(getModel(), serviceIter.next()));
        }
        serviceIter.close();

        return result;
    }

    public void removeService(ServiceProxy serviceProxy) {
        Service.deleteAllProperties(
                serviceProxy.getService().getModel(),
                serviceProxy.getService());
    }
}
