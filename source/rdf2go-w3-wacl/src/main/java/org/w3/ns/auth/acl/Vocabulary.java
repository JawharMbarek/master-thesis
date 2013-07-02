package org.w3.ns.auth.acl;

import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.model.node.impl.URIImpl;

/**
 * Vocabulary File. Created by org.ontoware.rdf2go.util.VocabularyWriter on Thu May 30 14:06:58 CEST 2013
 * input file: acl
 * namespace: http://www.w3.org/ns/auth/acl#
 */
public interface Vocabulary {
	public static final URI NS_ACLVocabulary = new URIImpl("http://www.w3.org/ns/auth/acl#",false);

    /**
     * Label: write@en 
     */
    public static final URI Write = new URIImpl("http://www.w3.org/ns/auth/acl#Write", false);

    /**
     * Label: read@en 
     * Comment: The class of read operations 
     */
    public static final URI Read = new URIImpl("http://www.w3.org/ns/auth/acl#Read", false);

    /**
     * Label: control@en 
     * Comment: Allows read/write access to the ACL for the resource(s) 
     */
    public static final URI Control = new URIImpl("http://www.w3.org/ns/auth/acl#Control", false);

    /**
     * Label: authorization 
     * Comment: An element of access control,
    allowing agent to agents access of some kind to resources or classes of resources 
     */
    public static final URI Authorization = new URIImpl("http://www.w3.org/ns/auth/acl#Authorization", false);

    /**
     * Label: append@en 
     * Comment: Append accesses are specific write access which only add information, and do not remove information.
    For text files, for example, append access allows bytes to be added onto the end of the file.
    For RDF graphs, Append access allows adds triples to the graph but does not remove any.
    Append access is useful for dropbox functionality.
    Dropbox can be used for link notification, which the information added is a notification
    that a some link has been made elsewhere relevant to the given resource.
     
     */
    public static final URI Append = new URIImpl("http://www.w3.org/ns/auth/acl#Append", false);

    /**
     * Comment: Any kind of access to a resource. Don't use this, use R W and RW 
     */
    public static final URI Access = new URIImpl("http://www.w3.org/ns/auth/acl#Access", false);

    /**
     * Label: owner@en 
     * Comment: The person or other agent which owns this. 
    For example, the owner of a file in a filesystem.
    There is a sense of right to control.   Typically defaults to the agent who craeted
    something but can be changed. 
     * Range: http://xmlns.com/foaf/0.1/Agent 
     */
    public static final URI owner = new URIImpl("http://www.w3.org/ns/auth/acl#owner", false);

    /**
     * Label: access mode 
     * Comment: A mode of access such as read or write. 
     * Comment: http://www.w3.org/ns/auth/acl#Authorization 
     * Range: http://www.w3.org/2000/01/rdf-schema#Class 
     */
    public static final URI mode = new URIImpl("http://www.w3.org/ns/auth/acl#mode", false);

    /**
     * Label: default access for new things in 
     * Comment: A directory for which this authorization is used for new files in the directory. 
     * Comment: http://www.w3.org/ns/auth/acl#Authorization 
     */
    public static final URI defaultForNew = new URIImpl("http://www.w3.org/ns/auth/acl#defaultForNew", false);

    /**
     * Label: agent class 
     * Comment: A class of persons or social entities to being given the right 
     * Comment: http://www.w3.org/ns/auth/acl#Authorization 
     * Range: http://www.w3.org/2000/01/rdf-schema#Class 
     */
    public static final URI agentClass = new URIImpl("http://www.w3.org/ns/auth/acl#agentClass", false);

    /**
     * Label: agent 
     * Comment: A person or social entity to being given the right 
     * Comment: http://www.w3.org/ns/auth/acl#Authorization 
     * Range: http://xmlns.com/foaf/0.1/Agent 
     */
    public static final URI agent = new URIImpl("http://www.w3.org/ns/auth/acl#agent", false);

    /**
     * Label: to all in 
     * Comment: A class of information resources to which access is being granted. 
     * Comment: http://www.w3.org/ns/auth/acl#Authorization 
     * Range: http://www.w3.org/2000/01/rdf-schema#Class 
     */
    public static final URI accessToClass = new URIImpl("http://www.w3.org/ns/auth/acl#accessToClass", false);

    /**
     * Label: to 
     * Comment: The information resource to which access is being granted. 
     * Comment: http://www.w3.org/ns/auth/acl#Authorization 
     * Range: http://www.w3.org/2006/gen/ont#InformationResource 
     */
    public static final URI accessTo = new URIImpl("http://www.w3.org/ns/auth/acl#accessTo", false);

    /**
     * Label: access control 
     * Comment: The Access Control file for this information resource.
        This may of course be a virtual resorce implemented by the access control system.
        Note also HTTP's header  Link:  foo.meta ;rel=meta can be used for this. 
     * Comment: http://www.w3.org/2006/gen/ont#InformationResource 
     * Range: http://www.w3.org/2006/gen/ont#InformationResource 
     */
    public static final URI accessControl = new URIImpl("http://www.w3.org/ns/auth/acl#accessControl", false);

}
