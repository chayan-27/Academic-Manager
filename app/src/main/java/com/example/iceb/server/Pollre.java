
package com.example.iceb.server;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pollre {

    @SerializedName("yesval")
    @Expose
    private Integer yesval;

    @SerializedName("totalvotes")
    @Expose
    private Integer totalvotes;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Pollre() {
    }

    public Integer getTotalvotes() {
        return totalvotes;
    }

    public void setTotalvotes(Integer totalvotes) {
        this.totalvotes = totalvotes;
    }

    /**
     * 
     * @param yesval
     */
    public Pollre(Integer yesval,Integer totalvotes) {
        super();
        this.yesval = yesval;
        this.totalvotes=totalvotes;
    }

    public Integer getYesval() {
        return yesval;
    }

    public void setYesval(Integer yesval) {
        this.yesval = yesval;
    }

}
