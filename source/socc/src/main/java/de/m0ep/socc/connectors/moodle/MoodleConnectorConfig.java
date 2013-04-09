package de.m0ep.socc.connectors.moodle;

import com.google.common.base.Objects;

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

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + Objects.hashCode(this.url);
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
	if (!(obj instanceof MoodleConnectorConfig)) {
	    return false;
	}
	MoodleConnectorConfig other = (MoodleConnectorConfig) obj;

	if (Objects.equal(this.url, other.url)) {
	    return false;
	}

	return super.equals(obj);
    }

}
