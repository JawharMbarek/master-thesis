package de.m0ep.socc.connectors.google.youtube;

public final class YoutubeV2Constants {
    static final String COMMENT_ID_SEPERATOR = "/comments/";

    static final String GDATA_FEED_BASE = "http://gdata.youtube.com/feeds/api/";

    static final String FMT_FEED_UPLOADS = GDATA_FEED_BASE + "users/%s/uploads";
    static final String FMT_FEED_PLAYLISTS = GDATA_FEED_BASE
	    + "users/%s/playlists";
    static final String FMT_FEED_PLAYLIST = GDATA_FEED_BASE + "playlists/%s";
    static final String FMT_FEED_COMMENTS = GDATA_FEED_BASE
	    + "videos/%s/comments";
    static final String FMT_ENTRY_PLAYLIST = GDATA_FEED_BASE
	    + "users/%s/playlists/%s";
    static final String FMT_ENTRY_VIDEO = GDATA_FEED_BASE + "videos/%s";
    static final String FMT_ENTRY_COMMENT = GDATA_FEED_BASE
	    + "videos/%s/comments/%s";
    static final String FMT_ENTRY_USER = GDATA_FEED_BASE + "users/%s";

    static final String FMT_ID_FORUM_UPLOADS = "%s/uploads";
    static final String FMT_ID_FORUM_PLAYLISTS = "%s/playlists";
    static final String FMT_ID_COMMENT = "%s" + COMMENT_ID_SEPERATOR + "%s";
}
