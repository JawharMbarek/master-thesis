package de.m0ep.uni.ma.moodlewsapi.model;

import com.google.common.base.Objects;

public class CourseRecord {
    private String error;
    private int    id;
    private int    category;
    private long   sortorder;
    private String password;
    private String fullname;
    private String shortname;
    private String idnumber;
    private String summary;
    private String format;
    private int    showgrades;
    private int    newsitems;
    private String teacher;
    private String teachers;
    private String student;
    private String students;
    private int    guest;
    private int    startdate;
    private int    enrolperiod;
    private int    numsections;
    private int    marker;
    private int    maxbytes;
    private int    visible;
    private int    hiddensections;
    private int    groupmode;
    private int    groupmodeforce;
    private String lang;
    private String theme;
    private String cost;
    private int    timecreated;
    private int    timemodified;
    private int    metacourse;
    private int    myrole;

    CourseRecord() {
    }

    @Override
    public String toString() {
        return Objects.toStringHelper( this ).add( "id", id )
                .add( "fullname", fullname ).add( "shortname", shortname )
                .toString();
    }

    public String getError() {
        return this.error;
    }

    public int getId() {
        return this.id;
    }

    public int getCategory() {
        return this.category;
    }

    public long getSortorder() {
        return this.sortorder;
    }

    public String getPassword() {
        return this.password;
    }

    public String getFullname() {
        return this.fullname;
    }

    public String getShortname() {
        return this.shortname;
    }

    public String getIdnumber() {
        return this.idnumber;
    }

    public String getSummary() {
        return this.summary;
    }

    public String getFormat() {
        return this.format;
    }

    public int getShowgrades() {
        return this.showgrades;
    }

    public int getNewsitems() {
        return this.newsitems;
    }

    public String getTeacher() {
        return this.teacher;
    }

    public String getTeachers() {
        return this.teachers;
    }

    public String getStudent() {
        return this.student;
    }

    public String getStudents() {
        return this.students;
    }

    public int getGuest() {
        return this.guest;
    }

    public int getStartdate() {
        return this.startdate;
    }

    public int getEnrolperiod() {
        return this.enrolperiod;
    }

    public int getNumsections() {
        return this.numsections;
    }

    public int getMarker() {
        return this.marker;
    }

    public int getMaxbytes() {
        return this.maxbytes;
    }

    public int getVisible() {
        return this.visible;
    }

    public int getHiddensections() {
        return this.hiddensections;
    }

    public int getGroupmode() {
        return this.groupmode;
    }

    public int getGroupmodeforce() {
        return this.groupmodeforce;
    }

    public String getLang() {
        return this.lang;
    }

    public String getTheme() {
        return this.theme;
    }

    public String getCost() {
        return this.cost;
    }

    public int getTimecreated() {
        return this.timecreated;
    }

    public int getTimemodified() {
        return this.timemodified;
    }

    public int getMetacourse() {
        return this.metacourse;
    }

    public int getMyrole() {
        return this.myrole;
    }
}
