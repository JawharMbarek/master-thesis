package de.m0ep.oauthtool.facebook;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.damnhandy.uri.template.UriTemplate;
import com.google.common.base.Preconditions;

public class FacebookController {
	private static final Logger LOG = LoggerFactory.getLogger( FacebookController.class );

	public static final String URI_PARAM_SCOPE = "scope";
	public static final String URI_PARAM_REDIRECT_URI = "redirectUri";
	public static final String URI_PARAM_CLIENT_ID = "clientId";
	public static final String URI_PARAM_CLIENT_SECRET = "clientSecret";
	public static final String URI_PARAM_CODE = "code";
	public static final String URI_PARAM_TOKEN = "token";

	public static final String URI_OAUTH = "https://www.facebook.com/dialog/oauth"
	        + "?scope={" + URI_PARAM_SCOPE + "}"
	        + "&redirect_uri={" + URI_PARAM_REDIRECT_URI + "}"
	        + "&client_id={" + URI_PARAM_CLIENT_ID + "}";

	public static final String URI_ACCESS_TOKEN = "https://graph.facebook.com/oauth/access_token"
	        + "?client_id={" + URI_PARAM_CLIENT_ID + "}"
	        + "&redirect_uri={" + URI_PARAM_REDIRECT_URI + "}"
	        + "&client_secret={" + URI_PARAM_CLIENT_SECRET + "}"
	        + "&code={" + URI_PARAM_CODE + "}";

	public static final String URI_EXTENDED_ACCESS_TOKEN = "https://graph.facebook.com/oauth/access_token"
	        + "?grant_type=fb_exchange_token"
	        + "&client_id={" + URI_PARAM_CLIENT_ID + "}"
	        + "&client_secret={" + URI_PARAM_CLIENT_SECRET + "}"
	        + "&fb_exchange_token={" + URI_PARAM_TOKEN + "}";

	private final FacebookDialog view;

	public FacebookController( final FacebookDialog view ) {
		this.view = Preconditions.checkNotNull( view,
		        "Required parameter view must be specified." );

		view.getGenerateButton().addActionListener( new ActionListener() {
			@Override
			public void actionPerformed( ActionEvent e ) {
				try {
					FBCredentials credential = authorize(
					        view.getClientId().getText(),
					        view.getClientSecret().getText(),
					        view.getPermissons().getText() );

					setCredentialData( credential );
				} catch ( Exception e1 ) {
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

	protected FBCredentials authorize( String id, String secret, String permissons )
	        throws Exception {
		CodeReceiverServer receiverServer = new CodeReceiverServer();
		String redirectUri = receiverServer.getRedirectUlAndStartServer();

		// Open Browser
		Desktop.getDesktop().browse( new URI( UriTemplate.fromTemplate( URI_OAUTH )
		        .set( URI_PARAM_SCOPE, permissons )
		        .set( URI_PARAM_REDIRECT_URI, redirectUri )
		        .set( URI_PARAM_CLIENT_ID, id )
		        .expand() ) );

		String code = receiverServer.waitForCode();
		receiverServer.stopServer();

		/*
		 * Get access token from authorization code
		 */
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpPost httpPost = new HttpPost( UriTemplate.fromTemplate( URI_ACCESS_TOKEN )
			        .set( URI_PARAM_CLIENT_ID, id )
			        .set( URI_PARAM_REDIRECT_URI, redirectUri )
			        .set( URI_PARAM_CLIENT_SECRET, secret )
			        .set( URI_PARAM_CODE, code )
			        .expand() );

			LOG.info( "Send POST Request {}", httpPost );

			FBCredentials result = new FBCredentials();
			HttpResponse httpResponse = httpClient.execute( httpPost );
			String body = EntityUtils.toString( httpResponse.getEntity() );
			List<NameValuePair> pairList = URLEncodedUtils.parse( body, Charset.defaultCharset() );

			if ( LOG.isDebugEnabled() ) {
				LOG.debug( "Received response {} {} {}:\n{}",
				        httpResponse.getStatusLine().getProtocolVersion(),
				        httpResponse.getStatusLine().getStatusCode(),
				        httpResponse.getStatusLine().getReasonPhrase(),
				        body );
			} else {
				LOG.debug( "Received response {} {} {}",
				        httpResponse.getStatusLine().getProtocolVersion(),
				        httpResponse.getStatusLine().getStatusCode(),
				        httpResponse.getStatusLine().getReasonPhrase() );
			}

			for ( NameValuePair nvp : pairList ) {
				if ( "access_token".equals( nvp.getName() ) ) {
					result.setAccessToken( nvp.getValue() );
				} else if ( "expires".equals( nvp.getName() ) ) {
					result.setExpiresInSeconds( Integer.parseInt( nvp.getValue() ) );
				}
			}

			if ( null != result.getAccessToken() ) {
				httpPost = new HttpPost( UriTemplate.fromTemplate( URI_EXTENDED_ACCESS_TOKEN )
				        .set( URI_PARAM_CLIENT_ID, id )
				        .set( URI_PARAM_CLIENT_SECRET, secret )
				        .set( URI_PARAM_TOKEN, result.getAccessToken() )
				        .expand() );

				LOG.info( "Send POST Request {}", httpPost );

				httpResponse = httpClient.execute( httpPost );
				body = EntityUtils.toString( httpResponse.getEntity() );
				pairList = URLEncodedUtils.parse( body, Charset.defaultCharset() );

				if ( LOG.isDebugEnabled() ) {
					LOG.debug( "Received response {} {} {}:\n{}",
					        httpResponse.getStatusLine().getProtocolVersion(),
					        httpResponse.getStatusLine().getStatusCode(),
					        httpResponse.getStatusLine().getReasonPhrase(),
					        body );
				} else {
					LOG.debug( "Received response {} {} {}",
					        httpResponse.getStatusLine().getProtocolVersion(),
					        httpResponse.getStatusLine().getStatusCode(),
					        httpResponse.getStatusLine().getReasonPhrase() );
				}

				for ( NameValuePair nvp : pairList ) {
					if ( "access_token".equals( nvp.getName() ) ) {
						result.setExtendedAccessToken( nvp.getValue() );
					} else if ( "expires".equals( nvp.getName() ) ) {
						result.setExpiresInSeconds( Integer.parseInt( nvp.getValue() ) );
					}
				}

				return result;
			}
		} finally {
			httpClient.close();
		}

		return null;
	}

	protected void setCredentialData( FBCredentials credential ) {
		view.getAccesstoken().setText( credential.getAccessToken() );
		view.getExtendedToken().setText( credential.getExtendedAccessToken() );
		view.getExpires().setText( new Date( credential.getExpiresInSeconds() * 1000L
		        + System.currentTimeMillis() ).toString() );
	}

	public void run() {
		if ( null != view ) {
			view.showDialog();
		}
	}

	public static class FBCredentials {
		private String accessToken;
		private String extendedAccessToken;
		private int expires;

		public FBCredentials() {
			accessToken = null;
			extendedAccessToken = null;
			expires = 0;
		}

		public String getAccessToken() {
			return accessToken;
		}

		public void setAccessToken( String accessToken ) {
			this.accessToken = accessToken;
		}

		public String getExtendedAccessToken() {
			return extendedAccessToken;
		}

		public void setExtendedAccessToken( String extendedAccessToken ) {
			this.extendedAccessToken = extendedAccessToken;
		}

		public int getExpiresInSeconds() {
			return expires;
		}

		public void setExpiresInSeconds( int expires ) {
			this.expires = expires;
		}
	}
}
