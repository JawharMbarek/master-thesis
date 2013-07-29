
package de.m0ep.socc.core.connector.google.youtube.v2;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;

import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.Thread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.damnhandy.uri.template.UriTemplate;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.VideoFeed;
import com.google.gdata.util.ServiceException;

import de.m0ep.socc.core.connector.IConnector;
import de.m0ep.socc.core.connector.IConnector.IPostReader;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.utils.RdfUtils;

public class YoutubeV2PostReader implements IPostReader {
    private static final Logger LOG = LoggerFactory.getLogger(YoutubeV2PostReader.class);

    private static final UriTemplate playlistUriTemplate = UriTemplate.fromTemplate(
            "http://gdata.youtube.com/feeds/api/playlists/{playlistId}?v=2");

    public static final String KEY_PLAYLIST_ID = "playlistId";

    private YoutubeV2Connector connector;
    private YoutubeV2ClientWrapper client;
    private String serviceEndpoint;

    public YoutubeV2PostReader(YoutubeV2Connector connector) {
        this.connector = connector;
        this.client = (YoutubeV2ClientWrapper) this.connector.getServiceClientManager()
                .getDefaultClient();
        this.serviceEndpoint = connector.getService().getServiceEndpoint().toString();
    }

    @Override
    public IConnector getConnector() {
        return connector;
    }

    @Override
    public boolean containsPosts(Container container) {
        return isUploadsContainer(container) || isPlaylistContainer(container);
    }

    @Override
    public List<Post> readNewPosts(Date lastPostDate, long limit, Container container)
            throws AuthenticationException, IOException {
        if (0 == limit) {
            return Lists.newArrayList();
        }

        Preconditions.checkNotNull(container,
                "Required parameter container must be specified.");
        Preconditions.checkArgument(containsPosts(container),
                "The container contains no posts on this service.");
        List<Post> results = Lists.newArrayList();

        String nextFeedUri = null;
        if (isUploadsContainer(container)) {
            nextFeedUri = UriTemplate.fromTemplate(
                    "http://gdata.youtube.com/feeds/api/users/{userId}/uploads?v=2")
                    .set("userId", container.getId().split(":")[1])
                    .expand();
        } else if (isPlaylistContainer(container)) {
            results.addAll(readPlaylistPosts(lastPostDate, limit, container));
        }

        while (null != nextFeedUri) {
            System.out.println(nextFeedUri);
            VideoFeed videoFeed = null;
            try {
                videoFeed = client.getService().getFeed(
                        new URL(nextFeedUri),
                        VideoFeed.class);
                LOG.debug("Fetched {} videos from {}.", videoFeed.getEntries().size(), nextFeedUri);

            } catch (com.google.gdata.util.AuthenticationException e) {
                throw new AuthenticationException(e);
            } catch (ServiceException e) {
                Throwables.propagate(e);
            } finally {
                nextFeedUri = null;
            }

            if (null != videoFeed) {
                for (VideoEntry entry : videoFeed.getEntries()) {
                    results.add(
                            YoutubeV2SiocConverter.createSiocPostFromVideoEntry(
                                    connector,
                                    entry,
                                    container));
                }
            }

            if (null != videoFeed.getNextLink()) {
                nextFeedUri = videoFeed.getNextLink().getHref();
            }
        }

        return results;
    }

    private List<Post> readUploadsPosts(Date lastPostDate, long limit,
            Container container) throws AuthenticationException, IOException {

        List<Post> results = Lists.newArrayList();

        String nextFeedUri = UriTemplate.fromTemplate(
                "http://gdata.youtube.com/feeds/api/users/{userId}/uploads?v=2")
                .set("userId", container.getId().split(":")[1])
                .expand();

        do {
            System.out.println(nextFeedUri);
            VideoFeed videoFeed = null;
            try {
                videoFeed = client.getService().getFeed(
                        new URL(nextFeedUri),
                        VideoFeed.class);
                LOG.debug("Fetched {} videos from {}.", videoFeed.getEntries().size(), nextFeedUri);

            } catch (com.google.gdata.util.AuthenticationException e) {
                throw new AuthenticationException(e);
            } catch (ServiceException e) {
                Throwables.propagate(e);
            } finally {
                nextFeedUri = null;
            }

            if (null != videoFeed) {
                for (VideoEntry entry : videoFeed.getEntries()) {
                    results.add(
                            YoutubeV2SiocConverter.createSiocPostFromVideoEntry(
                                    connector,
                                    entry,
                                    container));
                }
            }

            if (null != videoFeed.getNextLink()) {
                nextFeedUri = videoFeed.getNextLink().getHref();
            }
        } while (null != nextFeedUri);

        return results;
    }

    private List<Post> readPlaylistPosts(Date lastPostDate, long limit,
            Container container) throws AuthenticationException, IOException {

        List<Post> results = Lists.newArrayList();

        return results;
    }

    @Override
    public boolean containsReplies(Post post) {
        return post.toString().startsWith(serviceEndpoint) &&
                !post.hasReplyOf() &&
                post.hasContainer() &&
                post.getContainer().toString().startsWith(serviceEndpoint);
    }

    @Override
    public List<Post> readNewReplies(Date lastReplyDate, long limit, Post parentPost)
            throws AuthenticationException, IOException {
        // TODO Auto-generated method stub
        return null;
    }

    private boolean isPlaylistContainer(Container container) {
        return container.toString().startsWith(serviceEndpoint) &&
                RdfUtils.isType(
                        container.getModel(),
                        container.getResource(),
                        Thread.RDFS_CLASS) &&
                container.hasParent() &&
                container.getParent().toString().startsWith(serviceEndpoint) &&
                container.getParent().hasId() &&
                container.getId().startsWith(YoutubeV2Connector.PLAYLISTS_ID);
    }

    private boolean isUploadsContainer(Container container) {
        return container.toString().startsWith(serviceEndpoint) &&
                RdfUtils.isType(
                        container.getModel(),
                        container.getResource(),
                        Forum.RDFS_CLASS) &&
                container.hasId() &&
                container.getId().startsWith(YoutubeV2Connector.UPLOADS_ID);
    }

}
