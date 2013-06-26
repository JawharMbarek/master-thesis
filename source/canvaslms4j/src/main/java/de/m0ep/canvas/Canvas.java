package de.m0ep.canvas;

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

import de.m0ep.canvas.exceptions.AccessControlException;
import de.m0ep.canvas.exceptions.AuthorizationException;
import de.m0ep.canvas.exceptions.CanvasException;
import de.m0ep.canvas.exceptions.NetworkException;
import de.m0ep.canvas.gson.ISO8601TypeAdapter;
import de.m0ep.canvas.model.Course;
import de.m0ep.canvas.model.DiscussionTopicEntry;
import de.m0ep.canvas.model.DiscussionTopicInfo;
import de.m0ep.canvas.model.UserInfo;
import de.m0ep.canvas.model.UserProfile;
import de.m0ep.canvas.utils.UriTemplate;

public class Canvas {

    private static final String SERVICE_PATH = "/api/v1";

    public static Range<Integer> HTTP_SUCCESSFULL_CODE_RANGE = Range
	    .closedOpen(200, 300);

    private long accountId;
    private String accessToken;
    private String baseUri;
    private HttpClient httpClient;

    /* package */Gson gson;

    private String rootUri;

    /**
     * Constructor to create a new {@link Canvas}
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
    public Canvas(final String uri, final String accesstoken,
	    final long accountId) {
	this.rootUri = Preconditions.checkNotNull(uri,
		"Required parameter uri must be specified.");

	Preconditions.checkArgument(
		!uri.isEmpty(),
		"Required parameter uri may not be empty");

	if (this.rootUri.endsWith("/")) { // remove tailing backslash
	    this.rootUri = this.rootUri.substring(0, this.rootUri.length() - 1);
	}

	this.baseUri = this.rootUri + SERVICE_PATH;

	this.accessToken = Preconditions.checkNotNull(accesstoken,
		"Required parameter accesstoken must be specified.");
	Preconditions.checkArgument(
		!accesstoken.isEmpty(),
		"Required parameter accesstoken may not be empty");

	this.accountId = accountId;

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
    public String getRootUri() {
	return baseUri;
    }

    public String getBaseUri() {
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
    public Gson getGson() {
	return gson;
    }

    public HttpClient getHttpClient() {
	return httpClient;
    }

    public Pagination<DiscussionTopicInfo> listCourseDiscussionTopics(
	    final long id) throws CanvasException {

	return fetchPagination(
		"/courses/"
			+ id
			+ "/discussion_topics",
		DiscussionTopicInfo.class);
    }

    public Pagination<Course> listCourses() throws CanvasException {
	return new ListCourses(this)
		.setOauthToken(accessToken)
		.executePagination();
    }

    public Pagination<DiscussionTopicEntry> listDiscussionTopicEntries(
	    final long courseId, final long topicId) throws CanvasException {

	String uri = new UriTemplate("/courses/{}/discussion_topics/{topicId}")
		.set("courseId", courseId)
		.set("topicId", topicId)
		.toString();

	return fetchPagination(uri, DiscussionTopicEntry.class);
    }

    public DiscussionTopicEntry postDiscussionEntry(String message,
	    long courseId,
	    long topicId) throws CanvasException {
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
	    throw new CanvasException(
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
	    throw new CanvasException(
		    "Http error: " + status.getStatusCode());
	}
    }

    public DiscussionTopicEntry postReply(String message, long courseId,
	    long topicId,
	    long postId) throws CanvasException {
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
	    throw new CanvasException(
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
		    "Authorization error: Accesstoken is missing or " +
			    "invalid for this operation.");
	} else if (403 == statusCode) { // Forbidden
	    throw new AccessControlException("Forbidden access: " + data);
	} else {
	    throw new CanvasException(
		    "Http error: " + status.getStatusCode());
	}
    }

    public Pagination<UserInfo> listUsers() throws CanvasException {
	return fetchPagination(
		"/accounts/"
			+ accountId
			+ "/users",
		UserInfo.class);
    }

    public UserProfile getUserProfile(final long id) throws CanvasException {
	return fetchObject(
		"/users/" +
			id +
			"/profile",
		UserProfile.class);
    }

    public <T> T fetchObject(String uri, Class<T> type)
	    throws CanvasException {
	HttpResponse response = makeGetRequest(uri);

	String data = "";
	try {
	    data = EntityUtils.toString(response.getEntity());
	} catch (Throwable t) {
	    throw new CanvasException(
		    "Failed to read data from stream.", t);
	}

	StatusLine status = response.getStatusLine();
	int statusCode = status.getStatusCode();

	if (HTTP_SUCCESSFULL_CODE_RANGE.contains(statusCode)) {// OK
	    return gson.fromJson(data, type);
	} else if (401 == statusCode) { // Unauthorized
	    System.err.println(data);
	    throw new AuthorizationException(
		    "Authorization error: Accesstoken is missing or invalid.");
	} else if (403 == statusCode) { // Forbidden
	    throw new AccessControlException("Forbidden access: " + data);
	} else {
	    throw new CanvasException(
		    "Http error: " + status.getStatusCode());
	}
    }

    public <T> Pagination<T> fetchPagination(String uri, Class<T> type)
	    throws CanvasException {
	HttpResponse response = makeGetRequest(uri);

	String data = "";
	try {
	    data = EntityUtils.toString(response.getEntity());
	} catch (Throwable t) {
	    throw new CanvasException(
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
	    throw new CanvasException(
		    "Http error: " + status.getStatusCode());
	}
    }

    HttpResponse makeGetRequest(String uri, NameValuePair... params)
	    throws CanvasException {

	URIBuilder builder;
	try {
	    builder = new URIBuilder(uri);
	} catch (URISyntaxException e) {
	    throw new CanvasException("Invalid URI: " + uri);
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
	    throw new CanvasException("Invalid URI: " + uri);
	} catch (ClientProtocolException e) {
	    throw new CanvasException("Http protocol error.", e);
	} catch (IOException e) {
	    throw new NetworkException("Network error.", e);
	}
    }

    HttpResponse makePostRequest(String uri, NameValuePair... params)
	    throws CanvasException {

	try {
	    HttpPost request = createRequestObject(
		    HttpPost.class,
		    uri);

	    request.setEntity(new UrlEncodedFormEntity(
		    Arrays.asList(params), "UTF-8"));

	    return httpClient.execute(request);
	} catch (ClientProtocolException e) {
	    throw new CanvasException("Http protocol error.", e);
	} catch (IOException e) {
	    throw new NetworkException("Network error.", e);
	}
    }

    HttpResponse makePutRequest(String uri, NameValuePair... params)
	    throws CanvasException {

	try {
	    HttpPut request = createRequestObject(
		    HttpPut.class,
		    uri);

	    request.setEntity(new UrlEncodedFormEntity(
		    Arrays.asList(params), "UTF-8"));

	    return httpClient.execute(request);
	} catch (ClientProtocolException e) {
	    throw new CanvasException("Http protocol error.", e);
	} catch (IOException e) {
	    throw new NetworkException("Network error.", e);
	}
    }

    <T extends HttpRequestBase> T createRequestObject(Class<T> type,
	    String uri) throws CanvasException {
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
	    throw new CanvasException("Failed to create request", e);
	}
    }

    <T extends HttpRequestBase> T createRequestObject(Class<T> type,
	    URI uri) throws CanvasException {
	return createRequestObject(type, uri.toString());
    }

}
