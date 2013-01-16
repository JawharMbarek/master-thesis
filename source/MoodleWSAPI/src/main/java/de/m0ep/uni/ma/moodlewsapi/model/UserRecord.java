package de.m0ep.uni.ma.moodlewsapi.model;

import com.google.common.base.Objects;


public class UserRecord {
    private String              error;
    private int                 id;
    private String              auth;
    private int                 confirmed;
    private int                 policyagreed;
    private int                 deleted;
    private String              username;
    private String              idnumber;
    private String              firstname;
    private String              lastname;
    private String              email;
    private String              icq;
    private int                 emailstop;
    private String              skype;
    private String              yahoo;
    private String              aim;
    private String              msn;
    private String              phone1;
    private String              phone2;
    private String              institution;
    private String              department;
    private String              address;
    private String              city;
    private String              country;
    private String              lang;
    private String              timezone;
    private int                 mnethostid;
    private String              lastip;
    private String              theme;
    private String              description;
    private int                 role;
    private ProfileitemRecord[] profile;

    UserRecord() {
    }

    @Override
    public String toString() {
        return Objects.toStringHelper( this ).add( "id", id )
                .add( "firstname", firstname ).add( "lastname", lastname )
                .add( "email", email ).toString();
    }

    public String getError() {
        return this.error;
    }

    public int getId() {
        return this.id;
    }

    public String getAuth() {
        return this.auth;
    }

    public int getConfirmed() {
        return this.confirmed;
    }

    public int getPolicyagreed() {
        return this.policyagreed;
    }

    public int getDeleted() {
        return this.deleted;
    }

    public String getUsername() {
        return this.username;
    }

    public String getIdnumber() {
        return this.idnumber;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public String getEmail() {
        return this.email;
    }

    public String getIcq() {
        return this.icq;
    }

    public int getEmailstop() {
        return this.emailstop;
    }

    public String getSkype() {
        return this.skype;
    }

    public String getYahoo() {
        return this.yahoo;
    }

    public String getAim() {
        return this.aim;
    }

    public String getMsn() {
        return this.msn;
    }

    public String getPhone1() {
        return this.phone1;
    }

    public String getPhone2() {
        return this.phone2;
    }

    public String getInstitution() {
        return this.institution;
    }

    public String getDepartment() {
        return this.department;
    }

    public String getAddress() {
        return this.address;
    }

    public String getCity() {
        return this.city;
    }

    public String getCountry() {
        return this.country;
    }

    public String getLang() {
        return this.lang;
    }

    public String getTimezone() {
        return this.timezone;
    }

    public int getMnethostid() {
        return this.mnethostid;
    }

    public String getLastip() {
        return this.lastip;
    }

    public String getTheme() {
        return this.theme;
    }

    public String getDescription() {
        return this.description;
    }

    public int getRole() {
        return this.role;
    }

    public ProfileitemRecord[] getProfile() {
        return this.profile;
    }
}
