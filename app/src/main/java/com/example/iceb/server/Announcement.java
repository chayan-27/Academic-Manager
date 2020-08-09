
package com.example.iceb.server;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Announcement {

    @SerializedName("AnnContent")
    @Expose
    private Object annContent;
    @SerializedName("AnnouncementDate")
    @Expose
    private String announcementDate;
    @SerializedName("Section")
    @Expose
    private Object section;
    @SerializedName("Sem")
    @Expose
    private Integer sem;
    @SerializedName("Title")
    @Expose
    private String title;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Announcement() {
    }

    /**
     * 
     * @param annContent
     * @param announcementDate
     * @param section
     * @param sem
     * @param title
     */
    public Announcement(Object annContent, String announcementDate, Object section, Integer sem, String title) {
        super();
        this.annContent = annContent;
        this.announcementDate = announcementDate;
        this.section = section;
        this.sem = sem;
        this.title = title;
    }

    public Object getAnnContent() {
        return annContent;
    }

    public void setAnnContent(Object annContent) {
        this.annContent = annContent;
    }

    public String getAnnouncementDate() {
        return announcementDate;
    }

    public void setAnnouncementDate(String announcementDate) {
        this.announcementDate = announcementDate;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
