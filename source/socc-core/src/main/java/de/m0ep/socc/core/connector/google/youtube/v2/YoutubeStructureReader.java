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

package de.m0ep.socc.core.connector.google.youtube.v2;

import java.io.IOException;
import java.net.URL;
import java.util.List;

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
import com.google.gdata.util.ResourceNotFoundException;
import com.google.gdata.util.ServiceException;
import com.google.gdata.util.ServiceForbiddenException;

import de.m0ep.socc.core.connector.AbstractConnectorIOComponent;
import de.m0ep.socc.core.connector.IConnector.IStructureReader;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.exceptions.NotFoundException;
import de.m0ep.socc.core.utils.RdfUtils;

public class YoutubeStructureReader extends
        AbstractConnectorIOComponent<YoutubeConnector> implements
        IStructureReader {

    private YoutubeClientWrapper defaultClient;

    private Forum playlists;
    private Forum uploads;

    public YoutubeStructureReader(YoutubeConnector connector) {
        super(connector);
        this.defaultClient = connector.getServiceClientManager()
                .getDefaultClient();

        URI playlistsUri = Builder.createURI(
                getServiceEndpoint()
                        + YoutubeSiocConverter.PLAYLISTS_URI_PATH
                        + defaultClient
                                .getUserProfile()
                                .getUsername());

        if (!Forum.hasInstance(getModel(), playlistsUri)) {
            this.playlists = new Forum(getModel(), playlistsUri, true);
            this.playlists.setId(
                    YoutubeSiocConverter.PLAYLISTS_ID_PREFIX
                            + defaultClient
                                    .getUserProfile()
                                    .getUsername());
            this.playlists
                    .setName(
                    (Strings.nullToEmpty(defaultClient
                            .getUserProfile().getFirstName())
                            + " "
                            + Strings
                                    .nullToEmpty(defaultClient
                                            .getUserProfile().getLastName())
                            + "'s Playlists").trim());
            this.playlists.setNumThreads(0);

            Site site = getSite();
            this.playlists.setHost(site);
            site.addHostOf(this.playlists);
        } else {
            this.playlists = Forum.getInstance(getModel(), playlistsUri);
        }

        URI uploadsUri = Builder.createURI(
                getServiceEndpoint()
                        + YoutubeSiocConverter.UPLOADS_URI_PATH
                        + defaultClient
                                .getUserProfile()
                                .getUsername());

        if (!Forum.hasInstance(getModel(), uploadsUri)) {
            this.uploads = new Forum(getModel(), uploadsUri, true);
            this.uploads.setId(
                    YoutubeSiocConverter.UPLOADS_ID_PREFIX
                            + defaultClient
                                    .getUserProfile()
                                    .getUsername());
            this.uploads
                    .setName(
                    (Strings.nullToEmpty(defaultClient
                            .getUserProfile().getFirstName())
                            + " "
                            + Strings
                                    .nullToEmpty(defaultClient
                                            .getUserProfile().getLastName())
                            + "'s Uploads").trim());
            this.uploads.setNumItems(0);

            Site site = getSite();
            this.uploads.setHost(site);
            site.addHostOf(this.uploads);
        } else {
            this.uploads = Forum.getInstance(getModel(), uploadsUri);
        }
    }

    @Override
    public Site getSite() {
        Site result = Site.getInstance(getModel(), getServiceEndpoint());

        if (null == result) {
            result = new Site(getModel(), getServiceEndpoint(), true);
            result.setName("Youtube");
        }

        return result;
    }

    @Override
    public Forum getForum(String id) throws NotFoundException,
            AuthenticationException, IOException {
        Preconditions.checkNotNull(id,
                "Required parameter id must be specified.");
        Preconditions.checkArgument(!id.isEmpty(),
                "Required parameter id may not be empty.");

        if (id.startsWith(YoutubeSiocConverter.PLAYLISTS_ID_PREFIX)) {
            return playlists;
        } else if (id.startsWith(YoutubeSiocConverter.UPLOADS_ID_PREFIX)) {
            return uploads;
        }

        throw new NotFoundException("No forum with with id " + id);
    }

    @Override
    public List<Forum> listForums() throws AuthenticationException, IOException {
        return Lists.newArrayList(playlists, uploads);
    }

    @Override
    public Thread getThread(String id, Container parent)
            throws NotFoundException,
            AuthenticationException, IOException {
        Preconditions.checkNotNull(id,
                "Required parameter id must be specified.");
        Preconditions.checkArgument(!id.isEmpty(),
                "Required parameter id may not be empty.");
        Preconditions.checkNotNull(parent,
                "Required parameter parent must be specified.");
        Preconditions.checkArgument(RdfUtils.isType(
                parent.getModel(),
                parent.getResource(),
                Forum.RDFS_CLASS),
                "The parameter parent is no sioc:forum.");
        Preconditions.checkArgument(parent.hasId(),
                "The parameter parent has no id");
        Preconditions.checkArgument(parent.getId().startsWith(
                YoutubeSiocConverter.PLAYLISTS_ID_PREFIX),
                "The parent is no Playlist forum");

        String userId = parent.getId().split(YoutubeSiocConverter.ID_SEPERATOR)[1];
        String playlistUri = UriTemplate
                .fromTemplate(
                        "http://gdata.youtube.com/feeds/api/users/{userId}/playlists/{playlistId}?v=2")
                .set("userId", userId)
                .set("playlistId", id)
                .expand(); // FIXME: magic strings

        PlaylistLinkEntry playlistEntry = null;
        try {
            getConnector().waitForCooldown();
            playlistEntry = defaultClient
                    .getService().getEntry(
                            new URL(playlistUri),
                            PlaylistLinkEntry.class);
        } catch (com.google.gdata.util.AuthenticationException e) {
            throw new AuthenticationException(e);
        } catch (ServiceForbiddenException e) {
            throw new AuthenticationException(
                    defaultClient
                            .getUserProfile().getUsername()
                            + " has no access to the youtube service.", e);
        } catch (ResourceNotFoundException e) {
            throw new NotFoundException("No thread found with id " + id);
        } catch (ServiceException e) {
            throw Throwables.propagate(e);
        }

        if (null != playlistEntry) {
            Thread result = YoutubeSiocConverter.createSiocThread(
                    getConnector(),
                    playlistEntry,
                    Forum.getInstance(parent.getModel(), parent.getResource()));

            return result;
        }

        throw new NotFoundException("No thread found with id " + id);
    }

    @Override
    public List<Thread> listThreads(Container parent)
            throws AuthenticationException, IOException {
        Preconditions.checkNotNull(parent,
                "Required parameter parent must be specified.");
        Preconditions.checkArgument(parent.getResource().equals(
                playlists.getResource()),
                "Only the playlists forum contains threads");

        String nextFeed = UriTemplate
                .fromTemplate(
                        "https://gdata.youtube.com/feeds/api/users/{userId}/playlists?v=2")
                .set("userId",
                        defaultClient
                                .getUserProfile()
                                .getUsername())
                .expand(); // FIXME: magic strings

        List<Thread> results = Lists.newArrayList();
        do {
            PlaylistLinkFeed playlistFeed = null;
            try {
                getConnector().waitForCooldown();
                playlistFeed = defaultClient
                        .getService().getFeed(
                                new URL(nextFeed),
                                PlaylistLinkFeed.class);
            } catch (com.google.gdata.util.AuthenticationException e) {
                throw new AuthenticationException(e);
            } catch (ServiceException e) {
                throw Throwables.propagate(e);
            } finally {
                nextFeed = null;
            }

            if (null != playlistFeed) {
                for (PlaylistLinkEntry playlistEntry : playlistFeed
                        .getEntries()) {
                    results.add(YoutubeSiocConverter.createSiocThread(
                            getConnector(),
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