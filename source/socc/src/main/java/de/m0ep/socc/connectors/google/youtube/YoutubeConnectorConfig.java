package de.m0ep.socc.connectors.google.youtube;

import de.m0ep.socc.connectors.AbstractConnectorConfig;

public class YoutubeConnectorConfig extends AbstractConnectorConfig {
    private static final long serialVersionUID = -2587148753086121486L;

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    private String username;
    private String password;

    public String getUsername() {
	return username;
    }

    public void setUsername(String username) {
	this.username = username;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }
}
