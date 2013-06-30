
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

import de.m0ep.canvas.exceptions.AccessControlException;
import de.m0ep.canvas.exceptions.AuthorizationException;
import de.m0ep.canvas.exceptions.CanvasException;
import de.m0ep.canvas.exceptions.NetworkException;

public abstract class CanvasRequest<T> {
    private static final Logger LOG = LoggerFactory.getLogger(CanvasRequest.class);

    private Class<? extends HttpRequestBase> methodType;
    private String uri;
    private CanvasClient client;
    private String oauthToken;
    private HttpEntity content;
    private Class<T> responseType;

    public CanvasRequest(
            final CanvasClient client,
            final Class<? extends HttpRequestBase> methodType,
            final String uri,
            final Class<T> responseType) {
        super();
        setClient(client);
        setMethodType(methodType);
        setUri(uri);
        setResponseType(responseType);

        if (!getUri().startsWith(client.getBaseUri())) {
            if (getUri().startsWith("/")) {
                setUri(getClient().getBaseUri() + getUri());
            } else {
                setUri(getClient().getBaseUri() + "/" + getUri());
            }
        }
    }

    public CanvasClient getClient() {
        return client;
    }

    public CanvasRequest<T> setClient(CanvasClient client) {
        this.client = Preconditions.checkNotNull(
                client,
                "Required parameter client must be specified.");
        return this;
    }

    public Class<? extends HttpRequestBase> getMethodType() {
        return methodType;
    }

    public CanvasRequest<T> setMethodType(Class<? extends HttpRequestBase> methodType) {
        this.methodType = Preconditions.checkNotNull(
                methodType,
                "Required parameter methodType must be specified.");
        return this;
    }

    public String getUri() {
        return uri;
    }

    public CanvasRequest<T> setUri(String uri) {
        this.uri = Preconditions.checkNotNull(uri, "Required parameter uri must be specified.");
        return this;
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

    public Class<T> getResponseType() {
        return responseType;
    }

    public CanvasRequest<T> setResponseType(Class<T> responseType) {
        this.responseType = Preconditions.checkNotNull(
                responseType,
                "Required parameter responseType must be specified.");
        return this;
    }

    private HttpResponse executeUnparsed() throws CanvasException {
        HttpRequestBase request = null;
        try {
            request = getMethodType().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new CanvasException(
                    "Failed to create requeset method object.",
                    e);
        }

        try {
            request.setURI(new URI(getUri()));
        } catch (URISyntaxException e) {
            throw new CanvasException("Invalid uri", e);
        }

        if (null != getOauthToken()) {
            request.addHeader("Authorization", "Bearer " + getOauthToken());
        }

        if (null != getContent()) {
            // Set the content, if the request method can handle it
            if (request instanceof HttpEntityEnclosingRequest) {
                ((HttpEntityEnclosingRequest) request).setEntity(getContent());
            }
        }

        LOG.info(
                "Send '{}' request to '{}'.",
                getMethodType().getSimpleName(),
                getUri(),
                getResponseType().getSimpleName());

        try {
            return getClient().getHttpClient().execute(request);
        } catch (IOException e) {
            throw new NetworkException("Failed to execute "
                    + getMethodType().getSimpleName() + " request.", e);
        }
    }

    public Pagination<T> executePagination() throws CanvasException {
        HttpResponse response = executeUnparsed();

        String responseContent = null;
        if (null != response.getEntity()) {
            try {
                responseContent = EntityUtils.toString(response.getEntity());
            } catch (ParseException | IOException e) {
                throw new CanvasException("Failed to read data from stream.", e);
            }
        }

        if (LOG.isDebugEnabled() && null != response.getEntity()) {
            LOG.debug("Reseived response from '{}', status='{}' content='{}'.",
                    getUri(),
                    response.getStatusLine(),
                    responseContent);
        } else {
            LOG.info("Reseived response from '{}', status='{}'.",
                    getUri(),
                    response.getStatusLine());
        }

        isHttpStatusOkOrThrow(response.getStatusLine(), responseContent);

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

        return new Pagination<T>(getClient(), responseContent, nextUri, getResponseType());
    }

    public T execute() throws CanvasException {
        HttpResponse response = executeUnparsed();

        String responseContent = null;
        if (null != response.getEntity()) {
            try {
                responseContent = EntityUtils.toString(response.getEntity());
            } catch (ParseException | IOException e) {
                throw new CanvasException("Failed to read data from stream.", e);
            }
        }

        if (LOG.isDebugEnabled() && null != response.getEntity()) {
            LOG.debug("Reseived response from '{}', status='{}' content='{}'.",
                    getUri(),
                    response.getStatusLine(),
                    responseContent);
        } else {
            LOG.info("Reseived response from '{}', status='{}'.",
                    getUri(),
                    response.getStatusLine());
        }

        isHttpStatusOkOrThrow(response.getStatusLine(), responseContent);

        if (Void.class.equals(getResponseType())) {
            return null;
        }

        return getClient().getGson().fromJson(responseContent, getResponseType());
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