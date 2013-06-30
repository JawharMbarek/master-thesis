
package de.m0ep.canvas.model;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import com.google.common.base.Objects;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

public class DiscussionTopic {

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

        @Override
        public int hashCode() {
            return Objects.hashCode(id, avatarIamgeUrl, displayName, htmlUrl);
        }

        @Override
        public boolean equals(Object obj) {
            if (null == obj) {
                return true;
            }

            if (this == obj) {
                return true;
            }

            if (this.getClass() != obj.getClass()) {
                return false;
            }

            Author other = (Author) obj;

            return Objects.equal(this.id, other.id) &&
                    Objects.equal(this.avatarIamgeUrl, other.avatarIamgeUrl) &&
                    Objects.equal(this.displayName, other.displayName) &&
                    Objects.equal(this.htmlUrl, other.htmlUrl);
        }

        @Override
        public String toString() {
            return Objects.toStringHelper(this)
                    .add("id", id)
                    .add("avatarIamgeUrl", avatarIamgeUrl)
                    .add("displayName", displayName)
                    .add("htmlUrl", htmlUrl)
                    .toString();
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

        @Override
        public int hashCode() {
            return Objects.hashCode(assetString, unlockAt, lockAt, context_module);
        }

        @Override
        public boolean equals(Object obj) {
            if (null == obj) {
                return false;
            }

            if (this == obj) {
                return true;
            }

            if (this.getClass() != obj.getClass()) {
                return false;
            }

            LockInfo other = (LockInfo) obj;

            return Objects.equal(this.assetString, other.assetString) &&
                    Objects.equal(this.lockAt, other.lockAt) &&
                    Objects.equal(this.lockAt, other.lockAt) &&
                    Objects.equal(this.context_module, other.context_module);
        }

        @Override
        public String toString() {
            return Objects.toStringHelper(this)
                    .add("assetString", assetString)
                    .add("unlockAt", unlockAt)
                    .add("lockAt", lockAt)
                    .add("context_module", context_module)
                    .toString();
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

    @Override
    public int hashCode() {
        return Objects.hashCode(
                id,
                author,
                title,
                message,
                htmlURL,
                postedAt,
                lastReplyAt,
                requireInitialPost,
                discussionSubentryCount,
                readState,
                unreadCount,
                assignmentId,
                delayedPostAt,
                lockedAt,
                locked,
                lockedForUser,
                lockExplanation,
                userName,
                Arrays.hashCode(topicChildren),
                podcastHasStudentPosts,
                podcastURL,
                discussionType,
                Arrays.hashCode(attachments),
                permissions);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        DiscussionTopic other = (DiscussionTopic) obj;

        return Objects.equal(this.id, other.id) &&
                Objects.equal(this.author, other.author) &&
                Objects.equal(this.title, other.title) &&
                Objects.equal(this.message, other.message) &&
                Objects.equal(this.htmlURL, other.htmlURL) &&
                Objects.equal(this.postedAt, other.postedAt) &&
                Objects.equal(this.lastReplyAt, other.lastReplyAt) &&
                Objects.equal(this.requireInitialPost, other.requireInitialPost) &&
                Objects.equal(this.discussionSubentryCount, other.discussionSubentryCount) &&
                Objects.equal(this.readState, other.readState) &&
                Objects.equal(this.unreadCount, other.unreadCount) &&
                Objects.equal(this.delayedPostAt, other.delayedPostAt) &&
                Objects.equal(this.assignmentId, other.assignmentId) &&
                Objects.equal(this.lockedAt, other.lockedAt) &&
                Objects.equal(this.locked, other.locked) &&
                Objects.equal(this.lockedForUser, other.lockedForUser) &&
                Objects.equal(this.lockExplanation, other.lockExplanation) &&
                Objects.equal(this.userName, other.userName) &&
                Arrays.equals(this.topicChildren, other.topicChildren) &&
                Objects.equal(this.rootTopicId, other.rootTopicId) &&
                Objects.equal(this.podcastHasStudentPosts, other.podcastHasStudentPosts) &&
                Objects.equal(this.podcastURL, other.podcastURL) &&
                Objects.equal(this.discussionType, other.discussionType) &&
                Arrays.equals(this.attachments, other.attachments) &&
                Objects.equal(this.permissions, other.permissions);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("author", author)
                .add("title", title)
                .add("message", message)
                .add("htmlUrl", htmlURL)
                .add("postedAt", postedAt)
                .add("lastReplyAt", lastReplyAt)
                .add("requiredInitialPost", requireInitialPost)
                .add("discussionSubentryCount", discussionSubentryCount)
                .add("readState", readState)
                .add("unreadCount", unreadCount)
                .add("assignmentId", assignmentId)
                .add("delayedPostAt", delayedPostAt)
                .add("lockedAt", lockedAt)
                .add("locked", locked)
                .add("lockedForUser", lockedForUser)
                .add("lockExplanation", lockExplanation)
                .add("userName", userName)
                .add("topicChildren", Arrays.toString(topicChildren))
                .add("podcastHasStudentPosts", podcastHasStudentPosts)
                .add("podcastURL", podcastURL)
                .add("discussionType", discussionType)
                .add("attachments", Arrays.toString(attachments))
                .add("permissions", permissions)
                .toString();
    }
}
