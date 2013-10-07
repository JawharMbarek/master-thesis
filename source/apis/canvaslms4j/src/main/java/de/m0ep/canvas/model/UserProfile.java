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

public class UserProfile {
	private long id;

	private String name;

	@SerializedName( "short_name" )
	private String shortName;

	@SerializedName( "sortable_name" )
	private String sortableName;

	@SerializedName( "primary_email" )
	private String primaryEmail;

	@SerializedName( "login_id" )
	private String loginId;

	@SerializedName( "sis_user_id" )
	private String sisUserId;

	@SerializedName( "sis_login_id" )
	private String sisLoginId;

	@SerializedName( "avatar_url" )
	private String avatarUrl;

	private Calendar calendar;

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getShortName() {
		return shortName;
	}

	public String getSortableName() {
		return sortableName;
	}

	public String getPrimaryEmail() {
		return primaryEmail;
	}

	public String getLoginId() {
		return loginId;
	}

	public String getSisUserId() {
		return sisUserId;
	}

	public String getSisLoginId() {
		return sisLoginId;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public Calendar getCalendar() {
		return calendar;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(
		        id,
		        name,
		        shortName,
		        sortableName,
		        primaryEmail,
		        loginId,
		        sisUserId,
		        sisLoginId,
		        avatarUrl,
		        calendar );
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

		UserProfile other = (UserProfile) obj;

		return Objects.equal( this.id, other.id ) &&
		        Objects.equal( this.name, other.name ) &&
		        Objects.equal( this.shortName, other.shortName ) &&
		        Objects.equal( this.sortableName, other.sortableName ) &&
		        Objects.equal( this.primaryEmail, other.primaryEmail ) &&
		        Objects.equal( this.loginId, other.loginId ) &&
		        Objects.equal( this.sisUserId, other.sisUserId ) &&
		        Objects.equal( this.sisLoginId, other.sisLoginId ) &&
		        Objects.equal( this.avatarUrl, other.avatarUrl ) &&
		        Objects.equal( this.calendar, other.calendar );
	}

	@Override
	public String toString() {
		return Objects.toStringHelper( this )
		        .add( "id", id )
		        .add( "name", name )
		        .add( "shortName", shortName )
		        .add( "sortableName", sortableName )
		        .add( "primaryEmail", primaryEmail )
		        .add( "loginId", loginId )
		        .add( "sisUserId", sisUserId )
		        .add( "sisLoginId", sisLoginId )
		        .add( "avatarUrl", avatarUrl )
		        .add( "calendar", calendar )
		        .toString();
	}
}
