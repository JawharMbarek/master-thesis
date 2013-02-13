

package de.m0ep.uni.ma.rdfs.foaf;

import com.hp.hpl.jena.rdf.model.*;

/**
 * Interface for Person ontology class<br>
 * Use the de.m0ep.uni.ma.rdfs.foaf.Factory to create instances of this interface.
 * <p>(URI: http://xmlns.com/foaf/0.1/Person)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	isDefinedBy : http://xmlns.com/foaf/0.1/ <br>
 * 	label : Person <br>
 * 	comment : A person. <br>
 * <br>
 * <br>
 */
public interface Person extends de.m0ep.uni.ma.rdfs.foaf.SpatialThing, de.m0ep.uni.ma.rdfs.foaf.Person, de.m0ep.uni.ma.rdfs.foaf.Agent, com.ibm.adtech.jastor.Thing {
	
	/**
	 * The rdf:type for this ontology class
     */
	public static final Resource TYPE = ResourceFactory.createResource("http://xmlns.com/foaf/0.1/Person");
	

	/**
	 * The Jena Property for geekcode 
	 * <p>(URI: http://xmlns.com/foaf/0.1/geekcode)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	isDefinedBy : http://xmlns.com/foaf/0.1/ <br>
	 * 	label : geekcode <br>
	 * 	comment : A textual geekcode for this person, see http://www.geekcode.com/geek.html <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property geekcodeProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/geekcode");


	/**
	 * The Jena Property for publications 
	 * <p>(URI: http://xmlns.com/foaf/0.1/publications)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	isDefinedBy : http://xmlns.com/foaf/0.1/ <br>
	 * 	label : publications <br>
	 * 	comment : A link to the publications of this person. <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property publicationsProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/publications");


	/**
	 * The Jena Property for workInfoHomepage 
	 * <p>(URI: http://xmlns.com/foaf/0.1/workInfoHomepage)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	isDefinedBy : http://xmlns.com/foaf/0.1/ <br>
	 * 	label : work info homepage <br>
	 * 	comment : A work info homepage of some person; a page about their work for some organization. <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property workInfoHomepageProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/workInfoHomepage");


	/**
	 * The Jena Property for surname 
	 * <p>(URI: http://xmlns.com/foaf/0.1/surname)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	isDefinedBy : http://xmlns.com/foaf/0.1/ <br>
	 * 	label : Surname <br>
	 * 	comment : The surname of some person. <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property surnameProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/surname");


	/**
	 * The Jena Property for lastName 
	 * <p>(URI: http://xmlns.com/foaf/0.1/lastName)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	isDefinedBy : http://xmlns.com/foaf/0.1/ <br>
	 * 	label : lastName <br>
	 * 	comment : The last name of a person. <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property lastNameProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/lastName");


	/**
	 * The Jena Property for schoolHomepage 
	 * <p>(URI: http://xmlns.com/foaf/0.1/schoolHomepage)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	isDefinedBy : http://xmlns.com/foaf/0.1/ <br>
	 * 	label : schoolHomepage <br>
	 * 	comment : A homepage of a school attended by the person. <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property schoolHomepageProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/schoolHomepage");


	/**
	 * The Jena Property for family__name 
	 * <p>(URI: http://xmlns.com/foaf/0.1/family_name)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	isDefinedBy : http://xmlns.com/foaf/0.1/ <br>
	 * 	label : family_name <br>
	 * 	comment : The family name of some person. <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property family__nameProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/family_name");


	/**
	 * The Jena Property for plan 
	 * <p>(URI: http://xmlns.com/foaf/0.1/plan)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	isDefinedBy : http://xmlns.com/foaf/0.1/ <br>
	 * 	label : plan <br>
	 * 	comment : A .plan comment, in the tradition of finger and '.plan' files. <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property planProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/plan");


	/**
	 * The Jena Property for firstName 
	 * <p>(URI: http://xmlns.com/foaf/0.1/firstName)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	isDefinedBy : http://xmlns.com/foaf/0.1/ <br>
	 * 	label : firstName <br>
	 * 	comment : The first name of a person. <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property firstNameProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/firstName");


	/**
	 * The Jena Property for img 
	 * <p>(URI: http://xmlns.com/foaf/0.1/img)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	isDefinedBy : http://xmlns.com/foaf/0.1/ <br>
	 * 	label : image <br>
	 * 	comment : An image that can be used to represent some thing (ie. those depictions which are particularly representative of something, eg. one's photo on a homepage). <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property imgProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/img");


	/**
	 * The Jena Property for currentProject 
	 * <p>(URI: http://xmlns.com/foaf/0.1/currentProject)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	isDefinedBy : http://xmlns.com/foaf/0.1/ <br>
	 * 	label : current project <br>
	 * 	comment : A current project this person works on. <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property currentProjectProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/currentProject");


	/**
	 * The Jena Property for knows 
	 * <p>(URI: http://xmlns.com/foaf/0.1/knows)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	isDefinedBy : http://xmlns.com/foaf/0.1/ <br>
	 * 	label : knows <br>
	 * 	comment : A person known by this person (indicating some level of reciprocated interaction between the parties). <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property knowsProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/knows");


	/**
	 * The Jena Property for workplaceHomepage 
	 * <p>(URI: http://xmlns.com/foaf/0.1/workplaceHomepage)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	isDefinedBy : http://xmlns.com/foaf/0.1/ <br>
	 * 	label : workplace homepage <br>
	 * 	comment : A workplace homepage of some person; the homepage of an organization they work for. <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property workplaceHomepageProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/workplaceHomepage");


	/**
	 * The Jena Property for pastProject 
	 * <p>(URI: http://xmlns.com/foaf/0.1/pastProject)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	isDefinedBy : http://xmlns.com/foaf/0.1/ <br>
	 * 	label : past project <br>
	 * 	comment : A project this person has previously worked on. <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property pastProjectProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/pastProject");


	/**
	 * The Jena Property for familyName 
	 * <p>(URI: http://xmlns.com/foaf/0.1/familyName)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	isDefinedBy : http://xmlns.com/foaf/0.1/ <br>
	 * 	label : familyName <br>
	 * 	comment : The family name of some person. <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property familyNameProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/familyName");


	/**
	 * The Jena Property for myersBriggs 
	 * <p>(URI: http://xmlns.com/foaf/0.1/myersBriggs)</p>
	 * <br>
	 * <br>
	 * RDF Schema Standard Properties <br>
	 * 	isDefinedBy : http://xmlns.com/foaf/0.1/ <br>
	 * 	label : myersBriggs <br>
	 * 	comment : A Myers Briggs (MBTI) personality classification. <br>
	 * <br>  
	 */
	public static com.hp.hpl.jena.rdf.model.Property myersBriggsProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/myersBriggs");


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
	 * Iterates through the 'geekcode' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link com.hp.hpl.jena.rdf.model.Literal}
	 * @see			#geekcodeProperty
	 */
	public java.util.Iterator getGeekcode() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Add a 'geekcode' property value
	 * @param		{@link com.hp.hpl.jena.rdf.model.Literal}, the value to add
	 * @see			#geekcodeProperty
	 */
	public void addGeekcode(com.hp.hpl.jena.rdf.model.Literal geekcode) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Remove a 'geekcode' property value. This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Literal}, the value to remove
	 * @see			#geekcodeProperty
	 */
	public void removeGeekcode(com.hp.hpl.jena.rdf.model.Literal geekcode) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Get an Iterator the 'publications' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link de.m0ep.uni.ma.rdfs.foaf.Document}
	 * @see			#publicationsProperty
	 */
	public java.util.Iterator getPublications() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'publications' property
	 * @param		The {@link de.m0ep.uni.ma.rdfs.foaf.Document} to add
	 * @see			#publicationsProperty
	 */
	public void addPublications(de.m0ep.uni.ma.rdfs.foaf.Document publications) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'publications' property
	 * @return		The anoymous {@link de.m0ep.uni.ma.rdfs.foaf.Document} created
	 * @see			#publicationsProperty
	 */
	public de.m0ep.uni.ma.rdfs.foaf.Document addPublications() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * 
	 * The resource argument have rdf:type http://xmlns.com/foaf/0.1/Document.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#publicationsProperty
	 */
	public de.m0ep.uni.ma.rdfs.foaf.Document addPublications(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'publications' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link de.m0ep.uni.ma.rdfs.foaf.Document} to remove
	 * @see			#publicationsProperty
	 */
	public void removePublications(de.m0ep.uni.ma.rdfs.foaf.Document publications) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Get an Iterator the 'workInfoHomepage' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link de.m0ep.uni.ma.rdfs.foaf.Document}
	 * @see			#workInfoHomepageProperty
	 */
	public java.util.Iterator getWorkInfoHomepage() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'workInfoHomepage' property
	 * @param		The {@link de.m0ep.uni.ma.rdfs.foaf.Document} to add
	 * @see			#workInfoHomepageProperty
	 */
	public void addWorkInfoHomepage(de.m0ep.uni.ma.rdfs.foaf.Document workInfoHomepage) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'workInfoHomepage' property
	 * @return		The anoymous {@link de.m0ep.uni.ma.rdfs.foaf.Document} created
	 * @see			#workInfoHomepageProperty
	 */
	public de.m0ep.uni.ma.rdfs.foaf.Document addWorkInfoHomepage() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * 
	 * The resource argument have rdf:type http://xmlns.com/foaf/0.1/Document.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#workInfoHomepageProperty
	 */
	public de.m0ep.uni.ma.rdfs.foaf.Document addWorkInfoHomepage(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'workInfoHomepage' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link de.m0ep.uni.ma.rdfs.foaf.Document} to remove
	 * @see			#workInfoHomepageProperty
	 */
	public void removeWorkInfoHomepage(de.m0ep.uni.ma.rdfs.foaf.Document workInfoHomepage) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Iterates through the 'surname' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link com.hp.hpl.jena.rdf.model.Literal}
	 * @see			#surnameProperty
	 */
	public java.util.Iterator getSurname() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Add a 'surname' property value
	 * @param		{@link com.hp.hpl.jena.rdf.model.Literal}, the value to add
	 * @see			#surnameProperty
	 */
	public void addSurname(com.hp.hpl.jena.rdf.model.Literal surname) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Remove a 'surname' property value. This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Literal}, the value to remove
	 * @see			#surnameProperty
	 */
	public void removeSurname(com.hp.hpl.jena.rdf.model.Literal surname) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Iterates through the 'lastName' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link com.hp.hpl.jena.rdf.model.Literal}
	 * @see			#lastNameProperty
	 */
	public java.util.Iterator getLastName() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Add a 'lastName' property value
	 * @param		{@link com.hp.hpl.jena.rdf.model.Literal}, the value to add
	 * @see			#lastNameProperty
	 */
	public void addLastName(com.hp.hpl.jena.rdf.model.Literal lastName) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Remove a 'lastName' property value. This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Literal}, the value to remove
	 * @see			#lastNameProperty
	 */
	public void removeLastName(com.hp.hpl.jena.rdf.model.Literal lastName) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Get an Iterator the 'schoolHomepage' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link de.m0ep.uni.ma.rdfs.foaf.Document}
	 * @see			#schoolHomepageProperty
	 */
	public java.util.Iterator getSchoolHomepage() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'schoolHomepage' property
	 * @param		The {@link de.m0ep.uni.ma.rdfs.foaf.Document} to add
	 * @see			#schoolHomepageProperty
	 */
	public void addSchoolHomepage(de.m0ep.uni.ma.rdfs.foaf.Document schoolHomepage) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'schoolHomepage' property
	 * @return		The anoymous {@link de.m0ep.uni.ma.rdfs.foaf.Document} created
	 * @see			#schoolHomepageProperty
	 */
	public de.m0ep.uni.ma.rdfs.foaf.Document addSchoolHomepage() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * 
	 * The resource argument have rdf:type http://xmlns.com/foaf/0.1/Document.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#schoolHomepageProperty
	 */
	public de.m0ep.uni.ma.rdfs.foaf.Document addSchoolHomepage(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'schoolHomepage' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link de.m0ep.uni.ma.rdfs.foaf.Document} to remove
	 * @see			#schoolHomepageProperty
	 */
	public void removeSchoolHomepage(de.m0ep.uni.ma.rdfs.foaf.Document schoolHomepage) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Iterates through the 'family__name' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link com.hp.hpl.jena.rdf.model.Literal}
	 * @see			#family__nameProperty
	 */
	public java.util.Iterator getFamily__name() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Add a 'family__name' property value
	 * @param		{@link com.hp.hpl.jena.rdf.model.Literal}, the value to add
	 * @see			#family__nameProperty
	 */
	public void addFamily__name(com.hp.hpl.jena.rdf.model.Literal family__name) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Remove a 'family__name' property value. This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Literal}, the value to remove
	 * @see			#family__nameProperty
	 */
	public void removeFamily__name(com.hp.hpl.jena.rdf.model.Literal family__name) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Iterates through the 'plan' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link com.hp.hpl.jena.rdf.model.Literal}
	 * @see			#planProperty
	 */
	public java.util.Iterator getPlan() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Add a 'plan' property value
	 * @param		{@link com.hp.hpl.jena.rdf.model.Literal}, the value to add
	 * @see			#planProperty
	 */
	public void addPlan(com.hp.hpl.jena.rdf.model.Literal plan) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Remove a 'plan' property value. This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Literal}, the value to remove
	 * @see			#planProperty
	 */
	public void removePlan(com.hp.hpl.jena.rdf.model.Literal plan) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Iterates through the 'firstName' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link com.hp.hpl.jena.rdf.model.Literal}
	 * @see			#firstNameProperty
	 */
	public java.util.Iterator getFirstName() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Add a 'firstName' property value
	 * @param		{@link com.hp.hpl.jena.rdf.model.Literal}, the value to add
	 * @see			#firstNameProperty
	 */
	public void addFirstName(com.hp.hpl.jena.rdf.model.Literal firstName) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Remove a 'firstName' property value. This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Literal}, the value to remove
	 * @see			#firstNameProperty
	 */
	public void removeFirstName(com.hp.hpl.jena.rdf.model.Literal firstName) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Get an Iterator the 'img' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link de.m0ep.uni.ma.rdfs.foaf.Image}
	 * @see			#imgProperty
	 */
	public java.util.Iterator getImg() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'img' property
	 * @param		The {@link de.m0ep.uni.ma.rdfs.foaf.Image} to add
	 * @see			#imgProperty
	 */
	public void addImg(de.m0ep.uni.ma.rdfs.foaf.Image img) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'img' property
	 * @return		The anoymous {@link de.m0ep.uni.ma.rdfs.foaf.Image} created
	 * @see			#imgProperty
	 */
	public de.m0ep.uni.ma.rdfs.foaf.Image addImg() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * 
	 * The resource argument have rdf:type http://xmlns.com/foaf/0.1/Image.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#imgProperty
	 */
	public de.m0ep.uni.ma.rdfs.foaf.Image addImg(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'img' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link de.m0ep.uni.ma.rdfs.foaf.Image} to remove
	 * @see			#imgProperty
	 */
	public void removeImg(de.m0ep.uni.ma.rdfs.foaf.Image img) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Get an Iterator the 'currentProject' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link com.ibm.adtech.jastor.Thing}
	 * @see			#currentProjectProperty
	 */
	public java.util.Iterator getCurrentProject() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'currentProject' property
	 * @param		The {@link com.ibm.adtech.jastor.Thing} to add
	 * @see			#currentProjectProperty
	 */
	public void addCurrentProject(com.ibm.adtech.jastor.Thing currentProject) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'currentProject' property
	 * @return		The anoymous {@link com.ibm.adtech.jastor.Thing} created
	 * @see			#currentProjectProperty
	 */
	public com.ibm.adtech.jastor.Thing addCurrentProject() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * 
	 * The resource argument have rdf:type http://www.w3.org/2000/01/rdf-schema#Resource.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#currentProjectProperty
	 */
	public com.ibm.adtech.jastor.Thing addCurrentProject(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'currentProject' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link com.ibm.adtech.jastor.Thing} to remove
	 * @see			#currentProjectProperty
	 */
	public void removeCurrentProject(com.ibm.adtech.jastor.Thing currentProject) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Get an Iterator the 'knows' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link de.m0ep.uni.ma.rdfs.foaf.Person}
	 * @see			#knowsProperty
	 */
	public java.util.Iterator getKnows() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'knows' property
	 * @param		The {@link de.m0ep.uni.ma.rdfs.foaf.Person} to add
	 * @see			#knowsProperty
	 */
	public void addKnows(de.m0ep.uni.ma.rdfs.foaf.Person knows) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'knows' property
	 * @return		The anoymous {@link de.m0ep.uni.ma.rdfs.foaf.Person} created
	 * @see			#knowsProperty
	 */
	public de.m0ep.uni.ma.rdfs.foaf.Person addKnows() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * 
	 * The resource argument have rdf:type http://xmlns.com/foaf/0.1/Person.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#knowsProperty
	 */
	public de.m0ep.uni.ma.rdfs.foaf.Person addKnows(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'knows' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link de.m0ep.uni.ma.rdfs.foaf.Person} to remove
	 * @see			#knowsProperty
	 */
	public void removeKnows(de.m0ep.uni.ma.rdfs.foaf.Person knows) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Get an Iterator the 'workplaceHomepage' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link de.m0ep.uni.ma.rdfs.foaf.Document}
	 * @see			#workplaceHomepageProperty
	 */
	public java.util.Iterator getWorkplaceHomepage() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'workplaceHomepage' property
	 * @param		The {@link de.m0ep.uni.ma.rdfs.foaf.Document} to add
	 * @see			#workplaceHomepageProperty
	 */
	public void addWorkplaceHomepage(de.m0ep.uni.ma.rdfs.foaf.Document workplaceHomepage) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'workplaceHomepage' property
	 * @return		The anoymous {@link de.m0ep.uni.ma.rdfs.foaf.Document} created
	 * @see			#workplaceHomepageProperty
	 */
	public de.m0ep.uni.ma.rdfs.foaf.Document addWorkplaceHomepage() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * 
	 * The resource argument have rdf:type http://xmlns.com/foaf/0.1/Document.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#workplaceHomepageProperty
	 */
	public de.m0ep.uni.ma.rdfs.foaf.Document addWorkplaceHomepage(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'workplaceHomepage' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link de.m0ep.uni.ma.rdfs.foaf.Document} to remove
	 * @see			#workplaceHomepageProperty
	 */
	public void removeWorkplaceHomepage(de.m0ep.uni.ma.rdfs.foaf.Document workplaceHomepage) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Get an Iterator the 'pastProject' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link com.ibm.adtech.jastor.Thing}
	 * @see			#pastProjectProperty
	 */
	public java.util.Iterator getPastProject() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Adds a value for the 'pastProject' property
	 * @param		The {@link com.ibm.adtech.jastor.Thing} to add
	 * @see			#pastProjectProperty
	 */
	public void addPastProject(com.ibm.adtech.jastor.Thing pastProject) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Adds an anonymous value for the 'pastProject' property
	 * @return		The anoymous {@link com.ibm.adtech.jastor.Thing} created
	 * @see			#pastProjectProperty
	 */
	public com.ibm.adtech.jastor.Thing addPastProject() throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * 
	 * The resource argument have rdf:type http://www.w3.org/2000/01/rdf-schema#Resource.  That is, this method
	 * should not be used as a shortcut for creating new objects in the model.
	 * @param		The {@link om.hp.hpl.jena.rdf.model.Resource} to add
	 * @see			#pastProjectProperty
	 */
	public com.ibm.adtech.jastor.Thing addPastProject(com.hp.hpl.jena.rdf.model.Resource resource) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Removes a value for the 'pastProject' property.  This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		The {@link com.ibm.adtech.jastor.Thing} to remove
	 * @see			#pastProjectProperty
	 */
	public void removePastProject(com.ibm.adtech.jastor.Thing pastProject) throws com.ibm.adtech.jastor.JastorException;
		
	/**
	 * Iterates through the 'familyName' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link com.hp.hpl.jena.rdf.model.Literal}
	 * @see			#familyNameProperty
	 */
	public java.util.Iterator getFamilyName() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Add a 'familyName' property value
	 * @param		{@link com.hp.hpl.jena.rdf.model.Literal}, the value to add
	 * @see			#familyNameProperty
	 */
	public void addFamilyName(com.hp.hpl.jena.rdf.model.Literal familyName) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Remove a 'familyName' property value. This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Literal}, the value to remove
	 * @see			#familyNameProperty
	 */
	public void removeFamilyName(com.hp.hpl.jena.rdf.model.Literal familyName) throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Iterates through the 'myersBriggs' property values.  This Iteartor
	 * may be used to remove all such values.
	 * @return		{@link java.util.Iterator} of {@link com.hp.hpl.jena.rdf.model.Literal}
	 * @see			#myersBriggsProperty
	 */
	public java.util.Iterator getMyersBriggs() throws com.ibm.adtech.jastor.JastorException;

	/**
	 * Add a 'myersBriggs' property value
	 * @param		{@link com.hp.hpl.jena.rdf.model.Literal}, the value to add
	 * @see			#myersBriggsProperty
	 */
	public void addMyersBriggs(com.hp.hpl.jena.rdf.model.Literal myersBriggs) throws com.ibm.adtech.jastor.JastorException;
	
	/**
	 * Remove a 'myersBriggs' property value. This method should not
	 * be invoked while iterator through values.  In that case, the remove() method of the Iterator
	 * itself should be used.
	 * @param		{@link com.hp.hpl.jena.rdf.model.Literal}, the value to remove
	 * @see			#myersBriggsProperty
	 */
	public void removeMyersBriggs(com.hp.hpl.jena.rdf.model.Literal myersBriggs) throws com.ibm.adtech.jastor.JastorException;

}