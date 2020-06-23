
package com.example.iceb.server;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Poll {

    @SerializedName("Options")
    @Expose
    private String options;
    @SerializedName("PTitle")
    @Expose
    private String pTitle;
    @SerializedName("Section")
    @Expose
    private Object section;
    @SerializedName("Sem")
    @Expose
    private Integer sem;
    @SerializedName("Type")
    @Expose
    private String type;
    @SerializedName("pid")
    @Expose
    private Integer pid;
    @SerializedName("response")
    @Expose
    private Object response;
    @SerializedName("rollno")
    @Expose
    private Integer rollno;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Poll() {
    }

    /**
     * 
     * @param response
     * @param pTitle
     * @param options
     * @param rollno
     * @param section
     * @param sem
     * @param pid
     * @param type
     */
    public Poll(String options, String pTitle, Object section, Integer sem, String type, Integer pid, Object response, Integer rollno) {
        super();
        this.options = options;
        this.pTitle = pTitle;
        this.section = section;
        this.sem = sem;
        this.type = type;
        this.pid = pid;
        this.response = response;
        this.rollno = rollno;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getPTitle() {
        return pTitle;
    }

    public void setPTitle(String pTitle) {
        this.pTitle = pTitle;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    public Integer getRollno() {
        return rollno;
    }

    public void setRollno(Integer rollno) {
        this.rollno = rollno;
    }

}
