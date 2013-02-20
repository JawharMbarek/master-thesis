package de.m0ep.socc.connectors;

import org.ontoware.rdf2go.model.node.URI;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Post;

import com.restfb.json.JsonArray;
import com.restfb.json.JsonObject;

import de.m0ep.socc.ConnectorException;
import de.m0ep.socc.utils.RDFUtils;

public class FacebookPostParser {

    private static final String DESCRIPTION = "description";
    private static final String CAPTION = "caption";
    private static final String SOURCE = "source";
    private static final String UPDATED_TIME = "updated_time";
    private static final String LINK = "link";
    private static final String NAME = "name";
    private static final String FROM = "from";
    private static final String CREATED_TIME = "created_time";
    private static final String TYPE_PHOTO = "photo";
    private static final String TYPE_STATUS = "status";
    private static final String TYPE_LINK = LINK;
    private static final String TYPE_VIDEO = "video";

    private static final String ID = "id";
    private static final String TYPE = "type";
    private static final String STORY = "story";
    private static final String MESSAGE = "message";
    private static final String COMMENTS = "comments";
    private static final String DATA = "data";

    public static Post parse(final FacebookConnector connector,
	    final JsonObject obj, final Container parentContainer) {
	String type = obj.getString(TYPE);
	Post post = null;
	
	if(TYPE_STATUS.equalsIgnoreCase(type)){
	    post = parseStatusMessage(connector, obj, parentContainer);
	}
	else if(TYPE_LINK.equalsIgnoreCase(type)){
	    post = parseLinkMessage(connector, obj, parentContainer);
	}else if(TYPE_VIDEO.equalsIgnoreCase(type)){
	    post = parseVideoMessage(connector, obj, parentContainer);
	} else if (TYPE_PHOTO.equalsIgnoreCase(type)) {
	    post = parsePhotoMessage(connector, obj, parentContainer);
	} else {
	    throw new ConnectorException("failed to parse object");
	}
	
	if (null != post && obj.has(COMMENTS)) {
	    JsonObject comments = obj.getJsonObject(COMMENTS);

	    if (comments.has(DATA)) {
		JsonArray data = comments.getJsonArray(DATA);

		for (int i = 0; i < data.length(); i++) {
		    addComment(connector, data.getJsonObject(i), post);
		}
	    }
	}

	return post;
    }

    private static Post parseStatusMessage(FacebookConnector connector,
	    JsonObject obj, final Container parentContainer) {
	String id = obj.getString(ID);
	URI uri = RDFUtils.createURI(connector.getURL() + id);

	if (!Post.hasInstance(connector.getModel(), uri)) {
	    Post result = new Post(connector.getModel(), uri, true);
	    result.setId(id);

	    if (obj.has(FROM))
		result.setCreator(connector.getUser(obj.getJsonObject(FROM)
			.getString(ID)));

	    if (obj.has(STORY))
		result.setContent(obj.getString(STORY));
	    else if (obj.has(MESSAGE))
		result.setContent(obj.getString(MESSAGE));

	    /* just in case... */
	    if (obj.has(LINK))
		result.setAttachment(RDFUtils.createURI(obj.getString(LINK)));

	    if (obj.has(CREATED_TIME))
		result.setCreated(obj.getString(CREATED_TIME));

	    if (obj.has(UPDATED_TIME))
		result.setModified(obj.getString(UPDATED_TIME));

	    result.setContainer(parentContainer);
	    parentContainer.setContainerof(result);

	    return result;

	} else {
	    return Post.getInstance(connector.getModel(), uri);
	}
    }

    private static Post parseLinkMessage(FacebookConnector connector,
	    JsonObject obj, final Container parentContainer) {
	String id = obj.getString(ID);
	URI uri = RDFUtils.createURI(connector.getURL() + id);

	if (!Post.hasInstance(connector.getModel(), uri)) {
	    Post result = new Post(connector.getModel(), uri, true);
	    result.setId(id);

	    if (obj.has(FROM))
		result.setCreator(connector.getUser(obj.getJsonObject(FROM)
			.getString(ID)));

	    if (obj.has(NAME))
		result.setName(obj.getString(NAME));

	    if (obj.has(LINK))
		result.setAttachment(RDFUtils.createURI(obj.getString(LINK)));

	    if (obj.has(CAPTION))
		result.setTitle(CAPTION);

	    if (obj.has(DESCRIPTION))
		result.setDescription(obj.getString(DESCRIPTION));

	    if (obj.has(STORY))
		result.setContent(obj.getString(STORY));
	    else if (obj.has(MESSAGE))
		result.setContent(obj.getString(MESSAGE));

	    if (obj.has(CREATED_TIME))
		result.setCreated(obj.getString(CREATED_TIME));

	    if (obj.has(UPDATED_TIME))
		result.setModified(obj.getString(UPDATED_TIME));

	    result.setContainer(parentContainer);
	    parentContainer.setContainerof(result);

	    return result;

	} else {
	    return Post.getInstance(connector.getModel(), uri);
	}
    }

    private static Post parseVideoMessage(FacebookConnector connector,
	    JsonObject obj, final Container parentContainer) {
	String id = obj.getString(ID);
	URI uri = RDFUtils.createURI(connector.getURL() + id);

	if (!Post.hasInstance(connector.getModel(), uri)) {
	    Post result = new Post(connector.getModel(), uri, true);
	    result.setId(id);

	    if (obj.has(FROM))
		result.setCreator(connector.getUser(obj.getJsonObject(FROM)
			.getString(ID)));

	    if (obj.has(NAME))
		result.setName(obj.getString(NAME));

	    if (obj.has(SOURCE))
		result.setAttachment(RDFUtils.createURI(obj.getString(SOURCE)));

	    if (obj.has(STORY))
		result.setContent(obj.getString(STORY));
	    else if (obj.has(MESSAGE))
		result.setContent(obj.getString(MESSAGE));

	    if (obj.has(CREATED_TIME))
		result.setCreated(obj.getString(CREATED_TIME));

	    if (obj.has(UPDATED_TIME))
		result.setModified(obj.getString(UPDATED_TIME));

	    result.setContainer(parentContainer);
	    parentContainer.setContainerof(result);

	    return result;

	} else {
	    return Post.getInstance(connector.getModel(), uri);
	}
    }

    private static Post parsePhotoMessage(FacebookConnector connector,
	    JsonObject obj, final Container parentContainer) {

	String id = obj.getString(ID);
	URI uri = RDFUtils.createURI(connector.getURL() + id);

	if (!Post.hasInstance(connector.getModel(), uri)) {
	    Post result = new Post(connector.getModel(), uri, true);
	    result.setId(id);

	    if (obj.has(FROM))
		result.setCreator(connector.getUser(obj.getJsonObject(FROM)
			.getString(ID)));

	    if (obj.has(NAME))
		result.setName(obj.getString(NAME));

	    if (obj.has(LINK))
		result.setAttachment(RDFUtils.createURI(obj.getString(LINK)));

	    if (obj.has(STORY))
		result.setContent(obj.getString(STORY));
	    else if (obj.has(MESSAGE))
		result.setContent(obj.getString(MESSAGE));

	    if (obj.has(CREATED_TIME))
		result.setCreated(obj.getString(CREATED_TIME));

	    if (obj.has(UPDATED_TIME))
		result.setModified(obj.getString(UPDATED_TIME));

	    result.setContainer(parentContainer);
	    parentContainer.setContainerof(result);

	    return result;

	} else {
	    return Post.getInstance(connector.getModel(), uri);
	}
    }

    private static Post addComment(final FacebookConnector connector,
	    final JsonObject obj, final Post parentPost) {
	
	String id = obj.getString(ID);
	URI uri = RDFUtils.createURI(connector.getURL() + id);
	
	if (!Post.hasInstance(connector.getModel(), uri)) {
	    Post result = new Post(connector.getModel(), uri, true);
	    result.setId(id);

	    if (obj.has(FROM))
		result.setCreator(connector.getUser(obj.getJsonObject(FROM)
			.getString(ID)));

	    if (obj.has(MESSAGE))
		result.setContent(obj.getString(MESSAGE));

	    if (obj.has(CREATED_TIME))
		result.setCreated(obj.getString(CREATED_TIME));

	    parentPost.addReply(result);
	    result.setReplyof(parentPost);

	    return result;

	} else {
	    return Post.getInstance(connector.getModel(), uri);
	}
    }
}
