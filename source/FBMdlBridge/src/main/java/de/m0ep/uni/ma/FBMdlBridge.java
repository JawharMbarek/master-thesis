package de.m0ep.uni.ma;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.m0ep.uni.ma.rdf.sioc.Forum;
import de.m0ep.uni.ma.rdf.sioc.UserAccount;
import de.m0ep.uni.ma.socc.DefaultSIOCModel;
import de.m0ep.uni.ma.socc.SIOCModel;
import de.m0ep.uni.ma.socc.connectors.FacebookConnector;

public class FBMdlBridge {
    private static final Logger log = LoggerFactory.getLogger( FBMdlBridge.class );

    public static Properties config;

    static {
        config = new Properties();
        try {
            config.load( ClassLoader
                    .getSystemResourceAsStream( "local.properties" ) );
        } catch ( IOException e ) {
            log.warn( "failed to load config", e );
        }
    }

    /**
     * @param args
     */
    public static void main( String[] args ) {
        SIOCModel siocModel = new DefaultSIOCModel();
        FacebookConnector fb = new FacebookConnector();
        fb.init( siocModel, config );

        for ( Forum forum : fb.getForums() ) {
            System.out.println( forum.getResource().toString() );
            System.out.println( forum.getAllSIOCName_as().firstValue() );

            if( forum.hasDCTermsDescription() )
                System.out.println( forum.getAllDCTermsDescription_as()
                        .firstValue() );

            if( forum.hasDCTermsModified() )
                System.out.println( forum.getAllDCTermsModified_as()
                        .firstValue() );
            System.out.println( "======================" );
        }

        UserAccount user = fb.getUser();

        System.out.println( user.getAllFOAFAccountname_as().firstValue() );
        System.out.println( user.getAllFOAFName_as().firstValue() );


    }
}
