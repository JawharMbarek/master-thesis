
package de.m0ep.socc.core.connector.google.youtube.v2;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.util.Builder;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Site;
import org.rdfs.sioc.Thread;

import com.damnhandy.uri.template.UriTemplate;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.google.gdata.data.youtube.PlaylistLinkEntry;
import com.google.gdata.data.youtube.PlaylistLinkFeed;
import com.google.gdata.util.ServiceException;

import de.m0ep.socc.core.connector.IConnector;
import de.m0ep.socc.core.connector.IConnector.IServiceStructureReader;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.exceptions.NotFoundException;

public class YoutubeV2StructureReader implements IServiceStructureReader {
    private static final String PARAM_USER_ID = "userId";
    private static final String FEED_USER_PLAYLISTS = "https://gdata.youtube.com/feeds/api/users/{userId}/playlists?v=2";
    private YoutubeV2Connector connector;
    private YoutubeV2ClientWrapper client;

    private Model model;
    private URI serviceEndpoint;

    private Forum playlists;
    private Forum uploads;

    public YoutubeV2StructureReader(YoutubeV2Connector youtubeV2Connector) {
        Preconditions.checkNotNull(youtubeV2Connector,
                "Required parameter youtubeV2Connector must be specified.");

        this.connector = youtubeV2Connector;
        this.model = this.connector.getContext().getModel();
        this.serviceEndpoint = this.connector.getService().getServiceEndpoint().asURI();
        this.client = (YoutubeV2ClientWrapper) this.connector.getServiceClientManager()
                .getDefaultClient();

        URI playlistsUri = Builder.createURI(
                serviceEndpoint
                        + "/"
                        + client.getUserProfile().getUsername()
                        + "/"
                        + YoutubeV2Connector.PLAYLISTS_ID);

        if (!Forum.hasInstance(model, playlistsUri)) {
            this.playlists = new Forum(model, playlistsUri, true);
            this.playlists.setId(
                    YoutubeV2Connector.PLAYLISTS_ID
                            + ":"
                            + client.getUserProfile().getUsername());
            this.playlists.setName(
                    (Strings.nullToEmpty(client.getUserProfile().getFirstName())
                            + " "
                            + Strings.nullToEmpty(client.getUserProfile().getLastName())
                            + "'s Playlists").trim());
            this.playlists.setNumThreads(0);

            Site site = getSite();
            this.playlists.setHost(site);
            site.addHostOf(this.playlists);
        } else {
            this.playlists = Forum.getInstance(model, playlistsUri);
        }

        URI uploadsUri = Builder.createURI(
                serviceEndpoint
                        + "/"
                        + client.getUserProfile().getUsername()
                        + "/"
                        + YoutubeV2Connector.UPLOADS_ID);

        if (!Forum.hasInstance(model, uploadsUri)) {
            this.uploads = new Forum(model, uploadsUri, true);
            this.uploads.setId(
                    YoutubeV2Connector.UPLOADS_ID
                            + ":"
                            + client.getUserProfile().getUsername());
            this.uploads.setName(
                    (Strings.nullToEmpty(client.getUserProfile().getFirstName())
                            + " "
                            + Strings.nullToEmpty(client.getUserProfile().getLastName())
                            + "'s Uploads").trim());
            this.uploads.setNumItems(0);

            Site site = getSite();
            this.uploads.setHost(site);
            site.addHostOf(this.uploads);
        } else {
            this.uploads = Forum.getInstance(model, uploadsUri);
        }
    }

    @Override
    public IConnector getConnector() {
        return connector;
    }

    @Override
    public Site getSite() {
        Site result = Site.getInstance(model, serviceEndpoint);

        if (null == result) {
            result = new Site(model, serviceEndpoint, true);
            result.setName("Youtube");
        }

        return result;
    }

    @Override
    public Forum getForum(String id) throws NotFoundException, AuthenticationException, IOException {
        Preconditions.checkNotNull(id,
                "Required parameter id must be specified.");
        Preconditions.checkArgument(!id.isEmpty(),
                "Required parameter id may not be empty.");

        if (YoutubeV2Connector.PLAYLISTS_ID.equals(id)) {
            return playlists;
        } else if (YoutubeV2Connector.UPLOADS_ID.equals(id)) {
            return uploads;
        }

        throw new NotFoundException("No forum with with id " + id);
    }

    @Override
    public List<Forum> listForums() throws AuthenticationException, IOException {
        return Lists.newArrayList(playlists, uploads);
    }

    @Override
    public Thread getThread(String id, Container parent) throws NotFoundException,
            AuthenticationException, IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Thread> listThreads(Container parent) throws AuthenticationException, IOException {
        Preconditions.checkNotNull(parent,
                "Required parameter parent must be specified.");
        Preconditions.checkArgument(parent.getResource().equals(playlists.getResource()),
                "Only the playlists forum contains threads");

        String nextFeed = UriTemplate.fromTemplate(FEED_USER_PLAYLISTS)
                .set(PARAM_USER_ID, client.getUserProfile().getUsername())
                .expand();

        List<Thread> results = Lists.newArrayList();
        do {

            PlaylistLinkFeed playlistFeed = null;
            try {
                playlistFeed = client.getService().getFeed(
                        new URL(nextFeed),
                        PlaylistLinkFeed.class);
            } catch (com.google.gdata.util.AuthenticationException e) {
                throw new AuthenticationException(e);
            } catch (ServiceException e) {
                Throwables.propagate(e);
            } finally {
                nextFeed = null;
            }

            if (null != playlistFeed) {
                for (PlaylistLinkEntry playlistEntry : playlistFeed.getEntries()) {
                    results.add(YoutubeV2SiocConverter.createSiocThread(
                            connector,
                            playlistEntry,
                            playlists));
                }
            }

            if (null != playlistFeed.getNextLink()) {
                nextFeed = playlistFeed.getNextLink().getHref();
            }
        } while (null != nextFeed);

        return results;
    }
}
