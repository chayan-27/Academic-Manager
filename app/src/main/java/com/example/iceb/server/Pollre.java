
package com.example.iceb.server;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pollre {

    @SerializedName("yesval")
    @Expose
    private Integer yesval;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Pollre() {
    }

    /**
     * 
     * @param yesval
     */
    public Pollre(Integer yesval) {
        super();
        this.yesval = yesval;
    }

    public Integer getYesval() {
        return yesval;
    }

    public void setYesval(Integer yesval) {
        this.yesval = yesval;
    }

}
