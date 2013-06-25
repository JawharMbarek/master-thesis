package de.m0ep.canvas.model;

import java.util.Collection;
import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class DiscussionTopicEntry {
    public static final String READ_STATE_READ = "read";
    public static final String READ_STATE_UNREAD = "unread";

    private long id;

    @SerializedName("parent_id")
    private long parentId;

    @SerializedName("user_id")
    private long userId;

    @SerializedName("editor_id")
    private long editorId;

    @SerializedName("user_name")
    private String userName;

    private String message;

    @SerializedName("read_state")
    private String readState;

    @SerializedName("forced_read_state")
    private boolean forcedReadState;

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

    public long getId() {
	return id;
    }

    public long getParentId() {
	return parentId;
    }

    public long getUserId() {
	return userId;
    }

    public long getEditorId() {
	return editorId;
    }

    public String getUserName() {
	return userName;
    }

    public String getMessage() {
	return message;
    }

    public String getReadState() {
	return readState;
    }

    public boolean hasForcedReadState() {
	return forcedReadState;
    }

    public Date getCreatedAt() {
	return createdAt;
    }

    public Date getUpdatedAt() {
	return updatedAt;
    }

    public Attachment getAttachment() {
	return attachment;
    }

    public Collection<DiscussionTopicEntry> getRecentReplies() {
	return recentReplies;
    }

    public boolean hasMoreReplies() {
	return hasMoreReplies;
    }

    public boolean isDeleted() {
	return deleted;
    }
}