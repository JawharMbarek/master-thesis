package de.m0ep.canvaslms.model;

import java.util.Date;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

public class DiscussionTopicInfo {

    public static class Author {
	private Long id;

	@SerializedName("avatar_image_url")
	private String avatarIamgeUrl;

	@SerializedName("display_name")
	private String displayName;

	@SerializedName("html_url")
	private String htmlUrl;

	/**
	 * @return the id
	 */
	public Long getId() {
	    return id;
	}

	/**
	 * @return the avatarIamgeUrl
	 */
	public String getAvatarIamgeUrl() {
	    return avatarIamgeUrl;
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
	    return displayName;
	}

	/**
	 * @return the htmlUrl
	 */
	public String getHtmlUrl() {
	    return htmlUrl;
	}
    }

    public static class LockInfo {
	@SerializedName("asset_string")
	private String assetString;

	@SerializedName("unlock_at")
	private Date unlockAt;

	@SerializedName("lock_at")
	private Date lockAt;

	@SerializedName("context_module")
	private JsonElement context_module;

	/**
	 * @return the assetString
	 */
	public String getAssetString() {
	    return assetString;
	}

	/**
	 * @return the unlockAt
	 */
	public Date getUnlockAt() {
	    return unlockAt;
	}

	/**
	 * @return the lockAt
	 */
	public Date getLockAt() {
	    return lockAt;
	}

	/**
	 * @return the context_module
	 */
	public JsonElement getContext_module() {
	    return context_module;
	}
    }

    private Long id;

    private Author author;

    private String title;

    private String message;

    @SerializedName("html_url")
    private String htmlURL;

    @SerializedName("posted_at")
    private Date postedAt;

    @SerializedName("last_reply_at")
    private Date lastReplyAt;

    @SerializedName("require_initial_post")
    private boolean requireInitialPost;

    @SerializedName("discussion_subentry_count")
    private Integer discussionSubentryCount;

    @SerializedName("read_state")
    private String readState;

    @SerializedName("unread_count")
    private Integer unreadCount;

    @SerializedName("assignment_id")
    private Long assignmentId;

    @SerializedName("delayed_post_at")
    private Date delayedPostAt;

    @SerializedName("lock_at")
    private Date lockedAt;

    private boolean locked;

    @SerializedName("locked_for_user")
    private boolean lockedForUser;

    @SerializedName("lock_explanation")
    private String lockExplanation;

    @SerializedName("user_name")
    private String userName;

    @SerializedName("topic_children")
    private Long[] topicChildren;

    @SerializedName("root_topic_id")
    private Long rootTopicId;

    @SerializedName("podcast_has_student_posts")
    private boolean podcastHasStudentPosts;

    @SerializedName("podcast_url")
    private String podcastURL;

    @SerializedName("discussion_type")
    private String discussionType;

    private Attachment[] attachments;

    private Map<String, Boolean> permissions;

    /**
     * @return the id
     */
    public Long getId() {
	return id;
    }

    /**
     * @return the author
     */
    public Author getAuthor() {
	return author;
    }

    /**
     * @return the title
     */
    public String getTitle() {
	return title;
    }

    /**
     * @return the message
     */
    public String getMessage() {
	return message;
    }

    /**
     * @return the htmlURL
     */
    public String getHtmlURL() {
	return htmlURL;
    }

    /**
     * @return the postedAt
     */
    public Date getPostedAt() {
	return postedAt;
    }

    /**
     * @return the lastReplyAt
     */
    public Date getLastReplyAt() {
	return lastReplyAt;
    }

    /**
     * @return the requireInitialPost
     */
    public boolean requireInitialPost() {
	return requireInitialPost;
    }

    /**
     * @return the discussionSubentryCount
     */
    public Integer getDiscussionSubentryCount() {
	return discussionSubentryCount;
    }

    /**
     * @return the readState
     */
    public String getReadState() {
	return readState;
    }

    /**
     * @return the unreadCount
     */
    public Integer getUnreadCount() {
	return unreadCount;
    }

    /**
     * @return the assignmentId
     */
    public Long getAssignmentId() {
	return assignmentId;
    }

    /**
     * @return the delayedPostAt
     */
    public Date getDelayedPostAt() {
	return delayedPostAt;
    }

    /**
     * @return the lockedAt
     */
    public Date getLockedAt() {
	return lockedAt;
    }

    /**
     * @return the lockedForUser
     */
    public boolean isLockedForUser() {
	return lockedForUser;
    }

    /**
     * @return the locked
     */
    public boolean isLocked() {
	return locked;
    }

    /**
     * 
     * @return the lockExplanation
     */
    public String getLockExplanation() {
	return lockExplanation;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
	return userName;
    }

    /**
     * @return the topicChildren
     */
    public Long[] getTopicChildren() {
	return topicChildren;
    }

    /**
     * @return the rootTopicId
     */
    public Long getRootTopicId() {
	return rootTopicId;
    }

    /**
     * @return the podcastURL
     */
    public String getPodcastURL() {
	return podcastURL;
    }

    /**
     * @return the discussionType
     */
    public String getDiscussionType() {
	return discussionType;
    }

    /**
     * @return the attachments
     */
    public Attachment[] getAttachments() {
	return attachments;
    }

    /**
     * @return the permissions
     */
    public Map<String, Boolean> getPermissions() {
	return permissions;
    }

    /**
     * @return the podcastHasStudentPosts
     */
    public boolean isPodcastHasStudentPosts() {
	return podcastHasStudentPosts;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + (int) (id ^ (id >>> 32));
	return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;

	if (obj == null)
	    return false;

	if (getClass() != obj.getClass())
	    return false;

	DiscussionTopicInfo other = (DiscussionTopicInfo) obj;
	if (id != other.id)
	    return false;
	return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "DiscussionTopicResponse [id=" + id + ", title=" + title
		+ ", message=" + message + "]";
    }

}
