/**
 * generated by http://RDFReactor.semweb4j.org ($Id: CodeGenerator.java 1765 2010-02-11 09:51:13Z max.at.xam.de $) on 5/28/13 8:39 PM
 */
package org.w3.ns.auth.acl;

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.ontoware.rdf2go.exception.ModelRuntimeException;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.node.BlankNode;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.model.node.impl.URIImpl;
import org.ontoware.rdfreactor.runtime.Base;
import org.ontoware.rdfreactor.runtime.ReactorResult;

import com.xmlns.foaf.Agent;

/**
 * This class manages access to these properties:
 * <ul>
 * <li>Owner</li>
 * </ul>
 * 
 * This class was generated by <a
 * href="http://RDFReactor.semweb4j.org">RDFReactor</a> on 5/28/13 8:39 PM
 */
public class ACLThing extends org.ontoware.rdfreactor.schema.rdfs.Class {
    private static final long serialVersionUID = -1661690437496076301L;

    /** http://www.w3.org/2000/01/rdf-schema#Class */
    public static final URI RDFS_CLASS = new URIImpl(
	    "http://www.w3.org/2000/01/rdf-schema#Class", false);

    /** http://www.w3.org/ns/auth/acl#owner */
    public static final URI OWNER = new URIImpl(
	    "http://www.w3.org/ns/auth/acl#owner", false);

    /**
     * All property-URIs with this class as domain. All properties of all
     * super-classes are also available.
     */
    public static final URI[] MANAGED_URIS = {
	    new URIImpl("http://www.w3.org/ns/auth/acl#owner", false)
    };

    // protected constructors needed for inheritance

    /**
     * Returns a Java wrapper over an RDF object, identified by URI. Creating
     * two wrappers for the same instanceURI is legal.
     * 
     * @param model
     *            RDF2GO Model implementation, see http://rdf2go.semweb4j.org
     * @param classURI
     *            URI of RDFS class
     * @param instanceIdentifier
     *            Resource that identifies this instance
     * @param write
     *            if true, the statement (this, rdf:type, TYPE) is written to
     *            the model
     * 
     *            [Generated from RDFReactor template rule #c1]
     */
    protected ACLThing(Model model, URI classURI,
	    org.ontoware.rdf2go.model.node.Resource instanceIdentifier,
	    boolean write) {
	super(model, classURI, instanceIdentifier, write);
    }

    // public constructors

    /**
     * Returns a Java wrapper over an RDF object, identified by URI. Creating
     * two wrappers for the same instanceURI is legal.
     * 
     * @param model
     *            RDF2GO Model implementation, see http://rdf2go.ontoware.org
     * @param instanceIdentifier
     *            an RDF2Go Resource identifying this instance
     * @param write
     *            if true, the statement (this, rdf:type, TYPE) is written to
     *            the model
     * 
     *            [Generated from RDFReactor template rule #c2]
     */
    public ACLThing(Model model,
	    org.ontoware.rdf2go.model.node.Resource instanceIdentifier,
	    boolean write) {
	super(model, RDFS_CLASS, instanceIdentifier, write);
    }

    /**
     * Returns a Java wrapper over an RDF object, identified by a URI, given as
     * a String. Creating two wrappers for the same URI is legal.
     * 
     * @param model
     *            RDF2GO Model implementation, see http://rdf2go.ontoware.org
     * @param uriString
     *            a URI given as a String
     * @param write
     *            if true, the statement (this, rdf:type, TYPE) is written to
     *            the model
     * @throws ModelRuntimeException
     *             if URI syntax is wrong
     * 
     *             [Generated from RDFReactor template rule #c7]
     */
    public ACLThing(Model model, String uriString, boolean write)
	    throws ModelRuntimeException {
	super(model, RDFS_CLASS, new URIImpl(uriString, false), write);
    }

    /**
     * Returns a Java wrapper over an RDF object, identified by a blank node.
     * Creating two wrappers for the same blank node is legal.
     * 
     * @param model
     *            RDF2GO Model implementation, see http://rdf2go.ontoware.org
     * @param bnode
     *            BlankNode of this instance
     * @param write
     *            if true, the statement (this, rdf:type, TYPE) is written to
     *            the model
     * 
     *            [Generated from RDFReactor template rule #c8]
     */
    public ACLThing(Model model, BlankNode bnode, boolean write) {
	super(model, RDFS_CLASS, bnode, write);
    }

    /**
     * Returns a Java wrapper over an RDF object, identified by a randomly
     * generated URI. Creating two wrappers results in different URIs.
     * 
     * @param model
     *            RDF2GO Model implementation, see http://rdf2go.ontoware.org
     * @param write
     *            if true, the statement (this, rdf:type, TYPE) is written to
     *            the model
     * 
     *            [Generated from RDFReactor template rule #c9]
     */
    public ACLThing(Model model, boolean write) {
	super(model, RDFS_CLASS, model.newRandomUniqueURI(), write);
    }

    // /////////////////////////////////////////////////////////////////
    // typing

    /**
     * Return an existing instance of this class in the model. No statements are
     * written.
     * 
     * @param model
     *            an RDF2Go model
     * @param instanceResource
     *            an RDF2Go resource
     * @return an instance of Thing or null if none existst
     * 
     *         [Generated from RDFReactor template rule #class0]
     */
    public static ACLThing getInstance(Model model,
	    org.ontoware.rdf2go.model.node.Resource instanceResource) {
	return Base.getInstance(model, instanceResource, ACLThing.class);
    }

    /**
     * Create a new instance of this class in the model. That is, create the
     * statement (instanceResource, RDF.type,
     * http://www.w3.org/2000/01/rdf-schema#Class).
     * 
     * @param model
     *            an RDF2Go model
     * @param instanceResource
     *            an RDF2Go resource
     * 
     *            [Generated from RDFReactor template rule #class1]
     */
    public static void createInstance(Model model,
	    org.ontoware.rdf2go.model.node.Resource instanceResource) {
	Base.createInstance(model, RDFS_CLASS, instanceResource);
    }

    /**
     * @param model
     *            an RDF2Go model
     * @param instanceResource
     *            an RDF2Go resource
     * @return true if instanceResource is an instance of this class in the
     *         model
     * 
     *         [Generated from RDFReactor template rule #class2]
     */
    public static boolean hasInstance(Model model,
	    org.ontoware.rdf2go.model.node.Resource instanceResource) {
	return Base.hasInstance(model, RDFS_CLASS, instanceResource);
    }

    /**
     * @param model
     *            an RDF2Go model
     * @return all instances of this class in Model 'model' as RDF resources
     * 
     *         [Generated from RDFReactor template rule #class3]
     */
    public static ClosableIterator<org.ontoware.rdf2go.model.node.Resource> getAllInstances(
	    Model model) {
	return Base.getAllInstances(model, RDFS_CLASS,
		org.ontoware.rdf2go.model.node.Resource.class);
    }

    /**
     * @param model
     *            an RDF2Go model
     * @return all instances of this class in Model 'model' as a ReactorResult,
     *         which can conveniently be converted to iterator, list or array.
     * 
     *         [Generated from RDFReactor template rule #class3-as]
     */
    public static ReactorResult<? extends ACLThing> getAllInstances_as(Model model) {
	return Base.getAllInstances_as(model, RDFS_CLASS, ACLThing.class);
    }

    /**
     * Remove rdf:type Thing from this instance. Other triples are not affected.
     * To delete more, use deleteAllProperties
     * 
     * @param model
     *            an RDF2Go model
     * @param instanceResource
     *            an RDF2Go resource
     * 
     *            [Generated from RDFReactor template rule #class4]
     */
    public static void deleteInstance(Model model,
	    org.ontoware.rdf2go.model.node.Resource instanceResource) {
	Base.deleteInstance(model, RDFS_CLASS, instanceResource);
    }

    /**
     * Delete all (this, *, *), i.e. including rdf:type
     * 
     * @param model
     *            an RDF2Go model
     * @param resource
     */
    public static void deleteAllProperties(Model model,
	    org.ontoware.rdf2go.model.node.Resource instanceResource) {
	Base.deleteAllProperties(model, instanceResource);
    }

    /**
     * Check if org.ontoware.rdfreactor.generator.java.JProperty@492e5810 has at
     * least one value set
     * 
     * @param model
     *            an RDF2Go model
     * @param resource
     *            an RDF2Go resource
     * @return true if this property has at least one value
     * 
     *         [Generated from RDFReactor template rule #get0has-static]
     */
    public static boolean hasOwner(Model model,
	    org.ontoware.rdf2go.model.node.Resource instanceResource) {
	return Base.has(model, instanceResource, OWNER);
    }

    /**
     * Check if org.ontoware.rdfreactor.generator.java.JProperty@492e5810 has at
     * least one value set
     * 
     * @return true if this property has at least one value
     * 
     *         [Generated from RDFReactor template rule #get0has-dynamic]
     */
    public boolean hasOwner() {
	return Base.has(this.model, this.getResource(), OWNER);
    }

    /**
     * Check if org.ontoware.rdfreactor.generator.java.JProperty@492e5810 has
     * the given value (maybe among other values).
     * 
     * @param model
     *            an RDF2Go model
     * @param resource
     *            an RDF2Go resource
     * @param value
     *            the value to be checked
     * @return true if this property contains (maybe among other) the given
     *         value
     * 
     *         [Generated from RDFReactor template rule #get0has-value-static]
     */
    public static boolean hasOwner(Model model,
	    org.ontoware.rdf2go.model.node.Resource instanceResource,
	    org.ontoware.rdf2go.model.node.Node value) {
	return Base.hasValue(model, instanceResource, OWNER, value);
    }

    /**
     * Check if org.ontoware.rdfreactor.generator.java.JProperty@492e5810 has
     * the given value (maybe among other values).
     * 
     * @param value
     *            the value to be checked
     * @return true if this property contains (maybe among other) the given
     *         value
     * 
     *         [Generated from RDFReactor template rule #get0has-value-dynamic]
     */
    public boolean hasOwner(org.ontoware.rdf2go.model.node.Node value) {
	return Base.hasValue(this.model, this.getResource(), OWNER, value);
    }

    /**
     * Get all values of property Owner as an Iterator over RDF2Go nodes
     * 
     * @param model
     *            an RDF2Go model
     * @param resource
     *            an RDF2Go resource
     * @return a ClosableIterator of RDF2Go Nodes
     * 
     *         [Generated from RDFReactor template rule #get7static]
     */
    public static ClosableIterator<org.ontoware.rdf2go.model.node.Node> getAllOwner_asNode(
	    Model model,
	    org.ontoware.rdf2go.model.node.Resource instanceResource) {
	return Base.getAll_asNode(model, instanceResource, OWNER);
    }

    /**
     * Get all values of property Owner as an Iterator over RDF2Go nodes
     * 
     * @return a ClosableIterator of RDF2Go Nodes
     * 
     *         [Generated from RDFReactor template rule #get8dynamic]
     */
    public ClosableIterator<org.ontoware.rdf2go.model.node.Node> getAllOwner_asNode() {
	return Base.getAll_asNode(this.model, this.getResource(), OWNER);
    }

    /**
     * Get all values of property Owner * @param model an RDF2Go model
     * 
     * @param resource
     *            an RDF2Go resource
     * @return a ClosableIterator of $type
     * 
     *         [Generated from RDFReactor template rule #get11static]
     */
    public static ClosableIterator<Agent> getAllOwner(Model model,
	    org.ontoware.rdf2go.model.node.Resource instanceResource) {
	return Base.getAll(model, instanceResource, OWNER, Agent.class);
    }

    /**
     * Get all values of property Owner * @return a ClosableIterator of $type
     * 
     * [Generated from RDFReactor template rule #get12dynamic]
     */
    public ClosableIterator<Agent> getAllOwner() {
	return Base.getAll(this.model, this.getResource(), OWNER, Agent.class);
    }

    /**
     * Get all values of property Owner * @return a ClosableIterator of $type
     * 
     * [Generated from RDFReactor template rule #get12dynamic]
     */
    public Agent getOwner() {
	return Base.getAll_as(this.model, this.getResource(), OWNER,
		Agent.class).firstValue();
    }

    /**
     * Adds a value to property Owner as an RDF2Go node
     * 
     * @param model
     *            an RDF2Go model
     * @param resource
     *            an RDF2Go resource
     * @param value
     *            the value to be added
     * 
     *            [Generated from RDFReactor template rule #add1static]
     */
    public static void addOwner(Model model,
	    org.ontoware.rdf2go.model.node.Resource instanceResource,
	    org.ontoware.rdf2go.model.node.Node value) {
	Base.add(model, instanceResource, OWNER, value);
    }

    /**
     * Adds a value to property Owner as an RDF2Go node
     * 
     * @param value
     *            the value to be added
     * 
     *            [Generated from RDFReactor template rule #add1dynamic]
     */
    public void addOwner(org.ontoware.rdf2go.model.node.Node value) {
	Base.add(this.model, this.getResource(), OWNER, value);
    }

    /**
     * Adds a value to property Owner from an instance of Agent
     * 
     * @param model
     *            an RDF2Go model
     * @param resource
     *            an RDF2Go resource
     * 
     *            [Generated from RDFReactor template rule #add3static]
     */
    public static void addOwner(Model model,
	    org.ontoware.rdf2go.model.node.Resource instanceResource,
	    Agent value) {
	Base.add(model, instanceResource, OWNER, value);
    }

    /**
     * Adds a value to property Owner from an instance of Agent
     * 
     * [Generated from RDFReactor template rule #add4dynamic]
     */
    public void addOwner(Agent value) {
	Base.add(this.model, this.getResource(), OWNER, value);
    }

    /**
     * Sets a value of property Owner from an RDF2Go node. First, all existing
     * values are removed, then this value is added. Cardinality constraints are
     * not checked, but this method exists only for properties with no
     * minCardinality or minCardinality == 1.
     * 
     * @param model
     *            an RDF2Go model
     * @param resource
     *            an RDF2Go resource
     * @param value
     *            the value to be set
     * 
     *            [Generated from RDFReactor template rule #set1static]
     */
    public static void setOwner(Model model,
	    org.ontoware.rdf2go.model.node.Resource instanceResource,
	    org.ontoware.rdf2go.model.node.Node value) {
	Base.set(model, instanceResource, OWNER, value);
    }

    /**
     * Sets a value of property Owner from an RDF2Go node. First, all existing
     * values are removed, then this value is added. Cardinality constraints are
     * not checked, but this method exists only for properties with no
     * minCardinality or minCardinality == 1.
     * 
     * @param value
     *            the value to be added
     * 
     *            [Generated from RDFReactor template rule #set1dynamic]
     */
    public void setOwner(org.ontoware.rdf2go.model.node.Node value) {
	Base.set(this.model, this.getResource(), OWNER, value);
    }

    /**
     * Sets a value of property Owner from an instance of Agent First, all
     * existing values are removed, then this value is added. Cardinality
     * constraints are not checked, but this method exists only for properties
     * with no minCardinality or minCardinality == 1.
     * 
     * @param model
     *            an RDF2Go model
     * @param resource
     *            an RDF2Go resource
     * @param value
     *            the value to be added
     * 
     *            [Generated from RDFReactor template rule #set3static]
     */
    public static void setOwner(Model model,
	    org.ontoware.rdf2go.model.node.Resource instanceResource,
	    Agent value) {
	Base.set(model, instanceResource, OWNER, value);
    }

    /**
     * Sets a value of property Owner from an instance of Agent First, all
     * existing values are removed, then this value is added. Cardinality
     * constraints are not checked, but this method exists only for properties
     * with no minCardinality or minCardinality == 1.
     * 
     * @param value
     *            the value to be added
     * 
     *            [Generated from RDFReactor template rule #set4dynamic]
     */
    public void setOwner(Agent value) {
	Base.set(this.model, this.getResource(), OWNER, value);
    }

    /**
     * Removes a value of property Owner as an RDF2Go node
     * 
     * @param model
     *            an RDF2Go model
     * @param resource
     *            an RDF2Go resource
     * @param value
     *            the value to be removed
     * 
     *            [Generated from RDFReactor template rule #remove1static]
     */
    public static void removeOwner(Model model,
	    org.ontoware.rdf2go.model.node.Resource instanceResource,
	    org.ontoware.rdf2go.model.node.Node value) {
	Base.remove(model, instanceResource, OWNER, value);
    }

    /**
     * Removes a value of property Owner as an RDF2Go node
     * 
     * @param value
     *            the value to be removed
     * 
     *            [Generated from RDFReactor template rule #remove1dynamic]
     */
    public void removeOwner(org.ontoware.rdf2go.model.node.Node value) {
	Base.remove(this.model, this.getResource(), OWNER, value);
    }

    /**
     * Removes a value of property Owner given as an instance of Agent
     * 
     * @param model
     *            an RDF2Go model
     * @param resource
     *            an RDF2Go resource
     * @param value
     *            the value to be removed
     * 
     *            [Generated from RDFReactor template rule #remove3static]
     */
    public static void removeOwner(Model model,
	    org.ontoware.rdf2go.model.node.Resource instanceResource,
	    Agent value) {
	Base.remove(model, instanceResource, OWNER, value);
    }

    /**
     * Removes a value of property Owner given as an instance of Agent
     * 
     * @param value
     *            the value to be removed
     * 
     *            [Generated from RDFReactor template rule #remove4dynamic]
     */
    public void removeOwner(Agent value) {
	Base.remove(this.model, this.getResource(), OWNER, value);
    }

    /**
     * Removes all values of property Owner * @param model an RDF2Go model
     * 
     * @param resource
     *            an RDF2Go resource
     * 
     *            [Generated from RDFReactor template rule #removeall1static]
     */
    public static void removeAllOwner(Model model,
	    org.ontoware.rdf2go.model.node.Resource instanceResource) {
	Base.removeAll(model, instanceResource, OWNER);
    }

    /**
     * Removes all values of property Owner * [Generated from RDFReactor
     * template rule #removeall1dynamic]
     */
    public void removeAllOwner() {
	Base.removeAll(this.model, this.getResource(), OWNER);
    }
}