

package de.m0ep.uni.ma.rdfs.foaf;

/*
import com.hp.hpl.jena.datatypes.xsd.*;
import com.hp.hpl.jena.datatypes.xsd.impl.*;
*/
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.rdf.listeners.StatementListener;
import com.hp.hpl.jena.vocabulary.RDF;
import com.ibm.adtech.jastor.*;
import com.ibm.adtech.jastor.util.*;


/**
 * Implementation of {@link de.m0ep.uni.ma.rdfs.foaf.Image}
 * Use the de.m0ep.uni.ma.rdfs.foaf.Factory to create instances of this class.
 * <p>(URI: http://xmlns.com/foaf/0.1/Image)</p>
 * <br>
 */
public class ImageImpl extends com.ibm.adtech.jastor.ThingImpl implements de.m0ep.uni.ma.rdfs.foaf.Image {
	

	private static com.hp.hpl.jena.rdf.model.Property fundedByProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/fundedBy");
	private java.util.ArrayList fundedBy;
	private static com.hp.hpl.jena.rdf.model.Property nameProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/name");
	private java.util.ArrayList name;
	private static com.hp.hpl.jena.rdf.model.Property primaryTopicProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/primaryTopic");
	private com.ibm.adtech.jastor.Thing primaryTopic;
	private static com.hp.hpl.jena.rdf.model.Property dnaChecksumProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/dnaChecksum");
	private java.util.ArrayList dnaChecksum;
	private static com.hp.hpl.jena.rdf.model.Property phoneProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/phone");
	private java.util.ArrayList phone;
	private static com.hp.hpl.jena.rdf.model.Property pageProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/page");
	private java.util.ArrayList page;
	private static com.hp.hpl.jena.rdf.model.Property nickProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/nick");
	private java.util.ArrayList nick;
	private static com.hp.hpl.jena.rdf.model.Property logoProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/logo");
	private java.util.ArrayList logo;
	private static com.hp.hpl.jena.rdf.model.Property makerProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/maker");
	private java.util.ArrayList maker;
	private static com.hp.hpl.jena.rdf.model.Property givennameProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/givenname");
	private java.util.ArrayList givenname;
	private static com.hp.hpl.jena.rdf.model.Property topicProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/topic");
	private java.util.ArrayList topic;
	private static com.hp.hpl.jena.rdf.model.Property titleProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/title");
	private java.util.ArrayList title;
	private static com.hp.hpl.jena.rdf.model.Property givenNameProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/givenName");
	private java.util.ArrayList givenName;
	private static com.hp.hpl.jena.rdf.model.Property homepageProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/homepage");
	private java.util.ArrayList homepage;
	private static com.hp.hpl.jena.rdf.model.Property sha1Property = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/sha1");
	private java.util.ArrayList sha1;
	private static com.hp.hpl.jena.rdf.model.Property themeProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/theme");
	private java.util.ArrayList theme;
	private static com.hp.hpl.jena.rdf.model.Property depictsProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/depicts");
	private java.util.ArrayList depicts;
	private static com.hp.hpl.jena.rdf.model.Property thumbnailProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/thumbnail");
	private java.util.ArrayList thumbnail;
 

	ImageImpl(Resource resource, Model model) throws JastorException {
		super(resource, model);
		setupModelListener();
	}     
    	
	static ImageImpl getImage(Resource resource, Model model) throws JastorException {
		return new ImageImpl(resource, model);
	}
	    
	static ImageImpl createImage(Resource resource, Model model) throws JastorException { 
		ImageImpl impl = new ImageImpl(resource, model);
		
		if (!impl._model.contains(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(impl._resource, RDF.type, Image.TYPE)))
			impl._model.add(impl._resource, RDF.type, Image.TYPE);
		impl.addSuperTypes();
		impl.addHasValueValues();
		return impl;
	}
	
	void addSuperTypes() {
		if (!_model.contains(_resource, RDF.type, de.m0ep.uni.ma.rdfs.foaf.Document.TYPE))
			_model.add(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(_resource, RDF.type, de.m0ep.uni.ma.rdfs.foaf.Document.TYPE));     
	}
   
	void addHasValueValues() {
	}
    
    private void setupModelListener() {
    	listeners = new java.util.ArrayList();
    	de.m0ep.uni.ma.rdfs.foaf.Factory.registerThing(this);
    }

	public java.util.List listStatements() {
		java.util.List list = new java.util.ArrayList();
		StmtIterator it = null;
		it = _model.listStatements(_resource,fundedByProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,nameProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,primaryTopicProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,dnaChecksumProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,phoneProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,pageProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,nickProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,logoProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,makerProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,givennameProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,topicProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,titleProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,givenNameProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,homepageProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,sha1Property,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,themeProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,depictsProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,thumbnailProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, de.m0ep.uni.ma.rdfs.foaf.Image.TYPE);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, de.m0ep.uni.ma.rdfs.foaf.Document.TYPE);
		while (it.hasNext()) {
			list.add(it.next());
		}
		return list;
	}
	
	public void clearCache() {
		fundedBy = null;
		name = null;
		primaryTopic = null;
		dnaChecksum = null;
		phone = null;
		page = null;
		nick = null;
		logo = null;
		maker = null;
		givenname = null;
		topic = null;
		title = null;
		givenName = null;
		homepage = null;
		sha1 = null;
		theme = null;
		depicts = null;
		thumbnail = null;
	}

	private com.hp.hpl.jena.rdf.model.Literal createLiteral(Object obj) {
		return _model.createTypedLiteral(obj);
	}


	private void initFundedBy() throws JastorException {
		this.fundedBy = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, fundedByProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/fundedBy properties in Image model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				com.ibm.adtech.jastor.Thing fundedBy = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
				this.fundedBy.add(fundedBy);
			}
		}
	}

	public java.util.Iterator getFundedBy() throws JastorException {
		if (fundedBy == null)
			initFundedBy();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(fundedBy,_resource,fundedByProperty,true);
	}

	public void addFundedBy(com.ibm.adtech.jastor.Thing fundedBy) throws JastorException {
		if (this.fundedBy == null)
			initFundedBy();
		if (this.fundedBy.contains(fundedBy)) {
			this.fundedBy.remove(fundedBy);
			this.fundedBy.add(fundedBy);
			return;
		}
		this.fundedBy.add(fundedBy);
		_model.add(_model.createStatement(_resource,fundedByProperty,fundedBy.resource()));
	}
	
	public com.ibm.adtech.jastor.Thing addFundedBy() throws JastorException {
		com.ibm.adtech.jastor.Thing fundedBy = com.ibm.adtech.jastor.ThingFactory.createThing(_model.createResource(),_model);
		if (this.fundedBy == null)
			initFundedBy();
		this.fundedBy.add(fundedBy);
		_model.add(_model.createStatement(_resource,fundedByProperty,fundedBy.resource()));
		return fundedBy;
	}
	
	public com.ibm.adtech.jastor.Thing addFundedBy(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		com.ibm.adtech.jastor.Thing fundedBy = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
		if (this.fundedBy == null)
			initFundedBy();
		if (this.fundedBy.contains(fundedBy))
			return fundedBy;
		this.fundedBy.add(fundedBy);
		_model.add(_model.createStatement(_resource,fundedByProperty,fundedBy.resource()));
		return fundedBy;
	}
	
	public void removeFundedBy(com.ibm.adtech.jastor.Thing fundedBy) throws JastorException {
		if (this.fundedBy == null)
			initFundedBy();
		if (!this.fundedBy.contains(fundedBy))
			return;
		if (!_model.contains(_resource, fundedByProperty, fundedBy.resource()))
			return;
		this.fundedBy.remove(fundedBy);
		_model.removeAll(_resource, fundedByProperty, fundedBy.resource());
	}
		 

	private void initName() throws JastorException {
		name = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, nameProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/name properties in Image model not a Literal", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
			name.add(literal);
		}
	}

	public java.util.Iterator getName() throws JastorException {
		if (name == null)
			initName();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(name,_resource,nameProperty,false);
	}

	public void addName(com.hp.hpl.jena.rdf.model.Literal name) throws JastorException {
		if (this.name == null)
			initName();
		if (this.name.contains(name))
			return;
		if (_model.contains(_resource, nameProperty, createLiteral(name)))
			return;
		this.name.add(name);
		_model.add(_resource, nameProperty, name);
	}
	
	public void removeName(com.hp.hpl.jena.rdf.model.Literal name) throws JastorException {
		if (this.name == null)
			initName();
		if (!this.name.contains(name))
			return;
		if (!_model.contains(_resource, nameProperty, createLiteral(name)))
			return;
		this.name.remove(name);
		_model.removeAll(_resource, nameProperty, createLiteral(name));
	}


	public com.ibm.adtech.jastor.Thing getPrimaryTopic() throws JastorException {
		if (primaryTopic != null)
			return primaryTopic;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, primaryTopicProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
			throw new JastorInvalidRDFNodeException(uri() + ": primaryTopic getProperty() in de.m0ep.uni.ma.rdfs.foaf.Image model not Resource", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
		primaryTopic = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
		return primaryTopic;
	}

	public void setPrimaryTopic(com.ibm.adtech.jastor.Thing primaryTopic) throws JastorException {
		if (_model.contains(_resource,primaryTopicProperty)) {
			_model.removeAll(_resource,primaryTopicProperty,null);
		}
		this.primaryTopic = primaryTopic;
		if (primaryTopic != null) {
			_model.add(_model.createStatement(_resource,primaryTopicProperty, primaryTopic.resource()));
		}			
	}
		
	public com.ibm.adtech.jastor.Thing setPrimaryTopic() throws JastorException {
		if (_model.contains(_resource,primaryTopicProperty)) {
			_model.removeAll(_resource,primaryTopicProperty,null);
		}
		com.ibm.adtech.jastor.Thing primaryTopic = com.ibm.adtech.jastor.ThingFactory.createThing(_model.createResource(),_model);
		this.primaryTopic = primaryTopic;
		_model.add(_model.createStatement(_resource,primaryTopicProperty, primaryTopic.resource()));
		return primaryTopic;
	}
	
	public com.ibm.adtech.jastor.Thing setPrimaryTopic(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		if (_model.contains(_resource,primaryTopicProperty)) {
			_model.removeAll(_resource,primaryTopicProperty,null);
		}
		com.ibm.adtech.jastor.Thing primaryTopic = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
		this.primaryTopic = primaryTopic;
		_model.add(_model.createStatement(_resource,primaryTopicProperty, primaryTopic.resource()));
		return primaryTopic;
	}
	

	private void initDnaChecksum() throws JastorException {
		dnaChecksum = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, dnaChecksumProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/dnaChecksum properties in Image model not a Literal", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
			dnaChecksum.add(literal);
		}
	}

	public java.util.Iterator getDnaChecksum() throws JastorException {
		if (dnaChecksum == null)
			initDnaChecksum();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(dnaChecksum,_resource,dnaChecksumProperty,false);
	}

	public void addDnaChecksum(com.hp.hpl.jena.rdf.model.Literal dnaChecksum) throws JastorException {
		if (this.dnaChecksum == null)
			initDnaChecksum();
		if (this.dnaChecksum.contains(dnaChecksum))
			return;
		if (_model.contains(_resource, dnaChecksumProperty, createLiteral(dnaChecksum)))
			return;
		this.dnaChecksum.add(dnaChecksum);
		_model.add(_resource, dnaChecksumProperty, dnaChecksum);
	}
	
	public void removeDnaChecksum(com.hp.hpl.jena.rdf.model.Literal dnaChecksum) throws JastorException {
		if (this.dnaChecksum == null)
			initDnaChecksum();
		if (!this.dnaChecksum.contains(dnaChecksum))
			return;
		if (!_model.contains(_resource, dnaChecksumProperty, createLiteral(dnaChecksum)))
			return;
		this.dnaChecksum.remove(dnaChecksum);
		_model.removeAll(_resource, dnaChecksumProperty, createLiteral(dnaChecksum));
	}


	private void initPhone() throws JastorException {
		this.phone = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, phoneProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/phone properties in Image model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				com.ibm.adtech.jastor.Thing phone = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
				this.phone.add(phone);
			}
		}
	}

	public java.util.Iterator getPhone() throws JastorException {
		if (phone == null)
			initPhone();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(phone,_resource,phoneProperty,true);
	}

	public void addPhone(com.ibm.adtech.jastor.Thing phone) throws JastorException {
		if (this.phone == null)
			initPhone();
		if (this.phone.contains(phone)) {
			this.phone.remove(phone);
			this.phone.add(phone);
			return;
		}
		this.phone.add(phone);
		_model.add(_model.createStatement(_resource,phoneProperty,phone.resource()));
	}
	
	public com.ibm.adtech.jastor.Thing addPhone() throws JastorException {
		com.ibm.adtech.jastor.Thing phone = com.ibm.adtech.jastor.ThingFactory.createThing(_model.createResource(),_model);
		if (this.phone == null)
			initPhone();
		this.phone.add(phone);
		_model.add(_model.createStatement(_resource,phoneProperty,phone.resource()));
		return phone;
	}
	
	public com.ibm.adtech.jastor.Thing addPhone(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		com.ibm.adtech.jastor.Thing phone = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
		if (this.phone == null)
			initPhone();
		if (this.phone.contains(phone))
			return phone;
		this.phone.add(phone);
		_model.add(_model.createStatement(_resource,phoneProperty,phone.resource()));
		return phone;
	}
	
	public void removePhone(com.ibm.adtech.jastor.Thing phone) throws JastorException {
		if (this.phone == null)
			initPhone();
		if (!this.phone.contains(phone))
			return;
		if (!_model.contains(_resource, phoneProperty, phone.resource()))
			return;
		this.phone.remove(phone);
		_model.removeAll(_resource, phoneProperty, phone.resource());
	}
		 

	private void initPage() throws JastorException {
		this.page = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, pageProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/page properties in Image model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				de.m0ep.uni.ma.rdfs.foaf.Document page = de.m0ep.uni.ma.rdfs.foaf.Factory.getDocument(resource,_model);
				this.page.add(page);
			}
		}
	}

	public java.util.Iterator getPage() throws JastorException {
		if (page == null)
			initPage();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(page,_resource,pageProperty,true);
	}

	public void addPage(de.m0ep.uni.ma.rdfs.foaf.Document page) throws JastorException {
		if (this.page == null)
			initPage();
		if (this.page.contains(page)) {
			this.page.remove(page);
			this.page.add(page);
			return;
		}
		this.page.add(page);
		_model.add(_model.createStatement(_resource,pageProperty,page.resource()));
	}
	
	public de.m0ep.uni.ma.rdfs.foaf.Document addPage() throws JastorException {
		de.m0ep.uni.ma.rdfs.foaf.Document page = de.m0ep.uni.ma.rdfs.foaf.Factory.createDocument(_model.createResource(),_model);
		if (this.page == null)
			initPage();
		this.page.add(page);
		_model.add(_model.createStatement(_resource,pageProperty,page.resource()));
		return page;
	}
	
	public de.m0ep.uni.ma.rdfs.foaf.Document addPage(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		de.m0ep.uni.ma.rdfs.foaf.Document page = de.m0ep.uni.ma.rdfs.foaf.Factory.getDocument(resource,_model);
		if (this.page == null)
			initPage();
		if (this.page.contains(page))
			return page;
		this.page.add(page);
		_model.add(_model.createStatement(_resource,pageProperty,page.resource()));
		return page;
	}
	
	public void removePage(de.m0ep.uni.ma.rdfs.foaf.Document page) throws JastorException {
		if (this.page == null)
			initPage();
		if (!this.page.contains(page))
			return;
		if (!_model.contains(_resource, pageProperty, page.resource()))
			return;
		this.page.remove(page);
		_model.removeAll(_resource, pageProperty, page.resource());
	}
		 

	private void initNick() throws JastorException {
		nick = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, nickProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/nick properties in Image model not a Literal", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
			nick.add(literal);
		}
	}

	public java.util.Iterator getNick() throws JastorException {
		if (nick == null)
			initNick();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(nick,_resource,nickProperty,false);
	}

	public void addNick(com.hp.hpl.jena.rdf.model.Literal nick) throws JastorException {
		if (this.nick == null)
			initNick();
		if (this.nick.contains(nick))
			return;
		if (_model.contains(_resource, nickProperty, createLiteral(nick)))
			return;
		this.nick.add(nick);
		_model.add(_resource, nickProperty, nick);
	}
	
	public void removeNick(com.hp.hpl.jena.rdf.model.Literal nick) throws JastorException {
		if (this.nick == null)
			initNick();
		if (!this.nick.contains(nick))
			return;
		if (!_model.contains(_resource, nickProperty, createLiteral(nick)))
			return;
		this.nick.remove(nick);
		_model.removeAll(_resource, nickProperty, createLiteral(nick));
	}


	private void initLogo() throws JastorException {
		this.logo = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, logoProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/logo properties in Image model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				com.ibm.adtech.jastor.Thing logo = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
				this.logo.add(logo);
			}
		}
	}

	public java.util.Iterator getLogo() throws JastorException {
		if (logo == null)
			initLogo();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(logo,_resource,logoProperty,true);
	}

	public void addLogo(com.ibm.adtech.jastor.Thing logo) throws JastorException {
		if (this.logo == null)
			initLogo();
		if (this.logo.contains(logo)) {
			this.logo.remove(logo);
			this.logo.add(logo);
			return;
		}
		this.logo.add(logo);
		_model.add(_model.createStatement(_resource,logoProperty,logo.resource()));
	}
	
	public com.ibm.adtech.jastor.Thing addLogo() throws JastorException {
		com.ibm.adtech.jastor.Thing logo = com.ibm.adtech.jastor.ThingFactory.createThing(_model.createResource(),_model);
		if (this.logo == null)
			initLogo();
		this.logo.add(logo);
		_model.add(_model.createStatement(_resource,logoProperty,logo.resource()));
		return logo;
	}
	
	public com.ibm.adtech.jastor.Thing addLogo(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		com.ibm.adtech.jastor.Thing logo = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
		if (this.logo == null)
			initLogo();
		if (this.logo.contains(logo))
			return logo;
		this.logo.add(logo);
		_model.add(_model.createStatement(_resource,logoProperty,logo.resource()));
		return logo;
	}
	
	public void removeLogo(com.ibm.adtech.jastor.Thing logo) throws JastorException {
		if (this.logo == null)
			initLogo();
		if (!this.logo.contains(logo))
			return;
		if (!_model.contains(_resource, logoProperty, logo.resource()))
			return;
		this.logo.remove(logo);
		_model.removeAll(_resource, logoProperty, logo.resource());
	}
		 

	private void initMaker() throws JastorException {
		this.maker = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, makerProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/maker properties in Image model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				de.m0ep.uni.ma.rdfs.foaf.Agent maker = de.m0ep.uni.ma.rdfs.foaf.Factory.getAgent(resource,_model);
				this.maker.add(maker);
			}
		}
	}

	public java.util.Iterator getMaker() throws JastorException {
		if (maker == null)
			initMaker();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(maker,_resource,makerProperty,true);
	}

	public void addMaker(de.m0ep.uni.ma.rdfs.foaf.Agent maker) throws JastorException {
		if (this.maker == null)
			initMaker();
		if (this.maker.contains(maker)) {
			this.maker.remove(maker);
			this.maker.add(maker);
			return;
		}
		this.maker.add(maker);
		_model.add(_model.createStatement(_resource,makerProperty,maker.resource()));
	}
	
	public de.m0ep.uni.ma.rdfs.foaf.Agent addMaker() throws JastorException {
		de.m0ep.uni.ma.rdfs.foaf.Agent maker = de.m0ep.uni.ma.rdfs.foaf.Factory.createAgent(_model.createResource(),_model);
		if (this.maker == null)
			initMaker();
		this.maker.add(maker);
		_model.add(_model.createStatement(_resource,makerProperty,maker.resource()));
		return maker;
	}
	
	public de.m0ep.uni.ma.rdfs.foaf.Agent addMaker(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		de.m0ep.uni.ma.rdfs.foaf.Agent maker = de.m0ep.uni.ma.rdfs.foaf.Factory.getAgent(resource,_model);
		if (this.maker == null)
			initMaker();
		if (this.maker.contains(maker))
			return maker;
		this.maker.add(maker);
		_model.add(_model.createStatement(_resource,makerProperty,maker.resource()));
		return maker;
	}
	
	public void removeMaker(de.m0ep.uni.ma.rdfs.foaf.Agent maker) throws JastorException {
		if (this.maker == null)
			initMaker();
		if (!this.maker.contains(maker))
			return;
		if (!_model.contains(_resource, makerProperty, maker.resource()))
			return;
		this.maker.remove(maker);
		_model.removeAll(_resource, makerProperty, maker.resource());
	}
		 

	private void initGivenname() throws JastorException {
		givenname = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, givennameProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/givenname properties in Image model not a Literal", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
			givenname.add(literal);
		}
	}

	public java.util.Iterator getGivenname() throws JastorException {
		if (givenname == null)
			initGivenname();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(givenname,_resource,givennameProperty,false);
	}

	public void addGivenname(com.hp.hpl.jena.rdf.model.Literal givenname) throws JastorException {
		if (this.givenname == null)
			initGivenname();
		if (this.givenname.contains(givenname))
			return;
		if (_model.contains(_resource, givennameProperty, createLiteral(givenname)))
			return;
		this.givenname.add(givenname);
		_model.add(_resource, givennameProperty, givenname);
	}
	
	public void removeGivenname(com.hp.hpl.jena.rdf.model.Literal givenname) throws JastorException {
		if (this.givenname == null)
			initGivenname();
		if (!this.givenname.contains(givenname))
			return;
		if (!_model.contains(_resource, givennameProperty, createLiteral(givenname)))
			return;
		this.givenname.remove(givenname);
		_model.removeAll(_resource, givennameProperty, createLiteral(givenname));
	}


	private void initTopic() throws JastorException {
		this.topic = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, topicProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/topic properties in Image model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				com.ibm.adtech.jastor.Thing topic = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
				this.topic.add(topic);
			}
		}
	}

	public java.util.Iterator getTopic() throws JastorException {
		if (topic == null)
			initTopic();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(topic,_resource,topicProperty,true);
	}

	public void addTopic(com.ibm.adtech.jastor.Thing topic) throws JastorException {
		if (this.topic == null)
			initTopic();
		if (this.topic.contains(topic)) {
			this.topic.remove(topic);
			this.topic.add(topic);
			return;
		}
		this.topic.add(topic);
		_model.add(_model.createStatement(_resource,topicProperty,topic.resource()));
	}
	
	public com.ibm.adtech.jastor.Thing addTopic() throws JastorException {
		com.ibm.adtech.jastor.Thing topic = com.ibm.adtech.jastor.ThingFactory.createThing(_model.createResource(),_model);
		if (this.topic == null)
			initTopic();
		this.topic.add(topic);
		_model.add(_model.createStatement(_resource,topicProperty,topic.resource()));
		return topic;
	}
	
	public com.ibm.adtech.jastor.Thing addTopic(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		com.ibm.adtech.jastor.Thing topic = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
		if (this.topic == null)
			initTopic();
		if (this.topic.contains(topic))
			return topic;
		this.topic.add(topic);
		_model.add(_model.createStatement(_resource,topicProperty,topic.resource()));
		return topic;
	}
	
	public void removeTopic(com.ibm.adtech.jastor.Thing topic) throws JastorException {
		if (this.topic == null)
			initTopic();
		if (!this.topic.contains(topic))
			return;
		if (!_model.contains(_resource, topicProperty, topic.resource()))
			return;
		this.topic.remove(topic);
		_model.removeAll(_resource, topicProperty, topic.resource());
	}
		 

	private void initTitle() throws JastorException {
		title = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, titleProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/title properties in Image model not a Literal", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
			title.add(literal);
		}
	}

	public java.util.Iterator getTitle() throws JastorException {
		if (title == null)
			initTitle();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(title,_resource,titleProperty,false);
	}

	public void addTitle(com.hp.hpl.jena.rdf.model.Literal title) throws JastorException {
		if (this.title == null)
			initTitle();
		if (this.title.contains(title))
			return;
		if (_model.contains(_resource, titleProperty, createLiteral(title)))
			return;
		this.title.add(title);
		_model.add(_resource, titleProperty, title);
	}
	
	public void removeTitle(com.hp.hpl.jena.rdf.model.Literal title) throws JastorException {
		if (this.title == null)
			initTitle();
		if (!this.title.contains(title))
			return;
		if (!_model.contains(_resource, titleProperty, createLiteral(title)))
			return;
		this.title.remove(title);
		_model.removeAll(_resource, titleProperty, createLiteral(title));
	}


	private void initGivenName() throws JastorException {
		givenName = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, givenNameProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/givenName properties in Image model not a Literal", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
			givenName.add(literal);
		}
	}

	public java.util.Iterator getGivenName() throws JastorException {
		if (givenName == null)
			initGivenName();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(givenName,_resource,givenNameProperty,false);
	}

	public void addGivenName(com.hp.hpl.jena.rdf.model.Literal givenName) throws JastorException {
		if (this.givenName == null)
			initGivenName();
		if (this.givenName.contains(givenName))
			return;
		if (_model.contains(_resource, givenNameProperty, createLiteral(givenName)))
			return;
		this.givenName.add(givenName);
		_model.add(_resource, givenNameProperty, givenName);
	}
	
	public void removeGivenName(com.hp.hpl.jena.rdf.model.Literal givenName) throws JastorException {
		if (this.givenName == null)
			initGivenName();
		if (!this.givenName.contains(givenName))
			return;
		if (!_model.contains(_resource, givenNameProperty, createLiteral(givenName)))
			return;
		this.givenName.remove(givenName);
		_model.removeAll(_resource, givenNameProperty, createLiteral(givenName));
	}


	private void initHomepage() throws JastorException {
		this.homepage = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, homepageProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/homepage properties in Image model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				de.m0ep.uni.ma.rdfs.foaf.Document homepage = de.m0ep.uni.ma.rdfs.foaf.Factory.getDocument(resource,_model);
				this.homepage.add(homepage);
			}
		}
	}

	public java.util.Iterator getHomepage() throws JastorException {
		if (homepage == null)
			initHomepage();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(homepage,_resource,homepageProperty,true);
	}

	public void addHomepage(de.m0ep.uni.ma.rdfs.foaf.Document homepage) throws JastorException {
		if (this.homepage == null)
			initHomepage();
		if (this.homepage.contains(homepage)) {
			this.homepage.remove(homepage);
			this.homepage.add(homepage);
			return;
		}
		this.homepage.add(homepage);
		_model.add(_model.createStatement(_resource,homepageProperty,homepage.resource()));
	}
	
	public de.m0ep.uni.ma.rdfs.foaf.Document addHomepage() throws JastorException {
		de.m0ep.uni.ma.rdfs.foaf.Document homepage = de.m0ep.uni.ma.rdfs.foaf.Factory.createDocument(_model.createResource(),_model);
		if (this.homepage == null)
			initHomepage();
		this.homepage.add(homepage);
		_model.add(_model.createStatement(_resource,homepageProperty,homepage.resource()));
		return homepage;
	}
	
	public de.m0ep.uni.ma.rdfs.foaf.Document addHomepage(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		de.m0ep.uni.ma.rdfs.foaf.Document homepage = de.m0ep.uni.ma.rdfs.foaf.Factory.getDocument(resource,_model);
		if (this.homepage == null)
			initHomepage();
		if (this.homepage.contains(homepage))
			return homepage;
		this.homepage.add(homepage);
		_model.add(_model.createStatement(_resource,homepageProperty,homepage.resource()));
		return homepage;
	}
	
	public void removeHomepage(de.m0ep.uni.ma.rdfs.foaf.Document homepage) throws JastorException {
		if (this.homepage == null)
			initHomepage();
		if (!this.homepage.contains(homepage))
			return;
		if (!_model.contains(_resource, homepageProperty, homepage.resource()))
			return;
		this.homepage.remove(homepage);
		_model.removeAll(_resource, homepageProperty, homepage.resource());
	}
		 

	private void initSha1() throws JastorException {
		sha1 = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, sha1Property, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/sha1 properties in Image model not a Literal", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
			sha1.add(literal);
		}
	}

	public java.util.Iterator getSha1() throws JastorException {
		if (sha1 == null)
			initSha1();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(sha1,_resource,sha1Property,false);
	}

	public void addSha1(com.hp.hpl.jena.rdf.model.Literal sha1) throws JastorException {
		if (this.sha1 == null)
			initSha1();
		if (this.sha1.contains(sha1))
			return;
		if (_model.contains(_resource, sha1Property, createLiteral(sha1)))
			return;
		this.sha1.add(sha1);
		_model.add(_resource, sha1Property, sha1);
	}
	
	public void removeSha1(com.hp.hpl.jena.rdf.model.Literal sha1) throws JastorException {
		if (this.sha1 == null)
			initSha1();
		if (!this.sha1.contains(sha1))
			return;
		if (!_model.contains(_resource, sha1Property, createLiteral(sha1)))
			return;
		this.sha1.remove(sha1);
		_model.removeAll(_resource, sha1Property, createLiteral(sha1));
	}


	private void initTheme() throws JastorException {
		this.theme = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, themeProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/theme properties in Image model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				com.ibm.adtech.jastor.Thing theme = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
				this.theme.add(theme);
			}
		}
	}

	public java.util.Iterator getTheme() throws JastorException {
		if (theme == null)
			initTheme();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(theme,_resource,themeProperty,true);
	}

	public void addTheme(com.ibm.adtech.jastor.Thing theme) throws JastorException {
		if (this.theme == null)
			initTheme();
		if (this.theme.contains(theme)) {
			this.theme.remove(theme);
			this.theme.add(theme);
			return;
		}
		this.theme.add(theme);
		_model.add(_model.createStatement(_resource,themeProperty,theme.resource()));
	}
	
	public com.ibm.adtech.jastor.Thing addTheme() throws JastorException {
		com.ibm.adtech.jastor.Thing theme = com.ibm.adtech.jastor.ThingFactory.createThing(_model.createResource(),_model);
		if (this.theme == null)
			initTheme();
		this.theme.add(theme);
		_model.add(_model.createStatement(_resource,themeProperty,theme.resource()));
		return theme;
	}
	
	public com.ibm.adtech.jastor.Thing addTheme(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		com.ibm.adtech.jastor.Thing theme = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
		if (this.theme == null)
			initTheme();
		if (this.theme.contains(theme))
			return theme;
		this.theme.add(theme);
		_model.add(_model.createStatement(_resource,themeProperty,theme.resource()));
		return theme;
	}
	
	public void removeTheme(com.ibm.adtech.jastor.Thing theme) throws JastorException {
		if (this.theme == null)
			initTheme();
		if (!this.theme.contains(theme))
			return;
		if (!_model.contains(_resource, themeProperty, theme.resource()))
			return;
		this.theme.remove(theme);
		_model.removeAll(_resource, themeProperty, theme.resource());
	}
		 

	private void initDepicts() throws JastorException {
		this.depicts = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, depictsProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/depicts properties in Image model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				com.ibm.adtech.jastor.Thing depicts = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
				this.depicts.add(depicts);
			}
		}
	}

	public java.util.Iterator getDepicts() throws JastorException {
		if (depicts == null)
			initDepicts();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(depicts,_resource,depictsProperty,true);
	}

	public void addDepicts(com.ibm.adtech.jastor.Thing depicts) throws JastorException {
		if (this.depicts == null)
			initDepicts();
		if (this.depicts.contains(depicts)) {
			this.depicts.remove(depicts);
			this.depicts.add(depicts);
			return;
		}
		this.depicts.add(depicts);
		_model.add(_model.createStatement(_resource,depictsProperty,depicts.resource()));
	}
	
	public com.ibm.adtech.jastor.Thing addDepicts() throws JastorException {
		com.ibm.adtech.jastor.Thing depicts = com.ibm.adtech.jastor.ThingFactory.createThing(_model.createResource(),_model);
		if (this.depicts == null)
			initDepicts();
		this.depicts.add(depicts);
		_model.add(_model.createStatement(_resource,depictsProperty,depicts.resource()));
		return depicts;
	}
	
	public com.ibm.adtech.jastor.Thing addDepicts(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		com.ibm.adtech.jastor.Thing depicts = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
		if (this.depicts == null)
			initDepicts();
		if (this.depicts.contains(depicts))
			return depicts;
		this.depicts.add(depicts);
		_model.add(_model.createStatement(_resource,depictsProperty,depicts.resource()));
		return depicts;
	}
	
	public void removeDepicts(com.ibm.adtech.jastor.Thing depicts) throws JastorException {
		if (this.depicts == null)
			initDepicts();
		if (!this.depicts.contains(depicts))
			return;
		if (!_model.contains(_resource, depictsProperty, depicts.resource()))
			return;
		this.depicts.remove(depicts);
		_model.removeAll(_resource, depictsProperty, depicts.resource());
	}
		 

	private void initThumbnail() throws JastorException {
		this.thumbnail = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, thumbnailProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/thumbnail properties in Image model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				de.m0ep.uni.ma.rdfs.foaf.Image thumbnail = de.m0ep.uni.ma.rdfs.foaf.Factory.getImage(resource,_model);
				this.thumbnail.add(thumbnail);
			}
		}
	}

	public java.util.Iterator getThumbnail() throws JastorException {
		if (thumbnail == null)
			initThumbnail();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(thumbnail,_resource,thumbnailProperty,true);
	}

	public void addThumbnail(de.m0ep.uni.ma.rdfs.foaf.Image thumbnail) throws JastorException {
		if (this.thumbnail == null)
			initThumbnail();
		if (this.thumbnail.contains(thumbnail)) {
			this.thumbnail.remove(thumbnail);
			this.thumbnail.add(thumbnail);
			return;
		}
		this.thumbnail.add(thumbnail);
		_model.add(_model.createStatement(_resource,thumbnailProperty,thumbnail.resource()));
	}
	
	public de.m0ep.uni.ma.rdfs.foaf.Image addThumbnail() throws JastorException {
		de.m0ep.uni.ma.rdfs.foaf.Image thumbnail = de.m0ep.uni.ma.rdfs.foaf.Factory.createImage(_model.createResource(),_model);
		if (this.thumbnail == null)
			initThumbnail();
		this.thumbnail.add(thumbnail);
		_model.add(_model.createStatement(_resource,thumbnailProperty,thumbnail.resource()));
		return thumbnail;
	}
	
	public de.m0ep.uni.ma.rdfs.foaf.Image addThumbnail(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		de.m0ep.uni.ma.rdfs.foaf.Image thumbnail = de.m0ep.uni.ma.rdfs.foaf.Factory.getImage(resource,_model);
		if (this.thumbnail == null)
			initThumbnail();
		if (this.thumbnail.contains(thumbnail))
			return thumbnail;
		this.thumbnail.add(thumbnail);
		_model.add(_model.createStatement(_resource,thumbnailProperty,thumbnail.resource()));
		return thumbnail;
	}
	
	public void removeThumbnail(de.m0ep.uni.ma.rdfs.foaf.Image thumbnail) throws JastorException {
		if (this.thumbnail == null)
			initThumbnail();
		if (!this.thumbnail.contains(thumbnail))
			return;
		if (!_model.contains(_resource, thumbnailProperty, thumbnail.resource()))
			return;
		this.thumbnail.remove(thumbnail);
		_model.removeAll(_resource, thumbnailProperty, thumbnail.resource());
	}
		  


	private java.util.ArrayList listeners;
	
	public void registerListener(ThingListener listener) {
		if (!(listener instanceof ImageListener))
			throw new IllegalArgumentException("ThingListener must be instance of ImageListener"); 
		if (listeners == null)
			setupModelListener();
		if(!this.listeners.contains(listener)){
			this.listeners.add((ImageListener)listener);
		}
	}
	
	public void unregisterListener(ThingListener listener) {
		if (!(listener instanceof ImageListener))
			throw new IllegalArgumentException("ThingListener must be instance of ImageListener"); 
		if (listeners == null)
			return;
		if (this.listeners.contains(listener)){
			listeners.remove(listener);
		}
	}



	
		public void addedStatement(com.hp.hpl.jena.rdf.model.Statement stmt) {

			if (stmt.getPredicate().equals(fundedByProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					com.ibm.adtech.jastor.Thing _fundedBy = null;
					try {
						_fundedBy = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (fundedBy == null) {
						try {
							initFundedBy();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!fundedBy.contains(_fundedBy))
						fundedBy.add(_fundedBy);
					if (listeners != null) {
						java.util.ArrayList consumersForFundedBy;
						synchronized (listeners) {
							consumersForFundedBy = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForFundedBy.iterator();iter.hasNext();){
							ImageListener listener=(ImageListener)iter.next();
							listener.fundedByAdded(de.m0ep.uni.ma.rdfs.foaf.ImageImpl.this,_fundedBy);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(nameProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (name == null)
					try {
						initName();
					} catch (JastorException e) {
						e.printStackTrace();
						return;
					}
				if (!name.contains(literal))
					name.add(literal);
				if (listeners != null) {
					java.util.ArrayList consumersForName;
					synchronized (listeners) {
						consumersForName = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForName.iterator();iter.hasNext();){
						ImageListener listener=(ImageListener)iter.next();
						listener.nameAdded(de.m0ep.uni.ma.rdfs.foaf.ImageImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(primaryTopicProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				primaryTopic = null;
				if (true) { // don't check resource type if the property range is Resource
					try {
						primaryTopic = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
				}
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						ImageListener listener=(ImageListener)iter.next();
						listener.primaryTopicChanged(de.m0ep.uni.ma.rdfs.foaf.ImageImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(dnaChecksumProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (dnaChecksum == null)
					try {
						initDnaChecksum();
					} catch (JastorException e) {
						e.printStackTrace();
						return;
					}
				if (!dnaChecksum.contains(literal))
					dnaChecksum.add(literal);
				if (listeners != null) {
					java.util.ArrayList consumersForDnaChecksum;
					synchronized (listeners) {
						consumersForDnaChecksum = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForDnaChecksum.iterator();iter.hasNext();){
						ImageListener listener=(ImageListener)iter.next();
						listener.dnaChecksumAdded(de.m0ep.uni.ma.rdfs.foaf.ImageImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(phoneProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					com.ibm.adtech.jastor.Thing _phone = null;
					try {
						_phone = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (phone == null) {
						try {
							initPhone();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!phone.contains(_phone))
						phone.add(_phone);
					if (listeners != null) {
						java.util.ArrayList consumersForPhone;
						synchronized (listeners) {
							consumersForPhone = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForPhone.iterator();iter.hasNext();){
							ImageListener listener=(ImageListener)iter.next();
							listener.phoneAdded(de.m0ep.uni.ma.rdfs.foaf.ImageImpl.this,_phone);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(pageProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.foaf.Document _page = null;
					try {
						_page = de.m0ep.uni.ma.rdfs.foaf.Factory.getDocument(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (page == null) {
						try {
							initPage();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!page.contains(_page))
						page.add(_page);
					if (listeners != null) {
						java.util.ArrayList consumersForPage;
						synchronized (listeners) {
							consumersForPage = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForPage.iterator();iter.hasNext();){
							ImageListener listener=(ImageListener)iter.next();
							listener.pageAdded(de.m0ep.uni.ma.rdfs.foaf.ImageImpl.this,_page);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(nickProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (nick == null)
					try {
						initNick();
					} catch (JastorException e) {
						e.printStackTrace();
						return;
					}
				if (!nick.contains(literal))
					nick.add(literal);
				if (listeners != null) {
					java.util.ArrayList consumersForNick;
					synchronized (listeners) {
						consumersForNick = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForNick.iterator();iter.hasNext();){
						ImageListener listener=(ImageListener)iter.next();
						listener.nickAdded(de.m0ep.uni.ma.rdfs.foaf.ImageImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(logoProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					com.ibm.adtech.jastor.Thing _logo = null;
					try {
						_logo = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (logo == null) {
						try {
							initLogo();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!logo.contains(_logo))
						logo.add(_logo);
					if (listeners != null) {
						java.util.ArrayList consumersForLogo;
						synchronized (listeners) {
							consumersForLogo = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForLogo.iterator();iter.hasNext();){
							ImageListener listener=(ImageListener)iter.next();
							listener.logoAdded(de.m0ep.uni.ma.rdfs.foaf.ImageImpl.this,_logo);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(makerProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.foaf.Agent _maker = null;
					try {
						_maker = de.m0ep.uni.ma.rdfs.foaf.Factory.getAgent(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (maker == null) {
						try {
							initMaker();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!maker.contains(_maker))
						maker.add(_maker);
					if (listeners != null) {
						java.util.ArrayList consumersForMaker;
						synchronized (listeners) {
							consumersForMaker = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForMaker.iterator();iter.hasNext();){
							ImageListener listener=(ImageListener)iter.next();
							listener.makerAdded(de.m0ep.uni.ma.rdfs.foaf.ImageImpl.this,_maker);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(givennameProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (givenname == null)
					try {
						initGivenname();
					} catch (JastorException e) {
						e.printStackTrace();
						return;
					}
				if (!givenname.contains(literal))
					givenname.add(literal);
				if (listeners != null) {
					java.util.ArrayList consumersForGivenname;
					synchronized (listeners) {
						consumersForGivenname = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForGivenname.iterator();iter.hasNext();){
						ImageListener listener=(ImageListener)iter.next();
						listener.givennameAdded(de.m0ep.uni.ma.rdfs.foaf.ImageImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(topicProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					com.ibm.adtech.jastor.Thing _topic = null;
					try {
						_topic = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (topic == null) {
						try {
							initTopic();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!topic.contains(_topic))
						topic.add(_topic);
					if (listeners != null) {
						java.util.ArrayList consumersForTopic;
						synchronized (listeners) {
							consumersForTopic = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForTopic.iterator();iter.hasNext();){
							ImageListener listener=(ImageListener)iter.next();
							listener.topicAdded(de.m0ep.uni.ma.rdfs.foaf.ImageImpl.this,_topic);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(titleProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (title == null)
					try {
						initTitle();
					} catch (JastorException e) {
						e.printStackTrace();
						return;
					}
				if (!title.contains(literal))
					title.add(literal);
				if (listeners != null) {
					java.util.ArrayList consumersForTitle;
					synchronized (listeners) {
						consumersForTitle = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForTitle.iterator();iter.hasNext();){
						ImageListener listener=(ImageListener)iter.next();
						listener.titleAdded(de.m0ep.uni.ma.rdfs.foaf.ImageImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(givenNameProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (givenName == null)
					try {
						initGivenName();
					} catch (JastorException e) {
						e.printStackTrace();
						return;
					}
				if (!givenName.contains(literal))
					givenName.add(literal);
				if (listeners != null) {
					java.util.ArrayList consumersForGivenName;
					synchronized (listeners) {
						consumersForGivenName = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForGivenName.iterator();iter.hasNext();){
						ImageListener listener=(ImageListener)iter.next();
						listener.givenNameAdded(de.m0ep.uni.ma.rdfs.foaf.ImageImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(homepageProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.foaf.Document _homepage = null;
					try {
						_homepage = de.m0ep.uni.ma.rdfs.foaf.Factory.getDocument(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (homepage == null) {
						try {
							initHomepage();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!homepage.contains(_homepage))
						homepage.add(_homepage);
					if (listeners != null) {
						java.util.ArrayList consumersForHomepage;
						synchronized (listeners) {
							consumersForHomepage = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForHomepage.iterator();iter.hasNext();){
							ImageListener listener=(ImageListener)iter.next();
							listener.homepageAdded(de.m0ep.uni.ma.rdfs.foaf.ImageImpl.this,_homepage);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(sha1Property)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (sha1 == null)
					try {
						initSha1();
					} catch (JastorException e) {
						e.printStackTrace();
						return;
					}
				if (!sha1.contains(literal))
					sha1.add(literal);
				if (listeners != null) {
					java.util.ArrayList consumersForSha1;
					synchronized (listeners) {
						consumersForSha1 = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForSha1.iterator();iter.hasNext();){
						ImageListener listener=(ImageListener)iter.next();
						listener.sha1Added(de.m0ep.uni.ma.rdfs.foaf.ImageImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(themeProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					com.ibm.adtech.jastor.Thing _theme = null;
					try {
						_theme = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (theme == null) {
						try {
							initTheme();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!theme.contains(_theme))
						theme.add(_theme);
					if (listeners != null) {
						java.util.ArrayList consumersForTheme;
						synchronized (listeners) {
							consumersForTheme = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForTheme.iterator();iter.hasNext();){
							ImageListener listener=(ImageListener)iter.next();
							listener.themeAdded(de.m0ep.uni.ma.rdfs.foaf.ImageImpl.this,_theme);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(depictsProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					com.ibm.adtech.jastor.Thing _depicts = null;
					try {
						_depicts = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (depicts == null) {
						try {
							initDepicts();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!depicts.contains(_depicts))
						depicts.add(_depicts);
					if (listeners != null) {
						java.util.ArrayList consumersForDepicts;
						synchronized (listeners) {
							consumersForDepicts = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForDepicts.iterator();iter.hasNext();){
							ImageListener listener=(ImageListener)iter.next();
							listener.depictsAdded(de.m0ep.uni.ma.rdfs.foaf.ImageImpl.this,_depicts);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(thumbnailProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.foaf.Image _thumbnail = null;
					try {
						_thumbnail = de.m0ep.uni.ma.rdfs.foaf.Factory.getImage(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (thumbnail == null) {
						try {
							initThumbnail();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!thumbnail.contains(_thumbnail))
						thumbnail.add(_thumbnail);
					if (listeners != null) {
						java.util.ArrayList consumersForThumbnail;
						synchronized (listeners) {
							consumersForThumbnail = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForThumbnail.iterator();iter.hasNext();){
							ImageListener listener=(ImageListener)iter.next();
							listener.thumbnailAdded(de.m0ep.uni.ma.rdfs.foaf.ImageImpl.this,_thumbnail);
						}
					}
				}
				return;
			}
		}
		
		public void removedStatement(com.hp.hpl.jena.rdf.model.Statement stmt) {
//			if (!stmt.getSubject().equals(_resource))
//				return;
			if (stmt.getPredicate().equals(fundedByProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					com.ibm.adtech.jastor.Thing _fundedBy = null;
					if (fundedBy != null) {
						boolean found = false;
						for (int i=0;i<fundedBy.size();i++) {
							com.ibm.adtech.jastor.Thing __item = (com.ibm.adtech.jastor.Thing) fundedBy.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_fundedBy = __item;
								break;
							}
						}
						if (found)
							fundedBy.remove(_fundedBy);
						else {
							try {
								_fundedBy = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_fundedBy = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForFundedBy;
						synchronized (listeners) {
							consumersForFundedBy = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForFundedBy.iterator();iter.hasNext();){
							ImageListener listener=(ImageListener)iter.next();
							listener.fundedByRemoved(de.m0ep.uni.ma.rdfs.foaf.ImageImpl.this,_fundedBy);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(nameProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (name != null) {
					if (name.contains(literal))
						name.remove(literal);
				}
				if (listeners != null) {
					java.util.ArrayList consumersForName;
					synchronized (listeners) {
						consumersForName = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForName.iterator();iter.hasNext();){
						ImageListener listener=(ImageListener)iter.next();
						listener.nameRemoved(de.m0ep.uni.ma.rdfs.foaf.ImageImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(primaryTopicProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
					if (primaryTopic != null && primaryTopic.resource().equals(resource))
						primaryTopic = null;				
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						ImageListener listener=(ImageListener)iter.next();
						listener.primaryTopicChanged(de.m0ep.uni.ma.rdfs.foaf.ImageImpl.this);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(dnaChecksumProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (dnaChecksum != null) {
					if (dnaChecksum.contains(literal))
						dnaChecksum.remove(literal);
				}
				if (listeners != null) {
					java.util.ArrayList consumersForDnaChecksum;
					synchronized (listeners) {
						consumersForDnaChecksum = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForDnaChecksum.iterator();iter.hasNext();){
						ImageListener listener=(ImageListener)iter.next();
						listener.dnaChecksumRemoved(de.m0ep.uni.ma.rdfs.foaf.ImageImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(phoneProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					com.ibm.adtech.jastor.Thing _phone = null;
					if (phone != null) {
						boolean found = false;
						for (int i=0;i<phone.size();i++) {
							com.ibm.adtech.jastor.Thing __item = (com.ibm.adtech.jastor.Thing) phone.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_phone = __item;
								break;
							}
						}
						if (found)
							phone.remove(_phone);
						else {
							try {
								_phone = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_phone = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForPhone;
						synchronized (listeners) {
							consumersForPhone = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForPhone.iterator();iter.hasNext();){
							ImageListener listener=(ImageListener)iter.next();
							listener.phoneRemoved(de.m0ep.uni.ma.rdfs.foaf.ImageImpl.this,_phone);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(pageProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.foaf.Document _page = null;
					if (page != null) {
						boolean found = false;
						for (int i=0;i<page.size();i++) {
							de.m0ep.uni.ma.rdfs.foaf.Document __item = (de.m0ep.uni.ma.rdfs.foaf.Document) page.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_page = __item;
								break;
							}
						}
						if (found)
							page.remove(_page);
						else {
							try {
								_page = de.m0ep.uni.ma.rdfs.foaf.Factory.getDocument(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_page = de.m0ep.uni.ma.rdfs.foaf.Factory.getDocument(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForPage;
						synchronized (listeners) {
							consumersForPage = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForPage.iterator();iter.hasNext();){
							ImageListener listener=(ImageListener)iter.next();
							listener.pageRemoved(de.m0ep.uni.ma.rdfs.foaf.ImageImpl.this,_page);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(nickProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (nick != null) {
					if (nick.contains(literal))
						nick.remove(literal);
				}
				if (listeners != null) {
					java.util.ArrayList consumersForNick;
					synchronized (listeners) {
						consumersForNick = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForNick.iterator();iter.hasNext();){
						ImageListener listener=(ImageListener)iter.next();
						listener.nickRemoved(de.m0ep.uni.ma.rdfs.foaf.ImageImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(logoProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					com.ibm.adtech.jastor.Thing _logo = null;
					if (logo != null) {
						boolean found = false;
						for (int i=0;i<logo.size();i++) {
							com.ibm.adtech.jastor.Thing __item = (com.ibm.adtech.jastor.Thing) logo.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_logo = __item;
								break;
							}
						}
						if (found)
							logo.remove(_logo);
						else {
							try {
								_logo = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_logo = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForLogo;
						synchronized (listeners) {
							consumersForLogo = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForLogo.iterator();iter.hasNext();){
							ImageListener listener=(ImageListener)iter.next();
							listener.logoRemoved(de.m0ep.uni.ma.rdfs.foaf.ImageImpl.this,_logo);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(makerProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.foaf.Agent _maker = null;
					if (maker != null) {
						boolean found = false;
						for (int i=0;i<maker.size();i++) {
							de.m0ep.uni.ma.rdfs.foaf.Agent __item = (de.m0ep.uni.ma.rdfs.foaf.Agent) maker.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_maker = __item;
								break;
							}
						}
						if (found)
							maker.remove(_maker);
						else {
							try {
								_maker = de.m0ep.uni.ma.rdfs.foaf.Factory.getAgent(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_maker = de.m0ep.uni.ma.rdfs.foaf.Factory.getAgent(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForMaker;
						synchronized (listeners) {
							consumersForMaker = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForMaker.iterator();iter.hasNext();){
							ImageListener listener=(ImageListener)iter.next();
							listener.makerRemoved(de.m0ep.uni.ma.rdfs.foaf.ImageImpl.this,_maker);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(givennameProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (givenname != null) {
					if (givenname.contains(literal))
						givenname.remove(literal);
				}
				if (listeners != null) {
					java.util.ArrayList consumersForGivenname;
					synchronized (listeners) {
						consumersForGivenname = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForGivenname.iterator();iter.hasNext();){
						ImageListener listener=(ImageListener)iter.next();
						listener.givennameRemoved(de.m0ep.uni.ma.rdfs.foaf.ImageImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(topicProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					com.ibm.adtech.jastor.Thing _topic = null;
					if (topic != null) {
						boolean found = false;
						for (int i=0;i<topic.size();i++) {
							com.ibm.adtech.jastor.Thing __item = (com.ibm.adtech.jastor.Thing) topic.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_topic = __item;
								break;
							}
						}
						if (found)
							topic.remove(_topic);
						else {
							try {
								_topic = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_topic = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForTopic;
						synchronized (listeners) {
							consumersForTopic = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForTopic.iterator();iter.hasNext();){
							ImageListener listener=(ImageListener)iter.next();
							listener.topicRemoved(de.m0ep.uni.ma.rdfs.foaf.ImageImpl.this,_topic);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(titleProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (title != null) {
					if (title.contains(literal))
						title.remove(literal);
				}
				if (listeners != null) {
					java.util.ArrayList consumersForTitle;
					synchronized (listeners) {
						consumersForTitle = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForTitle.iterator();iter.hasNext();){
						ImageListener listener=(ImageListener)iter.next();
						listener.titleRemoved(de.m0ep.uni.ma.rdfs.foaf.ImageImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(givenNameProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (givenName != null) {
					if (givenName.contains(literal))
						givenName.remove(literal);
				}
				if (listeners != null) {
					java.util.ArrayList consumersForGivenName;
					synchronized (listeners) {
						consumersForGivenName = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForGivenName.iterator();iter.hasNext();){
						ImageListener listener=(ImageListener)iter.next();
						listener.givenNameRemoved(de.m0ep.uni.ma.rdfs.foaf.ImageImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(homepageProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.foaf.Document _homepage = null;
					if (homepage != null) {
						boolean found = false;
						for (int i=0;i<homepage.size();i++) {
							de.m0ep.uni.ma.rdfs.foaf.Document __item = (de.m0ep.uni.ma.rdfs.foaf.Document) homepage.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_homepage = __item;
								break;
							}
						}
						if (found)
							homepage.remove(_homepage);
						else {
							try {
								_homepage = de.m0ep.uni.ma.rdfs.foaf.Factory.getDocument(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_homepage = de.m0ep.uni.ma.rdfs.foaf.Factory.getDocument(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForHomepage;
						synchronized (listeners) {
							consumersForHomepage = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForHomepage.iterator();iter.hasNext();){
							ImageListener listener=(ImageListener)iter.next();
							listener.homepageRemoved(de.m0ep.uni.ma.rdfs.foaf.ImageImpl.this,_homepage);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(sha1Property)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (sha1 != null) {
					if (sha1.contains(literal))
						sha1.remove(literal);
				}
				if (listeners != null) {
					java.util.ArrayList consumersForSha1;
					synchronized (listeners) {
						consumersForSha1 = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForSha1.iterator();iter.hasNext();){
						ImageListener listener=(ImageListener)iter.next();
						listener.sha1Removed(de.m0ep.uni.ma.rdfs.foaf.ImageImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(themeProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					com.ibm.adtech.jastor.Thing _theme = null;
					if (theme != null) {
						boolean found = false;
						for (int i=0;i<theme.size();i++) {
							com.ibm.adtech.jastor.Thing __item = (com.ibm.adtech.jastor.Thing) theme.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_theme = __item;
								break;
							}
						}
						if (found)
							theme.remove(_theme);
						else {
							try {
								_theme = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_theme = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForTheme;
						synchronized (listeners) {
							consumersForTheme = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForTheme.iterator();iter.hasNext();){
							ImageListener listener=(ImageListener)iter.next();
							listener.themeRemoved(de.m0ep.uni.ma.rdfs.foaf.ImageImpl.this,_theme);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(depictsProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					com.ibm.adtech.jastor.Thing _depicts = null;
					if (depicts != null) {
						boolean found = false;
						for (int i=0;i<depicts.size();i++) {
							com.ibm.adtech.jastor.Thing __item = (com.ibm.adtech.jastor.Thing) depicts.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_depicts = __item;
								break;
							}
						}
						if (found)
							depicts.remove(_depicts);
						else {
							try {
								_depicts = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_depicts = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForDepicts;
						synchronized (listeners) {
							consumersForDepicts = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForDepicts.iterator();iter.hasNext();){
							ImageListener listener=(ImageListener)iter.next();
							listener.depictsRemoved(de.m0ep.uni.ma.rdfs.foaf.ImageImpl.this,_depicts);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(thumbnailProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.foaf.Image _thumbnail = null;
					if (thumbnail != null) {
						boolean found = false;
						for (int i=0;i<thumbnail.size();i++) {
							de.m0ep.uni.ma.rdfs.foaf.Image __item = (de.m0ep.uni.ma.rdfs.foaf.Image) thumbnail.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_thumbnail = __item;
								break;
							}
						}
						if (found)
							thumbnail.remove(_thumbnail);
						else {
							try {
								_thumbnail = de.m0ep.uni.ma.rdfs.foaf.Factory.getImage(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_thumbnail = de.m0ep.uni.ma.rdfs.foaf.Factory.getImage(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForThumbnail;
						synchronized (listeners) {
							consumersForThumbnail = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForThumbnail.iterator();iter.hasNext();){
							ImageListener listener=(ImageListener)iter.next();
							listener.thumbnailRemoved(de.m0ep.uni.ma.rdfs.foaf.ImageImpl.this,_thumbnail);
						}
					}
				}
				return;
			}
		}

	//}
	


}