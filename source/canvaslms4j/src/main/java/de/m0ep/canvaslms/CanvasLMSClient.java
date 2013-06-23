package de.m0ep.canvaslms;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.m0ep.canvaslms.exceptions.AuthorizationException;
import de.m0ep.canvaslms.exceptions.CanvasLMSException;
import de.m0ep.canvaslms.exceptions.NetworkException;
import de.m0ep.canvaslms.gson.ISO8601TypeAdapter;
import de.m0ep.canvaslms.model.DiscussionTopicResponse;

public class CanvasLMSClient {
    private String accessToken;
    private String baseUri;
    private HttpClient httpClient;

    /* package */Gson gson;

    /**
     * Constructor to create a new {@link CanvasLMSClient}
     * 
     * @param uri
     *            The uri to a CanvasLMS instance.
     * 
     * @param accesstoken
     *            An CanvasLMS accesstoken
     * 
     * @throws NullPointerException
     *             Thrown if uri or accesstoken are null.
     * @throws IllegalArgumentException
     *             Thrown if uri or accesstoken are empty.
     */
    public CanvasLMSClient(final String uri, final String accesstoken) {
	this.baseUri = Preconditions.checkNotNull(
		uri,
		"Uri can not be null.");
	Preconditions.checkArgument(
		!uri.isEmpty(),
		"Uri can not be empty.");

	if (uri.endsWith("/")) {
	    this.baseUri = this.baseUri.substring(0, this.baseUri.length() - 1);
	}

	this.accessToken = Preconditions.checkNotNull(
		accesstoken,
		"Token can not be null.");
	Preconditions.checkArgument(
		!accesstoken.isEmpty(),
		"Token can not be empty.");

	this.httpClient = new DefaultHttpClient();
	this.gson = new GsonBuilder()
		.registerTypeAdapter(
			Date.class,
			new ISO8601TypeAdapter().nullSafe())
		.create();

    }

    /**
     * Returns the CanvasLMS instance url.
     * 
     * @return A {@link String} containing the url.
     */
    public String getUri() {
	return baseUri;
    }

    /**
     * Returns the provided accesstoken.
     * 
     * @return A {@link String} containing the accesstoken.
     */
    public String getAccessToken() {
	return accessToken;
    }

    /**
     * Retruns the used GSON parser.
     * 
     * @return A {@link Gson} instance.
     */
    public Gson getJsonParser() {
	return gson;
    }

    public Pagination<DiscussionTopicResponse> listCourseDiscussionTopics(
	    final long id) throws CanvasLMSException {
	return fetchPagination(
		"/api/v1/courses/"
			+ id
			+ "/discussion_topics",
		DiscussionTopicResponse.class);
    }

    public <T> Pagination<T> fetchPagination(String uri, Class<T> type)
	    throws CanvasLMSException {
	HttpResponse response = makeGetRequest(uri);
	StatusLine status = response.getStatusLine();

	if (200 == status.getStatusCode()) {
	    Header[] links = response.getHeaders("Link");

	    String nextUri = null;
	    for (Header link : links) {
		String value = link.getValue();
		if (value.contains("rel=\"next\"")) {
		    String[] split = value.split(";");

		    nextUri = split[0].trim();
		    nextUri = nextUri.substring(1, nextUri.length() - 1);
		    break;
		}
	    }

	    String json = "[]";
	    try {
		json = EntityUtils.toString(response.getEntity());
	    } catch (Throwable t) {
		throw new CanvasLMSException(
			"Failed to read data from stream.", t);
	    }

	    return new Pagination<T>(this, json, nextUri, type);
	} else if (401 == status.getStatusCode()) {
	    throw new AuthorizationException(
		    "Authorization error: Accesstoken is missing or invalid.");
	} else {
	    throw new CanvasLMSException(
		    "Http error: " + status.getStatusCode());
	}
    }

    HttpResponse makeGetRequest(String uri, NameValuePair... params)
	    throws CanvasLMSException {

	URIBuilder builder;
	try {
	    builder = new URIBuilder(uri);
	} catch (URISyntaxException e) {
	    throw new CanvasLMSException("Invalid URI: " + uri);
	}

	for (NameValuePair param : params) {
	    builder.addParameter(param.getName(), param.getValue());
	}

	try {
	    HttpGet request = createRequestObject(
		    HttpGet.class,
		    builder.build());
	    return httpClient.execute(request);
	} catch (URISyntaxException e) {
	    throw new CanvasLMSException("Invalid URI: " + uri);
	} catch (ClientProtocolException e) {
	    throw new CanvasLMSException("Http protocol error.", e);
	} catch (IOException e) {
	    throw new NetworkException("Network error.", e);
	}
    }

    <T extends HttpRequestBase> T createRequestObject(Class<T> type,
	    String uri) throws CanvasLMSException {
	try {
	    if (!uri.startsWith(baseUri)) {
		if (!uri.startsWith("/")) {
		    uri = "/" + uri;
		}
		uri = baseUri + uri;
	    }

	    T instance = type.newInstance();
	    instance.setURI(new URI(uri));
	    instance.addHeader("Authorization", "Bearer " + accessToken);

	    return instance;
	} catch (Exception e) {
	    throw new CanvasLMSException("Failed to create request", e);
	}
    }

    <T extends HttpRequestBase> T createRequestObject(Class<T> type,
	    URI uri) throws CanvasLMSException {
	return createRequestObject(type, uri.toString());
    }
}
