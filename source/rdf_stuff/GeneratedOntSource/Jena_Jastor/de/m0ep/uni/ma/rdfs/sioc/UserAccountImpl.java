

package de.m0ep.uni.ma.rdfs.sioc;

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
 * Implementation of {@link de.m0ep.uni.ma.rdfs.sioc.UserAccount}
 * Use the de.m0ep.uni.ma.rdfs.sioc.Factory to create instances of this class.
 * <p>(URI: http://rdfs.org/sioc/ns#UserAccount)</p>
 * <br>
 */
public class UserAccountImpl extends com.ibm.adtech.jastor.ThingImpl implements de.m0ep.uni.ma.rdfs.sioc.UserAccount {
	

	private static com.hp.hpl.jena.rdf.model.Property fundedByProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/fundedBy");
	private java.util.ArrayList fundedBy;
	private static com.hp.hpl.jena.rdf.model.Property nameProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/name");
	private java.util.ArrayList name;
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
	private static com.hp.hpl.jena.rdf.model.Property titleProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/title");
	private java.util.ArrayList title;
	private static com.hp.hpl.jena.rdf.model.Property givenNameProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/givenName");
	private java.util.ArrayList givenName;
	private static com.hp.hpl.jena.rdf.model.Property homepageProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/homepage");
	private java.util.ArrayList homepage;
	private static com.hp.hpl.jena.rdf.model.Property accountServiceHomepageProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/accountServiceHomepage");
	private java.util.ArrayList accountServiceHomepage;
	private static com.hp.hpl.jena.rdf.model.Property themeProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/theme");
	private java.util.ArrayList theme;
	private static com.hp.hpl.jena.rdf.model.Property accountNameProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/accountName");
	private java.util.ArrayList accountName;
	private static com.hp.hpl.jena.rdf.model.Property email__sha1Property = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#email_sha1");
	private java.util.ArrayList email__sha1;
	private static com.hp.hpl.jena.rdf.model.Property member__ofProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#member_of");
	private java.util.ArrayList member__of;
	private static com.hp.hpl.jena.rdf.model.Property creator__ofProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#creator_of");
	private java.util.ArrayList creator__of;
	private static com.hp.hpl.jena.rdf.model.Property moderator__ofProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#moderator_of");
	private java.util.ArrayList moderator__of;
	private static com.hp.hpl.jena.rdf.model.Property followsProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#follows");
	private java.util.ArrayList follows;
	private static com.hp.hpl.jena.rdf.model.Property account__ofProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#account_of");
	private java.util.ArrayList account__of;
	private static com.hp.hpl.jena.rdf.model.Property owner__ofProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#owner_of");
	private java.util.ArrayList owner__of;
	private static com.hp.hpl.jena.rdf.model.Property emailProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#email");
	private java.util.ArrayList email;
	private static com.hp.hpl.jena.rdf.model.Property avatarProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#avatar");
	private java.util.ArrayList avatar;
	private static com.hp.hpl.jena.rdf.model.Property administrator__ofProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#administrator_of");
	private java.util.ArrayList administrator__of;
	private static com.hp.hpl.jena.rdf.model.Property modifier__ofProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#modifier_of");
	private java.util.ArrayList modifier__of;
	private static com.hp.hpl.jena.rdf.model.Property subscriber__ofProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#subscriber_of");
	private java.util.ArrayList subscriber__of;
	private static com.hp.hpl.jena.rdf.model.Property depictionProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/depiction");
	private java.util.ArrayList depiction;
	private static com.hp.hpl.jena.rdf.model.Property idProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#id");
	private java.util.ArrayList id;
	private static com.hp.hpl.jena.rdf.model.Property num__authorsProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#num_authors");
	private java.util.ArrayList num__authors;
	private static com.hp.hpl.jena.rdf.model.Property noteProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#note");
	private java.util.ArrayList note;
	private static com.hp.hpl.jena.rdf.model.Property ns1_nameProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#name");
	private java.util.ArrayList ns1_name;
	private static com.hp.hpl.jena.rdf.model.Property num__viewsProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#num_views");
	private java.util.ArrayList num__views;
	private static com.hp.hpl.jena.rdf.model.Property descriptionProperty = ResourceFactory.createProperty("http://purl.org/dc/terms/description");
	private java.util.ArrayList description;
	private static com.hp.hpl.jena.rdf.model.Property dateProperty = ResourceFactory.createProperty("http://purl.org/dc/terms/date");
	private java.util.ArrayList date;
	private static com.hp.hpl.jena.rdf.model.Property num__repliesProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#num_replies");
	private java.util.ArrayList num__replies;
	private static com.hp.hpl.jena.rdf.model.Property last__activity__dateProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#last_activity_date");
	private java.util.ArrayList last__activity__date;
	private static com.hp.hpl.jena.rdf.model.Property last__reply__dateProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#last_reply_date");
	private java.util.ArrayList last__reply__date;
	private static com.hp.hpl.jena.rdf.model.Property ns2_titleProperty = ResourceFactory.createProperty("http://purl.org/dc/terms/title");
	private java.util.ArrayList ns2_title;
	private static com.hp.hpl.jena.rdf.model.Property subjectProperty = ResourceFactory.createProperty("http://purl.org/dc/terms/subject");
	private java.util.ArrayList subject;
	private static com.hp.hpl.jena.rdf.model.Property referencesProperty = ResourceFactory.createProperty("http://purl.org/dc/terms/references");
	private java.util.ArrayList references;
	private static com.hp.hpl.jena.rdf.model.Property links__toProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#links_to");
	private java.util.ArrayList links__to;
	private static com.hp.hpl.jena.rdf.model.Property feedProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#feed");
	private java.util.ArrayList feed;
	private static com.hp.hpl.jena.rdf.model.Property has__spaceProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#has_space");
	private java.util.ArrayList has__space;
	private static com.hp.hpl.jena.rdf.model.Property scope__ofProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#scope_of");
	private java.util.ArrayList scope__of;
	private static com.hp.hpl.jena.rdf.model.Property topicProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#topic");
	private java.util.ArrayList topic;
	private static com.hp.hpl.jena.rdf.model.Property linkProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#link");
	private java.util.ArrayList link;
	private static com.hp.hpl.jena.rdf.model.Property has__creatorProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#has_creator");
	private java.util.ArrayList has__creator;
	private static com.hp.hpl.jena.rdf.model.Property has__ownerProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#has_owner");
	private java.util.ArrayList has__owner;
	private static com.hp.hpl.jena.rdf.model.Property has__functionProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#has_function");
	private java.util.ArrayList has__function;
	private static com.hp.hpl.jena.rdf.model.Property related__toProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#related_to");
	private java.util.ArrayList related__to;
 

	UserAccountImpl(Resource resource, Model model) throws JastorException {
		super(resource, model);
		setupModelListener();
	}     
    	
	static UserAccountImpl getUserAccount(Resource resource, Model model) throws JastorException {
		return new UserAccountImpl(resource, model);
	}
	    
	static UserAccountImpl createUserAccount(Resource resource, Model model) throws JastorException { 
		UserAccountImpl impl = new UserAccountImpl(resource, model);
		
		if (!impl._model.contains(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(impl._resource, RDF.type, UserAccount.TYPE)))
			impl._model.add(impl._resource, RDF.type, UserAccount.TYPE);
		impl.addSuperTypes();
		impl.addHasValueValues();
		return impl;
	}
	
	void addSuperTypes() {
		if (!_model.contains(_resource, RDF.type, de.m0ep.uni.ma.rdfs.foaf.OnlineAccount.TYPE))
			_model.add(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(_resource, RDF.type, de.m0ep.uni.ma.rdfs.foaf.OnlineAccount.TYPE));     
	}
   
	void addHasValueValues() {
	}
    
    private void setupModelListener() {
    	listeners = new java.util.ArrayList();
    	de.m0ep.uni.ma.rdfs.sioc.Factory.registerThing(this);
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
		it = _model.listStatements(_resource,accountServiceHomepageProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,themeProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,accountNameProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,email__sha1Property,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,member__ofProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,creator__ofProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,moderator__ofProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,followsProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,account__ofProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,owner__ofProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,emailProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,avatarProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,administrator__ofProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,modifier__ofProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,subscriber__ofProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,depictionProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,idProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,num__authorsProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,noteProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,ns1_nameProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,num__viewsProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,descriptionProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,dateProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,num__repliesProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,last__activity__dateProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,last__reply__dateProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,ns2_titleProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,subjectProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,referencesProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,links__toProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,feedProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,has__spaceProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,scope__ofProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,topicProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,linkProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,has__creatorProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,has__ownerProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,has__functionProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,related__toProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, de.m0ep.uni.ma.rdfs.sioc.UserAccount.TYPE);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, de.m0ep.uni.ma.rdfs.foaf.OnlineAccount.TYPE);
		while (it.hasNext()) {
			list.add(it.next());
		}
		return list;
	}
	
	public void clearCache() {
		fundedBy = null;
		name = null;
		dnaChecksum = null;
		phone = null;
		page = null;
		nick = null;
		logo = null;
		maker = null;
		givenname = null;
		title = null;
		givenName = null;
		homepage = null;
		accountServiceHomepage = null;
		theme = null;
		accountName = null;
		email__sha1 = null;
		member__of = null;
		creator__of = null;
		moderator__of = null;
		follows = null;
		account__of = null;
		owner__of = null;
		email = null;
		avatar = null;
		administrator__of = null;
		modifier__of = null;
		subscriber__of = null;
		depiction = null;
		id = null;
		num__authors = null;
		note = null;
		ns1_name = null;
		num__views = null;
		description = null;
		date = null;
		num__replies = null;
		last__activity__date = null;
		last__reply__date = null;
		ns2_title = null;
		subject = null;
		references = null;
		links__to = null;
		feed = null;
		has__space = null;
		scope__of = null;
		topic = null;
		link = null;
		has__creator = null;
		has__owner = null;
		has__function = null;
		related__to = null;
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/fundedBy properties in UserAccount model not a Resource", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/name properties in UserAccount model not a Literal", stmt.getObject());
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


	private void initDnaChecksum() throws JastorException {
		dnaChecksum = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, dnaChecksumProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/dnaChecksum properties in UserAccount model not a Literal", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/phone properties in UserAccount model not a Resource", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/page properties in UserAccount model not a Resource", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/nick properties in UserAccount model not a Literal", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/logo properties in UserAccount model not a Resource", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/maker properties in UserAccount model not a Resource", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/givenname properties in UserAccount model not a Literal", stmt.getObject());
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


	private void initTitle() throws JastorException {
		title = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, titleProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/title properties in UserAccount model not a Literal", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/givenName properties in UserAccount model not a Literal", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/homepage properties in UserAccount model not a Resource", stmt.getObject());
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
		 

	private void initAccountServiceHomepage() throws JastorException {
		this.accountServiceHomepage = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, accountServiceHomepageProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/accountServiceHomepage properties in UserAccount model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				de.m0ep.uni.ma.rdfs.foaf.Document accountServiceHomepage = de.m0ep.uni.ma.rdfs.foaf.Factory.getDocument(resource,_model);
				this.accountServiceHomepage.add(accountServiceHomepage);
			}
		}
	}

	public java.util.Iterator getAccountServiceHomepage() throws JastorException {
		if (accountServiceHomepage == null)
			initAccountServiceHomepage();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(accountServiceHomepage,_resource,accountServiceHomepageProperty,true);
	}

	public void addAccountServiceHomepage(de.m0ep.uni.ma.rdfs.foaf.Document accountServiceHomepage) throws JastorException {
		if (this.accountServiceHomepage == null)
			initAccountServiceHomepage();
		if (this.accountServiceHomepage.contains(accountServiceHomepage)) {
			this.accountServiceHomepage.remove(accountServiceHomepage);
			this.accountServiceHomepage.add(accountServiceHomepage);
			return;
		}
		this.accountServiceHomepage.add(accountServiceHomepage);
		_model.add(_model.createStatement(_resource,accountServiceHomepageProperty,accountServiceHomepage.resource()));
	}
	
	public de.m0ep.uni.ma.rdfs.foaf.Document addAccountServiceHomepage() throws JastorException {
		de.m0ep.uni.ma.rdfs.foaf.Document accountServiceHomepage = de.m0ep.uni.ma.rdfs.foaf.Factory.createDocument(_model.createResource(),_model);
		if (this.accountServiceHomepage == null)
			initAccountServiceHomepage();
		this.accountServiceHomepage.add(accountServiceHomepage);
		_model.add(_model.createStatement(_resource,accountServiceHomepageProperty,accountServiceHomepage.resource()));
		return accountServiceHomepage;
	}
	
	public de.m0ep.uni.ma.rdfs.foaf.Document addAccountServiceHomepage(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		de.m0ep.uni.ma.rdfs.foaf.Document accountServiceHomepage = de.m0ep.uni.ma.rdfs.foaf.Factory.getDocument(resource,_model);
		if (this.accountServiceHomepage == null)
			initAccountServiceHomepage();
		if (this.accountServiceHomepage.contains(accountServiceHomepage))
			return accountServiceHomepage;
		this.accountServiceHomepage.add(accountServiceHomepage);
		_model.add(_model.createStatement(_resource,accountServiceHomepageProperty,accountServiceHomepage.resource()));
		return accountServiceHomepage;
	}
	
	public void removeAccountServiceHomepage(de.m0ep.uni.ma.rdfs.foaf.Document accountServiceHomepage) throws JastorException {
		if (this.accountServiceHomepage == null)
			initAccountServiceHomepage();
		if (!this.accountServiceHomepage.contains(accountServiceHomepage))
			return;
		if (!_model.contains(_resource, accountServiceHomepageProperty, accountServiceHomepage.resource()))
			return;
		this.accountServiceHomepage.remove(accountServiceHomepage);
		_model.removeAll(_resource, accountServiceHomepageProperty, accountServiceHomepage.resource());
	}
		 

	private void initTheme() throws JastorException {
		this.theme = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, themeProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/theme properties in UserAccount model not a Resource", stmt.getObject());
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
		 

	private void initAccountName() throws JastorException {
		accountName = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, accountNameProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/accountName properties in UserAccount model not a Literal", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
			accountName.add(literal);
		}
	}

	public java.util.Iterator getAccountName() throws JastorException {
		if (accountName == null)
			initAccountName();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(accountName,_resource,accountNameProperty,false);
	}

	public void addAccountName(com.hp.hpl.jena.rdf.model.Literal accountName) throws JastorException {
		if (this.accountName == null)
			initAccountName();
		if (this.accountName.contains(accountName))
			return;
		if (_model.contains(_resource, accountNameProperty, createLiteral(accountName)))
			return;
		this.accountName.add(accountName);
		_model.add(_resource, accountNameProperty, accountName);
	}
	
	public void removeAccountName(com.hp.hpl.jena.rdf.model.Literal accountName) throws JastorException {
		if (this.accountName == null)
			initAccountName();
		if (!this.accountName.contains(accountName))
			return;
		if (!_model.contains(_resource, accountNameProperty, createLiteral(accountName)))
			return;
		this.accountName.remove(accountName);
		_model.removeAll(_resource, accountNameProperty, createLiteral(accountName));
	}


	private void initEmail__sha1() throws JastorException {
		email__sha1 = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, email__sha1Property, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#email_sha1 properties in UserAccount model not a Literal", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
			email__sha1.add(literal);
		}
	}

	public java.util.Iterator getEmail__sha1() throws JastorException {
		if (email__sha1 == null)
			initEmail__sha1();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(email__sha1,_resource,email__sha1Property,false);
	}

	public void addEmail__sha1(com.hp.hpl.jena.rdf.model.Literal email__sha1) throws JastorException {
		if (this.email__sha1 == null)
			initEmail__sha1();
		if (this.email__sha1.contains(email__sha1))
			return;
		if (_model.contains(_resource, email__sha1Property, createLiteral(email__sha1)))
			return;
		this.email__sha1.add(email__sha1);
		_model.add(_resource, email__sha1Property, email__sha1);
	}
	
	public void removeEmail__sha1(com.hp.hpl.jena.rdf.model.Literal email__sha1) throws JastorException {
		if (this.email__sha1 == null)
			initEmail__sha1();
		if (!this.email__sha1.contains(email__sha1))
			return;
		if (!_model.contains(_resource, email__sha1Property, createLiteral(email__sha1)))
			return;
		this.email__sha1.remove(email__sha1);
		_model.removeAll(_resource, email__sha1Property, createLiteral(email__sha1));
	}


	private void initMember__of() throws JastorException {
		this.member__of = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, member__ofProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#member_of properties in UserAccount model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				de.m0ep.uni.ma.rdfs.sioc.Usergroup member__of = de.m0ep.uni.ma.rdfs.sioc.Factory.getUsergroup(resource,_model);
				this.member__of.add(member__of);
			}
		}
	}

	public java.util.Iterator getMember__of() throws JastorException {
		if (member__of == null)
			initMember__of();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(member__of,_resource,member__ofProperty,true);
	}

	public void addMember__of(de.m0ep.uni.ma.rdfs.sioc.Usergroup member__of) throws JastorException {
		if (this.member__of == null)
			initMember__of();
		if (this.member__of.contains(member__of)) {
			this.member__of.remove(member__of);
			this.member__of.add(member__of);
			return;
		}
		this.member__of.add(member__of);
		_model.add(_model.createStatement(_resource,member__ofProperty,member__of.resource()));
	}
	
	public de.m0ep.uni.ma.rdfs.sioc.Usergroup addMember__of() throws JastorException {
		de.m0ep.uni.ma.rdfs.sioc.Usergroup member__of = de.m0ep.uni.ma.rdfs.sioc.Factory.createUsergroup(_model.createResource(),_model);
		if (this.member__of == null)
			initMember__of();
		this.member__of.add(member__of);
		_model.add(_model.createStatement(_resource,member__ofProperty,member__of.resource()));
		return member__of;
	}
	
	public de.m0ep.uni.ma.rdfs.sioc.Usergroup addMember__of(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		de.m0ep.uni.ma.rdfs.sioc.Usergroup member__of = de.m0ep.uni.ma.rdfs.sioc.Factory.getUsergroup(resource,_model);
		if (this.member__of == null)
			initMember__of();
		if (this.member__of.contains(member__of))
			return member__of;
		this.member__of.add(member__of);
		_model.add(_model.createStatement(_resource,member__ofProperty,member__of.resource()));
		return member__of;
	}
	
	public void removeMember__of(de.m0ep.uni.ma.rdfs.sioc.Usergroup member__of) throws JastorException {
		if (this.member__of == null)
			initMember__of();
		if (!this.member__of.contains(member__of))
			return;
		if (!_model.contains(_resource, member__ofProperty, member__of.resource()))
			return;
		this.member__of.remove(member__of);
		_model.removeAll(_resource, member__ofProperty, member__of.resource());
	}
		 

	private void initCreator__of() throws JastorException {
		this.creator__of = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, creator__ofProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#creator_of properties in UserAccount model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				com.ibm.adtech.jastor.Thing creator__of = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
				this.creator__of.add(creator__of);
			}
		}
	}

	public java.util.Iterator getCreator__of() throws JastorException {
		if (creator__of == null)
			initCreator__of();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(creator__of,_resource,creator__ofProperty,true);
	}

	public void addCreator__of(com.ibm.adtech.jastor.Thing creator__of) throws JastorException {
		if (this.creator__of == null)
			initCreator__of();
		if (this.creator__of.contains(creator__of)) {
			this.creator__of.remove(creator__of);
			this.creator__of.add(creator__of);
			return;
		}
		this.creator__of.add(creator__of);
		_model.add(_model.createStatement(_resource,creator__ofProperty,creator__of.resource()));
	}
	
	public com.ibm.adtech.jastor.Thing addCreator__of() throws JastorException {
		com.ibm.adtech.jastor.Thing creator__of = com.ibm.adtech.jastor.ThingFactory.createThing(_model.createResource(),_model);
		if (this.creator__of == null)
			initCreator__of();
		this.creator__of.add(creator__of);
		_model.add(_model.createStatement(_resource,creator__ofProperty,creator__of.resource()));
		return creator__of;
	}
	
	public com.ibm.adtech.jastor.Thing addCreator__of(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		com.ibm.adtech.jastor.Thing creator__of = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
		if (this.creator__of == null)
			initCreator__of();
		if (this.creator__of.contains(creator__of))
			return creator__of;
		this.creator__of.add(creator__of);
		_model.add(_model.createStatement(_resource,creator__ofProperty,creator__of.resource()));
		return creator__of;
	}
	
	public void removeCreator__of(com.ibm.adtech.jastor.Thing creator__of) throws JastorException {
		if (this.creator__of == null)
			initCreator__of();
		if (!this.creator__of.contains(creator__of))
			return;
		if (!_model.contains(_resource, creator__ofProperty, creator__of.resource()))
			return;
		this.creator__of.remove(creator__of);
		_model.removeAll(_resource, creator__ofProperty, creator__of.resource());
	}
		 

	private void initModerator__of() throws JastorException {
		this.moderator__of = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, moderator__ofProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#moderator_of properties in UserAccount model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				de.m0ep.uni.ma.rdfs.sioc.Forum moderator__of = de.m0ep.uni.ma.rdfs.sioc.Factory.getForum(resource,_model);
				this.moderator__of.add(moderator__of);
			}
		}
	}

	public java.util.Iterator getModerator__of() throws JastorException {
		if (moderator__of == null)
			initModerator__of();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(moderator__of,_resource,moderator__ofProperty,true);
	}

	public void addModerator__of(de.m0ep.uni.ma.rdfs.sioc.Forum moderator__of) throws JastorException {
		if (this.moderator__of == null)
			initModerator__of();
		if (this.moderator__of.contains(moderator__of)) {
			this.moderator__of.remove(moderator__of);
			this.moderator__of.add(moderator__of);
			return;
		}
		this.moderator__of.add(moderator__of);
		_model.add(_model.createStatement(_resource,moderator__ofProperty,moderator__of.resource()));
	}
	
	public de.m0ep.uni.ma.rdfs.sioc.Forum addModerator__of() throws JastorException {
		de.m0ep.uni.ma.rdfs.sioc.Forum moderator__of = de.m0ep.uni.ma.rdfs.sioc.Factory.createForum(_model.createResource(),_model);
		if (this.moderator__of == null)
			initModerator__of();
		this.moderator__of.add(moderator__of);
		_model.add(_model.createStatement(_resource,moderator__ofProperty,moderator__of.resource()));
		return moderator__of;
	}
	
	public de.m0ep.uni.ma.rdfs.sioc.Forum addModerator__of(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		de.m0ep.uni.ma.rdfs.sioc.Forum moderator__of = de.m0ep.uni.ma.rdfs.sioc.Factory.getForum(resource,_model);
		if (this.moderator__of == null)
			initModerator__of();
		if (this.moderator__of.contains(moderator__of))
			return moderator__of;
		this.moderator__of.add(moderator__of);
		_model.add(_model.createStatement(_resource,moderator__ofProperty,moderator__of.resource()));
		return moderator__of;
	}
	
	public void removeModerator__of(de.m0ep.uni.ma.rdfs.sioc.Forum moderator__of) throws JastorException {
		if (this.moderator__of == null)
			initModerator__of();
		if (!this.moderator__of.contains(moderator__of))
			return;
		if (!_model.contains(_resource, moderator__ofProperty, moderator__of.resource()))
			return;
		this.moderator__of.remove(moderator__of);
		_model.removeAll(_resource, moderator__ofProperty, moderator__of.resource());
	}
		 

	private void initFollows() throws JastorException {
		this.follows = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, followsProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#follows properties in UserAccount model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				de.m0ep.uni.ma.rdfs.sioc.UserAccount follows = de.m0ep.uni.ma.rdfs.sioc.Factory.getUserAccount(resource,_model);
				this.follows.add(follows);
			}
		}
	}

	public java.util.Iterator getFollows() throws JastorException {
		if (follows == null)
			initFollows();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(follows,_resource,followsProperty,true);
	}

	public void addFollows(de.m0ep.uni.ma.rdfs.sioc.UserAccount follows) throws JastorException {
		if (this.follows == null)
			initFollows();
		if (this.follows.contains(follows)) {
			this.follows.remove(follows);
			this.follows.add(follows);
			return;
		}
		this.follows.add(follows);
		_model.add(_model.createStatement(_resource,followsProperty,follows.resource()));
	}
	
	public de.m0ep.uni.ma.rdfs.sioc.UserAccount addFollows() throws JastorException {
		de.m0ep.uni.ma.rdfs.sioc.UserAccount follows = de.m0ep.uni.ma.rdfs.sioc.Factory.createUserAccount(_model.createResource(),_model);
		if (this.follows == null)
			initFollows();
		this.follows.add(follows);
		_model.add(_model.createStatement(_resource,followsProperty,follows.resource()));
		return follows;
	}
	
	public de.m0ep.uni.ma.rdfs.sioc.UserAccount addFollows(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		de.m0ep.uni.ma.rdfs.sioc.UserAccount follows = de.m0ep.uni.ma.rdfs.sioc.Factory.getUserAccount(resource,_model);
		if (this.follows == null)
			initFollows();
		if (this.follows.contains(follows))
			return follows;
		this.follows.add(follows);
		_model.add(_model.createStatement(_resource,followsProperty,follows.resource()));
		return follows;
	}
	
	public void removeFollows(de.m0ep.uni.ma.rdfs.sioc.UserAccount follows) throws JastorException {
		if (this.follows == null)
			initFollows();
		if (!this.follows.contains(follows))
			return;
		if (!_model.contains(_resource, followsProperty, follows.resource()))
			return;
		this.follows.remove(follows);
		_model.removeAll(_resource, followsProperty, follows.resource());
	}
		 

	private void initAccount__of() throws JastorException {
		this.account__of = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, account__ofProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#account_of properties in UserAccount model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				de.m0ep.uni.ma.rdfs.foaf.Agent account__of = de.m0ep.uni.ma.rdfs.foaf.Factory.getAgent(resource,_model);
				this.account__of.add(account__of);
			}
		}
	}

	public java.util.Iterator getAccount__of() throws JastorException {
		if (account__of == null)
			initAccount__of();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(account__of,_resource,account__ofProperty,true);
	}

	public void addAccount__of(de.m0ep.uni.ma.rdfs.foaf.Agent account__of) throws JastorException {
		if (this.account__of == null)
			initAccount__of();
		if (this.account__of.contains(account__of)) {
			this.account__of.remove(account__of);
			this.account__of.add(account__of);
			return;
		}
		this.account__of.add(account__of);
		_model.add(_model.createStatement(_resource,account__ofProperty,account__of.resource()));
	}
	
	public de.m0ep.uni.ma.rdfs.foaf.Agent addAccount__of() throws JastorException {
		de.m0ep.uni.ma.rdfs.foaf.Agent account__of = de.m0ep.uni.ma.rdfs.foaf.Factory.createAgent(_model.createResource(),_model);
		if (this.account__of == null)
			initAccount__of();
		this.account__of.add(account__of);
		_model.add(_model.createStatement(_resource,account__ofProperty,account__of.resource()));
		return account__of;
	}
	
	public de.m0ep.uni.ma.rdfs.foaf.Agent addAccount__of(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		de.m0ep.uni.ma.rdfs.foaf.Agent account__of = de.m0ep.uni.ma.rdfs.foaf.Factory.getAgent(resource,_model);
		if (this.account__of == null)
			initAccount__of();
		if (this.account__of.contains(account__of))
			return account__of;
		this.account__of.add(account__of);
		_model.add(_model.createStatement(_resource,account__ofProperty,account__of.resource()));
		return account__of;
	}
	
	public void removeAccount__of(de.m0ep.uni.ma.rdfs.foaf.Agent account__of) throws JastorException {
		if (this.account__of == null)
			initAccount__of();
		if (!this.account__of.contains(account__of))
			return;
		if (!_model.contains(_resource, account__ofProperty, account__of.resource()))
			return;
		this.account__of.remove(account__of);
		_model.removeAll(_resource, account__ofProperty, account__of.resource());
	}
		 

	private void initOwner__of() throws JastorException {
		this.owner__of = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, owner__ofProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#owner_of properties in UserAccount model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				com.ibm.adtech.jastor.Thing owner__of = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
				this.owner__of.add(owner__of);
			}
		}
	}

	public java.util.Iterator getOwner__of() throws JastorException {
		if (owner__of == null)
			initOwner__of();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(owner__of,_resource,owner__ofProperty,true);
	}

	public void addOwner__of(com.ibm.adtech.jastor.Thing owner__of) throws JastorException {
		if (this.owner__of == null)
			initOwner__of();
		if (this.owner__of.contains(owner__of)) {
			this.owner__of.remove(owner__of);
			this.owner__of.add(owner__of);
			return;
		}
		this.owner__of.add(owner__of);
		_model.add(_model.createStatement(_resource,owner__ofProperty,owner__of.resource()));
	}
	
	public com.ibm.adtech.jastor.Thing addOwner__of() throws JastorException {
		com.ibm.adtech.jastor.Thing owner__of = com.ibm.adtech.jastor.ThingFactory.createThing(_model.createResource(),_model);
		if (this.owner__of == null)
			initOwner__of();
		this.owner__of.add(owner__of);
		_model.add(_model.createStatement(_resource,owner__ofProperty,owner__of.resource()));
		return owner__of;
	}
	
	public com.ibm.adtech.jastor.Thing addOwner__of(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		com.ibm.adtech.jastor.Thing owner__of = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
		if (this.owner__of == null)
			initOwner__of();
		if (this.owner__of.contains(owner__of))
			return owner__of;
		this.owner__of.add(owner__of);
		_model.add(_model.createStatement(_resource,owner__ofProperty,owner__of.resource()));
		return owner__of;
	}
	
	public void removeOwner__of(com.ibm.adtech.jastor.Thing owner__of) throws JastorException {
		if (this.owner__of == null)
			initOwner__of();
		if (!this.owner__of.contains(owner__of))
			return;
		if (!_model.contains(_resource, owner__ofProperty, owner__of.resource()))
			return;
		this.owner__of.remove(owner__of);
		_model.removeAll(_resource, owner__ofProperty, owner__of.resource());
	}
		 

	private void initEmail() throws JastorException {
		this.email = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, emailProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#email properties in UserAccount model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				com.ibm.adtech.jastor.Thing email = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
				this.email.add(email);
			}
		}
	}

	public java.util.Iterator getEmail() throws JastorException {
		if (email == null)
			initEmail();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(email,_resource,emailProperty,true);
	}

	public void addEmail(com.ibm.adtech.jastor.Thing email) throws JastorException {
		if (this.email == null)
			initEmail();
		if (this.email.contains(email)) {
			this.email.remove(email);
			this.email.add(email);
			return;
		}
		this.email.add(email);
		_model.add(_model.createStatement(_resource,emailProperty,email.resource()));
	}
	
	public com.ibm.adtech.jastor.Thing addEmail() throws JastorException {
		com.ibm.adtech.jastor.Thing email = com.ibm.adtech.jastor.ThingFactory.createThing(_model.createResource(),_model);
		if (this.email == null)
			initEmail();
		this.email.add(email);
		_model.add(_model.createStatement(_resource,emailProperty,email.resource()));
		return email;
	}
	
	public com.ibm.adtech.jastor.Thing addEmail(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		com.ibm.adtech.jastor.Thing email = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
		if (this.email == null)
			initEmail();
		if (this.email.contains(email))
			return email;
		this.email.add(email);
		_model.add(_model.createStatement(_resource,emailProperty,email.resource()));
		return email;
	}
	
	public void removeEmail(com.ibm.adtech.jastor.Thing email) throws JastorException {
		if (this.email == null)
			initEmail();
		if (!this.email.contains(email))
			return;
		if (!_model.contains(_resource, emailProperty, email.resource()))
			return;
		this.email.remove(email);
		_model.removeAll(_resource, emailProperty, email.resource());
	}
		 

	private void initAvatar() throws JastorException {
		this.avatar = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, avatarProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#avatar properties in UserAccount model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				de.m0ep.uni.ma.rdfs.foaf.Image avatar = de.m0ep.uni.ma.rdfs.foaf.Factory.getImage(resource,_model);
				this.avatar.add(avatar);
			}
		}
	}

	public java.util.Iterator getAvatar() throws JastorException {
		if (avatar == null)
			initAvatar();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(avatar,_resource,avatarProperty,true);
	}

	public void addAvatar(de.m0ep.uni.ma.rdfs.foaf.Image avatar) throws JastorException {
		if (this.avatar == null)
			initAvatar();
		if (this.avatar.contains(avatar)) {
			this.avatar.remove(avatar);
			this.avatar.add(avatar);
			return;
		}
		this.avatar.add(avatar);
		_model.add(_model.createStatement(_resource,avatarProperty,avatar.resource()));
	}
	
	public de.m0ep.uni.ma.rdfs.foaf.Image addAvatar() throws JastorException {
		de.m0ep.uni.ma.rdfs.foaf.Image avatar = de.m0ep.uni.ma.rdfs.foaf.Factory.createImage(_model.createResource(),_model);
		if (this.avatar == null)
			initAvatar();
		this.avatar.add(avatar);
		_model.add(_model.createStatement(_resource,avatarProperty,avatar.resource()));
		return avatar;
	}
	
	public de.m0ep.uni.ma.rdfs.foaf.Image addAvatar(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		de.m0ep.uni.ma.rdfs.foaf.Image avatar = de.m0ep.uni.ma.rdfs.foaf.Factory.getImage(resource,_model);
		if (this.avatar == null)
			initAvatar();
		if (this.avatar.contains(avatar))
			return avatar;
		this.avatar.add(avatar);
		_model.add(_model.createStatement(_resource,avatarProperty,avatar.resource()));
		return avatar;
	}
	
	public void removeAvatar(de.m0ep.uni.ma.rdfs.foaf.Image avatar) throws JastorException {
		if (this.avatar == null)
			initAvatar();
		if (!this.avatar.contains(avatar))
			return;
		if (!_model.contains(_resource, avatarProperty, avatar.resource()))
			return;
		this.avatar.remove(avatar);
		_model.removeAll(_resource, avatarProperty, avatar.resource());
	}
		 

	private void initAdministrator__of() throws JastorException {
		this.administrator__of = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, administrator__ofProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#administrator_of properties in UserAccount model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				de.m0ep.uni.ma.rdfs.sioc.Site administrator__of = de.m0ep.uni.ma.rdfs.sioc.Factory.getSite(resource,_model);
				this.administrator__of.add(administrator__of);
			}
		}
	}

	public java.util.Iterator getAdministrator__of() throws JastorException {
		if (administrator__of == null)
			initAdministrator__of();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(administrator__of,_resource,administrator__ofProperty,true);
	}

	public void addAdministrator__of(de.m0ep.uni.ma.rdfs.sioc.Site administrator__of) throws JastorException {
		if (this.administrator__of == null)
			initAdministrator__of();
		if (this.administrator__of.contains(administrator__of)) {
			this.administrator__of.remove(administrator__of);
			this.administrator__of.add(administrator__of);
			return;
		}
		this.administrator__of.add(administrator__of);
		_model.add(_model.createStatement(_resource,administrator__ofProperty,administrator__of.resource()));
	}
	
	public de.m0ep.uni.ma.rdfs.sioc.Site addAdministrator__of() throws JastorException {
		de.m0ep.uni.ma.rdfs.sioc.Site administrator__of = de.m0ep.uni.ma.rdfs.sioc.Factory.createSite(_model.createResource(),_model);
		if (this.administrator__of == null)
			initAdministrator__of();
		this.administrator__of.add(administrator__of);
		_model.add(_model.createStatement(_resource,administrator__ofProperty,administrator__of.resource()));
		return administrator__of;
	}
	
	public de.m0ep.uni.ma.rdfs.sioc.Site addAdministrator__of(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		de.m0ep.uni.ma.rdfs.sioc.Site administrator__of = de.m0ep.uni.ma.rdfs.sioc.Factory.getSite(resource,_model);
		if (this.administrator__of == null)
			initAdministrator__of();
		if (this.administrator__of.contains(administrator__of))
			return administrator__of;
		this.administrator__of.add(administrator__of);
		_model.add(_model.createStatement(_resource,administrator__ofProperty,administrator__of.resource()));
		return administrator__of;
	}
	
	public void removeAdministrator__of(de.m0ep.uni.ma.rdfs.sioc.Site administrator__of) throws JastorException {
		if (this.administrator__of == null)
			initAdministrator__of();
		if (!this.administrator__of.contains(administrator__of))
			return;
		if (!_model.contains(_resource, administrator__ofProperty, administrator__of.resource()))
			return;
		this.administrator__of.remove(administrator__of);
		_model.removeAll(_resource, administrator__ofProperty, administrator__of.resource());
	}
		 

	private void initModifier__of() throws JastorException {
		this.modifier__of = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, modifier__ofProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#modifier_of properties in UserAccount model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				de.m0ep.uni.ma.rdfs.sioc.Item modifier__of = de.m0ep.uni.ma.rdfs.sioc.Factory.getItem(resource,_model);
				this.modifier__of.add(modifier__of);
			}
		}
	}

	public java.util.Iterator getModifier__of() throws JastorException {
		if (modifier__of == null)
			initModifier__of();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(modifier__of,_resource,modifier__ofProperty,true);
	}

	public void addModifier__of(de.m0ep.uni.ma.rdfs.sioc.Item modifier__of) throws JastorException {
		if (this.modifier__of == null)
			initModifier__of();
		if (this.modifier__of.contains(modifier__of)) {
			this.modifier__of.remove(modifier__of);
			this.modifier__of.add(modifier__of);
			return;
		}
		this.modifier__of.add(modifier__of);
		_model.add(_model.createStatement(_resource,modifier__ofProperty,modifier__of.resource()));
	}
	
	public de.m0ep.uni.ma.rdfs.sioc.Item addModifier__of() throws JastorException {
		de.m0ep.uni.ma.rdfs.sioc.Item modifier__of = de.m0ep.uni.ma.rdfs.sioc.Factory.createItem(_model.createResource(),_model);
		if (this.modifier__of == null)
			initModifier__of();
		this.modifier__of.add(modifier__of);
		_model.add(_model.createStatement(_resource,modifier__ofProperty,modifier__of.resource()));
		return modifier__of;
	}
	
	public de.m0ep.uni.ma.rdfs.sioc.Item addModifier__of(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		de.m0ep.uni.ma.rdfs.sioc.Item modifier__of = de.m0ep.uni.ma.rdfs.sioc.Factory.getItem(resource,_model);
		if (this.modifier__of == null)
			initModifier__of();
		if (this.modifier__of.contains(modifier__of))
			return modifier__of;
		this.modifier__of.add(modifier__of);
		_model.add(_model.createStatement(_resource,modifier__ofProperty,modifier__of.resource()));
		return modifier__of;
	}
	
	public void removeModifier__of(de.m0ep.uni.ma.rdfs.sioc.Item modifier__of) throws JastorException {
		if (this.modifier__of == null)
			initModifier__of();
		if (!this.modifier__of.contains(modifier__of))
			return;
		if (!_model.contains(_resource, modifier__ofProperty, modifier__of.resource()))
			return;
		this.modifier__of.remove(modifier__of);
		_model.removeAll(_resource, modifier__ofProperty, modifier__of.resource());
	}
		 

	private void initSubscriber__of() throws JastorException {
		this.subscriber__of = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, subscriber__ofProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#subscriber_of properties in UserAccount model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				de.m0ep.uni.ma.rdfs.sioc.Container subscriber__of = de.m0ep.uni.ma.rdfs.sioc.Factory.getContainer(resource,_model);
				this.subscriber__of.add(subscriber__of);
			}
		}
	}

	public java.util.Iterator getSubscriber__of() throws JastorException {
		if (subscriber__of == null)
			initSubscriber__of();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(subscriber__of,_resource,subscriber__ofProperty,true);
	}

	public void addSubscriber__of(de.m0ep.uni.ma.rdfs.sioc.Container subscriber__of) throws JastorException {
		if (this.subscriber__of == null)
			initSubscriber__of();
		if (this.subscriber__of.contains(subscriber__of)) {
			this.subscriber__of.remove(subscriber__of);
			this.subscriber__of.add(subscriber__of);
			return;
		}
		this.subscriber__of.add(subscriber__of);
		_model.add(_model.createStatement(_resource,subscriber__ofProperty,subscriber__of.resource()));
	}
	
	public de.m0ep.uni.ma.rdfs.sioc.Container addSubscriber__of() throws JastorException {
		de.m0ep.uni.ma.rdfs.sioc.Container subscriber__of = de.m0ep.uni.ma.rdfs.sioc.Factory.createContainer(_model.createResource(),_model);
		if (this.subscriber__of == null)
			initSubscriber__of();
		this.subscriber__of.add(subscriber__of);
		_model.add(_model.createStatement(_resource,subscriber__ofProperty,subscriber__of.resource()));
		return subscriber__of;
	}
	
	public de.m0ep.uni.ma.rdfs.sioc.Container addSubscriber__of(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		de.m0ep.uni.ma.rdfs.sioc.Container subscriber__of = de.m0ep.uni.ma.rdfs.sioc.Factory.getContainer(resource,_model);
		if (this.subscriber__of == null)
			initSubscriber__of();
		if (this.subscriber__of.contains(subscriber__of))
			return subscriber__of;
		this.subscriber__of.add(subscriber__of);
		_model.add(_model.createStatement(_resource,subscriber__ofProperty,subscriber__of.resource()));
		return subscriber__of;
	}
	
	public void removeSubscriber__of(de.m0ep.uni.ma.rdfs.sioc.Container subscriber__of) throws JastorException {
		if (this.subscriber__of == null)
			initSubscriber__of();
		if (!this.subscriber__of.contains(subscriber__of))
			return;
		if (!_model.contains(_resource, subscriber__ofProperty, subscriber__of.resource()))
			return;
		this.subscriber__of.remove(subscriber__of);
		_model.removeAll(_resource, subscriber__ofProperty, subscriber__of.resource());
	}
		 

	private void initDepiction() throws JastorException {
		this.depiction = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, depictionProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/depiction properties in UserAccount model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				de.m0ep.uni.ma.rdfs.foaf.Image depiction = de.m0ep.uni.ma.rdfs.foaf.Factory.getImage(resource,_model);
				this.depiction.add(depiction);
			}
		}
	}

	public java.util.Iterator getDepiction() throws JastorException {
		if (depiction == null)
			initDepiction();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(depiction,_resource,depictionProperty,true);
	}

	public void addDepiction(de.m0ep.uni.ma.rdfs.foaf.Image depiction) throws JastorException {
		if (this.depiction == null)
			initDepiction();
		if (this.depiction.contains(depiction)) {
			this.depiction.remove(depiction);
			this.depiction.add(depiction);
			return;
		}
		this.depiction.add(depiction);
		_model.add(_model.createStatement(_resource,depictionProperty,depiction.resource()));
	}
	
	public de.m0ep.uni.ma.rdfs.foaf.Image addDepiction() throws JastorException {
		de.m0ep.uni.ma.rdfs.foaf.Image depiction = de.m0ep.uni.ma.rdfs.foaf.Factory.createImage(_model.createResource(),_model);
		if (this.depiction == null)
			initDepiction();
		this.depiction.add(depiction);
		_model.add(_model.createStatement(_resource,depictionProperty,depiction.resource()));
		return depiction;
	}
	
	public de.m0ep.uni.ma.rdfs.foaf.Image addDepiction(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		de.m0ep.uni.ma.rdfs.foaf.Image depiction = de.m0ep.uni.ma.rdfs.foaf.Factory.getImage(resource,_model);
		if (this.depiction == null)
			initDepiction();
		if (this.depiction.contains(depiction))
			return depiction;
		this.depiction.add(depiction);
		_model.add(_model.createStatement(_resource,depictionProperty,depiction.resource()));
		return depiction;
	}
	
	public void removeDepiction(de.m0ep.uni.ma.rdfs.foaf.Image depiction) throws JastorException {
		if (this.depiction == null)
			initDepiction();
		if (!this.depiction.contains(depiction))
			return;
		if (!_model.contains(_resource, depictionProperty, depiction.resource()))
			return;
		this.depiction.remove(depiction);
		_model.removeAll(_resource, depictionProperty, depiction.resource());
	}
		 

	private void initId() throws JastorException {
		id = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, idProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#id properties in UserAccount model not a Literal", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
			id.add(literal);
		}
	}

	public java.util.Iterator getId() throws JastorException {
		if (id == null)
			initId();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(id,_resource,idProperty,false);
	}

	public void addId(com.hp.hpl.jena.rdf.model.Literal id) throws JastorException {
		if (this.id == null)
			initId();
		if (this.id.contains(id))
			return;
		if (_model.contains(_resource, idProperty, createLiteral(id)))
			return;
		this.id.add(id);
		_model.add(_resource, idProperty, id);
	}
	
	public void removeId(com.hp.hpl.jena.rdf.model.Literal id) throws JastorException {
		if (this.id == null)
			initId();
		if (!this.id.contains(id))
			return;
		if (!_model.contains(_resource, idProperty, createLiteral(id)))
			return;
		this.id.remove(id);
		_model.removeAll(_resource, idProperty, createLiteral(id));
	}


	private void initNum__authors() throws JastorException {
		num__authors = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, num__authorsProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#num_authors properties in UserAccount model not a Literal", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
			Object obj = Util.fixLiteral(true,literal,"java.lang.String","http://www.w3.org/2001/XMLSchema#nonNegativeInteger");
			if (obj != null)
				num__authors.add(obj);
		}
	}

	public java.util.Iterator getNum__authors() throws JastorException {
		if (num__authors == null)
			initNum__authors();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(num__authors,_resource,num__authorsProperty,false);
	}

	public void addNum__authors(java.lang.String num__authors) throws JastorException {
		if (this.num__authors == null)
			initNum__authors();
		if (this.num__authors.contains(num__authors))
			return;
		if (_model.contains(_resource, num__authorsProperty, createLiteral(num__authors)))
			return;
		this.num__authors.add(num__authors);
		_model.add(_resource, num__authorsProperty, createLiteral(num__authors));
	}
	
	public void removeNum__authors(java.lang.String num__authors) throws JastorException {
		if (this.num__authors == null)
			initNum__authors();
		if (!this.num__authors.contains(num__authors))
			return;
		if (!_model.contains(_resource, num__authorsProperty, createLiteral(num__authors)))
			return;
		this.num__authors.remove(num__authors);
		_model.removeAll(_resource, num__authorsProperty, createLiteral(num__authors));
	}


	private void initNote() throws JastorException {
		note = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, noteProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#note properties in UserAccount model not a Literal", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
			note.add(literal);
		}
	}

	public java.util.Iterator getNote() throws JastorException {
		if (note == null)
			initNote();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(note,_resource,noteProperty,false);
	}

	public void addNote(com.hp.hpl.jena.rdf.model.Literal note) throws JastorException {
		if (this.note == null)
			initNote();
		if (this.note.contains(note))
			return;
		if (_model.contains(_resource, noteProperty, createLiteral(note)))
			return;
		this.note.add(note);
		_model.add(_resource, noteProperty, note);
	}
	
	public void removeNote(com.hp.hpl.jena.rdf.model.Literal note) throws JastorException {
		if (this.note == null)
			initNote();
		if (!this.note.contains(note))
			return;
		if (!_model.contains(_resource, noteProperty, createLiteral(note)))
			return;
		this.note.remove(note);
		_model.removeAll(_resource, noteProperty, createLiteral(note));
	}


	private void initNs1_Name() throws JastorException {
		ns1_name = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, ns1_nameProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#name properties in UserAccount model not a Literal", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
			ns1_name.add(literal);
		}
	}

	public java.util.Iterator getNs1_Name() throws JastorException {
		if (ns1_name == null)
			initNs1_Name();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(ns1_name,_resource,ns1_nameProperty,false);
	}

	public void addNs1_Name(com.hp.hpl.jena.rdf.model.Literal ns1_name) throws JastorException {
		if (this.ns1_name == null)
			initNs1_Name();
		if (this.ns1_name.contains(ns1_name))
			return;
		if (_model.contains(_resource, ns1_nameProperty, createLiteral(ns1_name)))
			return;
		this.ns1_name.add(ns1_name);
		_model.add(_resource, ns1_nameProperty, ns1_name);
	}
	
	public void removeNs1_Name(com.hp.hpl.jena.rdf.model.Literal ns1_name) throws JastorException {
		if (this.ns1_name == null)
			initNs1_Name();
		if (!this.ns1_name.contains(ns1_name))
			return;
		if (!_model.contains(_resource, ns1_nameProperty, createLiteral(ns1_name)))
			return;
		this.ns1_name.remove(ns1_name);
		_model.removeAll(_resource, ns1_nameProperty, createLiteral(ns1_name));
	}


	private void initNum__views() throws JastorException {
		num__views = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, num__viewsProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#num_views properties in UserAccount model not a Literal", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
			Object obj = Util.fixLiteral(true,literal,"java.lang.String","http://www.w3.org/2001/XMLSchema#nonNegativeInteger");
			if (obj != null)
				num__views.add(obj);
		}
	}

	public java.util.Iterator getNum__views() throws JastorException {
		if (num__views == null)
			initNum__views();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(num__views,_resource,num__viewsProperty,false);
	}

	public void addNum__views(java.lang.String num__views) throws JastorException {
		if (this.num__views == null)
			initNum__views();
		if (this.num__views.contains(num__views))
			return;
		if (_model.contains(_resource, num__viewsProperty, createLiteral(num__views)))
			return;
		this.num__views.add(num__views);
		_model.add(_resource, num__viewsProperty, createLiteral(num__views));
	}
	
	public void removeNum__views(java.lang.String num__views) throws JastorException {
		if (this.num__views == null)
			initNum__views();
		if (!this.num__views.contains(num__views))
			return;
		if (!_model.contains(_resource, num__viewsProperty, createLiteral(num__views)))
			return;
		this.num__views.remove(num__views);
		_model.removeAll(_resource, num__viewsProperty, createLiteral(num__views));
	}


	private void initDescription() throws JastorException {
		description = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, descriptionProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://purl.org/dc/terms/description properties in UserAccount model not a Literal", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
			description.add(literal);
		}
	}

	public java.util.Iterator getDescription() throws JastorException {
		if (description == null)
			initDescription();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(description,_resource,descriptionProperty,false);
	}

	public void addDescription(com.hp.hpl.jena.rdf.model.Literal description) throws JastorException {
		if (this.description == null)
			initDescription();
		if (this.description.contains(description))
			return;
		if (_model.contains(_resource, descriptionProperty, createLiteral(description)))
			return;
		this.description.add(description);
		_model.add(_resource, descriptionProperty, description);
	}
	
	public void removeDescription(com.hp.hpl.jena.rdf.model.Literal description) throws JastorException {
		if (this.description == null)
			initDescription();
		if (!this.description.contains(description))
			return;
		if (!_model.contains(_resource, descriptionProperty, createLiteral(description)))
			return;
		this.description.remove(description);
		_model.removeAll(_resource, descriptionProperty, createLiteral(description));
	}


	private void initDate() throws JastorException {
		date = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, dateProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://purl.org/dc/terms/date properties in UserAccount model not a Literal", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
			date.add(literal);
		}
	}

	public java.util.Iterator getDate() throws JastorException {
		if (date == null)
			initDate();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(date,_resource,dateProperty,false);
	}

	public void addDate(com.hp.hpl.jena.rdf.model.Literal date) throws JastorException {
		if (this.date == null)
			initDate();
		if (this.date.contains(date))
			return;
		if (_model.contains(_resource, dateProperty, createLiteral(date)))
			return;
		this.date.add(date);
		_model.add(_resource, dateProperty, date);
	}
	
	public void removeDate(com.hp.hpl.jena.rdf.model.Literal date) throws JastorException {
		if (this.date == null)
			initDate();
		if (!this.date.contains(date))
			return;
		if (!_model.contains(_resource, dateProperty, createLiteral(date)))
			return;
		this.date.remove(date);
		_model.removeAll(_resource, dateProperty, createLiteral(date));
	}


	private void initNum__replies() throws JastorException {
		num__replies = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, num__repliesProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#num_replies properties in UserAccount model not a Literal", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
			Object obj = Util.fixLiteral(true,literal,"java.lang.String","http://www.w3.org/2001/XMLSchema#nonNegativeInteger");
			if (obj != null)
				num__replies.add(obj);
		}
	}

	public java.util.Iterator getNum__replies() throws JastorException {
		if (num__replies == null)
			initNum__replies();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(num__replies,_resource,num__repliesProperty,false);
	}

	public void addNum__replies(java.lang.String num__replies) throws JastorException {
		if (this.num__replies == null)
			initNum__replies();
		if (this.num__replies.contains(num__replies))
			return;
		if (_model.contains(_resource, num__repliesProperty, createLiteral(num__replies)))
			return;
		this.num__replies.add(num__replies);
		_model.add(_resource, num__repliesProperty, createLiteral(num__replies));
	}
	
	public void removeNum__replies(java.lang.String num__replies) throws JastorException {
		if (this.num__replies == null)
			initNum__replies();
		if (!this.num__replies.contains(num__replies))
			return;
		if (!_model.contains(_resource, num__repliesProperty, createLiteral(num__replies)))
			return;
		this.num__replies.remove(num__replies);
		_model.removeAll(_resource, num__repliesProperty, createLiteral(num__replies));
	}


	private void initLast__activity__date() throws JastorException {
		last__activity__date = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, last__activity__dateProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#last_activity_date properties in UserAccount model not a Literal", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
			last__activity__date.add(literal);
		}
	}

	public java.util.Iterator getLast__activity__date() throws JastorException {
		if (last__activity__date == null)
			initLast__activity__date();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(last__activity__date,_resource,last__activity__dateProperty,false);
	}

	public void addLast__activity__date(com.hp.hpl.jena.rdf.model.Literal last__activity__date) throws JastorException {
		if (this.last__activity__date == null)
			initLast__activity__date();
		if (this.last__activity__date.contains(last__activity__date))
			return;
		if (_model.contains(_resource, last__activity__dateProperty, createLiteral(last__activity__date)))
			return;
		this.last__activity__date.add(last__activity__date);
		_model.add(_resource, last__activity__dateProperty, last__activity__date);
	}
	
	public void removeLast__activity__date(com.hp.hpl.jena.rdf.model.Literal last__activity__date) throws JastorException {
		if (this.last__activity__date == null)
			initLast__activity__date();
		if (!this.last__activity__date.contains(last__activity__date))
			return;
		if (!_model.contains(_resource, last__activity__dateProperty, createLiteral(last__activity__date)))
			return;
		this.last__activity__date.remove(last__activity__date);
		_model.removeAll(_resource, last__activity__dateProperty, createLiteral(last__activity__date));
	}


	private void initLast__reply__date() throws JastorException {
		last__reply__date = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, last__reply__dateProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#last_reply_date properties in UserAccount model not a Literal", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
			last__reply__date.add(literal);
		}
	}

	public java.util.Iterator getLast__reply__date() throws JastorException {
		if (last__reply__date == null)
			initLast__reply__date();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(last__reply__date,_resource,last__reply__dateProperty,false);
	}

	public void addLast__reply__date(com.hp.hpl.jena.rdf.model.Literal last__reply__date) throws JastorException {
		if (this.last__reply__date == null)
			initLast__reply__date();
		if (this.last__reply__date.contains(last__reply__date))
			return;
		if (_model.contains(_resource, last__reply__dateProperty, createLiteral(last__reply__date)))
			return;
		this.last__reply__date.add(last__reply__date);
		_model.add(_resource, last__reply__dateProperty, last__reply__date);
	}
	
	public void removeLast__reply__date(com.hp.hpl.jena.rdf.model.Literal last__reply__date) throws JastorException {
		if (this.last__reply__date == null)
			initLast__reply__date();
		if (!this.last__reply__date.contains(last__reply__date))
			return;
		if (!_model.contains(_resource, last__reply__dateProperty, createLiteral(last__reply__date)))
			return;
		this.last__reply__date.remove(last__reply__date);
		_model.removeAll(_resource, last__reply__dateProperty, createLiteral(last__reply__date));
	}


	private void initNs2_Title() throws JastorException {
		ns2_title = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, ns2_titleProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://purl.org/dc/terms/title properties in UserAccount model not a Literal", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
			ns2_title.add(literal);
		}
	}

	public java.util.Iterator getNs2_Title() throws JastorException {
		if (ns2_title == null)
			initNs2_Title();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(ns2_title,_resource,ns2_titleProperty,false);
	}

	public void addNs2_Title(com.hp.hpl.jena.rdf.model.Literal ns2_title) throws JastorException {
		if (this.ns2_title == null)
			initNs2_Title();
		if (this.ns2_title.contains(ns2_title))
			return;
		if (_model.contains(_resource, ns2_titleProperty, createLiteral(ns2_title)))
			return;
		this.ns2_title.add(ns2_title);
		_model.add(_resource, ns2_titleProperty, ns2_title);
	}
	
	public void removeNs2_Title(com.hp.hpl.jena.rdf.model.Literal ns2_title) throws JastorException {
		if (this.ns2_title == null)
			initNs2_Title();
		if (!this.ns2_title.contains(ns2_title))
			return;
		if (!_model.contains(_resource, ns2_titleProperty, createLiteral(ns2_title)))
			return;
		this.ns2_title.remove(ns2_title);
		_model.removeAll(_resource, ns2_titleProperty, createLiteral(ns2_title));
	}


	private void initSubject() throws JastorException {
		this.subject = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, subjectProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://purl.org/dc/terms/subject properties in UserAccount model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				com.ibm.adtech.jastor.Thing subject = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
				this.subject.add(subject);
			}
		}
	}

	public java.util.Iterator getSubject() throws JastorException {
		if (subject == null)
			initSubject();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(subject,_resource,subjectProperty,true);
	}

	public void addSubject(com.ibm.adtech.jastor.Thing subject) throws JastorException {
		if (this.subject == null)
			initSubject();
		if (this.subject.contains(subject)) {
			this.subject.remove(subject);
			this.subject.add(subject);
			return;
		}
		this.subject.add(subject);
		_model.add(_model.createStatement(_resource,subjectProperty,subject.resource()));
	}
	
	public com.ibm.adtech.jastor.Thing addSubject() throws JastorException {
		com.ibm.adtech.jastor.Thing subject = com.ibm.adtech.jastor.ThingFactory.createThing(_model.createResource(),_model);
		if (this.subject == null)
			initSubject();
		this.subject.add(subject);
		_model.add(_model.createStatement(_resource,subjectProperty,subject.resource()));
		return subject;
	}
	
	public com.ibm.adtech.jastor.Thing addSubject(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		com.ibm.adtech.jastor.Thing subject = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
		if (this.subject == null)
			initSubject();
		if (this.subject.contains(subject))
			return subject;
		this.subject.add(subject);
		_model.add(_model.createStatement(_resource,subjectProperty,subject.resource()));
		return subject;
	}
	
	public void removeSubject(com.ibm.adtech.jastor.Thing subject) throws JastorException {
		if (this.subject == null)
			initSubject();
		if (!this.subject.contains(subject))
			return;
		if (!_model.contains(_resource, subjectProperty, subject.resource()))
			return;
		this.subject.remove(subject);
		_model.removeAll(_resource, subjectProperty, subject.resource());
	}
		 

	private void initReferences() throws JastorException {
		this.references = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, referencesProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://purl.org/dc/terms/references properties in UserAccount model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				com.ibm.adtech.jastor.Thing references = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
				this.references.add(references);
			}
		}
	}

	public java.util.Iterator getReferences() throws JastorException {
		if (references == null)
			initReferences();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(references,_resource,referencesProperty,true);
	}

	public void addReferences(com.ibm.adtech.jastor.Thing references) throws JastorException {
		if (this.references == null)
			initReferences();
		if (this.references.contains(references)) {
			this.references.remove(references);
			this.references.add(references);
			return;
		}
		this.references.add(references);
		_model.add(_model.createStatement(_resource,referencesProperty,references.resource()));
	}
	
	public com.ibm.adtech.jastor.Thing addReferences() throws JastorException {
		com.ibm.adtech.jastor.Thing references = com.ibm.adtech.jastor.ThingFactory.createThing(_model.createResource(),_model);
		if (this.references == null)
			initReferences();
		this.references.add(references);
		_model.add(_model.createStatement(_resource,referencesProperty,references.resource()));
		return references;
	}
	
	public com.ibm.adtech.jastor.Thing addReferences(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		com.ibm.adtech.jastor.Thing references = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
		if (this.references == null)
			initReferences();
		if (this.references.contains(references))
			return references;
		this.references.add(references);
		_model.add(_model.createStatement(_resource,referencesProperty,references.resource()));
		return references;
	}
	
	public void removeReferences(com.ibm.adtech.jastor.Thing references) throws JastorException {
		if (this.references == null)
			initReferences();
		if (!this.references.contains(references))
			return;
		if (!_model.contains(_resource, referencesProperty, references.resource()))
			return;
		this.references.remove(references);
		_model.removeAll(_resource, referencesProperty, references.resource());
	}
		 

	private void initLinks__to() throws JastorException {
		this.links__to = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, links__toProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#links_to properties in UserAccount model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				com.ibm.adtech.jastor.Thing links__to = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
				this.links__to.add(links__to);
			}
		}
	}

	public java.util.Iterator getLinks__to() throws JastorException {
		if (links__to == null)
			initLinks__to();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(links__to,_resource,links__toProperty,true);
	}

	public void addLinks__to(com.ibm.adtech.jastor.Thing links__to) throws JastorException {
		if (this.links__to == null)
			initLinks__to();
		if (this.links__to.contains(links__to)) {
			this.links__to.remove(links__to);
			this.links__to.add(links__to);
			return;
		}
		this.links__to.add(links__to);
		_model.add(_model.createStatement(_resource,links__toProperty,links__to.resource()));
	}
	
	public com.ibm.adtech.jastor.Thing addLinks__to() throws JastorException {
		com.ibm.adtech.jastor.Thing links__to = com.ibm.adtech.jastor.ThingFactory.createThing(_model.createResource(),_model);
		if (this.links__to == null)
			initLinks__to();
		this.links__to.add(links__to);
		_model.add(_model.createStatement(_resource,links__toProperty,links__to.resource()));
		return links__to;
	}
	
	public com.ibm.adtech.jastor.Thing addLinks__to(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		com.ibm.adtech.jastor.Thing links__to = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
		if (this.links__to == null)
			initLinks__to();
		if (this.links__to.contains(links__to))
			return links__to;
		this.links__to.add(links__to);
		_model.add(_model.createStatement(_resource,links__toProperty,links__to.resource()));
		return links__to;
	}
	
	public void removeLinks__to(com.ibm.adtech.jastor.Thing links__to) throws JastorException {
		if (this.links__to == null)
			initLinks__to();
		if (!this.links__to.contains(links__to))
			return;
		if (!_model.contains(_resource, links__toProperty, links__to.resource()))
			return;
		this.links__to.remove(links__to);
		_model.removeAll(_resource, links__toProperty, links__to.resource());
	}
		 

	private void initFeed() throws JastorException {
		this.feed = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, feedProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#feed properties in UserAccount model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				com.ibm.adtech.jastor.Thing feed = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
				this.feed.add(feed);
			}
		}
	}

	public java.util.Iterator getFeed() throws JastorException {
		if (feed == null)
			initFeed();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(feed,_resource,feedProperty,true);
	}

	public void addFeed(com.ibm.adtech.jastor.Thing feed) throws JastorException {
		if (this.feed == null)
			initFeed();
		if (this.feed.contains(feed)) {
			this.feed.remove(feed);
			this.feed.add(feed);
			return;
		}
		this.feed.add(feed);
		_model.add(_model.createStatement(_resource,feedProperty,feed.resource()));
	}
	
	public com.ibm.adtech.jastor.Thing addFeed() throws JastorException {
		com.ibm.adtech.jastor.Thing feed = com.ibm.adtech.jastor.ThingFactory.createThing(_model.createResource(),_model);
		if (this.feed == null)
			initFeed();
		this.feed.add(feed);
		_model.add(_model.createStatement(_resource,feedProperty,feed.resource()));
		return feed;
	}
	
	public com.ibm.adtech.jastor.Thing addFeed(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		com.ibm.adtech.jastor.Thing feed = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
		if (this.feed == null)
			initFeed();
		if (this.feed.contains(feed))
			return feed;
		this.feed.add(feed);
		_model.add(_model.createStatement(_resource,feedProperty,feed.resource()));
		return feed;
	}
	
	public void removeFeed(com.ibm.adtech.jastor.Thing feed) throws JastorException {
		if (this.feed == null)
			initFeed();
		if (!this.feed.contains(feed))
			return;
		if (!_model.contains(_resource, feedProperty, feed.resource()))
			return;
		this.feed.remove(feed);
		_model.removeAll(_resource, feedProperty, feed.resource());
	}
		 

	private void initHas__space() throws JastorException {
		this.has__space = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, has__spaceProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#has_space properties in UserAccount model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				de.m0ep.uni.ma.rdfs.sioc.Space has__space = de.m0ep.uni.ma.rdfs.sioc.Factory.getSpace(resource,_model);
				this.has__space.add(has__space);
			}
		}
	}

	public java.util.Iterator getHas__space() throws JastorException {
		if (has__space == null)
			initHas__space();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(has__space,_resource,has__spaceProperty,true);
	}

	public void addHas__space(de.m0ep.uni.ma.rdfs.sioc.Space has__space) throws JastorException {
		if (this.has__space == null)
			initHas__space();
		if (this.has__space.contains(has__space)) {
			this.has__space.remove(has__space);
			this.has__space.add(has__space);
			return;
		}
		this.has__space.add(has__space);
		_model.add(_model.createStatement(_resource,has__spaceProperty,has__space.resource()));
	}
	
	public de.m0ep.uni.ma.rdfs.sioc.Space addHas__space() throws JastorException {
		de.m0ep.uni.ma.rdfs.sioc.Space has__space = de.m0ep.uni.ma.rdfs.sioc.Factory.createSpace(_model.createResource(),_model);
		if (this.has__space == null)
			initHas__space();
		this.has__space.add(has__space);
		_model.add(_model.createStatement(_resource,has__spaceProperty,has__space.resource()));
		return has__space;
	}
	
	public de.m0ep.uni.ma.rdfs.sioc.Space addHas__space(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		de.m0ep.uni.ma.rdfs.sioc.Space has__space = de.m0ep.uni.ma.rdfs.sioc.Factory.getSpace(resource,_model);
		if (this.has__space == null)
			initHas__space();
		if (this.has__space.contains(has__space))
			return has__space;
		this.has__space.add(has__space);
		_model.add(_model.createStatement(_resource,has__spaceProperty,has__space.resource()));
		return has__space;
	}
	
	public void removeHas__space(de.m0ep.uni.ma.rdfs.sioc.Space has__space) throws JastorException {
		if (this.has__space == null)
			initHas__space();
		if (!this.has__space.contains(has__space))
			return;
		if (!_model.contains(_resource, has__spaceProperty, has__space.resource()))
			return;
		this.has__space.remove(has__space);
		_model.removeAll(_resource, has__spaceProperty, has__space.resource());
	}
		 

	private void initScope__of() throws JastorException {
		this.scope__of = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, scope__ofProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#scope_of properties in UserAccount model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				de.m0ep.uni.ma.rdfs.sioc.Role scope__of = de.m0ep.uni.ma.rdfs.sioc.Factory.getRole(resource,_model);
				this.scope__of.add(scope__of);
			}
		}
	}

	public java.util.Iterator getScope__of() throws JastorException {
		if (scope__of == null)
			initScope__of();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(scope__of,_resource,scope__ofProperty,true);
	}

	public void addScope__of(de.m0ep.uni.ma.rdfs.sioc.Role scope__of) throws JastorException {
		if (this.scope__of == null)
			initScope__of();
		if (this.scope__of.contains(scope__of)) {
			this.scope__of.remove(scope__of);
			this.scope__of.add(scope__of);
			return;
		}
		this.scope__of.add(scope__of);
		_model.add(_model.createStatement(_resource,scope__ofProperty,scope__of.resource()));
	}
	
	public de.m0ep.uni.ma.rdfs.sioc.Role addScope__of() throws JastorException {
		de.m0ep.uni.ma.rdfs.sioc.Role scope__of = de.m0ep.uni.ma.rdfs.sioc.Factory.createRole(_model.createResource(),_model);
		if (this.scope__of == null)
			initScope__of();
		this.scope__of.add(scope__of);
		_model.add(_model.createStatement(_resource,scope__ofProperty,scope__of.resource()));
		return scope__of;
	}
	
	public de.m0ep.uni.ma.rdfs.sioc.Role addScope__of(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		de.m0ep.uni.ma.rdfs.sioc.Role scope__of = de.m0ep.uni.ma.rdfs.sioc.Factory.getRole(resource,_model);
		if (this.scope__of == null)
			initScope__of();
		if (this.scope__of.contains(scope__of))
			return scope__of;
		this.scope__of.add(scope__of);
		_model.add(_model.createStatement(_resource,scope__ofProperty,scope__of.resource()));
		return scope__of;
	}
	
	public void removeScope__of(de.m0ep.uni.ma.rdfs.sioc.Role scope__of) throws JastorException {
		if (this.scope__of == null)
			initScope__of();
		if (!this.scope__of.contains(scope__of))
			return;
		if (!_model.contains(_resource, scope__ofProperty, scope__of.resource()))
			return;
		this.scope__of.remove(scope__of);
		_model.removeAll(_resource, scope__ofProperty, scope__of.resource());
	}
		 

	private void initTopic() throws JastorException {
		this.topic = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, topicProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#topic properties in UserAccount model not a Resource", stmt.getObject());
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
		 

	private void initLink() throws JastorException {
		this.link = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, linkProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#link properties in UserAccount model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				com.ibm.adtech.jastor.Thing link = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
				this.link.add(link);
			}
		}
	}

	public java.util.Iterator getLink() throws JastorException {
		if (link == null)
			initLink();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(link,_resource,linkProperty,true);
	}

	public void addLink(com.ibm.adtech.jastor.Thing link) throws JastorException {
		if (this.link == null)
			initLink();
		if (this.link.contains(link)) {
			this.link.remove(link);
			this.link.add(link);
			return;
		}
		this.link.add(link);
		_model.add(_model.createStatement(_resource,linkProperty,link.resource()));
	}
	
	public com.ibm.adtech.jastor.Thing addLink() throws JastorException {
		com.ibm.adtech.jastor.Thing link = com.ibm.adtech.jastor.ThingFactory.createThing(_model.createResource(),_model);
		if (this.link == null)
			initLink();
		this.link.add(link);
		_model.add(_model.createStatement(_resource,linkProperty,link.resource()));
		return link;
	}
	
	public com.ibm.adtech.jastor.Thing addLink(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		com.ibm.adtech.jastor.Thing link = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
		if (this.link == null)
			initLink();
		if (this.link.contains(link))
			return link;
		this.link.add(link);
		_model.add(_model.createStatement(_resource,linkProperty,link.resource()));
		return link;
	}
	
	public void removeLink(com.ibm.adtech.jastor.Thing link) throws JastorException {
		if (this.link == null)
			initLink();
		if (!this.link.contains(link))
			return;
		if (!_model.contains(_resource, linkProperty, link.resource()))
			return;
		this.link.remove(link);
		_model.removeAll(_resource, linkProperty, link.resource());
	}
		 

	private void initHas__creator() throws JastorException {
		this.has__creator = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, has__creatorProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#has_creator properties in UserAccount model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				de.m0ep.uni.ma.rdfs.sioc.UserAccount has__creator = de.m0ep.uni.ma.rdfs.sioc.Factory.getUserAccount(resource,_model);
				this.has__creator.add(has__creator);
			}
		}
	}

	public java.util.Iterator getHas__creator() throws JastorException {
		if (has__creator == null)
			initHas__creator();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(has__creator,_resource,has__creatorProperty,true);
	}

	public void addHas__creator(de.m0ep.uni.ma.rdfs.sioc.UserAccount has__creator) throws JastorException {
		if (this.has__creator == null)
			initHas__creator();
		if (this.has__creator.contains(has__creator)) {
			this.has__creator.remove(has__creator);
			this.has__creator.add(has__creator);
			return;
		}
		this.has__creator.add(has__creator);
		_model.add(_model.createStatement(_resource,has__creatorProperty,has__creator.resource()));
	}
	
	public de.m0ep.uni.ma.rdfs.sioc.UserAccount addHas__creator() throws JastorException {
		de.m0ep.uni.ma.rdfs.sioc.UserAccount has__creator = de.m0ep.uni.ma.rdfs.sioc.Factory.createUserAccount(_model.createResource(),_model);
		if (this.has__creator == null)
			initHas__creator();
		this.has__creator.add(has__creator);
		_model.add(_model.createStatement(_resource,has__creatorProperty,has__creator.resource()));
		return has__creator;
	}
	
	public de.m0ep.uni.ma.rdfs.sioc.UserAccount addHas__creator(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		de.m0ep.uni.ma.rdfs.sioc.UserAccount has__creator = de.m0ep.uni.ma.rdfs.sioc.Factory.getUserAccount(resource,_model);
		if (this.has__creator == null)
			initHas__creator();
		if (this.has__creator.contains(has__creator))
			return has__creator;
		this.has__creator.add(has__creator);
		_model.add(_model.createStatement(_resource,has__creatorProperty,has__creator.resource()));
		return has__creator;
	}
	
	public void removeHas__creator(de.m0ep.uni.ma.rdfs.sioc.UserAccount has__creator) throws JastorException {
		if (this.has__creator == null)
			initHas__creator();
		if (!this.has__creator.contains(has__creator))
			return;
		if (!_model.contains(_resource, has__creatorProperty, has__creator.resource()))
			return;
		this.has__creator.remove(has__creator);
		_model.removeAll(_resource, has__creatorProperty, has__creator.resource());
	}
		 

	private void initHas__owner() throws JastorException {
		this.has__owner = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, has__ownerProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#has_owner properties in UserAccount model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				de.m0ep.uni.ma.rdfs.sioc.UserAccount has__owner = de.m0ep.uni.ma.rdfs.sioc.Factory.getUserAccount(resource,_model);
				this.has__owner.add(has__owner);
			}
		}
	}

	public java.util.Iterator getHas__owner() throws JastorException {
		if (has__owner == null)
			initHas__owner();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(has__owner,_resource,has__ownerProperty,true);
	}

	public void addHas__owner(de.m0ep.uni.ma.rdfs.sioc.UserAccount has__owner) throws JastorException {
		if (this.has__owner == null)
			initHas__owner();
		if (this.has__owner.contains(has__owner)) {
			this.has__owner.remove(has__owner);
			this.has__owner.add(has__owner);
			return;
		}
		this.has__owner.add(has__owner);
		_model.add(_model.createStatement(_resource,has__ownerProperty,has__owner.resource()));
	}
	
	public de.m0ep.uni.ma.rdfs.sioc.UserAccount addHas__owner() throws JastorException {
		de.m0ep.uni.ma.rdfs.sioc.UserAccount has__owner = de.m0ep.uni.ma.rdfs.sioc.Factory.createUserAccount(_model.createResource(),_model);
		if (this.has__owner == null)
			initHas__owner();
		this.has__owner.add(has__owner);
		_model.add(_model.createStatement(_resource,has__ownerProperty,has__owner.resource()));
		return has__owner;
	}
	
	public de.m0ep.uni.ma.rdfs.sioc.UserAccount addHas__owner(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		de.m0ep.uni.ma.rdfs.sioc.UserAccount has__owner = de.m0ep.uni.ma.rdfs.sioc.Factory.getUserAccount(resource,_model);
		if (this.has__owner == null)
			initHas__owner();
		if (this.has__owner.contains(has__owner))
			return has__owner;
		this.has__owner.add(has__owner);
		_model.add(_model.createStatement(_resource,has__ownerProperty,has__owner.resource()));
		return has__owner;
	}
	
	public void removeHas__owner(de.m0ep.uni.ma.rdfs.sioc.UserAccount has__owner) throws JastorException {
		if (this.has__owner == null)
			initHas__owner();
		if (!this.has__owner.contains(has__owner))
			return;
		if (!_model.contains(_resource, has__ownerProperty, has__owner.resource()))
			return;
		this.has__owner.remove(has__owner);
		_model.removeAll(_resource, has__ownerProperty, has__owner.resource());
	}
		 

	private void initHas__function() throws JastorException {
		this.has__function = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, has__functionProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#has_function properties in UserAccount model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				de.m0ep.uni.ma.rdfs.sioc.Role has__function = de.m0ep.uni.ma.rdfs.sioc.Factory.getRole(resource,_model);
				this.has__function.add(has__function);
			}
		}
	}

	public java.util.Iterator getHas__function() throws JastorException {
		if (has__function == null)
			initHas__function();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(has__function,_resource,has__functionProperty,true);
	}

	public void addHas__function(de.m0ep.uni.ma.rdfs.sioc.Role has__function) throws JastorException {
		if (this.has__function == null)
			initHas__function();
		if (this.has__function.contains(has__function)) {
			this.has__function.remove(has__function);
			this.has__function.add(has__function);
			return;
		}
		this.has__function.add(has__function);
		_model.add(_model.createStatement(_resource,has__functionProperty,has__function.resource()));
	}
	
	public de.m0ep.uni.ma.rdfs.sioc.Role addHas__function() throws JastorException {
		de.m0ep.uni.ma.rdfs.sioc.Role has__function = de.m0ep.uni.ma.rdfs.sioc.Factory.createRole(_model.createResource(),_model);
		if (this.has__function == null)
			initHas__function();
		this.has__function.add(has__function);
		_model.add(_model.createStatement(_resource,has__functionProperty,has__function.resource()));
		return has__function;
	}
	
	public de.m0ep.uni.ma.rdfs.sioc.Role addHas__function(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		de.m0ep.uni.ma.rdfs.sioc.Role has__function = de.m0ep.uni.ma.rdfs.sioc.Factory.getRole(resource,_model);
		if (this.has__function == null)
			initHas__function();
		if (this.has__function.contains(has__function))
			return has__function;
		this.has__function.add(has__function);
		_model.add(_model.createStatement(_resource,has__functionProperty,has__function.resource()));
		return has__function;
	}
	
	public void removeHas__function(de.m0ep.uni.ma.rdfs.sioc.Role has__function) throws JastorException {
		if (this.has__function == null)
			initHas__function();
		if (!this.has__function.contains(has__function))
			return;
		if (!_model.contains(_resource, has__functionProperty, has__function.resource()))
			return;
		this.has__function.remove(has__function);
		_model.removeAll(_resource, has__functionProperty, has__function.resource());
	}
		 

	private void initRelated__to() throws JastorException {
		this.related__to = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, related__toProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#related_to properties in UserAccount model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				com.ibm.adtech.jastor.Thing related__to = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
				this.related__to.add(related__to);
			}
		}
	}

	public java.util.Iterator getRelated__to() throws JastorException {
		if (related__to == null)
			initRelated__to();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(related__to,_resource,related__toProperty,true);
	}

	public void addRelated__to(com.ibm.adtech.jastor.Thing related__to) throws JastorException {
		if (this.related__to == null)
			initRelated__to();
		if (this.related__to.contains(related__to)) {
			this.related__to.remove(related__to);
			this.related__to.add(related__to);
			return;
		}
		this.related__to.add(related__to);
		_model.add(_model.createStatement(_resource,related__toProperty,related__to.resource()));
	}
	
	public com.ibm.adtech.jastor.Thing addRelated__to() throws JastorException {
		com.ibm.adtech.jastor.Thing related__to = com.ibm.adtech.jastor.ThingFactory.createThing(_model.createResource(),_model);
		if (this.related__to == null)
			initRelated__to();
		this.related__to.add(related__to);
		_model.add(_model.createStatement(_resource,related__toProperty,related__to.resource()));
		return related__to;
	}
	
	public com.ibm.adtech.jastor.Thing addRelated__to(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		com.ibm.adtech.jastor.Thing related__to = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
		if (this.related__to == null)
			initRelated__to();
		if (this.related__to.contains(related__to))
			return related__to;
		this.related__to.add(related__to);
		_model.add(_model.createStatement(_resource,related__toProperty,related__to.resource()));
		return related__to;
	}
	
	public void removeRelated__to(com.ibm.adtech.jastor.Thing related__to) throws JastorException {
		if (this.related__to == null)
			initRelated__to();
		if (!this.related__to.contains(related__to))
			return;
		if (!_model.contains(_resource, related__toProperty, related__to.resource()))
			return;
		this.related__to.remove(related__to);
		_model.removeAll(_resource, related__toProperty, related__to.resource());
	}
		  


	private java.util.ArrayList listeners;
	
	public void registerListener(ThingListener listener) {
		if (!(listener instanceof UserAccountListener))
			throw new IllegalArgumentException("ThingListener must be instance of UserAccountListener"); 
		if (listeners == null)
			setupModelListener();
		if(!this.listeners.contains(listener)){
			this.listeners.add((UserAccountListener)listener);
		}
	}
	
	public void unregisterListener(ThingListener listener) {
		if (!(listener instanceof UserAccountListener))
			throw new IllegalArgumentException("ThingListener must be instance of UserAccountListener"); 
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
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.fundedByAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_fundedBy);
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
						UserAccountListener listener=(UserAccountListener)iter.next();
						listener.nameAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,literal);
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
						UserAccountListener listener=(UserAccountListener)iter.next();
						listener.dnaChecksumAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,literal);
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
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.phoneAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_phone);
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
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.pageAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_page);
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
						UserAccountListener listener=(UserAccountListener)iter.next();
						listener.nickAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,literal);
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
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.logoAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_logo);
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
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.makerAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_maker);
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
						UserAccountListener listener=(UserAccountListener)iter.next();
						listener.givennameAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,literal);
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
						UserAccountListener listener=(UserAccountListener)iter.next();
						listener.titleAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,literal);
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
						UserAccountListener listener=(UserAccountListener)iter.next();
						listener.givenNameAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,literal);
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
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.homepageAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_homepage);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(accountServiceHomepageProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.foaf.Document _accountServiceHomepage = null;
					try {
						_accountServiceHomepage = de.m0ep.uni.ma.rdfs.foaf.Factory.getDocument(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (accountServiceHomepage == null) {
						try {
							initAccountServiceHomepage();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!accountServiceHomepage.contains(_accountServiceHomepage))
						accountServiceHomepage.add(_accountServiceHomepage);
					if (listeners != null) {
						java.util.ArrayList consumersForAccountServiceHomepage;
						synchronized (listeners) {
							consumersForAccountServiceHomepage = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForAccountServiceHomepage.iterator();iter.hasNext();){
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.accountServiceHomepageAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_accountServiceHomepage);
						}
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
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.themeAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_theme);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(accountNameProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (accountName == null)
					try {
						initAccountName();
					} catch (JastorException e) {
						e.printStackTrace();
						return;
					}
				if (!accountName.contains(literal))
					accountName.add(literal);
				if (listeners != null) {
					java.util.ArrayList consumersForAccountName;
					synchronized (listeners) {
						consumersForAccountName = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForAccountName.iterator();iter.hasNext();){
						UserAccountListener listener=(UserAccountListener)iter.next();
						listener.accountNameAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(email__sha1Property)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (email__sha1 == null)
					try {
						initEmail__sha1();
					} catch (JastorException e) {
						e.printStackTrace();
						return;
					}
				if (!email__sha1.contains(literal))
					email__sha1.add(literal);
				if (listeners != null) {
					java.util.ArrayList consumersForEmail__sha1;
					synchronized (listeners) {
						consumersForEmail__sha1 = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForEmail__sha1.iterator();iter.hasNext();){
						UserAccountListener listener=(UserAccountListener)iter.next();
						listener.email__sha1Added(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(member__ofProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.sioc.Usergroup _member__of = null;
					try {
						_member__of = de.m0ep.uni.ma.rdfs.sioc.Factory.getUsergroup(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (member__of == null) {
						try {
							initMember__of();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!member__of.contains(_member__of))
						member__of.add(_member__of);
					if (listeners != null) {
						java.util.ArrayList consumersForMember__of;
						synchronized (listeners) {
							consumersForMember__of = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForMember__of.iterator();iter.hasNext();){
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.member__ofAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_member__of);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(creator__ofProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					com.ibm.adtech.jastor.Thing _creator__of = null;
					try {
						_creator__of = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (creator__of == null) {
						try {
							initCreator__of();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!creator__of.contains(_creator__of))
						creator__of.add(_creator__of);
					if (listeners != null) {
						java.util.ArrayList consumersForCreator__of;
						synchronized (listeners) {
							consumersForCreator__of = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForCreator__of.iterator();iter.hasNext();){
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.creator__ofAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_creator__of);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(moderator__ofProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.sioc.Forum _moderator__of = null;
					try {
						_moderator__of = de.m0ep.uni.ma.rdfs.sioc.Factory.getForum(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (moderator__of == null) {
						try {
							initModerator__of();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!moderator__of.contains(_moderator__of))
						moderator__of.add(_moderator__of);
					if (listeners != null) {
						java.util.ArrayList consumersForModerator__of;
						synchronized (listeners) {
							consumersForModerator__of = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForModerator__of.iterator();iter.hasNext();){
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.moderator__ofAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_moderator__of);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(followsProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.sioc.UserAccount _follows = null;
					try {
						_follows = de.m0ep.uni.ma.rdfs.sioc.Factory.getUserAccount(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (follows == null) {
						try {
							initFollows();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!follows.contains(_follows))
						follows.add(_follows);
					if (listeners != null) {
						java.util.ArrayList consumersForFollows;
						synchronized (listeners) {
							consumersForFollows = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForFollows.iterator();iter.hasNext();){
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.followsAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_follows);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(account__ofProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.foaf.Agent _account__of = null;
					try {
						_account__of = de.m0ep.uni.ma.rdfs.foaf.Factory.getAgent(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (account__of == null) {
						try {
							initAccount__of();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!account__of.contains(_account__of))
						account__of.add(_account__of);
					if (listeners != null) {
						java.util.ArrayList consumersForAccount__of;
						synchronized (listeners) {
							consumersForAccount__of = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForAccount__of.iterator();iter.hasNext();){
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.account__ofAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_account__of);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(owner__ofProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					com.ibm.adtech.jastor.Thing _owner__of = null;
					try {
						_owner__of = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (owner__of == null) {
						try {
							initOwner__of();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!owner__of.contains(_owner__of))
						owner__of.add(_owner__of);
					if (listeners != null) {
						java.util.ArrayList consumersForOwner__of;
						synchronized (listeners) {
							consumersForOwner__of = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForOwner__of.iterator();iter.hasNext();){
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.owner__ofAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_owner__of);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(emailProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					com.ibm.adtech.jastor.Thing _email = null;
					try {
						_email = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (email == null) {
						try {
							initEmail();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!email.contains(_email))
						email.add(_email);
					if (listeners != null) {
						java.util.ArrayList consumersForEmail;
						synchronized (listeners) {
							consumersForEmail = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForEmail.iterator();iter.hasNext();){
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.emailAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_email);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(avatarProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.foaf.Image _avatar = null;
					try {
						_avatar = de.m0ep.uni.ma.rdfs.foaf.Factory.getImage(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (avatar == null) {
						try {
							initAvatar();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!avatar.contains(_avatar))
						avatar.add(_avatar);
					if (listeners != null) {
						java.util.ArrayList consumersForAvatar;
						synchronized (listeners) {
							consumersForAvatar = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForAvatar.iterator();iter.hasNext();){
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.avatarAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_avatar);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(administrator__ofProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.sioc.Site _administrator__of = null;
					try {
						_administrator__of = de.m0ep.uni.ma.rdfs.sioc.Factory.getSite(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (administrator__of == null) {
						try {
							initAdministrator__of();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!administrator__of.contains(_administrator__of))
						administrator__of.add(_administrator__of);
					if (listeners != null) {
						java.util.ArrayList consumersForAdministrator__of;
						synchronized (listeners) {
							consumersForAdministrator__of = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForAdministrator__of.iterator();iter.hasNext();){
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.administrator__ofAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_administrator__of);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(modifier__ofProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.sioc.Item _modifier__of = null;
					try {
						_modifier__of = de.m0ep.uni.ma.rdfs.sioc.Factory.getItem(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (modifier__of == null) {
						try {
							initModifier__of();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!modifier__of.contains(_modifier__of))
						modifier__of.add(_modifier__of);
					if (listeners != null) {
						java.util.ArrayList consumersForModifier__of;
						synchronized (listeners) {
							consumersForModifier__of = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForModifier__of.iterator();iter.hasNext();){
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.modifier__ofAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_modifier__of);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(subscriber__ofProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.sioc.Container _subscriber__of = null;
					try {
						_subscriber__of = de.m0ep.uni.ma.rdfs.sioc.Factory.getContainer(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (subscriber__of == null) {
						try {
							initSubscriber__of();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!subscriber__of.contains(_subscriber__of))
						subscriber__of.add(_subscriber__of);
					if (listeners != null) {
						java.util.ArrayList consumersForSubscriber__of;
						synchronized (listeners) {
							consumersForSubscriber__of = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForSubscriber__of.iterator();iter.hasNext();){
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.subscriber__ofAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_subscriber__of);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(depictionProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.foaf.Image _depiction = null;
					try {
						_depiction = de.m0ep.uni.ma.rdfs.foaf.Factory.getImage(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (depiction == null) {
						try {
							initDepiction();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!depiction.contains(_depiction))
						depiction.add(_depiction);
					if (listeners != null) {
						java.util.ArrayList consumersForDepiction;
						synchronized (listeners) {
							consumersForDepiction = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForDepiction.iterator();iter.hasNext();){
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.depictionAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_depiction);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(idProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (id == null)
					try {
						initId();
					} catch (JastorException e) {
						e.printStackTrace();
						return;
					}
				if (!id.contains(literal))
					id.add(literal);
				if (listeners != null) {
					java.util.ArrayList consumersForId;
					synchronized (listeners) {
						consumersForId = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForId.iterator();iter.hasNext();){
						UserAccountListener listener=(UserAccountListener)iter.next();
						listener.idAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(num__authorsProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(true,literal,"java.lang.String","http://www.w3.org/2001/XMLSchema#nonNegativeInteger");
				if (num__authors == null) {
					try {
						initNum__authors();
					} catch (JastorException e) {
						e.printStackTrace();
						return;
					}
				}
				if (obj != null && !num__authors.contains(obj))
					num__authors.add(obj);
				java.util.ArrayList consumersForNum__authors;
				if (listeners != null) {
					synchronized (listeners) {
						consumersForNum__authors = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForNum__authors.iterator();iter.hasNext();){
						UserAccountListener listener=(UserAccountListener)iter.next();
						listener.num__authorsAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(noteProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (note == null)
					try {
						initNote();
					} catch (JastorException e) {
						e.printStackTrace();
						return;
					}
				if (!note.contains(literal))
					note.add(literal);
				if (listeners != null) {
					java.util.ArrayList consumersForNote;
					synchronized (listeners) {
						consumersForNote = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForNote.iterator();iter.hasNext();){
						UserAccountListener listener=(UserAccountListener)iter.next();
						listener.noteAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(ns1_nameProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (ns1_name == null)
					try {
						initNs1_Name();
					} catch (JastorException e) {
						e.printStackTrace();
						return;
					}
				if (!ns1_name.contains(literal))
					ns1_name.add(literal);
				if (listeners != null) {
					java.util.ArrayList consumersForNs1_Name;
					synchronized (listeners) {
						consumersForNs1_Name = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForNs1_Name.iterator();iter.hasNext();){
						UserAccountListener listener=(UserAccountListener)iter.next();
						listener.ns1_nameAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(num__viewsProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(true,literal,"java.lang.String","http://www.w3.org/2001/XMLSchema#nonNegativeInteger");
				if (num__views == null) {
					try {
						initNum__views();
					} catch (JastorException e) {
						e.printStackTrace();
						return;
					}
				}
				if (obj != null && !num__views.contains(obj))
					num__views.add(obj);
				java.util.ArrayList consumersForNum__views;
				if (listeners != null) {
					synchronized (listeners) {
						consumersForNum__views = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForNum__views.iterator();iter.hasNext();){
						UserAccountListener listener=(UserAccountListener)iter.next();
						listener.num__viewsAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(descriptionProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (description == null)
					try {
						initDescription();
					} catch (JastorException e) {
						e.printStackTrace();
						return;
					}
				if (!description.contains(literal))
					description.add(literal);
				if (listeners != null) {
					java.util.ArrayList consumersForDescription;
					synchronized (listeners) {
						consumersForDescription = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForDescription.iterator();iter.hasNext();){
						UserAccountListener listener=(UserAccountListener)iter.next();
						listener.descriptionAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(dateProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (date == null)
					try {
						initDate();
					} catch (JastorException e) {
						e.printStackTrace();
						return;
					}
				if (!date.contains(literal))
					date.add(literal);
				if (listeners != null) {
					java.util.ArrayList consumersForDate;
					synchronized (listeners) {
						consumersForDate = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForDate.iterator();iter.hasNext();){
						UserAccountListener listener=(UserAccountListener)iter.next();
						listener.dateAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(num__repliesProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(true,literal,"java.lang.String","http://www.w3.org/2001/XMLSchema#nonNegativeInteger");
				if (num__replies == null) {
					try {
						initNum__replies();
					} catch (JastorException e) {
						e.printStackTrace();
						return;
					}
				}
				if (obj != null && !num__replies.contains(obj))
					num__replies.add(obj);
				java.util.ArrayList consumersForNum__replies;
				if (listeners != null) {
					synchronized (listeners) {
						consumersForNum__replies = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForNum__replies.iterator();iter.hasNext();){
						UserAccountListener listener=(UserAccountListener)iter.next();
						listener.num__repliesAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(last__activity__dateProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (last__activity__date == null)
					try {
						initLast__activity__date();
					} catch (JastorException e) {
						e.printStackTrace();
						return;
					}
				if (!last__activity__date.contains(literal))
					last__activity__date.add(literal);
				if (listeners != null) {
					java.util.ArrayList consumersForLast__activity__date;
					synchronized (listeners) {
						consumersForLast__activity__date = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForLast__activity__date.iterator();iter.hasNext();){
						UserAccountListener listener=(UserAccountListener)iter.next();
						listener.last__activity__dateAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(last__reply__dateProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (last__reply__date == null)
					try {
						initLast__reply__date();
					} catch (JastorException e) {
						e.printStackTrace();
						return;
					}
				if (!last__reply__date.contains(literal))
					last__reply__date.add(literal);
				if (listeners != null) {
					java.util.ArrayList consumersForLast__reply__date;
					synchronized (listeners) {
						consumersForLast__reply__date = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForLast__reply__date.iterator();iter.hasNext();){
						UserAccountListener listener=(UserAccountListener)iter.next();
						listener.last__reply__dateAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(ns2_titleProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (ns2_title == null)
					try {
						initNs2_Title();
					} catch (JastorException e) {
						e.printStackTrace();
						return;
					}
				if (!ns2_title.contains(literal))
					ns2_title.add(literal);
				if (listeners != null) {
					java.util.ArrayList consumersForNs2_Title;
					synchronized (listeners) {
						consumersForNs2_Title = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForNs2_Title.iterator();iter.hasNext();){
						UserAccountListener listener=(UserAccountListener)iter.next();
						listener.ns2_titleAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(subjectProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					com.ibm.adtech.jastor.Thing _subject = null;
					try {
						_subject = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (subject == null) {
						try {
							initSubject();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!subject.contains(_subject))
						subject.add(_subject);
					if (listeners != null) {
						java.util.ArrayList consumersForSubject;
						synchronized (listeners) {
							consumersForSubject = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForSubject.iterator();iter.hasNext();){
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.subjectAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_subject);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(referencesProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					com.ibm.adtech.jastor.Thing _references = null;
					try {
						_references = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (references == null) {
						try {
							initReferences();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!references.contains(_references))
						references.add(_references);
					if (listeners != null) {
						java.util.ArrayList consumersForReferences;
						synchronized (listeners) {
							consumersForReferences = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForReferences.iterator();iter.hasNext();){
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.referencesAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_references);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(links__toProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					com.ibm.adtech.jastor.Thing _links__to = null;
					try {
						_links__to = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (links__to == null) {
						try {
							initLinks__to();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!links__to.contains(_links__to))
						links__to.add(_links__to);
					if (listeners != null) {
						java.util.ArrayList consumersForLinks__to;
						synchronized (listeners) {
							consumersForLinks__to = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForLinks__to.iterator();iter.hasNext();){
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.links__toAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_links__to);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(feedProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					com.ibm.adtech.jastor.Thing _feed = null;
					try {
						_feed = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (feed == null) {
						try {
							initFeed();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!feed.contains(_feed))
						feed.add(_feed);
					if (listeners != null) {
						java.util.ArrayList consumersForFeed;
						synchronized (listeners) {
							consumersForFeed = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForFeed.iterator();iter.hasNext();){
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.feedAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_feed);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(has__spaceProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.sioc.Space _has__space = null;
					try {
						_has__space = de.m0ep.uni.ma.rdfs.sioc.Factory.getSpace(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (has__space == null) {
						try {
							initHas__space();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!has__space.contains(_has__space))
						has__space.add(_has__space);
					if (listeners != null) {
						java.util.ArrayList consumersForHas__space;
						synchronized (listeners) {
							consumersForHas__space = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForHas__space.iterator();iter.hasNext();){
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.has__spaceAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_has__space);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(scope__ofProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.sioc.Role _scope__of = null;
					try {
						_scope__of = de.m0ep.uni.ma.rdfs.sioc.Factory.getRole(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (scope__of == null) {
						try {
							initScope__of();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!scope__of.contains(_scope__of))
						scope__of.add(_scope__of);
					if (listeners != null) {
						java.util.ArrayList consumersForScope__of;
						synchronized (listeners) {
							consumersForScope__of = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForScope__of.iterator();iter.hasNext();){
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.scope__ofAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_scope__of);
						}
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
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.topicAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_topic);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(linkProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					com.ibm.adtech.jastor.Thing _link = null;
					try {
						_link = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (link == null) {
						try {
							initLink();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!link.contains(_link))
						link.add(_link);
					if (listeners != null) {
						java.util.ArrayList consumersForLink;
						synchronized (listeners) {
							consumersForLink = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForLink.iterator();iter.hasNext();){
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.linkAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_link);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(has__creatorProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.sioc.UserAccount _has__creator = null;
					try {
						_has__creator = de.m0ep.uni.ma.rdfs.sioc.Factory.getUserAccount(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (has__creator == null) {
						try {
							initHas__creator();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!has__creator.contains(_has__creator))
						has__creator.add(_has__creator);
					if (listeners != null) {
						java.util.ArrayList consumersForHas__creator;
						synchronized (listeners) {
							consumersForHas__creator = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForHas__creator.iterator();iter.hasNext();){
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.has__creatorAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_has__creator);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(has__ownerProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.sioc.UserAccount _has__owner = null;
					try {
						_has__owner = de.m0ep.uni.ma.rdfs.sioc.Factory.getUserAccount(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (has__owner == null) {
						try {
							initHas__owner();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!has__owner.contains(_has__owner))
						has__owner.add(_has__owner);
					if (listeners != null) {
						java.util.ArrayList consumersForHas__owner;
						synchronized (listeners) {
							consumersForHas__owner = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForHas__owner.iterator();iter.hasNext();){
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.has__ownerAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_has__owner);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(has__functionProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.sioc.Role _has__function = null;
					try {
						_has__function = de.m0ep.uni.ma.rdfs.sioc.Factory.getRole(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (has__function == null) {
						try {
							initHas__function();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!has__function.contains(_has__function))
						has__function.add(_has__function);
					if (listeners != null) {
						java.util.ArrayList consumersForHas__function;
						synchronized (listeners) {
							consumersForHas__function = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForHas__function.iterator();iter.hasNext();){
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.has__functionAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_has__function);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(related__toProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					com.ibm.adtech.jastor.Thing _related__to = null;
					try {
						_related__to = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (related__to == null) {
						try {
							initRelated__to();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!related__to.contains(_related__to))
						related__to.add(_related__to);
					if (listeners != null) {
						java.util.ArrayList consumersForRelated__to;
						synchronized (listeners) {
							consumersForRelated__to = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForRelated__to.iterator();iter.hasNext();){
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.related__toAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_related__to);
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
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.fundedByRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_fundedBy);
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
						UserAccountListener listener=(UserAccountListener)iter.next();
						listener.nameRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,literal);
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
						UserAccountListener listener=(UserAccountListener)iter.next();
						listener.dnaChecksumRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,literal);
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
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.phoneRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_phone);
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
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.pageRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_page);
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
						UserAccountListener listener=(UserAccountListener)iter.next();
						listener.nickRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,literal);
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
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.logoRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_logo);
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
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.makerRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_maker);
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
						UserAccountListener listener=(UserAccountListener)iter.next();
						listener.givennameRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,literal);
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
						UserAccountListener listener=(UserAccountListener)iter.next();
						listener.titleRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,literal);
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
						UserAccountListener listener=(UserAccountListener)iter.next();
						listener.givenNameRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,literal);
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
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.homepageRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_homepage);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(accountServiceHomepageProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.foaf.Document _accountServiceHomepage = null;
					if (accountServiceHomepage != null) {
						boolean found = false;
						for (int i=0;i<accountServiceHomepage.size();i++) {
							de.m0ep.uni.ma.rdfs.foaf.Document __item = (de.m0ep.uni.ma.rdfs.foaf.Document) accountServiceHomepage.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_accountServiceHomepage = __item;
								break;
							}
						}
						if (found)
							accountServiceHomepage.remove(_accountServiceHomepage);
						else {
							try {
								_accountServiceHomepage = de.m0ep.uni.ma.rdfs.foaf.Factory.getDocument(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_accountServiceHomepage = de.m0ep.uni.ma.rdfs.foaf.Factory.getDocument(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForAccountServiceHomepage;
						synchronized (listeners) {
							consumersForAccountServiceHomepage = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForAccountServiceHomepage.iterator();iter.hasNext();){
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.accountServiceHomepageRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_accountServiceHomepage);
						}
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
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.themeRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_theme);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(accountNameProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (accountName != null) {
					if (accountName.contains(literal))
						accountName.remove(literal);
				}
				if (listeners != null) {
					java.util.ArrayList consumersForAccountName;
					synchronized (listeners) {
						consumersForAccountName = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForAccountName.iterator();iter.hasNext();){
						UserAccountListener listener=(UserAccountListener)iter.next();
						listener.accountNameRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(email__sha1Property)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (email__sha1 != null) {
					if (email__sha1.contains(literal))
						email__sha1.remove(literal);
				}
				if (listeners != null) {
					java.util.ArrayList consumersForEmail__sha1;
					synchronized (listeners) {
						consumersForEmail__sha1 = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForEmail__sha1.iterator();iter.hasNext();){
						UserAccountListener listener=(UserAccountListener)iter.next();
						listener.email__sha1Removed(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(member__ofProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.sioc.Usergroup _member__of = null;
					if (member__of != null) {
						boolean found = false;
						for (int i=0;i<member__of.size();i++) {
							de.m0ep.uni.ma.rdfs.sioc.Usergroup __item = (de.m0ep.uni.ma.rdfs.sioc.Usergroup) member__of.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_member__of = __item;
								break;
							}
						}
						if (found)
							member__of.remove(_member__of);
						else {
							try {
								_member__of = de.m0ep.uni.ma.rdfs.sioc.Factory.getUsergroup(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_member__of = de.m0ep.uni.ma.rdfs.sioc.Factory.getUsergroup(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForMember__of;
						synchronized (listeners) {
							consumersForMember__of = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForMember__of.iterator();iter.hasNext();){
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.member__ofRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_member__of);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(creator__ofProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					com.ibm.adtech.jastor.Thing _creator__of = null;
					if (creator__of != null) {
						boolean found = false;
						for (int i=0;i<creator__of.size();i++) {
							com.ibm.adtech.jastor.Thing __item = (com.ibm.adtech.jastor.Thing) creator__of.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_creator__of = __item;
								break;
							}
						}
						if (found)
							creator__of.remove(_creator__of);
						else {
							try {
								_creator__of = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_creator__of = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForCreator__of;
						synchronized (listeners) {
							consumersForCreator__of = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForCreator__of.iterator();iter.hasNext();){
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.creator__ofRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_creator__of);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(moderator__ofProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.sioc.Forum _moderator__of = null;
					if (moderator__of != null) {
						boolean found = false;
						for (int i=0;i<moderator__of.size();i++) {
							de.m0ep.uni.ma.rdfs.sioc.Forum __item = (de.m0ep.uni.ma.rdfs.sioc.Forum) moderator__of.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_moderator__of = __item;
								break;
							}
						}
						if (found)
							moderator__of.remove(_moderator__of);
						else {
							try {
								_moderator__of = de.m0ep.uni.ma.rdfs.sioc.Factory.getForum(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_moderator__of = de.m0ep.uni.ma.rdfs.sioc.Factory.getForum(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForModerator__of;
						synchronized (listeners) {
							consumersForModerator__of = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForModerator__of.iterator();iter.hasNext();){
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.moderator__ofRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_moderator__of);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(followsProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.sioc.UserAccount _follows = null;
					if (follows != null) {
						boolean found = false;
						for (int i=0;i<follows.size();i++) {
							de.m0ep.uni.ma.rdfs.sioc.UserAccount __item = (de.m0ep.uni.ma.rdfs.sioc.UserAccount) follows.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_follows = __item;
								break;
							}
						}
						if (found)
							follows.remove(_follows);
						else {
							try {
								_follows = de.m0ep.uni.ma.rdfs.sioc.Factory.getUserAccount(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_follows = de.m0ep.uni.ma.rdfs.sioc.Factory.getUserAccount(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForFollows;
						synchronized (listeners) {
							consumersForFollows = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForFollows.iterator();iter.hasNext();){
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.followsRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_follows);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(account__ofProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.foaf.Agent _account__of = null;
					if (account__of != null) {
						boolean found = false;
						for (int i=0;i<account__of.size();i++) {
							de.m0ep.uni.ma.rdfs.foaf.Agent __item = (de.m0ep.uni.ma.rdfs.foaf.Agent) account__of.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_account__of = __item;
								break;
							}
						}
						if (found)
							account__of.remove(_account__of);
						else {
							try {
								_account__of = de.m0ep.uni.ma.rdfs.foaf.Factory.getAgent(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_account__of = de.m0ep.uni.ma.rdfs.foaf.Factory.getAgent(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForAccount__of;
						synchronized (listeners) {
							consumersForAccount__of = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForAccount__of.iterator();iter.hasNext();){
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.account__ofRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_account__of);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(owner__ofProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					com.ibm.adtech.jastor.Thing _owner__of = null;
					if (owner__of != null) {
						boolean found = false;
						for (int i=0;i<owner__of.size();i++) {
							com.ibm.adtech.jastor.Thing __item = (com.ibm.adtech.jastor.Thing) owner__of.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_owner__of = __item;
								break;
							}
						}
						if (found)
							owner__of.remove(_owner__of);
						else {
							try {
								_owner__of = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_owner__of = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForOwner__of;
						synchronized (listeners) {
							consumersForOwner__of = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForOwner__of.iterator();iter.hasNext();){
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.owner__ofRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_owner__of);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(emailProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					com.ibm.adtech.jastor.Thing _email = null;
					if (email != null) {
						boolean found = false;
						for (int i=0;i<email.size();i++) {
							com.ibm.adtech.jastor.Thing __item = (com.ibm.adtech.jastor.Thing) email.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_email = __item;
								break;
							}
						}
						if (found)
							email.remove(_email);
						else {
							try {
								_email = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_email = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForEmail;
						synchronized (listeners) {
							consumersForEmail = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForEmail.iterator();iter.hasNext();){
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.emailRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_email);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(avatarProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.foaf.Image _avatar = null;
					if (avatar != null) {
						boolean found = false;
						for (int i=0;i<avatar.size();i++) {
							de.m0ep.uni.ma.rdfs.foaf.Image __item = (de.m0ep.uni.ma.rdfs.foaf.Image) avatar.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_avatar = __item;
								break;
							}
						}
						if (found)
							avatar.remove(_avatar);
						else {
							try {
								_avatar = de.m0ep.uni.ma.rdfs.foaf.Factory.getImage(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_avatar = de.m0ep.uni.ma.rdfs.foaf.Factory.getImage(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForAvatar;
						synchronized (listeners) {
							consumersForAvatar = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForAvatar.iterator();iter.hasNext();){
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.avatarRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_avatar);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(administrator__ofProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.sioc.Site _administrator__of = null;
					if (administrator__of != null) {
						boolean found = false;
						for (int i=0;i<administrator__of.size();i++) {
							de.m0ep.uni.ma.rdfs.sioc.Site __item = (de.m0ep.uni.ma.rdfs.sioc.Site) administrator__of.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_administrator__of = __item;
								break;
							}
						}
						if (found)
							administrator__of.remove(_administrator__of);
						else {
							try {
								_administrator__of = de.m0ep.uni.ma.rdfs.sioc.Factory.getSite(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_administrator__of = de.m0ep.uni.ma.rdfs.sioc.Factory.getSite(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForAdministrator__of;
						synchronized (listeners) {
							consumersForAdministrator__of = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForAdministrator__of.iterator();iter.hasNext();){
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.administrator__ofRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_administrator__of);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(modifier__ofProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.sioc.Item _modifier__of = null;
					if (modifier__of != null) {
						boolean found = false;
						for (int i=0;i<modifier__of.size();i++) {
							de.m0ep.uni.ma.rdfs.sioc.Item __item = (de.m0ep.uni.ma.rdfs.sioc.Item) modifier__of.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_modifier__of = __item;
								break;
							}
						}
						if (found)
							modifier__of.remove(_modifier__of);
						else {
							try {
								_modifier__of = de.m0ep.uni.ma.rdfs.sioc.Factory.getItem(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_modifier__of = de.m0ep.uni.ma.rdfs.sioc.Factory.getItem(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForModifier__of;
						synchronized (listeners) {
							consumersForModifier__of = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForModifier__of.iterator();iter.hasNext();){
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.modifier__ofRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_modifier__of);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(subscriber__ofProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.sioc.Container _subscriber__of = null;
					if (subscriber__of != null) {
						boolean found = false;
						for (int i=0;i<subscriber__of.size();i++) {
							de.m0ep.uni.ma.rdfs.sioc.Container __item = (de.m0ep.uni.ma.rdfs.sioc.Container) subscriber__of.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_subscriber__of = __item;
								break;
							}
						}
						if (found)
							subscriber__of.remove(_subscriber__of);
						else {
							try {
								_subscriber__of = de.m0ep.uni.ma.rdfs.sioc.Factory.getContainer(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_subscriber__of = de.m0ep.uni.ma.rdfs.sioc.Factory.getContainer(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForSubscriber__of;
						synchronized (listeners) {
							consumersForSubscriber__of = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForSubscriber__of.iterator();iter.hasNext();){
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.subscriber__ofRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_subscriber__of);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(depictionProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.foaf.Image _depiction = null;
					if (depiction != null) {
						boolean found = false;
						for (int i=0;i<depiction.size();i++) {
							de.m0ep.uni.ma.rdfs.foaf.Image __item = (de.m0ep.uni.ma.rdfs.foaf.Image) depiction.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_depiction = __item;
								break;
							}
						}
						if (found)
							depiction.remove(_depiction);
						else {
							try {
								_depiction = de.m0ep.uni.ma.rdfs.foaf.Factory.getImage(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_depiction = de.m0ep.uni.ma.rdfs.foaf.Factory.getImage(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForDepiction;
						synchronized (listeners) {
							consumersForDepiction = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForDepiction.iterator();iter.hasNext();){
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.depictionRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_depiction);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(idProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (id != null) {
					if (id.contains(literal))
						id.remove(literal);
				}
				if (listeners != null) {
					java.util.ArrayList consumersForId;
					synchronized (listeners) {
						consumersForId = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForId.iterator();iter.hasNext();){
						UserAccountListener listener=(UserAccountListener)iter.next();
						listener.idRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(num__authorsProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(true,literal,"java.lang.String","http://www.w3.org/2001/XMLSchema#nonNegativeInteger");
				if (num__authors != null) {
					if (num__authors.contains(obj))
						num__authors.remove(obj);
				}
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						UserAccountListener listener=(UserAccountListener)iter.next();
						listener.num__authorsRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(noteProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (note != null) {
					if (note.contains(literal))
						note.remove(literal);
				}
				if (listeners != null) {
					java.util.ArrayList consumersForNote;
					synchronized (listeners) {
						consumersForNote = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForNote.iterator();iter.hasNext();){
						UserAccountListener listener=(UserAccountListener)iter.next();
						listener.noteRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(ns1_nameProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (ns1_name != null) {
					if (ns1_name.contains(literal))
						ns1_name.remove(literal);
				}
				if (listeners != null) {
					java.util.ArrayList consumersForNs1_Name;
					synchronized (listeners) {
						consumersForNs1_Name = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForNs1_Name.iterator();iter.hasNext();){
						UserAccountListener listener=(UserAccountListener)iter.next();
						listener.ns1_nameRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(num__viewsProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(true,literal,"java.lang.String","http://www.w3.org/2001/XMLSchema#nonNegativeInteger");
				if (num__views != null) {
					if (num__views.contains(obj))
						num__views.remove(obj);
				}
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						UserAccountListener listener=(UserAccountListener)iter.next();
						listener.num__viewsRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(descriptionProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (description != null) {
					if (description.contains(literal))
						description.remove(literal);
				}
				if (listeners != null) {
					java.util.ArrayList consumersForDescription;
					synchronized (listeners) {
						consumersForDescription = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForDescription.iterator();iter.hasNext();){
						UserAccountListener listener=(UserAccountListener)iter.next();
						listener.descriptionRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(dateProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (date != null) {
					if (date.contains(literal))
						date.remove(literal);
				}
				if (listeners != null) {
					java.util.ArrayList consumersForDate;
					synchronized (listeners) {
						consumersForDate = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForDate.iterator();iter.hasNext();){
						UserAccountListener listener=(UserAccountListener)iter.next();
						listener.dateRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(num__repliesProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				Object obj = Util.fixLiteral(true,literal,"java.lang.String","http://www.w3.org/2001/XMLSchema#nonNegativeInteger");
				if (num__replies != null) {
					if (num__replies.contains(obj))
						num__replies.remove(obj);
				}
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						UserAccountListener listener=(UserAccountListener)iter.next();
						listener.num__repliesRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(last__activity__dateProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (last__activity__date != null) {
					if (last__activity__date.contains(literal))
						last__activity__date.remove(literal);
				}
				if (listeners != null) {
					java.util.ArrayList consumersForLast__activity__date;
					synchronized (listeners) {
						consumersForLast__activity__date = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForLast__activity__date.iterator();iter.hasNext();){
						UserAccountListener listener=(UserAccountListener)iter.next();
						listener.last__activity__dateRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(last__reply__dateProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (last__reply__date != null) {
					if (last__reply__date.contains(literal))
						last__reply__date.remove(literal);
				}
				if (listeners != null) {
					java.util.ArrayList consumersForLast__reply__date;
					synchronized (listeners) {
						consumersForLast__reply__date = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForLast__reply__date.iterator();iter.hasNext();){
						UserAccountListener listener=(UserAccountListener)iter.next();
						listener.last__reply__dateRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(ns2_titleProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (ns2_title != null) {
					if (ns2_title.contains(literal))
						ns2_title.remove(literal);
				}
				if (listeners != null) {
					java.util.ArrayList consumersForNs2_Title;
					synchronized (listeners) {
						consumersForNs2_Title = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForNs2_Title.iterator();iter.hasNext();){
						UserAccountListener listener=(UserAccountListener)iter.next();
						listener.ns2_titleRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(subjectProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					com.ibm.adtech.jastor.Thing _subject = null;
					if (subject != null) {
						boolean found = false;
						for (int i=0;i<subject.size();i++) {
							com.ibm.adtech.jastor.Thing __item = (com.ibm.adtech.jastor.Thing) subject.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_subject = __item;
								break;
							}
						}
						if (found)
							subject.remove(_subject);
						else {
							try {
								_subject = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_subject = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForSubject;
						synchronized (listeners) {
							consumersForSubject = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForSubject.iterator();iter.hasNext();){
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.subjectRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_subject);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(referencesProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					com.ibm.adtech.jastor.Thing _references = null;
					if (references != null) {
						boolean found = false;
						for (int i=0;i<references.size();i++) {
							com.ibm.adtech.jastor.Thing __item = (com.ibm.adtech.jastor.Thing) references.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_references = __item;
								break;
							}
						}
						if (found)
							references.remove(_references);
						else {
							try {
								_references = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_references = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForReferences;
						synchronized (listeners) {
							consumersForReferences = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForReferences.iterator();iter.hasNext();){
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.referencesRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_references);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(links__toProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					com.ibm.adtech.jastor.Thing _links__to = null;
					if (links__to != null) {
						boolean found = false;
						for (int i=0;i<links__to.size();i++) {
							com.ibm.adtech.jastor.Thing __item = (com.ibm.adtech.jastor.Thing) links__to.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_links__to = __item;
								break;
							}
						}
						if (found)
							links__to.remove(_links__to);
						else {
							try {
								_links__to = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_links__to = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForLinks__to;
						synchronized (listeners) {
							consumersForLinks__to = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForLinks__to.iterator();iter.hasNext();){
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.links__toRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_links__to);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(feedProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					com.ibm.adtech.jastor.Thing _feed = null;
					if (feed != null) {
						boolean found = false;
						for (int i=0;i<feed.size();i++) {
							com.ibm.adtech.jastor.Thing __item = (com.ibm.adtech.jastor.Thing) feed.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_feed = __item;
								break;
							}
						}
						if (found)
							feed.remove(_feed);
						else {
							try {
								_feed = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_feed = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForFeed;
						synchronized (listeners) {
							consumersForFeed = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForFeed.iterator();iter.hasNext();){
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.feedRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_feed);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(has__spaceProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.sioc.Space _has__space = null;
					if (has__space != null) {
						boolean found = false;
						for (int i=0;i<has__space.size();i++) {
							de.m0ep.uni.ma.rdfs.sioc.Space __item = (de.m0ep.uni.ma.rdfs.sioc.Space) has__space.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_has__space = __item;
								break;
							}
						}
						if (found)
							has__space.remove(_has__space);
						else {
							try {
								_has__space = de.m0ep.uni.ma.rdfs.sioc.Factory.getSpace(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_has__space = de.m0ep.uni.ma.rdfs.sioc.Factory.getSpace(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForHas__space;
						synchronized (listeners) {
							consumersForHas__space = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForHas__space.iterator();iter.hasNext();){
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.has__spaceRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_has__space);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(scope__ofProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.sioc.Role _scope__of = null;
					if (scope__of != null) {
						boolean found = false;
						for (int i=0;i<scope__of.size();i++) {
							de.m0ep.uni.ma.rdfs.sioc.Role __item = (de.m0ep.uni.ma.rdfs.sioc.Role) scope__of.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_scope__of = __item;
								break;
							}
						}
						if (found)
							scope__of.remove(_scope__of);
						else {
							try {
								_scope__of = de.m0ep.uni.ma.rdfs.sioc.Factory.getRole(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_scope__of = de.m0ep.uni.ma.rdfs.sioc.Factory.getRole(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForScope__of;
						synchronized (listeners) {
							consumersForScope__of = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForScope__of.iterator();iter.hasNext();){
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.scope__ofRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_scope__of);
						}
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
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.topicRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_topic);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(linkProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					com.ibm.adtech.jastor.Thing _link = null;
					if (link != null) {
						boolean found = false;
						for (int i=0;i<link.size();i++) {
							com.ibm.adtech.jastor.Thing __item = (com.ibm.adtech.jastor.Thing) link.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_link = __item;
								break;
							}
						}
						if (found)
							link.remove(_link);
						else {
							try {
								_link = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_link = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForLink;
						synchronized (listeners) {
							consumersForLink = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForLink.iterator();iter.hasNext();){
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.linkRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_link);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(has__creatorProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.sioc.UserAccount _has__creator = null;
					if (has__creator != null) {
						boolean found = false;
						for (int i=0;i<has__creator.size();i++) {
							de.m0ep.uni.ma.rdfs.sioc.UserAccount __item = (de.m0ep.uni.ma.rdfs.sioc.UserAccount) has__creator.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_has__creator = __item;
								break;
							}
						}
						if (found)
							has__creator.remove(_has__creator);
						else {
							try {
								_has__creator = de.m0ep.uni.ma.rdfs.sioc.Factory.getUserAccount(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_has__creator = de.m0ep.uni.ma.rdfs.sioc.Factory.getUserAccount(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForHas__creator;
						synchronized (listeners) {
							consumersForHas__creator = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForHas__creator.iterator();iter.hasNext();){
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.has__creatorRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_has__creator);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(has__ownerProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.sioc.UserAccount _has__owner = null;
					if (has__owner != null) {
						boolean found = false;
						for (int i=0;i<has__owner.size();i++) {
							de.m0ep.uni.ma.rdfs.sioc.UserAccount __item = (de.m0ep.uni.ma.rdfs.sioc.UserAccount) has__owner.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_has__owner = __item;
								break;
							}
						}
						if (found)
							has__owner.remove(_has__owner);
						else {
							try {
								_has__owner = de.m0ep.uni.ma.rdfs.sioc.Factory.getUserAccount(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_has__owner = de.m0ep.uni.ma.rdfs.sioc.Factory.getUserAccount(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForHas__owner;
						synchronized (listeners) {
							consumersForHas__owner = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForHas__owner.iterator();iter.hasNext();){
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.has__ownerRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_has__owner);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(has__functionProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.sioc.Role _has__function = null;
					if (has__function != null) {
						boolean found = false;
						for (int i=0;i<has__function.size();i++) {
							de.m0ep.uni.ma.rdfs.sioc.Role __item = (de.m0ep.uni.ma.rdfs.sioc.Role) has__function.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_has__function = __item;
								break;
							}
						}
						if (found)
							has__function.remove(_has__function);
						else {
							try {
								_has__function = de.m0ep.uni.ma.rdfs.sioc.Factory.getRole(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_has__function = de.m0ep.uni.ma.rdfs.sioc.Factory.getRole(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForHas__function;
						synchronized (listeners) {
							consumersForHas__function = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForHas__function.iterator();iter.hasNext();){
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.has__functionRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_has__function);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(related__toProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					com.ibm.adtech.jastor.Thing _related__to = null;
					if (related__to != null) {
						boolean found = false;
						for (int i=0;i<related__to.size();i++) {
							com.ibm.adtech.jastor.Thing __item = (com.ibm.adtech.jastor.Thing) related__to.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_related__to = __item;
								break;
							}
						}
						if (found)
							related__to.remove(_related__to);
						else {
							try {
								_related__to = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_related__to = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForRelated__to;
						synchronized (listeners) {
							consumersForRelated__to = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForRelated__to.iterator();iter.hasNext();){
							UserAccountListener listener=(UserAccountListener)iter.next();
							listener.related__toRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.this,_related__to);
						}
					}
				}
				return;
			}
		}

	//}
	


}