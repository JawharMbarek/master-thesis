

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
 * Implementations of this listener may be registered with instances of de.m0ep.uni.ma.rdfs.sioc.UserAccount to 
 * receive notification when properties changed, added or removed.
 * <br>
 */
public interface UserAccountListener extends com.ibm.adtech.jastor.ThingListener {


	/**
	 * Called when a value of fundedBy has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param newValue the object representing the new value
	 */	
	public void fundedByAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.ibm.adtech.jastor.Thing newValue);

	/**
	 * Called when a value of fundedBy has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param oldValue the object representing the removed value
	 */
	public void fundedByRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.ibm.adtech.jastor.Thing oldValue);
		
	/**
	 * Called when a value of name has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param newValue the object representing the new value
	 */	
	public void nameAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of name has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param oldValue the object representing the removed value
	 */	
	public void nameRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of dnaChecksum has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param newValue the object representing the new value
	 */	
	public void dnaChecksumAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of dnaChecksum has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param oldValue the object representing the removed value
	 */	
	public void dnaChecksumRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of phone has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param newValue the object representing the new value
	 */	
	public void phoneAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.ibm.adtech.jastor.Thing newValue);

	/**
	 * Called when a value of phone has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param oldValue the object representing the removed value
	 */
	public void phoneRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.ibm.adtech.jastor.Thing oldValue);
		
	/**
	 * Called when a value of page has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param newValue the object representing the new value
	 */	
	public void pageAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, de.m0ep.uni.ma.rdfs.foaf.Document newValue);

	/**
	 * Called when a value of page has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param oldValue the object representing the removed value
	 */
	public void pageRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, de.m0ep.uni.ma.rdfs.foaf.Document oldValue);
		
	/**
	 * Called when a value of nick has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param newValue the object representing the new value
	 */	
	public void nickAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of nick has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param oldValue the object representing the removed value
	 */	
	public void nickRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of logo has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param newValue the object representing the new value
	 */	
	public void logoAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.ibm.adtech.jastor.Thing newValue);

	/**
	 * Called when a value of logo has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param oldValue the object representing the removed value
	 */
	public void logoRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.ibm.adtech.jastor.Thing oldValue);
		
	/**
	 * Called when a value of maker has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param newValue the object representing the new value
	 */	
	public void makerAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, de.m0ep.uni.ma.rdfs.foaf.Agent newValue);

	/**
	 * Called when a value of maker has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param oldValue the object representing the removed value
	 */
	public void makerRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, de.m0ep.uni.ma.rdfs.foaf.Agent oldValue);
		
	/**
	 * Called when a value of givenname has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param newValue the object representing the new value
	 */	
	public void givennameAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of givenname has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param oldValue the object representing the removed value
	 */	
	public void givennameRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of title has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param newValue the object representing the new value
	 */	
	public void titleAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of title has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param oldValue the object representing the removed value
	 */	
	public void titleRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of givenName has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param newValue the object representing the new value
	 */	
	public void givenNameAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of givenName has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param oldValue the object representing the removed value
	 */	
	public void givenNameRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of homepage has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param newValue the object representing the new value
	 */	
	public void homepageAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, de.m0ep.uni.ma.rdfs.foaf.Document newValue);

	/**
	 * Called when a value of homepage has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param oldValue the object representing the removed value
	 */
	public void homepageRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, de.m0ep.uni.ma.rdfs.foaf.Document oldValue);
		
	/**
	 * Called when a value of accountServiceHomepage has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param newValue the object representing the new value
	 */	
	public void accountServiceHomepageAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, de.m0ep.uni.ma.rdfs.foaf.Document newValue);

	/**
	 * Called when a value of accountServiceHomepage has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param oldValue the object representing the removed value
	 */
	public void accountServiceHomepageRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, de.m0ep.uni.ma.rdfs.foaf.Document oldValue);
		
	/**
	 * Called when a value of theme has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param newValue the object representing the new value
	 */	
	public void themeAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.ibm.adtech.jastor.Thing newValue);

	/**
	 * Called when a value of theme has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param oldValue the object representing the removed value
	 */
	public void themeRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.ibm.adtech.jastor.Thing oldValue);
		
	/**
	 * Called when a value of accountName has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param newValue the object representing the new value
	 */	
	public void accountNameAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of accountName has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param oldValue the object representing the removed value
	 */	
	public void accountNameRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of email__sha1 has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param newValue the object representing the new value
	 */	
	public void email__sha1Added(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of email__sha1 has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param oldValue the object representing the removed value
	 */	
	public void email__sha1Removed(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of member__of has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param newValue the object representing the new value
	 */	
	public void member__ofAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, de.m0ep.uni.ma.rdfs.sioc.Usergroup newValue);

	/**
	 * Called when a value of member__of has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param oldValue the object representing the removed value
	 */
	public void member__ofRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, de.m0ep.uni.ma.rdfs.sioc.Usergroup oldValue);
		
	/**
	 * Called when a value of creator__of has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param newValue the object representing the new value
	 */	
	public void creator__ofAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.ibm.adtech.jastor.Thing newValue);

	/**
	 * Called when a value of creator__of has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param oldValue the object representing the removed value
	 */
	public void creator__ofRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.ibm.adtech.jastor.Thing oldValue);
		
	/**
	 * Called when a value of moderator__of has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param newValue the object representing the new value
	 */	
	public void moderator__ofAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, de.m0ep.uni.ma.rdfs.sioc.Forum newValue);

	/**
	 * Called when a value of moderator__of has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param oldValue the object representing the removed value
	 */
	public void moderator__ofRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, de.m0ep.uni.ma.rdfs.sioc.Forum oldValue);
		
	/**
	 * Called when a value of follows has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param newValue the object representing the new value
	 */	
	public void followsAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, de.m0ep.uni.ma.rdfs.sioc.UserAccount newValue);

	/**
	 * Called when a value of follows has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param oldValue the object representing the removed value
	 */
	public void followsRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, de.m0ep.uni.ma.rdfs.sioc.UserAccount oldValue);
		
	/**
	 * Called when a value of account__of has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param newValue the object representing the new value
	 */	
	public void account__ofAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, de.m0ep.uni.ma.rdfs.foaf.Agent newValue);

	/**
	 * Called when a value of account__of has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param oldValue the object representing the removed value
	 */
	public void account__ofRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, de.m0ep.uni.ma.rdfs.foaf.Agent oldValue);
		
	/**
	 * Called when a value of owner__of has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param newValue the object representing the new value
	 */	
	public void owner__ofAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.ibm.adtech.jastor.Thing newValue);

	/**
	 * Called when a value of owner__of has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param oldValue the object representing the removed value
	 */
	public void owner__ofRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.ibm.adtech.jastor.Thing oldValue);
		
	/**
	 * Called when a value of email has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param newValue the object representing the new value
	 */	
	public void emailAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.ibm.adtech.jastor.Thing newValue);

	/**
	 * Called when a value of email has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param oldValue the object representing the removed value
	 */
	public void emailRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.ibm.adtech.jastor.Thing oldValue);
		
	/**
	 * Called when a value of avatar has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param newValue the object representing the new value
	 */	
	public void avatarAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, de.m0ep.uni.ma.rdfs.foaf.Image newValue);

	/**
	 * Called when a value of avatar has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param oldValue the object representing the removed value
	 */
	public void avatarRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, de.m0ep.uni.ma.rdfs.foaf.Image oldValue);
		
	/**
	 * Called when a value of administrator__of has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param newValue the object representing the new value
	 */	
	public void administrator__ofAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, de.m0ep.uni.ma.rdfs.sioc.Site newValue);

	/**
	 * Called when a value of administrator__of has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param oldValue the object representing the removed value
	 */
	public void administrator__ofRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, de.m0ep.uni.ma.rdfs.sioc.Site oldValue);
		
	/**
	 * Called when a value of modifier__of has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param newValue the object representing the new value
	 */	
	public void modifier__ofAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, de.m0ep.uni.ma.rdfs.sioc.Item newValue);

	/**
	 * Called when a value of modifier__of has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param oldValue the object representing the removed value
	 */
	public void modifier__ofRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, de.m0ep.uni.ma.rdfs.sioc.Item oldValue);
		
	/**
	 * Called when a value of subscriber__of has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param newValue the object representing the new value
	 */	
	public void subscriber__ofAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, de.m0ep.uni.ma.rdfs.sioc.Container newValue);

	/**
	 * Called when a value of subscriber__of has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param oldValue the object representing the removed value
	 */
	public void subscriber__ofRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, de.m0ep.uni.ma.rdfs.sioc.Container oldValue);
		
	/**
	 * Called when a value of depiction has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param newValue the object representing the new value
	 */	
	public void depictionAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, de.m0ep.uni.ma.rdfs.foaf.Image newValue);

	/**
	 * Called when a value of depiction has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param oldValue the object representing the removed value
	 */
	public void depictionRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, de.m0ep.uni.ma.rdfs.foaf.Image oldValue);
		
	/**
	 * Called when a value of id has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param newValue the object representing the new value
	 */	
	public void idAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of id has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param oldValue the object representing the removed value
	 */	
	public void idRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of num__authors has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param newValue the object representing the new value
	 */	
	public void num__authorsAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, java.lang.String newValue);

	/**
	 * Called when a value of num__authors has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param oldValue the object representing the removed value
	 */	
	public void num__authorsRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, java.lang.String oldValue);

	/**
	 * Called when a value of note has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param newValue the object representing the new value
	 */	
	public void noteAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of note has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param oldValue the object representing the removed value
	 */	
	public void noteRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of ns1_name has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param newValue the object representing the new value
	 */	
	public void ns1_nameAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of ns1_name has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param oldValue the object representing the removed value
	 */	
	public void ns1_nameRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of num__views has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param newValue the object representing the new value
	 */	
	public void num__viewsAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, java.lang.String newValue);

	/**
	 * Called when a value of num__views has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param oldValue the object representing the removed value
	 */	
	public void num__viewsRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, java.lang.String oldValue);

	/**
	 * Called when a value of description has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param newValue the object representing the new value
	 */	
	public void descriptionAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of description has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param oldValue the object representing the removed value
	 */	
	public void descriptionRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of date has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param newValue the object representing the new value
	 */	
	public void dateAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of date has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param oldValue the object representing the removed value
	 */	
	public void dateRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of num__replies has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param newValue the object representing the new value
	 */	
	public void num__repliesAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, java.lang.String newValue);

	/**
	 * Called when a value of num__replies has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param oldValue the object representing the removed value
	 */	
	public void num__repliesRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, java.lang.String oldValue);

	/**
	 * Called when a value of last__activity__date has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param newValue the object representing the new value
	 */	
	public void last__activity__dateAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of last__activity__date has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param oldValue the object representing the removed value
	 */	
	public void last__activity__dateRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of last__reply__date has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param newValue the object representing the new value
	 */	
	public void last__reply__dateAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of last__reply__date has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param oldValue the object representing the removed value
	 */	
	public void last__reply__dateRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of ns2_title has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param newValue the object representing the new value
	 */	
	public void ns2_titleAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of ns2_title has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param oldValue the object representing the removed value
	 */	
	public void ns2_titleRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of subject has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param newValue the object representing the new value
	 */	
	public void subjectAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.ibm.adtech.jastor.Thing newValue);

	/**
	 * Called when a value of subject has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param oldValue the object representing the removed value
	 */
	public void subjectRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.ibm.adtech.jastor.Thing oldValue);
		
	/**
	 * Called when a value of references has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param newValue the object representing the new value
	 */	
	public void referencesAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.ibm.adtech.jastor.Thing newValue);

	/**
	 * Called when a value of references has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param oldValue the object representing the removed value
	 */
	public void referencesRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.ibm.adtech.jastor.Thing oldValue);
		
	/**
	 * Called when a value of links__to has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param newValue the object representing the new value
	 */	
	public void links__toAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.ibm.adtech.jastor.Thing newValue);

	/**
	 * Called when a value of links__to has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param oldValue the object representing the removed value
	 */
	public void links__toRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.ibm.adtech.jastor.Thing oldValue);
		
	/**
	 * Called when a value of feed has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param newValue the object representing the new value
	 */	
	public void feedAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.ibm.adtech.jastor.Thing newValue);

	/**
	 * Called when a value of feed has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param oldValue the object representing the removed value
	 */
	public void feedRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.ibm.adtech.jastor.Thing oldValue);
		
	/**
	 * Called when a value of has__space has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param newValue the object representing the new value
	 */	
	public void has__spaceAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, de.m0ep.uni.ma.rdfs.sioc.Space newValue);

	/**
	 * Called when a value of has__space has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param oldValue the object representing the removed value
	 */
	public void has__spaceRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, de.m0ep.uni.ma.rdfs.sioc.Space oldValue);
		
	/**
	 * Called when a value of scope__of has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param newValue the object representing the new value
	 */	
	public void scope__ofAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, de.m0ep.uni.ma.rdfs.sioc.Role newValue);

	/**
	 * Called when a value of scope__of has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param oldValue the object representing the removed value
	 */
	public void scope__ofRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, de.m0ep.uni.ma.rdfs.sioc.Role oldValue);
		
	/**
	 * Called when a value of topic has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param newValue the object representing the new value
	 */	
	public void topicAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.ibm.adtech.jastor.Thing newValue);

	/**
	 * Called when a value of topic has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param oldValue the object representing the removed value
	 */
	public void topicRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.ibm.adtech.jastor.Thing oldValue);
		
	/**
	 * Called when a value of link has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param newValue the object representing the new value
	 */	
	public void linkAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.ibm.adtech.jastor.Thing newValue);

	/**
	 * Called when a value of link has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param oldValue the object representing the removed value
	 */
	public void linkRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.ibm.adtech.jastor.Thing oldValue);
		
	/**
	 * Called when a value of has__creator has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param newValue the object representing the new value
	 */	
	public void has__creatorAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, de.m0ep.uni.ma.rdfs.sioc.UserAccount newValue);

	/**
	 * Called when a value of has__creator has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param oldValue the object representing the removed value
	 */
	public void has__creatorRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, de.m0ep.uni.ma.rdfs.sioc.UserAccount oldValue);
		
	/**
	 * Called when a value of has__owner has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param newValue the object representing the new value
	 */	
	public void has__ownerAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, de.m0ep.uni.ma.rdfs.sioc.UserAccount newValue);

	/**
	 * Called when a value of has__owner has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param oldValue the object representing the removed value
	 */
	public void has__ownerRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, de.m0ep.uni.ma.rdfs.sioc.UserAccount oldValue);
		
	/**
	 * Called when a value of has__function has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param newValue the object representing the new value
	 */	
	public void has__functionAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, de.m0ep.uni.ma.rdfs.sioc.Role newValue);

	/**
	 * Called when a value of has__function has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param oldValue the object representing the removed value
	 */
	public void has__functionRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, de.m0ep.uni.ma.rdfs.sioc.Role oldValue);
		
	/**
	 * Called when a value of related__to has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param newValue the object representing the new value
	 */	
	public void related__toAdded(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.ibm.adtech.jastor.Thing newValue);

	/**
	 * Called when a value of related__to has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.sioc.UserAccount
	 * @param oldValue the object representing the removed value
	 */
	public void related__toRemoved(de.m0ep.uni.ma.rdfs.sioc.UserAccount source, com.ibm.adtech.jastor.Thing oldValue);
		
}