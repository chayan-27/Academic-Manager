
package com.example.iceb.server;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class APKdown {

    @SerializedName("con")
    @Expose
    private String con;

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

    public String getCon() {
        return con;
    }

    public void setCon(String con) {
        this.con = con;
    }

}
