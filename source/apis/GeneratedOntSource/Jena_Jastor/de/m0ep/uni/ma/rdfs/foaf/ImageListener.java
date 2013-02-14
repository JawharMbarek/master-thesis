

package de.m0ep.uni.ma.rdfs.foaf;

/*
import com.hp.hpl.jena.datatypes.xsd.*;
import com.hp.hpl.jena.datatypes.xsd.impl.*;
import com.hp.hpl.jena.rdf.model.*;
import com.ibm.adtech.jastor.*;
import java.util.*;
import java.math.*;
*/


/**
 * Implementations of this listener may be registered with instances of de.m0ep.uni.ma.rdfs.foaf.Image to 
 * receive notification when properties changed, added or removed.
 * <br>
 */
public interface ImageListener extends com.ibm.adtech.jastor.ThingListener {


	/**
	 * Called when a value of fundedBy has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Image
	 * @param newValue the object representing the new value
	 */	
	public void fundedByAdded(de.m0ep.uni.ma.rdfs.foaf.Image source, com.ibm.adtech.jastor.Thing newValue);

	/**
	 * Called when a value of fundedBy has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Image
	 * @param oldValue the object representing the removed value
	 */
	public void fundedByRemoved(de.m0ep.uni.ma.rdfs.foaf.Image source, com.ibm.adtech.jastor.Thing oldValue);
		
	/**
	 * Called when a value of name has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Image
	 * @param newValue the object representing the new value
	 */	
	public void nameAdded(de.m0ep.uni.ma.rdfs.foaf.Image source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of name has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Image
	 * @param oldValue the object representing the removed value
	 */	
	public void nameRemoved(de.m0ep.uni.ma.rdfs.foaf.Image source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when primaryTopic has changed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Image
	 */
	public void primaryTopicChanged(de.m0ep.uni.ma.rdfs.foaf.Image source);

	/**
	 * Called when a value of dnaChecksum has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Image
	 * @param newValue the object representing the new value
	 */	
	public void dnaChecksumAdded(de.m0ep.uni.ma.rdfs.foaf.Image source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of dnaChecksum has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Image
	 * @param oldValue the object representing the removed value
	 */	
	public void dnaChecksumRemoved(de.m0ep.uni.ma.rdfs.foaf.Image source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of phone has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Image
	 * @param newValue the object representing the new value
	 */	
	public void phoneAdded(de.m0ep.uni.ma.rdfs.foaf.Image source, com.ibm.adtech.jastor.Thing newValue);

	/**
	 * Called when a value of phone has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Image
	 * @param oldValue the object representing the removed value
	 */
	public void phoneRemoved(de.m0ep.uni.ma.rdfs.foaf.Image source, com.ibm.adtech.jastor.Thing oldValue);
		
	/**
	 * Called when a value of page has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Image
	 * @param newValue the object representing the new value
	 */	
	public void pageAdded(de.m0ep.uni.ma.rdfs.foaf.Image source, de.m0ep.uni.ma.rdfs.foaf.Document newValue);

	/**
	 * Called when a value of page has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Image
	 * @param oldValue the object representing the removed value
	 */
	public void pageRemoved(de.m0ep.uni.ma.rdfs.foaf.Image source, de.m0ep.uni.ma.rdfs.foaf.Document oldValue);
		
	/**
	 * Called when a value of nick has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Image
	 * @param newValue the object representing the new value
	 */	
	public void nickAdded(de.m0ep.uni.ma.rdfs.foaf.Image source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of nick has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Image
	 * @param oldValue the object representing the removed value
	 */	
	public void nickRemoved(de.m0ep.uni.ma.rdfs.foaf.Image source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of logo has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Image
	 * @param newValue the object representing the new value
	 */	
	public void logoAdded(de.m0ep.uni.ma.rdfs.foaf.Image source, com.ibm.adtech.jastor.Thing newValue);

	/**
	 * Called when a value of logo has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Image
	 * @param oldValue the object representing the removed value
	 */
	public void logoRemoved(de.m0ep.uni.ma.rdfs.foaf.Image source, com.ibm.adtech.jastor.Thing oldValue);
		
	/**
	 * Called when a value of maker has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Image
	 * @param newValue the object representing the new value
	 */	
	public void makerAdded(de.m0ep.uni.ma.rdfs.foaf.Image source, de.m0ep.uni.ma.rdfs.foaf.Agent newValue);

	/**
	 * Called when a value of maker has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Image
	 * @param oldValue the object representing the removed value
	 */
	public void makerRemoved(de.m0ep.uni.ma.rdfs.foaf.Image source, de.m0ep.uni.ma.rdfs.foaf.Agent oldValue);
		
	/**
	 * Called when a value of givenname has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Image
	 * @param newValue the object representing the new value
	 */	
	public void givennameAdded(de.m0ep.uni.ma.rdfs.foaf.Image source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of givenname has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Image
	 * @param oldValue the object representing the removed value
	 */	
	public void givennameRemoved(de.m0ep.uni.ma.rdfs.foaf.Image source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of topic has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Image
	 * @param newValue the object representing the new value
	 */	
	public void topicAdded(de.m0ep.uni.ma.rdfs.foaf.Image source, com.ibm.adtech.jastor.Thing newValue);

	/**
	 * Called when a value of topic has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Image
	 * @param oldValue the object representing the removed value
	 */
	public void topicRemoved(de.m0ep.uni.ma.rdfs.foaf.Image source, com.ibm.adtech.jastor.Thing oldValue);
		
	/**
	 * Called when a value of title has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Image
	 * @param newValue the object representing the new value
	 */	
	public void titleAdded(de.m0ep.uni.ma.rdfs.foaf.Image source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of title has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Image
	 * @param oldValue the object representing the removed value
	 */	
	public void titleRemoved(de.m0ep.uni.ma.rdfs.foaf.Image source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of givenName has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Image
	 * @param newValue the object representing the new value
	 */	
	public void givenNameAdded(de.m0ep.uni.ma.rdfs.foaf.Image source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of givenName has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Image
	 * @param oldValue the object representing the removed value
	 */	
	public void givenNameRemoved(de.m0ep.uni.ma.rdfs.foaf.Image source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of homepage has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Image
	 * @param newValue the object representing the new value
	 */	
	public void homepageAdded(de.m0ep.uni.ma.rdfs.foaf.Image source, de.m0ep.uni.ma.rdfs.foaf.Document newValue);

	/**
	 * Called when a value of homepage has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Image
	 * @param oldValue the object representing the removed value
	 */
	public void homepageRemoved(de.m0ep.uni.ma.rdfs.foaf.Image source, de.m0ep.uni.ma.rdfs.foaf.Document oldValue);
		
	/**
	 * Called when a value of sha1 has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Image
	 * @param newValue the object representing the new value
	 */	
	public void sha1Added(de.m0ep.uni.ma.rdfs.foaf.Image source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of sha1 has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Image
	 * @param oldValue the object representing the removed value
	 */	
	public void sha1Removed(de.m0ep.uni.ma.rdfs.foaf.Image source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of theme has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Image
	 * @param newValue the object representing the new value
	 */	
	public void themeAdded(de.m0ep.uni.ma.rdfs.foaf.Image source, com.ibm.adtech.jastor.Thing newValue);

	/**
	 * Called when a value of theme has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Image
	 * @param oldValue the object representing the removed value
	 */
	public void themeRemoved(de.m0ep.uni.ma.rdfs.foaf.Image source, com.ibm.adtech.jastor.Thing oldValue);
		
	/**
	 * Called when a value of depicts has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Image
	 * @param newValue the object representing the new value
	 */	
	public void depictsAdded(de.m0ep.uni.ma.rdfs.foaf.Image source, com.ibm.adtech.jastor.Thing newValue);

	/**
	 * Called when a value of depicts has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Image
	 * @param oldValue the object representing the removed value
	 */
	public void depictsRemoved(de.m0ep.uni.ma.rdfs.foaf.Image source, com.ibm.adtech.jastor.Thing oldValue);
		
	/**
	 * Called when a value of thumbnail has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Image
	 * @param newValue the object representing the new value
	 */	
	public void thumbnailAdded(de.m0ep.uni.ma.rdfs.foaf.Image source, de.m0ep.uni.ma.rdfs.foaf.Image newValue);

	/**
	 * Called when a value of thumbnail has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Image
	 * @param oldValue the object representing the removed value
	 */
	public void thumbnailRemoved(de.m0ep.uni.ma.rdfs.foaf.Image source, de.m0ep.uni.ma.rdfs.foaf.Image oldValue);
		
}