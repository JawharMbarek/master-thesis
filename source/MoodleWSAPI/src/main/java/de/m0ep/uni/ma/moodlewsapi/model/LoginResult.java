package de.m0ep.uni.ma.moodlewsapi.model;

public class LoginResult {
    private int    client;
    private String sessionKey;

    public LoginResult( int client, String sessionKey ) {
        this.client = client;
        this.sessionKey = sessionKey;
    }

    public int getClient() {
        return client;
    }

    public String getSessionKey() {
        return sessionKey;
    }
}
