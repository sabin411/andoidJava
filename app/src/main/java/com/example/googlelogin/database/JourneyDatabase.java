package com.example.googlelogin.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.googlelogin.Journal.Journal;
import com.example.googlelogin.Journal.JournalDao;

@Database(entities = {User.class, Journal.class}, exportSchema = false, version = 1)
public abstract class JourneyDatabase extends RoomDatabase {
    private static final String DB_NAME = "journey_db";
    private static JourneyDatabase instance;

    public static synchronized JourneyDatabase getInstances(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), JourneyDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public abstract UserDao userdao();
    public abstract JournalDao journalDao();
}
