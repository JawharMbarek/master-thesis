package de.m0ep.oauth;

import java.awt.Desktop;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class OAuthTest {

	// private static String AUTH_URL =
	// "https://accounts.google.com/o/oauth2/auth?scope=%s&redirect_uri=%s&response_type=code&client_id=%s";
	// private static String SCOPE =
	// "https://www.googleapis.com/auth/plus.login";
	// private static String CLIENT_ID =
	// "733024832603-patciplam4cqq0dnv7a5qdhuq262n6ia.apps.googleusercontent.com";
	// private static String CLIENT_SECRET = "LckucP4MA1jJsZQKjk9okhAu";

	public static final String AUTH_URL = "https://www.facebook.com/dialog/oauth?scope=%s&redirect_uri=%s&client_id=%s";
	public static final String TOKEN_URL = "https://graph.facebook.com/oauth/access_token?client_id=%s&redirect_uri=%s&client_secret=%s&code=%s";;
	public static final String EXTENDED_URL = "https://graph.facebook.com/oauth/access_token?grant_type=fb_exchange_token&client_id=%s&client_secret=%s&fb_exchange_token=%s";;
	public static final String SCOPE = "publish_actions,read_stream,user_groups";
	public static final String CLIENT_ID = "410334369012894";
	public static final String CLIENT_SECRET = "5988954e39fc9ca4d39bd7b374a7d72b";

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main( String[] args ) throws Exception {
		CodeReceiverServer receiverServer = new CodeReceiverServer();

		String redirectUri = receiverServer.getRedirectUlAndStartServer();

		URI uri = new URI( String
		        .format( AUTH_URL, SCOPE, redirectUri, CLIENT_ID ) );

		System.out.println( "Start browser with: " + uri.toString() );
		Desktop.getDesktop().browse( uri );

		System.out.println( "wait for code..." );
		String code = receiverServer.waitForCode();
		receiverServer.stopServer();

		System.out.println( "Code: " + code );

		/*
		 * Get access token from authorization code
		 */
		String accessTokenUri = String.format(
		        TOKEN_URL,
		        CLIENT_ID,
		        redirectUri,
		        CLIENT_SECRET,
		        code );

		HttpClient client = new DefaultHttpClient();
		HttpPost postRequest = new HttpPost( accessTokenUri );
		HttpResponse response = client.execute( postRequest );

		System.out.println( response.getStatusLine().getStatusCode() );
		String body = EntityUtils.toString( response.getEntity() );

		List<NameValuePair> pairs = URLEncodedUtils.parse( body, Charset
		        .defaultCharset() );

		String accessToken = null;
		for ( NameValuePair nvp : pairs ) {
			if ( "access_token".equals( nvp.getName() ) ) {
				accessToken = nvp.getValue();
			}

			System.out.print( nvp.getName() );
			System.out.print( " = " );
			System.out.println( nvp.getValue() );
		}

		System.out.println( "--------------------" );

		if ( null != accessToken ) {
			/*
			 * extend access token
			 */
			postRequest = new HttpPost(
			        String.format(
			                EXTENDED_URL,
			                CLIENT_ID,
			                CLIENT_SECRET,
			                accessToken ) );

			response = client.execute( postRequest );

			System.out.println( response.getStatusLine().getStatusCode() );
			body = EntityUtils.toString( response.getEntity() );

			pairs = URLEncodedUtils.parse( body, Charset
			        .defaultCharset() );

			for ( NameValuePair nvp : pairs ) {
				System.out.print( nvp.getName() );
				System.out.print( " = " );
				System.out.println( nvp.getValue() );
			}
		}
	}
}
