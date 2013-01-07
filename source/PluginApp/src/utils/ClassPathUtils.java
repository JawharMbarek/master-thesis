package utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClassPathUtils {
    public static final Logger log = Logger.getLogger( ClassPathUtils.class
                                           .getName() );

    public static void addDirectoryToClasspath( File dir ) throws IOException {
        if( dir.exists() ) {
            File[] files = dir.listFiles();

            for ( File file : files ) {
                if( file.isFile() && file.getName().endsWith( ".jar" ) ) {
                    log.info( "add " + file + " to classpath" );
                    addUrl( file.toURI().toURL() );
                }
            }
        }

    }

    public static void addUrl( URL newUrl ) throws IOException {
        URLClassLoader sysLoader = (URLClassLoader) ClassLoader
                .getSystemClassLoader();
        URL[] urls = sysLoader.getURLs();

        for ( URL url : urls ) {
            if( url.toString().equalsIgnoreCase( newUrl.toString() ) ) {
                log.info( newUrl + " already in CLASSPATH" );
                return;
            }
        }

        Class<URLClassLoader> sysClass = URLClassLoader.class;
        Class<?>[] parameters = new Class<?>[] { URL.class };
        
        try {
            Method addUrl = sysClass.getDeclaredMethod( "addURL", parameters );
            addUrl.setAccessible( true );
            addUrl.invoke( sysLoader, newUrl );
        } catch ( Throwable e ) {
            log.log( Level.SEVERE, null, e );
            throw new IOException( "failed to add " + newUrl + " to CLASSPATH" );
        }
    }
}
