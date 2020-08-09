
package com.example.iceb.server;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class APKdown {

    @SerializedName("con")
    @Expose
    private String con;

    @SerializedName("ver")
    @Expose
    private String ver;

    /**
     * No args constructor for use in serialization
     * 
     */
    public APKdown() {
    }

    /**
     * 
     * @param con
     */
    public APKdown(String con) {
        super();
        this.con = con;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getCon() {
        return con;
    }

    public void setCon(String con) {
        this.con = con;
    }

}
