package de.m0ep.canvas.model;

import java.util.Arrays;
import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class UserInfo {
    private long id;

    private String name;

    @SerializedName("sortable_name")
    private String sortableName;

    @SerializedName("short_name")
    private String shortName;

    @SerializedName("sis_user_id")
    private String sisUserId;

    @SerializedName("sis_login_id")
    private String sisLoginId;

    @SerializedName("login_id")
    private String loginId;

    @SerializedName("avatar_url")
    private String avatarUrl;

    private Enrollment[] enrollments;

    private String email;

    private String locale;

    @SerializedName("last_login")
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
	final int prime = 31;
	int result = 1;
	result = prime * result
		+ ((avatarUrl == null) ? 0 : avatarUrl.hashCode());
	result = prime * result + ((email == null) ? 0 : email.hashCode());
	result = prime * result + Arrays.hashCode(enrollments);
	result = prime * result + (int) (id ^ (id >>> 32));
	result = prime * result
		+ ((lastLogin == null) ? 0 : lastLogin.hashCode());
	result = prime * result + ((locale == null) ? 0 : locale.hashCode());
	result = prime * result + ((loginId == null) ? 0 : loginId.hashCode());
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result
		+ ((shortName == null) ? 0 : shortName.hashCode());
	result = prime * result
		+ ((sisLoginId == null) ? 0 : sisLoginId.hashCode());
	result = prime * result
		+ ((sisUserId == null) ? 0 : sisUserId.hashCode());
	result = prime * result
		+ ((sortableName == null) ? 0 : sortableName.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;

	if (obj == null)
	    return false;

	if (getClass() != obj.getClass())
	    return false;

	UserInfo other = (UserInfo) obj;

	if (avatarUrl == null) {
	    if (other.avatarUrl != null)
		return false;
	} else if (!avatarUrl.equals(other.avatarUrl))
	    return false;
	if (email == null) {
	    if (other.email != null)
		return false;
	} else if (!email.equals(other.email))
	    return false;
	if (!Arrays.equals(enrollments, other.enrollments))
	    return false;
	if (id != other.id)
	    return false;
	if (lastLogin == null) {
	    if (other.lastLogin != null)
		return false;
	} else if (!lastLogin.equals(other.lastLogin))
	    return false;
	if (locale == null) {
	    if (other.locale != null)
		return false;
	} else if (!locale.equals(other.locale))
	    return false;
	if (loginId == null) {
	    if (other.loginId != null)
		return false;
	} else if (!loginId.equals(other.loginId))
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	if (shortName == null) {
	    if (other.shortName != null)
		return false;
	} else if (!shortName.equals(other.shortName))
	    return false;
	if (sisLoginId == null) {
	    if (other.sisLoginId != null)
		return false;
	} else if (!sisLoginId.equals(other.sisLoginId))
	    return false;
	if (sisUserId == null) {
	    if (other.sisUserId != null)
		return false;
	} else if (!sisUserId.equals(other.sisUserId))
	    return false;
	if (sortableName == null) {
	    if (other.sortableName != null)
		return false;
	} else if (!sortableName.equals(other.sortableName))
	    return false;
	return true;
    }
}
