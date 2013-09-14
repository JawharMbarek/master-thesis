package de.m0ep.socc.config;

import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.model.node.impl.URIImpl;

/**
 * Vocabulary File. Created by org.ontoware.rdf2go.util.VocabularyWriter on Thu
 * Aug 08 11:59:26 CEST 2013 input file:
 * /home/florian/Develop/uni/master-thesis/resources/ontologies/socc-config.xml
 * namespace: http://www.m0ep.de/socc/config#
 */
public interface SoccConfigVocabulary {
	public static final URI NS_SoccConfigVocabulary = new URIImpl(
	        "http://www.m0ep.de/socc/config#", false );

	/**
     */
	public static final URI ConnectorConfig = new URIImpl(
	        "http://www.m0ep.de/socc/config#ConnectorConfig", false );

	/**
	 * Comment: http://www.m0ep.de/socc/config#ConnectorConfig Range:
	 * http://www.w3.org/2001/XMLSchema#string
	 */
	public static final URI connectorClassName = new URIImpl(
	        "http://www.m0ep.de/socc/config#connectorClassName", false );

	/**
	 * Comment: http://www.m0ep.de/socc/config#ConnectorConfig Range:
	 * http://www.w3.org/2001/XMLSchema#string
	 */
	public static final URI id = new URIImpl( "http://www.m0ep.de/socc/config#id", false );

	/**
	 * Comment: http://www.m0ep.de/socc/config#ConnectorConfig Range:
	 * http://rdfs.org/sioc/ns#UserAccount
	 */
	public static final URI defaultUserAccount = new URIImpl(
	        "http://www.m0ep.de/socc/config#defaultUserAccount", false );

	/**
	 * Comment: http://www.m0ep.de/socc/config#ConnectorConfig Range:
	 * http://rdfs.org/sioc/services#Service
	 */
	public static final URI service = new URIImpl( "http://www.m0ep.de/socc/config#service", false );

	/**
	 * Comment: http://www.m0ep.de/socc/config#messageTemplate Range:
	 * http://www.w3.org/2001/XMLSchema#string
	 */
	public static final URI unknownMessageTemplate = new URIImpl(
	        "http://www.m0ep.de/socc/config#unknownMessageTemplate",
	        false );
	/**
	 * Comment: http://www.m0ep.de/socc/config#mappedTo Range:
	 * http://rdfs.org/sioc/ns#UserAccount
	 */
	public static final URI MAPPED_TO = new URIImpl(
	        "http://www.m0ep.de/socc/config#mappedTo", false );
}
