package de.m0ep.socc.core.utils;

import java.text.ParseException;
import java.util.Date;

import org.ontoware.rdf2go.model.node.URI;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Item;
import org.rdfs.sioc.SIOCVocabulary;

import com.google.common.base.Preconditions;

public final class SiocUtils {
	private SiocUtils() {
	}

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

	public static void updateLastReplyDate( Item item, Date createdDate ) {
		Preconditions.checkNotNull( item,
		        "Required parameter post must be specified." );
		Preconditions.checkNotNull( createdDate,
		        "Required parameter createdDate must be specified." );

		if ( item.hasLastReplyDate() ) {
			String rawLastItemDate = item.getLastReplyDate();

			Date lastItemDate;
			try {
				lastItemDate = DateUtils.parseISO8601( rawLastItemDate );
			} catch ( ParseException e ) {
				throw new IllegalArgumentException(
				        "The parameter container has an invalid lastLastDate value." );
			}

			if ( null != lastItemDate && createdDate.after( lastItemDate ) ) {
				item.setLastReplyDate( DateUtils.formatISO8601( createdDate ) );
			}

		} else {
			item.setLastReplyDate( DateUtils.formatISO8601( createdDate ) );
		}
	}

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

	public static void incNumReplies( Item item ) {
		Preconditions.checkNotNull( item,
		        "Required parameter item must be specified." );

		if ( item.hasNumReplies() ) {
			item.setNumReplies( item.getNumReplies() + 1 );
		} else {
			item.setNumReplies( 1 );
		}
	}

	public static void incNumThreads( Forum forum ) {
		Preconditions.checkNotNull( forum,
		        "Required parameter forum must be specified." );

		if ( forum.hasNumThreads() ) {
			forum.setNumThreads( forum.getNumThreads() + 1 );
		} else {
			forum.setNumThreads( 1 );
		}
	}

	public static boolean isContainerOfSite( Container container, URI siteUri ) {
		Preconditions.checkNotNull( siteUri,
		        "Required parameter siteUri must be specified." );
		Preconditions.checkNotNull( container,
		        "Required parameter container must be specified." );

		// if its a forum, it has maybe a host
		if ( RdfUtils.isType( container.getModel(), container.getResource(), SIOCVocabulary.Forum ) ) {
			Forum forum = Forum.getInstance( container.getModel(), container.getResource() );
			return forum.hasHost( siteUri );
		}

		// walk through parent
		if ( container.hasParent() ) {
			return isContainerOfSite( container.getParent(), siteUri );
		}

		return false;
	}
}
