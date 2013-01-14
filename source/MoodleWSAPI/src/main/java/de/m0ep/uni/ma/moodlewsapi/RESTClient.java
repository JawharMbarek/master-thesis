package de.m0ep.uni.ma.moodlewsapi;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;

import com.google.common.base.Preconditions;
import com.google.common.io.CharStreams;

import de.m0ep.uni.ma.moodlewsapi.exception.RESTException;

public class RESTClient implements Closeable {
    public HttpClient httpClient;

    public void open() {
        httpClient = new DefaultHttpClient();
    }

    public void close() throws IOException {
        if( null != httpClient )
            HttpClientUtils.closeQuietly( httpClient );
    }

    public String doGETRequest( final String url, final HttpParams params )
            throws RESTException {
        Preconditions.checkNotNull( url, "Url can not be null" );
        Preconditions.checkState( null != httpClient, "Client not open" );

        String result = null;
        HttpGet request = new HttpGet( url );
        
        if( null != params )
            request.setParams( params );

        try {
            HttpResponse response = httpClient.execute( request );
            StatusLine status = response.getStatusLine();
            
            if( 200 != status.getStatusCode() ) {
                throw new RESTException( "Failed with HTTP error " + status.getStatusCode() );
            }
            
            result = CharStreams.toString( new InputStreamReader( response
                    .getEntity().getContent() ) );

            HttpClientUtils.closeQuietly( response );
        } catch ( Throwable t ) {
            throw new RESTException( t );
        }

        return result;
    }

    public String doPostRequest( final String url, final HttpParams params )
            throws RESTException {
        Preconditions.checkNotNull( url, "Url can not be null" );
        Preconditions.checkState( null != httpClient, "Client not open" );

        String result = null;
        HttpPost request = new HttpPost( url );

        if( null != params )
            request.setParams( params );

        try {
            HttpResponse response = httpClient.execute( request );
            StatusLine status = response.getStatusLine();

            if( 200 != status.getStatusCode() ) {
                throw new RESTException( "Failed with HTTP error "
                        + status.getStatusCode() );
            }

            result = CharStreams.toString( new InputStreamReader( response
                    .getEntity().getContent() ) );

            HttpClientUtils.closeQuietly( response );
        } catch ( Throwable t ) {
            throw new RESTException( t );
        }

        return result;
    }
}
