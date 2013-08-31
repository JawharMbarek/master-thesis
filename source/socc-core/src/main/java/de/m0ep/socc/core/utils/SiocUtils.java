package de.m0ep.socc.core.utils;

import java.text.ParseException;
import java.util.Date;

import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.QueryResultTable;
import org.ontoware.rdf2go.model.QueryRow;
import org.ontoware.rdf2go.model.node.Node;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.util.SparqlUtil;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Item;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.SIOCVocabulary;
import org.rdfs.sioc.Site;
import org.rdfs.sioc.Thread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import de.m0ep.socc.core.exceptions.NotFoundException;

public final class SiocUtils {
	private static final Logger LOG = LoggerFactory.getLogger( SiocUtils.class );

	private SiocUtils() {
	}

	/**
	 * Updates the lastItemDate property of the given <code>container</code> if
	 * the <code>createdDate</code> is after the existing lastItemDate. Updates
	 * also the lastItemDates of the containers parents.
	 * 
	 * @param container
	 *            {@link Container} to update the lastItemDate property.
	 * @param createdDate
	 *            The {@link Date} that should be inserted.
	 * @throws NullPointerException
	 *             Thrown if container or createdDate are <code>null</code>.
	 */
	public static void updateLastItemDate( Container container, Date createdDate ) {
		Preconditions.checkNotNull( container,
		        "Required parameter container must be specified." );
		Preconditions.checkNotNull( createdDate,
		        "Required parameter createdDate must be specified." );

		if ( container.hasLastItemDate() ) {
			String rawLastItemDate = container.getLastItemDate();

			Date lastItemDate;
			try {
				lastItemDate = DateUtils.parseISO8601( rawLastItemDate );
			} catch ( ParseException e ) {
				throw new IllegalArgumentException(
				        "The parameter container has an invalid lastLastDate value." );
			}

			if ( null != lastItemDate && createdDate.after( lastItemDate ) ) {
				container.setLastItemDate( DateUtils.formatISO8601( createdDate ) );
			}

		} else {
			container.setLastItemDate( DateUtils.formatISO8601( createdDate ) );
		}

		if ( container.hasParent() ) {
			updateLastItemDate( container.getParent(), createdDate );
		}
	}

	/**
	 * Returns the lastItemDate property of the given <code>container</code> as
	 * a {@link Date} object.
	 * 
	 * @param container
	 * @return LastItemDate property as {@link Date} object or <code>null</code>
	 *         if there is no such property.
	 * @throws NullPointerException
	 *             Thrown if <code>container</code> is <code>null</code>.
	 */
	public static Date getLastItemDate( final Container container ) {
		Preconditions.checkNotNull( container,
		        "Required parameter container must be specified." );

		if ( container.hasLastItemDate() ) {
			String lastItemDateString = container.getLastItemDate();

			try {
				return DateUtils.parseISO8601( lastItemDateString );
			} catch ( ParseException e ) {
				LOG.debug( "Failed to parse date: {}", lastItemDateString );
			}
		}

		return null;
	}

	/**
	 * Updates the lastReplyDate property of the given <code>item</code> if the
	 * <code>createdDate</code> is after the existing lastReplyDate.
	 * 
	 * @param item
	 *            {@link Item} to update the lastReplyDate property.
	 * @param createdDate
	 *            The {@link Date} that should be inserted.
	 * @throws NullPointerException
	 *             thrown if <code>item</code> or <code>createdDate</code> are
	 *             <code>null</code>.
	 */
	public static void updateLastReplyDate( Item item, Date createdDate ) {
		Preconditions.checkNotNull( item,
		        "Required parameter post must be specified." );
		Preconditions.checkNotNull( createdDate,
		        "Required parameter createdDate must be specified." );

		if ( item.hasLastReplyDate() ) {
			String lastReplyDateString = item.getLastReplyDate();

			Date lastReplyDate;
			try {
				lastReplyDate = DateUtils.parseISO8601( lastReplyDateString );
			} catch ( ParseException e ) {
				throw new IllegalArgumentException(
				        "Failed to parse date string: " + lastReplyDateString );
			}

			if ( null != lastReplyDate && createdDate.after( lastReplyDate ) ) {
				item.setLastReplyDate( DateUtils.formatISO8601( createdDate ) );
			}

		} else {
			item.setLastReplyDate( DateUtils.formatISO8601( createdDate ) );
		}
	}

	/**
	 * Returns the lastReplyDate property of the given <code>item</code> as a
	 * {@link Date} object.
	 * 
	 * @param item
	 * @return LastReplyDate property as {@link Date} object or
	 *         <code>null</code> if there is no such property.
	 * @throws NullPointerException
	 *             Thrown if <code>item</code> is <code>null</code>.
	 */
	public static Date getLastReplyDate( final Item item ) {
		Preconditions.checkNotNull( item,
		        "Required parameter item must be specified." );

		if ( item.hasLastReplyDate() ) {
			String lastItemDateString = item.getLastReplyDate();

			try {
				return DateUtils.parseISO8601( lastItemDateString );
			} catch ( ParseException e ) {
				LOG.debug( "Failed to parse date: {}", lastItemDateString );
			}
		}

		return null;
	}

	/**
	 * Increments the numItems property of the given <code>container</code> or
	 * set it to 1 if this property is not set.
	 * 
	 * @param container
	 * @throws NullPointerException
	 *             Thrown if <code>container</code> is <code>null</code>.
	 */
	public static void incNumItems( final Container container ) {
		Preconditions.checkNotNull( container,
		        "Required parameter container must be specified." );

		if ( container.hasNumItems() ) {
			container.setNumItems( container.getNumItems() + 1 );

			if ( container.hasParent() ) {
				incNumItems( container.getParent() );
			}
		} else {
			container.setNumItems( 1 );
		}
	}

	/**
	 * Increments the numReplies property of the given <code>item</code> or set
	 * it to 1 if this property is not set.
	 * 
	 * @param container
	 * @throws NullPointerException
	 *             Thrown if <code>item</code> is <code>null</code>.
	 */
	public static void incNumReplies( final Item item ) {
		Preconditions.checkNotNull( item,
		        "Required parameter item must be specified." );

		if ( item.hasNumReplies() ) {
			item.setNumReplies( item.getNumReplies() + 1 );
		} else {
			item.setNumReplies( 1 );
		}
	}

	/**
	 * Increments the numThreads property of the given <code>forum</code> or set
	 * it to 1 if this property is not set.
	 * 
	 * @param container
	 * @throws NullPointerException
	 *             Thrown if <code>forum</code> is <code>null</code>.
	 */
	public static void incNumThreads( final Forum forum ) {
		Preconditions.checkNotNull( forum,
		        "Required parameter forum must be specified." );

		if ( forum.hasNumThreads() ) {
			forum.setNumThreads( forum.getNumThreads() + 1 );
		} else {
			forum.setNumThreads( 1 );
		}
	}

	/**
	 * Returns if the given <code>container</code> belongs to the {@link Site}
	 * of <code>siteUri</code>.
	 * 
	 * @param container
	 * @param siteUri
	 * @return Returns true if <code>container</code> is a {@link Container} of
	 *         the <code>siteUri</code>, false otherwise.
	 * @throws NullPointerException
	 *             Thrown if <code>container</code> or <code>siteUri</code> are
	 *             <code>null</code>.
	 */
	public static boolean isContainerOfSite( final Container container,
	        final URI siteUri ) {
		Preconditions.checkNotNull( siteUri,
		        "Required parameter siteUri must be specified." );
		Preconditions.checkNotNull( container,
		        "Required parameter container must be specified." );

		// if its a forum, it has maybe a host
		if ( RdfUtils.isType( container.getModel(), container.getResource(),
		        SIOCVocabulary.Forum ) ) {
			Forum forum = Forum.getInstance( container.getModel(), container
			        .getResource() );
			return forum.hasHost( siteUri );
		}

		// walk through parent
		if ( container.hasParent() ) {
			return isContainerOfSite( container.getParent(), siteUri );
		}

		return false;
	}

	/**
	 * Converts an arbitrary {@link Container} into a {@link Forum} Interface.
	 * 
	 * @param container
	 * @return The <code>container</code> converted in a {@link Forum}
	 *         interface.
	 * @throws NullPointerException
	 *             Thrown if <code>container</code> is <code>null</code>.
	 */
	public static Forum asForum( final Container container ) {
		Preconditions.checkNotNull( container,
		        "Required parameter container must be specified." );

		return Forum.getInstance( container.getModel(), container.getResource() );
	}

	/**
	 * Converts an arbitrary {@link Container} into a {@link Thread} Interface.
	 * 
	 * @param container
	 * @return The <code>container</code> converted in a {@link Thread}
	 *         interface.
	 * @throws NullPointerException
	 *             Thrown if <code>container</code> is <code>null</code>.
	 */
	public static Thread asThread( Container container ) {
		Preconditions.checkNotNull( container,
		        "Required parameter container must be specified." );

		return Thread
		        .getInstance( container.getModel(), container.getResource() );
	}

	public static Post getPost( Model model, String id, Container container )
	        throws NotFoundException {
		String queryTemplate =
		        "PREFIX sioc: <http://rdfs.org/sioc/ns#>"
		                + "SELECT ?post WHERE { "
		                + "?post sioc:has_container %s ."
		                + "?post sioc:id %s . }";
		String query = SparqlUtil.formatQuery(
		        queryTemplate,
		        container,
		        SparqlUtil.toSparqlLiteral( id ) );

		QueryResultTable resultTable = model.sparqlSelect( query );

		for ( QueryRow queryRow : resultTable ) {
			Node postNode = queryRow.getValue( "post" );

			if ( null != postNode ) {
				return Post.getInstance( model, postNode.asResource() );
			}
		}

		throw new NotFoundException(
		        "No post found with id "
		                + id
		                + " and parent "
		                + container
		                + "." );
	}
}
