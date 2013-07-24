
package de.m0ep.socc.core.connector.canvaslms;

import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.util.Builder;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.Site;
import org.rdfs.sioc.Thread;
import org.rdfs.sioc.UserAccount;

import com.xmlns.foaf.Person;

import de.m0ep.canvas.model.Attachment;
import de.m0ep.canvas.model.Course;
import de.m0ep.canvas.model.DiscussionTopic;
import de.m0ep.canvas.model.Entry;
import de.m0ep.socc.core.exceptions.NotFoundException;
import de.m0ep.socc.core.utils.DateUtils;
import de.m0ep.socc.core.utils.StringUtils;
import de.m0ep.socc.core.utils.UserAccountUtils;

public final class CanvasLmsSiocConverter {

    private CanvasLmsSiocConverter() {
    }

    public static Forum createSiocForum(CanvasLmsConnector connector, Course course) {
        URI serviceEndpoint = connector.getService().getServiceEndpoint().asURI();
        URI uri = Builder.createURI(serviceEndpoint.toString() + "/forum/" + course.getId());

        Forum result = null;

        if (!Forum.hasInstance(connector.getContext().getModel(), uri)) {
            result = new Forum(connector.getContext().getModel(), uri, true);

            result.setId(Long.toString(course.getId()));
            result.setNumThreads(0);
            result.setName(course.getName());

            Site site = connector.serviceStructureReader().getSite();
            result.setHost(site);
            site.addHostOf(result);
        }

        return Forum.getInstance(connector.getContext().getModel(), uri);
    }

    public static Thread createSiocThread(CanvasLmsConnector connector,
            DiscussionTopic discussionTopic, Forum parent) {

        URI serviceEndpoint = connector.getService().getServiceEndpoint().asURI();
        URI uri = Builder.createURI(
                serviceEndpoint.toString() + "/thread/" + discussionTopic.getId());

        if (!Thread.hasInstance(connector.getContext().getModel(), uri)) {
            Thread result = new Thread(connector.getContext().getModel(), uri, true);
            result.setId(Long.toString(discussionTopic.getId()));
            result.setName(discussionTopic.getTitle());
            result.setTitle(discussionTopic.getTitle());
            result.setSubject(discussionTopic.getTitle());
            result.setCreated(DateUtils.formatISO8601(discussionTopic.getPostedAt()));
            result.setSeeAlso(Builder.createURI(discussionTopic.getHtmlURL()));

            result.setParent(parent);
            parent.addParentOf(result);
            parent.setNumThreads((parent.hasNumThreads()) ? (parent.getNumThreads() + 1) : (1));

            /*
             * Create a SIOC Post for the initial post. Because it has no own id
             * "thread-{thread_id}-initialpost" will be used.
             */

            // check if we already know the author, else create a new
            // UserAccount + Person
            UserAccount creator = null;
            try {
                String accountName = Long.toString(discussionTopic.getAuthor().getId());
                creator = UserAccountUtils.findUserAccount(
                        connector.getContext().getModel(),
                        accountName,
                        serviceEndpoint);
            } catch (NotFoundException e) {
                creator = createSiocUserAccountFromAuthor(connector, discussionTopic.getAuthor());
            }

            String initPostId = "thread-" + discussionTopic.getId() + "-initialPost";
            URI initPostUri = Builder.createURI(
                    serviceEndpoint.toString()
                            + "/post/"
                            + initPostId);
            Post initPost = new Post(connector.getContext().getModel(), initPostUri, true);
            initPost.setId(initPostId);
            initPost.setTitle(discussionTopic.getTitle());
            initPost.setSubject(discussionTopic.getTitle());
            initPost.setContent(StringUtils.stripHTML(discussionTopic.getMessage()));
            initPost.setContentEncoded(discussionTopic.getMessage());
            initPost.setCreator(creator);
            initPost.setCreated(DateUtils.formatISO8601(discussionTopic.getPostedAt()));

            for (Attachment attachment : discussionTopic.getAttachments()) {
                initPost.addAttachment(Builder.createURI(attachment.getUrl()));
            }

            initPost.setContainer(result);
            result.addContainerOf(initPost);
            result.setNumItems(1);

            return result;
        }

        return Thread.getInstance(connector.getContext().getModel(), uri);
    }

    private static UserAccount createSiocUserAccountFromAuthor(
            CanvasLmsConnector connector,
            DiscussionTopic.Author author) {
        URI serviceEndpoint = connector.getService().getServiceEndpoint().asURI();
        URI userUri = Builder.createURI(
                serviceEndpoint.toString()
                        + "/user/"
                        + author.getId());

        UserAccount result = new UserAccount(connector.getContext().getModel(), userUri, true);
        result.setId(Long.toString(author.getId()));
        result.setAccountName(Long.toString(author.getId()));
        result.setAccountServiceHomepage(serviceEndpoint);
        result.addSeeAlso(Builder.createURI(author.getHtmlUrl()));

        // create a new Person for unknown account.
        Person person = new Person(connector.getContext().getModel(), true);
        person.setNickname(Builder.createPlainliteral(author.getDisplayName()));
        person.setDepiction(Builder.createURI(author.getAvatarIamgeUrl()));
        person.addAccount(result);
        result.setAccountOf(person);

        return result;
    }

    public static Post createSiocPost(CanvasLmsConnector connector, Entry entry, Container container) {
        URI serviceEndpoint = connector.getService().getServiceEndpoint().asURI();
        URI uri = Builder.createURI(
                serviceEndpoint.toString()
                        + "/post/"
                        + entry.getId());

        Post result = null;
        if (Post.hasInstance(connector.getContext().getModel(), uri)) {
            result = Post.getInstance(connector.getContext().getModel(), uri);
        } else {
            UserAccount creator = null;
            try {
                String accountName = Long.toString(entry.getUserId());
                creator = UserAccountUtils.findUserAccount(
                        connector.getContext().getModel(),
                        accountName,
                        serviceEndpoint);
            } catch (NotFoundException e) {
                creator = createSiocUserAccountFromEntry(connector, entry);
            }

            result = new Post(connector.getContext().getModel(), uri, true);
            result.setId(Long.toString(entry.getId()));
            result.setCreator(creator);
            result.setCreated(DateUtils.formatISO8601(entry.getCreatedAt()));

            if (null != entry.getAttachment()) {
                result.addAttachment(Builder.createURI(entry.getAttachment().getUrl()));
            }

            // update container relation.
            result.setContainer(container);
            container.addContainerOf(result);
            container.setNumItems((container.hasNumItems()) ? (container.getNumItems() + 1) : (1));
        }

        result.setContent(StringUtils.stripHTML(entry.getMessage()));
        result.setContentEncoded(entry.getMessage());
        result.setModified(DateUtils.formatISO8601(entry.getUpdatedAt()));

        return result;
    }

    private static UserAccount createSiocUserAccountFromEntry(CanvasLmsConnector connector,
            Entry entry) {
        URI serviceEndpoint = connector.getService().getServiceEndpoint().asURI();
        URI userUri = Builder.createURI(
                serviceEndpoint.toString()
                        + "/user/"
                        + entry.getUserId());

        UserAccount result = new UserAccount(connector.getContext().getModel(), userUri, true);
        result.setId(Long.toString(entry.getUserId()));
        result.setAccountName(Long.toString(entry.getUserId()));
        result.setAccountServiceHomepage(serviceEndpoint);

        // create a new Person for unknown account.
        Person person = new Person(connector.getContext().getModel(), true);
        person.setNickname(Builder.createPlainliteral(entry.getUserName()));
        person.addAccount(result);
        result.setAccountOf(person);

        return result;
    }
}
