
package com.example.iceb.server;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Timetable {

    @SerializedName("Section")
    @Expose
    private Object section;
    @SerializedName("Sem")
    @Expose
    private Integer sem;
    @SerializedName("timetable")
    @Expose
    private String timetable;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Timetable() {
    }

    /**
     * 
     * @param section
     * @param sem
     * @param timetable
     */
    public Timetable(Object section, Integer sem, String timetable) {
        super();
        this.section = section;
        this.sem = sem;
        this.timetable = timetable;
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

    public String getTimetable() {
        return timetable;
    }

    public void setTimetable(String timetable) {
        this.timetable = timetable;
    }

}
