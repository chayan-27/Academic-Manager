
package com.example.iceb.server2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notif {

    @SerializedName("to")
    @Expose
    private String to;
    @SerializedName("data")
    @Expose
    private Notification notification;

    public Notif(String to, Notification notification) {
        this.to = to;
        this.notification = notification;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

}
