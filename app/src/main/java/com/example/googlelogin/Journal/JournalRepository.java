package com.example.googlelogin.Journal;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.googlelogin.Journal.Journal;
import com.example.googlelogin.Journal.JournalDao;
import com.example.googlelogin.database.JourneyDatabase;

import java.util.List;

public class JournalRepository {

    private final JourneyDatabase db;
    private final JournalDao journalDao;

    public JournalRepository(Context context) {
        this.db = JourneyDatabase.getInstances(context);
        journalDao = db.journalDao();
    }
    long insertJournal(Journal journal){
    return journalDao.insertJournal(journal);
    }

    LiveData<List<Journal>> retrieveJournals(){
        return journalDao.retrieveJournals();
    }
}