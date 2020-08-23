
package com.example.iceb.server2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdminSubject {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("semester")
    @Expose
    private Integer semester;
    @SerializedName("subject_code")
    @Expose
    private String subjectCode;
    @SerializedName("classroom_id")
    @Expose
    private Integer classroomId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AdminSubject() {
    }

    /**
     * 
     * @param classroomId
     * @param semester
     * @param id
     * @param subjectCode
     */
    public AdminSubject(Integer id, Integer semester, String subjectCode, Integer classroomId) {
        super();
        this.id = id;
        this.semester = semester;
        this.subjectCode = subjectCode;
        this.classroomId = classroomId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public Integer getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Integer classroomId) {
        this.classroomId = classroomId;
    }

}
