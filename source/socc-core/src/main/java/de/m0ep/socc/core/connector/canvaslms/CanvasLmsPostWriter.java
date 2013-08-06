
package de.m0ep.socc.core.connector.canvaslms;

import java.io.IOException;

import org.rdfs.sioc.Container;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.Thread;
import org.rdfs.sioc.UserAccount;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.xmlns.foaf.Person;

import de.m0ep.canvas.CanvasLmsClient;
import de.m0ep.canvas.exceptions.AuthorizationException;
import de.m0ep.canvas.exceptions.CanvasLmsException;
import de.m0ep.canvas.exceptions.NetworkException;
import de.m0ep.canvas.model.Entry;
import de.m0ep.socc.core.connector.AbstractConnectorIOComponent;
import de.m0ep.socc.core.connector.IConnector.IPostWriter;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.utils.PostWriterUtils;
import de.m0ep.socc.core.utils.RdfUtils;

public class CanvasLmsPostWriter extends
        AbstractConnectorIOComponent<CanvasLmsConnector> implements
        IPostWriter {

    public CanvasLmsPostWriter(final CanvasLmsConnector connector) {
        super(connector);
    }

    @Override
    public boolean canPostTo(Container container) {
        Preconditions.checkNotNull(container,
                "Required parameter container must be specified.");

        return container.toString().startsWith(getServiceEndpoint().toString())
                && RdfUtils.isType(
                        container.getModel(),
                        container,
                        Thread.RDFS_CLASS)
                && container.hasParent()
                && container.getParent().toString().startsWith(
                        getServiceEndpoint().toString());
    }

    @Override
    public void writePost(Post post, Container container)
            throws AuthenticationException,
            IOException {
        Preconditions.checkNotNull(container,
                "Required parameter container must be specified.");
        Preconditions.checkArgument(container.hasId(),
                "The container has no id.");
        Preconditions.checkArgument(container.getParent().hasId(),
                "The parent container of the container has no id.");

        Container parentContainer = container.getParent();

        long courseId;
        try {
            courseId = Long.parseLong(parentContainer.getId());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "The id of the containers parent is invalid: was "
                            + parentContainer.getId());
        }

        long topicId;
        try {
            topicId = Long.parseLong(container.getId());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "The id of the container is invalid: was "
                            + container.getId());
        }

        UserAccount creatorAccount = post.getCreator();
        Person creatorPerson = PostWriterUtils.getPersonOfCreatorOrNull(
                getConnector(),
                creatorAccount);

        CanvasLmsClient client = null;
        if (null != creatorPerson) {
            UserAccount serviceAccount = PostWriterUtils
                    .getServiceAccountOfPersonOrNull(
                            getConnector(),
                            creatorPerson,
                            getServiceEndpoint());
            if (null != serviceAccount) {
                client = (CanvasLmsClient) PostWriterUtils
                        .getClientOfServiceAccountOrNull(
                                getConnector(),
                                serviceAccount);
            }
        }

        String content = post.getContent();
        if (null == client) { // No client found, get default one an adapt
                              // message content
            client = getConnector().getServiceClientManager()
                    .getDefaultClient();
            content = PostWriterUtils.createContentOfUnknownAccount(
                    post,
                    creatorAccount,
                    creatorPerson);
        }

        Entry resultEntry = null;
        try {
            resultEntry = client.courses()
                    .discussionTopics(courseId)
                    .entries(topicId)
                    .post(content)
                    .execute();
        } catch (CanvasLmsException e) {
            if (e instanceof NetworkException) {
                throw new IOException(e);
            } else if (e instanceof AuthorizationException) {
                throw new AuthenticationException(e);
            }

            throw Throwables.propagate(e);
        }

        if (null != resultEntry) {
            Post resultPost = CanvasLmsSiocConverter.createSiocPost(
                    getConnector(),
                    resultEntry,
                    container);

            resultPost.addSibling(post);
            post.addSibling(resultPost);
        }
    }

    @Override
    public boolean canReplyTo(Post post) {
        Preconditions.checkNotNull(post,
                "Required parameter post must be specified.");

        return post.toString().startsWith(getServiceEndpoint().toString())
                && post.hasContainer()
                && canPostTo(post.getContainer());
    }

    @Override
    public void writeReply(Post replyPost, Post parentPost)
            throws AuthenticationException,
            IOException {
        Preconditions.checkNotNull(replyPost,
                "Required parameter replyPost must be specified.");
        Preconditions.checkArgument(replyPost.hasContent(),
                "The replyPost has no content.");
        Preconditions.checkArgument(replyPost.hasCreator(),
                "The replyPost has no creator.");

        Preconditions.checkNotNull(parentPost,
                "Required parameter parentPost must be specified.");
        Preconditions.checkArgument(parentPost.hasId(),
                "The parentPost has no id.");
        Preconditions.checkArgument(parentPost.hasContainer(),
                "The parentPost has no container");
        Preconditions.checkArgument(canReplyTo(parentPost),
                "Writing a reply to the parentpost is not possible.");

        Container container = parentPost.getContainer();
        Container parentContainer = container.getParent();

        long courseId;
        try {
            courseId = Long.parseLong(parentContainer.getId());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "The id of the containers parent is invalid: was "
                            + parentContainer.getId());
        }

        long topicId;
        try {
            topicId = Long.parseLong(container.getId());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "The id of the container is invalid: was "
                            + container.getId());
        }

        long entryId;
        try {
            entryId = Long.parseLong(parentPost.getId());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "The id of the parentPost is invalid: was "
                            + parentPost.getId());
        }

        UserAccount creatorAccount = replyPost.getCreator();
        Person creatorPerson = PostWriterUtils.getPersonOfCreatorOrNull(
                getConnector(),
                creatorAccount);

        CanvasLmsClient client = null;
        if (null != creatorPerson) {
            UserAccount serviceAccount = PostWriterUtils
                    .getServiceAccountOfPersonOrNull(
                            getConnector(),
                            creatorPerson,
                            getServiceEndpoint());
            if (null != serviceAccount) {
                client = (CanvasLmsClient) PostWriterUtils
                        .getClientOfServiceAccountOrNull(
                                getConnector(),
                                serviceAccount);
            }
        }

        String content = replyPost.getContent();
        if (null == client) { // No client found, get default one an adapt
                              // message content
            client = getConnector().getServiceClientManager()
                    .getDefaultClient();
            content = PostWriterUtils.createContentOfUnknownAccount(
                    replyPost,
                    creatorAccount,
                    creatorPerson);
        }

        Entry resultEntry = null;
        try {
            resultEntry = client.courses()
                    .discussionTopics(courseId)
                    .entries(topicId)
                    .postReply(content, entryId)
                    .execute();
        } catch (NetworkException e) {
            throw new IOException(e);
        } catch (AuthorizationException e) {
            throw new AuthenticationException(e);
        } catch (CanvasLmsException e) {
            throw Throwables.propagate(e);
        }

        if (null != resultEntry) {
            Post resultPost = CanvasLmsSiocConverter.createSiocPost(
                    getConnector(),
                    resultEntry,
                    container);

            resultPost.addSibling(replyPost);
            replyPost.addSibling(resultPost);
        }
    }
}
