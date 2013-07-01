
package de.m0ep.canvas.model;

import java.util.Arrays;
import java.util.Date;

import com.google.common.base.Objects;
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
        return Objects.hashCode(
                id,
                name,
                sortableName,
                shortName,
                sisLoginId,
                sisUserId,
                loginId,
                avatarUrl,
                Arrays.hashCode(enrollments),
                email,
                locale,
                lastLogin);
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

        UserInfo other = (UserInfo) obj;

        return Objects.equal(this.id, other.id) &&
                Objects.equal(this.name, other.name) &&
                Objects.equal(this.sortableName, other.sortableName) &&
                Objects.equal(this.shortName, other.sortableName) &&
                Objects.equal(this.sisUserId, other.sisUserId) &&
                Objects.equal(this.sisLoginId, other.sisLoginId) &&
                Objects.equal(this.loginId, other.loginId) &&
                Objects.equal(this.avatarUrl, other.avatarUrl) &&
                Arrays.equals(this.enrollments, other.enrollments) &&
                Objects.equal(this.email, other.email) &&
                Objects.equal(this.locale, other.locale) &&
                Objects.equal(this.lastLogin, other.lastLogin);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("sortableName", sortableName)
                .add("shortName", shortName)
                .add("sisUserId", sisUserId)
                .add("sisLoginId", sisLoginId)
                .add("loginId", loginId)
                .add("avatarUrl", avatarUrl)
                .add("enrtollments", Arrays.toString(enrollments))
                .add("email", email)
                .add("locale", locale)
                .add("lastLogin", lastLogin)
                .toString();
    }
}
