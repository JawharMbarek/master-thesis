package de.m0ep.oauth2;

import java.io.Serializable;

public class GoogleCredentials implements Serializable {
    private static final long serialVersionUID = 1L;

    private String clientId;
    private String clientSecret;
    private String accessToken;
    private String refreshToken;
    private Long expiresSeconds;
    private Long expriresAt;

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

    public String getRefreshToken() {
	return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
	this.refreshToken = refreshToken;
    }

    public Long getExpiresSeconds() {
	return expiresSeconds;
    }

    public void setExpiresSeconds(Long expiresSeconds) {
	this.expiresSeconds = expiresSeconds;
    }

    public Long getExpriresAt() {
	return expriresAt;
    }

    public void setExpriresAt(Long expriresAt) {
	this.expriresAt = expriresAt;
    }

}
