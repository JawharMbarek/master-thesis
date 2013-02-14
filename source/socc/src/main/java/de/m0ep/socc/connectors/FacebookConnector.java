package de.m0ep.socc.connectors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;

import org.apache.commons.codec.digest.DigestUtils;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.util.RDFTool;
import org.openrdf.model.impl.URIImpl;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.SIOCThing;
import org.rdfs.sioc.Site;
import org.rdfs.sioc.UserAccount;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.json.JsonObject;
import com.restfb.types.FacebookType;
import com.restfb.types.Group;
import com.restfb.types.User;

import de.m0ep.socc.AbstractConnector;

public class FacebookConnector extends AbstractConnector {

    private FacebookClient client;
    private String myId;

    public FacebookConnector(String id, Model model, Properties config) {
	super(id, model, config);


	this.client = new DefaultFacebookClient();
	FacebookType me = client.fetchObject("me", FacebookType.class,
		Parameter.with("fields", "id"));
	this.myId = me.getId();
    }

    @Override
    public String getURL() {
	return "http://www.facebook.com/";
    }

    @Override
    public Site getSite() {
	URI uri = (URI) new URIImpl(getURL());

	if (!Site.hasInstance(getModel(), uri)) {
	    Site result = new Site(getModel(), uri, true);
	    result.setName("Facebook");
	    return result;
	} else {
	    return Site.getInstance(getModel(), uri);
	}
    }

    @Override
    public UserAccount getUser() {
	URI uri = (URI) new URIImpl(getURL() + myId);

	if (!UserAccount.hasInstance(getModel(), uri)) {
	    User me = client.fetchObject(myId, User.class);
	    UserAccount result = new UserAccount(getModel(), uri, true);

	    // SIOC statements
	    SIOCThing.setId(getModel(), result, me.getId());
	    SIOCThing.setIsPartOf(getModel(), result, getSite());

	    if (null != me.getEmail() && !me.getEmail().isEmpty())
		result.setEmailsha1(DigestUtils.sha1Hex(me.getEmail()));

	    if (null != me.getUsername() && !me.getUsername().isEmpty())
		result.setAccountname(me.getUsername());

	    if (null != me.getName() && !me.getName().isEmpty())
		result.setName(me.getName());

	    if (null != me.getUpdatedTime())
		result.setModified(RDFTool.dateTime2String(me.getUpdatedTime()));

	    return result;
	} else {
	    return UserAccount.getInstance(getModel(), uri);
	}
    }

    @Override
    public Iterator<Forum> getForums() {
	List<Forum> result = new ArrayList<>();

	URI wallUri = (URI) new URIImpl(getURL() + myId + "/feed");
	if (!Forum.hasInstance(getModel(), wallUri)) {
	    Forum wall = new Forum(getModel(), wallUri, true);
	    wall.setId(myId);
	    wall.setName(getUser().getAllAccountname_as().firstValue()
		    + "'s Wall");

	    result.add(wall);
	} else {
	    result.add(Forum.getInstance(getModel(), wallUri));
	}

	Connection<FacebookType> groupsConnections = client.fetchConnection(
		"me/groups", FacebookType.class);

	for (List<FacebookType> myGroups : groupsConnections) {
	    for (FacebookType type : myGroups) {
		Group group = client.fetchObject(type.getId(), Group.class);

		URI uri = (URI) new URIImpl(getURL() + group.getId() + "/feed");
		Forum forum = new Forum(getModel(), uri, true);
		forum.setId(group.getId());
		forum.setName(group.getName());
		forum.setIsPartOf(getSite());

		if (null != group.getDescription()
			&& !group.getDescription().isEmpty())
		    forum.setDescription(group.getDescription());

		if (null != group.getUpdatedTime())
		    forum.setModified(RDFTool.dateTime2String(group
			    .getUpdatedTime()));

		result.add(forum);
	    }
	}

	return result.iterator();
    }

    @Override
    public Iterator<Post> getPosts(Container container) {
	if (!container.hasIsPartOf(getSite()))
	    return super.getPosts(container);

	Connection<JsonObject> feed = client.fetchConnectionPage(container
		.getAllId_as().firstValue() + "/feed", JsonObject.class);

	return new PostIterator(feed.iterator());
    }

    @Override
    public boolean canPostOn(Container container) {
	return container.hasIsPartOf(getSite());
    }

    @Override
    public boolean canReplyOn(Post parent) {
	return parent.hasIsPartOf(getSite());
    }

    private class PostIterator implements Iterator<Post> {
	private Iterator<List<JsonObject>> feed;
	private Iterator<JsonObject> page;

	public PostIterator(Iterator<List<JsonObject>> feed) {
	    this.feed = feed;
	    this.page = feed.next().iterator();
	}

	@Override
	public boolean hasNext() {
	    return null != feed && null != page
		    && (feed.hasNext() || page.hasNext());
	}

	@Override
	public Post next() {
	    if (!hasNext())
		throw new NoSuchElementException("nothing here");

	    // page is empty, fetch next
	    if (feed.hasNext() && !page.hasNext())
		page = feed.next().iterator();

	    JsonObject next = page.next();
	    Post result = parsePost(next);

	    return result;
	}

	@Override
	public void remove() {
	    throw new UnsupportedOperationException("remove is not supported");
	}
    }

    private Post parsePost(final JsonObject obj) {
	return null;
    }
}
