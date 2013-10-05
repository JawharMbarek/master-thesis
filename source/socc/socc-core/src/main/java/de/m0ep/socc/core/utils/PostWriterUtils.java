package de.m0ep.socc.core.utils;

import org.ontoware.rdf2go.model.Model;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.UserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Throwables;

import de.m0ep.socc.core.connector.IConnector;

public final class PostWriterUtils {
	private static final Logger LOG = LoggerFactory.getLogger( PostWriterUtils.class );

	private PostWriterUtils() {

	}

	/**
	 * Returns the {@link UserAccount} of the posts creator. If there is already
	 * one in the model, this will be used.
	 * 
	 * @param connector
	 *            Used Connector
	 * @param post
	 *            The {@link Post}
	 * 
	 * @return The {@link UserAccount} of the creator or <code>null</code>.
	 */
	public static UserAccount getCreatorUserAccount( final IConnector connector, final Post post ) {
		if ( null != post ) {
			if ( post.hasCreator() ) {
				UserAccount result = post.getCreator();
				Model model = connector.getContext().getModel();

				if ( UserAccount.hasInstance( model, result.getResource() ) ) {
					return UserAccount.getInstance( model, result.getResource() );
				}

				return result;
			}
		}

		return null;
	}

	/**
	 * REturns the Client for the creators {@link UserAccount}.
	 * 
	 * @param connector
	 *            The used Connector
	 * @param creatorAccount
	 *            The {@link UserAccount}
	 * @return The client for this {@link UserAccount} or <code>null</code>.
	 */
	@SuppressWarnings( "unchecked" )
	public static <T> T getClientOfCreator( IConnector connector, UserAccount creatorAccount ) {
		if ( null != creatorAccount ) {
			if ( creatorAccount.hasAccountName() && creatorAccount.hasAccountServiceHomepage() ) {
				try {
					UserAccount serviceAccount = UserAccountUtils.findUserAccountOfService(
					        connector.getContext().getModel(),
					        creatorAccount,
					        connector.getService() );

					return (T) connector.getClientManager().get( serviceAccount );
				} catch ( Exception e ) {
					if ( LOG.isDebugEnabled() ) {
						LOG.warn(
						        "No client found for UserAccount {}: exception -> {}\n{}",
						        creatorAccount,
						        e.getMessage(),
						        Throwables.getStackTraceAsString( e ) );
					} else {
						LOG.warn( "No client found for UserAccount {}" );
					}
				}
			}
		}

		return null;
	}

}
