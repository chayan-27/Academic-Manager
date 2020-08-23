
package com.example.iceb.server2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AssignmentRe {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("rollno")
    @Expose
    private Integer rollno;
    @SerializedName("submission_timestamp")
    @Expose
    private String submissionTimestamp;
    @SerializedName("file")
    @Expose
    private String file;
    @SerializedName("assigned_assignment_id")
    @Expose
    private Integer assignedAssignmentId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AssignmentRe() {
    }

    /**
     * 
     * @param file
     * @param rollno
     * @param id
     * @param assignedAssignmentId
     * @param submissionTimestamp
     */
    public AssignmentRe(Integer id, Integer rollno, String submissionTimestamp, String file, Integer assignedAssignmentId) {
        super();
        this.id = id;
        this.rollno = rollno;
        this.submissionTimestamp = submissionTimestamp;
        this.file = file;
        this.assignedAssignmentId = assignedAssignmentId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRollno() {
        return rollno;
    }

    public void setRollno(Integer rollno) {
        this.rollno = rollno;
    }

    public String getSubmissionTimestamp() {
        return submissionTimestamp;
    }

    public void setSubmissionTimestamp(String submissionTimestamp) {
        this.submissionTimestamp = submissionTimestamp;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Integer getAssignedAssignmentId() {
        return assignedAssignmentId;
    }

    public void setAssignedAssignmentId(Integer assignedAssignmentId) {
        this.assignedAssignmentId = assignedAssignmentId;
    }

}
