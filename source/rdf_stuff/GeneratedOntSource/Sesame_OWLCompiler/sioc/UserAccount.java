package sioc;

import foaf.Agent;
import foaf.Image;
import foaf.OnlineAccount;
import java.lang.Deprecated;
import java.lang.Object;
import java.util.Set;
import org.openrdf.annotations.Iri;
import owl.equivalentClass;
import rdfs.subClassOf;
import rdfs.subPropertyOf;

/** A user account in an online community site. */
@equivalentClass({"http://rdfs.org/sioc/ns#User"})
@subClassOf({"http://xmlns.com/foaf/0.1/OnlineAccount"})
@Iri("http://rdfs.org/sioc/ns#UserAccount")
public interface UserAccount extends OnlineAccount {
	/** Refers to the foaf:Agent or foaf:Person who owns this sioc:UserAccount. */
	@Iri("http://rdfs.org/sioc/ns#account_of")
	Set<Agent> getSiocAccount_of();
	/** Refers to the foaf:Agent or foaf:Person who owns this sioc:UserAccount. */
	@Iri("http://rdfs.org/sioc/ns#account_of")
	void setSiocAccount_of(Set<? extends Agent> siocAccount_of);

	/** A Site that the UserAccount is an administrator of. */
	@Iri("http://rdfs.org/sioc/ns#administrator_of")
	Set<Site> getSiocAdministrator_of();
	/** A Site that the UserAccount is an administrator of. */
	@Iri("http://rdfs.org/sioc/ns#administrator_of")
	void setSiocAdministrator_of(Set<? extends Site> siocAdministrator_of);

	/** An image or depiction used to represent this UserAccount. */
	@subPropertyOf({"http://xmlns.com/foaf/0.1/depiction"})
	@Iri("http://rdfs.org/sioc/ns#avatar")
	Set<Image> getSiocAvatars();
	/** An image or depiction used to represent this UserAccount. */
	@subPropertyOf({"http://xmlns.com/foaf/0.1/depiction"})
	@Iri("http://rdfs.org/sioc/ns#avatar")
	void setSiocAvatars(Set<? extends Image> siocAvatars);

	/** A resource that the UserAccount is a creator of. */
	@Iri("http://rdfs.org/sioc/ns#creator_of")
	Set<Object> getSiocCreator_of();
	/** A resource that the UserAccount is a creator of. */
	@Iri("http://rdfs.org/sioc/ns#creator_of")
	void setSiocCreator_of(Set<?> siocCreator_of);

	/** An electronic mail address of the UserAccount. */
	@Iri("http://rdfs.org/sioc/ns#email")
	Set<Object> getSiocEmails();
	/** An electronic mail address of the UserAccount. */
	@Iri("http://rdfs.org/sioc/ns#email")
	void setSiocEmails(Set<?> siocEmails);

	/** An electronic mail address of the UserAccount, encoded using SHA1. */
	@Iri("http://rdfs.org/sioc/ns#email_sha1")
	Set<Object> getSiocEmail_sha1();
	/** An electronic mail address of the UserAccount, encoded using SHA1. */
	@Iri("http://rdfs.org/sioc/ns#email_sha1")
	void setSiocEmail_sha1(Set<?> siocEmail_sha1);

	/** 
	 * First (real) name of this User. Synonyms include given name or christian name.
	 * @version This property is deprecated. Use foaf:name or foaf:firstName from the FOAF vocabulary instead.
	 */
	@Deprecated
	@Iri("http://rdfs.org/sioc/ns#first_name")
	Set<Object> getSiocFirst_names();
	/** 
	 * First (real) name of this User. Synonyms include given name or christian name.
	 * @version This property is deprecated. Use foaf:name or foaf:firstName from the FOAF vocabulary instead.
	 */
	@Deprecated
	@Iri("http://rdfs.org/sioc/ns#first_name")
	void setSiocFirst_names(Set<?> siocFirst_names);

	/** Indicates that one UserAccount follows another UserAccount (e.g. for microblog posts or other content item updates). */
	@Iri("http://rdfs.org/sioc/ns#follows")
	Set<UserAccount> getSiocFollows();
	/** Indicates that one UserAccount follows another UserAccount (e.g. for microblog posts or other content item updates). */
	@Iri("http://rdfs.org/sioc/ns#follows")
	void setSiocFollows(Set<? extends UserAccount> siocFollows);

	/** 
	 * Last (real) name of this user. Synonyms include surname or family name.
	 * @version This property is deprecated. Use foaf:name or foaf:surname from the FOAF vocabulary instead.
	 */
	@Deprecated
	@Iri("http://rdfs.org/sioc/ns#last_name")
	Set<Object> getSiocLast_names();
	/** 
	 * Last (real) name of this user. Synonyms include surname or family name.
	 * @version This property is deprecated. Use foaf:name or foaf:surname from the FOAF vocabulary instead.
	 */
	@Deprecated
	@Iri("http://rdfs.org/sioc/ns#last_name")
	void setSiocLast_names(Set<?> siocLast_names);

	/** A Usergroup that this UserAccount is a member of. */
	@Iri("http://rdfs.org/sioc/ns#member_of")
	Set<Usergroup> getSiocMember_of();
	/** A Usergroup that this UserAccount is a member of. */
	@Iri("http://rdfs.org/sioc/ns#member_of")
	void setSiocMember_of(Set<? extends Usergroup> siocMember_of);

	/** A Forum that a UserAccount is a moderator of. */
	@Iri("http://rdfs.org/sioc/ns#moderator_of")
	Set<Forum> getSiocModerator_of();
	/** A Forum that a UserAccount is a moderator of. */
	@Iri("http://rdfs.org/sioc/ns#moderator_of")
	void setSiocModerator_of(Set<? extends Forum> siocModerator_of);

	/** An Item that this UserAccount has modified. */
	@Iri("http://rdfs.org/sioc/ns#modifier_of")
	Set<Item> getSiocModifier_of();
	/** An Item that this UserAccount has modified. */
	@Iri("http://rdfs.org/sioc/ns#modifier_of")
	void setSiocModifier_of(Set<? extends Item> siocModifier_of);

	/** A resource owned by a particular UserAccount, for example, a weblog or image gallery. */
	@Iri("http://rdfs.org/sioc/ns#owner_of")
	Set<Object> getSiocOwner_of();
	/** A resource owned by a particular UserAccount, for example, a weblog or image gallery. */
	@Iri("http://rdfs.org/sioc/ns#owner_of")
	void setSiocOwner_of(Set<?> siocOwner_of);

	/** 
	 * A Container that a UserAccount is subscribed to.
	 * @see rdfs.Resource#getSiocFeed
	 */
	@Iri("http://rdfs.org/sioc/ns#subscriber_of")
	Set<Container> getSiocSubscriber_of();
	/** 
	 * A Container that a UserAccount is subscribed to.
	 * @see rdfs.Resource#getSiocFeed_1
	 */
	@Iri("http://rdfs.org/sioc/ns#subscriber_of")
	void setSiocSubscriber_of(Set<? extends Container> siocSubscriber_of);

}
