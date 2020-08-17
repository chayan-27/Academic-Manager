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
    private Integer title;
    private String body;

    @Ignore
    public LiveTest(Integer title, String body) {
        this.title = title;
        this.body = body;
    }

    public LiveTest(int id, Integer title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getTitle() {
        return title;
    }

    public void setTitle(Integer title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

