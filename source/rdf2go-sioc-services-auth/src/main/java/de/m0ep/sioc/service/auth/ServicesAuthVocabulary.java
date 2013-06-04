package de.m0ep.sioc.service.auth;

import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.model.node.impl.URIImpl;

/**
 * Vocabulary File. Created by org.ontoware.rdf2go.util.VocabularyWriter on Mon
 * Jun 03 16:25:04 CEST 2013 input file: sioc-service-auth.owl namespace:
 * http://www.m0ep.de/sioc-service-auth#
 */
public interface ServicesAuthVocabulary {
    public static final URI NS_ServicesAuthVocabulary = new URIImpl(
	    "http://www.m0ep.de/sioc-service-auth#", false);

    /**
     */
    public static final URI RefreshToken = new URIImpl(
	    "http://www.m0ep.de/sioc-service-auth#RefreshToken", false);

    /**
     */
    public static final URI ClientSecret = new URIImpl(
	    "http://www.m0ep.de/sioc-service-auth#ClientSecret", false);

    /**
     */
    public static final URI ClientId = new URIImpl(
	    "http://www.m0ep.de/sioc-service-auth#ClientId", false);

    /**
     */
    public static final URI Credential = new URIImpl(
	    "http://www.m0ep.de/sioc-service-auth#Credential", false);

    /**
     */
    public static final URI Direct = new URIImpl(
	    "http://www.m0ep.de/sioc-service-auth#Direct", false);

    /**
     */
    public static final URI WebAPI = new URIImpl(
	    "http://www.m0ep.de/sioc-service-auth#WebAPI", false);

    /**
     */
    public static final URI Authentication = new URIImpl(
	    "http://www.m0ep.de/sioc-service-auth#Authentication", false);

    /**
     */
    public static final URI Username = new URIImpl(
	    "http://www.m0ep.de/sioc-service-auth#Username", false);

    /**
     */
    public static final URI AccessToken = new URIImpl(
	    "http://www.m0ep.de/sioc-service-auth#AccessToken", false);

    /**
     */
    public static final URI APIKey = new URIImpl(
	    "http://www.m0ep.de/sioc-service-auth#APIKey", false);

    /**
     */
    public static final URI OAuth = new URIImpl(
	    "http://www.m0ep.de/sioc-service-auth#OAuth", false);

    /**
     */
    public static final URI Password = new URIImpl(
	    "http://www.m0ep.de/sioc-service-auth#Password", false);

    /**
     * Comment: http://www.m0ep.de/sioc-service-auth#OAuth
     */
    public static final URI has_authorizationurl = new URIImpl(
	    "http://www.m0ep.de/sioc-service-auth#has_authorizationurl", false);

    /**
     * Comment: http://www.m0ep.de/sioc-service-auth#OAuth
     */
    public static final URI has_requesttokenendpoint = new URIImpl(
	    "http://www.m0ep.de/sioc-service-auth#has_requesttokenendpoint",
	    false);

    /**
     * Comment: http://www.m0ep.de/sioc-service-auth#OAuth
     */
    public static final URI has_accesstokenendpoint = new URIImpl(
	    "http://www.m0ep.de/sioc-service-auth#has_accesstokenendpoint",
	    false);

    /**
     * Comment: http://www.m0ep.de/sioc-service-auth#OAuth Range:
     * http://www.w3.org/2000/01/rdf-schema#Literal
     */
    public static final URI oauth_version = new URIImpl(
	    "http://www.m0ep.de/sioc-service-auth#oauth_version", false);

    /**
     * Comment: http://www.m0ep.de/sioc-service-auth#Authentication Range:
     * http://www.m0ep.de/sioc-service-auth#Credential
     */
    public static final URI has_credential = new URIImpl(
	    "http://www.m0ep.de/sioc-service-auth#has_credential", false);

    /**
     * Comment: http://rdfs.org/sioc/ns#UserAccount Range:
     * http://www.m0ep.de/sioc-service-auth#Authentication
     */
    public static final URI has_authentication = new URIImpl(
	    "http://www.m0ep.de/sioc-service-auth#has_authentication", false);

    /**
     * Comment: http://rdfs.org/sioc/services#Service Range:
     * http://www.m0ep.de/sioc-service-auth#Authentication
     */
    public static final URI require_authentication = new URIImpl(
	    "http://www.m0ep.de/sioc-service-auth#require_authentication",
	    false);

}
