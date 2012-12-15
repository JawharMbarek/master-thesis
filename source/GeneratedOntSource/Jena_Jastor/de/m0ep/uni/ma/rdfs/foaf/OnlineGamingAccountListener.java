

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
 * Implementations of this listener may be registered with instances of de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount to 
 * receive notification when properties changed, added or removed.
 * <br>
 */
public interface OnlineGamingAccountListener extends com.ibm.adtech.jastor.ThingListener {


	/**
	 * Called when a value of fundedBy has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount
	 * @param newValue the object representing the new value
	 */	
	public void fundedByAdded(de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount source, com.ibm.adtech.jastor.Thing newValue);

	/**
	 * Called when a value of fundedBy has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount
	 * @param oldValue the object representing the removed value
	 */
	public void fundedByRemoved(de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount source, com.ibm.adtech.jastor.Thing oldValue);
		
	/**
	 * Called when a value of name has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount
	 * @param newValue the object representing the new value
	 */	
	public void nameAdded(de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of name has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount
	 * @param oldValue the object representing the removed value
	 */	
	public void nameRemoved(de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of dnaChecksum has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount
	 * @param newValue the object representing the new value
	 */	
	public void dnaChecksumAdded(de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of dnaChecksum has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount
	 * @param oldValue the object representing the removed value
	 */	
	public void dnaChecksumRemoved(de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of phone has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount
	 * @param newValue the object representing the new value
	 */	
	public void phoneAdded(de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount source, com.ibm.adtech.jastor.Thing newValue);

	/**
	 * Called when a value of phone has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount
	 * @param oldValue the object representing the removed value
	 */
	public void phoneRemoved(de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount source, com.ibm.adtech.jastor.Thing oldValue);
		
	/**
	 * Called when a value of page has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount
	 * @param newValue the object representing the new value
	 */	
	public void pageAdded(de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount source, de.m0ep.uni.ma.rdfs.foaf.Document newValue);

	/**
	 * Called when a value of page has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount
	 * @param oldValue the object representing the removed value
	 */
	public void pageRemoved(de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount source, de.m0ep.uni.ma.rdfs.foaf.Document oldValue);
		
	/**
	 * Called when a value of nick has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount
	 * @param newValue the object representing the new value
	 */	
	public void nickAdded(de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of nick has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount
	 * @param oldValue the object representing the removed value
	 */	
	public void nickRemoved(de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of logo has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount
	 * @param newValue the object representing the new value
	 */	
	public void logoAdded(de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount source, com.ibm.adtech.jastor.Thing newValue);

	/**
	 * Called when a value of logo has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount
	 * @param oldValue the object representing the removed value
	 */
	public void logoRemoved(de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount source, com.ibm.adtech.jastor.Thing oldValue);
		
	/**
	 * Called when a value of maker has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount
	 * @param newValue the object representing the new value
	 */	
	public void makerAdded(de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount source, de.m0ep.uni.ma.rdfs.foaf.Agent newValue);

	/**
	 * Called when a value of maker has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount
	 * @param oldValue the object representing the removed value
	 */
	public void makerRemoved(de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount source, de.m0ep.uni.ma.rdfs.foaf.Agent oldValue);
		
	/**
	 * Called when a value of givenname has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount
	 * @param newValue the object representing the new value
	 */	
	public void givennameAdded(de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of givenname has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount
	 * @param oldValue the object representing the removed value
	 */	
	public void givennameRemoved(de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of title has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount
	 * @param newValue the object representing the new value
	 */	
	public void titleAdded(de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of title has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount
	 * @param oldValue the object representing the removed value
	 */	
	public void titleRemoved(de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of givenName has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount
	 * @param newValue the object representing the new value
	 */	
	public void givenNameAdded(de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of givenName has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount
	 * @param oldValue the object representing the removed value
	 */	
	public void givenNameRemoved(de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of homepage has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount
	 * @param newValue the object representing the new value
	 */	
	public void homepageAdded(de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount source, de.m0ep.uni.ma.rdfs.foaf.Document newValue);

	/**
	 * Called when a value of homepage has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount
	 * @param oldValue the object representing the removed value
	 */
	public void homepageRemoved(de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount source, de.m0ep.uni.ma.rdfs.foaf.Document oldValue);
		
	/**
	 * Called when a value of accountServiceHomepage has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount
	 * @param newValue the object representing the new value
	 */	
	public void accountServiceHomepageAdded(de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount source, de.m0ep.uni.ma.rdfs.foaf.Document newValue);

	/**
	 * Called when a value of accountServiceHomepage has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount
	 * @param oldValue the object representing the removed value
	 */
	public void accountServiceHomepageRemoved(de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount source, de.m0ep.uni.ma.rdfs.foaf.Document oldValue);
		
	/**
	 * Called when a value of theme has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount
	 * @param newValue the object representing the new value
	 */	
	public void themeAdded(de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount source, com.ibm.adtech.jastor.Thing newValue);

	/**
	 * Called when a value of theme has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount
	 * @param oldValue the object representing the removed value
	 */
	public void themeRemoved(de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount source, com.ibm.adtech.jastor.Thing oldValue);
		
	/**
	 * Called when a value of accountName has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount
	 * @param newValue the object representing the new value
	 */	
	public void accountNameAdded(de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of accountName has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount
	 * @param oldValue the object representing the removed value
	 */	
	public void accountNameRemoved(de.m0ep.uni.ma.rdfs.foaf.OnlineGamingAccount source, com.hp.hpl.jena.rdf.model.Literal oldValue);

}