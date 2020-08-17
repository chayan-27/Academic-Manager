package com.example.iceb;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@androidx.room.Dao
public interface Dao {

    @Insert
   void insert(LiveTest liveTest);

    @Query("SELECT * FROM tasks ORDER BY id")
    LiveData<List<LiveTest>> loadall();
}
