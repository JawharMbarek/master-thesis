package de.m0ep.uni.ma.moodlewsapi.model;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.gson.Gson;

public class ModelTests {

    public Gson gson = new Gson();

    @Test
    public void testLoginResultGson() {
        LoginResult lr = new LoginResult( 1, "afkjdlfslkfksbfjskahlkjfhsjkbfks" );

        String json = gson.toJson( lr );

        System.out.println( json );

        LoginResult lr2 = gson.fromJson( json, LoginResult.class );

        assertEquals( lr.getClient(), lr2.getClient() );
        assertEquals( lr.getSessionKey(), lr2.getSessionKey() );
    }

}
