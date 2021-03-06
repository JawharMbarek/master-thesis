/**
 * generated by http://RDFReactor.semweb4j.org ($Id: CodeGenerator.java 1765 2010-02-11 09:51:13Z max.at.xam.de $) on 21.12.12 17:00
 */

package com.xmlns.foaf;

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.ontoware.rdf2go.exception.ModelRuntimeException;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.node.BlankNode;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.model.node.impl.URIImpl;
import org.ontoware.rdfreactor.runtime.Base;

/**
 * This class manages access to these properties:
 * <ul>
 * <li>AccountName</li>
 * <li>AccountServiceHomepage</li>
 * </ul>
 * This class was generated by <a
 * href="http://RDFReactor.semweb4j.org">RDFReactor</a> on 21.12.12 17:00
 */
public class OnlineAccount extends Thing {

    /**
     * 
     */
    private static final long serialVersionUID = -5435060116238105931L;

    /** http://xmlns.com/foaf/0.1/OnlineAccount */
    public static final URI RDFS_CLASS = new URIImpl(
            "http://xmlns.com/foaf/0.1/OnlineAccount", false);

    /** http://xmlns.com/foaf/0.1/accountName */
    public static final URI ACCOUNTNAME = new URIImpl(
            "http://xmlns.com/foaf/0.1/accountName", false);

    /** http://xmlns.com/foaf/0.1/accountServiceHomepage */
    public static final URI ACCOUNTSERVICEHOMEPAGE = new URIImpl(
            "http://xmlns.com/foaf/0.1/accountServiceHomepage", false);

    /**
     * All property-URIs with this class as domain. All properties of all
     * super-classes are also available.
     */
    public static final URI[] MANAGED_URIS = {
            new URIImpl("http://xmlns.com/foaf/0.1/accountName", false),
            new URIImpl("http://xmlns.com/foaf/0.1/accountServiceHomepage",
                    false),
    };

    // protected constructors needed for inheritance

    /**
     * Returns a Java wrapper over an RDF object, identified by URI. Creating
     * two wrappers for the same instanceURI is legal.
     * 
     * @param model RDF2GO Model implementation, see http://rdf2go.semweb4j.org
     * @param classURI URI of RDFS class
     * @param instanceIdentifier Resource that identifies this instance
     * @param write if true, the statement (this, rdf:type, TYPE) is written to
     *            the model [Generated from RDFReactor template rule #c1]
     */
    protected OnlineAccount(Model model, URI classURI,
            org.ontoware.rdf2go.model.node.Resource instanceIdentifier,
            boolean write) {
        super(model, classURI, instanceIdentifier, write);
    }

    // public constructors

    /**
     * Returns a Java wrapper over an RDF object, identified by URI. Creating
     * two wrappers for the same instanceURI is legal.
     * 
     * @param model RDF2GO Model implementation, see http://rdf2go.ontoware.org
     * @param instanceIdentifier an RDF2Go Resource identifying this instance
     * @param write if true, the statement (this, rdf:type, TYPE) is written to
     *            the model [Generated from RDFReactor template rule #c2]
     */
    public OnlineAccount(Model model,
            org.ontoware.rdf2go.model.node.Resource instanceIdentifier,
            boolean write) {
        super(model, RDFS_CLASS, instanceIdentifier, write);
    }

    /**
     * Returns a Java wrapper over an RDF object, identified by a URI, given as
     * a String. Creating two wrappers for the same URI is legal.
     * 
     * @param model RDF2GO Model implementation, see http://rdf2go.ontoware.org
     * @param uriString a URI given as a String
     * @param write if true, the statement (this, rdf:type, TYPE) is written to
     *            the model
     * @throws ModelRuntimeException if URI syntax is wrong [Generated from
     *             RDFReactor template rule #c7]
     */
    public OnlineAccount(Model model, String uriString, boolean write)
            throws ModelRuntimeException {
        super(model, RDFS_CLASS, new URIImpl(uriString, false), write);
    }

    /**
     * Returns a Java wrapper over an RDF object, identified by a blank node.
     * Creating two wrappers for the same blank node is legal.
     * 
     * @param model RDF2GO Model implementation, see http://rdf2go.ontoware.org
     * @param bnode BlankNode of this instance
     * @param write if true, the statement (this, rdf:type, TYPE) is written to
     *            the model [Generated from RDFReactor template rule #c8]
     */
    public OnlineAccount(Model model, BlankNode bnode, boolean write) {
        super(model, RDFS_CLASS, bnode, write);
    }

    /**
     * Returns a Java wrapper over an RDF object, identified by a randomly
     * generated URI. Creating two wrappers results in different URIs.
     * 
     * @param model RDF2GO Model implementation, see http://rdf2go.ontoware.org
     * @param write if true, the statement (this, rdf:type, TYPE) is written to
     *            the model [Generated from RDFReactor template rule #c9]
     */
    public OnlineAccount(Model model, boolean write) {
        super(model, RDFS_CLASS, model.newRandomUniqueURI(), write);
    }

    // /////////////////////////////////////////////////////////////////
    // typing

    /**
     * Return an existing instance of this class in the model. No statements are
     * written.
     * 
     * @param model an RDF2Go model
     * @param instanceResource an RDF2Go resource
     * @return an instance of OnlineAccount or null if none existst [Generated
     *         from RDFReactor template rule #class0]
     */
    public static OnlineAccount getInstance(Model model,
            org.ontoware.rdf2go.model.node.Resource instanceResource) {
        return Base.getInstance(model, instanceResource, OnlineAccount.class);
    }

    /**
     * Create a new instance of this class in the model. That is, create the
     * statement (instanceResource, RDF.type,
     * http://xmlns.com/foaf/0.1/OnlineAccount).
     * 
     * @param model an RDF2Go model
     * @param instanceResource an RDF2Go resource [Generated from RDFReactor
     *            template rule #class1]
     */
    public static void createInstance(Model model,
            org.ontoware.rdf2go.model.node.Resource instanceResource) {
        Base.createInstance(model, RDFS_CLASS, instanceResource);
    }

    /**
     * @param model an RDF2Go model
     * @param instanceResource an RDF2Go resource
     * @return true if instanceResource is an instance of this class in the
     *         model [Generated from RDFReactor template rule #class2]
     */
    public static boolean hasInstance(Model model,
            org.ontoware.rdf2go.model.node.Resource instanceResource) {
        return Base.hasInstance(model, RDFS_CLASS, instanceResource);
    }

    /**
     * @param model an RDF2Go model
     * @return all instances of this class in Model 'model' as RDF resources
     *         [Generated from RDFReactor template rule #class3]
     */
    public static ClosableIterator<org.ontoware.rdf2go.model.node.Resource> getAllInstances(
            Model model) {
        return Base.getAllInstances(model, RDFS_CLASS,
                org.ontoware.rdf2go.model.node.Resource.class);
    }

    /**
     * Remove rdf:type OnlineAccount from this instance. Other triples are not
     * affected. To delete more, use deleteAllProperties
     * 
     * @param model an RDF2Go model
     * @param instanceResource an RDF2Go resource [Generated from RDFReactor
     *            template rule #class4]
     */
    public static void deleteInstance(Model model,
            org.ontoware.rdf2go.model.node.Resource instanceResource) {
        Base.deleteInstance(model, RDFS_CLASS, instanceResource);
    }

    /**
     * Delete all (this, *, *), i.e. including rdf:type
     * 
     * @param model an RDF2Go model
     * @param resource
     */
    public static void deleteAllProperties(Model model,
            org.ontoware.rdf2go.model.node.Resource instanceResource) {
        Base.deleteAllProperties(model, instanceResource);
    }

    // /////////////////////////////////////////////////////////////////
    // property access methods

    /**
     * @param model an RDF2Go model
     * @param objectValue
     * @return all A's as RDF resources, that have a relation 'Account' to this
     *         OnlineAccount instance [Generated from RDFReactor template rule
     *         #getallinverse1static]
     */
    public static ClosableIterator<org.ontoware.rdf2go.model.node.Resource> getAllAccount_Inverse(
            Model model, Object objectValue) {
        return Base.getAll_Inverse(model, Agent.ACCOUNT, objectValue);
    }

    /**
     * @return all A's as RDF resources, that have a relation 'Account' to this
     *         OnlineAccount instance [Generated from RDFReactor template rule
     *         #getallinverse1dynamic]
     */
    public ClosableIterator<org.ontoware.rdf2go.model.node.Resource> getAllAccount_Inverse() {
        return Base.getAll_Inverse(this.model, Agent.ACCOUNT,
                this.getResource());
    }

    /**
     * @param model an RDF2Go model
     * @param objectValue
     * @return all A's as RDF resources, that have a relation 'HoldsAccount' to
     *         this OnlineAccount instance [Generated from RDFReactor template
     *         rule #getallinverse1static]
     */
    public static ClosableIterator<org.ontoware.rdf2go.model.node.Resource> getAllHoldsAccount_Inverse(
            Model model, Object objectValue) {
        return Base.getAll_Inverse(model, Agent.HOLDSACCOUNT, objectValue);
    }

    /**
     * @return all A's as RDF resources, that have a relation 'HoldsAccount' to
     *         this OnlineAccount instance [Generated from RDFReactor template
     *         rule #getallinverse1dynamic]
     */
    public ClosableIterator<org.ontoware.rdf2go.model.node.Resource> getAllHoldsAccount_Inverse() {
        return Base.getAll_Inverse(this.model, Agent.HOLDSACCOUNT,
                this.getResource());
    }

    /**
     * Check if org.ontoware.rdfreactor.generator.java.JProperty@22e5a644 has at
     * least one value set
     * 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
     * @return true if this property has at least one value [Generated from
     *         RDFReactor template rule #get0has-static]
     */
    public static boolean hasAccountNames(Model model,
            org.ontoware.rdf2go.model.node.Resource instanceResource) {
        return Base.has(model, instanceResource, ACCOUNTNAME);
    }

    /**
     * Check if org.ontoware.rdfreactor.generator.java.JProperty@22e5a644 has at
     * least one value set
     * 
     * @return true if this property has at least one value [Generated from
     *         RDFReactor template rule #get0has-dynamic]
     */
    public boolean hasAccountNames() {
        return Base.has(this.model, this.getResource(), ACCOUNTNAME);
    }

    /**
     * Check if org.ontoware.rdfreactor.generator.java.JProperty@22e5a644 has
     * the given value (maybe among other values).
     * 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
     * @param value the value to be checked
     * @return true if this property contains (maybe among other) the given
     *         value [Generated from RDFReactor template rule
     *         #get0has-value-static]
     */
    public static boolean hasAccountName(Model model,
            org.ontoware.rdf2go.model.node.Resource instanceResource,
            org.ontoware.rdf2go.model.node.Node value) {
        return Base.hasValue(model, instanceResource, ACCOUNTNAME, value);
    }

    /**
     * Check if org.ontoware.rdfreactor.generator.java.JProperty@22e5a644 has
     * the given value (maybe among other values).
     * 
     * @param value the value to be checked
     * @return true if this property contains (maybe among other) the given
     *         value [Generated from RDFReactor template rule
     *         #get0has-value-dynamic]
     */
    public boolean hasAccountName(org.ontoware.rdf2go.model.node.Node value) {
        return Base
                .hasValue(this.model, this.getResource(), ACCOUNTNAME, value);
    }

    /**
     * Get all values of property AccountName as an Iterator over RDF2Go nodes
     * 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
     * @return a ClosableIterator of RDF2Go Nodes [Generated from RDFReactor
     *         template rule #get7static]
     */
    public static ClosableIterator<org.ontoware.rdf2go.model.node.Node> getAllAccountNames_asNode(
            Model model,
            org.ontoware.rdf2go.model.node.Resource instanceResource) {
        return Base.getAll_asNode(model, instanceResource, ACCOUNTNAME);
    }

    /**
     * Get all values of property AccountName as an Iterator over RDF2Go nodes
     * 
     * @return a ClosableIterator of RDF2Go Nodes [Generated from RDFReactor
     *         template rule #get8dynamic]
     */
    public ClosableIterator<org.ontoware.rdf2go.model.node.Node> getAllAccountNames_asNode() {
        return Base.getAll_asNode(this.model, this.getResource(), ACCOUNTNAME);
    }

    /**
     * Get all values of property AccountName * @param model an RDF2Go model
     * 
     * @param resource an RDF2Go resource
     * @return a ClosableIterator of $type [Generated from RDFReactor template
     *         rule #get11static]
     */
    public static ClosableIterator<java.lang.String> getAllAccountName(
            Model model,
            org.ontoware.rdf2go.model.node.Resource instanceResource) {
        return Base.getAll(model, instanceResource, ACCOUNTNAME,
                java.lang.String.class);
    }

    /**
     * Get all values of property AccountName * @return a ClosableIterator of
     * $type [Generated from RDFReactor template rule #get12dynamic]
     */
    public ClosableIterator<java.lang.String> getAllAccountNamess() {
        return Base.getAll(this.model, this.getResource(), ACCOUNTNAME,
                java.lang.String.class);
    }

    /**
     * Get all values of property AccountName * @return a ClosableIterator of
     * $type [Generated from RDFReactor template rule #get12dynamic]
     */
    public java.lang.String getAccountName() {
        return Base.getAll_as(this.model, this.getResource(), ACCOUNTNAME,
                java.lang.String.class).firstValue();
    }

    /**
     * Adds a value to property AccountName as an RDF2Go node
     * 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
     * @param value the value to be added [Generated from RDFReactor template
     *            rule #add1static]
     */
    public static void addAccountName(Model model,
            org.ontoware.rdf2go.model.node.Resource instanceResource,
            org.ontoware.rdf2go.model.node.Node value) {
        Base.add(model, instanceResource, ACCOUNTNAME, value);
    }

    /**
     * Adds a value to property AccountName as an RDF2Go node
     * 
     * @param value the value to be added [Generated from RDFReactor template
     *            rule #add1dynamic]
     */
    public void addAccountName(org.ontoware.rdf2go.model.node.Node value) {
        Base.add(this.model, this.getResource(), ACCOUNTNAME, value);
    }

    /**
     * Adds a value to property AccountName from an instance of java.lang.String
     * 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource [Generated from RDFReactor template
     *            rule #add3static]
     */
    public static void addAccountName(Model model,
            org.ontoware.rdf2go.model.node.Resource instanceResource,
            java.lang.String value) {
        Base.add(model, instanceResource, ACCOUNTNAME, value);
    }

    /**
     * Adds a value to property AccountName from an instance of java.lang.String
     * [Generated from RDFReactor template rule #add4dynamic]
     */
    public void addAccountName(java.lang.String value) {
        Base.add(this.model, this.getResource(), ACCOUNTNAME, value);
    }

    /**
     * Sets a value of property AccountName from an RDF2Go node. First, all
     * existing values are removed, then this value is added. Cardinality
     * constraints are not checked, but this method exists only for properties
     * with no minCardinality or minCardinality == 1.
     * 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
     * @param value the value to be set [Generated from RDFReactor template rule
     *            #set1static]
     */
    public static void setAccountName(Model model,
            org.ontoware.rdf2go.model.node.Resource instanceResource,
            org.ontoware.rdf2go.model.node.Node value) {
        Base.set(model, instanceResource, ACCOUNTNAME, value);
    }

    /**
     * Sets a value of property AccountName from an RDF2Go node. First, all
     * existing values are removed, then this value is added. Cardinality
     * constraints are not checked, but this method exists only for properties
     * with no minCardinality or minCardinality == 1.
     * 
     * @param value the value to be added [Generated from RDFReactor template
     *            rule #set1dynamic]
     */
    public void setAccountName(org.ontoware.rdf2go.model.node.Node value) {
        Base.set(this.model, this.getResource(), ACCOUNTNAME, value);
    }

    /**
     * Sets a value of property AccountName from an instance of java.lang.String
     * First, all existing values are removed, then this value is added.
     * Cardinality constraints are not checked, but this method exists only for
     * properties with no minCardinality or minCardinality == 1.
     * 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
     * @param value the value to be added [Generated from RDFReactor template
     *            rule #set3static]
     */
    public static void setAccountName(Model model,
            org.ontoware.rdf2go.model.node.Resource instanceResource,
            java.lang.String value) {
        Base.set(model, instanceResource, ACCOUNTNAME, value);
    }

    /**
     * Sets a value of property AccountName from an instance of java.lang.String
     * First, all existing values are removed, then this value is added.
     * Cardinality constraints are not checked, but this method exists only for
     * properties with no minCardinality or minCardinality == 1.
     * 
     * @param value the value to be added [Generated from RDFReactor template
     *            rule #set4dynamic]
     */
    public void setAccountName(java.lang.String value) {
        Base.set(this.model, this.getResource(), ACCOUNTNAME, value);
    }

    /**
     * Removes a value of property AccountName as an RDF2Go node
     * 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
     * @param value the value to be removed [Generated from RDFReactor template
     *            rule #remove1static]
     */
    public static void removeAccountName(Model model,
            org.ontoware.rdf2go.model.node.Resource instanceResource,
            org.ontoware.rdf2go.model.node.Node value) {
        Base.remove(model, instanceResource, ACCOUNTNAME, value);
    }

    /**
     * Removes a value of property AccountName as an RDF2Go node
     * 
     * @param value the value to be removed [Generated from RDFReactor template
     *            rule #remove1dynamic]
     */
    public void removeAccountName(org.ontoware.rdf2go.model.node.Node value) {
        Base.remove(this.model, this.getResource(), ACCOUNTNAME, value);
    }

    /**
     * Removes a value of property AccountName given as an instance of
     * java.lang.String
     * 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
     * @param value the value to be removed [Generated from RDFReactor template
     *            rule #remove3static]
     */
    public static void removeAccountName(Model model,
            org.ontoware.rdf2go.model.node.Resource instanceResource,
            java.lang.String value) {
        Base.remove(model, instanceResource, ACCOUNTNAME, value);
    }

    /**
     * Removes a value of property AccountName given as an instance of
     * java.lang.String
     * 
     * @param value the value to be removed [Generated from RDFReactor template
     *            rule #remove4dynamic]
     */
    public void removeAccountName(java.lang.String value) {
        Base.remove(this.model, this.getResource(), ACCOUNTNAME, value);
    }

    /**
     * Removes all values of property AccountName * @param model an RDF2Go model
     * 
     * @param resource an RDF2Go resource [Generated from RDFReactor template
     *            rule #removeall1static]
     */
    public static void removeAllAccountNames(Model model,
            org.ontoware.rdf2go.model.node.Resource instanceResource) {
        Base.removeAll(model, instanceResource, ACCOUNTNAME);
    }

    /**
     * Removes all values of property AccountName * [Generated from RDFReactor
     * template rule #removeall1dynamic]
     */
    public void removeAllAccountNames() {
        Base.removeAll(this.model, this.getResource(), ACCOUNTNAME);
    }

    /**
     * Check if org.ontoware.rdfreactor.generator.java.JProperty@12082570 has at
     * least one value set
     * 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
     * @return true if this property has at least one value [Generated from
     *         RDFReactor template rule #get0has-static]
     */
    public static boolean hasAccountServiceHomepage(Model model,
            org.ontoware.rdf2go.model.node.Resource instanceResource) {
        return Base.has(model, instanceResource, ACCOUNTSERVICEHOMEPAGE);
    }

    /**
     * Check if org.ontoware.rdfreactor.generator.java.JProperty@12082570 has at
     * least one value set
     * 
     * @return true if this property has at least one value [Generated from
     *         RDFReactor template rule #get0has-dynamic]
     */
    public boolean hasAccountServiceHomepages() {
        return Base.has(this.model, this.getResource(), ACCOUNTSERVICEHOMEPAGE);
    }

    /**
     * Check if org.ontoware.rdfreactor.generator.java.JProperty@12082570 has
     * the given value (maybe among other values).
     * 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
     * @param value the value to be checked
     * @return true if this property contains (maybe among other) the given
     *         value [Generated from RDFReactor template rule
     *         #get0has-value-static]
     */
    public static boolean hasAccountServiceHomepages(Model model,
            org.ontoware.rdf2go.model.node.Resource instanceResource,
            org.ontoware.rdf2go.model.node.Node value) {
        return Base.hasValue(model, instanceResource, ACCOUNTSERVICEHOMEPAGE,
                value);
    }

    /**
     * Check if org.ontoware.rdfreactor.generator.java.JProperty@12082570 has
     * the given value (maybe among other values).
     * 
     * @param value the value to be checked
     * @return true if this property contains (maybe among other) the given
     *         value [Generated from RDFReactor template rule
     *         #get0has-value-dynamic]
     */
    public boolean hasAccountServiceHomepage(
            org.ontoware.rdf2go.model.node.Node value) {
        return Base.hasValue(this.model, this.getResource(),
                ACCOUNTSERVICEHOMEPAGE, value);
    }

    /**
     * Get all values of property AccountServiceHomepage as an Iterator over
     * RDF2Go nodes
     * 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
     * @return a ClosableIterator of RDF2Go Nodes [Generated from RDFReactor
     *         template rule #get7static]
     */
    public static ClosableIterator<org.ontoware.rdf2go.model.node.Node> getAllAccountServiceHomepages_asNode(
            Model model,
            org.ontoware.rdf2go.model.node.Resource instanceResource) {
        return Base.getAll_asNode(model, instanceResource,
                ACCOUNTSERVICEHOMEPAGE);
    }

    /**
     * Get all values of property AccountServiceHomepage as an Iterator over
     * RDF2Go nodes
     * 
     * @return a ClosableIterator of RDF2Go Nodes [Generated from RDFReactor
     *         template rule #get8dynamic]
     */
    public ClosableIterator<org.ontoware.rdf2go.model.node.Node> getAllAccountServiceHomepages_asNode() {
        return Base.getAll_asNode(this.model, this.getResource(),
                ACCOUNTSERVICEHOMEPAGE);
    }

    /**
     * Get all values of property AccountServiceHomepage * @param model an
     * RDF2Go model
     * 
     * @param resource an RDF2Go resource
     * @return a ClosableIterator of $type [Generated from RDFReactor template
     *         rule #get11static]
     */
    public static ClosableIterator<Document> getAllAccountServiceHomepages(
            Model model,
            org.ontoware.rdf2go.model.node.Resource instanceResource) {
        return Base.getAll(model, instanceResource, ACCOUNTSERVICEHOMEPAGE,
                Document.class);
    }

    /**
     * Get all values of property AccountServiceHomepage * @return a
     * ClosableIterator of $type [Generated from RDFReactor template rule
     * #get12dynamic]
     */
    public ClosableIterator<Document> getAllAccountServiceHomepages() {
        return Base.getAll(this.model, this.getResource(),
                ACCOUNTSERVICEHOMEPAGE, Document.class);
    }

    /**
     * Get all values of property AccountServiceHomepage * @return a
     * ClosableIterator of $type [Generated from RDFReactor template rule
     * #get12dynamic]
     */
    public Document getAccountServiceHomepage() {
        return Base.getAll_as(this.model, this.getResource(),
                ACCOUNTSERVICEHOMEPAGE, Document.class).firstValue();
    }

    /**
     * Adds a value to property AccountServiceHomepage as an RDF2Go node
     * 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
     * @param value the value to be added [Generated from RDFReactor template
     *            rule #add1static]
     */
    public static void addAccountServiceHomepage(Model model,
            org.ontoware.rdf2go.model.node.Resource instanceResource,
            org.ontoware.rdf2go.model.node.Node value) {
        Base.add(model, instanceResource, ACCOUNTSERVICEHOMEPAGE, value);
    }

    /**
     * Adds a value to property AccountServiceHomepage as an RDF2Go node
     * 
     * @param value the value to be added [Generated from RDFReactor template
     *            rule #add1dynamic]
     */
    public void addAccountServiceHomepage(
            org.ontoware.rdf2go.model.node.Node value) {
        Base.add(this.model, this.getResource(), ACCOUNTSERVICEHOMEPAGE, value);
    }

    /**
     * Adds a value to property AccountServiceHomepage from an instance of
     * Document
     * 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource [Generated from RDFReactor template
     *            rule #add3static]
     */
    public static void addAccountServiceHomepage(Model model,
            org.ontoware.rdf2go.model.node.Resource instanceResource,
            Document value) {
        Base.add(model, instanceResource, ACCOUNTSERVICEHOMEPAGE, value);
    }

    /**
     * Adds a value to property AccountServiceHomepage from an instance of
     * Document [Generated from RDFReactor template rule #add4dynamic]
     */
    public void addAccountServiceHomepage(Document value) {
        Base.add(this.model, this.getResource(), ACCOUNTSERVICEHOMEPAGE, value);
    }

    /**
     * Sets a value of property AccountServiceHomepage from an RDF2Go node.
     * First, all existing values are removed, then this value is added.
     * Cardinality constraints are not checked, but this method exists only for
     * properties with no minCardinality or minCardinality == 1.
     * 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
     * @param value the value to be set [Generated from RDFReactor template rule
     *            #set1static]
     */
    public static void setAccountServiceHomepage(Model model,
            org.ontoware.rdf2go.model.node.Resource instanceResource,
            org.ontoware.rdf2go.model.node.Node value) {
        Base.set(model, instanceResource, ACCOUNTSERVICEHOMEPAGE, value);
    }

    /**
     * Sets a value of property AccountServiceHomepage from an RDF2Go node.
     * First, all existing values are removed, then this value is added.
     * Cardinality constraints are not checked, but this method exists only for
     * properties with no minCardinality or minCardinality == 1.
     * 
     * @param value the value to be added [Generated from RDFReactor template
     *            rule #set1dynamic]
     */
    public void setAccountServiceHomepage(
            org.ontoware.rdf2go.model.node.Node value) {
        Base.set(this.model, this.getResource(), ACCOUNTSERVICEHOMEPAGE, value);
    }

    /**
     * Sets a value of property AccountServiceHomepage from an instance of
     * Document First, all existing values are removed, then this value is
     * added. Cardinality constraints are not checked, but this method exists
     * only for properties with no minCardinality or minCardinality == 1.
     * 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
     * @param value the value to be added [Generated from RDFReactor template
     *            rule #set3static]
     */
    public static void setAccountServiceHomepage(Model model,
            org.ontoware.rdf2go.model.node.Resource instanceResource,
            Document value) {
        Base.set(model, instanceResource, ACCOUNTSERVICEHOMEPAGE, value);
    }

    /**
     * Sets a value of property AccountServiceHomepage from an instance of
     * Document First, all existing values are removed, then this value is
     * added. Cardinality constraints are not checked, but this method exists
     * only for properties with no minCardinality or minCardinality == 1.
     * 
     * @param value the value to be added [Generated from RDFReactor template
     *            rule #set4dynamic]
     */
    public void setAccountServiceHomepage(Document value) {
        Base.set(this.model, this.getResource(), ACCOUNTSERVICEHOMEPAGE, value);
    }

    /**
     * Removes a value of property AccountServiceHomepage as an RDF2Go node
     * 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
     * @param value the value to be removed [Generated from RDFReactor template
     *            rule #remove1static]
     */
    public static void removeAccountServiceHomepage(Model model,
            org.ontoware.rdf2go.model.node.Resource instanceResource,
            org.ontoware.rdf2go.model.node.Node value) {
        Base.remove(model, instanceResource, ACCOUNTSERVICEHOMEPAGE, value);
    }

    /**
     * Removes a value of property AccountServiceHomepage as an RDF2Go node
     * 
     * @param value the value to be removed [Generated from RDFReactor template
     *            rule #remove1dynamic]
     */
    public void removeAccountServiceHomepage(
            org.ontoware.rdf2go.model.node.Node value) {
        Base.remove(this.model, this.getResource(), ACCOUNTSERVICEHOMEPAGE,
                value);
    }

    /**
     * Removes a value of property AccountServiceHomepage given as an instance
     * of Document
     * 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
     * @param value the value to be removed [Generated from RDFReactor template
     *            rule #remove3static]
     */
    public static void removeAccountServiceHomepage(Model model,
            org.ontoware.rdf2go.model.node.Resource instanceResource,
            Document value) {
        Base.remove(model, instanceResource, ACCOUNTSERVICEHOMEPAGE, value);
    }

    /**
     * Removes a value of property AccountServiceHomepage given as an instance
     * of Document
     * 
     * @param value the value to be removed [Generated from RDFReactor template
     *            rule #remove4dynamic]
     */
    public void removeAccountServiceHomepage(Document value) {
        Base.remove(this.model, this.getResource(), ACCOUNTSERVICEHOMEPAGE,
                value);
    }

    /**
     * Removes all values of property AccountServiceHomepage * @param model an
     * RDF2Go model
     * 
     * @param resource an RDF2Go resource [Generated from RDFReactor template
     *            rule #removeall1static]
     */
    public static void removeAllAccountServiceHomepages(Model model,
            org.ontoware.rdf2go.model.node.Resource instanceResource) {
        Base.removeAll(model, instanceResource, ACCOUNTSERVICEHOMEPAGE);
    }

    /**
     * Removes all values of property AccountServiceHomepage * [Generated from
     * RDFReactor template rule #removeall1dynamic]
     */
    public void removeAllAccountServiceHomepages() {
        Base.removeAll(this.model, this.getResource(), ACCOUNTSERVICEHOMEPAGE);
    }
}
