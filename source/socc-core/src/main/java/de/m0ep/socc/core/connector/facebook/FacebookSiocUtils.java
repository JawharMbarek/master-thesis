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

package de.m0ep.socc.core.connector.facebook;

import java.util.Date;

import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.node.Node;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.util.Builder;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.Site;
import org.rdfs.sioc.UserAccount;

import com.damnhandy.uri.template.UriTemplate;
import com.google.common.base.Preconditions;
import com.restfb.json.JsonObject;
import com.xmlns.foaf.Person;

import de.m0ep.socc.core.exceptions.NotFoundException;
import de.m0ep.socc.core.utils.DateUtils;
import de.m0ep.socc.core.utils.SiocUtils;
import de.m0ep.socc.core.utils.UserAccountUtils;

public class FacebookSiocUtils {
	// Connection names
	public static final class Connections {
		static final String COMMENTS = "comments";
		static final String FEED = "feed";
		static final String GROUPS = "groups";

		private Connections() {
		}
	}

	// JSON Fields
	public static final class Fields {
		public static final String ABOUT = "about";
		public static final String ATTACHMENT = "attachment";
		public static final String CAPTION = "caption";
		public static final String COMMENTS = "comments";
		public static final String CONNECTIONS = "connections";
		public static final String CREATED_TIME = "created_time";
		public static final String DATA = "data";
		public static final String DESCRIPTION = "description";
		public static final String FROM = "from";
		public static final String GENDER = "gender";
		public static final String ID = "id";
		public static final String LINK = "link";
		public static final String MESSAGE = "message";
		public static final String METADATA = "metadata";
		public static final String NAME = "name";
		public static final String OWNER = "owner";
		public static final String PARENT = "parent";
		public static final String SOURCE = "source";
		public static final String STORY = "story";
		public static final String TARGET = "target";
		public static final String TYPE = "type";
		public static final String UPDATED_TIME = "updated_time";
		public static final String URL = "url";

		private Fields() {
		}
	}

	// Request parameters
	public static final class RequestParameters {
		public static final String LIMIT = "limit";
		public static final String SINCE = "since";
		public static final String FIELDS = "fields";
		public static final String METADATA = "metadata";

		private RequestParameters() {
		}
	}

	static final String REGEX_FACEBOOK_URI = "^https://[\\w]+.facebook.com/([^/\\s?=]+)";

	// Request fields lists	
	static final String FIELDS_GROUP = Fields.ID + ","
	        + Fields.NAME + ","
	        + Fields.DESCRIPTION;

	static final String FIELDS_POST = Fields.ID + ","
	        + Fields.FROM + ","
	        + Fields.MESSAGE + ","
	        + Fields.STORY + ","
	        + Fields.NAME + ","
	        + Fields.CAPTION + ","
	        + Fields.LINK + ","
	        + Fields.SOURCE + ","
	        + Fields.CREATED_TIME + ","
	        + Fields.UPDATED_TIME + ","
	        + Fields.DESCRIPTION;

	static final String FIELDS_COMMENT = Fields.ID + ","
	        + Fields.FROM + ","
	        + Fields.MESSAGE + ","
	        + Fields.CREATED_TIME + ","
	        + Fields.ATTACHMENT;

	public static Forum createSiocForum( final FacebookConnector connector, final JsonObject object ) {
		Model model = connector.getContext().getModel();
		URI uri = FacebookSiocUtils.createSiocUri( object.getString( Fields.ID ) );

		Forum result = null;
		if ( Forum.hasInstance( model, uri ) ) {
			result = Forum.getInstance( model, uri );
		} else {
			result = new Forum( model, uri, true );
			result.setId( object.getString( Fields.ID ) );
			result.setName( object.getString( Fields.NAME ) + "'s Wall" );

			result.setNumItems( 0 );

			Site site = connector.getStructureReader().getSite();
			result.setHost( site );
			site.addHostOf( result );
		}

		if ( object.has( Fields.DESCRIPTION ) ) {
			result.setDescription( object.getString( Fields.DESCRIPTION ) );
		} else if ( object.has( Fields.ABOUT ) ) {
			result.setDescription( object.getString( Fields.ABOUT ) );
		}

		return result;
	}

	public static Post createSiocPost(
	        final FacebookConnector connector,
	        final JsonObject object,
	        final Container container,
	        final Post parentPost ) {
		Preconditions.checkNotNull( connector,
		        "Required parameter connector must be specified." );
		Preconditions.checkNotNull( object,
		        "Required parameter object must be specified." );

		Model model = connector.getContext().getModel();
		URI serviceEndpoint = connector.getService().getServiceEndpoint()
		        .asURI();
		URI uri = createSiocUri( object.getString( Fields.ID ) );

		Post result;
		if ( Post.hasInstance( model, uri ) ) {
			result = Post.getInstance( model, uri );

			if ( object.has( Fields.UPDATED_TIME ) ) {
				Date modifiedDate = com.restfb.util.DateUtils
				        .toDateFromLongFormat(
				        object.getString( Fields.UPDATED_TIME ) );
				Node modifiedNode = Builder.createPlainliteral(
				        DateUtils.formatISO8601( modifiedDate ) );

				if ( !result.hasModified( modifiedNode ) ) {
					result.removeAllAttachments();
					setPostCoreProperties( result, object );
					result.setModified( modifiedNode );
				}
			}
		} else {
			result = new Post( model, uri, true );

			result.setId( object.getString( Fields.ID ) );

			JsonObject fromObject = object.getJsonObject( Fields.FROM );
			String creatorId = fromObject.getString( Fields.ID );

			// check if we already know the author, else create a new
			// UserAccount + Person
			try {
				result.setCreator( UserAccountUtils.findUserAccount(
				        connector.getContext().getModel(),
				        creatorId,
				        serviceEndpoint ) );
			} catch ( NotFoundException e ) {
				result.setCreator( createSiocUserAccount( connector, fromObject ) );
			}

			if ( object.has( Fields.CREATED_TIME ) ) {
				Date date = com.restfb.util.DateUtils
				        .toDateFromLongFormat(
				        object.getString( Fields.CREATED_TIME ) );
				result.setCreated( DateUtils.formatISO8601( date ) );
			}

			if ( object.has( Fields.UPDATED_TIME ) ) {
				Date date = com.restfb.util.DateUtils
				        .toDateFromLongFormat(
				        object.getString( Fields.UPDATED_TIME ) );
				result.setModified( DateUtils.formatISO8601( date ) );
			}

			setPostCoreProperties( result, object );

		}

		if ( null != container && !result.hasContainer() ) {
			result.setContainer( container );
			container.addContainerOf( result );
			SiocUtils.incNumItems( container );
		}

		if ( null != parentPost && !result.hasReplyOf() ) {
			result.setReplyOf( parentPost );
			parentPost.addReply( result );
			SiocUtils.incNumReplies( parentPost );
		}

		return result;
	}

	private static void setPostCoreProperties(
	        final Post result,
	        final JsonObject object ) {
		Preconditions.checkNotNull( result,
		        "Required parameter result must be specified." );
		Preconditions.checkNotNull( object,
		        "Required parameter object must be specified." );

		// content
		if ( object.has( Fields.MESSAGE ) ) {
			result.setContent(
			        object.getString( Fields.MESSAGE ) );
		} else if ( object.has( Fields.DESCRIPTION ) ) {
			result.setContent(
			        object.getString( Fields.DESCRIPTION ) );
		} else if ( object.has( Fields.STORY ) ) {
			result.setContent(
			        object.getString( Fields.STORY ) );
		}

		// title
		if ( object.has( Fields.NAME ) ) {
			result.setTitle( object.getString( Fields.NAME ) );
		} else if ( object.has( Fields.CAPTION ) ) {
			result.setTitle( Fields.CAPTION );
		}

		// attachment
		if ( object.has( Fields.LINK ) ) {
			result.setAttachment(
			        Builder.createURI(
			                object.getString( Fields.LINK ) ) );
		} else if ( object.has( Fields.SOURCE ) ) {
			result.setAttachment(
			        Builder.createURI(
			                object.getString( Fields.SOURCE ) ) );
		} else if ( object.has( Fields.ATTACHMENT ) ) {
			JsonObject attachment = object
			        .getJsonObject( Fields.ATTACHMENT );

			if ( attachment.has( Fields.TARGET ) ) {
				JsonObject target = attachment
				        .getJsonObject( Fields.TARGET );
				if ( target.has( Fields.URL ) ) {
					result.addAttachment(
					        Builder.createURI(
					                target.getString( Fields.URL ) ) );
				}
			} else if ( attachment.has( Fields.URL ) ) {
				result.addAttachment(
				        Builder.createURI(
				                attachment
				                        .getString( Fields.URL ) ) );
			}
		}
	}

	public static UserAccount createSiocUserAccount(
	        final FacebookConnector connector,
	        final JsonObject jsonObject ) {
		Preconditions.checkNotNull( connector,
		        "Required parameter connector must be specified." );
		Preconditions.checkNotNull( jsonObject,
		        "Required parameter jsonObject must be specified." );

		Model model = connector.getContext().getModel();
		URI serviceEndpoint = connector.getService()
		        .getServiceEndpoint()
		        .asURI();
		String id = jsonObject.getString( Fields.ID );
		URI uri = createSiocUri( id );

		UserAccount result = new UserAccount( model, uri, true );
		result.setId( id );
		result.setName( jsonObject.getString( Fields.NAME ) );
		result.setAccountName( id );
		result.setAccountServiceHomepage( serviceEndpoint );

		// create a new Person for unknown account.
		Person person = new Person( model, true );
		person.setName( jsonObject.getString( Fields.NAME ) );

		person.addAccount( result );
		result.setAccountOf( person );

		return result;
	}

	public static URI createSiocUri( final String id ) {
		Preconditions.checkNotNull( id,
		        "Required parameter id must be specified." );
		Preconditions.checkArgument( !id.isEmpty(),
		        "Required parameter id may not be empty." );

		return Builder.createURI(
		        UriTemplate.fromTemplate( "https://graph.facebook.com/{id}" )
		                .set( "id", id )
		                .expand() );
	}

	public static boolean hasConnection( JsonObject object, String connection ) {
		if ( object.has( Fields.METADATA ) ) {
			JsonObject metadata = object.getJsonObject( Fields.METADATA );

			if ( metadata.has( Fields.CONNECTIONS ) ) {
				JsonObject connections = metadata.getJsonObject( Fields.CONNECTIONS );

				return connections.has( connection );
			}
		}

		return false;
	}
}
