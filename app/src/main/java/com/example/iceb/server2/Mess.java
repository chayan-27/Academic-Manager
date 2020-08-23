
package com.example.iceb.server2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Mess {

    @SerializedName("message_id")
    @Expose
    private Integer messageId;

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

}
