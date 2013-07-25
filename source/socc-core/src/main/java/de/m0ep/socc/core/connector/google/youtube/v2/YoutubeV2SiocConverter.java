
package de.m0ep.socc.core.connector.google.youtube.v2;

import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.util.Builder;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Thread;

import com.google.gdata.data.youtube.PlaylistLinkEntry;

import de.m0ep.socc.core.utils.DateUtils;

public class YoutubeV2SiocConverter {

    public static Thread createSiocThread(YoutubeV2Connector connector,
            PlaylistLinkEntry playlistEntry, Forum parent) {

        URI uri = Builder.createURI(
                connector.getService().getServiceEndpoint()
                        + "/playlist/"
                        + playlistEntry.getId());

        Model model = connector.getContext().getModel();

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
}
