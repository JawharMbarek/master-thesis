package de.m0ep.rdf.sioc;

import java.util.HashSet;
import java.util.Set;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;

import de.m0ep.rdf.sioc.vocabulary.SIOC;

public class SIOCFactory {
    public static Community getCommunity( final Resource resource,
            final Model model ) {
        return CommunityImpl.get( resource, model );
    }

    public static Set<Community> getAllCommunities( final Model model ) {
        Set<Community> result = new HashSet<Community>();

        ResIterator iter = model.listResourcesWithProperty( RDF.type,
                SIOC.Community );

        while ( iter.hasNext() ) {
            result.add( CommunityImpl.get( iter.next(), model ) );
        }

        return result;
    }

    public static Community createCommunity( final String uri, final Model model ) {
        return CommunityImpl.create( uri, model );
    }

    public static Community createCommunity( final Resource resource,
            final Model model ) {
        return CommunityImpl.create( resource, model );
    }

    public static Container getContainer( final Resource resource,
            final Model model ) {
        return ContainerImpl.get( resource, model );
    }

    public static Set<Container> getAllContainers( final Model model ) {
        Set<Container> result = new HashSet<Container>();

        ResIterator iter = model.listResourcesWithProperty( RDF.type,
                SIOC.Container );

        while ( iter.hasNext() ) {
            result.add( ContainerImpl.get( iter.next(), model ) );
        }

        return result;
    }

    public static Container createContainer( final String uri, final Model model ) {
        return ContainerImpl.create( uri, model );
    }

    public static Container createContainer( final Resource resource,
            final Model model ) {
        return ContainerImpl.create( resource, model );
    }

    public static Forum getForum( final Resource resource, final Model model ) {
        return ForumImpl.get( resource, model );
    }

    public static Set<Forum> getAllForums( final Model model ) {
        Set<Forum> result = new HashSet<Forum>();

        ResIterator iter = model.listResourcesWithProperty( RDF.type,
                SIOC.Forum );

        while ( iter.hasNext() ) {
            result.add( ForumImpl.get( iter.next(), model ) );
        }

        return result;
    }

    public static Forum createForum( final String uri, final Model model ) {
        return ForumImpl.create( uri, model );
    }

    public static Forum createForum( final Resource resource, final Model model ) {
        return ForumImpl.create( resource, model );
    }

    public static Item getItem( final Resource resource, final Model model ) {
        return ItemImpl.get( resource, model );
    }

    public static Set<Item> getAllItems( final Model model ) {
        Set<Item> result = new HashSet<Item>();

        ResIterator iter = model
                .listResourcesWithProperty( RDF.type, SIOC.Item );

        while ( iter.hasNext() ) {
            result.add( ItemImpl.get( iter.next(), model ) );
        }

        return result;
    }

    public static Item createItem( final String uri, final Model model ) {
        return ItemImpl.create( uri, model );
    }

    public static Item createItem( final Resource resource, final Model model ) {
        return ItemImpl.create( resource, model );
    }

    public static Post getPost( final Resource resource, final Model model ) {
        return PostImpl.get( resource, model );
    }

    public static Set<Post> getAllPosts( final Model model ) {
        Set<Post> result = new HashSet<Post>();

        ResIterator iter = model
                .listResourcesWithProperty( RDF.type, SIOC.Post );

        while ( iter.hasNext() ) {
            result.add( PostImpl.get( iter.next(), model ) );
        }

        return result;
    }

    public static Post createPost( final String uri, final Model model ) {
        return PostImpl.create( uri, model );
    }

    public static Post createPost( final Resource resource, final Model model ) {
        return PostImpl.create( resource, model );
    }

    public static Role getRole( final Resource resource, final Model model ) {
        return RoleImpl.get( resource, model );
    }

    public static Set<Role> getAllRolls( final Model model ) {
        Set<Role> result = new HashSet<Role>();

        ResIterator iter = model
                .listResourcesWithProperty( RDF.type, SIOC.Role );

        while ( iter.hasNext() ) {
            result.add( RoleImpl.get( iter.next(), model ) );
        }

        return result;
    }

    public static Role createRole( final String uri, final Model model ) {
        return RoleImpl.create( uri, model );
    }

    public static Role createRole( final Resource resource, final Model model ) {
        return RoleImpl.create( resource, model );
    }

    public static Space getSpace( final Resource resource, final Model model ) {
        return SpaceImpl.get( resource, model );
    }

    public static Set<Space> getAllSpaces( final Model model ) {
        Set<Space> result = new HashSet<Space>();

        ResIterator iter = model.listResourcesWithProperty( RDF.type,
                SIOC.Space );

        while ( iter.hasNext() ) {
            result.add( SpaceImpl.get( iter.next(), model ) );
        }

        return result;
    }

    public static Space createSpace( final String uri, final Model model ) {
        return SpaceImpl.create( uri, model );
    }

    public static Space createSpace( final Resource resource, final Model model ) {
        return SpaceImpl.create( resource, model );
    }

    public static Site getSite( final Resource resource, final Model model ) {
        return SiteImpl.get( resource, model );
    }

    public static Set<Site> getAllSites( final Model model ) {
        Set<Site> result = new HashSet<Site>();

        ResIterator iter = model
                .listResourcesWithProperty( RDF.type, SIOC.Site );

        while ( iter.hasNext() ) {
            result.add( SiteImpl.get( iter.next(), model ) );
        }

        return result;
    }

    public static Site createSite( final String uri, final Model model ) {
        return SiteImpl.create( uri, model );
    }

    public static Site createSite( final Resource resource, final Model model ) {
        return SiteImpl.create( resource, model );
    }

    public static Thread getThread( final Resource resource, final Model model ) {
        return ThreadImpl.get( resource, model );
    }

    public static Set<Thread> getAllThreads( final Model model ) {
        Set<Thread> result = new HashSet<Thread>();

        ResIterator iter = model.listResourcesWithProperty( RDF.type,
                SIOC.Thread );

        while ( iter.hasNext() ) {
            result.add( ThreadImpl.get( iter.next(), model ) );
        }

        return result;
    }

    public static Thread createThread( final String uri, final Model model ) {
        return ThreadImpl.create( uri, model );
    }

    public static Thread createThread( final Resource resource,
            final Model model ) {
        return ThreadImpl.create( resource, model );
    }

    public static UserAccount getUserAccount( final Resource resource,
            final Model model ) {
        return UserAccountImpl.get( resource, model );
    }

    public static Set<UserAccount> getAllUserAccounts( final Model model ) {
        Set<UserAccount> result = new HashSet<UserAccount>();

        ResIterator iter = model.listResourcesWithProperty( RDF.type,
                SIOC.UserAccount );

        while ( iter.hasNext() ) {
            result.add( UserAccountImpl.get( iter.next(), model ) );
        }

        return result;
    }

    public static UserAccount createUserAccount( final String uri,
            final Model model ) {
        return UserAccountImpl.create( uri, model );
    }

    public static UserAccount createUserAccount( final Resource resource,
            final Model model ) {
        return UserAccountImpl.create( resource, model );
    }

    public static Usergroup getUsergroup( final Resource resource,
            final Model model ) {
        return UsergroupImpl.get( resource, model );
    }

    public static Set<Usergroup> getAllUsergroups( final Model model ) {
        Set<Usergroup> result = new HashSet<Usergroup>();

        ResIterator iter = model.listResourcesWithProperty( RDF.type,
                SIOC.Usergroup );

        while ( iter.hasNext() ) {
            result.add( UsergroupImpl.get( iter.next(), model ) );
        }

        return result;
    }

    public static Usergroup createUsergroup( final String uri, final Model model ) {
        return UsergroupImpl.create( uri, model );
    }

    public static Usergroup createUsergroup( final Resource resource,
            final Model model ) {
        return UsergroupImpl.create( resource, model );
    }
}
