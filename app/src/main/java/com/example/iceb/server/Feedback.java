
package com.example.iceb.server;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Feedback {

    @SerializedName("FeedbackLoc")
    @Expose
    private Object feedbackLoc;
    @SerializedName("FeedbackRollNo")
    @Expose
    private Integer feedbackRollNo;
    @SerializedName("FeedbackText")
    @Expose
    private String feedbackText;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Feedback() {
    }

    /**
     * 
     * @param feedbackLoc
     * @param feedbackText
     * @param feedbackRollNo
     */
    public Feedback(Object feedbackLoc, Integer feedbackRollNo, String feedbackText) {
        super();
        this.feedbackLoc = feedbackLoc;
        this.feedbackRollNo = feedbackRollNo;
        this.feedbackText = feedbackText;
    }

    public Object getFeedbackLoc() {
        return feedbackLoc;
    }

    public void setFeedbackLoc(Object feedbackLoc) {
        this.feedbackLoc = feedbackLoc;
    }

    public Integer getFeedbackRollNo() {
        return feedbackRollNo;
    }

    public void setFeedbackRollNo(Integer feedbackRollNo) {
        this.feedbackRollNo = feedbackRollNo;
    }

    public String getFeedbackText() {
        return feedbackText;
    }

    public void setFeedbackText(String feedbackText) {
        this.feedbackText = feedbackText;
    }

}
