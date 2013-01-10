package de.m0ep.uni.ma.plugin.utils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.base.Preconditions;

public class FileClassLoader extends URLClassLoader {
    private static final Logger LOG = Logger.getLogger( FileClassLoader.class
                                            .getName() );

    /**
     * Contructor to create a new {@link FileClassLoader} with the
     * SystemClassLoader as the parent
     */
    public FileClassLoader() {
        super( new URL[] {}, ClassLoader.getSystemClassLoader() );
    }

    /**
     * Constructor to create a new {@link FileClassLoader}, add some urls and
     * with the SystemClassLoader as the parent
     * 
     * @param urls
     */
    public FileClassLoader( URL[] urls ) {
        super( urls, ClassLoader.getSystemClassLoader() );
    }

    /**
     * 
     * @param urls
     * @param parent
     */
    public FileClassLoader( URL[] urls, ClassLoader parent ) {
        super( urls, parent );
    }

    /**
     * 
     * @param urls
     * @param parent
     * @param factory
     */
    public FileClassLoader( URL[] urls, ClassLoader parent,
            URLStreamHandlerFactory factory ) {
        super( urls, parent, factory );
    }


    /**
     * Add all *.jar files inside the given directory to the classpath. The
     * directory will be traversed recursively.
     * 
     * @param dir
     *            Directory of *.jar's to add
     * 
     * @throws NullPointerException
     *             Thrown if <i>pluginDir</i> was null
     * @throws IllegalArgumentException
     *             Throw if <i>pluginDir</i> is not a directory or not readable
     */
    public void addDirectory( final File dir ) {
        Preconditions.checkNotNull( dir );
        Preconditions.checkArgument( dir.isDirectory(), dir
                + " is not a directory" );
        Preconditions.checkArgument( dir.canRead(), "Can't read " + dir );

        File[] files = dir.listFiles();

        for ( File file : files ) {
            try {
                if( file.getName().endsWith( ".jar" ) ) {
                    addURL( file.toURI().toURL() );
                }
            } catch ( MalformedURLException e ) {
                LOG.log( Level.WARNING, "Invalid URL", e );
            }
        }
    }

    /**
     * 
     * @param url
     *            {@link URL} of a resource to add it to the classpath
     * 
     * @throws NullPointerException
     *             Thrown if <i>url</i> was null.
     */
    public void addURL( final URL url ) {
        Preconditions.checkNotNull( url );

        if( !isURLInClasspath( url ) ) {
            super.addURL( url );
            LOG.info( "add " + url.toString() + " to classpath" );
        }
    }

    /**
     * Checks if a resource is already in the classpath
     * 
     * @param url
     *            {@link URL} to check
     * @return True if the <i>url</i> is not already in the classpath, false
     *         otherwise
     */
    private boolean isURLInClasspath( final URL url ) {
        URL[] classpath = getURLs();
        
        for ( URL u : classpath ) {
            if( u.toString().equalsIgnoreCase( url.toString() ) ) {
                return true;
            }
        }
        
        return false;
    }
}
