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

package de.m0ep.socc.core.utils;

import java.text.ParseException;
import java.util.Map;

import org.rdfs.sioc.Post;
import org.rdfs.sioc.UserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.util.Maps;
import com.google.common.base.Preconditions;
import com.java2s.code.java.i18n.MapFormat;

import de.m0ep.socc.core.connector.IConnector;
import de.m0ep.socc.core.connector.IConnector.IPostWriter;

/**
 * Utility methods so the PostWriters are not a huge mess ;)
 * 
 * @author "Florian Mueller"
 */
public final class PostWriterUtils {
	private static final Logger LOG = LoggerFactory
	        .getLogger( PostWriterUtils.class );

	private PostWriterUtils() {
	}

	/**
	 * Formats the content of a post with the
	 * <code>unknownMessageTemplate</code> of the <code>connector</code>. If the
	 * creator of the post has no sioc:name property set,
	 * {@link IConnector#MESSAGE_TEMPLATE_UNKNOWN_AUTHOR_NAME} will be used as
	 * <code>{authorName}</code>.
	 * 
	 * 
	 * @param connector
	 *            Used Connector
	 * @param post
	 *            The Post from where the content should be formated.
	 * @return The formated content as {@link String}.
	 * @throws NullPointerException
	 *             Thrown if <code>connector</code> or <code>post</code> are
	 *             <code>null</code>.
	 */
	public static String formatUnknownMessage(
	        final IConnector connector,
	        final Post post ) {
		Preconditions.checkNotNull( connector,
		        "Required parameter connector must be specified." );
		Preconditions.checkNotNull( post,
		        "Required parameter post must be specified." );
		Preconditions.checkArgument( post.hasContent(),
		        "The parameter post has no content." );

		Map<String, Object> args = Maps.newHashMap();
		UserAccount creatorAccount = post.hasCreator() ? post.getCreator() : null;
		String authorName = ( null != creatorAccount && creatorAccount.hasName() )
		        ? creatorAccount.getName()
		        : "an unknown user";

		args.put( IPostWriter.MESSAGE_TEMPLATE_VAR_MESSAGE, post.getContent() );
		args.put( IPostWriter.MESSAGE_TEMPLATE_VAR_CONNECTOR_ID, connector.getId() );
		args.put( IPostWriter.MESSAGE_TEMPLATE_VAR_AUTHOR_NAME, authorName );
		args.put( IPostWriter.MESSAGE_TEMPLATE_VAR_SOURCE_URI, post.getResource() );

		if ( connector.getService().hasName() ) {
			args.put(
			        IPostWriter.MESSAGE_TEMPLATE_VAR_SERVICE_NAME,
			        connector.getService().getName() );
		}

		if ( post.hasCreated() ) {
			try {
				args.put(
				        IPostWriter.MESSAGE_TEMPLATE_VAR_CREATION_DATE,
				        DateUtils.parseISO8601( post.getCreated() ) );
			} catch ( ParseException e ) {
				LOG.warn(
				        "Failed to parse date {} from post {}",
				        post.getCreated(),
				        post.getResource() );
			}
		}

		return MapFormat.format( connector.getUnknownMessageTemplate(), args );
	}
}
