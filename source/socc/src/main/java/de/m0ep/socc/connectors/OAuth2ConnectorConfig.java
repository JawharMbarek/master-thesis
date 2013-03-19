package de.m0ep.socc.connectors;

public class OAuth2ConnectorConfig extends DefaultConnectorConfig {
    private static final long serialVersionUID = 1613150078617134827L;

    public static final String CLIENT_ID = "clientId";
    public static final String CLIENT_SECRET = "clientSecret";
    public static final String ACCESS_TOKEN = "accessToken";
    public static final String EXPIRES_IN_SECONDS = "expiresInSeconds";

    private String clientId;
    private String clientSecret;
    private String accessToken;
    private long expiresInSeconds;

    public String getClientId() {
	return clientId;
    }

    public void setClientId(String clientId) {
	this.clientId = clientId;
    }

    public String getClientSecret() {
	return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
	this.clientSecret = clientSecret;
    }

    public String getAccessToken() {
	return accessToken;
    }

    public void setAccessToken(String accessToken) {
	this.accessToken = accessToken;
    }

    public long getExpiresInSeconds() {
	return expiresInSeconds;
    }

    public void setExpiresInSeconds(long expiresInSeconds) {
	this.expiresInSeconds = expiresInSeconds;
    }
}
