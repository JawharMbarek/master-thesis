package de.m0ep.uni.ma.socc;

import java.util.List;

import de.m0ep.uni.ma.rdf.sioc.Container;
import de.m0ep.uni.ma.rdf.sioc.Forum;
import de.m0ep.uni.ma.rdf.sioc.Item;
import de.m0ep.uni.ma.rdf.sioc.Post;
import de.m0ep.uni.ma.rdf.sioc.Role;
import de.m0ep.uni.ma.rdf.sioc.Space;
import de.m0ep.uni.ma.rdf.sioc.Thread;
import de.m0ep.uni.ma.rdf.sioc.UserAccount;
import de.m0ep.uni.ma.rdf.sioc.Usergroup;

public interface SIOCModel {

    public Space createSpace( String uri );

    public Space getSpace( String uri );

    public List<Space> getAllSpaces();

    public void removeSpace( Space space );


    public Container createContainer( String uri );

    public Container getContainer( String uri );

    public List<Container> getAllContainers();

    public void removeContainer( Container container );


    public Item createItem( String uri );

    public Item getItem( String uri );

    public List<Item> getAllItems();

    public void removeItem( Item item );


    public Forum createForum( String uri );

    public Forum getForum( String uri );

    public List<Forum> getAllForums();

    public void removeForum( Forum forum );


    public Thread createThread( String uri );

    public Thread getThread( String uri );

    public List<Thread> getAllThreads();

    public void removeThread( Thread thread );


    public Post createPost( String uri );

    public Post getPost( String uri );

    public List<Post> getAllPosts();

    public void removePost( Post post );


    public UserAccount createUserAccount( String uri );

    public UserAccount getUserAccount( String uri );

    public List<UserAccount> getAllUserAccounts();

    public void removeUserAccount( UserAccount userAccount );


    public Usergroup createUsergroup( String uri );

    public Usergroup getUsergroup( String uri );

    public List<Usergroup> getAllUsergroups();

    public void removeUsergroup( Usergroup usergroup );


    public Role createRole( String uri );

    public Role getRole( String uri );

    public List<Role> getAllRoles();

    public void removeRole( Role role );
}
