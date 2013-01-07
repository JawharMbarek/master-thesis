package de.m0ep.uni.ma;

public class PluginB implements Plugin {

    @Override
    public String getName() {
        return "PluginB";
    }

    @Override
    public void init() {
        System.out.println( "Init Plugin " + getName() );
    }

}
