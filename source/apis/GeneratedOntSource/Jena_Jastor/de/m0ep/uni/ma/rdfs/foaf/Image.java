

package de.m0ep.uni.ma.rdfs.foaf;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for Image ontology class<br>
 * Use the de.m0ep.uni.ma.rdfs.foaf.Factory to create instances of this interface.
 * <p>(URI: http://xmlns.com/foaf/0.1/Image)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	isDefinedBy : http://xmlns.com/foaf/0.1/ <br>
 * 	label : Image <br>
 * 	comment : An image. <br>
 * <br>
 * <br>
 */
public interface Image extends de.m0ep.uni.ma.rdfs.foaf.Document, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://xmlns.com/foaf/0.1/Image");
	

	/**
	 * The Jena Property for depicts 
	 * <p>(URI: http://xmlns.com/foaf/0.1/depicts)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	isDefinedBy : http://xmlns.com/foaf/0.1/ <br>
	 * 	label : depicts <br>
	 * 	comment : A thing depicted in this representation. <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property depictsProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/depicts");


	/**
	 * The Jena Property for thumbnail 
	 * <p>(URI: http://xmlns.com/foaf/0.1/thumbnail)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	isDefinedBy : http://xmlns.com/foaf/0.1/ <br>
	 * 	label : thumbnail <br>
	 * 	comment : A derived thumbnail image. <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property thumbnailProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/thumbnail");


	/**
	 * The Jena Property for theme 
	 * <p>(URI: http://xmlns.com/foaf/0.1/theme)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	isDefinedBy : http://xmlns.com/foaf/0.1/ <br>
	 * 	label : theme <br>
	 * 	comment : A theme. <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property themeProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/theme");


	/**
	 * The Jena Property for maker 
	 * <p>(URI: http://xmlns.com/foaf/0.1/maker)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	isDefinedBy : http://xmlns.com/foaf/0.1/ <br>
	 * 	label : maker <br>
	 * 	comment : An agent that  made this thing. <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property makerProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/maker");


	/**
	 * The Jena Property for fundedBy 
	 * <p>(URI: http://xmlns.com/foaf/0.1/fundedBy)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	isDefinedBy : http://xmlns.com/foaf/0.1/ <br>
	 * 	label : funded by <br>
	 * 	comment : An organization funding a project or person. <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property fundedByProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/fundedBy");


	/**
	 * The Jena Property for homepage 
	 * <p>(URI: http://xmlns.com/foaf/0.1/homepage)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	isDefinedBy : http://xmlns.com/foaf/0.1/ <br>
	 * 	label : homepage <br>
	 * 	comment : A homepage for some thing. <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property homepageProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/homepage");


	/**
	 * The Jena Property for name 
	 * <p>(URI: http://xmlns.com/foaf/0.1/name)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	isDefinedBy : http://xmlns.com/foaf/0.1/ <br>
	 * 	label : name <br>
	 * 	comment : A name for some thing. <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property nameProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/name");


	/**
	 * The Jena Property for logo 
	 * <p>(URI: http://xmlns.com/foaf/0.1/logo)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	isDefinedBy : http://xmlns.com/foaf/0.1/ <br>
	 * 	label : logo <br>
	 * 	comment : A logo representing some thing. <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property logoProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/logo");


	/**
	 * The Jena Property for page 
	 * <p>(URI: http://xmlns.com/foaf/0.1/page)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	isDefinedBy : http://xmlns.com/foaf/0.1/ <br>
	 * 	label : page <br>
	 * 	comment : A page or document about this thing. <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property pageProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/page");


	/**
	 * The Jena Property for title 
	 * <p>(URI: http://xmlns.com/foaf/0.1/title)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	isDefinedBy : http://xmlns.com/foaf/0.1/ <br>
	 * 	label : title <br>
	 * 	comment : Title (Mr, Mrs, Ms, Dr. etc) <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property titleProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/title");


	/**
	 * The Jena Property for givenName 
	 * <p>(URI: http://xmlns.com/foaf/0.1/givenName)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	isDefinedBy : http://xmlns.com/foaf/0.1/ <br>
	 * 	label : Given name <br>
	 * 	comment : The given name of some person. <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property givenNameProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/givenName");


	/**
	 * The Jena Property for givenname 
	 * <p>(URI: http://xmlns.com/foaf/0.1/givenname)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	isDefinedBy : http://xmlns.com/foaf/0.1/ <br>
	 * 	label : Given name <br>
	 * 	comment : The given name of some person. <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property givennameProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/givenname");


	/**
	 * The Jena Property for nick 
	 * <p>(URI: http://xmlns.com/foaf/0.1/nick)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	isDefinedBy : http://xmlns.com/foaf/0.1/ <br>
	 * 	label : nickname <br>
	 * 	comment : A short informal nickname characterising an agent (includes login identifiers, IRC and other chat nicknames). <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property nickProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/nick");


	/**
	 * The Jena Property for dnaChecksum 
	 * <p>(URI: http://xmlns.com/foaf/0.1/dnaChecksum)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	isDefinedBy : http://xmlns.com/foaf/0.1/ <br>
	 * 	label : DNA checksum <br>
	 * 	comment : A checksum for the DNA of some thing. Joke. <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property dnaChecksumProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/dnaChecksum");


	/**
	 * The Jena Property for phone 
	 * <p>(URI: http://xmlns.com/foaf/0.1/phone)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	isDefinedBy : http://xmlns.com/foaf/0.1/ <br>
	 * 	label : phone <br>
	 * 	comment : A phone,  specified using fully qualified tel: URI scheme (refs: http://www.w3.org/Addressing/schemes.html#tel). <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property phoneProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/phone");




	/**
	 * Get an Iterator the 'depicts' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link com.ibm.adtech.jastor.Thing}
	 * @see			#depictsProperty
	 */
	public java.util.Iterator getDepicts() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'depicts' property
	 * @param		The {@link com.ibm.adtech.jastor.Thing} to add
	 * @see			#depictsProperty
	 */
	public void addDepicts(com.ibm.adtech.jastor.Thing depicts) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'depicts' property
	 * @return		The anoymous {@link com.ibm.adtech.jastor.Thing} created
	 * @see			#depictsProperty
	 */
	public com.ibm.adtech.jastor.Thing addDepicts() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * 
	 * The resource argument have rdf:type http://www.w3.org/2000/01/rdf-schema#Resource.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#depictsProperty
	 */
	public com.ibm.adtech.jastor.Thing addDepicts(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'depicts' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link com.ibm.adtech.jastor.Thing} to remove
	 * @see			#depictsProperty
	 */
	public void removeDepicts(com.ibm.adtech.jastor.Thing depicts) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Get an Iterator the 'thumbnail' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link de.m0ep.uni.ma.rdfs.foaf.Image}
	 * @see			#thumbnailProperty
	 */
	public java.util.Iterator getThumbnail() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'thumbnail' property
	 * @param		The {@link de.m0ep.uni.ma.rdfs.foaf.Image} to add
	 * @see			#thumbnailProperty
	 */
	public void addThumbnail(de.m0ep.uni.ma.rdfs.foaf.Image thumbnail) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'thumbnail' property
	 * @return		The anoymous {@link de.m0ep.uni.ma.rdfs.foaf.Image} created
	 * @see			#thumbnailProperty
	 */
	public de.m0ep.uni.ma.rdfs.foaf.Image addThumbnail() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * 
	 * The resource argument have rdf:type http://xmlns.com/foaf/0.1/Image.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#thumbnailProperty
	 */
	public de.m0ep.uni.ma.rdfs.foaf.Image addThumbnail(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'thumbnail' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link de.m0ep.uni.ma.rdfs.foaf.Image} to remove
	 * @see			#thumbnailProperty
	 */
	public void removeThumbnail(de.m0ep.uni.ma.rdfs.foaf.Image thumbnail) throws com.ibm.adtech.jastor.JastorException;
		
}