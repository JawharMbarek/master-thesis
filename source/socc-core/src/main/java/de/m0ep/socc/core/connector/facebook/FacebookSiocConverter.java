
package de.m0ep.socc.core.connector.facebook;

import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.util.Builder;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Site;

import com.restfb.types.Group;

import de.m0ep.socc.core.utils.DateUtils;

public class FacebookSiocConverter {

    public static Forum createSiocForum(FacebookConnector connector, Group group) {
        URI serviceEndpoint = connector.getService().getServiceEndpoint().asURI();
        Model model = connector.getContext().getModel();

        URI uri = Builder.createURI(serviceEndpoint + "/group/" + group.getId());

        Forum result;
        if (Forum.hasInstance(model, uri)) {
            result = Forum.getInstance(model, uri);
        } else {
            result = new Forum(model, uri, true);
            result.setId("group:" + group.getId());

            Site site = connector.serviceStructureReader().getSite();
            result.setHost(site);
            site.setHostOf(result);
        }

        result.setName(group.getName());

        if (null != group.getDescription()) {
            result.setDescription(group.getDescription());
        }

        if (null != group.getUpdatedTime()) {
            result.setModified(DateUtils.formatISO8601(group.getUpdatedTime()));
        }

        return result;
    }

}
