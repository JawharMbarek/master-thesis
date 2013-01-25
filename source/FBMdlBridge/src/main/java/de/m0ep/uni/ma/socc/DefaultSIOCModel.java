package de.m0ep.uni.ma.socc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ontoware.rdf2go.RDF2Go;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdfreactor.runtime.ReactorResult;

import com.google.common.base.Preconditions;

import de.m0ep.uni.ma.rdf.sioc.Container;
import de.m0ep.uni.ma.rdf.sioc.Forum;
import de.m0ep.uni.ma.rdf.sioc.Item;
import de.m0ep.uni.ma.rdf.sioc.Post;
import de.m0ep.uni.ma.rdf.sioc.Role;
import de.m0ep.uni.ma.rdf.sioc.Space;
import de.m0ep.uni.ma.rdf.sioc.Thread;
import de.m0ep.uni.ma.rdf.sioc.UserAccount;
import de.m0ep.uni.ma.rdf.sioc.Usergroup;
import de.m0ep.uni.ma.rdf.vocabularies.DCTerms;
import de.m0ep.uni.ma.rdf.vocabularies.FOAF;
import de.m0ep.uni.ma.rdf.vocabularies.SIOC;

public class DefaultSIOCModel implements SIOCModel {

    private Model model;

    public DefaultSIOCModel() {
        this.model = RDF2Go.getModelFactory().createModel();
        this.model.open();
        initModel();
    }

    public DefaultSIOCModel( final Model model ) {
        Preconditions.checkNotNull( model, "model can't be null" );
        Preconditions.checkArgument( model.isOpen(), "model have to ne open" );

        this.model = model;
        initModel();
    }

    private void initModel() {
        model.setNamespace( "sioc", SIOC.NS_SIOC.toString() );
        model.setNamespace( "foaf", FOAF.NS_FOAF.toString() );
        model.setNamespace( "dcterms", DCTerms.NS_DCTerms.toString() );
    }

    public Model getDelegatingModel() {
        return model;
    }

    public Space createSpace( final String uri ) {
        Preconditions.checkNotNull( uri, "uri can't be null" );
        Preconditions.checkArgument( model.isValidURI( uri ), uri
                + " is no valid uri" );

        return new Space( model, uri, true );
    }

    public Space getSpace( final String uri ) {
        Preconditions.checkNotNull( uri, "uri can't be null" );
        Preconditions.checkArgument( model.isValidURI( uri ), uri
                + " is no valid uri" );

        return Space.getInstance( model, model.createURI( uri ) );
    }

    public List<Space> getAllSpaces() {
        List<Space> result = new ArrayList<Space>();

        ReactorResult<? extends Space> all = Space
                .getAllInstances_as( model );

        result.addAll( all.asList() );

        return Collections.unmodifiableList( result );
    }

    public void removeSpace( final Space space ) {
        Space.deleteAllProperties( model, space );
    }

    public Container createContainer( final String uri ) {
        Preconditions.checkNotNull( uri, "uri can't be null" );
        Preconditions.checkArgument( model.isValidURI( uri ), uri
                + " is no valid uri" );

        return new Container( model, uri, true );
    }

    public Container getContainer( final String uri ) {
        Preconditions.checkNotNull( uri, "uri can't be null" );
        Preconditions.checkArgument( model.isValidURI( uri ), uri
                + " is no valid uri" );

        return Container.getInstance( model, model.createURI( uri ) );
    }

    public List<Container> getAllContainers() {
        List<Container> result = new ArrayList<Container>();

        ReactorResult<? extends Container> all = Container
                .getAllInstances_as( model );

        result.addAll( all.asList() );

        return Collections.unmodifiableList( result );
    }

    public void removeContainer( final Container container ) {
        Container.deleteAllProperties( model, container );
    }

    public Item createItem( final String uri ) {
        Preconditions.checkNotNull( uri, "uri can't be null" );
        Preconditions.checkArgument( model.isValidURI( uri ), uri
                + " is no valid uri" );

        return new Item( model, uri, true );
    }

    public Item getItem( final String uri ) {
        Preconditions.checkNotNull( uri, "uri can't be null" );
        Preconditions.checkArgument( model.isValidURI( uri ), uri
                + " is no valid uri" );

        return Item.getInstance( model, model.createURI( uri ) );
    }

    public List<Item> getAllItems() {
        List<Item> result = new ArrayList<Item>();

        ReactorResult<? extends Item> all = Item.getAllInstances_as( model );

        result.addAll( all.asList() );

        return Collections.unmodifiableList( result );
    }

    public void removeItem( final Item item ) {
        Item.deleteAllProperties( model, item );
    }

    public Forum createForum( final String uri ) {
        Preconditions.checkNotNull( uri, "uri can't be null" );
        Preconditions.checkArgument( model.isValidURI( uri ), uri
                + " is no valid uri" );

        return new Forum( model, uri, true );
    }

    public Forum getForum( final String uri ) {
        Preconditions.checkNotNull( uri, "uri can't be null" );
        Preconditions.checkArgument( model.isValidURI( uri ), uri
                + " is no valid uri" );

        return Forum.getInstance( model, model.createURI( uri ) );
    }

    public List<Forum> getAllForums() {
        List<Forum> result = new ArrayList<Forum>();

        ReactorResult<? extends Forum> all = Forum.getAllInstances_as( model );

        result.addAll( all.asList() );

        return Collections.unmodifiableList( result );
    }

    public void removeForum( final Forum forum ) {
        Forum.deleteAllProperties( model, forum );
    }

    public Thread createThread( final String uri ) {
        Preconditions.checkNotNull( uri, "uri can't be null" );
        Preconditions.checkArgument( model.isValidURI( uri ), uri
                + " is no valid uri" );

        return new Thread( model, uri, true );
    }

    public Thread getThread( final String uri ) {
        Preconditions.checkNotNull( uri, "uri can't be null" );
        Preconditions.checkArgument( model.isValidURI( uri ), uri
                + " is no valid uri" );

        return Thread.getInstance( model, model.createURI( uri ) );
    }

    public List<Thread> getAllThreads() {
        List<Thread> result = new ArrayList<Thread>();

        ReactorResult<? extends Thread> all = Thread.getAllInstances_as( model );

        result.addAll( all.asList() );

        return Collections.unmodifiableList( result );
    }

    public void removeThread( final Thread thread ) {
        Thread.deleteAllProperties( model, thread );
    }

    public Post createPost( final String uri ) {
        Preconditions.checkNotNull( uri, "uri can't be null" );
        Preconditions.checkArgument( model.isValidURI( uri ), uri
                + " is no valid uri" );

        return new Post( model, uri, true );
    }

    public Post getPost( final String uri ) {
        Preconditions.checkNotNull( uri, "uri can't be null" );
        Preconditions.checkArgument( model.isValidURI( uri ), uri
                + " is no valid uri" );

        return Post.getInstance( model, model.createURI( uri ) );
    }

    public List<Post> getAllPosts() {
        List<Post> result = new ArrayList<Post>();

        ReactorResult<? extends Post> all = Post.getAllInstances_as( model );

        result.addAll( all.asList() );

        return Collections.unmodifiableList( result );
    }

    public void removePost( final Post post ) {
        Post.deleteAllProperties( model, post );
    }

    public UserAccount createUserAccount( final String uri ) {
        Preconditions.checkNotNull( uri, "uri can't be null" );
        Preconditions.checkArgument( model.isValidURI( uri ), uri
                + " is no valid uri" );

        return new UserAccount( model, uri, true );
    }

    public UserAccount getUserAccount( final String uri ) {
        Preconditions.checkNotNull( uri, "uri can't be null" );
        Preconditions.checkArgument( model.isValidURI( uri ), uri
                + " is no valid uri" );

        return UserAccount.getInstance( model, model.createURI( uri ) );
    }

    public List<UserAccount> getAllUserAccounts() {
        List<UserAccount> result = new ArrayList<UserAccount>();

        ReactorResult<? extends UserAccount> all = UserAccount
                .getAllInstances_as( model );

        result.addAll( all.asList() );

        return Collections.unmodifiableList( result );
    }

    public void removeUserAccount( final UserAccount userAccount ) {
        UserAccount.deleteAllProperties( model, userAccount );
    }

    public Usergroup createUsergroup( final String uri ) {
        Preconditions.checkNotNull( uri, "uri can't be null" );
        Preconditions.checkArgument( model.isValidURI( uri ), uri
                + " is no valid uri" );

        return new Usergroup( model, uri, true );
    }

    public Usergroup getUsergroup( final String uri ) {
        Preconditions.checkNotNull( uri, "uri can't be null" );
        Preconditions.checkArgument( model.isValidURI( uri ), uri
                + " is no valid uri" );

        return Usergroup.getInstance( model, model.createURI( uri ) );
    }

    public List<Usergroup> getAllUsergroups() {
        List<Usergroup> result = new ArrayList<Usergroup>();

        ReactorResult<? extends Usergroup> all = Usergroup
                .getAllInstances_as( model );

        result.addAll( all.asList() );

        return Collections.unmodifiableList( result );
    }

    public void removeUsergroup( final Usergroup usergroup ) {
        Usergroup.deleteAllProperties( model, usergroup );
    }

    public Role createRole( final String uri ) {
        Preconditions.checkNotNull( uri, "uri can't be null" );
        Preconditions.checkArgument( model.isValidURI( uri ), uri
                + " is no valid uri" );

        return new Role( model, uri, true );
    }

    public Role getRole( final String uri ) {
        Preconditions.checkNotNull( uri, "uri can't be null" );
        Preconditions.checkArgument( model.isValidURI( uri ), uri
                + " is no valid uri" );

        return Role.getInstance( model, model.createURI( uri ) );
    }

    public List<Role> getAllRoles() {
        List<Role> result = new ArrayList<Role>();

        ReactorResult<? extends Role> all = Role.getAllInstances_as( model );

        result.addAll( all.asList() );

        return Collections.unmodifiableList( result );
    }

    public void removeRole( final Role role ) {
        Role.deleteAllProperties( model, role );
    }
}
