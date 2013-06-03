package org.rdfs.sioc.services;

import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.model.node.impl.URIImpl;

/**
 * Vocabulary File. Created by org.ontoware.rdf2go.util.VocabularyWriter on Mon Jun 03 15:15:47 CEST 2013
 * input file: services
 * namespace: http://rdfs.org/sioc/services#
 */
public interface ServicesVocabulary {
	public static final URI NS_ServicesVocabulary = new URIImpl("http://rdfs.org/sioc/services#",false);

    /**
     * Label: Service@en 
     * Comment: A Service is web service associated with a Site or part of it.@en 
     */
    public static final URI Service = new URIImpl("http://rdfs.org/sioc/services#Service", false);

    /**
     * Label: service protocol@en 
     * Comment: A protocol used by a web service. Possible protocol values include SOAP, REST, SPARQL-QUERY, GData and OpenSearch. These will be added to this module later.@en 
     * Comment: http://rdfs.org/sioc/services#Service 
     */
    public static final URI service_protocol = new URIImpl("http://rdfs.org/sioc/services#service_protocol", false);

    /**
     * Label: service endpoint@en 
     * Comment: URL of a web service endpoint.@en 
     * Comment: http://rdfs.org/sioc/services#Service 
     */
    public static final URI service_endpoint = new URIImpl("http://rdfs.org/sioc/services#service_endpoint", false);

    /**
     * Label: service definition@en 
     * Comment: Links to a web service definition of this sioc:Service.@en 
     * Comment: http://rdfs.org/sioc/services#Service 
     */
    public static final URI service_definition = new URIImpl("http://rdfs.org/sioc/services#service_definition", false);

    /**
     * Label: service of@en 
     * Comment: A SIOC object this Service is associated with.@en 
     * Comment: http://rdfs.org/sioc/services#Service 
     */
    public static final URI service_of = new URIImpl("http://rdfs.org/sioc/services#service_of", false);

    /**
     * Label: results format@en 
     * Comment: Format of results returned by a web service.@en 
     * Comment: http://rdfs.org/sioc/services#Service 
     */
    public static final URI results_format = new URIImpl("http://rdfs.org/sioc/services#results_format", false);

    /**
     * Label: max results@en 
     * Comment: Maximum number of results results returned by a web service.@en 
     * Comment: http://rdfs.org/sioc/services#Service 
     * Range: http://www.w3.org/2001/XMLSchema#integer 
     */
    public static final URI max_results = new URIImpl("http://rdfs.org/sioc/services#max_results", false);

    /**
     * Label: has service@en 
     * Comment: A Service associated with this SIOC object.@en 
     * Range: http://rdfs.org/sioc/services#Service 
     */
    public static final URI has_service = new URIImpl("http://rdfs.org/sioc/services#has_service", false);

}
