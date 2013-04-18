package de.m0ep.socc.connectors.moodle;

import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.util.RDFTool;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.Thread;
import org.rdfs.sioc.UserAccount;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import de.m0ep.moodlews.soap.CourseRecord;
import de.m0ep.moodlews.soap.ForumDiscussionRecord;
import de.m0ep.moodlews.soap.ForumPostRecord;
import de.m0ep.moodlews.soap.ForumRecord;
import de.m0ep.moodlews.soap.UserRecord;
import de.m0ep.socc.exceptions.ConnectorException;
import de.m0ep.socc.utils.DateUtils;
import de.m0ep.socc.utils.RDF2GoUtils;
import de.m0ep.socc.utils.SIOCUtils;
import de.m0ep.socc.utils.StringUtils;

public final class MoodleToSIOCConverter {
    public static UserAccount createUserAccount(
	    final MoodleConnector connector, final UserRecord userRecord,
	    final URI uri) {
	Preconditions.checkNotNull(connector, "connector can not be null");
	Preconditions.checkNotNull(userRecord, "userRecord can not be null");
	Preconditions.checkNotNull(uri, "uri can not be null");

	UserAccount result = new UserAccount(connector.getModel(), uri, true);
	result.setId(Integer.toString(userRecord.getId()));
	result.setIsPartOf(connector.getSite());
	result.setAccountservicehomepage(RDF2GoUtils.createURI(connector
		.getURL()));

	String firstName = Strings.emptyToNull(userRecord.getFirstname());
	String lastName = Strings.emptyToNull(userRecord.getLastname());
	if (null != firstName || null != lastName) {
	    result.setName((firstName + " " + lastName).trim());
	}

	String username = Strings.emptyToNull(userRecord.getUsername());
	if (null != username) {
	    result.setAccountname(username);
	}

	String email = Strings.emptyToNull(userRecord.getEmail());
	if (null != email) {
	    result.setEmail(RDF2GoUtils.createMailtoURI(email));
	    result.setEmailsha1(RDFTool.sha1sum(email));
	}

	String description = Strings.emptyToNull(userRecord.getDescription());
	if (null != description) {
	    result.setDescription(description);
	}

	return result;
    }

    public static Forum createForum(final MoodleConnector connector,
	    final ForumRecord forumRecord, final URI uri)
	    throws ConnectorException {
	Preconditions.checkNotNull(connector, "connector can not be null");
	Preconditions.checkNotNull(forumRecord, "forumRecord can not be null");
	Preconditions.checkNotNull(uri, "uri can not be null");

	Forum result = new Forum(connector.getModel(), uri, true);
	CourseRecord course = connector.getCourse(forumRecord.getCourse());
	result.setId(Integer.toString(forumRecord.getId()));
	result.setNumthreads(0);
	result.setHost(connector.getSite());
	connector.getSite().addHostof(result);

	result.setModified(DateUtils.formatISO8601(forumRecord
		.getTimemodified() * 1000L));

	if (null != forumRecord.getIntro()) {
	    result.setDescription(forumRecord.getIntro());
	}

	if (null != forumRecord.getName()) {
	    result.setName(course.getFullname() + "/" + forumRecord.getName());
	}

	return result;
    }

    public static Thread createThread(final MoodleConnector connector,
	    final ForumDiscussionRecord discussionRecord, final URI uri,
	    final Forum parentForum) throws ConnectorException {
	Preconditions.checkNotNull(connector, "connector can not be null");
	Preconditions.checkNotNull(discussionRecord,
		"discussionRecord can not be null");
	Preconditions.checkNotNull(uri, "uri can not be null");
	Preconditions.checkNotNull(parentForum, "parentForum can not be null");

	Thread result = new Thread(connector.getModel(), uri, true);
	result.setId(Integer.toString(discussionRecord.getId()));
	result.setParent(parentForum);
	result.setNumitems(0);
	parentForum.addParentof(result);

	result.setModified(DateUtils.formatISO8601(discussionRecord
		.getTimemodified() * 1000L));

	result.setCreator(connector.getUserAccount(Integer
		.toString(discussionRecord.getUserid())));

	if (null != discussionRecord.getName()) {
	    result.setName(discussionRecord.getName());
	    result.setTitle(discussionRecord.getName());
	    result.setSubject(discussionRecord.getName());
	}

	parentForum.setNumthreads((parentForum.hasNumthreads()) ? (parentForum
		.getNumthreads() + 1) : (1));

	return result;
    }

    public static Post createPost(final MoodleConnector connector,
	    ForumPostRecord postRecord, URI uri, final Container container,
	    final Post parentPost) throws ConnectorException {
	Preconditions.checkNotNull(connector, "connector can not be null");
	Preconditions.checkNotNull(postRecord, "postRecord can not be null");
	Preconditions.checkNotNull(uri, "uri can not be null");
	Preconditions.checkNotNull(container, "container can not be null");

	Post result = new Post(connector.getModel(), uri, true);
	result.setId(Integer.toString(postRecord.getId()));
	result.setCreator(connector.getUserAccount(Integer.toString(postRecord
		.getUserid())));

	if (null != postRecord.getSubject()) {
	    result.setSubject(postRecord.getSubject());
	}

	String content = Strings.nullToEmpty(postRecord.getMessage());
	result.setContent(StringUtils.stripHTML(content));
	result.setContentEncoded(RDF2GoUtils.createCDATASection(content));

	result.setCreated(DateUtils.formatISO8601(postRecord
		.getCreated() * 1000L));
	result.setModified(DateUtils.formatISO8601(postRecord
		.getModified() * 1000L));

	if (null != container) {
	    result.setContainer(container);
	    container.addContainerof(result);
	    result.setDiscussion(container);
	    SIOCUtils.updateLastItemDate(container, result);

	    container.setNumitems((container.hasNumitems()) ? (container
		    .getNumitems() + 1) : (1));
	}

	if (null != parentPost) {
	    result.setReplyof(parentPost);
	    parentPost.addReply(result);
	    SIOCUtils.updateLastReplyDate(parentPost, result);

	    parentPost.setNumreplies((parentPost.hasNumreplies()) ? (parentPost
		    .getNumreplies() + 1) : (1));
	}

	return result;
    }
}
