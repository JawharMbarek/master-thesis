

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
 * Implementation of {@link de.m0ep.uni.ma.rdfs.foaf.Group}
 * Use the de.m0ep.uni.ma.rdfs.foaf.Factory to create instances of this class.
 * <p>(URI: http://xmlns.com/foaf/0.1/Group)</p>
 * <br>
 */
public class GroupImpl extends com.ibm.adtech.jastor.ThingImpl implements de.m0ep.uni.ma.rdfs.foaf.Group {
	

	private static com.hp.hpl.jena.rdf.model.Property fundedByProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/fundedBy");
	private java.util.ArrayList fundedBy;
	private static com.hp.hpl.jena.rdf.model.Property mboxProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/mbox");
	private java.util.ArrayList mbox;
	private static com.hp.hpl.jena.rdf.model.Property nameProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/name");
	private java.util.ArrayList name;
	private static com.hp.hpl.jena.rdf.model.Property dnaChecksumProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/dnaChecksum");
	private java.util.ArrayList dnaChecksum;
	private static com.hp.hpl.jena.rdf.model.Property ageProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/age");
	private com.hp.hpl.jena.rdf.model.Literal age;
	private static com.hp.hpl.jena.rdf.model.Property phoneProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/phone");
	private java.util.ArrayList phone;
	private static com.hp.hpl.jena.rdf.model.Property genderProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/gender");
	private com.hp.hpl.jena.rdf.model.Literal gender;
	private static com.hp.hpl.jena.rdf.model.Property pageProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/page");
	private java.util.ArrayList page;
	private static com.hp.hpl.jena.rdf.model.Property nickProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/nick");
	private java.util.ArrayList nick;
	private static com.hp.hpl.jena.rdf.model.Property logoProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/logo");
	private java.util.ArrayList logo;
	private static com.hp.hpl.jena.rdf.model.Property weblogProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/weblog");
	private java.util.ArrayList weblog;
	private static com.hp.hpl.jena.rdf.model.Property makerProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/maker");
	private java.util.ArrayList maker;
	private static com.hp.hpl.jena.rdf.model.Property givennameProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/givenname");
	private java.util.ArrayList givenname;
	private static com.hp.hpl.jena.rdf.model.Property accountProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/account");
	private java.util.ArrayList account;
	private static com.hp.hpl.jena.rdf.model.Property openidProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/openid");
	private java.util.ArrayList openid;
	private static com.hp.hpl.jena.rdf.model.Property titleProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/title");
	private java.util.ArrayList title;
	private static com.hp.hpl.jena.rdf.model.Property birthdayProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/birthday");
	private com.hp.hpl.jena.rdf.model.Literal birthday;
	private static com.hp.hpl.jena.rdf.model.Property givenNameProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/givenName");
	private java.util.ArrayList givenName;
	private static com.hp.hpl.jena.rdf.model.Property homepageProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/homepage");
	private java.util.ArrayList homepage;
	private static com.hp.hpl.jena.rdf.model.Property yahooChatIDProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/yahooChatID");
	private java.util.ArrayList yahooChatID;
	private static com.hp.hpl.jena.rdf.model.Property icqChatIDProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/icqChatID");
	private java.util.ArrayList icqChatID;
	private static com.hp.hpl.jena.rdf.model.Property holdsAccountProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/holdsAccount");
	private java.util.ArrayList holdsAccount;
	private static com.hp.hpl.jena.rdf.model.Property themeProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/theme");
	private java.util.ArrayList theme;
	private static com.hp.hpl.jena.rdf.model.Property madeProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/made");
	private java.util.ArrayList made;
	private static com.hp.hpl.jena.rdf.model.Property mbox__sha1sumProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/mbox_sha1sum");
	private java.util.ArrayList mbox__sha1sum;
	private static com.hp.hpl.jena.rdf.model.Property topic__interestProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/topic_interest");
	private java.util.ArrayList topic__interest;
	private static com.hp.hpl.jena.rdf.model.Property interestProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/interest");
	private java.util.ArrayList interest;
	private static com.hp.hpl.jena.rdf.model.Property aimChatIDProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/aimChatID");
	private java.util.ArrayList aimChatID;
	private static com.hp.hpl.jena.rdf.model.Property skypeIDProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/skypeID");
	private java.util.ArrayList skypeID;
	private static com.hp.hpl.jena.rdf.model.Property tipjarProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/tipjar");
	private java.util.ArrayList tipjar;
	private static com.hp.hpl.jena.rdf.model.Property statusProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/status");
	private java.util.ArrayList status;
	private static com.hp.hpl.jena.rdf.model.Property jabberIDProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/jabberID");
	private java.util.ArrayList jabberID;
	private static com.hp.hpl.jena.rdf.model.Property msnChatIDProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/msnChatID");
	private java.util.ArrayList msnChatID;
	private static com.hp.hpl.jena.rdf.model.Property memberProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/member");
	private java.util.ArrayList member;
 

	GroupImpl(Resource resource, Model model) throws JastorException {
		super(resource, model);
		setupModelListener();
	}     
    	
	static GroupImpl getGroup(Resource resource, Model model) throws JastorException {
		return new GroupImpl(resource, model);
	}
	    
	static GroupImpl createGroup(Resource resource, Model model) throws JastorException { 
		GroupImpl impl = new GroupImpl(resource, model);
		
		if (!impl._model.contains(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(impl._resource, RDF.type, Group.TYPE)))
			impl._model.add(impl._resource, RDF.type, Group.TYPE);
		impl.addSuperTypes();
		impl.addHasValueValues();
		return impl;
	}
	
	void addSuperTypes() {
		if (!_model.contains(_resource, RDF.type, de.m0ep.uni.ma.rdfs.foaf.Agent.TYPE))
			_model.add(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(_resource, RDF.type, de.m0ep.uni.ma.rdfs.foaf.Agent.TYPE));     
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
		it = _model.listStatements(_resource,mboxProperty,(RDFNode)null);
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
		it = _model.listStatements(_resource,ageProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,phoneProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,genderProperty,(RDFNode)null);
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
		it = _model.listStatements(_resource,weblogProperty,(RDFNode)null);
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
		it = _model.listStatements(_resource,accountProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,openidProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,titleProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,birthdayProperty,(RDFNode)null);
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
		it = _model.listStatements(_resource,yahooChatIDProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,icqChatIDProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,holdsAccountProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,themeProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,madeProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,mbox__sha1sumProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,topic__interestProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,interestProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,aimChatIDProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,skypeIDProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,tipjarProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,statusProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,jabberIDProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,msnChatIDProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,memberProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, de.m0ep.uni.ma.rdfs.foaf.Group.TYPE);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, de.m0ep.uni.ma.rdfs.foaf.Agent.TYPE);
		while (it.hasNext()) {
			list.add(it.next());
		}
		return list;
	}
	
	public void clearCache() {
		fundedBy = null;
		mbox = null;
		name = null;
		dnaChecksum = null;
		age = null;
		phone = null;
		gender = null;
		page = null;
		nick = null;
		logo = null;
		weblog = null;
		maker = null;
		givenname = null;
		account = null;
		openid = null;
		title = null;
		birthday = null;
		givenName = null;
		homepage = null;
		yahooChatID = null;
		icqChatID = null;
		holdsAccount = null;
		theme = null;
		made = null;
		mbox__sha1sum = null;
		topic__interest = null;
		interest = null;
		aimChatID = null;
		skypeID = null;
		tipjar = null;
		status = null;
		jabberID = null;
		msnChatID = null;
		member = null;
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/fundedBy properties in Group model not a Resource", stmt.getObject());
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
		 

	private void initMbox() throws JastorException {
		this.mbox = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, mboxProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/mbox properties in Group model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				com.ibm.adtech.jastor.Thing mbox = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
				this.mbox.add(mbox);
			}
		}
	}

	public java.util.Iterator getMbox() throws JastorException {
		if (mbox == null)
			initMbox();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(mbox,_resource,mboxProperty,true);
	}

	public void addMbox(com.ibm.adtech.jastor.Thing mbox) throws JastorException {
		if (this.mbox == null)
			initMbox();
		if (this.mbox.contains(mbox)) {
			this.mbox.remove(mbox);
			this.mbox.add(mbox);
			return;
		}
		this.mbox.add(mbox);
		_model.add(_model.createStatement(_resource,mboxProperty,mbox.resource()));
	}
	
	public com.ibm.adtech.jastor.Thing addMbox() throws JastorException {
		com.ibm.adtech.jastor.Thing mbox = com.ibm.adtech.jastor.ThingFactory.createThing(_model.createResource(),_model);
		if (this.mbox == null)
			initMbox();
		this.mbox.add(mbox);
		_model.add(_model.createStatement(_resource,mboxProperty,mbox.resource()));
		return mbox;
	}
	
	public com.ibm.adtech.jastor.Thing addMbox(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		com.ibm.adtech.jastor.Thing mbox = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
		if (this.mbox == null)
			initMbox();
		if (this.mbox.contains(mbox))
			return mbox;
		this.mbox.add(mbox);
		_model.add(_model.createStatement(_resource,mboxProperty,mbox.resource()));
		return mbox;
	}
	
	public void removeMbox(com.ibm.adtech.jastor.Thing mbox) throws JastorException {
		if (this.mbox == null)
			initMbox();
		if (!this.mbox.contains(mbox))
			return;
		if (!_model.contains(_resource, mboxProperty, mbox.resource()))
			return;
		this.mbox.remove(mbox);
		_model.removeAll(_resource, mboxProperty, mbox.resource());
	}
		 

	private void initName() throws JastorException {
		name = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, nameProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/name properties in Group model not a Literal", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/dnaChecksum properties in Group model not a Literal", stmt.getObject());
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

	public com.hp.hpl.jena.rdf.model.Literal getAge() throws JastorException {
		if (age != null)
			return age;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, ageProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
			throw new JastorInvalidRDFNodeException(uri() + ": age getProperty() in de.m0ep.uni.ma.rdfs.foaf.Group model not Literal", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
		age = literal;
		return literal;
	}
	
	public void setAge(com.hp.hpl.jena.rdf.model.Literal age) throws JastorException {
		if (_model.contains(_resource,ageProperty)) {
			_model.removeAll(_resource,ageProperty,null);
		}
		this.age = age;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
		if (age != null) {
			_model.add(_model.createStatement(_resource,ageProperty, age));
		}	
	}


	private void initPhone() throws JastorException {
		this.phone = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, phoneProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/phone properties in Group model not a Resource", stmt.getObject());
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
		 
	public com.hp.hpl.jena.rdf.model.Literal getGender() throws JastorException {
		if (gender != null)
			return gender;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, genderProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
			throw new JastorInvalidRDFNodeException(uri() + ": gender getProperty() in de.m0ep.uni.ma.rdfs.foaf.Group model not Literal", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
		gender = literal;
		return literal;
	}
	
	public void setGender(com.hp.hpl.jena.rdf.model.Literal gender) throws JastorException {
		if (_model.contains(_resource,genderProperty)) {
			_model.removeAll(_resource,genderProperty,null);
		}
		this.gender = gender;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
		if (gender != null) {
			_model.add(_model.createStatement(_resource,genderProperty, gender));
		}	
	}


	private void initPage() throws JastorException {
		this.page = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, pageProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/page properties in Group model not a Resource", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/nick properties in Group model not a Literal", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/logo properties in Group model not a Resource", stmt.getObject());
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
		 

	private void initWeblog() throws JastorException {
		this.weblog = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, weblogProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/weblog properties in Group model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				de.m0ep.uni.ma.rdfs.foaf.Document weblog = de.m0ep.uni.ma.rdfs.foaf.Factory.getDocument(resource,_model);
				this.weblog.add(weblog);
			}
		}
	}

	public java.util.Iterator getWeblog() throws JastorException {
		if (weblog == null)
			initWeblog();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(weblog,_resource,weblogProperty,true);
	}

	public void addWeblog(de.m0ep.uni.ma.rdfs.foaf.Document weblog) throws JastorException {
		if (this.weblog == null)
			initWeblog();
		if (this.weblog.contains(weblog)) {
			this.weblog.remove(weblog);
			this.weblog.add(weblog);
			return;
		}
		this.weblog.add(weblog);
		_model.add(_model.createStatement(_resource,weblogProperty,weblog.resource()));
	}
	
	public de.m0ep.uni.ma.rdfs.foaf.Document addWeblog() throws JastorException {
		de.m0ep.uni.ma.rdfs.foaf.Document weblog = de.m0ep.uni.ma.rdfs.foaf.Factory.createDocument(_model.createResource(),_model);
		if (this.weblog == null)
			initWeblog();
		this.weblog.add(weblog);
		_model.add(_model.createStatement(_resource,weblogProperty,weblog.resource()));
		return weblog;
	}
	
	public de.m0ep.uni.ma.rdfs.foaf.Document addWeblog(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		de.m0ep.uni.ma.rdfs.foaf.Document weblog = de.m0ep.uni.ma.rdfs.foaf.Factory.getDocument(resource,_model);
		if (this.weblog == null)
			initWeblog();
		if (this.weblog.contains(weblog))
			return weblog;
		this.weblog.add(weblog);
		_model.add(_model.createStatement(_resource,weblogProperty,weblog.resource()));
		return weblog;
	}
	
	public void removeWeblog(de.m0ep.uni.ma.rdfs.foaf.Document weblog) throws JastorException {
		if (this.weblog == null)
			initWeblog();
		if (!this.weblog.contains(weblog))
			return;
		if (!_model.contains(_resource, weblogProperty, weblog.resource()))
			return;
		this.weblog.remove(weblog);
		_model.removeAll(_resource, weblogProperty, weblog.resource());
	}
		 

	private void initMaker() throws JastorException {
		this.maker = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, makerProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/maker properties in Group model not a Resource", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/givenname properties in Group model not a Literal", stmt.getObject());
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


	private void initAccount() throws JastorException {
		this.account = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, accountProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/account properties in Group model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				de.m0ep.uni.ma.rdfs.foaf.OnlineAccount account = de.m0ep.uni.ma.rdfs.foaf.Factory.getOnlineAccount(resource,_model);
				this.account.add(account);
			}
		}
	}

	public java.util.Iterator getAccount() throws JastorException {
		if (account == null)
			initAccount();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(account,_resource,accountProperty,true);
	}

	public void addAccount(de.m0ep.uni.ma.rdfs.foaf.OnlineAccount account) throws JastorException {
		if (this.account == null)
			initAccount();
		if (this.account.contains(account)) {
			this.account.remove(account);
			this.account.add(account);
			return;
		}
		this.account.add(account);
		_model.add(_model.createStatement(_resource,accountProperty,account.resource()));
	}
	
	public de.m0ep.uni.ma.rdfs.foaf.OnlineAccount addAccount() throws JastorException {
		de.m0ep.uni.ma.rdfs.foaf.OnlineAccount account = de.m0ep.uni.ma.rdfs.foaf.Factory.createOnlineAccount(_model.createResource(),_model);
		if (this.account == null)
			initAccount();
		this.account.add(account);
		_model.add(_model.createStatement(_resource,accountProperty,account.resource()));
		return account;
	}
	
	public de.m0ep.uni.ma.rdfs.foaf.OnlineAccount addAccount(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		de.m0ep.uni.ma.rdfs.foaf.OnlineAccount account = de.m0ep.uni.ma.rdfs.foaf.Factory.getOnlineAccount(resource,_model);
		if (this.account == null)
			initAccount();
		if (this.account.contains(account))
			return account;
		this.account.add(account);
		_model.add(_model.createStatement(_resource,accountProperty,account.resource()));
		return account;
	}
	
	public void removeAccount(de.m0ep.uni.ma.rdfs.foaf.OnlineAccount account) throws JastorException {
		if (this.account == null)
			initAccount();
		if (!this.account.contains(account))
			return;
		if (!_model.contains(_resource, accountProperty, account.resource()))
			return;
		this.account.remove(account);
		_model.removeAll(_resource, accountProperty, account.resource());
	}
		 

	private void initOpenid() throws JastorException {
		this.openid = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, openidProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/openid properties in Group model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				de.m0ep.uni.ma.rdfs.foaf.Document openid = de.m0ep.uni.ma.rdfs.foaf.Factory.getDocument(resource,_model);
				this.openid.add(openid);
			}
		}
	}

	public java.util.Iterator getOpenid() throws JastorException {
		if (openid == null)
			initOpenid();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(openid,_resource,openidProperty,true);
	}

	public void addOpenid(de.m0ep.uni.ma.rdfs.foaf.Document openid) throws JastorException {
		if (this.openid == null)
			initOpenid();
		if (this.openid.contains(openid)) {
			this.openid.remove(openid);
			this.openid.add(openid);
			return;
		}
		this.openid.add(openid);
		_model.add(_model.createStatement(_resource,openidProperty,openid.resource()));
	}
	
	public de.m0ep.uni.ma.rdfs.foaf.Document addOpenid() throws JastorException {
		de.m0ep.uni.ma.rdfs.foaf.Document openid = de.m0ep.uni.ma.rdfs.foaf.Factory.createDocument(_model.createResource(),_model);
		if (this.openid == null)
			initOpenid();
		this.openid.add(openid);
		_model.add(_model.createStatement(_resource,openidProperty,openid.resource()));
		return openid;
	}
	
	public de.m0ep.uni.ma.rdfs.foaf.Document addOpenid(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		de.m0ep.uni.ma.rdfs.foaf.Document openid = de.m0ep.uni.ma.rdfs.foaf.Factory.getDocument(resource,_model);
		if (this.openid == null)
			initOpenid();
		if (this.openid.contains(openid))
			return openid;
		this.openid.add(openid);
		_model.add(_model.createStatement(_resource,openidProperty,openid.resource()));
		return openid;
	}
	
	public void removeOpenid(de.m0ep.uni.ma.rdfs.foaf.Document openid) throws JastorException {
		if (this.openid == null)
			initOpenid();
		if (!this.openid.contains(openid))
			return;
		if (!_model.contains(_resource, openidProperty, openid.resource()))
			return;
		this.openid.remove(openid);
		_model.removeAll(_resource, openidProperty, openid.resource());
	}
		 

	private void initTitle() throws JastorException {
		title = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, titleProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/title properties in Group model not a Literal", stmt.getObject());
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

	public com.hp.hpl.jena.rdf.model.Literal getBirthday() throws JastorException {
		if (birthday != null)
			return birthday;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, birthdayProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
			throw new JastorInvalidRDFNodeException(uri() + ": birthday getProperty() in de.m0ep.uni.ma.rdfs.foaf.Group model not Literal", stmt.getObject());
		com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
		birthday = literal;
		return literal;
	}
	
	public void setBirthday(com.hp.hpl.jena.rdf.model.Literal birthday) throws JastorException {
		if (_model.contains(_resource,birthdayProperty)) {
			_model.removeAll(_resource,birthdayProperty,null);
		}
		this.birthday = birthday;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
		if (birthday != null) {
			_model.add(_model.createStatement(_resource,birthdayProperty, birthday));
		}	
	}


	private void initGivenName() throws JastorException {
		givenName = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, givenNameProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/givenName properties in Group model not a Literal", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/homepage properties in Group model not a Resource", stmt.getObject());
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
		 

	private void initYahooChatID() throws JastorException {
		yahooChatID = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, yahooChatIDProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/yahooChatID properties in Group model not a Literal", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
			yahooChatID.add(literal);
		}
	}

	public java.util.Iterator getYahooChatID() throws JastorException {
		if (yahooChatID == null)
			initYahooChatID();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(yahooChatID,_resource,yahooChatIDProperty,false);
	}

	public void addYahooChatID(com.hp.hpl.jena.rdf.model.Literal yahooChatID) throws JastorException {
		if (this.yahooChatID == null)
			initYahooChatID();
		if (this.yahooChatID.contains(yahooChatID))
			return;
		if (_model.contains(_resource, yahooChatIDProperty, createLiteral(yahooChatID)))
			return;
		this.yahooChatID.add(yahooChatID);
		_model.add(_resource, yahooChatIDProperty, yahooChatID);
	}
	
	public void removeYahooChatID(com.hp.hpl.jena.rdf.model.Literal yahooChatID) throws JastorException {
		if (this.yahooChatID == null)
			initYahooChatID();
		if (!this.yahooChatID.contains(yahooChatID))
			return;
		if (!_model.contains(_resource, yahooChatIDProperty, createLiteral(yahooChatID)))
			return;
		this.yahooChatID.remove(yahooChatID);
		_model.removeAll(_resource, yahooChatIDProperty, createLiteral(yahooChatID));
	}


	private void initIcqChatID() throws JastorException {
		icqChatID = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, icqChatIDProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/icqChatID properties in Group model not a Literal", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
			icqChatID.add(literal);
		}
	}

	public java.util.Iterator getIcqChatID() throws JastorException {
		if (icqChatID == null)
			initIcqChatID();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(icqChatID,_resource,icqChatIDProperty,false);
	}

	public void addIcqChatID(com.hp.hpl.jena.rdf.model.Literal icqChatID) throws JastorException {
		if (this.icqChatID == null)
			initIcqChatID();
		if (this.icqChatID.contains(icqChatID))
			return;
		if (_model.contains(_resource, icqChatIDProperty, createLiteral(icqChatID)))
			return;
		this.icqChatID.add(icqChatID);
		_model.add(_resource, icqChatIDProperty, icqChatID);
	}
	
	public void removeIcqChatID(com.hp.hpl.jena.rdf.model.Literal icqChatID) throws JastorException {
		if (this.icqChatID == null)
			initIcqChatID();
		if (!this.icqChatID.contains(icqChatID))
			return;
		if (!_model.contains(_resource, icqChatIDProperty, createLiteral(icqChatID)))
			return;
		this.icqChatID.remove(icqChatID);
		_model.removeAll(_resource, icqChatIDProperty, createLiteral(icqChatID));
	}


	private void initHoldsAccount() throws JastorException {
		this.holdsAccount = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, holdsAccountProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/holdsAccount properties in Group model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				de.m0ep.uni.ma.rdfs.foaf.OnlineAccount holdsAccount = de.m0ep.uni.ma.rdfs.foaf.Factory.getOnlineAccount(resource,_model);
				this.holdsAccount.add(holdsAccount);
			}
		}
	}

	public java.util.Iterator getHoldsAccount() throws JastorException {
		if (holdsAccount == null)
			initHoldsAccount();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(holdsAccount,_resource,holdsAccountProperty,true);
	}

	public void addHoldsAccount(de.m0ep.uni.ma.rdfs.foaf.OnlineAccount holdsAccount) throws JastorException {
		if (this.holdsAccount == null)
			initHoldsAccount();
		if (this.holdsAccount.contains(holdsAccount)) {
			this.holdsAccount.remove(holdsAccount);
			this.holdsAccount.add(holdsAccount);
			return;
		}
		this.holdsAccount.add(holdsAccount);
		_model.add(_model.createStatement(_resource,holdsAccountProperty,holdsAccount.resource()));
	}
	
	public de.m0ep.uni.ma.rdfs.foaf.OnlineAccount addHoldsAccount() throws JastorException {
		de.m0ep.uni.ma.rdfs.foaf.OnlineAccount holdsAccount = de.m0ep.uni.ma.rdfs.foaf.Factory.createOnlineAccount(_model.createResource(),_model);
		if (this.holdsAccount == null)
			initHoldsAccount();
		this.holdsAccount.add(holdsAccount);
		_model.add(_model.createStatement(_resource,holdsAccountProperty,holdsAccount.resource()));
		return holdsAccount;
	}
	
	public de.m0ep.uni.ma.rdfs.foaf.OnlineAccount addHoldsAccount(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		de.m0ep.uni.ma.rdfs.foaf.OnlineAccount holdsAccount = de.m0ep.uni.ma.rdfs.foaf.Factory.getOnlineAccount(resource,_model);
		if (this.holdsAccount == null)
			initHoldsAccount();
		if (this.holdsAccount.contains(holdsAccount))
			return holdsAccount;
		this.holdsAccount.add(holdsAccount);
		_model.add(_model.createStatement(_resource,holdsAccountProperty,holdsAccount.resource()));
		return holdsAccount;
	}
	
	public void removeHoldsAccount(de.m0ep.uni.ma.rdfs.foaf.OnlineAccount holdsAccount) throws JastorException {
		if (this.holdsAccount == null)
			initHoldsAccount();
		if (!this.holdsAccount.contains(holdsAccount))
			return;
		if (!_model.contains(_resource, holdsAccountProperty, holdsAccount.resource()))
			return;
		this.holdsAccount.remove(holdsAccount);
		_model.removeAll(_resource, holdsAccountProperty, holdsAccount.resource());
	}
		 

	private void initTheme() throws JastorException {
		this.theme = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, themeProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/theme properties in Group model not a Resource", stmt.getObject());
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
		 

	private void initMade() throws JastorException {
		this.made = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, madeProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/made properties in Group model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				com.ibm.adtech.jastor.Thing made = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
				this.made.add(made);
			}
		}
	}

	public java.util.Iterator getMade() throws JastorException {
		if (made == null)
			initMade();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(made,_resource,madeProperty,true);
	}

	public void addMade(com.ibm.adtech.jastor.Thing made) throws JastorException {
		if (this.made == null)
			initMade();
		if (this.made.contains(made)) {
			this.made.remove(made);
			this.made.add(made);
			return;
		}
		this.made.add(made);
		_model.add(_model.createStatement(_resource,madeProperty,made.resource()));
	}
	
	public com.ibm.adtech.jastor.Thing addMade() throws JastorException {
		com.ibm.adtech.jastor.Thing made = com.ibm.adtech.jastor.ThingFactory.createThing(_model.createResource(),_model);
		if (this.made == null)
			initMade();
		this.made.add(made);
		_model.add(_model.createStatement(_resource,madeProperty,made.resource()));
		return made;
	}
	
	public com.ibm.adtech.jastor.Thing addMade(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		com.ibm.adtech.jastor.Thing made = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
		if (this.made == null)
			initMade();
		if (this.made.contains(made))
			return made;
		this.made.add(made);
		_model.add(_model.createStatement(_resource,madeProperty,made.resource()));
		return made;
	}
	
	public void removeMade(com.ibm.adtech.jastor.Thing made) throws JastorException {
		if (this.made == null)
			initMade();
		if (!this.made.contains(made))
			return;
		if (!_model.contains(_resource, madeProperty, made.resource()))
			return;
		this.made.remove(made);
		_model.removeAll(_resource, madeProperty, made.resource());
	}
		 

	private void initMbox__sha1sum() throws JastorException {
		mbox__sha1sum = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, mbox__sha1sumProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/mbox_sha1sum properties in Group model not a Literal", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
			mbox__sha1sum.add(literal);
		}
	}

	public java.util.Iterator getMbox__sha1sum() throws JastorException {
		if (mbox__sha1sum == null)
			initMbox__sha1sum();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(mbox__sha1sum,_resource,mbox__sha1sumProperty,false);
	}

	public void addMbox__sha1sum(com.hp.hpl.jena.rdf.model.Literal mbox__sha1sum) throws JastorException {
		if (this.mbox__sha1sum == null)
			initMbox__sha1sum();
		if (this.mbox__sha1sum.contains(mbox__sha1sum))
			return;
		if (_model.contains(_resource, mbox__sha1sumProperty, createLiteral(mbox__sha1sum)))
			return;
		this.mbox__sha1sum.add(mbox__sha1sum);
		_model.add(_resource, mbox__sha1sumProperty, mbox__sha1sum);
	}
	
	public void removeMbox__sha1sum(com.hp.hpl.jena.rdf.model.Literal mbox__sha1sum) throws JastorException {
		if (this.mbox__sha1sum == null)
			initMbox__sha1sum();
		if (!this.mbox__sha1sum.contains(mbox__sha1sum))
			return;
		if (!_model.contains(_resource, mbox__sha1sumProperty, createLiteral(mbox__sha1sum)))
			return;
		this.mbox__sha1sum.remove(mbox__sha1sum);
		_model.removeAll(_resource, mbox__sha1sumProperty, createLiteral(mbox__sha1sum));
	}


	private void initTopic__interest() throws JastorException {
		this.topic__interest = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, topic__interestProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/topic_interest properties in Group model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				com.ibm.adtech.jastor.Thing topic__interest = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
				this.topic__interest.add(topic__interest);
			}
		}
	}

	public java.util.Iterator getTopic__interest() throws JastorException {
		if (topic__interest == null)
			initTopic__interest();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(topic__interest,_resource,topic__interestProperty,true);
	}

	public void addTopic__interest(com.ibm.adtech.jastor.Thing topic__interest) throws JastorException {
		if (this.topic__interest == null)
			initTopic__interest();
		if (this.topic__interest.contains(topic__interest)) {
			this.topic__interest.remove(topic__interest);
			this.topic__interest.add(topic__interest);
			return;
		}
		this.topic__interest.add(topic__interest);
		_model.add(_model.createStatement(_resource,topic__interestProperty,topic__interest.resource()));
	}
	
	public com.ibm.adtech.jastor.Thing addTopic__interest() throws JastorException {
		com.ibm.adtech.jastor.Thing topic__interest = com.ibm.adtech.jastor.ThingFactory.createThing(_model.createResource(),_model);
		if (this.topic__interest == null)
			initTopic__interest();
		this.topic__interest.add(topic__interest);
		_model.add(_model.createStatement(_resource,topic__interestProperty,topic__interest.resource()));
		return topic__interest;
	}
	
	public com.ibm.adtech.jastor.Thing addTopic__interest(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		com.ibm.adtech.jastor.Thing topic__interest = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
		if (this.topic__interest == null)
			initTopic__interest();
		if (this.topic__interest.contains(topic__interest))
			return topic__interest;
		this.topic__interest.add(topic__interest);
		_model.add(_model.createStatement(_resource,topic__interestProperty,topic__interest.resource()));
		return topic__interest;
	}
	
	public void removeTopic__interest(com.ibm.adtech.jastor.Thing topic__interest) throws JastorException {
		if (this.topic__interest == null)
			initTopic__interest();
		if (!this.topic__interest.contains(topic__interest))
			return;
		if (!_model.contains(_resource, topic__interestProperty, topic__interest.resource()))
			return;
		this.topic__interest.remove(topic__interest);
		_model.removeAll(_resource, topic__interestProperty, topic__interest.resource());
	}
		 

	private void initInterest() throws JastorException {
		this.interest = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, interestProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/interest properties in Group model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				de.m0ep.uni.ma.rdfs.foaf.Document interest = de.m0ep.uni.ma.rdfs.foaf.Factory.getDocument(resource,_model);
				this.interest.add(interest);
			}
		}
	}

	public java.util.Iterator getInterest() throws JastorException {
		if (interest == null)
			initInterest();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(interest,_resource,interestProperty,true);
	}

	public void addInterest(de.m0ep.uni.ma.rdfs.foaf.Document interest) throws JastorException {
		if (this.interest == null)
			initInterest();
		if (this.interest.contains(interest)) {
			this.interest.remove(interest);
			this.interest.add(interest);
			return;
		}
		this.interest.add(interest);
		_model.add(_model.createStatement(_resource,interestProperty,interest.resource()));
	}
	
	public de.m0ep.uni.ma.rdfs.foaf.Document addInterest() throws JastorException {
		de.m0ep.uni.ma.rdfs.foaf.Document interest = de.m0ep.uni.ma.rdfs.foaf.Factory.createDocument(_model.createResource(),_model);
		if (this.interest == null)
			initInterest();
		this.interest.add(interest);
		_model.add(_model.createStatement(_resource,interestProperty,interest.resource()));
		return interest;
	}
	
	public de.m0ep.uni.ma.rdfs.foaf.Document addInterest(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		de.m0ep.uni.ma.rdfs.foaf.Document interest = de.m0ep.uni.ma.rdfs.foaf.Factory.getDocument(resource,_model);
		if (this.interest == null)
			initInterest();
		if (this.interest.contains(interest))
			return interest;
		this.interest.add(interest);
		_model.add(_model.createStatement(_resource,interestProperty,interest.resource()));
		return interest;
	}
	
	public void removeInterest(de.m0ep.uni.ma.rdfs.foaf.Document interest) throws JastorException {
		if (this.interest == null)
			initInterest();
		if (!this.interest.contains(interest))
			return;
		if (!_model.contains(_resource, interestProperty, interest.resource()))
			return;
		this.interest.remove(interest);
		_model.removeAll(_resource, interestProperty, interest.resource());
	}
		 

	private void initAimChatID() throws JastorException {
		aimChatID = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, aimChatIDProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/aimChatID properties in Group model not a Literal", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
			aimChatID.add(literal);
		}
	}

	public java.util.Iterator getAimChatID() throws JastorException {
		if (aimChatID == null)
			initAimChatID();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(aimChatID,_resource,aimChatIDProperty,false);
	}

	public void addAimChatID(com.hp.hpl.jena.rdf.model.Literal aimChatID) throws JastorException {
		if (this.aimChatID == null)
			initAimChatID();
		if (this.aimChatID.contains(aimChatID))
			return;
		if (_model.contains(_resource, aimChatIDProperty, createLiteral(aimChatID)))
			return;
		this.aimChatID.add(aimChatID);
		_model.add(_resource, aimChatIDProperty, aimChatID);
	}
	
	public void removeAimChatID(com.hp.hpl.jena.rdf.model.Literal aimChatID) throws JastorException {
		if (this.aimChatID == null)
			initAimChatID();
		if (!this.aimChatID.contains(aimChatID))
			return;
		if (!_model.contains(_resource, aimChatIDProperty, createLiteral(aimChatID)))
			return;
		this.aimChatID.remove(aimChatID);
		_model.removeAll(_resource, aimChatIDProperty, createLiteral(aimChatID));
	}


	private void initSkypeID() throws JastorException {
		skypeID = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, skypeIDProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/skypeID properties in Group model not a Literal", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
			skypeID.add(literal);
		}
	}

	public java.util.Iterator getSkypeID() throws JastorException {
		if (skypeID == null)
			initSkypeID();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(skypeID,_resource,skypeIDProperty,false);
	}

	public void addSkypeID(com.hp.hpl.jena.rdf.model.Literal skypeID) throws JastorException {
		if (this.skypeID == null)
			initSkypeID();
		if (this.skypeID.contains(skypeID))
			return;
		if (_model.contains(_resource, skypeIDProperty, createLiteral(skypeID)))
			return;
		this.skypeID.add(skypeID);
		_model.add(_resource, skypeIDProperty, skypeID);
	}
	
	public void removeSkypeID(com.hp.hpl.jena.rdf.model.Literal skypeID) throws JastorException {
		if (this.skypeID == null)
			initSkypeID();
		if (!this.skypeID.contains(skypeID))
			return;
		if (!_model.contains(_resource, skypeIDProperty, createLiteral(skypeID)))
			return;
		this.skypeID.remove(skypeID);
		_model.removeAll(_resource, skypeIDProperty, createLiteral(skypeID));
	}


	private void initTipjar() throws JastorException {
		this.tipjar = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, tipjarProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/tipjar properties in Group model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				de.m0ep.uni.ma.rdfs.foaf.Document tipjar = de.m0ep.uni.ma.rdfs.foaf.Factory.getDocument(resource,_model);
				this.tipjar.add(tipjar);
			}
		}
	}

	public java.util.Iterator getTipjar() throws JastorException {
		if (tipjar == null)
			initTipjar();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(tipjar,_resource,tipjarProperty,true);
	}

	public void addTipjar(de.m0ep.uni.ma.rdfs.foaf.Document tipjar) throws JastorException {
		if (this.tipjar == null)
			initTipjar();
		if (this.tipjar.contains(tipjar)) {
			this.tipjar.remove(tipjar);
			this.tipjar.add(tipjar);
			return;
		}
		this.tipjar.add(tipjar);
		_model.add(_model.createStatement(_resource,tipjarProperty,tipjar.resource()));
	}
	
	public de.m0ep.uni.ma.rdfs.foaf.Document addTipjar() throws JastorException {
		de.m0ep.uni.ma.rdfs.foaf.Document tipjar = de.m0ep.uni.ma.rdfs.foaf.Factory.createDocument(_model.createResource(),_model);
		if (this.tipjar == null)
			initTipjar();
		this.tipjar.add(tipjar);
		_model.add(_model.createStatement(_resource,tipjarProperty,tipjar.resource()));
		return tipjar;
	}
	
	public de.m0ep.uni.ma.rdfs.foaf.Document addTipjar(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		de.m0ep.uni.ma.rdfs.foaf.Document tipjar = de.m0ep.uni.ma.rdfs.foaf.Factory.getDocument(resource,_model);
		if (this.tipjar == null)
			initTipjar();
		if (this.tipjar.contains(tipjar))
			return tipjar;
		this.tipjar.add(tipjar);
		_model.add(_model.createStatement(_resource,tipjarProperty,tipjar.resource()));
		return tipjar;
	}
	
	public void removeTipjar(de.m0ep.uni.ma.rdfs.foaf.Document tipjar) throws JastorException {
		if (this.tipjar == null)
			initTipjar();
		if (!this.tipjar.contains(tipjar))
			return;
		if (!_model.contains(_resource, tipjarProperty, tipjar.resource()))
			return;
		this.tipjar.remove(tipjar);
		_model.removeAll(_resource, tipjarProperty, tipjar.resource());
	}
		 

	private void initStatus() throws JastorException {
		status = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, statusProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/status properties in Group model not a Literal", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
			status.add(literal);
		}
	}

	public java.util.Iterator getStatus() throws JastorException {
		if (status == null)
			initStatus();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(status,_resource,statusProperty,false);
	}

	public void addStatus(com.hp.hpl.jena.rdf.model.Literal status) throws JastorException {
		if (this.status == null)
			initStatus();
		if (this.status.contains(status))
			return;
		if (_model.contains(_resource, statusProperty, createLiteral(status)))
			return;
		this.status.add(status);
		_model.add(_resource, statusProperty, status);
	}
	
	public void removeStatus(com.hp.hpl.jena.rdf.model.Literal status) throws JastorException {
		if (this.status == null)
			initStatus();
		if (!this.status.contains(status))
			return;
		if (!_model.contains(_resource, statusProperty, createLiteral(status)))
			return;
		this.status.remove(status);
		_model.removeAll(_resource, statusProperty, createLiteral(status));
	}


	private void initJabberID() throws JastorException {
		jabberID = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, jabberIDProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/jabberID properties in Group model not a Literal", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
			jabberID.add(literal);
		}
	}

	public java.util.Iterator getJabberID() throws JastorException {
		if (jabberID == null)
			initJabberID();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(jabberID,_resource,jabberIDProperty,false);
	}

	public void addJabberID(com.hp.hpl.jena.rdf.model.Literal jabberID) throws JastorException {
		if (this.jabberID == null)
			initJabberID();
		if (this.jabberID.contains(jabberID))
			return;
		if (_model.contains(_resource, jabberIDProperty, createLiteral(jabberID)))
			return;
		this.jabberID.add(jabberID);
		_model.add(_resource, jabberIDProperty, jabberID);
	}
	
	public void removeJabberID(com.hp.hpl.jena.rdf.model.Literal jabberID) throws JastorException {
		if (this.jabberID == null)
			initJabberID();
		if (!this.jabberID.contains(jabberID))
			return;
		if (!_model.contains(_resource, jabberIDProperty, createLiteral(jabberID)))
			return;
		this.jabberID.remove(jabberID);
		_model.removeAll(_resource, jabberIDProperty, createLiteral(jabberID));
	}


	private void initMsnChatID() throws JastorException {
		msnChatID = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, msnChatIDProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/msnChatID properties in Group model not a Literal", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
			msnChatID.add(literal);
		}
	}

	public java.util.Iterator getMsnChatID() throws JastorException {
		if (msnChatID == null)
			initMsnChatID();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(msnChatID,_resource,msnChatIDProperty,false);
	}

	public void addMsnChatID(com.hp.hpl.jena.rdf.model.Literal msnChatID) throws JastorException {
		if (this.msnChatID == null)
			initMsnChatID();
		if (this.msnChatID.contains(msnChatID))
			return;
		if (_model.contains(_resource, msnChatIDProperty, createLiteral(msnChatID)))
			return;
		this.msnChatID.add(msnChatID);
		_model.add(_resource, msnChatIDProperty, msnChatID);
	}
	
	public void removeMsnChatID(com.hp.hpl.jena.rdf.model.Literal msnChatID) throws JastorException {
		if (this.msnChatID == null)
			initMsnChatID();
		if (!this.msnChatID.contains(msnChatID))
			return;
		if (!_model.contains(_resource, msnChatIDProperty, createLiteral(msnChatID)))
			return;
		this.msnChatID.remove(msnChatID);
		_model.removeAll(_resource, msnChatIDProperty, createLiteral(msnChatID));
	}


	private void initMember() throws JastorException {
		this.member = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, memberProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/member properties in Group model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				de.m0ep.uni.ma.rdfs.foaf.Agent member = de.m0ep.uni.ma.rdfs.foaf.Factory.getAgent(resource,_model);
				this.member.add(member);
			}
		}
	}

	public java.util.Iterator getMember() throws JastorException {
		if (member == null)
			initMember();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(member,_resource,memberProperty,true);
	}

	public void addMember(de.m0ep.uni.ma.rdfs.foaf.Agent member) throws JastorException {
		if (this.member == null)
			initMember();
		if (this.member.contains(member)) {
			this.member.remove(member);
			this.member.add(member);
			return;
		}
		this.member.add(member);
		_model.add(_model.createStatement(_resource,memberProperty,member.resource()));
	}
	
	public de.m0ep.uni.ma.rdfs.foaf.Agent addMember() throws JastorException {
		de.m0ep.uni.ma.rdfs.foaf.Agent member = de.m0ep.uni.ma.rdfs.foaf.Factory.createAgent(_model.createResource(),_model);
		if (this.member == null)
			initMember();
		this.member.add(member);
		_model.add(_model.createStatement(_resource,memberProperty,member.resource()));
		return member;
	}
	
	public de.m0ep.uni.ma.rdfs.foaf.Agent addMember(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		de.m0ep.uni.ma.rdfs.foaf.Agent member = de.m0ep.uni.ma.rdfs.foaf.Factory.getAgent(resource,_model);
		if (this.member == null)
			initMember();
		if (this.member.contains(member))
			return member;
		this.member.add(member);
		_model.add(_model.createStatement(_resource,memberProperty,member.resource()));
		return member;
	}
	
	public void removeMember(de.m0ep.uni.ma.rdfs.foaf.Agent member) throws JastorException {
		if (this.member == null)
			initMember();
		if (!this.member.contains(member))
			return;
		if (!_model.contains(_resource, memberProperty, member.resource()))
			return;
		this.member.remove(member);
		_model.removeAll(_resource, memberProperty, member.resource());
	}
		  


	private java.util.ArrayList listeners;
	
	public void registerListener(ThingListener listener) {
		if (!(listener instanceof GroupListener))
			throw new IllegalArgumentException("ThingListener must be instance of GroupListener"); 
		if (listeners == null)
			setupModelListener();
		if(!this.listeners.contains(listener)){
			this.listeners.add((GroupListener)listener);
		}
	}
	
	public void unregisterListener(ThingListener listener) {
		if (!(listener instanceof GroupListener))
			throw new IllegalArgumentException("ThingListener must be instance of GroupListener"); 
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
							GroupListener listener=(GroupListener)iter.next();
							listener.fundedByAdded(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,_fundedBy);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(mboxProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					com.ibm.adtech.jastor.Thing _mbox = null;
					try {
						_mbox = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (mbox == null) {
						try {
							initMbox();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!mbox.contains(_mbox))
						mbox.add(_mbox);
					if (listeners != null) {
						java.util.ArrayList consumersForMbox;
						synchronized (listeners) {
							consumersForMbox = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForMbox.iterator();iter.hasNext();){
							GroupListener listener=(GroupListener)iter.next();
							listener.mboxAdded(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,_mbox);
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
						GroupListener listener=(GroupListener)iter.next();
						listener.nameAdded(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,literal);
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
						GroupListener listener=(GroupListener)iter.next();
						listener.dnaChecksumAdded(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(ageProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				age = literal;
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						GroupListener listener=(GroupListener)iter.next();
						listener.ageChanged(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this);
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
							GroupListener listener=(GroupListener)iter.next();
							listener.phoneAdded(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,_phone);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(genderProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				gender = literal;
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						GroupListener listener=(GroupListener)iter.next();
						listener.genderChanged(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this);
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
							GroupListener listener=(GroupListener)iter.next();
							listener.pageAdded(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,_page);
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
						GroupListener listener=(GroupListener)iter.next();
						listener.nickAdded(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,literal);
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
							GroupListener listener=(GroupListener)iter.next();
							listener.logoAdded(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,_logo);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(weblogProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.foaf.Document _weblog = null;
					try {
						_weblog = de.m0ep.uni.ma.rdfs.foaf.Factory.getDocument(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (weblog == null) {
						try {
							initWeblog();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!weblog.contains(_weblog))
						weblog.add(_weblog);
					if (listeners != null) {
						java.util.ArrayList consumersForWeblog;
						synchronized (listeners) {
							consumersForWeblog = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForWeblog.iterator();iter.hasNext();){
							GroupListener listener=(GroupListener)iter.next();
							listener.weblogAdded(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,_weblog);
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
							GroupListener listener=(GroupListener)iter.next();
							listener.makerAdded(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,_maker);
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
						GroupListener listener=(GroupListener)iter.next();
						listener.givennameAdded(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(accountProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.foaf.OnlineAccount _account = null;
					try {
						_account = de.m0ep.uni.ma.rdfs.foaf.Factory.getOnlineAccount(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (account == null) {
						try {
							initAccount();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!account.contains(_account))
						account.add(_account);
					if (listeners != null) {
						java.util.ArrayList consumersForAccount;
						synchronized (listeners) {
							consumersForAccount = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForAccount.iterator();iter.hasNext();){
							GroupListener listener=(GroupListener)iter.next();
							listener.accountAdded(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,_account);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(openidProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.foaf.Document _openid = null;
					try {
						_openid = de.m0ep.uni.ma.rdfs.foaf.Factory.getDocument(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (openid == null) {
						try {
							initOpenid();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!openid.contains(_openid))
						openid.add(_openid);
					if (listeners != null) {
						java.util.ArrayList consumersForOpenid;
						synchronized (listeners) {
							consumersForOpenid = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForOpenid.iterator();iter.hasNext();){
							GroupListener listener=(GroupListener)iter.next();
							listener.openidAdded(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,_openid);
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
						GroupListener listener=(GroupListener)iter.next();
						listener.titleAdded(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(birthdayProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				birthday = literal;
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						GroupListener listener=(GroupListener)iter.next();
						listener.birthdayChanged(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this);
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
						GroupListener listener=(GroupListener)iter.next();
						listener.givenNameAdded(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,literal);
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
							GroupListener listener=(GroupListener)iter.next();
							listener.homepageAdded(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,_homepage);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(yahooChatIDProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (yahooChatID == null)
					try {
						initYahooChatID();
					} catch (JastorException e) {
						e.printStackTrace();
						return;
					}
				if (!yahooChatID.contains(literal))
					yahooChatID.add(literal);
				if (listeners != null) {
					java.util.ArrayList consumersForYahooChatID;
					synchronized (listeners) {
						consumersForYahooChatID = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForYahooChatID.iterator();iter.hasNext();){
						GroupListener listener=(GroupListener)iter.next();
						listener.yahooChatIDAdded(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(icqChatIDProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (icqChatID == null)
					try {
						initIcqChatID();
					} catch (JastorException e) {
						e.printStackTrace();
						return;
					}
				if (!icqChatID.contains(literal))
					icqChatID.add(literal);
				if (listeners != null) {
					java.util.ArrayList consumersForIcqChatID;
					synchronized (listeners) {
						consumersForIcqChatID = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForIcqChatID.iterator();iter.hasNext();){
						GroupListener listener=(GroupListener)iter.next();
						listener.icqChatIDAdded(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(holdsAccountProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.foaf.OnlineAccount _holdsAccount = null;
					try {
						_holdsAccount = de.m0ep.uni.ma.rdfs.foaf.Factory.getOnlineAccount(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (holdsAccount == null) {
						try {
							initHoldsAccount();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!holdsAccount.contains(_holdsAccount))
						holdsAccount.add(_holdsAccount);
					if (listeners != null) {
						java.util.ArrayList consumersForHoldsAccount;
						synchronized (listeners) {
							consumersForHoldsAccount = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForHoldsAccount.iterator();iter.hasNext();){
							GroupListener listener=(GroupListener)iter.next();
							listener.holdsAccountAdded(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,_holdsAccount);
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
							GroupListener listener=(GroupListener)iter.next();
							listener.themeAdded(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,_theme);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(madeProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					com.ibm.adtech.jastor.Thing _made = null;
					try {
						_made = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (made == null) {
						try {
							initMade();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!made.contains(_made))
						made.add(_made);
					if (listeners != null) {
						java.util.ArrayList consumersForMade;
						synchronized (listeners) {
							consumersForMade = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForMade.iterator();iter.hasNext();){
							GroupListener listener=(GroupListener)iter.next();
							listener.madeAdded(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,_made);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(mbox__sha1sumProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (mbox__sha1sum == null)
					try {
						initMbox__sha1sum();
					} catch (JastorException e) {
						e.printStackTrace();
						return;
					}
				if (!mbox__sha1sum.contains(literal))
					mbox__sha1sum.add(literal);
				if (listeners != null) {
					java.util.ArrayList consumersForMbox__sha1sum;
					synchronized (listeners) {
						consumersForMbox__sha1sum = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForMbox__sha1sum.iterator();iter.hasNext();){
						GroupListener listener=(GroupListener)iter.next();
						listener.mbox__sha1sumAdded(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(topic__interestProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					com.ibm.adtech.jastor.Thing _topic__interest = null;
					try {
						_topic__interest = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (topic__interest == null) {
						try {
							initTopic__interest();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!topic__interest.contains(_topic__interest))
						topic__interest.add(_topic__interest);
					if (listeners != null) {
						java.util.ArrayList consumersForTopic__interest;
						synchronized (listeners) {
							consumersForTopic__interest = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForTopic__interest.iterator();iter.hasNext();){
							GroupListener listener=(GroupListener)iter.next();
							listener.topic__interestAdded(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,_topic__interest);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(interestProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.foaf.Document _interest = null;
					try {
						_interest = de.m0ep.uni.ma.rdfs.foaf.Factory.getDocument(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (interest == null) {
						try {
							initInterest();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!interest.contains(_interest))
						interest.add(_interest);
					if (listeners != null) {
						java.util.ArrayList consumersForInterest;
						synchronized (listeners) {
							consumersForInterest = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForInterest.iterator();iter.hasNext();){
							GroupListener listener=(GroupListener)iter.next();
							listener.interestAdded(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,_interest);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(aimChatIDProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (aimChatID == null)
					try {
						initAimChatID();
					} catch (JastorException e) {
						e.printStackTrace();
						return;
					}
				if (!aimChatID.contains(literal))
					aimChatID.add(literal);
				if (listeners != null) {
					java.util.ArrayList consumersForAimChatID;
					synchronized (listeners) {
						consumersForAimChatID = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForAimChatID.iterator();iter.hasNext();){
						GroupListener listener=(GroupListener)iter.next();
						listener.aimChatIDAdded(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(skypeIDProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (skypeID == null)
					try {
						initSkypeID();
					} catch (JastorException e) {
						e.printStackTrace();
						return;
					}
				if (!skypeID.contains(literal))
					skypeID.add(literal);
				if (listeners != null) {
					java.util.ArrayList consumersForSkypeID;
					synchronized (listeners) {
						consumersForSkypeID = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForSkypeID.iterator();iter.hasNext();){
						GroupListener listener=(GroupListener)iter.next();
						listener.skypeIDAdded(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(tipjarProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.foaf.Document _tipjar = null;
					try {
						_tipjar = de.m0ep.uni.ma.rdfs.foaf.Factory.getDocument(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (tipjar == null) {
						try {
							initTipjar();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!tipjar.contains(_tipjar))
						tipjar.add(_tipjar);
					if (listeners != null) {
						java.util.ArrayList consumersForTipjar;
						synchronized (listeners) {
							consumersForTipjar = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForTipjar.iterator();iter.hasNext();){
							GroupListener listener=(GroupListener)iter.next();
							listener.tipjarAdded(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,_tipjar);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(statusProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (status == null)
					try {
						initStatus();
					} catch (JastorException e) {
						e.printStackTrace();
						return;
					}
				if (!status.contains(literal))
					status.add(literal);
				if (listeners != null) {
					java.util.ArrayList consumersForStatus;
					synchronized (listeners) {
						consumersForStatus = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForStatus.iterator();iter.hasNext();){
						GroupListener listener=(GroupListener)iter.next();
						listener.statusAdded(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(jabberIDProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (jabberID == null)
					try {
						initJabberID();
					} catch (JastorException e) {
						e.printStackTrace();
						return;
					}
				if (!jabberID.contains(literal))
					jabberID.add(literal);
				if (listeners != null) {
					java.util.ArrayList consumersForJabberID;
					synchronized (listeners) {
						consumersForJabberID = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForJabberID.iterator();iter.hasNext();){
						GroupListener listener=(GroupListener)iter.next();
						listener.jabberIDAdded(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(msnChatIDProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (msnChatID == null)
					try {
						initMsnChatID();
					} catch (JastorException e) {
						e.printStackTrace();
						return;
					}
				if (!msnChatID.contains(literal))
					msnChatID.add(literal);
				if (listeners != null) {
					java.util.ArrayList consumersForMsnChatID;
					synchronized (listeners) {
						consumersForMsnChatID = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForMsnChatID.iterator();iter.hasNext();){
						GroupListener listener=(GroupListener)iter.next();
						listener.msnChatIDAdded(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(memberProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.foaf.Agent _member = null;
					try {
						_member = de.m0ep.uni.ma.rdfs.foaf.Factory.getAgent(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (member == null) {
						try {
							initMember();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!member.contains(_member))
						member.add(_member);
					if (listeners != null) {
						java.util.ArrayList consumersForMember;
						synchronized (listeners) {
							consumersForMember = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForMember.iterator();iter.hasNext();){
							GroupListener listener=(GroupListener)iter.next();
							listener.memberAdded(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,_member);
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
							GroupListener listener=(GroupListener)iter.next();
							listener.fundedByRemoved(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,_fundedBy);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(mboxProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					com.ibm.adtech.jastor.Thing _mbox = null;
					if (mbox != null) {
						boolean found = false;
						for (int i=0;i<mbox.size();i++) {
							com.ibm.adtech.jastor.Thing __item = (com.ibm.adtech.jastor.Thing) mbox.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_mbox = __item;
								break;
							}
						}
						if (found)
							mbox.remove(_mbox);
						else {
							try {
								_mbox = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_mbox = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForMbox;
						synchronized (listeners) {
							consumersForMbox = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForMbox.iterator();iter.hasNext();){
							GroupListener listener=(GroupListener)iter.next();
							listener.mboxRemoved(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,_mbox);
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
						GroupListener listener=(GroupListener)iter.next();
						listener.nameRemoved(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,literal);
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
						GroupListener listener=(GroupListener)iter.next();
						listener.dnaChecksumRemoved(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(ageProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (age != null && age.equals(literal))
					age = null;
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						GroupListener listener=(GroupListener)iter.next();
						listener.ageChanged(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this);
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
							GroupListener listener=(GroupListener)iter.next();
							listener.phoneRemoved(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,_phone);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(genderProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (gender != null && gender.equals(literal))
					gender = null;
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						GroupListener listener=(GroupListener)iter.next();
						listener.genderChanged(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this);
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
							GroupListener listener=(GroupListener)iter.next();
							listener.pageRemoved(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,_page);
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
						GroupListener listener=(GroupListener)iter.next();
						listener.nickRemoved(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,literal);
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
							GroupListener listener=(GroupListener)iter.next();
							listener.logoRemoved(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,_logo);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(weblogProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.foaf.Document _weblog = null;
					if (weblog != null) {
						boolean found = false;
						for (int i=0;i<weblog.size();i++) {
							de.m0ep.uni.ma.rdfs.foaf.Document __item = (de.m0ep.uni.ma.rdfs.foaf.Document) weblog.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_weblog = __item;
								break;
							}
						}
						if (found)
							weblog.remove(_weblog);
						else {
							try {
								_weblog = de.m0ep.uni.ma.rdfs.foaf.Factory.getDocument(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_weblog = de.m0ep.uni.ma.rdfs.foaf.Factory.getDocument(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForWeblog;
						synchronized (listeners) {
							consumersForWeblog = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForWeblog.iterator();iter.hasNext();){
							GroupListener listener=(GroupListener)iter.next();
							listener.weblogRemoved(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,_weblog);
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
							GroupListener listener=(GroupListener)iter.next();
							listener.makerRemoved(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,_maker);
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
						GroupListener listener=(GroupListener)iter.next();
						listener.givennameRemoved(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(accountProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.foaf.OnlineAccount _account = null;
					if (account != null) {
						boolean found = false;
						for (int i=0;i<account.size();i++) {
							de.m0ep.uni.ma.rdfs.foaf.OnlineAccount __item = (de.m0ep.uni.ma.rdfs.foaf.OnlineAccount) account.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_account = __item;
								break;
							}
						}
						if (found)
							account.remove(_account);
						else {
							try {
								_account = de.m0ep.uni.ma.rdfs.foaf.Factory.getOnlineAccount(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_account = de.m0ep.uni.ma.rdfs.foaf.Factory.getOnlineAccount(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForAccount;
						synchronized (listeners) {
							consumersForAccount = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForAccount.iterator();iter.hasNext();){
							GroupListener listener=(GroupListener)iter.next();
							listener.accountRemoved(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,_account);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(openidProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.foaf.Document _openid = null;
					if (openid != null) {
						boolean found = false;
						for (int i=0;i<openid.size();i++) {
							de.m0ep.uni.ma.rdfs.foaf.Document __item = (de.m0ep.uni.ma.rdfs.foaf.Document) openid.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_openid = __item;
								break;
							}
						}
						if (found)
							openid.remove(_openid);
						else {
							try {
								_openid = de.m0ep.uni.ma.rdfs.foaf.Factory.getDocument(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_openid = de.m0ep.uni.ma.rdfs.foaf.Factory.getDocument(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForOpenid;
						synchronized (listeners) {
							consumersForOpenid = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForOpenid.iterator();iter.hasNext();){
							GroupListener listener=(GroupListener)iter.next();
							listener.openidRemoved(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,_openid);
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
						GroupListener listener=(GroupListener)iter.next();
						listener.titleRemoved(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(birthdayProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (birthday != null && birthday.equals(literal))
					birthday = null;
				if (listeners != null) {
					java.util.ArrayList consumers;
					synchronized (listeners) {
						consumers = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumers.iterator();iter.hasNext();){
						GroupListener listener=(GroupListener)iter.next();
						listener.birthdayChanged(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this);
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
						GroupListener listener=(GroupListener)iter.next();
						listener.givenNameRemoved(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,literal);
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
							GroupListener listener=(GroupListener)iter.next();
							listener.homepageRemoved(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,_homepage);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(yahooChatIDProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (yahooChatID != null) {
					if (yahooChatID.contains(literal))
						yahooChatID.remove(literal);
				}
				if (listeners != null) {
					java.util.ArrayList consumersForYahooChatID;
					synchronized (listeners) {
						consumersForYahooChatID = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForYahooChatID.iterator();iter.hasNext();){
						GroupListener listener=(GroupListener)iter.next();
						listener.yahooChatIDRemoved(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(icqChatIDProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (icqChatID != null) {
					if (icqChatID.contains(literal))
						icqChatID.remove(literal);
				}
				if (listeners != null) {
					java.util.ArrayList consumersForIcqChatID;
					synchronized (listeners) {
						consumersForIcqChatID = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForIcqChatID.iterator();iter.hasNext();){
						GroupListener listener=(GroupListener)iter.next();
						listener.icqChatIDRemoved(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(holdsAccountProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.foaf.OnlineAccount _holdsAccount = null;
					if (holdsAccount != null) {
						boolean found = false;
						for (int i=0;i<holdsAccount.size();i++) {
							de.m0ep.uni.ma.rdfs.foaf.OnlineAccount __item = (de.m0ep.uni.ma.rdfs.foaf.OnlineAccount) holdsAccount.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_holdsAccount = __item;
								break;
							}
						}
						if (found)
							holdsAccount.remove(_holdsAccount);
						else {
							try {
								_holdsAccount = de.m0ep.uni.ma.rdfs.foaf.Factory.getOnlineAccount(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_holdsAccount = de.m0ep.uni.ma.rdfs.foaf.Factory.getOnlineAccount(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForHoldsAccount;
						synchronized (listeners) {
							consumersForHoldsAccount = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForHoldsAccount.iterator();iter.hasNext();){
							GroupListener listener=(GroupListener)iter.next();
							listener.holdsAccountRemoved(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,_holdsAccount);
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
							GroupListener listener=(GroupListener)iter.next();
							listener.themeRemoved(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,_theme);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(madeProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					com.ibm.adtech.jastor.Thing _made = null;
					if (made != null) {
						boolean found = false;
						for (int i=0;i<made.size();i++) {
							com.ibm.adtech.jastor.Thing __item = (com.ibm.adtech.jastor.Thing) made.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_made = __item;
								break;
							}
						}
						if (found)
							made.remove(_made);
						else {
							try {
								_made = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_made = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForMade;
						synchronized (listeners) {
							consumersForMade = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForMade.iterator();iter.hasNext();){
							GroupListener listener=(GroupListener)iter.next();
							listener.madeRemoved(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,_made);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(mbox__sha1sumProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (mbox__sha1sum != null) {
					if (mbox__sha1sum.contains(literal))
						mbox__sha1sum.remove(literal);
				}
				if (listeners != null) {
					java.util.ArrayList consumersForMbox__sha1sum;
					synchronized (listeners) {
						consumersForMbox__sha1sum = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForMbox__sha1sum.iterator();iter.hasNext();){
						GroupListener listener=(GroupListener)iter.next();
						listener.mbox__sha1sumRemoved(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(topic__interestProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					com.ibm.adtech.jastor.Thing _topic__interest = null;
					if (topic__interest != null) {
						boolean found = false;
						for (int i=0;i<topic__interest.size();i++) {
							com.ibm.adtech.jastor.Thing __item = (com.ibm.adtech.jastor.Thing) topic__interest.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_topic__interest = __item;
								break;
							}
						}
						if (found)
							topic__interest.remove(_topic__interest);
						else {
							try {
								_topic__interest = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_topic__interest = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForTopic__interest;
						synchronized (listeners) {
							consumersForTopic__interest = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForTopic__interest.iterator();iter.hasNext();){
							GroupListener listener=(GroupListener)iter.next();
							listener.topic__interestRemoved(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,_topic__interest);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(interestProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.foaf.Document _interest = null;
					if (interest != null) {
						boolean found = false;
						for (int i=0;i<interest.size();i++) {
							de.m0ep.uni.ma.rdfs.foaf.Document __item = (de.m0ep.uni.ma.rdfs.foaf.Document) interest.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_interest = __item;
								break;
							}
						}
						if (found)
							interest.remove(_interest);
						else {
							try {
								_interest = de.m0ep.uni.ma.rdfs.foaf.Factory.getDocument(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_interest = de.m0ep.uni.ma.rdfs.foaf.Factory.getDocument(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForInterest;
						synchronized (listeners) {
							consumersForInterest = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForInterest.iterator();iter.hasNext();){
							GroupListener listener=(GroupListener)iter.next();
							listener.interestRemoved(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,_interest);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(aimChatIDProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (aimChatID != null) {
					if (aimChatID.contains(literal))
						aimChatID.remove(literal);
				}
				if (listeners != null) {
					java.util.ArrayList consumersForAimChatID;
					synchronized (listeners) {
						consumersForAimChatID = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForAimChatID.iterator();iter.hasNext();){
						GroupListener listener=(GroupListener)iter.next();
						listener.aimChatIDRemoved(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(skypeIDProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (skypeID != null) {
					if (skypeID.contains(literal))
						skypeID.remove(literal);
				}
				if (listeners != null) {
					java.util.ArrayList consumersForSkypeID;
					synchronized (listeners) {
						consumersForSkypeID = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForSkypeID.iterator();iter.hasNext();){
						GroupListener listener=(GroupListener)iter.next();
						listener.skypeIDRemoved(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(tipjarProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.foaf.Document _tipjar = null;
					if (tipjar != null) {
						boolean found = false;
						for (int i=0;i<tipjar.size();i++) {
							de.m0ep.uni.ma.rdfs.foaf.Document __item = (de.m0ep.uni.ma.rdfs.foaf.Document) tipjar.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_tipjar = __item;
								break;
							}
						}
						if (found)
							tipjar.remove(_tipjar);
						else {
							try {
								_tipjar = de.m0ep.uni.ma.rdfs.foaf.Factory.getDocument(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_tipjar = de.m0ep.uni.ma.rdfs.foaf.Factory.getDocument(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForTipjar;
						synchronized (listeners) {
							consumersForTipjar = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForTipjar.iterator();iter.hasNext();){
							GroupListener listener=(GroupListener)iter.next();
							listener.tipjarRemoved(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,_tipjar);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(statusProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (status != null) {
					if (status.contains(literal))
						status.remove(literal);
				}
				if (listeners != null) {
					java.util.ArrayList consumersForStatus;
					synchronized (listeners) {
						consumersForStatus = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForStatus.iterator();iter.hasNext();){
						GroupListener listener=(GroupListener)iter.next();
						listener.statusRemoved(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(jabberIDProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (jabberID != null) {
					if (jabberID.contains(literal))
						jabberID.remove(literal);
				}
				if (listeners != null) {
					java.util.ArrayList consumersForJabberID;
					synchronized (listeners) {
						consumersForJabberID = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForJabberID.iterator();iter.hasNext();){
						GroupListener listener=(GroupListener)iter.next();
						listener.jabberIDRemoved(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(msnChatIDProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (msnChatID != null) {
					if (msnChatID.contains(literal))
						msnChatID.remove(literal);
				}
				if (listeners != null) {
					java.util.ArrayList consumersForMsnChatID;
					synchronized (listeners) {
						consumersForMsnChatID = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForMsnChatID.iterator();iter.hasNext();){
						GroupListener listener=(GroupListener)iter.next();
						listener.msnChatIDRemoved(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(memberProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.foaf.Agent _member = null;
					if (member != null) {
						boolean found = false;
						for (int i=0;i<member.size();i++) {
							de.m0ep.uni.ma.rdfs.foaf.Agent __item = (de.m0ep.uni.ma.rdfs.foaf.Agent) member.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_member = __item;
								break;
							}
						}
						if (found)
							member.remove(_member);
						else {
							try {
								_member = de.m0ep.uni.ma.rdfs.foaf.Factory.getAgent(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_member = de.m0ep.uni.ma.rdfs.foaf.Factory.getAgent(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForMember;
						synchronized (listeners) {
							consumersForMember = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForMember.iterator();iter.hasNext();){
							GroupListener listener=(GroupListener)iter.next();
							listener.memberRemoved(de.m0ep.uni.ma.rdfs.foaf.GroupImpl.this,_member);
						}
					}
				}
				return;
			}
		}

	//}
	


}