package de.m0ep.socc.connectors.google.youtube;

import de.m0ep.socc.connectors.DefaultConnectorConfig;

public class YoutubeConnectorV2Config extends DefaultConnectorConfig {
    private static final long serialVersionUID = -2587148753086121486L;

    public static final String EMAIL = "email";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String DEVELOPER_KEY = "developerKey";

    private String email;
    private String username;
    private String password;
    private String developerKey;

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
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

    public String getDeveloperKey() {
	return developerKey;
    }

    public void setDeveloperKey(String developerKey) {
	this.developerKey = developerKey;
    }
}
