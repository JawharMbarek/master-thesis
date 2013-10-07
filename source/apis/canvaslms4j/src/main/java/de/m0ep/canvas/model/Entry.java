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

import java.util.Arrays;
import java.util.Date;

import com.google.common.base.Objects;
import com.google.gson.annotations.SerializedName;

public class Entry {
	public static final String READ_STATE_READ = "read";
	public static final String READ_STATE_UNREAD = "unread";

	private long id;

	@SerializedName( "parent_id" )
	private long parentId;

	@SerializedName( "user_id" )
	private long userId;

	@SerializedName( "editor_id" )
	private long editorId;

	@SerializedName( "user_name" )
	private String userName;

	private String message;

	@SerializedName( "read_state" )
	private String readState;

	@SerializedName( "forced_read_state" )
	private boolean forcedReadState;

	@SerializedName( "created_at" )
	private Date createdAt;

	@SerializedName( "updated_at" )
	private Date updatedAt;

	private Attachment attachment;

	@SerializedName( "recent_replies" )
	private Entry[] recentReplies;

	@SerializedName( "has_more_replies" )
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

	public Entry[] getRecentReplies() {
		return recentReplies;
	}

	public boolean hasMoreReplies() {
		return hasMoreReplies;
	}

	public boolean isDeleted() {
		return deleted;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(
		        id,
		        parentId,
		        userId,
		        editorId,
		        userName,
		        message,
		        readState,
		        forcedReadState,
		        createdAt,
		        updatedAt,
		        attachment,
		        Arrays.hashCode( recentReplies ),
		        hasMoreReplies,
		        deleted );
	}

	@Override
	public boolean equals( Object obj ) {
		if ( null == obj ) {
			return false;
		}

		if ( this == obj ) {
			return true;
		}

		if ( this.getClass() != obj.getClass() ) {
			return false;
		}

		Entry other = (Entry) obj;

		return Objects.equal( this.id, other.id ) &&
		        Objects.equal( this.parentId, other.parentId ) &&
		        Objects.equal( this.userId, other.userId ) &&
		        Objects.equal( this.editorId, other.editorId ) &&
		        Objects.equal( this.userName, other.userName ) &&
		        Objects.equal( this.message, other.message ) &&
		        Objects.equal( this.readState, other.readState ) &&
		        Objects.equal( this.forcedReadState, other.forcedReadState ) &&
		        Objects.equal( this.createdAt, other.createdAt ) &&
		        Objects.equal( this.updatedAt, other.updatedAt ) &&
		        Objects.equal( this.attachment, other.attachment ) &&
		        Arrays.equals( this.recentReplies, other.recentReplies ) &&
		        Objects.equal( this.hasMoreReplies, other.hasMoreReplies ) &&
		        Objects.equal( this.deleted, other.deleted );
	}

	@Override
	public String toString() {
		return Objects.toStringHelper( this )
		        .add( "id", id )
		        .add( "parentId", parentId )
		        .add( "userId", userId )
		        .add( "editorId", editorId )
		        .add( "userName", userName )
		        .add( "message", message )
		        .add( "readState", readState )
		        .add( "forcedReadState", forcedReadState )
		        .add( "createdAt", createdAt )
		        .add( "updatedAt", updatedAt )
		        .add( "attachment", attachment )
		        .add( "recentReplies", Arrays.toString( recentReplies ) )
		        .add( "hasMoreReplies", hasMoreReplies )
		        .add( "deleted", deleted )
		        .toString();
	}
}
