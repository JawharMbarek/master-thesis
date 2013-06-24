package de.m0ep.canvaslms.model;

import java.util.Collection;
import java.util.Date;

import com.google.gson.annotations.SerializedName;


public class DiscussionTopicEntry {
    private long id;

    @SerializedName("user_id")
    private long userId;

    @SerializedName("editor_id")
    private long editorId;

    @SerializedName("user_name")
    private String userName;

    private String message;

    @SerializedName("read_state")
    private String readState;

    @SerializedName("created_at")
    private Date createdAt;

    @SerializedName("updated_at")
    private Date updatedAt;

    private Attachment attachment;

    @SerializedName("recent_replies")
    private Collection<DiscussionTopicEntry> recentReplies;

    @SerializedName("has_more_replies")
    private boolean hasMoreReplies;

    private boolean deleted;

    /**
     * @return the id
     */
    public long getId() {
	return id;
    }

    /**
     * @return the userId
     */
    public long getUserId() {
	return userId;
    }

    /**
     * @return the editorId
     */
    public long getEditorId() {
	return editorId;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
	return userName;
    }

    /**
     * @return the message
     */
    public String getMessage() {
	return message;
    }

    /**
     * @return the readState
     */
    public String getReadState() {
	return readState;
    }

    /**
     * @return the createdAt
     */
    public Date getCreatedAt() {
	return createdAt;
    }

    /**
     * @return the updatedAt
     */
    public Date getUpdatedAt() {
	return updatedAt;
    }

    /**
     * @return the attachment
     */
    public Attachment getAttachment() {
	return attachment;
    }

    /**
     * @return the recentReplies
     */
    public Collection<DiscussionTopicEntry> getRecentReplies() {
	return recentReplies;
    }

    /**
     * @return the hasMoreReplies
     */
    public boolean hasMoreReplies() {
	return hasMoreReplies;
    }

    /**
     * @return the deleted
     */
    public boolean isDeleted() {
	return deleted;
    }
}