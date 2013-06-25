package de.m0ep.canvas.model;

import com.google.gson.annotations.SerializedName;

public class Group {
    public static final String JOIN_LEVEL_PARENT_CONTEXT_AUTO_JOIN = "parent_context_auto_join";
    public static final String JOIN_LEVEL_PARENT_CONTEXT_REQUEST = "parent_context_request";
    public static final String JOIN_LEVEL_INVITATION_ONLY = "invitation_only";

    public static final String CONTEXT_TYPE_COURSE = "course";
    public static final String CONTEXT_TYPE_ACCOUNT = "account";

    private long id;

    private String name;

    private String description;

    @SerializedName("is_public")
    private boolean isPublic;

    @SerializedName("followed_by_user")
    private boolean followedByUser;

    @SerializedName("join_level")
    private String joinLevel;

    @SerializedName("members_count")
    private int membersCount;

    @SerializedName("avatar_url")
    private String avatarUrl;

    @SerializedName("context_type")
    private String contextType;

    @SerializedName("course_id")
    private long courseId;

    @SerializedName("account_id")
    private long accountId;

    private String role;

    @SerializedName("group_category_id")
    private long groupCategoryId;

    @SerializedName("storage_quota_mb")
    private int storageQuotaMB;

    public static String getJoinLevelParentContextAutoJoin() {
	return JOIN_LEVEL_PARENT_CONTEXT_AUTO_JOIN;
    }

    public static String getJoinLevelParentContextRequest() {
	return JOIN_LEVEL_PARENT_CONTEXT_REQUEST;
    }

    public static String getJoinLevelInvitationOnly() {
	return JOIN_LEVEL_INVITATION_ONLY;
    }

    public static String getContextTypeCourse() {
	return CONTEXT_TYPE_COURSE;
    }

    public static String getContextTypeAccount() {
	return CONTEXT_TYPE_ACCOUNT;
    }

    public long getId() {
	return id;
    }

    public String getName() {
	return name;
    }

    public String getDescription() {
	return description;
    }

    public boolean isPublic() {
	return isPublic;
    }

    public boolean isFollowedByUser() {
	return followedByUser;
    }

    public String getJoinLevel() {
	return joinLevel;
    }

    public int getMembersCount() {
	return membersCount;
    }

    public String getAvatarUrl() {
	return avatarUrl;
    }

    public String getContextType() {
	return contextType;
    }

    public long getCourseId() {
	return courseId;
    }

    public long getAccountId() {
	return accountId;
    }

    public String getRole() {
	return role;
    }

    public long getGroupCategoryId() {
	return groupCategoryId;
    }

    public int getStorageQuotaMB() {
	return storageQuotaMB;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + (int) (accountId ^ (accountId >>> 32));
	result = prime * result
		+ ((avatarUrl == null) ? 0 : avatarUrl.hashCode());
	result = prime * result
		+ ((contextType == null) ? 0 : contextType.hashCode());
	result = prime * result + (int) (courseId ^ (courseId >>> 32));
	result = prime * result
		+ ((description == null) ? 0 : description.hashCode());
	result = prime * result + (followedByUser ? 1231 : 1237);
	result = prime * result
		+ (int) (groupCategoryId ^ (groupCategoryId >>> 32));
	result = prime * result + (int) (id ^ (id >>> 32));
	result = prime * result + (isPublic ? 1231 : 1237);
	result = prime * result
		+ ((joinLevel == null) ? 0 : joinLevel.hashCode());
	result = prime * result + membersCount;
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((role == null) ? 0 : role.hashCode());
	result = prime * result + storageQuotaMB;
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}

	if (obj == null) {
	    return false;
	}

	if (getClass() != obj.getClass()) {
	    return false;
	}

	Group other = (Group) obj;

	if (accountId != other.accountId) {
	    return false;
	}
	if (avatarUrl == null) {
	    if (other.avatarUrl != null) {
		return false;
	    }
	} else if (!avatarUrl.equals(other.avatarUrl)) {
	    return false;
	}
	if (contextType == null) {
	    if (other.contextType != null) {
		return false;
	    }
	} else if (!contextType.equals(other.contextType)) {
	    return false;
	}
	if (courseId != other.courseId) {
	    return false;
	}
	if (description == null) {
	    if (other.description != null) {
		return false;
	    }
	} else if (!description.equals(other.description)) {
	    return false;
	}
	if (followedByUser != other.followedByUser) {
	    return false;
	}
	if (groupCategoryId != other.groupCategoryId) {
	    return false;
	}
	if (id != other.id) {
	    return false;
	}
	if (isPublic != other.isPublic) {
	    return false;
	}
	if (joinLevel == null) {
	    if (other.joinLevel != null) {
		return false;
	    }
	} else if (!joinLevel.equals(other.joinLevel)) {
	    return false;
	}
	if (membersCount != other.membersCount) {
	    return false;
	}
	if (name == null) {
	    if (other.name != null) {
		return false;
	    }
	} else if (!name.equals(other.name)) {
	    return false;
	}
	if (role == null) {
	    if (other.role != null) {
		return false;
	    }
	} else if (!role.equals(other.role)) {
	    return false;
	}
	if (storageQuotaMB != other.storageQuotaMB) {
	    return false;
	}
	return true;
    }

}
