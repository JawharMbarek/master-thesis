package de.m0ep.socc.connectors.google.youtube;

import com.google.common.base.Objects;

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

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + Objects.hashCode(this.developerKey);
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
	if (!(obj instanceof YoutubeConnectorV2Config)) {
	    return false;
	}
	YoutubeConnectorV2Config other = (YoutubeConnectorV2Config) obj;
	if (!Objects.equal(this.developerKey, other.developerKey)) {
	    return false;
	}

	return super.equals(obj);
    }

}
