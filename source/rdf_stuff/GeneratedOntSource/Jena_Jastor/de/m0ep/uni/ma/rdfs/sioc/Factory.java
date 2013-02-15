

package de.m0ep.uni.ma.rdfs.sioc;

import com.ibm.adtech.jastor.*;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.RDF;

/**
 * Factory for instantiating objects for ontology classes in the  ontology.  The
 * get methods leave the model unchanged and return a Java view of the object in the model.  The create methods
 * may add certain baseline properties to the model such as rdf:type and any properties with hasValue restrictions.
 * <p>(URI: http://rdfs.org/sioc/ns#)</p>
 * <br>
 * RDF Schema Standard Properties <br>
 * 	seeAlso : http://rdfs.org/sioc/spec <br>
 * <br>
 * <br>
 *	@version Revision: 1.35
 */
public class Factory extends com.ibm.adtech.jastor.ThingFactory { 



	/**
	 * Create a new instance of Thread.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the Thread
	 * @param model the Jena Model.
	 */
	public static Thread createThread(Resource resource, Model model) throws JastorException {
		return de.m0ep.uni.ma.rdfs.sioc.ThreadImpl.createThread(resource,model);
	}
	
	/**
	 * Create a new instance of Thread.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the Thread
	 * @param model the Jena Model.
	 */
	public static Thread createThread(String uri, Model model) throws JastorException {
		Thread obj = de.m0ep.uni.ma.rdfs.sioc.ThreadImpl.createThread(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of Thread.  Leaves the model unchanged.
	 * @param uri The uri of the Thread
	 * @param model the Jena Model.
	 */
	public static Thread getThread(String uri, Model model) throws JastorException {
		return getThread(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of Thread.  Leaves the model unchanged.
	 * @param resource The resource of the Thread
	 * @param model the Jena Model.
	 */
	public static Thread getThread(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + de.m0ep.uni.ma.rdfs.sioc.Thread.class.hashCode()) + resource.toString();
		de.m0ep.uni.ma.rdfs.sioc.ThreadImpl obj = (de.m0ep.uni.ma.rdfs.sioc.ThreadImpl)objects.get(code);
		if (obj == null) {
			obj = de.m0ep.uni.ma.rdfs.sioc.ThreadImpl.getThread(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of Thread for every resource in the model with rdf:Type http://rdfs.org/sioc/ns#Thread
	 * @param model the Jena Model
	 * @return a List of Thread
	 */
	public static java.util.List getAllThread(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,Thread.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getThread(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of UserAccount.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the UserAccount
	 * @param model the Jena Model.
	 */
	public static UserAccount createUserAccount(Resource resource, Model model) throws JastorException {
		return de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.createUserAccount(resource,model);
	}
	
	/**
	 * Create a new instance of UserAccount.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the UserAccount
	 * @param model the Jena Model.
	 */
	public static UserAccount createUserAccount(String uri, Model model) throws JastorException {
		UserAccount obj = de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.createUserAccount(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of UserAccount.  Leaves the model unchanged.
	 * @param uri The uri of the UserAccount
	 * @param model the Jena Model.
	 */
	public static UserAccount getUserAccount(String uri, Model model) throws JastorException {
		return getUserAccount(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of UserAccount.  Leaves the model unchanged.
	 * @param resource The resource of the UserAccount
	 * @param model the Jena Model.
	 */
	public static UserAccount getUserAccount(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + de.m0ep.uni.ma.rdfs.sioc.UserAccount.class.hashCode()) + resource.toString();
		de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl obj = (de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl)objects.get(code);
		if (obj == null) {
			obj = de.m0ep.uni.ma.rdfs.sioc.UserAccountImpl.getUserAccount(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of UserAccount for every resource in the model with rdf:Type http://rdfs.org/sioc/ns#UserAccount
	 * @param model the Jena Model
	 * @return a List of UserAccount
	 */
	public static java.util.List getAllUserAccount(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,UserAccount.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getUserAccount(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of Usergroup.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the Usergroup
	 * @param model the Jena Model.
	 */
	public static Usergroup createUsergroup(Resource resource, Model model) throws JastorException {
		return de.m0ep.uni.ma.rdfs.sioc.UsergroupImpl.createUsergroup(resource,model);
	}
	
	/**
	 * Create a new instance of Usergroup.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the Usergroup
	 * @param model the Jena Model.
	 */
	public static Usergroup createUsergroup(String uri, Model model) throws JastorException {
		Usergroup obj = de.m0ep.uni.ma.rdfs.sioc.UsergroupImpl.createUsergroup(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of Usergroup.  Leaves the model unchanged.
	 * @param uri The uri of the Usergroup
	 * @param model the Jena Model.
	 */
	public static Usergroup getUsergroup(String uri, Model model) throws JastorException {
		return getUsergroup(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of Usergroup.  Leaves the model unchanged.
	 * @param resource The resource of the Usergroup
	 * @param model the Jena Model.
	 */
	public static Usergroup getUsergroup(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + de.m0ep.uni.ma.rdfs.sioc.Usergroup.class.hashCode()) + resource.toString();
		de.m0ep.uni.ma.rdfs.sioc.UsergroupImpl obj = (de.m0ep.uni.ma.rdfs.sioc.UsergroupImpl)objects.get(code);
		if (obj == null) {
			obj = de.m0ep.uni.ma.rdfs.sioc.UsergroupImpl.getUsergroup(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of Usergroup for every resource in the model with rdf:Type http://rdfs.org/sioc/ns#Usergroup
	 * @param model the Jena Model
	 * @return a List of Usergroup
	 */
	public static java.util.List getAllUsergroup(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,Usergroup.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getUsergroup(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of Agent.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the Agent
	 * @param model the Jena Model.
	 */
	public static Agent createAgent(Resource resource, Model model) throws JastorException {
		return de.m0ep.uni.ma.rdfs.foaf.AgentImpl.createAgent(resource,model);
	}
	
	/**
	 * Create a new instance of Agent.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the Agent
	 * @param model the Jena Model.
	 */
	public static Agent createAgent(String uri, Model model) throws JastorException {
		Agent obj = de.m0ep.uni.ma.rdfs.foaf.AgentImpl.createAgent(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of Agent.  Leaves the model unchanged.
	 * @param uri The uri of the Agent
	 * @param model the Jena Model.
	 */
	public static Agent getAgent(String uri, Model model) throws JastorException {
		return getAgent(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of Agent.  Leaves the model unchanged.
	 * @param resource The resource of the Agent
	 * @param model the Jena Model.
	 */
	public static Agent getAgent(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + de.m0ep.uni.ma.rdfs.foaf.Agent.class.hashCode()) + resource.toString();
		de.m0ep.uni.ma.rdfs.foaf.AgentImpl obj = (de.m0ep.uni.ma.rdfs.foaf.AgentImpl)objects.get(code);
		if (obj == null) {
			obj = de.m0ep.uni.ma.rdfs.foaf.AgentImpl.getAgent(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of Agent for every resource in the model with rdf:Type http://xmlns.com/foaf/0.1/Agent
	 * @param model the Jena Model
	 * @return a List of Agent
	 */
	public static java.util.List getAllAgent(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,Agent.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getAgent(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of Post.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the Post
	 * @param model the Jena Model.
	 */
	public static Post createPost(Resource resource, Model model) throws JastorException {
		return de.m0ep.uni.ma.rdfs.sioc.PostImpl.createPost(resource,model);
	}
	
	/**
	 * Create a new instance of Post.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the Post
	 * @param model the Jena Model.
	 */
	public static Post createPost(String uri, Model model) throws JastorException {
		Post obj = de.m0ep.uni.ma.rdfs.sioc.PostImpl.createPost(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of Post.  Leaves the model unchanged.
	 * @param uri The uri of the Post
	 * @param model the Jena Model.
	 */
	public static Post getPost(String uri, Model model) throws JastorException {
		return getPost(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of Post.  Leaves the model unchanged.
	 * @param resource The resource of the Post
	 * @param model the Jena Model.
	 */
	public static Post getPost(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + de.m0ep.uni.ma.rdfs.sioc.Post.class.hashCode()) + resource.toString();
		de.m0ep.uni.ma.rdfs.sioc.PostImpl obj = (de.m0ep.uni.ma.rdfs.sioc.PostImpl)objects.get(code);
		if (obj == null) {
			obj = de.m0ep.uni.ma.rdfs.sioc.PostImpl.getPost(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of Post for every resource in the model with rdf:Type http://rdfs.org/sioc/ns#Post
	 * @param model the Jena Model
	 * @return a List of Post
	 */
	public static java.util.List getAllPost(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,Post.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getPost(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of Item.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the Item
	 * @param model the Jena Model.
	 */
	public static Item createItem(Resource resource, Model model) throws JastorException {
		return de.m0ep.uni.ma.rdfs.sioc.ItemImpl.createItem(resource,model);
	}
	
	/**
	 * Create a new instance of Item.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the Item
	 * @param model the Jena Model.
	 */
	public static Item createItem(String uri, Model model) throws JastorException {
		Item obj = de.m0ep.uni.ma.rdfs.sioc.ItemImpl.createItem(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of Item.  Leaves the model unchanged.
	 * @param uri The uri of the Item
	 * @param model the Jena Model.
	 */
	public static Item getItem(String uri, Model model) throws JastorException {
		return getItem(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of Item.  Leaves the model unchanged.
	 * @param resource The resource of the Item
	 * @param model the Jena Model.
	 */
	public static Item getItem(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + de.m0ep.uni.ma.rdfs.sioc.Item.class.hashCode()) + resource.toString();
		de.m0ep.uni.ma.rdfs.sioc.ItemImpl obj = (de.m0ep.uni.ma.rdfs.sioc.ItemImpl)objects.get(code);
		if (obj == null) {
			obj = de.m0ep.uni.ma.rdfs.sioc.ItemImpl.getItem(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of Item for every resource in the model with rdf:Type http://rdfs.org/sioc/ns#Item
	 * @param model the Jena Model
	 * @return a List of Item
	 */
	public static java.util.List getAllItem(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,Item.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getItem(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of Space.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the Space
	 * @param model the Jena Model.
	 */
	public static Space createSpace(Resource resource, Model model) throws JastorException {
		return de.m0ep.uni.ma.rdfs.sioc.SpaceImpl.createSpace(resource,model);
	}
	
	/**
	 * Create a new instance of Space.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the Space
	 * @param model the Jena Model.
	 */
	public static Space createSpace(String uri, Model model) throws JastorException {
		Space obj = de.m0ep.uni.ma.rdfs.sioc.SpaceImpl.createSpace(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of Space.  Leaves the model unchanged.
	 * @param uri The uri of the Space
	 * @param model the Jena Model.
	 */
	public static Space getSpace(String uri, Model model) throws JastorException {
		return getSpace(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of Space.  Leaves the model unchanged.
	 * @param resource The resource of the Space
	 * @param model the Jena Model.
	 */
	public static Space getSpace(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + de.m0ep.uni.ma.rdfs.sioc.Space.class.hashCode()) + resource.toString();
		de.m0ep.uni.ma.rdfs.sioc.SpaceImpl obj = (de.m0ep.uni.ma.rdfs.sioc.SpaceImpl)objects.get(code);
		if (obj == null) {
			obj = de.m0ep.uni.ma.rdfs.sioc.SpaceImpl.getSpace(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of Space for every resource in the model with rdf:Type http://rdfs.org/sioc/ns#Space
	 * @param model the Jena Model
	 * @return a List of Space
	 */
	public static java.util.List getAllSpace(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,Space.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getSpace(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of Community.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the Community
	 * @param model the Jena Model.
	 */
	public static Community createCommunity(Resource resource, Model model) throws JastorException {
		return de.m0ep.uni.ma.rdfs.sioc.CommunityImpl.createCommunity(resource,model);
	}
	
	/**
	 * Create a new instance of Community.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the Community
	 * @param model the Jena Model.
	 */
	public static Community createCommunity(String uri, Model model) throws JastorException {
		Community obj = de.m0ep.uni.ma.rdfs.sioc.CommunityImpl.createCommunity(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of Community.  Leaves the model unchanged.
	 * @param uri The uri of the Community
	 * @param model the Jena Model.
	 */
	public static Community getCommunity(String uri, Model model) throws JastorException {
		return getCommunity(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of Community.  Leaves the model unchanged.
	 * @param resource The resource of the Community
	 * @param model the Jena Model.
	 */
	public static Community getCommunity(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + de.m0ep.uni.ma.rdfs.sioc.Community.class.hashCode()) + resource.toString();
		de.m0ep.uni.ma.rdfs.sioc.CommunityImpl obj = (de.m0ep.uni.ma.rdfs.sioc.CommunityImpl)objects.get(code);
		if (obj == null) {
			obj = de.m0ep.uni.ma.rdfs.sioc.CommunityImpl.getCommunity(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of Community for every resource in the model with rdf:Type http://rdfs.org/sioc/ns#Community
	 * @param model the Jena Model
	 * @return a List of Community
	 */
	public static java.util.List getAllCommunity(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,Community.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getCommunity(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of Document.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the Document
	 * @param model the Jena Model.
	 */
	public static Document createDocument(Resource resource, Model model) throws JastorException {
		return de.m0ep.uni.ma.rdfs.foaf.DocumentImpl.createDocument(resource,model);
	}
	
	/**
	 * Create a new instance of Document.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the Document
	 * @param model the Jena Model.
	 */
	public static Document createDocument(String uri, Model model) throws JastorException {
		Document obj = de.m0ep.uni.ma.rdfs.foaf.DocumentImpl.createDocument(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of Document.  Leaves the model unchanged.
	 * @param uri The uri of the Document
	 * @param model the Jena Model.
	 */
	public static Document getDocument(String uri, Model model) throws JastorException {
		return getDocument(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of Document.  Leaves the model unchanged.
	 * @param resource The resource of the Document
	 * @param model the Jena Model.
	 */
	public static Document getDocument(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + de.m0ep.uni.ma.rdfs.foaf.Document.class.hashCode()) + resource.toString();
		de.m0ep.uni.ma.rdfs.foaf.DocumentImpl obj = (de.m0ep.uni.ma.rdfs.foaf.DocumentImpl)objects.get(code);
		if (obj == null) {
			obj = de.m0ep.uni.ma.rdfs.foaf.DocumentImpl.getDocument(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of Document for every resource in the model with rdf:Type http://xmlns.com/foaf/0.1/Document
	 * @param model the Jena Model
	 * @return a List of Document
	 */
	public static java.util.List getAllDocument(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,Document.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getDocument(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of Site.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the Site
	 * @param model the Jena Model.
	 */
	public static Site createSite(Resource resource, Model model) throws JastorException {
		return de.m0ep.uni.ma.rdfs.sioc.SiteImpl.createSite(resource,model);
	}
	
	/**
	 * Create a new instance of Site.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the Site
	 * @param model the Jena Model.
	 */
	public static Site createSite(String uri, Model model) throws JastorException {
		Site obj = de.m0ep.uni.ma.rdfs.sioc.SiteImpl.createSite(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of Site.  Leaves the model unchanged.
	 * @param uri The uri of the Site
	 * @param model the Jena Model.
	 */
	public static Site getSite(String uri, Model model) throws JastorException {
		return getSite(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of Site.  Leaves the model unchanged.
	 * @param resource The resource of the Site
	 * @param model the Jena Model.
	 */
	public static Site getSite(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + de.m0ep.uni.ma.rdfs.sioc.Site.class.hashCode()) + resource.toString();
		de.m0ep.uni.ma.rdfs.sioc.SiteImpl obj = (de.m0ep.uni.ma.rdfs.sioc.SiteImpl)objects.get(code);
		if (obj == null) {
			obj = de.m0ep.uni.ma.rdfs.sioc.SiteImpl.getSite(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of Site for every resource in the model with rdf:Type http://rdfs.org/sioc/ns#Site
	 * @param model the Jena Model
	 * @return a List of Site
	 */
	public static java.util.List getAllSite(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,Site.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getSite(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of OnlineAccount.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the OnlineAccount
	 * @param model the Jena Model.
	 */
	public static OnlineAccount createOnlineAccount(Resource resource, Model model) throws JastorException {
		return de.m0ep.uni.ma.rdfs.foaf.OnlineAccountImpl.createOnlineAccount(resource,model);
	}
	
	/**
	 * Create a new instance of OnlineAccount.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the OnlineAccount
	 * @param model the Jena Model.
	 */
	public static OnlineAccount createOnlineAccount(String uri, Model model) throws JastorException {
		OnlineAccount obj = de.m0ep.uni.ma.rdfs.foaf.OnlineAccountImpl.createOnlineAccount(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of OnlineAccount.  Leaves the model unchanged.
	 * @param uri The uri of the OnlineAccount
	 * @param model the Jena Model.
	 */
	public static OnlineAccount getOnlineAccount(String uri, Model model) throws JastorException {
		return getOnlineAccount(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of OnlineAccount.  Leaves the model unchanged.
	 * @param resource The resource of the OnlineAccount
	 * @param model the Jena Model.
	 */
	public static OnlineAccount getOnlineAccount(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + de.m0ep.uni.ma.rdfs.foaf.OnlineAccount.class.hashCode()) + resource.toString();
		de.m0ep.uni.ma.rdfs.foaf.OnlineAccountImpl obj = (de.m0ep.uni.ma.rdfs.foaf.OnlineAccountImpl)objects.get(code);
		if (obj == null) {
			obj = de.m0ep.uni.ma.rdfs.foaf.OnlineAccountImpl.getOnlineAccount(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of OnlineAccount for every resource in the model with rdf:Type http://xmlns.com/foaf/0.1/OnlineAccount
	 * @param model the Jena Model
	 * @return a List of OnlineAccount
	 */
	public static java.util.List getAllOnlineAccount(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,OnlineAccount.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getOnlineAccount(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of Container.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the Container
	 * @param model the Jena Model.
	 */
	public static Container createContainer(Resource resource, Model model) throws JastorException {
		return de.m0ep.uni.ma.rdfs.sioc.ContainerImpl.createContainer(resource,model);
	}
	
	/**
	 * Create a new instance of Container.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the Container
	 * @param model the Jena Model.
	 */
	public static Container createContainer(String uri, Model model) throws JastorException {
		Container obj = de.m0ep.uni.ma.rdfs.sioc.ContainerImpl.createContainer(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of Container.  Leaves the model unchanged.
	 * @param uri The uri of the Container
	 * @param model the Jena Model.
	 */
	public static Container getContainer(String uri, Model model) throws JastorException {
		return getContainer(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of Container.  Leaves the model unchanged.
	 * @param resource The resource of the Container
	 * @param model the Jena Model.
	 */
	public static Container getContainer(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + de.m0ep.uni.ma.rdfs.sioc.Container.class.hashCode()) + resource.toString();
		de.m0ep.uni.ma.rdfs.sioc.ContainerImpl obj = (de.m0ep.uni.ma.rdfs.sioc.ContainerImpl)objects.get(code);
		if (obj == null) {
			obj = de.m0ep.uni.ma.rdfs.sioc.ContainerImpl.getContainer(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of Container for every resource in the model with rdf:Type http://rdfs.org/sioc/ns#Container
	 * @param model the Jena Model
	 * @return a List of Container
	 */
	public static java.util.List getAllContainer(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,Container.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getContainer(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of Role.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the Role
	 * @param model the Jena Model.
	 */
	public static Role createRole(Resource resource, Model model) throws JastorException {
		return de.m0ep.uni.ma.rdfs.sioc.RoleImpl.createRole(resource,model);
	}
	
	/**
	 * Create a new instance of Role.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the Role
	 * @param model the Jena Model.
	 */
	public static Role createRole(String uri, Model model) throws JastorException {
		Role obj = de.m0ep.uni.ma.rdfs.sioc.RoleImpl.createRole(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of Role.  Leaves the model unchanged.
	 * @param uri The uri of the Role
	 * @param model the Jena Model.
	 */
	public static Role getRole(String uri, Model model) throws JastorException {
		return getRole(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of Role.  Leaves the model unchanged.
	 * @param resource The resource of the Role
	 * @param model the Jena Model.
	 */
	public static Role getRole(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + de.m0ep.uni.ma.rdfs.sioc.Role.class.hashCode()) + resource.toString();
		de.m0ep.uni.ma.rdfs.sioc.RoleImpl obj = (de.m0ep.uni.ma.rdfs.sioc.RoleImpl)objects.get(code);
		if (obj == null) {
			obj = de.m0ep.uni.ma.rdfs.sioc.RoleImpl.getRole(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of Role for every resource in the model with rdf:Type http://rdfs.org/sioc/ns#Role
	 * @param model the Jena Model
	 * @return a List of Role
	 */
	public static java.util.List getAllRole(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,Role.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getRole(stmt.getSubject(),model));
		}
		return list;
	}
	

	/**
	 * Create a new instance of Forum.  Adds the rdf:type property for the given resource to the model.
	 * @param resource The resource of the Forum
	 * @param model the Jena Model.
	 */
	public static Forum createForum(Resource resource, Model model) throws JastorException {
		return de.m0ep.uni.ma.rdfs.sioc.ForumImpl.createForum(resource,model);
	}
	
	/**
	 * Create a new instance of Forum.  Adds the rdf:type property for the given resource to the model.
	 * @param uri The uri of the Forum
	 * @param model the Jena Model.
	 */
	public static Forum createForum(String uri, Model model) throws JastorException {
		Forum obj = de.m0ep.uni.ma.rdfs.sioc.ForumImpl.createForum(model.createResource(uri), model);
		return obj;
	}
	
	/**
	 * Create a new instance of Forum.  Leaves the model unchanged.
	 * @param uri The uri of the Forum
	 * @param model the Jena Model.
	 */
	public static Forum getForum(String uri, Model model) throws JastorException {
		return getForum(model.createResource(uri),model);
	}
	
	/**
	 * Create a new instance of Forum.  Leaves the model unchanged.
	 * @param resource The resource of the Forum
	 * @param model the Jena Model.
	 */
	public static Forum getForum(Resource resource, Model model) throws JastorException {
		String code = (model.hashCode()*17 + de.m0ep.uni.ma.rdfs.sioc.Forum.class.hashCode()) + resource.toString();
		de.m0ep.uni.ma.rdfs.sioc.ForumImpl obj = (de.m0ep.uni.ma.rdfs.sioc.ForumImpl)objects.get(code);
		if (obj == null) {
			obj = de.m0ep.uni.ma.rdfs.sioc.ForumImpl.getForum(resource, model);
			if (obj == null)
				return null;
			objects.put(code, obj);
		}
		return obj;
	}
	
	/**
	 * Return an instance of Forum for every resource in the model with rdf:Type http://rdfs.org/sioc/ns#Forum
	 * @param model the Jena Model
	 * @return a List of Forum
	 */
	public static java.util.List getAllForum(Model model) throws JastorException {
		StmtIterator it = model.listStatements(null,RDF.type,Forum.TYPE);
		java.util.List list = new java.util.ArrayList();
		while (it.hasNext()) {
			Statement stmt = it.nextStatement();
			list.add(getForum(stmt.getSubject(),model));
		}
		return list;
	}
	
	
	/**
	 * Returns an instance of an interface for the given Resource.  The return instance is guaranteed to 
	 * implement the most specific interface in *some* hierarchy in which the Resource participates.  The behavior
	 * is unspecified for resources with RDF types from different hierarchies.
	 * @return an instance of Thing
	 */
	public static Thing getThing(com.hp.hpl.jena.rdf.model.Resource res, com.hp.hpl.jena.rdf.model.Model model) throws JastorException {
		if (res.hasProperty(RDF.type,model.getResource("http://rdfs.org/sioc/ns#Forum"))) {
			return getForum(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://rdfs.org/sioc/ns#Role"))) {
			return getRole(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://rdfs.org/sioc/ns#Site"))) {
			return getSite(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://rdfs.org/sioc/ns#Space"))) {
			return getSpace(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://rdfs.org/sioc/ns#Community"))) {
			return getCommunity(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://rdfs.org/sioc/ns#Post"))) {
			return getPost(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://xmlns.com/foaf/0.1/Document"))) {
			return getDocument(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://rdfs.org/sioc/ns#Item"))) {
			return getItem(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://xmlns.com/foaf/0.1/Agent"))) {
			return getAgent(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://rdfs.org/sioc/ns#Usergroup"))) {
			return getUsergroup(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://rdfs.org/sioc/ns#UserAccount"))) {
			return getUserAccount(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://xmlns.com/foaf/0.1/OnlineAccount"))) {
			return getOnlineAccount(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://rdfs.org/sioc/ns#Thread"))) {
			return getThread(res,model);
		}
		if (res.hasProperty(RDF.type,model.getResource("http://rdfs.org/sioc/ns#Container"))) {
			return getContainer(res,model);
		}
		return new ThingImpl(res,model);
	}
	
	/**
	 * Returns an instance of an interface for the given Resource URI.  The return instance is guaranteed to 
	 * implement the most specific interface in *some* hierarchy in which the Resource participates.  The behavior
	 * is unspecified for resources with RDF types from different hierarchies.
	 * @return an instance of Thing
	 */
	public static Thing getThing(String uri, com.hp.hpl.jena.rdf.model.Model model) throws JastorException {
		return getThing(model.getResource(uri),model);
	}

	/**
	 * Return a list of compatible interfaces for the given type.  Searches through all ontology classes
	 * in the  ontology.  The list is sorted according to the topological sort
	 * of the class hierarchy
	 * @return a List of type java.lang.Class
	 */
	public static java.util.List listCompatibleInterfaces (com.hp.hpl.jena.rdf.model.Resource type) {
		java.util.List types = new java.util.ArrayList();
		if (type.equals(de.m0ep.uni.ma.rdfs.sioc.Forum.TYPE)) {
			types.add(de.m0ep.uni.ma.rdfs.sioc.Forum.class);
		}
		if (type.equals(de.m0ep.uni.ma.rdfs.sioc.Role.TYPE)) {
			types.add(de.m0ep.uni.ma.rdfs.sioc.Role.class);
		}
		if (type.equals(de.m0ep.uni.ma.rdfs.sioc.Site.TYPE)) {
			types.add(de.m0ep.uni.ma.rdfs.sioc.Site.class);
		}
		if (type.equals(de.m0ep.uni.ma.rdfs.sioc.Space.TYPE)) {
			types.add(de.m0ep.uni.ma.rdfs.sioc.Space.class);
		}
		if (type.equals(de.m0ep.uni.ma.rdfs.sioc.Community.TYPE)) {
			types.add(de.m0ep.uni.ma.rdfs.sioc.Community.class);
		}
		if (type.equals(de.m0ep.uni.ma.rdfs.sioc.Post.TYPE)) {
			types.add(de.m0ep.uni.ma.rdfs.sioc.Post.class);
		}
		if (type.equals(de.m0ep.uni.ma.rdfs.foaf.Document.TYPE)) {
			types.add(de.m0ep.uni.ma.rdfs.foaf.Document.class);
		}
		if (type.equals(de.m0ep.uni.ma.rdfs.sioc.Item.TYPE)) {
			types.add(de.m0ep.uni.ma.rdfs.sioc.Item.class);
		}
		if (type.equals(de.m0ep.uni.ma.rdfs.foaf.Agent.TYPE)) {
			types.add(de.m0ep.uni.ma.rdfs.foaf.Agent.class);
		}
		if (type.equals(de.m0ep.uni.ma.rdfs.sioc.Usergroup.TYPE)) {
			types.add(de.m0ep.uni.ma.rdfs.sioc.Usergroup.class);
		}
		if (type.equals(de.m0ep.uni.ma.rdfs.sioc.UserAccount.TYPE)) {
			types.add(de.m0ep.uni.ma.rdfs.sioc.UserAccount.class);
		}
		if (type.equals(de.m0ep.uni.ma.rdfs.foaf.OnlineAccount.TYPE)) {
			types.add(de.m0ep.uni.ma.rdfs.foaf.OnlineAccount.class);
		}
		if (type.equals(de.m0ep.uni.ma.rdfs.sioc.Thread.TYPE)) {
			types.add(de.m0ep.uni.ma.rdfs.sioc.Thread.class);
		}
		if (type.equals(de.m0ep.uni.ma.rdfs.sioc.Container.TYPE)) {
			types.add(de.m0ep.uni.ma.rdfs.sioc.Container.class);
		}
		return types;
	}
}