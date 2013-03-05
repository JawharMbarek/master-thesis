package de.m0ep.uni.ma.plugin;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.logging.Logger;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.m0ep.uni.ma.plugin.utils.ClassPathUtils;

/**
 * 
 * @author Florian Müller
 * 
 * @param <P>
 *            Superclass of a plugin
 */
public class PluginService<P extends Plugin> {
    public static final Logger           LOG                = Logger.getLogger( PluginService.class
                                                                    .getName() );

    private Class<P>         pluginClass;
    private Map<String, P>   plugins;

    /**
     * Contructor to create an empty {@link PluginService}.
     * 
     * @param pluginClass
     *            {@link Class} of the superclass of a plugin
     * 
     * @throws NullPointerException
     *             Thrown if <i>pluginClass</i> was null
     */
    public PluginService( Class<P> pluginClass ) {
        Preconditions.checkNotNull( pluginClass );

        this.pluginClass = pluginClass;
        this.plugins = new HashMap<String, P>();
    }

    /**
     * Load all *.jar's inside <i>pluginDir</i> to the classpath and
     * instantiates an object for each defined plugin.
     * 
     * @param pluginDir
     *            Directory where the plugins are stored
     * @param override
     *            Override already loaded plugins with the same name
     * 
     * @see ServiceLoader#load(Class)
     * 
     * @throws NullPointerException
     *             Thrown if <i>pluginDir</i> was null
     * @throws IllegalArgumentException
     *             Throw if <i>pluginDir</i> is not a directory or not readable
     */
    public void load( final File pluginDir, final boolean override ) {
        Preconditions.checkNotNull( pluginDir );
        Preconditions.checkArgument( pluginDir.isDirectory(), pluginDir
                + " is not a directory" );
        Preconditions.checkArgument( pluginDir.canRead(),
                "Can't read plugin directory" + pluginDir );

        ClassPathUtils.addDirectoryToClasspath( pluginDir );
        ServiceLoader<P> serviceLoader = ServiceLoader.load( pluginClass );

        for ( P plugin : serviceLoader ) {

            if( override || !plugins.containsKey( plugin.getName() ) ) {
                plugin.onLoad();
                plugins.put( plugin.getName(), plugin );
            }
        }
    }

    /**
     * Unload all load plugins
     * 
     * The onUnload() method will be called on each plugin
     */
    public void unload() {
        for ( P plugin : getPlugins() ) {
            plugin.onUnload();
        }

        plugins.clear();
    }

    /**
     * Get a specific plugin by his name
     * 
     * @param name
     *            Name of the Plugin
     * 
     * @return Plugin with this <i>name</i>
     * 
     * @throws IllegalArgumentException
     *             Thrown if no plugin with this name was found
     */
    public P getPlugin( final String name ) {
        Preconditions.checkArgument( plugins.containsKey( name ),
                "Can't find a plugin with the name: " + name );

        return plugins.get( name );
    }

    /**
     * Get all loaded plugins
     * 
     * @return {@link ImmutableList} of plugins
     */
    public Collection<P> getPlugins() {
        return ImmutableList.copyOf( plugins.values() );
    }

    /**
     * Get names of all loaded plugins
     * 
     * @return {@link ImmutableSet} of plugin names
     */
    public Set<String> getPluginNames() {
        return ImmutableSet.copyOf( plugins.keySet() );
    }
}
