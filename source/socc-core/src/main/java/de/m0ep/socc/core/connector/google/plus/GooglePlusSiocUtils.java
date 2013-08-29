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

package de.m0ep.socc.core.connector.google.plus;

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
import org.rdfs.sioc.services.Thing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.damnhandy.uri.template.UriTemplate;
import com.google.api.services.plus.model.Activity;
import com.google.api.services.plus.model.Activity.Actor;
import com.google.api.services.plus.model.Activity.PlusObject.Attachments;
import com.google.api.services.plus.model.Comment;
import com.google.api.services.plus.model.Comment.InReplyTo;
import com.google.api.services.plus.model.Person;
import com.google.common.base.Preconditions;

import de.m0ep.socc.core.exceptions.NotFoundException;
import de.m0ep.socc.core.utils.DateUtils;
import de.m0ep.socc.core.utils.SiocUtils;
import de.m0ep.socc.core.utils.StringUtils;
import de.m0ep.socc.core.utils.UserAccountUtils;

public class GooglePlusSiocUtils {
    private static final Logger LOG = LoggerFactory
            .getLogger(GooglePlusSiocUtils.class);

    public static final String GOOGLE_PLUS_ROOT_URI =
            "https://www.googleapis.com/plus/v1";

    public static final String REGEX_ACTIVITY_FEED_URI =
            "/people/([^/\\s?=]+)/activities/([^/\\s?=]+)";

    public static final String REGEX_ACTIVITY_URI =
            "/activities/([^/\\s?=]+)";

    public static final String REGEX_PEOPLE_URI =
            "/people/([^/\\s?=]+)";

    public static final String REGEX_COMMENT_URI =
            "/comments/([^/\\s?=]+)";

    public static final String TEMPLATE_ACTIVITY_FEED_URI =
            GOOGLE_PLUS_ROOT_URI
                    + "/people/{userId}/activities/{collection}";

    public static final String TEMPLATE_ACTIVITY_URI =
            GOOGLE_PLUS_ROOT_URI + "/activities/{activityId}";

    public static final String TEMPLATE_PEOPLE_URI =
            GOOGLE_PLUS_ROOT_URI + "/people/{userId}";

    public static final String TEMPLATE_COMMENT_URI =
            GOOGLE_PLUS_ROOT_URI + "/comments/{commentId}";

    public static final String TEMPLATE_VAR_USER_ID =
            "userId";

    public static final String TEMPLATE_VAR_COLLECTION =
            "collection";

    public static final String TEMPLATE_VAR_ACTIVITY_ID =
            "activityId";

    public static final String TEMPLATE_VAR_COMMENT_ID =
            "commentId";

    public static Forum createSiocForum(GooglePlusConnector connector,
            Person person, String collection) {
        Preconditions.checkNotNull(connector,
                "Required parameter connector must be specified.");
        Preconditions.checkNotNull(person,
                "Required parameter person must be specified.");

        Model model = connector.getContext().getModel();
        URI uri = createActivityFeedUri(person.getId(), collection);

        if (Forum.hasInstance(model, uri)) {
            return Forum.getInstance(model, uri);
        } else {
            Forum result = new Forum(model, uri, true);

            result.setId(person.getId()
                    + "/"
                    + collection);
            result.setName(person.getDisplayName()
                    + "'s "
                    + collection
                    + " feed");
            result.setNumItems(0);

            Site site = connector.getStructureReader().getSite();
            result.setHost(site);
            site.setHostOf(result);
            return result;
        }
    }

    public static Post createSiocPost(GooglePlusConnector connector,
            Activity activity, Container container) {
        Node serviceEndpoint = connector.getService().getServiceEndpoint();
        Model model = connector.getContext().getModel();
        URI uri = createActivityUri(activity.getId());

        Post result = null;
        if (Post.hasInstance(model, uri)) {
            result = Post.getInstance(model, uri);

            if (null != activity.getUpdated()) {
                Node modifiedNode = Builder.createPlainliteral(
                        DateUtils.formatISO8601(
                                activity.getUpdated().getValue()));

                if (!result.hasModified(modifiedNode)) {
                    result.setModified(modifiedNode);

                    if (null != activity.getTitle()) {
                        result.setTitle(activity.getTitle());
                    }

                    String content = activity.getObject().getContent();
                    result.setContent(StringUtils.stripHTML(content));

                    result.removeAllAttachments();
                    if (null != activity.getObject().getAttachments()) {
                        for (Attachments attachments : activity.getObject()
                                .getAttachments()) {
                            result.addAttachment(
                                    Builder.createURI(attachments.getUrl()));
                        }
                    }
                }
            }
        } else {
            result = new Post(model, uri, true);
            result.setId(activity.getId());

            if (null != activity.getTitle()) {
                result.setTitle(activity.getTitle());
            }

            if (null != activity.getActor()) {
                UserAccount creator = null;
                try {
                    creator = UserAccountUtils.findUserAccount(
                            model,
                            activity.getActor().getId(),
                            serviceEndpoint.asURI());
                } catch (NotFoundException e) {
                    creator = createSiocUserAccount(
                            connector,
                            activity.getActor());
                }

                result.setCreator(creator);
            }

            Date createdDate = new Date(activity.getPublished().getValue());
            result.setCreated(DateUtils.formatISO8601(createdDate));

            if (null != activity.getUpdated()) {
                result.setModified(DateUtils
                        .formatISO8601(activity.getUpdated()
                                .getValue()));
            }

            String content = activity.getObject().getContent();
            result.setContent(StringUtils.stripHTML(content));

            if (null != activity.getObject().getAttachments()) {
                for (Attachments attachments : activity.getObject()
                        .getAttachments()) {
                    result.addAttachment(
                            Builder.createURI(attachments.getUrl()));
                }
            }

            result.setContainer(container);
            container.addContainerOf(result);
            SiocUtils.incNumItems(container);
            SiocUtils.updateLastItemDate(container, createdDate);
        }

        return result;
    }

    public static Post createSiocComment(GooglePlusConnector connector,
            Comment comment, Post parentPost) {
        Node serviceEndpoint = connector.getService().getServiceEndpoint();
        Model model = connector.getContext().getModel();
        URI uri = createCommentUri(comment.getId());

        Post result = (Post.hasInstance(model, uri)) ?
                (Post.getInstance(model, uri)) :
                (new Post(model, uri, true));
        result.setId(comment.getId());

        if (null != comment.getActor()) {
            UserAccount creator = null;
            try {
                creator = UserAccountUtils.findUserAccount(
                        model,
                        comment.getActor().getId(),
                        serviceEndpoint.asURI());
            } catch (NotFoundException e) {
                creator = createSiocUserAccount(
                        connector,
                        comment.getActor());
            }

            result.setCreator(creator);
        }

        Date createdDate = new Date(comment.getPublished().getValue());
        result.setCreated(DateUtils.formatISO8601(createdDate));

        if (null != comment.getUpdated()) {
            result.setModified(DateUtils
                    .formatISO8601(comment.getUpdated()
                            .getValue()));
        }
        String content = comment.getObject().getContent();
        result.setContent(StringUtils.stripHTML(content));

        if (null == parentPost) {
            // try to set replied post if there is none parentPost
            for (InReplyTo inReplyTo : comment.getInReplyTo()) {
                try {
                    Post inReplyToPost = connector.getPostReader().readPost(
                            Builder.createURI(inReplyTo.getUrl()));

                    result.setReplyOf(inReplyToPost);
                    inReplyToPost.addReply(result);
                    SiocUtils.incNumReplies(inReplyToPost);
                    SiocUtils.updateLastReplyDate(inReplyToPost, createdDate);

                    if (inReplyToPost.hasContainer()) {
                        Container container = inReplyToPost.getContainer();
                        result.setContainer(container);
                        container.addContainerOf(result);
                        SiocUtils.incNumItems(container);
                        SiocUtils.updateLastItemDate(container, createdDate);
                    }
                } catch (Exception e) {
                    LOG.warn("Failed to read activity from "
                            + inReplyTo.getUrl());
                }
            }
        } else {
            result.setReplyOf(parentPost);
            parentPost.addReply(result);
            SiocUtils.incNumReplies(parentPost);
            SiocUtils.updateLastReplyDate(parentPost, createdDate);

            if (parentPost.hasContainer()) {
                Container container = parentPost.getContainer();
                result.setContainer(container);
                container.addContainerOf(result);
                SiocUtils.incNumItems(container);
                SiocUtils.updateLastItemDate(container, createdDate);
            }
        }

        return result;
    }

    private static UserAccount createSiocUserAccount(
            GooglePlusConnector connector,
            com.google.api.services.plus.model.Comment.Actor actor) {
        Model model = connector.getContext().getModel();
        URI userUri = createPeopleUri(actor.getId());

        UserAccount result = new UserAccount(model, userUri, true);
        result.setId(actor.getId());
        result.setName(actor.getDisplayName());
        result.setAccountName(actor.getId());
        result.setAccountServiceHomepage(connector.getService()
                .getServiceEndpoint());

        Thing.setService(result.getModel(), result, connector.getService());
        connector.getService().addServiceOf(result);

        if (null != actor.getUrl()) {
            result.addSeeAlso(Builder.createURI(actor.getUrl()));
        }

        // create a new Person for unknown account.
        com.xmlns.foaf.Person person = new com.xmlns.foaf.Person(
                model,
                true);
        person.setName(actor.getDisplayName());

        if (null != actor.getImage()) {
            if (null != actor.getImage().getUrl()) {
                person.setDepiction(Builder
                        .createURI(actor.getImage().getUrl()));
            }
        }

        person.addAccount(result);
        result.setAccountOf(person);

        return result;
    }

    public static UserAccount createSiocUserAccount(
            GooglePlusConnector connector,
            Actor actor) {
        Model model = connector.getContext().getModel();
        URI userUri = createPeopleUri(actor.getId());

        UserAccount result = new UserAccount(model, userUri, true);
        result.setId(actor.getId());
        result.setName(actor.getDisplayName());
        result.setAccountName(actor.getId());
        result.setAccountServiceHomepage(connector.getService()
                .getServiceEndpoint());

        Thing.setService(result.getModel(), result, connector.getService());
        connector.getService().addServiceOf(result);

        if (null != actor.getUrl()) {
            result.addSeeAlso(Builder.createURI(actor.getUrl()));
        }

        // create a new Person for unknown account.
        com.xmlns.foaf.Person person = new com.xmlns.foaf.Person(
                model,
                true);
        person.setName(actor.getDisplayName());

        if (null != actor.getImage()) {
            if (null != actor.getImage().getUrl()) {
                person.setDepiction(Builder
                        .createURI(actor.getImage().getUrl()));
            }
        }

        person.addAccount(result);
        result.setAccountOf(person);

        return result;
    }

    public static URI createPeopleUri(String userId) {
        return Builder.createURI(
                UriTemplate.fromTemplate(TEMPLATE_PEOPLE_URI)
                        .set(TEMPLATE_VAR_USER_ID, userId)
                        .expand());
    }

    public static URI createActivityFeedUri(String userId, String collection) {
        return Builder.createURI(
                UriTemplate.fromTemplate(TEMPLATE_ACTIVITY_FEED_URI)
                        .set(TEMPLATE_VAR_USER_ID, userId)
                        .set(TEMPLATE_VAR_COLLECTION, collection)
                        .expand());
    }

    public static URI createActivityUri(String activityId) {
        return Builder.createURI(
                UriTemplate.fromTemplate(TEMPLATE_ACTIVITY_URI)
                        .set(TEMPLATE_VAR_ACTIVITY_ID, activityId)
                        .expand());
    }

    public static URI createCommentUri(String commentId) {
        return Builder.createURI(
                UriTemplate.fromTemplate(TEMPLATE_COMMENT_URI)
                        .set(TEMPLATE_VAR_COMMENT_ID, commentId)
                        .expand());
    }
}
