/*
 * The MIT License (MIT) Copyright © 2013 Florian Müller
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the “Software”), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
import com.restfb.types.User;
import com.xmlns.foaf.Person;

import de.m0ep.socc.core.exceptions.NotFoundException;
import de.m0ep.socc.core.utils.DateUtils;
import de.m0ep.socc.core.utils.StringUtils;
import de.m0ep.socc.core.utils.UserAccountUtils;

public class FacebookSiocConverter {
    static final String ID_SEPERATOR = ":";
    static final String WALL_URI_PATH = "/wall/";
    static final String WALL_ID_PREFIX = "wall" + ID_SEPERATOR;
    static final String GROUP_URI_PATH = "/group/";
    static final String GROUP_ID_PREFIX = "group" + ID_SEPERATOR;
    static final String POST_URI_PATH = "/post/";
    static final String POST_ID_PREFIX = "post" + ID_SEPERATOR;
    static final String USER_URI_PATH = "/user/";
    static final String COMMENT_ID_PREFIX = "comment" + ID_SEPERATOR;
    static final String COMMENT_URI_PATH = "/comment/";

    public static Forum createSiocForum(FacebookConnector connector, User user) {
        URI serviceEndpoint = connector.getService().getServiceEndpoint().asURI();
        Model model = connector.getContext().getModel();
        URI uri = Builder.createURI(
                serviceEndpoint
                        + FacebookSiocConverter.WALL_URI_PATH
                        + user.getId());

        if (Forum.hasInstance(model, uri)) {
            return Forum.getInstance(model, uri);
        }

        Forum result = new Forum(model, uri, true);
        result.setId(FacebookSiocConverter.WALL_ID_PREFIX + user.getId());
        result.setName(user.getName() + "'s Wall");

        Site site = connector.getStructureReader().getSite();
        result.setHost(site);
        site.setHostOf(result);
        result.setNumItems(0);

        return result;
    }

    public static Forum createSiocForum(FacebookConnector connector, Group group) {
        URI serviceEndpoint = connector.getService().getServiceEndpoint().asURI();
        Model model = connector.getContext().getModel();

        URI uri = Builder.createURI(serviceEndpoint + GROUP_URI_PATH + group.getId());

        Forum result;
        if (Forum.hasInstance(model, uri)) {
            result = Forum.getInstance(model, uri);
        } else {
            result = new Forum(model, uri, true);
            result.setId(GROUP_ID_PREFIX + group.getId());

            Site site = connector.getStructureReader().getSite();
            result.setHost(site);
            site.setHostOf(result);
        }

        result.setName(group.getName());

        if (null != group.getDescription()) {
            result.setDescription(group.getDescription());
        }

        Site site = connector.getStructureReader().getSite();
        result.setHost(site);
        site.addHostOf(result);
        result.setNumItems(0);

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
                        + POST_URI_PATH
                        + object.getString(FacebookApiConstants.FIELD_ID));

        Post result = (Post.hasInstance(model, uri)) ?
                (Post.getInstance(model, uri)) :
                (new Post(model, uri, true));

        if (object.has(FacebookApiConstants.FIELD_ID)) {
            result.setId(POST_ID_PREFIX + object.getString(FacebookApiConstants.FIELD_ID));
        }

        if (object.has(FacebookApiConstants.FIELD_FROM)) {
            String creatorId = object.getJsonObject(
                    FacebookApiConstants.FIELD_FROM)
                    .getString(FacebookApiConstants.FIELD_ID);

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
                        object.getJsonObject(FacebookApiConstants.FIELD_FROM));
            }

            result.setCreator(creator);
        }

        String content = "";
        if (object.has(FacebookApiConstants.FIELD_STORY)) {
            content = object.getString(FacebookApiConstants.FIELD_STORY);
        } else if (object.has(FacebookApiConstants.FIELD_MESSAGE)) {
            content = object.getString(FacebookApiConstants.FIELD_MESSAGE);
        }

        result.setContent(StringUtils.stripHTML(content));

        if (object.has(FacebookApiConstants.FIELD_NAME)) {
            result.setName(object.getString(FacebookApiConstants.FIELD_NAME));
        }

        if (object.has(FacebookApiConstants.FIELD_CAPTION)) {
            result.setTitle(FacebookApiConstants.FIELD_CAPTION);
        }

        if (object.has(FacebookApiConstants.FIELD_DESCRIPTION)) {
            result.setDescription(object
                    .getString(FacebookApiConstants.FIELD_DESCRIPTION));
        }

        if (object.has(FacebookApiConstants.FIELD_LINK)) {
            result.setAttachment(
                    Builder.createURI(
                            object.getString(FacebookApiConstants.FIELD_LINK)));
        } else if (object.has(FacebookApiConstants.FIELD_SOURCE)) {
            result.setAttachment(
                    Builder.createURI(
                            object.getString(FacebookApiConstants.FIELD_SOURCE)));
        }

        if (object.has(FacebookApiConstants.FIELD_CREATED_TIME)) {
            Date date = com.restfb.util.DateUtils.toDateFromLongFormat(
                    object.getString(FacebookApiConstants.FIELD_CREATED_TIME));
            result.setCreated(DateUtils.formatISO8601(date));
        }

        if (object.has(FacebookApiConstants.FIELD_UPDATED_TIME)) {
            Date date = com.restfb.util.DateUtils.toDateFromLongFormat(
                    object.getString(FacebookApiConstants.FIELD_UPDATED_TIME));
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
        String userId = jsonObject.getString(FacebookApiConstants.FIELD_ID);
        URI serviceEndpoint = connector.getService().getServiceEndpoint().asURI();
        URI userUri = Builder.createURI(
                serviceEndpoint.toString()
                        + USER_URI_PATH
                        + userId);

        UserAccount result = new UserAccount(connector.getContext().getModel(), userUri, true);
        result.setId(userId);
        result.setAccountName(userId);
        result.setAccountServiceHomepage(serviceEndpoint);
        result.addSeeAlso(Builder.createURI(serviceEndpoint + "/" + userId));

        // create a new Person for unknown account.
        Person person = new Person(connector.getContext().getModel(), true);
        person.setName(jsonObject.getString(FacebookApiConstants.FIELD_NAME));
        person.addAccount(result);
        result.setAccountOf(person);

        return result;
    }

    public static Post createSiocComment(FacebookConnector connector, JsonObject object,
            Post parentPost) {

        Model model = connector.getContext().getModel();
        URI serviceEndpoint = connector.getService().getServiceEndpoint().asURI();
        URI uri = Builder.createURI(
                serviceEndpoint
                        + COMMENT_URI_PATH
                        + object.getString(FacebookApiConstants.FIELD_ID));

        Post result = (Post.hasInstance(model, uri)) ?
                (Post.getInstance(model, uri)) :
                (new Post(model, uri, true));

        if (object.has(FacebookApiConstants.FIELD_ID)) {
            result.setId(COMMENT_ID_PREFIX + object.getString(FacebookApiConstants.FIELD_ID));
        }

        if (object.has(FacebookApiConstants.FIELD_FROM)) {
            JsonObject from = object.getJsonObject(FacebookApiConstants.FIELD_FROM);
            String creatorId = from.getString(FacebookApiConstants.FIELD_ID);

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
                        object.getJsonObject(FacebookApiConstants.FIELD_FROM));
            }

            result.setCreator(creator);
        }

        String content = "";
        if (object.has(FacebookApiConstants.FIELD_MESSAGE)) {
            content = object.getString(FacebookApiConstants.FIELD_MESSAGE);
        }

        result.setContent(StringUtils.stripHTML(content));

        if (object.has(FacebookApiConstants.FIELD_CREATED_TIME)) {
            Date date = com.restfb.util.DateUtils.toDateFromLongFormat(
                    object.getString(FacebookApiConstants.FIELD_CREATED_TIME));
            result.setCreated(DateUtils.formatISO8601(date));
        }

        if (parentPost.hasContainer()) {
            Container container = parentPost.getContainer();
            result.setContainer(container);
            container.addContainerOf(result);
        }

        if (object.has(FacebookApiConstants.FIELD_ATTACHMENT)) {
            JsonObject attachment = object.getJsonObject(FacebookApiConstants.FIELD_ATTACHMENT);

            if (attachment.has(FacebookApiConstants.FIELD_TARGET)) {
                JsonObject target = attachment.getJsonObject(FacebookApiConstants.FIELD_TARGET);
                if (target.has(FacebookApiConstants.FIELD_URL)) {
                    result.addAttachment(
                            Builder.createURI(
                                    target.getString(FacebookApiConstants.FIELD_URL)));
                }
            } else if (attachment.has(FacebookApiConstants.FIELD_URL)) {
                result.addAttachment(
                        Builder.createURI(
                                attachment.getString(FacebookApiConstants.FIELD_URL)));
            }
        }

        result.setReplyOf(parentPost);
        parentPost.addReply(result);
        parentPost.setNumReplies(
                (parentPost.hasNumReplies()) ?
                        (parentPost.getNumReplies() + 1) :
                        (1));

        return result;
    }
}
