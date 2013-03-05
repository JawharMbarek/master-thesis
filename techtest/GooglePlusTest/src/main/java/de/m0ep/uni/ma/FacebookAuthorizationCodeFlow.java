package de.m0ep.uni.ma;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.Credential.AccessMethod;
import com.google.api.client.auth.oauth2.CredentialStore;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.Clock;
import com.google.common.base.Preconditions;

public class FacebookAuthorizationCodeFlow extends AuthorizationCodeFlow {

    public FacebookAuthorizationCodeFlow(AccessMethod method,
	    HttpTransport transport, JsonFactory jsonFactory,
	    GenericUrl tokenServerUrl,
	    HttpExecuteInterceptor clientAuthentication, String clientId,
	    String authorizationServerEncodedUrl,
	    CredentialStore credentialStore,
	    HttpRequestInitializer requestInitializer, String scopes) {
	super(method, transport, jsonFactory, tokenServerUrl,
		clientAuthentication, clientId, authorizationServerEncodedUrl,
		credentialStore, requestInitializer, scopes);
	// TODO Auto-generated constructor stub
    }

    public FacebookAuthorizationCodeFlow(AccessMethod method,
	    HttpTransport transport, JsonFactory jsonFactory,
	    GenericUrl tokenServerUrl,
	    HttpExecuteInterceptor clientAuthentication, String clientId,
	    String authorizationServerEncodedUrl,
	    CredentialStore credentialStore,
	    HttpRequestInitializer requestInitializer, String scopes,
	    Clock clock) {
	super(method, transport, jsonFactory, tokenServerUrl,
		clientAuthentication, clientId, authorizationServerEncodedUrl,
		credentialStore, requestInitializer, scopes, clock);
	// TODO Auto-generated constructor stub
    }

    public FacebookAuthorizationCodeFlow(Builder builder) {
	this(builder.getMethod(), builder.getTransport(), builder
		.getJsonFactory(), builder.getTokenServerUrl(), builder
		.getClientAuthentication(), builder.getClientId(), builder
		.getAuthorizationServerEncodedUrl(), builder
		.getCredentialStore(), builder.getRequestInitializer(), builder
		.getScopes(), builder.getClock());
    }

    public static class Builder extends AuthorizationCodeFlow.Builder {

	/**
	 * @param transport
	 *            HTTP transport
	 * @param jsonFactory
	 *            JSON factory
	 * @param clientId
	 *            client identifier
	 * @param clientSecret
	 *            client secret
	 * @param scopes
	 *            list of scopes to be joined by a space separator (or a
	 *            single value containing multiple space-separated scopes)
	 */
	public Builder(HttpTransport transport, JsonFactory jsonFactory,
		String clientId, String clientSecret, Iterable<String> scopes) {

	    super(BearerToken.authorizationHeaderAccessMethod(), transport,
		    jsonFactory, new GenericUrl(
			    FacebookOAuthConstants.TOKEN_SERVER_URL),
		    new ClientParametersAuthentication(clientId, clientSecret),
		    clientId, FacebookOAuthConstants.AUTHORIZATION_SERVER_URL);
	    setScopes(Preconditions.checkNotNull(scopes));
	}

	@Override
	public FacebookAuthorizationCodeFlow build() {
	    // TODO Auto-generated method stub
	    return new FacebookAuthorizationCodeFlow(this);
	}
    }
}
