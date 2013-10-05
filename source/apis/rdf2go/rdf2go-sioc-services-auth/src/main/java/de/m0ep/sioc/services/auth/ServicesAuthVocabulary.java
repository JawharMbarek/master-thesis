package de.m0ep.sioc.services.auth;

import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.model.node.impl.URIImpl;

/**
 * Vocabulary File. Created by org.ontoware.rdf2go.util.VocabularyWriter on Thu Aug 08 12:01:53 CEST 2013
 * input file: /home/florian/Develop/uni/master-thesis/resources/ontologies/sioc-service-auth.xml
 * namespace: http://www.m0ep.de/sioc/services/auth#
 */
public interface ServicesAuthVocabulary {
	public static final URI NS_ServicesAuthVocabulary = new URIImpl("http://www.m0ep.de/sioc/services/auth#",false);

    /**
     */
    public static final URI APIKey = new URIImpl("http://purl.oclc.org/NET/WebApiAuthentication#APIKey", false);

    /**
     */
    public static final URI AuthenticationMechanism = new URIImpl("http://purl.oclc.org/NET/WebApiAuthentication#AuthenticationMechanism", false);

    /**
     */
    public static final URI Credentials = new URIImpl("http://purl.oclc.org/NET/WebApiAuthentication#Credentials", false);

    /**
     */
    public static final URI Direct = new URIImpl("http://purl.oclc.org/NET/WebApiAuthentication#Direct", false);

    /**
     */
    public static final URI OAuth = new URIImpl("http://purl.oclc.org/NET/WebApiAuthentication#OAuth", false);

    /**
     */
    public static final URI Password = new URIImpl("http://purl.oclc.org/NET/WebApiAuthentication#Password", false);

    /**
     */
    public static final URI Username = new URIImpl("http://purl.oclc.org/NET/WebApiAuthentication#Username", false);

    /**
     */
    public static final URI AccessToken = new URIImpl("http://www.m0ep.de/sioc/services/auth#AccessToken", false);

    /**
     */
    public static final URI ClientId = new URIImpl("http://www.m0ep.de/sioc/services/auth#ClientId", false);

    /**
     */
    public static final URI ClientSecret = new URIImpl("http://www.m0ep.de/sioc/services/auth#ClientSecret", false);

    /**
     */
    public static final URI RefreshToken = new URIImpl("http://www.m0ep.de/sioc/services/auth#RefreshToken", false);

    /**
     */
    public static final URI WebAPI = new URIImpl("http://www.m0ep.de/sioc/services/auth#WebAPI", false);

    /**
     */
    public static final URI hasInputCredentials = new URIImpl("http://purl.oclc.org/NET/WebApiAuthentication#hasInputCredentials", false);

    /**
     * Comment: http://rdfs.org/sioc/services#Service 
     * Range: http://purl.oclc.org/NET/WebApiAuthentication#AuthenticationMechanism 
     */
    public static final URI serviceAuthentication = new URIImpl("http://www.m0ep.de/sioc/services/auth#serviceAuthentication", false);

    /**
     * Comment: http://rdfs.org/sioc/ns#UserAccount 
     * Range: http://purl.oclc.org/NET/WebApiAuthentication#AuthenticationMechanism 
     */
    public static final URI accountAuthentication = new URIImpl("http://www.m0ep.de/sioc/services/auth#accountAuthentication", false);

    /**
     * Comment: http://purl.oclc.org/NET/WebApiAuthentication#AuthenticationMechanism 
     * Range: http://purl.oclc.org/NET/WebApiAuthentication#Credentials 
     */
    public static final URI credentials = new URIImpl("http://www.m0ep.de/sioc/services/auth#credentials", false);

}
