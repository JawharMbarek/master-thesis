
package de.m0ep.socc.core.connector.google.youtube.v2;

import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.util.Builder;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.Thread;

import com.google.gdata.data.Link;
import com.google.gdata.data.Person;
import com.google.gdata.data.TextContent;
import com.google.gdata.data.youtube.PlaylistLinkEntry;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.YouTubeMediaGroup;

import de.m0ep.socc.core.utils.DateUtils;
import de.m0ep.socc.core.utils.StringUtils;

public class YoutubeV2SiocConverter {
    public static Thread createSiocThread(YoutubeV2Connector connector,
            PlaylistLinkEntry playlistEntry, Forum parent) {
        Model model = connector.getContext().getModel();
        URI uri = Builder.createURI(
                connector.getService().getServiceEndpoint()
                        + "/playlist/"
                        + playlistEntry.getPlaylistId());

        if (!Thread.hasInstance(model, uri)) {
            Thread result = new Thread(model, uri, true);
            result.setId(playlistEntry.getPlaylistId());
            result.setSeeAlso(Builder.createURI(playlistEntry.getSelfLink().getHref()));

            if (null != playlistEntry.getTitle()) {
                result.setName(playlistEntry.getTitle().getPlainText());
            }

            if (null != playlistEntry.getSummary()) {
                result.setDescription(playlistEntry.getSummary().getPlainText());
            }

            if (null != playlistEntry.getPublished()) {
                result.setCreated(DateUtils.formatISO8601(playlistEntry
                        .getPublished().getValue()));
            }
            if (null != playlistEntry.getUpdated()) {
                result.setModified(DateUtils.formatISO8601(playlistEntry
                        .getUpdated().getValue()));
            }

            result.setParent(parent);
            parent.addParentOf(result);
            parent.setNumThreads((parent.hasNumThreads()) ? (parent.getNumThreads() + 1) : (1));
            return result;
        }

        return Thread.getInstance(model, uri);
    }

    public static Post createSiocPostFromVideoEntry(YoutubeV2Connector connector,
            VideoEntry videoEntry, Container container) {
        Model model = connector.getContext().getModel();
        String videoId = getVideoId(videoEntry);
        URI uri = Builder.createURI(
                connector.getService().getServiceEndpoint()
                        + "/video/"
                        + videoId);

        if (!Post.hasInstance(model, uri)) {
            Post result = new Post(model, uri, true);
            result.setId(videoId);

            for (Person author : videoEntry.getAuthors()) {
                System.err.println(
                        author.getName()
                                + " "
                                + author.getEmail()
                                + " "
                                + author.getUri());
            }

            YouTubeMediaGroup mediaGroup = videoEntry.getMediaGroup();

            if (null != mediaGroup.getTitle()) {
                result.setTitle(mediaGroup.getTitle().getPlainTextContent());
            }

            String content = "";
            if (null != videoEntry.getContent()
                    && videoEntry.getContent() instanceof TextContent) {
                content = videoEntry.getPlainTextContent();
            } else if (null != mediaGroup.getDescription()) {
                content = mediaGroup.getDescription().getPlainTextContent();
            }
            result.setContent(StringUtils.stripHTML(content));
            result.setContentEncoded(content);

            Link videoLink = videoEntry.getLink("alternate", "text/html");
            if (null != videoLink) {
                result.addAttachment(Builder.createURI(videoLink.getHref()));
            }

            if (null != videoEntry.getPublished()) {
                result.setCreated(DateUtils.formatISO8601(videoEntry.getPublished()
                        .getValue()));
            }

            if (null != videoEntry.getUpdated()) {
                result.setModified(DateUtils.formatISO8601(videoEntry.getUpdated()
                        .getValue()));
            }

            result.setContainer(container);
            container.addContainerOf(result);
            container.setNumItems((container.hasNumItems()) ? (container
                    .getNumItems() + 1) : (1));

            return result;
        }

        return Post.getInstance(model, uri);
    }

    private static String getVideoId(VideoEntry videoEntry) {
        return videoEntry.getId().substring(videoEntry.getId().lastIndexOf(':') + 1);
    }
}
