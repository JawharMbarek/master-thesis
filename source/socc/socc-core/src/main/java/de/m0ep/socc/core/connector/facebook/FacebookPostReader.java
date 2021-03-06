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

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ontoware.rdf2go.model.Syntax;
import org.ontoware.rdf2go.model.node.URI;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.restfb.Connection;
import com.restfb.Parameter;
import com.restfb.exception.FacebookException;
import com.restfb.json.JsonObject;

import de.m0ep.socc.core.connector.DefaultConnectorIOComponent;
import de.m0ep.socc.core.connector.IConnector.IPostReader;
import de.m0ep.socc.core.connector.facebook.FacebookSiocUtils.Connections;
import de.m0ep.socc.core.connector.facebook.FacebookSiocUtils.Fields;
import de.m0ep.socc.core.connector.facebook.FacebookSiocUtils.RequestParameters;
import de.m0ep.socc.core.exceptions.AccessControlException;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.exceptions.NotFoundException;
import de.m0ep.socc.core.utils.RdfUtils;
import de.m0ep.socc.core.utils.SoccUtils;

/**
 * Implementation of an {@link IPostReader} for a {@link FacebookConnector}.
 * 
 * @author Florian Müller
 * 
 */
public class FacebookPostReader extends DefaultConnectorIOComponent<FacebookConnector>
        implements IPostReader<FacebookConnector> {
	private static final Logger LOG = LoggerFactory.getLogger( FacebookPostReader.class );

	private final FacebookClientWrapper defaultClient;

	/**
	 * Constructs a new {@link FacebookPostReader}.
	 * 
	 * @param connector
	 *            {@link FacebookConnector} for that {@link FacebookPostReader}.
	 */
	public FacebookPostReader( final FacebookConnector connector ) {
		super( connector );

		this.defaultClient = connector.getClientManager().getDefaultClient();
	}

	@Override
	public boolean isPost( final URI uri ) {
		if ( Post.hasInstance( getModel(), uri ) ) {
			return true;
		}

		Pattern pattern = Pattern.compile( FacebookSiocUtils.REGEX_FACEBOOK_URI );
		Matcher matcher = pattern.matcher( uri.toString() );

		if ( matcher.find() ) {
			String id = matcher.group( 1 );
			JsonObject object = null;

			try {
				object = defaultClient.getFacebookClient().fetchObject(
				        "/" + id,
				        JsonObject.class,
				        Parameter.with( RequestParameters.METADATA, 1 ) );
			} catch ( Exception e ) {
				Throwables.propagate( e );
			}

			if ( FacebookSiocUtils.hasConnection( object, Connections.COMMENTS ) ) {
				FacebookSiocUtils.createSiocPost( getConnector(), object, null, null );
				return true;
			} else if ( FacebookSiocUtils.hasConnection( object, Connections.FEED ) ) {
				// if the resource has a feed, create a SIOC Forum so we haven't to load it another time.
				FacebookSiocUtils.createSiocForum( getConnector(), object );
				return false;
			}
		}

		return false;
	}

	@Override
	public Post getPost( final URI uri )
	        throws NotFoundException,
	        AuthenticationException,
	        IOException,
	        AccessControlException {
		LOG.info( "Read post from '{}'.", uri );

		if ( Post.hasInstance( getModel(), uri ) ) {
			return Post.getInstance( getModel(), uri );
		}

		Pattern pattern = Pattern.compile( FacebookSiocUtils.REGEX_FACEBOOK_URI );
		Matcher matcher = pattern.matcher( uri.toString() );

		if ( matcher.find() ) {
			String id = matcher.group( 1 );
			JsonObject object = null;

			try {
				object = defaultClient.getFacebookClient().fetchObject( "/" + id,
				        JsonObject.class,
				        Parameter.with( RequestParameters.METADATA, 1 ) );
			} catch ( FacebookException e ) {
				FacebookConnector.handleFacebookException( e );
			} catch ( Exception e ) {
				Throwables.propagate( e );
			}

			if ( null != object && FacebookSiocUtils.hasConnection( object, Connections.COMMENTS ) ) {
				if ( LOG.isDebugEnabled() ) {
					LOG.debug( "Read post '{}' from {}:\n{}",
					        object.get( FacebookSiocUtils.Fields.ID ),
					        uri,
					        object.toString( 2 ) );
				} else {
					LOG.info( "Read post '{}' from {}.",
					        object.get( FacebookSiocUtils.Fields.ID ),
					        uri );
				}

				Post resultPost = FacebookSiocUtils.createSiocPost(
				        getConnector(),
				        object,
				        null,
				        null );

				if ( LOG.isDebugEnabled() ) {
					LOG.debug( "Converted post '{}' to SIOC:\n{}",
					        resultPost.getId(),
					        RdfUtils.resourceToString( resultPost, Syntax.Turtle ) );
				} else {
					LOG.info( "Converted entry '{}' to SIOC {}",
					        resultPost.getId(),
					        resultPost );
				}

				if ( SoccUtils.haveReadAccess(
				        getConnector(),
				        resultPost.getCreator(),
				        resultPost.getContainer() ) ) {
					throw new AccessControlException( "Have no permission to read post '"
					        + uri
					        + "'" );
				}

				return resultPost;
			}
		}

		throw new NotFoundException( "The uri " + uri + " is no post at " + getServiceEndpoint() );
	}

	@Override
	public boolean hasPosts( final URI uri ) {
		Pattern pattern = Pattern.compile( FacebookSiocUtils.REGEX_FACEBOOK_URI );
		Matcher matcher = pattern.matcher( uri.toString() );

		if ( matcher.find() ) {
			String id = matcher.group( 1 );
			JsonObject object = null;

			try {
				object = defaultClient.getFacebookClient().fetchObject( "/" + id,
				        JsonObject.class,
				        Parameter.with( RequestParameters.METADATA, 1 ) );
			} catch ( Exception e ) {
				return false;
			}

			if ( null != object ) {
				if ( FacebookSiocUtils.hasConnection( object, Connections.FEED ) ) {
					FacebookSiocUtils.createSiocForum( getConnector(), object );
					return true;
				} else if ( FacebookSiocUtils.hasConnection( object, Connections.COMMENTS ) ) {
					FacebookSiocUtils.createSiocPost( getConnector(), object, null, null );
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public List<Post> pollPosts( final URI sourceUri, final Date since, final int limit )
	        throws AuthenticationException,
	        IOException {
		LOG.info( "Poll posts from sourceUri='{}' since='{}' limit='{}'",
		        sourceUri, since, limit );

		if ( Forum.hasInstance( getModel(), sourceUri ) ) {
			return pollPostsAtContainer( Forum.getInstance( getModel(), sourceUri ), since, limit );
		} else if ( Post.hasInstance( getModel(), sourceUri ) ) {
			return pollRepliesAtPost( Post.getInstance( getModel(), sourceUri ), since, limit );
		}

		Pattern pattern = Pattern.compile( FacebookSiocUtils.REGEX_FACEBOOK_URI );
		Matcher matcher = pattern.matcher( sourceUri.toString() );

		if ( matcher.find() ) {
			String id = matcher.group( 1 );
			JsonObject object = null;

			try {
				object = defaultClient.getFacebookClient().fetchObject(
				        "/" + id,
				        JsonObject.class,
				        Parameter.with( RequestParameters.METADATA, 1 ) );
			} catch ( FacebookException e ) {
				FacebookConnector.handleFacebookException( e );
			} catch ( Exception e ) {
				Throwables.propagate( e );
			}

			if ( null != object ) {
				if ( FacebookSiocUtils.hasConnection( object, Connections.FEED ) ) {
					Container container = FacebookSiocUtils.createSiocForum(
					        getConnector(),
					        object );
					return pollPostsAtContainer( container, since, limit );
				} else if ( FacebookSiocUtils.hasConnection( object, Connections.COMMENTS ) ) {
					Post post = FacebookSiocUtils.createSiocPost(
					        getConnector(),
					        object,
					        null,
					        null );
					return pollRepliesAtPost( post, since, limit );
				}
			}
		}

		throw new IOException(
		        "Can't read post from uri "
		                + sourceUri
		                + " at "
		                + getServiceEndpoint() );
	}

	/**
	 * Polls post from a {@link Container}.
	 * 
	 * @param sourceContainer
	 *            Source {@link Container} to poll from.
	 * @param since
	 *            Data since a post is new
	 * @param limit
	 *            Limit result size to this number.
	 * @return A {@link List} of {@link Post}s
	 * 
	 * @throws AuthenticationException
	 *             Thrown if there is a problem with authentication.
	 * @throws IOException
	 *             Thrown if there is a problem in communication.
	 */
	private List<Post> pollPostsAtContainer(
	        final Container sourceContainer,
	        final Date since,
	        final long limit )
	        throws AuthenticationException,
	        IOException {
		Preconditions.checkNotNull( sourceContainer,
		        "Required parameter sourceContainer must be specified." );

		LOG.info( "Polling posts from '{}' since='{}' limit='{}'", sourceContainer,
		        since.getTime(), limit );

		if ( 0 == limit ) {
			return Lists.newArrayList();
		}

		List<Parameter> paramList = Lists.newArrayList();

		if ( 0 < limit ) {
			paramList.add( Parameter.with(
			        RequestParameters.LIMIT,
			        Math.min( 25, limit ) ) );
		}

		Connection<JsonObject> postFeed = null;
		try {
			postFeed = defaultClient.getFacebookClient().fetchConnection(
			        "/" + sourceContainer.getId() + "/" + Connections.FEED,
			        JsonObject.class,
			        paramList.toArray( new Parameter[0] ) );
		} catch ( FacebookException e ) {
			FacebookConnector.handleFacebookException( e );
		}

		List<Post> result = Lists.newArrayList();
		if ( null != postFeed ) {
			for ( List<JsonObject> objectList : postFeed ) {
				for ( JsonObject object : objectList ) {
					if ( LOG.isDebugEnabled() ) {
						LOG.debug( "Read post id='{}' from {}:\n{}",
						        object.get( FacebookSiocUtils.Fields.ID ),
						        sourceContainer,
						        object.toString( 2 ) );
					} else {
						LOG.info( "Read post id='{}' from {}.",
						        object.get( FacebookSiocUtils.Fields.ID ),
						        sourceContainer );
					}

					if ( 0 > limit || limit > result.size() ) {
						Date createdDate = null;
						if ( object.has( Fields.CREATED_TIME ) ) {
							createdDate = com.restfb.util.DateUtils
							        .toDateFromLongFormat(
							        object.getString( Fields.CREATED_TIME ) );
						}

						if ( null == since || null == createdDate || createdDate.after( since ) ) {
							Post post = FacebookSiocUtils.createSiocPost(
							        getConnector(),
							        object,
							        sourceContainer,
							        null );

							if ( LOG.isDebugEnabled() ) {
								LOG.debug( "Converted post '{}' to SIOC:\n{}",
								        post.getId(),
								        RdfUtils.resourceToString( post, Syntax.Turtle ) );
							} else {
								LOG.info( "Converted post '{}' to SIOC {}",
								        post.getId(),
								        post );
							}

							if ( SoccUtils.haveReadAccess(
							        getConnector(),
							        post.getCreator(),
							        post.getContainer() ) ) {
								result.add( post );
								LOG.info( "Added {} to polling result. result size: {}",
								        post,
								        result.size() );
							} else {
								LOG.info(
								        "Have no permission to read posts for this UserAccount='{}'",
								        post.getCreator() );
								SoccUtils.anonymisePost( post );
							}

							// poll all comments
							result.addAll( pollRepliesAtPost(
							        post,
							        since,
							        Math.max( -1, limit - result.size() ) ) );
						} else {
							LOG.debug( "Skip post id='{}', it's to old.", object
							        .get( FacebookSiocUtils.Fields.ID ) );
						}
					} else {
						return result;
					}
				}
			}
		}

		return result;
	}

	/**
	 * Polls post from a {@link Post}.
	 * 
	 * @param sourcePost
	 *            Source {@link Post} to poll from.
	 * @param since
	 *            Data since a post is new
	 * @param limit
	 *            Limit result size to this number.
	 * @return A {@link List} of {@link Post}s
	 * 
	 * @throws AuthenticationException
	 *             Thrown if there is a problem with authentication.
	 * @throws IOException
	 *             Thrown if there is a problem in communication.
	 */
	private List<Post> pollRepliesAtPost( final Post sourcePost, final Date since, final long limit )
	        throws AuthenticationException, IOException {
		Preconditions.checkNotNull( sourcePost,
		        "Required parameter sourcePost must be specified." );

		LOG.info( "Polling replies from '{}' since='{}' limit='{}'", sourcePost, since, limit );

		if ( 0 == limit ) {
			return Lists.newArrayList();
		}

		List<Parameter> paramList = Lists.newArrayList();

		if ( 0 < limit ) {
			paramList.add( Parameter.with(
			        RequestParameters.LIMIT,
			        Math.min( 25, limit ) ) );
		}

		paramList.add( Parameter.with(
		        RequestParameters.FIELDS,
		        FacebookSiocUtils.FIELDS_COMMENT ) );

		Connection<JsonObject> commentFeed = null;
		try {
			commentFeed = defaultClient.getFacebookClient().fetchConnection(
			        "/" + sourcePost.getId() + "/" + Connections.COMMENTS,
			        JsonObject.class,
			        paramList.toArray( new Parameter[0] ) );
		} catch ( FacebookException e ) {
			FacebookConnector.handleFacebookException( e );
		}

		List<Post> resultList = Lists.newArrayList();
		if ( null != commentFeed ) {
			for ( List<JsonObject> objectList : commentFeed ) {
				for ( JsonObject object : objectList ) {
					if ( LOG.isDebugEnabled() ) {
						LOG.debug( "Read comment '{}' from {}:\n{}",
						        object.get( FacebookSiocUtils.Fields.ID ),
						        sourcePost,
						        object.toString( 2 ) );
					} else {
						LOG.info( "Read comment '{}' from {}.",
						        object.get( FacebookSiocUtils.Fields.ID ),
						        sourcePost );
					}

					if ( 0 > limit || limit > resultList.size() ) {
						Date createdDate = null;
						if ( object.has( Fields.CREATED_TIME ) ) {
							createdDate = com.restfb.util.DateUtils
							        .toDateFromLongFormat(
							        object.getString( Fields.CREATED_TIME ) );
						}

						if ( null == since || null == createdDate || createdDate.after( since ) ) {
							Post post = FacebookSiocUtils.createSiocPost(
							        getConnector(),
							        object,
							        sourcePost.getContainer(),
							        sourcePost );

							if ( LOG.isDebugEnabled() ) {
								LOG.debug( "Converted post '{}' to SIOC:\n{}",
								        post.getId(),
								        RdfUtils.resourceToString( post, Syntax.Turtle ) );
							} else {
								LOG.info( "Converted entry '{}' to SIOC {}",
								        post.getId(),
								        post );
							}

							if ( SoccUtils.haveReadAccess(
							        getConnector(),
							        post.getCreator(),
							        post.getContainer() ) ) {
								resultList.add( post );
								LOG.info( "Added {} to polling result. result size: {}",
								        post,
								        resultList.size() );
							} else {
								LOG.info(
								        "Have no permission to read posts from this user '{}'",
								        post.getCreator() );
								SoccUtils.anonymisePost( post );
							}

						} else {
							LOG.debug( "Skip post Facebookpost, it's to old." );
						}
					} else {
						return resultList;
					}
				}
			}
		} else {
			LOG.warn( "CommentFeed was null" );
		}

		return resultList;
	}
}
