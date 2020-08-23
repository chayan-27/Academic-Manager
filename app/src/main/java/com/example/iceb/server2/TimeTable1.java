
package com.example.iceb.server2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimeTable1 {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("semester")
    @Expose
    private Integer semester;
    @SerializedName("upload_date")
    @Expose
    private String uploadDate;
    @SerializedName("file")
    @Expose
    private String file;
    @SerializedName("classroom_id")
    @Expose
    private Integer classroomId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public TimeTable1() {
    }

    /**
     * 
     * @param file
     * @param uploadDate
     * @param classroomId
     * @param semester
     * @param id
     */
    public TimeTable1(Integer id, Integer semester, String uploadDate, String file, Integer classroomId) {
        super();
        this.id = id;
        this.semester = semester;
        this.uploadDate = uploadDate;
        this.file = file;
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

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Integer getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Integer classroomId) {
        this.classroomId = classroomId;
    }

}
