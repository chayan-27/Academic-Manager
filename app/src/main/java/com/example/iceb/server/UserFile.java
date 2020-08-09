
package com.example.iceb.server;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserFile {

    @SerializedName("UploadedContent")
    @Expose
    private String uploadedContent;
    @SerializedName("Section")
    @Expose
    private String section;
    @SerializedName("Sem")
    @Expose
    private Integer sem;
    @SerializedName("RollNo")
    @Expose
    private Integer rollNo;
    @SerializedName("Subject")
    @Expose
    private String subject;
    @SerializedName("Title")
    @Expose
    private String title;

    /**
     * No args constructor for use in serialization
     * 
     */
    public UserFile() {
    }

    /**
     * 
     * @param uploadedContent
     * @param subject
     * @param rollNo
     * @param section
     * @param sem
     * @param title
     */
    public UserFile(String uploadedContent, String section, Integer sem, Integer rollNo, String subject, String title) {
        super();
        this.uploadedContent = uploadedContent;
        this.section = section;
        this.sem = sem;
        this.rollNo = rollNo;
        this.subject = subject;
        this.title = title;
    }

    public String getUploadedContent() {
        return uploadedContent;
    }

    public void setUploadedContent(String uploadedContent) {
        this.uploadedContent = uploadedContent;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public Integer getSem() {
        return sem;
    }

    public void setSem(Integer sem) {
        this.sem = sem;
    }

    public Integer getRollNo() {
        return rollNo;
    }

    public void setRollNo(Integer rollNo) {
        this.rollNo = rollNo;
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

}
