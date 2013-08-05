
package de.m0ep.socc.core.connector.moodle;

import java.io.IOException;
import java.util.concurrent.Callable;

import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.util.Builder;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.Site;
import org.rdfs.sioc.Thread;
import org.rdfs.sioc.UserAccount;

import com.google.common.base.Strings;
import com.xmlns.foaf.Person;

import de.m0ep.moodlews.soap.ForumDiscussionRecord;
import de.m0ep.moodlews.soap.ForumPostRecord;
import de.m0ep.moodlews.soap.ForumRecord;
import de.m0ep.moodlews.soap.UserRecord;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.exceptions.NotFoundException;
import de.m0ep.socc.core.utils.DateUtils;
import de.m0ep.socc.core.utils.StringUtils;
import de.m0ep.socc.core.utils.UserAccountUtils;

public class Moodle2SiocConverter {

    public static Forum createSiocForum(Moodle2Connector connector, ForumRecord forumRecord) {
        System.out.println(connector);
        System.out.println(connector.getService());
        System.out.println(connector.getService().getServiceEndpoint());
        URI serviceEndpoint = connector.getService().getServiceEndpoint().asURI();
        URI uri = Builder.createURI(serviceEndpoint.toString()
                + "/forum/"
                + forumRecord.getId());

        if (Forum.hasInstance(connector.getContext().getModel(), uri)) {
            return Forum.getInstance(connector.getContext().getModel(), uri);
        }

        Forum result = new Forum(connector.getContext().getModel(), uri, true);
        result.setId(Integer.toString(forumRecord.getId()));
        result.setName("Course (id=" + forumRecord.getCourse() + ")/" + forumRecord.getName());
        result.setDescription(forumRecord.getIntro());

        Site site = connector.getStructureReader().getSite();
        result.setHost(site);
        site.addHostOf(result);

        return result;
    }

    public static Thread createSiocThread(Moodle2Connector connector,
            ForumDiscussionRecord discussionRecord, Forum parent) {
        URI serviceEndpoint = connector.getService().getServiceEndpoint().asURI();
        URI uri = Builder.createURI(serviceEndpoint.toString()
                + "/thread/"
                + discussionRecord.getId());

        if (!Thread.hasInstance(connector.getContext().getModel(), uri)) {
            Thread result = new Thread(connector.getContext().getModel(), uri, true);
            result.setId(Integer.toString(discussionRecord.getId()));
            result.setName(discussionRecord.getName());
            result.setTitle(discussionRecord.getName());
            result.setSubject(discussionRecord.getName());

            result.setParent(parent);
            parent.addParentOf(result);
            parent.setNumThreads((parent.hasNumThreads()) ? (parent.getNumThreads() + 1) : (1));
            return result;
        }

        return Thread.getInstance(connector.getContext().getModel(), uri);
    }

    public static Post createSiocPost(Moodle2Connector connector, ForumPostRecord postRecord,
            Container container) {
        URI serviceEndpoint = connector.getService().getServiceEndpoint().asURI();
        URI uri = Builder.createURI(serviceEndpoint.toString()
                + "/post/"
                + postRecord.getId());

        Post result;
        if (Post.hasInstance(connector.getContext().getModel(), uri)) {
            result = Post.getInstance(connector.getContext().getModel(), uri);
        } else {
            UserAccount creator = null;
            try {
                String accountName = Integer.toString(postRecord.getUserid());
                creator = UserAccountUtils.findUserAccount(
                        connector.getContext().getModel(),
                        accountName,
                        serviceEndpoint);
            } catch (NotFoundException e) {
                try {
                    creator = createSiocUserAccount(connector, postRecord.getUserid());
                } catch (AuthenticationException | IOException e1) {
                    // TODO: proper exception handling
                    throw new RuntimeException(e1);
                }
            }

            result = new Post(connector.getContext().getModel(), uri, true);
            result.setId(Integer.toString(postRecord.getId()));
            result.setCreator(creator);
            result.setCreated(DateUtils.formatISO8601(postRecord.getCreated() * 1000L));

            // update container relation.
            result.setContainer(container);
            container.addContainerOf(result);
            container.setNumItems((container.hasNumItems()) ? (container.getNumItems() + 1) : (1));
        }

        result.setModified(DateUtils.formatISO8601(postRecord.getModified() * 1000L));
        result.setContent(StringUtils.stripHTML(postRecord.getMessage()));
        result.setContentEncoded(postRecord.getMessage());

        return result;
    }

    public static UserAccount createSiocUserAccount(final Moodle2Connector connector,
            final int userid)
            throws AuthenticationException, IOException {
        final Moodle2ClientWrapper client = (Moodle2ClientWrapper) connector
                .getServiceClientManager()
                .getDefaultClient();

        UserRecord[] userRecords = client.callMethod(new Callable<UserRecord[]>() {
            @Override
            public UserRecord[] call() throws Exception {
                return client.getBindingStub().get_user_byid(
                        client.getAuthClient(),
                        client.getSessionKey(),
                        userid);
            }
        });

        if (null != userRecords && 0 < userRecords.length) {
            UserRecord userRecord = userRecords[0];

            URI uri = Builder.createURI(
                    connector.getService().getServiceEndpoint().toString()
                            + "/user/"
                            + userRecord.getId());

            UserAccount userAccount = new UserAccount(connector.getContext().getModel(), uri, true);
            userAccount.setId(Integer.toString(userRecord.getId()));
            userAccount.setAccountName(Integer.toString(userRecord.getId()));
            userAccount.setAccountServiceHomepage(connector.getService().getServiceEndpoint());

            Person person = new Person(connector.getContext().getModel(), true);
            person.setNickname(userRecord.getUsername());
            if (null != userRecord.getName()) {
                person.setName(userRecord.getName());
            } else {
                person.setName(
                        (Strings.nullToEmpty(userRecord.getFirstname())
                                + " "
                                + Strings.nullToEmpty(userRecord.getLastname())).trim());
            }

            if (null != userRecord.getFirstname()) {
                person.setFirstName(userRecord.getFirstname());
            }

            if (null != userRecord.getLastname()) {
                person.setLastName(userRecord.getLastname());
            }

            userAccount.setAccountOf(person);
            person.setAccount(userAccount);

            return userAccount;

        }

        throw new IOException("Failed to read user data");
    }
}
