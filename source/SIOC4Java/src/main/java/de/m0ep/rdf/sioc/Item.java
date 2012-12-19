package de.m0ep.rdf.sioc;

import java.util.Set;

import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * Interface Item sioc:Item - An Item is something which can be in a Container.
 */
public interface Item extends SIOCBase {

    /**
     * sioc:about - Specifies that this Item is about a particular resource,
     * e.g. a Post describing a book, hotel, etc.
     * 
     * @return RDFNode
     */
    public Resource getAbout();

    /**
     * sioc:about - Specifies that this Item is about a particular resource,
     * e.g. a Post describing a book, hotel, etc.
     * 
     * @param node
     */
    public void setAbout( Resource resource );

    /**
     * sioc:addressed_to - Refers to who (e.g. a UserAccount, e-mail address,
     * etc.) a particular Item is addressed to.
     * 
     * @return RDFNode
     */
    public Resource getAddressedTo();

    /**
     * sioc:addressed_to - Refers to who (e.g. a UserAccount, e-mail address,
     * etc.) a particular Item is addressed to.
     * 
     * @param node
     */
    public void setAddressedTo( Resource resource );

    /**
     * sioc:attachment - The URI of a file attached to an Item.
     */
    public Set<Resource> getAttachments();

    /**
     * sioc:attachment - The URI of a file attached to an Item.
     * 
     * @param node
     */
    public void addAttachment( Resource resource );

    /**
     * sioc:attachment - The URI of a file attached to an Item.
     * 
     * @param node
     */
    public void removeAttachment( Resource resource );

    /**
     * sioc:content - The content of the Item in plain text format.
     * 
     * @return Literal
     */
    public Literal getContent();

    /**
     * sioc:content - The content of the Item in plain text format.
     * 
     * @param content
     */
    public void setContent( Literal content );

    /**
     * sioc:earlier_version - Links to a previous (older) revision of this Item
     * or Post.
     * 
     * @return de.m0ep.rdf.sioc.Item
     */
    public Item getEarlierVersion();

    /**
     * sioc:earlier_version - Links to a previous (older) revision of this Item
     * or Post.
     * 
     * @param item
     */
    public void setEarlierVersion( Item item );

    /**
     * sioc:embeds_knowledge - This links Items to embedded statements, facts
     * and structured content
     * 
     * @return Graph
     */
    public Graph getEmbededKnowledge();

    /**
     * sioc:embeds_knowledge - This links Items to embedded statements, facts
     * and structured content
     * 
     * @param graph
     */
    public void setEmbededKnowledge( Graph graph );

    /**
     * sioc:has_container - The Container to which this Item belongs.
     * 
     * @return de.m0ep.rdf.sioc.Container
     */
    public Container getContainer();

    /**
     * sioc:has_container - The Container to which this Item belongs.
     * 
     * @param container
     */
    public void setContainer( Container container );

    /**
     * sioc:has_discussion - The discussion that is related to this Item. The
     * discussion can be anything, for instance a sioc:Forum, a
     * sioct:WikiArticle or simply a foaf:Document.
     * 
     * @return RDFNode
     */
    public Resource getDiscussion();

    /**
     * sioc:has_discussion - The discussion that is related to this Item. The
     * discussion can be anything, for instance a sioc:Forum, a
     * sioct:WikiArticle or simply a foaf:Document.
     * 
     * @param discussion
     */
    public void setDiscussion( Resource resource );

    /**
     * sioc:has_modifier - A UserAccount that modified this Item.
     * 
     * @return de.m0ep.rdf.sioc.UserAccount
     */
    public UserAccount getModifier();

    /**
     * sioc:has_modifier - A UserAccount that modified this Item.
     * 
     * @param modifier
     */
    public void setModifier( UserAccount modifier );

    /**
     * sioc:has_reply - Points to an Item or Post that is a reply or response to
     * this Item or Post.
     */
    public Set<Item> getReplies();

    /**
     * sioc:has_reply - Points to an Item or Post that is a reply or response to
     * this Item or Post.
     * 
     * @param item
     */
    public void addReply( Item item );

    /**
     * sioc:has_reply - Points to an Item or Post that is a reply or response to
     * this Item or Post.
     * 
     * @param item
     */
    public void removeReply( Item item );

    /**
     * sioc:ip_address - The IP address used when creating this Item. This can
     * be associated with a creator. Some wiki articles list the IP addresses
     * for the creator or modifiers when the usernames are absent.
     * 
     * @return Literal
     */
    public Literal getIPAddress();

    /**
     * sioc:ip_address - The IP address used when creating this Item. This can
     * be associated with a creator. Some wiki articles list the IP addresses
     * for the creator or modifiers when the usernames are absent.
     * 
     * @param ip
     */
    public void setIPAddress( Literal ip );

    /**
     * sioc:later_version - Links to a later (newer) revision of this Item or
     * Post.
     * 
     * @return de.m0ep.rdf.sioc.Item
     */
    public Item getLaterVersion();

    /**
     * sioc:later_version - Links to a later (newer) revision of this Item or
     * Post.
     * 
     * @param item
     */
    public void setLaterVersion( Item item );

    /**
     * sioc:latest_version - Links to the latest revision of this Item or Post.
     * 
     * @return de.m0ep.rdf.sioc.Item
     */
    public Item getLatestVersion();

    /**
     * sioc:latest_version - Links to the latest revision of this Item or Post.
     * 
     * @param item
     */
    public void setLatestVersion( Item item );

    /**
     * sioc:next_by_date - Next Item or Post in a given Container sorted by
     * date.
     * 
     * @return de.m0ep.rdf.sioc.Item
     */
    public Item getNextByDate();

    /**
     * sioc:next_by_date - Next Item or Post in a given Container sorted by
     * date.
     * 
     * @param item
     */
    public void setNextByDate( Item item );

    /**
     * sioc:next_version - Links to the next revision of this Item or Post.
     * 
     * @return de.m0ep.rdf.sioc.Item
     */
    public Item getNextVersion();

    /**
     * sioc:next_version - Links to the next revision of this Item or Post.
     * 
     * @param item
     */
    public void setNextVersion( Item item );

    /**
     * sioc:previous_by_date - Previous Item or Post in a given Container sorted
     * by date.
     * 
     * @return de.m0ep.rdf.sioc.Item
     */
    public Item getPreviousByDate();

    /**
     * sioc:previous_by_date - Previous Item or Post in a given Container sorted
     * by date.
     * 
     * @param item
     */
    public void setPreviousByDate( Item item );

    /**
     * sioc:previous_version - Links to the previous revision of this Item or
     * Post.
     * 
     * @return de.m0ep.rdf.sioc.Item
     */
    public Item getPreviousVersion();

    /**
     * sioc:previous_version - Links to the previous revision of this Item or
     * Post.
     * 
     * @param item
     */
    public void setPreviousVersion( Item item );

    /**
     * sioc:reply_of - Links to an Item or Post which this Item or Post is a
     * reply to.
     * 
     * @return de.m0ep.rdf.sioc.Item
     */
    public Item getReplyOf();

    /**
     * sioc:reply_of - Links to an Item or Post which this Item or Post is a
     * reply to.
     * 
     * @param item
     */
    public void setReplyOf( Item item );

    /**
     * sioc:sibling - An Item may have a sibling or a twin that exists in a
     * different Container, but the siblings may differ in some small way (for
     * example, language, category, etc.). The sibling of this Item should be
     * self-describing (that is, it should contain all available information).
     * 
     * @return de.m0ep.rdf.sioc.Item
     */
    public Item getSibling();

    /**
     * sioc:sibling - An Item may have a sibling or a twin that exists in a
     * different Container, but the siblings may differ in some small way (for
     * example, language, category, etc.). The sibling of this Item should be
     * self-describing (that is, it should contain all available information).
     * 
     * @param item
     */
    public void setSibling( Item item );
}
