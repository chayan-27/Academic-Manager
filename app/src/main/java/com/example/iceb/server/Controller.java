
package com.example.iceb.server;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Controller {

    @SerializedName("ErrMsg")
    @Expose
    private Object errMsg;
    @SerializedName("Feedback")
    @Expose
    private List<Feedback> feedback = null;
    @SerializedName("announcements")
    @Expose
    private List<Announcement> announcements = null;
    @SerializedName("assignments")
    @Expose
    private List<Assignment> assignments = null;
    @SerializedName("attendance")
    @Expose
    private List<Attendance> attendance = null;
    @SerializedName("courseplan")
    @Expose
    private List<Courseplan> courseplan = null;
    @SerializedName("poll")
    @Expose
    private List<Poll> poll = null;
    @SerializedName("pollres")
    @Expose
    private List<Pollre> pollres = null;
    @SerializedName("studymaterial")
    @Expose
    private List<Studymaterial> studymaterial = null;
    @SerializedName("timetable")
    @Expose
    private List<Timetable> timetable = null;
    @SerializedName("user")
    @Expose
    private List<User> user = null;
    @SerializedName("UserFile")
    private List<UserFile> userFiles=null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Controller() {
    }

    /**
     * 
     * @param feedback
     * @param assignments
     * @param courseplan
     * @param errMsg
     * @param pollres
     * @param poll
     * @param studymaterial
     * @param announcements
     * @param user
     * @param attendance
     * @param timetable
     */
    public Controller(Object errMsg, List<Feedback> feedback, List<Announcement> announcements, List<Assignment> assignments, List<Attendance> attendance, List<Courseplan> courseplan, List<Poll> poll, List<Pollre> pollres, List<Studymaterial> studymaterial, List<Timetable> timetable, List<User> user,List<UserFile> userFiles) {
        super();
        this.errMsg = errMsg;
        this.feedback = feedback;
        this.announcements = announcements;
        this.assignments = assignments;
        this.attendance = attendance;
        this.courseplan = courseplan;
        this.poll = poll;
        this.pollres = pollres;
        this.studymaterial = studymaterial;
        this.timetable = timetable;
        this.user = user;
        this.userFiles=userFiles;
    }

    public Object getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(Object errMsg) {
        this.errMsg = errMsg;
    }

    public List<Feedback> getFeedback() {
        return feedback;
    }

    public void setFeedback(List<Feedback> feedback) {
        this.feedback = feedback;
    }

    public List<Announcement> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(List<Announcement> announcements) {
        this.announcements = announcements;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    public List<Attendance> getAttendance() {
        return attendance;
    }

    public void setAttendance(List<Attendance> attendance) {
        this.attendance = attendance;
    }

    public List<Courseplan> getCourseplan() {
        return courseplan;
    }

    public void setCourseplan(List<Courseplan> courseplan) {
        this.courseplan = courseplan;
    }

    public List<Poll> getPoll() {
        return poll;
    }

    public void setPoll(List<Poll> poll) {
        this.poll = poll;
    }

    public List<Pollre> getPollres() {
        return pollres;
    }

    public void setPollres(List<Pollre> pollres) {
        this.pollres = pollres;
    }

    public List<Studymaterial> getStudymaterial() {
        return studymaterial;
    }

    public void setStudymaterial(List<Studymaterial> studymaterial) {
        this.studymaterial = studymaterial;
    }

    public List<Timetable> getTimetable() {
        return timetable;
    }

    public void setTimetable(List<Timetable> timetable) {
        this.timetable = timetable;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    public List<UserFile> getUserFiles() {
        return userFiles;
    }

    public void setUserFiles(List<UserFile> userFiles) {
        this.userFiles = userFiles;
    }
}
