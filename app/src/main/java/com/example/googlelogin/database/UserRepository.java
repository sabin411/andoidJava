package com.example.googlelogin.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

public class UserRepository {

    private final JourneyDatabase db;
    private final UserDao userDao;

    public UserRepository(Context context){
        db = JourneyDatabase.getInstances(context);
            userDao = db.userdao();
        }
        LiveData<List<User>> getLiveUsers(){
            return userDao.getUsersLiveData();
        }
        // to insert user information
    public long insertUser(User user){
        return userDao.insertAll(user);
    }
}
