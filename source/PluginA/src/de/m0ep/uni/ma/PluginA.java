package de.m0ep.uni.ma;

import de.m0ep.uni.ma.plugin.Plugin;

public class PluginA implements Plugin {

    public String getName() {
        return "A";
    }

    @Override
    public void onLoad() {
        System.out.println( "loaded" );
    }

    @Override
    public void onUnload() {
        System.out.println( "unloaded" );
    }

}
