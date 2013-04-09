package de.m0ep.socc.connectors.google.youtube;

public final class YoutubeV2Constants {
    static final String COMMENT_ID_SEPERATOR = "/comments/";

    static final String GDATA_FEED_BASE = "http://gdata.youtube.com/feeds/api/";

    static final String FEED_UPLOADS = GDATA_FEED_BASE + "users/%s/uploads";
    static final String FEED_PLAYLISTS = GDATA_FEED_BASE + "users/%s/playlists";
    static final String FEED_PLAYLIST = GDATA_FEED_BASE + "playlists/%s";
    static final String FEED_COMMENTS = GDATA_FEED_BASE + "videos/%s/comments";
    static final String ENTRY_PLAYLIST = GDATA_FEED_BASE
	    + "users/%s/playlists/%s";
    static final String ENTRY_VIDEO = GDATA_FEED_BASE + "videos/%s";
    static final String ENTRY_COMMENT = GDATA_FEED_BASE
	    + "videos/%s/comments/%s";
    static final String ENTRY_USER = GDATA_FEED_BASE + "users/%s";

    static final String ID_FOURM_UPLOADS = "uploads";
    static final String ID_FORUM_PLAYLISTS = "playlists";
}
