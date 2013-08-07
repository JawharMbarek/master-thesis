
package de.m0ep.socc.config;

import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.model.node.impl.URIImpl;

/**
 * Vocabulary File. Created by org.ontoware.rdf2go.util.VocabularyWriter on Wed Jul 10 13:55:08 CEST
 * 2013 input file: soccconfig.owl namespace: http://www.m0ep.de/ontologies/2013/soccconfig#
 */
public interface SoccConfigVocabulary {
    public static final URI NS_SoccConfigVocabulary = new URIImpl(
            "http://www.m0ep.de/ontologies/2013/soccconfig#", false );

    /**
     */
    public static final URI Configuration = new URIImpl(
            "http://www.m0ep.de/ontologies/2013/soccconfig#ConnectorConfig", false );

    /**
     * Comment: http://www.m0ep.de/ontologies/2013/soccconfig#Configuration Range:
     * http://www.w3.org/2001/XMLSchema#string
     */
    public static final URI id = new URIImpl( "http://www.m0ep.de/ontologies/2013/soccconfig#id",
            false );

    public static final URI connectorClass = new URIImpl(
            "http://www.m0ep.de/ontologies/2013/soccconfig#connectorClass", false );

    /**
     * Comment: http://www.m0ep.de/ontologies/2013/soccconfig#Configuration Range:
     * http://rdfs.org/sioc/ns#UserAccount
     */
    public static final URI defaultUser = new URIImpl(
            "http://www.m0ep.de/ontologies/2013/soccconfig#defaultUser", false );

    /**
     * Comment: http://www.m0ep.de/ontologies/2013/soccconfig#Configuration Range:
     * http://rdfs.org/sioc/services#Service
     */
    public static final URI service = new URIImpl(
            "http://www.m0ep.de/ontologies/2013/soccconfig#service", false );

}
