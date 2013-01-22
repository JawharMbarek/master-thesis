package de.m0ep.uni.ma;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FBMdlBridge {
    private static final Logger log = LoggerFactory.getLogger( FBMdlBridge.class );

    public static Properties config;

    static {
        config = new Properties();
        try {
            config.load( ClassLoader
                    .getSystemResourceAsStream( "loacl.properties" ) );
        } catch ( IOException e ) {
            log.warn( "failed to load config", e );
        }
    }

    /**
     * @param args
     */
    public static void main( String[] args ) {
        // TODO Auto-generated method stub

    }
}
