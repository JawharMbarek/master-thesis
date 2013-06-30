
package de.m0ep.canvas;

import java.util.Date;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.m0ep.canvas.gson.ISO8601TypeAdapter;

public class CanvasClient {
    private static final String SERVICE_PATH = "/api/v1";

    private String oauthToken;
    private HttpClient httpClient;

    private Gson gson;

    private String baseUri;
    private String rootUri;

    /**
     * Constructor to create a new {@link CanvasClient}
     * 
     * @param uri The uri to a CanvasLMS instance.
     * @param oauthtoken An CanvasLMS accesstoken
     * @throws NullPointerException Thrown if uri or accesstoken are null.
     * @throws IllegalArgumentException Thrown if uri or accesstoken are empty.
     */
    public CanvasClient(final String uri, final String oauthtoken) {
        this.rootUri = Preconditions.checkNotNull(uri,
                "Required parameter uri must be specified.");

        Preconditions.checkArgument(
                !uri.isEmpty(),
                "Required parameter uri may not be empty");

        if (this.rootUri.endsWith("/")) { // remove tailing backslash
            this.rootUri = this.rootUri.substring(0, this.rootUri.length() - 1);
        }

        this.baseUri = this.rootUri + SERVICE_PATH;

        this.oauthToken = Preconditions.checkNotNull(oauthtoken,
                "Required parameter accesstoken must be specified.");
        Preconditions.checkArgument(
                !oauthtoken.isEmpty(),
                "Required parameter accesstoken may not be empty");

        this.httpClient = new DefaultHttpClient();
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Date.class,
                        new ISO8601TypeAdapter().nullSafe())
                .create();
    }

    public String getRootUri() {
        return baseUri;
    }

    public String getBaseUri() {
        return baseUri;
    }

    public String getOAuthToken() {
        return oauthToken;
    }

    public Gson getGson() {
        return gson;
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public Courses courses() {
        return new Courses(this);
    }

}
