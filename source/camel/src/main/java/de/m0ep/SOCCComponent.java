package de.m0ep;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;

public class SOCCComponent extends DefaultComponent {

    String accessToken;

    @Override
    protected Endpoint createEndpoint( String uri, String remaining, Map<String, Object> parameters ) throws Exception {
        System.out.println( uri + " " + remaining );
        System.out.println( "parameters:" );
        for ( Entry<String, Object> param : parameters.entrySet() )
            System.out.println( "  " + param.getKey() + " = "
                    + param.getValue() );

        SOCCConfiguartion config = new SOCCConfiguartion();
        setProperties( config, parameters );

        System.out.println( parameters.size() );

        return new SOCCEndpoint();
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public void setAccessToken( String accessToken ) {
        this.accessToken = accessToken;
    }
}
