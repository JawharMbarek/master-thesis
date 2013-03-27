package de.m0ep.socc.connectors.moodle;

import de.m0ep.socc.config.LoginConnectorConfig;

public class MoodleConnectorConfig extends LoginConnectorConfig {
    private static final long serialVersionUID = 6469962267853893278L;

    public static final String URL = "url";

    private String url;

    public String getUrl() {
	return url;
    }

    public void setUrl(String url) {
	this.url = url;
    }

}
