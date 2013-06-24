package de.m0ep.canvaslms;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Date;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Range;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.m0ep.canvaslms.exceptions.AccessControlException;
import de.m0ep.canvaslms.exceptions.AuthorizationException;
import de.m0ep.canvaslms.exceptions.CanvasLMSException;
import de.m0ep.canvaslms.exceptions.NetworkException;
import de.m0ep.canvaslms.gson.ISO8601TypeAdapter;
import de.m0ep.canvaslms.model.CourseInfo;
import de.m0ep.canvaslms.model.DiscussionTopicEntry;
import de.m0ep.canvaslms.model.DiscussionTopicInfo;

public class CanvasLMSClient {

    public static Range<Integer> HTTP_SUCCESSFULL_CODE_RANGE = Range
	    .closedOpen(200, 300);

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

	this.baseUri += "/api/v1";

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

    public Pagination<DiscussionTopicInfo> listCourseDiscussionTopics(
	    final long id) throws CanvasLMSException {
	return fetchPagination(
		"/courses/"
			+ id
			+ "/discussion_topics",
		DiscussionTopicInfo.class);
    }

    public Pagination<CourseInfo> listCourses() throws CanvasLMSException {
	return fetchPagination("/courses", CourseInfo.class);
    }

    public Pagination<DiscussionTopicEntry> listDiscussionTopicEntries(
	    final long courseId, final long topicId) throws CanvasLMSException {
	return fetchPagination("/courses/"
		+ courseId
		+ "/discussion_topics/"
		+ topicId
		+ "/entries",
		DiscussionTopicEntry.class);
    }

    public DiscussionTopicEntry postDiscussionEntry(String message,
	    long courseId,
	    long topicId) throws CanvasLMSException {
	HttpResponse response = makePostRequest("/courses/"
		+ courseId
		+ "/discussion_topics/"
		+ topicId
		+ "/entries",
		new BasicNameValuePair("message", message));

	String data = "";
	try {
	    data = EntityUtils.toString(response.getEntity());
	} catch (Throwable t) {
	    throw new CanvasLMSException(
		    "Failed to read data from stream.", t);
	}

	StatusLine status = response.getStatusLine();
	int statusCode = status.getStatusCode();
	if (HTTP_SUCCESSFULL_CODE_RANGE.contains(statusCode)) { // OK

	    return gson.fromJson(
		    data,
		    DiscussionTopicEntry.class);

	} else if (401 == statusCode) { // Unauthorized
	    throw new AuthorizationException(
		    "Authorization error: Accesstoken is missing or invalid.");
	} else if (403 == statusCode) { // Forbidden
	    throw new AccessControlException("Forbidden access: " + data);
	} else {
	    throw new CanvasLMSException(
		    "Http error: " + status.getStatusCode());
	}
    }

    public DiscussionTopicEntry postReply(String message, long courseId,
	    long topicId,
	    long postId) throws CanvasLMSException {
	HttpResponse response = makePostRequest("/courses/"
		+ courseId
		+ "/discussion_topics/"
		+ topicId
		+ "/entries/"
		+ postId
		+ "/replies",
		new BasicNameValuePair("message", message));

	String data = "";
	try {
	    data = EntityUtils.toString(response.getEntity());
	} catch (Throwable t) {
	    throw new CanvasLMSException(
		    "Failed to read data from stream.", t);
	}

	StatusLine status = response.getStatusLine();
	int statusCode = status.getStatusCode();
	if (HTTP_SUCCESSFULL_CODE_RANGE.contains(statusCode)) { // OK

	    return gson.fromJson(
		    data,
		    DiscussionTopicEntry.class);

	} else if (401 == statusCode) { // Unauthorized
	    throw new AuthorizationException(
		    "Authorization error: Accesstoken is missing or invalid.");
	} else if (403 == statusCode) { // Forbidden
	    throw new AccessControlException("Forbidden access: " + data);
	} else {
	    throw new CanvasLMSException(
		    "Http error: " + status.getStatusCode());
	}
    }

    public <T> Pagination<T> fetchPagination(String uri, Class<T> type)
	    throws CanvasLMSException {
	HttpResponse response = makeGetRequest(uri);

	String data = "";
	try {
	    data = EntityUtils.toString(response.getEntity());
	} catch (Throwable t) {
	    throw new CanvasLMSException(
		    "Failed to read data from stream.", t);
	}

	StatusLine status = response.getStatusLine();
	int statusCode = status.getStatusCode();

	if (HTTP_SUCCESSFULL_CODE_RANGE.contains(statusCode)) {// OK
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

	    return new Pagination<T>(this, data, nextUri, type);
	} else if (401 == statusCode) { // Unauthorized
	    throw new AuthorizationException(
		    "Authorization error: Accesstoken is missing or invalid.");
	} else if (403 == statusCode) { // Forbidden
	    throw new AccessControlException("Forbidden access: " + data);
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

    HttpResponse makePostRequest(String uri, NameValuePair... params)
	    throws CanvasLMSException {

	try {
	    HttpPost request = createRequestObject(
		    HttpPost.class,
		    uri);

	    request.setEntity(new UrlEncodedFormEntity(
		    Arrays.asList(params), "UTF-8"));

	    return httpClient.execute(request);
	} catch (ClientProtocolException e) {
	    throw new CanvasLMSException("Http protocol error.", e);
	} catch (IOException e) {
	    throw new NetworkException("Network error.", e);
	}
    }

    HttpResponse makePutRequest(String uri, NameValuePair... params)
	    throws CanvasLMSException {

	try {
	    HttpPut request = createRequestObject(
		    HttpPut.class,
		    uri);

	    request.setEntity(new UrlEncodedFormEntity(
		    Arrays.asList(params), "UTF-8"));

	    return httpClient.execute(request);
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
