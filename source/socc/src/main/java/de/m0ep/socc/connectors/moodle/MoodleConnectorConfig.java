package de.m0ep.socc.connectors.moodle;

import de.m0ep.socc.connectors.AbstractConnectorConfig;

public class MoodleConnectorConfig extends AbstractConnectorConfig {
    private static final long serialVersionUID = 6469962267853893278L;

    public static final String URL = "url";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    private String url;
    private String username;
    private String password;

    public String getUrl() {
	return url;
    }

    public void setUrl(String url) {
	this.url = url;
    }

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
