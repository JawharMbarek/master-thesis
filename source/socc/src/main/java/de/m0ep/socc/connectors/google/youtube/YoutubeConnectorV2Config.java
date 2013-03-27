package de.m0ep.socc.connectors.google.youtube;

import de.m0ep.socc.config.LoginConnectorConfig;

public class YoutubeConnectorV2Config extends LoginConnectorConfig {
    private static final long serialVersionUID = -2587148753086121486L;

    public static final String DEVELOPER_KEY = "developerKey";

    private String developerKey;

    public String getDeveloperKey() {
	return developerKey;
    }

    public void setDeveloperKey(String developerKey) {
	this.developerKey = developerKey;
    }
}
