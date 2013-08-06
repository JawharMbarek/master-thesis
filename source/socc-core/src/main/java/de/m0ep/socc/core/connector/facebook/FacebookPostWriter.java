
package de.m0ep.socc.core.connector.facebook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.Thing;
import org.rdfs.sioc.UserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.restfb.Parameter;
import com.restfb.exception.FacebookException;
import com.restfb.json.JsonObject;
import com.restfb.types.FacebookType;
import com.xmlns.foaf.Person;

import de.m0ep.socc.core.connector.AbstractConnectorIOComponent;
import de.m0ep.socc.core.connector.IConnector.IPostWriter;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.utils.PostWriterUtils;
import de.m0ep.socc.core.utils.RdfUtils;

public class FacebookPostWriter extends
        AbstractConnectorIOComponent<FacebookConnector> implements IPostWriter {
    private static final Logger LOG = LoggerFactory
            .getLogger(FacebookPostWriter.class);

    public FacebookPostWriter(FacebookConnector connector) {
        super(connector);
    }

    @Override
    public boolean canPostTo(Container container) {
        return null != container
                &&
                RdfUtils.isType(container.getModel(), container.getResource(),
                        Forum.RDFS_CLASS)
                &&
                container.toString()
                        .startsWith(getServiceEndpoint().toString())
                &&
                container.hasId()
                &&
                (container.getId().startsWith(
                        FacebookSiocConverter.WALL_ID_PREFIX) ||
                container.getId().startsWith(
                        FacebookSiocConverter.GROUP_ID_PREFIX));
    }

    @Override
    public void writePost(Post post, Container container)
            throws AuthenticationException,
            IOException {
        Preconditions.checkNotNull(post,
                "Required parameter post must be specified.");
        Preconditions.checkNotNull(container,
                "Required parameter container must be specified.");
        Preconditions.checkArgument(canPostTo(container),
                "Can't write a post to this container at this service.");

        String containerSiocId = container.getId();
        String containerFbId = containerSiocId.substring(
                containerSiocId.lastIndexOf(
                        FacebookSiocConverter.ID_SEPERATOR) + 1);

        UserAccount creatorAccount = post.getCreator();
        Person creatorPerson = PostWriterUtils.getPersonOfCreatorOrNull(
                getConnector(), creatorAccount);

        FacebookClientWrapper client = null;
        if (null != creatorPerson) {
            UserAccount serviceAccount = PostWriterUtils
                    .getServiceAccountOfPersonOrNull(
                            getConnector(),
                            creatorPerson,
                            getConnector().getService().getServiceEndpoint()
                                    .asURI());
            if (null != serviceAccount) {
                client = (FacebookClientWrapper) PostWriterUtils
                        .getClientOfServiceAccountOrNull(
                                getConnector(),
                                serviceAccount);
            }
        }

        String content = Strings.nullToEmpty(post.getContent());
        if (null == client) { // No client found, get default one an adapt
                              // message content
            client = getConnector().getServiceClientManager()
                    .getDefaultClient();
            content = PostWriterUtils.createContentOfUnknownAccount(
                    post,
                    creatorAccount,
                    creatorPerson);
        }

        ClosableIterator<Thing> attachIter = post.getAllAttachments();
        try {
            while (attachIter.hasNext()) {
                Thing thing = (Thing) attachIter.next();
                content += "\n" + thing.toString();
            }

        } finally {
            attachIter.close();
        }

        // create Facebook Graph API publish parameter
        List<Parameter> params = new ArrayList<Parameter>();
        params.add(Parameter.with(FacebookApiConstants.FIELD_MESSAGE, content));

        if (post.hasAttachments()) {
            params.add(
                    Parameter.with(
                            FacebookApiConstants.FIELD_LINK,
                            post.getAttachment().toString()));
        }

        FacebookType result = null;
        try {
            result = client.getClient().publish(
                    containerFbId + "/" + FacebookApiConstants.CONNECTION_FEED,
                    FacebookType.class,
                    params.toArray(new Parameter[params.size()]));
        } catch (FacebookException e) {
            FacebookConnector.handleFacebookException(e);
        }

        if (null != result && null != result.getId()) {
            LOG.debug("Successfully writen post {} to container {}.", post,
                    container);
            JsonObject object = null;
            try {
                object = client.getClient().fetchObject(
                        result.getId(),
                        JsonObject.class);
            } catch (FacebookException e) {
                FacebookConnector.handleFacebookException(e);
            }

            if (null != object) {
                Post addedPost = FacebookSiocConverter.createSiocPost(
                        getConnector(),
                        object,
                        container);
                addedPost.setSibling(post);
                post.addSibling(addedPost);
            }
        }
    }

    @Override
    public boolean canReplyTo(Post post) {
        return null != post
                &&
                post.toString().startsWith(getServiceEndpoint().toString())
                &&
                post.hasId()
                &&
                (post.getId().startsWith(FacebookSiocConverter.POST_ID_PREFIX) ||
                post.getId().startsWith(FacebookSiocConverter.COMMENT_URI_PATH));
    }

    @Override
    public void writeReply(Post replyPost, Post parentPost)
            throws AuthenticationException,
            IOException {
        Preconditions.checkNotNull(replyPost,
                "Required parameter replyPost must be specified.");
        Preconditions.checkNotNull(parentPost,
                "Required parameter parentPost must be specified.");
        Preconditions.checkArgument(canReplyTo(parentPost),
                "Can't write a reply to this parenPost at this service.");

        String parentSiocId = parentPost.getId();
        String parentFbId = parentSiocId.substring(
                parentSiocId.lastIndexOf(
                        FacebookSiocConverter.ID_SEPERATOR) + 1);

        UserAccount creatorAccount = replyPost.getCreator();
        Person creatorPerson = PostWriterUtils.getPersonOfCreatorOrNull(
                getConnector(),
                creatorAccount);

        FacebookClientWrapper client = null;
        if (null != creatorPerson) {
            UserAccount serviceAccount = PostWriterUtils
                    .getServiceAccountOfPersonOrNull(
                            getConnector(),
                            creatorPerson,
                            getConnector().getService().getServiceEndpoint()
                                    .asURI());
            if (null != serviceAccount) {
                client = (FacebookClientWrapper) PostWriterUtils
                        .getClientOfServiceAccountOrNull(
                                getConnector(),
                                serviceAccount);
            }
        }

        String content = Strings.nullToEmpty(replyPost.getContent());
        if (null == client) { // No client found, get default one an adapt
                              // message content
            client = getConnector().getServiceClientManager()
                    .getDefaultClient();
            content = PostWriterUtils.createContentOfUnknownAccount(
                    replyPost,
                    creatorAccount,
                    creatorPerson);
        }

        ClosableIterator<Thing> attachIter = replyPost.getAllAttachments();
        try {
            while (attachIter.hasNext()) {
                Thing thing = (Thing) attachIter.next();
                content += "\n" + thing.toString();
            }

        } finally {
            attachIter.close();
        }

        FacebookType result = null;
        try {
            result = client.getClient()
                    .publish(
                            parentFbId + "/"
                                    + FacebookApiConstants.CONNECTION_COMMENTS,
                            FacebookType.class,
                            Parameter.with(FacebookApiConstants.FIELD_MESSAGE,
                                    content));
        } catch (FacebookException e) {
            FacebookConnector.handleFacebookException(e);
        }

        if (null != result && null != result.getId()) {
            LOG.debug("Successfully writen reply {} to post {}.", replyPost,
                    parentPost);

            JsonObject object = null;
            try {
                object = client.getClient().fetchObject(
                        result.getId(),
                        JsonObject.class);
            } catch (FacebookException e) {
                FacebookConnector.handleFacebookException(e);
            }
            if (null != object) {
                Post addedPost = FacebookSiocConverter.createSiocComment(
                        getConnector(),
                        object,
                        parentPost);
                addedPost.setSibling(replyPost);
                replyPost.addSibling(addedPost);
            }
        }
    }
}
