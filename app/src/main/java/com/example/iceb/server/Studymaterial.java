
package com.example.iceb.server;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Studymaterial {

    @SerializedName("Section")
    @Expose
    private Object section;
    @SerializedName("Sem")
    @Expose
    private Integer sem;
    @SerializedName("StuContent")
    @Expose
    private Object stuContent;
    @SerializedName("Subject")
    @Expose
    private String subject;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("UploadDate")
    @Expose
    private String uploadDate;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Studymaterial() {
    }

    /**
     * 
     * @param uploadDate
     * @param subject
     * @param section
     * @param sem
     * @param stuContent
     * @param title
     */
    public Studymaterial(Object section, Integer sem, Object stuContent, String subject, String title, String uploadDate) {
        super();
        this.section = section;
        this.sem = sem;
        this.stuContent = stuContent;
        this.subject = subject;
        this.title = title;
        this.uploadDate = uploadDate;
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

    public Object getStuContent() {
        return stuContent;
    }

    public void setStuContent(Object stuContent) {
        this.stuContent = stuContent;
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

}
