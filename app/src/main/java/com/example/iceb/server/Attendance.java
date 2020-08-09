
package com.example.iceb.server;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Attendance {

    @SerializedName("Absent")
    @Expose
    private Integer absent;
    @SerializedName("LastUpdated")
    @Expose
    private String lastUpdated;
    @SerializedName("Present")
    @Expose
    private Integer present;
    @SerializedName("RollNo")
    @Expose
    private Integer rollNo;
    @SerializedName("Section")
    @Expose
    private Object section;
    @SerializedName("Subject")
    @Expose
    private String subject;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Attendance() {
    }

    /**
     * 
     * @param lastUpdated
     * @param subject
     * @param absent
     * @param rollNo
     * @param section
     * @param present
     */
    public Attendance(Integer absent, String lastUpdated, Integer present, Integer rollNo, Object section, String subject) {
        super();
        this.absent = absent;
        this.lastUpdated = lastUpdated;
        this.present = present;
        this.rollNo = rollNo;
        this.section = section;
        this.subject = subject;
    }

    public Integer getAbsent() {
        return absent;
    }

    public void setAbsent(Integer absent) {
        this.absent = absent;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Integer getPresent() {
        return present;
    }

    public void setPresent(Integer present) {
        this.present = present;
    }

    public Integer getRollNo() {
        return rollNo;
    }

    public void setRollNo(Integer rollNo) {
        this.rollNo = rollNo;
    }

    public Object getSection() {
        return section;
    }

    public void setSection(Object section) {
        this.section = section;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

}
