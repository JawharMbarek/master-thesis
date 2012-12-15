

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
 * Implementation of {@link de.m0ep.uni.ma.rdfs.sioc.Post}
 * Use the de.m0ep.uni.ma.rdfs.sioc.Factory to create instances of this class.
 * <p>(URI: http://rdfs.org/sioc/ns#Post)</p>
 * <br>
 */
public class PostImpl extends com.ibm.adtech.jastor.ThingImpl implements de.m0ep.uni.ma.rdfs.sioc.Post {
	

	private static com.hp.hpl.jena.rdf.model.Property next__versionProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#next_version");
	private java.util.ArrayList next__version;
	private static com.hp.hpl.jena.rdf.model.Property earlier__versionProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#earlier_version");
	private static com.hp.hpl.jena.rdf.model.Property ip__addressProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#ip_address");
	private java.util.ArrayList ip__address;
	private static com.hp.hpl.jena.rdf.model.Property has__modifierProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#has_modifier");
	private java.util.ArrayList has__modifier;
	private static com.hp.hpl.jena.rdf.model.Property linkProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#link");
	private java.util.ArrayList link;
	private static com.hp.hpl.jena.rdf.model.Property previous__by__dateProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#previous_by_date");
	private java.util.ArrayList previous__by__date;
	private static com.hp.hpl.jena.rdf.model.Property noteProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#note");
	private java.util.ArrayList note;
	private static com.hp.hpl.jena.rdf.model.Property has__creatorProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#has_creator");
	private java.util.ArrayList has__creator;
	private static com.hp.hpl.jena.rdf.model.Property has__ownerProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#has_owner");
	private java.util.ArrayList has__owner;
	private static com.hp.hpl.jena.rdf.model.Property addressed__toProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#addressed_to");
	private java.util.ArrayList addressed__to;
	private static com.hp.hpl.jena.rdf.model.Property descriptionProperty = ResourceFactory.createProperty("http://purl.org/dc/terms/description");
	private java.util.ArrayList description;
	private static com.hp.hpl.jena.rdf.model.Property latest__versionProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#latest_version");
	private java.util.ArrayList latest__version;
	private static com.hp.hpl.jena.rdf.model.Property previous__versionProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#previous_version");
	private java.util.ArrayList previous__version;
	private static com.hp.hpl.jena.rdf.model.Property next__by__dateProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#next_by_date");
	private java.util.ArrayList next__by__date;
	private static com.hp.hpl.jena.rdf.model.Property has__containerProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#has_container");
	private java.util.ArrayList has__container;
	private static com.hp.hpl.jena.rdf.model.Property contentProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#content");
	private java.util.ArrayList content;
	private static com.hp.hpl.jena.rdf.model.Property subjectProperty = ResourceFactory.createProperty("http://purl.org/dc/terms/subject");
	private java.util.ArrayList subject;
	private static com.hp.hpl.jena.rdf.model.Property num__authorsProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#num_authors");
	private java.util.ArrayList num__authors;
	private static com.hp.hpl.jena.rdf.model.Property num__viewsProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#num_views");
	private java.util.ArrayList num__views;
	private static com.hp.hpl.jena.rdf.model.Property siblingProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#sibling");
	private static com.hp.hpl.jena.rdf.model.Property referencesProperty = ResourceFactory.createProperty("http://purl.org/dc/terms/references");
	private java.util.ArrayList references;
	private static com.hp.hpl.jena.rdf.model.Property has__discussionProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#has_discussion");
	private java.util.ArrayList has__discussion;
	private static com.hp.hpl.jena.rdf.model.Property topicProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#topic");
	private java.util.ArrayList topic;
	private static com.hp.hpl.jena.rdf.model.Property last__activity__dateProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#last_activity_date");
	private java.util.ArrayList last__activity__date;
	private static com.hp.hpl.jena.rdf.model.Property idProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#id");
	private java.util.ArrayList id;
	private static com.hp.hpl.jena.rdf.model.Property links__toProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#links_to");
	private java.util.ArrayList links__to;
	private static com.hp.hpl.jena.rdf.model.Property has__replyProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#has_reply");
	private java.util.ArrayList has__reply;
	private static com.hp.hpl.jena.rdf.model.Property last__reply__dateProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#last_reply_date");
	private java.util.ArrayList last__reply__date;
	private static com.hp.hpl.jena.rdf.model.Property num__repliesProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#num_replies");
	private java.util.ArrayList num__replies;
	private static com.hp.hpl.jena.rdf.model.Property has__functionProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#has_function");
	private java.util.ArrayList has__function;
	private static com.hp.hpl.jena.rdf.model.Property attachmentProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#attachment");
	private java.util.ArrayList attachment;
	private static com.hp.hpl.jena.rdf.model.Property nameProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#name");
	private java.util.ArrayList name;
	private static com.hp.hpl.jena.rdf.model.Property has__spaceProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#has_space");
	private java.util.ArrayList has__space;
	private static com.hp.hpl.jena.rdf.model.Property related__toProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#related_to");
	private java.util.ArrayList related__to;
	private static com.hp.hpl.jena.rdf.model.Property dateProperty = ResourceFactory.createProperty("http://purl.org/dc/terms/date");
	private java.util.ArrayList date;
	private static com.hp.hpl.jena.rdf.model.Property embeds__knowledgeProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#embeds_knowledge");
	private java.util.ArrayList embeds__knowledge;
	private static com.hp.hpl.jena.rdf.model.Property titleProperty = ResourceFactory.createProperty("http://purl.org/dc/terms/title");
	private java.util.ArrayList title;
	private static com.hp.hpl.jena.rdf.model.Property scope__ofProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#scope_of");
	private java.util.ArrayList scope__of;
	private static com.hp.hpl.jena.rdf.model.Property feedProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#feed");
	private java.util.ArrayList feed;
	private static com.hp.hpl.jena.rdf.model.Property later__versionProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#later_version");
	private static com.hp.hpl.jena.rdf.model.Property reply__ofProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#reply_of");
	private java.util.ArrayList reply__of;
	private static com.hp.hpl.jena.rdf.model.Property depictionProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/depiction");
	private java.util.ArrayList depiction;
	private static com.hp.hpl.jena.rdf.model.Property aboutProperty = ResourceFactory.createProperty("http://rdfs.org/sioc/ns#about");
	private java.util.ArrayList about;
	private static com.hp.hpl.jena.rdf.model.Property fundedByProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/fundedBy");
	private java.util.ArrayList fundedBy;
	private static com.hp.hpl.jena.rdf.model.Property ns3_nameProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/name");
	private java.util.ArrayList ns3_name;
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
	private static com.hp.hpl.jena.rdf.model.Property ns3_topicProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/topic");
	private java.util.ArrayList ns3_topic;
	private static com.hp.hpl.jena.rdf.model.Property ns3_titleProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/title");
	private java.util.ArrayList ns3_title;
	private static com.hp.hpl.jena.rdf.model.Property givenNameProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/givenName");
	private java.util.ArrayList givenName;
	private static com.hp.hpl.jena.rdf.model.Property homepageProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/homepage");
	private java.util.ArrayList homepage;
	private static com.hp.hpl.jena.rdf.model.Property sha1Property = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/sha1");
	private java.util.ArrayList sha1;
	private static com.hp.hpl.jena.rdf.model.Property themeProperty = ResourceFactory.createProperty("http://xmlns.com/foaf/0.1/theme");
	private java.util.ArrayList theme;
 

	PostImpl(Resource resource, Model model) throws JastorException {
		super(resource, model);
		setupModelListener();
	}     
    	
	static PostImpl getPost(Resource resource, Model model) throws JastorException {
		return new PostImpl(resource, model);
	}
	    
	static PostImpl createPost(Resource resource, Model model) throws JastorException { 
		PostImpl impl = new PostImpl(resource, model);
		
		if (!impl._model.contains(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(impl._resource, RDF.type, Post.TYPE)))
			impl._model.add(impl._resource, RDF.type, Post.TYPE);
		impl.addSuperTypes();
		impl.addHasValueValues();
		return impl;
	}
	
	void addSuperTypes() {
		if (!_model.contains(_resource, RDF.type, de.m0ep.uni.ma.rdfs.sioc.Item.TYPE))
			_model.add(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(_resource, RDF.type, de.m0ep.uni.ma.rdfs.sioc.Item.TYPE));     
		if (!_model.contains(_resource, RDF.type, de.m0ep.uni.ma.rdfs.foaf.Document.TYPE))
			_model.add(new com.hp.hpl.jena.rdf.model.impl.StatementImpl(_resource, RDF.type, de.m0ep.uni.ma.rdfs.foaf.Document.TYPE));     
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
		it = _model.listStatements(_resource,next__versionProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,earlier__versionProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,ip__addressProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,has__modifierProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,linkProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,previous__by__dateProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,noteProperty,(RDFNode)null);
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
		it = _model.listStatements(_resource,addressed__toProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,descriptionProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,latest__versionProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,previous__versionProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,next__by__dateProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,has__containerProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,contentProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,subjectProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,num__authorsProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,num__viewsProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,siblingProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,referencesProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,has__discussionProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,topicProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,last__activity__dateProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,idProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,links__toProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,has__replyProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,last__reply__dateProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,num__repliesProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,has__functionProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,attachmentProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,nameProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,has__spaceProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,related__toProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,dateProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,embeds__knowledgeProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,titleProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,scope__ofProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,feedProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,later__versionProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,reply__ofProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,depictionProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,aboutProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,fundedByProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,ns3_nameProperty,(RDFNode)null);
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
		it = _model.listStatements(_resource,ns3_topicProperty,(RDFNode)null);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,ns3_titleProperty,(RDFNode)null);
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
		it = _model.listStatements(_resource,RDF.type, de.m0ep.uni.ma.rdfs.sioc.Post.TYPE);
		while (it.hasNext()) {
			list.add(it.next());
		}
		it = _model.listStatements(_resource,RDF.type, de.m0ep.uni.ma.rdfs.sioc.Item.TYPE);
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
		next__version = null;
		earlier__version = null;
		ip__address = null;
		has__modifier = null;
		link = null;
		previous__by__date = null;
		note = null;
		has__creator = null;
		has__owner = null;
		addressed__to = null;
		description = null;
		latest__version = null;
		previous__version = null;
		next__by__date = null;
		has__container = null;
		content = null;
		subject = null;
		num__authors = null;
		num__views = null;
		sibling = null;
		references = null;
		has__discussion = null;
		topic = null;
		last__activity__date = null;
		id = null;
		links__to = null;
		has__reply = null;
		last__reply__date = null;
		num__replies = null;
		has__function = null;
		attachment = null;
		name = null;
		has__space = null;
		related__to = null;
		date = null;
		embeds__knowledge = null;
		title = null;
		scope__of = null;
		feed = null;
		later__version = null;
		reply__of = null;
		depiction = null;
		about = null;
		fundedBy = null;
		ns3_name = null;
		primaryTopic = null;
		dnaChecksum = null;
		phone = null;
		page = null;
		nick = null;
		logo = null;
		maker = null;
		givenname = null;
		ns3_topic = null;
		ns3_title = null;
		givenName = null;
		homepage = null;
		sha1 = null;
		theme = null;
	}

	private com.hp.hpl.jena.rdf.model.Literal createLiteral(Object obj) {
		return _model.createTypedLiteral(obj);
	}


	private void initNext__version() throws JastorException {
		this.next__version = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, next__versionProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#next_version properties in Post model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				de.m0ep.uni.ma.rdfs.sioc.Item next__version = de.m0ep.uni.ma.rdfs.sioc.Factory.getItem(resource,_model);
				this.next__version.add(next__version);
			}
		}
	}

	public java.util.Iterator getNext__version() throws JastorException {
		if (next__version == null)
			initNext__version();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(next__version,_resource,next__versionProperty,true);
	}

	public void addNext__version(de.m0ep.uni.ma.rdfs.sioc.Item next__version) throws JastorException {
		if (this.next__version == null)
			initNext__version();
		if (this.next__version.contains(next__version)) {
			this.next__version.remove(next__version);
			this.next__version.add(next__version);
			return;
		}
		this.next__version.add(next__version);
		_model.add(_model.createStatement(_resource,next__versionProperty,next__version.resource()));
	}
	
	public de.m0ep.uni.ma.rdfs.sioc.Item addNext__version() throws JastorException {
		de.m0ep.uni.ma.rdfs.sioc.Item next__version = de.m0ep.uni.ma.rdfs.sioc.Factory.createItem(_model.createResource(),_model);
		if (this.next__version == null)
			initNext__version();
		this.next__version.add(next__version);
		_model.add(_model.createStatement(_resource,next__versionProperty,next__version.resource()));
		return next__version;
	}
	
	public de.m0ep.uni.ma.rdfs.sioc.Item addNext__version(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		de.m0ep.uni.ma.rdfs.sioc.Item next__version = de.m0ep.uni.ma.rdfs.sioc.Factory.getItem(resource,_model);
		if (this.next__version == null)
			initNext__version();
		if (this.next__version.contains(next__version))
			return next__version;
		this.next__version.add(next__version);
		_model.add(_model.createStatement(_resource,next__versionProperty,next__version.resource()));
		return next__version;
	}
	
	public void removeNext__version(de.m0ep.uni.ma.rdfs.sioc.Item next__version) throws JastorException {
		if (this.next__version == null)
			initNext__version();
		if (!this.next__version.contains(next__version))
			return;
		if (!_model.contains(_resource, next__versionProperty, next__version.resource()))
			return;
		this.next__version.remove(next__version);
		_model.removeAll(_resource, next__versionProperty, next__version.resource());
	}
		 


	private void initIp__address() throws JastorException {
		ip__address = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, ip__addressProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#ip_address properties in Post model not a Literal", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
			ip__address.add(literal);
		}
	}

	public java.util.Iterator getIp__address() throws JastorException {
		if (ip__address == null)
			initIp__address();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(ip__address,_resource,ip__addressProperty,false);
	}

	public void addIp__address(com.hp.hpl.jena.rdf.model.Literal ip__address) throws JastorException {
		if (this.ip__address == null)
			initIp__address();
		if (this.ip__address.contains(ip__address))
			return;
		if (_model.contains(_resource, ip__addressProperty, createLiteral(ip__address)))
			return;
		this.ip__address.add(ip__address);
		_model.add(_resource, ip__addressProperty, ip__address);
	}
	
	public void removeIp__address(com.hp.hpl.jena.rdf.model.Literal ip__address) throws JastorException {
		if (this.ip__address == null)
			initIp__address();
		if (!this.ip__address.contains(ip__address))
			return;
		if (!_model.contains(_resource, ip__addressProperty, createLiteral(ip__address)))
			return;
		this.ip__address.remove(ip__address);
		_model.removeAll(_resource, ip__addressProperty, createLiteral(ip__address));
	}


	private void initHas__modifier() throws JastorException {
		this.has__modifier = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, has__modifierProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#has_modifier properties in Post model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				de.m0ep.uni.ma.rdfs.sioc.UserAccount has__modifier = de.m0ep.uni.ma.rdfs.sioc.Factory.getUserAccount(resource,_model);
				this.has__modifier.add(has__modifier);
			}
		}
	}

	public java.util.Iterator getHas__modifier() throws JastorException {
		if (has__modifier == null)
			initHas__modifier();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(has__modifier,_resource,has__modifierProperty,true);
	}

	public void addHas__modifier(de.m0ep.uni.ma.rdfs.sioc.UserAccount has__modifier) throws JastorException {
		if (this.has__modifier == null)
			initHas__modifier();
		if (this.has__modifier.contains(has__modifier)) {
			this.has__modifier.remove(has__modifier);
			this.has__modifier.add(has__modifier);
			return;
		}
		this.has__modifier.add(has__modifier);
		_model.add(_model.createStatement(_resource,has__modifierProperty,has__modifier.resource()));
	}
	
	public de.m0ep.uni.ma.rdfs.sioc.UserAccount addHas__modifier() throws JastorException {
		de.m0ep.uni.ma.rdfs.sioc.UserAccount has__modifier = de.m0ep.uni.ma.rdfs.sioc.Factory.createUserAccount(_model.createResource(),_model);
		if (this.has__modifier == null)
			initHas__modifier();
		this.has__modifier.add(has__modifier);
		_model.add(_model.createStatement(_resource,has__modifierProperty,has__modifier.resource()));
		return has__modifier;
	}
	
	public de.m0ep.uni.ma.rdfs.sioc.UserAccount addHas__modifier(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		de.m0ep.uni.ma.rdfs.sioc.UserAccount has__modifier = de.m0ep.uni.ma.rdfs.sioc.Factory.getUserAccount(resource,_model);
		if (this.has__modifier == null)
			initHas__modifier();
		if (this.has__modifier.contains(has__modifier))
			return has__modifier;
		this.has__modifier.add(has__modifier);
		_model.add(_model.createStatement(_resource,has__modifierProperty,has__modifier.resource()));
		return has__modifier;
	}
	
	public void removeHas__modifier(de.m0ep.uni.ma.rdfs.sioc.UserAccount has__modifier) throws JastorException {
		if (this.has__modifier == null)
			initHas__modifier();
		if (!this.has__modifier.contains(has__modifier))
			return;
		if (!_model.contains(_resource, has__modifierProperty, has__modifier.resource()))
			return;
		this.has__modifier.remove(has__modifier);
		_model.removeAll(_resource, has__modifierProperty, has__modifier.resource());
	}
		 

	private void initLink() throws JastorException {
		this.link = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, linkProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#link properties in Post model not a Resource", stmt.getObject());
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
		 

	private void initPrevious__by__date() throws JastorException {
		this.previous__by__date = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, previous__by__dateProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#previous_by_date properties in Post model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				de.m0ep.uni.ma.rdfs.sioc.Item previous__by__date = de.m0ep.uni.ma.rdfs.sioc.Factory.getItem(resource,_model);
				this.previous__by__date.add(previous__by__date);
			}
		}
	}

	public java.util.Iterator getPrevious__by__date() throws JastorException {
		if (previous__by__date == null)
			initPrevious__by__date();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(previous__by__date,_resource,previous__by__dateProperty,true);
	}

	public void addPrevious__by__date(de.m0ep.uni.ma.rdfs.sioc.Item previous__by__date) throws JastorException {
		if (this.previous__by__date == null)
			initPrevious__by__date();
		if (this.previous__by__date.contains(previous__by__date)) {
			this.previous__by__date.remove(previous__by__date);
			this.previous__by__date.add(previous__by__date);
			return;
		}
		this.previous__by__date.add(previous__by__date);
		_model.add(_model.createStatement(_resource,previous__by__dateProperty,previous__by__date.resource()));
	}
	
	public de.m0ep.uni.ma.rdfs.sioc.Item addPrevious__by__date() throws JastorException {
		de.m0ep.uni.ma.rdfs.sioc.Item previous__by__date = de.m0ep.uni.ma.rdfs.sioc.Factory.createItem(_model.createResource(),_model);
		if (this.previous__by__date == null)
			initPrevious__by__date();
		this.previous__by__date.add(previous__by__date);
		_model.add(_model.createStatement(_resource,previous__by__dateProperty,previous__by__date.resource()));
		return previous__by__date;
	}
	
	public de.m0ep.uni.ma.rdfs.sioc.Item addPrevious__by__date(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		de.m0ep.uni.ma.rdfs.sioc.Item previous__by__date = de.m0ep.uni.ma.rdfs.sioc.Factory.getItem(resource,_model);
		if (this.previous__by__date == null)
			initPrevious__by__date();
		if (this.previous__by__date.contains(previous__by__date))
			return previous__by__date;
		this.previous__by__date.add(previous__by__date);
		_model.add(_model.createStatement(_resource,previous__by__dateProperty,previous__by__date.resource()));
		return previous__by__date;
	}
	
	public void removePrevious__by__date(de.m0ep.uni.ma.rdfs.sioc.Item previous__by__date) throws JastorException {
		if (this.previous__by__date == null)
			initPrevious__by__date();
		if (!this.previous__by__date.contains(previous__by__date))
			return;
		if (!_model.contains(_resource, previous__by__dateProperty, previous__by__date.resource()))
			return;
		this.previous__by__date.remove(previous__by__date);
		_model.removeAll(_resource, previous__by__dateProperty, previous__by__date.resource());
	}
		 

	private void initNote() throws JastorException {
		note = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, noteProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#note properties in Post model not a Literal", stmt.getObject());
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


	private void initHas__creator() throws JastorException {
		this.has__creator = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, has__creatorProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#has_creator properties in Post model not a Resource", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#has_owner properties in Post model not a Resource", stmt.getObject());
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
		 

	private void initAddressed__to() throws JastorException {
		this.addressed__to = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, addressed__toProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#addressed_to properties in Post model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				com.ibm.adtech.jastor.Thing addressed__to = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
				this.addressed__to.add(addressed__to);
			}
		}
	}

	public java.util.Iterator getAddressed__to() throws JastorException {
		if (addressed__to == null)
			initAddressed__to();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(addressed__to,_resource,addressed__toProperty,true);
	}

	public void addAddressed__to(com.ibm.adtech.jastor.Thing addressed__to) throws JastorException {
		if (this.addressed__to == null)
			initAddressed__to();
		if (this.addressed__to.contains(addressed__to)) {
			this.addressed__to.remove(addressed__to);
			this.addressed__to.add(addressed__to);
			return;
		}
		this.addressed__to.add(addressed__to);
		_model.add(_model.createStatement(_resource,addressed__toProperty,addressed__to.resource()));
	}
	
	public com.ibm.adtech.jastor.Thing addAddressed__to() throws JastorException {
		com.ibm.adtech.jastor.Thing addressed__to = com.ibm.adtech.jastor.ThingFactory.createThing(_model.createResource(),_model);
		if (this.addressed__to == null)
			initAddressed__to();
		this.addressed__to.add(addressed__to);
		_model.add(_model.createStatement(_resource,addressed__toProperty,addressed__to.resource()));
		return addressed__to;
	}
	
	public com.ibm.adtech.jastor.Thing addAddressed__to(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		com.ibm.adtech.jastor.Thing addressed__to = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
		if (this.addressed__to == null)
			initAddressed__to();
		if (this.addressed__to.contains(addressed__to))
			return addressed__to;
		this.addressed__to.add(addressed__to);
		_model.add(_model.createStatement(_resource,addressed__toProperty,addressed__to.resource()));
		return addressed__to;
	}
	
	public void removeAddressed__to(com.ibm.adtech.jastor.Thing addressed__to) throws JastorException {
		if (this.addressed__to == null)
			initAddressed__to();
		if (!this.addressed__to.contains(addressed__to))
			return;
		if (!_model.contains(_resource, addressed__toProperty, addressed__to.resource()))
			return;
		this.addressed__to.remove(addressed__to);
		_model.removeAll(_resource, addressed__toProperty, addressed__to.resource());
	}
		 

	private void initDescription() throws JastorException {
		description = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, descriptionProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://purl.org/dc/terms/description properties in Post model not a Literal", stmt.getObject());
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


	private void initLatest__version() throws JastorException {
		this.latest__version = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, latest__versionProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#latest_version properties in Post model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				de.m0ep.uni.ma.rdfs.sioc.Item latest__version = de.m0ep.uni.ma.rdfs.sioc.Factory.getItem(resource,_model);
				this.latest__version.add(latest__version);
			}
		}
	}

	public java.util.Iterator getLatest__version() throws JastorException {
		if (latest__version == null)
			initLatest__version();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(latest__version,_resource,latest__versionProperty,true);
	}

	public void addLatest__version(de.m0ep.uni.ma.rdfs.sioc.Item latest__version) throws JastorException {
		if (this.latest__version == null)
			initLatest__version();
		if (this.latest__version.contains(latest__version)) {
			this.latest__version.remove(latest__version);
			this.latest__version.add(latest__version);
			return;
		}
		this.latest__version.add(latest__version);
		_model.add(_model.createStatement(_resource,latest__versionProperty,latest__version.resource()));
	}
	
	public de.m0ep.uni.ma.rdfs.sioc.Item addLatest__version() throws JastorException {
		de.m0ep.uni.ma.rdfs.sioc.Item latest__version = de.m0ep.uni.ma.rdfs.sioc.Factory.createItem(_model.createResource(),_model);
		if (this.latest__version == null)
			initLatest__version();
		this.latest__version.add(latest__version);
		_model.add(_model.createStatement(_resource,latest__versionProperty,latest__version.resource()));
		return latest__version;
	}
	
	public de.m0ep.uni.ma.rdfs.sioc.Item addLatest__version(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		de.m0ep.uni.ma.rdfs.sioc.Item latest__version = de.m0ep.uni.ma.rdfs.sioc.Factory.getItem(resource,_model);
		if (this.latest__version == null)
			initLatest__version();
		if (this.latest__version.contains(latest__version))
			return latest__version;
		this.latest__version.add(latest__version);
		_model.add(_model.createStatement(_resource,latest__versionProperty,latest__version.resource()));
		return latest__version;
	}
	
	public void removeLatest__version(de.m0ep.uni.ma.rdfs.sioc.Item latest__version) throws JastorException {
		if (this.latest__version == null)
			initLatest__version();
		if (!this.latest__version.contains(latest__version))
			return;
		if (!_model.contains(_resource, latest__versionProperty, latest__version.resource()))
			return;
		this.latest__version.remove(latest__version);
		_model.removeAll(_resource, latest__versionProperty, latest__version.resource());
	}
		 

	private void initPrevious__version() throws JastorException {
		this.previous__version = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, previous__versionProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#previous_version properties in Post model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				de.m0ep.uni.ma.rdfs.sioc.Item previous__version = de.m0ep.uni.ma.rdfs.sioc.Factory.getItem(resource,_model);
				this.previous__version.add(previous__version);
			}
		}
	}

	public java.util.Iterator getPrevious__version() throws JastorException {
		if (previous__version == null)
			initPrevious__version();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(previous__version,_resource,previous__versionProperty,true);
	}

	public void addPrevious__version(de.m0ep.uni.ma.rdfs.sioc.Item previous__version) throws JastorException {
		if (this.previous__version == null)
			initPrevious__version();
		if (this.previous__version.contains(previous__version)) {
			this.previous__version.remove(previous__version);
			this.previous__version.add(previous__version);
			return;
		}
		this.previous__version.add(previous__version);
		_model.add(_model.createStatement(_resource,previous__versionProperty,previous__version.resource()));
	}
	
	public de.m0ep.uni.ma.rdfs.sioc.Item addPrevious__version() throws JastorException {
		de.m0ep.uni.ma.rdfs.sioc.Item previous__version = de.m0ep.uni.ma.rdfs.sioc.Factory.createItem(_model.createResource(),_model);
		if (this.previous__version == null)
			initPrevious__version();
		this.previous__version.add(previous__version);
		_model.add(_model.createStatement(_resource,previous__versionProperty,previous__version.resource()));
		return previous__version;
	}
	
	public de.m0ep.uni.ma.rdfs.sioc.Item addPrevious__version(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		de.m0ep.uni.ma.rdfs.sioc.Item previous__version = de.m0ep.uni.ma.rdfs.sioc.Factory.getItem(resource,_model);
		if (this.previous__version == null)
			initPrevious__version();
		if (this.previous__version.contains(previous__version))
			return previous__version;
		this.previous__version.add(previous__version);
		_model.add(_model.createStatement(_resource,previous__versionProperty,previous__version.resource()));
		return previous__version;
	}
	
	public void removePrevious__version(de.m0ep.uni.ma.rdfs.sioc.Item previous__version) throws JastorException {
		if (this.previous__version == null)
			initPrevious__version();
		if (!this.previous__version.contains(previous__version))
			return;
		if (!_model.contains(_resource, previous__versionProperty, previous__version.resource()))
			return;
		this.previous__version.remove(previous__version);
		_model.removeAll(_resource, previous__versionProperty, previous__version.resource());
	}
		 

	private void initNext__by__date() throws JastorException {
		this.next__by__date = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, next__by__dateProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#next_by_date properties in Post model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				de.m0ep.uni.ma.rdfs.sioc.Item next__by__date = de.m0ep.uni.ma.rdfs.sioc.Factory.getItem(resource,_model);
				this.next__by__date.add(next__by__date);
			}
		}
	}

	public java.util.Iterator getNext__by__date() throws JastorException {
		if (next__by__date == null)
			initNext__by__date();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(next__by__date,_resource,next__by__dateProperty,true);
	}

	public void addNext__by__date(de.m0ep.uni.ma.rdfs.sioc.Item next__by__date) throws JastorException {
		if (this.next__by__date == null)
			initNext__by__date();
		if (this.next__by__date.contains(next__by__date)) {
			this.next__by__date.remove(next__by__date);
			this.next__by__date.add(next__by__date);
			return;
		}
		this.next__by__date.add(next__by__date);
		_model.add(_model.createStatement(_resource,next__by__dateProperty,next__by__date.resource()));
	}
	
	public de.m0ep.uni.ma.rdfs.sioc.Item addNext__by__date() throws JastorException {
		de.m0ep.uni.ma.rdfs.sioc.Item next__by__date = de.m0ep.uni.ma.rdfs.sioc.Factory.createItem(_model.createResource(),_model);
		if (this.next__by__date == null)
			initNext__by__date();
		this.next__by__date.add(next__by__date);
		_model.add(_model.createStatement(_resource,next__by__dateProperty,next__by__date.resource()));
		return next__by__date;
	}
	
	public de.m0ep.uni.ma.rdfs.sioc.Item addNext__by__date(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		de.m0ep.uni.ma.rdfs.sioc.Item next__by__date = de.m0ep.uni.ma.rdfs.sioc.Factory.getItem(resource,_model);
		if (this.next__by__date == null)
			initNext__by__date();
		if (this.next__by__date.contains(next__by__date))
			return next__by__date;
		this.next__by__date.add(next__by__date);
		_model.add(_model.createStatement(_resource,next__by__dateProperty,next__by__date.resource()));
		return next__by__date;
	}
	
	public void removeNext__by__date(de.m0ep.uni.ma.rdfs.sioc.Item next__by__date) throws JastorException {
		if (this.next__by__date == null)
			initNext__by__date();
		if (!this.next__by__date.contains(next__by__date))
			return;
		if (!_model.contains(_resource, next__by__dateProperty, next__by__date.resource()))
			return;
		this.next__by__date.remove(next__by__date);
		_model.removeAll(_resource, next__by__dateProperty, next__by__date.resource());
	}
		 

	private void initHas__container() throws JastorException {
		this.has__container = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, has__containerProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#has_container properties in Post model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				de.m0ep.uni.ma.rdfs.sioc.Container has__container = de.m0ep.uni.ma.rdfs.sioc.Factory.getContainer(resource,_model);
				this.has__container.add(has__container);
			}
		}
	}

	public java.util.Iterator getHas__container() throws JastorException {
		if (has__container == null)
			initHas__container();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(has__container,_resource,has__containerProperty,true);
	}

	public void addHas__container(de.m0ep.uni.ma.rdfs.sioc.Container has__container) throws JastorException {
		if (this.has__container == null)
			initHas__container();
		if (this.has__container.contains(has__container)) {
			this.has__container.remove(has__container);
			this.has__container.add(has__container);
			return;
		}
		this.has__container.add(has__container);
		_model.add(_model.createStatement(_resource,has__containerProperty,has__container.resource()));
	}
	
	public de.m0ep.uni.ma.rdfs.sioc.Container addHas__container() throws JastorException {
		de.m0ep.uni.ma.rdfs.sioc.Container has__container = de.m0ep.uni.ma.rdfs.sioc.Factory.createContainer(_model.createResource(),_model);
		if (this.has__container == null)
			initHas__container();
		this.has__container.add(has__container);
		_model.add(_model.createStatement(_resource,has__containerProperty,has__container.resource()));
		return has__container;
	}
	
	public de.m0ep.uni.ma.rdfs.sioc.Container addHas__container(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		de.m0ep.uni.ma.rdfs.sioc.Container has__container = de.m0ep.uni.ma.rdfs.sioc.Factory.getContainer(resource,_model);
		if (this.has__container == null)
			initHas__container();
		if (this.has__container.contains(has__container))
			return has__container;
		this.has__container.add(has__container);
		_model.add(_model.createStatement(_resource,has__containerProperty,has__container.resource()));
		return has__container;
	}
	
	public void removeHas__container(de.m0ep.uni.ma.rdfs.sioc.Container has__container) throws JastorException {
		if (this.has__container == null)
			initHas__container();
		if (!this.has__container.contains(has__container))
			return;
		if (!_model.contains(_resource, has__containerProperty, has__container.resource()))
			return;
		this.has__container.remove(has__container);
		_model.removeAll(_resource, has__containerProperty, has__container.resource());
	}
		 

	private void initContent() throws JastorException {
		content = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, contentProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#content properties in Post model not a Literal", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
			content.add(literal);
		}
	}

	public java.util.Iterator getContent() throws JastorException {
		if (content == null)
			initContent();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(content,_resource,contentProperty,false);
	}

	public void addContent(com.hp.hpl.jena.rdf.model.Literal content) throws JastorException {
		if (this.content == null)
			initContent();
		if (this.content.contains(content))
			return;
		if (_model.contains(_resource, contentProperty, createLiteral(content)))
			return;
		this.content.add(content);
		_model.add(_resource, contentProperty, content);
	}
	
	public void removeContent(com.hp.hpl.jena.rdf.model.Literal content) throws JastorException {
		if (this.content == null)
			initContent();
		if (!this.content.contains(content))
			return;
		if (!_model.contains(_resource, contentProperty, createLiteral(content)))
			return;
		this.content.remove(content);
		_model.removeAll(_resource, contentProperty, createLiteral(content));
	}


	private void initSubject() throws JastorException {
		this.subject = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, subjectProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://purl.org/dc/terms/subject properties in Post model not a Resource", stmt.getObject());
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
		 

	private void initNum__authors() throws JastorException {
		num__authors = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, num__authorsProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#num_authors properties in Post model not a Literal", stmt.getObject());
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


	private void initNum__views() throws JastorException {
		num__views = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, num__viewsProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#num_views properties in Post model not a Literal", stmt.getObject());
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



	private void initReferences() throws JastorException {
		this.references = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, referencesProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://purl.org/dc/terms/references properties in Post model not a Resource", stmt.getObject());
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
		 

	private void initHas__discussion() throws JastorException {
		this.has__discussion = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, has__discussionProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#has_discussion properties in Post model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				com.ibm.adtech.jastor.Thing has__discussion = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
				this.has__discussion.add(has__discussion);
			}
		}
	}

	public java.util.Iterator getHas__discussion() throws JastorException {
		if (has__discussion == null)
			initHas__discussion();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(has__discussion,_resource,has__discussionProperty,true);
	}

	public void addHas__discussion(com.ibm.adtech.jastor.Thing has__discussion) throws JastorException {
		if (this.has__discussion == null)
			initHas__discussion();
		if (this.has__discussion.contains(has__discussion)) {
			this.has__discussion.remove(has__discussion);
			this.has__discussion.add(has__discussion);
			return;
		}
		this.has__discussion.add(has__discussion);
		_model.add(_model.createStatement(_resource,has__discussionProperty,has__discussion.resource()));
	}
	
	public com.ibm.adtech.jastor.Thing addHas__discussion() throws JastorException {
		com.ibm.adtech.jastor.Thing has__discussion = com.ibm.adtech.jastor.ThingFactory.createThing(_model.createResource(),_model);
		if (this.has__discussion == null)
			initHas__discussion();
		this.has__discussion.add(has__discussion);
		_model.add(_model.createStatement(_resource,has__discussionProperty,has__discussion.resource()));
		return has__discussion;
	}
	
	public com.ibm.adtech.jastor.Thing addHas__discussion(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		com.ibm.adtech.jastor.Thing has__discussion = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
		if (this.has__discussion == null)
			initHas__discussion();
		if (this.has__discussion.contains(has__discussion))
			return has__discussion;
		this.has__discussion.add(has__discussion);
		_model.add(_model.createStatement(_resource,has__discussionProperty,has__discussion.resource()));
		return has__discussion;
	}
	
	public void removeHas__discussion(com.ibm.adtech.jastor.Thing has__discussion) throws JastorException {
		if (this.has__discussion == null)
			initHas__discussion();
		if (!this.has__discussion.contains(has__discussion))
			return;
		if (!_model.contains(_resource, has__discussionProperty, has__discussion.resource()))
			return;
		this.has__discussion.remove(has__discussion);
		_model.removeAll(_resource, has__discussionProperty, has__discussion.resource());
	}
		 

	private void initTopic() throws JastorException {
		this.topic = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, topicProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#topic properties in Post model not a Resource", stmt.getObject());
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
		 

	private void initLast__activity__date() throws JastorException {
		last__activity__date = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, last__activity__dateProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#last_activity_date properties in Post model not a Literal", stmt.getObject());
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


	private void initId() throws JastorException {
		id = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, idProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#id properties in Post model not a Literal", stmt.getObject());
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


	private void initLinks__to() throws JastorException {
		this.links__to = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, links__toProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#links_to properties in Post model not a Resource", stmt.getObject());
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
		 

	private void initHas__reply() throws JastorException {
		this.has__reply = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, has__replyProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#has_reply properties in Post model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				de.m0ep.uni.ma.rdfs.sioc.Item has__reply = de.m0ep.uni.ma.rdfs.sioc.Factory.getItem(resource,_model);
				this.has__reply.add(has__reply);
			}
		}
	}

	public java.util.Iterator getHas__reply() throws JastorException {
		if (has__reply == null)
			initHas__reply();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(has__reply,_resource,has__replyProperty,true);
	}

	public void addHas__reply(de.m0ep.uni.ma.rdfs.sioc.Item has__reply) throws JastorException {
		if (this.has__reply == null)
			initHas__reply();
		if (this.has__reply.contains(has__reply)) {
			this.has__reply.remove(has__reply);
			this.has__reply.add(has__reply);
			return;
		}
		this.has__reply.add(has__reply);
		_model.add(_model.createStatement(_resource,has__replyProperty,has__reply.resource()));
	}
	
	public de.m0ep.uni.ma.rdfs.sioc.Item addHas__reply() throws JastorException {
		de.m0ep.uni.ma.rdfs.sioc.Item has__reply = de.m0ep.uni.ma.rdfs.sioc.Factory.createItem(_model.createResource(),_model);
		if (this.has__reply == null)
			initHas__reply();
		this.has__reply.add(has__reply);
		_model.add(_model.createStatement(_resource,has__replyProperty,has__reply.resource()));
		return has__reply;
	}
	
	public de.m0ep.uni.ma.rdfs.sioc.Item addHas__reply(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		de.m0ep.uni.ma.rdfs.sioc.Item has__reply = de.m0ep.uni.ma.rdfs.sioc.Factory.getItem(resource,_model);
		if (this.has__reply == null)
			initHas__reply();
		if (this.has__reply.contains(has__reply))
			return has__reply;
		this.has__reply.add(has__reply);
		_model.add(_model.createStatement(_resource,has__replyProperty,has__reply.resource()));
		return has__reply;
	}
	
	public void removeHas__reply(de.m0ep.uni.ma.rdfs.sioc.Item has__reply) throws JastorException {
		if (this.has__reply == null)
			initHas__reply();
		if (!this.has__reply.contains(has__reply))
			return;
		if (!_model.contains(_resource, has__replyProperty, has__reply.resource()))
			return;
		this.has__reply.remove(has__reply);
		_model.removeAll(_resource, has__replyProperty, has__reply.resource());
	}
		 

	private void initLast__reply__date() throws JastorException {
		last__reply__date = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, last__reply__dateProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#last_reply_date properties in Post model not a Literal", stmt.getObject());
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


	private void initNum__replies() throws JastorException {
		num__replies = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, num__repliesProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#num_replies properties in Post model not a Literal", stmt.getObject());
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


	private void initHas__function() throws JastorException {
		this.has__function = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, has__functionProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#has_function properties in Post model not a Resource", stmt.getObject());
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
		 

	private void initAttachment() throws JastorException {
		this.attachment = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, attachmentProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#attachment properties in Post model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				com.ibm.adtech.jastor.Thing attachment = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
				this.attachment.add(attachment);
			}
		}
	}

	public java.util.Iterator getAttachment() throws JastorException {
		if (attachment == null)
			initAttachment();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(attachment,_resource,attachmentProperty,true);
	}

	public void addAttachment(com.ibm.adtech.jastor.Thing attachment) throws JastorException {
		if (this.attachment == null)
			initAttachment();
		if (this.attachment.contains(attachment)) {
			this.attachment.remove(attachment);
			this.attachment.add(attachment);
			return;
		}
		this.attachment.add(attachment);
		_model.add(_model.createStatement(_resource,attachmentProperty,attachment.resource()));
	}
	
	public com.ibm.adtech.jastor.Thing addAttachment() throws JastorException {
		com.ibm.adtech.jastor.Thing attachment = com.ibm.adtech.jastor.ThingFactory.createThing(_model.createResource(),_model);
		if (this.attachment == null)
			initAttachment();
		this.attachment.add(attachment);
		_model.add(_model.createStatement(_resource,attachmentProperty,attachment.resource()));
		return attachment;
	}
	
	public com.ibm.adtech.jastor.Thing addAttachment(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		com.ibm.adtech.jastor.Thing attachment = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
		if (this.attachment == null)
			initAttachment();
		if (this.attachment.contains(attachment))
			return attachment;
		this.attachment.add(attachment);
		_model.add(_model.createStatement(_resource,attachmentProperty,attachment.resource()));
		return attachment;
	}
	
	public void removeAttachment(com.ibm.adtech.jastor.Thing attachment) throws JastorException {
		if (this.attachment == null)
			initAttachment();
		if (!this.attachment.contains(attachment))
			return;
		if (!_model.contains(_resource, attachmentProperty, attachment.resource()))
			return;
		this.attachment.remove(attachment);
		_model.removeAll(_resource, attachmentProperty, attachment.resource());
	}
		 

	private void initName() throws JastorException {
		name = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, nameProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#name properties in Post model not a Literal", stmt.getObject());
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


	private void initHas__space() throws JastorException {
		this.has__space = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, has__spaceProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#has_space properties in Post model not a Resource", stmt.getObject());
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
		 

	private void initRelated__to() throws JastorException {
		this.related__to = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, related__toProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#related_to properties in Post model not a Resource", stmt.getObject());
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
		 

	private void initDate() throws JastorException {
		date = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, dateProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://purl.org/dc/terms/date properties in Post model not a Literal", stmt.getObject());
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


	private void initEmbeds__knowledge() throws JastorException {
		this.embeds__knowledge = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, embeds__knowledgeProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#embeds_knowledge properties in Post model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				com.ibm.adtech.jastor.Thing embeds__knowledge = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
				this.embeds__knowledge.add(embeds__knowledge);
			}
		}
	}

	public java.util.Iterator getEmbeds__knowledge() throws JastorException {
		if (embeds__knowledge == null)
			initEmbeds__knowledge();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(embeds__knowledge,_resource,embeds__knowledgeProperty,true);
	}

	public void addEmbeds__knowledge(com.ibm.adtech.jastor.Thing embeds__knowledge) throws JastorException {
		if (this.embeds__knowledge == null)
			initEmbeds__knowledge();
		if (this.embeds__knowledge.contains(embeds__knowledge)) {
			this.embeds__knowledge.remove(embeds__knowledge);
			this.embeds__knowledge.add(embeds__knowledge);
			return;
		}
		this.embeds__knowledge.add(embeds__knowledge);
		_model.add(_model.createStatement(_resource,embeds__knowledgeProperty,embeds__knowledge.resource()));
	}
	
	public com.ibm.adtech.jastor.Thing addEmbeds__knowledge() throws JastorException {
		com.ibm.adtech.jastor.Thing embeds__knowledge = com.ibm.adtech.jastor.ThingFactory.createThing(_model.createResource(),_model);
		if (this.embeds__knowledge == null)
			initEmbeds__knowledge();
		this.embeds__knowledge.add(embeds__knowledge);
		_model.add(_model.createStatement(_resource,embeds__knowledgeProperty,embeds__knowledge.resource()));
		return embeds__knowledge;
	}
	
	public com.ibm.adtech.jastor.Thing addEmbeds__knowledge(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		com.ibm.adtech.jastor.Thing embeds__knowledge = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
		if (this.embeds__knowledge == null)
			initEmbeds__knowledge();
		if (this.embeds__knowledge.contains(embeds__knowledge))
			return embeds__knowledge;
		this.embeds__knowledge.add(embeds__knowledge);
		_model.add(_model.createStatement(_resource,embeds__knowledgeProperty,embeds__knowledge.resource()));
		return embeds__knowledge;
	}
	
	public void removeEmbeds__knowledge(com.ibm.adtech.jastor.Thing embeds__knowledge) throws JastorException {
		if (this.embeds__knowledge == null)
			initEmbeds__knowledge();
		if (!this.embeds__knowledge.contains(embeds__knowledge))
			return;
		if (!_model.contains(_resource, embeds__knowledgeProperty, embeds__knowledge.resource()))
			return;
		this.embeds__knowledge.remove(embeds__knowledge);
		_model.removeAll(_resource, embeds__knowledgeProperty, embeds__knowledge.resource());
	}
		 

	private void initTitle() throws JastorException {
		title = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, titleProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://purl.org/dc/terms/title properties in Post model not a Literal", stmt.getObject());
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


	private void initScope__of() throws JastorException {
		this.scope__of = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, scope__ofProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#scope_of properties in Post model not a Resource", stmt.getObject());
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
		 

	private void initFeed() throws JastorException {
		this.feed = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, feedProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#feed properties in Post model not a Resource", stmt.getObject());
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
		 


	private void initReply__of() throws JastorException {
		this.reply__of = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, reply__ofProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#reply_of properties in Post model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				de.m0ep.uni.ma.rdfs.sioc.Item reply__of = de.m0ep.uni.ma.rdfs.sioc.Factory.getItem(resource,_model);
				this.reply__of.add(reply__of);
			}
		}
	}

	public java.util.Iterator getReply__of() throws JastorException {
		if (reply__of == null)
			initReply__of();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(reply__of,_resource,reply__ofProperty,true);
	}

	public void addReply__of(de.m0ep.uni.ma.rdfs.sioc.Item reply__of) throws JastorException {
		if (this.reply__of == null)
			initReply__of();
		if (this.reply__of.contains(reply__of)) {
			this.reply__of.remove(reply__of);
			this.reply__of.add(reply__of);
			return;
		}
		this.reply__of.add(reply__of);
		_model.add(_model.createStatement(_resource,reply__ofProperty,reply__of.resource()));
	}
	
	public de.m0ep.uni.ma.rdfs.sioc.Item addReply__of() throws JastorException {
		de.m0ep.uni.ma.rdfs.sioc.Item reply__of = de.m0ep.uni.ma.rdfs.sioc.Factory.createItem(_model.createResource(),_model);
		if (this.reply__of == null)
			initReply__of();
		this.reply__of.add(reply__of);
		_model.add(_model.createStatement(_resource,reply__ofProperty,reply__of.resource()));
		return reply__of;
	}
	
	public de.m0ep.uni.ma.rdfs.sioc.Item addReply__of(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		de.m0ep.uni.ma.rdfs.sioc.Item reply__of = de.m0ep.uni.ma.rdfs.sioc.Factory.getItem(resource,_model);
		if (this.reply__of == null)
			initReply__of();
		if (this.reply__of.contains(reply__of))
			return reply__of;
		this.reply__of.add(reply__of);
		_model.add(_model.createStatement(_resource,reply__ofProperty,reply__of.resource()));
		return reply__of;
	}
	
	public void removeReply__of(de.m0ep.uni.ma.rdfs.sioc.Item reply__of) throws JastorException {
		if (this.reply__of == null)
			initReply__of();
		if (!this.reply__of.contains(reply__of))
			return;
		if (!_model.contains(_resource, reply__ofProperty, reply__of.resource()))
			return;
		this.reply__of.remove(reply__of);
		_model.removeAll(_resource, reply__ofProperty, reply__of.resource());
	}
		 

	private void initDepiction() throws JastorException {
		this.depiction = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, depictionProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/depiction properties in Post model not a Resource", stmt.getObject());
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
		 

	private void initAbout() throws JastorException {
		this.about = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, aboutProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://rdfs.org/sioc/ns#about properties in Post model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				com.ibm.adtech.jastor.Thing about = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
				this.about.add(about);
			}
		}
	}

	public java.util.Iterator getAbout() throws JastorException {
		if (about == null)
			initAbout();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(about,_resource,aboutProperty,true);
	}

	public void addAbout(com.ibm.adtech.jastor.Thing about) throws JastorException {
		if (this.about == null)
			initAbout();
		if (this.about.contains(about)) {
			this.about.remove(about);
			this.about.add(about);
			return;
		}
		this.about.add(about);
		_model.add(_model.createStatement(_resource,aboutProperty,about.resource()));
	}
	
	public com.ibm.adtech.jastor.Thing addAbout() throws JastorException {
		com.ibm.adtech.jastor.Thing about = com.ibm.adtech.jastor.ThingFactory.createThing(_model.createResource(),_model);
		if (this.about == null)
			initAbout();
		this.about.add(about);
		_model.add(_model.createStatement(_resource,aboutProperty,about.resource()));
		return about;
	}
	
	public com.ibm.adtech.jastor.Thing addAbout(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		com.ibm.adtech.jastor.Thing about = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
		if (this.about == null)
			initAbout();
		if (this.about.contains(about))
			return about;
		this.about.add(about);
		_model.add(_model.createStatement(_resource,aboutProperty,about.resource()));
		return about;
	}
	
	public void removeAbout(com.ibm.adtech.jastor.Thing about) throws JastorException {
		if (this.about == null)
			initAbout();
		if (!this.about.contains(about))
			return;
		if (!_model.contains(_resource, aboutProperty, about.resource()))
			return;
		this.about.remove(about);
		_model.removeAll(_resource, aboutProperty, about.resource());
	}
		 

	private void initFundedBy() throws JastorException {
		this.fundedBy = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, fundedByProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/fundedBy properties in Post model not a Resource", stmt.getObject());
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
		 

	private void initNs3_Name() throws JastorException {
		ns3_name = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, ns3_nameProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/name properties in Post model not a Literal", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
			ns3_name.add(literal);
		}
	}

	public java.util.Iterator getNs3_Name() throws JastorException {
		if (ns3_name == null)
			initNs3_Name();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(ns3_name,_resource,ns3_nameProperty,false);
	}

	public void addNs3_Name(com.hp.hpl.jena.rdf.model.Literal ns3_name) throws JastorException {
		if (this.ns3_name == null)
			initNs3_Name();
		if (this.ns3_name.contains(ns3_name))
			return;
		if (_model.contains(_resource, ns3_nameProperty, createLiteral(ns3_name)))
			return;
		this.ns3_name.add(ns3_name);
		_model.add(_resource, ns3_nameProperty, ns3_name);
	}
	
	public void removeNs3_Name(com.hp.hpl.jena.rdf.model.Literal ns3_name) throws JastorException {
		if (this.ns3_name == null)
			initNs3_Name();
		if (!this.ns3_name.contains(ns3_name))
			return;
		if (!_model.contains(_resource, ns3_nameProperty, createLiteral(ns3_name)))
			return;
		this.ns3_name.remove(ns3_name);
		_model.removeAll(_resource, ns3_nameProperty, createLiteral(ns3_name));
	}


	public com.ibm.adtech.jastor.Thing getPrimaryTopic() throws JastorException {
		if (primaryTopic != null)
			return primaryTopic;
		com.hp.hpl.jena.rdf.model.Statement stmt = _model.getProperty(_resource, primaryTopicProperty);
		if (stmt == null)
			return null;
		if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
			throw new JastorInvalidRDFNodeException(uri() + ": primaryTopic getProperty() in de.m0ep.uni.ma.rdfs.sioc.Post model not Resource", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/dnaChecksum properties in Post model not a Literal", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/phone properties in Post model not a Resource", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/page properties in Post model not a Resource", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/nick properties in Post model not a Literal", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/logo properties in Post model not a Resource", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/maker properties in Post model not a Resource", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/givenname properties in Post model not a Literal", stmt.getObject());
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


	private void initNs3_Topic() throws JastorException {
		this.ns3_topic = new java.util.ArrayList();
		StmtIterator it = _model.listStatements(_resource, ns3_topicProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/topic properties in Post model not a Resource", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
			if (true) { // don't check resource type if the property range is Resource
				com.ibm.adtech.jastor.Thing ns3_topic = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
				this.ns3_topic.add(ns3_topic);
			}
		}
	}

	public java.util.Iterator getNs3_Topic() throws JastorException {
		if (ns3_topic == null)
			initNs3_Topic();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(ns3_topic,_resource,ns3_topicProperty,true);
	}

	public void addNs3_Topic(com.ibm.adtech.jastor.Thing ns3_topic) throws JastorException {
		if (this.ns3_topic == null)
			initNs3_Topic();
		if (this.ns3_topic.contains(ns3_topic)) {
			this.ns3_topic.remove(ns3_topic);
			this.ns3_topic.add(ns3_topic);
			return;
		}
		this.ns3_topic.add(ns3_topic);
		_model.add(_model.createStatement(_resource,ns3_topicProperty,ns3_topic.resource()));
	}
	
	public com.ibm.adtech.jastor.Thing addNs3_Topic() throws JastorException {
		com.ibm.adtech.jastor.Thing ns3_topic = com.ibm.adtech.jastor.ThingFactory.createThing(_model.createResource(),_model);
		if (this.ns3_topic == null)
			initNs3_Topic();
		this.ns3_topic.add(ns3_topic);
		_model.add(_model.createStatement(_resource,ns3_topicProperty,ns3_topic.resource()));
		return ns3_topic;
	}
	
	public com.ibm.adtech.jastor.Thing addNs3_Topic(com.hp.hpl.jena.rdf.model.Resource resource) throws JastorException {
		com.ibm.adtech.jastor.Thing ns3_topic = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
		if (this.ns3_topic == null)
			initNs3_Topic();
		if (this.ns3_topic.contains(ns3_topic))
			return ns3_topic;
		this.ns3_topic.add(ns3_topic);
		_model.add(_model.createStatement(_resource,ns3_topicProperty,ns3_topic.resource()));
		return ns3_topic;
	}
	
	public void removeNs3_Topic(com.ibm.adtech.jastor.Thing ns3_topic) throws JastorException {
		if (this.ns3_topic == null)
			initNs3_Topic();
		if (!this.ns3_topic.contains(ns3_topic))
			return;
		if (!_model.contains(_resource, ns3_topicProperty, ns3_topic.resource()))
			return;
		this.ns3_topic.remove(ns3_topic);
		_model.removeAll(_resource, ns3_topicProperty, ns3_topic.resource());
	}
		 

	private void initNs3_Title() throws JastorException {
		ns3_title = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, ns3_titleProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/title properties in Post model not a Literal", stmt.getObject());
			com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
			ns3_title.add(literal);
		}
	}

	public java.util.Iterator getNs3_Title() throws JastorException {
		if (ns3_title == null)
			initNs3_Title();
		return new com.ibm.adtech.jastor.util.CachedPropertyIterator(ns3_title,_resource,ns3_titleProperty,false);
	}

	public void addNs3_Title(com.hp.hpl.jena.rdf.model.Literal ns3_title) throws JastorException {
		if (this.ns3_title == null)
			initNs3_Title();
		if (this.ns3_title.contains(ns3_title))
			return;
		if (_model.contains(_resource, ns3_titleProperty, createLiteral(ns3_title)))
			return;
		this.ns3_title.add(ns3_title);
		_model.add(_resource, ns3_titleProperty, ns3_title);
	}
	
	public void removeNs3_Title(com.hp.hpl.jena.rdf.model.Literal ns3_title) throws JastorException {
		if (this.ns3_title == null)
			initNs3_Title();
		if (!this.ns3_title.contains(ns3_title))
			return;
		if (!_model.contains(_resource, ns3_titleProperty, createLiteral(ns3_title)))
			return;
		this.ns3_title.remove(ns3_title);
		_model.removeAll(_resource, ns3_titleProperty, createLiteral(ns3_title));
	}


	private void initGivenName() throws JastorException {
		givenName = new java.util.ArrayList();
		
		StmtIterator it = _model.listStatements(_resource, givenNameProperty, (RDFNode)null);
		while(it.hasNext()) {
			com.hp.hpl.jena.rdf.model.Statement stmt = (com.hp.hpl.jena.rdf.model.Statement)it.next();
			if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/givenName properties in Post model not a Literal", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/homepage properties in Post model not a Resource", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/sha1 properties in Post model not a Literal", stmt.getObject());
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
				throw new JastorInvalidRDFNodeException (uri() + ": One of the http://xmlns.com/foaf/0.1/theme properties in Post model not a Resource", stmt.getObject());
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
		  


	private java.util.ArrayList listeners;
	
	public void registerListener(ThingListener listener) {
		if (!(listener instanceof PostListener))
			throw new IllegalArgumentException("ThingListener must be instance of PostListener"); 
		if (listeners == null)
			setupModelListener();
		if(!this.listeners.contains(listener)){
			this.listeners.add((PostListener)listener);
		}
	}
	
	public void unregisterListener(ThingListener listener) {
		if (!(listener instanceof PostListener))
			throw new IllegalArgumentException("ThingListener must be instance of PostListener"); 
		if (listeners == null)
			return;
		if (this.listeners.contains(listener)){
			listeners.remove(listener);
		}
	}



	
		public void addedStatement(com.hp.hpl.jena.rdf.model.Statement stmt) {

			if (stmt.getPredicate().equals(next__versionProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.sioc.Item _next__version = null;
					try {
						_next__version = de.m0ep.uni.ma.rdfs.sioc.Factory.getItem(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (next__version == null) {
						try {
							initNext__version();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!next__version.contains(_next__version))
						next__version.add(_next__version);
					if (listeners != null) {
						java.util.ArrayList consumersForNext__version;
						synchronized (listeners) {
							consumersForNext__version = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForNext__version.iterator();iter.hasNext();){
							PostListener listener=(PostListener)iter.next();
							listener.next__versionAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_next__version);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(earlier__versionProperty)) {
				return;
			}
			if (stmt.getPredicate().equals(ip__addressProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (ip__address == null)
					try {
						initIp__address();
					} catch (JastorException e) {
						e.printStackTrace();
						return;
					}
				if (!ip__address.contains(literal))
					ip__address.add(literal);
				if (listeners != null) {
					java.util.ArrayList consumersForIp__address;
					synchronized (listeners) {
						consumersForIp__address = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForIp__address.iterator();iter.hasNext();){
						PostListener listener=(PostListener)iter.next();
						listener.ip__addressAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(has__modifierProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.sioc.UserAccount _has__modifier = null;
					try {
						_has__modifier = de.m0ep.uni.ma.rdfs.sioc.Factory.getUserAccount(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (has__modifier == null) {
						try {
							initHas__modifier();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!has__modifier.contains(_has__modifier))
						has__modifier.add(_has__modifier);
					if (listeners != null) {
						java.util.ArrayList consumersForHas__modifier;
						synchronized (listeners) {
							consumersForHas__modifier = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForHas__modifier.iterator();iter.hasNext();){
							PostListener listener=(PostListener)iter.next();
							listener.has__modifierAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_has__modifier);
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
							PostListener listener=(PostListener)iter.next();
							listener.linkAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_link);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(previous__by__dateProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.sioc.Item _previous__by__date = null;
					try {
						_previous__by__date = de.m0ep.uni.ma.rdfs.sioc.Factory.getItem(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (previous__by__date == null) {
						try {
							initPrevious__by__date();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!previous__by__date.contains(_previous__by__date))
						previous__by__date.add(_previous__by__date);
					if (listeners != null) {
						java.util.ArrayList consumersForPrevious__by__date;
						synchronized (listeners) {
							consumersForPrevious__by__date = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForPrevious__by__date.iterator();iter.hasNext();){
							PostListener listener=(PostListener)iter.next();
							listener.previous__by__dateAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_previous__by__date);
						}
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
						PostListener listener=(PostListener)iter.next();
						listener.noteAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,literal);
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
							PostListener listener=(PostListener)iter.next();
							listener.has__creatorAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_has__creator);
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
							PostListener listener=(PostListener)iter.next();
							listener.has__ownerAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_has__owner);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(addressed__toProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					com.ibm.adtech.jastor.Thing _addressed__to = null;
					try {
						_addressed__to = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (addressed__to == null) {
						try {
							initAddressed__to();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!addressed__to.contains(_addressed__to))
						addressed__to.add(_addressed__to);
					if (listeners != null) {
						java.util.ArrayList consumersForAddressed__to;
						synchronized (listeners) {
							consumersForAddressed__to = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForAddressed__to.iterator();iter.hasNext();){
							PostListener listener=(PostListener)iter.next();
							listener.addressed__toAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_addressed__to);
						}
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
						PostListener listener=(PostListener)iter.next();
						listener.descriptionAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(latest__versionProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.sioc.Item _latest__version = null;
					try {
						_latest__version = de.m0ep.uni.ma.rdfs.sioc.Factory.getItem(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (latest__version == null) {
						try {
							initLatest__version();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!latest__version.contains(_latest__version))
						latest__version.add(_latest__version);
					if (listeners != null) {
						java.util.ArrayList consumersForLatest__version;
						synchronized (listeners) {
							consumersForLatest__version = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForLatest__version.iterator();iter.hasNext();){
							PostListener listener=(PostListener)iter.next();
							listener.latest__versionAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_latest__version);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(previous__versionProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.sioc.Item _previous__version = null;
					try {
						_previous__version = de.m0ep.uni.ma.rdfs.sioc.Factory.getItem(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (previous__version == null) {
						try {
							initPrevious__version();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!previous__version.contains(_previous__version))
						previous__version.add(_previous__version);
					if (listeners != null) {
						java.util.ArrayList consumersForPrevious__version;
						synchronized (listeners) {
							consumersForPrevious__version = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForPrevious__version.iterator();iter.hasNext();){
							PostListener listener=(PostListener)iter.next();
							listener.previous__versionAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_previous__version);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(next__by__dateProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.sioc.Item _next__by__date = null;
					try {
						_next__by__date = de.m0ep.uni.ma.rdfs.sioc.Factory.getItem(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (next__by__date == null) {
						try {
							initNext__by__date();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!next__by__date.contains(_next__by__date))
						next__by__date.add(_next__by__date);
					if (listeners != null) {
						java.util.ArrayList consumersForNext__by__date;
						synchronized (listeners) {
							consumersForNext__by__date = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForNext__by__date.iterator();iter.hasNext();){
							PostListener listener=(PostListener)iter.next();
							listener.next__by__dateAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_next__by__date);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(has__containerProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.sioc.Container _has__container = null;
					try {
						_has__container = de.m0ep.uni.ma.rdfs.sioc.Factory.getContainer(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (has__container == null) {
						try {
							initHas__container();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!has__container.contains(_has__container))
						has__container.add(_has__container);
					if (listeners != null) {
						java.util.ArrayList consumersForHas__container;
						synchronized (listeners) {
							consumersForHas__container = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForHas__container.iterator();iter.hasNext();){
							PostListener listener=(PostListener)iter.next();
							listener.has__containerAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_has__container);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(contentProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (content == null)
					try {
						initContent();
					} catch (JastorException e) {
						e.printStackTrace();
						return;
					}
				if (!content.contains(literal))
					content.add(literal);
				if (listeners != null) {
					java.util.ArrayList consumersForContent;
					synchronized (listeners) {
						consumersForContent = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForContent.iterator();iter.hasNext();){
						PostListener listener=(PostListener)iter.next();
						listener.contentAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,literal);
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
							PostListener listener=(PostListener)iter.next();
							listener.subjectAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_subject);
						}
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
						PostListener listener=(PostListener)iter.next();
						listener.num__authorsAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,(java.lang.String)obj);
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
						PostListener listener=(PostListener)iter.next();
						listener.num__viewsAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(siblingProperty)) {
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
							PostListener listener=(PostListener)iter.next();
							listener.referencesAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_references);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(has__discussionProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					com.ibm.adtech.jastor.Thing _has__discussion = null;
					try {
						_has__discussion = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (has__discussion == null) {
						try {
							initHas__discussion();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!has__discussion.contains(_has__discussion))
						has__discussion.add(_has__discussion);
					if (listeners != null) {
						java.util.ArrayList consumersForHas__discussion;
						synchronized (listeners) {
							consumersForHas__discussion = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForHas__discussion.iterator();iter.hasNext();){
							PostListener listener=(PostListener)iter.next();
							listener.has__discussionAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_has__discussion);
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
							PostListener listener=(PostListener)iter.next();
							listener.topicAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_topic);
						}
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
						PostListener listener=(PostListener)iter.next();
						listener.last__activity__dateAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,literal);
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
						PostListener listener=(PostListener)iter.next();
						listener.idAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,literal);
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
							PostListener listener=(PostListener)iter.next();
							listener.links__toAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_links__to);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(has__replyProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.sioc.Item _has__reply = null;
					try {
						_has__reply = de.m0ep.uni.ma.rdfs.sioc.Factory.getItem(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (has__reply == null) {
						try {
							initHas__reply();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!has__reply.contains(_has__reply))
						has__reply.add(_has__reply);
					if (listeners != null) {
						java.util.ArrayList consumersForHas__reply;
						synchronized (listeners) {
							consumersForHas__reply = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForHas__reply.iterator();iter.hasNext();){
							PostListener listener=(PostListener)iter.next();
							listener.has__replyAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_has__reply);
						}
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
						PostListener listener=(PostListener)iter.next();
						listener.last__reply__dateAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,literal);
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
						PostListener listener=(PostListener)iter.next();
						listener.num__repliesAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,(java.lang.String)obj);
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
							PostListener listener=(PostListener)iter.next();
							listener.has__functionAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_has__function);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(attachmentProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					com.ibm.adtech.jastor.Thing _attachment = null;
					try {
						_attachment = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (attachment == null) {
						try {
							initAttachment();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!attachment.contains(_attachment))
						attachment.add(_attachment);
					if (listeners != null) {
						java.util.ArrayList consumersForAttachment;
						synchronized (listeners) {
							consumersForAttachment = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForAttachment.iterator();iter.hasNext();){
							PostListener listener=(PostListener)iter.next();
							listener.attachmentAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_attachment);
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
						PostListener listener=(PostListener)iter.next();
						listener.nameAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,literal);
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
							PostListener listener=(PostListener)iter.next();
							listener.has__spaceAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_has__space);
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
							PostListener listener=(PostListener)iter.next();
							listener.related__toAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_related__to);
						}
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
						PostListener listener=(PostListener)iter.next();
						listener.dateAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(embeds__knowledgeProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					com.ibm.adtech.jastor.Thing _embeds__knowledge = null;
					try {
						_embeds__knowledge = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (embeds__knowledge == null) {
						try {
							initEmbeds__knowledge();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!embeds__knowledge.contains(_embeds__knowledge))
						embeds__knowledge.add(_embeds__knowledge);
					if (listeners != null) {
						java.util.ArrayList consumersForEmbeds__knowledge;
						synchronized (listeners) {
							consumersForEmbeds__knowledge = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForEmbeds__knowledge.iterator();iter.hasNext();){
							PostListener listener=(PostListener)iter.next();
							listener.embeds__knowledgeAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_embeds__knowledge);
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
						PostListener listener=(PostListener)iter.next();
						listener.titleAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,literal);
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
							PostListener listener=(PostListener)iter.next();
							listener.scope__ofAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_scope__of);
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
							PostListener listener=(PostListener)iter.next();
							listener.feedAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_feed);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(later__versionProperty)) {
				return;
			}
			if (stmt.getPredicate().equals(reply__ofProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.sioc.Item _reply__of = null;
					try {
						_reply__of = de.m0ep.uni.ma.rdfs.sioc.Factory.getItem(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (reply__of == null) {
						try {
							initReply__of();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!reply__of.contains(_reply__of))
						reply__of.add(_reply__of);
					if (listeners != null) {
						java.util.ArrayList consumersForReply__of;
						synchronized (listeners) {
							consumersForReply__of = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForReply__of.iterator();iter.hasNext();){
							PostListener listener=(PostListener)iter.next();
							listener.reply__ofAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_reply__of);
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
							PostListener listener=(PostListener)iter.next();
							listener.depictionAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_depiction);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(aboutProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					com.ibm.adtech.jastor.Thing _about = null;
					try {
						_about = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (about == null) {
						try {
							initAbout();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!about.contains(_about))
						about.add(_about);
					if (listeners != null) {
						java.util.ArrayList consumersForAbout;
						synchronized (listeners) {
							consumersForAbout = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForAbout.iterator();iter.hasNext();){
							PostListener listener=(PostListener)iter.next();
							listener.aboutAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_about);
						}
					}
				}
				return;
			}
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
							PostListener listener=(PostListener)iter.next();
							listener.fundedByAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_fundedBy);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(ns3_nameProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (ns3_name == null)
					try {
						initNs3_Name();
					} catch (JastorException e) {
						e.printStackTrace();
						return;
					}
				if (!ns3_name.contains(literal))
					ns3_name.add(literal);
				if (listeners != null) {
					java.util.ArrayList consumersForNs3_Name;
					synchronized (listeners) {
						consumersForNs3_Name = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForNs3_Name.iterator();iter.hasNext();){
						PostListener listener=(PostListener)iter.next();
						listener.ns3_nameAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,literal);
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
						PostListener listener=(PostListener)iter.next();
						listener.primaryTopicChanged(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this);
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
						PostListener listener=(PostListener)iter.next();
						listener.dnaChecksumAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,literal);
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
							PostListener listener=(PostListener)iter.next();
							listener.phoneAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_phone);
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
							PostListener listener=(PostListener)iter.next();
							listener.pageAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_page);
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
						PostListener listener=(PostListener)iter.next();
						listener.nickAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,literal);
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
							PostListener listener=(PostListener)iter.next();
							listener.logoAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_logo);
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
							PostListener listener=(PostListener)iter.next();
							listener.makerAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_maker);
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
						PostListener listener=(PostListener)iter.next();
						listener.givennameAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(ns3_topicProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					com.ibm.adtech.jastor.Thing _ns3_topic = null;
					try {
						_ns3_topic = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
					} catch (JastorException e) {
						//e.printStackTrace();
					}
					if (ns3_topic == null) {
						try {
							initNs3_Topic();
						} catch (JastorException e) {
							e.printStackTrace();
							return;
						}
					}
					if (!ns3_topic.contains(_ns3_topic))
						ns3_topic.add(_ns3_topic);
					if (listeners != null) {
						java.util.ArrayList consumersForNs3_Topic;
						synchronized (listeners) {
							consumersForNs3_Topic = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForNs3_Topic.iterator();iter.hasNext();){
							PostListener listener=(PostListener)iter.next();
							listener.ns3_topicAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_ns3_topic);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(ns3_titleProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (ns3_title == null)
					try {
						initNs3_Title();
					} catch (JastorException e) {
						e.printStackTrace();
						return;
					}
				if (!ns3_title.contains(literal))
					ns3_title.add(literal);
				if (listeners != null) {
					java.util.ArrayList consumersForNs3_Title;
					synchronized (listeners) {
						consumersForNs3_Title = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForNs3_Title.iterator();iter.hasNext();){
						PostListener listener=(PostListener)iter.next();
						listener.ns3_titleAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,literal);
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
						PostListener listener=(PostListener)iter.next();
						listener.givenNameAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,literal);
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
							PostListener listener=(PostListener)iter.next();
							listener.homepageAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_homepage);
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
						PostListener listener=(PostListener)iter.next();
						listener.sha1Added(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,literal);
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
							PostListener listener=(PostListener)iter.next();
							listener.themeAdded(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_theme);
						}
					}
				}
				return;
			}
		}
		
		public void removedStatement(com.hp.hpl.jena.rdf.model.Statement stmt) {
//			if (!stmt.getSubject().equals(_resource))
//				return;
			if (stmt.getPredicate().equals(next__versionProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.sioc.Item _next__version = null;
					if (next__version != null) {
						boolean found = false;
						for (int i=0;i<next__version.size();i++) {
							de.m0ep.uni.ma.rdfs.sioc.Item __item = (de.m0ep.uni.ma.rdfs.sioc.Item) next__version.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_next__version = __item;
								break;
							}
						}
						if (found)
							next__version.remove(_next__version);
						else {
							try {
								_next__version = de.m0ep.uni.ma.rdfs.sioc.Factory.getItem(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_next__version = de.m0ep.uni.ma.rdfs.sioc.Factory.getItem(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForNext__version;
						synchronized (listeners) {
							consumersForNext__version = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForNext__version.iterator();iter.hasNext();){
							PostListener listener=(PostListener)iter.next();
							listener.next__versionRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_next__version);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(earlier__versionProperty)) {
				return;
			}
			if (stmt.getPredicate().equals(ip__addressProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (ip__address != null) {
					if (ip__address.contains(literal))
						ip__address.remove(literal);
				}
				if (listeners != null) {
					java.util.ArrayList consumersForIp__address;
					synchronized (listeners) {
						consumersForIp__address = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForIp__address.iterator();iter.hasNext();){
						PostListener listener=(PostListener)iter.next();
						listener.ip__addressRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(has__modifierProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.sioc.UserAccount _has__modifier = null;
					if (has__modifier != null) {
						boolean found = false;
						for (int i=0;i<has__modifier.size();i++) {
							de.m0ep.uni.ma.rdfs.sioc.UserAccount __item = (de.m0ep.uni.ma.rdfs.sioc.UserAccount) has__modifier.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_has__modifier = __item;
								break;
							}
						}
						if (found)
							has__modifier.remove(_has__modifier);
						else {
							try {
								_has__modifier = de.m0ep.uni.ma.rdfs.sioc.Factory.getUserAccount(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_has__modifier = de.m0ep.uni.ma.rdfs.sioc.Factory.getUserAccount(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForHas__modifier;
						synchronized (listeners) {
							consumersForHas__modifier = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForHas__modifier.iterator();iter.hasNext();){
							PostListener listener=(PostListener)iter.next();
							listener.has__modifierRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_has__modifier);
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
							PostListener listener=(PostListener)iter.next();
							listener.linkRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_link);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(previous__by__dateProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.sioc.Item _previous__by__date = null;
					if (previous__by__date != null) {
						boolean found = false;
						for (int i=0;i<previous__by__date.size();i++) {
							de.m0ep.uni.ma.rdfs.sioc.Item __item = (de.m0ep.uni.ma.rdfs.sioc.Item) previous__by__date.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_previous__by__date = __item;
								break;
							}
						}
						if (found)
							previous__by__date.remove(_previous__by__date);
						else {
							try {
								_previous__by__date = de.m0ep.uni.ma.rdfs.sioc.Factory.getItem(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_previous__by__date = de.m0ep.uni.ma.rdfs.sioc.Factory.getItem(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForPrevious__by__date;
						synchronized (listeners) {
							consumersForPrevious__by__date = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForPrevious__by__date.iterator();iter.hasNext();){
							PostListener listener=(PostListener)iter.next();
							listener.previous__by__dateRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_previous__by__date);
						}
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
						PostListener listener=(PostListener)iter.next();
						listener.noteRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,literal);
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
							PostListener listener=(PostListener)iter.next();
							listener.has__creatorRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_has__creator);
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
							PostListener listener=(PostListener)iter.next();
							listener.has__ownerRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_has__owner);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(addressed__toProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					com.ibm.adtech.jastor.Thing _addressed__to = null;
					if (addressed__to != null) {
						boolean found = false;
						for (int i=0;i<addressed__to.size();i++) {
							com.ibm.adtech.jastor.Thing __item = (com.ibm.adtech.jastor.Thing) addressed__to.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_addressed__to = __item;
								break;
							}
						}
						if (found)
							addressed__to.remove(_addressed__to);
						else {
							try {
								_addressed__to = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_addressed__to = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForAddressed__to;
						synchronized (listeners) {
							consumersForAddressed__to = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForAddressed__to.iterator();iter.hasNext();){
							PostListener listener=(PostListener)iter.next();
							listener.addressed__toRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_addressed__to);
						}
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
						PostListener listener=(PostListener)iter.next();
						listener.descriptionRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(latest__versionProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.sioc.Item _latest__version = null;
					if (latest__version != null) {
						boolean found = false;
						for (int i=0;i<latest__version.size();i++) {
							de.m0ep.uni.ma.rdfs.sioc.Item __item = (de.m0ep.uni.ma.rdfs.sioc.Item) latest__version.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_latest__version = __item;
								break;
							}
						}
						if (found)
							latest__version.remove(_latest__version);
						else {
							try {
								_latest__version = de.m0ep.uni.ma.rdfs.sioc.Factory.getItem(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_latest__version = de.m0ep.uni.ma.rdfs.sioc.Factory.getItem(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForLatest__version;
						synchronized (listeners) {
							consumersForLatest__version = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForLatest__version.iterator();iter.hasNext();){
							PostListener listener=(PostListener)iter.next();
							listener.latest__versionRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_latest__version);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(previous__versionProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.sioc.Item _previous__version = null;
					if (previous__version != null) {
						boolean found = false;
						for (int i=0;i<previous__version.size();i++) {
							de.m0ep.uni.ma.rdfs.sioc.Item __item = (de.m0ep.uni.ma.rdfs.sioc.Item) previous__version.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_previous__version = __item;
								break;
							}
						}
						if (found)
							previous__version.remove(_previous__version);
						else {
							try {
								_previous__version = de.m0ep.uni.ma.rdfs.sioc.Factory.getItem(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_previous__version = de.m0ep.uni.ma.rdfs.sioc.Factory.getItem(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForPrevious__version;
						synchronized (listeners) {
							consumersForPrevious__version = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForPrevious__version.iterator();iter.hasNext();){
							PostListener listener=(PostListener)iter.next();
							listener.previous__versionRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_previous__version);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(next__by__dateProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.sioc.Item _next__by__date = null;
					if (next__by__date != null) {
						boolean found = false;
						for (int i=0;i<next__by__date.size();i++) {
							de.m0ep.uni.ma.rdfs.sioc.Item __item = (de.m0ep.uni.ma.rdfs.sioc.Item) next__by__date.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_next__by__date = __item;
								break;
							}
						}
						if (found)
							next__by__date.remove(_next__by__date);
						else {
							try {
								_next__by__date = de.m0ep.uni.ma.rdfs.sioc.Factory.getItem(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_next__by__date = de.m0ep.uni.ma.rdfs.sioc.Factory.getItem(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForNext__by__date;
						synchronized (listeners) {
							consumersForNext__by__date = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForNext__by__date.iterator();iter.hasNext();){
							PostListener listener=(PostListener)iter.next();
							listener.next__by__dateRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_next__by__date);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(has__containerProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.sioc.Container _has__container = null;
					if (has__container != null) {
						boolean found = false;
						for (int i=0;i<has__container.size();i++) {
							de.m0ep.uni.ma.rdfs.sioc.Container __item = (de.m0ep.uni.ma.rdfs.sioc.Container) has__container.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_has__container = __item;
								break;
							}
						}
						if (found)
							has__container.remove(_has__container);
						else {
							try {
								_has__container = de.m0ep.uni.ma.rdfs.sioc.Factory.getContainer(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_has__container = de.m0ep.uni.ma.rdfs.sioc.Factory.getContainer(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForHas__container;
						synchronized (listeners) {
							consumersForHas__container = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForHas__container.iterator();iter.hasNext();){
							PostListener listener=(PostListener)iter.next();
							listener.has__containerRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_has__container);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(contentProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (content != null) {
					if (content.contains(literal))
						content.remove(literal);
				}
				if (listeners != null) {
					java.util.ArrayList consumersForContent;
					synchronized (listeners) {
						consumersForContent = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForContent.iterator();iter.hasNext();){
						PostListener listener=(PostListener)iter.next();
						listener.contentRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,literal);
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
							PostListener listener=(PostListener)iter.next();
							listener.subjectRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_subject);
						}
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
						PostListener listener=(PostListener)iter.next();
						listener.num__authorsRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,(java.lang.String)obj);
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
						PostListener listener=(PostListener)iter.next();
						listener.num__viewsRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,(java.lang.String)obj);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(siblingProperty)) {
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
							PostListener listener=(PostListener)iter.next();
							listener.referencesRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_references);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(has__discussionProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					com.ibm.adtech.jastor.Thing _has__discussion = null;
					if (has__discussion != null) {
						boolean found = false;
						for (int i=0;i<has__discussion.size();i++) {
							com.ibm.adtech.jastor.Thing __item = (com.ibm.adtech.jastor.Thing) has__discussion.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_has__discussion = __item;
								break;
							}
						}
						if (found)
							has__discussion.remove(_has__discussion);
						else {
							try {
								_has__discussion = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_has__discussion = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForHas__discussion;
						synchronized (listeners) {
							consumersForHas__discussion = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForHas__discussion.iterator();iter.hasNext();){
							PostListener listener=(PostListener)iter.next();
							listener.has__discussionRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_has__discussion);
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
							PostListener listener=(PostListener)iter.next();
							listener.topicRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_topic);
						}
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
						PostListener listener=(PostListener)iter.next();
						listener.last__activity__dateRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,literal);
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
						PostListener listener=(PostListener)iter.next();
						listener.idRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,literal);
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
							PostListener listener=(PostListener)iter.next();
							listener.links__toRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_links__to);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(has__replyProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.sioc.Item _has__reply = null;
					if (has__reply != null) {
						boolean found = false;
						for (int i=0;i<has__reply.size();i++) {
							de.m0ep.uni.ma.rdfs.sioc.Item __item = (de.m0ep.uni.ma.rdfs.sioc.Item) has__reply.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_has__reply = __item;
								break;
							}
						}
						if (found)
							has__reply.remove(_has__reply);
						else {
							try {
								_has__reply = de.m0ep.uni.ma.rdfs.sioc.Factory.getItem(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_has__reply = de.m0ep.uni.ma.rdfs.sioc.Factory.getItem(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForHas__reply;
						synchronized (listeners) {
							consumersForHas__reply = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForHas__reply.iterator();iter.hasNext();){
							PostListener listener=(PostListener)iter.next();
							listener.has__replyRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_has__reply);
						}
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
						PostListener listener=(PostListener)iter.next();
						listener.last__reply__dateRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,literal);
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
						PostListener listener=(PostListener)iter.next();
						listener.num__repliesRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,(java.lang.String)obj);
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
							PostListener listener=(PostListener)iter.next();
							listener.has__functionRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_has__function);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(attachmentProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					com.ibm.adtech.jastor.Thing _attachment = null;
					if (attachment != null) {
						boolean found = false;
						for (int i=0;i<attachment.size();i++) {
							com.ibm.adtech.jastor.Thing __item = (com.ibm.adtech.jastor.Thing) attachment.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_attachment = __item;
								break;
							}
						}
						if (found)
							attachment.remove(_attachment);
						else {
							try {
								_attachment = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_attachment = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForAttachment;
						synchronized (listeners) {
							consumersForAttachment = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForAttachment.iterator();iter.hasNext();){
							PostListener listener=(PostListener)iter.next();
							listener.attachmentRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_attachment);
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
						PostListener listener=(PostListener)iter.next();
						listener.nameRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,literal);
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
							PostListener listener=(PostListener)iter.next();
							listener.has__spaceRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_has__space);
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
							PostListener listener=(PostListener)iter.next();
							listener.related__toRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_related__to);
						}
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
						PostListener listener=(PostListener)iter.next();
						listener.dateRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(embeds__knowledgeProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					com.ibm.adtech.jastor.Thing _embeds__knowledge = null;
					if (embeds__knowledge != null) {
						boolean found = false;
						for (int i=0;i<embeds__knowledge.size();i++) {
							com.ibm.adtech.jastor.Thing __item = (com.ibm.adtech.jastor.Thing) embeds__knowledge.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_embeds__knowledge = __item;
								break;
							}
						}
						if (found)
							embeds__knowledge.remove(_embeds__knowledge);
						else {
							try {
								_embeds__knowledge = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_embeds__knowledge = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForEmbeds__knowledge;
						synchronized (listeners) {
							consumersForEmbeds__knowledge = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForEmbeds__knowledge.iterator();iter.hasNext();){
							PostListener listener=(PostListener)iter.next();
							listener.embeds__knowledgeRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_embeds__knowledge);
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
						PostListener listener=(PostListener)iter.next();
						listener.titleRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,literal);
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
							PostListener listener=(PostListener)iter.next();
							listener.scope__ofRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_scope__of);
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
							PostListener listener=(PostListener)iter.next();
							listener.feedRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_feed);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(later__versionProperty)) {
				return;
			}
			if (stmt.getPredicate().equals(reply__ofProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					de.m0ep.uni.ma.rdfs.sioc.Item _reply__of = null;
					if (reply__of != null) {
						boolean found = false;
						for (int i=0;i<reply__of.size();i++) {
							de.m0ep.uni.ma.rdfs.sioc.Item __item = (de.m0ep.uni.ma.rdfs.sioc.Item) reply__of.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_reply__of = __item;
								break;
							}
						}
						if (found)
							reply__of.remove(_reply__of);
						else {
							try {
								_reply__of = de.m0ep.uni.ma.rdfs.sioc.Factory.getItem(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_reply__of = de.m0ep.uni.ma.rdfs.sioc.Factory.getItem(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForReply__of;
						synchronized (listeners) {
							consumersForReply__of = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForReply__of.iterator();iter.hasNext();){
							PostListener listener=(PostListener)iter.next();
							listener.reply__ofRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_reply__of);
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
							PostListener listener=(PostListener)iter.next();
							listener.depictionRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_depiction);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(aboutProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					com.ibm.adtech.jastor.Thing _about = null;
					if (about != null) {
						boolean found = false;
						for (int i=0;i<about.size();i++) {
							com.ibm.adtech.jastor.Thing __item = (com.ibm.adtech.jastor.Thing) about.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_about = __item;
								break;
							}
						}
						if (found)
							about.remove(_about);
						else {
							try {
								_about = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_about = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForAbout;
						synchronized (listeners) {
							consumersForAbout = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForAbout.iterator();iter.hasNext();){
							PostListener listener=(PostListener)iter.next();
							listener.aboutRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_about);
						}
					}
				}
				return;
			}
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
							PostListener listener=(PostListener)iter.next();
							listener.fundedByRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_fundedBy);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(ns3_nameProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (ns3_name != null) {
					if (ns3_name.contains(literal))
						ns3_name.remove(literal);
				}
				if (listeners != null) {
					java.util.ArrayList consumersForNs3_Name;
					synchronized (listeners) {
						consumersForNs3_Name = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForNs3_Name.iterator();iter.hasNext();){
						PostListener listener=(PostListener)iter.next();
						listener.ns3_nameRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,literal);
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
						PostListener listener=(PostListener)iter.next();
						listener.primaryTopicChanged(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this);
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
						PostListener listener=(PostListener)iter.next();
						listener.dnaChecksumRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,literal);
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
							PostListener listener=(PostListener)iter.next();
							listener.phoneRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_phone);
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
							PostListener listener=(PostListener)iter.next();
							listener.pageRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_page);
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
						PostListener listener=(PostListener)iter.next();
						listener.nickRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,literal);
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
							PostListener listener=(PostListener)iter.next();
							listener.logoRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_logo);
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
							PostListener listener=(PostListener)iter.next();
							listener.makerRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_maker);
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
						PostListener listener=(PostListener)iter.next();
						listener.givennameRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,literal);
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(ns3_topicProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Resource.class))
					return;
				com.hp.hpl.jena.rdf.model.Resource resource = (com.hp.hpl.jena.rdf.model.Resource) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Resource.class);
				if (true) { // don't check resource type if the property range is Resource
					com.ibm.adtech.jastor.Thing _ns3_topic = null;
					if (ns3_topic != null) {
						boolean found = false;
						for (int i=0;i<ns3_topic.size();i++) {
							com.ibm.adtech.jastor.Thing __item = (com.ibm.adtech.jastor.Thing) ns3_topic.get(i);
							if (__item.resource().equals(resource)) {
								found = true;
								_ns3_topic = __item;
								break;
							}
						}
						if (found)
							ns3_topic.remove(_ns3_topic);
						else {
							try {
								_ns3_topic = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
							} catch (JastorException e) {
							}
						}
					} else {
						try {
							_ns3_topic = com.ibm.adtech.jastor.ThingFactory.getThing(resource,_model);
						} catch (JastorException e) {
						}
					}
					if (listeners != null) {
						java.util.ArrayList consumersForNs3_Topic;
						synchronized (listeners) {
							consumersForNs3_Topic = (java.util.ArrayList) listeners.clone();
						}
						for(java.util.Iterator iter=consumersForNs3_Topic.iterator();iter.hasNext();){
							PostListener listener=(PostListener)iter.next();
							listener.ns3_topicRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_ns3_topic);
						}
					}
				}
				return;
			}
			if (stmt.getPredicate().equals(ns3_titleProperty)) {
				if (!stmt.getObject().canAs(com.hp.hpl.jena.rdf.model.Literal.class))
					return;
				com.hp.hpl.jena.rdf.model.Literal literal = (com.hp.hpl.jena.rdf.model.Literal) stmt.getObject().as(com.hp.hpl.jena.rdf.model.Literal.class);
				//Object obj = literal.getValue();
				if (ns3_title != null) {
					if (ns3_title.contains(literal))
						ns3_title.remove(literal);
				}
				if (listeners != null) {
					java.util.ArrayList consumersForNs3_Title;
					synchronized (listeners) {
						consumersForNs3_Title = (java.util.ArrayList) listeners.clone();
					}
					for(java.util.Iterator iter=consumersForNs3_Title.iterator();iter.hasNext();){
						PostListener listener=(PostListener)iter.next();
						listener.ns3_titleRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,literal);
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
						PostListener listener=(PostListener)iter.next();
						listener.givenNameRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,literal);
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
							PostListener listener=(PostListener)iter.next();
							listener.homepageRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_homepage);
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
						PostListener listener=(PostListener)iter.next();
						listener.sha1Removed(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,literal);
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
							PostListener listener=(PostListener)iter.next();
							listener.themeRemoved(de.m0ep.uni.ma.rdfs.sioc.PostImpl.this,_theme);
						}
					}
				}
				return;
			}
		}

	//}
	


}