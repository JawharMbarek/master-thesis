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

package de.m0ep.socc.connectors.facebook;

import org.ontoware.rdf2go.model.node.URI;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Post;

import com.restfb.json.JsonArray;
import com.restfb.json.JsonObject;

import de.m0ep.socc.connectors.exceptions.ConnectorException;
import de.m0ep.socc.utils.RDF2GoUtils;
import de.m0ep.socc.utils.SIOCUtils;
import de.m0ep.socc.utils.StringUtils;

public class FacebookPostParser {

    private static final String TYPE_PHOTO = "photo";
    private static final String TYPE_STATUS = "status";
    private static final String TYPE_LINK = "link";
    private static final String TYPE_VIDEO = "video";

    private static final String ID = "id";
    private static final String TYPE = "type";
    private static final String STORY = "story";
    private static final String MESSAGE = "message";
    private static final String COMMENTS = "comments";
    private static final String DATA = "data";
    private static final String DESCRIPTION = "description";
    private static final String CAPTION = "caption";
    private static final String SOURCE = "source";
    private static final String UPDATED_TIME = "updated_time";
    private static final String LINK = "link";
    private static final String NAME = "name";
    private static final String FROM = "from";
    private static final String CREATED_TIME = "created_time";

    public static Post parse(final FacebookConnector connector,
	    final JsonObject obj, final Container parentContainer) {
	String type = obj.getString(TYPE);
	Post post = null;

	if (TYPE_STATUS.equalsIgnoreCase(type)) {
	    post = parseStatusMessage(connector, obj, parentContainer);
	} else if (TYPE_LINK.equalsIgnoreCase(type)) {
	    post = parseLinkMessage(connector, obj, parentContainer);
	} else if (TYPE_VIDEO.equalsIgnoreCase(type)) {
	    post = parseVideoMessage(connector, obj, parentContainer);
	} else if (TYPE_PHOTO.equalsIgnoreCase(type)) {
	    post = parsePhotoMessage(connector, obj, parentContainer);
	} else {
	    throw new ConnectorException("failed to parse object");
	}

	if (null != post) {
	    post.setContainer(parentContainer);
	    parentContainer.addContainerof(post);
	    SIOCUtils.updateLastItemDate(parentContainer, post);

	    if (obj.has(COMMENTS)) {
		JsonObject comments = obj.getJsonObject(COMMENTS);

		if (comments.has(DATA)) {
		    JsonArray data = comments.getJsonArray(DATA);

		    for (int i = 0; i < data.length(); i++) {
			Post comment = parseComment(connector,
				data.getJsonObject(i), post);
			comment.setReplyof(post);
			post.addReply(comment);
			SIOCUtils.updateLastReplyDate(post, comment);
		    }
		}
	    }
	}

	return post;
    }

    private static Post parseStatusMessage(FacebookConnector connector,
	    JsonObject obj, final Container parentContainer) {
	String id = obj.getString(ID);
	URI uri = RDF2GoUtils.createURI(connector.getURL() + id);

	if (!Post.hasInstance(connector.getModel(), uri)) {
	    Post result = new Post(connector.getModel(), uri, true);
	    result.setId(id);

	    if (obj.has(FROM))
		result.setCreator(connector.getUserAccount(obj.getJsonObject(FROM)
			.getString(ID)));

	    String content = "";
	    if (obj.has(STORY))
		content = obj.getString(STORY);
	    else if (obj.has(MESSAGE))
		content = obj.getString(MESSAGE);

	    result.setContent(StringUtils.stripHTML(content));
	    result.setContentEncoded(RDF2GoUtils.createCDATASection(content));

	    /* just in case... */
	    if (obj.has(LINK))
		result.setAttachment(RDF2GoUtils.createURI(obj.getString(LINK)));

	    if (obj.has(CREATED_TIME))
		result.setCreated(obj.getString(CREATED_TIME));

	    if (obj.has(UPDATED_TIME))
		result.setModified(obj.getString(UPDATED_TIME));

	    return result;

	} else {
	    return Post.getInstance(connector.getModel(), uri);
	}
    }

    private static Post parseLinkMessage(FacebookConnector connector,
	    JsonObject obj, final Container parentContainer) {
	String id = obj.getString(ID);
	URI uri = RDF2GoUtils.createURI(connector.getURL() + id);

	if (!Post.hasInstance(connector.getModel(), uri)) {
	    Post result = new Post(connector.getModel(), uri, true);
	    result.setId(id);

	    if (obj.has(FROM))
		result.setCreator(connector.getUserAccount(obj.getJsonObject(FROM)
			.getString(ID)));

	    if (obj.has(NAME))
		result.setName(obj.getString(NAME));

	    if (obj.has(LINK))
		result.setAttachment(RDF2GoUtils.createURI(obj.getString(LINK)));

	    if (obj.has(CAPTION))
		result.setTitle(CAPTION);

	    if (obj.has(DESCRIPTION))
		result.setDescription(obj.getString(DESCRIPTION));

	    String content = "";
	    if (obj.has(STORY))
		content = obj.getString(STORY);
	    else if (obj.has(MESSAGE))
		content = obj.getString(MESSAGE);

	    result.setContent(StringUtils.stripHTML(content));
	    result.setContentEncoded(RDF2GoUtils.createCDATASection(content));

	    if (obj.has(CREATED_TIME))
		result.setCreated(obj.getString(CREATED_TIME));

	    if (obj.has(UPDATED_TIME))
		result.setModified(obj.getString(UPDATED_TIME));

	    return result;

	} else {
	    return Post.getInstance(connector.getModel(), uri);
	}
    }

    private static Post parseVideoMessage(FacebookConnector connector,
	    JsonObject obj, final Container parentContainer) {
	String id = obj.getString(ID);
	URI uri = RDF2GoUtils.createURI(connector.getURL() + id);

	if (!Post.hasInstance(connector.getModel(), uri)) {
	    Post result = new Post(connector.getModel(), uri, true);
	    result.setId(id);

	    if (obj.has(FROM))
		result.setCreator(connector.getUserAccount(obj.getJsonObject(FROM)
			.getString(ID)));

	    if (obj.has(NAME))
		result.setName(obj.getString(NAME));

	    if (obj.has(SOURCE))
		result.setAttachment(RDF2GoUtils.createURI(obj
			.getString(SOURCE)));

	    String content = "";
	    if (obj.has(STORY))
		content = obj.getString(STORY);
	    else if (obj.has(MESSAGE))
		content = obj.getString(MESSAGE);

	    result.setContent(StringUtils.stripHTML(content));
	    result.setContentEncoded(RDF2GoUtils.createCDATASection(content));

	    if (obj.has(CREATED_TIME))
		result.setCreated(obj.getString(CREATED_TIME));

	    if (obj.has(UPDATED_TIME))
		result.setModified(obj.getString(UPDATED_TIME));

	    return result;

	} else {
	    return Post.getInstance(connector.getModel(), uri);
	}
    }

    private static Post parsePhotoMessage(FacebookConnector connector,
	    JsonObject obj, final Container parentContainer) {

	String id = obj.getString(ID);
	URI uri = RDF2GoUtils.createURI(connector.getURL() + id);

	if (!Post.hasInstance(connector.getModel(), uri)) {
	    Post result = new Post(connector.getModel(), uri, true);
	    result.setId(id);

	    if (obj.has(FROM))
		result.setCreator(connector.getUserAccount(obj.getJsonObject(FROM)
			.getString(ID)));

	    if (obj.has(NAME))
		result.setName(obj.getString(NAME));

	    if (obj.has(LINK))
		result.setAttachment(RDF2GoUtils.createURI(obj.getString(LINK)));

	    String content = "";
	    if (obj.has(STORY))
		content = obj.getString(STORY);
	    else if (obj.has(MESSAGE))
		content = obj.getString(MESSAGE);

	    result.setContent(StringUtils.stripHTML(content));
	    result.setContentEncoded(RDF2GoUtils.createCDATASection(content));

	    if (obj.has(CREATED_TIME))
		result.setCreated(obj.getString(CREATED_TIME));

	    if (obj.has(UPDATED_TIME))
		result.setModified(obj.getString(UPDATED_TIME));

	    return result;

	} else {
	    return Post.getInstance(connector.getModel(), uri);
	}
    }

    private static Post parseComment(final FacebookConnector connector,
	    final JsonObject obj, final Post parentPost) {

	String id = obj.getString(ID);
	URI uri = RDF2GoUtils.createURI(connector.getURL() + id);

	if (!Post.hasInstance(connector.getModel(), uri)) {
	    Post result = new Post(connector.getModel(), uri, true);
	    result.setId(id);

	    if (obj.has(FROM))
		result.setCreator(connector.getUserAccount(obj.getJsonObject(FROM)
			.getString(ID)));

	    String content = "";
	    if (obj.has(MESSAGE))
		content = obj.getString(MESSAGE);

	    result.setContent(StringUtils.stripHTML(content));
	    result.setContentEncoded(RDF2GoUtils.createCDATASection(content));

	    if (obj.has(CREATED_TIME))
		result.setCreated(obj.getString(CREATED_TIME));

	    return result;

	} else {
	    return Post.getInstance(connector.getModel(), uri);
	}
    }
}
