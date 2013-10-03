package de.m0ep.oauthtool;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import javax.swing.JOptionPane;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.common.base.Preconditions;

public class GooglePlusController {

	/** Global instance of the HTTP transport. */
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();

	private final GooglePlusDialog view;

	public GooglePlusController( final GooglePlusDialog view ) {
		this.view = Preconditions.checkNotNull( view,
		        "Required parameter view must be specified." );

		view.getGenerateButton().addActionListener( new ActionListener() {
			@Override
			public void actionPerformed( ActionEvent e ) {
				try {
					Credential credential = authorize(
					        view.getClientId().getText(),
					        view.getClientSecret().getText(),
					        Arrays.asList( view.getScopes().getText().split( ";" ) ) );

					setCredentialData( credential );
				} catch ( IOException e1 ) {
					JOptionPane.showMessageDialog(
					        view,
					        "Failed to get Accesstoke:\n" + e1.getMessage(),
					        "Error",
					        JOptionPane.ERROR_MESSAGE );
				}
			}
		} );

		view.getCloseButton().addActionListener( new ActionListener() {
			@Override
			public void actionPerformed( ActionEvent e ) {
				view.closeDialog();
			}
		} );
	}

	protected Credential authorize( String id, String secret, Collection<String> scopes )
	        throws IOException {
		// set up authorization code flow
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
		        HTTP_TRANSPORT,
		        JSON_FACTORY,
		        id,
		        secret,
		        scopes )
		        .build();

		return new AuthorizationCodeInstalledApp( flow,
		        new LocalServerReceiver() ).authorize( "user" );

	}

	protected void setCredentialData( Credential credential ) {
		view.getAccesstoken().setText( credential.getAccessToken() );
		view.getRefreshtoken().setText( credential.getRefreshToken() );
		view.getExpires().setText( new Date( credential.getExpiresInSeconds() * 1000L
		        + System.currentTimeMillis() ).toString() );
	}

	public void run() {
		if ( null != view ) {
			view.showDialog();
		}
	}
}
