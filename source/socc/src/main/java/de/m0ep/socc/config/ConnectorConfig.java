package de.m0ep.socc.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

public class ConnectorConfig {
    public String id;
    @SerializedName("factory")
    public String factory_id;
    @SerializedName("config")
    public HashMap<String, String> parameters;

    public ConnectorConfig() {
	parameters = new HashMap<String, String>();
    }

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getFactoryName() {
	return factory_id;
    }

    public void setFactoryName(String factory_id) {
	this.factory_id = factory_id;
    }

    public Map<String, String> getParameters() {
	return Collections.unmodifiableMap(parameters);
    }

    public void putParameter(String key, String value) {
	parameters.put(key, value);
    }

    public String getParameter(String key) {
	return parameters.get(key);
    }
}
