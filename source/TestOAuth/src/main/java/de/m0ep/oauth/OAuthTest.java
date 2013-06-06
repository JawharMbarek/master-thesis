package de.m0ep.oauth;

import java.awt.Desktop;
import java.net.URI;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.util.ssl.SslContextFactory;

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
    public static final String SCOPE = "user_about_me";
    public static final String CLIENT_ID = "218182098322396";
    public static final String CLIENT_SECRET = "f4ed27b621c0f6476c2741f7cf9c4dc5";

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
	CodeReceiverServer receiverServer = new CodeReceiverServer();

	String redirectUri = receiverServer.getRedirectUlAndStartServer();

	URI uri = new URI(String
		.format(AUTH_URL, SCOPE, redirectUri, CLIENT_ID));

	System.out.println("Start browser with: " + uri.toString());
	Desktop.getDesktop().browse(uri);

	System.out.println("wait for code...");
	String code = receiverServer.waitForCode();

	System.out.println("Code: " + code);

	SslContextFactory sslFactory = new SslContextFactory(true);
	HttpClient client = new HttpClient(sslFactory);
	client.start();

	System.out.println(client.GET(
		String.format(TOKEN_URL, CLIENT_ID, redirectUri,
			CLIENT_SECRET, code)).getContentAsString());

	client.stop();
	receiverServer.stopServer();
    }
}
