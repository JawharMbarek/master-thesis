package de.m0ep.uni.ma.plugin.utils;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.base.Preconditions;

public class ClassPathUtils {
    public static final Logger LOG = Logger.getLogger( ClassPathUtils.class
                                           .getName() );

    public static void addDirectoryToClasspath( final File dir ) {
        Preconditions.checkNotNull( dir );
        Preconditions.checkArgument( dir.exists() );
        Preconditions.checkArgument( dir.isDirectory() );
        Preconditions.checkArgument( dir.canRead() );
        
        if( dir.exists() ) {
            File[] files = dir.listFiles();
 
            for ( File file : files ) {
                if( file.isFile() && file.getName().endsWith( ".jar" ) ) {
                    LOG.info( "add " + file + " to classpath" );
                    try {
                        addUrl( file.toURI().toURL() );
                    } catch ( MalformedURLException e ) {
                        LOG.log( Level.WARNING, "Invalid URL", e );
                    }
                }
            }
        }
    }

    public static void addUrl( URL newUrl ) {
        URLClassLoader sysLoader = (URLClassLoader) ClassLoader
                .getSystemClassLoader();
        URL[] urls = sysLoader.getURLs();

        for ( URL url : urls ) {
            if( url.toString().equalsIgnoreCase( newUrl.toString() ) ) {
                LOG.info( newUrl + " already in CLASSPATH" );
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
            LOG.log( Level.WARNING,
                    "failed to add " + newUrl + " to CLASSPATH", e );
        }
    }
}
