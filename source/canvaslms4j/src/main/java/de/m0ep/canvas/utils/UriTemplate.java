package de.m0ep.canvas.utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.Preconditions;

public class UriTemplate {
    private String template;
    private Map<String, Object> parameterMap;

    public UriTemplate(String template) {
	this.template = template;
	this.parameterMap = new HashMap<String, Object>();
    }

    public UriTemplate set(String key, Object value) {
	Preconditions.checkNotNull(key,
		"Required parameter key must be specified.");
	Preconditions.checkNotNull(value,
		"Required parameter value must be specified.");

	parameterMap.put(key, value);
	return this;
    }

    public URI toUri() throws URISyntaxException {
	return new URI(toString());
    }

    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder(template);
	int start = -1;

	for (Entry<String, Object> entry : parameterMap.entrySet()) {
	    String pattern = "{" + entry.getKey() + "}";
	    String value = entry.getValue().toString();

	    while (-1 != (start = builder.indexOf(pattern))) {
		builder.replace(start, start + pattern.length(), value);
	    }
	}

	return builder.toString();
    }
}
