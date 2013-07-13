
package de.m0ep.socc.core.connector.canvaslms;

import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.util.Builder;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.Site;
import org.rdfs.sioc.Thread;
import org.rdfs.sioc.UserAccount;

import com.xmlns.foaf.Person;

import de.m0ep.canvas.model.Attachment;
import de.m0ep.canvas.model.Course;
import de.m0ep.canvas.model.DiscussionTopic;
import de.m0ep.socc.core.exceptions.NotFoundException;
import de.m0ep.socc.core.user.IUserDataService;
import de.m0ep.socc.core.utils.DateUtils;
import de.m0ep.socc.core.utils.StringUtils;

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

            Site site = connector.getServiceStructureReader().getSite();
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
            result.setCreated(DateUtils.formatISO8601(discussionTopic.getPostedAt()));
            result.setSeeAlso(Builder.createURI(discussionTopic.getHtmlURL()));

            result.setParent(parent);
            parent.addParentOf(result);
            parent.setNumThreads((parent.hasNumThreads()) ? (parent.getNumThreads() + 1) : (1));

            /*
             * Create a SIOC Post for the initial post. Because it has no own id
             * "thread/{thread_id}/initialpost" will be used.
             */

            // check if we already know the author, else create a new
            // UserAccount + Person
            UserAccount creator = null;
            try {
                IUserDataService userDataService = connector.getContext().getUserDataService();
                String accountName = Long.toString(discussionTopic.getAuthor().getId());

                if (null != userDataService) {
                    creator = userDataService.findUserAccount(
                            accountName,
                            serviceEndpoint);

                } else {
                    throw new NotFoundException(
                            "User "
                                    + accountName
                                    + " not found, create new UserAccount");
                }
            } catch (NotFoundException e) {
                creator = createSiocUserAccountFromAuthor(connector, discussionTopic.getAuthor());
            }

            URI initPostUri = Builder.createURI(
                    serviceEndpoint.toString()
                            + "/thread/"
                            + discussionTopic.getId()
                            + "/initialPost");
            Post initPost = new Post(connector.getContext().getModel(), initPostUri, true);
            initPost.setId("thread/" + discussionTopic.getId() + "/initialPost");
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
}