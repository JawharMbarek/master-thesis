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
import org.ontoware.rdf2go.model.node.Node;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.util.Builder;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.Site;
import org.rdfs.sioc.UserAccount;

import com.damnhandy.uri.template.UriTemplate;
import com.google.api.client.repackaged.com.google.common.base.Strings;
import com.google.common.base.Preconditions;
import com.restfb.json.JsonObject;
import com.restfb.types.Group;
import com.restfb.types.User;
import com.xmlns.foaf.Person;

import de.m0ep.socc.core.exceptions.NotFoundException;
import de.m0ep.socc.core.utils.DateUtils;
import de.m0ep.socc.core.utils.SiocUtils;
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

    public static Forum createSiocForum(
            final FacebookConnector connector,
            final User user) {
        Preconditions.checkNotNull(connector,
                "Required parameter connector must be specified.");
        Preconditions.checkNotNull(user,
                "Required parameter user must be specified.");

        URI serviceEndpoint = connector.getService().getServiceEndpoint()
                .asURI();
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
        result.setNumItems(0);

        Site site = connector.getStructureReader().getSite();
        result.setHost(site);
        site.setHostOf(result);

        return result;
    }

    public static Forum createSiocForum(
            final FacebookConnector connector,
            final Group group) {
        Preconditions.checkNotNull(connector,
                "Required parameter connector must be specified.");
        Preconditions.checkNotNull(group,
                "Required parameter group must be specified.");

        Model model = connector.getContext().getModel();
        URI uri = FacebookSiocConverter.createSiocUri(group.getId());

        if (!Forum.hasInstance(model, uri)) {
            Forum result = new Forum(model, uri, true);
            result.setId(GROUP_ID_PREFIX + group.getId());
            result.setName(group.getName());
            result.setDescription(Strings.nullToEmpty(group.getDescription()));
            result.setNumItems(0);

            Site site = connector.getStructureReader().getSite();
            result.setHost(site);
            site.addHostOf(result);
        }

        return Forum.getInstance(model, uri);
    }

    public static Post createSiocPost(
            final FacebookConnector connector,
            final JsonObject object,
            final Container container,
            final Post parentPost) {
        Preconditions.checkNotNull(connector,
                "Required parameter connector must be specified.");
        Preconditions.checkNotNull(object,
                "Required parameter object must be specified.");
        Preconditions.checkNotNull(container,
                "Required parameter container must be specified.");
        Preconditions.checkNotNull(parentPost,
                "Required parameter parentPost must be specified.");

        Model model = connector.getContext().getModel();
        URI serviceEndpoint = connector.getService().getServiceEndpoint()
                .asURI();
        URI uri = createSiocUri(object.getString(FacebookApiConstants.FIELD_ID));

        Post result;
        if (Post.hasInstance(model, uri)) {
            result = Post.getInstance(model, uri);

            if (object.has(FacebookApiConstants.FIELD_UPDATED_TIME)) {
                Date modifiedDate = com.restfb.util.DateUtils
                        .toDateFromLongFormat(
                        object.getString(FacebookApiConstants.FIELD_UPDATED_TIME));
                Node modifiedNode = Builder.createPlainliteral(
                        DateUtils.formatISO8601(modifiedDate));

                if (!result.hasModified(modifiedNode)) {
                    result.removeAllAttachments();
                    setPostCoreProperties(result, object);
                    result.setModified(modifiedNode);
                }
            }
        } else {
            result = new Post(model, uri, true);

            result.setId(POST_ID_PREFIX
                    + object.getString(FacebookApiConstants.FIELD_ID));

            String creatorId = object.getJsonObject(
                    FacebookApiConstants.FIELD_FROM)
                    .getString(FacebookApiConstants.FIELD_ID);

            // check if we already know the author, else create a new
            // UserAccount + Person
            try {
                result.setCreator(UserAccountUtils.findUserAccount(
                        connector.getContext().getModel(),
                        creatorId,
                        serviceEndpoint));
            } catch (NotFoundException e) {
                result.setCreator(createSiocUserAccount(
                        connector,
                        object.getJsonObject(FacebookApiConstants.FIELD_FROM)));
            }

            if (object.has(FacebookApiConstants.FIELD_CREATED_TIME)) {
                Date date = com.restfb.util.DateUtils
                        .toDateFromLongFormat(
                        object.getString(FacebookApiConstants.FIELD_CREATED_TIME));
                result.setCreated(DateUtils.formatISO8601(date));
            }

            if (object.has(FacebookApiConstants.FIELD_UPDATED_TIME)) {
                Date date = com.restfb.util.DateUtils
                        .toDateFromLongFormat(
                        object.getString(FacebookApiConstants.FIELD_UPDATED_TIME));
                result.setModified(DateUtils.formatISO8601(date));
            }

            setPostCoreProperties(result, object);

            result.setContainer(container);
            container.addContainerOf(result);
            SiocUtils.incNumItems(container);

            if (null != parentPost) {
                result.setReplyOf(parentPost);
                parentPost.addReply(result);
                SiocUtils.incNumReplies(parentPost);
            }
        }

        return result;
    }

    private static void setPostCoreProperties(
            final Post result,
            final JsonObject object) {
        Preconditions.checkNotNull(result,
                "Required parameter result must be specified.");
        Preconditions.checkNotNull(object,
                "Required parameter object must be specified.");

        // content
        if (object.has(FacebookApiConstants.FIELD_MESSAGE)) {
            result.setContent(
                    object.getString(FacebookApiConstants.FIELD_MESSAGE));
        } else if (object.has(FacebookApiConstants.FIELD_DESCRIPTION)) {
            result.setContent(
                    object.getString(FacebookApiConstants.FIELD_DESCRIPTION));
        } else if (object.has(FacebookApiConstants.FIELD_STORY)) {
            result.setContent(
                    object.getString(FacebookApiConstants.FIELD_STORY));
        }

        // title
        if (object.has(FacebookApiConstants.FIELD_NAME)) {
            result.setTitle(object.getString(FacebookApiConstants.FIELD_NAME));
        } else if (object.has(FacebookApiConstants.FIELD_CAPTION)) {
            result.setTitle(FacebookApiConstants.FIELD_CAPTION);
        }

        // attachment
        if (object.has(FacebookApiConstants.FIELD_LINK)) {
            result.setAttachment(
                    Builder.createURI(
                            object.getString(FacebookApiConstants.FIELD_LINK)));
        } else if (object.has(FacebookApiConstants.FIELD_SOURCE)) {
            result.setAttachment(
                    Builder.createURI(
                            object.getString(FacebookApiConstants.FIELD_SOURCE)));
        } else if (object.has(FacebookApiConstants.FIELD_ATTACHMENT)) {
            JsonObject attachment = object
                    .getJsonObject(FacebookApiConstants.FIELD_ATTACHMENT);

            if (attachment.has(FacebookApiConstants.FIELD_TARGET)) {
                JsonObject target = attachment
                        .getJsonObject(FacebookApiConstants.FIELD_TARGET);
                if (target.has(FacebookApiConstants.FIELD_URL)) {
                    result.addAttachment(
                            Builder.createURI(
                                    target.getString(FacebookApiConstants.FIELD_URL)));
                }
            } else if (attachment.has(FacebookApiConstants.FIELD_URL)) {
                result.addAttachment(
                        Builder.createURI(
                                attachment
                                        .getString(FacebookApiConstants.FIELD_URL)));
            }
        }
    }

    public static UserAccount createSiocUserAccount(
            final FacebookConnector connector,
            final JsonObject jsonObject) {
        Preconditions.checkNotNull(connector,
                "Required parameter connector must be specified.");
        Preconditions.checkNotNull(jsonObject,
                "Required parameter jsonObject must be specified.");

        Model model = connector.getContext().getModel();
        URI serviceEndpoint = connector.getService()
                .getServiceEndpoint()
                .asURI();
        String id = jsonObject.getString(FacebookApiConstants.FIELD_ID);
        URI uri = createSiocUri(id);

        UserAccount result = new UserAccount(model, uri, true);
        result.setId(id);
        result.setName(jsonObject.getString(FacebookApiConstants.FIELD_NAME));
        result.setAccountName(id);
        result.setAccountServiceHomepage(serviceEndpoint);

        // create a new Person for unknown account.
        Person person = new Person(model, true);
        person.setName(jsonObject.getString(FacebookApiConstants.FIELD_NAME));

        person.addAccount(result);
        result.setAccountOf(person);

        return result;
    }

    public static URI createSiocUri(final String id) {
        Preconditions.checkNotNull(id,
                "Required parameter id must be specified.");
        Preconditions.checkArgument(!id.isEmpty(),
                "Required parameter id may not be empty.");

        return Builder.createURI(
                UriTemplate.fromTemplate("https://www.facebook.com/{id}")
                        .set("id", id)
                        .expand());
    }
}
