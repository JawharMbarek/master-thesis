package de.m0ep.uni.ma;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Logger;

import de.m0ep.uni.ma.plugin.Plugin;
import de.m0ep.uni.ma.plugin.PluginService;

public class PluginTest {
    public static final Logger LOG = Logger.getLogger( PluginTest.class
                                           .getName() );
    /**
     * @param args
     */
    public static void main( String[] args ) throws Throwable {
        PluginService<Plugin> pluginService = new PluginService<Plugin>(
                Plugin.class );


        pluginService.load(
 new File( ClassLoader
                .getSystemResource( "plugins/" ).toURI() ), false );

        System.out.println( "Available plugins:" );
        for ( Plugin p : pluginService.getPlugins() ) {
            System.out.println( "\t" + "class: " + p.getClass().getName()
                    + " | name: " + p.getName() );
        }

        URL[] urls = ( (URLClassLoader) ClassLoader.getSystemClassLoader() )
                .getURLs();


        pluginService.unload();
    }

}
