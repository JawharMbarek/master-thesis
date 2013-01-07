package de.m0ep.uni.ma;

public class PluginA implements Plugin {

    @Override
    public String getName() {
        return "plugin A";
    }

    @Override
    public void init() {
        System.out.println( "init Plugin " + getName() );
    }

}
