

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
 * Implementations of this listener may be registered with instances of de.m0ep.uni.ma.rdfs.foaf.Person to 
 * receive notification when properties changed, added or removed.
 * <br>
 */
public interface PersonListener extends com.ibm.adtech.jastor.ThingListener {


	/**
	 * Called when a value of fundedBy has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param newValue the object representing the new value
	 */	
	public void fundedByAdded(de.m0ep.uni.ma.rdfs.foaf.Person source, com.ibm.adtech.jastor.Thing newValue);

	/**
	 * Called when a value of fundedBy has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param oldValue the object representing the removed value
	 */
	public void fundedByRemoved(de.m0ep.uni.ma.rdfs.foaf.Person source, com.ibm.adtech.jastor.Thing oldValue);
		
	/**
	 * Called when a value of name has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param newValue the object representing the new value
	 */	
	public void nameAdded(de.m0ep.uni.ma.rdfs.foaf.Person source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of name has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param oldValue the object representing the removed value
	 */	
	public void nameRemoved(de.m0ep.uni.ma.rdfs.foaf.Person source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of dnaChecksum has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param newValue the object representing the new value
	 */	
	public void dnaChecksumAdded(de.m0ep.uni.ma.rdfs.foaf.Person source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of dnaChecksum has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param oldValue the object representing the removed value
	 */	
	public void dnaChecksumRemoved(de.m0ep.uni.ma.rdfs.foaf.Person source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of phone has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param newValue the object representing the new value
	 */	
	public void phoneAdded(de.m0ep.uni.ma.rdfs.foaf.Person source, com.ibm.adtech.jastor.Thing newValue);

	/**
	 * Called when a value of phone has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param oldValue the object representing the removed value
	 */
	public void phoneRemoved(de.m0ep.uni.ma.rdfs.foaf.Person source, com.ibm.adtech.jastor.Thing oldValue);
		
	/**
	 * Called when a value of page has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param newValue the object representing the new value
	 */	
	public void pageAdded(de.m0ep.uni.ma.rdfs.foaf.Person source, de.m0ep.uni.ma.rdfs.foaf.Document newValue);

	/**
	 * Called when a value of page has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param oldValue the object representing the removed value
	 */
	public void pageRemoved(de.m0ep.uni.ma.rdfs.foaf.Person source, de.m0ep.uni.ma.rdfs.foaf.Document oldValue);
		
	/**
	 * Called when a value of nick has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param newValue the object representing the new value
	 */	
	public void nickAdded(de.m0ep.uni.ma.rdfs.foaf.Person source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of nick has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param oldValue the object representing the removed value
	 */	
	public void nickRemoved(de.m0ep.uni.ma.rdfs.foaf.Person source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of based__near has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param newValue the object representing the new value
	 */	
	public void based__nearAdded(de.m0ep.uni.ma.rdfs.foaf.Person source, de.m0ep.uni.ma.rdfs.foaf.SpatialThing newValue);

	/**
	 * Called when a value of based__near has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param oldValue the object representing the removed value
	 */
	public void based__nearRemoved(de.m0ep.uni.ma.rdfs.foaf.Person source, de.m0ep.uni.ma.rdfs.foaf.SpatialThing oldValue);
		
	/**
	 * Called when a value of logo has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param newValue the object representing the new value
	 */	
	public void logoAdded(de.m0ep.uni.ma.rdfs.foaf.Person source, com.ibm.adtech.jastor.Thing newValue);

	/**
	 * Called when a value of logo has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param oldValue the object representing the removed value
	 */
	public void logoRemoved(de.m0ep.uni.ma.rdfs.foaf.Person source, com.ibm.adtech.jastor.Thing oldValue);
		
	/**
	 * Called when a value of maker has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param newValue the object representing the new value
	 */	
	public void makerAdded(de.m0ep.uni.ma.rdfs.foaf.Person source, de.m0ep.uni.ma.rdfs.foaf.Agent newValue);

	/**
	 * Called when a value of maker has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param oldValue the object representing the removed value
	 */
	public void makerRemoved(de.m0ep.uni.ma.rdfs.foaf.Person source, de.m0ep.uni.ma.rdfs.foaf.Agent oldValue);
		
	/**
	 * Called when a value of givenname has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param newValue the object representing the new value
	 */	
	public void givennameAdded(de.m0ep.uni.ma.rdfs.foaf.Person source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of givenname has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param oldValue the object representing the removed value
	 */	
	public void givennameRemoved(de.m0ep.uni.ma.rdfs.foaf.Person source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of title has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param newValue the object representing the new value
	 */	
	public void titleAdded(de.m0ep.uni.ma.rdfs.foaf.Person source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of title has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param oldValue the object representing the removed value
	 */	
	public void titleRemoved(de.m0ep.uni.ma.rdfs.foaf.Person source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of givenName has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param newValue the object representing the new value
	 */	
	public void givenNameAdded(de.m0ep.uni.ma.rdfs.foaf.Person source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of givenName has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param oldValue the object representing the removed value
	 */	
	public void givenNameRemoved(de.m0ep.uni.ma.rdfs.foaf.Person source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of homepage has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param newValue the object representing the new value
	 */	
	public void homepageAdded(de.m0ep.uni.ma.rdfs.foaf.Person source, de.m0ep.uni.ma.rdfs.foaf.Document newValue);

	/**
	 * Called when a value of homepage has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param oldValue the object representing the removed value
	 */
	public void homepageRemoved(de.m0ep.uni.ma.rdfs.foaf.Person source, de.m0ep.uni.ma.rdfs.foaf.Document oldValue);
		
	/**
	 * Called when a value of theme has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param newValue the object representing the new value
	 */	
	public void themeAdded(de.m0ep.uni.ma.rdfs.foaf.Person source, com.ibm.adtech.jastor.Thing newValue);

	/**
	 * Called when a value of theme has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param oldValue the object representing the removed value
	 */
	public void themeRemoved(de.m0ep.uni.ma.rdfs.foaf.Person source, com.ibm.adtech.jastor.Thing oldValue);
		
	/**
	 * Called when a value of mbox has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param newValue the object representing the new value
	 */	
	public void mboxAdded(de.m0ep.uni.ma.rdfs.foaf.Person source, com.ibm.adtech.jastor.Thing newValue);

	/**
	 * Called when a value of mbox has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param oldValue the object representing the removed value
	 */
	public void mboxRemoved(de.m0ep.uni.ma.rdfs.foaf.Person source, com.ibm.adtech.jastor.Thing oldValue);
		
	/**
	 * Called when age has changed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 */
	public void ageChanged(de.m0ep.uni.ma.rdfs.foaf.Person source);

	/**
	 * Called when gender has changed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 */
	public void genderChanged(de.m0ep.uni.ma.rdfs.foaf.Person source);

	/**
	 * Called when a value of weblog has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param newValue the object representing the new value
	 */	
	public void weblogAdded(de.m0ep.uni.ma.rdfs.foaf.Person source, de.m0ep.uni.ma.rdfs.foaf.Document newValue);

	/**
	 * Called when a value of weblog has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param oldValue the object representing the removed value
	 */
	public void weblogRemoved(de.m0ep.uni.ma.rdfs.foaf.Person source, de.m0ep.uni.ma.rdfs.foaf.Document oldValue);
		
	/**
	 * Called when a value of account has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param newValue the object representing the new value
	 */	
	public void accountAdded(de.m0ep.uni.ma.rdfs.foaf.Person source, de.m0ep.uni.ma.rdfs.foaf.OnlineAccount newValue);

	/**
	 * Called when a value of account has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param oldValue the object representing the removed value
	 */
	public void accountRemoved(de.m0ep.uni.ma.rdfs.foaf.Person source, de.m0ep.uni.ma.rdfs.foaf.OnlineAccount oldValue);
		
	/**
	 * Called when a value of openid has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param newValue the object representing the new value
	 */	
	public void openidAdded(de.m0ep.uni.ma.rdfs.foaf.Person source, de.m0ep.uni.ma.rdfs.foaf.Document newValue);

	/**
	 * Called when a value of openid has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param oldValue the object representing the removed value
	 */
	public void openidRemoved(de.m0ep.uni.ma.rdfs.foaf.Person source, de.m0ep.uni.ma.rdfs.foaf.Document oldValue);
		
	/**
	 * Called when birthday has changed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 */
	public void birthdayChanged(de.m0ep.uni.ma.rdfs.foaf.Person source);

	/**
	 * Called when a value of yahooChatID has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param newValue the object representing the new value
	 */	
	public void yahooChatIDAdded(de.m0ep.uni.ma.rdfs.foaf.Person source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of yahooChatID has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param oldValue the object representing the removed value
	 */	
	public void yahooChatIDRemoved(de.m0ep.uni.ma.rdfs.foaf.Person source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of icqChatID has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param newValue the object representing the new value
	 */	
	public void icqChatIDAdded(de.m0ep.uni.ma.rdfs.foaf.Person source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of icqChatID has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param oldValue the object representing the removed value
	 */	
	public void icqChatIDRemoved(de.m0ep.uni.ma.rdfs.foaf.Person source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of holdsAccount has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param newValue the object representing the new value
	 */	
	public void holdsAccountAdded(de.m0ep.uni.ma.rdfs.foaf.Person source, de.m0ep.uni.ma.rdfs.foaf.OnlineAccount newValue);

	/**
	 * Called when a value of holdsAccount has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param oldValue the object representing the removed value
	 */
	public void holdsAccountRemoved(de.m0ep.uni.ma.rdfs.foaf.Person source, de.m0ep.uni.ma.rdfs.foaf.OnlineAccount oldValue);
		
	/**
	 * Called when a value of made has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param newValue the object representing the new value
	 */	
	public void madeAdded(de.m0ep.uni.ma.rdfs.foaf.Person source, com.ibm.adtech.jastor.Thing newValue);

	/**
	 * Called when a value of made has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param oldValue the object representing the removed value
	 */
	public void madeRemoved(de.m0ep.uni.ma.rdfs.foaf.Person source, com.ibm.adtech.jastor.Thing oldValue);
		
	/**
	 * Called when a value of mbox__sha1sum has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param newValue the object representing the new value
	 */	
	public void mbox__sha1sumAdded(de.m0ep.uni.ma.rdfs.foaf.Person source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of mbox__sha1sum has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param oldValue the object representing the removed value
	 */	
	public void mbox__sha1sumRemoved(de.m0ep.uni.ma.rdfs.foaf.Person source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of topic__interest has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param newValue the object representing the new value
	 */	
	public void topic__interestAdded(de.m0ep.uni.ma.rdfs.foaf.Person source, com.ibm.adtech.jastor.Thing newValue);

	/**
	 * Called when a value of topic__interest has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param oldValue the object representing the removed value
	 */
	public void topic__interestRemoved(de.m0ep.uni.ma.rdfs.foaf.Person source, com.ibm.adtech.jastor.Thing oldValue);
		
	/**
	 * Called when a value of interest has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param newValue the object representing the new value
	 */	
	public void interestAdded(de.m0ep.uni.ma.rdfs.foaf.Person source, de.m0ep.uni.ma.rdfs.foaf.Document newValue);

	/**
	 * Called when a value of interest has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param oldValue the object representing the removed value
	 */
	public void interestRemoved(de.m0ep.uni.ma.rdfs.foaf.Person source, de.m0ep.uni.ma.rdfs.foaf.Document oldValue);
		
	/**
	 * Called when a value of aimChatID has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param newValue the object representing the new value
	 */	
	public void aimChatIDAdded(de.m0ep.uni.ma.rdfs.foaf.Person source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of aimChatID has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param oldValue the object representing the removed value
	 */	
	public void aimChatIDRemoved(de.m0ep.uni.ma.rdfs.foaf.Person source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of skypeID has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param newValue the object representing the new value
	 */	
	public void skypeIDAdded(de.m0ep.uni.ma.rdfs.foaf.Person source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of skypeID has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param oldValue the object representing the removed value
	 */	
	public void skypeIDRemoved(de.m0ep.uni.ma.rdfs.foaf.Person source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of tipjar has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param newValue the object representing the new value
	 */	
	public void tipjarAdded(de.m0ep.uni.ma.rdfs.foaf.Person source, de.m0ep.uni.ma.rdfs.foaf.Document newValue);

	/**
	 * Called when a value of tipjar has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param oldValue the object representing the removed value
	 */
	public void tipjarRemoved(de.m0ep.uni.ma.rdfs.foaf.Person source, de.m0ep.uni.ma.rdfs.foaf.Document oldValue);
		
	/**
	 * Called when a value of status has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param newValue the object representing the new value
	 */	
	public void statusAdded(de.m0ep.uni.ma.rdfs.foaf.Person source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of status has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param oldValue the object representing the removed value
	 */	
	public void statusRemoved(de.m0ep.uni.ma.rdfs.foaf.Person source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of jabberID has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param newValue the object representing the new value
	 */	
	public void jabberIDAdded(de.m0ep.uni.ma.rdfs.foaf.Person source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of jabberID has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param oldValue the object representing the removed value
	 */	
	public void jabberIDRemoved(de.m0ep.uni.ma.rdfs.foaf.Person source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of msnChatID has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param newValue the object representing the new value
	 */	
	public void msnChatIDAdded(de.m0ep.uni.ma.rdfs.foaf.Person source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of msnChatID has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param oldValue the object representing the removed value
	 */	
	public void msnChatIDRemoved(de.m0ep.uni.ma.rdfs.foaf.Person source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of geekcode has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param newValue the object representing the new value
	 */	
	public void geekcodeAdded(de.m0ep.uni.ma.rdfs.foaf.Person source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of geekcode has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param oldValue the object representing the removed value
	 */	
	public void geekcodeRemoved(de.m0ep.uni.ma.rdfs.foaf.Person source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of publications has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param newValue the object representing the new value
	 */	
	public void publicationsAdded(de.m0ep.uni.ma.rdfs.foaf.Person source, de.m0ep.uni.ma.rdfs.foaf.Document newValue);

	/**
	 * Called when a value of publications has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param oldValue the object representing the removed value
	 */
	public void publicationsRemoved(de.m0ep.uni.ma.rdfs.foaf.Person source, de.m0ep.uni.ma.rdfs.foaf.Document oldValue);
		
	/**
	 * Called when a value of workInfoHomepage has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param newValue the object representing the new value
	 */	
	public void workInfoHomepageAdded(de.m0ep.uni.ma.rdfs.foaf.Person source, de.m0ep.uni.ma.rdfs.foaf.Document newValue);

	/**
	 * Called when a value of workInfoHomepage has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param oldValue the object representing the removed value
	 */
	public void workInfoHomepageRemoved(de.m0ep.uni.ma.rdfs.foaf.Person source, de.m0ep.uni.ma.rdfs.foaf.Document oldValue);
		
	/**
	 * Called when a value of surname has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param newValue the object representing the new value
	 */	
	public void surnameAdded(de.m0ep.uni.ma.rdfs.foaf.Person source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of surname has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param oldValue the object representing the removed value
	 */	
	public void surnameRemoved(de.m0ep.uni.ma.rdfs.foaf.Person source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of lastName has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param newValue the object representing the new value
	 */	
	public void lastNameAdded(de.m0ep.uni.ma.rdfs.foaf.Person source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of lastName has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param oldValue the object representing the removed value
	 */	
	public void lastNameRemoved(de.m0ep.uni.ma.rdfs.foaf.Person source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of schoolHomepage has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param newValue the object representing the new value
	 */	
	public void schoolHomepageAdded(de.m0ep.uni.ma.rdfs.foaf.Person source, de.m0ep.uni.ma.rdfs.foaf.Document newValue);

	/**
	 * Called when a value of schoolHomepage has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param oldValue the object representing the removed value
	 */
	public void schoolHomepageRemoved(de.m0ep.uni.ma.rdfs.foaf.Person source, de.m0ep.uni.ma.rdfs.foaf.Document oldValue);
		
	/**
	 * Called when a value of family__name has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param newValue the object representing the new value
	 */	
	public void family__nameAdded(de.m0ep.uni.ma.rdfs.foaf.Person source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of family__name has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param oldValue the object representing the removed value
	 */	
	public void family__nameRemoved(de.m0ep.uni.ma.rdfs.foaf.Person source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of plan has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param newValue the object representing the new value
	 */	
	public void planAdded(de.m0ep.uni.ma.rdfs.foaf.Person source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of plan has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param oldValue the object representing the removed value
	 */	
	public void planRemoved(de.m0ep.uni.ma.rdfs.foaf.Person source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of firstName has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param newValue the object representing the new value
	 */	
	public void firstNameAdded(de.m0ep.uni.ma.rdfs.foaf.Person source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of firstName has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param oldValue the object representing the removed value
	 */	
	public void firstNameRemoved(de.m0ep.uni.ma.rdfs.foaf.Person source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of img has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param newValue the object representing the new value
	 */	
	public void imgAdded(de.m0ep.uni.ma.rdfs.foaf.Person source, de.m0ep.uni.ma.rdfs.foaf.Image newValue);

	/**
	 * Called when a value of img has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param oldValue the object representing the removed value
	 */
	public void imgRemoved(de.m0ep.uni.ma.rdfs.foaf.Person source, de.m0ep.uni.ma.rdfs.foaf.Image oldValue);
		
	/**
	 * Called when a value of currentProject has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param newValue the object representing the new value
	 */	
	public void currentProjectAdded(de.m0ep.uni.ma.rdfs.foaf.Person source, com.ibm.adtech.jastor.Thing newValue);

	/**
	 * Called when a value of currentProject has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param oldValue the object representing the removed value
	 */
	public void currentProjectRemoved(de.m0ep.uni.ma.rdfs.foaf.Person source, com.ibm.adtech.jastor.Thing oldValue);
		
	/**
	 * Called when a value of knows has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param newValue the object representing the new value
	 */	
	public void knowsAdded(de.m0ep.uni.ma.rdfs.foaf.Person source, de.m0ep.uni.ma.rdfs.foaf.Person newValue);

	/**
	 * Called when a value of knows has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param oldValue the object representing the removed value
	 */
	public void knowsRemoved(de.m0ep.uni.ma.rdfs.foaf.Person source, de.m0ep.uni.ma.rdfs.foaf.Person oldValue);
		
	/**
	 * Called when a value of workplaceHomepage has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param newValue the object representing the new value
	 */	
	public void workplaceHomepageAdded(de.m0ep.uni.ma.rdfs.foaf.Person source, de.m0ep.uni.ma.rdfs.foaf.Document newValue);

	/**
	 * Called when a value of workplaceHomepage has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param oldValue the object representing the removed value
	 */
	public void workplaceHomepageRemoved(de.m0ep.uni.ma.rdfs.foaf.Person source, de.m0ep.uni.ma.rdfs.foaf.Document oldValue);
		
	/**
	 * Called when a value of pastProject has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param newValue the object representing the new value
	 */	
	public void pastProjectAdded(de.m0ep.uni.ma.rdfs.foaf.Person source, com.ibm.adtech.jastor.Thing newValue);

	/**
	 * Called when a value of pastProject has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param oldValue the object representing the removed value
	 */
	public void pastProjectRemoved(de.m0ep.uni.ma.rdfs.foaf.Person source, com.ibm.adtech.jastor.Thing oldValue);
		
	/**
	 * Called when a value of familyName has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param newValue the object representing the new value
	 */	
	public void familyNameAdded(de.m0ep.uni.ma.rdfs.foaf.Person source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of familyName has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param oldValue the object representing the removed value
	 */	
	public void familyNameRemoved(de.m0ep.uni.ma.rdfs.foaf.Person source, com.hp.hpl.jena.rdf.model.Literal oldValue);

	/**
	 * Called when a value of myersBriggs has been added
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param newValue the object representing the new value
	 */	
	public void myersBriggsAdded(de.m0ep.uni.ma.rdfs.foaf.Person source, com.hp.hpl.jena.rdf.model.Literal newValue);

	/**
	 * Called when a value of myersBriggs has been removed
	 * @param source the affected instance of de.m0ep.uni.ma.rdfs.foaf.Person
	 * @param oldValue the object representing the removed value
	 */	
	public void myersBriggsRemoved(de.m0ep.uni.ma.rdfs.foaf.Person source, com.hp.hpl.jena.rdf.model.Literal oldValue);

}