package com.example.googlelogin.Journal;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.googlelogin.Journal.Journal;

import java.util.List;

@Dao
public interface JournalDao {
    @Insert
    long insertJournal(Journal journal);

    @Query("select * from journal")
    LiveData<List<Journal>> retrieveJournals();

    @Update
    void updateJournal(Journal journal);

    @Delete
    void deleteJournal(Journal journal);

}
