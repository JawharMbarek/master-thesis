package de.m0ep.canvas.model;

import com.google.gson.annotations.SerializedName;

public class UserProfile {
    private long id;

    private String name;

    @SerializedName("short_name")
    private String shortName;

    @SerializedName("sortable_name")
    private String sortableName;

    @SerializedName("primary_email")
    private String primaryEmail;

    @SerializedName("login_id")
    private String loginId;

    @SerializedName("sis_user_id")
    private String sisUserId;

    @SerializedName("sis_login_id")
    private String sisLoginId;

    @SerializedName("avatar_url")
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
	final int prime = 31;
	int result = 1;
	result = prime * result
		+ ((avatarUrl == null) ? 0 : avatarUrl.hashCode());
	result = prime * result
		+ ((calendar == null) ? 0 : calendar.hashCode());
	result = prime * result + (int) (id ^ (id >>> 32));
	result = prime * result + ((loginId == null) ? 0 : loginId.hashCode());
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result
		+ ((primaryEmail == null) ? 0 : primaryEmail.hashCode());
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
	UserProfile other = (UserProfile) obj;
	if (avatarUrl == null) {
	    if (other.avatarUrl != null)
		return false;
	} else if (!avatarUrl.equals(other.avatarUrl))
	    return false;
	if (calendar == null) {
	    if (other.calendar != null)
		return false;
	} else if (!calendar.equals(other.calendar))
	    return false;
	if (id != other.id)
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
	if (primaryEmail == null) {
	    if (other.primaryEmail != null)
		return false;
	} else if (!primaryEmail.equals(other.primaryEmail))
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
