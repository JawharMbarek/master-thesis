package de.m0ep.canvaslms.model;

import java.util.Arrays;
import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class CourseInfo {

    public static class Enrollment {
	private String type;

	private String role;

	@SerializedName("computed_final_score")
	private Double computedFinalScore;

	@SerializedName("computed_current_score")
	private Integer computedCurrentScore;

	@SerializedName("computed_final_grade")
	private String computedFinalGrade;

	@SerializedName("computed_current_grade")
	private String computedCurrentGrade;

	public String getType() {
	    return type;
	}

	public String getRole() {
	    return role;
	}

	public Double getComputedFinalScore() {
	    return computedFinalScore;
	}

	public Integer getComputedCurrentScore() {
	    return computedCurrentScore;
	}

	public String getComputedFinalGrade() {
	    return computedFinalGrade;
	}

	public String getComputedCurrentGrade() {
	    return computedCurrentGrade;
	}

	@Override
	public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime
		    * result
		    + ((computedCurrentGrade == null) ? 0
			    : computedCurrentGrade.hashCode());
	    result = prime
		    * result
		    + ((computedCurrentScore == null) ? 0
			    : computedCurrentScore.hashCode());
	    result = prime
		    * result
		    + ((computedFinalGrade == null) ? 0 : computedFinalGrade
			    .hashCode());
	    result = prime
		    * result
		    + ((computedFinalScore == null) ? 0 : computedFinalScore
			    .hashCode());
	    result = prime * result + ((role == null) ? 0 : role.hashCode());
	    result = prime * result + ((type == null) ? 0 : type.hashCode());
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

	    Enrollment other = (Enrollment) obj;

	    if (computedCurrentGrade == null) {
		if (other.computedCurrentGrade != null)
		    return false;
	    } else if (!computedCurrentGrade.equals(other.computedCurrentGrade))
		return false;
	    if (computedCurrentScore == null) {
		if (other.computedCurrentScore != null)
		    return false;
	    } else if (!computedCurrentScore.equals(other.computedCurrentScore))
		return false;
	    if (computedFinalGrade == null) {
		if (other.computedFinalGrade != null)
		    return false;
	    } else if (!computedFinalGrade.equals(other.computedFinalGrade))
		return false;
	    if (computedFinalScore == null) {
		if (other.computedFinalScore != null)
		    return false;
	    } else if (!computedFinalScore.equals(other.computedFinalScore))
		return false;
	    if (role == null) {
		if (other.role != null)
		    return false;
	    } else if (!role.equals(other.role))
		return false;
	    if (type == null) {
		if (other.type != null)
		    return false;
	    } else if (!type.equals(other.type))
		return false;
	    return true;
	}
    }

    public static class Calendar {
	private String ics;

	public String getIcs() {
	    return ics;
	}

	@Override
	public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((ics == null) ? 0 : ics.hashCode());
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
	    Calendar other = (Calendar) obj;
	    if (ics == null) {
		if (other.ics != null)
		    return false;
	    } else if (!ics.equals(other.ics))
		return false;
	    return true;
	}
    }

    public static class Term {
	private long id;

	private String name;

	@SerializedName("start_at")
	private Date startAt;

	@SerializedName("end_at")
	private Date endAt;

	public long getId() {
	    return id;
	}

	public String getName() {
	    return name;
	}

	public Date getStartAt() {
	    return startAt;
	}

	public Date getEndAt() {
	    return endAt;
	}

	@Override
	public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((endAt == null) ? 0 : endAt.hashCode());
	    result = prime * result + (int) (id ^ (id >>> 32));
	    result = prime * result + ((name == null) ? 0 : name.hashCode());
	    result = prime * result
		    + ((startAt == null) ? 0 : startAt.hashCode());
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

	    Term other = (Term) obj;

	    if (endAt == null) {
		if (other.endAt != null)
		    return false;
	    } else if (!endAt.equals(other.endAt))
		return false;
	    if (id != other.id)
		return false;
	    if (name == null) {
		if (other.name != null)
		    return false;
	    } else if (!name.equals(other.name))
		return false;
	    if (startAt == null) {
		if (other.startAt != null)
		    return false;
	    } else if (!startAt.equals(other.startAt))
		return false;
	    return true;
	}
    }

    private long id;

    @SerializedName("sis_course_id")
    private String sisCourseId;

    private String name;

    @SerializedName("course_code")
    private String courseCode;

    @SerializedName("workflow_state")
    private String workflowState;

    @SerializedName("account_id")
    private long accountId;

    @SerializedName("start_at")
    private Date startAt;

    @SerializedName("end_at")
    private Date endAt;

    private Enrollment[] enrollments;

    private Calendar calendar;

    @SerializedName("default_view")
    private String defaultView;

    @SerializedName("syllabus_body")
    private String syllabusBody;

    @SerializedName(" needs_grading_count")
    private String needsGradingCount;

    private Term term;

    public long getId() {
	return id;
    }

    public String getSisCourseId() {
	return sisCourseId;
    }

    public String getName() {
	return name;
    }

    public String getCourseCode() {
	return courseCode;
    }

    public String getWorkflowState() {
	return workflowState;
    }

    public long getAccountId() {
	return accountId;
    }

    public Date getStartAt() {
	return startAt;
    }

    public Date getEndAt() {
	return endAt;
    }

    public Enrollment[] getEnrollments() {
	return enrollments;
    }

    public Calendar getCalendar() {
	return calendar;
    }

    public String getDefaultView() {
	return defaultView;
    }

    public String getSyllabusBody() {
	return syllabusBody;
    }

    public String getNeedsGradingCount() {
	return needsGradingCount;
    }

    public Term getTerm() {
	return term;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + (int) (accountId ^ (accountId >>> 32));
	result = prime * result
		+ ((calendar == null) ? 0 : calendar.hashCode());
	result = prime * result
		+ ((courseCode == null) ? 0 : courseCode.hashCode());
	result = prime * result
		+ ((defaultView == null) ? 0 : defaultView.hashCode());
	result = prime * result + ((endAt == null) ? 0 : endAt.hashCode());
	result = prime * result + Arrays.hashCode(enrollments);
	result = prime * result + (int) (id ^ (id >>> 32));
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime
		* result
		+ ((needsGradingCount == null) ? 0 : needsGradingCount
			.hashCode());
	result = prime * result
		+ ((sisCourseId == null) ? 0 : sisCourseId.hashCode());
	result = prime * result + ((startAt == null) ? 0 : startAt.hashCode());
	result = prime * result
		+ ((syllabusBody == null) ? 0 : syllabusBody.hashCode());
	result = prime * result + ((term == null) ? 0 : term.hashCode());
	result = prime * result
		+ ((workflowState == null) ? 0 : workflowState.hashCode());
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

	CourseInfo other = (CourseInfo) obj;

	if (accountId != other.accountId)
	    return false;
	if (calendar == null) {
	    if (other.calendar != null)
		return false;
	} else if (!calendar.equals(other.calendar))
	    return false;
	if (courseCode == null) {
	    if (other.courseCode != null)
		return false;
	} else if (!courseCode.equals(other.courseCode))
	    return false;
	if (defaultView == null) {
	    if (other.defaultView != null)
		return false;
	} else if (!defaultView.equals(other.defaultView))
	    return false;
	if (endAt == null) {
	    if (other.endAt != null)
		return false;
	} else if (!endAt.equals(other.endAt))
	    return false;
	if (!Arrays.equals(enrollments, other.enrollments))
	    return false;
	if (id != other.id)
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	if (needsGradingCount == null) {
	    if (other.needsGradingCount != null)
		return false;
	} else if (!needsGradingCount.equals(other.needsGradingCount))
	    return false;
	if (sisCourseId == null) {
	    if (other.sisCourseId != null)
		return false;
	} else if (!sisCourseId.equals(other.sisCourseId))
	    return false;
	if (startAt == null) {
	    if (other.startAt != null)
		return false;
	} else if (!startAt.equals(other.startAt))
	    return false;
	if (syllabusBody == null) {
	    if (other.syllabusBody != null)
		return false;
	} else if (!syllabusBody.equals(other.syllabusBody))
	    return false;
	if (term == null) {
	    if (other.term != null)
		return false;
	} else if (!term.equals(other.term))
	    return false;
	if (workflowState == null) {
	    if (other.workflowState != null)
		return false;
	} else if (!workflowState.equals(other.workflowState))
	    return false;
	return true;
    }
}
