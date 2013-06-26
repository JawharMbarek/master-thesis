package de.m0ep.canvas;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.util.EntityUtils;

import com.google.common.base.Objects;

import de.m0ep.canvas.exceptions.AccessControlException;
import de.m0ep.canvas.exceptions.AuthorizationException;
import de.m0ep.canvas.exceptions.CanvasException;
import de.m0ep.canvas.exceptions.NetworkException;

public abstract class CanvasRequest<T> {

    private Class<? extends HttpRequestBase> methodType;
    private String uri;
    private Canvas client;
    private String oauthToken;
    private HttpEntity content;
    private Class<T> responseType;

    public CanvasRequest(
	    final Canvas client,
	    final Class<? extends HttpRequestBase> methodType,
	    final String uri,
	    final HttpEntity content,
	    final Class<T> responseType) {
	super();
	this.client = client;
	this.uri = uri;
	this.methodType = methodType;
	this.responseType = responseType;
	this.content = content;

	if (!this.uri.startsWith(client.getBaseUri())) {
	    if (this.uri.startsWith("/")) {
		this.uri = client.getBaseUri() + this.uri;
	    } else {
		this.uri = client.getBaseUri() + "/" + this.uri;
	    }
	}
    }

    public String getOauthToken() {
	return oauthToken;
    }

    public CanvasRequest<T> setOauthToken(String oauthToken) {
	this.oauthToken = oauthToken;
	return this;
    }

    public HttpEntity getContent() {
	return content;
    }

    public CanvasRequest<T> setContent(HttpEntity content) {
	this.content = content;
	return this;
    }

    public String getUri() {
	return uri;
    }

    public CanvasRequest<T> setUri(String uri) {
	this.uri = uri;
	return this;
    }

    public HttpResponse executeUnparsed() throws CanvasException {

	HttpRequestBase request = null;
	try {
	    request = methodType.newInstance();
	} catch (InstantiationException | IllegalAccessException e) {
	    throw new CanvasException(
		    "Failed to create requeset method object.",
		    e);
	}

	try {
	    request.setURI(new URI(uri));
	} catch (URISyntaxException e) {
	    throw new CanvasException("Invalid uri", e);
	}

	if (null != oauthToken) {
	    request.addHeader("Authorization", "Bearer " + oauthToken);
	}

	// Set the content, if the request method can handle it
	if (request instanceof HttpEntityEnclosingRequest) {
	    if (null != content) {
		((HttpEntityEnclosingRequest) request).setEntity(content);
	    }
	}

	try {
	    return client.getHttpClient().execute(request);
	} catch (IOException e) {
	    throw new NetworkException("Failed to execute "
		    + methodType.getSimpleName() + " request.", e);
	}
    }

    public Pagination<T> executePagination() throws CanvasException {
	HttpResponse response = executeUnparsed();

	String content = null;
	try {
	    content = EntityUtils.toString(response.getEntity());
	} catch (ParseException | IOException e) {
	    throw new CanvasException("Failed to read data from stream.", e);
	}

	isHttpStatusOkOrThrow(response.getStatusLine(), content);

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

	return new Pagination<T>(client, content, nextUri, responseType);
    }

    public T execute() throws CanvasException {
	HttpResponse response = executeUnparsed();

	String content = null;
	try {
	    content = EntityUtils.toString(response.getEntity());
	} catch (ParseException | IOException e) {
	    throw new CanvasException("Failed to read data from stream.", e);
	}

	isHttpStatusOkOrThrow(response.getStatusLine(), content);

	if (Void.class.equals(responseType)) {
	    return null;
	}

	return client.getGson().fromJson(content, responseType);
    }

    private void isHttpStatusOkOrThrow(StatusLine statusLine, String content)
	    throws CanvasException {
	int statusCode = statusLine.getStatusCode();

	if (200 <= statusCode && 300 > statusCode) {
	    return;
	} else if (401 == statusCode) { // Unauthorized
	    throw new AuthorizationException(
		    "Authorization error, check your accesstoken.");
	} else if (403 == statusCode) { // Forbidden
	    throw new AccessControlException("Forbidden access");
	} else {
	    throw new CanvasException("Http error: " + statusCode);
	}
    }

    @Override
    public int hashCode() {
	return Objects.hashCode(
		client,
		methodType,
		uri,
		oauthToken,
		content,
		responseType);
    }

    @Override
    public boolean equals(Object obj) {
	if (null == obj) {
	    return false;
	}

	if (this.getClass() != obj.getClass()) {
	    return false;
	}

	CanvasRequest<?> other = (CanvasRequest<?>) obj;

	if (!Objects.equal(this.methodType, other.methodType)) {
	    return false;
	}

	if (!Objects.equal(this.client, other.client)) {
	    return false;
	}

	if (!Objects.equal(this.uri, other.uri)) {
	    return false;
	}

	if (!Objects.equal(this.content, other.content)) {
	    return false;
	}

	if (!Objects.equal(this.responseType, other.responseType)) {
	    return false;
	}

	return true;
    }

    @Override
    public String toString() {
	return Objects.toStringHelper(this)
		.add("method", methodType.getSimpleName())
		.add("uri", uri)
		.add("has_accesstoken", null != oauthToken)
		.add("has_content", null != content)
		.add("response_type", responseType.getSimpleName())
		.toString();
    }

}
