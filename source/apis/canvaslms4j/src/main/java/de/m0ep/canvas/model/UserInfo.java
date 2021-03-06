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

public class UserInfo {
	private long id;

	private String name;

	@SerializedName( "sortable_name" )
	private String sortableName;

	@SerializedName( "short_name" )
	private String shortName;

	@SerializedName( "sis_user_id" )
	private String sisUserId;

	@SerializedName( "sis_login_id" )
	private String sisLoginId;

	@SerializedName( "login_id" )
	private String loginId;

	@SerializedName( "avatar_url" )
	private String avatarUrl;

	private Enrollment[] enrollments;

	private String email;

	private String locale;

	@SerializedName( "last_login" )
	private Date lastLogin;

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getSortableName() {
		return sortableName;
	}

	public String getShortName() {
		return shortName;
	}

	public String getSisUserId() {
		return sisUserId;
	}

	public String getSisLoginId() {
		return sisLoginId;
	}

	public String getLoginId() {
		return loginId;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public Enrollment[] getEnrollments() {
		return enrollments;
	}

	public String getEmail() {
		return email;
	}

	public String getLocale() {
		return locale;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(
		        id,
		        name,
		        sortableName,
		        shortName,
		        sisLoginId,
		        sisUserId,
		        loginId,
		        avatarUrl,
		        Arrays.hashCode( enrollments ),
		        email,
		        locale,
		        lastLogin );
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

		UserInfo other = (UserInfo) obj;

		return Objects.equal( this.id, other.id ) &&
		        Objects.equal( this.name, other.name ) &&
		        Objects.equal( this.sortableName, other.sortableName ) &&
		        Objects.equal( this.shortName, other.sortableName ) &&
		        Objects.equal( this.sisUserId, other.sisUserId ) &&
		        Objects.equal( this.sisLoginId, other.sisLoginId ) &&
		        Objects.equal( this.loginId, other.loginId ) &&
		        Objects.equal( this.avatarUrl, other.avatarUrl ) &&
		        Arrays.equals( this.enrollments, other.enrollments ) &&
		        Objects.equal( this.email, other.email ) &&
		        Objects.equal( this.locale, other.locale ) &&
		        Objects.equal( this.lastLogin, other.lastLogin );
	}

	@Override
	public String toString() {
		return Objects.toStringHelper( this )
		        .add( "id", id )
		        .add( "name", name )
		        .add( "sortableName", sortableName )
		        .add( "shortName", shortName )
		        .add( "sisUserId", sisUserId )
		        .add( "sisLoginId", sisLoginId )
		        .add( "loginId", loginId )
		        .add( "avatarUrl", avatarUrl )
		        .add( "enrtollments", Arrays.toString( enrollments ) )
		        .add( "email", email )
		        .add( "locale", locale )
		        .add( "lastLogin", lastLogin )
		        .toString();
	}
}
