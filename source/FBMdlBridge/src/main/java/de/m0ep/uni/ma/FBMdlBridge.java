package de.m0ep.uni.ma;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.m0ep.uni.ma.socc.DefaultSIOCModel;
import de.m0ep.uni.ma.socc.SIOCModel;
import de.m0ep.uni.ma.socc.connectors.Connector;
import de.m0ep.uni.ma.socc.connectors.FacebookConnector;
import de.m0ep.uni.ma.socc.connectors.MoodleConnector;

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
        log.info( "{}", new Date() );

        SIOCModel siocModel = new DefaultSIOCModel();
        List<Connector> connectors = new ArrayList<Connector>();
        connectors.add( new FacebookConnector() );
        connectors.add( new MoodleConnector() );

        for ( Connector connector : connectors ) {
            connector.init( siocModel, config );
            ConnectorView connectorView = new ConnectorView( connector );
            connectorView.setVisible( true );
        }

        // Connector connector = new MoodleConnector();
        // // Connector connector = new FacebookConnector();
        // ConnectorView connectorView = new ConnectorView( connector );
        // connectorView.setVisible( true );

        /*
         * for ( Connector connector : connectors ) { try { connector.init(
         * siocModel, config ); System.out.println(
         * connector.getUserFriendlyName() ); System.out.println(
         * "======================" ); System.out.println( "Forums" );
         * System.out.println( "======================" ); for ( Forum forum :
         * connector.getForums() ) { System.out.println(
         * forum.getResource().toString() ); System.out.println(
         * forum.getAllSIOCName_as().firstValue() );
         * 
         * if( forum.hasDCTermsDescription() ) System.out.println(
         * forum.getAllDCTermsDescription_as() .firstValue() );
         * 
         * if( forum.hasDCTermsModified() ) System.out.println(
         * forum.getAllDCTermsModified_as() .firstValue() );
         * 
         * List<Thread> threads = connector.getThreads( forum ); if(
         * !threads.isEmpty() ) { System.out.println( "Threads" ); for ( Thread
         * thread : threads ) { System.out.println( "\t" +
         * thread.getAllSIOCName_as().firstValue() ); System.out .println(
         * "\t\tId: " + thread.getAllSIOCId_as().firstValue() );
         * System.out.println( "\t\tLast Item Date: " +
         * thread.getAllSIOCLastitemdate_as() .firstValue() ); System.out
         * .println( "\t\tParent: " + thread.getAllSIOCParent_as() .firstValue()
         * ); } }
         * 
         * System.out.println( "======================" ); }
         * 
         * System.out.println( "UserAccount" ); System.out.println(
         * "======================" ); UserAccount user = connector.getUser();
         * 
         * System.out.println( user.getAllFOAFAccountname_as() .firstValue() );
         * System.out.println( user.getAllFOAFName_as().firstValue() ); } catch
         * ( Throwable t ) { t.printStackTrace(); log.warn( "{}",
         * t.getLocalizedMessage() ); } }
         */
    }
}
