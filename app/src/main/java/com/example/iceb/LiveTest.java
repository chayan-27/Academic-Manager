package com.example.iceb;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.PrimaryKey;

import java.util.Set;

@Entity(tableName = "tasks")
public class LiveTest {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String body;
    private String date;
    private String time;

    @Ignore
    public LiveTest(String title, String body,String date,String time) {
        this.title = title;
        this.body = body;
        this.date=date;
        this.time=time;
    }

    public LiveTest(int id, String title, String body,String date,String time) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.date=date;
        this.time=time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

