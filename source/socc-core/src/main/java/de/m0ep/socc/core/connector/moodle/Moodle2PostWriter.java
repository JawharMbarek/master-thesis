/*
 * The MIT License (MIT) Copyright © 2013 "Florian Mueller"
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

package de.m0ep.socc.core.connector.moodle;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.Syntax;
import org.ontoware.rdf2go.model.node.Resource;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.util.RDFTool;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.Thing;
import org.rdfs.sioc.UserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.repackaged.com.google.common.base.Strings;
import com.google.api.client.repackaged.com.google.common.base.Throwables;

import de.m0ep.moodlews.soap.ForumPostDatum;
import de.m0ep.moodlews.soap.ForumPostRecord;
import de.m0ep.socc.core.connector.DefaultConnectorIOComponent;
import de.m0ep.socc.core.connector.IConnector.IPostWriter;
import de.m0ep.socc.core.exceptions.AccessControlException;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.exceptions.NotFoundException;
import de.m0ep.socc.core.utils.PostWriterUtils;
import de.m0ep.socc.core.utils.RdfUtils;
import de.m0ep.socc.core.utils.SiocUtils;
import de.m0ep.socc.core.utils.SoccUtils;

public class Moodle2PostWriter extends
        DefaultConnectorIOComponent<Moodle2Connector> implements
        IPostWriter<Moodle2Connector> {

	private static final Logger LOG = LoggerFactory
	        .getLogger( Moodle2PostWriter.class );
	private final Map<Integer, Post> firstPostIdMap = new HashMap<Integer, Post>();

	public Moodle2PostWriter( Moodle2Connector connector ) {
		super( connector );
	}

	@Override
	public void writePosts( final URI targetUri, final String rdfString, final Syntax syntax )
	        throws NotFoundException,
	        AuthenticationException,
	        IOException,
	        AccessControlException {
		Model tmpModel = RDFTool.stringToModel( rdfString, syntax );

		boolean isForumDiscussionUri = Moodle2SiocUtils.isForumDiscussionUri(
		        targetUri,
		        getServiceEndpoint() );

		boolean isForumPostUri = Moodle2SiocUtils.isForumPostUri(
		        targetUri,
		        getServiceEndpoint() );

		Thing targetResource = null;
		if ( isForumDiscussionUri ) {
			targetResource = getConnector()
			        .getStructureReader()
			        .getContainer( targetUri );
		} else if ( isForumPostUri ) {
			targetResource = getConnector()
			        .getPostReader()
			        .getPost( targetUri );
		} else {
			throw new NotFoundException(
			        "No suitable Moodle target found at uri "
			                + targetUri
			                + " to write posts to it." );
		}

		ClosableIterator<Resource> postIter = Post.getAllInstances( tmpModel );
		try {
			while ( postIter.hasNext() ) {
				Resource resource = postIter.next();
				Post post = Post.getInstance( tmpModel, resource );

				if ( LOG.isDebugEnabled() ) {
					LOG.debug( "Try to write Post to {}:\n{}",
					        getServiceEndpoint(),
					        RdfUtils.resourceToString(
					                post,
					                Syntax.Turtle ) );
				}

				// skip all posts that are already forwarded from this site
				if ( SoccUtils.hasContentWatermark(
				        getConnector().getStructureReader().getSite(),
				        post.getContent() ) ) {
					continue;
				}

				if ( post.hasReplyOf() ) {
					Post sibling = SoccUtils.findSibling(
					        getModel(),
					        SiocUtils.asPost( post.getReplyOf() ),
					        getConnector().getStructureReader().getSite() );

					if ( null != sibling ) {
						LOG.debug( "Write {} as reply to {}.", post, sibling );
						writeReplyToPost( sibling, post );
						return;
					} else {
						LOG.debug( "No sibling found for {}", post.getReplyOf() );
					}
				}

				if ( isForumDiscussionUri ) {
					writePostToContainer( (Container) targetResource, post );
				} else if ( isForumPostUri ) {
					writeReplyToPost( (Post) targetResource, post );
				}
			}
		} finally {
			postIter.close();
			tmpModel.close();
		}
	}

	private void writePostToContainer( final Container targetContainer, final Post post )
	        throws AuthenticationException,
	        IOException {
		final int discussionId;
		try {
			discussionId = Integer.parseInt( targetContainer.getId() );
		} catch ( NumberFormatException e ) {
			throw new IllegalArgumentException(
			        "The id of the container is invalid: was "
			                + targetContainer.getId() );
		}

		UserAccount creatorAccount = PostWriterUtils.getCreatorUserAccount(
		        getConnector(),
		        post );

		Moodle2ClientWrapper client = PostWriterUtils.getClientOfCreator(
		        getConnector(),
		        creatorAccount );

		String content = Strings.nullToEmpty( post.getContent() );

		if ( null == client ) {
			LOG.debug( "Using default client" );
			client = getConnector().getClientManager().getDefaultClient();
			content = SoccUtils.formatUnknownMessage(
			        getConnector(),
			        post );
		} else if ( !SoccUtils.haveWriteAccess( // check for write access 
		        getConnector(),
		        creatorAccount,
		        targetContainer ) ) {
			LOG.info( "Skip writing post {}, have no writing permission.", post );
			return;
		}

		// Add Attachments to message content
		content = SoccUtils.addAttachmentsToContent( post, content, "<br>" );

		if ( !SoccUtils.hasAnyContentWatermark( content ) ) {
			// add watermark for 'already forwarded' check
			content = SoccUtils.addContentWatermark( post.getIsPartOf(), content, "<br>" );
		}

		final Moodle2ClientWrapper callingClient = client;
		Post firstPost = null;
		if ( firstPostIdMap.containsKey( discussionId ) ) {
			firstPost = firstPostIdMap.get( discussionId );
		} else {
			// Need the id of the first entry to write the post as reply to it.
			ForumPostRecord[] firstPostRecordArray = callingClient
			        .callMethod( new Callable<ForumPostRecord[]>() {
				        @Override
				        public ForumPostRecord[] call() throws Exception {
					        return callingClient.getBindingStub()
					                .get_forum_posts(
					                        callingClient.getAuthClient(),
					                        callingClient.getSessionKey(),
					                        discussionId,
					                        1 );
				        }
			        } );
			if ( null != firstPostRecordArray && 0 < firstPostRecordArray.length ) {
				firstPost = Moodle2SiocUtils.createSiocPost(
				        getConnector(),
				        firstPostRecordArray[0],
				        SiocUtils.asThread( targetContainer ),
				        null );

				LOG.debug( "Loaded first post {} of discussion {}", firstPost
				        .getId(), discussionId );
				firstPostIdMap.put( discussionId, firstPost );
			}
		}

		if ( null != firstPost ) {
			final int firstPostId;
			try {
				firstPostId = Integer.parseInt( firstPost.getId() );
			} catch ( NumberFormatException e ) {
				throw Throwables.propagate( e ); // shouldn't happened
			}

			// create Moodle post data
			final ForumPostDatum postDatum = new ForumPostDatum( client
			        .getBindingStub()
			        .getNAMESPACE() );

			postDatum.setMessage( content );
			postDatum.setSubject( Strings.nullToEmpty( post.getTitle() ) );

			// add post to Moodle
			ForumPostRecord[] resultPostRecords = callingClient
			        .callMethod( new Callable<ForumPostRecord[]>() {
				        @Override
				        public ForumPostRecord[] call() throws Exception {
					        return callingClient.getBindingStub()
					                .forum_add_reply(
					                        callingClient.getAuthClient(),
					                        callingClient.getSessionKey(),
					                        firstPostId,
					                        postDatum );
				        }
			        } );

			if ( null != resultPostRecords && 0 < resultPostRecords.length ) {
				ForumPostRecord parentPostRecord = resultPostRecords[0];
				if ( null != parentPostRecord ) {
					ForumPostRecord[] childrenRecords = parentPostRecord.getChildren();
					if ( null != childrenRecords && 0 < childrenRecords.length ) {
						// get last children of the parent post, it's the previously written reply
						int len = childrenRecords.length;
						ForumPostRecord resultPostRecord = childrenRecords[len - 1];

						convertWrittenEntryToPost(
						        post,
						        targetContainer,
						        firstPost,
						        resultPostRecord );
						return;
					}
				}
			} else {
				LOG.warn( "Failed to write post(s) to uri " + targetContainer );
			}
		}
	}

	private void writeReplyToPost( final Post targetPost, final Post post )
	        throws AuthenticationException,
	        IOException {
		final int postId;
		try {
			postId = Integer.parseInt( targetPost.getId() );
		} catch ( NumberFormatException e ) {
			throw new IllegalArgumentException(
			        "The id of the parentPost is invalid: was "
			                + targetPost.getId() );
		}

		UserAccount creatorAccount = PostWriterUtils.getCreatorUserAccount(
		        getConnector(),
		        post );

		Moodle2ClientWrapper client = PostWriterUtils.getClientOfCreator(
		        getConnector(),
		        creatorAccount );

		String content = Strings.nullToEmpty( post.getContent() );

		if ( null == client ) {
			LOG.debug( "Using default client" );
			client = getConnector().getClientManager().getDefaultClient();
			content = SoccUtils.formatUnknownMessage(
			        getConnector(),
			        post );
		} else if ( !SoccUtils.haveWriteAccess( // check for write access 
		        getConnector(),
		        creatorAccount,
		        targetPost.getContainer() ) ) {
			LOG.info( "Skip writing post {}, have no writing permission.", post );
			return;
		}

		// Add Attachments to message content
		content = SoccUtils.addAttachmentsToContent( post, content, "<br>" );

		if ( !SoccUtils.hasAnyContentWatermark( content ) ) {
			// add watermark for 'already forwarded' check
			content = SoccUtils.addContentWatermark( post.getIsPartOf(), content );
		}

		final Moodle2ClientWrapper finalClient = client;
		final ForumPostDatum replyDatum = new ForumPostDatum( client
		        .getBindingStub().getNAMESPACE() );
		replyDatum.setMessage( content );
		replyDatum.setSubject( Strings.nullToEmpty( post.getTitle() ) );

		// returns an array with all posts in the parent thread
		ForumPostRecord[] resultPostRecords = client
		        .callMethod( new Callable<ForumPostRecord[]>() {
			        @Override
			        public ForumPostRecord[] call() throws Exception {
				        return finalClient.getBindingStub().forum_add_reply(
				                finalClient.getAuthClient(),
				                finalClient.getSessionKey(),
				                postId,
				                replyDatum );
			        }
		        } );

		if ( null != resultPostRecords && 0 < resultPostRecords.length ) {
			ForumPostRecord parentPostRecord = findForumPostRecord( resultPostRecords, postId );
			if ( null != parentPostRecord ) {
				ForumPostRecord[] childrenRecords = parentPostRecord.getChildren();
				if ( null != childrenRecords && 0 < childrenRecords.length ) {
					// get last children of the parent post, it's the previously written reply
					int len = childrenRecords.length;
					ForumPostRecord resultPostRecord = childrenRecords[len - 1];

					convertWrittenEntryToPost(
					        post,
					        targetPost.getContainer(),
					        targetPost,
					        resultPostRecord );
					return;
				}
			}
		} else {
			LOG.warn( "Failed to write post(s) to uri " + targetPost );
		}
	}

	/**
	 * Searches in an array of ForumPosts for an entry with an specific id.
	 * 
	 * @param forumPostRecords
	 * @param id
	 * @return
	 */
	private ForumPostRecord findForumPostRecord(
	        final ForumPostRecord[] forumPostRecords,
	        final int id ) {
		if ( null != forumPostRecords ) {
			for ( ForumPostRecord postRecord : forumPostRecords ) {
				if ( id == postRecord.getId() ) {
					return postRecord;
				}

				// iterate through all replies
				ForumPostRecord result = findForumPostRecord(
				        postRecord.getChildren(),
				        id );

				if ( null != result ) {
					return result;
				}
			}
		}
		return null;
	}

	private void convertWrittenEntryToPost( final Post originalPost,
	        final Container targetContainer, Post parentPost, ForumPostRecord postRecord )
	        throws IOException, AuthenticationException {
		if ( LOG.isDebugEnabled() ) {
			LOG.debug( "Received written entry:\n{}",
			        getServiceEndpoint(),
			        postRecord );
		} else {
			LOG.info( "Received written entry '{}'",
			        getServiceEndpoint(),
			        postRecord.getId() );
		}

		Post resultPost = Moodle2SiocUtils.createSiocPost(
		        getConnector(),
		        postRecord,
		        SiocUtils.asThread( targetContainer ),
		        parentPost );

		resultPost.addSibling( originalPost );

		if ( LOG.isDebugEnabled() ) {
			LOG.debug( "Writing a post to {} was successful:\n{}",
			        getServiceEndpoint(),
			        RdfUtils.resourceToString( resultPost, Syntax.Turtle ) );
		} else {
			LOG.info( "Writing a post to {} was successful: '{}'",
			        getServiceEndpoint(),
			        resultPost );
		}
	}
}
