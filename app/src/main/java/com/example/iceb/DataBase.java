package com.example.iceb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = LiveTest.class, version = 1, exportSchema = false)
public abstract class DataBase extends RoomDatabase {
    private static DataBase database;
    private static final Object LOCK = new Object();

    public static DataBase getInstance(Context context) {
        if (database == null) {
            synchronized (LOCK) {
                database = Room.databaseBuilder(context.getApplicationContext(), DataBase.class, "notification").build();
            }
        }
        return database;

    }
    public abstract  Dao getDao();
}
