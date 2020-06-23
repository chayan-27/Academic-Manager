
package com.example.iceb.server;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Assignment {

    @SerializedName("AssContent")
    @Expose
    private Object assContent;
    @SerializedName("Section")
    @Expose
    private Object section;
    @SerializedName("Sem")
    @Expose
    private Integer sem;
    @SerializedName("SubbmissionDate")
    @Expose
    private String subbmissionDate;
    @SerializedName("Subject")
    @Expose
    private String subject;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("UploadDate")
    @Expose
    private String uploadDate;
    @SerializedName("AID")
    private Integer aid;

    /**
     * No args constructor for use in serialization
     */
    public Assignment() {
    }

    /**
     * @param assContent
     * @param uploadDate
     * @param subject
     * @param section
     * @param sem
     * @param subbmissionDate
     * @param title
     */
    public Assignment(Object assContent, Object section, Integer sem, String subbmissionDate, String subject, String title, String uploadDate, Integer aid) {
        super();
        this.assContent = assContent;
        this.section = section;
        this.sem = sem;
        this.subbmissionDate = subbmissionDate;
        this.subject = subject;
        this.title = title;
        this.uploadDate = uploadDate;
        this.aid = aid;
    }

    public Object getAssContent() {
        return assContent;
    }

    public void setAssContent(Object assContent) {
        this.assContent = assContent;
    }

    public Object getSection() {
        return section;
    }

    public void setSection(Object section) {
        this.section = section;
    }

    public Integer getSem() {
        return sem;
    }

    public void setSem(Integer sem) {
        this.sem = sem;
    }

    public String getSubbmissionDate() {
        return subbmissionDate;
    }

    public void setSubbmissionDate(String subbmissionDate) {
        this.subbmissionDate = subbmissionDate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }
}
