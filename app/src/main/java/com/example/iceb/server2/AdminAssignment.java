
package com.example.iceb.server2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdminAssignment {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("topic")
    @Expose
    private String topic;
    @SerializedName("upload_date")
    @Expose
    private String uploadDate;
    @SerializedName("deadline")
    @Expose
    private String deadline;
    @SerializedName("file")
    @Expose
    private String file;
    @SerializedName("subject_id")
    @Expose
    private Integer subjectId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AdminAssignment() {
    }

    /**
     * 
     * @param file
     * @param uploadDate
     * @param topic
     * @param id
     * @param deadline
     * @param subjectId
     */
    public AdminAssignment(Integer id, String topic, String uploadDate, String deadline, String file, Integer subjectId) {
        super();
        this.id = id;
        this.topic = topic;
        this.uploadDate = uploadDate;
        this.deadline = deadline;
        this.file = file;
        this.subjectId = subjectId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

}
