
package de.m0ep.socc.workbench.rdf.proxies;

import java.util.List;

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.node.Resource;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.util.Builder;

import com.google.common.collect.Lists;

import de.m0ep.sioc.service.auth.Authentication;
import de.m0ep.sioc.service.auth.Service;

public class ServiceProxy extends AbstractRdfResourceProxy {
    public static final String PROPERTY_MAX_RESULTS = "maxResults";
    public static final String PROPERTY_RESULT_FORMAT = "resultFormat";
    public static final String PROPERTY_SERVICE_ENDPOINT = "serviceEndpoint";
    public static final String PROPERTY_SERVICE_DEFINITION = "serviceDefinition";
    public static final String PROPERTY_SERVICE_PROTOCOL = "serviceProtocol";
    public static final String PROPERTY_AUTHENTICATIONS = "authentications";

    private static final long serialVersionUID = 1L;

    private Service service;

    public static ServiceProxy createNewInstance(Model model, URI uri) {
        return new ServiceProxy(model, new Service(model, uri, true));
    }

    public ServiceProxy(Model model, Resource resource) {
        super(model, resource);

        this.service = Service.getInstance(getModel(), getResource());
    }

    public Service getService() {
        return service;
    }

    public int getMaxResults() {
        return service.getMaxResults();
    }

    public void setMaxResults(int maxResults) {
        firePropertyChange(PROPERTY_MAX_RESULTS, getMaxResults(), maxResults);
        service.setMaxResults(maxResults);
    }

    public String getResultFormat() {
        return service.getResultsFormat().asLiteral().getValue();
    }

    public void setResultFormat(String resultFormat) {
        firePropertyChange(PROPERTY_RESULT_FORMAT, getResultFormat(), resultFormat);
        service.setResultsFormat(Builder.createPlainliteral(resultFormat));
    }

    public String getServiceEndpoint() {
        return service.getServiceEndpoint().toString();
    }

    public void setServiceEndpoint(String serviceEndpoint) {
        firePropertyChange(PROPERTY_SERVICE_ENDPOINT, getServiceEndpoint(), serviceEndpoint);
        service.setServiceEndpoint(Builder.createURI(serviceEndpoint));
    }

    public String getServiceDefinition() {
        return service.getServiceDefinition().asLiteral().getValue();
    }

    public void setServiceDefinition(String serviceDefinition) {
        firePropertyChange(PROPERTY_SERVICE_DEFINITION, getServiceDefinition(), serviceDefinition);
        service.setServiceDefinition(Builder.createPlainliteral(serviceDefinition));
    }

    public String getServiceProtocol() {
        return service.getServiceProtocol().asLiteral().getValue();
    }

    public void setServiceProtocol(String serviceProtocol) {
        firePropertyChange(PROPERTY_SERVICE_PROTOCOL, getServiceProtocol(), serviceProtocol);
        service.setServiceProtocol(Builder.createPlainliteral(serviceProtocol));
    }

    public List<AuthenticationProxy> getAuthentications() {
        ClosableIterator<Authentication> iterator = service.getAllAuthentication();
        List<AuthenticationProxy> result = Lists.newArrayList();

        while (iterator.hasNext()) {
            result.add(new AuthenticationProxy(
                    service.getModel(),
                    iterator.next()));
        }
        iterator.close();

        return result;
    }

    public void addAuthentication(AuthenticationProxy authenticationProxy) {
        firePropertyChange(PROPERTY_AUTHENTICATIONS, null, null);
        service.addAuthentication(authenticationProxy.getAuthentication());
    }

    public void removetAuthentication(AuthenticationProxy authenticationProxy) {
        firePropertyChange(PROPERTY_AUTHENTICATIONS, null, null);
        service.removeAuthentication(authenticationProxy.getAuthentication());
    }
}
