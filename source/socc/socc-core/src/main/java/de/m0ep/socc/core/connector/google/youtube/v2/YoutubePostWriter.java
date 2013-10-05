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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.Syntax;
import org.ontoware.rdf2go.model.node.Resource;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.util.RDFTool;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.UserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.repackaged.com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.gdata.data.Link;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.youtube.CommentEntry;
import com.google.gdata.data.youtube.YouTubeNamespace;
import com.google.gdata.util.ServiceException;

import de.m0ep.socc.core.connector.DefaultConnectorIOComponent;
import de.m0ep.socc.core.connector.IConnector.IPostWriter;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.exceptions.NotFoundException;
import de.m0ep.socc.core.utils.PostWriterUtils;
import de.m0ep.socc.core.utils.SoccUtils;

public class YoutubePostWriter extends
        DefaultConnectorIOComponent<YoutubeConnector> implements
        IPostWriter<YoutubeConnector> {
	private static final Logger LOG = LoggerFactory
	        .getLogger( YoutubePostWriter.class );

	public YoutubePostWriter( YoutubeConnector connector ) {
		super( connector );
	}

	@Override
	public void writePosts( URI targetUri, String rdfString, Syntax syntax )
	        throws NotFoundException,
	        AuthenticationException,
	        IOException {
		boolean isVideo = YoutubeSiocUtils.isVideoUri( targetUri );
		boolean isComment = YoutubeSiocUtils.isCommentUri( targetUri );

		if ( isVideo || isComment ) {
			Model tmpModel = RDFTool.stringToModel( rdfString, syntax );
			ClosableIterator<Resource> postIter = Post
			        .getAllInstances( tmpModel );
			try {
				while ( postIter.hasNext() ) {
					Resource resource = postIter.next();
					Post post = Post.getInstance( tmpModel, resource );

					// skip all posts that are already forwarded from this site
					if ( SoccUtils.hasContentWatermark(
					        getConnector().getStructureReader().getSite(),
					        post.getContent() ) ) {
						continue;
					}

					UserAccount creatorAccount = PostWriterUtils.getCreatorUserAccount(
					        getConnector(),
					        post );

					YoutubeClientWrapper client = PostWriterUtils.getClientOfCreator(
					        getConnector(),
					        creatorAccount );

					String content = Strings.nullToEmpty( post.getContent() );

					if ( null == client ) {
						LOG.debug( "Using default client" );
						client = getConnector().getClientManager()
						        .getDefaultClient();
						content = SoccUtils.formatUnknownMessage(
						        getConnector(),
						        post );
					}

					// Add Attachments to message content
					content = SoccUtils.addAttachmentsToContent( post, content, "\n" );

					if ( !SoccUtils.hasAnyContentWatermark( content ) ) {
						// add watermark for 'already forwarded' check
						content = SoccUtils.addContentWatermark( post
						        .getIsPartOf(), content );
					}

					// Create CommentEntry
					CommentEntry entry = new CommentEntry();
					entry.setContent( new PlainTextConstruct( content ) );

					// if it's a comment, add reply_of link to the parent
					if ( isComment ) {
						entry.getLinks().add(
						        new Link( YouTubeNamespace.IN_REPLY_TO,
						                "application/atom+xml",
						                targetUri.toString() ) );
					}

					// find video ID by regular expression
					Pattern pattern = Pattern.compile( YoutubeSiocUtils.REGEX_VIDEO_URI );
					Matcher matcher = pattern.matcher( targetUri.toString() );

					if ( matcher.find() ) {
						CommentEntry result = null;
						try {
							result = client.getService().insert(
							        new URL(
							                YoutubeSiocUtils.createVideoUri(
							                        matcher.group( 1 ) )
							                        .toString()
							                        + "/comments" ),
							        entry );
						} catch ( MalformedURLException e ) {
							// shouldn't happened
							Throwables.propagate( e );
						} catch ( ServiceException e ) {
							YoutubeConnector.handleYoutubeExceptions( e );
						}

						if ( null != result ) {
							Post parentPost = getConnector().getPostReader()
							        .getPost( targetUri );
							Post resultPost = YoutubeSiocUtils.createSiocPost(
							        getConnector(),
							        result,
							        parentPost );

							resultPost.setSibling( post );

							return;
						} else {
							LOG.warn( "Failed to write post(s) to uri "
							        + targetUri );
						}
					}
				}
			} finally {
				postIter.close();
				tmpModel.close();
			}
		}

		throw new IOException(
		        "Failed to write post to target uri "
		                + targetUri
		                + ", it's neither a conainer nor a post od comment" );
	}
}
