
package de.m0ep.socc.core.connector.google.plus;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.CredentialRefreshListener;
import com.google.api.client.auth.oauth2.TokenErrorResponse;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.model.Person;

import de.m0ep.sioc.services.auth.AccessToken;
import de.m0ep.sioc.services.auth.ClientId;
import de.m0ep.sioc.services.auth.ClientSecret;
import de.m0ep.sioc.services.auth.RefreshToken;

public class GooglePlusClientWrapper implements CredentialRefreshListener {
    private static final Logger LOG = LoggerFactory
            .getLogger(GooglePlusClientWrapper.class);

    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final JacksonFactory JSON_FACTORY = new JacksonFactory();

    private Plus service;
    private AccessToken accessToken;
    private RefreshToken refreshToken;
    private Person person;

    public GooglePlusClientWrapper(
            ClientId clientId,
            ClientSecret clientSecret,
            AccessToken accessToken,
            RefreshToken refreshToken) throws Exception {

        GoogleCredential googleCredential = new GoogleCredential.Builder()
                .setClientSecrets(
                        clientId.getValue(),
                        clientSecret.getValue())
                .setJsonFactory(JSON_FACTORY)
                .setTransport(HTTP_TRANSPORT)
                .addRefreshListener(this)
                .build();

        service = new Plus.Builder(
                HTTP_TRANSPORT,
                JSON_FACTORY,
                googleCredential)
                .setApplicationName("SOCC Google Plus Connector")
                .build();
        try {
            person = service.people().get("me").execute();
        } catch (Exception e) {
            GooglePlusConnector.handleGoogleException(e);
        }
    }

    public Person getPerson() {
        return person;
    }

    public AccessToken getAccessToken() {
        return accessToken;
    }

    public RefreshToken getRefreshToken() {
        return refreshToken;
    }

    public Plus getGooglePlusService() {
        return service;
    }

    @Override
    public void onTokenResponse(Credential credential,
            TokenResponse tokenResponse) throws IOException {
        String newAccessToken = tokenResponse.getAccessToken();
        if (null != newAccessToken) {
            this.accessToken.setValue(newAccessToken);
        }

        String newRefresToken = tokenResponse.getRefreshToken();
        if (null != newRefresToken) {
            this.refreshToken.setValue(newRefresToken);
        }
    }

    @Override
    public void onTokenErrorResponse(Credential credential,
            TokenErrorResponse tokenErrorResponse) throws IOException {
        LOG.error("Google token refresh failed: {} - {}",
                tokenErrorResponse.getError(),
                tokenErrorResponse.getErrorDescription());
    }
}
