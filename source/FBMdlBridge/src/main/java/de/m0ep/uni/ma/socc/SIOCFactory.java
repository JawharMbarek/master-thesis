package de.m0ep.uni.ma.socc;

import org.ontoware.rdf2go.model.Model;

import com.google.common.base.Preconditions;

import de.m0ep.uni.ma.rdf.sioc.Container;
import de.m0ep.uni.ma.rdf.sioc.Forum;
import de.m0ep.uni.ma.rdf.sioc.Item;
import de.m0ep.uni.ma.rdf.sioc.Role;
import de.m0ep.uni.ma.rdf.sioc.Site;
import de.m0ep.uni.ma.rdf.sioc.Space;
import de.m0ep.uni.ma.rdf.sioc.Thread;
import de.m0ep.uni.ma.rdf.sioc.UserAccount;
import de.m0ep.uni.ma.rdf.sioc.Usergroup;

public class SIOCFactory {
    private Model  model;
    private String uri_prefix;

    public SIOCFactory( final Model model, final String uri_prefix ) {
        Preconditions.checkNotNull( model );
        Preconditions.checkNotNull( uri_prefix );

        this.model = model;
        this.uri_prefix = uri_prefix;
    }

    public Space createSpace( final String id ) {
        Preconditions.checkNotNull( id, "id can not be null" );
        Preconditions.checkArgument( !id.isEmpty(), "id can not be empty" );

        Space result = new Space( model, uri_prefix + id, true );
        result.setSIOCId( id );
        return result;
    }

    public Container createContainer( final String id ) {
        Preconditions.checkNotNull( id, "id can not be null" );
        Preconditions.checkArgument( !id.isEmpty(), "id can not be empty" );

        Container result = new Container( model, uri_prefix + id, true );
        result.setSIOCId( id );
        return result;
    }

    public Item createItem( final String id ) {
        Preconditions.checkNotNull( id, "id can not be null" );
        Preconditions.checkArgument( !id.isEmpty(), "id can not be empty" );

        Item result = new Item( model, uri_prefix + id, true );
        result.setSIOCId( id );
        return result;
    }

    public Site createSite( final String id ) {
        Preconditions.checkNotNull( id, "id can not be null" );
        Preconditions.checkArgument( !id.isEmpty(), "id can not be empty" );

        Site result = new Site( model, uri_prefix + id, true );
        result.setSIOCId( id );
        return result;
    }

    public Forum createForum( final String id ) {
        Preconditions.checkNotNull( id, "id can not be null" );
        Preconditions.checkArgument( !id.isEmpty(), "id can not be empty" );

        Forum result = new Forum( model, uri_prefix + id, true );
        result.setSIOCId( id );
        return result;
    }

    public Thread createThread( final String id ) {
        Preconditions.checkNotNull( id, "id can not be null" );
        Preconditions.checkArgument( !id.isEmpty(), "id can not be empty" );

        Thread result = new Thread( model, uri_prefix + id, true );
        result.setSIOCId( id );
        return result;
    }

    public UserAccount createUserAccount( final String id ) {
        Preconditions.checkNotNull( id, "id can not be null" );
        Preconditions.checkArgument( !id.isEmpty(), "id can not be empty" );

        UserAccount result = new UserAccount( model, uri_prefix + id, true );
        result.setSIOCId( id );
        return result;
    }

    public Usergroup createUsergroup( final String id ) {
        Preconditions.checkNotNull( id, "id can not be null" );
        Preconditions.checkArgument( !id.isEmpty(), "id can not be empty" );

        Usergroup result = new Usergroup( model, uri_prefix + id, true );
        result.setSIOCId( id );
        return result;
    }

    public Role createRole( final String id ) {
        Preconditions.checkNotNull( id, "id can not be null" );
        Preconditions.checkArgument( !id.isEmpty(), "id can not be empty" );

        Role result = new Role( model, uri_prefix + id, true );
        result.setSIOCId( id );
        return result;
    }
}
