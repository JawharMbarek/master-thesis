package de.m0ep.socc.connectors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;

import org.apache.commons.codec.digest.DigestUtils;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.model.node.impl.URIImpl;
import org.ontoware.rdf2go.util.RDFTool;
import org.ontoware.rdf2go.vocabulary.RDF;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.SIOC;
import org.rdfs.sioc.SIOCThing;
import org.rdfs.sioc.Site;
import org.rdfs.sioc.UserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.json.JsonObject;
import com.restfb.types.FacebookType;
import com.restfb.types.Group;
import com.restfb.types.User;

import de.m0ep.socc.AbstractConnector;
import de.m0ep.socc.utils.URIUtils;

public class FacebookConnector extends AbstractConnector {

    private static final Logger LOG = LoggerFactory
                                            .getLogger( FacebookConnector.class );

    private FacebookClient client;
    private String myId;


    public FacebookConnector(String id, Model model, Properties config) {
        super( id, model, config );

        this.client = new DefaultFacebookClient(
                config.getProperty( "access_token" ) );
        FacebookType me = client.fetchObject( "me", FacebookType.class,
                Parameter.with( "fields", "id" ) );
        this.myId = me.getId();
    }

    public String getURL() {
        return "http://www.facebook.com/";
    }

    public Site getSite() {
        URI uri = URIUtils.createURI( getURL() );

        if( !Site.hasInstance( getModel(), uri ) ) {
            Site result = new Site( getModel(), uri, true );
            result.setName( "Facebook" );
            return result;
        } else {
            return Site.getInstance( getModel(), uri );
        }
    }

    public UserAccount getUser() {
        URI uri = (URI) new URIImpl( getURL() + myId );

        if( !UserAccount.hasInstance( getModel(), uri ) ) {
            User me = client.fetchObject( myId, User.class );
            UserAccount result = new UserAccount( getModel(), uri, true );

            // SIOC statements
            SIOCThing.setId( getModel(), result, me.getId() );
            SIOCThing.setIsPartOf( getModel(), result, getSite() );

            if( null != me.getEmail() && !me.getEmail().isEmpty() ) {
                result.setEmail( URIUtils.createMailtoURI( me.getEmail() ) );
                result.setEmailsha1( DigestUtils.sha1Hex( me.getEmail() ) );
            }

            if( null != me.getUsername() && !me.getUsername().isEmpty() )
                result.setAccountname( me.getUsername() );

            if( null != me.getName() && !me.getName().isEmpty() )
                result.setName( me.getName() );

            if( null != me.getUpdatedTime() )
                result.setModified( RDFTool.dateTime2String( me
                        .getUpdatedTime() ) );

            return result;
        } else {
            return UserAccount.getInstance( getModel(), uri );
        }
    }

    public Iterator<Forum> getForums() {
        List<Forum> result = new ArrayList<Forum>();

        URI uri = URIUtils.createURI( getURL() + myId + "/feed" );
        if( !Forum.hasInstance( getModel(), uri ) ) {
            Forum wall = new Forum( getModel(), uri, true );
            wall.setId( myId );
            wall.setName( getUser().getAllAccountname_as().firstValue()
                    + "'s Wall" );
            wall.setHost( getSite() );

            result.add( wall );
        } else {
            result.add( Forum.getInstance( getModel(), uri ) );
        }

        Connection<Group> groupsConnections = client.fetchConnection(
                "me/groups", Group.class,
                Parameter.with( "fields", "name,id,description,updated_time" ) );

        for ( List<Group> myGroups : groupsConnections ) {
            for ( Group group : myGroups ) {
                uri = URIUtils.createURI( getURL() + group.getId()
                        + "/feed" );

                if( !Forum.hasInstance( getModel(), uri ) ) {
                    Forum forum = new Forum( getModel(), uri, true );
                    forum.setId( group.getId() );
                    forum.setName( group.getName() );
                    forum.setHost( getSite() );

                    if( null != group.getDescription()
                            && !group.getDescription().isEmpty() )
                        forum.setDescription( group.getDescription() );

                    if( null != group.getUpdatedTime() )
                        forum.setModified( RDFTool.dateTime2String( group
                                .getUpdatedTime() ) );

                    result.add( forum );
                } else {
                    result.add( Forum.getInstance( getModel(), uri ) );
                }
            }
        }

        return result.iterator();
    }

    @Override
    public Iterator<Post> getPosts(Container container) {
        if( !canPublishOn( container ) )
            return super.getPosts( container );

        Connection<JsonObject> feed;
        try {
            feed = client.fetchConnection( "/"
                    + container
                    .getAllId_as().firstValue() + "/feed", JsonObject.class );
        } catch ( Exception e ) {
            LOG.warn( e.getMessage(), e );
            return super.getPosts( container );
        }

        return new PostIterator( feed.iterator(), container );
    }

    @Override
    public boolean canPublishOn(Container container) {
        if( getModel().contains( container, RDF.type, SIOC.Forum ) ) {
            Forum forum = (Forum) container;
            return forum.hasHost( getSite() );
        }

        return false;
    }

    @Override
    public boolean canReplyOn(Post parent) {
        /**
         * We can reply on this post if:
         * - this post is not already a reply
         * - we can publish on the container of this post
         */
        if( !parent.hasReplyof() && parent.hasContainer() ) {
            Container container = parent.getAllContainer_as().firstValue();
            return canPublishOn( container );
        }

        return false;
    }

    private class PostIterator implements Iterator<Post> {
        private final Iterator<List<JsonObject>> feed;
        private final Container                  container;
        private Iterator<JsonObject>             page;

        public PostIterator( final Iterator<List<JsonObject>> feed,
                final Container container ) {
            this.feed = feed;
            this.page = feed.next().iterator();
            this.container = container;
        }

        public boolean hasNext() {
            return null != feed && null != page
                    && ( feed.hasNext() || page.hasNext() );
        }

        public Post next() {
            if( !hasNext() )
                throw new NoSuchElementException( "nothing here" );
            // page is empty, fetch next
            if( feed.hasNext() && !page.hasNext() ){
                page = feed.next().iterator();
            }

            if( page.hasNext() ) {
                JsonObject next = page.next();
                System.out.println( "\t" + next.getString( "id" ) );
                Post result = parsePost( next );
                return result;
            }

            return null;
        }

        public void remove() {
            throw new UnsupportedOperationException( "remove is not supported" );
        }
    }

    private Post parsePost(final JsonObject obj) {
        return null;
    }
}
