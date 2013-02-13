package de.m0ep.uni.ma.plugin;


/**
 * Superinterface that every plugin have to implement
 * 
 * 
 * @author Florian Müller
 */
public interface Plugin {
    /**
     * Retruns a unique name for this plugin
     * 
     * @return Name of the Plugin
     */
    public String getName();

    /**
     * Called after the pluging has been loaded
     */
    public void onLoad();

    /**
     * Called before the plugin will be unloaded
     */
    public void onUnload();
}
