
package com.example.iceb.server2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateClass {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("batch")
    @Expose
    private Integer batch;
    @SerializedName("department")
    @Expose
    private String department;
    @SerializedName("section")
    @Expose
    private String section;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CreateClass() {
    }

    /**
     * 
     * @param batch
     * @param section
     * @param id
     * @param department
     */
    public CreateClass(Integer id, Integer batch, String department, String section) {
        super();
        this.id = id;
        this.batch = batch;
        this.department = department;
        this.section = section;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBatch() {
        return batch;
    }

    public void setBatch(Integer batch) {
        this.batch = batch;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

}
