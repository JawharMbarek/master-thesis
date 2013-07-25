
package de.m0ep.socc.core.connector.google.youtube.v2;

import java.io.IOException;
import java.util.List;

import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.node.URI;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Site;
import org.rdfs.sioc.Thread;

import com.google.common.base.Preconditions;
import com.google.gdata.client.youtube.YouTubeService;

import de.m0ep.socc.core.connector.IConnector;
import de.m0ep.socc.core.connector.IConnector.IServiceStructureReader;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.exceptions.NotFoundException;

public class YoutubeV2StructureReader implements IServiceStructureReader {
    private YoutubeV2Connector connector;
    private Model model;
    private URI serviceEndpoint;
    private YouTubeService client;

    public YoutubeV2StructureReader(YoutubeV2Connector youtubeV2Connector) {
        Preconditions.checkNotNull(youtubeV2Connector,
                "Required parameter youtubeV2Connector must be specified.");

        this.model = connector.getContext().getModel();
        this.serviceEndpoint = connector.getService().getServiceEndpoint().asURI();
        this.client = (YouTubeService) connector.getServiceClientManager().getDefaultClient();
    }

    @Override
    public IConnector getConnector() {
        return connector;
    }

    @Override
    public Site getSite() {
        if (!Site.hasInstance(model, serviceEndpoint)) {
            Site result = new Site(model, serviceEndpoint, true);
            result.setName("Youtube");
            return result;
        }

        return Site.getInstance(model, serviceEndpoint);
    }

    @Override
    public Forum getForum(String id) throws NotFoundException, AuthenticationException, IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Forum> listForums() throws AuthenticationException, IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Thread getThread(String id, Container parent) throws NotFoundException,
            AuthenticationException, IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Thread> listThreads(Container parent) throws AuthenticationException, IOException {
        // TODO Auto-generated method stub
        return null;
    }

}
