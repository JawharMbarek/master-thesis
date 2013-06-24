package de.m0ep.canvaslms.model;

import com.google.gson.annotations.SerializedName;

public class Attachment {
    @SerializedName("content-type")
    private String contentType;

    private String url;

    private String filename;

    @SerializedName("display_name")
    private String displayName;

    /**
     * @return the contentType
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @return the filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }
}