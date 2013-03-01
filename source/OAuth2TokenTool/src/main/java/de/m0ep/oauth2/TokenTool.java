package de.m0ep.oauth2;

import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.io.IOException;
import java.net.URI;

import org.scribe.builder.api.DefaultApi20;
import org.scribe.builder.api.FacebookApi;
import org.scribe.model.OAuthConfig;
import org.scribe.model.OAuthConstants;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.SignatureType;
import org.scribe.oauth.OAuth20ServiceImpl;
import org.scribe.oauth.OAuthService;

import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.common.base.Preconditions;

public class TokenTool {

    public TokenTool() {

	LocalServerReceiver receiver = new LocalServerReceiver.Builder()
		.setHost("localhost").setPort(46441).build();

	OAuthConfig config = null;
	// DefaultApi20 api = new GoogleApi2();
	DefaultApi20 api = new FacebookApi();
	OAuthService service = null;
	String code = null;
	try {
	    // service = new ServiceBuilder().apiKey("218182098322396")
	    // .apiSecret("7b80b9d7265c719e1d9efe112e4dbada")
	    // .provider(new FacebookApi())
	    // .callback(receiver.getRedirectUri()).build();

	    String redirect_uri = receiver.getRedirectUri();
	    config = new OAuthConfig(
"218182098322396",
		    "7b80b9d7265c719e1d9efe112e4dbada", redirect_uri,
		    SignatureType.Header, "user_about_me", null);
	    // config = new OAuthConfig(
	    // "733024832603-patciplam4cqq0dnv7a5qdhuq262n6ia.apps.googleusercontent.com",
	    // "LckucP4MA1jJsZQKjk9okhAu",
	    // redirect_uri,
	    // SignatureType.Header,
//			"https://www.googleapis.com/auth/plus.me", 
	    // null);

	    service = new OAuth20ServiceImpl(api, config);
	    browse(service.getAuthorizationUrl(null));
	    code = receiver.waitForCode();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	if (null != code) {
	    OAuthRequest request = new OAuthRequest(
api.getAccessTokenVerb(),
		    api.getAccessTokenEndpoint());

	    request.addQuerystringParameter(OAuthConstants.CLIENT_ID,
		    config.getApiKey());
	    request.addQuerystringParameter(OAuthConstants.CLIENT_SECRET,
		    config.getApiSecret());
	    request.addQuerystringParameter(OAuthConstants.CODE, code);
	    request.addQuerystringParameter(OAuthConstants.REDIRECT_URI,
		    config.getCallback());

	    // request.addBodyParameter(OAuthConstants.CLIENT_ID,
	    // config.getApiKey());
	    // request.addBodyParameter(OAuthConstants.CLIENT_SECRET,
	    // config.getApiSecret());
	    // request.addBodyParameter(OAuthConstants.CODE, code);
	    // request.addBodyParameter(OAuthConstants.REDIRECT_URI,
	    // config.getCallback());
	    request.addBodyParameter("grant_type", "authorization_code");
	    Response response = request.send();
	    
	    
	    System.out.println(response.getBody());
	}
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
	new TokenTool();
    }

    public static void browse(String url) {
	Preconditions.checkNotNull(url);
	if (Desktop.isDesktopSupported()) {
	    Desktop desktop = Desktop.getDesktop();
	    if (desktop.isSupported(Action.BROWSE)) {
		try {
		    desktop.browse(URI.create(url));
		    return;
		} catch (IOException e) {
		    // handled below
		}
	    }
	}
	// Finally just ask user to open in their browser using copy-paste
	System.out.println("Please open the following URL in your browser:");
	System.out.println("  " + url);
    }

}
