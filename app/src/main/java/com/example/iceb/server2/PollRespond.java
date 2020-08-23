
package com.example.iceb.server2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PollRespond {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("rollno")
    @Expose
    private Integer rollno;
    @SerializedName("response")
    @Expose
    private Integer response;
    @SerializedName("poll_id")
    @Expose
    private Integer pollId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public PollRespond() {
    }

    /**
     * 
     * @param response
     * @param pollId
     * @param rollno
     * @param id
     */
    public PollRespond(Integer id, Integer rollno, Integer response, Integer pollId) {
        super();
        this.id = id;
        this.rollno = rollno;
        this.response = response;
        this.pollId = pollId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRollno() {
        return rollno;
    }

    public void setRollno(Integer rollno) {
        this.rollno = rollno;
    }

    public Integer getResponse() {
        return response;
    }

    public void setResponse(Integer response) {
        this.response = response;
    }

    public Integer getPollId() {
        return pollId;
    }

    public void setPollId(Integer pollId) {
        this.pollId = pollId;
    }

}
