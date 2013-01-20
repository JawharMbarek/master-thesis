package de.m0ep.uni.ma;

import java.io.IOException;
import java.util.Properties;

import net.patrickpollet.moodlews_gson.core.LoginReturn;
import net.patrickpollet.moodlews_gson.core.Mdl_restserverBindingStub;

public class MoodleWASTest {
    public static void main( String[] args ) throws IOException {
        Properties config = new Properties();
        config.load( ClassLoader.getSystemResourceAsStream( "user.properties" ) );

        Mdl_restserverBindingStub moodle = new Mdl_restserverBindingStub(
                config.getProperty( "moodle.url" ), "json", true );

        LoginReturn login = moodle.login(
                config.getProperty( "moodle.username" ),
                config.getProperty( "moodle.password" ) );

        int id = moodle.get_my_id( login.getClient(), login.getSessionkey() );

        moodle.get_user_byid( login.getClient(),
                login.getSessionkey(), id );
        moodle.get_my_courses( login.getClient(), login.getSessionkey(),
                Integer.toString( id ), "asc" );
        moodle.get_all_forums( login.getClient(), login.getSessionkey(), "", "" );
        moodle.get_courses( login.getClient(), login.getSessionkey(), null, "" );

        moodle.logout( login.getClient(), login.getSessionkey() );
    }
}
