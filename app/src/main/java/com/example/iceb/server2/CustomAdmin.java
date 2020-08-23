
package com.example.iceb.server2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomAdmin {

    @SerializedName("adminRollNo")
    @Expose
    private Integer adminRollNo;
    @SerializedName("adminPin")
    @Expose
    private Integer adminPin;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CustomAdmin() {
    }

    /**
     * 
     * @param adminPin
     * @param adminRollNo
     */
    public CustomAdmin(Integer adminRollNo, Integer adminPin) {
        super();
        this.adminRollNo = adminRollNo;
        this.adminPin = adminPin;
    }

    public Integer getAdminRollNo() {
        return adminRollNo;
    }

    public void setAdminRollNo(Integer adminRollNo) {
        this.adminRollNo = adminRollNo;
    }

    public Integer getAdminPin() {
        return adminPin;
    }

    public void setAdminPin(Integer adminPin) {
        this.adminPin = adminPin;
    }

}
