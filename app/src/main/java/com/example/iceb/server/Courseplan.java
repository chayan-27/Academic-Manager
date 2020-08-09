
package com.example.iceb.server;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Courseplan {

    @SerializedName("CouContent")
    @Expose
    private Object couContent;
    @SerializedName("Section")
    @Expose
    private Object section;
    @SerializedName("Sem")
    @Expose
    private Integer sem;
    @SerializedName("Subject")
    @Expose
    private String subject;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Courseplan() {
    }

    /**
     * 
     * @param subject
     * @param section
     * @param sem
     * @param couContent
     */
    public Courseplan(Object couContent, Object section, Integer sem, String subject) {
        super();
        this.couContent = couContent;
        this.section = section;
        this.sem = sem;
        this.subject = subject;
    }

    public Object getCouContent() {
        return couContent;
    }

    public void setCouContent(Object couContent) {
        this.couContent = couContent;
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

}
