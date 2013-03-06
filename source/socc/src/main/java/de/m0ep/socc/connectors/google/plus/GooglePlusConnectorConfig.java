package de.m0ep.socc.connectors.google.plus;

import de.m0ep.socc.connectors.OAuth2ConnectorConfig;

public class GooglePlusConnectorConfig extends OAuth2ConnectorConfig {
    private static final long serialVersionUID = 1613150078617134827L;

    public static final String REFRESH_TOKEN = "refreshToken";

    private String refreshToken;

    public String getRefreshToken() {
	return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
	this.refreshToken = refreshToken;
    }

}
