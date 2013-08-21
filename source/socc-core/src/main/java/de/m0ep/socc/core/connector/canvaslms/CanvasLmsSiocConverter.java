
package de.m0ep.socc.core.connector.canvaslms;

import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.node.Node;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.util.Builder;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.Site;
import org.rdfs.sioc.Thread;
import org.rdfs.sioc.UserAccount;
import org.rdfs.sioc.services.Thing;

import com.damnhandy.uri.template.UriTemplate;
import com.xmlns.foaf.Person;

import de.m0ep.canvas.model.Attachment;
import de.m0ep.canvas.model.Course;
import de.m0ep.canvas.model.DiscussionTopic;
import de.m0ep.canvas.model.Entry;
import de.m0ep.socc.core.exceptions.NotFoundException;
import de.m0ep.socc.core.utils.DateUtils;
import de.m0ep.socc.core.utils.SiocUtils;
import de.m0ep.socc.core.utils.StringUtils;
import de.m0ep.socc.core.utils.UserAccountUtils;

public final class CanvasLmsSiocConverter {

    private CanvasLmsSiocConverter() {
    }

    public static Forum createSiocForum(CanvasLmsConnector connector,
            Course course) {
        URI serviceEndpoint = connector.getService().getServiceEndpoint()
                .asURI();
        URI uri = createSiocForumUri(serviceEndpoint, course.getId());

        if (!Forum.hasInstance(connector.getContext().getModel(), uri)) {
            Forum result = new Forum(connector.getContext().getModel(), uri,
                    true);

            result.setId(Long.toString(course.getId()));
            result.setName(course.getName());
            result.setNumThreads(0);

            Site site = connector.getStructureReader().getSite();
            result.setHost(site);
            site.addHostOf(result);
        }

        return Forum.getInstance(connector.getContext().getModel(), uri);
    }

    public static Thread createSiocThread(CanvasLmsConnector connector,
            DiscussionTopic discussionTopic, Forum parent) {
        Model model = connector.getContext().getModel();
        URI serviceEndpoint = connector.getService()
                .getServiceEndpoint()
                .asURI();

        long courseId;
        try {
            courseId = Long.parseLong(parent.getId());
        } catch (NumberFormatException e1) {
            throw new IllegalArgumentException(
                    "The parent has an invalid id: was "
                            + parent.getId());
        }

        URI uri = createSiocThreadUri(
                serviceEndpoint,
                courseId,
                discussionTopic.getId());

        if (!Thread.hasInstance(model, uri)) {
            Thread result = new Thread(model, uri,
                    true);
            result.setId(Long.toString(discussionTopic.getId()));
            result.setName(discussionTopic.getTitle());
            result.setNumItems(0);

            result.setParent(parent);
            parent.addParentOf(result);
            SiocUtils.incNumThreads(parent);

            Post initPost = createTopicInitialPost(
                    connector,
                    result,
                    discussionTopic,
                    courseId);

            initPost.setContainer(result);
            result.setContainerOf(initPost);
            SiocUtils.incNumItems(result);

            return result;
        }

        return Thread.getInstance(model, uri);
    }

    public static Post createTopicInitialPost(CanvasLmsConnector connector,
            Container container, DiscussionTopic discussionTopic, long courseId) {
        Model model = connector.getContext().getModel();
        URI serviceEndpoint = connector.getService()
                .getServiceEndpoint()
                .asURI();

        UserAccount creator = null;
        try {
            String accountName = Long.toString(discussionTopic.getAuthor()
                    .getId());
            creator = UserAccountUtils.findUserAccount(
                    model,
                    accountName,
                    serviceEndpoint);
        } catch (NotFoundException e) {
            creator = createSiocUserAccountFromAuthor(connector,
                    discussionTopic.getAuthor());
        }

        String content = StringUtils.stripHTML(discussionTopic.getMessage());
        String createdDate = DateUtils.formatISO8601(
                discussionTopic.getPostedAt());

        URI initPostUri = createTopicPostUri(
                serviceEndpoint,
                courseId,
                discussionTopic.getId());

        Post result = new Post(model, initPostUri, true);
        result.setId(Long.toString(discussionTopic.getId()));
        result.setTitle(discussionTopic.getTitle());
        result.setContent(content);
        result.setCreator(creator);
        result.setCreated(createdDate);
        result.setNumReplies(0);

        for (Attachment attachment : discussionTopic.getAttachments()) {
            result.addAttachment(Builder.createURI(attachment.getUrl()));
        }

        // update container relation.
        result.setContainer(container);
        container.addContainerOf(result);
        SiocUtils.incNumItems(container);
        SiocUtils.updateLastItemDate(container, discussionTopic.getPostedAt());

        return result;
    }

    public static Post createSiocPost(CanvasLmsConnector connector,
            Entry entry, Container container, Post parentPost) {
        Model model = connector.getContext().getModel();
        URI serviceEndpoint = connector.getService()
                .getServiceEndpoint()
                .asURI();

        Container containerParent = container.getParent();

        long courseId;
        try {
            courseId = Long.parseLong(containerParent.getId());
        } catch (NumberFormatException e1) {
            throw new IllegalArgumentException(
                    "The parent of the container has an invalid id: was "
                            + containerParent.getId());
        }

        long discussionId;
        try {
            discussionId = Long.parseLong(container.getId());
        } catch (NumberFormatException e1) {
            throw new IllegalArgumentException(
                    "The  container has an invalid id: was "
                            + containerParent.getId());
        }

        URI uri = createSiocPostUri(
                serviceEndpoint,
                courseId,
                discussionId,
                entry.getId());

        String content = StringUtils.stripHTML(entry.getMessage());

        Post result = null;
        if (Post.hasInstance(model, uri)) {
            result = Post.getInstance(model, uri);

            Node modified = Builder.createPlainliteral(
                    DateUtils.formatISO8601(
                            entry.getUpdatedAt()));

            if (!result.hasModified(modified)) {
                result.setContent(content);
                result.setModified(modified);

                if (null != entry.getAttachment()) {
                    result.removeAllAttachments();
                    result.addAttachment(Builder.createURI(entry
                            .getAttachment()
                            .getUrl()));
                }
            }
        } else {
            UserAccount creator = null;
            try {
                String accountName = Long.toString(entry.getUserId());
                creator = UserAccountUtils.findUserAccount(
                        model,
                        accountName,
                        serviceEndpoint);
            } catch (NotFoundException e) {
                creator = createSiocUserAccountFromEntry(connector, entry);
            }

            result = new Post(model, uri, true);
            result.setId(Long.toString(entry.getId()));
            result.setCreator(creator);
            result.setCreated(DateUtils.formatISO8601(entry.getCreatedAt()));
            result.setContent(content);
            result.setNumReplies(0);

            if (null != entry.getAttachment()) {
                result.addAttachment(Builder.createURI(entry.getAttachment()
                        .getUrl()));
            }

            // update container relation.
            result.setContainer(container);
            container.addContainerOf(result);
            SiocUtils.incNumItems(container);
            SiocUtils.updateLastItemDate(container, entry.getCreatedAt());

            if (null != parentPost) {
                result.setReplyOf(parentPost);
                parentPost.addReply(result);
                SiocUtils.incNumReplies(parentPost);
                SiocUtils.updateLastReplyDate(parentPost, entry.getCreatedAt());
            }
        }

        return result;
    }

    public static UserAccount createSiocUserAccountFromAuthor(
            CanvasLmsConnector connector,
            DiscussionTopic.Author author) {
        Model model = connector.getContext().getModel();
        URI serviceEndpoint = connector.getService().getServiceEndpoint()
                .asURI();
        URI userUri = createSiocUserAccountUri(
                serviceEndpoint,
                author.getId());

        UserAccount result = new UserAccount(model, userUri, true);

        result.setId(Long.toString(author.getId()));
        result.setName(author.getDisplayName());
        result.setAccountName(Long.toString(author.getId()));
        result.setAccountServiceHomepage(serviceEndpoint);
        Thing.setService(
                result.getModel(),
                result.getResource(),
                connector.getService());

        // create a new Person for unknown account.
        Person person = new Person(model, true);
        person.setNickname(Builder.createPlainliteral(author.getDisplayName()));
        person.addAccount(result);
        result.setAccountOf(person);

        return result;
    }

    public static UserAccount createSiocUserAccountFromEntry(
            CanvasLmsConnector connector,
            Entry entry) {
        Model model = connector.getContext().getModel();
        URI serviceEndpoint = connector.getService().getServiceEndpoint()
                .asURI();
        URI userUri = createSiocUserAccountUri(
                serviceEndpoint,
                entry.getUserId());

        UserAccount result = new UserAccount(model, userUri, true);

        result.setId(Long.toString(entry.getUserId()));
        result.setName(entry.getUserName());
        result.setAccountName(Long.toString(entry.getUserId()));
        result.setAccountServiceHomepage(serviceEndpoint);
        Thing.setService(
                result.getModel(),
                result.getResource(),
                connector.getService());

        // create a new Person for unknown account.
        Person person = new Person(model, true);
        person.setNickname(Builder.createPlainliteral(entry.getUserName()));
        person.addAccount(result);
        result.setAccountOf(person);

        return result;
    }

    public static URI createSiocForumUri(final URI rootUri, final long courseId) {
        return Builder.createURI(
                UriTemplate.fromTemplate(rootUri + "/courses/{courseId}")
                        .set("courseId", courseId)
                        .expand());
    }

    public static URI createSiocThreadUri(final URI rootUri,
            final long courseId, final long discussionId) {
        return Builder
                .createURI(
                UriTemplate.fromTemplate(
                        rootUri
                                + "/courses/{courseId}"
                                + "/discussion_topics/{discussionId}")
                        .set("courseId", courseId)
                        .set("discussionId", discussionId)
                        .expand());
    }

    public static URI createTopicPostUri(final URI rootUri,
            final long courseId, final long discussionId) {
        return Builder
                .createURI(
                UriTemplate
                        .fromTemplate(
                                rootUri
                                        + "/courses/{courseId}"
                                        + "/discussion_topics/{discussionId}#discussion_topic")
                        .set("courseId", courseId)
                        .set("discussionId", discussionId)
                        .expand());
    }

    public static URI createSiocPostUri(final URI rootUri, final long courseId,
            final long discussionId, final long entryId) {
        return Builder
                .createURI(
                UriTemplate.fromTemplate(
                        rootUri
                                + "/courses/{courseId}"
                                + "/discussion_topics/{discussionId}"
                                + "/entries/{entryId}")
                        .set("courseId", courseId)
                        .set("discussionId", discussionId)
                        .set("entryId", entryId)
                        .expand());
    }

    public static URI createSiocUserAccountUri(final URI rootUri,
            final long userId) {
        return Builder.createURI(
                UriTemplate.fromTemplate(rootUri + "/about/{userId}")
                        .set("userId", userId)
                        .expand());
    }
}
