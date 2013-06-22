package de.m0ep.canvaslms;

import java.io.IOException;
import java.util.Date;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.m0ep.canvaslms.exceptions.AuthenticationException;
import de.m0ep.canvaslms.exceptions.CanvasLMSException;
import de.m0ep.canvaslms.exceptions.NetworkException;
import de.m0ep.canvaslms.gson.ISO8601TypeAdapter;
import de.m0ep.canvaslms.model.DiscussionTopicResponse;

public class CanvasLMSClient {
    private String accessToken;
    private String url;

    private Gson gson;

    /**
     * Constructor to create a new {@link CanvasLMSClient}
     * 
     * @param url
     *            The url to a CanvasLMS instance.
     * 
     * @param accesstoken
     *            An CanvasLMS accesstoken
     * 
     * @throws NullPointerException
     *             Thrown if url or accesstoken are null.
     * @throws IllegalArgumentException
     *             Thrown if url or accesstoken are empty.
     */
    public CanvasLMSClient(final String url, final String accesstoken) {
	this.url = Preconditions.checkNotNull(
		url,
		"Url can not be null.");
	Preconditions.checkArgument(
		!url.isEmpty(),
		"Url can not be empty.");

	if (!url.endsWith("/")) {
	    this.url = this.url + "/";
	}

	this.accessToken = Preconditions.checkNotNull(
		accesstoken,
		"Token can not be null.");
	Preconditions.checkArgument(
		!accesstoken.isEmpty(),
		"Token can not be empty.");

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
    public String getUrl() {
	return url;
    }

    /**
     * Returns the provided accesstoken.
     * 
     * @return A {@link String} containing the accesstoken.
     */
    public String getAccessToken() {
	return accessToken;
    }

    public DiscussionTopicResponse[] listCourseDiscussionTopics(
	    final long id) throws CanvasLMSException {
	HttpClient client = new DefaultHttpClient();
	HttpGet request = new HttpGet(
		url
			+ "/api/v1/courses/"
			+ id
			+ "/discussion_topics");
	request.addHeader("Authorization", "Bearer " + accessToken);
	String content = "{}";
	String next = "";

	try {
	    HttpResponse response = client.execute(request);
	    StatusLine status = response.getStatusLine();

	    if (200 == status.getStatusCode()) {
		Header[] links = response.getHeaders("Link");

		for (Header link : links) {
		    String value = link.getValue();
		    if (value.contains("rel=\"next\"")) {
			String[] split = value.split(";");

			next = split[0].trim();
			next = next.substring(1, next.length() - 1);
		    }
		}

		content = EntityUtils.toString(response.getEntity());
		return gson.fromJson(content, DiscussionTopicResponse[].class);
	    } else if (401 == status.getStatusCode()) {
		throw new AuthenticationException("Accesstoken is invalid");
	    } else {
		throw new CanvasLMSException(
			"Http error " + status.getStatusCode());
	    }

	} catch (ClientProtocolException e) {
	    throw new CanvasLMSException("Http protocol error occured", e);
	} catch (IOException e) {
	    throw new NetworkException("Network error", e);
	}
    }
}
