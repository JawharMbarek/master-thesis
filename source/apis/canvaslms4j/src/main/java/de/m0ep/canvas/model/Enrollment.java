
package de.m0ep.canvas.model;

import com.google.common.base.Objects;
import com.google.gson.annotations.SerializedName;

public class Enrollment {
    /*
     * Constants
     */
    public static final String TYPE_STUDENT = "StudentEnrollment";
    public static final String TYPE_TEACHER = "TeacherEnrollment";
    public static final String TYPE_TA = "TaEnrollment";
    public static final String TYPE_OBSERVER = "ObserverEnrollment";
    public static final String TYPE_DESIGNER = "DesignerEnrollment";

    public static final String STATE_ACTIVE = "active";
    public static final String STATE_INVITED = "invited";
    public static final String STATE_CREATION_PENDING = "creation_pending";
    public static final String STATE_DELETED = "deleted";
    public static final String STATE_REJECTED = "rejected";
    public static final String STATE_COMPLETED = "completed";
    public static final String STATE_INACTIVE = "inactive";

    /*
     * Internal classes
     */
    public static class Grades {
        @SerializedName("")
        private String htmlUrl;

        @SerializedName("")
        private String currentGrade;

        @SerializedName("")
        private String finalGrade;

        public String getHtmlUrl() {
            return htmlUrl;
        }

        public String getCurrentGrade() {
            return currentGrade;
        }

        public String getFinalGrade() {
            return finalGrade;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(htmlUrl, currentGrade, finalGrade);
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

            Grades other = (Grades) obj;

            return Objects.equal(this.htmlUrl, other.htmlUrl) &&
                    Objects.equal(this.currentGrade, other.currentGrade) &&
                    Objects.equal(this.finalGrade, other.finalGrade);
        }

        @Override
        public String toString() {
            return Objects.toStringHelper(this)
                    .add("htmlUrl", htmlUrl)
                    .add("currentGrade", currentGrade)
                    .add("finalGrade", finalGrade)
                    .toString();
        }
    }

    public static class User {
        private long id;

        @SerializedName("login_id")
        private String loginId;

        private String name;

        @SerializedName("short_name")
        private String shortName;

        @SerializedName("sortable_name")
        private String sortableName;

        public long getId() {
            return id;
        }

        public String getLoginId() {
            return loginId;
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

        @Override
        public int hashCode() {
            return Objects.hashCode(id, loginId, name, sortableName, shortName);
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

            User other = (User) obj;

            return Objects.equal(this.id, other.id) &&
                    Objects.equal(this.loginId, other.loginId) &&
                    Objects.equal(this.name, other.name) &&
                    Objects.equal(this.shortName, other.shortName) &&
                    Objects.equal(this.sortableName, other.sortableName);
        }

        @Override
        public String toString() {
            return Objects.toStringHelper(this)
                    .add("id", id)
                    .add("loginId", loginId)
                    .add("name", name)
                    .add("shortName", shortName)
                    .add("sortableName", sortableName)
                    .toString();
        }
    }

    /*
     * Fields
     */

    private long id;

    @SerializedName("course_id")
    private long courseId;

    @SerializedName("course_section_id")
    private long courseSectionId;

    @SerializedName("enrollment_state")
    private String enrollmentState;

    @SerializedName(" limit_privileges_to_course_section")
    private boolean limitPrivilegesToCourseSection;

    @SerializedName("root_account_id")
    private long rootAccountId;

    private String type;

    private String role;

    @SerializedName("user_id")
    private long userId;

    @SerializedName("html_url")
    private String htmlUrl;

    private Grades grades;

    private User user;

    public static String getTypeStudent() {
        return TYPE_STUDENT;
    }

    public static String getTypeTeacher() {
        return TYPE_TEACHER;
    }

    public static String getTypeTa() {
        return TYPE_TA;
    }

    public static String getTypeObserver() {
        return TYPE_OBSERVER;
    }

    public static String getTypeDesigner() {
        return TYPE_DESIGNER;
    }

    public static String getStateActive() {
        return STATE_ACTIVE;
    }

    public static String getStateInvited() {
        return STATE_INVITED;
    }

    public static String getStateCreationPending() {
        return STATE_CREATION_PENDING;
    }

    public static String getStateDeleted() {
        return STATE_DELETED;
    }

    public static String getStateRejected() {
        return STATE_REJECTED;
    }

    public static String getStateCompleted() {
        return STATE_COMPLETED;
    }

    public static String getStateInactive() {
        return STATE_INACTIVE;
    }

    public long getId() {
        return id;
    }

    public long getCourseId() {
        return courseId;
    }

    public long getCourseSectionId() {
        return courseSectionId;
    }

    public String getEnrollmentState() {
        return enrollmentState;
    }

    public boolean isLimitPrivilegesToCourseSection() {
        return limitPrivilegesToCourseSection;
    }

    public long getRootAccountId() {
        return rootAccountId;
    }

    public String getType() {
        return type;
    }

    public String getRole() {
        return role;
    }

    public long getUserId() {
        return userId;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public Grades getGrades() {
        return grades;
    }

    public User getUser() {
        return user;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
                id,
                courseId,
                courseSectionId,
                enrollmentState,
                limitPrivilegesToCourseSection,
                rootAccountId,
                type,
                role,
                userId,
                htmlUrl,
                grades,
                user);
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

        Enrollment other = (Enrollment) obj;

        return Objects.equal(this.id, other.id) &&
                Objects.equal(this.courseId, other.courseId) &&
                Objects.equal(this.courseSectionId, other.courseSectionId) &&
                Objects.equal(this.enrollmentState, other.enrollmentState) &&
                Objects.equal(
                        this.limitPrivilegesToCourseSection,
                        other.limitPrivilegesToCourseSection) &&
                Objects.equal(this.rootAccountId, other.rootAccountId) &&
                Objects.equal(this.type, other.type) &&
                Objects.equal(this.role, other.role) &&
                Objects.equal(this.userId, other.userId) &&
                Objects.equal(this.htmlUrl, other.htmlUrl) &&
                Objects.equal(this.grades, other.grades) &&
                Objects.equal(this.user, other.user);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("courseId", courseId)
                .add("courseSectionId", courseSectionId)
                .add("enrollmentState", enrollmentState)
                .add("limitPrivilegesToCourseSection", limitPrivilegesToCourseSection)
                .add("rootAccountId", rootAccountId)
                .add("type", type)
                .add("role", role)
                .add("userId", userId)
                .add("htmlUrl", htmlUrl)
                .add("grades", grades)
                .add("user", user)
                .toString();
    }
}
