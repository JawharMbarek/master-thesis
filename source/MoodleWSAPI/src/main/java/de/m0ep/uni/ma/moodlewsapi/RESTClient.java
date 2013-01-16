package de.m0ep.uni.ma.moodlewsapi;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.common.base.Preconditions;
import com.google.common.io.CharStreams;

import de.m0ep.uni.ma.moodlewsapi.exception.RESTException;

public class RESTClient implements Closeable {
    public static final Logger LOG = Logger.getLogger( RESTClient.class
                                           .getName() );

    public HttpClient httpClient;

    public void open() {
        httpClient = new DefaultHttpClient();
    }

    public void close() throws IOException {
        if( null != httpClient )
            HttpClientUtils.closeQuietly( httpClient );
    }

    public String doGETRequest( final String url,
            final Map<String, String> params )
            throws RESTException {
        Preconditions.checkNotNull( url, "Url can not be null" );
        Preconditions.checkState( null != httpClient, "Client not open" );

        try {
            URIBuilder uriBuilder = new URIBuilder( url );
            if( null != params ) {
                for ( Map.Entry<String, String> param : params.entrySet() ) {
                    uriBuilder.addParameter( param.getKey(), param.getValue() );
                }
            }

            HttpGet request = new HttpGet( uriBuilder.build() );
            HttpResponse response = httpClient.execute( request );
            StatusLine status = response.getStatusLine();
            
            if( 200 != status.getStatusCode() ) {
                HttpClientUtils.closeQuietly( response );
                throw new RESTException( "Failed with HTTP error " + status.getStatusCode() );
            }
            
            String result = CharStreams.toString( new InputStreamReader(
                    response
                    .getEntity().getContent() ) );

            HttpClientUtils.closeQuietly( response );
            return result;
        } catch ( Throwable t ) {
            throw new RESTException( t );
        }
    }

    public String doPostRequest( final String url,
            final Map<String, String> params )
            throws RESTException {
        Preconditions.checkNotNull( url, "Url can not be null" );
        Preconditions.checkState( null != httpClient, "Client not open" );

        try {
            URIBuilder uriBuilder = new URIBuilder( url );
            if( null != params ) {
                for ( Map.Entry<String, String> param : params.entrySet() ) {
                    uriBuilder.addParameter( param.getKey(), param.getValue() );
                }
            }

            HttpPost request = new HttpPost( uriBuilder.build() );
            HttpResponse response = httpClient.execute( request );
            StatusLine status = response.getStatusLine();

            if( 200 != status.getStatusCode() ) {
                HttpClientUtils.closeQuietly( response );
                throw new RESTException( "Failed with HTTP error "
                        + status.getStatusCode() );
            }

            String result = CharStreams.toString( new InputStreamReader(
                    response
                    .getEntity().getContent() ) );

            HttpClientUtils.closeQuietly( response );

            return result;

        } catch ( Throwable t ) {
            throw new RESTException( t );
        }
    }
}
