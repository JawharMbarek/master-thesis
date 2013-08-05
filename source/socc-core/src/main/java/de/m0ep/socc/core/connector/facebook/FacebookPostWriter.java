
package de.m0ep.socc.core.connector.facebook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Post;
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

import de.m0ep.socc.core.connector.IConnector;
import de.m0ep.socc.core.connector.IConnector.IPostWriter;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.utils.PostWriterUtils;
import de.m0ep.socc.core.utils.RdfUtils;

public class FacebookPostWriter implements IPostWriter {
    private static final Logger LOG = LoggerFactory.getLogger(FacebookPostWriter.class);

    private FacebookConnector connector;
    private String serviceEndpoint;

    public FacebookPostWriter(FacebookConnector connector) {
        this.connector = Preconditions.checkNotNull(connector,
                "Required parameter connector must be specified.");

        this.serviceEndpoint = this.connector.getService().getServiceEndpoint().toString();
    }

    @Override
    public IConnector getConnector() {
        return connector;
    }

    @Override
    public boolean canPostTo(Container container) {
        return null != container &&
                RdfUtils.isType(container.getModel(), container.getResource(), Forum.RDFS_CLASS) &&
                container.toString().startsWith(serviceEndpoint) &&
                container.hasId() &&
                (container.getId().startsWith(FacebookSiocConverter.WALL_ID_PREFIX) ||
                container.getId().startsWith(FacebookSiocConverter.GROUP_ID_PREFIX));
    }

    @Override
    public void writePost(Post post, Container container) throws AuthenticationException,
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
        Person creatorPerson = PostWriterUtils.getPersonOfCreatorOrNull(connector, creatorAccount);

        FacebookClientWrapper client = null;
        if (null != creatorPerson) {
            UserAccount serviceAccount = PostWriterUtils.getServiceAccountOfPersonOrNull(
                    connector,
                    creatorPerson,
                    connector.getService().getServiceEndpoint().asURI());
            if (null != serviceAccount) {
                client = (FacebookClientWrapper) PostWriterUtils.getClientOfServiceAccountOrNull(
                        connector,
                        serviceAccount);
            }
        }

        String content = Strings.nullToEmpty(post.getContent());
        if (null == client) { // No client found, get default one an adapt
                              // message content
            client = (FacebookClientWrapper) connector.getServiceClientManager().getDefaultClient();
            content = PostWriterUtils.createContentOfUnknownAccount(
                    post,
                    creatorAccount,
                    creatorPerson);
        }

        // create Facebook Graph API publish parameter
        List<Parameter> params = new ArrayList<Parameter>();
        params.add(Parameter.with(FacebookApiConstants.FIELD_MESSAGE, content));

        if (post.hasAttachments()) {
            params.add(Parameter.with(FacebookApiConstants.FIELD_LINK,
                    post.getAttachment()));
        }

        if (post.hasTitle()) {
            params.add(Parameter.with(FacebookApiConstants.FIELD_CAPTION,
                    post.getTitle()));
        } else if (post.hasSubject()) {
            params.add(Parameter.with(FacebookApiConstants.FIELD_CAPTION,
                    post.getSubject()));
        }

        if (post.hasDescriptions()) {
            params.add(Parameter.with(FacebookApiConstants.FIELD_DESCRIPTION,
                    post.getDescription()));
        }

        if (post.hasName()) {
            params.add(Parameter.with(FacebookApiConstants.FIELD_NAME,
                    post.getName()));
        }

        FacebookType result = null;
        try {
            result = client.getClient().publish(
                    containerFbId + "/" + FacebookApiConstants.CONNECTION_FEED,
                    FacebookType.class,
                    params.toArray(new Parameter[0]));
        } catch (FacebookException e) {
            FacebookConnector.handleFacebookException(e);
        }

        if (null != result && null != result.getId()) {
            JsonObject object = null;
            try {
                object = client.getClient().fetchObject(
                        result.getId(),
                        JsonObject.class);
            } catch (FacebookException e) {
                FacebookConnector.handleFacebookException(e);
            }

            Post addedPost = FacebookSiocConverter.createSiocPost(connector, object, container);
            addedPost.setSibling(post);
            post.addSibling(addedPost);
            LOG.debug("Successfully writen post {} to container {}.", post, container);
        }
    }

    @Override
    public boolean canReplyTo(Post post) {
        return null != post &&
                post.toString().startsWith(serviceEndpoint) &&
                post.hasId() &&
                post.getId().startsWith(FacebookSiocConverter.POST_ID_PREFIX);
    }

    @Override
    public void writeReply(Post replyPost, Post parentPost) throws AuthenticationException,
            IOException {
        // TODO Auto-generated method stub

    }

}
