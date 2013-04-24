package org.rdfs.sioc;

import java.util.ArrayList;
import java.util.List;

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.node.Resource;

public class SIOC {

    public static List<Space> listAllSpaces(final Model model) {
	ClosableIterator<Resource> iter = Space.getAllInstances(model);
	List<Space> result = new ArrayList<Space>();

	while (iter.hasNext()) {
	    Resource resource = (Resource) iter.next();
	    result.add(Space.getInstance(model, resource));
	}

	iter.close();
	return result;
    }

    public static List<Container> listAllContainers(final Model model) {
	ClosableIterator<Resource> iter = Container.getAllInstances(model);
	List<Container> result = new ArrayList<Container>();

	while (iter.hasNext()) {
	    Resource resource = (Resource) iter.next();
	    result.add(Container.getInstance(model, resource));
	}

	iter.close();
	return result;
    }

    public static List<Item> listAllItems(final Model model) {
	ClosableIterator<Resource> iter = Item.getAllInstances(model);
	List<Item> result = new ArrayList<Item>();

	while (iter.hasNext()) {
	    Resource resource = (Resource) iter.next();
	    result.add(Item.getInstance(model, resource));
	}

	iter.close();
	return result;
    }

    public static List<Site> listAllSites(final Model model) {
	ClosableIterator<Resource> iter = Site.getAllInstances(model);
	List<Site> result = new ArrayList<Site>();

	while (iter.hasNext()) {
	    Resource resource = (Resource) iter.next();
	    result.add(Site.getInstance(model, resource));
	}

	iter.close();
	return result;
    }

    public static List<Forum> listAllForums(final Model model) {
	ClosableIterator<Resource> iter = Forum.getAllInstances(model);
	List<Forum> result = new ArrayList<Forum>();

	while (iter.hasNext()) {
	    Resource resource = (Resource) iter.next();
	    result.add(Forum.getInstance(model, resource));
	}

	iter.close();
	return result;
    }

    public static List<Thread> listAllThreads(final Model model) {
	ClosableIterator<Resource> iter = Thread.getAllInstances(model);
	List<Thread> result = new ArrayList<Thread>();

	while (iter.hasNext()) {
	    Resource resource = (Resource) iter.next();
	    result.add(Thread.getInstance(model, resource));
	}

	iter.close();
	return result;
    }

    public static List<Post> listAllPosts(final Model model) {
	ClosableIterator<Resource> iter = Post.getAllInstances(model);
	List<Post> result = new ArrayList<Post>();

	while (iter.hasNext()) {
	    Resource resource = (Resource) iter.next();
	    result.add(Post.getInstance(model, resource));
	}

	iter.close();
	return result;
    }

    public static List<UserAccount> listAllUserAccounts(final Model model) {
	ClosableIterator<Resource> iter = UserAccount.getAllInstances(model);
	List<UserAccount> result = new ArrayList<UserAccount>();

	while (iter.hasNext()) {
	    Resource resource = (Resource) iter.next();
	    result.add(UserAccount.getInstance(model, resource));
	}

	iter.close();
	return result;
    }

    public static List<Usergroup> listAllUsergroups(final Model model) {
	ClosableIterator<Resource> iter = Usergroup.getAllInstances(model);
	List<Usergroup> result = new ArrayList<Usergroup>();

	while (iter.hasNext()) {
	    Resource resource = (Resource) iter.next();
	    result.add(Usergroup.getInstance(model, resource));
	}

	iter.close();
	return result;
    }

    public static List<Role> listAllRoles(final Model model) {
	ClosableIterator<Resource> iter = Role.getAllInstances(model);
	List<Role> result = new ArrayList<Role>();

	while (iter.hasNext()) {
	    Resource resource = (Resource) iter.next();
	    result.add(Role.getInstance(model, resource));
	}

	iter.close();
	return result;
    }

}
