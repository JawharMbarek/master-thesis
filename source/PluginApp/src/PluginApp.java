import java.io.File;
import java.io.IOException;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

import utils.ClassPathUtils;
import de.m0ep.uni.ma.Plugin;


public class PluginApp {
    public static final Logger log = Logger.getLogger( PluginApp.class
                                           .getName() );

    public static void main( String[] args ) {
        try {
            ClassPathUtils.addDirectoryToClasspath( new File( "plugins" ) );
        } catch ( IOException e ) {
            log.log( Level.SEVERE, null, e );
        }

        ServiceLoader<Plugin> serviceLoader = ServiceLoader.load( Plugin.class );

        for ( Plugin plugin : serviceLoader ) {
            plugin.init();
        }
    }
}
