
package de.m0ep.socc.core.connector.facebook;

import java.util.Date;

import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.util.Builder;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.Site;
import org.rdfs.sioc.UserAccount;

import com.google.common.base.Preconditions;
import com.restfb.json.JsonObject;
import com.restfb.types.Group;
import com.xmlns.foaf.Person;

import de.m0ep.socc.core.exceptions.NotFoundException;
import de.m0ep.socc.core.utils.DateUtils;
import de.m0ep.socc.core.utils.StringUtils;
import de.m0ep.socc.core.utils.UserAccountUtils;

public class FacebookSiocConverter {
    static final String CONNECTION_COMMENTS = "comments";
    static final String CONNECTION_FEED = "feed";

    static final String FIELD_CAPTION = "caption";
    static final String FIELD_COMMENTS = "comments";
    static final String FIELD_CONNECTIONS = "connections";
    static final String FIELD_CREATED_TIME = "created_time";
    static final String FIELD_DATA = "data";
    static final String FIELD_DESCRIPTION = "description";
    static final String FIELD_FROM = "from";
    static final String FIELD_ID = "id";
    static final String FIELD_LINK = "link";
    static final String FIELD_MESSAGE = "message";
    static final String FIELD_METADATA = "metadata";
    static final String FIELD_NAME = "name";
    static final String FIELD_SOURCE = "source";
    static final String FIELD_STORY = "story";
    static final String FIELD_TYPE = "type";
    static final String FIELD_UPDATED_TIME = "updated_time";

    public static Forum createSiocForum(FacebookConnector connector, Group group) {
        URI serviceEndpoint = connector.getService().getServiceEndpoint().asURI();
        Model model = connector.getContext().getModel();

        URI uri = Builder.createURI(serviceEndpoint + "/group/" + group.getId());

        Forum result;
        if (Forum.hasInstance(model, uri)) {
            result = Forum.getInstance(model, uri);
        } else {
            result = new Forum(model, uri, true);
            result.setId("group:" + group.getId());

            Site site = connector.serviceStructureReader().getSite();
            result.setHost(site);
            site.setHostOf(result);
        }

        result.setName(group.getName());

        if (null != group.getDescription()) {
            result.setDescription(group.getDescription());
        }

        if (null != group.getUpdatedTime()) {
            result.setModified(DateUtils.formatISO8601(group.getUpdatedTime()));
        }

        return result;
    }

    public static Post createSiocPost(FacebookConnector connector, JsonObject object,
            Container container) {
        Preconditions.checkNotNull(connector,
                "Required parameter connector must be specified.");
        Preconditions.checkNotNull(object,
                "Required parameter object must be specified.");
        Preconditions.checkNotNull(container,
                "Required parameter container must be specified.");
        Preconditions.checkArgument(object.has("id"),
                "JSON object contains no id.");

        Model model = connector.getContext().getModel();
        URI serviceEndpoint = connector.getService().getServiceEndpoint().asURI();
        URI uri = Builder.createURI(
                serviceEndpoint
                        + "/post/"
                        + object.getString("id"));

        Post result = (Post.hasInstance(model, uri)) ?
                (Post.getInstance(model, uri)) :
                (new Post(model, uri, true));

        if (object.has(FIELD_ID)) {
            result.setId("post:" + object.getString(FIELD_ID));
        }

        if (object.has(FIELD_FROM)) {
            String creatorId = object.getJsonObject(FIELD_FROM).getString(FIELD_ID);

            // check if we already know the author, else create a new
            // UserAccount + Person
            UserAccount creator = null;
            try {
                creator = UserAccountUtils.findUserAccount(
                        connector.getContext().getModel(),
                        creatorId,
                        serviceEndpoint);
            } catch (NotFoundException e) {
                creator = createSiocUserAccount(
                        connector,
                        object.getJsonObject(FIELD_FROM));
            }

            result.setCreator(creator);
        }

        String content = "";
        if (object.has(FIELD_STORY)) {
            content = object.getString(FIELD_STORY);
        } else if (object.has(FIELD_MESSAGE)) {
            content = object.getString(FIELD_MESSAGE);
        }

        result.setContent(StringUtils.stripHTML(content));

        if (object.has(FIELD_NAME)) {
            result.setName(object.getString(FIELD_NAME));
        }

        if (object.has(FIELD_CAPTION)) {
            result.setTitle(FIELD_CAPTION);
        }

        if (object.has(FIELD_DESCRIPTION)) {
            result.setDescription(object
                    .getString(FIELD_DESCRIPTION));
        }

        if (object.has(FIELD_LINK)) {
            result.setAttachment(
                    Builder.createURI(
                            object.getString(FIELD_LINK)));
        } else if (object.has(FIELD_SOURCE)) {
            result.setAttachment(
                    Builder.createURI(
                            object.getString(FIELD_SOURCE)));
        }

        if (object.has(FIELD_CREATED_TIME)) {
            Date date = com.restfb.util.DateUtils.toDateFromLongFormat(
                    object.getString(FIELD_CREATED_TIME));
            result.setCreated(DateUtils.formatISO8601(date));
        }

        if (object.has(FIELD_UPDATED_TIME)) {
            Date date = com.restfb.util.DateUtils.toDateFromLongFormat(
                    object.getString(FIELD_UPDATED_TIME));
            result.setModified(DateUtils.formatISO8601(date));
        }

        result.setContainer(container);
        container.addContainerOf(result);
        container.setNumItems(
                (container.hasNumItems()) ?
                        (container.getNumItems() + 1) :
                        (1));

        return result;
    }

    private static UserAccount createSiocUserAccount(FacebookConnector connector,
            JsonObject jsonObject) {
        String userId = jsonObject.getString(FIELD_ID);
        URI serviceEndpoint = connector.getService().getServiceEndpoint().asURI();
        URI userUri = Builder.createURI(
                serviceEndpoint.toString()
                        + "/user/"
                        + userId);

        UserAccount result = new UserAccount(connector.getContext().getModel(), userUri, true);
        result.setId(userId);
        result.setAccountName(userId);
        result.setAccountServiceHomepage(serviceEndpoint);
        result.addSeeAlso(Builder.createURI(serviceEndpoint + "/" + userId));

        // create a new Person for unknown account.
        Person person = new Person(connector.getContext().getModel(), true);
        person.setName(jsonObject.getString(FIELD_NAME));
        person.addAccount(result);
        result.setAccountOf(person);

        return result;
    }

    public static Post createSiocComment(FacebookConnector connector, JsonObject object,
            Post parentPost) {
        // TODO Auto-generated method stub
        return null;
    }
}
