package de.m0ep.uni.ma.socc.connectors;

public interface URLCreator {
    public String createForumURL( String id );

    public String createThreadURL( String id );

    public String createPostURL( String id );

    public String createUserAccountURL( String id );
}
