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

import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.util.Builder;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.Site;
import org.rdfs.sioc.UserAccount;

import com.google.api.services.plus.model.Activity;
import com.google.api.services.plus.model.Activity.Actor;
import com.google.api.services.plus.model.Activity.PlusObject.Attachments;
import com.google.api.services.plus.model.Comment;
import com.google.api.services.plus.model.Person;
import com.google.common.base.Preconditions;

import de.m0ep.socc.core.exceptions.NotFoundException;
import de.m0ep.socc.core.utils.DateUtils;
import de.m0ep.socc.core.utils.StringUtils;
import de.m0ep.socc.core.utils.UserAccountUtils;

public class GooglePlusSiocConverter {

    public static final String ID_SEPERATOR = ":";
    public static final String PUBLIC_FEED_ID_PREFIX = "public" + ID_SEPERATOR;
    public static final String PUBLIC_FEED_URI_PATH = "/public_feed/";

    public static final String ACTIVITY_ID_PREFIX = "activity" + ID_SEPERATOR;
    public static final String ACTIVITY_URI_PATH = "/activity/";

    public static final String COMMENT_ID_PREFIX = "comment" + ID_SEPERATOR;
    public static final String COMMENT_URI_PATH = "/comment/";

    public static final String USER_URI_PATH = "/user/";

    public static Forum createSiocForum(GooglePlusConnector connector,
            Person person) {
        Preconditions.checkNotNull(connector,
                "Required parameter connector must be specified.");
        Preconditions.checkNotNull(person,
                "Required parameter person must be specified.");

        URI serviceEndpoint = connector.getService().getServiceEndpoint()
                .asURI();
        Model model = connector.getContext().getModel();
        URI uri = Builder.createURI(serviceEndpoint + PUBLIC_FEED_URI_PATH
                + person.getId());

        Forum result = null;
        if (Forum.hasInstance(model, uri)) {
            result = Forum.getInstance(model, uri);
        } else {
            result = new Forum(model, uri, true);
        }

        result.setId(PUBLIC_FEED_ID_PREFIX + person.getId());
        if (null != person.getName()) {
            result.setName(person.getName().getFormatted() + "'s public feed");
        } else if (null != person.getDisplayName()) {
            result.setName(person.getDisplayName() + "'s public feed");
        }

        Site site = connector.getStructureReader().getSite();
        result.setHost(site);
        site.setHostOf(result);

        return result;
    }

    public static Post createSiocPost(GooglePlusConnector connector,
            Activity activity, Container container) {
        URI serviceEndpoint = connector.getService().getServiceEndpoint()
                .asURI();
        Model model = connector.getContext().getModel();
        URI uri = Builder.createURI(serviceEndpoint + ACTIVITY_URI_PATH
                + activity.getId());

        Post result = (Post.hasInstance(model, uri)) ?
                (Post.getInstance(model, uri)) :
                (new Post(model, uri, true));
        result.setId(ACTIVITY_ID_PREFIX + activity.getId());

        if (null != activity.getTitle()) {
            result.setTitle(activity.getTitle());
        }

        if (null != activity.getActor()) {
            UserAccount creator = null;
            try {
                creator = UserAccountUtils.findUserAccount(
                        model,
                        activity.getActor().getId(),
                        serviceEndpoint);
            } catch (NotFoundException e) {
                creator = createSiocUserAccount(
                        connector,
                        activity.getActor());
            }

            result.setCreator(creator);
        }

        if (null != activity.getPublished()) {
            result.setCreated(DateUtils
                    .formatISO8601(activity.getPublished()
                            .getValue()));
        }

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
        // TODO update LastItemDate

        container.setNumItems(
                (container.hasNumItems())
                        ? (container.getNumItems() + 1)
                        : (1));

        return result;
    }

    public static Post createSiocComment(GooglePlusConnector connector,
            Comment comment, Post parentPost) {
        URI serviceEndpoint = connector.getService().getServiceEndpoint()
                .asURI();
        Model model = connector.getContext().getModel();
        URI uri = Builder.createURI(serviceEndpoint + COMMENT_URI_PATH
                + comment.getId());

        Post result = (Post.hasInstance(model, uri)) ?
                (Post.getInstance(model, uri)) :
                (new Post(model, uri, true));
        result.setId(COMMENT_ID_PREFIX + comment.getId());

        if (null != comment.getActor()) {
            UserAccount creator = null;
            try {
                creator = UserAccountUtils.findUserAccount(
                        model,
                        comment.getActor().getId(),
                        serviceEndpoint);
            } catch (NotFoundException e) {
                creator = createSiocUserAccount(
                        connector,
                        comment.getActor());
            }

            result.setCreator(creator);
        }

        result.setReplyOf(parentPost);
        parentPost.addReply(result);
        // TODO update LastReplyDate

        parentPost.setNumReplies(
                (parentPost.hasNumReplies())
                        ? (parentPost.getNumReplies())
                        : (1));

        return result;
    }

    private static UserAccount createSiocUserAccount(
            GooglePlusConnector connector,
            com.google.api.services.plus.model.Comment.Actor actor) {
        URI serviceEndpoint = connector.getService().getServiceEndpoint()
                .asURI();
        URI userUri = Builder.createURI(
                serviceEndpoint.toString()
                        + USER_URI_PATH
                        + actor.getId());

        UserAccount result = new UserAccount(connector.getContext().getModel(),
                userUri, true);
        result.setId(actor.getId());
        result.setAccountName(actor.getId());
        result.setAccountServiceHomepage(serviceEndpoint);

        if (null != actor.getUrl()) {
            result.addSeeAlso(Builder.createURI(actor.getUrl()));
        } else {
            result.addSeeAlso(
                    Builder.createURI(
                            serviceEndpoint + "/" + actor.getId()));
        }

        // create a new Person for unknown account.
        com.xmlns.foaf.Person person = new com.xmlns.foaf.Person(
                connector.getContext().getModel(),
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
        URI serviceEndpoint = connector.getService().getServiceEndpoint()
                .asURI();
        URI userUri = Builder.createURI(
                serviceEndpoint.toString()
                        + USER_URI_PATH
                        + actor.getId());

        UserAccount result = new UserAccount(connector.getContext().getModel(),
                userUri, true);
        result.setId(actor.getId());
        result.setAccountName(actor.getId());
        result.setAccountServiceHomepage(serviceEndpoint);

        if (null != actor.getUrl()) {
            result.addSeeAlso(Builder.createURI(actor.getUrl()));
        } else {
            result.addSeeAlso(
                    Builder.createURI(
                            serviceEndpoint + "/" + actor.getId()));
        }

        // create a new Person for unknown account.
        com.xmlns.foaf.Person person = new com.xmlns.foaf.Person(
                connector.getContext().getModel(),
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
}
