package de.m0ep.socc.connectors.google.plus;

import com.google.common.base.Objects;

import de.m0ep.socc.config.OAuth2ConnectorConfig;

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

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + Objects.hashCode(this.refreshToken);
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (!super.equals(obj)) {
	    return false;
	}
	if (!(obj instanceof GooglePlusConnectorConfig)) {
	    return false;
	}
	GooglePlusConnectorConfig other = (GooglePlusConnectorConfig) obj;

	if (!Objects.equal(this.refreshToken, other.refreshToken)) {
	    return false;
	}

	return super.equals(obj);
    }
}
