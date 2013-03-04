package de.m0ep;

import org.apache.camel.Consumer;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultConsumer;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.impl.DefaultProducer;

public class SOCCEndpoint extends DefaultEndpoint {

    @Override
    public Producer createProducer() throws Exception {
        return new DefaultProducer( this ) {

            @Override
            public void process( Exchange exchange ) throws Exception {
                
                System.out.println( "got message: "
                        + exchange.getIn().getBody() );
            }
        };
    }

    @Override
    public Consumer createConsumer( Processor processor ) throws Exception {
        return new DefaultConsumer( this, processor );
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    protected String createEndpointUri() {
        // TODO Auto-generated method stub
        return "facebook";
    }

    void setAccesstoken( String token ) {
        System.out.println( "set token to " + token );
    }

}
