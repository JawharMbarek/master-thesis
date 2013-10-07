/*
 * The MIT License (MIT) Copyright © 2013 Florian Müller
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the “Software”), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package de.m0ep.canvas.model;

import com.google.common.base.Objects;
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

	@SerializedName( "is_public" )
	private boolean isPublic;

	@SerializedName( "followed_by_user" )
	private boolean followedByUser;

	@SerializedName( "join_level" )
	private String joinLevel;

	@SerializedName( "members_count" )
	private int membersCount;

	@SerializedName( "avatar_url" )
	private String avatarUrl;

	@SerializedName( "context_type" )
	private String contextType;

	@SerializedName( "course_id" )
	private long courseId;

	@SerializedName( "account_id" )
	private long accountId;

	private String role;

	@SerializedName( "group_category_id" )
	private long groupCategoryId;

	@SerializedName( "storage_quota_mb" )
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
		return Objects.hashCode(
		        id,
		        name,
		        description,
		        isPublic,
		        followedByUser,
		        joinLevel,
		        membersCount,
		        avatarUrl,
		        contextType,
		        courseId,
		        role,
		        groupCategoryId,
		        accountId,
		        storageQuotaMB );
	}

	@Override
	public boolean equals( Object obj ) {
		if ( obj == null ) {
			return false;
		}

		if ( this == obj ) {
			return true;
		}

		if ( getClass() != obj.getClass() ) {
			return false;
		}

		Group other = (Group) obj;

		return Objects.equal( this.id, other.id ) &&
		        Objects.equal( this.name, other.name ) &&
		        Objects.equal( this.description, other.description ) &&
		        Objects.equal( this.isPublic, other.isPublic ) &&
		        Objects.equal( this.followedByUser, other.followedByUser ) &&
		        Objects.equal( this.joinLevel, other.joinLevel ) &&
		        Objects.equal( this.membersCount, other.membersCount ) &&
		        Objects.equal( this.avatarUrl, other.avatarUrl ) &&
		        Objects.equal( this.contextType, other.contextType ) &&
		        Objects.equal( this.courseId, other.courseId ) &&
		        Objects.equal( this.accountId, other.accountId ) &&
		        Objects.equal( this.role, other.role ) &&
		        Objects.equal( this.groupCategoryId, other.groupCategoryId ) &&
		        Objects.equal( this.storageQuotaMB, other.storageQuotaMB );

	}

	@Override
	public String toString() {
		return Objects.toStringHelper( this )
		        .add( "id", id )
		        .add( "name", name )
		        .add( "description", description )
		        .add( "isPublic", isPublic )
		        .add( "followedByUser", followedByUser )
		        .add( "joinLevel", joinLevel )
		        .add( "memberCount", membersCount )
		        .add( "avatarUrl", avatarUrl )
		        .add( "contextType", contextType )
		        .add( "courseId", courseId )
		        .add( "accountId", accountId )
		        .add( "role", role )
		        .add( "groupCategoryId", groupCategoryId )
		        .add( "storageQuotaMB", storageQuotaMB )
		        .toString();
	}

}
