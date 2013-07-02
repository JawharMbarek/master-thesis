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
import de.m0ep.moodlews.soap.ForumPostDatum;
import de.m0ep.moodlews.soap.ForumPostRecord;
import de.m0ep.moodlews.soap.ForumRecord;
import de.m0ep.moodlews.soap.Mdl_soapserverBindingStub;
import de.m0ep.moodlews.soap.UserRecord;
import de.m0ep.socc.exceptions.ConnectorException;
import de.m0ep.socc.utils.DateUtils;
import de.m0ep.socc.utils.RDF2GoUtils;
import de.m0ep.socc.utils.SIOCUtils;
import de.m0ep.socc.utils.StringUtils;

public final class MoodleSIOCConverter {

    /**
     * Private constructor to prevent instanciation.
     */
    private MoodleSIOCConverter() {
    }

/**
     * Create a {@link UserAccount} from a {@link UserRecord} with the provided {@link URI}.
     * 
     * @param connector
     *            Connector to create the {@link UserAccount}
     * @param userRecord
     *            The {@link UserRecord} where the data are from.
     * @param uri
     *            The {@link URI) for the new UserAccount;
     *            
     * @throws NullPointerException 
     * 		  Thrown if one or more parameter are null.
     */
    public static UserAccount createSiocUserAccount(
	    final MoodleConnector connector,
	    final UserRecord userRecord,
	    final URI uri) {
	Preconditions.checkNotNull(connector, "connector can not be null");
	Preconditions.checkNotNull(userRecord, "userRecord can not be null");
	Preconditions.checkNotNull(uri, "uri can not be null");

	UserAccount result = new UserAccount(
		connector.getContext().getDataModel(),
		uri,
		true);
	result.setId(Integer.toString(userRecord.getId()));
	result.setIsPartOf(connector.getSite());
	result.setAccountServiceHomepage(connector.getService()
		.getServiceEndpoint());

	String firstName = Strings.emptyToNull(userRecord.getFirstname());
	String lastName = Strings.emptyToNull(userRecord.getLastname());
	if (null != firstName || null != lastName) {
	    result.setName((firstName + " " + lastName).trim());
	}

	String username = Strings.emptyToNull(userRecord.getUsername());
	if (null != username) {
	    result.setAccountName(username);
	}

	String email = Strings.emptyToNull(userRecord.getEmail());
	if (null != email) {
	    result.addEmail(RDF2GoUtils.createMailtoURI(email));
	    result.addEmailSha1(RDFTool.sha1sum(email));
	}

	String description = Strings.emptyToNull(userRecord.getDescription());
	if (null != description) {
	    result.setDescription(description);
	}

	return result;
    }

    /**
     * Create a new SIOC {@link Forum} from the provided {@link ForumRecord}
     * with the provided {@link URI}.
     * 
     * @param connector
     *            The connector to create the {@link Forum}.
     * @param forumRecord
     *            The {@link ForumRecord} where the data for the new
     *            {@link Forum} is from.
     * @param uri
     *            The {@link URI} for the {@link Forum}.
     * 
     * @return
     * 
     * @throws ConnectorException
     */
    public static Forum createSiocForum(final MoodleConnector connector,
	    final ForumRecord forumRecord, final URI uri)
	    throws ConnectorException {
	Preconditions.checkNotNull(connector, "connector can not be null");
	Preconditions.checkNotNull(forumRecord, "forumRecord can not be null");
	Preconditions.checkNotNull(uri, "uri can not be null");

	Forum result = new Forum(
		connector.getContext().getDataModel(),
		uri,
		true);
	CourseRecord course = connector.getCourse(forumRecord.getCourse());
	result.setId(Integer.toString(forumRecord.getId()));
	result.setNumThreads(0);
	result.setHost(connector.getSite());
	connector.getSite().addHostOf(result);

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

    /**
     * 
     * @param connector
     * @param discussionRecord
     * @param uri
     * @param parentForum
     * @return
     * @throws ConnectorException
     */
    public static Thread createSiocThread(final MoodleConnector connector,
	    final ForumDiscussionRecord discussionRecord, final URI uri,
	    final Forum parentForum) throws ConnectorException {
	Preconditions.checkNotNull(connector, "connector can not be null");
	Preconditions.checkNotNull(discussionRecord,
		"discussionRecord can not be null");
	Preconditions.checkNotNull(uri, "uri can not be null");
	Preconditions.checkNotNull(parentForum, "parentForum can not be null");

	Thread result = new Thread(
		connector.getContext().getDataModel(),
		uri,
		true);
	result.setId(Integer.toString(discussionRecord.getId()));
	result.setParent(parentForum);
	result.setNumItems(0);
	parentForum.addParentOf(result);

	result.setModified(DateUtils.formatISO8601(discussionRecord
		.getTimemodified() * 1000L));

	result.setCreator(connector.getUserAccount(Integer
		.toString(discussionRecord.getUserid())));

	if (null != discussionRecord.getName()) {
	    result.setName(discussionRecord.getName());
	    result.setTitle(discussionRecord.getName());
	    result.setSubject(discussionRecord.getName());
	}

	parentForum.setNumThreads((parentForum.hasNumThreads()) ? (parentForum
		.getNumThreads() + 1) : (1));

	return result;
    }

    /**
     * 
     * @param connector
     * @param postRecord
     * @param uri
     * @param container
     * @param parentPost
     * @return
     * @throws ConnectorException
     */
    public static Post createSiocPost(final MoodleConnector connector,
	    ForumPostRecord postRecord, URI uri, final Container container,
	    final Post parentPost) throws ConnectorException {
	Preconditions.checkNotNull(connector, "connector can not be null");
	Preconditions.checkNotNull(postRecord, "postRecord can not be null");
	Preconditions.checkNotNull(uri, "uri can not be null");
	Preconditions.checkNotNull(container, "container can not be null");

	Post result = new Post(
		connector.getContext().getDataModel(),
		uri,
		true);
	result.setId(Integer.toString(postRecord.getId()));
	result.setCreator(connector.getUserAccount(Integer.toString(postRecord
		.getUserid())));

	if (null != postRecord.getSubject()) {
	    result.setSubject(postRecord.getSubject());
	}

	String content = Strings.nullToEmpty(postRecord.getMessage());
	result.setContent(StringUtils.stripHTML(content));
	result.setContentEncoded(RDF2GoUtils.createCDATASection(content));

	result.setCreated(DateUtils
		.formatISO8601(postRecord.getCreated() * 1000L));
	result.setModified(DateUtils
		.formatISO8601(postRecord.getModified() * 1000L));

	if (null != container) {
	    result.setContainer(container);
	    container.addContainerOf(result);
	    result.setDiscussion(container);
	    SIOCUtils.updateLastItemDate(container, result);

	    container.setNumItems((container.hasNumItems()) ? (container
		    .getNumItems() + 1) : (1));
	}

	if (null != parentPost) {
	    result.setReplyOf(parentPost);
	    parentPost.addReply(result);
	    SIOCUtils.updateLastReplyDate(parentPost, result);

	    parentPost.setNumReplies((parentPost.hasNumReplies()) ? (parentPost
		    .getNumReplies() + 1) : (1));
	}

	return result;
    }

    /**
     * 
     * @param mdlClient
     * @param post
     * @return
     */
    public static ForumPostDatum createMoodleForumPost(
	    final Mdl_soapserverBindingStub mdlClient,
	    final Post post) {
	final ForumPostDatum datum = new ForumPostDatum(mdlClient
		.getNAMESPACE());
	datum.setMessage(post.getContent());
	if (post.hasTitle()) {
	    datum.setSubject(post.getTitle());
	} else if (post.hasSubject()) {
	    datum.setSubject(post.getSubject());
	} else {
	    datum.setSubject("");
	}

	return datum;
    }
}
