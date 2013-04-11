package org.rdfs.sioc;

import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.model.node.impl.URIImpl;

/**
 * Vocabulary File. Created by org.ontoware.rdf2go.util.VocabularyWriter on Sun
 * Dec 23 16:39:20 CET 2012 input file: src/main/resources/ontologies/sioc.rdf
 * namespace: http://rdfs.org/sioc/ns#
 */
public interface SIOC {
    public static final URI NS_SIOC = new URIImpl("http://rdfs.org/sioc/ns#",
	    false);

    /**
     * Label: Community@en Comment: Community is a high-level concept that
     * defines an online community and what it consists of.@en
     */
    public static final URI Community = new URIImpl(
	    "http://rdfs.org/sioc/ns#Community", false);

    /**
     * Label: Container@en Comment: An area in which content Items are
     * contained.@en
     */
    public static final URI Container = new URIImpl(
	    "http://rdfs.org/sioc/ns#Container", false);

    /**
     * Label: Forum@en Comment: A discussion area on which Posts or entries are
     * made.@en
     */
    public static final URI Forum = new URIImpl(
	    "http://rdfs.org/sioc/ns#Forum", false);

    /**
     * Label: Item@en Comment: An Item is something which can be in a Container.@en
     */
    public static final URI Item = new URIImpl("http://rdfs.org/sioc/ns#Item",
	    false);

    /**
     * Label: Post@en Comment: An article or message that can be posted to a
     * Forum.@en
     */
    public static final URI Post = new URIImpl("http://rdfs.org/sioc/ns#Post",
	    false);

    /**
     * Label: Role@en Comment: A Role is a function of a UserAccount within a
     * scope of a particular Forum, Site, etc.@en
     */
    public static final URI Role = new URIImpl("http://rdfs.org/sioc/ns#Role",
	    false);

    /**
     * Label: Space@en Comment: A Space is a place where data resides, e.g. on a
     * website, desktop, fileshare, etc.@en
     */
    public static final URI Space = new URIImpl(
	    "http://rdfs.org/sioc/ns#Space", false);

    /**
     * Label: Site@en Comment: A Site can be the location of an online community
     * or set of communities, with UserAccounts and Usergroups creating Items in
     * a set of Containers. It can be thought of as a web-accessible data Space.@en
     */
    public static final URI Site = new URIImpl("http://rdfs.org/sioc/ns#Site",
	    false);

    /**
     * Label: Thread@en Comment: A container for a series of threaded discussion
     * Posts or Items.@en
     */
    public static final URI Thread = new URIImpl(
	    "http://rdfs.org/sioc/ns#Thread", false);

    /**
     * Label: User Account@en Comment: A user account in an online community
     * site.@en
     */
    public static final URI UserAccount = new URIImpl(
	    "http://rdfs.org/sioc/ns#UserAccount", false);

    /**
     * Label: Usergroup@en Comment: A set of UserAccounts whose owners have a
     * common purpose or interest. Can be used for access control purposes.@en
     */
    public static final URI Usergroup = new URIImpl(
	    "http://rdfs.org/sioc/ns#Usergroup", false);

    /**
     * Label: content@en Comment: The content of the Item in plain text format.@en
     * Comment: http://rdfs.org/sioc/ns#Item Range:
     * http://www.w3.org/2000/01/rdf-schema#Literal
     */
    public static final URI content = new URIImpl(
	    "http://rdfs.org/sioc/ns#content", false);

    /**
     * Label: email sha1@en Comment: An electronic mail address of the
     * UserAccount, encoded using SHA1.@en Comment:
     * http://rdfs.org/sioc/ns#UserAccount Range:
     * http://www.w3.org/2000/01/rdf-schema#Literal
     */
    public static final URI email_sha1 = new URIImpl(
	    "http://rdfs.org/sioc/ns#email_sha1", false);

    /**
     * Label: id@en Comment: An identifier of a SIOC concept instance. For
     * example, a user ID. Must be unique for instances of each type of SIOC
     * concept within the same site.@en Range:
     * http://www.w3.org/2000/01/rdf-schema#Literal
     */
    public static final URI id = new URIImpl("http://rdfs.org/sioc/ns#id",
	    false);

    /**
     * Label: ip address@en Comment: The IP address used when creating this
     * Item. This can be associated with a creator. Some wiki articles list the
     * IP addresses for the creator or modifiers when the usernames are absent.@en
     * Comment: http://rdfs.org/sioc/ns#Item Range:
     * http://www.w3.org/2000/01/rdf-schema#Literal
     */
    public static final URI ip_address = new URIImpl(
	    "http://rdfs.org/sioc/ns#ip_address", false);

    /**
     * Label: last activity date@en Comment: The date and time of the last
     * activity associated with a SIOC concept instance, and expressed in ISO
     * 8601 format. This could be due to a reply Post or Comment, a modification
     * to an Item, etc.@en Range: http://www.w3.org/2000/01/rdf-schema#Literal
     */
    public static final URI last_activity_date = new URIImpl(
	    "http://rdfs.org/sioc/ns#last_activity_date", false);

    /**
     * Label: last item date@en Comment: The date and time of the last Post (or
     * Item) in a Forum (or a Container), in ISO 8601 format.@en Comment:
     * http://rdfs.org/sioc/ns#Container Range:
     * http://www.w3.org/2000/01/rdf-schema#Literal
     */
    public static final URI last_item_date = new URIImpl(
	    "http://rdfs.org/sioc/ns#last_item_date", false);

    /**
     * Label: last reply date@en Comment: The date and time of the last reply
     * Post or Comment, which could be associated with a starter Item or Post or
     * with a Thread, and expressed in ISO 8601 format.@en Range:
     * http://www.w3.org/2000/01/rdf-schema#Literal
     */
    public static final URI last_reply_date = new URIImpl(
	    "http://rdfs.org/sioc/ns#last_reply_date", false);

    /**
     * Label: name@en Comment: The name of a SIOC concept instance, e.g. a
     * username for a UserAccount, group name for a Usergroup, etc.@en Range:
     * http://www.w3.org/2000/01/rdf-schema#Literal
     */
    public static final URI name = new URIImpl("http://rdfs.org/sioc/ns#name",
	    false);

    /**
     * Label: note@en Comment: A note associated with this resource, for
     * example, if it has been edited by a UserAccount.@en Range:
     * http://www.w3.org/2000/01/rdf-schema#Literal
     */
    public static final URI note = new URIImpl("http://rdfs.org/sioc/ns#note",
	    false);

    /**
     * Label: num authors@en Comment: The number of unique authors (UserAccounts
     * and unregistered posters) who have contributed to this Item, Thread,
     * Post, etc.@en Range: http://www.w3.org/2001/XMLSchema#nonNegativeInteger
     */
    public static final URI num_authors = new URIImpl(
	    "http://rdfs.org/sioc/ns#num_authors", false);

    /**
     * Label: num items@en Comment: The number of Posts (or Items) in a Forum
     * (or a Container).@en Comment: http://rdfs.org/sioc/ns#Container Range:
     * http://www.w3.org/2001/XMLSchema#nonNegativeInteger
     */
    public static final URI num_items = new URIImpl(
	    "http://rdfs.org/sioc/ns#num_items", false);

    /**
     * Label: num replies@en Comment: The number of replies that this Item,
     * Thread, Post, etc. has. Useful for when the reply structure is absent.@en
     * Range: http://www.w3.org/2001/XMLSchema#nonNegativeInteger
     */
    public static final URI num_replies = new URIImpl(
	    "http://rdfs.org/sioc/ns#num_replies", false);

    /**
     * Label: num threads@en Comment: The number of Threads (AKA discussion
     * topics) in a Forum.@en Comment: http://rdfs.org/sioc/ns#Forum Range:
     * http://www.w3.org/2001/XMLSchema#nonNegativeInteger
     */
    public static final URI num_threads = new URIImpl(
	    "http://rdfs.org/sioc/ns#num_threads", false);

    /**
     * Label: num views@en Comment: The number of times this Item, Thread,
     * UserAccount profile, etc. has been viewed.@en Range:
     * http://www.w3.org/2001/XMLSchema#nonNegativeInteger
     */
    public static final URI num_views = new URIImpl(
	    "http://rdfs.org/sioc/ns#num_views", false);

    /**
     * Label: about@en Comment: Specifies that this Item is about a particular
     * resource, e.g. a Post describing a book, hotel, etc.@en Comment:
     * http://rdfs.org/sioc/ns#Item
     */
    public static final URI about = new URIImpl(
	    "http://rdfs.org/sioc/ns#about", false);

    /**
     * Label: account of@en Comment: Refers to the foaf:Agent or foaf:Person who
     * owns this sioc:UserAccount.@en Comment:
     * http://rdfs.org/sioc/ns#UserAccount Range:
     * http://xmlns.com/foaf/0.1/Agent
     */
    public static final URI account_of = new URIImpl(
	    "http://rdfs.org/sioc/ns#account_of", false);

    /**
     * Label: addressed to@en Comment: Refers to who (e.g. a UserAccount, e-mail
     * address, etc.) a particular Item is addressed to.@en Comment:
     * http://rdfs.org/sioc/ns#Item
     */
    public static final URI addressed_to = new URIImpl(
	    "http://rdfs.org/sioc/ns#addressed_to", false);

    /**
     * Label: administrator of@en Comment: A Site that the UserAccount is an
     * administrator of.@en Comment: http://rdfs.org/sioc/ns#UserAccount Range:
     * http://rdfs.org/sioc/ns#Site
     */
    public static final URI administrator_of = new URIImpl(
	    "http://rdfs.org/sioc/ns#administrator_of", false);

    /**
     * Label: attachment@en Comment: The URI of a file attached to an Item.@en
     * Comment: http://rdfs.org/sioc/ns#Item
     */
    public static final URI attachment = new URIImpl(
	    "http://rdfs.org/sioc/ns#attachment", false);

    /**
     * Label: avatar@en Comment: An image or depiction used to represent this
     * UserAccount.@en Comment: http://rdfs.org/sioc/ns#UserAccount
     */
    public static final URI avatar = new URIImpl(
	    "http://rdfs.org/sioc/ns#avatar", false);

    /**
     * Label: container of@en Comment: An Item that this Container contains.@en
     * Comment: http://rdfs.org/sioc/ns#Container Range:
     * http://rdfs.org/sioc/ns#Item
     */
    public static final URI container_of = new URIImpl(
	    "http://rdfs.org/sioc/ns#container_of", false);

    /**
     * Label: creator of@en Comment: A resource that the UserAccount is a
     * creator of.@en Comment: http://rdfs.org/sioc/ns#UserAccount
     */
    public static final URI creator_of = new URIImpl(
	    "http://rdfs.org/sioc/ns#creator_of", false);

    /**
     * Label: email@en Comment: An electronic mail address of the UserAccount.@en
     * Comment: http://rdfs.org/sioc/ns#UserAccount
     */
    public static final URI email = new URIImpl(
	    "http://rdfs.org/sioc/ns#email", false);

    /**
     * Label: embeds knowledge@en Comment: This links Items to embedded
     * statements, facts and structured content.@en Comment:
     * http://rdfs.org/sioc/ns#Item Range:
     * http://www.w3.org/2004/03/trix/rdfg-1/Graph
     */
    public static final URI embeds_knowledge = new URIImpl(
	    "http://rdfs.org/sioc/ns#embeds_knowledge", false);

    /**
     * Label: feed@en Comment: A feed (e.g. RSS, Atom, etc.) pertaining to this
     * resource (e.g. for a Forum, Site, UserAccount, etc.).@en
     */
    public static final URI feed = new URIImpl("http://rdfs.org/sioc/ns#feed",
	    false);

    /**
     * Label: follows@en Comment: Indicates that one UserAccount follows another
     * UserAccount (e.g. for microblog posts or other content item updates).@en
     * Comment: http://rdfs.org/sioc/ns#UserAccount Range:
     * http://rdfs.org/sioc/ns#UserAccount
     */
    public static final URI follows = new URIImpl(
	    "http://rdfs.org/sioc/ns#follows", false);

    /**
     * Label: function of@en Comment: A UserAccount that has this Role.@en
     * Comment: http://rdfs.org/sioc/ns#Role
     */
    public static final URI function_of = new URIImpl(
	    "http://rdfs.org/sioc/ns#function_of", false);

    /**
     * Label: has administrator@en Comment: A UserAccount that is an
     * administrator of this Site.@en Comment: http://rdfs.org/sioc/ns#Site
     * Range: http://rdfs.org/sioc/ns#UserAccount
     */
    public static final URI has_administrator = new URIImpl(
	    "http://rdfs.org/sioc/ns#has_administrator", false);

    /**
     * Label: has container@en Comment: The Container to which this Item
     * belongs.@en Comment: http://rdfs.org/sioc/ns#Item Range:
     * http://rdfs.org/sioc/ns#Container
     */
    public static final URI has_container = new URIImpl(
	    "http://rdfs.org/sioc/ns#has_container", false);

    /**
     * Label: has creator@en Comment: This is the UserAccount that made this
     * resource.@en Range: http://rdfs.org/sioc/ns#UserAccount
     */
    public static final URI has_creator = new URIImpl(
	    "http://rdfs.org/sioc/ns#has_creator", false);

    /**
     * Label: has discussion@en Comment: The discussion that is related to this
     * Item.@en Comment: http://rdfs.org/sioc/ns#Item
     */
    public static final URI has_discussion = new URIImpl(
	    "http://rdfs.org/sioc/ns#has_discussion", false);

    /**
     * Label: has function@en Comment: A Role that this UserAccount has.@en
     * Range: http://rdfs.org/sioc/ns#Role
     */
    public static final URI has_function = new URIImpl(
	    "http://rdfs.org/sioc/ns#has_function", false);

    /**
     * Label: has host@en Comment: The Site that hosts this Forum.@en Comment:
     * http://rdfs.org/sioc/ns#Forum Range: http://rdfs.org/sioc/ns#Site
     */
    public static final URI has_host = new URIImpl(
	    "http://rdfs.org/sioc/ns#has_host", false);

    /**
     * Label: has member@en Comment: A UserAccount that is a member of this
     * Usergroup.@en Comment: http://rdfs.org/sioc/ns#Usergroup Range:
     * http://rdfs.org/sioc/ns#UserAccount
     */
    public static final URI has_member = new URIImpl(
	    "http://rdfs.org/sioc/ns#has_member", false);

    /**
     * Label: has moderator@en Comment: A UserAccount that is a moderator of
     * this Forum.@en Comment: http://rdfs.org/sioc/ns#Forum Range:
     * http://rdfs.org/sioc/ns#UserAccount
     */
    public static final URI has_moderator = new URIImpl(
	    "http://rdfs.org/sioc/ns#has_moderator", false);

    /**
     * Label: has modifier@en Comment: A UserAccount that modified this Item.@en
     * Comment: http://rdfs.org/sioc/ns#Item Range:
     * http://rdfs.org/sioc/ns#UserAccount
     */
    public static final URI has_modifier = new URIImpl(
	    "http://rdfs.org/sioc/ns#has_modifier", false);

    /**
     * Label: has owner@en Comment: A UserAccount that this resource is owned
     * by.@en Range: http://rdfs.org/sioc/ns#UserAccount
     */
    public static final URI has_owner = new URIImpl(
	    "http://rdfs.org/sioc/ns#has_owner", false);

    /**
     * Label: has parent@en Comment: A Container or Forum that this Container or
     * Forum is a child of.@en Comment: http://rdfs.org/sioc/ns#Container Range:
     * http://rdfs.org/sioc/ns#Container
     */
    public static final URI has_parent = new URIImpl(
	    "http://rdfs.org/sioc/ns#has_parent", false);

    /**
     * Label: has reply@en Comment: Points to an Item or Post that is a reply or
     * response to this Item or Post.@en Comment: http://rdfs.org/sioc/ns#Item
     * Range: http://rdfs.org/sioc/ns#Item
     */
    public static final URI has_reply = new URIImpl(
	    "http://rdfs.org/sioc/ns#has_reply", false);

    /**
     * Label: has scope@en Comment: A resource that this Role applies to.@en
     * Comment: http://rdfs.org/sioc/ns#Role
     */
    public static final URI has_scope = new URIImpl(
	    "http://rdfs.org/sioc/ns#has_scope", false);

    /**
     * Label: has space@en Comment: A data Space which this resource is a part
     * of.@en Range: http://rdfs.org/sioc/ns#Space
     */
    public static final URI has_space = new URIImpl(
	    "http://rdfs.org/sioc/ns#has_space", false);

    /**
     * Label: has subscriber@en Comment: A UserAccount that is subscribed to
     * this Container.@en Comment: http://rdfs.org/sioc/ns#Container Range:
     * http://rdfs.org/sioc/ns#UserAccount
     */
    public static final URI has_subscriber = new URIImpl(
	    "http://rdfs.org/sioc/ns#has_subscriber", false);

    /**
     * Label: has usergroup@en Comment: Points to a Usergroup that has certain
     * access to this Space.@en Comment: http://rdfs.org/sioc/ns#Space Range:
     * http://rdfs.org/sioc/ns#Usergroup
     */
    public static final URI has_usergroup = new URIImpl(
	    "http://rdfs.org/sioc/ns#has_usergroup", false);

    /**
     * Label: host of@en Comment: A Forum that is hosted on this Site.@en
     * Comment: http://rdfs.org/sioc/ns#Site Range:
     * http://rdfs.org/sioc/ns#Forum
     */
    public static final URI host_of = new URIImpl(
	    "http://rdfs.org/sioc/ns#host_of", false);

    /**
     * Label: latest version@en Comment: Links to the latest revision of this
     * Item or Post.@en Comment: http://rdfs.org/sioc/ns#Item Range:
     * http://rdfs.org/sioc/ns#Item
     */
    public static final URI latest_version = new URIImpl(
	    "http://rdfs.org/sioc/ns#latest_version", false);

    /**
     * Label: link@en Comment: A URI of a document which contains this SIOC
     * object.@en
     */
    public static final URI link = new URIImpl("http://rdfs.org/sioc/ns#link",
	    false);

    /**
     * Label: links to@en Comment: Links extracted from hyperlinks within a SIOC
     * concept, e.g. Post or Site.@en
     */
    public static final URI links_to = new URIImpl(
	    "http://rdfs.org/sioc/ns#links_to", false);

    /**
     * Label: member of@en Comment: A Usergroup that this UserAccount is a
     * member of.@en Comment: http://rdfs.org/sioc/ns#UserAccount Range:
     * http://rdfs.org/sioc/ns#Usergroup
     */
    public static final URI member_of = new URIImpl(
	    "http://rdfs.org/sioc/ns#member_of", false);

    /**
     * Label: moderator of@en Comment: A Forum that a UserAccount is a moderator
     * of.@en Comment: http://rdfs.org/sioc/ns#UserAccount Range:
     * http://rdfs.org/sioc/ns#Forum
     */
    public static final URI moderator_of = new URIImpl(
	    "http://rdfs.org/sioc/ns#moderator_of", false);

    /**
     * Label: modifier of@en Comment: An Item that this UserAccount has
     * modified.@en Comment: http://rdfs.org/sioc/ns#UserAccount Range:
     * http://rdfs.org/sioc/ns#Item
     */
    public static final URI modifier_of = new URIImpl(
	    "http://rdfs.org/sioc/ns#modifier_of", false);

    /**
     * Label: next by date@en Comment: Next Item or Post in a given Container
     * sorted by date.@en Comment: http://rdfs.org/sioc/ns#Item Range:
     * http://rdfs.org/sioc/ns#Item
     */
    public static final URI next_by_date = new URIImpl(
	    "http://rdfs.org/sioc/ns#next_by_date", false);

    /**
     * Label: next version@en Comment: Links to the next revision of this Item
     * or Post.@en Comment: http://rdfs.org/sioc/ns#Item Range:
     * http://rdfs.org/sioc/ns#Item
     */
    public static final URI next_version = new URIImpl(
	    "http://rdfs.org/sioc/ns#next_version", false);

    /**
     * Label: owner of@en Comment: A resource owned by a particular UserAccount,
     * for example, a weblog or image gallery.@en Comment:
     * http://rdfs.org/sioc/ns#UserAccount
     */
    public static final URI owner_of = new URIImpl(
	    "http://rdfs.org/sioc/ns#owner_of", false);

    /**
     * Label: parent of@en Comment: A child Container or Forum that this
     * Container or Forum is a parent of.@en Comment:
     * http://rdfs.org/sioc/ns#Container Range:
     * http://rdfs.org/sioc/ns#Container
     */
    public static final URI parent_of = new URIImpl(
	    "http://rdfs.org/sioc/ns#parent_of", false);

    /**
     * Label: previous by date@en Comment: Previous Item or Post in a given
     * Container sorted by date.@en Comment: http://rdfs.org/sioc/ns#Item Range:
     * http://rdfs.org/sioc/ns#Item
     */
    public static final URI previous_by_date = new URIImpl(
	    "http://rdfs.org/sioc/ns#previous_by_date", false);

    /**
     * Label: previous version@en Comment: Links to the previous revision of
     * this Item or Post.@en Comment: http://rdfs.org/sioc/ns#Item Range:
     * http://rdfs.org/sioc/ns#Item
     */
    public static final URI previous_version = new URIImpl(
	    "http://rdfs.org/sioc/ns#previous_version", false);

    /**
     * Label: related to@en Comment: Related Posts for this Post, perhaps
     * determined implicitly from topics or references.@en
     */
    public static final URI related_to = new URIImpl(
	    "http://rdfs.org/sioc/ns#related_to", false);

    /**
     * Label: reply of@en Comment: Links to an Item or Post which this Item or
     * Post is a reply to.@en Comment: http://rdfs.org/sioc/ns#Item Range:
     * http://rdfs.org/sioc/ns#Item
     */
    public static final URI reply_of = new URIImpl(
	    "http://rdfs.org/sioc/ns#reply_of", false);

    /**
     * Label: scope of@en Comment: A Role that has a scope of this resource.@en
     * Range: http://rdfs.org/sioc/ns#Role
     */
    public static final URI scope_of = new URIImpl(
	    "http://rdfs.org/sioc/ns#scope_of", false);

    /**
     * Label: space of@en Comment: A resource which belongs to this data Space.@en
     * Comment: http://rdfs.org/sioc/ns#Space
     */
    public static final URI space_of = new URIImpl(
	    "http://rdfs.org/sioc/ns#space_of", false);

    /**
     * Label: subscriber of@en Comment: A Container that a UserAccount is
     * subscribed to.@en Comment: http://rdfs.org/sioc/ns#UserAccount Range:
     * http://rdfs.org/sioc/ns#Container
     */
    public static final URI subscriber_of = new URIImpl(
	    "http://rdfs.org/sioc/ns#subscriber_of", false);

    /**
     * Label: topic@en Comment: A topic of interest, linking to the appropriate
     * URI, e.g. in the Open Directory Project or of a SKOS category.@en
     */
    public static final URI topic = new URIImpl(
	    "http://rdfs.org/sioc/ns#topic", false);

    /**
     * Label: usergroup of@en Comment: A Space that the Usergroup has access to.@en
     * Comment: http://rdfs.org/sioc/ns#Usergroup Range:
     * http://rdfs.org/sioc/ns#Space
     */
    public static final URI usergroup_of = new URIImpl(
	    "http://rdfs.org/sioc/ns#usergroup_of", false);

}
