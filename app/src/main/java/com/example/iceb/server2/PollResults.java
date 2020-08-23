
package com.example.iceb.server2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PollResults {

    @SerializedName("Poll ID")
    @Expose
    private String pollID;
    @SerializedName("1")
    @Expose
    private Integer _1;
    @SerializedName("2")
    @Expose
    private Integer _2;
    @SerializedName("3")
    @Expose
    private Integer _3;
    @SerializedName("4")
    @Expose
    private Integer _4;
    @SerializedName("5")
    @Expose
    private Integer _5;

    /**
     * No args constructor for use in serialization
     * 
     */
    public PollResults() {
    }

    /**
     * 
     * @param _1
     * @param _2
     * @param _3
     * @param _4
     * @param _5
     * @param pollID
     */
    public PollResults(String pollID, Integer _1, Integer _2, Integer _3, Integer _4, Integer _5) {
        super();
        this.pollID = pollID;
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
        this._4 = _4;
        this._5 = _5;
    }

    public String getPollID() {
        return pollID;
    }

    public void setPollID(String pollID) {
        this.pollID = pollID;
    }

    public Integer get1() {
        return _1;
    }

    public void set1(Integer _1) {
        this._1 = _1;
    }

    public Integer get2() {
        return _2;
    }

    public void set2(Integer _2) {
        this._2 = _2;
    }

    public Integer get3() {
        return _3;
    }

    public void set3(Integer _3) {
        this._3 = _3;
    }

    public Integer get4() {
        return _4;
    }

    public void set4(Integer _4) {
        this._4 = _4;
    }

    public Integer get5() {
        return _5;
    }

    public void set5(Integer _5) {
        this._5 = _5;
    }

}
