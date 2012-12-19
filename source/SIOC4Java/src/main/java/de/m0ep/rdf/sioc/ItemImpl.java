package de.m0ep.rdf.sioc;

import java.util.Set;

import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;

import de.m0ep.rdf.sioc.vocabulary.SIOC;

public class ItemImpl extends SIOCBaseImpl implements Item {

    protected ItemImpl( final Resource resource, final Model model ) {
        super( resource, model );
    }

    public static ItemImpl get( final Resource resource, final Model model ) {
        return new ItemImpl( resource, model );
    }

    public static ItemImpl create( final String uri, final Model model ) {
        return create( model.createResource( uri ), model );
    }

    public static ItemImpl create( final Resource resource, final Model model ) {
        ItemImpl impl = new ItemImpl( resource, model );

        if( !impl.isRDFType( SIOC.Item ) )
            impl.resource().addProperty( RDF.type, SIOC.Item );

        return impl;
    }

    public Resource getAbout() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setAbout( Resource resource ) {
        // TODO Auto-generated method stub

    }

    public Resource getAddressedTo() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setAddressedTo( Resource resource ) {
        // TODO Auto-generated method stub

    }

    public Set<Resource> getAttachments() {
        // TODO Auto-generated method stub
        return null;
    }

    public void addAttachment( Resource resource ) {
        // TODO Auto-generated method stub

    }

    public void removeAttachment( Resource resource ) {
        // TODO Auto-generated method stub

    }

    public Literal getContent() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setContent( Literal content ) {
        // TODO Auto-generated method stub

    }

    public Item getEarlierVersion() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setEarlierVersion( Item item ) {
        // TODO Auto-generated method stub

    }

    public Graph getEmbededKnowledge() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setEmbededKnowledge( Graph graph ) {
        // TODO Auto-generated method stub

    }

    public Container getContainer() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setContainer( Container container ) {
        // TODO Auto-generated method stub

    }

    public Resource getDiscussion() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setDiscussion( Resource resource ) {
        // TODO Auto-generated method stub

    }

    public UserAccount getModifier() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setModifier( UserAccount modifier ) {
        // TODO Auto-generated method stub

    }

    public Set<Item> getReplies() {
        // TODO Auto-generated method stub
        return null;
    }

    public void addReply( Item item ) {
        // TODO Auto-generated method stub

    }

    public void removeReply( Item item ) {
        // TODO Auto-generated method stub

    }

    public Literal getIPAddress() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setIPAddress( Literal ip ) {
        // TODO Auto-generated method stub

    }

    public Item getLaterVersion() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setLaterVersion( Item item ) {
        // TODO Auto-generated method stub

    }

    public Item getLatestVersion() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setLatestVersion( Item item ) {
        // TODO Auto-generated method stub

    }

    public Item getNextByDate() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setNextByDate( Item item ) {
        // TODO Auto-generated method stub

    }

    public Item getNextVersion() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setNextVersion( Item item ) {
        // TODO Auto-generated method stub

    }

    public Item getPreviousByDate() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setPreviousByDate( Item item ) {
        // TODO Auto-generated method stub

    }

    public Item getPreviousVersion() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setPreviousVersion( Item item ) {
        // TODO Auto-generated method stub

    }

    public Item getReplyOf() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setReplyOf( Item item ) {
        // TODO Auto-generated method stub

    }

    public Item getSibling() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setSibling( Item item ) {
        // TODO Auto-generated method stub

    }

}
