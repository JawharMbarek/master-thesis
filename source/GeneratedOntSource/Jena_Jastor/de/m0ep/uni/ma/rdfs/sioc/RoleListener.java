

package de.m0ep.uni.ma.rdfs.sioc;

/*
import com.hp.hpl.jena.datatypes.xsd.*;
import com.hp.hpl.jena.datatypes.xsd.impl.*;
import com.hp.hpl.jena.rdf.model.*;
import com.ibm.adtech.jastor.*;
import java.util.*;
import java.math.*;
*/


/**
 * Implementations of this listener may be registered with instances of de.m0ep.uni.ma.rdfs.sioc.Role to 
 * receive notification when properties changed, added or removed.
 * <br>
 */
public interface RoleListener extends com.ibm.adtech.jastor.ThingListener {


	/**
	 * Called when a value of link has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.Role
	 * @param newValue the object representing the new value
	 */	
	public void linkAdded(de.m0ep.uni.ma.rdfs.sioc.Role source, com.ibm.adtech.jastor.Thing newValue);

	/**
	 * Called when a value of link has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.Role
	 * @param oldValue the object representing the removed value
	 */
	public void linkRemoved(de.m0ep.uni.ma.rdfs.sioc.Role source, com.ibm.adtech.jastor.Thing oldValue);
		
	/**
	 * Called when a value of note has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.Role
	 * @param newValue the object representing the new value
	 */	
	public void noteAdded(de.m0ep.uni.ma.rdfs.sioc.Role source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of note has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.Role
	 * @param oldValue the object representing the removed value
	 */	
	public void noteRemoved(de.m0ep.uni.ma.rdfs.sioc.Role source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of has__creator has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.Role
	 * @param newValue the object representing the new value
	 */	
	public void has__creatorAdded(de.m0ep.uni.ma.rdfs.sioc.Role source, de.m0ep.uni.ma.rdfs.sioc.UserAccount newValue);

	/**
	 * Called when a value of has__creator has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.Role
	 * @param oldValue the object representing the removed value
	 */
	public void has__creatorRemoved(de.m0ep.uni.ma.rdfs.sioc.Role source, de.m0ep.uni.ma.rdfs.sioc.UserAccount oldValue);
		
	/**
	 * Called when a value of has__owner has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.Role
	 * @param newValue the object representing the new value
	 */	
	public void has__ownerAdded(de.m0ep.uni.ma.rdfs.sioc.Role source, de.m0ep.uni.ma.rdfs.sioc.UserAccount newValue);

	/**
	 * Called when a value of has__owner has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.Role
	 * @param oldValue the object representing the removed value
	 */
	public void has__ownerRemoved(de.m0ep.uni.ma.rdfs.sioc.Role source, de.m0ep.uni.ma.rdfs.sioc.UserAccount oldValue);
		
	/**
	 * Called when a value of description has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.Role
	 * @param newValue the object representing the new value
	 */	
	public void descriptionAdded(de.m0ep.uni.ma.rdfs.sioc.Role source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of description has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.Role
	 * @param oldValue the object representing the removed value
	 */	
	public void descriptionRemoved(de.m0ep.uni.ma.rdfs.sioc.Role source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of subject has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.Role
	 * @param newValue the object representing the new value
	 */	
	public void subjectAdded(de.m0ep.uni.ma.rdfs.sioc.Role source, com.ibm.adtech.jastor.Thing newValue);

	/**
	 * Called when a value of subject has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.Role
	 * @param oldValue the object representing the removed value
	 */
	public void subjectRemoved(de.m0ep.uni.ma.rdfs.sioc.Role source, com.ibm.adtech.jastor.Thing oldValue);
		
	/**
	 * Called when a value of num__authors has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.Role
	 * @param newValue the object representing the new value
	 */	
	public void num__authorsAdded(de.m0ep.uni.ma.rdfs.sioc.Role source, java.lang.String newValue);

	/**
	 * Called when a value of num__authors has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.Role
	 * @param oldValue the object representing the removed value
	 */	
	public void num__authorsRemoved(de.m0ep.uni.ma.rdfs.sioc.Role source, java.lang.String oldValue);

	/**
	 * Called when a value of num__views has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.Role
	 * @param newValue the object representing the new value
	 */	
	public void num__viewsAdded(de.m0ep.uni.ma.rdfs.sioc.Role source, java.lang.String newValue);

	/**
	 * Called when a value of num__views has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.Role
	 * @param oldValue the object representing the removed value
	 */	
	public void num__viewsRemoved(de.m0ep.uni.ma.rdfs.sioc.Role source, java.lang.String oldValue);

	/**
	 * Called when a value of references has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.Role
	 * @param newValue the object representing the new value
	 */	
	public void referencesAdded(de.m0ep.uni.ma.rdfs.sioc.Role source, com.ibm.adtech.jastor.Thing newValue);

	/**
	 * Called when a value of references has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.Role
	 * @param oldValue the object representing the removed value
	 */
	public void referencesRemoved(de.m0ep.uni.ma.rdfs.sioc.Role source, com.ibm.adtech.jastor.Thing oldValue);
		
	/**
	 * Called when a value of topic has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.Role
	 * @param newValue the object representing the new value
	 */	
	public void topicAdded(de.m0ep.uni.ma.rdfs.sioc.Role source, com.ibm.adtech.jastor.Thing newValue);

	/**
	 * Called when a value of topic has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.Role
	 * @param oldValue the object representing the removed value
	 */
	public void topicRemoved(de.m0ep.uni.ma.rdfs.sioc.Role source, com.ibm.adtech.jastor.Thing oldValue);
		
	/**
	 * Called when a value of last__activity__date has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.Role
	 * @param newValue the object representing the new value
	 */	
	public void last__activity__dateAdded(de.m0ep.uni.ma.rdfs.sioc.Role source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of last__activity__date has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.Role
	 * @param oldValue the object representing the removed value
	 */	
	public void last__activity__dateRemoved(de.m0ep.uni.ma.rdfs.sioc.Role source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of id has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.Role
	 * @param newValue the object representing the new value
	 */	
	public void idAdded(de.m0ep.uni.ma.rdfs.sioc.Role source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of id has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.Role
	 * @param oldValue the object representing the removed value
	 */	
	public void idRemoved(de.m0ep.uni.ma.rdfs.sioc.Role source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of links__to has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.Role
	 * @param newValue the object representing the new value
	 */	
	public void links__toAdded(de.m0ep.uni.ma.rdfs.sioc.Role source, com.ibm.adtech.jastor.Thing newValue);

	/**
	 * Called when a value of links__to has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.Role
	 * @param oldValue the object representing the removed value
	 */
	public void links__toRemoved(de.m0ep.uni.ma.rdfs.sioc.Role source, com.ibm.adtech.jastor.Thing oldValue);
		
	/**
	 * Called when a value of has__scope has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.Role
	 * @param newValue the object representing the new value
	 */	
	public void has__scopeAdded(de.m0ep.uni.ma.rdfs.sioc.Role source, com.ibm.adtech.jastor.Thing newValue);

	/**
	 * Called when a value of has__scope has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.Role
	 * @param oldValue the object representing the removed value
	 */
	public void has__scopeRemoved(de.m0ep.uni.ma.rdfs.sioc.Role source, com.ibm.adtech.jastor.Thing oldValue);
		
	/**
	 * Called when a value of last__reply__date has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.Role
	 * @param newValue the object representing the new value
	 */	
	public void last__reply__dateAdded(de.m0ep.uni.ma.rdfs.sioc.Role source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of last__reply__date has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.Role
	 * @param oldValue the object representing the removed value
	 */	
	public void last__reply__dateRemoved(de.m0ep.uni.ma.rdfs.sioc.Role source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of num__replies has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.Role
	 * @param newValue the object representing the new value
	 */	
	public void num__repliesAdded(de.m0ep.uni.ma.rdfs.sioc.Role source, java.lang.String newValue);

	/**
	 * Called when a value of num__replies has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.Role
	 * @param oldValue the object representing the removed value
	 */	
	public void num__repliesRemoved(de.m0ep.uni.ma.rdfs.sioc.Role source, java.lang.String oldValue);

	/**
	 * Called when a value of has__function has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.Role
	 * @param newValue the object representing the new value
	 */	
	public void has__functionAdded(de.m0ep.uni.ma.rdfs.sioc.Role source, de.m0ep.uni.ma.rdfs.sioc.Role newValue);

	/**
	 * Called when a value of has__function has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.Role
	 * @param oldValue the object representing the removed value
	 */
	public void has__functionRemoved(de.m0ep.uni.ma.rdfs.sioc.Role source, de.m0ep.uni.ma.rdfs.sioc.Role oldValue);
		
	/**
	 * Called when a value of name has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.Role
	 * @param newValue the object representing the new value
	 */	
	public void nameAdded(de.m0ep.uni.ma.rdfs.sioc.Role source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of name has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.Role
	 * @param oldValue the object representing the removed value
	 */	
	public void nameRemoved(de.m0ep.uni.ma.rdfs.sioc.Role source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of has__space has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.Role
	 * @param newValue the object representing the new value
	 */	
	public void has__spaceAdded(de.m0ep.uni.ma.rdfs.sioc.Role source, de.m0ep.uni.ma.rdfs.sioc.Space newValue);

	/**
	 * Called when a value of has__space has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.Role
	 * @param oldValue the object representing the removed value
	 */
	public void has__spaceRemoved(de.m0ep.uni.ma.rdfs.sioc.Role source, de.m0ep.uni.ma.rdfs.sioc.Space oldValue);
		
	/**
	 * Called when a value of related__to has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.Role
	 * @param newValue the object representing the new value
	 */	
	public void related__toAdded(de.m0ep.uni.ma.rdfs.sioc.Role source, com.ibm.adtech.jastor.Thing newValue);

	/**
	 * Called when a value of related__to has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.Role
	 * @param oldValue the object representing the removed value
	 */
	public void related__toRemoved(de.m0ep.uni.ma.rdfs.sioc.Role source, com.ibm.adtech.jastor.Thing oldValue);
		
	/**
	 * Called when a value of date has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.Role
	 * @param newValue the object representing the new value
	 */	
	public void dateAdded(de.m0ep.uni.ma.rdfs.sioc.Role source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of date has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.Role
	 * @param oldValue the object representing the removed value
	 */	
	public void dateRemoved(de.m0ep.uni.ma.rdfs.sioc.Role source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of title has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.Role
	 * @param newValue the object representing the new value
	 */	
	public void titleAdded(de.m0ep.uni.ma.rdfs.sioc.Role source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of title has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.Role
	 * @param oldValue the object representing the removed value
	 */	
	public void titleRemoved(de.m0ep.uni.ma.rdfs.sioc.Role source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of scope__of has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.Role
	 * @param newValue the object representing the new value
	 */	
	public void scope__ofAdded(de.m0ep.uni.ma.rdfs.sioc.Role source, de.m0ep.uni.ma.rdfs.sioc.Role newValue);

	/**
	 * Called when a value of scope__of has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.Role
	 * @param oldValue the object representing the removed value
	 */
	public void scope__ofRemoved(de.m0ep.uni.ma.rdfs.sioc.Role source, de.m0ep.uni.ma.rdfs.sioc.Role oldValue);
		
	/**
	 * Called when a value of feed has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.Role
	 * @param newValue the object representing the new value
	 */	
	public void feedAdded(de.m0ep.uni.ma.rdfs.sioc.Role source, com.ibm.adtech.jastor.Thing newValue);

	/**
	 * Called when a value of feed has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.Role
	 * @param oldValue the object representing the removed value
	 */
	public void feedRemoved(de.m0ep.uni.ma.rdfs.sioc.Role source, com.ibm.adtech.jastor.Thing oldValue);
		
	/**
	 * Called when a value of function__of has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.Role
	 * @param newValue the object representing the new value
	 */	
	public void function__ofAdded(de.m0ep.uni.ma.rdfs.sioc.Role source, com.ibm.adtech.jastor.Thing newValue);

	/**
	 * Called when a value of function__of has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.Role
	 * @param oldValue the object representing the removed value
	 */
	public void function__ofRemoved(de.m0ep.uni.ma.rdfs.sioc.Role source, com.ibm.adtech.jastor.Thing oldValue);
		
	/**
	 * Called when a value of depiction has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.Role
	 * @param newValue the object representing the new value
	 */	
	public void depictionAdded(de.m0ep.uni.ma.rdfs.sioc.Role source, de.m0ep.uni.ma.rdfs.foaf.Image newValue);

	/**
	 * Called when a value of depiction has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.Role
	 * @param oldValue the object representing the removed value
	 */
	public void depictionRemoved(de.m0ep.uni.ma.rdfs.sioc.Role source, de.m0ep.uni.ma.rdfs.foaf.Image oldValue);
		
}